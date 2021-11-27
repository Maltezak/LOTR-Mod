package lotr.common.block;

import java.util.*;

import net.minecraft.item.*;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.IShearable;

public class LOTRBlockDeadPlant extends LOTRBlockFlower implements IShearable {
    public LOTRBlockDeadPlant() {
        this.setBlockBounds(0.1f, 0.0f, 0.1f, 0.9f, 0.8f, 0.9f);
    }

    @Override
    public Item getItemDropped(int i, Random random, int j) {
        return null;
    }

    @Override
    public boolean isShearable(ItemStack item, IBlockAccess world, int i, int j, int k) {
        return true;
    }

    @Override
    public ArrayList onSheared(ItemStack item, IBlockAccess world, int i, int j, int k, int fortune) {
        ArrayList<ItemStack> drops = new ArrayList<>();
        drops.add(new ItemStack(this));
        return drops;
    }
}
