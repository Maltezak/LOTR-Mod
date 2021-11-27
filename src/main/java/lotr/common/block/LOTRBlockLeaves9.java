package lotr.common.block;

import java.util.Random;

import lotr.common.LOTRMod;
import net.minecraft.item.Item;

public class LOTRBlockLeaves9 extends LOTRBlockLeavesBase {
    public LOTRBlockLeaves9() {
        this.setLeafNames("dragon", "kanuka");
    }

    @Override
    public Item getItemDropped(int i, Random random, int j) {
        return Item.getItemFromBlock(LOTRMod.sapling9);
    }
}
