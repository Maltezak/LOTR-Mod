package lotr.common.block;

import java.util.Random;

import lotr.common.LOTRMod;
import net.minecraft.item.Item;

public class LOTRBlockLeaves3 extends LOTRBlockLeavesBase {
    public LOTRBlockLeaves3() {
        this.setLeafNames("maple", "larch", "datePalm", "mangrove");
    }

    @Override
    public Item getItemDropped(int i, Random random, int j) {
        return Item.getItemFromBlock(LOTRMod.sapling3);
    }

    @Override
    protected int getSaplingChance(int meta) {
        if(meta == 2) {
            return 15;
        }
        return super.getSaplingChance(meta);
    }
}
