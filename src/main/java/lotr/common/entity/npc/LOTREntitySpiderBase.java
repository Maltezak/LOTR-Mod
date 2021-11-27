package lotr.common.entity.npc;

import lotr.common.*;
import lotr.common.entity.LOTRMountFunctions;
import lotr.common.entity.ai.*;
import lotr.common.fac.LOTRAlignmentValues;
import net.minecraft.block.Block;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.*;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public abstract class LOTREntitySpiderBase
extends LOTREntityNPCRideable {
    public static int VENOM_NONE = 0;
    public static int VENOM_SLOWNESS = 1;
    public static int VENOM_POISON = 2;
    public LOTREntitySpiderBase(World world) {
        super(world);
        this.setSize(1.4f, 0.8f);
        this.getNavigator().setAvoidsWater(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new LOTREntityAIHiredRemainStill(this));
        this.tasks.addTask(2, new EntityAILeapAtTarget(this, 0.4f));
        this.tasks.addTask(3, new LOTREntityAIAttackOnCollide(this, 1.2, false));
        this.tasks.addTask(4, new LOTREntityAIUntamedPanic(this, 1.2));
        this.tasks.addTask(5, new LOTREntityAIFollowHiringPlayer(this));
        this.tasks.addTask(6, new EntityAIWander(this, 1.0));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0f, 0.02f));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.addTargetTasks(true);
        this.spawnsInDarkness = true;
    }

    protected abstract int getRandomSpiderScale();

    protected abstract int getRandomSpiderType();

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(20, (byte) 0);
        this.dataWatcher.addObject(21, (byte) 0);
        this.dataWatcher.addObject(22, (byte) this.getRandomSpiderScale());
        this.setSpiderType(this.getRandomSpiderType());
        this.dataWatcher.addObject(23, (short) 0);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(12.0 + this.getSpiderScale() * 6.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.35 - this.getSpiderScale() * 0.03);
        this.getEntityAttribute(npcAttackDamage).setBaseValue(2.0 + this.getSpiderScale());
    }

    public boolean isSpiderClimbing() {
        return (this.dataWatcher.getWatchableObjectByte(20) & 1) != 0;
    }

    public void setSpiderClimbing(boolean flag) {
        byte b = this.dataWatcher.getWatchableObjectByte(20);
        b = flag ? (byte)(b | 1) : (byte)(b & 0xFFFFFFFE);
        this.dataWatcher.updateObject(20, b);
    }

    public int getSpiderType() {
        return this.dataWatcher.getWatchableObjectByte(21);
    }

    public void setSpiderType(int i) {
        this.dataWatcher.updateObject(21, ((byte)i));
    }

    public int getSpiderScale() {
        return this.dataWatcher.getWatchableObjectByte(22);
    }

    public void setSpiderScale(int i) {
        this.dataWatcher.updateObject(22, ((byte)i));
    }

    public float getSpiderScaleAmount() {
        return 0.5f + this.getSpiderScale() / 2.0f;
    }

    public int getSpiderClimbTime() {
        return this.dataWatcher.getWatchableObjectShort(23);
    }

    public void setSpiderClimbTime(int i) {
        this.dataWatcher.updateObject(23, ((short)i));
    }

    public boolean shouldRenderClimbingMeter() {
        return !this.onGround && this.getSpiderClimbTime() > 0;
    }

    public float getClimbFractionRemaining() {
        float f = this.getSpiderClimbTime() / 100.0f;
        f = Math.min(f, 1.0f);
        f = 1.0f - f;
        return f;
    }

    @Override
    public boolean isMountSaddled() {
        return this.isNPCTamed() && this.riddenByEntity instanceof EntityPlayer;
    }

    @Override
    public boolean getBelongsToNPC() {
        return false;
    }

    @Override
    public void setBelongsToNPC(boolean flag) {
    }

    @Override
    public String getMountArmorTexture() {
        return null;
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        nbt.setByte("SpiderType", (byte)this.getSpiderType());
        nbt.setByte("SpiderScale", (byte)this.getSpiderScale());
        nbt.setShort("SpiderRideTime", (short)this.getSpiderClimbTime());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        this.setSpiderType(nbt.getByte("SpiderType"));
        this.setSpiderScale(nbt.getByte("SpiderScale"));
        this.getEntityAttribute(npcAttackDamage).setBaseValue(2.0 + this.getSpiderScale());
        this.setSpiderClimbTime(nbt.getShort("SpiderRideTime"));
    }

    @Override
    protected float getNPCScale() {
        return this.getSpiderScaleAmount();
    }

    public float getRenderSizeModifier() {
        return this.getSpiderScaleAmount();
    }

    protected boolean canRideSpider() {
        return this.getSpiderScale() > 0;
    }

    @Override
    protected double getBaseMountedYOffset() {
        return this.height - 0.7;
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (!this.worldObj.isRemote) {
            Entity rider = this.riddenByEntity;
            if (rider instanceof EntityPlayer && !this.onGround) {
                if (this.isCollidedHorizontally) {
                    this.setSpiderClimbTime(this.getSpiderClimbTime() + 1);
                }
            } else {
                this.setSpiderClimbTime(0);
            }
            if (this.getSpiderClimbTime() >= 100) {
                this.setSpiderClimbing(false);
                if (this.onGround) {
                    this.setSpiderClimbTime(0);
                }
            } else {
                this.setSpiderClimbing(this.isCollidedHorizontally);
            }
        }
        if (!this.worldObj.isRemote && this.riddenByEntity instanceof EntityPlayer && LOTRLevelData.getData((EntityPlayer)this.riddenByEntity).getAlignment(this.getFaction()) < 50.0f) {
            this.riddenByEntity.mountEntity(null);
        }
    }

    @Override
    public boolean interact(EntityPlayer entityplayer) {
        ItemStack itemstack = entityplayer.inventory.getCurrentItem();
        if (this.getSpiderType() == VENOM_POISON && itemstack != null && itemstack.getItem() == Items.glass_bottle) {
            --itemstack.stackSize;
            if (itemstack.stackSize <= 0) {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, new ItemStack(LOTRMod.bottlePoison));
            } else if (!entityplayer.inventory.addItemStackToInventory(new ItemStack(LOTRMod.bottlePoison)) && !entityplayer.capabilities.isCreativeMode) {
                entityplayer.dropPlayerItemWithRandomChoice(new ItemStack(LOTRMod.bottlePoison), false);
            }
            return true;
        }
        if (this.worldObj.isRemote || this.hiredNPCInfo.isActive) {
            return false;
        }
        if (LOTRMountFunctions.interact(this, entityplayer)) {
            return true;
        }
        if (this.canRideSpider() && this.getAttackTarget() != entityplayer) {
            boolean hasRequiredAlignment = LOTRLevelData.getData(entityplayer).getAlignment(this.getFaction()) >= 50.0f;
            boolean notifyNotEnoughAlignment = false;
            if (!notifyNotEnoughAlignment && itemstack != null && LOTRMod.isOreNameEqual(itemstack, "bone") && this.isNPCTamed() && this.getHealth() < this.getMaxHealth()) {
                if (hasRequiredAlignment) {
                    if (!entityplayer.capabilities.isCreativeMode) {
                        --itemstack.stackSize;
                        if (itemstack.stackSize == 0) {
                            entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
                        }
                    }
                    this.heal(4.0f);
                    this.playSound(this.getLivingSound(), this.getSoundVolume(), this.getSoundPitch() * 1.5f);
                    return true;
                }
                notifyNotEnoughAlignment = true;
            }
            if (!notifyNotEnoughAlignment && this.riddenByEntity == null) {
                if (itemstack != null && itemstack.interactWithEntity(entityplayer, this)) {
                    return true;
                }
                if (hasRequiredAlignment) {
                    entityplayer.mountEntity(this);
                    this.setAttackTarget(null);
                    this.getNavigator().clearPathEntity();
                    return true;
                }
                notifyNotEnoughAlignment = true;
            }
            if (notifyNotEnoughAlignment) {
                LOTRAlignmentValues.notifyAlignmentNotHighEnough(entityplayer, 50.0f, this.getFaction());
                return true;
            }
        }
        return super.interact(entityplayer);
    }

    @Override
    public boolean attackEntityAsMob(Entity entity) {
        if (super.attackEntityAsMob(entity)) {
            int difficulty;
            int duration;
            if (entity instanceof EntityLivingBase && (duration = (difficulty = this.worldObj.difficultySetting.getDifficultyId()) * (difficulty + 5) / 2) > 0) {
                if (this.getSpiderType() == VENOM_SLOWNESS) {
                    ((EntityLivingBase)entity).addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, duration * 20, 0));
                } else if (this.getSpiderType() == VENOM_POISON) {
                    ((EntityLivingBase)entity).addPotionEffect(new PotionEffect(Potion.poison.id, duration * 20, 0));
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float f) {
        if (damagesource == DamageSource.fall) {
            return false;
        }
        return super.attackEntityFrom(damagesource, f);
    }

    protected String getLivingSound() {
        return "mob.spider.say";
    }

    protected String getHurtSound() {
        return "mob.spider.say";
    }

    protected String getDeathSound() {
        return "mob.spider.death";
    }

    protected void func_145780_a(int i, int j, int k, Block block) {
        this.playSound("mob.spider.step", 0.15f, 1.0f);
    }

    @Override
    protected void dropFewItems(boolean flag, int i) {
        super.dropFewItems(flag, i);
        int string = this.rand.nextInt(3) + this.rand.nextInt(i + 1);
        for (int j = 0; j < string; ++j) {
            this.dropItem(Items.string, 1);
        }
        if (flag && (this.rand.nextInt(3) == 0 || this.rand.nextInt(1 + i) > 0)) {
            this.dropItem(Items.spider_eye, 1);
        }
    }

    @Override
    public boolean canDropRares() {
        return false;
    }

    @Override
    protected int getExperiencePoints(EntityPlayer entityplayer) {
        int i = this.getSpiderScale();
        return 2 + i + this.rand.nextInt(i + 2);
    }

    public boolean isOnLadder() {
        return this.isSpiderClimbing();
    }

    public void setInWeb() {
    }

    public void setInQuag() {
        super.setInWeb();
    }

    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.ARTHROPOD;
    }

    public boolean isPotionApplicable(PotionEffect effect) {
        if (this.getSpiderType() == VENOM_SLOWNESS && effect.getPotionID() == Potion.moveSlowdown.id) {
            return false;
        }
        if (this.getSpiderType() == VENOM_POISON && effect.getPotionID() == Potion.poison.id) {
            return false;
        }
        return super.isPotionApplicable(effect);
    }

    @Override
    public boolean allowLeashing() {
        return this.isNPCTamed();
    }

    @Override
    public boolean canReEquipHired(int slot, ItemStack itemstack) {
        return false;
    }
}

