package lotr.common.world.structure2;

import java.util.Random;

import com.google.common.math.IntMath;

import lotr.common.LOTRMod;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class LOTRWorldGenRhudaurCastle extends LOTRWorldGenStructureBase2 {
    private Block brickBlock;
    private int brickMeta;
    private Block brickSlabBlock;
    private int brickSlabMeta;
    private Block brickCrackedBlock;
    private int brickCrackedMeta;
    private Block brickCrackedSlabBlock;
    private int brickCrackedSlabMeta;

    public LOTRWorldGenRhudaurCastle(boolean flag) {
        super(flag);
    }

    @Override
    protected void setupRandomBlocks(Random random) {
        if(random.nextBoolean()) {
            this.brickBlock = Blocks.stonebrick;
            this.brickMeta = 0;
            this.brickSlabBlock = Blocks.stone_slab;
            this.brickSlabMeta = 5;
            this.brickCrackedBlock = Blocks.stonebrick;
            this.brickCrackedMeta = 2;
            this.brickCrackedSlabBlock = LOTRMod.slabSingleV;
            this.brickCrackedSlabMeta = 1;
        }
        else {
            this.brickBlock = LOTRMod.brick2;
            this.brickMeta = 0;
            this.brickSlabBlock = LOTRMod.slabSingle3;
            this.brickSlabMeta = 3;
            this.brickCrackedBlock = LOTRMod.brick2;
            this.brickCrackedMeta = 1;
            this.brickCrackedSlabBlock = LOTRMod.slabSingle3;
            this.brickCrackedSlabMeta = 4;
        }
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int chestY;
        this.setOriginAndRotation(world, i, j, k, rotation, 0);
        this.setupRandomBlocks(random);
        int width = MathHelper.getRandomIntegerInRange(random, 6, 15);
        int height = MathHelper.getRandomIntegerInRange(random, 3, 8);
        for(int i1 = -width; i1 <= width; ++i1) {
            for(int k1 = -width; k1 <= width; ++k1) {
                int j1;
                int i2 = Math.abs(i1);
                int k2 = Math.abs(k1);
                if(i2 == width || k2 == width) {
                    int j12;
                    float f = MathHelper.randomFloatClamp(random, 0.7f, 1.0f);
                    int h = Math.round(height * f);
                    int b = 1;
                    if(k1 == -width && i2 <= 1) {
                        b = 4;
                    }
                    if(IntMath.mod(i2 + k2, 2) == IntMath.mod(width, 2)) {
                        ++h;
                    }
                    int top = this.getTopBlock(world, i1, k1) - 1;
                    boolean foundSurface = false;
                    for(j12 = top; j12 >= top - 16; --j12) {
                        if(!this.isSurface(world, i1, j12, k1)) continue;
                        foundSurface = true;
                        break;
                    }
                    if(!foundSurface) continue;
                    for(int j2 = b; j2 <= h; ++j2) {
                        boolean low;
                        boolean cracked;
                        int j3 = j12 + j2;
                        low = j2 < (int) (height * 0.5f) && j2 < h;
                        if(low && random.nextInt(40) == 0) continue;
                        boolean slab = low && random.nextInt(20) == 0 || j2 == h && random.nextInt(5) == 0;
                        cracked = random.nextInt(4) == 0;
                        if(cracked) {
                            if(slab) {
                                this.setBlockAndMetadata(world, i1, j3, k1, this.brickCrackedSlabBlock, this.brickCrackedSlabMeta);
                            }
                            else {
                                this.setBlockAndMetadata(world, i1, j3, k1, this.brickCrackedBlock, this.brickCrackedMeta);
                            }
                        }
                        else if(slab) {
                            this.setBlockAndMetadata(world, i1, j3, k1, this.brickSlabBlock, this.brickSlabMeta);
                        }
                        else {
                            this.setBlockAndMetadata(world, i1, j3, k1, this.brickBlock, this.brickMeta);
                        }
                        if(j2 != 1) continue;
                        this.setGrassToDirt(world, i1, j3 - 1, k1);
                    }
                    continue;
                }
                if(random.nextInt(16) == 0 && this.isSurface(world, i1, j1 = this.getTopBlock(world, i1, k1) - 1, k1)) {
                    if(random.nextInt(3) == 0) {
                        this.setBlockAndMetadata(world, i1, j1, k1, Blocks.gravel, 0);
                    }
                    else {
                        this.setBlockAndMetadata(world, i1, j1, k1, Blocks.cobblestone, 0);
                    }
                }
                if(random.nextInt(50) != 0 || !this.isSurface(world, i1, j1 = this.getTopBlock(world, i1, k1) - 1, k1)) continue;
                if(random.nextInt(3) == 0) {
                    this.setBlockAndMetadata(world, i1, j1 + 1, k1, this.brickCrackedSlabBlock, this.brickCrackedSlabMeta);
                }
                else {
                    this.setBlockAndMetadata(world, i1, j1 + 1, k1, this.brickSlabBlock, this.brickSlabMeta);
                }
                this.setGrassToDirt(world, i1, j1, k1);
            }
        }
        int chestX = width - 1;
        int chestZ = width - 1;
        if(random.nextBoolean()) {
            chestX *= -1;
        }
        if(random.nextBoolean()) {
            chestZ *= -1;
        }
        if(this.isSurface(world, chestX, (chestY = this.getTopBlock(world, chestX, chestZ)) - 1, chestZ)) {
            int chestMeta = Direction.directionToFacing[random.nextInt(4)];
            this.setBlockAndMetadata(world, chestX, chestY, chestZ, LOTRMod.chestStone, chestMeta);
            this.fillChest(world, random, chestX, chestY, chestZ, LOTRChestContents.RUINED_HOUSE, 5);
            this.fillChest(world, random, chestX, chestY, chestZ, LOTRChestContents.ORC_DUNGEON, 4);
            this.fillChest(world, random, chestX, chestY, chestZ, LOTRChestContents.DUNEDAIN_TOWER, 4);
        }
        return true;
    }
}
