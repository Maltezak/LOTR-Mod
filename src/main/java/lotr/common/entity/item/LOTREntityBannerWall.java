package lotr.common.entity.item;

import java.util.List;

import cpw.mods.fml.relauncher.*;
import lotr.common.*;
import lotr.common.item.LOTRItemBanner;
import net.minecraft.block.Block;
import net.minecraft.entity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class LOTREntityBannerWall
extends EntityHanging {
    private NBTTagCompound protectData;
    private boolean updatedClientBB = false;

    public LOTREntityBannerWall(World world) {
        super(world);
        this.setSize(0.0f, 0.0f);
    }

    public LOTREntityBannerWall(World world, int i, int j, int k, int dir) {
        super(world, i, j, k, dir);
        this.setSize(0.0f, 0.0f);
        this.setDirection(dir);
    }

    protected void entityInit() {
        this.dataWatcher.addObject(10, 0);
        this.dataWatcher.addObject(11, 0);
        this.dataWatcher.addObject(12, 0);
        this.dataWatcher.addObject(13, (byte) 0);
        this.dataWatcher.addObject(18, (byte) 0);
    }

    private void updateWatchedDirection() {
        this.dataWatcher.updateObject(10, this.field_146063_b);
        this.dataWatcher.updateObject(11, this.field_146064_c);
        this.dataWatcher.updateObject(12, this.field_146062_d);
        this.dataWatcher.updateObject(13, (byte) this.hangingDirection);
    }

    public void getWatchedDirection() {
        this.field_146063_b = this.dataWatcher.getWatchableObjectInt(10);
        this.field_146064_c = this.dataWatcher.getWatchableObjectInt(11);
        this.field_146062_d = this.dataWatcher.getWatchableObjectInt(12);
        this.hangingDirection = this.dataWatcher.getWatchableObjectByte(13);
    }

    private int getBannerTypeID() {
        return this.dataWatcher.getWatchableObjectByte(18);
    }

    private void setBannerTypeID(int i) {
        this.dataWatcher.updateObject(18, ((byte)i));
    }

    public void setBannerType(LOTRItemBanner.BannerType type) {
        this.setBannerTypeID(type.bannerID);
    }

    public LOTRItemBanner.BannerType getBannerType() {
        return LOTRItemBanner.BannerType.forID(this.getBannerTypeID());
    }

    public void setProtectData(NBTTagCompound nbt) {
        this.protectData = nbt;
    }

    public void setDirection(int dir) {
        float zEdge;
        float xSize;
        float zSize;
        float edge;
        float xEdge;
        if (dir < 0 || dir >= Direction.directions.length) {
            dir = 0;
        }
        this.hangingDirection = dir;
        this.prevRotationYaw = this.rotationYaw = Direction.rotateOpposite[dir] * 90.0f;
        float width = 1.0f;
        float thickness = 0.0625f;
        float yEdge = edge = 0.01f;
        if (dir == 0 || dir == 2) {
            xSize = width;
            zSize = thickness;
            xEdge = thickness + edge;
            zEdge = edge;
        } else {
            xSize = thickness;
            zSize = width;
            xEdge = edge;
            zEdge = thickness + edge;
        }
        float f = this.field_146063_b + 0.5f;
        float f1 = this.field_146064_c + 0.5f;
        float f2 = this.field_146062_d + 0.5f;
        float f3 = 0.5f + thickness / 2.0f;
        this.setPosition(f += Direction.offsetX[dir] * f3, f1, f2 += Direction.offsetZ[dir] * f3);
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.boundingBox.setBounds(f - xSize / 2.0f, f1 - 1.5f, f2 - zSize / 2.0f, f + xSize / 2.0f, f1 + 0.5f, f2 + zSize / 2.0f);
        this.boundingBox.setBB(this.boundingBox.contract(xEdge, yEdge, zEdge));
        if (!this.worldObj.isRemote) {
            this.updateWatchedDirection();
        }
    }

    @SideOnly(value=Side.CLIENT)
    public int getBrightnessForRender(float f) {
        int i;
        int k;
        if (!this.updatedClientBB) {
            this.getWatchedDirection();
            this.setDirection(this.hangingDirection);
            this.updatedClientBB = true;
        }
        if (this.worldObj.blockExists(i = MathHelper.floor_double(this.posX), 0, k = MathHelper.floor_double(this.posZ))) {
            int j = MathHelper.floor_double(this.posY);
            return this.worldObj.getLightBrightnessForSkyBlocks(i, j, k, 0);
        }
        return 0;
    }

    public void onUpdate() {
        if (this.worldObj.isRemote && !this.updatedClientBB) {
            this.getWatchedDirection();
            this.setDirection(this.hangingDirection);
            this.updatedClientBB = true;
        }
        super.onUpdate();
    }

    public boolean onValidSurface() {
        if (!this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).isEmpty()) {
            return false;
        }
        int i = this.field_146063_b;
        int j = this.field_146064_c;
        int k = this.field_146062_d;
        Block block = this.worldObj.getBlock(i, j, k);
        if (!block.getMaterial().isSolid()) {
            return false;
        }
        List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox);
        for (Object obj : list) {
            if (!(obj instanceof EntityHanging)) continue;
            return false;
        }
        return true;
    }

    public void writeEntityToNBT(NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        nbt.setByte("BannerType", (byte)this.getBannerTypeID());
        if (this.protectData != null) {
            nbt.setTag("ProtectData", this.protectData);
        }
    }

    public void readEntityFromNBT(NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        this.setBannerTypeID(nbt.getByte("BannerType"));
        if (nbt.hasKey("ProtectData")) {
            this.protectData = nbt.getCompoundTag("ProtectData");
        }
    }

    public boolean attackEntityFrom(DamageSource damagesource, float f) {
        if (!this.worldObj.isRemote && damagesource.getEntity() instanceof EntityPlayer && LOTRBannerProtection.isProtected(this.worldObj, this, LOTRBannerProtection.forPlayer((EntityPlayer)damagesource.getEntity(), LOTRBannerProtection.Permission.FULL), true)) {
            return false;
        }
        return super.attackEntityFrom(damagesource, f);
    }

    public void onBroken(Entity entity) {
        this.worldObj.playSoundAtEntity(this, Blocks.planks.stepSound.getBreakSound(), (Blocks.planks.stepSound.getVolume() + 1.0f) / 2.0f, Blocks.planks.stepSound.getPitch() * 0.8f);
        boolean flag = true;
        if (entity instanceof EntityPlayer && ((EntityPlayer)entity).capabilities.isCreativeMode) {
            flag = false;
        }
        if (flag) {
            this.entityDropItem(this.getBannerItem(), 0.0f);
        }
    }

    public ItemStack getPickedResult(MovingObjectPosition target) {
        return this.getBannerItem();
    }

    private ItemStack getBannerItem() {
        ItemStack item = new ItemStack(LOTRMod.banner, 1, this.getBannerType().bannerID);
        if (this.protectData != null) {
            LOTRItemBanner.setProtectionData(item, this.protectData);
        }
        return item;
    }

    public int getWidthPixels() {
        return 16;
    }

    public int getHeightPixels() {
        return 32;
    }
}

