package lotr.common.world.structure2;

import lotr.common.LOTRMod;
import lotr.common.entity.npc.*;
import net.minecraft.world.World;

public class LOTRWorldGenHighElvenForge extends LOTRWorldGenElvenForge {
    public LOTRWorldGenHighElvenForge(boolean flag) {
        super(flag);
        this.brickBlock = LOTRMod.brick3;
        this.brickMeta = 2;
        this.pillarBlock = LOTRMod.pillar;
        this.pillarMeta = 10;
        this.slabBlock = LOTRMod.slabSingle5;
        this.slabMeta = 5;
        this.carvedBrickBlock = LOTRMod.brick2;
        this.carvedBrickMeta = 13;
        this.wallBlock = LOTRMod.wall2;
        this.wallMeta = 11;
        this.stairBlock = LOTRMod.stairsHighElvenBrick;
        this.torchBlock = LOTRMod.highElvenTorch;
        this.tableBlock = LOTRMod.highElvenTable;
        this.barsBlock = LOTRMod.highElfBars;
        this.woodBarsBlock = LOTRMod.highElfWoodBars;
        this.roofBlock = LOTRMod.clayTileDyed;
        this.roofMeta = 3;
        this.roofStairBlock = LOTRMod.stairsClayTileDyedLightBlue;
    }

    @Override
    protected LOTREntityElf getElf(World world) {
        return new LOTREntityHighElfSmith(world);
    }
}
