package lotr.common.world.structure2;

import java.util.Random;

import net.minecraft.init.Blocks;

public class LOTRWorldGenGundabadForgeTent extends LOTRWorldGenGundabadTent {
    public LOTRWorldGenGundabadForgeTent(boolean flag) {
        super(flag);
    }

    @Override
    protected void setupRandomBlocks(Random random) {
        super.setupRandomBlocks(random);
        this.tentBlock = Blocks.cobblestone;
        this.tentMeta = 0;
        this.fenceBlock = Blocks.cobblestone_wall;
        this.fenceMeta = 0;
        this.hasOrcForge = true;
    }
}
