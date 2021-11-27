package lotr.common.block;

import cpw.mods.fml.relauncher.*;
import lotr.common.LOTRMod;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class LOTRBlockSlab12 extends LOTRBlockSlabBase {
    public LOTRBlockSlab12(boolean flag) {
        super(flag, Material.rock, 8);
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public IIcon getIcon(int i, int j) {
        if((j &= 7) == 0) {
            return LOTRMod.brick5.getIcon(i, 11);
        }
        if(j == 1) {
            return LOTRMod.brick5.getIcon(i, 13);
        }
        if(j == 2) {
            return LOTRMod.brick5.getIcon(i, 14);
        }
        if(j == 3) {
            return LOTRMod.brick5.getIcon(i, 15);
        }
        if(j == 4) {
            return LOTRMod.pillar2.getIcon(i, 8);
        }
        if(j == 5) {
            return LOTRMod.brick6.getIcon(i, 1);
        }
        if(j == 6) {
            return LOTRMod.pillar2.getIcon(i, 9);
        }
        if(j == 7) {
            return LOTRMod.brick6.getIcon(i, 10);
        }
        return super.getIcon(i, j);
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister iconregister) {
    }
}
