package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.*;
import net.minecraft.world.World;

public class LOTRWorldGenMordorSpiderPit extends LOTRWorldGenMordorWargPit {
    public LOTRWorldGenMordorSpiderPit(boolean flag) {
        super(flag);
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        if(super.generateWithSetRotation(world, random, i, j, k, rotation)) {
            LOTREntityMordorOrcSpiderKeeper spiderKeeper = new LOTREntityMordorOrcSpiderKeeper(world);
            this.spawnNPCAndSetHome(spiderKeeper, world, 0, 1, 0, 8);
            return true;
        }
        return false;
    }

    @Override
    protected LOTREntityNPC getWarg(World world) {
        return new LOTREntityMordorSpider(world);
    }

    @Override
    protected void setWargSpawner(LOTREntityNPCRespawner spawner) {
        spawner.setSpawnClass(LOTREntityMordorSpider.class);
    }

    @Override
    protected void associateGroundBlocks() {
        super.associateGroundBlocks();
        this.clearScanAlias("GROUND_COVER");
        this.addBlockMetaAliasOption("GROUND_COVER", 1, LOTRMod.webUngoliant, 0);
        this.setBlockAliasChance("GROUND_COVER", 0.04f);
    }
}
