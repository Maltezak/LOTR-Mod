package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.LOTRMod;

public class LOTRWorldGenUrukForgeTent extends LOTRWorldGenUrukTent {
    public LOTRWorldGenUrukForgeTent(boolean flag) {
        super(flag);
    }

    @Override
    protected void setupRandomBlocks(Random random) {
        super.setupRandomBlocks(random);
        this.tentBlock = LOTRMod.brick2;
        this.tentMeta = 7;
        this.fenceBlock = LOTRMod.wall2;
        this.fenceMeta = 7;
        this.hasOrcForge = true;
    }
}
