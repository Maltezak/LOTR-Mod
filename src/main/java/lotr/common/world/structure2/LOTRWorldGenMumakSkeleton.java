package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.world.World;

public class LOTRWorldGenMumakSkeleton extends LOTRWorldGenStructureBase2 {
    protected Block boneBlock;
    protected int boneMeta;

    public LOTRWorldGenMumakSkeleton(boolean flag) {
        super(flag);
    }

    @Override
    protected void setupRandomBlocks(Random random) {
        this.boneBlock = LOTRMod.boneBlock;
        this.boneMeta = 0;
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 0);
        this.setupRandomBlocks(random);
        if(this.restrictions) {
            for(int i1 = -3; i1 <= 3; ++i1) {
                for(int k1 = -3; k1 <= 17; ++k1) {
                    int j1 = this.getTopBlock(world, i1, k1) - 1;
                    if(j1 >= -2) continue;
                    return false;
                }
            }
        }
        if(this.usingPlayer == null) {
            this.originY -= random.nextInt(6);
        }
        this.loadStrScan("mumak_skeleton");
        this.associateBlockMetaAlias("BONE", this.boneBlock, this.boneMeta);
        this.generateStrScan(world, random, 0, 1, 0);
        return true;
    }
}
