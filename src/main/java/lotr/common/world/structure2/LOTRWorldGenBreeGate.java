package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.LOTRMod;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenBreeGate
extends LOTRWorldGenBreeStructure {
    public LOTRWorldGenBreeGate(boolean flag) {
        super(flag);
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int j1;
        int k1;
        this.setOriginAndRotation(world, i, j, k, rotation, 1);
        this.setupRandomBlocks(random);
        if (this.restrictions) {
            for (int i1 = -4; i1 <= 4; ++i1) {
                for (k1 = 0; k1 <= 0; ++k1) {
                    j1 = this.getTopBlock(world, i1, k1) - 1;
                    if (this.isSurface(world, i1, j1, k1)) continue;
                    return false;
                }
            }
        }
        for (int i1 : new int[]{-4, 4}) {
            int k12 = 0;
            for (int j12 = 4; (((j12 >= 0) || !this.isOpaque(world, 0, j12, 0)) && (this.getY(j12) >= 0)); --j12) {
                this.setBlockAndMetadata(world, i1, j12, k12, this.beamBlock, this.beamMeta);
                this.setGrassToDirt(world, i1, j12 - 1, k12);
            }
            this.setBlockAndMetadata(world, i1, 5, k12, this.fenceBlock, this.fenceMeta);
            this.setBlockAndMetadata(world, i1, 6, k12, Blocks.torch, 5);
        }
        for (int i1 = -3; i1 <= 3; ++i1) {
            k1 = 0;
            j1 = 0;
            while (!this.isOpaque(world, i1, j1, k1) && this.getY(j1) >= 0) {
                this.placeRandomFloor(world, random, i1, j1, k1);
                this.setGrassToDirt(world, i1, j1 - 1, k1);
                --j1;
            }
            for (j1 = 1; j1 <= 3; ++j1) {
                this.setBlockAndMetadata(world, i1, j1, k1, LOTRMod.gateWoodenCross, 2);
            }
            this.setBlockAndMetadata(world, i1, 4, k1, this.plankSlabBlock, this.plankSlabMeta);
        }
        return true;
    }
}

