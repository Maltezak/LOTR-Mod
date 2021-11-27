package lotr.common.tileentity;

import cpw.mods.fml.relauncher.*;
import lotr.common.block.LOTRBlockSignCarved;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.*;

public class LOTRTileEntitySignCarved
extends LOTRTileEntitySign {
    @Override
    public int getNumLines() {
        return 8;
    }

    public IIcon getOnBlockIcon() {
        World world = this.getWorldObj();
        Block block = this.getBlockType();
        if (block instanceof LOTRBlockSignCarved) {
            LOTRBlockSignCarved signBlock = (LOTRBlockSignCarved)block;
            int meta = this.getBlockMetadata();
            int i = this.xCoord;
            int j = this.yCoord;
            int k = this.zCoord;
            int onSide = meta;
            return signBlock.getOnBlockIcon(world, i, j, k, onSide);
        }
        return Blocks.stone.getIcon(0, 0);
    }

    @SideOnly(value=Side.CLIENT)
    public double getMaxRenderDistanceSquared() {
        return 1600.0;
    }
}

