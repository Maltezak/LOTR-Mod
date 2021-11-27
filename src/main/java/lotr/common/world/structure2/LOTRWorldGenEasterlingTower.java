package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.*;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenEasterlingTower extends LOTRWorldGenEasterlingStructureTown {
    private boolean enableDoor = true;
    private boolean frontLadder = false;
    private boolean backLadder = false;
    private boolean leftLadder = false;
    private boolean rightLadder = false;

    public LOTRWorldGenEasterlingTower(boolean flag) {
        super(flag);
    }

    public LOTRWorldGenEasterlingTower disableDoor() {
        this.enableDoor = false;
        return this;
    }

    public LOTRWorldGenEasterlingTower setFrontLadder() {
        this.frontLadder = true;
        return this;
    }

    public LOTRWorldGenEasterlingTower setBackLadder() {
        this.backLadder = true;
        return this;
    }

    public LOTRWorldGenEasterlingTower setLeftLadder() {
        this.leftLadder = true;
        return this;
    }

    public LOTRWorldGenEasterlingTower setRightLadder() {
        this.rightLadder = true;
        return this;
    }

    @Override
    protected void setupRandomBlocks(Random random) {
        super.setupRandomBlocks(random);
        this.bedBlock = LOTRMod.strawBed;
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int k1;
        int i1;
        int i12;
        int k2;
        int i2;
        this.setOriginAndRotation(world, i, j, k, rotation, 3);
        this.setupRandomBlocks(random);
        if(this.restrictions) {
            for(i12 = -3; i12 <= 3; ++i12) {
                for(k1 = -3; k1 <= 3; ++k1) {
                    int j1 = this.getTopBlock(world, i12, k1) - 1;
                    if(this.isSurface(world, i12, j1, k1)) continue;
                    return false;
                }
            }
        }
        for(i12 = -2; i12 <= 2; ++i12) {
            for(k1 = -2; k1 <= 2; ++k1) {
                int j1;
                i2 = Math.abs(i12);
                k2 = Math.abs(k1);
                for(j1 = 1; j1 <= 15; ++j1) {
                    this.setAir(world, i12, j1, k1);
                }
                if(i2 == 2 && k2 == 2) {
                    for(j1 = 13; (((j1 >= 0) || !this.isOpaque(world, i12, j1, k1)) && (this.getY(j1) >= 0)); --j1) {
                        this.setBlockAndMetadata(world, i12, j1, k1, this.woodBeamBlock, this.woodBeamMeta);
                        this.setGrassToDirt(world, i12, j1 - 1, k1);
                    }
                    continue;
                }
                for(j1 = 0; (((j1 >= 0) || !this.isOpaque(world, i12, j1, k1)) && (this.getY(j1) >= 0)); --j1) {
                    this.setBlockAndMetadata(world, i12, j1, k1, this.brickBlock, this.brickMeta);
                    this.setGrassToDirt(world, i12, j1 - 1, k1);
                }
                if(i2 == 2 || k2 == 2) {
                    if(i2 == 2 && k2 == 0 || k2 == 2 && i2 == 0) {
                        for(j1 = 1; j1 <= 9; ++j1) {
                            this.setBlockAndMetadata(world, i12, j1, k1, this.pillarBlock, this.pillarMeta);
                        }
                    }
                    else {
                        for(j1 = 1; j1 <= 2; ++j1) {
                            this.setBlockAndMetadata(world, i12, j1, k1, this.brickBlock, this.brickMeta);
                        }
                        int stairMeta = 0;
                        if(i12 == -2) {
                            stairMeta = 1;
                        }
                        else if(i12 == 2) {
                            stairMeta = 0;
                        }
                        else if(k1 == -2) {
                            stairMeta = 2;
                        }
                        else if(k1 == 2) {
                            stairMeta = 3;
                        }
                        for(int j12 = 3; j12 <= 8; ++j12) {
                            if(j12 == 4) {
                                this.setBlockAndMetadata(world, i12, j12, k1, this.brickRedStairBlock, stairMeta);
                                continue;
                            }
                            this.setBlockAndMetadata(world, i12, j12, k1, this.brickStairBlock, stairMeta);
                        }
                        this.setBlockAndMetadata(world, i12, 9, k1, this.brickBlock, this.brickMeta);
                    }
                    this.setBlockAndMetadata(world, i12, 10, k1, this.brickRedBlock, this.brickRedMeta);
                    this.setBlockAndMetadata(world, i12, 11, k1, this.fenceBlock, this.fenceMeta);
                    continue;
                }
                this.setBlockAndMetadata(world, i12, 4, k1, this.brickBlock, this.brickMeta);
                this.setBlockAndMetadata(world, i12, 10, k1, this.brickBlock, this.brickMeta);
            }
        }
        for(i12 = -1; i12 <= 1; ++i12) {
            for(k1 = -1; k1 <= 1; ++k1) {
                i2 = Math.abs(i12);
                k2 = Math.abs(k1);
                if(i2 == 0 || k2 == 0) {
                    this.setBlockAndMetadata(world, i12, 0, k1, this.pillarBlock, this.pillarMeta);
                    continue;
                }
                this.setBlockAndMetadata(world, i12, 0, k1, this.brickRedBlock, this.brickRedMeta);
            }
        }
        if(this.enableDoor) {
            this.setBlockAndMetadata(world, 0, 0, -2, this.pillarBlock, this.pillarMeta);
            this.setBlockAndMetadata(world, 0, 1, -2, this.doorBlock, 1);
            this.setBlockAndMetadata(world, 0, 2, -2, this.doorBlock, 8);
        }
        this.setBlockAndMetadata(world, -1, 3, -1, Blocks.torch, 3);
        this.setBlockAndMetadata(world, 1, 3, -1, Blocks.torch, 3);
        this.setBlockAndMetadata(world, -1, 3, 1, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 1, 3, 1, Blocks.torch, 4);
        this.placeWeaponRack(world, -1, 2, 0, 5, this.getEasterlingWeaponItem(random));
        this.placeArmorStand(world, 1, 1, 0, 1, null);
        for(int j1 = 1; j1 <= 9; ++j1) {
            this.setBlockAndMetadata(world, 0, j1, 1, Blocks.ladder, 2);
        }
        this.setBlockAndMetadata(world, 0, 10, 1, Blocks.trapdoor, 9);
        this.setBlockAndMetadata(world, -1, 6, -1, this.plankSlabBlock, this.plankSlabMeta | 8);
        this.setBlockAndMetadata(world, 0, 6, -1, this.plankSlabBlock, this.plankSlabMeta | 8);
        int[] j1 = new int[] {5, 7};
        k1 = j1.length;
        for(i2 = 0; i2 < k1; ++i2) {
            int j13 = j1[i2];
            this.setBlockAndMetadata(world, 0, j13, -1, this.bedBlock, 3);
            this.setBlockAndMetadata(world, -1, j13, -1, this.bedBlock, 11);
        }
        for(int j14 = 6; j14 <= 9; ++j14) {
            this.setBlockAndMetadata(world, 1, j14, -1, Blocks.ladder, 3);
        }
        this.placeChest(world, random, 1, 5, -1, 3, LOTRChestContents.EASTERLING_TOWER);
        this.setBlockAndMetadata(world, -1, 8, 0, Blocks.torch, 2);
        this.setBlockAndMetadata(world, 1, 8, 0, Blocks.torch, 1);
        this.spawnItemFrame(world, -2, 7, 0, 1, this.getEasterlingFramedItem(random));
        this.spawnItemFrame(world, 2, 7, 0, 3, this.getEasterlingFramedItem(random));
        this.placeWallBanner(world, 0, 9, -2, this.bannerType, 2);
        this.setBlockAndMetadata(world, -3, 14, -3, this.roofSlabBlock, this.roofSlabMeta);
        this.setBlockAndMetadata(world, -2, 13, -3, this.roofStairBlock, 5);
        this.setBlockAndMetadata(world, -1, 13, -3, this.roofStairBlock, 2);
        this.setBlockAndMetadata(world, 0, 13, -3, this.roofSlabBlock, this.roofSlabMeta | 8);
        this.setBlockAndMetadata(world, 1, 13, -3, this.roofStairBlock, 2);
        this.setBlockAndMetadata(world, 2, 13, -3, this.roofStairBlock, 4);
        this.setBlockAndMetadata(world, 3, 14, -3, this.roofSlabBlock, this.roofSlabMeta);
        this.setBlockAndMetadata(world, 3, 13, -2, this.roofStairBlock, 6);
        this.setBlockAndMetadata(world, 3, 13, -1, this.roofStairBlock, 0);
        this.setBlockAndMetadata(world, 3, 13, 0, this.roofSlabBlock, this.roofSlabMeta | 8);
        this.setBlockAndMetadata(world, 3, 13, 1, this.roofStairBlock, 0);
        this.setBlockAndMetadata(world, 3, 13, 2, this.roofStairBlock, 7);
        this.setBlockAndMetadata(world, 3, 14, 3, this.roofSlabBlock, this.roofSlabMeta);
        this.setBlockAndMetadata(world, 2, 13, 3, this.roofStairBlock, 4);
        this.setBlockAndMetadata(world, 1, 13, 3, this.roofStairBlock, 3);
        this.setBlockAndMetadata(world, 0, 13, 3, this.roofSlabBlock, this.roofSlabMeta | 8);
        this.setBlockAndMetadata(world, -1, 13, 3, this.roofStairBlock, 3);
        this.setBlockAndMetadata(world, -2, 13, 3, this.roofStairBlock, 5);
        this.setBlockAndMetadata(world, -3, 14, 3, this.roofSlabBlock, this.roofSlabMeta);
        this.setBlockAndMetadata(world, -3, 13, 2, this.roofStairBlock, 7);
        this.setBlockAndMetadata(world, -3, 13, 1, this.roofStairBlock, 1);
        this.setBlockAndMetadata(world, -3, 13, 0, this.roofSlabBlock, this.roofSlabMeta | 8);
        this.setBlockAndMetadata(world, -3, 13, -1, this.roofStairBlock, 1);
        this.setBlockAndMetadata(world, -3, 13, -2, this.roofStairBlock, 6);
        for(i1 = -2; i1 <= 2; ++i1) {
            this.setBlockAndMetadata(world, i1, 14, -2, this.roofStairBlock, 2);
            this.setBlockAndMetadata(world, i1, 14, 2, this.roofStairBlock, 3);
        }
        for(int k12 = -2; k12 <= 2; ++k12) {
            this.setBlockAndMetadata(world, -2, 14, k12, this.roofStairBlock, 1);
            this.setBlockAndMetadata(world, 2, 14, k12, this.roofStairBlock, 0);
        }
        for(i1 = -1; i1 <= 1; ++i1) {
            for(k1 = -1; k1 <= 1; ++k1) {
                if(i1 != 0 || k1 != 0) {
                    this.setBlockAndMetadata(world, i1, 14, k1, this.roofSlabBlock, this.roofSlabMeta | 8);
                }
                if(i1 == 0 || k1 == 0) {
                    this.setBlockAndMetadata(world, i1, 15, k1, this.roofBlock, this.roofMeta);
                    continue;
                }
                this.setBlockAndMetadata(world, i1, 15, k1, this.roofSlabBlock, this.roofSlabMeta);
            }
        }
        this.setBlockAndMetadata(world, 0, 16, 0, this.roofWallBlock, this.roofWallMeta);
        this.setBlockAndMetadata(world, 0, 17, 0, this.roofWallBlock, this.roofWallMeta);
        this.setBlockAndMetadata(world, -2, 12, -1, Blocks.torch, 3);
        this.setBlockAndMetadata(world, -1, 12, -2, Blocks.torch, 2);
        this.setBlockAndMetadata(world, 1, 12, -2, Blocks.torch, 1);
        this.setBlockAndMetadata(world, 2, 12, -1, Blocks.torch, 3);
        this.setBlockAndMetadata(world, -2, 12, 1, Blocks.torch, 4);
        this.setBlockAndMetadata(world, -1, 12, 2, Blocks.torch, 2);
        this.setBlockAndMetadata(world, 1, 12, 2, Blocks.torch, 1);
        this.setBlockAndMetadata(world, 2, 12, 1, Blocks.torch, 4);
        if(this.frontLadder) {
            this.setBlockAndMetadata(world, 0, 11, -2, this.fenceGateBlock, 0);
            this.placeSideLadder(world, 0, 10, -3, 2);
        }
        if(this.backLadder) {
            this.setBlockAndMetadata(world, 0, 11, 2, this.fenceGateBlock, 2);
            this.placeSideLadder(world, 0, 10, 3, 3);
        }
        if(this.leftLadder) {
            this.setBlockAndMetadata(world, -2, 11, 0, this.fenceGateBlock, 3);
            this.placeSideLadder(world, -3, 10, 0, 5);
        }
        if(this.rightLadder) {
            this.setBlockAndMetadata(world, 2, 11, 0, this.fenceGateBlock, 1);
            this.placeSideLadder(world, 3, 10, 0, 4);
        }
        int soldiers = 1 + random.nextInt(3);
        for(int l = 0; l < soldiers; ++l) {
            LOTREntityEasterlingWarrior soldier = random.nextInt(3) == 0 ? new LOTREntityEasterlingArcher(world) : new LOTREntityEasterlingWarrior(world);
            soldier.spawnRidingHorse = false;
            this.spawnNPCAndSetHome(soldier, world, 0, 1, 0, 16);
        }
        LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
        respawner.setSpawnClasses(LOTREntityEasterlingWarrior.class, LOTREntityEasterlingArcher.class);
        respawner.setCheckRanges(16, -8, 8, 6);
        respawner.setSpawnRanges(3, -6, 6, 16);
        this.placeNPCRespawner(respawner, world, 0, 6, 0);
        return true;
    }

    private void placeSideLadder(World world, int i, int j, int k, int meta) {
        int j1 = j;
        while(!this.isOpaque(world, i, j1, k) && this.getY(j1) >= 0) {
            this.setBlockAndMetadata(world, i, j1, k, Blocks.ladder, meta);
            --j1;
        }
    }
}
