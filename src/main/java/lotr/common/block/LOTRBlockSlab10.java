package lotr.common.block;

import cpw.mods.fml.relauncher.*;
import lotr.common.LOTRMod;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class LOTRBlockSlab10 extends LOTRBlockSlabBase {
    public LOTRBlockSlab10(boolean flag) {
        super(flag, Material.rock, 8);
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public IIcon getIcon(int i, int j) {
        if((j &= 7) == 0) {
            return LOTRMod.pillar2.getIcon(i, 5);
        }
        if(j == 1) {
            return LOTRMod.brick5.getIcon(i, 4);
        }
        if(j == 2) {
            return LOTRMod.brick5.getIcon(i, 5);
        }
        if(j == 3) {
            return LOTRMod.brick5.getIcon(i, 7);
        }
        if(j == 4) {
            return LOTRMod.pillar2.getIcon(i, 6);
        }
        if(j == 5) {
            return LOTRMod.pillar2.getIcon(i, 7);
        }
        if(j == 6) {
            return LOTRMod.whiteSandstone.getIcon(i, 0);
        }
        if(j == 7) {
            return LOTRMod.rock.getIcon(i, 0);
        }
        return super.getIcon(i, j);
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister iconregister) {
    }
}
