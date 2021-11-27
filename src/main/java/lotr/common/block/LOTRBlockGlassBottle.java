package lotr.common.block;

import cpw.mods.fml.relauncher.*;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;

public class LOTRBlockGlassBottle extends LOTRBlockMug {
    public LOTRBlockGlassBottle() {
        super(3.0f, 10.0f);
        this.setStepSound(Block.soundTypeGlass);
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public IIcon getIcon(int i, int j) {
        return Blocks.glass.getIcon(i, 0);
    }
}
