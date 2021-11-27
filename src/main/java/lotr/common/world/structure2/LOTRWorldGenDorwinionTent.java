package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityDorwinionGuard;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenDorwinionTent extends LOTRWorldGenStructureBase2 {
    protected Block woodBeamBlock;
    protected int woodBeamMeta;
    protected Block plankBlock;
    protected int plankMeta;
    protected Block plankSlabBlock;
    protected int plankSlabMeta;
    protected Block plankStairBlock;
    protected Block fenceBlock;
    protected int fenceMeta;
    protected Block floorBlock;
    protected int floorMeta;
    protected Block wool1Block;
    protected int wool1Meta;
    protected Block clay1Block;
    protected int clay1Meta;
    protected Block clay1SlabBlock;
    protected int clay1SlabMeta;
    protected Block clay1StairBlock;
    protected Block wool2Block;
    protected int wool2Meta;
    protected Block clay2Block;
    protected int clay2Meta;
    protected Block clay2SlabBlock;
    protected int clay2SlabMeta;
    protected Block clay2StairBlock;

    public LOTRWorldGenDorwinionTent(boolean flag) {
        super(flag);
    }

    @Override
    protected void setupRandomBlocks(Random random) {
        int randomWood = random.nextInt(3);
        if(randomWood == 0) {
            this.woodBeamBlock = LOTRMod.woodBeamV1;
            this.woodBeamMeta = 0;
            this.plankBlock = Blocks.planks;
            this.plankMeta = 0;
            this.plankSlabBlock = Blocks.wooden_slab;
            this.plankSlabMeta = 0;
            this.plankStairBlock = Blocks.oak_stairs;
            this.fenceBlock = Blocks.fence;
            this.fenceMeta = 0;
        }
        else if(randomWood == 1) {
            this.woodBeamBlock = LOTRMod.woodBeam6;
            this.woodBeamMeta = 2;
            this.plankBlock = LOTRMod.planks2;
            this.plankMeta = 10;
            this.plankSlabBlock = LOTRMod.woodSlabSingle4;
            this.plankSlabMeta = 2;
            this.plankStairBlock = LOTRMod.stairsCypress;
            this.fenceBlock = LOTRMod.fence2;
            this.fenceMeta = 10;
        }
        else if(randomWood == 2) {
            this.woodBeamBlock = LOTRMod.woodBeam6;
            this.woodBeamMeta = 3;
            this.plankBlock = LOTRMod.planks2;
            this.plankMeta = 11;
            this.plankSlabBlock = LOTRMod.woodSlabSingle4;
            this.plankSlabMeta = 3;
            this.plankStairBlock = LOTRMod.stairsOlive;
            this.fenceBlock = LOTRMod.fence2;
            this.fenceMeta = 11;
        }
        int randomFloor = random.nextInt(4);
        if(randomFloor == 0) {
            this.floorBlock = Blocks.stained_hardened_clay;
            this.floorMeta = 2;
        }
        else if(randomFloor == 1) {
            this.floorBlock = Blocks.stained_hardened_clay;
            this.floorMeta = 3;
        }
        else if(randomFloor == 2) {
            this.floorBlock = Blocks.stained_hardened_clay;
            this.floorMeta = 14;
        }
        else if(randomFloor == 3) {
            this.floorBlock = Blocks.stained_hardened_clay;
            this.floorMeta = 10;
        }
        int randomWool1 = random.nextInt(3);
        if(randomWool1 == 0) {
            this.wool1Block = Blocks.wool;
            this.wool1Meta = 10;
            this.clay1Block = LOTRMod.clayTileDyed;
            this.clay1Meta = 10;
            this.clay1SlabBlock = LOTRMod.slabClayTileDyedSingle2;
            this.clay1SlabMeta = 2;
            this.clay1StairBlock = LOTRMod.stairsClayTileDyedPurple;
        }
        else if(randomWool1 == 1) {
            this.wool1Block = Blocks.wool;
            this.wool1Meta = 2;
            this.clay1Block = LOTRMod.clayTileDyed;
            this.clay1Meta = 2;
            this.clay1SlabBlock = LOTRMod.slabClayTileDyedSingle;
            this.clay1SlabMeta = 2;
            this.clay1StairBlock = LOTRMod.stairsClayTileDyedMagenta;
        }
        else if(randomWool1 == 2) {
            this.wool1Block = Blocks.wool;
            this.wool1Meta = 14;
            this.clay1Block = LOTRMod.clayTileDyed;
            this.clay1Meta = 14;
            this.clay1SlabBlock = LOTRMod.slabClayTileDyedSingle2;
            this.clay1SlabMeta = 6;
            this.clay1StairBlock = LOTRMod.stairsClayTileDyedRed;
        }
        int randomWool2 = random.nextInt(2);
        if(randomWool2 == 0) {
            this.wool2Block = Blocks.wool;
            this.wool2Meta = 4;
            this.clay2Block = LOTRMod.clayTileDyed;
            this.clay2Meta = 4;
            this.clay2SlabBlock = LOTRMod.slabClayTileDyedSingle;
            this.clay2SlabMeta = 4;
            this.clay2StairBlock = LOTRMod.stairsClayTileDyedYellow;
        }
        else if(randomWool2 == 1) {
            this.wool2Block = Blocks.wool;
            this.wool2Meta = 0;
            this.clay2Block = LOTRMod.clayTileDyed;
            this.clay2Meta = 0;
            this.clay2SlabBlock = LOTRMod.slabClayTileDyedSingle;
            this.clay2SlabMeta = 0;
            this.clay2StairBlock = LOTRMod.stairsClayTileDyedWhite;
        }
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int k1;
        int i1;
        this.setOriginAndRotation(world, i, j, k, rotation, 3);
        this.setupRandomBlocks(random);
        if(this.restrictions) {
            for(i1 = -2; i1 <= 2; ++i1) {
                for(k1 = -3; k1 <= 3; ++k1) {
                    int j1 = this.getTopBlock(world, i1, k1) - 1;
                    if(this.isSurface(world, i1, j1, k1)) continue;
                    return false;
                }
            }
        }
        for(i1 = -2; i1 <= 2; ++i1) {
            for(k1 = -2; k1 <= 2; ++k1) {
                int j1;
                int i2 = Math.abs(i1);
                int k2 = Math.abs(k1);
                for(j1 = 0; (((j1 == 0) || !this.isOpaque(world, i1, j1, k1)) && (this.getY(j1) >= 0)); --j1) {
                    this.setBlockAndMetadata(world, i1, j1, k1, this.floorBlock, this.floorMeta);
                    this.setGrassToDirt(world, i1, j1 - 1, k1);
                }
                for(j1 = 1; j1 <= 4; ++j1) {
                    this.setAir(world, i1, j1, k1);
                }
                if(i2 == 2 && k2 == 2) {
                    for(j1 = 1; j1 <= 2; ++j1) {
                        this.setBlockAndMetadata(world, i1, j1, k1, this.woodBeamBlock, this.woodBeamMeta);
                    }
                }
                else if(i2 == 2) {
                    for(j1 = 1; j1 <= 2; ++j1) {
                        if(k1 % 2 == 0) {
                            this.setBlockAndMetadata(world, i1, j1, k1, this.wool1Block, this.wool1Meta);
                            continue;
                        }
                        this.setBlockAndMetadata(world, i1, j1, k1, this.wool2Block, this.wool2Meta);
                    }
                }
                if(i2 != 0 || k2 != 2) continue;
                for(j1 = 1; j1 <= 3; ++j1) {
                    this.setBlockAndMetadata(world, i1, j1, k1, this.fenceBlock, this.fenceMeta);
                }
            }
        }
        for(int k12 = -2; k12 <= 2; ++k12) {
            if(k12 % 2 == 0) {
                this.setBlockAndMetadata(world, -2, 3, k12, this.clay1StairBlock, 1);
                this.setBlockAndMetadata(world, 2, 3, k12, this.clay1StairBlock, 0);
                this.setBlockAndMetadata(world, -1, 3, k12, this.clay1StairBlock, 4);
                this.setBlockAndMetadata(world, 1, 3, k12, this.clay1StairBlock, 5);
                this.setBlockAndMetadata(world, -1, 4, k12, this.clay1StairBlock, 1);
                this.setBlockAndMetadata(world, 1, 4, k12, this.clay1StairBlock, 0);
                this.setBlockAndMetadata(world, 0, 4, k12, this.clay1Block, this.clay1Meta);
                this.setBlockAndMetadata(world, 0, 5, k12, this.clay1SlabBlock, this.clay1SlabMeta);
                continue;
            }
            this.setBlockAndMetadata(world, -2, 3, k12, this.clay2StairBlock, 1);
            this.setBlockAndMetadata(world, 2, 3, k12, this.clay2StairBlock, 0);
            this.setBlockAndMetadata(world, -1, 3, k12, this.clay2StairBlock, 4);
            this.setBlockAndMetadata(world, 1, 3, k12, this.clay2StairBlock, 5);
            this.setBlockAndMetadata(world, -1, 4, k12, this.clay2StairBlock, 1);
            this.setBlockAndMetadata(world, 1, 4, k12, this.clay2StairBlock, 0);
            this.setBlockAndMetadata(world, 0, 4, k12, this.clay2Block, this.clay2Meta);
            this.setBlockAndMetadata(world, 0, 5, k12, this.clay2SlabBlock, this.clay2SlabMeta);
        }
        if(random.nextBoolean()) {
            this.placeChest(world, random, -1, 1, 0, 4, LOTRChestContents.DORWINION_CAMP);
            this.setBlockAndMetadata(world, 1, 2, 0, Blocks.torch, 1);
        }
        else {
            this.placeChest(world, random, 1, 1, 0, 4, LOTRChestContents.DORWINION_CAMP);
            this.setBlockAndMetadata(world, -1, 2, 0, Blocks.torch, 2);
        }
        LOTREntityDorwinionGuard guard = new LOTREntityDorwinionGuard(world);
        this.spawnNPCAndSetHome(guard, world, 0, 1, 0, 16);
        return true;
    }
}
