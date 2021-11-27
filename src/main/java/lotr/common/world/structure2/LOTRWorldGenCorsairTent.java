package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.LOTRMod;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenCorsairTent extends LOTRWorldGenCorsairStructure {
    public LOTRWorldGenCorsairTent(boolean flag) {
        super(flag);
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 4);
        this.setupRandomBlocks(random);
        if(this.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for(int i1 = -2; i1 <= 2; ++i1) {
                for(int k1 = -3; k1 <= 3; ++k1) {
                    int j1 = this.getTopBlock(world, i1, k1) - 1;
                    Block block = this.getBlock(world, i1, j1, k1);
                    if(!this.isSurface(world, i1, j1, k1) && block != Blocks.stone && block != Blocks.sandstone) {
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
        for(int i1 = -2; i1 <= 2; ++i1) {
            for(int k1 = -3; k1 <= 3; ++k1) {
                int j1;
                for(j1 = 0; (((j1 >= 0) || !this.isOpaque(world, i1, j1, k1)) && (this.getY(j1) >= 0)); --j1) {
                    int randomGround = random.nextInt(3);
                    if(randomGround == 0) {
                        if(j1 == 0) {
                            this.setBiomeTop(world, i1, j1, k1);
                        }
                        else {
                            this.setBiomeFiller(world, i1, j1, k1);
                        }
                    }
                    else if(randomGround == 1) {
                        this.setBlockAndMetadata(world, i1, j1, k1, Blocks.dirt, 1);
                    }
                    else if(randomGround == 2) {
                        this.setBlockAndMetadata(world, i1, j1, k1, Blocks.sand, 0);
                    }
                    this.setGrassToDirt(world, i1, j1 - 1, k1);
                }
                for(j1 = 1; j1 <= 3; ++j1) {
                    this.setAir(world, i1, j1, k1);
                }
            }
        }
        for(int k1 = -3; k1 <= 3; ++k1) {
            for(int i1 : new int[] {-2, 2}) {
                for(int j1 = 1; j1 <= 2; ++j1) {
                    this.setBlockAndMetadata(world, i1, j1, k1, Blocks.wool, random.nextBoolean() ? 15 : 7);
                }
                this.setGrassToDirt(world, i1, 0, k1);
            }
            this.setBlockAndMetadata(world, -1, 3, k1, Blocks.wool, random.nextBoolean() ? 15 : 7);
            this.setBlockAndMetadata(world, 1, 3, k1, Blocks.wool, random.nextBoolean() ? 15 : 7);
            this.setBlockAndMetadata(world, 0, 4, k1, Blocks.wool, random.nextBoolean() ? 15 : 7);
            if(Math.abs(k1) != 3) continue;
            this.setBlockAndMetadata(world, 0, 5, k1, Blocks.wool, random.nextBoolean() ? 15 : 7);
        }
        for(int j1 = 1; j1 <= 3; ++j1) {
            this.setBlockAndMetadata(world, 0, j1, -3, this.fenceBlock, this.fenceMeta);
            this.setBlockAndMetadata(world, 0, j1, 3, this.fenceBlock, this.fenceMeta);
        }
        this.setBlockAndMetadata(world, -1, 2, -3, Blocks.torch, 2);
        this.setBlockAndMetadata(world, 1, 2, -3, Blocks.torch, 1);
        this.setBlockAndMetadata(world, -1, 2, 3, Blocks.torch, 2);
        this.setBlockAndMetadata(world, 1, 2, 3, Blocks.torch, 1);
        if(random.nextBoolean()) {
            this.placeChest(world, random, -1, 1, 0, LOTRMod.chestBasket, 4, LOTRChestContents.CORSAIR, 1 + random.nextInt(2));
        }
        else {
            this.placeChest(world, random, 1, 1, 0, LOTRMod.chestBasket, 5, LOTRChestContents.CORSAIR, 1 + random.nextInt(2));
        }
        return true;
    }
}
