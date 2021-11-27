package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.LOTRMod;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenBreeHedgePart
extends LOTRWorldGenBreeStructure {
    private boolean grassOnly = false;

    public LOTRWorldGenBreeHedgePart(boolean flag) {
        super(flag);
    }

    public LOTRWorldGenBreeHedgePart setGrassOnly() {
        this.grassOnly = true;
        return this;
    }

    @Override
    protected void setupRandomBlocks(Random random) {
        super.setupRandomBlocks(random);
        this.fenceBlock = Blocks.fence;
        this.fenceMeta = 0;
        this.beamBlock = LOTRMod.woodBeamV1;
        this.beamMeta = 0;
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int i1;
        int j1;
        int k1;
        this.setOriginAndRotation(world, i, j, k, rotation, 0);
        this.setupRandomBlocks(random);
        if (this.restrictions && (!this.isSurface(world, i1 = 0, j1 = this.getTopBlock(world, i1, k1 = 0) - 1, k1) || this.grassOnly && this.getBlock(world, i1, j1, k1) != Blocks.grass)) {
            return false;
        }
        int j12 = 0;
        while (!this.isOpaque(world, 0, j12, 0) && this.getY(j12) >= 0) {
            this.setBlockAndMetadata(world, 0, j12, 0, LOTRMod.dirtPath, 0);
            this.setGrassToDirt(world, 0, j12 - 1, 0);
            --j12;
        }
        boolean hasBeams = random.nextInt(4) == 0;
        int height = 3 + random.nextInt(2);
        for (j1 = 1; j1 <= height; ++j1) {
            if (hasBeams && j1 <= 2) {
                this.setBlockAndMetadata(world, 0, j1, 0, this.beamBlock, this.beamMeta);
                this.setGrassToDirt(world, 0, j1 - 1, 0);
                continue;
            }
            if (random.nextInt(4) == 0) {
                this.setBlockAndMetadata(world, 0, j1, 0, this.fenceBlock, this.fenceMeta);
                continue;
            }
            int randLeaf = random.nextInt(4);
            if (randLeaf == 0) {
                this.setBlockAndMetadata(world, 0, j1, 0, Blocks.leaves, 4);
                continue;
            }
            if (randLeaf == 1) {
                this.setBlockAndMetadata(world, 0, j1, 0, LOTRMod.leaves2, 5);
                continue;
            }
            if (randLeaf == 2) {
                this.setBlockAndMetadata(world, 0, j1, 0, LOTRMod.leaves4, 4);
                continue;
            }
            if (randLeaf != 3) continue;
            this.setBlockAndMetadata(world, 0, j1, 0, LOTRMod.leaves7, 4);
        }
        return true;
    }
}

