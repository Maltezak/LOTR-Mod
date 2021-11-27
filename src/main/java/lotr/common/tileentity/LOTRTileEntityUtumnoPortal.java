package lotr.common.tileentity;

import java.util.*;

import cpw.mods.fml.relauncher.*;
import lotr.common.*;
import lotr.common.world.LOTRTeleporterUtumno;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.world.Teleporter;

public class LOTRTileEntityUtumnoPortal
extends TileEntity {
    public static final int WIDTH = 3;
    public static final int HEIGHT = 30;
    public static final int PORTAL_ABOVE = 2;
    public static final int PORTAL_BELOW = 2;
    public static final int TARGET_COORDINATE_RANGE = 50000;
    public static final int TARGET_FUZZ_RANGE = 32;
    private int targetX;
    private int targetZ;
    private int targetResetTick;
    public void updateEntity() {
        if (this.worldObj.getBlock(this.xCoord, this.yCoord - 1, this.zCoord) == this.getBlockType()) {
            this.worldObj.setBlockToAir(this.xCoord, this.yCoord, this.zCoord);
        }
        if (!this.worldObj.isRemote) {
            if (this.targetResetTick > 0) {
                --this.targetResetTick;
            } else {
                this.targetX = MathHelper.getRandomIntegerInRange(this.worldObj.rand, -50000, 50000);
                this.targetZ = MathHelper.getRandomIntegerInRange(this.worldObj.rand, -50000, 50000);
                this.targetResetTick = 1200;
            }
        }
        if (!this.worldObj.isRemote) {
            List players = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(this.xCoord - 8, this.yCoord, this.zCoord - 8, this.xCoord + 9, this.yCoord + 60, this.zCoord + 9));
            for (Object obj : players) {
                EntityPlayer entityplayer = (EntityPlayer)obj;
                LOTRLevelData.getData(entityplayer).sendMessageIfNotReceived(LOTRGuiMessageTypes.UTUMNO_WARN);
            }
        }
        if (!this.worldObj.isRemote && this.worldObj.rand.nextInt(2000) == 0) {
            String s = "ambient.cave.cave";
            if (this.worldObj.rand.nextBoolean()) {
                s = "lotr:wight.ambience";
            }
            float volume = 6.0f;
            this.worldObj.playSoundEffect(this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5, s, volume, 0.8f + this.worldObj.rand.nextFloat() * 0.2f);
        }
    }

    public void transferEntity(Entity entity) {
        entity.fallDistance = 0.0f;
        if (!this.worldObj.isRemote) {
            LOTRTileEntityUtumnoPortal actingPortal = this.findActingTargetingPortal();
            int dimension = LOTRDimension.UTUMNO.dimensionID;
            LOTRTeleporterUtumno teleporter = LOTRTeleporterUtumno.newTeleporter(dimension);
            teleporter.setTargetCoords(actingPortal.targetX, actingPortal.targetZ);
            if (entity instanceof EntityPlayerMP) {
                MinecraftServer.getServer().getConfigurationManager().transferPlayerToDimension((EntityPlayerMP)entity, dimension, (Teleporter)teleporter);
            } else {
                LOTRMod.transferEntityToDimension(entity, dimension, teleporter);
            }
            entity.fallDistance = 0.0f;
            actingPortal.targetResetTick = 1200;
        }
    }

    private LOTRTileEntityUtumnoPortal findActingTargetingPortal() {
        int range;
        for (int i = range = 8; i >= -range; --i) {
            for (int k = range; k >= -range; --k) {
                TileEntity te;
                int i1 = this.xCoord + i;
                int j1 = this.yCoord;
                int k1 = this.zCoord + k;
                if (this.worldObj.getBlock(i1, j1, k1) != this.getBlockType() || !((te = this.worldObj.getTileEntity(i1, j1, k1)) instanceof LOTRTileEntityUtumnoPortal)) continue;
                return (LOTRTileEntityUtumnoPortal)te;
            }
        }
        return this;
    }

    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("TargetX", this.targetX);
        nbt.setInteger("TargetZ", this.targetZ);
        nbt.setInteger("TargetReset", this.targetResetTick);
    }

    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        this.targetX = nbt.getInteger("TargetX");
        this.targetZ = nbt.getInteger("TargetZ");
        this.targetResetTick = nbt.getInteger("TargetReset");
    }

    @SideOnly(value=Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
        return AxisAlignedBB.getBoundingBox(this.xCoord - 2, this.yCoord, this.zCoord - 2, this.xCoord + 3, this.yCoord + 30, this.zCoord + 3);
    }

    @SideOnly(value=Side.CLIENT)
    public double getMaxRenderDistanceSquared() {
        double d = 256.0;
        return d * d;
    }
}

