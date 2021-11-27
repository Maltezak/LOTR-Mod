package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.animal.LOTREntityCamel;
import lotr.common.entity.npc.LOTREntityHarnedorWarrior;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLeashKnot;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenNearHaradDesertCamp extends LOTRWorldGenStructureBase2 {
    public LOTRWorldGenNearHaradDesertCamp(boolean flag) {
        super(flag);
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 0);
        int highestHeight = 0;
        for(int i1 = -1; i1 <= 1; ++i1) {
            for(int k1 = -1; k1 <= 1; ++k1) {
                int j1 = this.getTopBlock(world, i1, k1);
                Block block = this.getBlock(world, i1, j1 - 1, k1);
                if(block != Blocks.sand && block != Blocks.dirt && block != Blocks.grass) {
                    return false;
                }
                if(j1 <= highestHeight) continue;
                highestHeight = j1;
            }
        }
        boolean flag = false;
        int x = -2 + random.nextInt(5);
        int z = 4 + random.nextInt(4);
        int y = this.getTopBlock(world, x, z);
        flag = new LOTRWorldGenNearHaradTent(this.notifyChanges).generateWithSetRotation(world, random, this.getX(x, z), this.getY(y), this.getZ(x, z), this.getRotationMode()) || flag;
        x = -2 + random.nextInt(5);
        z = -4 - random.nextInt(4);
        y = this.getTopBlock(world, x, z);
        flag = new LOTRWorldGenNearHaradTent(this.notifyChanges).generateWithSetRotation(world, random, this.getX(x, z), this.getY(y), this.getZ(x, z), (this.getRotationMode() + 2) % 4) || flag;
        if(!flag) {
            return false;
        }
        for(int i1 = -1; i1 <= 1; ++i1) {
            for(int k1 = -1; k1 <= 1; ++k1) {
                int j1 = highestHeight - 1;
                while(!this.isOpaque(world, i1, j1, k1) && this.getY(j1) >= 0) {
                    this.setBlockAndMetadata(world, i1, j1, k1, Blocks.sandstone, 0);
                    this.setGrassToDirt(world, i1, j1 - 1, k1);
                    --j1;
                }
            }
            this.setBlockAndMetadata(world, i1, highestHeight, -1, LOTRMod.stairsNearHaradBrick, 2);
            this.setBlockAndMetadata(world, i1, highestHeight, 1, LOTRMod.stairsNearHaradBrick, 3);
        }
        this.setBlockAndMetadata(world, -1, highestHeight, 0, LOTRMod.stairsNearHaradBrick, 1);
        this.setBlockAndMetadata(world, 1, highestHeight, 0, LOTRMod.stairsNearHaradBrick, 0);
        this.setBlockAndMetadata(world, 0, highestHeight, 0, Blocks.water, 0);
        int camels = 1 + random.nextInt(4);
        for(int l = 0; l < camels; ++l) {
            int camelY;
            int camelZ;
            LOTREntityCamel camel = new LOTREntityCamel(world);
            int camelX = random.nextBoolean() ? -3 - random.nextInt(3) : 3 + random.nextInt(3);
            if(this.getBlock(world, camelX, (camelY = this.getTopBlock(world, camelX, camelZ = -3 + random.nextInt(7))) - 1, camelZ) != Blocks.sand || this.getBlock(world, camelX, camelY, camelZ) != Blocks.air || this.getBlock(world, camelX, camelY + 1, camelZ) != Blocks.air) continue;
            this.setBlockAndMetadata(world, camelX, camelY, camelZ, LOTRMod.fence2, 2);
            this.setBlockAndMetadata(world, camelX, camelY + 1, camelZ, LOTRMod.fence2, 2);
            this.spawnNPCAndSetHome(camel, world, camelX, camelY, camelZ, 0);
            camel.detachHome();
            camel.saddleMountForWorldGen();
            if(random.nextBoolean()) {
                camel.setChestedForWorldGen();
            }
            EntityLeashKnot leash = EntityLeashKnot.func_110129_a(world, this.getX(camelX, camelZ), this.getY(camelY), this.getZ(camelX, camelZ));
            camel.setLeashedToEntity(leash, true);
        }
        LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
        respawner.setSpawnClass(LOTREntityHarnedorWarrior.class);
        respawner.setCheckRanges(20, -12, 12, 4);
        respawner.setSpawnRanges(8, -4, 4, 16);
        this.placeNPCRespawner(respawner, world, 0, 0, 0);
        return true;
    }
}
