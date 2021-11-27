package lotr.common.world.structure2;

import lotr.common.LOTRMod;

public class LOTRWorldGenBurntHouse extends LOTRWorldGenRuinedHouse {
    public LOTRWorldGenBurntHouse(boolean flag) {
        super(flag);
        this.woodBlock = LOTRMod.wood;
        this.woodMeta = 3;
        this.plankBlock = LOTRMod.planks;
        this.plankMeta = 3;
        this.fenceBlock = LOTRMod.fence;
        this.fenceMeta = 3;
        this.stairBlock = LOTRMod.stairsCharred;
        this.stoneBlock = LOTRMod.scorchedStone;
        this.stoneMeta = 0;
        this.stoneVariantBlock = LOTRMod.scorchedStone;
        this.stoneVariantMeta = 0;
    }
}
