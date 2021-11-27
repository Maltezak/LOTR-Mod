package lotr.common.world.structure;

import java.util.Random;

import lotr.common.*;
import lotr.common.entity.npc.LOTREntityHobbit;
import lotr.common.world.biome.LOTRBiomeGenShire;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenHobbitPicnicBench extends LOTRWorldGenStructureBase {
    private Block baseBlock;
    private int baseMeta;
    private Block stairBlock;
    private Block halfBlock;
    private int halfMeta;
    private Block plateBlock;

    public LOTRWorldGenHobbitPicnicBench(boolean flag) {
        super(flag);
    }

    @Override
    public boolean generate(World world, Random random, int i, int j, int k) {
        if(this.restrictions && !(world.getBiomeGenForCoords(i, k) instanceof LOTRBiomeGenShire)) {
            return false;
        }
        int randomWood = random.nextInt(4);
        switch(randomWood) {
            case 0: {
                this.baseBlock = Blocks.planks;
                this.baseMeta = 0;
                this.stairBlock = Blocks.oak_stairs;
                this.halfBlock = Blocks.wooden_slab;
                this.halfMeta = 0;
                break;
            }
            case 1: {
                this.baseBlock = Blocks.planks;
                this.baseMeta = 1;
                this.stairBlock = Blocks.spruce_stairs;
                this.halfBlock = Blocks.wooden_slab;
                this.halfMeta = 1;
                break;
            }
            case 2: {
                this.baseBlock = Blocks.planks;
                this.baseMeta = 2;
                this.stairBlock = Blocks.birch_stairs;
                this.halfBlock = Blocks.wooden_slab;
                this.halfMeta = 2;
                break;
            }
            case 3: {
                this.baseBlock = LOTRMod.planks;
                this.baseMeta = 0;
                this.stairBlock = LOTRMod.stairsShirePine;
                this.halfBlock = LOTRMod.woodSlabSingle;
                this.halfMeta = 0;
            }
        }
        this.plateBlock = random.nextBoolean() ? LOTRMod.woodPlateBlock : LOTRMod.ceramicPlateBlock;
        int rotation = random.nextInt(4);
        if(!this.restrictions && this.usingPlayer != null) {
            rotation = this.usingPlayerRotation();
        }
        switch(rotation) {
            case 0: {
                return this.generateFacingSouth(world, random, i, j, k);
            }
            case 1: {
                return this.generateFacingWest(world, random, i, j, k);
            }
            case 2: {
                return this.generateFacingNorth(world, random, i, j, k);
            }
            case 3: {
                return this.generateFacingEast(world, random, i, j, k);
            }
        }
        return false;
    }

    private boolean generateFacingSouth(World world, Random random, int i, int j, int k) {
        int k1;
        int i1;
        if(this.restrictions) {
            for(k1 = k; k1 <= k + 5; ++k1) {
                for(i1 = i + 2; i1 >= i - 3; --i1) {
                    if(world.getBlock(i1, j - 1, k1) == Blocks.grass && world.isAirBlock(i1, j, k1) && world.isAirBlock(i1, j + 1, k1)) continue;
                    return false;
                }
            }
        }
        for(k1 = k; k1 <= k + 5; ++k1) {
            for(i1 = i + 2; i1 >= i - 3; --i1) {
                this.setBlockAndNotifyAdequately(world, i1, j, k1, Blocks.air, 0);
                this.setBlockAndNotifyAdequately(world, i1, j + 1, k1, Blocks.air, 0);
            }
        }
        for(k1 = k; k1 <= k + 5; ++k1) {
            for(i1 = i; i1 >= i - 1; --i1) {
                if(k1 == k || k1 == k + 5) {
                    this.setBlockAndNotifyAdequately(world, i1, j, k1, this.baseBlock, this.baseMeta);
                }
                else {
                    this.setBlockAndNotifyAdequately(world, i1, j, k1, this.halfBlock, this.halfMeta | 8);
                }
                this.placePlate(world, random, i1, j + 1, k1, this.plateBlock, LOTRFoods.HOBBIT);
            }
            this.setBlockAndNotifyAdequately(world, i - 3, j, k1, this.stairBlock, 1);
            this.setBlockAndNotifyAdequately(world, i + 2, j, k1, this.stairBlock, 0);
        }
        int hobbits = 2 + random.nextInt(3);
        for(i1 = 0; i1 < hobbits; ++i1) {
            LOTREntityHobbit hobbit = new LOTREntityHobbit(world);
            int hobbitX = i + 1 - random.nextInt(2) * 3;
            int hobbitY = j;
            int hobbitZ = k + random.nextInt(6);
            hobbit.setLocationAndAngles(hobbitX + 0.5, hobbitY, hobbitZ + 0.5, 0.0f, 0.0f);
            hobbit.setHomeArea(hobbitX, hobbitY, hobbitZ, 16);
            hobbit.onSpawnWithEgg(null);
            hobbit.isNPCPersistent = true;
            world.spawnEntityInWorld(hobbit);
        }
        return true;
    }

    private boolean generateFacingWest(World world, Random random, int i, int j, int k) {
        int k1;
        int i1;
        if(this.restrictions) {
            for(i1 = i; i1 >= i - 5; --i1) {
                for(k1 = k + 2; k1 >= k - 3; --k1) {
                    if(world.getBlock(i1, j - 1, k1) == Blocks.grass && world.isAirBlock(i1, j, k1) && world.isAirBlock(i1, j + 1, k1)) continue;
                    return false;
                }
            }
        }
        for(i1 = i; i1 >= i - 5; --i1) {
            for(k1 = k + 2; k1 >= k - 3; --k1) {
                this.setBlockAndNotifyAdequately(world, i1, j, k1, Blocks.air, 0);
                this.setBlockAndNotifyAdequately(world, i1, j + 1, k1, Blocks.air, 0);
            }
        }
        for(i1 = i; i1 >= i - 5; --i1) {
            for(k1 = k; k1 >= k - 1; --k1) {
                if(i1 == i || i1 == i - 5) {
                    this.setBlockAndNotifyAdequately(world, i1, j, k1, this.baseBlock, this.baseMeta);
                }
                else {
                    this.setBlockAndNotifyAdequately(world, i1, j, k1, this.halfBlock, this.halfMeta | 8);
                }
                this.placePlate(world, random, i1, j + 1, k1, this.plateBlock, LOTRFoods.HOBBIT);
            }
            this.setBlockAndNotifyAdequately(world, i1, j, k - 3, this.stairBlock, 3);
            this.setBlockAndNotifyAdequately(world, i1, j, k + 2, this.stairBlock, 2);
        }
        int hobbits = 2 + random.nextInt(3);
        for(int i12 = 0; i12 < hobbits; ++i12) {
            LOTREntityHobbit hobbit = new LOTREntityHobbit(world);
            int hobbitX = i - random.nextInt(6);
            int hobbitY = j;
            int hobbitZ = k + 1 - random.nextInt(2) * 3;
            hobbit.setLocationAndAngles(hobbitX + 0.5, hobbitY, hobbitZ + 0.5, 0.0f, 0.0f);
            hobbit.setHomeArea(hobbitX, hobbitY, hobbitZ, 16);
            hobbit.onSpawnWithEgg(null);
            hobbit.isNPCPersistent = true;
            world.spawnEntityInWorld(hobbit);
        }
        return true;
    }

    private boolean generateFacingNorth(World world, Random random, int i, int j, int k) {
        int k1;
        int i1;
        if(this.restrictions) {
            for(k1 = k; k1 >= k - 5; --k1) {
                for(i1 = i - 2; i1 <= i + 3; ++i1) {
                    if(world.getBlock(i1, j - 1, k1) == Blocks.grass && world.isAirBlock(i1, j, k1) && world.isAirBlock(i1, j + 1, k1)) continue;
                    return false;
                }
            }
        }
        for(k1 = k; k1 >= k - 5; --k1) {
            for(i1 = i - 2; i1 <= i + 3; ++i1) {
                this.setBlockAndNotifyAdequately(world, i1, j, k1, Blocks.air, 0);
                this.setBlockAndNotifyAdequately(world, i1, j + 1, k1, Blocks.air, 0);
            }
        }
        for(k1 = k; k1 >= k - 5; --k1) {
            for(i1 = i; i1 <= i + 1; ++i1) {
                if(k1 == k || k1 == k - 5) {
                    this.setBlockAndNotifyAdequately(world, i1, j, k1, this.baseBlock, this.baseMeta);
                }
                else {
                    this.setBlockAndNotifyAdequately(world, i1, j, k1, this.halfBlock, this.halfMeta | 8);
                }
                this.placePlate(world, random, i1, j + 1, k1, this.plateBlock, LOTRFoods.HOBBIT);
            }
            this.setBlockAndNotifyAdequately(world, i - 2, j, k1, this.stairBlock, 1);
            this.setBlockAndNotifyAdequately(world, i + 3, j, k1, this.stairBlock, 0);
        }
        int hobbits = 2 + random.nextInt(3);
        for(i1 = 0; i1 < hobbits; ++i1) {
            LOTREntityHobbit hobbit = new LOTREntityHobbit(world);
            int hobbitX = i - 1 + random.nextInt(2) * 3;
            int hobbitY = j;
            int hobbitZ = k - random.nextInt(6);
            hobbit.setLocationAndAngles(hobbitX + 0.5, hobbitY, hobbitZ + 0.5, 0.0f, 0.0f);
            hobbit.setHomeArea(hobbitX, hobbitY, hobbitZ, 16);
            hobbit.onSpawnWithEgg(null);
            hobbit.isNPCPersistent = true;
            world.spawnEntityInWorld(hobbit);
        }
        return true;
    }

    private boolean generateFacingEast(World world, Random random, int i, int j, int k) {
        int k1;
        int i1;
        if(this.restrictions) {
            for(i1 = i; i1 <= i + 5; ++i1) {
                for(k1 = k - 2; k1 <= k + 3; ++k1) {
                    if(world.getBlock(i1, j - 1, k1) == Blocks.grass && world.isAirBlock(i1, j, k1) && world.isAirBlock(i1, j + 1, k1)) continue;
                    return false;
                }
            }
        }
        for(i1 = i; i1 <= i + 5; ++i1) {
            for(k1 = k - 2; k1 <= k + 3; ++k1) {
                this.setBlockAndNotifyAdequately(world, i1, j, k1, Blocks.air, 0);
                this.setBlockAndNotifyAdequately(world, i1, j + 1, k1, Blocks.air, 0);
            }
        }
        for(i1 = i; i1 <= i + 5; ++i1) {
            for(k1 = k; k1 <= k + 1; ++k1) {
                if(i1 == i || i1 == i + 5) {
                    this.setBlockAndNotifyAdequately(world, i1, j, k1, this.baseBlock, this.baseMeta);
                }
                else {
                    this.setBlockAndNotifyAdequately(world, i1, j, k1, this.halfBlock, this.halfMeta | 8);
                }
                this.placePlate(world, random, i1, j + 1, k1, this.plateBlock, LOTRFoods.HOBBIT);
            }
            this.setBlockAndNotifyAdequately(world, i1, j, k - 2, this.stairBlock, 3);
            this.setBlockAndNotifyAdequately(world, i1, j, k + 3, this.stairBlock, 2);
        }
        int hobbits = 2 + random.nextInt(3);
        for(int i12 = 0; i12 < hobbits; ++i12) {
            LOTREntityHobbit hobbit = new LOTREntityHobbit(world);
            int hobbitX = i + random.nextInt(6);
            int hobbitY = j;
            int hobbitZ = k - 1 + random.nextInt(2) * 3;
            hobbit.setLocationAndAngles(hobbitX + 0.5, hobbitY, hobbitZ + 0.5, 0.0f, 0.0f);
            hobbit.setHomeArea(hobbitX, hobbitY, hobbitZ, 16);
            hobbit.onSpawnWithEgg(null);
            hobbit.isNPCPersistent = true;
            world.spawnEntityInWorld(hobbit);
        }
        return true;
    }
}
