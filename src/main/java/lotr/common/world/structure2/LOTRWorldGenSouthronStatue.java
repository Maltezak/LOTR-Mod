package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.LOTRMod;
import net.minecraft.world.World;

public class LOTRWorldGenSouthronStatue extends LOTRWorldGenSouthronStructure {
    public LOTRWorldGenSouthronStatue(boolean flag) {
        super(flag);
    }

    @Override
    protected boolean canUseRedBricks() {
        return false;
    }

    @Override
    protected void setupRandomBlocks(Random random) {
        super.setupRandomBlocks(random);
        if(this.isUmbar()) {
            this.brick2Block = LOTRMod.brick6;
            this.brick2Meta = 6;
            this.brick2SlabBlock = LOTRMod.slabSingle13;
            this.brick2SlabMeta = 2;
            this.brick2StairBlock = LOTRMod.stairsUmbarBrick;
            this.brick2WallBlock = LOTRMod.wall5;
            this.brick2WallMeta = 0;
            this.brick2CarvedBlock = LOTRMod.brick6;
            this.brick2CarvedMeta = 8;
            this.pillar2Block = LOTRMod.pillar2;
            this.pillar2Meta = 10;
        }
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int k1;
        int i1;
        this.setOriginAndRotation(world, i, j, k, rotation, 6);
        this.setupRandomBlocks(random);
        if(this.restrictions) {
            for(i1 = -5; i1 <= 5; ++i1) {
                for(k1 = -5; k1 <= 5; ++k1) {
                    int j1 = this.getTopBlock(world, i1, k1) - 1;
                    if(this.isSurface(world, i1, j1, k1)) continue;
                    return false;
                }
            }
        }
        for(i1 = -5; i1 <= 5; ++i1) {
            for(k1 = -5; k1 <= 5; ++k1) {
                int i2 = Math.abs(i1);
                int k2 = Math.abs(k1);
                if(i2 == 5 && k2 == 5) continue;
                for(int j1 = 1; j1 <= 10; ++j1) {
                    this.setAir(world, i1, j1, k1);
                }
            }
        }
        this.loadStrScan("southron_statue_base");
        this.assocStatueBlocks();
        this.generateStrScan(world, random, 0, 0, 0);
        String statue = this.getRandomStatueStrscan(random);
        this.loadStrScan(statue);
        this.assocStatueBlocks();
        this.generateStrScan(world, random, 0, 4, 0);
        return true;
    }

    protected String getRandomStatueStrscan(Random random) {
        String[] statues = new String[] {"mumak", "bird", "snake"};
        String str = "southron_statue_" + statues[random.nextInt(statues.length)];
        return str;
    }

    private void assocStatueBlocks() {
        this.associateBlockMetaAlias("STONE", this.stoneBlock, this.stoneMeta);
        this.associateBlockMetaAlias("BRICK", this.brickBlock, this.brickMeta);
        this.associateBlockMetaAlias("BRICK_SLAB", this.brickSlabBlock, this.brickSlabMeta);
        this.associateBlockMetaAlias("BRICK_SLAB_INV", this.brickSlabBlock, this.brickSlabMeta | 8);
        this.associateBlockAlias("BRICK_STAIR", this.brickStairBlock);
        this.associateBlockMetaAlias("BRICK_WALL", this.brickWallBlock, this.brickWallMeta);
        this.associateBlockMetaAlias("PILLAR", this.pillarBlock, this.pillarMeta);
        this.associateBlockMetaAlias("BRICK2", this.brick2Block, this.brick2Meta);
        this.associateBlockMetaAlias("BRICK2_SLAB", this.brick2SlabBlock, this.brick2SlabMeta);
        this.associateBlockMetaAlias("BRICK2_SLAB_INV", this.brick2SlabBlock, this.brick2SlabMeta | 8);
        this.associateBlockAlias("BRICK2_STAIR", this.brick2StairBlock);
        this.associateBlockMetaAlias("BRICK2_WALL", this.brick2WallBlock, this.brick2WallMeta);
        this.associateBlockMetaAlias("BRICK2_CARVED", this.brick2CarvedBlock, this.brick2CarvedMeta);
        this.associateBlockMetaAlias("PILLAR2", this.pillar2Block, this.pillar2Meta);
    }
}
