package lotr.common.entity.ai;

import lotr.common.LOTRFoods;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class LOTREntityAIBreeEat
extends LOTREntityAIEat {
    public LOTREntityAIBreeEat(LOTREntityNPC entity, LOTRFoods foods, int chance) {
        super(entity, foods, chance);
    }

    @Override
    protected ItemStack createConsumable() {
        if (this.theEntity.getNPCName().equals("Peter Jackson")) {
            return new ItemStack(Items.carrot);
        }
        return super.createConsumable();
    }
}

