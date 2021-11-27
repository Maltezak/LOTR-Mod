package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.LOTRMod;
import lotr.common.entity.npc.*;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenMoredainHutTrader extends LOTRWorldGenMoredainHut {
    public LOTRWorldGenMoredainHutTrader(boolean flag) {
        super(flag);
    }

    @Override
    protected int getOffset() {
        return 4;
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int k2;
        int k1;
        int i2;
        int i1;
        if(!super.generateWithSetRotation(world, random, i, j, k, rotation)) {
            return false;
        }
        for(i1 = -3; i1 <= 3; ++i1) {
            for(k1 = -3; k1 <= 3; ++k1) {
                this.layFoundation(world, i1, k1);
                for(int j1 = 1; j1 <= 6; ++j1) {
                    this.setAir(world, i1, j1, k1);
                }
                i2 = Math.abs(i1);
                k2 = Math.abs(k1);
                if(i2 == 3 || k2 == 3) {
                    this.setBlockAndMetadata(world, i1, 1, k1, this.clayBlock, this.clayMeta);
                }
                if(i2 == 3 && k2 <= 2 || k2 == 3 && i2 <= 2) {
                    this.setBlockAndMetadata(world, i1, 2, k1, this.brickBlock, this.brickMeta);
                    this.setBlockAndMetadata(world, i1, 3, k1, this.stainedClayBlock, this.stainedClayMeta);
                    this.setBlockAndMetadata(world, i1, 4, k1, this.stainedClayBlock, this.stainedClayMeta);
                }
                if(i2 != 3 || k2 != 3) continue;
                for(int j1 = 2; j1 <= 3; ++j1) {
                    this.setBlockAndMetadata(world, i1, j1, k1, this.fenceBlock, this.fenceMeta);
                }
            }
        }
        for(i1 = -4; i1 <= 4; ++i1) {
            for(k1 = -4; k1 <= 4; ++k1) {
                i2 = Math.abs(i1);
                k2 = Math.abs(k1);
                if(i2 == 4 && k2 <= 2 || k2 == 4 && i2 <= 2) {
                    this.setBlockAndMetadata(world, i1, 4, k1, this.thatchSlabBlock, this.thatchSlabMeta | 8);
                }
                if(i2 == 3 && k2 == 3) {
                    this.setBlockAndMetadata(world, i1, 4, k1, this.thatchSlabBlock, this.thatchSlabMeta | 8);
                }
                if(i2 == 4 && k2 == 0 || k2 == 4 && i2 == 0) {
                    this.setBlockAndMetadata(world, i1, 4, k1, this.thatchBlock, this.thatchMeta);
                    this.setBlockAndMetadata(world, i1, 5, k1, this.fenceBlock, this.fenceMeta);
                    this.setBlockAndMetadata(world, i1, 6, k1, this.fenceBlock, this.fenceMeta);
                    if(i1 != 0 || k1 != -4) {
                        this.setBlockAndMetadata(world, i1, 3, k1, this.fenceBlock, this.fenceMeta);
                        this.dropFence(world, i1, 1, k1);
                    }
                }
                if(i2 == 3 && k2 <= 2 || k2 == 3 && i2 <= 2) {
                    this.setBlockAndMetadata(world, i1, 5, k1, this.thatchSlabBlock, this.thatchSlabMeta);
                }
                if(i2 == 3 && k2 == 0 || k2 == 3 && i2 == 0) {
                    this.setBlockAndMetadata(world, i1, 5, k1, this.thatchBlock, this.thatchMeta);
                }
                if(i2 == 2 && k2 <= 2 || k2 == 2 && i2 <= 2) {
                    this.setBlockAndMetadata(world, i1, 5, k1, this.thatchSlabBlock, this.thatchSlabMeta | 8);
                }
                if(i2 + k2 == 2) {
                    this.setBlockAndMetadata(world, i1, 6, k1, this.thatchSlabBlock, this.thatchSlabMeta);
                }
                if(i2 + k2 == 1) {
                    this.setBlockAndMetadata(world, i1, 6, k1, this.thatchBlock, this.thatchMeta);
                }
                if(i2 <= 2 && k2 <= 2 && i2 + k2 >= 3) {
                    this.setBlockAndMetadata(world, i1, 5, k1, this.thatchBlock, this.thatchMeta);
                }
                if(i2 == 1 && k2 == 1) {
                    this.setBlockAndMetadata(world, i1, 5, k1, this.thatchSlabBlock, this.thatchSlabMeta | 8);
                }
                if(i2 != 2 || k2 != 2) continue;
                this.setBlockAndMetadata(world, i1, 4, k1, this.fenceBlock, this.fenceMeta);
            }
        }
        this.setAir(world, 0, 1, -3);
        this.setAir(world, 0, 2, -3);
        this.setBlockAndMetadata(world, 0, 3, -3, this.plankSlabBlock, this.plankSlabMeta | 8);
        this.layFoundation(world, -2, -4);
        this.layFoundation(world, 2, -4);
        this.setBlockAndMetadata(world, -2, 1, -4, this.fenceBlock, this.fenceMeta);
        this.setBlockAndMetadata(world, 2, 1, -4, this.fenceBlock, this.fenceMeta);
        this.setBlockAndMetadata(world, -2, 1, -2, LOTRMod.chestBasket, 4);
        this.setBlockAndMetadata(world, -2, 1, -1, this.stainedClayBlock, this.stainedClayMeta);
        this.setBlockAndMetadata(world, -2, 1, 0, this.stainedClayBlock, this.stainedClayMeta);
        this.setBlockAndMetadata(world, -2, 1, 1, LOTRMod.chestBasket, 4);
        this.setBlockAndMetadata(world, 2, 1, -2, LOTRMod.chestBasket, 5);
        this.setBlockAndMetadata(world, 2, 1, -1, this.stainedClayBlock, this.stainedClayMeta);
        this.setBlockAndMetadata(world, 2, 1, 0, this.stainedClayBlock, this.stainedClayMeta);
        this.setBlockAndMetadata(world, 2, 1, 1, LOTRMod.chestBasket, 5);
        for(int f : new int[] {-1, 1}) {
            this.setBlockAndMetadata(world, 2 * f, 1, 2, this.plankBlock, this.plankMeta);
            this.setBlockAndMetadata(world, 1 * f, 1, 2, this.plankSlabBlock, this.plankSlabMeta | 8);
            this.setBlockAndMetadata(world, 0 * f, 1, 2, LOTRMod.moredainTable, 0);
            this.setBlockAndMetadata(world, 2 * f, 2, 2, this.plankBlock, this.plankMeta);
            this.setBlockAndMetadata(world, 1 * f, 2, 2, LOTRMod.chestBasket, 2);
            this.setBlockAndMetadata(world, 0 * f, 2, 2, this.fenceBlock, this.fenceMeta);
            this.setBlockAndMetadata(world, 2 * f, 3, 2, this.plankBlock, this.plankMeta);
            this.setBlockAndMetadata(world, 1 * f, 3, 2, this.plankSlabBlock, this.plankSlabMeta);
            this.setBlockAndMetadata(world, 0 * f, 3, 2, this.plankSlabBlock, this.plankSlabMeta);
        }
        this.setBlockAndMetadata(world, -2, 2, -4, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 2, 2, -4, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 0, 4, 2, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 0, 4, -2, Blocks.torch, 3);
        LOTREntityMoredainVillageTrader trader = random.nextBoolean() ? new LOTREntityMoredainHuntsman(world) : new LOTREntityMoredainHutmaker(world);
        this.spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
        return true;
    }
}
