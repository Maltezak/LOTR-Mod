package lotr.common.entity.ai;

import java.util.Random;

import lotr.common.LOTRFoods;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.item.ItemStack;

public abstract class LOTREntityAIConsumeBase extends EntityAIBase {
    protected LOTREntityNPC theEntity;
    protected Random rand;
    protected LOTRFoods foodPool;
    private int chanceToConsume;
    private int consumeTick;

    public LOTREntityAIConsumeBase(LOTREntityNPC entity, LOTRFoods foods, int chance) {
        this.theEntity = entity;
        this.rand = this.theEntity.getRNG();
        this.foodPool = foods;
        this.chanceToConsume = chance;
        this.setMutexBits(3);
    }

    @Override
    public boolean shouldExecute() {
        if(this.theEntity.isChild()) {
            return false;
        }
        if(this.theEntity.getAttackTarget() != null) {
            return false;
        }
        if(this.theEntity.npcItemsInv.getIsEating()) {
            return false;
        }
        return this.shouldConsume();
    }

    protected boolean shouldConsume() {
        boolean needsHeal = this.theEntity.getHealth() < this.theEntity.getMaxHealth();
        return needsHeal && this.rand.nextInt(this.chanceToConsume / 4) == 0 || this.rand.nextInt(this.chanceToConsume) == 0;
    }

    @Override
    public void startExecuting() {
        this.theEntity.npcItemsInv.setEatingBackup(this.theEntity.getHeldItem());
        this.theEntity.npcItemsInv.setIsEating(true);
        this.theEntity.setCurrentItemOrArmor(0, this.createConsumable());
        this.consumeTick = this.getConsumeTime();
    }

    protected int getConsumeTime() {
        return 32;
    }

    @Override
    public void updateTask() {
        --this.consumeTick;
        this.updateConsumeTick(this.consumeTick);
        if(this.consumeTick == 0) {
            this.consume();
        }
    }

    protected abstract ItemStack createConsumable();

    protected abstract void updateConsumeTick(int var1);

    protected abstract void consume();

    @Override
    public boolean continueExecuting() {
        return this.consumeTick > 0 && this.theEntity.getHeldItem() != null && this.theEntity.getAttackTarget() == null;
    }

    @Override
    public void resetTask() {
        this.theEntity.setCurrentItemOrArmor(0, this.theEntity.npcItemsInv.getEatingBackup());
        this.theEntity.npcItemsInv.setEatingBackup(null);
        this.theEntity.npcItemsInv.setIsEating(false);
        this.theEntity.refreshCurrentAttackMode();
        this.consumeTick = 0;
    }
}
