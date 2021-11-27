package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.LOTRMod;

public class LOTRWorldGenMordorForgeTent extends LOTRWorldGenMordorTent {
    public LOTRWorldGenMordorForgeTent(boolean flag) {
        super(flag);
    }

    @Override
    protected void setupRandomBlocks(Random random) {
        super.setupRandomBlocks(random);
        this.tentBlock = LOTRMod.brick;
        this.tentMeta = 0;
        this.fenceBlock = LOTRMod.wall;
        this.fenceMeta = 1;
        this.hasOrcForge = true;
    }
}
