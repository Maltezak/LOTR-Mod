package lotr.common.world.map;

import java.util.Random;

import com.google.common.math.IntMath;

import lotr.common.LOTRMod;
import lotr.common.world.structure2.LOTRWorldGenStructureBase2;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTRWorldGenRammasEchor extends LOTRWorldGenStructureBase2 {
    public static LOTRWorldGenRammasEchor INSTANCE = new LOTRWorldGenRammasEchor(false);
    private int centreX;
    private int centreZ;
    private int radius = 500;
    private int radiusSq = this.radius * this.radius;
    private double wallThick = 0.03;
    private int wallTop = 85;
    private int gateBottom = 77;
    private int gateTop = 82;

    private LOTRWorldGenRammasEchor(boolean flag) {
        super(flag);
        this.centreX = LOTRWaypoint.MINAS_TIRITH.getXCoord();
        this.centreZ = LOTRWaypoint.MINAS_TIRITH.getZCoord();
    }

    private double isPosInWall(int i, int k) {
        int dx = i - this.centreX;
        int dz = k - this.centreZ;
        int distSq = dx * dx + dz * dz;
        double circleDist = Math.abs((double) distSq / (double) this.radiusSq - 1.0);
        return circleDist;
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        if(this.isPosInWall(i + 8, k + 8) < this.wallThick * 3.0) {
            for(int i1 = i; i1 <= i + 15; ++i1) {
                block1: for(int k1 = k; k1 <= k + 15; ++k1) {
                    double circleDist = this.isPosInWall(i1, k1);
                    if((circleDist >= this.wallThick)) continue;
                    float roadNear = LOTRRoads.isRoadNear(i1, k1, 9);
                    boolean gate = roadNear >= 0.0f;
                    boolean fences = false;
                    boolean wallEdge = circleDist > 0.025;
                    boolean ladder = false;
                    for(int j1 = this.wallTop; j1 > 0; --j1) {
                        if(fences) {
                            this.setBlockAndMetadata(world, i1, j1, k1, Blocks.fence, 0);
                        }
                        else {
                            if(j1 >= this.wallTop && wallEdge) {
                                this.setBlockAndMetadata(world, i1, j1, k1, LOTRMod.brick2, 11);
                            }
                            else if(j1 == this.wallTop && circleDist < 0.015) {
                                this.setBlockAndMetadata(world, i1, j1, k1, Blocks.wooden_slab, 0);
                            }
                            else {
                                this.setBlockAndMetadata(world, i1, j1, k1, LOTRMod.brick, 1);
                            }
                            if(wallEdge && j1 == this.wallTop && !ladder) {
                                this.setBlockAndMetadata(world, i1, j1 + 1, k1, LOTRMod.brick2, 11);
                                if(IntMath.mod(i1 + k1, 2) == 1) {
                                    this.setBlockAndMetadata(world, i1, j1 + 2, k1, LOTRMod.slabSingle5, 3);
                                }
                                else if(this.isTorchAt(i1, k1)) {
                                    this.setBlockAndMetadata(world, i1, j1 + 2, k1, Blocks.fence, 0);
                                    this.setBlockAndMetadata(world, i1, j1 + 3, k1, Blocks.torch, 5);
                                }
                            }
                            if(ladder) {
                                this.setBlockAndMetadata(world, i1, j1, k1 - 1, Blocks.ladder, 2);
                            }
                        }
                        Block below = this.getBlock(world, i1, j1 - 1, k1);
                        this.setGrassToDirt(world, i1, j1 - 1, k1);
                        if(below == Blocks.grass || below == Blocks.dirt || below == Blocks.stone) continue block1;
                        if(!gate) continue;
                        if(fences) {
                            if(j1 != this.gateBottom) continue;
                            continue block1;
                        }
                        int lerpGateTop = this.gateBottom + Math.round((this.gateTop - this.gateBottom) * MathHelper.sqrt_float(1.0f - roadNear));
                        if(j1 != lerpGateTop) continue;
                        if((circleDist <= 0.025)) continue block1;
                        fences = true;
                    }
                }
            }
        }
        return true;
    }

    private boolean isTorchAt(int i, int k) {
        int torchRange = 12;
        int xmod = IntMath.mod(i, torchRange);
        return IntMath.mod(xmod + (IntMath.mod(k, torchRange)), torchRange) == 0;
    }

    @Override
    protected int getX(int x, int z) {
        return x;
    }

    @Override
    protected int getZ(int x, int z) {
        return z;
    }

    @Override
    protected int getY(int y) {
        return y;
    }

    @Override
    protected int rotateMeta(Block block, int meta) {
        return meta;
    }
}
