package lotr.common.entity.ai;

import lotr.common.*;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.player.EntityPlayer;

public class LOTREntityAINearestAttackableTargetTroll extends LOTREntityAINearestAttackableTargetBasic {
    public LOTREntityAINearestAttackableTargetTroll(EntityCreature entity, Class targetClass, int chance, boolean flag) {
        super(entity, targetClass, chance, flag);
    }

    public LOTREntityAINearestAttackableTargetTroll(EntityCreature entity, Class targetClass, int chance, boolean flag, IEntitySelector selector) {
        super(entity, targetClass, chance, flag, selector);
    }

    @Override
    protected boolean isPlayerSuitableAlignmentTarget(EntityPlayer entityplayer) {
        float alignment = LOTRLevelData.getData(entityplayer).getAlignment(LOTRMod.getNPCFaction(this.taskOwner));
        if(alignment >= 100.0f) {
            return false;
        }
        if(alignment >= 0.0f) {
            int chance = Math.round(alignment * 10.0f);
            chance = Math.max(chance, 1);
            return this.taskOwner.getRNG().nextInt(chance) == 0;
        }
        return super.isPlayerSuitableAlignmentTarget(entityplayer);
    }
}
