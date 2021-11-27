package lotr.common.world.structure2;

import java.util.Random;

import com.google.common.math.IntMath;

import lotr.common.*;
import lotr.common.entity.npc.LOTREntityDorwinionElf;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.*;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class LOTRWorldGenDorwinionElfHouse extends LOTRWorldGenDorwinionHouse {
    private Block grapevineBlock;
    private Item wineItem;
    private Item grapeItem;

    public LOTRWorldGenDorwinionElfHouse(boolean flag) {
        super(flag);
    }

    @Override
    protected void setupRandomBlocks(Random random) {
        super.setupRandomBlocks(random);
        if(random.nextBoolean()) {
            this.grapevineBlock = LOTRMod.grapevineRed;
            this.wineItem = LOTRMod.mugRedWine;
            this.grapeItem = LOTRMod.grapeRed;
        }
        else {
            this.grapevineBlock = LOTRMod.grapevineWhite;
            this.wineItem = LOTRMod.mugWhiteWine;
            this.grapeItem = LOTRMod.grapeWhite;
        }
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int k1;
        int j1;
        int k2;
        int k12;
        int k13;
        int i1;
        int i12;
        int i13;
        int j12;
        int i14;
        this.setOriginAndRotation(world, i, j, k, rotation, 1);
        this.setupRandomBlocks(random);
        if(this.restrictions) {
            for(int i15 = -4; i15 <= 8; ++i15) {
                for(k13 = -1; k13 <= 20; ++k13) {
                    j1 = this.getTopBlock(world, i15, k13) - 1;
                    Block block = this.getBlock(world, i15, j1, k13);
                    if(block == Blocks.grass) continue;
                    return false;
                }
            }
        }
        boolean generateBackGate = true;
        for(i12 = 1; i12 <= 3; ++i12) {
            k12 = 20;
            j12 = this.getTopBlock(world, i12, k12) - 1;
            if(j12 == 0) continue;
            generateBackGate = false;
        }
        for(i12 = -4; i12 <= 8; ++i12) {
            for(k12 = 0; k12 <= 20; ++k12) {
                for(j12 = 1; j12 <= 6; ++j12) {
                    this.setAir(world, i12, j12, k12);
                }
                this.setBlockAndMetadata(world, i12, 0, k12, Blocks.grass, 0);
                j12 = -1;
                while(!this.isOpaque(world, i12, j12, k12) && this.getY(j12) >= 0) {
                    this.setBlockAndMetadata(world, i12, j12, k12, Blocks.dirt, 0);
                    this.setGrassToDirt(world, i12, j12 - 1, k12);
                    --j12;
                }
            }
        }
        for(i12 = -3; i12 <= 7; ++i12) {
            for(k12 = 0; k12 <= 8; ++k12) {
                if(i12 >= 3 && k12 <= 2) {
                    if(random.nextInt(3) != 0) continue;
                    BiomeGenBase biome = this.getBiome(world, i12, k12);
                    int j13 = 1;
                    biome.plantFlower(world, random, this.getX(i12, k12), this.getY(j13), this.getZ(i12, k12));
                    continue;
                }
                if(k12 == 0 && (i12 == -3 || i12 == 2) || k12 == 3 && (i12 == 2 || i12 == 7) || k12 == 8 && (i12 == -3 || i12 == 7)) {
                    for(j12 = 0; j12 <= 4; ++j12) {
                        this.setBlockAndMetadata(world, i12, j12, k12, this.woodBeamBlock, this.woodBeamMeta);
                    }
                    continue;
                }
                if(i12 == -3 || i12 == 2 && k12 <= 3 || i12 == 7 || k12 == 0 || k12 == 3 && i12 >= 2 || k12 == 8) {
                    for(j12 = 0; j12 <= 1; ++j12) {
                        this.setBlockAndMetadata(world, i12, j12, k12, this.wallBlock, this.wallMeta);
                    }
                    for(j12 = 2; j12 <= 4; ++j12) {
                        this.setBlockAndMetadata(world, i12, j12, k12, this.brickBlock, this.brickMeta);
                    }
                    continue;
                }
                this.setBlockAndMetadata(world, i12, 0, k12, this.floorBlock, this.floorMeta);
            }
        }
        for(k13 = 1; k13 <= 7; ++k13) {
            k2 = IntMath.mod(k13, 3);
            if(k2 == 1) {
                this.setBlockAndMetadata(world, -4, 1, k13, this.brickStairBlock, 1);
                this.setGrassToDirt(world, -4, 0, k13);
                continue;
            }
            if(k2 == 2) {
                this.setAir(world, -3, 2, k13);
                this.setBlockAndMetadata(world, -3, 3, k13, this.brickStairBlock, 7);
                this.setBlockAndMetadata(world, -4, 1, k13, this.leafBlock, this.leafMeta);
                continue;
            }
            if(k2 != 0) continue;
            this.setAir(world, -3, 2, k13);
            this.setBlockAndMetadata(world, -3, 3, k13, this.brickStairBlock, 6);
            this.setBlockAndMetadata(world, -4, 1, k13, this.leafBlock, this.leafMeta);
        }
        for(int k14 : new int[] {0, 8}) {
            this.setAir(world, -1, 2, k14);
            this.setAir(world, 0, 2, k14);
            this.setBlockAndMetadata(world, -1, 3, k14, this.brickStairBlock, 4);
            this.setBlockAndMetadata(world, 0, 3, k14, this.brickStairBlock, 5);
        }
        for(int k14 : new int[] {3, 8}) {
            this.setAir(world, 4, 2, k14);
            this.setAir(world, 5, 2, k14);
            this.setBlockAndMetadata(world, 4, 3, k14, this.brickStairBlock, 4);
            this.setBlockAndMetadata(world, 5, 3, k14, this.brickStairBlock, 5);
        }
        this.setBlockAndMetadata(world, 3, 1, 2, this.brickStairBlock, 2);
        this.setGrassToDirt(world, 3, 0, 2);
        this.setBlockAndMetadata(world, 4, 1, 2, this.leafBlock, this.leafMeta);
        this.setBlockAndMetadata(world, 5, 1, 2, this.leafBlock, this.leafMeta);
        this.setBlockAndMetadata(world, 6, 1, 2, this.brickStairBlock, 2);
        this.setGrassToDirt(world, 6, 0, 2);
        this.setBlockAndMetadata(world, 8, 1, 4, this.brickStairBlock, 0);
        this.setGrassToDirt(world, 8, 0, 4);
        this.setBlockAndMetadata(world, 8, 1, 5, this.leafBlock, this.leafMeta);
        this.setBlockAndMetadata(world, 8, 1, 6, this.leafBlock, this.leafMeta);
        this.setBlockAndMetadata(world, 8, 1, 7, this.brickStairBlock, 0);
        this.setGrassToDirt(world, 8, 0, 7);
        this.setAir(world, 7, 2, 5);
        this.setAir(world, 7, 2, 6);
        this.setBlockAndMetadata(world, 7, 3, 5, this.brickStairBlock, 7);
        this.setBlockAndMetadata(world, 7, 3, 6, this.brickStairBlock, 6);
        for(int i16 : new int[] {-1, 0}) {
            this.setBlockAndMetadata(world, i16, 0, 0, this.floorBlock, this.floorMeta);
            this.setAir(world, i16, 1, 0);
        }
        for(i1 = -3; i1 <= 2; ++i1) {
            this.setBlockAndMetadata(world, i1, 4, -1, this.brickStairBlock, 6);
        }
        for(k1 = -1; k1 <= 2; ++k1) {
            this.setBlockAndMetadata(world, 3, 4, k1, this.brickStairBlock, 4);
            if(IntMath.mod(k1, 2) != 1) continue;
            this.setBlockAndMetadata(world, 3, 5, k1, this.brickSlabBlock, this.brickSlabMeta);
        }
        for(i1 = 4; i1 <= 8; ++i1) {
            this.setBlockAndMetadata(world, i1, 4, 2, this.brickStairBlock, 6);
            if(IntMath.mod(i1, 2) != 0) continue;
            this.setBlockAndMetadata(world, i1, 5, 2, this.brickSlabBlock, this.brickSlabMeta);
        }
        for(k1 = 3; k1 <= 8; ++k1) {
            this.setBlockAndMetadata(world, 8, 4, k1, this.brickStairBlock, 4);
        }
        for(i1 = 8; i1 >= -4; --i1) {
            this.setBlockAndMetadata(world, i1, 4, 9, this.brickStairBlock, 7);
            if(IntMath.mod(i1, 2) != 0) continue;
            this.setBlockAndMetadata(world, i1, 5, 9, this.brickSlabBlock, this.brickSlabMeta);
        }
        for(k1 = 8; k1 >= -1; --k1) {
            this.setBlockAndMetadata(world, -4, 4, k1, this.brickStairBlock, 5);
            if(IntMath.mod(k1, 2) != 1) continue;
            this.setBlockAndMetadata(world, -4, 5, k1, this.brickSlabBlock, this.brickSlabMeta);
        }
        for(k1 = 1; k1 <= 7; ++k1) {
            this.setBlockAndMetadata(world, -2, 4, k1, this.plankSlabBlock, this.plankSlabMeta | 8);
            if(k1 <= 3) {
                this.setBlockAndMetadata(world, 1, 4, k1, this.plankSlabBlock, this.plankSlabMeta | 8);
            }
            if(k1 < 4) continue;
            this.setBlockAndMetadata(world, 2, 4, k1, this.plankSlabBlock, this.plankSlabMeta | 8);
        }
        for(i1 = -2; i1 <= 6; ++i1) {
            this.setBlockAndMetadata(world, i1, 4, 7, this.plankSlabBlock, this.plankSlabMeta | 8);
            if(i1 <= 1) {
                this.setBlockAndMetadata(world, i1, 4, 3, this.plankSlabBlock, this.plankSlabMeta | 8);
            }
            if(i1 < 2) continue;
            this.setBlockAndMetadata(world, i1, 4, 4, this.plankSlabBlock, this.plankSlabMeta | 8);
        }
        for(k1 = 1; k1 <= 6; ++k1) {
            this.setBlockAndMetadata(world, -2, 5, k1, this.plankStairBlock, 4);
            if(k1 <= 5) {
                this.setBlockAndMetadata(world, -1, 6, k1, this.plankStairBlock, 4);
            }
            if(k1 <= 4) {
                this.setBlockAndMetadata(world, 0, 6, k1, this.plankStairBlock, 5);
            }
            if(k1 > 3) continue;
            this.setBlockAndMetadata(world, 1, 5, k1, this.plankStairBlock, 5);
        }
        for(i1 = -2; i1 <= 6; ++i1) {
            this.setBlockAndMetadata(world, i1, 5, 7, this.plankStairBlock, 6);
            if(i1 >= -1) {
                this.setBlockAndMetadata(world, i1, 6, 6, this.plankStairBlock, 6);
            }
            if(i1 >= 0) {
                this.setBlockAndMetadata(world, i1, 6, 5, this.plankStairBlock, 7);
            }
            if(i1 < 1) continue;
            this.setBlockAndMetadata(world, i1, 5, 4, this.plankStairBlock, 7);
        }
        this.setBlockAndMetadata(world, -2, 5, 0, this.plankBlock, this.plankMeta);
        this.setBlockAndMetadata(world, -1, 5, 0, this.plankStairBlock, 4);
        this.setBlockAndMetadata(world, -1, 6, 0, this.plankBlock, this.plankMeta);
        this.setBlockAndMetadata(world, 0, 6, 0, this.plankBlock, this.plankMeta);
        this.setBlockAndMetadata(world, 0, 5, 0, this.plankStairBlock, 5);
        this.setBlockAndMetadata(world, 1, 5, 0, this.plankBlock, this.plankMeta);
        this.setBlockAndMetadata(world, 7, 5, 4, this.plankBlock, this.plankMeta);
        this.setBlockAndMetadata(world, 7, 5, 5, this.plankStairBlock, 7);
        this.setBlockAndMetadata(world, 7, 6, 5, this.plankBlock, this.plankMeta);
        this.setBlockAndMetadata(world, 7, 6, 6, this.plankBlock, this.plankMeta);
        this.setBlockAndMetadata(world, 7, 5, 6, this.plankStairBlock, 6);
        this.setBlockAndMetadata(world, 7, 5, 7, this.plankBlock, this.plankMeta);
        for(k1 = -1; k1 <= 7; ++k1) {
            this.setBlockAndMetadata(world, -3, 5, k1, this.clayStairBlock, 1);
            if(k1 <= 6) {
                this.setBlockAndMetadata(world, -2, 6, k1, this.clayStairBlock, 1);
            }
            if(k1 <= 5) {
                this.setBlockAndMetadata(world, -1, 7, k1, this.clayStairBlock, 1);
            }
            if(k1 <= 4) {
                this.setBlockAndMetadata(world, 0, 7, k1, this.clayStairBlock, 0);
            }
            if(k1 <= 3) {
                this.setBlockAndMetadata(world, 1, 6, k1, this.clayStairBlock, 0);
            }
            if(k1 > 2) continue;
            this.setBlockAndMetadata(world, 2, 5, k1, this.clayStairBlock, 0);
        }
        for(i1 = -3; i1 <= 8; ++i1) {
            this.setBlockAndMetadata(world, i1, 5, 8, this.clayStairBlock, 3);
            if(i1 >= -2) {
                this.setBlockAndMetadata(world, i1, 6, 7, this.clayStairBlock, 3);
            }
            if(i1 >= -1) {
                this.setBlockAndMetadata(world, i1, 7, 6, this.clayStairBlock, 3);
            }
            if(i1 >= 0) {
                this.setBlockAndMetadata(world, i1, 7, 5, this.clayStairBlock, 2);
            }
            if(i1 >= 1) {
                this.setBlockAndMetadata(world, i1, 6, 4, this.clayStairBlock, 2);
            }
            if(i1 < 2) continue;
            this.setBlockAndMetadata(world, i1, 5, 3, this.clayStairBlock, 2);
        }
        this.setBlockAndMetadata(world, -2, 5, -1, this.clayStairBlock, 4);
        this.setBlockAndMetadata(world, -1, 6, -1, this.clayStairBlock, 4);
        this.setBlockAndMetadata(world, 0, 6, -1, this.clayStairBlock, 5);
        this.setBlockAndMetadata(world, 1, 5, -1, this.clayStairBlock, 5);
        this.setBlockAndMetadata(world, 8, 5, 4, this.clayStairBlock, 7);
        this.setBlockAndMetadata(world, 8, 6, 5, this.clayStairBlock, 7);
        this.setBlockAndMetadata(world, 8, 6, 6, this.clayStairBlock, 6);
        this.setBlockAndMetadata(world, 8, 5, 7, this.clayStairBlock, 6);
        this.setBlockAndMetadata(world, -2, 3, 1, Blocks.torch, 3);
        this.setBlockAndMetadata(world, -2, 3, 4, Blocks.torch, 2);
        this.setBlockAndMetadata(world, -2, 3, 7, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 6, 3, 7, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 6, 3, 4, Blocks.torch, 3);
        this.setBlockAndMetadata(world, 1, 3, 1, Blocks.torch, 3);
        this.setBlockAndMetadata(world, -2, 1, 4, Blocks.crafting_table, 0);
        this.placeChest(world, random, -2, 1, 5, 5, LOTRChestContents.DORWINION_HOUSE);
        this.placeChest(world, random, -2, 1, 6, 5, LOTRChestContents.DORWINION_HOUSE);
        this.setBlockAndMetadata(world, -2, 1, 7, LOTRMod.dorwinionTable, 0);
        this.setBlockAndMetadata(world, -1, 1, 6, Blocks.bed, 0);
        this.setBlockAndMetadata(world, -1, 1, 7, Blocks.bed, 8);
        this.setBlockAndMetadata(world, 2, 1, 4, Blocks.furnace, 3);
        this.setBlockAndMetadata(world, 3, 1, 4, Blocks.cauldron, 3);
        this.setBlockAndMetadata(world, 4, 1, 4, this.plankStairBlock, 4);
        this.setBlockAndMetadata(world, 5, 1, 4, this.plankSlabBlock, this.plankSlabMeta | 8);
        this.setBlockAndMetadata(world, 6, 1, 4, this.plankBlock, this.plankMeta);
        this.setBlockAndMetadata(world, 6, 1, 5, this.plankSlabBlock, this.plankSlabMeta | 8);
        this.setBlockAndMetadata(world, 6, 1, 6, this.plankSlabBlock, this.plankSlabMeta | 8);
        this.setBlockAndMetadata(world, 6, 1, 7, this.plankBlock, this.plankMeta);
        this.setBlockAndMetadata(world, 5, 1, 7, this.plankSlabBlock, this.plankSlabMeta | 8);
        this.setBlockAndMetadata(world, 4, 1, 7, this.plankStairBlock, 4);
        int[] i17 = new int[] {4, 7};
        k2 = i17.length;
        for(j12 = 0; j12 < k2; ++j12) {
            int k14;
            k14 = i17[j12];
            for(int i18 = 4; i18 <= 5; ++i18) {
                this.placePlate(world, random, i18, 2, k14, this.plateBlock, LOTRFoods.DORWINION);
            }
            this.placeBarrel(world, random, 6, 2, k14, 5, new ItemStack(this.wineItem));
        }
        this.placeMug(world, random, 6, 2, 5, 1, new ItemStack(this.wineItem), LOTRFoods.DORWINION_DRINK);
        this.placeMug(world, random, 6, 2, 6, 1, new ItemStack(this.wineItem), LOTRFoods.DORWINION_DRINK);
        this.setBlockAndMetadata(world, 2, 0, 8, this.floorBlock, this.floorMeta);
        this.setBlockAndMetadata(world, 2, 1, 8, this.doorBlock, 3);
        this.setBlockAndMetadata(world, 2, 2, 8, this.doorBlock, 8);
        this.spawnItemFrame(world, 2, 3, 8, 2, new ItemStack(this.grapeItem));
        this.setBlockAndMetadata(world, 2, 3, 9, Blocks.torch, 3);
        for(i14 = -3; i14 <= 7; ++i14) {
            for(k12 = 9; k12 <= 19; ++k12) {
                if(i14 == -3 || i14 == 7 || k12 == 19) {
                    this.setGrassToDirt(world, i14, 0, k12);
                    this.setBlockAndMetadata(world, i14, 1, k12, this.wallBlock, this.wallMeta);
                    this.setBlockAndMetadata(world, i14, 2, k12, this.brickBlock, this.brickMeta);
                    if(IntMath.mod(i14 + k12, 2) != 0) continue;
                    this.setBlockAndMetadata(world, i14, 3, k12, this.brickSlabBlock, this.brickSlabMeta);
                    continue;
                }
                this.setBlockAndMetadata(world, i14, 0, k12, LOTRMod.dirtPath, 0);
                if(IntMath.mod(i14, 2) != 1) continue;
                if(k12 == 14) {
                    this.setBlockAndMetadata(world, i14, 0, k12, Blocks.water, 0);
                    this.setBlockAndMetadata(world, i14, 1, k12, this.fenceBlock, this.fenceMeta);
                    this.setBlockAndMetadata(world, i14, 2, k12, Blocks.torch, 5);
                    continue;
                }
                if(k12 < 11 || k12 > 17) continue;
                this.setBlockAndMetadata(world, i14, 0, k12, Blocks.farmland, 7);
                this.setBlockAndMetadata(world, i14, 1, k12, this.grapevineBlock, 7);
                this.setBlockAndMetadata(world, i14, 2, k12, this.grapevineBlock, 7);
            }
        }
        for(i14 = 0; i14 <= 4; ++i14) {
            this.setBlockAndMetadata(world, i14, 3, 19, this.brickBlock, this.brickMeta);
        }
        for(i14 = 1; i14 <= 3; ++i14) {
            this.setBlockAndMetadata(world, i14, 4, 19, this.brickBlock, this.brickMeta);
        }
        this.setBlockAndMetadata(world, 0, 4, 19, this.brickStairBlock, 1);
        this.setBlockAndMetadata(world, 4, 4, 19, this.brickStairBlock, 0);
        this.setBlockAndMetadata(world, 1, 5, 19, this.brickSlabBlock, this.brickSlabMeta);
        this.setBlockAndMetadata(world, 3, 5, 19, this.brickSlabBlock, this.brickSlabMeta);
        for(int i16 : new int[] {-3, 4}) {
            this.setGrassToDirt(world, i16, 0, 20);
            this.setBlockAndMetadata(world, i16, 1, 20, this.brickStairBlock, 3);
            this.setBlockAndMetadata(world, i16 + 1, 1, 20, this.leafBlock, this.leafMeta);
            this.setBlockAndMetadata(world, i16 + 2, 1, 20, this.leafBlock, this.leafMeta);
            this.setGrassToDirt(world, i16 + 3, 0, 20);
            this.setBlockAndMetadata(world, i16 + 3, 1, 20, this.brickStairBlock, 3);
        }
        if(generateBackGate) {
            for(i13 = 1; i13 <= 3; ++i13) {
                this.setBlockAndMetadata(world, i13, 0, 19, LOTRMod.dirtPath, 0);
                for(j1 = 1; j1 <= 3; ++j1) {
                    this.setBlockAndMetadata(world, i13, j1, 19, LOTRMod.gateWooden, 2);
                }
            }
        }
        else {
            for(i13 = 1; i13 <= 3; ++i13) {
                this.setBlockAndMetadata(world, i13, 1, 20, this.leafBlock, this.leafMeta);
            }
        }
        LOTREntityDorwinionElf dorwinionElf = new LOTREntityDorwinionElf(world);
        this.spawnNPCAndSetHome(dorwinionElf, world, 0, 1, 5, 16);
        return true;
    }
}
