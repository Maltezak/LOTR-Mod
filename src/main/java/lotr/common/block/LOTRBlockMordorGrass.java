package lotr.common.block;

import java.util.*;

import lotr.common.*;
import lotr.common.world.biome.LOTRBiomeGenMordor;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraftforge.common.IShearable;

public class LOTRBlockMordorGrass extends BlockBush implements IShearable {
    public LOTRBlockMordorGrass() {
        super(Material.vine);
        this.setBlockBounds(0.1f, 0.0f, 0.1f, 0.9f, 0.8f, 0.9f);
        this.setCreativeTab(LOTRCreativeTabs.tabDeco);
        this.setHardness(0.0f);
        this.setStepSound(Block.soundTypeGrass);
    }

    @Override
    public boolean canBlockStay(World world, int i, int j, int k) {
        if(j >= 0 && j < 256) {
            return LOTRBiomeGenMordor.isSurfaceMordorBlock(world, i, j - 1, k);
        }
        return false;
    }

    @Override
    public boolean isReplaceable(IBlockAccess world, int i, int j, int k) {
        return true;
    }

    @Override
    public int getRenderType() {
        return LOTRMod.proxy.getGrassRenderID();
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
        ArrayList<ItemStack> list = new ArrayList<>();
        list.add(new ItemStack(this, 1, world.getBlockMetadata(i, j, k)));
        return list;
    }
}
