package lotr.common.block;

import cpw.mods.fml.relauncher.*;
import lotr.common.LOTRMod;
import lotr.common.world.LOTRWorldProviderUtumno;
import net.minecraft.util.IIcon;

public class LOTRBlockUtumnoWall extends LOTRBlockWallBase implements LOTRWorldProviderUtumno.UtumnoBlock {
    public LOTRBlockUtumnoWall() {
        super(LOTRMod.utumnoBrick, 6);
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public IIcon getIcon(int i, int j) {
        if(j == 0) {
            return LOTRMod.utumnoBrick.getIcon(i, 0);
        }
        if(j == 1) {
            return LOTRMod.utumnoBrick.getIcon(i, 2);
        }
        if(j == 2) {
            return LOTRMod.utumnoBrick.getIcon(i, 4);
        }
        if(j == 3) {
            return LOTRMod.utumnoBrick.getIcon(i, 6);
        }
        if(j == 4) {
            return LOTRMod.utumnoBrick.getIcon(i, 7);
        }
        if(j == 5) {
            return LOTRMod.utumnoBrick.getIcon(i, 8);
        }
        return super.getIcon(i, j);
    }
}
