package lotr.common.tileentity;

import java.util.*;

import lotr.common.block.LOTRBlockAnimalJar;
import lotr.common.entity.*;
import lotr.common.entity.animal.LOTREntityButterfly;
import net.minecraft.block.Block;
import net.minecraft.entity.*;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.server.management.PlayerManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.*;

public class LOTRTileEntityAnimalJar
extends TileEntity {
    private NBTTagCompound jarEntityData;
    private Entity jarEntity;
    public int ticksExisted = -1;
    private float targetYaw;
    private boolean hasTargetYaw = false;

    public void updateEntity() {
        Random rand = this.worldObj.rand;
        super.updateEntity();
        if (this.ticksExisted < 0) {
            this.ticksExisted = rand.nextInt(100);
        }
        ++this.ticksExisted;
        this.getOrCreateJarEntity();
        if (this.jarEntity != null) {
            this.jarEntity.ticksExisted = this.ticksExisted;
            this.jarEntity.lastTickPosX = this.jarEntity.prevPosX = this.jarEntity.posX;
            this.jarEntity.lastTickPosY = this.jarEntity.prevPosY = this.jarEntity.posY;
            this.jarEntity.lastTickPosZ = this.jarEntity.prevPosZ = this.jarEntity.posZ;
            this.jarEntity.prevRotationYaw = this.jarEntity.rotationYaw;
            if (this.jarEntity instanceof AnimalJarUpdater) {
                ((AnimalJarUpdater)this.jarEntity).updateInAnimalJar();
            }
            if (!this.worldObj.isRemote) {
                if (this.jarEntity instanceof EntityLiving) {
                    EntityLiving jarLiving = (EntityLiving)this.jarEntity;
                    ++jarLiving.livingSoundTime;
                    if (rand.nextInt(1000) < jarLiving.livingSoundTime) {
                        jarLiving.livingSoundTime = -jarLiving.getTalkInterval();
                        jarLiving.playLivingSound();
                    }
                    if (rand.nextInt(200) == 0) {
                        this.targetYaw = rand.nextFloat() * 360.0f;
                        this.sendJarPacket(1);
                        this.jarEntity.rotationYaw = this.targetYaw;
                    }
                }
            } else if (this.hasTargetYaw) {
                float delta = this.targetYaw - this.jarEntity.rotationYaw;
                delta = MathHelper.wrapAngleTo180_float(delta);
                this.jarEntity.rotationYaw += (delta *= 0.1f);
                if (Math.abs(this.jarEntity.rotationYaw - this.targetYaw) <= 0.01f) {
                    this.hasTargetYaw = false;
                }
            }
        }
    }

    public void setEntityData(NBTTagCompound nbt) {
        this.jarEntityData = nbt;
        this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
        this.markDirty();
    }

    public void clearEntityData() {
        this.jarEntity = null;
        this.setEntityData(null);
    }

    public NBTTagCompound getEntityData() {
        return this.jarEntityData;
    }

    public Entity getOrCreateJarEntity() {
        if (this.jarEntityData == null || this.jarEntityData.hasNoTags()) {
            return null;
        }
        if (this.jarEntity == null) {
            this.jarEntity = EntityList.createEntityFromNBT(this.jarEntityData, this.worldObj);
            this.jarEntity.ticksExisted = this.ticksExisted;
            float[] coords = this.getInitialEntityCoords(this.jarEntity);
            this.jarEntity.setLocationAndAngles(coords[0], coords[1], coords[2], this.jarEntity.rotationYaw, this.jarEntity.rotationPitch);
        }
        return this.jarEntity;
    }

    private float[] getInitialEntityCoords(Entity entity) {
        float[] xyz = new float[]{this.xCoord + 0.5f, this.yCoord + this.getEntityHeight() - entity.height / 2.0f, this.zCoord + 0.5f};
        return xyz;
    }

    private float getEntityHeight() {
        Block block = this.getBlockType();
        if (block instanceof LOTRBlockAnimalJar) {
            return ((LOTRBlockAnimalJar)block).getJarEntityHeight();
        }
        return 0.5f;
    }

    public float getEntityBobbing(float f) {
        return MathHelper.sin((this.ticksExisted + f) * 0.2f) * 0.05f;
    }

    public boolean isEntityWatching() {
        this.getOrCreateJarEntity();
        return this.jarEntity instanceof LOTREntityButterfly;
    }

    public int getLightValue() {
        this.getOrCreateJarEntity();
        if (this.jarEntity instanceof LOTREntityButterfly && ((LOTREntityButterfly)this.jarEntity).getButterflyType() == LOTREntityButterfly.ButterflyType.LORIEN) {
            return 7;
        }
        return -1;
    }

    public void setWorldObj(World world) {
        super.setWorldObj(world);
        if (this.jarEntity != null) {
            this.jarEntity = null;
        }
    }

    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        this.getOrCreateJarEntity();
        if (this.jarEntity != null && this.jarEntityData != null) {
            this.jarEntity.writeToNBTOptional(this.jarEntityData);
        }
        if (this.jarEntityData != null) {
            nbt.setTag("JarEntityData", this.jarEntityData);
        }
    }

    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        if (nbt.hasKey("JarEntityData")) {
            this.jarEntityData = nbt.getCompoundTag("JarEntityData");
        } else if (nbt.hasKey("ButterflyData")) {
            this.jarEntityData = nbt.getCompoundTag("ButterflyData");
            this.jarEntityData.setString("id", LOTREntities.getStringFromClass(LOTREntityButterfly.class));
        }
        if (this.jarEntity != null) {
            this.jarEntity.readFromNBT(this.jarEntityData);
        }
    }

    public Packet getDescriptionPacket() {
        return this.getJarPacket(0);
    }

    private Packet getJarPacket(int type) {
        this.getOrCreateJarEntity();
        NBTTagCompound data = new NBTTagCompound();
        data.setByte("JarPacketType", (byte)type);
        if (type == 0) {
            this.writeToNBT(data);
        } else if (type == 1) {
            data.setFloat("TargetYaw", this.targetYaw);
        }
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 0, data);
    }

    private void sendJarPacket(int type) {
        Packet packet = this.getJarPacket(type);
        int i = MathHelper.floor_double(this.xCoord) >> 4;
        int k = MathHelper.floor_double(this.zCoord) >> 4;
        PlayerManager playermanager = ((WorldServer)this.worldObj).getPlayerManager();
        List players = this.worldObj.playerEntities;
        for (Object obj : players) {
            EntityPlayerMP entityplayer = (EntityPlayerMP)obj;
            if (!playermanager.isPlayerWatchingChunk(entityplayer, i, k)) continue;
            entityplayer.playerNetServerHandler.sendPacket(packet);
        }
    }

    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet) {
        this.getOrCreateJarEntity();
        NBTTagCompound data = packet.func_148857_g();
        byte type = 0;
        if (data.hasKey("JarPacketType")) {
            type = data.getByte("JarPacketType");
        }
        if (type == 0) {
            this.readFromNBT(packet.func_148857_g());
        } else if (type == 1) {
            this.targetYaw = data.getFloat("TargetYaw");
            this.hasTargetYaw = true;
        }
        this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
    }
}

