package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.LOTRMod;
import lotr.common.entity.npc.*;
import lotr.common.item.LOTRItemBanner;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenRuinedDwarvenTower extends LOTRWorldGenDwarvenTower {
    private boolean isGundabad;

    public LOTRWorldGenRuinedDwarvenTower(boolean flag) {
        super(flag);
        this.ruined = true;
        this.glowBrickBlock = this.brickBlock;
        this.glowBrickMeta = this.brickMeta;
    }

    @Override
    protected LOTREntityNPC getCommanderNPC(World world) {
        if(this.isGundabad) {
            return new LOTREntityGundabadOrcMercenaryCaptain(world);
        }
        return null;
    }

    @Override
    protected void setupRandomBlocks(Random random) {
        super.setupRandomBlocks(random);
        if(random.nextInt(3) == 0) {
            this.plankBlock = LOTRMod.planks;
            this.plankMeta = 3;
            this.plankSlabBlock = LOTRMod.woodSlabSingle;
            this.plankSlabMeta = 3;
        }
        if(random.nextInt(4) == 0) {
            this.barsBlock = Blocks.air;
        }
        else {
            int randomBars = random.nextInt(4);
            if(randomBars == 0) {
                this.barsBlock = LOTRMod.dwarfBars;
            }
            else if(randomBars == 1) {
                this.barsBlock = LOTRMod.orcSteelBars;
            }
            else if(randomBars == 2) {
                this.barsBlock = Blocks.iron_bars;
            }
            else if(randomBars == 3) {
                this.barsBlock = LOTRMod.bronzeBars;
            }
        }
        this.isGundabad = random.nextInt(3) == 0;
        if(this.isGundabad) {
            this.gateBlock = LOTRMod.gateOrc;
            this.tableBlock = LOTRMod.gundabadTable;
            this.forgeBlock = LOTRMod.orcForge;
            this.bannerType = LOTRItemBanner.BannerType.GUNDABAD;
            this.chestContents = LOTRChestContents.GUNDABAD_TENT;
        }
        else {
            this.gateBlock = LOTRMod.gateDwarven;
            this.tableBlock = LOTRMod.dwarvenTable;
            this.forgeBlock = LOTRMod.dwarvenForge;
            this.bannerType = null;
            this.chestContents = LOTRChestContents.DWARVEN_TOWER;
        }
    }

    @Override
    protected void placeBrick(World world, Random random, int i, int j, int k) {
        if(random.nextInt(4) == 0) {
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.brick4, 5);
        }
        else {
            super.placeBrick(world, random, i, j, k);
        }
    }

    @Override
    protected void placeBrickSlab(World world, Random random, int i, int j, int k, boolean flip) {
        if(random.nextInt(4) == 0) {
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.slabSingle7, 6 | (flip ? 8 : 0));
        }
        else {
            super.placeBrickSlab(world, random, i, j, k, flip);
        }
    }

    @Override
    protected void placeBrickStair(World world, Random random, int i, int j, int k, int meta) {
        if(random.nextInt(4) == 0) {
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.stairsDwarvenBrickCracked, meta);
        }
        else {
            super.placeBrickStair(world, random, i, j, k, meta);
        }
    }

    @Override
    protected void placeBrickWall(World world, Random random, int i, int j, int k) {
        if(random.nextInt(4) == 0) {
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.wall4, 5);
        }
        else {
            super.placeBrickWall(world, random, i, j, k);
        }
    }

    @Override
    protected void placePillar(World world, Random random, int i, int j, int k) {
        if(random.nextInt(4) == 0) {
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.pillar2, 0);
        }
        else {
            super.placePillar(world, random, i, j, k);
        }
    }
}
