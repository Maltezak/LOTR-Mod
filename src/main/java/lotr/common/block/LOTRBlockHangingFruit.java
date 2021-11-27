package lotr.common.block;

import java.util.Random;

import cpw.mods.fml.relauncher.*;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class LOTRBlockHangingFruit extends Block {
    @SideOnly(value = Side.CLIENT)
    private IIcon[] fruitIcons;
    private String[] fruitSides = new String[] {"top", "side", "bottom"};

    public LOTRBlockHangingFruit() {
        super(Material.plants);
        this.setTickRandomly(true);
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public IIcon getIcon(int i, int j) {
        if(i == 0) {
            return this.fruitIcons[2];
        }
        if(i == 1) {
            return this.fruitIcons[0];
        }
        return this.fruitIcons[1];
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister iconregister) {
        this.fruitIcons = new IIcon[3];
        for(int i = 0; i < 3; ++i) {
            this.fruitIcons[i] = iconregister.registerIcon(this.getTextureName() + "_" + this.fruitSides[i]);
        }
    }

    @Override
    public void updateTick(World world, int i, int j, int k, Random random) {
        if(!this.canBlockStay(world, i, j, k)) {
            this.dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k), 0);
            world.setBlockToAir(i, j, k);
        }
    }

    @Override
    public void onNeighborBlockChange(World world, int i, int j, int k, Block block) {
        if(!this.canBlockStay(world, i, j, k)) {
            this.dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k), 0);
            world.setBlockToAir(i, j, k);
        }
    }

    @Override
    public boolean canBlockStay(World world, int i, int j, int k) {
        int l = world.getBlockMetadata(i, j, k);
        ForgeDirection dir = ForgeDirection.getOrientation(l + 2);
        Block block = world.getBlock(i + dir.offsetX, j, k + dir.offsetZ);
        return block.isWood(world, i + dir.offsetX, j, k + dir.offsetZ);
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
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k) {
        this.setBlockBoundsBasedOnState(world, i, j, k);
        return super.getCollisionBoundingBoxFromPool(world, i, j, k);
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int i, int j, int k) {
        this.setBlockBoundsBasedOnState(world, i, j, k);
        return super.getSelectedBoundingBoxFromPool(world, i, j, k);
    }
}
