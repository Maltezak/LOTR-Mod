package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.LOTRMod;

public class LOTRWorldGenDolGuldurForgeTent extends LOTRWorldGenDolGuldurTent {
    public LOTRWorldGenDolGuldurForgeTent(boolean flag) {
        super(flag);
    }

    @Override
    protected void setupRandomBlocks(Random random) {
        super.setupRandomBlocks(random);
        this.tentBlock = LOTRMod.brick2;
        this.tentMeta = 8;
        this.fenceBlock = LOTRMod.wall2;
        this.fenceMeta = 8;
        this.hasOrcForge = true;
    }
}
