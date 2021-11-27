package lotr.common.world.structure2;

import java.util.Random;

import net.minecraft.world.World;

public class LOTRWorldGenSouthronTownCorner extends LOTRWorldGenSouthronStructure {
    public LOTRWorldGenSouthronTownCorner(boolean flag) {
        super(flag);
    }

    @Override
    protected boolean canUseRedBricks() {
        return false;
    }

    @Override
    protected boolean forceCedarWood() {
        return true;
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int j1;
        int k1;
        int i1;
        this.setOriginAndRotation(world, i, j, k, rotation, 4);
        this.setupRandomBlocks(random);
        if(this.restrictions) {
            for(i1 = -3; i1 <= 3; ++i1) {
                for(k1 = -3; k1 <= 3; ++k1) {
                    j1 = this.getTopBlock(world, i1, k1) - 1;
                    if(this.isSurface(world, i1, j1, k1)) continue;
                    return false;
                }
            }
        }
        for(i1 = -3; i1 <= 3; ++i1) {
            for(k1 = -3; k1 <= 3; ++k1) {
                for(j1 = 1; j1 <= 12; ++j1) {
                    this.setAir(world, i1, j1, k1);
                }
            }
        }
        this.loadStrScan("southron_town_corner");
        this.associateBlockMetaAlias("STONE", this.stoneBlock, this.stoneMeta);
        this.associateBlockMetaAlias("BRICK", this.brickBlock, this.brickMeta);
        this.associateBlockMetaAlias("BRICK_SLAB", this.brickSlabBlock, this.brickSlabMeta);
        this.associateBlockAlias("BRICK_STAIR", this.brickStairBlock);
        this.associateBlockMetaAlias("BRICK2", this.brick2Block, this.brick2Meta);
        this.associateBlockMetaAlias("BRICK2_SLAB", this.brick2SlabBlock, this.brick2SlabMeta);
        this.associateBlockMetaAlias("BRICK2_SLAB_INV", this.brick2SlabBlock, this.brick2SlabMeta | 8);
        this.associateBlockMetaAlias("FENCE", this.fenceBlock, this.fenceMeta);
        this.associateBlockAlias("GATE_METAL", this.gateMetalBlock);
        this.generateStrScan(world, random, 0, 0, 0);
        return true;
    }
}
