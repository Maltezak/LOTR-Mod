package lotr.common.entity.ai;

import lotr.common.entity.npc.IBandit;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.*;
import net.minecraft.entity.player.EntityPlayer;

public class LOTREntityAINearestAttackableTargetBandit
extends LOTREntityAINearestAttackableTargetBasic {
    private final IBandit taskOwnerAsBandit;

    public LOTREntityAINearestAttackableTargetBandit(EntityCreature entity, Class targetClass, int chance, boolean flag) {
        super(entity, targetClass, chance, flag);
        this.taskOwnerAsBandit = (IBandit)entity;
    }

    public LOTREntityAINearestAttackableTargetBandit(EntityCreature entity, Class targetClass, int chance, boolean flag, IEntitySelector selector) {
        super(entity, targetClass, chance, flag, selector);
        this.taskOwnerAsBandit = (IBandit)entity;
    }

    @Override
    public boolean shouldExecute() {
        if (!this.taskOwnerAsBandit.getBanditInventory().isEmpty()) {
            return false;
        }
        return super.shouldExecute();
    }

    @Override
    protected boolean isSuitableTarget(EntityLivingBase entity, boolean flag) {
        return entity instanceof EntityPlayer && super.isSuitableTarget(entity, flag);
    }

    @Override
    protected boolean isPlayerSuitableTarget(EntityPlayer entityplayer) {
        if (IBandit.Helper.canStealFromPlayerInv(this.taskOwnerAsBandit, entityplayer)) {
            return false;
        }
        return super.isPlayerSuitableTarget(entityplayer);
    }
}

