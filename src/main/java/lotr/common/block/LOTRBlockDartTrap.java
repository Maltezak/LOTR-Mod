package lotr.common.block;

import cpw.mods.fml.relauncher.*;
import lotr.common.*;
import lotr.common.tileentity.LOTRTileEntityDartTrap;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.world.*;

public class LOTRBlockDartTrap extends BlockContainer {
    @SideOnly(value = Side.CLIENT)
    private IIcon trapIcon;
    private Block modelBlock;
    private int modelBlockMeta;

    public LOTRBlockDartTrap(Block block, int meta) {
        super(Material.rock);
        this.setCreativeTab(LOTRCreativeTabs.tabUtil);
        this.setHardness(4.0f);
        this.setStepSound(Block.soundTypeStone);
        this.modelBlock = block;
        this.modelBlockMeta = meta;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int i) {
        return new LOTRTileEntityDartTrap();
    }

    @Override
    public void onBlockAdded(World world, int i, int j, int k) {
        super.onBlockAdded(world, i, j, k);
        this.setDefaultDirection(world, i, j, k);
    }

    private void setDefaultDirection(World world, int i, int j, int k) {
        if(!world.isRemote) {
            Block i1 = world.getBlock(i, j, k - 1);
            Block j1 = world.getBlock(i, j, k + 1);
            Block k1 = world.getBlock(i - 1, j, k);
            Block l1 = world.getBlock(i + 1, j, k);
            int meta = 3;
            if(i1.isOpaqueCube() && !j1.isOpaqueCube()) {
                meta = 3;
            }
            if(j1.isOpaqueCube() && !i1.isOpaqueCube()) {
                meta = 2;
            }
            if(k1.isOpaqueCube() && !l1.isOpaqueCube()) {
                meta = 5;
            }
            if(l1.isOpaqueCube() && !k1.isOpaqueCube()) {
                meta = 4;
            }
            world.setBlockMetadataWithNotify(i, j, k, meta, 2);
        }
    }

    @Override
    public void onBlockPlacedBy(World world, int i, int j, int k, EntityLivingBase entity, ItemStack itemstack) {
        int rotation = MathHelper.floor_double(entity.rotationYaw * 4.0f / 360.0f + 0.5) & 3;
        if(rotation == 0) {
            world.setBlockMetadataWithNotify(i, j, k, 2, 2);
        }
        if(rotation == 1) {
            world.setBlockMetadataWithNotify(i, j, k, 5, 2);
        }
        if(rotation == 2) {
            world.setBlockMetadataWithNotify(i, j, k, 3, 2);
        }
        if(rotation == 3) {
            world.setBlockMetadataWithNotify(i, j, k, 4, 2);
        }
        if(itemstack.hasDisplayName()) {
            ((LOTRTileEntityDartTrap) world.getTileEntity(i, j, k)).func_146018_a(itemstack.getDisplayName());
        }
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public IIcon getIcon(IBlockAccess world, int i, int j, int k, int side) {
        int meta = world.getBlockMetadata(i, j, k);
        if(side == meta) {
            return this.trapIcon;
        }
        return this.modelBlock.getIcon(i, this.modelBlockMeta);
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public IIcon getIcon(int i, int j) {
        if(i == 3) {
            return this.trapIcon;
        }
        return this.modelBlock.getIcon(i, this.modelBlockMeta);
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister iconregister) {
        this.trapIcon = iconregister.registerIcon(this.getTextureName() + "_face");
    }

    @Override
    public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float f, float f1, float f2) {
        if(!world.isRemote) {
            entityplayer.openGui(LOTRMod.instance, 40, world, i, j, k);
        }
        return true;
    }

    @Override
    public void breakBlock(World world, int i, int j, int k, Block block, int meta) {
        LOTRTileEntityDartTrap trap = (LOTRTileEntityDartTrap) world.getTileEntity(i, j, k);
        if(trap != null) {
            LOTRMod.dropContainerItems(trap, world, i, j, k);
            world.func_147453_f(i, j, k, block);
        }
        super.breakBlock(world, i, j, k, block, meta);
    }

    @Override
    public boolean hasComparatorInputOverride() {
        return true;
    }

    @Override
    public int getComparatorInputOverride(World world, int i, int j, int k, int direction) {
        return Container.calcRedstoneFromInventory((IInventory) (world.getTileEntity(i, j, k)));
    }
}
