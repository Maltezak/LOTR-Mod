package lotr.common.world.structure2;

import lotr.common.LOTRMod;

public class LOTRWorldGenRottenHouse extends LOTRWorldGenRuinedHouse {
    public LOTRWorldGenRottenHouse(boolean flag) {
        super(flag);
        this.woodBlock = LOTRMod.rottenLog;
        this.woodMeta = 0;
        this.plankBlock = LOTRMod.planksRotten;
        this.plankMeta = 0;
        this.fenceBlock = LOTRMod.fenceRotten;
        this.fenceMeta = 0;
        this.stairBlock = LOTRMod.stairsRotten;
    }
}
