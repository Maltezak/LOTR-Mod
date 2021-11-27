package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.animal.LOTREntityHorse;
import lotr.common.entity.npc.*;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRWorldGenEasterlingFortress extends LOTRWorldGenEasterlingStructureTown {
    public LOTRWorldGenEasterlingFortress(boolean flag) {
        super(flag);
    }

    @Override
    protected void setupRandomBlocks(Random random) {
        super.setupRandomBlocks(random);
        this.bedBlock = LOTRMod.strawBed;
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int i1;
        int j1;
        int j12;
        int i12;
        int i13;
        int j13;
        int i14;
        int i2;
        int k1;
        int j14;
        int i22;
        int j15;
        int j16;
        int k2;
        int k12;
        int k132;
        int i15;
        int k14;
        this.setOriginAndRotation(world, i, j, k, rotation, 13);
        this.setupRandomBlocks(random);
        if(this.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for(int i16 = -12; i16 <= 12; ++i16) {
                for(k132 = -12; k132 <= 12; ++k132) {
                    j13 = this.getTopBlock(world, i16, k132) - 1;
                    if(!this.isSurface(world, i16, j13, k132)) {
                        return false;
                    }
                    if(j13 < minHeight) {
                        minHeight = j13;
                    }
                    if(j13 > maxHeight) {
                        maxHeight = j13;
                    }
                    if(maxHeight - minHeight <= 12) continue;
                    return false;
                }
            }
        }
        for(i14 = -12; i14 <= 12; ++i14) {
            for(k12 = -12; k12 <= 12; ++k12) {
                i22 = Math.abs(i14);
                k2 = Math.abs(k12);
                for(j13 = 1; j13 <= 10; ++j13) {
                    this.setAir(world, i14, j13, k12);
                }
                if(i22 <= 9 && k2 <= 9) {
                    for(j13 = 0; (((j13 >= 0) || !this.isOpaque(world, i14, j13, k12)) && (this.getY(j13) >= 0)); --j13) {
                        this.setBlockAndMetadata(world, i14, j13, k12, Blocks.dirt, 0);
                        this.setGrassToDirt(world, i14, j13 - 1, k12);
                    }
                    int randomGround = random.nextInt(3);
                    if(randomGround == 0) {
                        this.setBlockAndMetadata(world, i14, 0, k12, Blocks.grass, 0);
                    }
                    else if(randomGround == 1) {
                        this.setBlockAndMetadata(world, i14, 0, k12, Blocks.dirt, 1);
                    }
                    else if(randomGround == 2) {
                        this.setBlockAndMetadata(world, i14, 0, k12, LOTRMod.dirtPath, 0);
                    }
                    if(random.nextInt(3) == 0) {
                        this.setBlockAndMetadata(world, i14, 1, k12, LOTRMod.thatchFloor, 0);
                    }
                }
                if((((i22 == 12) || (i22 == 9)) && ((k2 == 12) || (k2 == 9)))) {
                    for(j13 = 8; (((j13 >= 0) || !this.isOpaque(world, i14, j13, k12)) && (this.getY(j13) >= 0)); --j13) {
                        this.setBlockAndMetadata(world, i14, j13, k12, this.pillarBlock, this.pillarMeta);
                        this.setGrassToDirt(world, i14, j13 - 1, k12);
                    }
                    continue;
                }
                if(i22 == 3 && (k2 == 9 || k2 == 12) || k2 == 3 && (i22 == 9 || i22 == 12)) {
                    for(j13 = 7; (((j13 >= 0) || !this.isOpaque(world, i14, j13, k12)) && (this.getY(j13) >= 0)); --j13) {
                        this.setBlockAndMetadata(world, i14, j13, k12, this.pillarBlock, this.pillarMeta);
                        this.setGrassToDirt(world, i14, j13 - 1, k12);
                    }
                    if(i22 != 12 && k2 != 12) continue;
                    this.setBlockAndMetadata(world, i14, 8, k12, this.brickWallBlock, this.brickWallMeta);
                    this.setBlockAndMetadata(world, i14, 9, k12, Blocks.torch, 5);
                    continue;
                }
                if(i22 >= 10 || k2 >= 10) {
                    for(j13 = 4; (((j13 >= 0) || !this.isOpaque(world, i14, j13, k12)) && (this.getY(j13) >= 0)); --j13) {
                        this.setBlockAndMetadata(world, i14, j13, k12, this.brickBlock, this.brickMeta);
                        this.setGrassToDirt(world, i14, j13 - 1, k12);
                    }
                    this.setBlockAndMetadata(world, i14, 5, k12, this.brickGoldBlock, this.brickGoldMeta);
                }
                if(i22 <= 11 && k2 <= 11 && (i22 >= 10 || k2 >= 10)) {
                    this.setBlockAndMetadata(world, i14, 5, k12, this.pillarBlock, this.pillarMeta);
                }
                if(i22 <= 12 && k2 <= 12 && (i22 == 12 || k2 == 12) || i22 <= 9 && k2 <= 9 && (i22 == 9 || k2 == 9)) {
                    this.setBlockAndMetadata(world, i14, 6, k12, this.brickRedWallBlock, this.brickRedWallMeta);
                }
                if(i14 == -9 && k2 <= 8) {
                    this.setBlockAndMetadata(world, i14, 5, k12, this.brickStairBlock, 4);
                }
                if(i14 == 9 && k2 <= 8) {
                    this.setBlockAndMetadata(world, i14, 5, k12, this.brickStairBlock, 5);
                }
                if(k12 == -9 && i22 <= 8) {
                    this.setBlockAndMetadata(world, i14, 5, k12, this.brickStairBlock, 7);
                }
                if(k12 != 9 || i22 > 8) continue;
                this.setBlockAndMetadata(world, i14, 5, k12, this.brickStairBlock, 6);
            }
        }
        for(i14 = -2; i14 <= 2; ++i14) {
            k12 = -13;
            for(j12 = 0; (((j12 >= 0) || !this.isOpaque(world, i14, j12, k12)) && (this.getY(j12) >= 0)); --j12) {
                this.setBlockAndMetadata(world, i14, j12, k12, this.pillarBlock, this.pillarMeta);
                this.setGrassToDirt(world, i14, j12 - 1, k12);
            }
        }
        for(int i17 : new int[] {-6, 0, 6}) {
            int i23;
            if(i17 != 0) {
                this.setBlockAndMetadata(world, i17 - 1, 0, -12, this.pillarBlock, this.pillarMeta);
                this.setBlockAndMetadata(world, i17, 0, -12, this.pillarRedBlock, this.pillarRedMeta);
                this.setBlockAndMetadata(world, i17 + 1, 0, -12, this.pillarBlock, this.pillarMeta);
                for(i23 = i17 - 1; i23 <= i17 + 1; ++i23) {
                    this.setBlockAndMetadata(world, i23, 1, -12, this.barsBlock, 0);
                    this.setBlockAndMetadata(world, i23, 2, -12, this.barsBlock, 0);
                }
                this.setBlockAndMetadata(world, i17 - 1, 3, -12, this.brickStairBlock, 4);
                this.setBlockAndMetadata(world, i17, 3, -12, this.barsBlock, 0);
                this.setBlockAndMetadata(world, i17 + 1, 3, -12, this.brickStairBlock, 5);
                this.setBlockAndMetadata(world, i17, 4, -12, this.brickStairBlock, 6);
                this.setBlockAndMetadata(world, i17, 0, -11, LOTRMod.hearth, 0);
                this.setBlockAndMetadata(world, i17, 1, -11, Blocks.fire, 0);
                this.setBlockAndMetadata(world, i17, 2, -11, this.brickStairBlock, 6);
            }
            this.setBlockAndMetadata(world, i17 - 1, 0, 12, this.pillarBlock, this.pillarMeta);
            this.setBlockAndMetadata(world, i17, 0, 12, this.pillarRedBlock, this.pillarRedMeta);
            this.setBlockAndMetadata(world, i17 + 1, 0, 12, this.pillarBlock, this.pillarMeta);
            for(i23 = i17 - 1; i23 <= i17 + 1; ++i23) {
                this.setAir(world, i23, 1, 12);
                this.setAir(world, i23, 2, 12);
            }
            this.setBlockAndMetadata(world, i17 - 1, 3, 12, this.brickStairBlock, 4);
            this.setAir(world, i17, 3, 12);
            this.setBlockAndMetadata(world, i17 + 1, 3, 12, this.brickStairBlock, 5);
            this.setBlockAndMetadata(world, i17, 4, 12, this.brickStairBlock, 7);
        }
        int[] i18 = new int[] {-6, 0, 6};
        k12 = i18.length;
        for(j12 = 0; j12 < k12; ++j12) {
            int k22;
            k132 = i18[j12];
            this.setBlockAndMetadata(world, -12, 0, k132 - 1, this.pillarBlock, this.pillarMeta);
            this.setBlockAndMetadata(world, -12, 0, k132, this.pillarRedBlock, this.pillarRedMeta);
            this.setBlockAndMetadata(world, -12, 0, k132 + 1, this.pillarBlock, this.pillarMeta);
            for(k22 = k132 - 1; k22 <= k132 + 1; ++k22) {
                this.setAir(world, -12, 1, k22);
                this.setAir(world, -12, 2, k22);
            }
            this.setBlockAndMetadata(world, -12, 3, k132 - 1, this.brickStairBlock, 7);
            this.setAir(world, -12, 3, k132);
            this.setBlockAndMetadata(world, -12, 3, k132 + 1, this.brickStairBlock, 6);
            this.setBlockAndMetadata(world, -12, 4, k132, this.brickStairBlock, 5);
            this.setBlockAndMetadata(world, 12, 0, k132 - 1, this.pillarBlock, this.pillarMeta);
            this.setBlockAndMetadata(world, 12, 0, k132, this.pillarRedBlock, this.pillarRedMeta);
            this.setBlockAndMetadata(world, 12, 0, k132 + 1, this.pillarBlock, this.pillarMeta);
            for(k22 = k132 - 1; k22 <= k132 + 1; ++k22) {
                this.setAir(world, 12, 1, k22);
                this.setAir(world, 12, 2, k22);
            }
            this.setBlockAndMetadata(world, 12, 3, k132 - 1, this.brickStairBlock, 7);
            this.setAir(world, 12, 3, k132);
            this.setBlockAndMetadata(world, 12, 3, k132 + 1, this.brickStairBlock, 6);
            this.setBlockAndMetadata(world, 12, 4, k132, this.brickStairBlock, 4);
        }
        for(i12 = -1; i12 <= 1; ++i12) {
            for(j1 = 1; j1 <= 3; ++j1) {
                this.setAir(world, i12, j1, -12);
                this.setBlockAndMetadata(world, i12, j1, -11, this.gateBlock, 2);
                this.setAir(world, i12, j1, -10);
            }
        }
        this.setBlockAndMetadata(world, -1, 4, -12, this.brickStairBlock, 4);
        this.setBlockAndMetadata(world, 0, 4, -12, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 1, 4, -12, this.brickStairBlock, 5);
        for(i12 = -1; i12 <= 1; ++i12) {
            this.setBlockAndMetadata(world, i12, 6, -12, this.brickGoldBlock, this.brickGoldMeta);
        }
        this.setBlockAndMetadata(world, -2, 6, -12, this.brickCarvedBlock, this.brickCarvedMeta);
        this.setBlockAndMetadata(world, 2, 6, -12, this.brickCarvedBlock, this.brickCarvedMeta);
        this.setBlockAndMetadata(world, -2, 7, -12, this.brickStairBlock, 1);
        this.setBlockAndMetadata(world, -1, 7, -12, this.brickStairBlock, 0);
        this.setBlockAndMetadata(world, 0, 7, -12, this.brickBlock, this.brickMeta);
        this.setBlockAndMetadata(world, 0, 8, -12, this.brickSlabBlock, this.brickSlabMeta);
        this.setBlockAndMetadata(world, 1, 7, -12, this.brickStairBlock, 1);
        this.setBlockAndMetadata(world, 2, 7, -12, this.brickStairBlock, 0);
        this.setBlockAndMetadata(world, -1, 4, -10, this.brickStairBlock, 4);
        this.setBlockAndMetadata(world, 0, 4, -10, this.brickStairBlock, 7);
        this.setBlockAndMetadata(world, 1, 4, -10, this.brickStairBlock, 5);
        for(int i17 : new int[] {-3, 3}) {
            this.placeWallBanner(world, i17, 4, -12, this.bannerType, 2);
            this.placeWallBanner(world, i17, 4, 12, this.bannerType, 0);
        }
        for(int k1321 : new int[] {-3, 3}) {
            this.placeWallBanner(world, -12, 4, k1321, this.bannerType, 3);
            this.placeWallBanner(world, 12, 4, k1321, this.bannerType, 1);
        }
        this.placeWallBanner(world, 0, 6, -12, this.bannerType, 2);
        int[] i19 = new int[] {-12, 9};
        j1 = i19.length;
        for(j12 = 0; j12 < j1; ++j12) {
            int i17;
            i17 = i19[j12];
            for(int k15 : new int[] {-12, 9}) {
                this.setBlockAndMetadata(world, i17 + 1, 8, k15, this.brickStairBlock, 0);
                this.setBlockAndMetadata(world, i17 + 2, 8, k15, this.brickStairBlock, 1);
                this.setBlockAndMetadata(world, i17 + 1, 8, k15 + 3, this.brickStairBlock, 0);
                this.setBlockAndMetadata(world, i17 + 2, 8, k15 + 3, this.brickStairBlock, 1);
                this.setBlockAndMetadata(world, i17, 8, k15 + 1, this.brickStairBlock, 3);
                this.setBlockAndMetadata(world, i17, 8, k15 + 2, this.brickStairBlock, 2);
                this.setBlockAndMetadata(world, i17 + 3, 8, k15 + 1, this.brickStairBlock, 3);
                this.setBlockAndMetadata(world, i17 + 3, 8, k15 + 2, this.brickStairBlock, 2);
                this.setBlockAndMetadata(world, i17, 9, k15, this.brickWallBlock, this.brickWallMeta);
                this.setBlockAndMetadata(world, i17 + 3, 9, k15, this.brickWallBlock, this.brickWallMeta);
                this.setBlockAndMetadata(world, i17, 9, k15 + 3, this.brickWallBlock, this.brickWallMeta);
                this.setBlockAndMetadata(world, i17 + 3, 9, k15 + 3, this.brickWallBlock, this.brickWallMeta);
                for(i2 = i17 + 1; i2 <= i17 + 2; ++i2) {
                    for(int k23 = k15 + 1; k23 <= k15 + 2; ++k23) {
                        this.setBlockAndMetadata(world, i2, 10, k23, this.roofBlock, this.roofMeta);
                    }
                }
                for(i2 = i17; i2 <= i17 + 3; ++i2) {
                    this.setBlockAndMetadata(world, i2, 10, k15, this.roofStairBlock, 2);
                    this.setBlockAndMetadata(world, i2, 10, k15 + 3, this.roofStairBlock, 3);
                }
                for(int k24 = k15 + 1; k24 <= k15 + 2; ++k24) {
                    this.setBlockAndMetadata(world, i17, 10, k24, this.roofStairBlock, 1);
                    this.setBlockAndMetadata(world, i17 + 3, 10, k24, this.roofStairBlock, 0);
                }
                if(k15 == -12) {
                    this.setBlockAndMetadata(world, i17 + 1, 6, k15, this.brickStairBlock, 0);
                    this.setBlockAndMetadata(world, i17 + 2, 6, k15, this.brickStairBlock, 1);
                }
                if(k15 == 9) {
                    this.setBlockAndMetadata(world, i17 + 1, 6, k15 + 3, this.brickStairBlock, 0);
                    this.setBlockAndMetadata(world, i17 + 2, 6, k15 + 3, this.brickStairBlock, 1);
                }
                if(i17 == -12) {
                    this.setBlockAndMetadata(world, i17, 6, k15 + 1, this.brickStairBlock, 3);
                    this.setBlockAndMetadata(world, i17, 6, k15 + 2, this.brickStairBlock, 2);
                }
                if(i17 != 9) continue;
                this.setBlockAndMetadata(world, i17 + 3, 6, k15 + 1, this.brickStairBlock, 3);
                this.setBlockAndMetadata(world, i17 + 3, 6, k15 + 2, this.brickStairBlock, 2);
            }
        }
        this.setBlockAndMetadata(world, -9, 7, -10, Blocks.torch, 4);
        this.setBlockAndMetadata(world, -10, 7, -9, Blocks.torch, 1);
        this.setBlockAndMetadata(world, 9, 7, -10, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 10, 7, -9, Blocks.torch, 2);
        this.setBlockAndMetadata(world, -9, 7, 10, Blocks.torch, 3);
        this.setBlockAndMetadata(world, -10, 7, 9, Blocks.torch, 1);
        this.setBlockAndMetadata(world, 9, 7, 10, Blocks.torch, 3);
        this.setBlockAndMetadata(world, 10, 7, 9, Blocks.torch, 2);
        for(i13 = -1; i13 <= 1; ++i13) {
            for(k12 = -12; k12 <= -4; ++k12) {
                if(i13 == 0) {
                    this.setBlockAndMetadata(world, i13, 0, k12, this.pillarRedBlock, this.pillarRedMeta);
                    continue;
                }
                this.setBlockAndMetadata(world, i13, 0, k12, this.pillarBlock, this.pillarMeta);
            }
        }
        for(k14 = -1; k14 <= 1; ++k14) {
            for(i15 = -9; i15 <= 9; ++i15) {
                if(i15 > -4 && i15 < 4) continue;
                if(k14 == 0) {
                    this.setBlockAndMetadata(world, i15, 0, k14, this.pillarRedBlock, this.pillarRedMeta);
                    continue;
                }
                this.setBlockAndMetadata(world, i15, 0, k14, this.pillarBlock, this.pillarMeta);
            }
        }
        for(i13 = -3; i13 <= 3; ++i13) {
            for(k12 = -3; k12 <= 3; ++k12) {
                i22 = Math.abs(i13);
                k2 = Math.abs(k12);
                if(i22 == 3 && k2 == 3) {
                    for(j13 = 0; j13 <= 5; ++j13) {
                        this.setBlockAndMetadata(world, i13, j13, k12, this.pillarBlock, this.pillarMeta);
                    }
                }
                else if(i22 == 3 || k2 == 3) {
                    for(j13 = 0; j13 <= 5; ++j13) {
                        this.setBlockAndMetadata(world, i13, j13, k12, this.brickBlock, this.brickMeta);
                    }
                }
                else {
                    this.setBlockAndMetadata(world, i13, 0, k12, this.plankBlock, this.plankMeta);
                }
                if(i22 > 2 || k2 > 2) continue;
                if(i22 == 2 && k2 == 2) {
                    for(j13 = 6; j13 <= 11; ++j13) {
                        this.setBlockAndMetadata(world, i13, j13, k12, this.pillarBlock, this.pillarMeta);
                    }
                    continue;
                }
                if(i22 == 2 || k2 == 2) {
                    for(j13 = 6; j13 <= 11; ++j13) {
                        if(j13 == 11) {
                            this.setBlockAndMetadata(world, i13, j13, k12, this.brickGoldBlock, this.brickGoldMeta);
                            continue;
                        }
                        this.setBlockAndMetadata(world, i13, j13, k12, this.brickBlock, this.brickMeta);
                    }
                    continue;
                }
                this.setBlockAndMetadata(world, i13, 6, k12, this.pillarBlock, this.pillarMeta);
            }
        }
        for(i13 = -3; i13 <= 3; ++i13) {
            this.setBlockAndMetadata(world, i13, 6, -3, this.roofStairBlock, 2);
            this.setBlockAndMetadata(world, i13, 6, 3, this.roofStairBlock, 3);
        }
        for(k14 = -2; k14 <= 2; ++k14) {
            this.setBlockAndMetadata(world, -3, 6, k14, this.roofStairBlock, 1);
            this.setBlockAndMetadata(world, 3, 6, k14, this.roofStairBlock, 0);
        }
        this.setBlockAndMetadata(world, -4, 6, -4, this.roofSlabBlock, this.roofSlabMeta);
        this.setBlockAndMetadata(world, -3, 5, -4, this.roofStairBlock, 5);
        this.setBlockAndMetadata(world, -2, 5, -4, this.roofStairBlock, 2);
        this.setBlockAndMetadata(world, -1, 5, -4, this.roofStairBlock, 4);
        this.setBlockAndMetadata(world, 0, 6, -4, this.roofSlabBlock, this.roofSlabMeta);
        this.setBlockAndMetadata(world, 1, 5, -4, this.roofStairBlock, 5);
        this.setBlockAndMetadata(world, 2, 5, -4, this.roofStairBlock, 2);
        this.setBlockAndMetadata(world, 3, 5, -4, this.roofStairBlock, 4);
        this.setBlockAndMetadata(world, 4, 6, -4, this.roofSlabBlock, this.roofSlabMeta);
        this.setBlockAndMetadata(world, 4, 5, -3, this.roofStairBlock, 6);
        this.setBlockAndMetadata(world, 4, 5, -2, this.roofStairBlock, 0);
        this.setBlockAndMetadata(world, 4, 5, -1, this.roofStairBlock, 7);
        this.setBlockAndMetadata(world, 4, 6, 0, this.roofSlabBlock, this.roofSlabMeta);
        this.setBlockAndMetadata(world, 4, 5, 1, this.roofStairBlock, 6);
        this.setBlockAndMetadata(world, 4, 5, 2, this.roofStairBlock, 0);
        this.setBlockAndMetadata(world, 4, 5, 3, this.roofStairBlock, 7);
        this.setBlockAndMetadata(world, 4, 6, 4, this.roofSlabBlock, this.roofSlabMeta);
        this.setBlockAndMetadata(world, 3, 5, 4, this.roofStairBlock, 4);
        this.setBlockAndMetadata(world, 2, 5, 4, this.roofStairBlock, 3);
        this.setBlockAndMetadata(world, 1, 5, 4, this.roofStairBlock, 5);
        this.setBlockAndMetadata(world, 0, 6, 4, this.roofSlabBlock, this.roofSlabMeta);
        this.setBlockAndMetadata(world, -1, 5, 4, this.roofStairBlock, 4);
        this.setBlockAndMetadata(world, -2, 5, 4, this.roofStairBlock, 3);
        this.setBlockAndMetadata(world, -3, 5, 4, this.roofStairBlock, 5);
        this.setBlockAndMetadata(world, -4, 6, 4, this.roofSlabBlock, this.roofSlabMeta);
        this.setBlockAndMetadata(world, -4, 5, 3, this.roofStairBlock, 7);
        this.setBlockAndMetadata(world, -4, 5, 2, this.roofStairBlock, 1);
        this.setBlockAndMetadata(world, -4, 5, 1, this.roofStairBlock, 6);
        this.setBlockAndMetadata(world, -4, 6, 0, this.roofSlabBlock, this.roofSlabMeta);
        this.setBlockAndMetadata(world, -4, 5, -1, this.roofStairBlock, 7);
        this.setBlockAndMetadata(world, -4, 5, -2, this.roofStairBlock, 1);
        this.setBlockAndMetadata(world, -4, 5, -3, this.roofStairBlock, 6);
        for(i13 = -2; i13 <= 2; ++i13) {
            this.setBlockAndMetadata(world, i13, 12, -2, this.roofStairBlock, 2);
            this.setBlockAndMetadata(world, i13, 12, 2, this.roofStairBlock, 3);
        }
        for(k14 = -1; k14 <= 1; ++k14) {
            this.setBlockAndMetadata(world, -2, 12, k14, this.roofStairBlock, 1);
            this.setBlockAndMetadata(world, 2, 12, k14, this.roofStairBlock, 0);
        }
        this.setBlockAndMetadata(world, -3, 12, -3, this.roofSlabBlock, this.roofSlabMeta);
        this.setBlockAndMetadata(world, -2, 11, -3, this.roofStairBlock, 5);
        this.setBlockAndMetadata(world, -1, 11, -3, this.roofStairBlock, 4);
        this.setBlockAndMetadata(world, 0, 12, -3, this.roofSlabBlock, this.roofSlabMeta);
        this.setBlockAndMetadata(world, 1, 11, -3, this.roofStairBlock, 5);
        this.setBlockAndMetadata(world, 2, 11, -3, this.roofStairBlock, 4);
        this.setBlockAndMetadata(world, 3, 12, -3, this.roofSlabBlock, this.roofSlabMeta);
        this.setBlockAndMetadata(world, 3, 11, -2, this.roofStairBlock, 6);
        this.setBlockAndMetadata(world, 3, 11, -1, this.roofStairBlock, 7);
        this.setBlockAndMetadata(world, 3, 12, 0, this.roofSlabBlock, this.roofSlabMeta);
        this.setBlockAndMetadata(world, 3, 11, 1, this.roofStairBlock, 6);
        this.setBlockAndMetadata(world, 3, 11, 2, this.roofStairBlock, 7);
        this.setBlockAndMetadata(world, 3, 12, 3, this.roofSlabBlock, this.roofSlabMeta);
        this.setBlockAndMetadata(world, 2, 11, 3, this.roofStairBlock, 4);
        this.setBlockAndMetadata(world, 1, 11, 3, this.roofStairBlock, 5);
        this.setBlockAndMetadata(world, 0, 12, 3, this.roofSlabBlock, this.roofSlabMeta);
        this.setBlockAndMetadata(world, -1, 11, 3, this.roofStairBlock, 4);
        this.setBlockAndMetadata(world, -2, 11, 3, this.roofStairBlock, 5);
        this.setBlockAndMetadata(world, -3, 12, 3, this.roofSlabBlock, this.roofSlabMeta);
        this.setBlockAndMetadata(world, -3, 11, 2, this.roofStairBlock, 7);
        this.setBlockAndMetadata(world, -3, 11, 1, this.roofStairBlock, 6);
        this.setBlockAndMetadata(world, -3, 12, 0, this.roofSlabBlock, this.roofSlabMeta);
        this.setBlockAndMetadata(world, -3, 11, -1, this.roofStairBlock, 7);
        this.setBlockAndMetadata(world, -3, 11, -2, this.roofStairBlock, 6);
        for(i13 = -1; i13 <= 1; ++i13) {
            this.setBlockAndMetadata(world, i13, 12, -1, this.roofStairBlock, 7);
            this.setBlockAndMetadata(world, i13, 12, 1, this.roofStairBlock, 6);
        }
        this.setBlockAndMetadata(world, -1, 12, 0, this.roofStairBlock, 4);
        this.setBlockAndMetadata(world, 1, 12, 0, this.roofStairBlock, 5);
        this.setBlockAndMetadata(world, -1, 13, -1, this.roofBlock, this.roofMeta);
        this.setBlockAndMetadata(world, 0, 13, -1, this.roofStairBlock, 2);
        this.setBlockAndMetadata(world, 1, 13, -1, this.roofBlock, this.roofMeta);
        this.setBlockAndMetadata(world, 1, 13, 0, this.roofStairBlock, 0);
        this.setBlockAndMetadata(world, 1, 13, 1, this.roofBlock, this.roofMeta);
        this.setBlockAndMetadata(world, 0, 13, 1, this.roofStairBlock, 3);
        this.setBlockAndMetadata(world, -1, 13, 1, this.roofBlock, this.roofMeta);
        this.setBlockAndMetadata(world, -1, 13, 0, this.roofStairBlock, 1);
        this.setBlockAndMetadata(world, 0, 13, 0, this.roofBlock, this.roofMeta);
        this.setBlockAndMetadata(world, 0, 14, 0, this.roofBlock, this.roofMeta);
        this.setBlockAndMetadata(world, 0, 15, 0, this.roofWallBlock, this.roofWallMeta);
        this.setBlockAndMetadata(world, 0, 16, 0, this.roofWallBlock, this.roofWallMeta);
        this.setBlockAndMetadata(world, -3, 4, -4, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 3, 4, -4, Blocks.torch, 4);
        this.setBlockAndMetadata(world, -3, 4, 4, Blocks.torch, 3);
        this.setBlockAndMetadata(world, 3, 4, 4, Blocks.torch, 3);
        this.setBlockAndMetadata(world, -4, 4, -3, Blocks.torch, 1);
        this.setBlockAndMetadata(world, -4, 4, 3, Blocks.torch, 1);
        this.setBlockAndMetadata(world, 4, 4, -3, Blocks.torch, 2);
        this.setBlockAndMetadata(world, 4, 4, 3, Blocks.torch, 2);
        this.setBlockAndMetadata(world, -2, 10, -3, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 2, 10, -3, Blocks.torch, 4);
        this.setBlockAndMetadata(world, -2, 10, 3, Blocks.torch, 3);
        this.setBlockAndMetadata(world, 2, 10, 3, Blocks.torch, 3);
        this.setBlockAndMetadata(world, -3, 10, -2, Blocks.torch, 1);
        this.setBlockAndMetadata(world, -3, 10, 2, Blocks.torch, 1);
        this.setBlockAndMetadata(world, 3, 10, -2, Blocks.torch, 2);
        this.setBlockAndMetadata(world, 3, 10, 2, Blocks.torch, 2);
        this.setBlockAndMetadata(world, 0, 1, -3, this.doorBlock, 1);
        this.setBlockAndMetadata(world, 0, 2, -3, this.doorBlock, 8);
        this.setBlockAndMetadata(world, -3, 1, 0, this.doorBlock, 2);
        this.setBlockAndMetadata(world, -3, 2, 0, this.doorBlock, 8);
        this.setBlockAndMetadata(world, 3, 1, 0, this.doorBlock, 0);
        this.setBlockAndMetadata(world, 3, 2, 0, this.doorBlock, 8);
        for(k14 = -3; k14 <= 0; ++k14) {
            this.setBlockAndMetadata(world, 0, 0, k14, this.pillarRedBlock, this.pillarRedMeta);
        }
        for(i13 = -3; i13 <= 3; ++i13) {
            this.setBlockAndMetadata(world, i13, 0, 0, this.pillarRedBlock, this.pillarRedMeta);
        }
        for(i13 = -2; i13 <= 2; ++i13) {
            this.setBlockAndMetadata(world, i13, 5, -2, this.brickStairBlock, 7);
            this.setBlockAndMetadata(world, i13, 5, 2, this.brickStairBlock, 6);
        }
        for(k14 = -2; k14 <= 2; ++k14) {
            this.setBlockAndMetadata(world, -2, 5, k14, this.brickStairBlock, 4);
            this.setBlockAndMetadata(world, 2, 5, k14, this.brickStairBlock, 5);
        }
        for(j14 = 1; j14 <= 6; ++j14) {
            this.setBlockAndMetadata(world, 0, j14, 1, this.pillarBlock, this.pillarMeta);
            this.setBlockAndMetadata(world, 0, j14, 0, Blocks.ladder, 2);
            if(j14 > 5) continue;
            this.setBlockAndMetadata(world, -1, j14, 1, Blocks.ladder, 5);
            this.setBlockAndMetadata(world, 1, j14, 1, Blocks.ladder, 4);
        }
        for(j14 = 1; j14 <= 5; ++j14) {
            this.setBlockAndMetadata(world, 0, j14, 2, this.brickBlock, this.brickMeta);
        }
        this.setBlockAndMetadata(world, -2, 2, 2, this.plankSlabBlock, this.plankSlabMeta | 8);
        this.setBlockAndMetadata(world, -1, 2, 2, this.plankSlabBlock, this.plankSlabMeta | 8);
        this.setBlockAndMetadata(world, 1, 2, 2, this.plankSlabBlock, this.plankSlabMeta | 8);
        this.setBlockAndMetadata(world, 2, 2, 2, this.plankSlabBlock, this.plankSlabMeta | 8);
        int[] j17 = new int[] {1, 3};
        k12 = j17.length;
        for(i22 = 0; i22 < k12; ++i22) {
            j16 = j17[i22];
            this.setBlockAndMetadata(world, -2, j16, 2, this.bedBlock, 1);
            this.setBlockAndMetadata(world, -1, j16, 2, this.bedBlock, 9);
            this.setBlockAndMetadata(world, 2, j16, 2, this.bedBlock, 3);
            this.setBlockAndMetadata(world, 1, j16, 2, this.bedBlock, 11);
        }
        this.setBlockAndMetadata(world, -2, 4, -2, Blocks.torch, 3);
        this.setBlockAndMetadata(world, 2, 4, -2, Blocks.torch, 3);
        this.setBlockAndMetadata(world, -2, 4, 2, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 2, 4, 2, Blocks.torch, 4);
        this.placeArmorStand(world, -2, 1, -2, 2, null);
        this.placeArmorStand(world, 2, 1, -2, 2, null);
        this.placeWeaponRack(world, 0, 3, -2, 4, this.getEasterlingWeaponItem(random));
        this.placeWeaponRack(world, -2, 3, 0, 5, this.getEasterlingWeaponItem(random));
        this.placeWeaponRack(world, 2, 3, 0, 7, this.getEasterlingWeaponItem(random));
        for(j15 = 8; j15 <= 9; ++j15) {
            this.setBlockAndMetadata(world, 0, j15, -2, this.barsBlock, 0);
            this.setBlockAndMetadata(world, 0, j15, 2, this.barsBlock, 0);
            this.setBlockAndMetadata(world, -2, j15, 0, this.barsBlock, 0);
            this.setBlockAndMetadata(world, 2, j15, 0, this.barsBlock, 0);
        }
        this.setBlockAndMetadata(world, -1, 11, -1, Blocks.torch, 3);
        this.setBlockAndMetadata(world, 1, 11, -1, Blocks.torch, 3);
        this.setBlockAndMetadata(world, -1, 11, 1, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 1, 11, 1, Blocks.torch, 4);
        for(i1 = -9; i1 <= -6; ++i1) {
            for(k12 = -9; k12 <= -6; ++k12) {
                this.setBlockAndMetadata(world, i1, 0, k12, this.pillarBlock, this.pillarMeta);
            }
        }
        for(j15 = 1; j15 <= 4; ++j15) {
            this.setBlockAndMetadata(world, -6, j15, -6, this.woodBeamBlock, this.woodBeamMeta);
        }
        this.setBlockAndMetadata(world, -6, 5, -6, this.plankSlabBlock, this.plankSlabMeta);
        this.setBlockAndMetadata(world, -6, 2, -5, Blocks.torch, 3);
        this.setBlockAndMetadata(world, -5, 2, -6, Blocks.torch, 2);
        for(j15 = 1; j15 <= 3; ++j15) {
            this.setBlockAndMetadata(world, -9, j15, -6, this.fenceBlock, this.fenceMeta);
            this.setBlockAndMetadata(world, -6, j15, -9, this.fenceBlock, this.fenceMeta);
        }
        this.setBlockAndMetadata(world, -7, 3, -6, this.fenceBlock, this.fenceMeta);
        this.setBlockAndMetadata(world, -6, 3, -7, this.fenceBlock, this.fenceMeta);
        for(i1 = -9; i1 <= -7; ++i1) {
            for(k12 = -9; k12 <= -7; ++k12) {
                if(i1 < -8 && k12 < -8) continue;
                this.setBlockAndMetadata(world, i1, 4, k12, this.plankBlock, this.plankMeta);
            }
        }
        for(i1 = -9; i1 <= -7; ++i1) {
            this.setBlockAndMetadata(world, i1, 4, -6, this.plankStairBlock, 3);
        }
        for(int k16 = -9; k16 <= -7; ++k16) {
            this.setBlockAndMetadata(world, -6, 4, k16, this.plankStairBlock, 0);
        }
        this.setBlockAndMetadata(world, -8, 1, -10, Blocks.stonebrick, 0);
        this.setBlockAndMetadata(world, -8, 2, -10, LOTRMod.alloyForge, 3);
        this.setBlockAndMetadata(world, -10, 1, -8, Blocks.stonebrick, 0);
        this.setBlockAndMetadata(world, -10, 2, -8, Blocks.furnace, 4);
        this.setBlockAndMetadata(world, -7, 1, -6, Blocks.anvil, 1);
        this.setBlockAndMetadata(world, -6, 1, -7, Blocks.cauldron, 3);
        LOTREntityEasterlingBlacksmith blacksmith = new LOTREntityEasterlingBlacksmith(world);
        this.spawnNPCAndSetHome(blacksmith, world, -8, 1, -8, 8);
        for(j1 = 1; j1 <= 4; ++j1) {
            this.setBlockAndMetadata(world, 6, j1, -6, this.woodBeamBlock, this.woodBeamMeta);
        }
        this.setBlockAndMetadata(world, 6, 5, -6, this.plankSlabBlock, this.plankSlabMeta);
        this.setBlockAndMetadata(world, 6, 2, -5, Blocks.torch, 3);
        this.setBlockAndMetadata(world, 5, 2, -6, Blocks.torch, 1);
        for(j1 = 1; j1 <= 3; ++j1) {
            this.setBlockAndMetadata(world, 9, j1, -6, this.fenceBlock, this.fenceMeta);
            this.setBlockAndMetadata(world, 6, j1, -9, this.fenceBlock, this.fenceMeta);
        }
        this.setBlockAndMetadata(world, 7, 3, -6, this.fenceBlock, this.fenceMeta);
        this.setBlockAndMetadata(world, 6, 3, -7, this.fenceBlock, this.fenceMeta);
        for(i15 = 7; i15 <= 9; ++i15) {
            for(k1 = -9; k1 <= -7; ++k1) {
                if(i15 > 8 && k1 < -8) continue;
                this.setBlockAndMetadata(world, i15, 4, k1, this.plankBlock, this.plankMeta);
            }
        }
        for(i15 = 7; i15 <= 9; ++i15) {
            this.setBlockAndMetadata(world, i15, 4, -6, this.plankStairBlock, 3);
        }
        for(k12 = -9; k12 <= -7; ++k12) {
            this.setBlockAndMetadata(world, 6, 4, k12, this.plankStairBlock, 1);
        }
        for(i15 = 7; i15 <= 8; ++i15) {
            this.placeChest(world, random, i15, 1, -9, 3, LOTRChestContents.EASTERLING_TOWER);
        }
        this.setBlockAndMetadata(world, 9, 1, -8, this.tableBlock, 0);
        this.setBlockAndMetadata(world, 9, 1, -7, Blocks.crafting_table, 0);
        this.setBlockAndMetadata(world, 8, 1, -5, LOTRMod.commandTable, 0);
        this.placeWeaponRack(world, -8, 2, -3, 5, new ItemStack(LOTRMod.rhunBow));
        this.placeWeaponRack(world, -8, 2, 3, 5, new ItemStack(LOTRMod.rhunBow));
        this.setBlockAndMetadata(world, -9, 1, -1, this.plankStairBlock, 6);
        this.setBlockAndMetadata(world, -9, 1, 0, Blocks.wool, 0);
        this.setBlockAndMetadata(world, -9, 1, 1, this.plankStairBlock, 7);
        this.setBlockAndMetadata(world, -9, 2, -1, Blocks.wool, 0);
        this.setBlockAndMetadata(world, -9, 2, 0, Blocks.wool, 14);
        this.setBlockAndMetadata(world, -9, 2, 1, Blocks.wool, 0);
        this.setBlockAndMetadata(world, -9, 3, -1, this.plankStairBlock, 2);
        this.setBlockAndMetadata(world, -9, 3, 0, Blocks.wool, 0);
        this.setBlockAndMetadata(world, -9, 3, 1, this.plankStairBlock, 3);
        this.setBlockAndMetadata(world, -8, 2, 0, Blocks.wooden_button, 2);
        this.placeWeaponRack(world, 8, 2, -3, 7, new ItemStack(LOTRMod.swordRhun));
        this.placeWeaponRack(world, 8, 2, 3, 7, new ItemStack(LOTRMod.swordRhun));
        this.setBlockAndMetadata(world, 8, 1, 0, this.fenceBlock, this.fenceMeta);
        this.setBlockAndMetadata(world, 8, 2, 0, Blocks.hay_block, 0);
        this.setBlockAndMetadata(world, 8, 3, 0, Blocks.pumpkin, 3);
        this.setBlockAndMetadata(world, 8, 2, -1, Blocks.lever, 4);
        this.setBlockAndMetadata(world, 8, 2, 1, Blocks.lever, 3);
        for(j1 = 0; j1 <= 7; ++j1) {
            this.setBlockAndMetadata(world, -1, j1, 9, this.pillarBlock, this.pillarMeta);
            this.setBlockAndMetadata(world, 1, j1, 9, this.pillarBlock, this.pillarMeta);
        }
        for(j1 = 1; j1 <= 4; ++j1) {
            this.setBlockAndMetadata(world, 0, j1, 9, Blocks.ladder, 2);
        }
        this.setAir(world, 0, 5, 9);
        this.setAir(world, 0, 6, 9);
        this.setBlockAndMetadata(world, 0, 5, 10, this.brickStairBlock, 2);
        int[] j3 = new int[] {-6, 6};
        k1 = j3.length;
        for(j16 = 0; j16 < k1; ++j16) {
            int i110 = j3[j16];
            for(int l = 0; l <= 4; ++l) {
                int k15;
                int j18 = 1 + l;
                k15 = 6 + l;
                for(i2 = i110 - 1; i2 <= i110 + 1; ++i2) {
                    this.setBlockAndMetadata(world, i2, j18, k15, this.brickStairBlock, 2);
                    for(int j2 = j18 - 1; j2 >= 1 && !this.isOpaque(world, i2, j2, k15); --j2) {
                        this.setBlockAndMetadata(world, i2, j2, k15, this.brickBlock, this.brickMeta);
                        this.setGrassToDirt(world, i2, j2 - 1, k15);
                    }
                    if(k15 != 9) continue;
                    this.setAir(world, i2, 5, k15);
                    this.setAir(world, i2, 6, k15);
                }
            }
        }
        for(int i111 = -4; i111 <= 4; ++i111) {
            if(Math.abs(i111) < 2) continue;
            for(k1 = 8; k1 <= 9; ++k1) {
                if(this.isOpaque(world, i111, 1, k1)) continue;
                int h = 1;
                if(random.nextInt(4) == 0) {
                    ++h;
                }
                this.setGrassToDirt(world, i111, 0, k1);
                for(j13 = 1; j13 < 1 + h; ++j13) {
                    this.setBlockAndMetadata(world, i111, j13, k1, Blocks.hay_block, 0);
                }
            }
        }
        this.setBlockAndMetadata(world, 4, 1, 7, Blocks.cauldron, 3);
        for(int i110 : new int[] {-2, 2}) {
            this.setBlockAndMetadata(world, i110, 1, 6, this.fenceBlock, this.fenceMeta);
            this.setBlockAndMetadata(world, i110, 2, 6, this.fenceBlock, this.fenceMeta);
            LOTREntityHorse horse = new LOTREntityHorse(world);
            this.spawnNPCAndSetHome(horse, world, i110, 1, 5, 0);
            horse.setHorseType(0);
            horse.saddleMountForWorldGen();
            horse.detachHome();
            this.leashEntityTo(horse, world, i110, 2, 6);
        }
        LOTREntityEasterlingWarlord captain = new LOTREntityEasterlingWarlord(world);
        captain.spawnRidingHorse = false;
        this.spawnNPCAndSetHome(captain, world, 0, 1, 0, 12);
        int soldiers = 4 + random.nextInt(4);
        for(int l = 0; l < soldiers; ++l) {
            LOTREntityEasterlingWarrior soldier;
            soldier = random.nextInt(3) == 0 ? new LOTREntityEasterlingArcher(world) : new LOTREntityEasterlingWarrior(world);
            if(random.nextInt(3) == 0) {
                soldier = new LOTREntityEasterlingGoldWarrior(world);
            }
            soldier.spawnRidingHorse = false;
            this.spawnNPCAndSetHome(soldier, world, 0, 1, 0, 16);
        }
        LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
        respawner.setSpawnClasses(LOTREntityEasterlingWarrior.class, LOTREntityEasterlingArcher.class);
        respawner.setCheckRanges(20, -8, 12, 10);
        respawner.setSpawnRanges(10, 0, 8, 16);
        this.placeNPCRespawner(respawner, world, 0, 0, 0);
        LOTREntityNPCRespawner respawnerGold = new LOTREntityNPCRespawner(world);
        respawnerGold.setSpawnClass(LOTREntityEasterlingGoldWarrior.class);
        respawnerGold.setCheckRanges(20, -8, 12, 5);
        respawnerGold.setSpawnRanges(10, 0, 8, 16);
        this.placeNPCRespawner(respawnerGold, world, 0, 0, 0);
        return true;
    }
}
