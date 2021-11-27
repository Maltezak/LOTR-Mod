package lotr.common.world.structure2;

import java.util.Random;

import net.minecraft.world.World;

public class LOTRWorldGenSouthronFortGate extends LOTRWorldGenSouthronStructure {
    public LOTRWorldGenSouthronFortGate(boolean flag) {
        super(flag);
    }

    @Override
    protected boolean canUseRedBricks() {
        return false;
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int j1;
        int k1;
        int i1;
        this.setOriginAndRotation(world, i, j, k, rotation, 1);
        this.setupRandomBlocks(random);
        if(this.restrictions) {
            for(i1 = -5; i1 <= 5; ++i1) {
                for(k1 = -2; k1 <= 2; ++k1) {
                    j1 = this.getTopBlock(world, i1, k1) - 1;
                    if(this.isSurface(world, i1, j1, k1)) continue;
                    return false;
                }
            }
        }
        for(i1 = -5; i1 <= 5; ++i1) {
            for(k1 = 1; k1 <= 1; ++k1) {
                for(j1 = 1; j1 <= 8; ++j1) {
                    this.setAir(world, i1, j1, k1);
                }
            }
        }
        this.loadStrScan("southron_fortress_gate");
        this.associateBlockMetaAlias("BRICK", this.brickBlock, this.brickMeta);
        this.associateBlockAlias("BRICK_STAIR", this.brickStairBlock);
        this.associateBlockMetaAlias("PLANK", this.plankBlock, this.plankMeta);
        this.associateBlockMetaAlias("PLANK_SLAB", this.plankSlabBlock, this.plankSlabMeta);
        this.associateBlockMetaAlias("PLANK_SLAB_INV", this.plankSlabBlock, this.plankSlabMeta | 8);
        this.associateBlockAlias("PLANK_STAIR", this.plankStairBlock);
        this.associateBlockMetaAlias("FENCE", this.fenceBlock, this.fenceMeta);
        this.associateBlockMetaAlias("BEAM|8", this.woodBeamBlock, this.woodBeamMeta8);
        this.associateBlockAlias("GATE_METAL", this.gateMetalBlock);
        this.generateStrScan(world, random, 0, 0, 0);
        this.placeWallBanner(world, -3, 5, -1, this.bannerType, 2);
        this.placeWallBanner(world, 3, 5, -1, this.bannerType, 2);
        return true;
    }
}
