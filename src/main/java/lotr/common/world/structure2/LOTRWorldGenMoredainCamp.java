package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.LOTRMod;
import lotr.common.world.biome.LOTRBiomeGenFarHaradSavannah;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class LOTRWorldGenMoredainCamp extends LOTRWorldGenStructureBase2 {
    public LOTRWorldGenMoredainCamp(boolean flag) {
        super(flag);
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 0);
        if(this.restrictions) {
            boolean suitableSpawn = false;
            BiomeGenBase biome = world.getBiomeGenForCoords(this.originX, this.originZ);
            if(biome instanceof LOTRBiomeGenFarHaradSavannah) {
                suitableSpawn = !LOTRBiomeGenFarHaradSavannah.isBiomePopulated(this.originX, this.originY, this.originZ);
            }
            if(!suitableSpawn) {
                return false;
            }
            int minHeight = 0;
            int maxHeight = 0;
            int range = 3;
            for(int i1 = -range; i1 <= range; ++i1) {
                for(int k1 = -range; k1 <= range; ++k1) {
                    int j1 = this.getTopBlock(world, i1, k1);
                    Block block = this.getBlock(world, i1, j1 - 1, k1);
                    if(block != Blocks.grass && block != Blocks.dirt && block != Blocks.sand && block != Blocks.stone) {
                        return false;
                    }
                    if(j1 < minHeight) {
                        minHeight = j1;
                    }
                    if(j1 > maxHeight) {
                        maxHeight = j1;
                    }
                    if(maxHeight - minHeight <= 5) continue;
                    return false;
                }
            }
        }
        for(int i1 = -1; i1 <= 1; ++i1) {
            for(int k1 = -1; k1 <= 1; ++k1) {
                for(int j1 = 0; (((j1 == 0) || !this.isOpaque(world, i1, j1, k1)) && (this.getY(j1) >= 0)); --j1) {
                    this.setBlockAndMetadata(world, i1, j1, k1, Blocks.hardened_clay, 0);
                    this.setGrassToDirt(world, i1, j1 - 1, k1);
                }
                if(i1 == 0 && k1 == 0) {
                    this.setBlockAndMetadata(world, i1, 1, k1, LOTRMod.moredainTable, 0);
                    continue;
                }
                this.setBlockAndMetadata(world, i1, 1, k1, LOTRMod.slabSingle7, 0);
            }
        }
        int huts = MathHelper.getRandomIntegerInRange(random, 2, 4);
        for(int l = 0; l < huts; ++l) {
            LOTRWorldGenMoredainHutHunter structure = new LOTRWorldGenMoredainHutHunter(this.notifyChanges);
            this.attemptHutSpawn(structure, world, random);
        }
        return true;
    }

    private boolean attemptHutSpawn(LOTRWorldGenStructureBase2 structure, World world, Random random) {
        structure.restrictions = this.restrictions;
        structure.usingPlayer = this.usingPlayer;
        for(int l = 0; l < 16; ++l) {
            int x = MathHelper.getRandomIntegerInRange(random, 4, 8);
            int z = MathHelper.getRandomIntegerInRange(random, 4, 8);
            int spawnX = this.getX(x *= random.nextBoolean() ? -1 : 1, z *= random.nextBoolean() ? -1 : 1);
            int spawnZ = this.getZ(x, z);
            int spawnY = this.getY(this.getTopBlock(world, x, z));
            if(!structure.generateWithSetRotation(world, random, spawnX, spawnY, spawnZ, random.nextInt(4))) continue;
            return true;
        }
        return false;
    }
}
