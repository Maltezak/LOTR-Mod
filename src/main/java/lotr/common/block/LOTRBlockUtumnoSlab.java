package lotr.common.block;

import cpw.mods.fml.relauncher.*;
import lotr.common.LOTRMod;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class LOTRBlockUtumnoSlab extends LOTRBlockUtumnoSlabBase {
    public LOTRBlockUtumnoSlab(boolean flag) {
        super(flag, 6);
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public IIcon getIcon(int i, int j) {
        if((j &= 7) == 0) {
            return LOTRMod.utumnoBrick.getIcon(i, 0);
        }
        if(j == 1) {
            return LOTRMod.utumnoBrick.getIcon(i, 2);
        }
        if(j == 2) {
            return LOTRMod.utumnoBrick.getIcon(i, 4);
        }
        if(j == 3) {
            return LOTRMod.utumnoPillar.getIcon(i, 0);
        }
        if(j == 4) {
            return LOTRMod.utumnoPillar.getIcon(i, 1);
        }
        if(j == 5) {
            return LOTRMod.utumnoPillar.getIcon(i, 2);
        }
        return super.getIcon(i, j);
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister iconregister) {
    }
}
