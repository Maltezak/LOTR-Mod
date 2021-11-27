package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.item.LOTRItemBanner;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenGulfTownWall extends LOTRWorldGenGulfStructure {
    private boolean isTall = false;

    public LOTRWorldGenGulfTownWall(boolean flag) {
        super(flag);
    }

    public void setTall() {
        this.isTall = true;
    }

    @Override
    protected boolean canUseRedBrick() {
        return false;
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int j1;
        int k1;
        int i1;
        this.setOriginAndRotation(world, i, j, k, rotation, 0);
        this.setupRandomBlocks(random);
        if(this.restrictions && !this.isSurface(world, i1 = 0, this.getTopBlock(world, i1, k1 = 0) - 1, k1)) {
            return false;
        }
        for(j1 = 1; (((j1 >= 0) || !this.isOpaque(world, 0, j1, 0)) && (this.getY(j1) >= 0)); --j1) {
            if(random.nextBoolean()) {
                this.setBlockAndMetadata(world, 0, j1, 0, Blocks.sandstone, 0);
            }
            else {
                this.setBlockAndMetadata(world, 0, j1, 0, this.brickBlock, this.brickMeta);
            }
            this.setGrassToDirt(world, 0, j1 - 1, 0);
        }
        for(j1 = 2; j1 <= 4; ++j1) {
            if(random.nextBoolean()) {
                this.setBlockAndMetadata(world, 0, j1, 0, Blocks.sandstone, 0);
                continue;
            }
            this.setBlockAndMetadata(world, 0, j1, 0, this.brickBlock, this.brickMeta);
        }
        if(this.isTall) {
            for(j1 = 5; j1 <= 6; ++j1) {
                this.setBlockAndMetadata(world, 0, j1, 0, this.boneWallBlock, this.boneWallMeta);
            }
            this.setBlockAndMetadata(world, 0, 7, 0, this.boneBlock, this.boneMeta);
            this.placeWallBanner(world, 0, 7, 0, LOTRItemBanner.BannerType.HARAD_GULF, 2);
        }
        else {
            this.setBlockAndMetadata(world, 0, 5, 0, this.fenceBlock, this.fenceMeta);
        }
        return true;
    }
}
