package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.item.LOTRItemBanner;
import net.minecraft.world.World;

public class LOTRWorldGenGulfTotem extends LOTRWorldGenGulfStructure {
    public LOTRWorldGenGulfTotem(boolean flag) {
        super(flag);
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 2);
        this.setupRandomBlocks(random);
        if(this.restrictions) {
            for(int i1 = -3; i1 <= 3; ++i1) {
                for(int k1 = -3; k1 <= 3; ++k1) {
                    int j1 = this.getTopBlock(world, i1, k1) - 1;
                    if(this.isSurface(world, i1, j1, k1)) continue;
                    return false;
                }
            }
        }
        this.loadStrScan("gulf_totem");
        this.associateBlockMetaAlias("WOOD", this.woodBlock, this.woodMeta);
        this.associateBlockMetaAlias("FENCE", this.fenceBlock, this.fenceMeta);
        this.associateBlockMetaAlias("FLAG", this.flagBlock, this.flagMeta);
        this.associateBlockMetaAlias("BONE", this.boneBlock, this.boneMeta);
        this.generateStrScan(world, random, 0, 0, 0);
        this.placeWallBanner(world, 0, 6, -5, LOTRItemBanner.BannerType.HARAD_GULF, 2);
        this.placeWallBanner(world, 0, 6, 5, LOTRItemBanner.BannerType.HARAD_GULF, 0);
        this.placeWallBanner(world, -6, 8, 0, LOTRItemBanner.BannerType.HARAD_GULF, 3);
        this.placeWallBanner(world, 6, 8, 0, LOTRItemBanner.BannerType.HARAD_GULF, 1);
        return true;
    }
}
