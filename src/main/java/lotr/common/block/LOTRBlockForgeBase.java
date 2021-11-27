package lotr.common.block;

import java.util.Random;

import cpw.mods.fml.relauncher.*;
import lotr.common.*;
import lotr.common.tileentity.LOTRTileEntityForgeBase;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.*;

public abstract class LOTRBlockForgeBase extends BlockContainer {
    @SideOnly(value = Side.CLIENT)
    private IIcon[] forgeIcons;

    public LOTRBlockForgeBase() {
        super(Material.rock);
        this.setCreativeTab(LOTRCreativeTabs.tabUtil);
        this.setHardness(4.0f);
        this.setStepSound(Block.soundTypeStone);
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

    @SideOnly(value = Side.CLIENT)
    @Override
    public IIcon getIcon(IBlockAccess world, int i, int j, int k, int side) {
        if(side == 1 || side == 0) {
            return this.forgeIcons[1];
        }
        int meta = world.getBlockMetadata(i, j, k) & 7;
        return side != meta ? this.forgeIcons[0] : (LOTRBlockForgeBase.isForgeActive(world, i, j, k) ? this.forgeIcons[3] : this.forgeIcons[2]);
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public IIcon getIcon(int i, int j) {
        return i == 1 || i == 0 ? this.forgeIcons[1] : (i == 3 ? this.forgeIcons[2] : this.forgeIcons[0]);
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister iconregister) {
        this.forgeIcons = new IIcon[4];
        this.forgeIcons[0] = iconregister.registerIcon(this.getTextureName() + "_side");
        this.forgeIcons[1] = iconregister.registerIcon(this.getTextureName() + "_top");
        this.forgeIcons[2] = iconregister.registerIcon(this.getTextureName() + "_front");
        this.forgeIcons[3] = iconregister.registerIcon(this.getTextureName() + "_active");
    }

    protected abstract boolean useLargeSmoke();

    @SideOnly(value = Side.CLIENT)
    @Override
    public void randomDisplayTick(World world, int i, int j, int k, Random random) {
        if(LOTRBlockForgeBase.isForgeActive(world, i, j, k)) {
            int meta = world.getBlockMetadata(i, j, k) & 7;
            float f = i + 0.5f;
            float f1 = j + 0.0f + random.nextFloat() * 6.0f / 16.0f;
            float f2 = k + 0.5f;
            float f3 = 0.52f;
            float f4 = random.nextFloat() * 0.6f - 0.3f;
            if(meta == 4) {
                world.spawnParticle("smoke", f - f3, f1, f2 + f4, 0.0, 0.0, 0.0);
                world.spawnParticle("flame", f - f3, f1, f2 + f4, 0.0, 0.0, 0.0);
            }
            else if(meta == 5) {
                world.spawnParticle("smoke", f + f3, f1, f2 + f4, 0.0, 0.0, 0.0);
                world.spawnParticle("flame", f + f3, f1, f2 + f4, 0.0, 0.0, 0.0);
            }
            else if(meta == 2) {
                world.spawnParticle("smoke", f + f4, f1, f2 - f3, 0.0, 0.0, 0.0);
                world.spawnParticle("flame", f + f4, f1, f2 - f3, 0.0, 0.0, 0.0);
            }
            else if(meta == 3) {
                world.spawnParticle("smoke", f + f4, f1, f2 + f3, 0.0, 0.0, 0.0);
                world.spawnParticle("flame", f + f4, f1, f2 + f3, 0.0, 0.0, 0.0);
            }
            if(this.useLargeSmoke()) {
                for(int l = 0; l < 6; ++l) {
                    float f10 = random.nextBoolean() ? 0.0f : 1.0f;
                    float f11 = random.nextBoolean() ? 0.0f : 1.0f;
                    float f12 = 0.5f;
                    f10 += -0.1f + random.nextFloat() * 0.2f;
                    f11 += -0.1f + random.nextFloat() * 0.2f;
                    if(random.nextInt(3) > 0) {
                        world.spawnParticle("largesmoke", i + f10, j + f12, k + f11, 0.0, 0.0, 0.0);
                        continue;
                    }
                    world.spawnParticle("smoke", i + f10, j + f12, k + f11, 0.0, 0.0, 0.0);
                }
            }
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
            ((LOTRTileEntityForgeBase) world.getTileEntity(i, j, k)).setSpecialForgeName(itemstack.getDisplayName());
        }
    }

    @Override
    public int getLightValue(IBlockAccess world, int i, int j, int k) {
        return LOTRBlockForgeBase.isForgeActive(world, i, j, k) ? 13 : 0;
    }

    public static boolean isForgeActive(IBlockAccess world, int i, int j, int k) {
        int meta = world.getBlockMetadata(i, j, k);
        return (meta & 8) != 0;
    }

    public static void toggleForgeActive(World world, int i, int j, int k) {
        int meta = world.getBlockMetadata(i, j, k);
        world.setBlockMetadataWithNotify(i, j, k, meta ^ 8, 2);
        world.updateLightByType(EnumSkyBlock.Block, i, j, k);
    }

    @Override
    public void breakBlock(World world, int i, int j, int k, Block block, int meta) {
        LOTRTileEntityForgeBase forge = (LOTRTileEntityForgeBase) world.getTileEntity(i, j, k);
        if(forge != null) {
            LOTRMod.dropContainerItems(forge, world, i, j, k);
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
