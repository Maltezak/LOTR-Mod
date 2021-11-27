package lotr.common.entity.npc;

import java.util.*;

import cpw.mods.fml.relauncher.*;
import lotr.common.*;
import lotr.common.entity.ai.*;
import lotr.common.inventory.LOTRInventoryNPC;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class LOTREntityGollum extends LOTREntityNPC implements LOTRCharacter {
    public static int INV_ROWS = 3;
    private int eatingTick;
    public int prevFishTime = 400;
    public boolean isFishing;
    public LOTRInventoryNPC inventory = new LOTRInventoryNPC("gollum", this, INV_ROWS * 9);
    public int prevFishRequired;
    public int fishRequired = this.prevFishRequired = 20;

    public LOTREntityGollum(World world) {
        super(world);
        this.setSize(0.6f, 1.2f);
        this.getNavigator().setAvoidsWater(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new LOTREntityAIGollumRemainStill(this));
        this.tasks.addTask(2, new LOTREntityAIGollumPanic(this, 1.4));
        this.tasks.addTask(3, new LOTREntityAIGollumAvoidEntity(this, LOTREntityOrc.class, 8.0f, 1.2, 1.4));
        this.tasks.addTask(3, new LOTREntityAIGollumAvoidEntity(this, LOTREntityElf.class, 8.0f, 1.2, 1.4));
        this.tasks.addTask(4, new LOTREntityAIGollumFishing(this, 1.5));
        this.tasks.addTask(5, new LOTREntityAIGollumFollowOwner(this, 1.2, 6.0f, 4.0f));
        this.tasks.addTask(6, new EntityAIWander(this, 1.0));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0f, 0.1f));
        this.tasks.addTask(8, new EntityAILookIdle(this));
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(17, "");
        this.dataWatcher.addObject(18, (byte) 0);
        this.dataWatcher.addObject(19, (byte) 0);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25);
    }

    public String getGollumOwnerUUID() {
        return this.dataWatcher.getWatchableObjectString(17);
    }

    public void setGollumOwnerUUID(String s) {
        this.dataWatcher.updateObject(17, s);
    }

    public EntityPlayer getGollumOwner() {
        try {
            UUID uuid = UUID.fromString(this.getGollumOwnerUUID());
            return uuid == null ? null : this.worldObj.func_152378_a(uuid);
        }
        catch(IllegalArgumentException e) {
            return null;
        }
    }

    public boolean isGollumFleeing() {
        return this.dataWatcher.getWatchableObjectByte(18) == 1;
    }

    public void setGollumFleeing(boolean flag) {
        this.dataWatcher.updateObject(18, flag ? (byte) 1 : 0);
    }

    public boolean isGollumSitting() {
        return this.dataWatcher.getWatchableObjectByte(19) == 1;
    }

    public void setGollumSitting(boolean flag) {
        this.dataWatcher.updateObject(19, flag ? (byte) 1 : 0);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        this.inventory.writeToNBT(nbt);
        nbt.setString("GollumOwnerUUID", this.getGollumOwnerUUID());
        nbt.setBoolean("GollumSitting", this.isGollumSitting());
        nbt.setInteger("GollumFishTime", this.prevFishTime);
        nbt.setInteger("FishReq", this.fishRequired);
        nbt.setInteger("FishReqPrev", this.prevFishRequired);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        this.inventory.readFromNBT(nbt);
        if(nbt.hasKey("GollumOwnerUUID")) {
            this.setGollumOwnerUUID(nbt.getString("GollumOwnerUUID"));
        }
        this.setGollumSitting(nbt.getBoolean("GollumSitting"));
        this.prevFishTime = nbt.getInteger("GollumFishTime");
        if(nbt.hasKey("FishReq")) {
            this.fishRequired = nbt.getInteger("FishReq");
            this.prevFishRequired = nbt.getInteger("FishReqPrev");
        }
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if(!this.worldObj.isRemote && this.rand.nextInt(500) == 0) {
            this.heal(1.0f);
        }
        if(this.eatingTick > 0) {
            if(this.eatingTick % 4 == 0) {
                this.worldObj.playSoundAtEntity(this, "random.eat", 0.5f + 0.5f * this.rand.nextInt(2), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f);
            }
            --this.eatingTick;
        }
        if(this.prevFishTime > 0) {
            --this.prevFishTime;
        }
        if(this.isGollumSitting() && !this.worldObj.isRemote && this.onGround) {
            this.getJumpHelper().setJumping();
        }
        if(!this.worldObj.isRemote && this.getEquipmentInSlot(0) != null && this.getGollumOwner() != null && (this.getDistanceSqToEntity(this.getGollumOwner())) < 4.0) {
            this.getLookHelper().setLookPositionWithEntity(this.getGollumOwner(), 100.0f, 100.0f);
            this.getLookHelper().onUpdateLook();
            EntityItem entityitem = new EntityItem(this.worldObj, this.posX, this.posY + this.getEyeHeight(), this.posZ, this.getEquipmentInSlot(0));
            entityitem.delayBeforeCanPickup = 40;
            float f = 0.3f;
            entityitem.motionX = -MathHelper.sin(this.rotationYawHead / 180.0f * 3.1415927f) * MathHelper.cos(this.rotationPitch / 180.0f * 3.1415927f) * f;
            entityitem.motionZ = MathHelper.cos(this.rotationYawHead / 180.0f * 3.1415927f) * MathHelper.cos(this.rotationPitch / 180.0f * 3.1415927f) * f;
            entityitem.motionY = -MathHelper.sin(this.rotationPitch / 180.0f * 3.1415927f) * f + 0.1f;
            f = 0.02f;
            float f1 = this.rand.nextFloat() * 3.1415927f * 2.0f;
            entityitem.motionX += Math.cos(f1) * (f *= this.rand.nextFloat());
            entityitem.motionY += (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1f;
            entityitem.motionZ += Math.sin(f1) * f;
            this.worldObj.spawnEntityInWorld(entityitem);
            this.setCurrentItemOrArmor(0, null);
        }
        if(!this.worldObj.isRemote && StringUtils.isNullOrEmpty(this.getGollumOwnerUUID()) && this.rand.nextInt(40) == 0) {
            List<EntityPlayer> nearbyPlayers = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, this.boundingBox.expand(80.0, 80.0, 80.0));
            for(EntityPlayer entityplayer : nearbyPlayers) {
                double d2 = this.getDistanceToEntity(entityplayer);
                int chance = (int) (d2 / 8.0);
                if(this.rand.nextInt(chance = Math.max(2, chance)) != 0) continue;
                this.worldObj.playSoundAtEntity(entityplayer, this.getLivingSound(), this.getSoundVolume(), this.getSoundPitch());
            }
        }
    }

    @Override
    public boolean interact(EntityPlayer entityplayer) {
        if(!this.worldObj.isRemote) {
            if(this.getGollumOwner() != null && entityplayer == this.getGollumOwner()) {
                ItemStack itemstack = entityplayer.inventory.getCurrentItem();
                if(itemstack != null && itemstack.getItem() instanceof ItemFood && this.canGollumEat(itemstack) && this.getHealth() < this.getMaxHealth()) {
                    if(!entityplayer.capabilities.isCreativeMode) {
                        --itemstack.stackSize;
                        if(itemstack.stackSize <= 0) {
                            entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
                        }
                    }
                    this.heal(((ItemFood) itemstack.getItem()).func_150905_g(itemstack));
                    this.eatingTick = 20;
                    return true;
                }
                if(entityplayer.isSneaking()) {
                    entityplayer.openGui(LOTRMod.instance, 10, this.worldObj, this.getEntityId(), 0, 0);
                    return true;
                }
                this.setGollumSitting(!this.isGollumSitting());
                if(this.isGollumSitting()) {
                    LOTRSpeech.sendSpeech(this.getGollumOwner(), this, LOTRSpeech.getRandomSpeechForPlayer(this, "char/gollum/stay", this.getGollumOwner()));
                }
                else {
                    LOTRSpeech.sendSpeech(this.getGollumOwner(), this, LOTRSpeech.getRandomSpeechForPlayer(this, "char/gollum/follow", this.getGollumOwner()));
                }
                return true;
            }
            ItemStack itemstack = entityplayer.inventory.getCurrentItem();
            if(itemstack != null && itemstack.getItem() == Items.fish) {
                if(itemstack.stackSize >= this.fishRequired) {
                    if(!entityplayer.capabilities.isCreativeMode) {
                        itemstack.stackSize -= this.fishRequired;
                        if(itemstack.stackSize <= 0) {
                            entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
                        }
                    }
                    this.fishRequired = 0;
                }
                else {
                    this.fishRequired -= itemstack.stackSize;
                    if(!entityplayer.capabilities.isCreativeMode) {
                        entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
                    }
                }
                this.eatingTick = 20;
                if(this.fishRequired <= 0) {
                    this.setGollumOwnerUUID(entityplayer.getUniqueID().toString());
                    LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.tameGollum);
                    LOTRSpeech.sendSpeech(entityplayer, this, LOTRSpeech.getRandomSpeechForPlayer(this, "char/gollum/tame", entityplayer));
                    LOTRSpeech.messageAllPlayers(new ChatComponentTranslation("chat.lotr.tameGollum", entityplayer.getCommandSenderName(), this.getCommandSenderName()));
                    this.spawnHearts();
                    this.prevFishRequired = this.fishRequired = Math.round(this.prevFishRequired * (1.5f + this.rand.nextFloat() * 0.25f));
                }
                else {
                    LOTRSpeech.sendSpeech(entityplayer, this, LOTRSpeech.getRandomSpeechForPlayer(this, "char/gollum/tameProgress", entityplayer));
                }
                return true;
            }
        }
        return super.interact(entityplayer);
    }

    @Override
    public String getSpeechBank(EntityPlayer entityplayer) {
        if(!this.isGollumFleeing()) {
            return "char/gollum/say";
        }
        return super.getSpeechBank(entityplayer);
    }

    private boolean canGollumEat(ItemStack itemstack) {
        if(itemstack.getItem() == Items.fish || itemstack.getItem() == Items.cooked_fished) {
            return true;
        }
        ItemFood food = (ItemFood) itemstack.getItem();
        return food.isWolfsFavoriteMeat();
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float f) {
        EntityPlayer owner = this.getGollumOwner();
        if(owner != null && damagesource.getEntity() == owner) {
            f = 0.0f;
            if(!this.worldObj.isRemote) {
                LOTRSpeech.sendSpeech(owner, this, LOTRSpeech.getRandomSpeechForPlayer(this, "char/gollum/hurt", owner));
            }
        }
        if(super.attackEntityFrom(damagesource, f)) {
            this.setGollumSitting(false);
            return true;
        }
        return false;
    }

    @Override
    public void onDeath(DamageSource damagesource) {
        if(!this.worldObj.isRemote && !StringUtils.isNullOrEmpty(this.getGollumOwnerUUID())) {
            LOTRSpeech.messageAllPlayers(this.func_110142_aN().func_151521_b());
        }
        super.onDeath(damagesource);
        if(!this.worldObj.isRemote) {
            this.inventory.dropAllItems();
            LOTRLevelData.setGollumSpawned(false);
        }
    }

    @Override
    public boolean canDropRares() {
        return false;
    }

    @Override
    public String getLivingSound() {
        return "lotr:gollum.say";
    }

    @Override
    public String getHurtSound() {
        return "lotr:gollum.hurt";
    }

    @Override
    public String getDeathSound() {
        return "lotr:gollum.death";
    }

    @Override
    public String getSplashSound() {
        return super.getSplashSound();
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void handleHealthUpdate(byte b) {
        if(b == 15) {
            for(int i = 0; i < 4; ++i) {
                double d = this.rand.nextGaussian() * 0.02;
                double d1 = this.rand.nextGaussian() * 0.02;
                double d2 = this.rand.nextGaussian() * 0.02;
                this.worldObj.spawnParticle(this.rand.nextBoolean() ? "bubble" : "splash", this.posX + this.rand.nextFloat() * this.width * 2.0f - this.width, this.posY + 0.5 + this.rand.nextFloat() * this.height, this.posZ + this.rand.nextFloat() * this.width * 2.0f - this.width, d, d1, d2);
            }
        }
        else {
            super.handleHealthUpdate(b);
        }
    }

    @Override
    public boolean canReEquipHired(int slot, ItemStack itemstack) {
        return false;
    }
}
