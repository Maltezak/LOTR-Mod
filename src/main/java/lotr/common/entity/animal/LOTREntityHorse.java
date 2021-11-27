package lotr.common.entity.animal;

import java.util.List;
import lotr.common.LOTRMod;
import lotr.common.LOTRReflection;
import lotr.common.entity.LOTREntities;
import lotr.common.entity.LOTREntityUtils;
import lotr.common.entity.ai.*;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.entity.npc.LOTRNPCMount;
import lotr.common.item.LOTRItemMountArmor;
import lotr.common.world.biome.LOTRBiomeGenDorEnErnil;
import lotr.common.world.biome.LOTRBiomeGenRohan;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.AnimalChest;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class LOTREntityHorse extends EntityHorse implements LOTRNPCMount {
    private boolean isMoving;
    private ItemStack prevMountArmor;
    private EntityAIBase attackAI;
    private EntityAIBase panicAI;
    private boolean prevIsChild = true;

    public LOTREntityHorse(World world) {
        super(world);
        this.tasks.addTask(0, new LOTREntityAIHiredHorseRemainStill(this));
        this.tasks.addTask(0, new LOTREntityAIHorseMoveToRiderTarget(this));
        this.tasks.addTask(0, new LOTREntityAIHorseFollowHiringPlayer(this));
        EntityAITasks.EntityAITaskEntry panic = LOTREntityUtils.removeAITask(this, EntityAIPanic.class);
        this.tasks.addTask(panic.priority, panic.action);
        this.panicAI = panic.action;
        this.attackAI = this.createMountAttackAI();
        if(this.isMountHostile()) {
            this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        }
    }

    protected EntityAIBase createMountAttackAI() {
        return null;
    }

    protected boolean isMountHostile() {
        return false;
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(25, (byte) 0);
        this.dataWatcher.addObject(26, (byte) 1);
        this.dataWatcher.addObject(27, 0);
        this.dataWatcher.addObject(28, (byte) 0);
        this.dataWatcher.addObject(29, (byte) 0);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0);
        if(this.isMountHostile()) {
            this.getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage);
        }
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        if(!this.worldObj.isRemote) {
            data = super.onSpawnWithEgg(data);
            this.onLOTRHorseSpawn();
            this.setHealth(this.getMaxHealth());
            return data;
        }
        int j = this.rand.nextInt(7);
        int k = this.rand.nextInt(5);
        int i = j | k << 8;
        this.setHorseVariant(i);
        return data;
    }

    protected void onLOTRHorseSpawn() {
        int i = MathHelper.floor_double(this.posX);
        int k = MathHelper.floor_double(this.posZ);
        BiomeGenBase biome = this.worldObj.getBiomeGenForCoords(i, k);
        if(this.getClass() == LOTREntityHorse.class) {
            float healthBoost = 0.0f;
            float speedBoost = 0.0f;
            float jumpAdd = 0.0f;
            if(biome instanceof LOTRBiomeGenRohan) {
                healthBoost = 0.5f;
                speedBoost = 0.3f;
                jumpAdd = 0.2f;
            }
            if(biome instanceof LOTRBiomeGenDorEnErnil) {
                healthBoost = 0.3f;
                speedBoost = 0.2f;
                jumpAdd = 0.1f;
            }
            if(healthBoost > 0.0f) {
                double maxHealth = this.getEntityAttribute(SharedMonsterAttributes.maxHealth).getAttributeValue();
                this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(maxHealth *= 1.0f + this.rand.nextFloat() * healthBoost);
                this.setHealth(this.getMaxHealth());
            }
            if(speedBoost > 0.0f) {
                double movementSpeed = this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue();
                this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(movementSpeed *= 1.0f + this.rand.nextFloat() * speedBoost);
            }
            double jumpStrength = this.getEntityAttribute(LOTRReflection.getHorseJumpStrength()).getAttributeValue();
            double jumpLimit = Math.max(jumpStrength, 1.0);
            if(jumpAdd > 0.0f) {
                jumpStrength += jumpAdd;
            }
            jumpStrength = Math.min(jumpStrength, jumpLimit);
            this.getEntityAttribute(LOTRReflection.getHorseJumpStrength()).setBaseValue(jumpStrength);
        }
    }

    @Override
    public boolean getBelongsToNPC() {
        return this.dataWatcher.getWatchableObjectByte(25) == 1;
    }

    @Override
    public void setBelongsToNPC(boolean flag) {
        this.dataWatcher.updateObject(25, flag ? (byte) 1 : 0);
        if(flag) {
            this.setHorseTamed(true);
            this.setHorseSaddled(true);
            if(this.getGrowingAge() < 0) {
                this.setGrowingAge(0);
            }
            if(this.getClass() == LOTREntityHorse.class) {
                this.setHorseType(0);
            }
        }
    }

    public boolean getMountable() {
        return this.dataWatcher.getWatchableObjectByte(26) == 1;
    }

    public void setMountable(boolean flag) {
        this.dataWatcher.updateObject(26, flag ? (byte) 1 : 0);
    }

    public ItemStack getMountArmor() {
        int ID = this.dataWatcher.getWatchableObjectInt(27);
        byte meta = this.dataWatcher.getWatchableObjectByte(28);
        return new ItemStack(Item.getItemById(ID), 1, meta);
    }

    @Override
    public String getMountArmorTexture() {
        ItemStack armor = this.getMountArmor();
        if(armor != null && armor.getItem() instanceof LOTRItemMountArmor) {
            return ((LOTRItemMountArmor) armor.getItem()).getArmorTexture();
        }
        return null;
    }

    private void setMountArmorWatched(ItemStack itemstack) {
        if(itemstack == null) {
            this.dataWatcher.updateObject(27, 0);
            this.dataWatcher.updateObject(28, (byte) 0);
        }
        else {
            this.dataWatcher.updateObject(27, Item.getIdFromItem(itemstack.getItem()));
            this.dataWatcher.updateObject(28, (byte) itemstack.getItemDamage());
        }
    }

    public boolean isMountEnraged() {
        return this.dataWatcher.getWatchableObjectByte(29) == 1;
    }

    public void setMountEnraged(boolean flag) {
        this.dataWatcher.updateObject(29, flag ? (byte) 1 : 0);
    }

    @Override
    public boolean isMountSaddled() {
        return this.isHorseSaddled();
    }

    @Override
    public boolean isHorseSaddled() {
        return (!this.isMoving || !this.getBelongsToNPC()) && super.isHorseSaddled();
    }

    public void saddleMountForWorldGen() {
        this.setGrowingAge(0);
        LOTRReflection.getHorseInv(this).setInventorySlotContents(0, new ItemStack(Items.saddle));
        LOTRReflection.setupHorseInv(this);
        this.setHorseTamed(true);
    }

    public void setChestedForWorldGen() {
        this.setChested(true);
        LOTRReflection.setupHorseInv(this);
    }

    public void setMountArmor(ItemStack itemstack) {
        LOTRReflection.getHorseInv(this).setInventorySlotContents(1, itemstack);
        LOTRReflection.setupHorseInv(this);
        this.setMountArmorWatched(itemstack);
    }

    @Override
    public boolean isMountArmorValid(ItemStack itemstack) {
        if(itemstack != null && itemstack.getItem() instanceof LOTRItemMountArmor) {
            LOTRItemMountArmor armor = (LOTRItemMountArmor) itemstack.getItem();
            return armor.isValid(this);
        }
        return false;
    }

    @Override
    public int getTotalArmorValue() {
        ItemStack itemstack = LOTRReflection.getHorseInv(this).getStackInSlot(1);
        if(itemstack != null && itemstack.getItem() instanceof LOTRItemMountArmor) {
            LOTRItemMountArmor armor = (LOTRItemMountArmor) itemstack.getItem();
            return armor.getDamageReduceAmount();
        }
        return 0;
    }

    @Override
    public void onLivingUpdate() {
        AxisAlignedBB swimCheckBox;
        if(!this.worldObj.isRemote) {
            ItemStack armor = LOTRReflection.getHorseInv(this).getStackInSlot(1);
            if(this.ticksExisted > 20 && !ItemStack.areItemStacksEqual(this.prevMountArmor, armor)) {
                this.playSound("mob.horse.armor", 0.5f, 1.0f);
            }
            this.prevMountArmor = armor;
            this.setMountArmorWatched(armor);
        }
        super.onLivingUpdate();
        if(!this.worldObj.isRemote && this.riddenByEntity instanceof EntityPlayer && this.isInWater() && this.motionY < 0.0 && this.worldObj.func_147461_a(swimCheckBox = this.boundingBox.copy().addCoord(0.0, -1.0, 0.0)).isEmpty() && this.rand.nextFloat() < 0.55f) {
            this.motionY += 0.05;
            this.isAirBorne = true;
        }
        if(!this.worldObj.isRemote && this.isMountHostile()) {
            EntityLivingBase target;
            boolean isChild = this.isChild();
            if(isChild != this.prevIsChild) {
                EntityAITasks.EntityAITaskEntry taskEntry;
                if(isChild) {
                    taskEntry = LOTREntityUtils.removeAITask(this, this.attackAI.getClass());
                    this.tasks.addTask(taskEntry.priority, this.panicAI);
                }
                else {
                    taskEntry = LOTREntityUtils.removeAITask(this, this.panicAI.getClass());
                    this.tasks.addTask(taskEntry.priority, this.attackAI);
                }
            }
            if(this.getAttackTarget() != null && (!(target = this.getAttackTarget()).isEntityAlive() || target instanceof EntityPlayer && ((EntityPlayer) target).capabilities.isCreativeMode)) {
                this.setAttackTarget(null);
            }
            if(this.riddenByEntity instanceof EntityLiving) {
                target = ((EntityLiving) this.riddenByEntity).getAttackTarget();
                this.setAttackTarget(target);
            }
            else if(this.riddenByEntity instanceof EntityPlayer) {
                this.setAttackTarget(null);
            }
            this.setMountEnraged(this.getAttackTarget() != null);
        }
        this.prevIsChild = this.isChild();
    }

    @Override
    protected boolean isMovementBlocked() {
        this.isMoving = true;
        boolean flag = super.isMovementBlocked();
        this.isMoving = false;
        return flag;
    }

    @Override
    public void moveEntityWithHeading(float f, float f1) {
        this.isMoving = true;
        super.moveEntityWithHeading(f, f1);
        this.isMoving = false;
    }

    @Override
    public void super_moveEntityWithHeading(float strafe, float forward) {
        super.moveEntityWithHeading(strafe, forward);
    }

    @Override
    public float getBlockPathWeight(int i, int j, int k) {
        if(this.getBelongsToNPC() && this.riddenByEntity instanceof LOTREntityNPC) {
            return ((LOTREntityNPC) this.riddenByEntity).getBlockPathWeight(i, j, k);
        }
        return super.getBlockPathWeight(i, j, k);
    }

    @Override
    public double getMountedYOffset() {
        double d = this.height * 0.5;
        if(this.riddenByEntity != null) {
            d += this.riddenByEntity.yOffset - this.riddenByEntity.getYOffset();
        }
        return d;
    }

    @Override
    public boolean isBreedingItem(ItemStack itemstack) {
        return itemstack != null && LOTRMod.isOreNameEqual(itemstack, "apple");
    }

    @Override
    public EntityAgeable createChild(EntityAgeable otherParent) {
        EntityHorse superChild = (EntityHorse) super.createChild(otherParent);
        LOTREntityHorse child = (LOTREntityHorse) EntityList.createEntityByName(LOTREntities.getStringFromClass(this.getClass()), this.worldObj);
        child.setHorseType(superChild.getHorseType());
        child.setHorseVariant(superChild.getHorseVariant());
        double maxHealth = this.getChildAttribute(this, otherParent, SharedMonsterAttributes.maxHealth, 3.0);
        maxHealth = this.clampChildHealth(maxHealth);
        child.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(maxHealth);
        child.setHealth(child.getMaxHealth());
        double jumpStrength = this.getChildAttribute(this, otherParent, LOTRReflection.getHorseJumpStrength(), 0.1);
        jumpStrength = this.clampChildJump(jumpStrength);
        child.getEntityAttribute(LOTRReflection.getHorseJumpStrength()).setBaseValue(jumpStrength);
        double moveSpeed = this.getChildAttribute(this, otherParent, SharedMonsterAttributes.movementSpeed, 0.03);
        moveSpeed = this.clampChildSpeed(moveSpeed);
        child.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(moveSpeed);
        if(this.isTame() && ((LOTREntityHorse) otherParent).isTame()) {
            child.setHorseTamed(true);
        }
        return child;
    }

    private double getChildAttribute(EntityAgeable parent, EntityAgeable otherParent, IAttribute stat, double variance) {
        double val2;
        double val1 = parent.getEntityAttribute(stat).getBaseValue();
        if(val1 <= (val2 = otherParent.getEntityAttribute(stat).getBaseValue())) {
            return MathHelper.getRandomDoubleInRange(this.rand, val1 - variance, val2 + variance);
        }
        return MathHelper.getRandomDoubleInRange(this.rand, val2 - variance, val1 + variance);
    }

    protected double clampChildHealth(double health) {
        return MathHelper.clamp_double(health, 12.0, 48.0);
    }

    protected double clampChildJump(double jump) {
        return MathHelper.clamp_double(jump, 0.3, 1.0);
    }

    protected double clampChildSpeed(double speed) {
        return MathHelper.clamp_double(speed, 0.08, 0.45);
    }

    @Override
    public boolean interact(EntityPlayer entityplayer) {
        if(!this.getMountable()) {
            return false;
        }
        if(this.isMountEnraged()) {
            return false;
        }
        if(this.getBelongsToNPC()) {
            if(this.riddenByEntity == null) {
                if(!this.worldObj.isRemote) {
                    entityplayer.addChatMessage(new ChatComponentTranslation("chat.lotr.mountOwnedByNPC", new Object[0]));
                }
                return true;
            }
            return false;
        }
        ItemStack itemstack = entityplayer.getHeldItem();
        if(itemstack != null && this.isBreedingItem(itemstack) && this.getGrowingAge() == 0 && !this.isInLove() && this.isTame()) {
            if(!entityplayer.capabilities.isCreativeMode) {
                --itemstack.stackSize;
                if(itemstack.stackSize <= 0) {
                    entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
                }
            }
            this.func_146082_f(entityplayer);
            return true;
        }
        boolean prevInLove = this.isInLove();
        boolean flag = super.interact(entityplayer);
        if(this.isInLove() && !prevInLove) {
            this.resetInLove();
        }
        return flag;
    }

    @Override
    public boolean attackEntityAsMob(Entity entity) {
        float f = (float) this.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
        boolean flag = entity.attackEntityFrom(DamageSource.causeMobDamage(this), f);
        return flag;
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float f) {
        Entity attacker;
        boolean flag = super.attackEntityFrom(damagesource, f);
        if(flag && this.isChild() && this.isMountHostile() && (attacker = damagesource.getEntity()) instanceof EntityLivingBase) {
            List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(12.0, 12.0, 12.0));
            for(Object element : list) {
                LOTREntityHorse mount;
                Entity entity = (Entity) element;
                if(entity.getClass() != this.getClass() || (mount = (LOTREntityHorse) entity).isChild() || mount.isTame()) continue;
                mount.setAttackTarget((EntityLivingBase) attacker);
            }
        }
        return flag;
    }

    @Override
    public void openGUI(EntityPlayer entityplayer) {
        if(!this.worldObj.isRemote && (this.riddenByEntity == null || this.riddenByEntity == entityplayer) && this.isTame()) {
            AnimalChest animalchest = LOTRReflection.getHorseInv(this);
            animalchest.func_110133_a(this.getCommandSenderName());
            entityplayer.openGui(LOTRMod.instance, 29, this.worldObj, this.getEntityId(), animalchest.getSizeInventory(), 0);
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        nbt.setBoolean("BelongsNPC", this.getBelongsToNPC());
        nbt.setBoolean("Mountable", this.getMountable());
        AnimalChest inv = LOTRReflection.getHorseInv(this);
        if(inv.getStackInSlot(1) != null) {
            nbt.setTag("LOTRMountArmorItem", inv.getStackInSlot(1).writeToNBT(new NBTTagCompound()));
        }
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt) {
        ItemStack armor;
        double jumpStrength;
        super.readEntityFromNBT(nbt);
        boolean pre35 = false;
        if(nbt.hasKey("BelongsToNPC")) {
            pre35 = true;
            this.setBelongsToNPC(nbt.getBoolean("BelongsToNPC"));
        }
        else {
            this.setBelongsToNPC(nbt.getBoolean("BelongsNPC"));
        }
        if(nbt.hasKey("Mountable")) {
            this.setMountable(nbt.getBoolean("Mountable"));
        }
        AnimalChest inv = LOTRReflection.getHorseInv(this);
        if(nbt.hasKey("LOTRMountArmorItem") && (armor = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("LOTRMountArmorItem"))) != null && this.isMountArmorValid(armor)) {
            inv.setInventorySlotContents(1, armor);
        }
        if(pre35 && (jumpStrength = this.getEntityAttribute(LOTRReflection.getHorseJumpStrength()).getAttributeValue()) > 1.0) {
            System.out.println("Reducing horse jump strength from " + jumpStrength);
            jumpStrength = 1.0;
            this.getEntityAttribute(LOTRReflection.getHorseJumpStrength()).setBaseValue(jumpStrength);
            System.out.println("Jump strength now " + this.getEntityAttribute(LOTRReflection.getHorseJumpStrength()).getAttributeValue());
        }
    }

    @Override
    public boolean canDespawn() {
        return this.getBelongsToNPC() && this.riddenByEntity == null;
    }

    @Override
    public void onDeath(DamageSource damagesource) {
        if(this.getBelongsToNPC()) {
            AnimalChest inv = LOTRReflection.getHorseInv(this);
            inv.setInventorySlotContents(0, null);
            inv.setInventorySlotContents(1, null);
        }
        super.onDeath(damagesource);
    }

    @Override
    public String getCommandSenderName() {
        if(this.getClass() == LOTREntityHorse.class) {
            return super.getCommandSenderName();
        }
        if(this.hasCustomNameTag()) {
            return this.getCustomNameTag();
        }
        String s = EntityList.getEntityString(this);
        return StatCollector.translateToLocal("entity." + s + ".name");
    }

    @Override
    public ItemStack getPickedResult(MovingObjectPosition target) {
        return new ItemStack(LOTRMod.spawnEgg, 1, LOTREntities.getEntityID(this));
    }

    @Override
    public boolean allowLeashing() {
        if(this.getBelongsToNPC()) {
            return false;
        }
        return super.allowLeashing();
    }

    @Override
    public boolean shouldDismountInWater(Entity rider) {
        return false;
    }

	@Override
	public float getStepHeightWhileRiddenByPlayer() {
		return 1.0f;
	}
}