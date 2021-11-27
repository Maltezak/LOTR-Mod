package lotr.common.entity.ai;

import lotr.common.world.biome.LOTRBiomeGenShire;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.*;
import net.minecraft.util.MathHelper;

public class LOTREntityAIHobbitTargetRuffian
extends LOTREntityAINearestAttackableTargetBasic {
    public LOTREntityAIHobbitTargetRuffian(EntityCreature entity, Class targetClass, int chance, boolean flag) {
        super(entity, targetClass, chance, flag);
    }

    public LOTREntityAIHobbitTargetRuffian(EntityCreature entity, Class targetClass, int chance, boolean flag, IEntitySelector selector) {
        super(entity, targetClass, chance, flag, selector);
    }

    @Override
    protected boolean isSuitableTarget(EntityLivingBase entity, boolean flag) {
        return super.isSuitableTarget(entity, flag) && (this.taskOwner.worldObj.getBiomeGenForCoords(MathHelper.floor_double(this.taskOwner.posX), MathHelper.floor_double(this.taskOwner.posZ))) instanceof LOTRBiomeGenShire;
    }
}

