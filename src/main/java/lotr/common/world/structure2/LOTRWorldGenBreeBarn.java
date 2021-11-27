package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityBreeFarmer;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.entity.passive.*;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenBreeBarn
extends LOTRWorldGenBreeStructure {
    public LOTRWorldGenBreeBarn(boolean flag) {
        super(flag);
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int i1;
        int j1;
        int j12;
        int k13;
        int i12;
        int j2;
        int step;
        int k12;
        this.setOriginAndRotation(world, i, j, k, rotation, 8);
        this.setupRandomBlocks(random);
        if (this.restrictions) {
            for (i12 = -6; i12 <= 6; ++i12) {
                for (k12 = -9; k12 <= 9; ++k12) {
                    j12 = this.getTopBlock(world, i12, k12) - 1;
                    if (this.isSurface(world, i12, j12, k12)) continue;
                    return false;
                }
            }
        }
        for (i12 = -5; i12 <= 5; ++i12) {
            for (k12 = -7; k12 <= 7; ++k12) {
                for (j12 = 1; j12 <= 4; ++j12) {
                    this.setAir(world, i12, j12, k12);
                }
            }
        }
        for (i12 = -6; i12 <= 6; ++i12) {
            for (k12 = -9; k12 <= 9; ++k12) {
                for (j12 = 5; j12 <= 10; ++j12) {
                    this.setAir(world, i12, j12, k12);
                }
            }
        }
        for (i12 = -1; i12 <= 1; ++i12) {
            for (int k131 : new int[]{-8, 8}) {
                for (int j13 = 1; j13 <= 3; ++j13) {
                    this.setAir(world, i12, j13, k131);
                }
            }
        }
        this.loadStrScan("bree_barn");
        this.associateBlockMetaAlias("BRICK", this.brickBlock, this.brickMeta);
        this.associateBlockMetaAlias("STONE_WALL", this.stoneWallBlock, this.stoneWallMeta);
        this.associateBlockMetaAlias("PLANK", this.plankBlock, this.plankMeta);
        this.associateBlockMetaAlias("PLANK_SLAB", this.plankSlabBlock, this.plankSlabMeta);
        this.associateBlockMetaAlias("PLANK_SLAB_INV", this.plankSlabBlock, this.plankSlabMeta | 8);
        this.associateBlockMetaAlias("FENCE", this.fenceBlock, this.fenceMeta);
        this.associateBlockAlias("FENCE_GATE", this.fenceGateBlock);
        this.associateBlockAlias("TRAPDOOR", this.trapdoorBlock);
        this.associateBlockMetaAlias("BEAM", this.beamBlock, this.beamMeta);
        this.associateBlockMetaAlias("BEAM|4", this.beamBlock, this.beamMeta | 4);
        this.associateBlockMetaAlias("BEAM|8", this.beamBlock, this.beamMeta | 8);
        this.associateBlockMetaAlias("ROOF", this.roofBlock, this.roofMeta);
        this.associateBlockMetaAlias("ROOF_SLAB", this.roofSlabBlock, this.roofSlabMeta);
        this.associateBlockMetaAlias("ROOF_SLAB_INV", this.roofSlabBlock, this.roofSlabMeta | 8);
        this.associateBlockAlias("ROOF_STAIR", this.roofStairBlock);
        this.addBlockMetaAliasOption("THATCH_FLOOR", 1, LOTRMod.thatchFloor, 0);
        this.setBlockAliasChance("THATCH_FLOOR", 0.2f);
        this.addBlockMetaAliasOption("GROUND", 13, Blocks.grass, 0);
        this.addBlockMetaAliasOption("GROUND", 7, Blocks.cobblestone, 0);
        this.associateBlockMetaAlias("LEAF", Blocks.leaves, 4);
        this.generateStrScan(world, random, 0, 0, 0);
        for (i1 = -1; i1 <= 1; ++i1) {
            for (step = 0; step < 12 && !this.isOpaque(world, i1, j1 = 0 - step, k13 = -8 - step); ++step) {
                this.setBlockAndMetadata(world, i1, j1, k13, Blocks.grass, 0);
                this.setGrassToDirt(world, i1, j1 - 1, k13);
                j2 = j1 - 1;
                while (!this.isOpaque(world, i1, j2, k13) && this.getY(j2) >= 0) {
                    this.setBlockAndMetadata(world, i1, j2, k13, Blocks.dirt, 0);
                    this.setGrassToDirt(world, i1, j2 - 1, k13);
                    --j2;
                }
            }
        }
        for (i1 = -1; i1 <= 1; ++i1) {
            for (step = 0; step < 12 && !this.isOpaque(world, i1, j1 = 0 - step, k13 = 8 + step); ++step) {
                this.setBlockAndMetadata(world, i1, j1, k13, Blocks.grass, 0);
                this.setGrassToDirt(world, i1, j1 - 1, k13);
                j2 = j1 - 1;
                while (!this.isOpaque(world, i1, j2, k13) && this.getY(j2) >= 0) {
                    this.setBlockAndMetadata(world, i1, j2, k13, Blocks.dirt, 0);
                    this.setGrassToDirt(world, i1, j2 - 1, k13);
                    --j2;
                }
            }
        }
        this.placeChest(world, random, -4, 1, -6, 4, LOTRChestContents.BREE_HOUSE, 1 + random.nextInt(2));
        this.placeChest(world, random, -4, 1, -5, 4, LOTRChestContents.BREE_HOUSE, 1 + random.nextInt(2));
        this.placeChest(world, random, 4, 1, 5, 5, LOTRChestContents.BREE_HOUSE, 1 + random.nextInt(2));
        this.placeChest(world, random, 4, 1, 6, 5, LOTRChestContents.BREE_HOUSE, 1 + random.nextInt(2));
        this.placeChest(world, random, -4, 0, -1, 4, LOTRChestContents.BREE_TREASURE);
        this.placeChest(world, random, 4, 5, -5, 5, LOTRChestContents.BREE_HOUSE, 1 + random.nextInt(2));
        this.placeChest(world, random, -4, 5, 0, 4, LOTRChestContents.BREE_TREASURE, 1 + random.nextInt(2));
        this.placeChest(world, random, -4, 5, 6, 4, LOTRChestContents.BREE_TREASURE);
        LOTREntityBreeFarmer farmer = new LOTREntityBreeFarmer(world);
        this.spawnNPCAndSetHome(farmer, world, 0, 1, 0, 16);
        this.spawnAnimal(world, random, -3, 1, -2);
        this.spawnAnimal(world, random, 3, 1, -2);
        this.spawnAnimal(world, random, -3, 1, 2);
        this.spawnAnimal(world, random, 3, 1, 2);
        return true;
    }

    private void spawnAnimal(World world, Random random, int i, int j, int k) {
        int animals = 2;
        for (int l = 0; l < animals; ++l) {
            EntityAnimal animal = LOTRWorldGenBreeBarn.getRandomAnimal(world, random);
            this.spawnNPCAndSetHome(animal, world, i, j, k, 0);
            animal.detachHome();
        }
    }

    public static EntityAnimal getRandomAnimal(World world, Random random) {
        int animal = random.nextInt(4);
        if (animal == 0) {
            return new EntityCow(world);
        }
        if (animal == 1) {
            return new EntityPig(world);
        }
        if (animal == 2) {
            return new EntitySheep(world);
        }
        if (animal == 3) {
            return new EntityChicken(world);
        }
        return null;
    }
}

