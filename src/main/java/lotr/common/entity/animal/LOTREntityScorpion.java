package lotr.common.entity.animal;

import lotr.common.LOTRMod;
import lotr.common.entity.*;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.entity.npc.*;
import net.minecraft.block.Block;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.*;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public abstract class LOTREntityScorpion extends EntityMob implements LOTRMobSpawnerCondition {
    private float scorpionWidth = -1.0f;
    private float scorpionHeight;
    protected boolean spawningFromSpawner = false;
    private static IEntitySelector noWraiths = new IEntitySelector() {

        @Override
        public boolean isEntityApplicable(Entity entity) {
            return !(entity instanceof LOTREntityHaradPyramidWraith);
        }
    };

    public LOTREntityScorpion(World world) {
        super(world);
        this.setSize(1.2f, 0.9f);
        this.getNavigator().setAvoidsWater(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new LOTREntityAIAttackOnCollide(this, 1.2, false));
        this.tasks.addTask(2, new EntityAIWander(this, 1.0));
        this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0f, 0.05f));
        this.tasks.addTask(4, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, LOTREntityNPC.class, 0, true, false, noWraiths));
    }

    @Override
    public void setSpawningFromMobSpawner(boolean flag) {
        this.spawningFromSpawner = flag;
    }

    protected int getRandomScorpionScale() {
        return this.rand.nextInt(3);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(18, (byte) this.getRandomScorpionScale());
        this.dataWatcher.addObject(19, 0);
    }

    public int getScorpionScale() {
        return this.dataWatcher.getWatchableObjectByte(18);
    }

    public void setScorpionScale(int i) {
        this.dataWatcher.updateObject(18, (byte) i);
    }

    public float getScorpionScaleAmount() {
        return 0.5f + this.getScorpionScale() / 2.0f;
    }

    public int getStrikeTime() {
        return this.dataWatcher.getWatchableObjectInt(19);
    }

    public void setStrikeTime(int i) {
        this.dataWatcher.updateObject(19, i);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(12.0 + this.getScorpionScale() * 6.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.35 - this.getScorpionScale() * 0.05);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(2.0 + this.getScorpionScale());
    }

    @Override
    public boolean isAIEnabled() {
        return true;
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        nbt.setByte("ScorpionScale", (byte) this.getScorpionScale());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        this.setScorpionScale(nbt.getByte("ScorpionScale"));
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(2.0 + this.getScorpionScale());
    }

    @Override
    public float getBlockPathWeight(int i, int j, int k) {
        if(this.spawningFromSpawner) {
            return 0.0f;
        }
        return super.getBlockPathWeight(i, j, k);
    }

    @Override
    public void onLivingUpdate() {
        int i;
        super.onLivingUpdate();
        this.rescaleScorpion(this.getScorpionScaleAmount());
        if(!this.worldObj.isRemote && (i = this.getStrikeTime()) > 0) {
            this.setStrikeTime(i - 1);
        }
    }

    @Override
    protected void setSize(float f, float f1) {
        boolean flag = this.scorpionWidth > 0.0f;
        this.scorpionWidth = f;
        this.scorpionHeight = f1;
        if(!flag) {
            this.rescaleScorpion(1.0f);
        }
    }

    private void rescaleScorpion(float f) {
        super.setSize(this.scorpionWidth * f, this.scorpionHeight * f);
    }

    @Override
    public boolean interact(EntityPlayer entityplayer) {
        ItemStack itemstack = entityplayer.inventory.getCurrentItem();
        if(itemstack != null && itemstack.getItem() == Items.glass_bottle) {
            --itemstack.stackSize;
            if(itemstack.stackSize <= 0) {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, new ItemStack(LOTRMod.bottlePoison));
            }
            else if(!entityplayer.inventory.addItemStackToInventory(new ItemStack(LOTRMod.bottlePoison)) && !entityplayer.capabilities.isCreativeMode) {
                entityplayer.dropPlayerItemWithRandomChoice(new ItemStack(LOTRMod.bottlePoison), false);
            }
            return true;
        }
        return super.interact(entityplayer);
    }

    @Override
    public boolean attackEntityAsMob(Entity entity) {
        if(super.attackEntityAsMob(entity)) {
            int difficulty;
            int duration;
            if(!this.worldObj.isRemote) {
                this.setStrikeTime(20);
            }
            if(entity instanceof EntityLivingBase && (duration = (difficulty = this.worldObj.difficultySetting.getDifficultyId()) * (difficulty + 5) / 2) > 0) {
                ((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.poison.id, duration * 20, 0));
            }
            return true;
        }
        return false;
    }

    @Override
    protected String getLivingSound() {
        return "mob.spider.say";
    }

    @Override
    protected String getHurtSound() {
        return "mob.spider.say";
    }

    @Override
    protected String getDeathSound() {
        return "mob.spider.death";
    }

    @Override
    protected void func_145780_a(int i, int j, int k, Block block) {
        this.playSound("mob.spider.step", 0.15f, 1.0f);
    }

    @Override
    protected void dropFewItems(boolean flag, int i) {
        int k = 1 + this.rand.nextInt(3) + this.rand.nextInt(i + 1);
        for(int j = 0; j < k; ++j) {
            this.dropItem(Items.rotten_flesh, 1);
        }
    }

    @Override
    protected int getExperiencePoints(EntityPlayer entityplayer) {
        int i = this.getScorpionScale();
        return 2 + i + this.rand.nextInt(i + 2);
    }

    @Override
    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.ARTHROPOD;
    }

    @Override
    public boolean isPotionApplicable(PotionEffect effect) {
        if(effect.getPotionID() == Potion.poison.id) {
            return false;
        }
        return super.isPotionApplicable(effect);
    }

    @Override
    public ItemStack getPickedResult(MovingObjectPosition target) {
        return new ItemStack(LOTRMod.spawnEgg, 1, LOTREntities.getEntityID(this));
    }

}
