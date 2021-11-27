package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.*;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.*;
import lotr.common.item.LOTRItemBanner;
import lotr.common.tileentity.LOTRTileEntityAlloyForge;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.init.*;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class LOTRWorldGenDwarvenTower extends LOTRWorldGenStructureBase2 {
    protected Block brickBlock = LOTRMod.brick;
    protected int brickMeta = 6;
    protected Block brickSlabBlock = LOTRMod.slabSingle;
    protected int brickSlabMeta = 7;
    protected Block brickStairBlock = LOTRMod.stairsDwarvenBrick;
    protected Block brickWallBlock = LOTRMod.wall;
    protected int brickWallMeta = 7;
    protected Block pillarBlock = LOTRMod.pillar;
    protected int pillarMeta = 0;
    protected Block plankBlock;
    protected int plankMeta;
    protected Block plankSlabBlock;
    protected int plankSlabMeta;
    protected Block barsBlock = LOTRMod.dwarfBars;
    protected Block gateBlock = LOTRMod.gateDwarven;
    protected Block tableBlock = LOTRMod.dwarvenTable;
    protected Block forgeBlock = LOTRMod.dwarvenForge;
    protected Block glowBrickBlock = LOTRMod.brick3;
    protected int glowBrickMeta = 12;
    protected Block plateBlock;
    protected LOTRItemBanner.BannerType bannerType = LOTRItemBanner.BannerType.DWARF;
    protected LOTRChestContents chestContents = LOTRChestContents.DWARVEN_TOWER;
    protected boolean ruined = false;

    public LOTRWorldGenDwarvenTower(boolean flag) {
        super(flag);
    }

    protected LOTREntityNPC getCommanderNPC(World world) {
        return new LOTREntityDwarfCommander(world);
    }

    @Override
    protected void setupRandomBlocks(Random random) {
        int randomWood = random.nextInt(4);
        if(randomWood == 0) {
            this.plankBlock = Blocks.planks;
            this.plankMeta = 1;
            this.plankSlabBlock = Blocks.wooden_slab;
            this.plankSlabMeta = 1;
        }
        else if(randomWood == 1) {
            this.plankBlock = LOTRMod.planks;
            this.plankMeta = 13;
            this.plankSlabBlock = LOTRMod.woodSlabSingle2;
            this.plankSlabMeta = 5;
        }
        else if(randomWood == 2) {
            this.plankBlock = LOTRMod.planks2;
            this.plankMeta = 4;
            this.plankSlabBlock = LOTRMod.woodSlabSingle3;
            this.plankSlabMeta = 4;
        }
        else if(randomWood == 3) {
            this.plankBlock = LOTRMod.planks2;
            this.plankMeta = 3;
            this.plankSlabBlock = LOTRMod.woodSlabSingle3;
            this.plankSlabMeta = 3;
        }
        this.plateBlock = random.nextBoolean() ? LOTRMod.ceramicPlateBlock : LOTRMod.woodPlateBlock;
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int i1;
        int j1;
        int j12;
        int k1;
        int k12;
        LOTREntityNPC commander;
        this.setOriginAndRotation(world, i, j, k, rotation, 6);
        this.setupRandomBlocks(random);
        int sections = 5 + random.nextInt(3);
        if(this.restrictions) {
            for(i1 = -6; i1 <= 6; ++i1) {
                for(k1 = -6; k1 <= 6; ++k1) {
                    j1 = this.getTopBlock(world, i1, k1);
                    Block block = this.getBlock(world, i1, j1 - 1, k1);
                    if(block == Blocks.grass || block == Blocks.stone || block == Blocks.snow) continue;
                    return false;
                }
            }
        }
        for(i1 = -5; i1 <= 5; ++i1) {
            for(k1 = -5; k1 <= 5; ++k1) {
                for(j1 = 0; (((j1 == 0) || !this.isOpaque(world, i1, j1, k1)) && (this.getY(j1) >= 0)); --j1) {
                    this.placeBrick(world, random, i1, j1, k1);
                    this.setGrassToDirt(world, i1, j1 - 1, k1);
                }
            }
        }
        for(i1 = -4; i1 <= 4; ++i1) {
            for(k1 = -4; k1 <= 4; ++k1) {
                boolean flag = true;
                if(this.ruined) {
                    flag = random.nextInt(12) != 0;
                }
                if(!flag) continue;
                this.setBlockAndMetadata(world, i1, 0, k1, this.plankBlock, this.plankMeta);
            }
        }
        for(int l = 0; l <= sections; ++l) {
            int k13;
            int j13;
            int k14;
            int i12;
            int j14;
            int i13;
            int k2;
            int sectionBase = l * 5;
            for(i13 = -4; i13 <= 4; ++i13) {
                for(j14 = sectionBase + 1; j14 <= sectionBase + 5; ++j14) {
                    for(k13 = -4; k13 <= 4; ++k13) {
                        this.setAir(world, i13, j14, k13);
                        this.setAir(world, i13, j14, k13);
                    }
                }
            }
            for(j1 = sectionBase + 1; j1 <= sectionBase + 5; ++j1) {
                boolean flag;
                for(int i14 = -5; i14 <= 5; ++i14) {
                    for(int k15 : new int[] {-5, 5}) {
                        flag = true;
                        if(this.ruined) {
                            flag = random.nextInt(20) != 0;
                        }
                        if(!flag) continue;
                        this.placeBrick(world, random, i14, j1, k15);
                    }
                }
                for(k14 = -4; k14 <= 4; ++k14) {
                    for(int i15 : new int[] {-5, 5}) {
                        flag = true;
                        if(this.ruined) {
                            flag = random.nextInt(20) != 0;
                        }
                        if(!flag) continue;
                        this.placeBrick(world, random, i15, j1, k14);
                    }
                }
            }
            this.placePillar(world, random, -4, sectionBase + 1, -4);
            this.placePillar(world, random, -4, sectionBase + 2, -4);
            this.setBlockAndMetadata(world, -4, sectionBase + 3, -4, this.glowBrickBlock, this.glowBrickMeta);
            this.placePillar(world, random, -4, sectionBase + 4, -4);
            this.placePillar(world, random, -4, sectionBase + 1, 4);
            this.placePillar(world, random, -4, sectionBase + 2, 4);
            this.setBlockAndMetadata(world, -4, sectionBase + 3, 4, this.glowBrickBlock, this.glowBrickMeta);
            this.placePillar(world, random, -4, sectionBase + 4, 4);
            this.placePillar(world, random, 4, sectionBase + 1, -4);
            this.placePillar(world, random, 4, sectionBase + 2, -4);
            this.setBlockAndMetadata(world, 4, sectionBase + 3, -4, this.glowBrickBlock, this.glowBrickMeta);
            this.placePillar(world, random, 4, sectionBase + 4, -4);
            this.placePillar(world, random, 4, sectionBase + 1, 4);
            this.placePillar(world, random, 4, sectionBase + 2, 4);
            this.setBlockAndMetadata(world, 4, sectionBase + 3, 4, this.glowBrickBlock, this.glowBrickMeta);
            this.placePillar(world, random, 4, sectionBase + 4, 4);
            for(i13 = -4; i13 <= 4; ++i13) {
                for(k14 = -4; k14 <= 4; ++k14) {
                    boolean flag = true;
                    if(this.ruined) {
                        flag = random.nextInt(12) != 0;
                    }
                    if(!flag) continue;
                    this.setBlockAndMetadata(world, i13, sectionBase + 5, k14, this.plankBlock, this.plankMeta);
                }
            }
            for(k12 = -2; k12 <= 2; ++k12) {
                for(j14 = sectionBase + 1; j14 <= sectionBase + 4; ++j14) {
                    if(Math.abs(k12) < 2 && (j14 == sectionBase + 2 || j14 == sectionBase + 3)) {
                        this.setBlockAndMetadata(world, -5, j14, k12, this.barsBlock, 0);
                        this.setBlockAndMetadata(world, 5, j14, k12, this.barsBlock, 0);
                        continue;
                    }
                    this.placePillar(world, random, -5, j14, k12);
                    this.placePillar(world, random, 5, j14, k12);
                }
            }
            int randomFeature = random.nextInt(5);
            if(l % 2 == 0) {
                for(k14 = -1; k14 <= 4; ++k14) {
                    for(i12 = 1; i12 <= 2; ++i12) {
                        this.setAir(world, i12, sectionBase + 5, k14);
                        k2 = k14 - -1;
                        for(j13 = sectionBase + 1; j13 <= sectionBase + k2; ++j13) {
                            this.placeBrick(world, random, i12, j13, k14);
                        }
                        if(k2 >= 5) continue;
                        this.placeBrickStair(world, random, i12, sectionBase + k2 + 1, k14, 2);
                    }
                }
                this.placeRandomFeature(world, random, -2, sectionBase + 1, 4, randomFeature, false);
                this.placeRandomFeature(world, random, -1, sectionBase + 1, 4, randomFeature, false);
                this.setBlockAndMetadata(world, 0, sectionBase + 1, 4, this.plankBlock, this.plankMeta);
                this.setBlockAndMetadata(world, -3, sectionBase + 1, 4, this.plankBlock, this.plankMeta);
                this.setBlockAndMetadata(world, 0, sectionBase + 2, 4, this.plankSlabBlock, this.plankSlabMeta);
                this.setBlockAndMetadata(world, -3, sectionBase + 2, 4, this.plankSlabBlock, this.plankSlabMeta);
            }
            else {
                for(k14 = -4; k14 <= 1; ++k14) {
                    for(i12 = -2; i12 <= -1; ++i12) {
                        this.setAir(world, i12, sectionBase + 5, k14);
                        k2 = 5 - (k14 - -4);
                        for(j13 = sectionBase + 1; j13 <= sectionBase + k2; ++j13) {
                            this.placeBrick(world, random, i12, j13, k14);
                        }
                        if(k2 >= 5) continue;
                        this.placeBrickStair(world, random, i12, sectionBase + k2 + 1, k14, 3);
                    }
                }
                this.placeRandomFeature(world, random, 2, sectionBase + 1, -4, randomFeature, true);
                this.placeRandomFeature(world, random, 1, sectionBase + 1, -4, randomFeature, true);
                this.setBlockAndMetadata(world, 0, sectionBase + 1, -4, this.plankBlock, this.plankMeta);
                this.setBlockAndMetadata(world, 3, sectionBase + 1, -4, this.plankBlock, this.plankMeta);
                this.setBlockAndMetadata(world, 0, sectionBase + 2, -4, this.plankSlabBlock, this.plankSlabMeta);
                this.setBlockAndMetadata(world, 3, sectionBase + 2, -4, this.plankSlabBlock, this.plankSlabMeta);
            }
            if(this.ruined) continue;
            LOTREntityDwarfWarrior dwarf = random.nextInt(3) == 0 ? new LOTREntityDwarfAxeThrower(world) : new LOTREntityDwarfWarrior(world);
            this.spawnNPCAndSetHome(dwarf, world, 0, sectionBase + 1, 0, 12);
        }
        for(i1 = -4; i1 <= 4; ++i1) {
            for(j12 = 1; j12 <= 2; ++j12) {
                for(k12 = -4; k12 <= 4; ++k12) {
                    this.setAir(world, i1, (sections + 1) * 5 + j12, k12);
                }
            }
        }
        for(i1 = -4; i1 <= 4; ++i1) {
            this.placeBrickWall(world, random, i1, (sections + 1) * 5 + 1, -5);
            this.placeBrickWall(world, random, i1, (sections + 1) * 5 + 1, 5);
        }
        for(int k16 = -4; k16 <= 4; ++k16) {
            this.placeBrickWall(world, random, -5, (sections + 1) * 5 + 1, k16);
            this.placeBrickWall(world, random, 5, (sections + 1) * 5 + 1, k16);
        }
        this.generateCornerPillars(world, random, -5, (sections + 1) * 5 + 5, -5);
        this.generateCornerPillars(world, random, -5, (sections + 1) * 5 + 5, 6);
        this.generateCornerPillars(world, random, 6, (sections + 1) * 5 + 5, -5);
        this.generateCornerPillars(world, random, 6, (sections + 1) * 5 + 5, 6);
        for(i1 = -1; i1 <= 1; ++i1) {
            this.placePillar(world, random, i1, 0, -5);
            for(j12 = 1; j12 <= 4; ++j12) {
                this.setBlockAndMetadata(world, i1, j12, -5, this.gateBlock, 2);
            }
        }
        for(int i14 : new int[] {-2, 2}) {
            int j15 = 4;
            while(!this.isOpaque(world, i14, j15, -6) && this.getY(j15) >= 0) {
                if(j15 == 3) {
                    this.setBlockAndMetadata(world, i14, j15, -6, this.glowBrickBlock, this.glowBrickMeta);
                }
                else {
                    this.placePillar(world, random, i14, j15, -6);
                }
                this.setGrassToDirt(world, i14, j15 - 1, -6);
                --j15;
            }
        }
        for(int i16 = -2; i16 <= 2; ++i16) {
            this.placeBrickSlab(world, random, i16, 5, -6, false);
        }
        if(this.bannerType != null) {
            this.placeWallBanner(world, -2, 7, -5, this.bannerType, 2);
            this.placeWallBanner(world, 0, 8, -5, this.bannerType, 2);
            this.placeWallBanner(world, 2, 7, -5, this.bannerType, 2);
        }
        if((commander = this.getCommanderNPC(world)) != null) {
            this.spawnNPCAndSetHome(commander, world, 0, (sections + 1) * 5 + 1, 0, 16);
            if(sections % 2 == 0) {
                this.setBlockAndMetadata(world, -3, (sections + 1) * 5 + 1, -3, LOTRMod.commandTable, 0);
            }
            else {
                this.setBlockAndMetadata(world, 3, (sections + 1) * 5 + 1, 3, LOTRMod.commandTable, 0);
            }
        }
        this.placePillar(world, random, -4, (sections + 1) * 5 + 1, 0);
        this.placePillar(world, random, -4, (sections + 1) * 5 + 2, 0);
        this.placePillar(world, random, 4, (sections + 1) * 5 + 1, 0);
        this.placePillar(world, random, 4, (sections + 1) * 5 + 2, 0);
        if(this.bannerType != null) {
            this.placeBrick(world, random, -4, (sections + 1) * 5 + 1, 0);
            this.placeBanner(world, -4, (sections + 1) * 5 + 1, 0, this.bannerType, 1);
            this.placeBrick(world, random, 4, (sections + 1) * 5 + 1, 0);
            this.placeBanner(world, 4, (sections + 1) * 5 + 1, 0, this.bannerType, 3);
        }
        if(!this.ruined) {
            LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
            respawner.setSpawnClasses(LOTREntityDwarfWarrior.class, LOTREntityDwarfAxeThrower.class);
            respawner.setCheckRanges(12, -8, 42, 16);
            respawner.setSpawnRanges(4, 1, 41, 12);
            this.placeNPCRespawner(respawner, world, 0, 0, 0);
        }
        return true;
    }

    protected void placeBrick(World world, Random random, int i, int j, int k) {
        this.setBlockAndMetadata(world, i, j, k, this.brickBlock, this.brickMeta);
    }

    protected void placeBrickSlab(World world, Random random, int i, int j, int k, boolean flip) {
        this.setBlockAndMetadata(world, i, j, k, this.brickSlabBlock, this.brickSlabMeta | (flip ? 8 : 0));
    }

    protected void placeBrickStair(World world, Random random, int i, int j, int k, int meta) {
        this.setBlockAndMetadata(world, i, j, k, this.brickStairBlock, meta);
    }

    protected void placeBrickWall(World world, Random random, int i, int j, int k) {
        this.setBlockAndMetadata(world, i, j, k, this.brickWallBlock, this.brickWallMeta);
    }

    protected void placePillar(World world, Random random, int i, int j, int k) {
        this.setBlockAndMetadata(world, i, j, k, this.pillarBlock, this.pillarMeta);
    }

    private void generateCornerPillars(World world, Random random, int i, int j, int k) {
        for(int i1 = i - 1; i1 <= i; ++i1) {
            for(int k1 = k - 1; k1 <= k; ++k1) {
                for(int j1 = j; (((j1 == 0) || !this.isOpaque(world, i1, j1, k1)) && (this.getY(j1) >= 0)); --j1) {
                    if(j1 == j - 2) {
                        this.setBlockAndMetadata(world, i1, j1, k1, this.glowBrickBlock, this.glowBrickMeta);
                        continue;
                    }
                    this.placePillar(world, random, i1, j1, k1);
                    this.setGrassToDirt(world, i1, j1 - 1, k1);
                }
            }
        }
    }

    private void placeRandomFeature(World world, Random random, int i, int j, int k, int randomFeature, boolean flip) {
        if(randomFeature == 0) {
            this.setBlockAndMetadata(world, i, j, k, this.tableBlock, 0);
        }
        else if(randomFeature == 1) {
            this.setBlockAndMetadata(world, i, j, k, this.forgeBlock, flip ? 3 : 2);
            TileEntity tileentity = this.getTileEntity(world, i, j, k);
            if(tileentity instanceof LOTRTileEntityAlloyForge) {
                ((LOTRTileEntityAlloyForge) tileentity).setInventorySlotContents(12, new ItemStack(Items.coal, 1 + random.nextInt(4)));
            }
        }
        else if(randomFeature == 2) {
            this.setBlockAndMetadata(world, i, j, k, this.plankSlabBlock, this.plankSlabMeta | 8);
            this.placeChest(world, random, i, j + 1, k, flip ? 3 : 2, this.chestContents);
        }
        else if(randomFeature == 3) {
            this.setBlockAndMetadata(world, i, j, k, this.plankSlabBlock, this.plankSlabMeta | 8);
            if(!this.ruined) {
                this.placePlateWithCertainty(world, random, i, j + 1, k, this.plateBlock, LOTRFoods.DWARF);
            }
        }
        else if(randomFeature == 4) {
            this.setBlockAndMetadata(world, i, j, k, this.plankSlabBlock, this.plankSlabMeta | 8);
            if(!this.ruined) {
                this.placeBarrel(world, random, i, j + 1, k, flip ? 3 : 2, LOTRFoods.DWARF_DRINK);
            }
        }
    }
}
