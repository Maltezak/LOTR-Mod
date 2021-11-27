package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.entity.npc.*;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenEasterlingGatehouse extends LOTRWorldGenEasterlingStructureTown {
    private String[] signText = LOTRNames.getRhunVillageName(new Random());
    private boolean enableSigns = true;

    public LOTRWorldGenEasterlingGatehouse(boolean flag) {
        super(flag);
    }

    public LOTRWorldGenEasterlingGatehouse setSignText(String[] s) {
        this.signText = s;
        return this;
    }

    public LOTRWorldGenEasterlingGatehouse disableSigns() {
        this.enableSigns = false;
        return this;
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int k16;
        int k12;
        int i1;
        int i12;
        int i13;
        int k13;
        int i2;
        int j1;
        int k2;
        int k14;
        this.setOriginAndRotation(world, i, j, k, rotation, 4);
        this.setupRandomBlocks(random);
        if(this.restrictions) {
            for(i12 = -7; i12 <= 7; ++i12) {
                for(k12 = -4; k12 <= 4; ++k12) {
                    int j12 = this.getTopBlock(world, i12, k12) - 1;
                    if(this.isSurface(world, i12, j12, k12)) continue;
                    return false;
                }
            }
        }
        for(i12 = -7; i12 <= 7; ++i12) {
            for(k12 = -3; k12 <= 3; ++k12) {
                int j13;
                i2 = Math.abs(i12);
                k2 = Math.abs(k12);
                for(j13 = 1; j13 <= 12; ++j13) {
                    this.setAir(world, i12, j13, k12);
                }
                if(i2 <= 3 && k2 <= 3 || i2 <= 6 && k2 <= 2 || i2 <= 7 && k2 <= 1) {
                    for(j13 = 0; (((j13 >= 0) || !this.isOpaque(world, i12, j13, k12)) && (this.getY(j13) >= 0)); --j13) {
                        this.setBlockAndMetadata(world, i12, j13, k12, this.brickBlock, this.brickMeta);
                        this.setGrassToDirt(world, i12, j13 - 1, k12);
                    }
                }
                if(i2 != 3 || k2 > 2) continue;
                if(k2 == 0) {
                    for(j13 = 1; j13 <= 6; ++j13) {
                        this.setBlockAndMetadata(world, i12, j13, k12, this.pillarBlock, this.pillarMeta);
                    }
                    continue;
                }
                for(j13 = 1; j13 <= 6; ++j13) {
                    this.setBlockAndMetadata(world, i12, j13, k12, this.brickBlock, this.brickMeta);
                }
            }
        }
        for(i12 = -2; i12 <= 2; ++i12) {
            this.setBlockAndMetadata(world, i12, 0, 0, this.pillarBlock, this.pillarMeta);
            this.setBlockAndMetadata(world, i12, 0, -2, this.brickStairBlock, 3);
            this.setBlockAndMetadata(world, i12, 0, 2, this.brickStairBlock, 2);
        }
        for(int k15 = -1; k15 <= 1; ++k15) {
            this.setBlockAndMetadata(world, -2, 6, k15, this.brickStairBlock, 4);
            this.setBlockAndMetadata(world, 2, 6, k15, this.brickStairBlock, 5);
        }
        int[] k15 = new int[] {-3, 3};
        k12 = k15.length;
        for(i2 = 0; i2 < k12; ++i2) {
            k16 = k15[i2];
            for(int i14 : new int[] {-3, 3}) {
                for(int j14 = 1; j14 <= 6; ++j14) {
                    this.setBlockAndMetadata(world, i14, j14, k16, this.brickRedBlock, this.brickRedMeta);
                }
            }
            this.setBlockAndMetadata(world, -2, 6, k16, this.brickRedStairBlock, 4);
            this.setBlockAndMetadata(world, 2, 6, k16, this.brickRedStairBlock, 5);
            this.setBlockAndMetadata(world, -3, 7, k16, this.brickRedStairBlock, 1);
            this.setBlockAndMetadata(world, -2, 7, k16, this.brickRedBlock, this.brickRedMeta);
            this.setBlockAndMetadata(world, -1, 7, k16, this.brickRedBlock, this.brickRedMeta);
            this.setBlockAndMetadata(world, 0, 7, k16, this.brickRedStairBlock, k16 > 0 ? 7 : 6);
            this.setBlockAndMetadata(world, 1, 7, k16, this.brickRedBlock, this.brickRedMeta);
            this.setBlockAndMetadata(world, 2, 7, k16, this.brickRedBlock, this.brickRedMeta);
            this.setBlockAndMetadata(world, 3, 7, k16, this.brickRedStairBlock, 0);
        }
        for(int i15 = -2; i15 <= 2; ++i15) {
            for(k12 = -2; k12 <= 2; ++k12) {
                if(k12 == 0) {
                    this.setBlockAndMetadata(world, i15, 7, k12, this.brickRedBlock, this.brickRedMeta);
                    continue;
                }
                this.setBlockAndMetadata(world, i15, 7, k12, this.pillarBlock, this.pillarMeta);
            }
        }
        for(int k161 : new int[] {-2, 2}) {
            for(int i16 = -2; i16 <= 2; ++i16) {
                for(int j15 = 1; j15 <= (Math.abs(i16) <= 1 ? 7 : 6); ++j15) {
                    this.setBlockAndMetadata(world, i16, j15, k161, this.gateBlock, k161 > 0 ? 2 : 3);
                }
            }
            this.setBlockAndMetadata(world, -1, 8, k161, this.fenceBlock, this.fenceMeta);
            this.setBlockAndMetadata(world, 0, 8, k161, this.brickBlock, this.brickMeta);
            this.setBlockAndMetadata(world, 1, 8, k161, this.fenceBlock, this.fenceMeta);
            this.setBlockAndMetadata(world, 0, 9, k161, Blocks.lever, 14);
        }
        for(int j16 = 7; j16 <= 10; ++j16) {
            this.setBlockAndMetadata(world, -3, j16, -2, this.brickBlock, this.brickMeta);
            this.setBlockAndMetadata(world, 3, j16, -2, this.brickBlock, this.brickMeta);
            this.setBlockAndMetadata(world, -3, j16, 2, this.brickBlock, this.brickMeta);
            this.setBlockAndMetadata(world, 3, j16, 2, this.brickBlock, this.brickMeta);
        }
        int[] j16 = new int[] {-3, 3};
        k12 = j16.length;
        for(i2 = 0; i2 < k12; ++i2) {
            k16 = j16[i2];
            this.setBlockAndMetadata(world, -3, 8, k16, this.brickStairBlock, 5);
            this.setBlockAndMetadata(world, -2, 8, k16, this.brickStairBlock, k16 > 0 ? 3 : 2);
            this.setBlockAndMetadata(world, -1, 8, k16, this.brickBlock, this.brickMeta);
            this.setBlockAndMetadata(world, 0, 8, k16, this.brickStairBlock, k16 > 0 ? 3 : 2);
            this.setBlockAndMetadata(world, 1, 8, k16, this.brickBlock, this.brickMeta);
            this.setBlockAndMetadata(world, 2, 8, k16, this.brickStairBlock, k16 > 0 ? 3 : 2);
            this.setBlockAndMetadata(world, 3, 8, k16, this.brickStairBlock, 4);
            this.setBlockAndMetadata(world, -3, 9, k16, this.brickWallBlock, this.brickWallMeta);
            this.setBlockAndMetadata(world, -1, 9, k16, this.brickWallBlock, this.brickWallMeta);
            this.setBlockAndMetadata(world, 1, 9, k16, this.brickWallBlock, this.brickWallMeta);
            this.setBlockAndMetadata(world, 3, 9, k16, this.brickWallBlock, this.brickWallMeta);
            this.setBlockAndMetadata(world, -3, 10, k16, this.brickBlock, this.brickMeta);
            this.setBlockAndMetadata(world, -2, 10, k16, this.brickStairBlock, k16 > 0 ? 7 : 6);
            this.setBlockAndMetadata(world, -1, 10, k16, this.brickBlock, this.brickMeta);
            this.setBlockAndMetadata(world, 0, 10, k16, this.brickStairBlock, k16 > 0 ? 7 : 6);
            this.setBlockAndMetadata(world, 1, 10, k16, this.brickBlock, this.brickMeta);
            this.setBlockAndMetadata(world, 2, 10, k16, this.brickStairBlock, k16 > 0 ? 7 : 6);
            this.setBlockAndMetadata(world, 3, 10, k16, this.brickBlock, this.brickMeta);
            for(int i17 = -3; i17 <= 3; ++i17) {
                this.setBlockAndMetadata(world, i17, 11, k16, this.brickBlock, this.brickMeta);
            }
        }
        for(k14 = -2; k14 <= 2; ++k14) {
            this.setBlockAndMetadata(world, -3, 11, k14, this.brickBlock, this.brickMeta);
            this.setBlockAndMetadata(world, 3, 11, k14, this.brickBlock, this.brickMeta);
        }
        for(i1 = -3; i1 <= 3; ++i1) {
            for(k12 = -3; k12 <= 3; ++k12) {
                i2 = Math.abs(i1);
                if(i2 + (k2 = Math.abs(k12)) <= 1) {
                    this.setBlockAndMetadata(world, i1, 12, k12, this.brickSlabBlock, this.brickSlabMeta | 8);
                }
                else {
                    this.setBlockAndMetadata(world, i1, 12, k12, this.brickBlock, this.brickMeta);
                }
                if(i2 != 2 || k2 != 2) continue;
                this.setBlockAndMetadata(world, i1, 11, k12, this.brickSlabBlock, this.brickSlabMeta | 8);
            }
        }
        this.setBlockAndMetadata(world, -2, 10, -2, Blocks.torch, 2);
        this.setBlockAndMetadata(world, 2, 10, -2, Blocks.torch, 1);
        this.setBlockAndMetadata(world, -2, 10, 2, Blocks.torch, 2);
        this.setBlockAndMetadata(world, 2, 10, 2, Blocks.torch, 1);
        for(i1 = -4; i1 <= 4; ++i1) {
            for(k12 = -4; k12 <= 4; ++k12) {
                i2 = Math.abs(i1);
                k2 = Math.abs(k12);
                if(i2 != 4 && k2 != 4) continue;
                if((i2 + k2) % 2 == 0) {
                    this.setBlockAndMetadata(world, i1, 13, k12, this.roofSlabBlock, this.roofSlabMeta);
                    if(i2 > 2 && k2 > 2) continue;
                    this.setBlockAndMetadata(world, i1, 12, k12, this.fenceBlock, this.fenceMeta);
                    continue;
                }
                if(k2 == 4 && i2 == 3) {
                    this.setBlockAndMetadata(world, i1, 12, k12, this.roofStairBlock, k12 > 0 ? 7 : 6);
                    continue;
                }
                if(i2 == 4 && k2 == 3) {
                    this.setBlockAndMetadata(world, i1, 12, k12, this.roofStairBlock, i1 > 0 ? 4 : 5);
                    continue;
                }
                this.setBlockAndMetadata(world, i1, 12, k12, this.roofSlabBlock, this.roofSlabMeta | 8);
            }
        }
        for(i1 = -3; i1 <= 3; ++i1) {
            this.setBlockAndMetadata(world, i1, 13, -3, this.roofStairBlock, 2);
            this.setBlockAndMetadata(world, i1, 13, 3, this.roofStairBlock, 3);
        }
        for(k14 = -3; k14 <= 3; ++k14) {
            this.setBlockAndMetadata(world, -3, 13, k14, this.roofStairBlock, 1);
            this.setBlockAndMetadata(world, 3, 13, k14, this.roofStairBlock, 0);
        }
        for(i1 = -2; i1 <= 2; ++i1) {
            for(k12 = -2; k12 <= 2; ++k12) {
                i2 = Math.abs(i1);
                k2 = Math.abs(k12);
                this.setBlockAndMetadata(world, i1, 13, k12, this.roofBlock, this.roofMeta);
                if(i2 == 2 && k2 == 2) {
                    this.setBlockAndMetadata(world, i1, 14, k12, this.roofSlabBlock, this.roofSlabMeta);
                }
                else {
                    this.setBlockAndMetadata(world, i1, 14, k12, this.roofBlock, this.roofMeta);
                }
                if(i2 == 2 && k2 == 0 || k2 == 2 && i2 == 0) {
                    this.setBlockAndMetadata(world, i1, 15, k12, this.roofSlabBlock, this.roofSlabMeta);
                }
                if(i2 > 1 || k2 > 1) continue;
                this.setBlockAndMetadata(world, i1, 15, k12, this.roofBlock, this.roofMeta);
            }
        }
        this.setBlockAndMetadata(world, 0, 16, 0, this.roofBlock, this.roofMeta);
        this.setBlockAndMetadata(world, 0, 17, 0, this.roofBlock, this.roofMeta);
        this.setBlockAndMetadata(world, 0, 18, 0, this.roofWallBlock, this.roofWallMeta);
        this.setBlockAndMetadata(world, 0, 19, 0, this.roofWallBlock, this.roofWallMeta);
        this.setBlockAndMetadata(world, 0, 16, -1, this.roofStairBlock, 2);
        this.setBlockAndMetadata(world, 0, 16, 1, this.roofStairBlock, 3);
        this.setBlockAndMetadata(world, -1, 16, 0, this.roofStairBlock, 1);
        this.setBlockAndMetadata(world, 1, 16, 0, this.roofStairBlock, 0);
        for(i1 = -7; i1 <= 7; ++i1) {
            int i22 = Math.abs(i1);
            if(i22 >= 4 && i22 <= 6) {
                for(int k17 : new int[] {-2, 2}) {
                    for(int j17 = 1; j17 <= 8; ++j17) {
                        if(j17 == 1) {
                            this.setBlockAndMetadata(world, i1, j17, k17, this.brickRedBlock, this.brickRedMeta);
                        }
                        else {
                            this.setBlockAndMetadata(world, i1, j17, k17, this.brickBlock, this.brickMeta);
                        }
                        if(j17 != 3 || i22 != 5) continue;
                        this.setBlockAndMetadata(world, i1, j17, k17, this.brickCarvedBlock, this.brickCarvedMeta);
                    }
                    this.setBlockAndMetadata(world, i1, 9, k17, this.brickWallBlock, this.brickWallMeta);
                }
            }
            if(i22 < 4 || i22 > 7) continue;
            for(k13 = -1; k13 <= 2; ++k13) {
                for(int j18 = 1; j18 <= 5; ++j18) {
                    if(k13 == 0 || j18 == 1) {
                        this.setBlockAndMetadata(world, i1, j18, k13, this.brickRedBlock, this.brickRedMeta);
                        continue;
                    }
                    this.setBlockAndMetadata(world, i1, j18, k13, this.brickBlock, this.brickMeta);
                }
            }
        }
        for(int step = 0; step <= 1; ++step) {
            int j19 = 6 + step;
            for(k13 = -1; k13 <= 1; ++k13) {
                this.setBlockAndMetadata(world, -4 + step, j19, k13, k13 == 0 ? this.brickRedStairBlock : this.brickStairBlock, 1);
                this.setBlockAndMetadata(world, 4 - step, j19, k13, k13 == 0 ? this.brickRedStairBlock : this.brickStairBlock, 0);
            }
        }
        this.setBlockAndMetadata(world, -7, 5, -2, this.brickStairBlock, 5);
        this.setBlockAndMetadata(world, -7, 6, -2, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, -7, 7, -2, this.brickStairBlock, 3);
        this.setBlockAndMetadata(world, 7, 5, -2, this.brickStairBlock, 4);
        this.setBlockAndMetadata(world, 7, 6, -2, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, 7, 7, -2, this.brickStairBlock, 3);
        this.placeWallBanner(world, -5, 6, -2, this.bannerType, 2);
        this.placeWallBanner(world, 5, 6, -2, this.bannerType, 2);
        this.placeWallBanner(world, -5, 6, 2, this.bannerType, 0);
        this.placeWallBanner(world, 5, 6, 2, this.bannerType, 0);
        if(this.enableSigns && this.signText != null) {
            this.placeSign(world, -3, 3, -4, Blocks.wall_sign, 2, this.signText);
            this.placeSign(world, 3, 3, -4, Blocks.wall_sign, 2, this.signText);
        }
        int maxStep = 12;
        for(k12 = 2; k12 <= 2; ++k12) {
            int j2;
            int step;
            int j110;
            for(step = 0; step < maxStep && !this.isOpaque(world, i13 = -8 - step, j110 = 5 - step, k12); ++step) {
                this.setBlockAndMetadata(world, i13, j110, k12, this.brickStairBlock, 1);
                this.setGrassToDirt(world, i13, j110 - 1, k12);
                j2 = j110 - 1;
                while(!this.isOpaque(world, i13, j2, k12) && this.getY(j2) >= 0) {
                    this.setBlockAndMetadata(world, i13, j2, k12, this.brickBlock, this.brickMeta);
                    this.setGrassToDirt(world, i13, j2 - 1, k12);
                    --j2;
                }
            }
            for(step = 0; step < maxStep && !this.isOpaque(world, i13 = 8 + step, j1 = (5 - step), k12); ++step) {
                this.setBlockAndMetadata(world, i13, j1, k12, this.brickStairBlock, 0);
                this.setGrassToDirt(world, i13, j1 - 1, k12);
                j2 = j1 - 1;
                while(!this.isOpaque(world, i13, j2, k12) && this.getY(j2) >= 0) {
                    this.setBlockAndMetadata(world, i13, j2, k12, this.brickBlock, this.brickMeta);
                    this.setGrassToDirt(world, i13, j2 - 1, k12);
                    --j2;
                }
            }
        }
        int men = 2;
        for(int l = 0; l < men; ++l) {
            int k17;
            i13 = 0;
            int j111 = 8;
            k17 = 0;
            LOTREntityEasterlingWarrior guard = new LOTREntityEasterlingWarrior(world);
            guard.spawnRidingHorse = false;
            this.spawnNPCAndSetHome(guard, world, i13, j111, k17, 8);
        }
        return true;
    }
}
