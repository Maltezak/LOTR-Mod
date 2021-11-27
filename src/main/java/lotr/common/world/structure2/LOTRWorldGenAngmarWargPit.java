package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.*;
import lotr.common.item.LOTRItemBanner;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenAngmarWargPit extends LOTRWorldGenWargPitBase {
    public LOTRWorldGenAngmarWargPit(boolean flag) {
        super(flag);
    }

    @Override
    protected void setupRandomBlocks(Random random) {
        super.setupRandomBlocks(random);
        this.brickBlock = LOTRMod.brick2;
        this.brickMeta = 0;
        this.brickSlabBlock = LOTRMod.slabSingle3;
        this.brickSlabMeta = 3;
        this.brickStairBlock = LOTRMod.stairsAngmarBrick;
        this.brickWallBlock = LOTRMod.wall2;
        this.brickWallMeta = 0;
        this.pillarBlock = LOTRMod.pillar2;
        this.pillarMeta = 4;
        this.woolBlock = Blocks.wool;
        this.woolMeta = 15;
        this.carpetBlock = Blocks.carpet;
        this.carpetMeta = 15;
        this.tableBlock = LOTRMod.angmarTable;
        this.banner = LOTRItemBanner.BannerType.ANGMAR;
        this.chestContents = LOTRChestContents.ANGMAR_TENT;
    }

    @Override
    protected LOTREntityNPC getOrc(World world) {
        return new LOTREntityAngmarOrc(world);
    }

    @Override
    protected LOTREntityNPC getWarg(World world) {
        return new LOTREntityAngmarWarg(world);
    }

    @Override
    protected void setOrcSpawner(LOTREntityNPCRespawner spawner) {
        spawner.setSpawnClass(LOTREntityAngmarOrc.class);
    }

    @Override
    protected void setWargSpawner(LOTREntityNPCRespawner spawner) {
        spawner.setSpawnClass(LOTREntityAngmarWarg.class);
    }

    @Override
    protected void associateGroundBlocks() {
        super.associateGroundBlocks();
        this.clearScanAlias("GROUND_COVER");
        this.addBlockMetaAliasOption("GROUND_COVER", 1, Blocks.snow_layer, 0);
        this.setBlockAliasChance("GROUND_COVER", 0.25f);
    }
}
