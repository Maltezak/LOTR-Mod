package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.LOTRMod;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTRWorldGenGulfPyramid extends LOTRWorldGenGulfStructure {
    public LOTRWorldGenGulfPyramid(boolean flag) {
        super(flag);
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int j1;
        int j2;
        int i1;
        int j12;
        int k1;
        int step;
        this.setOriginAndRotation(world, i, j, k, rotation, 11);
        this.setupRandomBlocks(random);
        if(this.restrictions) {
            for(i1 = -11; i1 <= 11; ++i1) {
                for(k1 = -11; k1 <= 11; ++k1) {
                    j12 = this.getTopBlock(world, i1, k1) - 1;
                    if(this.isSurface(world, i1, j12, k1)) continue;
                    return false;
                }
            }
        }
        for(i1 = -10; i1 <= 10; ++i1) {
            for(k1 = -10; k1 <= 10; ++k1) {
                for(j12 = 1; j12 <= 20; ++j12) {
                    this.setAir(world, i1, j12, k1);
                }
            }
        }
        this.loadStrScan("gulf_pyramid");
        this.associateBlockMetaAlias("STONE", Blocks.sandstone, 0);
        this.associateBlockAlias("STONE_STAIR", Blocks.sandstone_stairs);
        this.associateBlockMetaAlias("STONE2", LOTRMod.redSandstone, 0);
        this.associateBlockAlias("STONE2_STAIR", LOTRMod.stairsRedSandstone);
        this.addBlockMetaAliasOption("BRICK", 8, LOTRMod.brick, 15);
        this.addBlockMetaAliasOption("BRICK", 2, LOTRMod.brick3, 11);
        this.addBlockAliasOption("BRICK_STAIR", 8, LOTRMod.stairsNearHaradBrick);
        this.addBlockAliasOption("BRICK_STAIR", 2, LOTRMod.stairsNearHaradBrickCracked);
        this.addBlockMetaAliasOption("BRICK_WALL", 8, LOTRMod.wall, 15);
        this.addBlockMetaAliasOption("BRICK_WALL", 2, LOTRMod.wall3, 3);
        this.addBlockMetaAliasOption("PILLAR", 10, LOTRMod.pillar, 5);
        this.addBlockMetaAliasOption("BRICK2", 8, LOTRMod.brick3, 13);
        this.addBlockMetaAliasOption("BRICK2", 2, LOTRMod.brick3, 14);
        this.associateBlockMetaAlias("BRICK2_CARVED", LOTRMod.brick3, 15);
        this.associateBlockMetaAlias("PLANK_SLAB_INV", this.plankSlabBlock, this.plankSlabMeta | 8);
        this.associateBlockMetaAlias("FENCE", this.fenceBlock, this.fenceMeta);
        this.associateBlockMetaAlias("ROOF_SLAB", this.roofSlabBlock, this.roofSlabMeta);
        this.associateBlockMetaAlias("ROOF_SLAB_INV", this.roofSlabBlock, this.roofSlabMeta | 8);
        this.associateBlockAlias("ROOF_STAIR", this.roofStairBlock);
        this.generateStrScan(world, random, 0, 1, 0);
        for(i1 = -5; i1 <= 5; ++i1) {
            for(k1 = -5; k1 <= 5; ++k1) {
                int i2 = Math.abs(i1);
                int k2 = Math.abs(k1);
                int j13 = 11;
                if(i2 <= 2 && k2 <= 2 || !this.isOpaque(world, i1, j13 - 1, k1) || !this.isAir(world, i1, j13, k1) || random.nextInt(12) != 0) continue;
                this.placeChest(world, random, i1, j13, k1, LOTRMod.chestBasket, MathHelper.getRandomIntegerInRange(random, 2, 5), LOTRChestContents.GULF_PYRAMID);
            }
        }
        int maxStep = 4;
        for(int k12 : new int[] {-11, 11}) {
            int i12;
            for(step = 0; step < maxStep && !this.isOpaque(world, i12 = -7 - step, j1 = 0 - step, k12); ++step) {
                this.setBlockAndMetadata(world, i12, j1, k12, Blocks.sandstone_stairs, 1);
                this.setGrassToDirt(world, i12, j1 - 1, k12);
                j2 = j1 - 1;
                while(!this.isOpaque(world, i12, j2, k12) && this.getY(j2) >= 0) {
                    this.setBlockAndMetadata(world, i12, j2, k12, Blocks.sandstone, 0);
                    this.setGrassToDirt(world, i12, j2 - 1, k12);
                    --j2;
                }
            }
            for(step = 0; step < maxStep && !this.isOpaque(world, i12 = 7 + step, j1 = 0 - step, k12); ++step) {
                this.setBlockAndMetadata(world, i12, j1, k12, Blocks.sandstone_stairs, 0);
                this.setGrassToDirt(world, i12, j1 - 1, k12);
                j2 = j1 - 1;
                while(!this.isOpaque(world, i12, j2, k12) && this.getY(j2) >= 0) {
                    this.setBlockAndMetadata(world, i12, j2, k12, Blocks.sandstone, 0);
                    this.setGrassToDirt(world, i12, j2 - 1, k12);
                    --j2;
                }
            }
        }
        for(int i13 : new int[] {-11, 11}) {
            int k13;
            for(step = 0; step < maxStep && !this.isOpaque(world, i13, j1 = 0 - step, k13 = -7 - step); ++step) {
                this.setBlockAndMetadata(world, i13, j1, k13, Blocks.sandstone_stairs, 2);
                this.setGrassToDirt(world, i13, j1 - 1, k13);
                j2 = j1 - 1;
                while(!this.isOpaque(world, i13, j2, k13) && this.getY(j2) >= 0) {
                    this.setBlockAndMetadata(world, i13, j2, k13, Blocks.sandstone, 0);
                    this.setGrassToDirt(world, i13, j2 - 1, k13);
                    --j2;
                }
            }
            for(step = 0; step < maxStep && !this.isOpaque(world, i13, j1 = 0 - step, k13 = 7 + step); ++step) {
                this.setBlockAndMetadata(world, i13, j1, k13, Blocks.sandstone_stairs, 3);
                this.setGrassToDirt(world, i13, j1 - 1, k13);
                j2 = j1 - 1;
                while(!this.isOpaque(world, i13, j2, k13) && this.getY(j2) >= 0) {
                    this.setBlockAndMetadata(world, i13, j2, k13, Blocks.sandstone, 0);
                    this.setGrassToDirt(world, i13, j2 - 1, k13);
                    --j2;
                }
            }
        }
        return true;
    }
}
