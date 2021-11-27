package lotr.common.entity.ai;

import lotr.common.entity.npc.*;
import net.minecraft.entity.ai.EntityAIBase;

public class LOTREntityAIBossJumpAttack extends EntityAIBase {
    private LOTREntityNPC theBoss;
    private double jumpSpeed;
    private float jumpChance;

    public LOTREntityAIBossJumpAttack(LOTREntityNPC boss, double d, float f) {
        this.theBoss = boss;
        this.jumpSpeed = d;
        this.jumpChance = f;
        this.setMutexBits(3);
    }

    @Override
    public boolean shouldExecute() {
        if(this.theBoss.getAttackTarget() == null) {
            return false;
        }
        if(this.theBoss.bossInfo.jumpAttack) {
            return false;
        }
        if(this.theBoss.getRNG().nextInt(20) == 0) {
            float f = ((LOTRBoss) (this.theBoss)).getBaseChanceModifier();
            f *= this.jumpChance;
            int enemies = this.theBoss.bossInfo.getNearbyEnemies().size();
            if(enemies > 1.0f) {
                f *= enemies * 4.0f;
            }
            float distance = this.theBoss.getDistanceToEntity(this.theBoss.getAttackTarget());
            distance = 8.0f - distance;
            if((distance /= 2.0f) > 1.0f) {
                f *= distance;
            }
            return this.theBoss.getRNG().nextFloat() < f;
        }
        return false;
    }

    @Override
    public void startExecuting() {
        this.theBoss.bossInfo.doJumpAttack(this.jumpSpeed);
    }
}
