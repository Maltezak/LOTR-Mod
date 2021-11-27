package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.world.biome.LOTRBiomeGenFarHaradSavannah;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class LOTRWorldGenMoredainVillage extends LOTRWorldGenStructureBase2 {
    private static int VILLAGE_SIZE = 16;

    public LOTRWorldGenMoredainVillage(boolean flag) {
        super(flag);
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        LOTRWorldGenMoredainHut structure;
        int l;
        this.setOriginAndRotation(world, i, j, k, rotation, this.usingPlayer != null ? VILLAGE_SIZE + 1 : 0);
        if(this.restrictions) {
            boolean suitableSpawn = false;
            BiomeGenBase biome = world.getBiomeGenForCoords(this.originX, this.originZ);
            if(biome instanceof LOTRBiomeGenFarHaradSavannah) {
                suitableSpawn = LOTRBiomeGenFarHaradSavannah.isBiomePopulated(this.originX, this.originY, this.originZ);
            }
            if(!suitableSpawn) {
                return false;
            }
        }
        int huts = MathHelper.getRandomIntegerInRange(random, 3, 6);
        int traderHuts = MathHelper.getRandomIntegerInRange(random, 0, 2);
        int chieftainHuts = MathHelper.getRandomIntegerInRange(random, 0, 1);
        for(l = 0; l < chieftainHuts; ++l) {
            structure = new LOTRWorldGenMoredainHutChieftain(this.notifyChanges);
            this.attemptHutSpawn(structure, world, random);
        }
        for(l = 0; l < huts; ++l) {
            structure = new LOTRWorldGenMoredainHutVillage(this.notifyChanges);
            this.attemptHutSpawn(structure, world, random);
        }
        for(l = 0; l < traderHuts; ++l) {
            structure = new LOTRWorldGenMoredainHutTrader(this.notifyChanges);
            this.attemptHutSpawn(structure, world, random);
        }
        return true;
    }

    private boolean attemptHutSpawn(LOTRWorldGenStructureBase2 structure, World world, Random random) {
        structure.restrictions = this.restrictions;
        structure.usingPlayer = this.usingPlayer;
        for(int l = 0; l < 16; ++l) {
            int x = MathHelper.getRandomIntegerInRange(random, -VILLAGE_SIZE, VILLAGE_SIZE);
            int z = MathHelper.getRandomIntegerInRange(random, -VILLAGE_SIZE, VILLAGE_SIZE);
            int spawnX = this.getX(x, z);
            int spawnZ = this.getZ(x, z);
            int spawnY = this.getY(this.getTopBlock(world, x, z));
            if(!structure.generateWithSetRotation(world, random, spawnX, spawnY, spawnZ, random.nextInt(4))) continue;
            return true;
        }
        return false;
    }
}
