package lotr.common.entity.animal;

import java.util.*;

import lotr.common.LOTRMod;
import lotr.common.entity.*;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class LOTREntityAurochs extends EntityCow implements LOTRRandomSkinEntity {
    private EntityAIBase attackAI;
    private EntityAIBase panicAI;
    private boolean prevIsChild = true;
    protected final float aurochsWidth;
    protected final float aurochsHeight;

    public LOTREntityAurochs(World world) {
        super(world);
        this.aurochsWidth = 1.5f;
        this.aurochsHeight = 1.7f;
        this.setSize(this.aurochsWidth, this.aurochsHeight);
        EntityAITasks.EntityAITaskEntry panic = LOTREntityUtils.removeAITask(this, EntityAIPanic.class);
        this.tasks.addTask(panic.priority, panic.action);
        this.panicAI = panic.action;
        this.attackAI = this.createAurochsAttackAI();
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
    }

    protected EntityAIBase createAurochsAttackAI() {
        return new LOTREntityAIAttackOnCollide(this, 1.7, true);
    }

    @Override
    public void setUniqueID(UUID uuid) {
        this.entityUniqueID = uuid;
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(20, (byte) 0);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.2);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(4.0);
    }

    public boolean isAurochsEnraged() {
        return this.dataWatcher.getWatchableObjectByte(20) == 1;
    }

    public void setAurochsEnraged(boolean flag) {
        this.dataWatcher.updateObject(20, flag ? (byte) 1 : 0);
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if(!this.worldObj.isRemote) {
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
            this.setAurochsEnraged(this.getAttackTarget() != null);
        }
        this.prevIsChild = this.isChild();
    }

    @Override
    public boolean interact(EntityPlayer entityplayer) {
        if(this.isAurochsEnraged()) {
            return false;
        }
        return super.interact(entityplayer);
    }

    @Override
    public boolean attackEntityAsMob(Entity entity) {
        float f = (float) this.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
        boolean flag = entity.attackEntityFrom(DamageSource.causeMobDamage(this), f);
        if(flag) {
            float kb = 0.75f;
            entity.addVelocity(-MathHelper.sin(this.rotationYaw * 3.1415927f / 180.0f) * kb * 0.5f, 0.0, MathHelper.cos(this.rotationYaw * 3.1415927f / 180.0f) * kb * 0.5f);
        }
        return flag;
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float f) {
        Entity attacker;
        boolean flag = super.attackEntityFrom(damagesource, f);
        if(flag && this.isChild() && (attacker = damagesource.getEntity()) instanceof EntityLivingBase) {
            List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(12.0, 12.0, 12.0));
            for(Object element : list) {
                LOTREntityAurochs aurochs;
                Entity entity = (Entity) element;
                if(entity.getClass() != this.getClass() || (aurochs = (LOTREntityAurochs) entity).isChild()) continue;
                aurochs.setAttackTarget((EntityLivingBase) attacker);
            }
        }
        return flag;
    }

    @Override
    protected void dropFewItems(boolean flag, int i) {
        int hides = 2 + this.rand.nextInt(3) + this.rand.nextInt(1 + i);
        for(int l = 0; l < hides; ++l) {
            this.dropItem(Items.leather, 1);
        }
        int meats = 2 + this.rand.nextInt(3) + this.rand.nextInt(1 + i);
        for(int l = 0; l < meats; ++l) {
            if(this.isBurning()) {
                this.dropItem(Items.cooked_beef, 1);
                continue;
            }
            this.dropItem(Items.beef, 1);
        }
        this.dropHornItem(flag, i);
    }

    protected void dropHornItem(boolean flag, int i) {
        this.dropItem(LOTRMod.horn, 1);
    }

    @Override
    public EntityCow createChild(EntityAgeable entity) {
        return new LOTREntityAurochs(this.worldObj);
    }

    @Override
    protected String getLivingSound() {
        return "lotr:aurochs.say";
    }

    @Override
    protected String getHurtSound() {
        return "lotr:aurochs.hurt";
    }

    @Override
    protected String getDeathSound() {
        return "lotr:aurochs.hurt";
    }

    @Override
    protected float getSoundVolume() {
        return 1.0f;
    }

    @Override
    protected float getSoundPitch() {
        return super.getSoundPitch() * 0.75f;
    }

    @Override
    public int getTalkInterval() {
        return 200;
    }

    @Override
    public ItemStack getPickedResult(MovingObjectPosition target) {
        return new ItemStack(LOTRMod.spawnEgg, 1, LOTREntities.getEntityID(this));
    }
}
