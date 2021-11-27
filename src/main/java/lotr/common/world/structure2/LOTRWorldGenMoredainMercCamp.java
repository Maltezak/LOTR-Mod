package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.*;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTRWorldGenMoredainMercCamp extends LOTRWorldGenCampBase {
    public LOTRWorldGenMoredainMercCamp(boolean flag) {
        super(flag);
    }

    @Override
    protected void setupRandomBlocks(Random random) {
        super.setupRandomBlocks(random);
        this.tableBlock = LOTRMod.commandTable;
        this.brickBlock = LOTRMod.planks2;
        this.brickMeta = 2;
        this.brickSlabBlock = LOTRMod.woodSlabSingle3;
        this.brickSlabMeta = 2;
        this.fenceBlock = LOTRMod.fence2;
        this.fenceMeta = 2;
        this.fenceGateBlock = LOTRMod.fenceGateCedar;
    }

    @Override
    protected LOTRWorldGenStructureBase2 createTent(boolean flag, Random random) {
        return new LOTRWorldGenMoredainMercTent(false);
    }

    @Override
    protected LOTREntityNPC getCampCaptain(World world, Random random) {
        return null;
    }

    @Override
    protected void placeNPCRespawner(World world, Random random, int i, int j, int k) {
        LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
        respawner.setSpawnClass(LOTREntityMoredainMercenary.class);
        respawner.setCheckRanges(24, -12, 12, 10);
        respawner.setSpawnRanges(8, -4, 4, 16);
        this.placeNPCRespawner(respawner, world, i, j, k);
        int mercs = 2 + random.nextInt(5);
        for(int l = 0; l < mercs; ++l) {
            LOTREntityMoredainMercenary merc = new LOTREntityMoredainMercenary(world);
            this.spawnNPCAndSetHome(merc, world, 0, 1, 0, 16);
        }
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        if(super.generateWithSetRotation(world, random, i, j, k, rotation)) {
            int dummies = 1 + random.nextInt(3);
            for(int l = 0; l < dummies; ++l) {
                int r;
                int k1;
                int i1;
                float ang;
                for(int att = 0; att < 8 && !this.generateSubstructureWithRestrictionFlag(new LOTRWorldGenMoredainMercDummy(this.notifyChanges), world, random, i1 = (int) ((r = MathHelper.getRandomIntegerInRange(random, 8, 15)) * MathHelper.cos(ang = random.nextFloat() * 3.1415927f * 2.0f)), this.getTopBlock(world, i1, k1 = (int) (r * MathHelper.sin(ang))), k1, random.nextInt(4), true); ++att) {
                }
            }
            return true;
        }
        return false;
    }
}
