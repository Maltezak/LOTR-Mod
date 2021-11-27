package lotr.common.world;

import cpw.mods.fml.relauncher.*;
import net.minecraft.world.WorldType;

public class LOTRWorldTypeMiddleEarth extends WorldType {
    public LOTRWorldTypeMiddleEarth(String name) {
        super(name);
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public boolean showWorldInfoNotice() {
        return true;
    }
}
