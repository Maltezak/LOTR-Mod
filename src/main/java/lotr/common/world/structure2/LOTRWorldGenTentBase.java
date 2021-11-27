package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.LOTRMod;
import lotr.common.world.biome.LOTRBiomeGenMordor;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public abstract class LOTRWorldGenTentBase extends LOTRWorldGenStructureBase2 {
    protected Block tentBlock;
    protected int tentMeta;
    protected Block fenceBlock;
    protected int fenceMeta;
    protected Block tableBlock;
    protected LOTRChestContents chestContents;
    protected boolean hasOrcForge = false;
    protected boolean hasOrcTorches = false;

    public LOTRWorldGenTentBase(boolean flag) {
        super(flag);
    }

    protected boolean isOrcTent() {
        return true;
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        BiomeGenBase biome;
        int k1;
        int j1;
        int i1;
        this.setOriginAndRotation(world, i, j, k, rotation, 4);
        this.setupRandomBlocks(random);
        if(this.restrictions) {
            for(i1 = -2; i1 <= 2; ++i1) {
                for(k1 = -3; k1 <= 3; ++k1) {
                    j1 = this.getTopBlock(world, i1, k1) - 1;
                    if(this.isSurface(world, i1, j1, k1)) continue;
                    return false;
                }
            }
        }
        for(i1 = -2; i1 <= 2; ++i1) {
            for(k1 = -3; k1 <= 3; ++k1) {
                for(j1 = 0; (((j1 >= 0) || !this.isOpaque(world, i1, j1, k1)) && (this.getY(j1) >= 0)); --j1) {
                    int randomGround;
                    biome = this.getBiome(world, i1, k1);
                    if(biome instanceof LOTRBiomeGenMordor) {
                        randomGround = random.nextInt(3);
                        if(randomGround == 0) {
                            this.setBlockAndMetadata(world, i1, j1, k1, LOTRMod.rock, 0);
                        }
                        else if(randomGround == 1) {
                            this.setBlockAndMetadata(world, i1, j1, k1, LOTRMod.mordorDirt, 0);
                        }
                        else if(randomGround == 2) {
                            this.setBlockAndMetadata(world, i1, j1, k1, LOTRMod.mordorGravel, 0);
                        }
                    }
                    else {
                        randomGround = random.nextInt(3);
                        if(randomGround == 0) {
                            if(j1 == 0) {
                                this.setBiomeTop(world, i1, j1, k1);
                            }
                            else {
                                this.setBiomeFiller(world, i1, j1, k1);
                            }
                        }
                        else if(randomGround == 1) {
                            this.setBlockAndMetadata(world, i1, j1, k1, Blocks.gravel, 0);
                        }
                        else if(randomGround == 2) {
                            this.setBlockAndMetadata(world, i1, j1, k1, Blocks.cobblestone, 0);
                        }
                    }
                    this.setGrassToDirt(world, i1, j1 - 1, k1);
                }
                for(j1 = 1; j1 <= 3; ++j1) {
                    this.setAir(world, i1, j1, k1);
                }
            }
        }
        for(int k12 = -3; k12 <= 3; ++k12) {
            for(int i12 : new int[] {-2, 2}) {
                for(int j12 = 1; j12 <= 2; ++j12) {
                    this.setBlockAndMetadata(world, i12, j12, k12, this.tentBlock, this.tentMeta);
                }
                this.setGrassToDirt(world, i12, 0, k12);
            }
            this.setBlockAndMetadata(world, -1, 3, k12, this.tentBlock, this.tentMeta);
            this.setBlockAndMetadata(world, 1, 3, k12, this.tentBlock, this.tentMeta);
            this.setBlockAndMetadata(world, 0, 4, k12, this.tentBlock, this.tentMeta);
        }
        for(int j13 = 1; j13 <= 3; ++j13) {
            this.setBlockAndMetadata(world, 0, j13, -3, this.fenceBlock, this.fenceMeta);
            this.setBlockAndMetadata(world, 0, j13, 3, this.fenceBlock, this.fenceMeta);
        }
        if(this.hasOrcTorches) {
            this.placeOrcTorch(world, -1, 1, -3);
            this.placeOrcTorch(world, 1, 1, -3);
            this.placeOrcTorch(world, -1, 1, 3);
            this.placeOrcTorch(world, 1, 1, 3);
        }
        else {
            this.setBlockAndMetadata(world, -1, 2, -3, Blocks.torch, 2);
            this.setBlockAndMetadata(world, 1, 2, -3, Blocks.torch, 1);
            this.setBlockAndMetadata(world, -1, 2, 3, Blocks.torch, 2);
            this.setBlockAndMetadata(world, 1, 2, 3, Blocks.torch, 1);
        }
        if(random.nextBoolean()) {
            if(this.hasOrcForge) {
                this.setBlockAndMetadata(world, -1, 1, 0, LOTRMod.orcForge, 4);
                this.setGrassToDirt(world, -1, 0, 0);
                this.setBlockAndMetadata(world, -1, 1, -1, this.fenceBlock, this.fenceMeta);
                this.setBlockAndMetadata(world, -1, 1, 1, this.fenceBlock, this.fenceMeta);
            }
            else {
                this.placeChest(world, random, -1, 1, 0, 4, this.chestContents);
                this.setBlockAndMetadata(world, -1, 1, -1, Blocks.crafting_table, 0);
                this.setGrassToDirt(world, -1, 0, -1);
                this.setBlockAndMetadata(world, -1, 1, 1, this.tableBlock, 0);
                this.setGrassToDirt(world, -1, 0, 1);
            }
        }
        else if(this.hasOrcForge) {
            this.setBlockAndMetadata(world, 1, 1, 0, LOTRMod.orcForge, 5);
            this.setGrassToDirt(world, 1, 0, 0);
            this.setBlockAndMetadata(world, 1, 1, -1, this.fenceBlock, this.fenceMeta);
            this.setBlockAndMetadata(world, 1, 1, 1, this.fenceBlock, this.fenceMeta);
        }
        else {
            this.placeChest(world, random, 1, 1, 0, 5, this.chestContents);
            this.setBlockAndMetadata(world, 1, 1, -1, Blocks.crafting_table, 0);
            this.setGrassToDirt(world, 1, 0, -1);
            this.setBlockAndMetadata(world, 1, 1, 1, this.tableBlock, 0);
            this.setGrassToDirt(world, 1, 0, 1);
        }
        return true;
    }
}
