package lotr.common.block;

import java.util.ArrayList;

import cpw.mods.fml.relauncher.*;
import lotr.common.*;
import lotr.common.item.LOTRItemKebabStand;
import lotr.common.tileentity.LOTRTileEntityKebabStand;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class LOTRBlockKebabStand extends BlockContainer {
    private String standTextureName;

    public LOTRBlockKebabStand(String s) {
        super(Material.circuits);
        this.standTextureName = s;
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        this.setHardness(0.0f);
        this.setResistance(1.0f);
        this.setStepSound(Block.soundTypeWood);
        this.setCreativeTab(LOTRCreativeTabs.tabUtil);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int i) {
        return new LOTRTileEntityKebabStand();
    }

    public String getStandTextureName() {
        return this.standTextureName;
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister iconregister) {
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public IIcon getIcon(int i, int j) {
        return Blocks.planks.getIcon(i, 0);
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public String getItemIconName() {
        return this.getTextureName();
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public int getRenderType() {
        return -1;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k) {
        return null;
    }

    @Override
    public boolean canBlockStay(World world, int i, int j, int k) {
        return true;
    }

    @Override
    public void onNeighborBlockChange(World world, int i, int j, int k, Block block) {
        if(!this.canBlockStay(world, i, j, k)) {
            int meta = world.getBlockMetadata(i, j, k);
            this.dropBlockAsItem(world, i, j, k, meta, 0);
            world.setBlockToAir(i, j, k);
        }
    }

    @Override
    public void onBlockHarvested(World world, int i, int j, int k, int meta, EntityPlayer entityplayer) {
        if(entityplayer.capabilities.isCreativeMode) {
            world.setBlockMetadataWithNotify(i, j, k, meta |= 8, 4);
        }
        this.dropBlockAsItem(world, i, j, k, meta, 0);
        super.onBlockHarvested(world, i, j, k, meta, entityplayer);
    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int i, int j, int k, int meta, int fortune) {
        ArrayList<ItemStack> drops = new ArrayList<>();
        if((meta & 8) == 0) {
            ItemStack itemstack = this.getKebabStandDrop(world, i, j, k, meta);
            LOTRTileEntityKebabStand kebabStand = (LOTRTileEntityKebabStand) world.getTileEntity(i, j, k);
            if(kebabStand != null) {
                drops.add(itemstack);
            }
        }
        return drops;
    }

    public ItemStack getKebabStandDrop(World world, int i, int j, int k, int metadata) {
        ItemStack itemstack = new ItemStack(Item.getItemFromBlock(this));
        LOTRTileEntityKebabStand kebabStand = (LOTRTileEntityKebabStand) world.getTileEntity(i, j, k);
        if(kebabStand != null) {
            LOTRItemKebabStand.setKebabData(itemstack, kebabStand);
        }
        return itemstack;
    }

    @Override
    public ItemStack getPickBlock(MovingObjectPosition target, World world, int i, int j, int k) {
        world.markBlockForUpdate(i, j, k);
        return this.getKebabStandDrop(world, i, j, k, world.getBlockMetadata(i, j, k));
    }

    @Override
    public void onBlockPlacedBy(World world, int i, int j, int k, EntityLivingBase entity, ItemStack itemstack) {
        int rotation = MathHelper.floor_double(entity.rotationYaw * 4.0f / 360.0f + 0.5) & 3;
        int meta = 0;
        if(rotation == 0) {
            meta = 2;
        }
        else if(rotation == 1) {
            meta = 5;
        }
        else if(rotation == 2) {
            meta = 3;
        }
        else if(rotation == 3) {
            meta = 4;
        }
        world.setBlockMetadataWithNotify(i, j, k, meta, 2);
        TileEntity tileentity = world.getTileEntity(i, j, k);
        if(tileentity instanceof LOTRTileEntityKebabStand) {
            LOTRTileEntityKebabStand kebabStand = (LOTRTileEntityKebabStand) tileentity;
            LOTRItemKebabStand.loadKebabData(itemstack, kebabStand);
            kebabStand.onReplaced();
        }
    }

    @Override
    public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float f, float f1, float f2) {
        TileEntity tileentity = world.getTileEntity(i, j, k);
        if(tileentity instanceof LOTRTileEntityKebabStand) {
            LOTRTileEntityKebabStand stand = (LOTRTileEntityKebabStand) tileentity;
            ItemStack heldItem = entityplayer.getHeldItem();
            if(!stand.isCooked() && stand.isMeat(heldItem)) {
                if(stand.hasEmptyMeatSlot()) {
                    if(!world.isRemote && stand.addMeat(heldItem) && !entityplayer.capabilities.isCreativeMode) {
                        --heldItem.stackSize;
                    }
                    return true;
                }
            }
            else if(stand.getMeatAmount() > 0) {
                if(!world.isRemote) {
                    boolean wasCooked = stand.isCooked();
                    ItemStack meat = stand.removeFirstMeat();
                    if(meat != null) {
                        if(!entityplayer.inventory.addItemStackToInventory(meat)) {
                            this.dropBlockAsItem(world, i, j, k, meat);
                        }
                        entityplayer.inventoryContainer.detectAndSendChanges();
                        world.playSoundEffect(i + 0.5, j + 0.5, k + 0.5, "random.pop", 0.5f, 0.5f + world.rand.nextFloat() * 0.5f);
                        if(wasCooked) {
                            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.cookKebab);
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }
}
