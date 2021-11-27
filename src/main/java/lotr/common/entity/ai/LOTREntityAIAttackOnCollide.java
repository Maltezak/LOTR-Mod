package lotr.common.entity.ai;

import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.entity.projectile.LOTREntitySpear;
import lotr.common.item.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTREntityAIAttackOnCollide extends EntityAIBase {
    protected World worldObj;
    protected EntityCreature theOwner;
    protected EntityLivingBase attackTarget;
    protected int attackTick = 0;
    protected double moveSpeed;
    protected boolean sightNotRequired;
    protected PathEntity entityPathEntity;
    protected int pathCheckTimer;
    protected boolean avoidsWater;

    public LOTREntityAIAttackOnCollide(EntityCreature entity, double speed, boolean flag) {
        this.theOwner = entity;
        this.worldObj = entity.worldObj;
        this.moveSpeed = speed;
        this.sightNotRequired = flag;
        this.avoidsWater = entity.getNavigator().getAvoidsWater();
        this.setMutexBits(3);
    }

    @Override
    public boolean shouldExecute() {
        if(this.theOwner instanceof LOTREntityNPC && ((LOTREntityNPC) this.theOwner).isPassive) {
            return false;
        }
        EntityLivingBase entity = this.theOwner.getAttackTarget();
        if(entity == null) {
            return false;
        }
        this.attackTarget = entity;
        this.theOwner.getNavigator().setAvoidsWater(false);
        this.entityPathEntity = this.getPathEntity();
        if(this.entityPathEntity != null) {
            return true;
        }
        this.theOwner.getNavigator().setAvoidsWater(this.avoidsWater);
        return false;
    }

    @Override
    public boolean continueExecuting() {
        if(!this.theOwner.isEntityAlive()) {
            return false;
        }
        this.attackTarget = this.theOwner.getAttackTarget();
        if(this.attackTarget == null || !this.attackTarget.isEntityAlive()) {
            return false;
        }
        if(this.sightNotRequired) {
            return this.theOwner.isWithinHomeDistance(MathHelper.floor_double(this.attackTarget.posX), MathHelper.floor_double(this.attackTarget.posY), MathHelper.floor_double(this.attackTarget.posZ));
        }
        return !this.theOwner.getNavigator().noPath();
    }

    @Override
    public void startExecuting() {
        this.theOwner.getNavigator().setPath(this.entityPathEntity, this.moveSpeed);
        this.pathCheckTimer = 0;
    }

    @Override
    public void resetTask() {
        this.attackTarget = null;
        this.theOwner.getNavigator().clearPathEntity();
        this.theOwner.getNavigator().setAvoidsWater(this.avoidsWater);
    }

    @Override
    public void updateTask() {
        ItemStack weapon;
        this.updateLookAndPathing();
        if(this.attackTick > 0) {
            --this.attackTick;
        }
        if((weapon = this.theOwner.getHeldItem()) != null && weapon.getItem() instanceof LOTRItemSpear && this.attackTick <= 0 && this.theOwner instanceof LOTREntityNPC) {
            LOTREntityNPC theNPC = (LOTREntityNPC) this.theOwner;
            ItemStack spearBackup = theNPC.npcItemsInv.getSpearBackup();
            if(spearBackup != null) {
                weapon.getItem();
                double d = this.theOwner.getDistanceToEntity(this.attackTarget);
                double range = this.theOwner.getNavigator().getPathSearchRange();
                if(d > 5.0 && d < range * 0.75) {
                    LOTREntitySpear spear = new LOTREntitySpear(this.worldObj, this.theOwner, this.attackTarget, weapon.copy(), 0.75f + (float) d * 0.025f, 0.5f);
                    this.worldObj.playSoundAtEntity(this.theOwner, "random.bow", 1.0f, 1.0f / (this.worldObj.rand.nextFloat() * 0.4f + 1.2f) + 0.25f);
                    this.worldObj.spawnEntityInWorld(spear);
                    this.attackTick = 30 + this.theOwner.getRNG().nextInt(20);
                    if(ItemStack.areItemStacksEqual(theNPC.npcItemsInv.getIdleItem(), theNPC.npcItemsInv.getMeleeWeapon())) {
                        theNPC.npcItemsInv.setIdleItem(spearBackup);
                    }
                    theNPC.npcItemsInv.setMeleeWeapon(spearBackup);
                    theNPC.npcItemsInv.setSpearBackup(null);
                    return;
                }
            }
        }
        float weaponReach = 1.0f;
        if(this.theOwner.ridingEntity != null) {
            weaponReach = LOTREntityNPC.MOUNT_RANGE_BONUS;
        }
        float meleeRange = (float) this.theOwner.boundingBox.getAverageEdgeLength() + (weaponReach *= LOTRWeaponStats.getMeleeReachFactor(this.theOwner.getHeldItem()));
        if(this.theOwner.getDistanceSqToEntity(this.attackTarget) <= meleeRange * meleeRange && this.attackTick <= 0) {
            this.attackTick = LOTRWeaponStats.getAttackTimeMob(weapon);
            this.theOwner.attackEntityAsMob(this.attackTarget);
            this.theOwner.swingItem();
        }
    }

    protected void updateLookAndPathing() {
        this.theOwner.getLookHelper().setLookPositionWithEntity(this.attackTarget, 30.0f, 30.0f);
        if(this.theOwner.riddenByEntity instanceof EntityLiving) {
            ((EntityLiving) this.theOwner.riddenByEntity).rotationYaw = this.theOwner.rotationYaw;
            ((EntityLiving) this.theOwner.riddenByEntity).rotationYawHead = this.theOwner.rotationYawHead;
        }
        if((this.sightNotRequired || this.theOwner.getEntitySenses().canSee(this.attackTarget)) && --this.pathCheckTimer <= 0) {
            this.pathCheckTimer = 10 + this.theOwner.getRNG().nextInt(10);
            PathEntity path = this.getPathEntity();
            if(path != null) {
                this.theOwner.getNavigator().setPath(path, this.moveSpeed);
            }
        }
    }

    private PathEntity getPathEntity() {
        if(this.theOwner.ridingEntity != null) {
            return this.worldObj.getPathEntityToEntity(this.theOwner, this.attackTarget, this.theOwner.getNavigator().getPathSearchRange(), true, this.theOwner.getNavigator().getCanBreakDoors(), this.theOwner.getNavigator().getAvoidsWater(), false);
        }
        return this.theOwner.getNavigator().getPathToEntityLiving(this.attackTarget);
    }
}
