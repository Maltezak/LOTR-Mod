package lotr.common.block;

import cpw.mods.fml.relauncher.*;
import lotr.common.LOTRMod;
import net.minecraft.util.IIcon;

public class LOTRBlockWall3 extends LOTRBlockWallBase {
    public LOTRBlockWall3() {
        super(LOTRMod.brick, 16);
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public IIcon getIcon(int i, int j) {
        if(j == 0) {
            return LOTRMod.brick3.getIcon(i, 5);
        }
        if(j == 1) {
            return LOTRMod.brick3.getIcon(i, 6);
        }
        if(j == 2) {
            return LOTRMod.brick3.getIcon(i, 7);
        }
        if(j == 3) {
            return LOTRMod.brick3.getIcon(i, 11);
        }
        if(j == 4) {
            return LOTRMod.brick3.getIcon(i, 13);
        }
        if(j == 5) {
            return LOTRMod.brick3.getIcon(i, 14);
        }
        if(j == 6) {
            return LOTRMod.rock.getIcon(i, 5);
        }
        if(j == 7) {
            return LOTRMod.brick4.getIcon(i, 15);
        }
        if(j == 8) {
            return LOTRMod.brick5.getIcon(i, 0);
        }
        if(j == 9) {
            return LOTRMod.brick5.getIcon(i, 1);
        }
        if(j == 10) {
            return LOTRMod.brick5.getIcon(i, 2);
        }
        if(j == 11) {
            return LOTRMod.brick5.getIcon(i, 4);
        }
        if(j == 12) {
            return LOTRMod.brick5.getIcon(i, 5);
        }
        if(j == 13) {
            return LOTRMod.brick5.getIcon(i, 7);
        }
        if(j == 14) {
            return LOTRMod.whiteSandstone.getIcon(i, 0);
        }
        if(j == 15) {
            return LOTRMod.brick5.getIcon(i, 11);
        }
        return super.getIcon(i, j);
    }
}
