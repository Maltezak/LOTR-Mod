package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.LOTRMod;
import lotr.common.entity.npc.*;
import net.minecraft.block.Block;
import net.minecraft.world.World;

public class LOTRWorldGenGaladhrimForge extends LOTRWorldGenElvenForge {
    public LOTRWorldGenGaladhrimForge(boolean flag) {
        super(flag);
        this.brickBlock = LOTRMod.brick;
        this.brickMeta = 11;
        this.pillarBlock = LOTRMod.pillar;
        this.pillarMeta = 1;
        this.slabBlock = LOTRMod.slabSingle2;
        this.slabMeta = 3;
        this.carvedBrickBlock = LOTRMod.brick2;
        this.carvedBrickMeta = 15;
        this.wallBlock = LOTRMod.wall;
        this.wallMeta = 10;
        this.stairBlock = LOTRMod.stairsElvenBrick;
        this.torchBlock = LOTRMod.mallornTorchSilver;
        this.tableBlock = LOTRMod.elvenTable;
        this.barsBlock = LOTRMod.galadhrimBars;
        this.woodBarsBlock = LOTRMod.galadhrimWoodBars;
        this.roofBlock = LOTRMod.clayTileDyed;
        this.roofMeta = 4;
        this.roofStairBlock = LOTRMod.stairsClayTileDyedYellow;
        this.chestBlock = LOTRMod.chestMallorn;
    }

    @Override
    protected LOTREntityElf getElf(World world) {
        return new LOTREntityGaladhrimSmith(world);
    }

    @Override
    protected Block getTorchBlock(Random random) {
        return LOTRWorldGenElfHouse.getRandomTorch(random);
    }
}
