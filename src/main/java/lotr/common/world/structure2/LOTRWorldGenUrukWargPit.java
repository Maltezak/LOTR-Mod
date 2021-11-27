package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.*;
import lotr.common.item.LOTRItemBanner;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenUrukWargPit extends LOTRWorldGenWargPitBase {
    public LOTRWorldGenUrukWargPit(boolean flag) {
        super(flag);
    }

    @Override
    protected void setupRandomBlocks(Random random) {
        super.setupRandomBlocks(random);
        this.brickBlock = LOTRMod.brick2;
        this.brickMeta = 7;
        this.brickSlabBlock = LOTRMod.slabSingle4;
        this.brickSlabMeta = 4;
        this.brickStairBlock = LOTRMod.stairsUrukBrick;
        this.brickWallBlock = LOTRMod.wall2;
        this.brickWallMeta = 7;
        this.pillarBlock = this.beamBlock;
        this.pillarMeta = this.beamMeta;
        this.woolBlock = Blocks.wool;
        this.woolMeta = 12;
        this.carpetBlock = Blocks.carpet;
        this.carpetMeta = 12;
        this.barsBlock = LOTRMod.urukBars;
        this.gateOrcBlock = LOTRMod.gateUruk;
        this.tableBlock = LOTRMod.urukTable;
        this.banner = LOTRItemBanner.BannerType.ISENGARD;
        this.chestContents = LOTRChestContents.URUK_TENT;
    }

    @Override
    protected LOTREntityNPC getOrc(World world) {
        return new LOTREntityIsengardSnaga(world);
    }

    @Override
    protected LOTREntityNPC getWarg(World world) {
        return new LOTREntityUrukWarg(world);
    }

    @Override
    protected void setOrcSpawner(LOTREntityNPCRespawner spawner) {
        spawner.setSpawnClass(LOTREntityIsengardSnaga.class);
    }

    @Override
    protected void setWargSpawner(LOTREntityNPCRespawner spawner) {
        spawner.setSpawnClass(LOTREntityUrukWarg.class);
    }
}
