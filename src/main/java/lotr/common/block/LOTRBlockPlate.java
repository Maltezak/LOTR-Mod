package lotr.common.block;

import java.util.Random;

import cpw.mods.fml.relauncher.*;
import lotr.common.LOTRMod;
import lotr.common.tileentity.LOTRTileEntityPlate;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTRBlockPlate extends BlockContainer {
    public static final Block.SoundType soundTypePlate = new Block.SoundType("lotr:plate", 1.0f, 1.0f) {
        private Random rand = new Random();

        @Override
        public float getPitch() {
            return super.getPitch();
        }

        @Override
        public String getBreakSound() {
            return "lotr:block.plate.break";
        }

        @Override
        public String getStepResourcePath() {
            return Block.soundTypeStone.getStepResourcePath();
        }

        @Override
        public String func_150496_b() {
            return Block.soundTypeStone.func_150496_b();
        }
    };
    @SideOnly(value = Side.CLIENT)
    private IIcon[] plateIcons;
    private Item plateItem;

    public LOTRBlockPlate() {
        super(Material.circuits);
        this.setHardness(0.0f);
        this.setBlockBounds(0.125f, 0.0f, 0.125f, 0.875f, 0.125f, 0.875f);
    }

    public void setPlateItem(Item item) {
        this.plateItem = item;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int i) {
        return new LOTRTileEntityPlate();
    }

    @Override
    public Item getItemDropped(int i, Random random, int j) {
        return this.plateItem;
    }

    @Override
    public ItemStack getPickBlock(MovingObjectPosition target, World world, int i, int j, int k, EntityPlayer entityplayer) {
        ItemStack foodItem = LOTRBlockPlate.getFoodItem(world, i, j, k);
        if(foodItem != null) {
            ItemStack copy = foodItem.copy();
            copy.stackSize = 1;
            return copy;
        }
        int meta = world.getBlockMetadata(i, j, k);
        return new ItemStack(this.getItemDropped(meta, world.rand, 0), 1, this.damageDropped(meta));
    }

    @Override
    public void breakBlock(World world, int i, int j, int k, Block block, int meta) {
        ItemStack foodItem;
        TileEntity tileentity = world.getTileEntity(i, j, k);
        if(!world.isRemote && tileentity instanceof LOTRTileEntityPlate && (foodItem = ((LOTRTileEntityPlate) tileentity).getFoodItem()) != null) {
            this.dropBlockAsItem(world, i, j, k, foodItem);
        }
        super.breakBlock(world, i, j, k, block, meta);
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
        return LOTRMod.proxy.getPlateRenderID();
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public IIcon getIcon(int i, int j) {
        return i == 1 ? this.plateIcons[0] : this.plateIcons[1];
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister iconregister) {
        this.plateIcons = new IIcon[2];
        this.plateIcons[0] = iconregister.registerIcon(this.getTextureName() + "_top");
        this.plateIcons[1] = iconregister.registerIcon(this.getTextureName() + "_base");
    }

    @Override
    public boolean canBlockStay(World world, int i, int j, int k) {
        return world.getBlock(i, j - 1, k).isSideSolid(world, i, j - 1, k, ForgeDirection.UP);
    }

    @Override
    public boolean canPlaceBlockAt(World world, int i, int j, int k) {
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
    public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float f, float f1, float f2) {
        ItemStack itemstack = entityplayer.getCurrentEquippedItem();
        TileEntity tileentity = world.getTileEntity(i, j, k);
        if(tileentity instanceof LOTRTileEntityPlate) {
            LOTRTileEntityPlate plate = (LOTRTileEntityPlate) tileentity;
            ItemStack plateItem = plate.getFoodItem();
            if(plateItem == null && LOTRTileEntityPlate.isValidFoodItem(itemstack)) {
                if(!world.isRemote) {
                    plateItem = itemstack.copy();
                    plateItem.stackSize = 1;
                    plate.setFoodItem(plateItem);
                }
                if(!entityplayer.capabilities.isCreativeMode) {
                    --itemstack.stackSize;
                }
                return true;
            }
            if(plateItem != null) {
                if(itemstack != null && itemstack.isItemEqual(plateItem) && ItemStack.areItemStackTagsEqual(itemstack, plateItem)) {
                    if(plateItem.stackSize < plateItem.getMaxStackSize()) {
                        if(!world.isRemote) {
                            ++plateItem.stackSize;
                            plate.setFoodItem(plateItem);
                        }
                        if(!entityplayer.capabilities.isCreativeMode) {
                            --itemstack.stackSize;
                        }
                        return true;
                    }
                }
                else if(entityplayer.canEat(false)) {
                    plateItem.getItem().onEaten(plateItem, world, entityplayer);
                    if(!world.isRemote) {
                        plate.setFoodItem(plateItem);
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public static ItemStack getFoodItem(World world, int i, int j, int k) {
        TileEntity tileentity = world.getTileEntity(i, j, k);
        if(tileentity instanceof LOTRTileEntityPlate) {
            return ((LOTRTileEntityPlate) tileentity).getFoodItem();
        }
        return null;
    }

    public void dropPlateItem(LOTRTileEntityPlate plate) {
        this.dropPlateItem(plate, plate.getFoodItem());
    }

    public void dropOnePlateItem(LOTRTileEntityPlate plate) {
        ItemStack item = plate.getFoodItem().copy();
        item.stackSize = 1;
        this.dropPlateItem(plate, item);
    }

    private void dropPlateItem(LOTRTileEntityPlate plate, ItemStack itemstack) {
        this.dropBlockAsItem(plate.getWorldObj(), plate.xCoord, plate.yCoord, plate.zCoord, itemstack);
    }

}
