package lotr.common.entity.ai;

import java.util.*;

import lotr.common.*;
import lotr.common.entity.npc.*;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.player.EntityPlayer;

public class LOTREntityAINearestAttackableTargetBasic extends EntityAITarget {
    private final Class targetClass;
    private final int targetChance;
    private final TargetSorter targetSorter;
    private final IEntitySelector targetSelector;
    private EntityLivingBase targetEntity;

    public LOTREntityAINearestAttackableTargetBasic(EntityCreature entity, Class cls, int chance, boolean checkSight) {
        this(entity, cls, chance, checkSight, false, null);
    }

    public LOTREntityAINearestAttackableTargetBasic(EntityCreature entity, Class cls, int chance, boolean checkSight, IEntitySelector selector) {
        this(entity, cls, chance, checkSight, false, selector);
    }

    public LOTREntityAINearestAttackableTargetBasic(EntityCreature entity, Class cls, int chance, boolean checkSight, boolean nearby, final IEntitySelector selector) {
        super(entity, checkSight, nearby);
        this.targetClass = cls;
        this.targetChance = chance;
        this.targetSorter = new TargetSorter(entity);
        this.setMutexBits(1);
        this.targetSelector = new IEntitySelector() {

            @Override
            public boolean isEntityApplicable(Entity testEntity) {
                if(testEntity instanceof EntityLivingBase) {
                    EntityLivingBase testEntityLiving = (EntityLivingBase) testEntity;
                    if(selector != null && !selector.isEntityApplicable(testEntityLiving)) {
                        return false;
                    }
                    return LOTREntityAINearestAttackableTargetBasic.this.isSuitableTarget(testEntityLiving, false);
                }
                return false;
            }
        };
    }

    @Override
    public boolean shouldExecute() {
        LOTREntityNPCRideable rideable;
        if(this.targetChance > 0 && this.taskOwner.getRNG().nextInt(this.targetChance) != 0) {
            return false;
        }
        if(this.taskOwner instanceof LOTREntityNPC) {
            LOTREntityNPC npc = (LOTREntityNPC) this.taskOwner;
            if(npc.hiredNPCInfo.isActive && npc.hiredNPCInfo.isHalted()) {
                return false;
            }
            if(npc.isChild()) {
                return false;
            }
        }
        if(this.taskOwner instanceof LOTREntityNPCRideable && ((rideable = (LOTREntityNPCRideable) this.taskOwner).isNPCTamed() || rideable.riddenByEntity instanceof EntityPlayer)) {
            return false;
        }
        double range = this.getTargetDistance();
        double rangeY = Math.min(range, 8.0);
        List entities = this.taskOwner.worldObj.selectEntitiesWithinAABB(this.targetClass, this.taskOwner.boundingBox.expand(range, rangeY, range), this.targetSelector);
        Collections.sort(entities, this.targetSorter);
        if(entities.isEmpty()) {
            return false;
        }
        this.targetEntity = (EntityLivingBase) entities.get(0);
        return true;
    }

    @Override
    public void startExecuting() {
        this.taskOwner.setAttackTarget(this.targetEntity);
        super.startExecuting();
    }

    @Override
    protected boolean isSuitableTarget(EntityLivingBase entity, boolean flag) {
        if(entity == this.taskOwner.ridingEntity || entity == this.taskOwner.riddenByEntity) {
            return false;
        }
        if(super.isSuitableTarget(entity, flag)) {
            if(entity instanceof EntityPlayer) {
                return this.isPlayerSuitableTarget((EntityPlayer) entity);
            }
            if(entity instanceof LOTREntityBandit) {
                return this.taskOwner instanceof LOTREntityNPC && ((LOTREntityNPC) this.taskOwner).hiredNPCInfo.isActive;
            }
            return true;
        }
        return false;
    }

    protected boolean isPlayerSuitableTarget(EntityPlayer entityplayer) {
        return this.isPlayerSuitableAlignmentTarget(entityplayer);
    }

    protected boolean isPlayerSuitableAlignmentTarget(EntityPlayer entityplayer) {
        float alignment = LOTRLevelData.getData(entityplayer).getAlignment(LOTRMod.getNPCFaction(this.taskOwner));
        return alignment < 0.0f;
    }

    public static class TargetSorter implements Comparator<Entity> {
        private final EntityLivingBase theNPC;

        public TargetSorter(EntityLivingBase entity) {
            this.theNPC = entity;
        }

        @Override
        public int compare(Entity e1, Entity e2) {
            double d2;
            double d1 = this.distanceMetricSq(e1);
            if(d1 < (d2 = this.distanceMetricSq(e2))) {
                return -1;
            }
            if(d1 > d2) {
                return 1;
            }
            return 0;
        }

        private double distanceMetricSq(Entity target) {
            double dSq = this.theNPC.getDistanceSqToEntity(target);
            double avg = 12.0;
            double avgSq = avg * avg;
            dSq /= avgSq;
            int dupes = 0;
            double nearRange = 8.0;
            List nearbyEntities = this.theNPC.worldObj.getEntitiesWithinAABB(LOTREntityNPC.class, this.theNPC.boundingBox.expand(nearRange, nearRange, nearRange));
            for(Object obj : nearbyEntities) {
                LOTREntityNPC nearby = (LOTREntityNPC) obj;
                if(nearby == this.theNPC || !nearby.isEntityAlive() || nearby.getAttackTarget() != target) continue;
                ++dupes;
            }
            int dupesSq = dupes * dupes;
            return dSq + dupesSq;
        }
    }

}
