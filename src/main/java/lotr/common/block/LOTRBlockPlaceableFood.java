package lotr.common.block;

import java.util.*;

import cpw.mods.fml.relauncher.*;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTRBlockPlaceableFood extends Block {
    @SideOnly(value = Side.CLIENT)
    private IIcon iconBottom;
    @SideOnly(value = Side.CLIENT)
    private IIcon iconTop;
    @SideOnly(value = Side.CLIENT)
    private IIcon iconSide;
    @SideOnly(value = Side.CLIENT)
    private IIcon iconEaten;
    public Item foodItem;
    private float foodHalfWidth;
    private float foodHeight;
    private static int MAX_EATS = 6;
    private int healAmount;
    private float saturationAmount;

    public LOTRBlockPlaceableFood() {
        this(0.4375f, 0.5f);
    }

    public LOTRBlockPlaceableFood(float f, float f1) {
        super(Material.cake);
        this.foodHalfWidth = f;
        this.foodHeight = f1;
        this.setHardness(0.5f);
        this.setStepSound(Block.soundTypeCloth);
        this.setTickRandomly(true);
        this.setFoodStats(2, 0.1f);
    }

    public LOTRBlockPlaceableFood setFoodStats(int i, float f) {
        this.healAmount = i;
        this.saturationAmount = f;
        return this;
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public IIcon getIcon(int i, int j) {
        if(i == 0) {
            return this.iconBottom;
        }
        if(i == 1) {
            return this.iconTop;
        }
        if(j > 0 && i == 4) {
            return this.iconEaten;
        }
        return this.iconSide;
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister iconregister) {
        this.iconBottom = iconregister.registerIcon(this.getTextureName() + "_bottom");
        this.iconTop = iconregister.registerIcon(this.getTextureName() + "_top");
        this.iconSide = iconregister.registerIcon(this.getTextureName() + "_side");
        this.iconEaten = iconregister.registerIcon(this.getTextureName() + "_inner");
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, int i, int j, int k) {
        world.getBlockMetadata(i, j, k);
        float f = 0.5f - this.foodHalfWidth;
        float f1 = 0.5f + this.foodHalfWidth;
        float f2 = f + (f1 - f) * ((float) world.getBlockMetadata(i, j, k) / (float) MAX_EATS);
        this.setBlockBounds(f2, 0.0f, f, f1, this.foodHeight, f1);
    }

    @Override
    public void setBlockBoundsForItemRender() {
        float f = 0.5f - this.foodHalfWidth;
        float f1 = 0.5f + this.foodHalfWidth;
        this.setBlockBounds(f, 0.0f, f, f1, this.foodHeight, f1);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k) {
        float f = 0.5f - this.foodHalfWidth;
        float f1 = 0.5f + this.foodHalfWidth;
        float f2 = f + (f1 - f) * ((float) world.getBlockMetadata(i, j, k) / (float) MAX_EATS);
        return AxisAlignedBB.getBoundingBox(i + f2, j, k + f, i + f1, j + this.foodHeight, k + f1);
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int i, int j, int k) {
        return this.getCollisionBoundingBoxFromPool(world, i, j, k);
    }

    @Override
    public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float f, float f1, float f2) {
        this.eatCake(world, i, j, k, entityplayer);
        return true;
    }

    private void eatCake(World world, int i, int j, int k, EntityPlayer entityplayer) {
        if(!world.isRemote && entityplayer.canEat(false)) {
            entityplayer.getFoodStats().addStats(this.healAmount, this.saturationAmount);
            entityplayer.playSound("random.burp", 0.5f, world.rand.nextFloat() * 0.1f + 0.9f);
            int meta = world.getBlockMetadata(i, j, k);
            if(++meta >= MAX_EATS) {
                world.setBlockToAir(i, j, k);
            }
            else {
                world.setBlockMetadataWithNotify(i, j, k, meta, 3);
            }
        }
    }

    @Override
    public boolean canPlaceBlockAt(World world, int i, int j, int k) {
        return super.canPlaceBlockAt(world, i, j, k) && this.canBlockStay(world, i, j, k);
    }

    @Override
    public boolean canBlockStay(World world, int i, int j, int k) {
        return world.getBlock(i, j - 1, k).isSideSolid(world, i, j - 1, k, ForgeDirection.UP);
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
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public int quantityDropped(Random random) {
        return 0;
    }

    @Override
    public Item getItemDropped(int i, Random random, int j) {
        return null;
    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int i, int j, int k, int meta, int fortune) {
        ArrayList<ItemStack> drops = new ArrayList<>();
        if(meta == 0) {
            if(this.foodItem != null) {
                drops.add(new ItemStack(this.foodItem));
            }
            else {
                drops.add(new ItemStack(this, 1, 0));
            }
        }
        return drops;
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public Item getItem(World world, int i, int j, int k) {
        if(this.foodItem != null) {
            return this.foodItem;
        }
        return Item.getItemFromBlock(this);
    }
}
