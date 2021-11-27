package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.LOTRMod;

public class LOTRWorldGenAngmarForgeTent extends LOTRWorldGenAngmarTent {
    public LOTRWorldGenAngmarForgeTent(boolean flag) {
        super(flag);
    }

    @Override
    protected void setupRandomBlocks(Random random) {
        super.setupRandomBlocks(random);
        this.tentBlock = LOTRMod.brick2;
        this.tentMeta = 0;
        this.fenceBlock = LOTRMod.wall2;
        this.fenceMeta = 0;
        this.hasOrcForge = true;
    }
}
