package lotr.common.block;

import cpw.mods.fml.relauncher.*;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;

public class LOTRBlockSlabDirt extends LOTRBlockSlabBase {
    public LOTRBlockSlabDirt(boolean flag) {
        super(flag, Material.ground, 5);
        this.setHardness(0.5f);
        this.setStepSound(Block.soundTypeGravel);
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public IIcon getIcon(int i, int j) {
        if((j &= 7) == 0) {
            return Blocks.dirt.getIcon(i, 0);
        }
        if(j == 1) {
            return LOTRMod.dirtPath.getIcon(i, 0);
        }
        if(j == 2) {
            return LOTRMod.mud.getIcon(i, 0);
        }
        if(j == 3) {
            return LOTRMod.mordorDirt.getIcon(i, 0);
        }
        if(j == 4) {
            return LOTRMod.dirtPath.getIcon(i, 1);
        }
        return super.getIcon(i, j);
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister iconregister) {
    }
}
