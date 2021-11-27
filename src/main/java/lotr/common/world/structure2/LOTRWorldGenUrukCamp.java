package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.*;
import net.minecraft.world.World;

public class LOTRWorldGenUrukCamp extends LOTRWorldGenCampBase {
    public LOTRWorldGenUrukCamp(boolean flag) {
        super(flag);
    }

    @Override
    protected void setupRandomBlocks(Random random) {
        super.setupRandomBlocks(random);
        this.tableBlock = LOTRMod.commandTable;
        this.brickBlock = LOTRMod.brick2;
        this.brickMeta = 7;
        this.brickSlabBlock = LOTRMod.slabSingle4;
        this.brickSlabMeta = 4;
        this.fenceBlock = LOTRMod.fence;
        this.fenceMeta = 3;
        this.fenceGateBlock = LOTRMod.fenceGateCharred;
        this.hasOrcTorches = true;
        this.hasSkulls = true;
    }

    @Override
    protected LOTRWorldGenStructureBase2 createTent(boolean flag, Random random) {
        if(random.nextInt(6) == 0) {
            return new LOTRWorldGenUrukForgeTent(false);
        }
        return new LOTRWorldGenUrukTent(false);
    }

    @Override
    protected LOTREntityNPC getCampCaptain(World world, Random random) {
        return random.nextBoolean() ? new LOTREntityUrukHaiTrader(world) : new LOTREntityUrukHaiMercenaryCaptain(world);
    }

    @Override
    protected void placeNPCRespawner(World world, Random random, int i, int j, int k) {
        LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
        respawner.setSpawnClasses(LOTREntityUrukHai.class, LOTREntityUrukHaiCrossbower.class);
        respawner.setCheckRanges(24, -12, 12, 12);
        respawner.setSpawnRanges(8, -4, 4, 16);
        this.placeNPCRespawner(respawner, world, i, j, k);
    }
}
