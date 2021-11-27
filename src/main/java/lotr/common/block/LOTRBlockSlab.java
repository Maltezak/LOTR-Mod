package lotr.common.block;

import cpw.mods.fml.relauncher.*;
import lotr.common.LOTRMod;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class LOTRBlockSlab extends LOTRBlockSlabBase {
    public LOTRBlockSlab(boolean flag) {
        super(flag, Material.rock, 8);
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public IIcon getIcon(int i, int j) {
        if((j &= 7) == 0) {
            return LOTRMod.smoothStone.getIcon(i, 0);
        }
        if(j == 1) {
            return LOTRMod.brick.getIcon(i, 0);
        }
        if(j == 2) {
            return LOTRMod.smoothStone.getIcon(i, 1);
        }
        if(j == 3) {
            return LOTRMod.brick.getIcon(i, 1);
        }
        if(j == 4) {
            return LOTRMod.brick.getIcon(i, 2);
        }
        if(j == 5) {
            return LOTRMod.brick.getIcon(i, 3);
        }
        if(j == 6) {
            return LOTRMod.brick.getIcon(i, 4);
        }
        if(j == 7) {
            return LOTRMod.brick.getIcon(i, 6);
        }
        return super.getIcon(i, j);
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister iconregister) {
    }
}
