package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.LOTRMod;
import lotr.common.entity.npc.*;
import lotr.common.world.biome.LOTRBiome;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.item.Item;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class LOTRWorldGenHobbitFarm extends LOTRWorldGenStructureBase2 {
    private Block wood1Block;
    private int wood1Meta;
    private Block wood1Stair;
    private Block beam1Block;
    private int beam1Meta;
    private Block wood2Block;
    private int wood2Meta;
    private Block cropBlock;
    private int cropMeta;
    private Item seedItem;

    public LOTRWorldGenHobbitFarm(boolean flag) {
        super(flag);
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int j1;
        int j12;
        int k16;
        int i1;
        int i12;
        Block block;
        int k12;
        int i13;
        int k13;
        int i14;
        int k14;
        int k15;
        this.setOriginAndRotation(world, i, j, k, rotation, 6);
        int randomWood = random.nextInt(4);
        switch(randomWood) {
            case 0: {
                this.wood1Block = Blocks.planks;
                this.wood1Meta = 0;
                this.wood1Stair = Blocks.oak_stairs;
                this.beam1Block = LOTRMod.woodBeamV1;
                this.beam1Meta = 0;
                break;
            }
            case 1: {
                this.wood1Block = Blocks.planks;
                this.wood1Meta = 2;
                this.wood1Stair = Blocks.birch_stairs;
                this.beam1Block = LOTRMod.woodBeamV1;
                this.beam1Meta = 2;
                break;
            }
            case 2: {
                this.wood1Block = LOTRMod.planks;
                this.wood1Meta = 0;
                this.wood1Stair = LOTRMod.stairsShirePine;
                this.beam1Block = LOTRMod.woodBeam1;
                this.beam1Meta = 0;
                break;
            }
            case 3: {
                this.wood1Block = LOTRMod.planks;
                this.wood1Meta = 4;
                this.wood1Stair = LOTRMod.stairsApple;
                this.beam1Block = LOTRMod.woodBeamFruit;
                this.beam1Meta = 0;
            }
        }
        int randomWood2 = random.nextInt(2);
        switch(randomWood2) {
            case 0: {
                this.wood2Block = Blocks.planks;
                this.wood2Meta = 1;
                break;
            }
            case 1: {
                this.wood2Block = LOTRMod.planks;
                this.wood2Meta = 6;
            }
        }
        int randomCrop = random.nextInt(8);
        switch(randomCrop) {
            case 0: {
                this.cropBlock = Blocks.wheat;
                this.cropMeta = 7;
                this.seedItem = Items.wheat_seeds;
                break;
            }
            case 1: {
                this.cropBlock = Blocks.carrots;
                this.cropMeta = 7;
                this.seedItem = Items.carrot;
                break;
            }
            case 2: {
                this.cropBlock = Blocks.potatoes;
                this.cropMeta = 7;
                this.seedItem = Items.potato;
                break;
            }
            case 3: {
                this.cropBlock = LOTRMod.lettuceCrop;
                this.cropMeta = 7;
                this.seedItem = LOTRMod.lettuce;
                break;
            }
            case 4: {
                this.cropBlock = LOTRMod.pipeweedCrop;
                this.cropMeta = 7;
                this.seedItem = LOTRMod.pipeweedSeeds;
                break;
            }
            case 5: {
                this.cropBlock = LOTRMod.cornStalk;
                this.cropMeta = 0;
                this.seedItem = Item.getItemFromBlock(LOTRMod.cornStalk);
                break;
            }
            case 6: {
                this.cropBlock = LOTRMod.leekCrop;
                this.cropMeta = 7;
                this.seedItem = LOTRMod.leek;
                break;
            }
            case 7: {
                this.cropBlock = LOTRMod.turnipCrop;
                this.cropMeta = 7;
                this.seedItem = LOTRMod.turnip;
            }
        }
        if(this.restrictions) {
            int minHeight = 1;
            int maxHeight = 1;
            for(i14 = -5; i14 <= 10; ++i14) {
                for(k16 = -7; k16 <= 8; ++k16) {
                    j12 = this.getTopBlock(world, i14, k16);
                    block = this.getBlock(world, i14, j12 - 1, k16);
                    if(block != Blocks.grass && block != Blocks.dirt && block != Blocks.stone) {
                        return false;
                    }
                    if(j12 > maxHeight) {
                        maxHeight = j12;
                    }
                    if(j12 >= minHeight) continue;
                    minHeight = j12;
                }
            }
            if(Math.abs(maxHeight - minHeight) > 6) {
                return false;
            }
        }
        for(int i15 = -5; i15 <= 10; ++i15) {
            for(k13 = -7; k13 <= 8; ++k13) {
                for(j1 = 1; j1 <= 10; ++j1) {
                    this.setAir(world, i15, j1, k13);
                }
                this.setBlockAndMetadata(world, i15, 0, k13, Blocks.grass, 0);
                this.setGrassToDirt(world, i15, -1, k13);
                j1 = -1;
                while(!this.isOpaque(world, i15, j1, k13) && this.getY(j1) >= 0) {
                    this.setBlockAndMetadata(world, i15, j1, k13, Blocks.dirt, 0);
                    this.setGrassToDirt(world, i15, j1 - 1, k13);
                    --j1;
                }
            }
        }
        for(k14 = -5; k14 <= 6; ++k14) {
            for(i12 = -5; i12 <= 4; ++i12) {
                if(k14 != -5 && k14 != 6 && i12 != -5 && i12 != 4) continue;
                for(j1 = 1; j1 <= 5; ++j1) {
                    this.setBlockAndMetadata(world, i12, j1, k14, this.wood2Block, this.wood2Meta);
                    this.setGrassToDirt(world, i12, j1 - 1, k14);
                }
            }
        }
        for(int stair = 0; stair <= 4; ++stair) {
            int j13 = 5 + stair;
            for(i14 = -5 + stair; i14 <= 4 - stair; ++i14) {
                for(int k17 : new int[] {-5, 6}) {
                    this.setBlockAndMetadata(world, i14, j13, k17, this.wood2Block, this.wood2Meta);
                }
            }
            for(k12 = -6; k12 <= 7; ++k12) {
                this.setBlockAndMetadata(world, -6 + stair, j13, k12, LOTRMod.stairsThatch, 1);
                this.setBlockAndMetadata(world, 5 - stair, j13, k12, LOTRMod.stairsThatch, 0);
            }
        }
        for(k14 = -4; k14 <= 5; ++k14) {
            for(i12 = -4; i12 <= 3; ++i12) {
                this.setBlockAndMetadata(world, i12, 5, k14, this.wood1Block, this.wood1Meta);
            }
        }
        for(int j14 = 1; j14 <= 5; ++j14) {
            for(int k18 : new int[] {-5, 6}) {
                this.setBlockAndMetadata(world, -5, j14, k18, this.beam1Block, this.beam1Meta);
                this.setBlockAndMetadata(world, -2, j14, k18, this.wood1Block, this.wood1Meta);
                this.setBlockAndMetadata(world, 1, j14, k18, this.wood1Block, this.wood1Meta);
                this.setBlockAndMetadata(world, 4, j14, k18, this.beam1Block, this.beam1Meta);
            }
            int[] i16 = new int[] {-5, 4};
            k12 = i16.length;
            for(k16 = 0; k16 < k12; ++k16) {
                i1 = i16[k16];
                this.setBlockAndMetadata(world, i1, j14, -1, this.beam1Block, this.beam1Meta);
                this.setBlockAndMetadata(world, i1, j14, 2, this.beam1Block, this.beam1Meta);
            }
        }
        for(k14 = 0; k14 <= 1; ++k14) {
            int[] i16 = new int[] {-5, 4};
            k12 = i16.length;
            for(k16 = 0; k16 < k12; ++k16) {
                i1 = i16[k16];
                this.setBlockAndMetadata(world, i1, 2, k14, this.wood1Block, this.wood1Meta);
                this.setBlockAndMetadata(world, i1, 4, k14, this.wood1Block, this.wood1Meta);
            }
        }
        int[] k19 = new int[] {-5, 6};
        i12 = k19.length;
        for(k12 = 0; k12 < i12; ++k12) {
            k16 = k19[k12];
            for(i1 = -1; i1 <= 0; ++i1) {
                this.setBlockAndMetadata(world, i1, 3, k16, this.wood1Block, this.wood1Meta);
                this.setBlockAndMetadata(world, i1, 5, k16, this.wood1Block, this.wood1Meta);
                this.setBlockAndMetadata(world, i1, 7, k16, LOTRMod.glassPane, 0);
            }
            for(i1 = -2; i1 <= 1; ++i1) {
                this.setBlockAndMetadata(world, i1, 0, k16, Blocks.grass, 0);
                for(int j15 = 1; j15 <= 3; ++j15) {
                    this.setBlockAndMetadata(world, i1, j15, k16, LOTRMod.gateWooden, 2);
                }
            }
        }
        for(i13 = -1; i13 <= 0; ++i13) {
            for(k13 = -6; k13 <= 7; ++k13) {
                this.setBlockAndMetadata(world, i13, 10, k13, LOTRMod.slabSingleThatch, 0);
            }
        }
        for(i13 = -3; i13 <= 2; ++i13) {
            this.setBlockAndMetadata(world, i13, 5, -6, this.wood1Stair, 6);
            this.setBlockAndMetadata(world, i13, 5, 7, this.wood1Stair, 7);
        }
        this.setBlockAndMetadata(world, -5, 5, -6, this.wood1Block, this.wood1Meta);
        this.setBlockAndMetadata(world, -4, 5, -6, this.wood1Stair, 4);
        this.setBlockAndMetadata(world, 3, 5, -6, this.wood1Stair, 5);
        this.setBlockAndMetadata(world, 4, 5, -6, this.wood1Block, this.wood1Meta);
        this.setBlockAndMetadata(world, -5, 5, 7, this.wood1Block, this.wood1Meta);
        this.setBlockAndMetadata(world, -4, 5, 7, this.wood1Stair, 4);
        this.setBlockAndMetadata(world, 3, 5, 7, this.wood1Stair, 5);
        this.setBlockAndMetadata(world, 4, 5, 7, this.wood1Block, this.wood1Meta);
        int[] i17 = new int[] {-4, 3};
        k13 = i17.length;
        for(k12 = 0; k12 < k13; ++k12) {
            int i18 = i17[k12];
            for(int k110 : new int[] {-1, 2}) {
                this.setBlockAndMetadata(world, i18, 1, k110, Blocks.crafting_table, 0);
                this.setBlockAndMetadata(world, i18, 2, k110, Blocks.fence, 0);
                this.setBlockAndMetadata(world, i18, 3, k110, Blocks.fence, 0);
                this.setBlockAndMetadata(world, i18, 4, k110, Blocks.torch, 5);
            }
        }
        this.setBlockAndMetadata(world, -4, 1, -4, Blocks.hay_block, 0);
        this.setBlockAndMetadata(world, -4, 2, -4, Blocks.hay_block, 0);
        this.setBlockAndMetadata(world, -3, 1, -4, Blocks.hay_block, 0);
        this.setBlockAndMetadata(world, -4, 1, -3, Blocks.hay_block, 0);
        this.setBlockAndMetadata(world, -4, 1, 5, Blocks.hay_block, 0);
        this.setBlockAndMetadata(world, -4, 2, 5, Blocks.hay_block, 0);
        this.setBlockAndMetadata(world, -3, 1, 5, Blocks.hay_block, 0);
        this.setBlockAndMetadata(world, -4, 1, 4, Blocks.hay_block, 0);
        this.setBlockAndMetadata(world, 3, 1, 5, Blocks.hay_block, 0);
        this.setBlockAndMetadata(world, 3, 2, 5, Blocks.hay_block, 0);
        this.setBlockAndMetadata(world, 2, 1, 5, Blocks.hay_block, 0);
        this.setBlockAndMetadata(world, 3, 1, 4, Blocks.hay_block, 0);
        for(int j16 = 1; j16 <= 4; ++j16) {
            this.setBlockAndMetadata(world, 2, j16, -3, Blocks.fence, 0);
        }
        this.setBlockAndMetadata(world, 1, 1, -4, this.wood1Stair, 1);
        this.setBlockAndMetadata(world, 2, 1, -4, this.wood1Block, this.wood1Meta);
        this.setBlockAndMetadata(world, 2, 2, -4, this.wood1Stair, 1);
        this.setBlockAndMetadata(world, 3, 1, -4, this.wood1Block, this.wood1Meta);
        this.setBlockAndMetadata(world, 3, 2, -4, this.wood1Block, this.wood1Meta);
        this.setBlockAndMetadata(world, 3, 2, -3, this.wood1Stair, 7);
        this.setBlockAndMetadata(world, 3, 3, -3, this.wood1Stair, 2);
        this.setBlockAndMetadata(world, 3, 3, -2, this.wood1Block, this.wood1Meta);
        this.setBlockAndMetadata(world, 2, 3, -2, this.wood1Stair, 5);
        this.setBlockAndMetadata(world, 2, 4, -2, this.wood1Stair, 0);
        this.setBlockAndMetadata(world, 1, 4, -2, this.wood1Stair, 5);
        this.setBlockAndMetadata(world, 1, 5, -2, this.wood1Stair, 0);
        this.setAir(world, 3, 5, -4);
        this.setAir(world, 3, 5, -3);
        this.setAir(world, 3, 5, -2);
        this.setAir(world, 2, 5, -2);
        for(int i19 = 0; i19 <= 2; ++i19) {
            this.setBlockAndMetadata(world, i19, 6, -3, Blocks.fence, 0);
            this.setBlockAndMetadata(world, i19, 6, -1, Blocks.fence, 0);
        }
        this.setBlockAndMetadata(world, 2, 6, -4, Blocks.fence, 0);
        this.setBlockAndMetadata(world, 0, 6, -2, Blocks.fence_gate, 3);
        for(k15 = -4; k15 <= 5; ++k15) {
            this.setBlockAndMetadata(world, -4, 6, k15, this.wood2Block, this.wood2Meta);
        }
        for(k15 = -1; k15 <= 5; ++k15) {
            this.setBlockAndMetadata(world, 3, 6, k15, this.wood2Block, this.wood2Meta);
        }
        this.setBlockAndMetadata(world, -2, 7, -4, Blocks.torch, 3);
        this.setBlockAndMetadata(world, 1, 7, -4, Blocks.torch, 3);
        this.setBlockAndMetadata(world, -2, 7, 5, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 1, 7, 5, Blocks.torch, 4);
        int carpet = random.nextInt(16);
        this.setBlockAndMetadata(world, -1, 6, 2, Blocks.carpet, carpet);
        this.setBlockAndMetadata(world, 0, 6, 2, Blocks.carpet, carpet);
        this.setBlockAndMetadata(world, -1, 6, 3, Blocks.carpet, carpet);
        this.setBlockAndMetadata(world, 0, 6, 3, Blocks.carpet, carpet);
        for(k13 = 4; k13 <= 5; ++k13) {
            for(j1 = 6; j1 <= 7; ++j1) {
                this.setBlockAndMetadata(world, -3, j1, k13, Blocks.bookshelf, 0);
                this.setBlockAndMetadata(world, 2, j1, k13, Blocks.bookshelf, 0);
            }
        }
        this.setBlockAndMetadata(world, -3, 6, 0, this.wood2Block, this.wood2Meta);
        this.setBlockAndMetadata(world, -3, 7, 0, LOTRWorldGenHobbitStructure.getRandomCakeBlock(random), 0);
        this.setBlockAndMetadata(world, -3, 6, 1, Blocks.cauldron, 3);
        this.setBlockAndMetadata(world, -3, 6, 2, LOTRMod.hobbitOven, 4);
        this.setBlockAndMetadata(world, -3, 6, 3, Blocks.crafting_table, 0);
        this.placeChest(world, random, 2, 6, 1, 5, LOTRChestContents.HOBBIT_HOLE_LARDER);
        this.setBlockAndMetadata(world, 2, 6, 2, Blocks.bed, 0);
        this.setBlockAndMetadata(world, 2, 6, 3, Blocks.bed, 8);
        for(i12 = 5; i12 <= 10; ++i12) {
            this.setBlockAndMetadata(world, i12, 1, -5, Blocks.fence, 0);
            this.setBlockAndMetadata(world, i12, 1, 6, Blocks.fence, 0);
        }
        for(k13 = -4; k13 <= 5; ++k13) {
            this.setBlockAndMetadata(world, 10, 1, k13, Blocks.fence, 0);
        }
        this.setBlockAndMetadata(world, 7, 1, -5, Blocks.fence_gate, 0);
        this.setBlockAndMetadata(world, 5, 2, -5, Blocks.torch, 5);
        this.setBlockAndMetadata(world, 10, 2, -5, Blocks.torch, 5);
        this.setBlockAndMetadata(world, 10, 2, -1, Blocks.torch, 5);
        this.setBlockAndMetadata(world, 10, 2, 2, Blocks.torch, 5);
        this.setBlockAndMetadata(world, 5, 2, 6, Blocks.torch, 5);
        this.setBlockAndMetadata(world, 10, 2, 6, Blocks.torch, 5);
        for(i12 = 5; i12 <= 9; ++i12) {
            this.setBlockAndMetadata(world, i12, 0, -4, Blocks.gravel, 0);
            this.setBlockAndMetadata(world, i12, 0, 5, Blocks.gravel, 0);
        }
        for(k13 = -3; k13 <= 4; ++k13) {
            this.setBlockAndMetadata(world, 4, 0, k13, Blocks.stonebrick, 0);
            this.setBlockAndMetadata(world, 5, 0, k13, Blocks.water, 0);
            this.setBlockAndMetadata(world, 5, 1, k13, Blocks.stone_slab, 5);
            this.setBlockAndMetadata(world, 9, 0, k13, Blocks.gravel, 0);
            for(i14 = 6; i14 <= 8; ++i14) {
                this.setBlockAndMetadata(world, i14, 0, k13, Blocks.farmland, 7);
                this.setBlockAndMetadata(world, i14, 1, k13, this.cropBlock, this.cropMeta);
            }
        }
        this.setBlockAndMetadata(world, 10, 2, 0, Blocks.fence, 0);
        this.setBlockAndMetadata(world, 10, 3, 0, Blocks.hay_block, 0);
        this.setBlockAndMetadata(world, 10, 4, 0, Blocks.pumpkin, 1);
        LOTREntityHobbitFarmer farmer = new LOTREntityHobbitFarmer(world);
        this.spawnNPCAndSetHome(farmer, world, 0, 6, 0, 16);
        int farmhands = 1 + random.nextInt(3);
        for(int l = 0; l < farmhands; ++l) {
            LOTREntityHobbitFarmhand farmhand = new LOTREntityHobbitFarmhand(world);
            farmhand.seedsItem = this.seedItem;
            this.spawnNPCAndSetHome(farmhand, world, 7, 1, 0, 8);
        }
        int animals = 3 + random.nextInt(6);
        for(int l = 0; l < animals; ++l) {
            Class animalClass = ((BiomeGenBase.SpawnListEntry) WeightedRandom.getRandomItem(world.rand, LOTRBiome.shire.getSpawnableList(EnumCreatureType.creature))).entityClass;
            EntityCreature animal = null;
            try {
                animal = (EntityCreature) animalClass.getConstructor(World.class).newInstance(world);
            }
            catch(Exception exception) {
                exception.printStackTrace();
                continue;
            }
            this.spawnNPCAndSetHome(animal, world, 0, 1, 0, 8);
        }
        return true;
    }
}
