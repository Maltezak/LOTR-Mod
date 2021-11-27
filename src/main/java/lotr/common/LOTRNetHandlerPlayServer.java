package lotr.common;

import com.google.common.primitives.*;

import cpw.mods.fml.common.FMLLog;
import lotr.common.entity.LOTRMountFunctions;
import lotr.common.item.LOTRWeaponStats;
import lotr.common.network.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.network.*;
import net.minecraft.network.play.client.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.*;

public class LOTRNetHandlerPlayServer
extends NetHandlerPlayServer {
    private MinecraftServer theServer;
    private double defaultReach = -1.0;
    private int lastAttackTime = 0;
    private double lastX;
    private double lastY;
    private double lastZ;
    private int floatingMountTick;

    public LOTRNetHandlerPlayServer(MinecraftServer server, NetworkManager nm, EntityPlayerMP entityplayer) {
        super(server, nm, entityplayer);
        this.theServer = server;
    }

    public void update() {
        this.updateAttackTime();
    }

    public void processInput(C0CPacketInput packet) {
        super.processInput(packet);
        float forward = packet.func_149616_d();
        float strafing = packet.func_149620_c();
        boolean jump = packet.func_149618_e();
        if (forward != 0.0f || strafing != 0.0f || jump) {
            LOTRLevelData.getData(this.playerEntity).cancelFastTravel();
        }
    }

    public void processPlayer(C03PacketPlayer packet) {
        super.processPlayer(packet);
        if (!this.playerEntity.isRiding() && packet.func_149466_j()) {
            double newX = packet.func_149464_c();
            double newY = packet.func_149467_d();
            double newZ = packet.func_149472_e();
            if (newX != this.lastX || newY != this.lastY || newZ != this.lastZ) {
                LOTRLevelData.getData(this.playerEntity).cancelFastTravel();
            }
        }
        this.lastX = this.playerEntity.posX;
        this.lastY = this.playerEntity.posY;
        this.lastZ = this.playerEntity.posZ;
    }

    public void processMountControl(LOTRPacketMountControl packet) {
        double x = packet.posX;
        double y = packet.posY;
        double z = packet.posZ;
        float yaw = packet.rotationYaw;
        float pitch = packet.rotationPitch;
        if ((!Doubles.isFinite(x) || !Doubles.isFinite(y) || !Doubles.isFinite(z) || !Floats.isFinite(yaw) || !Floats.isFinite(pitch))) {
            this.playerEntity.playerNetServerHandler.kickPlayerFromServer("Invalid mount movement");
            return;
        }
        Entity mount = this.playerEntity.ridingEntity;
        if (mount != null && mount != this.playerEntity && mount.riddenByEntity == this.playerEntity && LOTRMountFunctions.isMountControllable(mount)) {
            WorldServer world = this.playerEntity.getServerForPlayer();
            MinecraftServer server = world.func_73046_m();
            double d0 = mount.posX;
            double dx = x - d0;
            double d1 = mount.posY;
            double dy = y - d1;
            double d2 = mount.posZ;
            double dz = z - d2;
            double distSq = dx * dx + dy * dy + dz * dz;
            double speedSq = mount.motionX * mount.motionX + mount.motionY * mount.motionY + mount.motionZ * mount.motionZ;
            if (((distSq - speedSq > 150.0) && (!server.isSinglePlayer() || !server.getServerOwner().equals(this.playerEntity.getCommandSenderName())))) {
                LOTRPacketMountControlServerEnforce pktClient = new LOTRPacketMountControlServerEnforce(mount);
                LOTRPacketHandler.networkWrapper.sendTo(pktClient, this.playerEntity);
                return;
            }
            double check = 0.0625;
            boolean noCollideBeforeMove = world.getCollidingBoundingBoxes(mount, mount.boundingBox.copy().contract(check, check, check)).isEmpty();
            dx = x - d0;
            dy = y - d1 - 1.0E-6;
            dz = z - d2;
            mount.moveEntity(dx, dy, dz);
            double movedY = dy;
            dx = x - mount.posX;
            dy = y - mount.posY;
            dz = z - mount.posZ;
            if (dy > -0.5 || dy < 0.5) {
                dy = 0.0;
            }
            distSq = dx * dx + dy * dy + dz * dz;
            boolean clientServerConflict = false;
            if (distSq > 10.0) {
                clientServerConflict = true;
            }
            mount.setPositionAndRotation(x, y, z, yaw, pitch);
            this.playerEntity.setPositionAndRotation(x, y, z, yaw, pitch);
            boolean noCollideAfterMove = world.getCollidingBoundingBoxes(mount, mount.boundingBox.copy().contract(check, check, check)).isEmpty();
            if (noCollideBeforeMove && (clientServerConflict || !noCollideAfterMove)) {
                mount.setPositionAndRotation(d0, d1, d2, yaw, pitch);
                this.playerEntity.setPositionAndRotation(d0, d1, d2, yaw, pitch);
                LOTRPacketMountControlServerEnforce pktClient = new LOTRPacketMountControlServerEnforce(mount);
                LOTRPacketHandler.networkWrapper.sendTo(pktClient, this.playerEntity);
                return;
            }
            AxisAlignedBB flyCheckBox = mount.boundingBox.copy().expand(check, check, check).addCoord(0.0, -0.55, 0.0);
            if (!server.isFlightAllowed() && !world.checkBlockCollision(flyCheckBox)) {
                if (movedY >= -0.03125) {
                    ++this.floatingMountTick;
                    if (this.floatingMountTick > 80) {
                        FMLLog.warning(this.playerEntity.getCommandSenderName() + " was kicked for floating too long on mount " + mount.getCommandSenderName() + "!");
                        this.kickPlayerFromServer("Flying is not enabled on this server");
                        return;
                    }
                }
            } else {
                this.floatingMountTick = 0;
            }
            server.getConfigurationManager().updatePlayerPertinentChunks(this.playerEntity);
            this.playerEntity.addMovementStat(this.playerEntity.posX - d0, this.playerEntity.posY - d1, this.playerEntity.posZ - d2);
        }
    }

    public void processUseEntity(C02PacketUseEntity packet) {
        WorldServer world = this.theServer.worldServerForDimension(this.playerEntity.dimension);
        Entity target = packet.func_149564_a(world);
        this.playerEntity.func_143004_u();
        if (target != null) {
            ItemStack itemstack = this.playerEntity.getHeldItem();
            double reach = LOTRWeaponStats.getMeleeReachDistance(this.playerEntity);
            reach += LOTRWeaponStats.getMeleeExtraLookWidth();
            reach += target.getCollisionBorderSize();
            int attackTime = LOTRWeaponStats.getAttackTimePlayer(itemstack);
            if (this.playerEntity.getDistanceSqToEntity(target) < reach * reach) {
                if (packet.func_149565_c() == C02PacketUseEntity.Action.INTERACT) {
                    this.playerEntity.interactWith(target);
                } else if (((packet.func_149565_c() == C02PacketUseEntity.Action.ATTACK) && ((this.lastAttackTime <= 0) || !(target instanceof EntityLivingBase)))) {
                    if (target instanceof EntityItem || target instanceof EntityXPOrb || target instanceof EntityArrow || target == this.playerEntity) {
                        this.kickPlayerFromServer("Attempting to attack an invalid entity");
                        this.theServer.logWarning("Player " + this.playerEntity.getCommandSenderName() + " tried to attack an invalid entity");
                        return;
                    }
                    this.playerEntity.attackTargetEntityWithCurrentItem(target);
                    this.lastAttackTime = attackTime;
                }
            }
        }
    }

    public void updateAttackTime() {
        if (this.lastAttackTime > 0) {
            --this.lastAttackTime;
        }
    }

    public void processPlayerDigging(C07PacketPlayerDigging packet) {
        this.setBlockReach();
        super.processPlayerDigging(packet);
    }

    public void processPlayerBlockPlacement(C08PacketPlayerBlockPlacement packet) {
        this.setBlockReach();
        super.processPlayerBlockPlacement(packet);
    }

    private void setBlockReach() {
        if (this.defaultReach == -1.0) {
            this.defaultReach = this.playerEntity.theItemInWorldManager.getBlockReachDistance();
        }
        double reach = this.defaultReach;
        this.playerEntity.theItemInWorldManager.setBlockReachDistance(reach *= LOTRWeaponStats.getMeleeReachFactor(this.playerEntity.getHeldItem()));
    }
}

