package lotr.common.block;

import java.util.Random;

import net.minecraft.item.Item;

public class LOTRBlockObsidianGravel extends LOTRBlockGravel {
    @Override
    public Item getItemDropped(int i, Random rand, int j) {
        return Item.getItemFromBlock(this);
    }
}
