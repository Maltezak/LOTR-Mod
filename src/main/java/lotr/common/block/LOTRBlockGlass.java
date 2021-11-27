package lotr.common.block;

import cpw.mods.fml.relauncher.*;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.Facing;
import net.minecraft.world.*;

public class LOTRBlockGlass extends BlockGlass {
    private boolean thirdParam = false;

    public LOTRBlockGlass() {
        super(Material.glass, false);
        this.setHardness(0.3f);
        this.setStepSound(Block.soundTypeGlass);
        this.setCreativeTab(LOTRCreativeTabs.tabBlock);
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public boolean shouldSideBeRendered(IBlockAccess world, int i, int j, int k, int side) {
        Block block = world.getBlock(i, j, k);
        if(world.getBlockMetadata(i, j, k) != world.getBlockMetadata(i - Facing.offsetsXForSide[side], j - Facing.offsetsYForSide[side], k - Facing.offsetsZForSide[side])) {
            return true;
        }
        if(block == this) {
            return false;
        }
        return !this.thirdParam && block == this ? false : super.shouldSideBeRendered(world, i, j, k, side);
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister iconregister) {
        this.blockIcon = iconregister.registerIcon(this.getTextureName());
    }

    @Override
    public boolean canPlaceTorchOnTop(World world, int i, int j, int k) {
        return true;
    }
}
