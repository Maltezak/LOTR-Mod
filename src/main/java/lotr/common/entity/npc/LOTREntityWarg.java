package lotr.common.entity.npc;

import lotr.common.*;
import lotr.common.entity.LOTRMountFunctions;
import lotr.common.entity.ai.*;
import lotr.common.entity.animal.*;
import lotr.common.fac.LOTRAlignmentValues;
import lotr.common.item.LOTRItemMountArmor;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.world.World;

public abstract class LOTREntityWarg extends LOTREntityNPCRideable implements IInvBasic {
    private int eatingTick;
    private AnimalChest wargInventory;

    public LOTREntityWarg(World world) {
        super(world);
        this.setSize(1.5f, 1.7f);
        this.getNavigator().setAvoidsWater(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new LOTREntityAIHiredRemainStill(this));
        this.tasks.addTask(2, this.getWargAttackAI());
        this.tasks.addTask(3, new LOTREntityAIUntamedPanic(this, 1.2));
        this.tasks.addTask(4, new LOTREntityAIFollowHiringPlayer(this));
        this.tasks.addTask(5, new EntityAIWander(this, 1.0));
        this.tasks.addTask(6, new EntityAIWatchClosest2(this, EntityPlayer.class, 12.0f, 0.02f));
        this.tasks.addTask(6, new EntityAIWatchClosest2(this, LOTREntityNPC.class, 8.0f, 0.02f));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityLiving.class, 12.0f, 0.02f));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        int target = this.addTargetTasks(true);
        if(!(this instanceof LOTREntityWargBombardier)) {
            this.targetTasks.addTask(target + 1, new LOTREntityAINearestAttackableTargetBasic(this, LOTREntityRabbit.class, 500, false));
            this.targetTasks.addTask(target + 1, new LOTREntityAINearestAttackableTargetBasic(this, LOTREntityDeer.class, 1000, false));
        }
        this.isImmuneToFrost = true;
        this.spawnsInDarkness = true;
        this.setupWargInventory();
    }

    public EntityAIBase getWargAttackAI() {
        return new LOTREntityAIAttackOnCollide(this, 1.6, false);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(18, (byte) 0);
        this.dataWatcher.addObject(19, (byte) 0);
        this.dataWatcher.addObject(20, 0);
        if(this.rand.nextInt(500) == 0) {
            this.setWargType(WargType.WHITE);
        }
        else if(this.rand.nextInt(20) == 0) {
            this.setWargType(WargType.BLACK);
        }
        else if(this.rand.nextInt(3) == 0) {
            this.setWargType(WargType.GREY);
        }
        else {
            this.setWargType(WargType.BROWN);
        }
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(MathHelper.getRandomIntegerInRange(this.rand, 20, 32));
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(32.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.22);
        this.getEntityAttribute(npcAttackDamage).setBaseValue(MathHelper.getRandomIntegerInRange(this.rand, 3, 5));
    }

    @Override
    public boolean isMountSaddled() {
        return this.dataWatcher.getWatchableObjectByte(18) == 1;
    }

    public void setWargSaddled(boolean flag) {
        this.dataWatcher.updateObject(18, flag ? (byte) 1 : 0);
    }

    public WargType getWargType() {
        byte i = this.dataWatcher.getWatchableObjectByte(19);
        return WargType.forID(i);
    }

    public void setWargType(WargType w) {
        this.dataWatcher.updateObject(19, (byte) w.wargID);
    }

    public ItemStack getWargArmorWatched() {
        int ID = this.dataWatcher.getWatchableObjectInt(20);
        return new ItemStack(Item.getItemById(ID));
    }

    @Override
    public String getMountArmorTexture() {
        ItemStack armor = this.getWargArmorWatched();
        if(armor != null && armor.getItem() instanceof LOTRItemMountArmor) {
            return ((LOTRItemMountArmor) armor.getItem()).getArmorTexture();
        }
        return null;
    }

    private void setWargArmorWatched(ItemStack itemstack) {
        if(itemstack == null) {
            this.dataWatcher.updateObject(20, 0);
        }
        else {
            this.dataWatcher.updateObject(20, Item.getIdFromItem(itemstack.getItem()));
        }
    }

    @Override
    public IInventory getMountInventory() {
        return this.wargInventory;
    }

    @Override
    public IEntityLivingData initCreatureForHire(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        return data;
    }

    public abstract LOTREntityNPC createWargRider();

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        if(!this.worldObj.isRemote && this.canWargBeRidden() && this.rand.nextInt(3) == 0) {
            LOTREntityNPC rider = this.createWargRider();
            rider.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0f);
            rider.onSpawnWithEgg(null);
            rider.isNPCPersistent = this.isNPCPersistent;
            this.worldObj.spawnEntityInWorld(rider);
            rider.mountEntity(this);
        }
        return data;
    }

    public boolean canWargBeRidden() {
        return true;
    }

    @Override
    public boolean getBelongsToNPC() {
        return false;
    }

    @Override
    public void setBelongsToNPC(boolean flag) {
    }

    private void setupWargInventory() {
        AnimalChest prevInv = this.wargInventory;
        this.wargInventory = new AnimalChest("WargInv", 2);
        this.wargInventory.func_110133_a(this.getCommandSenderName());
        if(prevInv != null) {
            prevInv.func_110132_b(this);
            int invSize = Math.min(prevInv.getSizeInventory(), this.wargInventory.getSizeInventory());
            for(int slot = 0; slot < invSize; ++slot) {
                ItemStack itemstack = prevInv.getStackInSlot(slot);
                if(itemstack == null) continue;
                this.wargInventory.setInventorySlotContents(slot, itemstack.copy());
            }
            prevInv = null;
        }
        this.wargInventory.func_110134_a(this);
        this.checkWargInventory();
    }

    private void checkWargInventory() {
        if(!this.worldObj.isRemote) {
            this.setWargSaddled(this.wargInventory.getStackInSlot(0) != null);
            this.setWargArmorWatched(this.getWargArmor());
        }
    }

    @Override
    public void onInventoryChanged(InventoryBasic inv) {
        boolean prevSaddled = this.isMountSaddled();
        ItemStack prevArmor = this.getWargArmorWatched();
        this.checkWargInventory();
        ItemStack wargArmor = this.getWargArmorWatched();
        if(this.ticksExisted > 20) {
            if(!prevSaddled && this.isMountSaddled()) {
                this.playSound("mob.horse.leather", 0.5f, 1.0f);
            }
            if(!ItemStack.areItemStacksEqual(prevArmor, wargArmor)) {
                this.playSound("mob.horse.armor", 0.5f, 1.0f);
            }
        }
    }

    public void setWargArmor(ItemStack itemstack) {
        this.wargInventory.setInventorySlotContents(1, itemstack);
        this.setupWargInventory();
        this.setWargArmorWatched(this.getWargArmor());
    }

    public ItemStack getWargArmor() {
        return this.wargInventory.getStackInSlot(1);
    }

    @Override
    public int getTotalArmorValue() {
        ItemStack itemstack = this.getWargArmor();
        if(itemstack != null && itemstack.getItem() instanceof LOTRItemMountArmor) {
            LOTRItemMountArmor armor = (LOTRItemMountArmor) itemstack.getItem();
            return armor.getDamageReduceAmount();
        }
        return 0;
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        nbt.setByte("WargType", (byte) this.getWargType().wargID);
        if(this.wargInventory.getStackInSlot(0) != null) {
            nbt.setTag("WargSaddleItem", this.wargInventory.getStackInSlot(0).writeToNBT(new NBTTagCompound()));
        }
        if(this.getWargArmor() != null) {
            nbt.setTag("WargArmorItem", this.getWargArmor().writeToNBT(new NBTTagCompound()));
        }
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt) {
        ItemStack wargArmor;
        super.readEntityFromNBT(nbt);
        this.setWargType(WargType.forID(nbt.getByte("WargType")));
        if(nbt.hasKey("WargSaddleItem")) {
            ItemStack saddle = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("WargSaddleItem"));
            if(saddle != null && saddle.getItem() == Items.saddle) {
                this.wargInventory.setInventorySlotContents(0, saddle);
            }
        }
        else if(nbt.getBoolean("Saddled")) {
            this.wargInventory.setInventorySlotContents(0, new ItemStack(Items.saddle));
        }
        if(nbt.hasKey("WargArmorItem") && (wargArmor = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("WargArmorItem"))) != null && this.isMountArmorValid(wargArmor)) {
            this.wargInventory.setInventorySlotContents(1, wargArmor);
        }
        this.checkWargInventory();
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if(!this.worldObj.isRemote && this.riddenByEntity instanceof EntityPlayer) {
            EntityPlayer entityplayer = (EntityPlayer) this.riddenByEntity;
            if(LOTRLevelData.getData(entityplayer).getAlignment(this.getFaction()) < 50.0f) {
                entityplayer.mountEntity(null);
            }
            else if(this.isNPCTamed() && this.isMountSaddled()) {
                LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.rideWarg);
            }
        }
        if(this.eatingTick > 0) {
            if(this.eatingTick % 4 == 0) {
                this.worldObj.playSoundAtEntity(this, "random.eat", 0.5f + 0.5f * this.rand.nextInt(2), 0.4f + this.rand.nextFloat() * 0.2f);
            }
            --this.eatingTick;
        }
    }

    @Override
    public boolean interact(EntityPlayer entityplayer) {
        if(this.worldObj.isRemote || this.hiredNPCInfo.isActive) {
            return false;
        }
        if(LOTRMountFunctions.interact(this, entityplayer)) {
            return true;
        }
        if(this.getAttackTarget() != entityplayer) {
            boolean hasRequiredAlignment = LOTRLevelData.getData(entityplayer).getAlignment(this.getFaction()) >= 50.0f;
            boolean notifyNotEnoughAlignment = false;
            ItemStack itemstack = entityplayer.inventory.getCurrentItem();
            if(!notifyNotEnoughAlignment && this.isNPCTamed() && entityplayer.isSneaking()) {
                if(hasRequiredAlignment) {
                    this.openGUI(entityplayer);
                    return true;
                }
                notifyNotEnoughAlignment = true;
            }
            if(!notifyNotEnoughAlignment && this.isNPCTamed() && itemstack != null && itemstack.getItem() instanceof ItemFood && ((ItemFood) itemstack.getItem()).isWolfsFavoriteMeat() && this.getHealth() < this.getMaxHealth()) {
                if(hasRequiredAlignment) {
                    if(!entityplayer.capabilities.isCreativeMode) {
                        --itemstack.stackSize;
                        if(itemstack.stackSize == 0) {
                            entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
                        }
                    }
                    this.heal(((ItemFood) itemstack.getItem()).func_150905_g(itemstack));
                    this.eatingTick = 20;
                    return true;
                }
                notifyNotEnoughAlignment = true;
            }
            if(!notifyNotEnoughAlignment && this.isNPCTamed() && !this.isMountSaddled() && this.canWargBeRidden() && this.riddenByEntity == null && itemstack != null && itemstack.getItem() == Items.saddle) {
                if(hasRequiredAlignment) {
                    this.openGUI(entityplayer);
                    return true;
                }
                notifyNotEnoughAlignment = true;
            }
            if(!notifyNotEnoughAlignment && !this.isChild() && this.canWargBeRidden() && this.riddenByEntity == null) {
                if(itemstack != null && itemstack.interactWithEntity(entityplayer, this)) {
                    return true;
                }
                if(hasRequiredAlignment) {
                    entityplayer.mountEntity(this);
                    this.setAttackTarget(null);
                    this.getNavigator().clearPathEntity();
                    return true;
                }
                notifyNotEnoughAlignment = true;
            }
            if(notifyNotEnoughAlignment) {
                LOTRAlignmentValues.notifyAlignmentNotHighEnough(entityplayer, 50.0f, this.getFaction());
                return true;
            }
        }
        return super.interact(entityplayer);
    }

    @Override
    protected void dropFewItems(boolean flag, int i) {
        super.dropFewItems(flag, i);
        int furs = 1 + this.rand.nextInt(3) + this.rand.nextInt(i + 1);
        for(int l = 0; l < furs; ++l) {
            this.dropItem(LOTRMod.fur, 1);
        }
        int bones = 2 + this.rand.nextInt(2) + this.rand.nextInt(i + 1);
        for(int l = 0; l < bones; ++l) {
            this.dropItem(LOTRMod.wargBone, 1);
        }
        if(flag) {
            int rugChance = 50 - i * 8;
            if(this.rand.nextInt(rugChance = Math.max(rugChance, 1)) == 0) {
                this.entityDropItem(new ItemStack(LOTRMod.wargskinRug, 1, this.getWargType().wargID), 0.0f);
            }
        }
    }

    @Override
    public boolean canDropRares() {
        return false;
    }

    @Override
    public String getLivingSound() {
        return "lotr:warg.say";
    }

    @Override
    public String getHurtSound() {
        return "lotr:warg.hurt";
    }

    @Override
    public String getDeathSound() {
        return "lotr:warg.death";
    }

    @Override
    public String getAttackSound() {
        return "lotr:warg.attack";
    }

    @Override
    public void onDeath(DamageSource damagesource) {
        super.onDeath(damagesource);
        if(!this.worldObj.isRemote) {
            if(this.getBelongsToNPC()) {
                this.wargInventory.setInventorySlotContents(0, null);
                this.wargInventory.setInventorySlotContents(1, null);
            }
            if(this.isNPCTamed()) {
                this.setWargSaddled(false);
                this.dropItem(Items.saddle, 1);
                ItemStack wargArmor = this.getWargArmor();
                if(wargArmor != null) {
                    this.entityDropItem(wargArmor, 0.0f);
                    this.setWargArmor(null);
                }
            }
        }
    }

    public float getTailRotation() {
        float f = (this.getMaxHealth() - this.getHealth()) / this.getMaxHealth();
        return f * -1.2f;
    }

    @Override
    public boolean allowLeashing() {
        return this.isNPCTamed();
    }

    @Override
    public boolean canReEquipHired(int slot, ItemStack itemstack) {
        return false;
    }

    public enum WargType {
        BROWN(0), GREY(1), BLACK(2), WHITE(3), ICE(4), OBSIDIAN(5), FIRE(6);

        public final int wargID;

        WargType(int i) {
            this.wargID = i;
        }

        public String textureName() {
            return this.name().toLowerCase();
        }

        public static WargType forID(int ID) {
            for(WargType w : WargType.values()) {
                if(w.wargID != ID) continue;
                return w;
            }
            return BROWN;
        }

        public static String[] wargTypeNames() {
            String[] names = new String[WargType.values().length];
            for(int i = 0; i < names.length; ++i) {
                names[i] = WargType.values()[i].textureName();
            }
            return names;
        }
    }

}
