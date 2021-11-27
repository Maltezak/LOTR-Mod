package lotr.common.block;

import java.util.*;

import lotr.common.*;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.*;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTRBlockGrass extends BlockBush implements IShearable {
    private boolean isSandy;

    public LOTRBlockGrass() {
        super(Material.vine);
        this.setBlockBounds(0.1f, 0.0f, 0.1f, 0.9f, 0.8f, 0.9f);
        this.setCreativeTab(LOTRCreativeTabs.tabDeco);
        this.setHardness(0.0f);
        this.setStepSound(Block.soundTypeGrass);
    }

    public LOTRBlockGrass setSandy() {
        this.isSandy = true;
        return this;
    }

    @Override
    public boolean canBlockStay(World world, int i, int j, int k) {
        Block below = world.getBlock(i, j - 1, k);
        if(this.isSandy && below.getMaterial() == Material.sand && below.isSideSolid(world, i, j - 1, k, ForgeDirection.UP)) {
            return true;
        }
        return below.canSustainPlant(world, i, j, k, ForgeDirection.UP, this);
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
    public int quantityDroppedWithBonus(int i, Random random) {
        return Blocks.tallgrass.quantityDroppedWithBonus(i, random);
    }

    @Override
    public ArrayList getDrops(World world, int i, int j, int k, int meta, int fortune) {
        return Blocks.tallgrass.getDrops(world, i, j, k, meta, fortune);
    }

    @Override
    public int getDamageValue(World world, int i, int j, int k) {
        return world.getBlockMetadata(i, j, k);
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
