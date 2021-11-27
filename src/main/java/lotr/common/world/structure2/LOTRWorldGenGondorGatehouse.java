package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntities;
import lotr.common.entity.npc.*;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenGondorGatehouse extends LOTRWorldGenGondorStructure {
    private String[] signText = LOTRNames.getGondorVillageName(new Random());

    public LOTRWorldGenGondorGatehouse(boolean flag) {
        super(flag);
    }

    public LOTRWorldGenGondorGatehouse setSignText(String[] s) {
        this.signText = s;
        return this;
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int j1;
        int i1;
        int i12;
        int i13;
        int j12;
        int i142;
        int k1;
        int i2;
        int k12;
        int i22;
        int k2;
        int k13;
        int j13;
        this.setOriginAndRotation(world, i, j, k, rotation, 4);
        this.setupRandomBlocks(random);
        if(this.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for(int i15 = -6; i15 <= 6; ++i15) {
                for(int k14 = -5; k14 <= 5; ++k14) {
                    j12 = this.getTopBlock(world, i15, k14) - 1;
                    if(!this.isSurface(world, i15, j12, k14)) {
                        return false;
                    }
                    if(j12 < minHeight) {
                        minHeight = j12;
                    }
                    if(j12 > maxHeight) {
                        maxHeight = j12;
                    }
                    if(maxHeight - minHeight <= 8) continue;
                    return false;
                }
            }
        }
        for(i13 = -3; i13 <= 3; ++i13) {
            for(k1 = -3; k1 <= 3; ++k1) {
                i22 = Math.abs(i13);
                k2 = Math.abs(k1);
                for(j12 = 0; (((j12 >= 0) || !this.isOpaque(world, i13, j12, k1)) && (this.getY(j12) >= 0)); --j12) {
                    this.setBlockAndMetadata(world, i13, j12, k1, this.cobbleBlock, this.cobbleMeta);
                    this.setGrassToDirt(world, i13, j12 - 1, k1);
                }
                for(j12 = 1; j12 <= 14; ++j12) {
                    this.setAir(world, i13, j12, k1);
                }
                if(i22 == 3 && k2 == 3) {
                    for(j12 = 1; j12 <= 10; ++j12) {
                        this.setBlockAndMetadata(world, i13, j12, k1, this.pillarBlock, this.pillarMeta);
                    }
                }
                else {
                    if(i22 == 3) {
                        for(j12 = 1; j12 <= 6; ++j12) {
                            this.setBlockAndMetadata(world, i13, j12, k1, this.brickBlock, this.brickMeta);
                        }
                    }
                    this.setBlockAndMetadata(world, i13, 7, k1, this.brickBlock, this.brickMeta);
                }
                if(i22 > 3 || k2 > 3) continue;
                if(i22 == 3 || k2 == 3) {
                    this.setBlockAndMetadata(world, i13, 11, k1, this.rockSlabDoubleBlock, this.rockSlabDoubleMeta);
                    continue;
                }
                this.setBlockAndMetadata(world, i13, 11, k1, this.rockSlabBlock, this.rockSlabMeta | 8);
            }
        }
        for(i13 = -2; i13 <= 2; ++i13) {
            this.setBlockAndMetadata(world, i13, 0, -1, this.cobbleStairBlock, 3);
            this.setBlockAndMetadata(world, i13, 0, 1, this.cobbleStairBlock, 2);
            this.setBlockAndMetadata(world, i13, -1, 0, this.cobbleBlock, this.cobbleMeta);
            this.setGrassToDirt(world, i13, -2, 0);
            this.setAir(world, i13, 0, 0);
        }
        for(i13 = -2; i13 <= 2; ++i13) {
            for(j1 = 1; j1 <= 7; ++j1) {
                if(j1 > 6 && i13 != 0) continue;
                this.setBlockAndMetadata(world, i13, j1, -1, this.gateBlock, 10);
                this.setBlockAndMetadata(world, i13, j1, 1, LOTRMod.gateIronBars, 10);
            }
        }
        for(int k14 : new int[] {-3, 3}) {
            this.setBlockAndMetadata(world, -2, 6, k14, this.brickStairBlock, 4);
            this.setBlockAndMetadata(world, 2, 6, k14, this.brickStairBlock, 5);
            this.setBlockAndMetadata(world, -2, 5, k14, Blocks.torch, 2);
            this.setBlockAndMetadata(world, 2, 5, k14, Blocks.torch, 1);
            for(int i16 = -2; i16 <= 2; ++i16) {
                int i23 = Math.abs(i16);
                this.setBlockAndMetadata(world, i16, 8, k14, this.rockSlabDoubleBlock, this.rockSlabDoubleMeta);
                if(i23 % 2 == 0) {
                    this.setBlockAndMetadata(world, i16, 9, k14, LOTRMod.gateIronBars, 2);
                }
                else {
                    this.setBlockAndMetadata(world, i16, 9, k14, this.brickBlock, this.brickMeta);
                }
                if(i23 == 0) {
                    this.setBlockAndMetadata(world, i16, 10, k14, LOTRMod.brick, 5);
                    continue;
                }
                this.setBlockAndMetadata(world, i16, 10, k14, this.brickBlock, this.brickMeta);
            }
        }
        int[] i17 = new int[] {-3, 3};
        j1 = i17.length;
        for(i22 = 0; i22 < j1; ++i22) {
            i142 = i17[i22];
            for(int k15 : new int[] {-2, 2}) {
                this.setBlockAndMetadata(world, i142, 8, k15, this.rockSlabDoubleBlock, this.rockSlabDoubleMeta);
                this.setBlockAndMetadata(world, i142, 9, k15, this.brickBlock, this.brickMeta);
                this.setBlockAndMetadata(world, i142, 10, k15, this.brickBlock, this.brickMeta);
            }
            this.setBlockAndMetadata(world, i142, 10, -1, this.brickStairBlock, 7);
            this.setBlockAndMetadata(world, i142, 10, 1, this.brickStairBlock, 6);
        }
        for(i1 = -3; i1 <= 3; ++i1) {
            this.setBlockAndMetadata(world, i1, 11, -4, this.brickStairBlock, 6);
            this.setBlockAndMetadata(world, i1, 11, 4, this.brickStairBlock, 7);
        }
        for(int k16 = -3; k16 <= 3; ++k16) {
            this.setBlockAndMetadata(world, -4, 11, k16, this.brickStairBlock, 5);
            this.setBlockAndMetadata(world, 4, 11, k16, this.brickStairBlock, 4);
        }
        for(i1 = -4; i1 <= 4; ++i1) {
            for(k1 = -4; k1 <= 4; ++k1) {
                i22 = Math.abs(i1);
                k2 = Math.abs(k1);
                if(i22 > 3 && k2 > 3 || i22 != 4 && k2 != 4) continue;
                if((i22 + k2) % 2 == 1) {
                    this.setBlockAndMetadata(world, i1, 12, k1, this.brickBlock, this.brickMeta);
                    this.setBlockAndMetadata(world, i1, 13, k1, this.brickSlabBlock, this.brickSlabMeta);
                    continue;
                }
                this.setBlockAndMetadata(world, i1, 12, k1, this.brickWallBlock, this.brickWallMeta);
            }
        }
        this.setBlockAndMetadata(world, 0, 8, -1, this.plankBlock, this.plankMeta);
        this.setBlockAndMetadata(world, 0, 8, 0, this.fenceBlock, this.fenceMeta);
        this.setAir(world, 0, 7, 0);
        this.setBlockAndMetadata(world, 0, 8, 1, this.plankBlock, this.plankMeta);
        this.setBlockAndMetadata(world, 0, 9, -1, Blocks.lever, 14);
        this.setBlockAndMetadata(world, 0, 9, 1, Blocks.lever, 14);
        for(int i1421 : new int[] {-1, 1}) {
            for(j12 = 8; j12 <= 11; ++j12) {
                this.setBlockAndMetadata(world, i1421, j12, 2, Blocks.ladder, 2);
            }
        }
        this.setBlockAndMetadata(world, -2, 10, -2, Blocks.torch, 2);
        this.setBlockAndMetadata(world, 2, 10, -2, Blocks.torch, 1);
        this.setBlockAndMetadata(world, -2, 10, 2, Blocks.torch, 2);
        this.setBlockAndMetadata(world, 2, 10, 2, Blocks.torch, 1);
        this.placeWallBanner(world, 1, 10, -3, this.bannerType2, 0);
        this.placeWallBanner(world, -1, 10, -3, this.bannerType, 0);
        this.setBlockAndMetadata(world, -3, 12, -3, Blocks.torch, 3);
        this.setBlockAndMetadata(world, 3, 12, -3, Blocks.torch, 3);
        this.setBlockAndMetadata(world, -3, 12, 3, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 3, 12, 3, Blocks.torch, 4);
        this.placeWallBanner(world, -3, 7, -3, this.bannerType2, 2);
        this.placeWallBanner(world, 3, 7, -3, this.bannerType, 2);
        this.placeWallBanner(world, 3, 7, 3, this.bannerType2, 0);
        this.placeWallBanner(world, -3, 7, 3, this.bannerType, 0);
        if(this.signText != null) {
            this.placeSign(world, -3, 3, -4, Blocks.wall_sign, 2, this.signText);
            this.placeSign(world, 3, 3, -4, Blocks.wall_sign, 2, this.signText);
        }
        for(i12 = -5; i12 <= 5; ++i12) {
            i2 = Math.abs(i12);
            if(i2 >= 4) {
                for(k12 = -1; k12 <= 1; ++k12) {
                    for(j13 = 4; (((j13 >= 0) || !this.isOpaque(world, i12, j13, k12)) && (this.getY(j13) >= 0)); --j13) {
                        this.setBlockAndMetadata(world, i12, j13, k12, this.brickBlock, this.brickMeta);
                        this.setGrassToDirt(world, i12, j13 - 1, k12);
                    }
                }
                k12 = -2;
                for(j13 = 7; (((j13 >= 0) || !this.isOpaque(world, i12, j13, k12)) && (this.getY(j13) >= 0)); --j13) {
                    this.setBlockAndMetadata(world, i12, j13, k12, this.brickBlock, this.brickMeta);
                    this.setGrassToDirt(world, i12, j13 - 1, k12);
                }
                this.setBlockAndMetadata(world, i12, 4, k12, this.brick2Block, this.brick2Meta);
                this.setBlockAndMetadata(world, i12, 8, k12, this.rockWallBlock, this.rockWallMeta);
            }
            if(i2 == 5) {
                k12 = -3;
                for(j13 = 7; (((j13 >= 0) || !this.isOpaque(world, i12, j13, k12)) && (this.getY(j13) >= 0)); --j13) {
                    this.setBlockAndMetadata(world, i12, j13, k12, this.pillarBlock, this.pillarMeta);
                    this.setGrassToDirt(world, i12, j13 - 1, k12);
                }
                this.setBlockAndMetadata(world, i12, 8, k12, this.rockWallBlock, this.rockWallMeta);
            }
            if(i2 != 4) continue;
            k12 = -3;
            this.setBlockAndMetadata(world, i12, 5, k12, this.brickStairBlock, 6);
            this.setBlockAndMetadata(world, i12, 6, k12, this.rockWallBlock, this.rockWallMeta);
        }
        for(k13 = -1; k13 <= 1; ++k13) {
            this.setBlockAndMetadata(world, -3, 7, k13, this.brickStairBlock, 1);
            this.setBlockAndMetadata(world, -4, 6, k13, this.brickStairBlock, 1);
            this.setBlockAndMetadata(world, -5, 5, k13, this.brickStairBlock, 1);
            this.setBlockAndMetadata(world, -4, 5, k13, this.brickBlock, this.brickMeta);
            this.setBlockAndMetadata(world, 3, 7, k13, this.brickStairBlock, 0);
            this.setBlockAndMetadata(world, 4, 6, k13, this.brickStairBlock, 0);
            this.setBlockAndMetadata(world, 5, 5, k13, this.brickStairBlock, 0);
            this.setBlockAndMetadata(world, 4, 5, k13, this.brickBlock, this.brickMeta);
        }
        for(i12 = -8; i12 <= 8; ++i12) {
            i2 = Math.abs(i12);
            if(i2 < 6) continue;
            for(k12 = 0; k12 <= 1; ++k12) {
                for(j13 = 4; (((j13 >= 0) || !this.isOpaque(world, i12, j13, k12)) && (this.getY(j13) >= 0)); --j13) {
                    this.setBlockAndMetadata(world, i12, j13, k12, this.brickBlock, this.brickMeta);
                    this.setGrassToDirt(world, i12, j13 - 1, k12);
                }
            }
        }
        for(k13 = 0; k13 <= 1; ++k13) {
            int j2;
            int step;
            int maxStep = 12;
            for(step = 0; step < maxStep && !this.isOpaque(world, i142 = -9 - step, j12 = 4 - step, k13); ++step) {
                this.setBlockAndMetadata(world, i142, j12, k13, this.brickStairBlock, 1);
                this.setGrassToDirt(world, i142, j12 - 1, k13);
                j2 = j12 - 1;
                while(!this.isOpaque(world, i142, j2, k13) && this.getY(j2) >= 0) {
                    this.setBlockAndMetadata(world, i142, j2, k13, this.brickBlock, this.brickMeta);
                    this.setGrassToDirt(world, i142, j2 - 1, k13);
                    --j2;
                }
            }
            for(step = 0; step < maxStep && !this.isOpaque(world, i142 = 9 + step, j12 = 4 - step, k13); ++step) {
                this.setBlockAndMetadata(world, i142, j12, k13, this.brickStairBlock, 0);
                this.setGrassToDirt(world, i142, j12 - 1, k13);
                j2 = j12 - 1;
                while(!this.isOpaque(world, i142, j2, k13) && this.getY(j2) >= 0) {
                    this.setBlockAndMetadata(world, i142, j2, k13, this.brickBlock, this.brickMeta);
                    this.setGrassToDirt(world, i142, j2 - 1, k13);
                    --j2;
                }
            }
        }
        for(i12 = -9; i12 <= 9; ++i12) {
            i2 = Math.abs(i12);
            if(i2 == 5 || i2 == 8) {
                this.setBlockAndMetadata(world, i12, 3, 1, LOTRMod.brick, 5);
                continue;
            }
            if(i2 < 4) continue;
            this.setBlockAndMetadata(world, i12, 3, 1, this.brickStairBlock, 7);
        }
        for(int i1421 : new int[] {-1, 1}) {
            j12 = 8;
            int k17 = 0;
            LOTREntityNPC levyman = (LOTREntityNPC) LOTREntities.createEntityByClass(this.strFief.getLevyClasses()[0], world);
            this.spawnNPCAndSetHome(levyman, world, i1421, j12, k17, 8);
        }
        return true;
    }
}
