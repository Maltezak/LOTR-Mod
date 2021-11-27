package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.entity.npc.LOTREntityRohirrimWarrior;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenRohanGatehouse extends LOTRWorldGenRohanStructure {
    public LOTRWorldGenRohanGatehouse(boolean flag) {
        super(flag);
    }

    @Override
    protected boolean oneWoodType() {
        return true;
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int k1;
        int i1;
        int j1;
        this.setOriginAndRotation(world, i, j, k, rotation, 2);
        this.setupRandomBlocks(random);
        if(this.restrictions) {
            for(i1 = -9; i1 <= 9; ++i1) {
                for(k1 = -2; k1 <= 2; ++k1) {
                    int j12 = this.getTopBlock(world, i1, k1) - 1;
                    if(this.isSurface(world, i1, j12, k1)) continue;
                    return false;
                }
            }
        }
        for(i1 = -9; i1 <= 9; ++i1) {
            for(k1 = -2; k1 <= 2; ++k1) {
                int i2 = Math.abs(i1);
                int k2 = Math.abs(k1);
                if(i2 >= 3 || k2 <= 1) {
                    for(j1 = 0; (((j1 >= 0) || !this.isOpaque(world, i1, j1, k1)) && (this.getY(j1) >= 0)); --j1) {
                        this.setBlockAndMetadata(world, i1, j1, k1, this.rockSlabDoubleBlock, this.rockSlabDoubleMeta);
                        this.setGrassToDirt(world, i1, j1 - 1, k1);
                    }
                    for(j1 = 1; j1 <= 12; ++j1) {
                        this.setAir(world, i1, j1, k1);
                    }
                }
                if(i2 == 2 && k2 == 0) {
                    for(j1 = 1; j1 <= 4; ++j1) {
                        this.setBlockAndMetadata(world, i1, j1, k1, this.woodBeamBlock, this.woodBeamMeta);
                    }
                }
                if(i2 == 2 && k2 == 1) {
                    for(j1 = 1; j1 <= 3; ++j1) {
                        this.setBlockAndMetadata(world, i1, j1, k1, this.fenceBlock, this.fenceMeta);
                    }
                    this.setBlockAndMetadata(world, i1, 4, k1, this.plankBlock, this.plankMeta);
                }
                if(i2 == 1 && k2 == 1) {
                    this.setBlockAndMetadata(world, i1, 4, k1, this.plankSlabBlock, this.plankSlabMeta | 8);
                }
                if(i2 >= 3 && i2 <= 9 && k2 <= 2) {
                    this.setBlockAndMetadata(world, i1, 1, k1, this.rockSlabDoubleBlock, this.rockSlabDoubleMeta);
                    for(j1 = 2; j1 <= 4; ++j1) {
                        this.setBlockAndMetadata(world, i1, j1, k1, this.brickBlock, this.brickMeta);
                    }
                }
                if((i2 == 4 || i2 == 8) && k2 == 2) {
                    for(j1 = 2; j1 <= 8; ++j1) {
                        this.setBlockAndMetadata(world, i1, j1, k1, this.woodBeamBlock, this.woodBeamMeta);
                    }
                    this.setBlockAndMetadata(world, i1, 9, k1, this.plankBlock, this.plankMeta);
                }
                if(i2 >= 5 && i2 <= 7 && k2 == 2 || i2 == 4 | i2 == 8 && k2 <= 1) {
                    this.setBlockAndMetadata(world, i1, 9, k1, this.plankSlabBlock, this.plankSlabMeta | 8);
                }
                if(i2 == 9 && k2 <= 1) {
                    for(j1 = 2; j1 <= 5; ++j1) {
                        this.setBlockAndMetadata(world, i1, j1, k1, this.woodBeamBlock, this.woodBeamMeta);
                    }
                    this.setBlockAndMetadata(world, i1, 6, k1, this.fenceBlock, this.fenceMeta);
                }
                if(i2 >= 5 && i2 <= 7 && k2 == 2) {
                    this.setBlockAndMetadata(world, i1, 4, k1, this.woodBeamBlock, this.woodBeamMeta | 8);
                    this.setBlockAndMetadata(world, i1, 5, k1, this.woodBeamBlock, this.woodBeamMeta);
                    if(k1 < 0) {
                        this.setBlockAndMetadata(world, i1, 6, k1, this.fenceBlock, this.fenceMeta);
                    }
                }
                if(i2 == 3 && k2 == 2) {
                    this.setBlockAndMetadata(world, i1, 2, k1, this.brickWallBlock, this.brickWallMeta);
                    this.setBlockAndMetadata(world, i1, 3, k1, this.fenceBlock, this.fenceMeta);
                    this.setBlockAndMetadata(world, i1, 4, k1, this.brickWallBlock, this.brickWallMeta);
                }
                if(i2 == 9 && k2 == 2) {
                    this.setBlockAndMetadata(world, i1, 2, k1, this.brickWallBlock, this.brickWallMeta);
                    for(j1 = 3; j1 <= 6; ++j1) {
                        this.setBlockAndMetadata(world, i1, j1, k1, this.fenceBlock, this.fenceMeta);
                    }
                }
                if(i2 <= 8 && k2 <= 1) {
                    this.setBlockAndMetadata(world, i1, 5, k1, this.plankBlock, this.plankMeta);
                }
                if(i2 > 3 || k2 != 2) continue;
                if(i2 == 3) {
                    this.setBlockAndMetadata(world, i1, 5, k1, this.plankBlock, this.plankMeta);
                }
                else {
                    this.setBlockAndMetadata(world, i1, 5, k1, this.plankSlabBlock, this.plankSlabMeta | 8);
                }
                this.setBlockAndMetadata(world, i1, 6, k1, this.fenceBlock, this.fenceMeta);
            }
        }
        for(i1 = -1; i1 <= 1; ++i1) {
            for(int j13 = 1; j13 <= 5; ++j13) {
                this.setBlockAndMetadata(world, i1, j13, 0, this.gateBlock, 2);
            }
        }
        this.setBlockAndMetadata(world, 0, 6, 0, this.fenceBlock, this.fenceMeta);
        for(int i12 : new int[] {-1, 1}) {
            this.setBlockAndMetadata(world, i12, 6, 0, this.woodBeamBlock, this.woodBeamMeta);
            this.setBlockAndMetadata(world, i12, 7, 0, Blocks.lever, 13);
        }
        for(int i12 : new int[] {-3, 3}) {
            this.placeWallBanner(world, i12, 5, -2, this.bannerType, 2);
        }
        for(int i12 : new int[] {-2, 2}) {
            this.setBlockAndMetadata(world, i12, 4, -2, Blocks.torch, 4);
            this.setBlockAndMetadata(world, i12, 4, 2, Blocks.torch, 3);
        }
        for(int k12 : new int[] {-2, 2}) {
            this.setBlockAndMetadata(world, -9, 8, k12, Blocks.torch, 1);
            this.setBlockAndMetadata(world, -3, 8, k12, Blocks.torch, 2);
            this.setBlockAndMetadata(world, 3, 8, k12, Blocks.torch, 1);
            this.setBlockAndMetadata(world, 9, 8, k12, Blocks.torch, 2);
        }
        for(int i12 : new int[] {-6, 6}) {
            for(int k13 = -3; k13 <= 3; ++k13) {
                int k2 = Math.abs(k13);
                if(k2 == 3) {
                    this.setBlockAndMetadata(world, i12 - 1, 10, k13, this.roofSlabBlock, this.roofSlabMeta);
                    this.setBlockAndMetadata(world, i12, 10, k13, this.roofSlabBlock, this.roofSlabMeta);
                    this.setBlockAndMetadata(world, i12 + 1, 10, k13, this.roofSlabBlock, this.roofSlabMeta);
                }
                if(k2 == 2) {
                    this.setBlockAndMetadata(world, i12 - 2, 10, k13, this.roofSlabBlock, this.roofSlabMeta);
                    this.setBlockAndMetadata(world, i12 - 1, 10, k13, this.roofBlock, this.roofMeta);
                    this.setBlockAndMetadata(world, i12, 10, k13, this.roofBlock, this.roofMeta);
                    this.setBlockAndMetadata(world, i12 + 1, 10, k13, this.roofBlock, this.roofMeta);
                    this.setBlockAndMetadata(world, i12 + 2, 10, k13, this.roofSlabBlock, this.roofSlabMeta);
                }
                if(k2 > 1) continue;
                this.setBlockAndMetadata(world, i12 - 3, 10, k13, this.roofSlabBlock, this.roofSlabMeta);
                this.setBlockAndMetadata(world, i12 - 2, 10, k13, this.roofBlock, this.roofMeta);
                this.setBlockAndMetadata(world, i12 - 1, 10, k13, this.roofSlabBlock, this.roofSlabMeta | 8);
                this.setBlockAndMetadata(world, i12 - 1, 11, k13, this.roofSlabBlock, this.roofSlabMeta);
                this.setBlockAndMetadata(world, i12, 10, k13, this.roofSlabBlock, this.roofSlabMeta | 8);
                this.setBlockAndMetadata(world, i12, 11, k13, this.roofSlabBlock, this.roofSlabMeta);
                this.setBlockAndMetadata(world, i12 + 1, 10, k13, this.roofSlabBlock, this.roofSlabMeta | 8);
                this.setBlockAndMetadata(world, i12 + 1, 11, k13, this.roofSlabBlock, this.roofSlabMeta);
                this.setBlockAndMetadata(world, i12 + 2, 10, k13, this.roofBlock, this.roofMeta);
                this.setBlockAndMetadata(world, i12 + 3, 10, k13, this.roofSlabBlock, this.roofSlabMeta);
            }
        }
        for(int i12 : new int[] {-3, 3}) {
            j1 = 6;
            int k14 = 0;
            LOTREntityRohirrimWarrior guard = new LOTREntityRohirrimWarrior(world);
            this.spawnNPCAndSetHome(guard, world, i12, j1, k14, 8);
        }
        for(int k15 = 3; k15 <= 4; ++k15) {
            int i12;
            int step;
            int j2;
            int maxStep = 16;
            for(step = 0; step < maxStep && !this.isOpaque(world, i12 = -6 - step, j1 = 5 - (step <= 1 ? 0 : step - 2), k15); ++step) {
                if(step <= 1) {
                    this.setBlockAndMetadata(world, i12, j1, k15, this.plankBlock, this.plankMeta);
                }
                else {
                    this.setBlockAndMetadata(world, i12, j1, k15, this.plankStairBlock, 1);
                }
                this.setGrassToDirt(world, i12, j1 - 1, k15);
                j2 = j1 - 1;
                while(!this.isOpaque(world, i12, j2, k15) && this.getY(j2) >= 0) {
                    this.setBlockAndMetadata(world, i12, j2, k15, this.plankBlock, this.plankMeta);
                    this.setGrassToDirt(world, i12, j2 - 1, k15);
                    --j2;
                }
            }
            for(step = 0; step < maxStep && !this.isOpaque(world, i12 = 6 + step, j1 = 5 - (step <= 1 ? 0 : step - 2), k15); ++step) {
                if(step <= 1) {
                    this.setBlockAndMetadata(world, i12, j1, k15, this.plankBlock, this.plankMeta);
                }
                else {
                    this.setBlockAndMetadata(world, i12, j1, k15, this.plankStairBlock, 0);
                }
                this.setGrassToDirt(world, i12, j1 - 1, k15);
                j2 = j1 - 1;
                while(!this.isOpaque(world, i12, j2, k15) && this.getY(j2) >= 0) {
                    this.setBlockAndMetadata(world, i12, j2, k15, this.plankBlock, this.plankMeta);
                    this.setGrassToDirt(world, i12, j2 - 1, k15);
                    --j2;
                }
            }
        }
        return true;
    }
}
