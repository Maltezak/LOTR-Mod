package lotr.common.block;

import java.util.*;

import lotr.common.LOTRCreativeTabs;
import lotr.common.world.biome.LOTRBiomeGenMordor;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraftforge.common.IShearable;

public class LOTRBlockMordorMoss extends Block implements IShearable {
    public LOTRBlockMordorMoss() {
        super(Material.plants);
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.0625f, 1.0f);
        this.setTickRandomly(true);
        this.setCreativeTab(LOTRCreativeTabs.tabDeco);
        this.setHardness(0.2f);
        this.setStepSound(Block.soundTypeGrass);
    }

    @Override
    public boolean canPlaceBlockAt(World world, int i, int j, int k) {
        return super.canPlaceBlockAt(world, i, j, k) && this.canBlockStay(world, i, j, k);
    }

    @Override
    public boolean canBlockStay(World world, int i, int j, int k) {
        if(j >= 0 && j < 256) {
            return LOTRBiomeGenMordor.isSurfaceMordorBlock(world, i, j - 1, k);
        }
        return false;
    }

    @Override
    public void onNeighborBlockChange(World world, int i, int j, int k, Block block) {
        super.onNeighborBlockChange(world, i, j, k, block);
        this.checkMossCanStay(world, i, j, k);
    }

    @Override
    public void updateTick(World world, int i, int j, int k, Random random) {
        this.checkMossCanStay(world, i, j, k);
    }

    private void checkMossCanStay(World world, int i, int j, int k) {
        if(!this.canBlockStay(world, i, j, k)) {
            this.dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k), 0);
            world.setBlockToAir(i, j, k);
        }
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
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
