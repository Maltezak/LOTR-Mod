package lotr.common.world.structure2;

import java.util.Random;

import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTRWorldGenAngmarHillmanVillage extends LOTRWorldGenStructureBase2 {
    private static int VILLAGE_SIZE = 16;

    public LOTRWorldGenAngmarHillmanVillage(boolean flag) {
        super(flag);
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        LOTRWorldGenStructureBase2 structure;
        int l;
        this.setOriginAndRotation(world, i, j, k, rotation, 0);
        if(this.restrictions) {
            boolean suitableSpawn = true;
            world.getBiomeGenForCoords(this.originX, this.originZ);
            if(!suitableSpawn) {
                return false;
            }
        }
        int houses = MathHelper.getRandomIntegerInRange(random, 3, 6);
        int chiefainHouses = MathHelper.getRandomIntegerInRange(random, 0, 1);
        for(l = 0; l < chiefainHouses; ++l) {
            structure = new LOTRWorldGenAngmarHillmanChieftainHouse(this.notifyChanges);
            this.attemptHouseSpawn(structure, world, random);
        }
        for(l = 0; l < houses; ++l) {
            structure = new LOTRWorldGenAngmarHillmanHouse(this.notifyChanges);
            this.attemptHouseSpawn(structure, world, random);
        }
        return true;
    }

    private boolean attemptHouseSpawn(LOTRWorldGenStructureBase2 structure, World world, Random random) {
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
