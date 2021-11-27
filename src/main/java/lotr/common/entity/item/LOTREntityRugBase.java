package lotr.common.entity.item;

import lotr.common.entity.LOTRBannerProtectable;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.world.World;

public abstract class LOTREntityRugBase extends Entity implements LOTRBannerProtectable {
    private int timeSinceLastGrowl = this.getTimeUntilGrowl();

    public LOTREntityRugBase(World world) {
        super(world);
    }

    @Override
    protected void entityInit() {
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
        if(!this.worldObj.isRemote) {
            if(this.timeSinceLastGrowl > 0) {
                --this.timeSinceLastGrowl;
            }
            else if(this.rand.nextInt(5000) == 0) {
                this.worldObj.playSoundAtEntity(this, this.getRugNoise(), 1.0f, (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2f + 1.0f);
                this.timeSinceLastGrowl = this.getTimeUntilGrowl();
            }
        }
    }

    private int getTimeUntilGrowl() {
        return (60 + this.rand.nextInt(150)) * 20;
    }

    protected abstract String getRugNoise();

    @Override
    protected void readEntityFromNBT(NBTTagCompound nbt) {
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound nbt) {
    }

    protected abstract ItemStack getRugItem();

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float f) {
        if(!this.worldObj.isRemote && !this.isDead) {
            boolean creative;
            Block.SoundType blockSound = Blocks.wool.stepSound;
            this.worldObj.playSoundAtEntity(this, blockSound.getBreakSound(), (blockSound.getVolume() + 1.0f) / 2.0f, blockSound.getPitch() * 0.8f);
            Entity attacker = damagesource.getEntity();
            creative = attacker instanceof EntityPlayer && ((EntityPlayer) attacker).capabilities.isCreativeMode;
            if(!creative) {
                this.entityDropItem(this.getRugItem(), 0.0f);
            }
            this.setDead();
        }
        return true;
    }

    @Override
    public ItemStack getPickedResult(MovingObjectPosition target) {
        return this.getRugItem();
    }
}
