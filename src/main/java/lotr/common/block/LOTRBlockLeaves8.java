package lotr.common.block;

import java.util.*;

import lotr.common.LOTRMod;
import net.minecraft.item.*;
import net.minecraft.world.World;

public class LOTRBlockLeaves8 extends LOTRBlockLeavesBase {
    public LOTRBlockLeaves8() {
        this.setLeafNames("plum", "redwood", "pomegranate", "palm");
    }

    @Override
    public Item getItemDropped(int i, Random random, int j) {
        return Item.getItemFromBlock(LOTRMod.sapling8);
    }

    @Override
    protected void addSpecialLeafDrops(ArrayList drops, World world, int i, int j, int k, int meta, int fortune) {
        if((meta & 3) == 0 && world.rand.nextInt(this.calcFortuneModifiedDropChance(16, fortune)) == 0) {
            drops.add(new ItemStack(LOTRMod.plum));
        }
        if((meta & 3) == 2 && world.rand.nextInt(this.calcFortuneModifiedDropChance(16, fortune)) == 0) {
            drops.add(new ItemStack(LOTRMod.pomegranate));
        }
    }
}
