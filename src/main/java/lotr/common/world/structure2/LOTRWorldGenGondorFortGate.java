package lotr.common.world.structure2;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenGondorFortGate extends LOTRWorldGenGondorStructure {
    public LOTRWorldGenGondorFortGate(boolean flag) {
        super(flag);
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 0);
        this.setupRandomBlocks(random);
        this.findSurface(world, 0, 0);
        int gateX = this.originX;
        int gateY = this.originY;
        int gateZ = this.originZ;
        for(int i1 = -4; i1 <= 4; ++i1) {
            int j1;
            int i2 = Math.abs(i1);
            int k1 = 0;
            if(i2 <= 1) {
                this.originX = gateX;
                this.originY = gateY;
                this.originZ = gateZ;
                for(j1 = 0; (((j1 >= 0) || !this.isOpaque(world, i1, j1, k1)) && (this.getY(j1) >= 0)); --j1) {
                    this.setBlockAndMetadata(world, i1, j1, k1, this.brickBlock, this.brickMeta);
                    this.setGrassToDirt(world, i1, j1 - 1, k1);
                }
                for(j1 = 1; j1 <= 4; ++j1) {
                    this.setBlockAndMetadata(world, i1, j1, k1, this.gateBlock, 2);
                }
                if(i1 == -1) {
                    this.setBlockAndMetadata(world, i1, 5, k1, this.brickStairBlock, 4);
                }
                if(i1 == 0) {
                    this.setBlockAndMetadata(world, i1, 5, k1, this.brickStairBlock, 6);
                }
                if(i1 == 1) {
                    this.setBlockAndMetadata(world, i1, 5, k1, this.brickStairBlock, 5);
                }
                this.setBlockAndMetadata(world, i1, 6, k1, this.brick2Block, this.brick2Meta);
                if(i2 == 0) {
                    this.setBlockAndMetadata(world, i1, 7, k1, this.brick2SlabBlock, this.brick2SlabMeta);
                }
                this.setBlockAndMetadata(world, i1, 5, 1, this.rockSlabBlock, this.rockSlabMeta);
            }
            if(i2 == 2) {
                this.findSurface(world, i1, k1);
                for(j1 = 5; (((j1 >= 1) || !this.isOpaque(world, i1, j1, k1)) && (this.getY(j1) >= 0)); --j1) {
                    this.setBlockAndMetadata(world, i1, j1, k1, this.pillarBlock, this.pillarMeta);
                    this.setGrassToDirt(world, i1, j1 - 1, k1);
                }
                this.setBlockAndMetadata(world, i1, 6, k1, this.brick2SlabBlock, this.brick2SlabMeta);
                this.setBlockAndMetadata(world, i1, 4, 1, this.rockSlabBlock, this.rockSlabMeta | 8);
            }
            if(i2 < 3) continue;
            this.findSurface(world, i1, k1);
            for(j1 = 3; (((j1 >= 1) || !this.isOpaque(world, i1, j1, k1)) && (this.getY(j1) >= 0)); --j1) {
                this.setBlockAndMetadata(world, i1, j1, k1, this.brickBlock, this.brickMeta);
                this.setGrassToDirt(world, i1, j1 - 1, k1);
            }
            this.setBlockAndMetadata(world, i1, 4, k1, this.brickStairBlock, 6);
            this.setBlockAndMetadata(world, i1, 5, k1, this.brick2WallBlock, this.brick2WallMeta);
            this.setBlockAndMetadata(world, i1, 4, 1, this.rockSlabBlock, this.rockSlabMeta | 8);
            if(i2 != 4) continue;
            for(j1 = 3; (((j1 >= 1) || !this.isOpaque(world, i1, j1, 1)) && (this.getY(j1) >= 0)); --j1) {
                this.setBlockAndMetadata(world, i1, j1, 1, this.brickBlock, this.brickMeta);
                this.setGrassToDirt(world, i1, j1 - 1, 1);
            }
            this.setBlockAndMetadata(world, i1, 4, 1, this.rockSlabDoubleBlock, this.rockSlabDoubleMeta);
            j1 = 4;
            while(!this.isOpaque(world, i1, j1, 2) && this.getY(j1) >= 0) {
                this.setBlockAndMetadata(world, i1, j1, 2, Blocks.ladder, 3);
                --j1;
            }
        }
        return true;
    }
}
