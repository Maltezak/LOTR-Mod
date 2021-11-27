package lotr.common.world.structure2;

import java.util.Random;

import net.minecraft.world.World;

public class LOTRWorldGenRohanFortWall extends LOTRWorldGenRohanStructure {
    private int xMin;
    private int xMax;

    public LOTRWorldGenRohanFortWall(boolean flag) {
        this(flag, -4, 4);
    }

    public LOTRWorldGenRohanFortWall(boolean flag, int x0, int x1) {
        super(flag);
        this.xMin = x0;
        this.xMax = x1;
    }

    @Override
    protected boolean oneWoodType() {
        return true;
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 0);
        this.setupRandomBlocks(random);
        for(int i1 = this.xMin; i1 <= this.xMax; ++i1) {
            int j1;
            Math.abs(i1);
            int k1 = 0;
            this.findSurface(world, i1, k1);
            this.setupRandomBlocks(random);
            for(j1 = 1; (((j1 >= 0) || !this.isOpaque(world, i1, j1, k1)) && (this.getY(j1) >= 0)); --j1) {
                this.setBlockAndMetadata(world, i1, j1, k1, this.rockSlabDoubleBlock, this.rockSlabDoubleMeta);
                this.setGrassToDirt(world, i1, j1 - 1, k1);
            }
            for(j1 = 2; j1 <= 2; ++j1) {
                this.setBlockAndMetadata(world, i1, j1, k1, this.brickBlock, this.brickMeta);
            }
            int h = 5 + random.nextInt(2);
            for(int j12 = 3; j12 <= h; ++j12) {
                this.setBlockAndMetadata(world, i1, j12, k1, this.woodBeamBlock, this.woodBeamMeta);
            }
            if(!random.nextBoolean()) continue;
            this.setBlockAndMetadata(world, i1, h + 1, k1, this.plankSlabBlock, this.plankSlabMeta);
        }
        return true;
    }
}
