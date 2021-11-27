package lotr.common.block;

import java.util.*;

import cpw.mods.fml.relauncher.*;
import lotr.common.*;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTRBlockFallenLeaves extends Block implements IShearable {
    public static List<LOTRBlockFallenLeaves> allFallenLeaves = new ArrayList<>();
    private static Random leafRand = new Random();
    private Block[] leafBlocks;

    public LOTRBlockFallenLeaves() {
        super(Material.vine);
        allFallenLeaves.add(this);
        this.setCreativeTab(LOTRCreativeTabs.tabDeco);
        this.setHardness(0.2f);
        this.setStepSound(Block.soundTypeGrass);
        this.useNeighborBrightness = true;
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.125f, 1.0f);
    }

    public static void assignLeaves(Block fallenLeaves, Block... leaves) {
        ((LOTRBlockFallenLeaves) fallenLeaves).leafBlocks = leaves;
    }

    public Block[] getLeafBlocks() {
        return this.leafBlocks;
    }

    public Object[] leafBlockMetaFromFallenMeta(int meta) {
        Block leaf = this.leafBlocks[meta / 4];
        int leafMeta = meta & 3;
        return new Object[] {leaf, leafMeta};
    }

    public static Object[] fallenBlockMetaFromLeafBlockMeta(Block block, int meta) {
        meta &= 3;
        for(LOTRBlockFallenLeaves fallenLeaves : allFallenLeaves) {
            for(int i = 0; i < fallenLeaves.leafBlocks.length; ++i) {
                Block leafBlock = fallenLeaves.leafBlocks[i];
                if(leafBlock != block) continue;
                return new Object[] {fallenLeaves, i * 4 + meta};
            }
        }
        return null;
    }

    @Override
    public void addCollisionBoxesToList(World world, int i, int j, int k, AxisAlignedBB bb, List boxes, Entity entity) {
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public int getRenderType() {
        return LOTRMod.proxy.getFallenLeavesRenderID();
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public IIcon getIcon(int i, int j) {
        Object[] obj = this.leafBlockMetaFromFallenMeta(j);
        return ((Block) obj[0]).getIcon(i, (Integer) obj[1]);
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister iconregister) {
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public int getRenderColor(int i) {
        Object[] obj = this.leafBlockMetaFromFallenMeta(i);
        return ((Block) obj[0]).getRenderColor((Integer) obj[1]);
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public int colorMultiplier(IBlockAccess world, int i, int j, int k) {
        int meta = world.getBlockMetadata(i, j, k);
        Object[] obj = this.leafBlockMetaFromFallenMeta(meta);
        return ((Block) obj[0]).colorMultiplier(world, i, j, k);
    }

    @Override
    public boolean canBlockStay(World world, int i, int j, int k) {
        Block below = world.getBlock(i, j - 1, k);
        int belowMeta = world.getBlockMetadata(i, j - 1, k);
        if(below.getMaterial() == Material.water && belowMeta == 0) {
            return true;
        }
        return below.isSideSolid(world, i, j - 1, k, ForgeDirection.UP);
    }

    @Override
    public boolean canPlaceBlockAt(World world, int i, int j, int k) {
        Block block = world.getBlock(i, j, k);
        if(block.getMaterial().isLiquid()) {
            return false;
        }
        return this.canBlockStay(world, i, j, k);
    }

    @Override
    public void onNeighborBlockChange(World world, int i, int j, int k, Block block) {
        if(!this.canBlockStay(world, i, j, k)) {
            this.dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k), 0);
            world.setBlockToAir(i, j, k);
        }
    }

    @Override
    public Item getItemDropped(int i, Random random, int j) {
        return null;
    }

    @Override
    public int damageDropped(int i) {
        return i;
    }

    @Override
    public boolean isShearable(ItemStack item, IBlockAccess world, int i, int j, int k) {
        return true;
    }

    @Override
    public ArrayList onSheared(ItemStack item, IBlockAccess world, int i, int j, int k, int fortune) {
        ArrayList<ItemStack> drops = new ArrayList<>();
        drops.add(new ItemStack(this, 1, world.getBlockMetadata(i, j, k)));
        return drops;
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void getSubBlocks(Item item, CreativeTabs tab, List list) {
        for(int i = 0; i < this.leafBlocks.length; ++i) {
            Block leaf = this.leafBlocks[i];
            ArrayList<ItemStack> leafTypes = new ArrayList<>();
            leaf.getSubBlocks(Item.getItemFromBlock(leaf), leaf.getCreativeTabToDisplayOn(), leafTypes);
            for(ItemStack leafItem : leafTypes) {
                int meta = leafItem.getItemDamage();
                list.add(new ItemStack(item, 1, i * 4 + meta));
            }
        }
    }
}
