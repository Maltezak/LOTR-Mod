package lotr.common.entity.animal;

import java.util.List;

import lotr.common.LOTRMod;
import lotr.common.entity.ai.*;
import lotr.common.item.LOTRItemLionRug;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public abstract class LOTREntityLionBase extends LOTREntityAnimalMF {
    private EntityAIBase attackAI = new LOTREntityAIAttackOnCollide(this, 1.5, false);
    private EntityAIBase panicAI = new EntityAIPanic(this, 1.5);
    private EntityAIBase targetNearAI = new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true);
    private int hostileTick = 0;
    private boolean prevIsChild = true;

    public LOTREntityLionBase(World world) {
        super(world);
        this.setSize(1.4f, 1.6f);
        this.getNavigator().setAvoidsWater(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, this.panicAI);
        this.tasks.addTask(3, new LOTREntityAIMFMate(this, 1.0));
        this.tasks.addTask(4, new EntityAITempt(this, 1.4, Items.fish, false));
        this.tasks.addTask(5, new EntityAIFollowParent(this, 1.4));
        this.tasks.addTask(6, new LOTREntityAILionChase(this, 1.5));
        this.tasks.addTask(7, new EntityAIWander(this, 1.0));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0f));
        this.tasks.addTask(9, new EntityAILookIdle(this));
        this.targetTasks.addTask(0, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(1, this.targetNearAI);
    }

    @Override
    public Class getAnimalMFBaseClass() {
        return LOTREntityLionBase.class;
    }

    @Override
    public void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(20, (byte) 0);
    }

    public boolean isHostile() {
        return this.dataWatcher.getWatchableObjectByte(20) == 1;
    }

    public void setHostile(boolean flag) {
        this.dataWatcher.updateObject(20, flag ? (byte) 1 : 0);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(40.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.2);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(4.0);
    }

    @Override
    public boolean isAIEnabled() {
        return true;
    }

    @Override
    public void onLivingUpdate() {
        boolean isChild;
        EntityLivingBase entity;
        if(!this.worldObj.isRemote && (isChild = this.isChild()) != this.prevIsChild) {
            if(isChild) {
                this.tasks.removeTask(this.attackAI);
                this.tasks.addTask(2, this.panicAI);
                this.targetTasks.removeTask(this.targetNearAI);
            }
            else {
                this.tasks.removeTask(this.panicAI);
                if(this.hostileTick > 0) {
                    this.tasks.addTask(1, this.attackAI);
                    this.targetTasks.addTask(1, this.targetNearAI);
                }
                else {
                    this.tasks.removeTask(this.attackAI);
                    this.targetTasks.removeTask(this.targetNearAI);
                }
            }
        }
        super.onLivingUpdate();
        if(!this.worldObj.isRemote && this.getAttackTarget() != null && (!(entity = this.getAttackTarget()).isEntityAlive() || entity instanceof EntityPlayer && ((EntityPlayer) entity).capabilities.isCreativeMode)) {
            this.setAttackTarget(null);
        }
        if(!this.worldObj.isRemote) {
            if(this.hostileTick > 0 && this.getAttackTarget() == null) {
                --this.hostileTick;
            }
            this.setHostile(this.hostileTick > 0);
            if(this.isHostile()) {
                this.resetInLove();
            }
        }
    }

    @Override
    protected void dropFewItems(boolean flag, int i) {
        int furs = 1 + this.rand.nextInt(3) + 1;
        for(int l = 0; l < furs; ++l) {
            this.dropItem(LOTRMod.lionFur, 1);
        }
        int meats = this.rand.nextInt(2) + 1 + this.rand.nextInt(1 + i);
        for(int l = 0; l < meats; ++l) {
            if(this.isBurning()) {
                this.dropItem(LOTRMod.lionCooked, 1);
                continue;
            }
            this.dropItem(LOTRMod.lionRaw, 1);
        }
        if(flag) {
            int rugChance = 30 - i * 5;
            if(this.rand.nextInt(rugChance = Math.max(rugChance, 1)) == 0) {
                this.entityDropItem(new ItemStack(LOTRMod.lionRug, 1, this.getLionRugType().lionID), 0.0f);
            }
        }
    }

    protected abstract LOTRItemLionRug.LionRugType getLionRugType();

    @Override
    protected int getExperiencePoints(EntityPlayer entityplayer) {
        return 2 + this.worldObj.rand.nextInt(3);
    }

    @Override
    public boolean attackEntityAsMob(Entity entity) {
        float f = (float) this.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
        return entity.attackEntityFrom(DamageSource.causeMobDamage(this), f);
    }

    @Override
    public EntityAgeable createChild(EntityAgeable entity) {
        return this.rand.nextBoolean() ? new LOTREntityLion(this.worldObj) : new LOTREntityLioness(this.worldObj);
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float f) {
        Entity attacker;
        boolean flag = super.attackEntityFrom(damagesource, f);
        if(flag && (attacker = damagesource.getEntity()) instanceof EntityLivingBase) {
            if(this.isChild()) {
                double range = 12.0;
                List<LOTREntityLionBase> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(range, range, range));
                for(LOTREntityLionBase obj : list) {
                    LOTREntityLionBase lion;
                    Entity entity = obj;
                    if(!(entity instanceof LOTREntityLionBase) || (lion = (LOTREntityLionBase) entity).isChild()) continue;
                    lion.becomeAngryAt((EntityLivingBase) attacker);
                }
            }
            else {
                this.becomeAngryAt((EntityLivingBase) attacker);
            }
        }
        return flag;
    }

    private void becomeAngryAt(EntityLivingBase entity) {
        this.setAttackTarget(entity);
        this.hostileTick = 200;
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        nbt.setInteger("Angry", this.hostileTick);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        this.hostileTick = nbt.getInteger("Angry");
    }

    @Override
    public boolean isBreedingItem(ItemStack itemstack) {
        return itemstack.getItem() == Items.fish;
    }

    @Override
    public boolean interact(EntityPlayer entityplayer) {
        if(this.isHostile()) {
            return false;
        }
        return super.interact(entityplayer);
    }

    @Override
    public int getTalkInterval() {
        return 300;
    }

    @Override
    protected String getLivingSound() {
        return "lotr:lion.say";
    }

    @Override
    protected String getHurtSound() {
        return "lotr:lion.hurt";
    }

    @Override
    protected String getDeathSound() {
        return "lotr:lion.death";
    }
}
