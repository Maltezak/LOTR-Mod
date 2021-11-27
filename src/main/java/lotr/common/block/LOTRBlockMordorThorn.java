package lotr.common.block;

import java.util.*;

import lotr.common.*;
import lotr.common.fac.LOTRFaction;
import net.minecraft.entity.Entity;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraftforge.common.IShearable;

public class LOTRBlockMordorThorn extends LOTRBlockMordorPlant implements IShearable {
    public LOTRBlockMordorThorn() {
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

    @Override
    public void onEntityCollidedWithBlock(World world, int i, int j, int k, Entity entity) {
        if(LOTRMod.getNPCFaction(entity) != LOTRFaction.MORDOR) {
            entity.attackEntityFrom(LOTRDamage.plantHurt, 2.0f);
        }
    }
}
