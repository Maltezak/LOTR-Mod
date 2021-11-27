package lotr.common.block;

import java.util.Random;

import cpw.mods.fml.relauncher.*;
import lotr.common.*;
import lotr.common.item.LOTRItemMatch;
import lotr.common.tileentity.LOTRTileEntityBeacon;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.*;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTRBlockBeacon
extends BlockContainer {
    public LOTRBlockBeacon() {
        super(Material.wood);
        this.setCreativeTab(LOTRCreativeTabs.tabUtil);
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.8125f, 1.0f);
        this.setHardness(0.0f);
        this.setResistance(5.0f);
        this.setStepSound(Block.soundTypeWood);
    }

    @SideOnly(value=Side.CLIENT)
    public IIcon getIcon(int i, int j) {
        return Blocks.planks.getIcon(i, 0);
    }

    @SideOnly(value=Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconregister) {
    }

    public TileEntity createNewTileEntity(World world, int i) {
        return new LOTRTileEntityBeacon();
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

    public int getRenderType() {
        return LOTRMod.proxy.getBeaconRenderID();
    }

    public boolean canBlockStay(World world, int i, int j, int k) {
        return world.getBlock(i, j - 1, k).isSideSolid(world, i, j - 1, k, ForgeDirection.UP);
    }

    public boolean canPlaceBlockAt(World world, int i, int j, int k) {
        return this.canBlockStay(world, i, j, k);
    }

    public void onNeighborBlockChange(World world, int i, int j, int k, Block block) {
        if (!this.canBlockStay(world, i, j, k)) {
            this.dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k), 0);
            world.setBlockToAir(i, j, k);
        } else if (LOTRBlockBeacon.isLit(world, i, j, k) && world.getBlock(i, j + 1, k).getMaterial() == Material.water) {
            world.playSoundEffect(i + 0.5, j + 0.5, k + 0.5, "random.fizz", 0.5f, 2.6f + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8f);
            if (!world.isRemote) {
                LOTRBlockBeacon.setLit(world, i, j, k, false);
            }
        }
    }

    public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float f, float f1, float f2) {
        ItemStack itemstack = entityplayer.getCurrentEquippedItem();
        if (this.canItemLightBeacon(itemstack) && !LOTRBlockBeacon.isLit(world, i, j, k) && world.getBlock(i, j + 1, k).getMaterial() != Material.water) {
            world.playSoundEffect(i + 0.5, j + 0.5, k + 0.5, "fire.ignite", 1.0f, world.rand.nextFloat() * 0.4f + 0.8f);
            if (!entityplayer.capabilities.isCreativeMode) {
                if (itemstack.getItem().isDamageable()) {
                    itemstack.damageItem(1, entityplayer);
                } else if (itemstack.getMaxStackSize() > 1) {
                    --itemstack.stackSize;
                    if (itemstack.stackSize <= 0) {
                        entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
                    }
                }
            }
            if (!world.isRemote) {
                LOTRBlockBeacon.setLit(world, i, j, k, true);
                LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.lightGondorBeacon);
            }
            return true;
        }
        if (itemstack != null && itemstack.getItem() == Items.water_bucket && LOTRBlockBeacon.isLit(world, i, j, k)) {
            world.playSoundEffect(i + 0.5, j + 0.5, k + 0.5, "random.fizz", 0.5f, 2.6f + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8f);
            if (!entityplayer.capabilities.isCreativeMode) {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, new ItemStack(Items.bucket));
            }
            if (!world.isRemote) {
                LOTRBlockBeacon.setLit(world, i, j, k, false);
            }
            return true;
        }
        entityplayer.openGui(LOTRMod.instance, 50, world, i, j, k);
        return true;
    }

    private boolean canItemLightBeacon(ItemStack itemstack) {
        if (itemstack == null) {
            return false;
        }
        Item item = itemstack.getItem();
        return item == Items.flint_and_steel || item instanceof LOTRItemMatch || item instanceof ItemBlock && ((ItemBlock)item).field_150939_a instanceof BlockTorch;
    }

    public int getLightValue(IBlockAccess world, int i, int j, int k) {
        return LOTRBlockBeacon.isFullyLit(world, i, j, k) ? 15 : 0;
    }

    public static boolean isLit(IBlockAccess world, int i, int j, int k) {
        TileEntity tileentity = world.getTileEntity(i, j, k);
        if (tileentity instanceof LOTRTileEntityBeacon) {
            LOTRTileEntityBeacon beacon = (LOTRTileEntityBeacon)tileentity;
            return beacon.isLit();
        }
        return false;
    }

    public static boolean isFullyLit(IBlockAccess world, int i, int j, int k) {
        TileEntity tileentity = world.getTileEntity(i, j, k);
        if (tileentity instanceof LOTRTileEntityBeacon) {
            LOTRTileEntityBeacon beacon = (LOTRTileEntityBeacon)tileentity;
            return beacon.isFullyLit();
        }
        return false;
    }

    public static void setLit(World world, int i, int j, int k, boolean lit) {
        TileEntity tileentity = world.getTileEntity(i, j, k);
        if (tileentity instanceof LOTRTileEntityBeacon) {
            LOTRTileEntityBeacon beacon = (LOTRTileEntityBeacon)tileentity;
            beacon.setLit(lit);
        }
    }

    public void onEntityCollidedWithBlock(World world, int i, int j, int k, Entity entity) {
        if (entity.isBurning() && !LOTRBlockBeacon.isLit(world, i, j, k) && world.getBlock(i, j + 1, k).getMaterial() != Material.water) {
            world.playSoundEffect(i + 0.5, j + 0.5, k + 0.5, "fire.ignite", 1.0f, world.rand.nextFloat() * 0.4f + 0.8f);
            if (!world.isRemote) {
                LOTRBlockBeacon.setLit(world, i, j, k, true);
                entity.setDead();
            }
        }
    }

    @SideOnly(value=Side.CLIENT)
    public void randomDisplayTick(World world, int i, int j, int k, Random random) {
        if (!LOTRBlockBeacon.isLit(world, i, j, k)) {
            return;
        }
        if (random.nextInt(24) == 0) {
            world.playSound(i + 0.5, j + 0.5, k + 0.5, "fire.fire", 1.0f + random.nextFloat(), random.nextFloat() * 0.7f + 0.3f, false);
        }
        for (int l = 0; l < 3; ++l) {
            double d = i + random.nextFloat();
            double d1 = j + random.nextFloat() * 0.5 + 0.5;
            double d2 = k + random.nextFloat();
            world.spawnParticle("largesmoke", d, d1, d2, 0.0, 0.0, 0.0);
        }
    }
}

