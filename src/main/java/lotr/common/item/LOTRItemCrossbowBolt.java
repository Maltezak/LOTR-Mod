package lotr.common.item;

import lotr.common.LOTRCreativeTabs;
import lotr.common.dispenser.LOTRDispenseCrossbowBolt;
import net.minecraft.block.BlockDispenser;
import net.minecraft.item.Item;

public class LOTRItemCrossbowBolt extends Item {
    public boolean isPoisoned = false;

    public LOTRItemCrossbowBolt() {
        this.setCreativeTab(LOTRCreativeTabs.tabCombat);
        BlockDispenser.dispenseBehaviorRegistry.putObject(this, new LOTRDispenseCrossbowBolt(this));
    }

    public LOTRItemCrossbowBolt setPoisoned() {
        this.isPoisoned = true;
        return this;
    }
}
