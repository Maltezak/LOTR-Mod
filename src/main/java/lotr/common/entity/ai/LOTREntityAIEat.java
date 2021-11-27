package lotr.common.entity.ai;

import lotr.common.LOTRFoods;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.item.*;

public class LOTREntityAIEat extends LOTREntityAIConsumeBase {
    public LOTREntityAIEat(LOTREntityNPC entity, LOTRFoods foods, int chance) {
        super(entity, foods, chance);
    }

    @Override
    protected ItemStack createConsumable() {
        return this.foodPool.getRandomFood(this.rand);
    }

    @Override
    protected void updateConsumeTick(int tick) {
        if(tick % 4 == 0) {
            this.theEntity.spawnFoodParticles();
            this.theEntity.playSound("random.eat", 0.5f + 0.5f * this.rand.nextInt(2), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f);
        }
    }

    @Override
    protected void consume() {
        ItemStack itemstack = this.theEntity.getHeldItem();
        Item item = itemstack.getItem();
        if(item instanceof ItemFood) {
            ItemFood food = (ItemFood) item;
            this.theEntity.heal(food.func_150905_g(itemstack));
        }
    }
}
