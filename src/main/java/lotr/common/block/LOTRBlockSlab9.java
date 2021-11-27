package lotr.common.block;

import cpw.mods.fml.relauncher.*;
import lotr.common.LOTRMod;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class LOTRBlockSlab9 extends LOTRBlockSlabBase {
    public LOTRBlockSlab9(boolean flag) {
        super(flag, Material.rock, 8);
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public IIcon getIcon(int i, int j) {
        if((j &= 7) == 0) {
            return LOTRMod.brick4.getIcon(i, 15);
        }
        if(j == 1) {
            return LOTRMod.pillar2.getIcon(i, 1);
        }
        if(j == 2) {
            return LOTRMod.pillar2.getIcon(i, 2);
        }
        if(j == 3) {
            return LOTRMod.pillar2.getIcon(i, 3);
        }
        if(j == 4) {
            return LOTRMod.pillar2.getIcon(i, 4);
        }
        if(j == 5) {
            return LOTRMod.brick5.getIcon(i, 0);
        }
        if(j == 6) {
            return LOTRMod.brick5.getIcon(i, 1);
        }
        if(j == 7) {
            return LOTRMod.brick5.getIcon(i, 2);
        }
        return super.getIcon(i, j);
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister iconregister) {
    }
}
