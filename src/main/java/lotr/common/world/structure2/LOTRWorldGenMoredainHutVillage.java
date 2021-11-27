package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityMoredain;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenMoredainHutVillage extends LOTRWorldGenMoredainHut {
    private Block bedBlock;

    public LOTRWorldGenMoredainHutVillage(boolean flag) {
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
        this.bedBlock = random.nextBoolean() ? LOTRMod.strawBed : LOTRMod.lionBed;
        for(i1 = -3; i1 <= 3; ++i1) {
            for(k1 = -3; k1 <= 3; ++k1) {
                int j1;
                this.layFoundation(world, i1, k1);
                for(int j12 = 1; j12 <= 6; ++j12) {
                    this.setAir(world, i1, j12, k1);
                }
                i2 = Math.abs(i1);
                k2 = Math.abs(k1);
                if(i2 <= 2 && k2 <= 2) {
                    this.setBlockAndMetadata(world, i1, 0, k1, LOTRMod.redClay, 0);
                    if(random.nextBoolean()) {
                        this.setBlockAndMetadata(world, i1, 1, k1, LOTRMod.thatchFloor, 0);
                    }
                }
                if(i2 == 3 || k2 == 3) {
                    this.setBlockAndMetadata(world, i1, 1, k1, this.clayBlock, this.clayMeta);
                }
                if(i2 == 3 && k2 <= 2 || k2 == 3 && i2 <= 2) {
                    for(j1 = 2; j1 <= 4; ++j1) {
                        this.setBlockAndMetadata(world, i1, j1, k1, this.brickBlock, this.brickMeta);
                    }
                }
                if(i2 == 3 && k2 == 3) {
                    for(j1 = 2; j1 <= 3; ++j1) {
                        this.setBlockAndMetadata(world, i1, j1, k1, this.fenceBlock, this.fenceMeta);
                    }
                }
                if(i2 != 2 || k2 != 2) continue;
                for(j1 = 1; j1 <= 4; ++j1) {
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
                    this.setBlockAndMetadata(world, i1, 5, k1, this.thatchSlabBlock, this.thatchSlabMeta);
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
                if(i2 != 1 || k2 != 1) continue;
                this.setBlockAndMetadata(world, i1, 5, k1, this.thatchSlabBlock, this.thatchSlabMeta | 8);
            }
        }
        this.setAir(world, 0, 1, -3);
        this.setAir(world, 0, 2, -3);
        this.setBlockAndMetadata(world, 0, 3, -3, this.plankSlabBlock, this.plankSlabMeta | 8);
        this.setBlockAndMetadata(world, -1, 4, -4, this.thatchBlock, this.thatchMeta);
        this.setBlockAndMetadata(world, 1, 4, -4, this.thatchBlock, this.thatchMeta);
        this.dropFence(world, -1, 3, -4);
        this.dropFence(world, 1, 3, -4);
        this.setBlockAndMetadata(world, -3, 2, 0, this.plankSlabBlock, this.plankSlabMeta);
        this.setBlockAndMetadata(world, -3, 3, 0, this.plankSlabBlock, this.plankSlabMeta | 8);
        this.setBlockAndMetadata(world, 0, 2, 3, this.plankSlabBlock, this.plankSlabMeta);
        this.setBlockAndMetadata(world, 0, 3, 3, this.plankSlabBlock, this.plankSlabMeta | 8);
        this.setBlockAndMetadata(world, 3, 2, 0, this.plankSlabBlock, this.plankSlabMeta);
        this.setBlockAndMetadata(world, 3, 3, 0, this.plankSlabBlock, this.plankSlabMeta | 8);
        this.setBlockAndMetadata(world, -2, 1, 2, this.plankBlock, this.plankMeta);
        this.setBlockAndMetadata(world, -1, 1, 2, this.plankBlock, this.plankMeta);
        this.setBlockAndMetadata(world, 2, 1, 2, Blocks.crafting_table, 0);
        this.setBlockAndMetadata(world, 2, 1, 1, this.plankBlock, this.plankMeta);
        this.placeChest(world, random, 2, 1, 0, LOTRMod.chestBasket, 5, LOTRChestContents.MOREDAIN_HUT);
        this.setBlockAndMetadata(world, 2, 1, -1, this.plankBlock, this.plankMeta);
        this.setBlockAndMetadata(world, 2, 1, -2, LOTRMod.moredainTable, 0);
        this.setBlockAndMetadata(world, -1, 1, -1, this.bedBlock, 3);
        this.setBlockAndMetadata(world, -2, 1, -1, this.bedBlock, 11);
        this.setBlockAndMetadata(world, -1, 1, 1, this.bedBlock, 3);
        this.setBlockAndMetadata(world, -2, 1, 1, this.bedBlock, 11);
        this.setBlockAndMetadata(world, -2, 2, -4, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 2, 2, -4, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 0, 4, 2, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 0, 4, -2, Blocks.torch, 3);
        LOTREntityMoredain moredain = new LOTREntityMoredain(world);
        this.spawnNPCAndSetHome(moredain, world, 0, 1, 0, 8);
        return true;
    }
}
