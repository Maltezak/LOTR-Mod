package lotr.common.block;

import cpw.mods.fml.relauncher.*;
import lotr.common.*;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTRBlockOrcChain
extends Block {
    @SideOnly(value=Side.CLIENT)
    private IIcon iconMiddle;
    @SideOnly(value=Side.CLIENT)
    private IIcon iconTop;
    @SideOnly(value=Side.CLIENT)
    private IIcon iconBottom;
    @SideOnly(value=Side.CLIENT)
    private IIcon iconSingle;

    public LOTRBlockOrcChain() {
        super(Material.circuits);
        this.setHardness(1.0f);
        this.setStepSound(Block.soundTypeMetal);
        this.setCreativeTab(LOTRCreativeTabs.tabUtil);
        float f = 0.2f;
        this.setBlockBounds(0.5f - f, 0.0f, 0.5f - f, 0.5f + f, 1.0f, 0.5f + f);
    }

    @SideOnly(value=Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconregister) {
        this.iconMiddle = iconregister.registerIcon(this.getTextureName() + "_mid");
        this.iconTop = iconregister.registerIcon(this.getTextureName() + "_top");
        this.iconBottom = iconregister.registerIcon(this.getTextureName() + "_bottom");
        this.iconSingle = iconregister.registerIcon(this.getTextureName() + "_single");
    }

    @SideOnly(value=Side.CLIENT)
    public IIcon getIcon(IBlockAccess world, int i, int j, int k, int side) {
        boolean chainBelow;
        Block above = world.getBlock(i, j + 1, k);
        Block below = world.getBlock(i, j - 1, k);
        boolean chainAbove = above instanceof LOTRBlockOrcChain;
        chainBelow = below instanceof LOTRBlockOrcChain || below instanceof LOTRBlockChandelier;
        if (chainAbove && chainBelow) {
            return this.iconMiddle;
        }
        if (chainAbove) {
            return this.iconBottom;
        }
        if (chainBelow) {
            return this.iconTop;
        }
        return this.iconSingle;
    }

    @SideOnly(value=Side.CLIENT)
    public IIcon getIcon(int i, int j) {
        return this.iconMiddle;
    }

    @SideOnly(value=Side.CLIENT)
    public String getItemIconName() {
        return this.getTextureName();
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

    public int getRenderType() {
        return LOTRMod.proxy.getOrcChainRenderID();
    }

    public boolean canPlaceBlockAt(World world, int i, int j, int k) {
        Block block = world.getBlock(i, j + 1, k);
        int meta = world.getBlockMetadata(i, j + 1, k);
        if (block instanceof LOTRBlockOrcChain) {
            return true;
        }
        if (block instanceof BlockFence || block instanceof BlockWall) {
            return true;
        }
        if (block instanceof BlockSlab && !block.isOpaqueCube() && (meta & 8) == 0) {
            return true;
        }
        if (block instanceof BlockStairs && (meta & 4) == 0) {
            return true;
        }
        return world.getBlock(i, j + 1, k).isSideSolid(world, i, j + 1, k, ForgeDirection.DOWN);
    }

    public boolean canBlockStay(World world, int i, int j, int k) {
        return this.canPlaceBlockAt(world, i, j, k);
    }

    public void onNeighborBlockChange(World world, int i, int j, int k, Block block) {
        if (!this.canBlockStay(world, i, j, k)) {
            int meta = world.getBlockMetadata(i, j, k);
            this.dropBlockAsItem(world, i, j, k, meta, 0);
            world.setBlockToAir(i, j, k);
        }
        super.onNeighborBlockChange(world, i, j, k, block);
    }

    public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float f, float f1, float f2) {
        ItemStack itemstack = entityplayer.getHeldItem();
        if (itemstack != null && itemstack.getItem() == Item.getItemFromBlock(this)) {
            int j1;
            Block block;
            for (j1 = j; j1 >= 0 && j1 < world.getHeight() && (block = world.getBlock(i, j1, k)) == this; --j1) {
            }
            if (j1 >= 0 && j1 < world.getHeight()) {
                block = world.getBlock(i, j1, k);
                if (this.canPlaceBlockOnSide(world, i, j1, k, side) && block.isReplaceable(world, i, j1, k) && !block.getMaterial().isLiquid()) {
                    int thisMeta = world.getBlockMetadata(i, j, k);
                    world.setBlock(i, j1, k, this, thisMeta, 3);
                    world.playSoundEffect(i + 0.5f, j1 + 0.5f, k + 0.5f, this.stepSound.func_150496_b(), (this.stepSound.getVolume() + 1.0f) / 2.0f, this.stepSound.getPitch() * 0.8f);
                    if (!entityplayer.capabilities.isCreativeMode) {
                        --itemstack.stackSize;
                    }
                    if (itemstack.stackSize <= 0) {
                        entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k) {
        float f = 0.01f;
        return AxisAlignedBB.getBoundingBox(i + 0.5f - f, j, k + 0.5f - f, i + 0.5f + f, j + 1, k + 0.5f + f);
    }

    public boolean isLadder(IBlockAccess world, int i, int j, int k, EntityLivingBase entity) {
        return true;
    }
}

