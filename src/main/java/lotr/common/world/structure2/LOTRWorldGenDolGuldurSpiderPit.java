package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.*;
import lotr.common.item.LOTRItemBanner;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenDolGuldurSpiderPit extends LOTRWorldGenWargPitBase {
    public LOTRWorldGenDolGuldurSpiderPit(boolean flag) {
        super(flag);
    }

    @Override
    protected void setupRandomBlocks(Random random) {
        super.setupRandomBlocks(random);
        this.brickBlock = LOTRMod.brick2;
        this.brickMeta = 8;
        this.brickSlabBlock = LOTRMod.slabSingle4;
        this.brickSlabMeta = 5;
        this.brickStairBlock = LOTRMod.stairsDolGuldurBrick;
        this.brickWallBlock = LOTRMod.wall2;
        this.brickWallMeta = 8;
        this.pillarBlock = this.beamBlock;
        this.pillarMeta = this.beamMeta;
        this.woolBlock = Blocks.wool;
        this.woolMeta = 15;
        this.carpetBlock = Blocks.carpet;
        this.carpetMeta = 15;
        this.gateMetalBlock = LOTRMod.gateIronBars;
        this.tableBlock = LOTRMod.dolGuldurTable;
        this.banner = LOTRItemBanner.BannerType.DOL_GULDUR;
        this.chestContents = LOTRChestContents.DOL_GULDUR_TENT;
    }

    @Override
    protected LOTREntityNPC getOrc(World world) {
        return new LOTREntityDolGuldurOrc(world);
    }

    @Override
    protected LOTREntityNPC getWarg(World world) {
        return new LOTREntityMirkwoodSpider(world);
    }

    @Override
    protected void setOrcSpawner(LOTREntityNPCRespawner spawner) {
        spawner.setSpawnClass(LOTREntityDolGuldurOrc.class);
    }

    @Override
    protected void setWargSpawner(LOTREntityNPCRespawner spawner) {
        spawner.setSpawnClass(LOTREntityMirkwoodSpider.class);
    }

    @Override
    protected void associateGroundBlocks() {
        super.associateGroundBlocks();
        this.clearScanAlias("GROUND_COVER");
        this.addBlockMetaAliasOption("GROUND_COVER", 1, LOTRMod.webUngoliant, 0);
        this.setBlockAliasChance("GROUND_COVER", 0.04f);
    }
}
