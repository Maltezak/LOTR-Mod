package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.entity.npc.LOTRNames;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenSouthronTownGate extends LOTRWorldGenSouthronStructure {
    private String[] signText = LOTRNames.getHaradVillageName(new Random());

    public LOTRWorldGenSouthronTownGate(boolean flag) {
        super(flag);
    }

    public LOTRWorldGenSouthronTownGate setSignText(String[] s) {
        this.signText = s;
        return this;
    }

    @Override
    protected boolean canUseRedBricks() {
        return false;
    }

    @Override
    protected boolean forceCedarWood() {
        return true;
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int j1;
        int j12;
        int j2;
        int i1;
        int k1;
        int k12;
        int step;
        int i12;
        this.setOriginAndRotation(world, i, j, k, rotation, 4);
        this.setupRandomBlocks(random);
        if(this.restrictions) {
            for(i12 = -8; i12 <= 8; ++i12) {
                for(k12 = -3; k12 <= 3; ++k12) {
                    j12 = this.getTopBlock(world, i12, k12) - 1;
                    if(this.isSurface(world, i12, j12, k12)) continue;
                    return false;
                }
            }
        }
        for(i12 = -8; i12 <= 8; ++i12) {
            for(k12 = -3; k12 <= 3; ++k12) {
                for(j12 = 1; j12 <= 12; ++j12) {
                    this.setAir(world, i12, j12, k12);
                }
            }
        }
        this.loadStrScan("southron_town_gate");
        this.associateBlockMetaAlias("STONE", this.stoneBlock, this.stoneMeta);
        this.associateBlockAlias("STONE_STAIR", this.stoneStairBlock);
        this.associateBlockMetaAlias("BRICK", this.brickBlock, this.brickMeta);
        this.associateBlockMetaAlias("BRICK_SLAB", this.brickSlabBlock, this.brickSlabMeta);
        this.associateBlockMetaAlias("BRICK_SLAB_INV", this.brickSlabBlock, this.brickSlabMeta | 8);
        this.associateBlockAlias("BRICK_STAIR", this.brickStairBlock);
        this.associateBlockMetaAlias("BRICK_WALL", this.brickWallBlock, this.brickWallMeta);
        this.associateBlockMetaAlias("PILLAR", this.pillarBlock, this.pillarMeta);
        this.associateBlockMetaAlias("BRICK2", this.brick2Block, this.brick2Meta);
        this.associateBlockMetaAlias("BRICK2_SLAB", this.brick2SlabBlock, this.brick2SlabMeta);
        this.associateBlockMetaAlias("BRICK2_SLAB_INV", this.brick2SlabBlock, this.brick2SlabMeta | 8);
        this.associateBlockAlias("PLANK_STAIR", this.plankStairBlock);
        this.associateBlockMetaAlias("FENCE", this.fenceBlock, this.fenceMeta);
        this.associateBlockMetaAlias("BEAM", this.woodBeamBlock, this.woodBeamMeta);
        this.associateBlockMetaAlias("BEAM|4", this.woodBeamBlock, this.woodBeamMeta4);
        this.associateBlockAlias("GATE_METAL", this.gateMetalBlock);
        this.generateStrScan(world, random, 0, 0, 0);
        if(this.signText != null) {
            this.placeSign(world, -3, 3, -4, Blocks.wall_sign, 2, this.signText);
            this.placeSign(world, 3, 3, -4, Blocks.wall_sign, 2, this.signText);
        }
        this.placeWallBanner(world, -6, 4, -2, this.bannerType, 2);
        this.placeWallBanner(world, 6, 4, -2, this.bannerType, 2);
        for(step = 0; step < 12 && !this.isOpaque(world, i1 = -7 - step, j1 = 5 - step, k1 = 2); ++step) {
            if(j1 <= 1) {
                this.setBlockAndMetadata(world, i1, j1, k1, this.stoneStairBlock, 1);
            }
            else {
                this.setBlockAndMetadata(world, i1, j1, k1, this.brickStairBlock, 1);
            }
            this.setGrassToDirt(world, i1, j1 - 1, k1);
            j2 = j1 - 1;
            while(!this.isOpaque(world, i1, j2, k1) && this.getY(j2) >= 0) {
                if(j2 <= 1) {
                    this.setBlockAndMetadata(world, i1, j2, k1, this.stoneBlock, this.stoneMeta);
                }
                else {
                    this.setBlockAndMetadata(world, i1, j2, k1, this.brickBlock, this.brickMeta);
                }
                this.setGrassToDirt(world, i1, j2 - 1, k1);
                --j2;
            }
        }
        for(step = 0; step < 12 && !this.isOpaque(world, i1 = 7 + step, j1 = 5 - step, k1 = 2); ++step) {
            if(j1 <= 1) {
                this.setBlockAndMetadata(world, i1, j1, k1, this.stoneStairBlock, 0);
            }
            else {
                this.setBlockAndMetadata(world, i1, j1, k1, this.brickStairBlock, 0);
            }
            this.setGrassToDirt(world, i1, j1 - 1, k1);
            j2 = j1 - 1;
            while(!this.isOpaque(world, i1, j2, k1) && this.getY(j2) >= 0) {
                if(j2 <= 1) {
                    this.setBlockAndMetadata(world, i1, j2, k1, this.stoneBlock, this.stoneMeta);
                }
                else {
                    this.setBlockAndMetadata(world, i1, j2, k1, this.brickBlock, this.brickMeta);
                }
                this.setGrassToDirt(world, i1, j2 - 1, k1);
                --j2;
            }
        }
        return true;
    }
}
