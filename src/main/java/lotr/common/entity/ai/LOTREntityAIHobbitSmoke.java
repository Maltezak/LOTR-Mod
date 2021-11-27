package lotr.common.entity.ai;

import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.entity.projectile.LOTREntitySmokeRing;
import lotr.common.item.LOTRItemHobbitPipe;
import net.minecraft.item.ItemStack;

public class LOTREntityAIHobbitSmoke extends LOTREntityAIConsumeBase {
    public LOTREntityAIHobbitSmoke(LOTREntityNPC entity, int chance) {
        super(entity, null, chance);
    }

    @Override
    protected ItemStack createConsumable() {
        return new ItemStack(LOTRMod.hobbitPipe);
    }

    @Override
    protected void updateConsumeTick(int tick) {
    }

    @Override
    protected void consume() {
        LOTREntitySmokeRing smoke = new LOTREntitySmokeRing(this.theEntity.worldObj, this.theEntity);
        int color = 0;
        ItemStack itemstack = this.theEntity.getHeldItem();
        if(itemstack != null && itemstack.getItem() instanceof LOTRItemHobbitPipe) {
            color = LOTRItemHobbitPipe.getSmokeColor(itemstack);
        }
        smoke.setSmokeColour(color);
        this.theEntity.worldObj.spawnEntityInWorld(smoke);
        this.theEntity.playSound("lotr:item.puff", 1.0f, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f);
        this.theEntity.heal(2.0f);
    }
}
