package lotr.common.world.structure2;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenGulfVillageLight extends LOTRWorldGenGulfStructure {
    public LOTRWorldGenGulfVillageLight(boolean flag) {
        super(flag);
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int k1;
        int i1;
        this.setOriginAndRotation(world, i, j, k, rotation, 0);
        this.setupRandomBlocks(random);
        if(this.restrictions && !this.isSurface(world, i1 = 0, this.getTopBlock(world, i1, k1 = 0) - 1, k1)) {
            return false;
        }
        for(int j12 = 0; (((j12 >= 0) || !this.isOpaque(world, 0, j12, 0)) && (this.getY(j12) >= 0)); --j12) {
            this.setBlockAndMetadata(world, 0, j12, 0, this.woodBlock, this.woodMeta);
            this.setGrassToDirt(world, 0, j12 - 1, 0);
        }
        this.setBlockAndMetadata(world, 0, 1, 0, this.woodBlock, this.woodMeta);
        this.setBlockAndMetadata(world, 0, 2, 0, this.boneWallBlock, this.boneWallMeta);
        this.setBlockAndMetadata(world, 0, 3, 0, this.boneWallBlock, this.boneWallMeta);
        this.setBlockAndMetadata(world, 0, 4, 0, this.boneWallBlock, this.boneWallMeta);
        this.placeSkull(world, 0, 5, 0, 0);
        this.setBlockAndMetadata(world, 1, 4, 0, this.fenceBlock, this.fenceMeta);
        this.setBlockAndMetadata(world, 1, 5, 0, Blocks.torch, 5);
        this.setBlockAndMetadata(world, -1, 4, 0, this.fenceBlock, this.fenceMeta);
        this.setBlockAndMetadata(world, -1, 5, 0, Blocks.torch, 5);
        this.setBlockAndMetadata(world, 0, 4, -1, this.fenceBlock, this.fenceMeta);
        this.setBlockAndMetadata(world, 0, 5, -1, Blocks.torch, 5);
        this.setBlockAndMetadata(world, 0, 4, 1, this.fenceBlock, this.fenceMeta);
        this.setBlockAndMetadata(world, 0, 5, 1, Blocks.torch, 5);
        return true;
    }
}
