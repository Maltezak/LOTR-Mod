package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityRohanFarmhand;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenRohanVillageFarm extends LOTRWorldGenRohanStructure {
    public LOTRWorldGenRohanVillageFarm(boolean flag) {
        super(flag);
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int j1;
        this.setOriginAndRotation(world, i, j, k, rotation, 6);
        this.setupRandomBlocks(random);
        if(this.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for(int i1 = -4; i1 <= 4; ++i1) {
                for(int k1 = -5; k1 <= 5; ++k1) {
                    j1 = this.getTopBlock(world, i1, k1) - 1;
                    if(!this.isSurface(world, i1, j1, k1)) {
                        return false;
                    }
                    if(j1 < minHeight) {
                        minHeight = j1;
                    }
                    if(j1 > maxHeight) {
                        maxHeight = j1;
                    }
                    if(maxHeight - minHeight <= 4) continue;
                    return false;
                }
            }
        }
        for(int i1 = -4; i1 <= 4; ++i1) {
            for(int k1 = -5; k1 <= 5; ++k1) {
                int i2 = Math.abs(i1);
                int k2 = Math.abs(k1);
                for(j1 = 0; (((j1 >= 0) || !this.isOpaque(world, i1, j1, k1)) && (this.getY(j1) >= 0)); --j1) {
                    this.setBlockAndMetadata(world, i1, j1, k1, LOTRMod.dirtPath, 0);
                    this.setGrassToDirt(world, i1, j1 - 1, k1);
                }
                for(j1 = 1; j1 <= 4; ++j1) {
                    this.setAir(world, i1, j1, k1);
                }
                if(i2 <= 3 && k2 <= 4) {
                    this.setBlockAndMetadata(world, i1, 0, k1, LOTRMod.dirtPath, 0);
                }
                if(i2 == 0 && k2 == 0) {
                    this.setBlockAndMetadata(world, i1, -1, k1, Blocks.dirt, 0);
                    this.setGrassToDirt(world, i1, -2, k1);
                    this.setBlockAndMetadata(world, i1, 0, k1, Blocks.water, 0);
                    this.setBlockAndMetadata(world, i1, 1, k1, this.logBlock, this.logMeta);
                    this.setBlockAndMetadata(world, i1, 2, k1, Blocks.hay_block, 0);
                    this.setBlockAndMetadata(world, i1, 3, k1, this.fenceBlock, this.fenceMeta);
                    this.setBlockAndMetadata(world, i1, 4, k1, Blocks.hay_block, 0);
                    this.setBlockAndMetadata(world, i1, 5, k1, Blocks.pumpkin, 2);
                    continue;
                }
                if(i2 == 3 && k2 == 4) {
                    for(j1 = 1; j1 <= 2; ++j1) {
                        this.setBlockAndMetadata(world, i1, j1, k1, this.logBlock, this.logMeta);
                    }
                    this.setBlockAndMetadata(world, i1, 3, k1, Blocks.torch, 5);
                    continue;
                }
                if(i2 == 3 && k2 <= 3) {
                    this.setBlockAndMetadata(world, i1, 1, k1, this.fenceBlock, this.fenceMeta);
                    continue;
                }
                if(i2 > 2 || i2 % 2 != 0) continue;
                if(k2 <= 3) {
                    this.setBlockAndMetadata(world, i1, 0, k1, Blocks.farmland, 7);
                    this.setBlockAndMetadata(world, i1, 1, k1, this.cropBlock, this.cropMeta);
                }
                if(k2 != 4) continue;
                this.setBlockAndMetadata(world, i1, 1, k1, this.fenceBlock, this.fenceMeta);
            }
        }
        int farmhands = 1 + random.nextInt(2);
        for(int l = 0; l < farmhands; ++l) {
            LOTREntityRohanFarmhand farmhand = new LOTREntityRohanFarmhand(world);
            this.spawnNPCAndSetHome(farmhand, world, random.nextBoolean() ? -1 : 1, 1, 0, 8);
            farmhand.seedsItem = this.seedItem;
        }
        return true;
    }
}
