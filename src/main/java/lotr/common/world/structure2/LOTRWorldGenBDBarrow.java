package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityBarrowWight;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenBDBarrow extends LOTRWorldGenStructureBase2 {
    private LOTRWorldGenStructureBase2 ruinsGen = new LOTRWorldGenStoneRuin.STONE(3, 3);

    public LOTRWorldGenBDBarrow(boolean flag) {
        super(flag);
        this.ruinsGen.restrictions = false;
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int j1;
        int i1;
        int i12;
        int j12;
        int k1;
        int radius = 12;
        int height = 7;
        int base = -4;
        this.setOriginAndRotation(world, i, j, k, rotation, this.usingPlayer != null ? radius : 0);
        if(this.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for(int i13 = -radius; i13 <= radius; ++i13) {
                for(k1 = -radius; k1 <= radius; ++k1) {
                    j1 = this.getTopBlock(world, i13, k1) - 1;
                    if(this.getBlock(world, i13, j1, k1) != Blocks.grass) {
                        return false;
                    }
                    if(j1 < minHeight) {
                        minHeight = j1;
                    }
                    if(j1 <= maxHeight) continue;
                    maxHeight = j1;
                }
            }
            if(maxHeight - minHeight > 5) {
                return false;
            }
        }
        for(i1 = -radius; i1 <= radius; ++i1) {
            for(int j13 = height; j13 >= base; --j13) {
                for(int k12 = -radius; k12 <= radius; ++k12) {
                    if(i1 * i1 + (j13 - base) * (j13 - base) + k12 * k12 > radius * radius) continue;
                    boolean grass = !this.isOpaque(world, i1, j13 + 1, k12);
                    this.setBlockAndMetadata(world, i1, j13, k12, grass ? Blocks.grass : Blocks.dirt, 0);
                    this.setGrassToDirt(world, i1, j13 - 1, k12);
                }
            }
        }
        for(i1 = -radius; i1 <= radius; ++i1) {
            for(int k13 = -radius; k13 <= radius; ++k13) {
                int j14 = base - 1;
                while(!this.isOpaque(world, i1, j14, k13) && this.getY(j14) >= 0) {
                    if(i1 * i1 + k13 * k13 <= radius * radius) {
                        this.setBlockAndMetadata(world, i1, j14, k13, Blocks.dirt, 0);
                        this.setGrassToDirt(world, i1, j14 - 1, k13);
                    }
                    --j14;
                }
            }
        }
        int innerR = 5;
        int innerH = 5;
        int innerB = -2;
        for(i12 = -innerR - 1; i12 <= innerR + 1; ++i12) {
            for(int k14 = -innerR - 1; k14 <= innerR + 1; ++k14) {
                for(j12 = innerB + 1; j12 <= innerB + innerH + 1; ++j12) {
                    int d = i12 * i12 + (j12 - innerB - 1) * (j12 - innerB - 1) + k14 * k14;
                    if(d < innerR * innerR) {
                        this.setAir(world, i12, j12, k14);
                        if(d <= (innerR - 1) * (innerR - 1) || random.nextInt(3) != 0) continue;
                        this.placeRandomBrick(world, random, i12, j12, k14);
                        continue;
                    }
                    if(d >= (innerR + 1) * (innerR + 1)) continue;
                    this.placeRandomBrick(world, random, i12, j12, k14);
                }
                this.placeRandomBrick(world, random, i12, innerB, k14);
            }
        }
        this.placeSpawnerChest(world, random, 0, innerB + 1, 0, LOTRMod.spawnerChestStone, 0, LOTREntityBarrowWight.class, LOTRChestContents.BARROW_DOWNS);
        this.setBlockAndMetadata(world, 1, innerB + 1, 0, Blocks.stone_stairs, 0);
        this.setBlockAndMetadata(world, -1, innerB + 1, 0, Blocks.stone_stairs, 1);
        this.setBlockAndMetadata(world, 0, innerB + 1, -1, Blocks.stone_stairs, 2);
        this.setBlockAndMetadata(world, 0, innerB + 1, 1, Blocks.stone_stairs, 3);
        for(k1 = -radius + 2; k1 <= -innerR + 1; ++k1) {
            for(int i14 = -1; i14 <= 1; ++i14) {
                this.placeRandomBrick(world, random, i14, 0, k1);
                for(j12 = 1; j12 <= 3; ++j12) {
                    this.setAir(world, i14, j12, k1);
                }
                this.placeRandomBrick(world, random, i14, 4, k1);
            }
            for(j1 = 0; j1 <= 4; ++j1) {
                this.placeRandomBrick(world, random, -2, j1, k1);
                this.placeRandomBrick(world, random, 2, j1, k1);
            }
        }
        for(i12 = -1; i12 <= 1; ++i12) {
            this.setBlockAndMetadata(world, i12, innerB + 1, -innerR + 1, Blocks.stone_stairs, 3);
            for(j1 = innerB + 2; j1 <= 3; ++j1) {
                this.setAir(world, i12, j1, -innerR + 1);
                this.setAir(world, i12, j1, -innerR + 2);
            }
            this.setBlockAndMetadata(world, i12, innerB + 2, -innerR + 0, Blocks.stone_stairs, 3);
        }
        this.placeRandomBrick(world, random, -2, innerB + 1, -innerR + 1);
        this.placeRandomBrick(world, random, 2, innerB + 1, -innerR + 1);
        for(int i15 : new int[] {-3, 3}) {
            this.placeRandomBrick(world, random, i15, 1, -radius + 1);
            this.placeRandomBrick(world, random, i15, 0, -radius + 1);
            this.placeRandomBrick(world, random, i15, -1, -radius + 1);
            this.placeRandomBrick(world, random, i15, 2, -radius + 2);
            this.placeRandomBrick(world, random, i15, 1, -radius + 2);
        }
        for(int i16 = -2; i16 <= 2; ++i16) {
            this.placeRandomBrick(world, random, i16, 5, -radius + 4);
            if(Math.abs(i16) > 1) continue;
            this.placeRandomBrick(world, random, i16, 5, -radius + 3);
        }
        for(int j15 = 1; j15 <= 3; ++j15) {
            this.placeRandomBrick(world, random, -1, j15, -radius + 4);
            this.placeRandomBrick(world, random, 0, j15, -radius + 6);
            this.placeRandomBrick(world, random, 1, j15, -radius + 4);
        }
        int rX = 0;
        int rY = height + 1;
        int rZ = 0;
        this.ruinsGen.generateWithSetRotation(world, random, this.getX(rX, rZ), this.getY(rY), this.getZ(rX, rZ), this.getRotationMode());
        return true;
    }

    private void placeRandomBrick(World world, Random random, int i, int j, int k) {
        if(random.nextBoolean()) {
            this.setBlockAndMetadata(world, i, j, k, Blocks.stone, 0);
        }
        else if(random.nextInt(3) > 0) {
            int l = random.nextInt(2);
            if(l == 0) {
                this.setBlockAndMetadata(world, i, j, k, Blocks.cobblestone, 0);
            }
            if(l == 1) {
                this.setBlockAndMetadata(world, i, j, k, Blocks.mossy_cobblestone, 0);
            }
        }
        else {
            int l = random.nextInt(3);
            if(l == 0) {
                this.setBlockAndMetadata(world, i, j, k, Blocks.stonebrick, 0);
            }
            if(l == 1) {
                this.setBlockAndMetadata(world, i, j, k, Blocks.stonebrick, 0);
            }
            if(l == 2) {
                this.setBlockAndMetadata(world, i, j, k, Blocks.stonebrick, 0);
            }
        }
    }
}
