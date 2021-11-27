package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public abstract class LOTRWorldGenCampBase extends LOTRWorldGenStructureBase2 {
    protected Block tableBlock;
    protected Block brickBlock;
    protected int brickMeta;
    protected Block brickSlabBlock;
    protected int brickSlabMeta;
    protected Block fenceBlock;
    protected int fenceMeta;
    protected Block fenceGateBlock;
    protected Block farmBaseBlock;
    protected int farmBaseMeta;
    protected Block farmCropBlock;
    protected int farmCropMeta;
    protected boolean hasOrcTorches = false;
    protected boolean hasSkulls = false;

    public LOTRWorldGenCampBase(boolean flag) {
        super(flag);
    }

    @Override
    protected void setupRandomBlocks(Random random) {
        this.tableBlock = Blocks.crafting_table;
        this.brickBlock = Blocks.cobblestone;
        this.brickMeta = 0;
        this.brickSlabBlock = Blocks.stone_slab;
        this.brickSlabMeta = 3;
        this.fenceBlock = Blocks.fence;
        this.fenceMeta = 0;
        this.fenceGateBlock = Blocks.fence_gate;
        this.farmBaseBlock = Blocks.farmland;
        this.farmBaseMeta = 7;
        this.farmCropBlock = Blocks.wheat;
        this.farmCropMeta = 7;
    }

    protected abstract LOTRWorldGenStructureBase2 createTent(boolean var1, Random var2);

    protected abstract LOTREntityNPC getCampCaptain(World var1, Random var2);

    protected void placeNPCRespawner(World world, Random random, int i, int j, int k) {
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int l;
        int i14;
        if(this.restrictions) {
            if(!LOTRWorldGenStructureBase2.isSurfaceStatic(world, i, j - 1, k)) {
                return false;
            }
            if(world.getBlock(i, j, k).getMaterial().isLiquid()) {
                return false;
            }
        }
        this.setOriginAndRotation(world, i, j, k, rotation, 0);
        this.setupRandomBlocks(random);
        int groundRange = 12;
        for(int i12 = -groundRange; i12 <= groundRange; ++i12) {
            for(int k1 = -groundRange; k1 <= groundRange; ++k1) {
                int j1;
                int k2;
                int i2 = Math.abs(i12);
                if(i2 * i2 + (k2 = Math.abs(k1)) * k2 >= groundRange * groundRange || this.getBlock(world, i12, (j1 = this.getTopBlock(world, i12, k1)) - 1, k1) != Blocks.grass || random.nextInt(5) == 0) continue;
                this.setBlockAndMetadata(world, i12, j1 - 1, k1, Blocks.dirt, 1);
            }
        }
        int highestHeight = 0;
        for(int i13 = -1; i13 <= 1; ++i13) {
            for(int k1 = -1; k1 <= 1; ++k1) {
                int j1 = this.getTopBlock(world, i13, k1);
                if(j1 <= highestHeight) continue;
                highestHeight = j1;
            }
        }
        this.originY = this.getY(highestHeight);
        this.generateCentrepiece(world, random, 0, 0, 0);
        LOTREntityNPC captain = this.getCampCaptain(world, random);
        if(captain != null) {
            captain.spawnRidingHorse = false;
            this.spawnNPCAndSetHome(captain, world, 0, 1, 0, 24);
        }
        for(l = 0; l < 4; ++l) {
            int tentX = MathHelper.getRandomIntegerInRange(random, -3, 3);
            int tentZ = MathHelper.getRandomIntegerInRange(random, 6, 12);
            i14 = 0;
            int k1 = 0;
            int rot = l;
            if(rot == 0) {
                i14 = tentX;
                k1 = tentZ;
            }
            else if(rot == 1) {
                i14 = tentZ;
                k1 = -tentX;
            }
            else if(rot == 2) {
                i14 = -tentX;
                k1 = -tentZ;
            }
            else if(rot == 3) {
                i14 = -tentZ;
                k1 = tentX;
            }
            int j1 = this.getTopBlock(world, i14, k1);
            this.generateSubstructure(this.createTent(this.notifyChanges, random), world, random, i14, j1, k1, rot);
        }
        if(this.hasOrcTorches) {
            for(int i141 : new int[] {-2, 2}) {
                for(int k1 : new int[] {-2, 2}) {
                    int j1 = this.getTopBlock(world, i141, k1);
                    this.placeOrcTorch(world, i141, j1, k1);
                }
            }
        }
        if(this.generateFarm()) {
            int[] farmCoords = null;
            int farmRange = 12;
            int minFarmRange = 5;
            block7: for(int l2 = 0; l2 < 32; ++l2) {
                int k1;
                int i15 = MathHelper.getRandomIntegerInRange(random, -farmRange, farmRange);
                int dSq = i15 * i15 + (k1 = MathHelper.getRandomIntegerInRange(random, -farmRange, farmRange)) * k1;
                if(dSq <= minFarmRange * minFarmRange) continue;
                for(int i2 = i15 - 2; i2 <= i15 + 2; ++i2) {
                    for(int k2 = k1 - 2; k2 <= k1 + 2; ++k2) {
                        int j2 = this.getTopBlock(world, i2, k2) - 1;
                        if(!this.isSurface(world, i2, j2, k2) || !this.isAir(world, i2, j2 + 1, k2) && !this.isReplaceable(world, i2, j2 + 1, k2)) continue block7;
                    }
                }
                farmCoords = new int[] {i15, k1};
                break;
            }
            if(farmCoords != null) {
                int k2;
                int j2;
                int i2;
                i14 = farmCoords[0];
                int k1 = farmCoords[1];
                int highestFarmHeight = this.getTopBlock(world, i14, k1);
                for(i2 = i14 - 2; i2 <= i14 + 2; ++i2) {
                    for(k2 = k1 - 2; k2 <= k1 + 2; ++k2) {
                        j2 = this.getTopBlock(world, i2, k2);
                        if(j2 <= highestFarmHeight) continue;
                        highestFarmHeight = j2;
                    }
                }
                for(i2 = i14 - 2; i2 <= i14 + 2; ++i2) {
                    for(k2 = k1 - 2; k2 <= k1 + 2; ++k2) {
                        j2 = highestFarmHeight - 2;
                        while(!this.isOpaque(world, i2, j2, k2) && this.getY(j2) >= 0) {
                            this.setBiomeFiller(world, i2, j2, k2);
                            this.setGrassToDirt(world, i2, j2 - 1, k2);
                            --j2;
                        }
                        if(Math.abs(i2 - i14) == 2 || Math.abs(k2 - k1) == 2) {
                            this.setBlockAndMetadata(world, i2, highestFarmHeight, k2, this.fenceBlock, this.fenceMeta);
                            this.setBiomeTop(world, i2, highestFarmHeight - 1, k2);
                            this.setGrassToDirt(world, i2, highestFarmHeight - 2, k2);
                            continue;
                        }
                        if(i2 == i14 && k2 == k1) {
                            this.setBlockAndMetadata(world, i2, highestFarmHeight - 1, k2, Blocks.water, 0);
                            continue;
                        }
                        this.setBlockAndMetadata(world, i2, highestFarmHeight, k2, this.farmCropBlock, this.farmCropMeta);
                        this.setBlockAndMetadata(world, i2, highestFarmHeight - 1, k2, this.farmBaseBlock, this.farmBaseMeta);
                        this.setGrassToDirt(world, i2, highestFarmHeight - 2, k2);
                    }
                }
                int gate = random.nextInt(4);
                if(gate == 0) {
                    this.setBlockAndMetadata(world, i14, highestFarmHeight, k1 + 2, this.fenceGateBlock, 0);
                }
                else if(gate == 1) {
                    this.setBlockAndMetadata(world, i14 - 2, highestFarmHeight, k1, this.fenceGateBlock, 1);
                }
                else if(gate == 2) {
                    this.setBlockAndMetadata(world, i14, highestFarmHeight, k1 - 2, this.fenceGateBlock, 2);
                }
                else if(gate == 3) {
                    this.setBlockAndMetadata(world, i14 + 2, highestFarmHeight, k1, this.fenceGateBlock, 3);
                }
                int scarecrowX = i14 + (random.nextBoolean() ? -2 : 2);
                int scarecrowZ = k1 + (random.nextBoolean() ? -2 : 2);
                this.setBlockAndMetadata(world, scarecrowX, highestFarmHeight + 1, scarecrowZ, this.fenceBlock, this.fenceMeta);
                if(this.hasOrcTorches) {
                    this.setBlockAndMetadata(world, scarecrowX, highestFarmHeight + 2, scarecrowZ, Blocks.wool, 12);
                    this.placeSkull(world, random, scarecrowX, highestFarmHeight + 3, scarecrowZ);
                }
                else {
                    this.setBlockAndMetadata(world, scarecrowX, highestFarmHeight + 2, scarecrowZ, Blocks.hay_block, 0);
                    this.setBlockAndMetadata(world, scarecrowX, highestFarmHeight + 3, scarecrowZ, Blocks.pumpkin, random.nextInt(4));
                }
            }
        }
        if(this.hasSkulls) {
            int range;
            int j1;
            int k1;
            int i16;
            for(l = 0; l < 6; ++l) {
                range = 8;
                i16 = MathHelper.getRandomIntegerInRange(random, -range, range);
                if(i16 * i16 + (k1 = MathHelper.getRandomIntegerInRange(random, -range, range)) * k1 <= 20 || !this.isSurface(world, i16, (j1 = this.getTopBlock(world, i16, k1)) - 1, k1) || !this.isReplaceable(world, i16, j1, k1) || !this.isAir(world, i16, j1 + 1, k1)) continue;
                this.setBlockAndMetadata(world, i16, j1, k1, this.fenceBlock, this.fenceMeta);
                this.placeSkull(world, random, i16, j1 + 1, k1);
            }
            for(l = 0; l < 6; ++l) {
                range = 12;
                i16 = MathHelper.getRandomIntegerInRange(random, -range, range);
                if(i16 * i16 + (k1 = MathHelper.getRandomIntegerInRange(random, -range, range)) * k1 <= 20 || !this.isSurface(world, i16, (j1 = this.getTopBlock(world, i16, k1)) - 1, k1) || !this.isReplaceable(world, i16, j1, k1) || !this.isAir(world, i16, j1 + 1, k1)) continue;
                this.placeSkull(world, random, i16, j1, k1);
            }
        }
        this.placeNPCRespawner(world, random, 0, 0, 0);
        return true;
    }

    protected boolean generateFarm() {
        return true;
    }

    protected void generateCentrepiece(World world, Random random, int i, int j, int k) {
        for(int i1 = i - 1; i1 <= i + 1; ++i1) {
            for(int k1 = k - 1; k1 <= k + 1; ++k1) {
                int j1 = j - 1;
                while(!this.isOpaque(world, i1, j1, k1) && this.getY(j1) >= 0) {
                    this.setBlockAndMetadata(world, i1, j1, k1, this.brickBlock, this.brickMeta);
                    this.setGrassToDirt(world, i1, j1 - 1, k1);
                    --j1;
                }
                this.setBlockAndMetadata(world, i1, j, k1, this.brickSlabBlock, this.brickSlabMeta);
                this.setGrassToDirt(world, i1, j - 1, k1);
            }
        }
        this.setBlockAndMetadata(world, i, j, k, this.tableBlock, 0);
    }
}
