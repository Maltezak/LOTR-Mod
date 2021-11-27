package lotr.common.world.structure2;

import java.util.Random;

import net.minecraft.world.World;

public class LOTRWorldGenSouthronFortCorner extends LOTRWorldGenSouthronStructure {
    public LOTRWorldGenSouthronFortCorner(boolean flag) {
        super(flag);
    }

    @Override
    protected boolean canUseRedBricks() {
        return false;
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int j1;
        boolean beam;
        int j12;
        this.setOriginAndRotation(world, i, j, k, rotation, 0);
        this.setupRandomBlocks(random);
        for(int i1 = -4; i1 <= 1; ++i1) {
            int i2 = Math.abs(i1);
            int k1 = 0;
            this.findSurface(world, i1, k1);
            if(i1 <= 0) {
                beam = i2 % 4 == 0;
                if(beam) {
                    for(j12 = 6; (((j12 >= 1) || !this.isOpaque(world, i1, j12, k1)) && (this.getY(j12) >= 0)); --j12) {
                        this.setBlockAndMetadata(world, i1, j12, k1, this.woodBeamBlock, this.woodBeamMeta);
                        this.setGrassToDirt(world, i1, j12 - 1, k1);
                    }
                }
                else {
                    for(j12 = 5; (((j12 >= 1) || !this.isOpaque(world, i1, j12, k1)) && (this.getY(j12) >= 0)); --j12) {
                        this.setBlockAndMetadata(world, i1, j12, k1, this.plankBlock, this.plankMeta);
                        this.setGrassToDirt(world, i1, j12 - 1, k1);
                    }
                    if(i2 % 2 == 1) {
                        this.setBlockAndMetadata(world, i1, 5, k1, this.plankStairBlock, 2);
                    }
                    else {
                        this.setBlockAndMetadata(world, i1, 6, k1, this.fenceBlock, this.fenceMeta);
                    }
                }
                if(i1 <= -1) {
                    int k3 = k1 + 1;
                    this.setBlockAndMetadata(world, i1, 2, k3, this.brickStairBlock, 3);
                    for(j1 = 1; (((j1 >= 1) || !this.isOpaque(world, i1, j1, k3)) && (this.getY(j1) >= 0)); --j1) {
                        this.setBlockAndMetadata(world, i1, j1, k3, this.brickBlock, this.brickMeta);
                        this.setGrassToDirt(world, i1, j1 - 1, k3);
                    }
                }
            }
            if(i1 > 1) continue;
            int k3 = k1 - 1;
            this.setBlockAndMetadata(world, i1, 2, k3, this.brickStairBlock, 2);
            for(j12 = 1; (((j12 >= 1) || !this.isOpaque(world, i1, j12, k3)) && (this.getY(j12) >= 0)); --j12) {
                this.setBlockAndMetadata(world, i1, j12, k3, this.brickBlock, this.brickMeta);
                this.setGrassToDirt(world, i1, j12 - 1, k3);
            }
        }
        for(int k1 = 0; k1 <= 4; ++k1) {
            int i1 = 0;
            int k2 = Math.abs(k1);
            this.findSurface(world, i1, k1);
            if(k1 >= 1) {
                beam = k2 % 4 == 0;
                if(beam) {
                    for(j12 = 6; (((j12 >= 1) || !this.isOpaque(world, i1, j12, k1)) && (this.getY(j12) >= 0)); --j12) {
                        this.setBlockAndMetadata(world, i1, j12, k1, this.woodBeamBlock, this.woodBeamMeta);
                        this.setGrassToDirt(world, i1, j12 - 1, k1);
                    }
                }
                else {
                    for(j12 = 5; (((j12 >= 1) || !this.isOpaque(world, i1, j12, k1)) && (this.getY(j12) >= 0)); --j12) {
                        this.setBlockAndMetadata(world, i1, j12, k1, this.plankBlock, this.plankMeta);
                        this.setGrassToDirt(world, i1, j12 - 1, k1);
                    }
                    if(k2 % 2 == 1) {
                        this.setBlockAndMetadata(world, i1, 5, k1, this.plankStairBlock, 0);
                    }
                    else {
                        this.setBlockAndMetadata(world, i1, 6, k1, this.fenceBlock, this.fenceMeta);
                    }
                }
                if(k1 >= 2) {
                    int i3 = i1 - 1;
                    this.setBlockAndMetadata(world, i3, 2, k1, this.brickStairBlock, 1);
                    for(j1 = 1; (((j1 >= 1) || !this.isOpaque(world, i3, j1, k1)) && (this.getY(j1) >= 0)); --j1) {
                        this.setBlockAndMetadata(world, i3, j1, k1, this.brickBlock, this.brickMeta);
                        this.setGrassToDirt(world, i3, j1 - 1, k1);
                    }
                }
            }
            if(k1 < 0) continue;
            int i3 = i1 + 1;
            this.setBlockAndMetadata(world, i3, 2, k1, this.brickStairBlock, 0);
            for(j12 = 1; (((j12 >= 1) || !this.isOpaque(world, i3, j12, k1)) && (this.getY(j12) >= 0)); --j12) {
                this.setBlockAndMetadata(world, i3, j12, k1, this.brickBlock, this.brickMeta);
                this.setGrassToDirt(world, i3, j12 - 1, k1);
            }
        }
        return true;
    }
}
