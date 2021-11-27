package lotr.common.world.structure2;

import java.util.Random;

import com.google.common.math.IntMath;

import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityElf;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public abstract class LOTRWorldGenElvenForge extends LOTRWorldGenStructureBase2 {
    protected Block brickBlock;
    protected int brickMeta;
    protected Block pillarBlock;
    protected int pillarMeta;
    protected Block slabBlock;
    protected int slabMeta;
    protected Block carvedBrickBlock;
    protected int carvedBrickMeta;
    protected Block wallBlock;
    protected int wallMeta;
    protected Block stairBlock;
    protected Block torchBlock;
    protected Block tableBlock;
    protected Block barsBlock;
    protected Block woodBarsBlock;
    protected Block roofBlock;
    protected int roofMeta;
    protected Block roofStairBlock;
    protected Block chestBlock = Blocks.chest;
    protected boolean ruined = false;

    public LOTRWorldGenElvenForge(boolean flag) {
        super(flag);
    }

    protected abstract LOTREntityElf getElf(World var1);

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int k2;
        LOTREntityElf elf;
        int k1;
        int i1;
        int j1;
        int i2;
        int k12;
        this.setOriginAndRotation(world, i, j, k, rotation, 7);
        if(this.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for(int i12 = -6; i12 <= 6; ++i12) {
                for(k12 = -6; k12 <= 6; ++k12) {
                    j1 = this.getTopBlock(world, i12, k12);
                    Block block = this.getBlock(world, i12, j1 - 1, k12);
                    if(block != Blocks.grass) {
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
        for(i1 = -4; i1 <= 4; ++i1) {
            for(k1 = -4; k1 <= 4; ++k1) {
                this.layFoundation(world, i1, k1, random);
            }
        }
        for(i1 = -2; i1 <= 2; ++i1) {
            this.layFoundation(world, i1, -5, random);
            this.layFoundation(world, i1, 5, random);
        }
        for(int k13 = -2; k13 <= 2; ++k13) {
            this.layFoundation(world, -5, k13, random);
            this.layFoundation(world, 5, k13, random);
        }
        for(i1 = -1; i1 <= 1; ++i1) {
            this.layFoundation(world, i1, -6, random);
        }
        this.placeStairs(world, -1, 1, -6, 1, random);
        this.placeStairs(world, 0, 1, -6, 2, random);
        this.placeStairs(world, 1, 1, -6, 0, random);
        for(int l = 0; l <= 3; ++l) {
            int width = 4 - l;
            int j12 = 7 + l;
            for(int i13 = -width; i13 <= width; ++i13) {
                this.placeRoofStairs(world, i13, j12, -width, 2, random);
                this.placeRoofStairs(world, i13, j12, width, 3, random);
                this.placeRoofStairs(world, i13, j12 - 1, -width, 7, random);
                this.placeRoofStairs(world, i13, j12 - 1, width, 6, random);
            }
            for(k12 = -width + 1; k12 <= width - 1; ++k12) {
                this.placeRoofStairs(world, -width, j12, k12, 1, random);
                this.placeRoofStairs(world, width, j12, k12, 0, random);
                this.placeRoofStairs(world, -width, j12 - 1, k12, 4, random);
                this.placeRoofStairs(world, width, j12 - 1, k12, 5, random);
            }
            if(l >= 3) continue;
            int width2 = 2 - l;
            for(int i14 = -width2; i14 <= width2; ++i14) {
                this.placeRoofStairs(world, i14, j12, -width - 1, 2, random);
                this.placeRoofStairs(world, i14, j12, width + 1, 3, random);
                this.placeRoof(world, i14, j12, -width, random);
                this.placeRoof(world, i14, j12, width, random);
            }
            for(int k14 = -width2; k14 <= width2; ++k14) {
                this.placeRoofStairs(world, -width - 1, j12, k14, 1, random);
                this.placeRoofStairs(world, width + 1, j12, k14, 0, random);
                this.placeRoof(world, -width, j12, k14, random);
                this.placeRoof(world, width, j12, k14, random);
            }
            if(width2 <= 0) continue;
            for(int l1 = 0; l1 <= 1; ++l1) {
                for(int l2 = 0; l2 <= 1; ++l2) {
                    int l3 = IntMath.pow(-1, l2);
                    this.placeRoofStairs(world, -width2, j12, l3 * (width + l1), 1, random);
                    this.placeRoofStairs(world, width2, j12, l3 * (width + l1), 0, random);
                    this.placeRoofStairs(world, l3 * (width + l1), j12, -width2, 2, random);
                    this.placeRoofStairs(world, l3 * (width + l1), j12, width2, 3, random);
                }
            }
        }
        this.setBlockAndMetadata(world, 0, 10, -1, this.carvedBrickBlock, this.carvedBrickMeta);
        this.setBlockAndMetadata(world, 0, 10, 1, this.carvedBrickBlock, this.carvedBrickMeta);
        this.setBlockAndMetadata(world, -1, 10, 0, this.carvedBrickBlock, this.carvedBrickMeta);
        this.setBlockAndMetadata(world, 1, 10, 0, this.carvedBrickBlock, this.carvedBrickMeta);
        this.placeRoofStairs(world, 0, 11, -1, 2, random);
        this.placeRoofStairs(world, 0, 11, 1, 3, random);
        this.placeRoofStairs(world, -1, 11, 0, 1, random);
        this.placeRoofStairs(world, 1, 11, 0, 0, random);
        this.buildPillar(world, -5, -2, random);
        this.buildPillar(world, -5, 2, random);
        this.buildPillar(world, 5, -2, random);
        this.buildPillar(world, 5, 2, random);
        this.buildPillar(world, -2, -5, random);
        this.buildPillar(world, 2, -5, random);
        this.buildPillar(world, -2, 5, random);
        this.buildPillar(world, 2, 5, random);
        this.buildPillar(world, -4, -4, random);
        this.buildPillar(world, -4, 4, random);
        this.buildPillar(world, 4, -4, random);
        this.buildPillar(world, 4, 4, random);
        this.buildWall(world, 2, -4, random);
        this.buildWall(world, 3, -4, random);
        this.buildWall(world, 4, -3, random);
        this.buildWall(world, 4, -2, random);
        this.buildWall(world, 5, -1, random);
        this.buildWall(world, 5, 0, random);
        this.buildWall(world, 5, 1, random);
        this.buildWall(world, 4, 2, random);
        this.buildWall(world, 4, 3, random);
        this.buildWall(world, 3, 4, random);
        this.buildWall(world, 2, 4, random);
        this.buildWall(world, 1, 5, random);
        this.buildWall(world, 0, 5, random);
        this.buildWall(world, -1, 5, random);
        this.buildWall(world, -2, 4, random);
        this.buildWall(world, -3, 4, random);
        this.buildWall(world, -4, 3, random);
        this.buildWall(world, -4, 2, random);
        this.buildWall(world, -5, 1, random);
        this.buildWall(world, -5, 0, random);
        this.buildWall(world, -5, -1, random);
        this.buildWall(world, -4, -2, random);
        this.buildWall(world, -4, -3, random);
        this.buildWall(world, -3, -4, random);
        this.buildWall(world, -2, -4, random);
        this.placeStairs(world, -1, 2, -5, 0, random);
        this.placeStairs(world, 1, 2, -5, 1, random);
        if(!this.ruined) {
            this.setBlockAndMetadata(world, -1, 5, -5, this.woodBarsBlock, 0);
            this.setBlockAndMetadata(world, 0, 5, -5, this.woodBarsBlock, 0);
            this.setBlockAndMetadata(world, 1, 5, -5, this.woodBarsBlock, 0);
            this.setBlockAndMetadata(world, 2, 5, -4, this.woodBarsBlock, 0);
            this.setBlockAndMetadata(world, 3, 5, -4, this.woodBarsBlock, 0);
            this.setBlockAndMetadata(world, 4, 5, -3, this.woodBarsBlock, 0);
            this.setBlockAndMetadata(world, 4, 5, -2, this.woodBarsBlock, 0);
            this.setBlockAndMetadata(world, 5, 5, -1, this.woodBarsBlock, 0);
            this.setBlockAndMetadata(world, 5, 5, 0, this.woodBarsBlock, 0);
            this.setBlockAndMetadata(world, 5, 5, 1, this.woodBarsBlock, 0);
            this.setBlockAndMetadata(world, 4, 5, 2, this.woodBarsBlock, 0);
            this.setBlockAndMetadata(world, 4, 5, 3, this.woodBarsBlock, 0);
            this.setBlockAndMetadata(world, 3, 5, 4, this.woodBarsBlock, 0);
            this.setBlockAndMetadata(world, 2, 5, 4, this.woodBarsBlock, 0);
            this.setBlockAndMetadata(world, 1, 5, 5, this.woodBarsBlock, 0);
            this.setBlockAndMetadata(world, 0, 5, 5, this.woodBarsBlock, 0);
            this.setBlockAndMetadata(world, -1, 5, 5, this.woodBarsBlock, 0);
            this.setBlockAndMetadata(world, -2, 5, 4, this.woodBarsBlock, 0);
            this.setBlockAndMetadata(world, -3, 5, 4, this.woodBarsBlock, 0);
            this.setBlockAndMetadata(world, -4, 5, 3, this.woodBarsBlock, 0);
            this.setBlockAndMetadata(world, -4, 5, 2, this.woodBarsBlock, 0);
            this.setBlockAndMetadata(world, -5, 5, 1, this.woodBarsBlock, 0);
            this.setBlockAndMetadata(world, -5, 5, 0, this.woodBarsBlock, 0);
            this.setBlockAndMetadata(world, -5, 5, -1, this.woodBarsBlock, 0);
            this.setBlockAndMetadata(world, -4, 5, -2, this.woodBarsBlock, 0);
            this.setBlockAndMetadata(world, -4, 5, -3, this.woodBarsBlock, 0);
            this.setBlockAndMetadata(world, -3, 5, -4, this.woodBarsBlock, 0);
            this.setBlockAndMetadata(world, -2, 5, -4, this.woodBarsBlock, 0);
        }
        for(i1 = -1; i1 <= 1; ++i1) {
            this.placePillar(world, i1, 6, -5, random);
        }
        for(i1 = -5; i1 <= 5; ++i1) {
            for(k1 = -5; k1 <= 5; ++k1) {
                i2 = Math.abs(i1);
                k2 = Math.abs(k1);
                if((i2 > 2 || k2 > 2) && i2 != 0 && k2 != 0) continue;
                this.placePillar(world, i1, 1, k1, random);
            }
        }
        if(!this.ruined) {
            for(i1 = -4; i1 <= 4; i1 += 8) {
                this.setBlockAndMetadata(world, i1, 2, -1, Blocks.anvil, 0);
                this.setBlockAndMetadata(world, i1, 2, 1, this.tableBlock, 0);
            }
        }
        this.setBlockAndMetadata(world, -4, 2, 0, LOTRMod.elvenForge, 4);
        this.setBlockAndMetadata(world, 4, 2, 0, LOTRMod.elvenForge, 5);
        if(!this.ruined) {
            this.placeChest(world, random, -1, 2, 4, this.chestBlock, 2, LOTRChestContents.ELVEN_FORGE);
            this.setBlockAndMetadata(world, 0, 2, 4, Blocks.crafting_table, 0);
            this.placeChest(world, random, 1, 2, 4, this.chestBlock, 2, LOTRChestContents.ELVEN_FORGE);
        }
        this.setBlockAndMetadata(world, 0, 1, -2, this.carvedBrickBlock, this.carvedBrickMeta);
        this.setBlockAndMetadata(world, 0, 1, 2, this.carvedBrickBlock, this.carvedBrickMeta);
        this.setBlockAndMetadata(world, -2, 1, 0, this.carvedBrickBlock, this.carvedBrickMeta);
        this.setBlockAndMetadata(world, 2, 1, 0, this.carvedBrickBlock, this.carvedBrickMeta);
        for(i1 = -1; i1 <= 1; ++i1) {
            for(k1 = -1; k1 <= 1; ++k1) {
                i2 = Math.abs(i1);
                k2 = Math.abs(k1);
                if(i2 == 1 && k2 == 1) {
                    this.placePillar(world, i1, 2, k1, random);
                    this.placePillar(world, i1, 3, k1, random);
                    this.placeSlab(world, i1, 4, k1, false, random);
                }
                if(i2 + k2 != 1) continue;
                for(j1 = 2; j1 <= 9; ++j1) {
                    this.placeBrick(world, i1, j1, k1, random);
                }
            }
        }
        if(!this.ruined) {
            this.setBlockAndMetadata(world, 0, 2, 0, Blocks.lava, 0);
        }
        this.setBlockAndMetadata(world, 0, 2, -1, LOTRMod.elvenForge, 2);
        this.setBlockAndMetadata(world, 0, 3, -1, this.barsBlock, 0);
        if(!this.ruined) {
            this.setBlockAndMetadata(world, -1, 5, -1, this.getTorchBlock(random), 1);
            this.setBlockAndMetadata(world, 1, 5, -1, this.getTorchBlock(random), 2);
            this.setBlockAndMetadata(world, -1, 5, 1, this.getTorchBlock(random), 1);
            this.setBlockAndMetadata(world, 1, 5, 1, this.getTorchBlock(random), 2);
        }
        if((elf = this.getElf(world)) != null) {
            this.spawnNPCAndSetHome(elf, world, 0, 2, -2, 8);
        }
        return true;
    }

    private void layFoundation(World world, int i, int k, Random random) {
        int j = 0;
        while(!this.isOpaque(world, i, j, k) && this.getY(j) >= 0) {
            this.placeBrick(world, i, j, k, random);
            this.setGrassToDirt(world, i, j - 1, k);
            --j;
        }
        this.placeBrick(world, i, 1, k, random);
        for(j = 2; j <= 6; ++j) {
            this.setAir(world, i, j, k);
        }
    }

    private void buildPillar(World world, int i, int k, Random random) {
        for(int j = 1; j <= 6; ++j) {
            this.placePillar(world, i, j, k, random);
        }
    }

    private void buildWall(World world, int i, int k, Random random) {
        this.placePillar(world, i, 2, k, random);
        this.placeWall(world, i, 3, k, random);
        this.placePillar(world, i, 6, k, random);
    }

    protected void placeBrick(World world, int i, int j, int k, Random random) {
        this.setBlockAndMetadata(world, i, j, k, this.brickBlock, this.brickMeta);
    }

    protected void placePillar(World world, int i, int j, int k, Random random) {
        this.setBlockAndMetadata(world, i, j, k, this.pillarBlock, this.pillarMeta);
    }

    protected void placeSlab(World world, int i, int j, int k, boolean flag, Random random) {
        this.setBlockAndMetadata(world, i, j, k, this.slabBlock, flag ? this.slabMeta | 8 : this.slabMeta);
    }

    protected void placeWall(World world, int i, int j, int k, Random random) {
        this.setBlockAndMetadata(world, i, j, k, this.wallBlock, this.wallMeta);
    }

    protected void placeStairs(World world, int i, int j, int k, int meta, Random random) {
        this.setBlockAndMetadata(world, i, j, k, this.stairBlock, meta);
    }

    protected void placeRoof(World world, int i, int j, int k, Random random) {
        this.setBlockAndMetadata(world, i, j, k, this.roofBlock, this.roofMeta);
    }

    protected void placeRoofStairs(World world, int i, int j, int k, int meta, Random random) {
        this.setBlockAndMetadata(world, i, j, k, this.roofStairBlock, meta);
    }

    protected Block getTorchBlock(Random random) {
        return this.torchBlock;
    }
}
