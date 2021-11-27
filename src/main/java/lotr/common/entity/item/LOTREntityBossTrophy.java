package lotr.common.entity.item;

import lotr.common.LOTRMod;
import lotr.common.entity.LOTRBannerProtectable;
import lotr.common.item.LOTRItemBossTrophy;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTREntityBossTrophy extends Entity implements LOTRBannerProtectable {
    public LOTREntityBossTrophy(World world) {
        super(world);
        this.setSize(1.0f, 1.0f);
    }

    @Override
    protected void entityInit() {
        this.dataWatcher.addObject(18, (byte) 0);
        this.dataWatcher.addObject(19, (byte) 0);
        this.dataWatcher.addObject(20, (byte) 0);
    }

    private int getTrophyTypeID() {
        return this.dataWatcher.getWatchableObjectByte(18);
    }

    private void setTrophyTypeID(int i) {
        this.dataWatcher.updateObject(18, (byte) i);
    }

    public void setTrophyType(LOTRItemBossTrophy.TrophyType type) {
        this.setTrophyTypeID(type.trophyID);
    }

    public LOTRItemBossTrophy.TrophyType getTrophyType() {
        return LOTRItemBossTrophy.TrophyType.forID(this.getTrophyTypeID());
    }

    public boolean isTrophyHanging() {
        return this.dataWatcher.getWatchableObjectByte(19) == 1;
    }

    public void setTrophyHanging(boolean flag) {
        this.dataWatcher.updateObject(19, flag ? (byte) 1 : 0);
    }

    public int getTrophyFacing() {
        byte i = this.dataWatcher.getWatchableObjectByte(20);
        if(i < 0 || i >= Direction.directions.length) {
            i = 0;
        }
        return i;
    }

    public void setTrophyFacing(int i) {
        this.dataWatcher.updateObject(20, (byte) i);
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    @Override
    public AxisAlignedBB getBoundingBox() {
        return this.boundingBox;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if(this.isTrophyHanging()) {
            if((!this.hangingOnValidSurface() && !this.worldObj.isRemote && !this.isDead)) {
                this.dropAsItem(true);
            }
        }
        else {
            this.motionY -= 0.04;
            this.func_145771_j(this.posX, (this.boundingBox.minY + this.boundingBox.maxY) / 2.0, this.posZ);
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            float f = 0.98f;
            if(this.onGround) {
                f = 0.588f;
                Block i = this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.boundingBox.minY) - 1, MathHelper.floor_double(this.posZ));
                if(i.getMaterial() != Material.air) {
                    f = i.slipperiness * 0.98f;
                }
            }
            this.motionX *= f;
            this.motionY *= 0.98;
            this.motionZ *= f;
            if(this.onGround) {
                this.motionY *= -0.5;
            }
        }
    }

    public boolean hangingOnValidSurface() {
        if(this.isTrophyHanging()) {
            int direction = this.getTrophyFacing();
            int opposite = Direction.rotateOpposite[direction];
            int dx = Direction.offsetX[opposite];
            int dz = Direction.offsetZ[opposite];
            int blockX = MathHelper.floor_double(this.posX);
            int blockY = MathHelper.floor_double(this.boundingBox.minY);
            int blockZ = MathHelper.floor_double(this.posZ);
            Block block = this.worldObj.getBlock(blockX += dx, blockY, blockZ += dz);
            int blockSide = Direction.directionToFacing[direction];
            return block.isSideSolid(this.worldObj, blockX, blockY, blockZ, ForgeDirection.getOrientation(blockSide));
        }
        return false;
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbt) {
        nbt.setByte("TrophyType", (byte) this.getTrophyTypeID());
        nbt.setBoolean("TrophyHanging", this.isTrophyHanging());
        nbt.setByte("TrophyFacing", (byte) this.getTrophyFacing());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt) {
        this.setTrophyTypeID(nbt.getByte("TrophyType"));
        this.setTrophyHanging(nbt.getBoolean("TrophyHanging"));
        this.setTrophyFacing(nbt.getByte("TrophyFacing"));
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float f) {
        if(!this.worldObj.isRemote && !this.isDead && damagesource.getSourceOfDamage() instanceof EntityPlayer) {
            EntityPlayer entityplayer = (EntityPlayer) damagesource.getSourceOfDamage();
            this.dropAsItem(!entityplayer.capabilities.isCreativeMode);
            return true;
        }
        return false;
    }

    private void dropAsItem(boolean dropItem) {
        this.worldObj.playSoundAtEntity(this, Blocks.stone.stepSound.getBreakSound(), (Blocks.stone.stepSound.getVolume() + 1.0f) / 2.0f, Blocks.stone.stepSound.getPitch() * 0.8f);
        if(dropItem) {
            this.entityDropItem(new ItemStack(LOTRMod.bossTrophy, 1, this.getTrophyType().trophyID), 0.0f);
        }
        this.setDead();
    }

    @Override
    public ItemStack getPickedResult(MovingObjectPosition target) {
        return new ItemStack(LOTRMod.bossTrophy, 1, this.getTrophyType().trophyID);
    }
}
