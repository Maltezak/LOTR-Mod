package lotr.common.entity.item;

import java.util.List;

import cpw.mods.fml.relauncher.*;
import lotr.common.*;
import lotr.common.item.LOTRItemBarrel;
import lotr.common.world.biome.LOTRBiomeGenMirkwood;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class LOTREntityBarrel
extends Entity {
    private boolean isBoatEmpty = true;
    private double speedMultiplier = minSpeedMultiplier;
    private static double minSpeedMultiplier = 0.04;
    private static double maxSpeedMultiplier = 0.25;
    private int boatPosRotationIncrements;
    private double boatX;
    private double boatY;
    private double boatZ;
    private double boatYaw;
    private double boatPitch;
    private double velocityX;
    private double velocityY;
    private double velocityZ;
    public NBTTagCompound barrelItemData;

    public LOTREntityBarrel(World world) {
        super(world);
        this.preventEntitySpawning = true;
        this.setSize(1.0f, 1.0f);
        this.yOffset = 0.0f;
    }

    public LOTREntityBarrel(World world, double d, double d1, double d2) {
        this(world);
        this.setPosition(d, d1 + this.yOffset, d2);
        this.motionX = 0.0;
        this.motionY = 0.0;
        this.motionZ = 0.0;
        this.prevPosX = d;
        this.prevPosY = d1;
        this.prevPosZ = d2;
    }

    protected void entityInit() {
        this.dataWatcher.addObject(17, 0);
        this.dataWatcher.addObject(18, 1);
        this.dataWatcher.addObject(19, Float.valueOf(0.0f));
        this.dataWatcher.addObject(20, new ItemStack(LOTRMod.barrel));
    }

    public void setTimeSinceHit(int i) {
        this.dataWatcher.updateObject(17, i);
    }

    public int getTimeSinceHit() {
        return this.dataWatcher.getWatchableObjectInt(17);
    }

    public void setForwardDirection(int i) {
        this.dataWatcher.updateObject(18, i);
    }

    public int getForwardDirection() {
        return this.dataWatcher.getWatchableObjectInt(18);
    }

    public void setDamageTaken(float f) {
        this.dataWatcher.updateObject(19, Float.valueOf(f));
    }

    public float getDamageTaken() {
        return this.dataWatcher.getWatchableObjectFloat(19);
    }

    private void setBarrelItem(ItemStack itemstack) {
        this.dataWatcher.updateObject(20, itemstack);
    }

    private ItemStack getBarrelItem() {
        return this.dataWatcher.getWatchableObjectItemStack(20);
    }

    protected boolean canTriggerWalking() {
        return false;
    }

    public AxisAlignedBB getCollisionBox(Entity par1Entity) {
        return par1Entity.boundingBox;
    }

    public AxisAlignedBB getBoundingBox() {
        return this.boundingBox;
    }

    public boolean canBePushed() {
        return true;
    }

    public double getMountedYOffset() {
        return 0.5;
    }

    public boolean attackEntityFrom(DamageSource damagesource, float f) {
        if (this.isEntityInvulnerable()) {
            return false;
        }
        if (!this.worldObj.isRemote && !this.isDead) {
            boolean isCreative;
            this.setForwardDirection(-this.getForwardDirection());
            this.setTimeSinceHit(10);
            this.setDamageTaken(this.getDamageTaken() + f * 10.0f);
            Block.SoundType stepSound = LOTRMod.barrel.stepSound;
            this.playSound(stepSound.getBreakSound(), (stepSound.getVolume() + 1.0f) / 2.0f, stepSound.getPitch() * 0.8f);
            this.setBeenAttacked();
            isCreative = damagesource.getEntity() instanceof EntityPlayer && ((EntityPlayer)damagesource.getEntity()).capabilities.isCreativeMode;
            if (isCreative || this.getDamageTaken() > 40.0f) {
                if (this.riddenByEntity != null) {
                    this.riddenByEntity.mountEntity(this);
                }
                if (!isCreative) {
                    this.entityDropItem(this.getBarrelDrop(), 0.0f);
                }
                this.setDead();
            }
            return true;
        }
        return true;
    }

    private ItemStack getBarrelDrop() {
        ItemStack itemstack = new ItemStack(LOTRMod.barrel);
        if (this.barrelItemData != null) {
            LOTRItemBarrel.setBarrelData(itemstack, this.barrelItemData);
        }
        return itemstack;
    }

    @SideOnly(value=Side.CLIENT)
    public void performHurtAnimation() {
        this.setForwardDirection(-this.getForwardDirection());
        this.setTimeSinceHit(10);
        this.setDamageTaken(this.getDamageTaken() * 11.0f);
    }

    public boolean canBeCollidedWith() {
        return !this.isDead;
    }

    @SideOnly(value=Side.CLIENT)
    public void setPositionAndRotation2(double d, double d1, double d2, float f, float f1, int i) {
        if (this.isBoatEmpty) {
            this.boatPosRotationIncrements = i + 5;
        } else {
            double d3 = d - this.posX;
            double d4 = d1 - this.posY;
            double d5 = d2 - this.posZ;
            double d6 = d3 * d3 + d4 * d4 + d5 * d5;
            if (d6 <= 1.0) {
                return;
            }
            this.boatPosRotationIncrements = 3;
        }
        this.boatX = d;
        this.boatY = d1;
        this.boatZ = d2;
        this.boatYaw = f;
        this.boatPitch = f1;
        this.motionX = this.velocityX;
        this.motionY = this.velocityY;
        this.motionZ = this.velocityZ;
    }

    @SideOnly(value=Side.CLIENT)
    public void setVelocity(double par1, double par3, double par5) {
        this.velocityX = this.motionX = par1;
        this.velocityY = this.motionY = par3;
        this.velocityZ = this.motionZ = par5;
    }

    public void onUpdate() {
        double d4;
        double d5;
        super.onUpdate();
        if (!this.worldObj.isRemote) {
            this.setBarrelItem(this.getBarrelDrop());
        }
        if (this.getTimeSinceHit() > 0) {
            this.setTimeSinceHit(this.getTimeSinceHit() - 1);
        }
        if (this.getDamageTaken() > 0.0f) {
            this.setDamageTaken(this.getDamageTaken() - 1.0f);
        }
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        int b0 = 5;
        double d0 = 0.0;
        for (int i2 = 0; i2 < b0; ++i2) {
            double d1 = this.boundingBox.minY + (this.boundingBox.maxY - this.boundingBox.minY) * (i2 + 0) / b0 - 0.125;
            double d2 = this.boundingBox.minY + (this.boundingBox.maxY - this.boundingBox.minY) * (i2 + 1) / b0 - 0.125;
            AxisAlignedBB axisalignedbb = AxisAlignedBB.getBoundingBox(this.boundingBox.minX, d1, this.boundingBox.minZ, this.boundingBox.maxX, d2, this.boundingBox.maxZ);
            if (!this.worldObj.isAABBInMaterial(axisalignedbb, Material.water)) continue;
            d0 += 1.0 / b0;
        }
        double d3 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
        if (d3 > 0.2625) {
            d4 = Math.cos(this.rotationYaw * 3.141592653589793 / 180.0);
            d5 = Math.sin(this.rotationYaw * 3.141592653589793 / 180.0);
            int j = 0;
            while (j < 1.0 + d3 * 60.0) {
                double d8;
                double d9;
                double d6 = this.rand.nextFloat() * 2.0f - 1.0f;
                double d7 = (this.rand.nextInt(2) * 2 - 1) * 0.7;
                if (this.rand.nextBoolean()) {
                    d8 = this.posX - d4 * d6 * 0.8 + d5 * d7;
                    d9 = this.posZ - d5 * d6 * 0.8 - d4 * d7;
                    this.worldObj.spawnParticle("splash", d8, this.posY - 0.125, d9, this.motionX, this.motionY, this.motionZ);
                } else {
                    d8 = this.posX + d4 + d5 * d6 * 0.7;
                    d9 = this.posZ + d5 - d4 * d6 * 0.7;
                    this.worldObj.spawnParticle("splash", d8, this.posY - 0.125, d9, this.motionX, this.motionY, this.motionZ);
                }
                ++j;
            }
        }
        if (this.worldObj.isRemote && this.isBoatEmpty) {
            if (this.boatPosRotationIncrements > 0) {
                d4 = this.posX + (this.boatX - this.posX) / this.boatPosRotationIncrements;
                d5 = this.posY + (this.boatY - this.posY) / this.boatPosRotationIncrements;
                double d11 = this.posZ + (this.boatZ - this.posZ) / this.boatPosRotationIncrements;
                double d10 = MathHelper.wrapAngleTo180_double(this.boatYaw - this.rotationYaw);
                this.rotationYaw = (float)(this.rotationYaw + d10 / this.boatPosRotationIncrements);
                this.rotationPitch = (float)(this.rotationPitch + (this.boatPitch - this.rotationPitch) / this.boatPosRotationIncrements);
                --this.boatPosRotationIncrements;
                this.setPosition(d4, d5, d11);
                this.setRotation(this.rotationYaw, this.rotationPitch);
            } else {
                d4 = this.posX + this.motionX;
                d5 = this.posY + this.motionY;
                double d11 = this.posZ + this.motionZ;
                this.setPosition(d4, d5, d11);
                if (this.onGround) {
                    this.motionX *= 0.5;
                    this.motionY *= 0.5;
                    this.motionZ *= 0.5;
                }
                this.motionX *= 0.99;
                this.motionY *= 0.95;
                this.motionZ *= 0.99;
            }
        } else {
            double d12;
            double d11;
            if (d0 < 1.0) {
                d4 = d0 * 2.0 - 1.0;
                this.motionY += 0.04 * d4;
            } else {
                if (this.motionY < 0.0) {
                    this.motionY /= 2.0;
                }
                this.motionY += 0.007;
            }
            if (this.riddenByEntity instanceof EntityLivingBase && (d4 = ((EntityLivingBase)this.riddenByEntity).moveForward) > 0.0) {
                d5 = -Math.sin(this.riddenByEntity.rotationYaw * 3.1415927f / 180.0f);
                d11 = Math.cos(this.riddenByEntity.rotationYaw * 3.1415927f / 180.0f);
                this.motionX += d5 * this.speedMultiplier * 0.05;
                this.motionZ += d11 * this.speedMultiplier * 0.05;
            }
            if ((d4 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ)) > maxSpeedMultiplier) {
                d5 = maxSpeedMultiplier / d4;
                this.motionX *= d5;
                this.motionZ *= d5;
                d4 = maxSpeedMultiplier;
            }
            if (d4 > d3 && this.speedMultiplier < maxSpeedMultiplier) {
                this.speedMultiplier += (maxSpeedMultiplier - this.speedMultiplier) / (maxSpeedMultiplier / 150.0);
                if (this.speedMultiplier > maxSpeedMultiplier) {
                    this.speedMultiplier = maxSpeedMultiplier;
                }
            } else {
                this.speedMultiplier -= (this.speedMultiplier - minSpeedMultiplier) / (maxSpeedMultiplier / 150.0);
                if (this.speedMultiplier < minSpeedMultiplier) {
                    this.speedMultiplier = minSpeedMultiplier;
                }
            }
            if (this.onGround) {
                this.motionX *= 0.5;
                this.motionY *= 0.5;
                this.motionZ *= 0.5;
            }
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            this.motionX *= 0.99;
            this.motionY *= 0.95;
            this.motionZ *= 0.99;
            this.rotationPitch = 0.0f;
            d5 = this.rotationYaw;
            d11 = this.prevPosX - this.posX;
            double d10 = this.prevPosZ - this.posZ;
            if (d11 * d11 + d10 * d10 > 0.001) {
                d5 = (float)(Math.atan2(d10, d11) * 180.0 / 3.141592653589793);
            }
            if ((d12 = MathHelper.wrapAngleTo180_double(d5 - this.rotationYaw)) > 20.0) {
                d12 = 20.0;
            }
            if (d12 < -20.0) {
                d12 = -20.0;
            }
            this.rotationYaw = (float)(this.rotationYaw + d12);
            this.setRotation(this.rotationYaw, this.rotationPitch);
            if (!this.worldObj.isRemote) {
                List nearbyEntities = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(0.2, 0.0, 0.2));
                if (nearbyEntities != null && !nearbyEntities.isEmpty()) {
                    for (int l = 0; l < nearbyEntities.size(); ++l) {
                        Entity entity = (Entity)nearbyEntities.get(l);
                        if (entity == this.riddenByEntity || !entity.canBePushed() || !(entity instanceof LOTREntityBarrel)) continue;
                        entity.applyEntityCollision(this);
                    }
                }
                if (this.riddenByEntity != null && this.riddenByEntity.isDead) {
                    this.riddenByEntity = null;
                }
            }
        }
        if (!this.worldObj.isRemote && this.riddenByEntity instanceof EntityPlayer && this.worldObj.isAABBInMaterial(this.boundingBox, Material.water) && this.worldObj.getBiomeGenForCoords(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posZ)) instanceof LOTRBiomeGenMirkwood) {
            LOTRLevelData.getData((EntityPlayer)this.riddenByEntity).addAchievement(LOTRAchievement.rideBarrelMirkwood);
        }
    }

    public void writeEntityToNBT(NBTTagCompound nbt) {
        if (this.barrelItemData != null) {
            nbt.setTag("BarrelItemData", this.barrelItemData);
        }
    }

    public void readEntityFromNBT(NBTTagCompound nbt) {
        if (nbt.hasKey("BarrelItemData")) {
            this.barrelItemData = nbt.getCompoundTag("BarrelItemData");
        }
    }

    @SideOnly(value=Side.CLIENT)
    public float getShadowSize() {
        return 0.0f;
    }

    public boolean interactFirst(EntityPlayer entityplayer) {
        if (this.riddenByEntity instanceof EntityPlayer && this.riddenByEntity != entityplayer) {
            return true;
        }
        if (!this.worldObj.isRemote) {
            entityplayer.mountEntity(this);
        }
        return true;
    }

    public ItemStack getPickedResult(MovingObjectPosition target) {
        return this.getBarrelItem();
    }
}

