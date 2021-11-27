package lotr.common.block;

import java.util.Random;

import cpw.mods.fml.relauncher.*;
import lotr.common.*;
import lotr.common.item.*;
import lotr.common.recipe.LOTRBrewingRecipes;
import lotr.common.tileentity.LOTRTileEntityBarrel;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class LOTRBlockBarrel
extends BlockContainer {
    @SideOnly(value=Side.CLIENT)
    private IIcon[] barrelIcons;

    public LOTRBlockBarrel() {
        super(Material.wood);
        this.setCreativeTab(LOTRCreativeTabs.tabFood);
        this.setBlockBounds(0.125f, 0.0f, 0.125f, 0.875f, 0.8125f, 0.875f);
        this.setHardness(3.0f);
        this.setResistance(5.0f);
        this.setStepSound(Block.soundTypeWood);
    }

    public TileEntity createNewTileEntity(World world, int i) {
        return new LOTRTileEntityBarrel();
    }

    @SideOnly(value=Side.CLIENT)
    public IIcon getIcon(int i, int j) {
        if (i == -1) {
            return this.barrelIcons[2];
        }
        if (i < 2) {
            return this.barrelIcons[1];
        }
        return this.barrelIcons[0];
    }

    @SideOnly(value=Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconregister) {
        this.barrelIcons = new IIcon[3];
        this.barrelIcons[0] = iconregister.registerIcon(this.getTextureName() + "_side");
        this.barrelIcons[1] = iconregister.registerIcon(this.getTextureName() + "_top");
        this.barrelIcons[2] = iconregister.registerIcon(this.getTextureName() + "_tap");
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public int getRenderType() {
        return LOTRMod.proxy.getBarrelRenderID();
    }

    public void onBlockPlacedBy(World world, int i, int j, int k, EntityLivingBase entity, ItemStack itemstack) {
        int rotation = MathHelper.floor_double(entity.rotationYaw * 4.0f / 360.0f + 0.5) & 3;
        int meta = 0;
        if (rotation == 0) {
            meta = 2;
        } else if (rotation == 1) {
            meta = 5;
        } else if (rotation == 2) {
            meta = 3;
        } else if (rotation == 3) {
            meta = 4;
        }
        world.setBlockMetadataWithNotify(i, j, k, meta, 2);
        if (itemstack.hasDisplayName()) {
            ((LOTRTileEntityBarrel)world.getTileEntity(i, j, k)).setBarrelName(itemstack.getDisplayName());
        }
    }

    public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float f, float f1, float f2) {
        Item item;
        LOTRTileEntityBarrel barrel = (LOTRTileEntityBarrel)world.getTileEntity(i, j, k);
        ItemStack barrelDrink = barrel.getBrewedDrink();
        ItemStack itemstack = entityplayer.inventory.getCurrentItem();
        item = itemstack == null ? null : itemstack.getItem();
        if (side == world.getBlockMetadata(i, j, k)) {
            if (barrelDrink != null && LOTRItemMug.isItemEmptyDrink(itemstack)) {
                ItemStack playerDrink = barrelDrink.copy();
                playerDrink.stackSize = 1;
                LOTRItemMug.Vessel v = LOTRItemMug.getVessel(itemstack);
                LOTRItemMug.setVessel(playerDrink, v, true);
                --itemstack.stackSize;
                if (itemstack.stackSize <= 0) {
                    entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, playerDrink);
                } else if (!entityplayer.inventory.addItemStackToInventory(playerDrink)) {
                    entityplayer.dropPlayerItemWithRandomChoice(playerDrink, false);
                }
                barrel.consumeMugRefill();
                world.playSoundEffect(i + 0.5, j + 0.5, k + 0.5, "lotr:item.mug_fill", 0.5f, 0.8f + world.rand.nextFloat() * 0.4f);
                return true;
            }
            if (itemstack != null && item instanceof LOTRItemMug && ((LOTRItemMug)item).isBrewable) {
                boolean match = false;
                if (barrel.barrelMode == 0) {
                    match = true;
                } else if (barrelDrink != null && barrelDrink.stackSize < LOTRBrewingRecipes.BARREL_CAPACITY) {
                    match = barrelDrink.getItem() == itemstack.getItem() && LOTRItemMug.getStrength(barrelDrink) == LOTRItemMug.getStrength(itemstack);
                }
                if (match) {
                    if (barrelDrink == null) {
                        ItemStack barrelFill = itemstack.copy();
                        barrelFill.stackSize = 1;
                        LOTRItemMug.setVessel(barrelFill, LOTRItemMug.Vessel.MUG, false);
                        barrel.setInventorySlotContents(9, barrelFill);
                    } else {
                        ++barrelDrink.stackSize;
                        barrel.setInventorySlotContents(9, barrelDrink);
                    }
                    barrel.barrelMode = 2;
                    if (!entityplayer.capabilities.isCreativeMode) {
                        LOTRItemMug.Vessel v = LOTRItemMug.getVessel(itemstack);
                        ItemStack emptyMug = v.getEmptyVessel();
                        --itemstack.stackSize;
                        if (itemstack.stackSize <= 0) {
                            entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, emptyMug);
                            entityplayer.openContainer.detectAndSendChanges();
                        } else if (!entityplayer.inventory.addItemStackToInventory(emptyMug)) {
                            entityplayer.dropPlayerItemWithRandomChoice(emptyMug, false);
                        }
                    }
                    world.playSoundEffect(i + 0.5, j + 0.5, k + 0.5, "lotr:item.mug_fill", 0.5f, 0.8f + world.rand.nextFloat() * 0.4f);
                    return true;
                }
            }
        }
        if (itemstack != null && item instanceof LOTRItemBottlePoison && barrel.canPoisonBarrel()) {
            if (!world.isRemote) {
                barrel.poisonBarrel(entityplayer);
                if (!entityplayer.capabilities.isCreativeMode) {
                    ItemStack containerItem = itemstack.getItem().getContainerItem(itemstack);
                    entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, containerItem);
                }
                entityplayer.openContainer.detectAndSendChanges();
                ((EntityPlayerMP)entityplayer).sendContainerToPlayer(entityplayer.openContainer);
            }
            return true;
        }
        if (!world.isRemote) {
            entityplayer.openGui(LOTRMod.instance, 16, world, i, j, k);
        }
        return true;
    }

    public void onBlockHarvested(World world, int i, int j, int k, int meta, EntityPlayer entityplayer) {
        if (entityplayer.capabilities.isCreativeMode) {
            world.setBlockMetadataWithNotify(i, j, k, meta |= 8, 4);
        }
        super.onBlockHarvested(world, i, j, k, meta, entityplayer);
    }

    public void breakBlock(World world, int i, int j, int k, Block block, int meta) {
        LOTRTileEntityBarrel barrel = (LOTRTileEntityBarrel)world.getTileEntity(i, j, k);
        if (barrel != null) {
            ItemStack brewing = barrel.getStackInSlot(9);
            barrel.setInventorySlotContents(9, null);
            LOTRMod.dropContainerItems(barrel, world, i, j, k);
            for (int slot = 0; slot < barrel.getSizeInventory(); ++slot) {
                barrel.setInventorySlotContents(slot, null);
            }
            barrel.setInventorySlotContents(9, brewing);
            if (!world.isRemote && (meta & 8) == 0) {
                this.dropBlockAsItem(world, i, j, k, this.getBarrelDrop(world, i, j, k, meta));
            }
        }
        super.breakBlock(world, i, j, k, block, meta);
    }

    public ItemStack getBarrelDrop(World world, int i, int j, int k, int metadata) {
        ItemStack itemstack = new ItemStack(Item.getItemFromBlock(this));
        LOTRTileEntityBarrel barrel = (LOTRTileEntityBarrel)world.getTileEntity(i, j, k);
        if (barrel != null && barrel.barrelMode != 0) {
            LOTRItemBarrel.setBarrelDataFromTE(itemstack, barrel);
        }
        return itemstack;
    }

    public ItemStack getPickBlock(MovingObjectPosition target, World world, int i, int j, int k) {
        world.markBlockForUpdate(i, j, k);
        return this.getBarrelDrop(world, i, j, k, world.getBlockMetadata(i, j, k));
    }

    public Item getItemDropped(int i, Random random, int j) {
        return null;
    }
}

