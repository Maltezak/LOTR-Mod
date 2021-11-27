package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.*;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.item.LOTRItemBanner;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class LOTRWorldGenWargPitBase extends LOTRWorldGenStructureBase2 {
    protected Block brickBlock;
    protected int brickMeta;
    protected Block brickSlabBlock;
    protected int brickSlabMeta;
    protected Block brickStairBlock;
    protected Block brickWallBlock;
    protected int brickWallMeta;
    protected Block pillarBlock;
    protected int pillarMeta;
    protected Block plankBlock;
    protected int plankMeta;
    protected Block plankSlabBlock;
    protected int plankSlabMeta;
    protected Block plankStairBlock;
    protected Block fenceBlock;
    protected int fenceMeta;
    protected Block beamBlock;
    protected int beamMeta;
    protected Block doorBlock;
    protected Block woolBlock;
    protected int woolMeta;
    protected Block carpetBlock;
    protected int carpetMeta;
    protected Block barsBlock;
    protected Block gateOrcBlock;
    protected Block gateMetalBlock;
    protected Block tableBlock;
    protected Block bedBlock;
    protected LOTRItemBanner.BannerType banner;
    protected LOTRChestContents chestContents;

    public LOTRWorldGenWargPitBase(boolean flag) {
        super(flag);
    }

    protected abstract LOTREntityNPC getOrc(World var1);

    protected abstract LOTREntityNPC getWarg(World var1);

    protected abstract void setOrcSpawner(LOTREntityNPCRespawner var1);

    protected abstract void setWargSpawner(LOTREntityNPCRespawner var1);

    @Override
    protected void setupRandomBlocks(Random random) {
        this.plankBlock = LOTRMod.planks;
        this.plankMeta = 3;
        this.plankSlabBlock = LOTRMod.woodSlabSingle;
        this.plankSlabMeta = 3;
        this.plankStairBlock = LOTRMod.stairsCharred;
        this.fenceBlock = LOTRMod.fence;
        this.fenceMeta = 3;
        this.beamBlock = LOTRMod.woodBeam1;
        this.beamMeta = 3;
        this.doorBlock = LOTRMod.doorCharred;
        this.woolBlock = Blocks.wool;
        this.woolMeta = 12;
        this.carpetBlock = Blocks.carpet;
        this.carpetMeta = 12;
        this.barsBlock = LOTRMod.orcSteelBars;
        this.gateOrcBlock = LOTRMod.gateOrc;
        this.gateMetalBlock = LOTRMod.gateBronzeBars;
        this.bedBlock = LOTRMod.orcBed;
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int j1;
        int i1;
        int step;
        int j12;
        int j2;
        int k1;
        int i12;
        int k12;
        this.setOriginAndRotation(world, i, j, k, rotation, 8, -10);
        this.originY -= 4;
        this.setupRandomBlocks(random);
        if(this.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for(i12 = -13; i12 <= 12; ++i12) {
                for(k12 = -12; k12 <= 14; ++k12) {
                    j12 = this.getTopBlock(world, i12, k12) - 1;
                    if(!this.isSurface(world, i12, j12, k12)) {
                        return false;
                    }
                    if(j12 < minHeight) {
                        minHeight = j12;
                    }
                    if(j12 <= maxHeight) continue;
                    maxHeight = j12;
                }
            }
            if(maxHeight - minHeight > 12) {
                return false;
            }
        }
        int radius = 8;
        for(int i13 = -radius; i13 <= radius; ++i13) {
            for(int k13 = -radius; k13 <= radius; ++k13) {
                if(i13 * i13 + k13 * k13 >= radius * radius) continue;
                for(int j13 = 0; j13 <= 12; ++j13) {
                    this.setAir(world, i13, j13, k13);
                }
            }
        }
        int r2 = 12;
        for(i12 = -r2; i12 <= r2; ++i12) {
            for(k12 = -r2; k12 <= r2; ++k12) {
                if(i12 * i12 + k12 * k12 >= r2 * r2 || k12 < -4 || i12 > 4) continue;
                for(j12 = 0; j12 <= 12; ++j12) {
                    this.setAir(world, i12, j12, k12);
                }
            }
        }
        for(i12 = -12; i12 <= -8; ++i12) {
            for(k12 = -7; k12 <= -4; ++k12) {
                if(k12 == -7 && (i12 == -12 || i12 == -8)) continue;
                for(j12 = 5; j12 <= 12; ++j12) {
                    this.setAir(world, i12, j12, k12);
                }
            }
        }
        for(i12 = -3; i12 <= 3; ++i12) {
            for(k12 = 8; k12 <= 12; ++k12) {
                for(j12 = 7; j12 <= 11; ++j12) {
                    this.setAir(world, i12, j12, k12);
                }
            }
        }
        for(i12 = -1; i12 <= 1; ++i12) {
            for(k12 = -11; k12 <= -6; ++k12) {
                for(j12 = 0; j12 <= 3; ++j12) {
                    this.setAir(world, i12, j12, k12);
                }
            }
        }
        for(i12 = 6; i12 <= 11; ++i12) {
            for(k12 = -1; k12 <= 1; ++k12) {
                for(j12 = 0; j12 <= 3; ++j12) {
                    this.setAir(world, i12, j12, k12);
                }
            }
        }
        this.loadStrScan("warg_pit");
        this.associateBlockMetaAlias("BRICK", this.brickBlock, this.brickMeta);
        this.associateBlockMetaAlias("BRICK_SLAB_INV", this.brickSlabBlock, this.brickSlabMeta | 8);
        this.associateBlockAlias("BRICK_STAIR", this.brickStairBlock);
        this.associateBlockMetaAlias("BRICK_WALL", this.brickWallBlock, this.brickWallMeta);
        this.associateBlockMetaAlias("PILLAR", this.pillarBlock, this.pillarMeta);
        this.associateBlockMetaAlias("PLANK", this.plankBlock, this.plankMeta);
        this.associateBlockMetaAlias("PLANK_SLAB", this.plankSlabBlock, this.plankSlabMeta);
        this.associateBlockMetaAlias("PLANK_SLAB_INV", this.plankSlabBlock, this.plankSlabMeta | 8);
        this.associateBlockAlias("PLANK_STAIR", this.plankStairBlock);
        this.associateBlockMetaAlias("FENCE", this.fenceBlock, this.fenceMeta);
        this.associateBlockMetaAlias("BEAM", this.beamBlock, this.beamMeta);
        this.associateBlockMetaAlias("BEAM|4", this.beamBlock, this.beamMeta | 4);
        this.associateBlockMetaAlias("BEAM|8", this.beamBlock, this.beamMeta | 8);
        this.associateBlockAlias("DOOR", this.doorBlock);
        this.associateBlockMetaAlias("WOOL", this.woolBlock, this.woolMeta);
        this.associateBlockMetaAlias("CARPET", this.carpetBlock, this.carpetMeta);
        this.associateGroundBlocks();
        this.associateBlockMetaAlias("BARS", this.barsBlock, 0);
        this.associateBlockAlias("GATE_ORC", this.gateOrcBlock);
        this.associateBlockAlias("GATE_METAL", this.gateMetalBlock);
        this.associateBlockMetaAlias("TABLE", this.tableBlock, 0);
        this.generateStrScan(world, random, 0, 0, 0);
        this.placeWallBanner(world, -7, 5, 0, this.banner, 1);
        this.placeWallBanner(world, 7, 5, 0, this.banner, 3);
        this.placeWallBanner(world, 0, 5, -7, this.banner, 0);
        this.placeWallBanner(world, 0, 5, 7, this.banner, 2);
        this.placeOrcTorch(world, 2, 4, -5);
        this.placeOrcTorch(world, -2, 4, -5);
        this.placeOrcTorch(world, 5, 4, -2);
        this.placeOrcTorch(world, -5, 4, -2);
        this.placeOrcTorch(world, 5, 4, 2);
        this.placeOrcTorch(world, -5, 4, 2);
        this.placeOrcTorch(world, 2, 4, 5);
        this.placeOrcTorch(world, -2, 4, 5);
        this.placeOrcTorch(world, 1, 7, 8);
        this.placeOrcTorch(world, -1, 7, 8);
        this.placeOrcTorch(world, 4, 8, -4);
        this.placeOrcTorch(world, -4, 8, -4);
        this.placeOrcTorch(world, 4, 8, 4);
        this.placeOrcTorch(world, -4, 8, 4);
        this.placeOrcTorch(world, -8, 10, -4);
        this.placeOrcTorch(world, -12, 10, -4);
        this.placeChest(world, random, -7, 1, 0, 4, this.chestContents);
        this.placeChest(world, random, 1, 7, 12, 2, this.chestContents);
        this.setBlockAndMetadata(world, -2, 7, 9, this.bedBlock, 3);
        this.setBlockAndMetadata(world, -3, 7, 9, this.bedBlock, 11);
        this.setBlockAndMetadata(world, -2, 7, 11, this.bedBlock, 3);
        this.setBlockAndMetadata(world, -3, 7, 11, this.bedBlock, 11);
        this.placeBarrel(world, random, 3, 8, 11, 5, LOTRFoods.ORC_DRINK);
        this.placeMug(world, random, 3, 8, 10, 1, LOTRFoods.ORC_DRINK);
        this.placePlateWithCertainty(world, random, 3, 8, 9, LOTRMod.woodPlateBlock, LOTRFoods.ORC);
        int maxStep = 12;
        for(i1 = -1; i1 <= 1; ++i1) {
            for(step = 0; step < 2 && !this.isSideSolid(world, i1, j1 = 5 - step, k1 = -9 - step, ForgeDirection.UP); ++step) {
                this.setBlockAndMetadata(world, i1, j1, k1, this.brickStairBlock, 2);
                this.setGrassToDirt(world, i1, j1 - 1, k1);
                j2 = j1 - 1;
                while(!this.isSideSolid(world, i1, j2, k1, ForgeDirection.UP) && this.getY(j2) >= 0) {
                    this.setBlockAndMetadata(world, i1, j2, k1, this.brickBlock, this.brickMeta);
                    this.setGrassToDirt(world, i1, j2 - 1, k1);
                    --j2;
                }
            }
        }
        for(i1 = -1; i1 <= 1; ++i1) {
            for(step = 0; step < maxStep && !this.isOpaque(world, i1, j1 = 3 - step, k1 = -13 - step); ++step) {
                this.setBlockAndMetadata(world, i1, j1, k1, this.brickStairBlock, 2);
                this.setGrassToDirt(world, i1, j1 - 1, k1);
                j2 = j1 - 1;
                while(!this.isOpaque(world, i1, j2, k1) && this.getY(j2) >= 0) {
                    this.setBlockAndMetadata(world, i1, j2, k1, this.brickBlock, this.brickMeta);
                    this.setGrassToDirt(world, i1, j2 - 1, k1);
                    --j2;
                }
            }
        }
        int wargs = 2 + random.nextInt(5);
        for(int l = 0; l < wargs; ++l) {
            LOTREntityNPC warg = this.getWarg(world);
            this.spawnNPCAndSetHome(warg, world, 0, 1, 0, 8);
        }
        LOTREntityNPC orc = this.getOrc(world);
        this.spawnNPCAndSetHome(orc, world, 0, 1, 0, 24);
        LOTREntityNPCRespawner wargSpawner = new LOTREntityNPCRespawner(world);
        this.setWargSpawner(wargSpawner);
        wargSpawner.setCheckRanges(12, -8, 16, 8);
        wargSpawner.setSpawnRanges(4, -4, 4, 24);
        this.placeNPCRespawner(wargSpawner, world, 0, 0, 0);
        LOTREntityNPCRespawner orcSpawner = new LOTREntityNPCRespawner(world);
        this.setOrcSpawner(orcSpawner);
        orcSpawner.setCheckRanges(32, -12, 20, 16);
        orcSpawner.setSpawnRanges(16, -4, 8, 16);
        this.placeNPCRespawner(orcSpawner, world, 0, 0, 0);
        return true;
    }

    protected void associateGroundBlocks() {
        this.addBlockMetaAliasOption("GROUND", 4, Blocks.dirt, 1);
        this.addBlockMetaAliasOption("GROUND", 4, LOTRMod.dirtPath, 0);
        this.addBlockMetaAliasOption("GROUND", 4, Blocks.gravel, 0);
        this.addBlockMetaAliasOption("GROUND", 4, Blocks.cobblestone, 0);
        this.addBlockMetaAliasOption("GROUND_SLAB", 4, LOTRMod.slabSingleDirt, 0);
        this.addBlockMetaAliasOption("GROUND_SLAB", 4, LOTRMod.slabSingleDirt, 1);
        this.addBlockMetaAliasOption("GROUND_SLAB", 4, LOTRMod.slabSingleGravel, 0);
        this.addBlockMetaAliasOption("GROUND_SLAB", 4, Blocks.stone_slab, 3);
        this.addBlockMetaAliasOption("GROUND_COVER", 1, LOTRMod.thatchFloor, 0);
        this.setBlockAliasChance("GROUND_COVER", 0.25f);
    }

    @Override
    protected void placeOrcTorch(World world, int i, int j, int k) {
        this.setBlockAndMetadata(world, i, j, k, LOTRMod.orcTorch, 0);
        this.setBlockAndMetadata(world, i, j + 1, k, LOTRMod.orcTorch, 1);
    }
}
