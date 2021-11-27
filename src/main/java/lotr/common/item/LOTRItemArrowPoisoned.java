package lotr.common.item;

import lotr.common.LOTRCreativeTabs;
import lotr.common.dispenser.LOTRDispenseArrowPoisoned;
import net.minecraft.block.BlockDispenser;
import net.minecraft.item.Item;

public class LOTRItemArrowPoisoned extends Item {
    public LOTRItemArrowPoisoned() {
        this.setCreativeTab(LOTRCreativeTabs.tabCombat);
        BlockDispenser.dispenseBehaviorRegistry.putObject(this, new LOTRDispenseArrowPoisoned());
    }
}
