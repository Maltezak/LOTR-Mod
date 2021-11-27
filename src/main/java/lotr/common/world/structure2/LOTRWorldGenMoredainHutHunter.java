package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityMoredainWarrior;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenMoredainHutHunter extends LOTRWorldGenMoredainHut {
    public LOTRWorldGenMoredainHutHunter(boolean flag) {
        super(flag);
    }

    @Override
    protected int getOffset() {
        return 3;
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
        for(i1 = -2; i1 <= 2; ++i1) {
            for(k1 = -2; k1 <= 2; ++k1) {
                int j1;
                this.layFoundation(world, i1, k1);
                for(int j12 = 1; j12 <= 5; ++j12) {
                    this.setAir(world, i1, j12, k1);
                }
                i2 = Math.abs(i1);
                k2 = Math.abs(k1);
                if(i2 <= 1 && k2 <= 1) {
                    this.setBlockAndMetadata(world, i1, 0, k1, LOTRMod.redClay, 0);
                    if(random.nextBoolean()) {
                        this.setBlockAndMetadata(world, i1, 1, k1, LOTRMod.thatchFloor, 0);
                    }
                }
                if(i2 == 2 && k2 <= 1 || k2 == 2 && i2 <= 1) {
                    this.setBlockAndMetadata(world, i1, 1, k1, this.clayBlock, this.clayMeta);
                    for(j1 = 2; j1 <= 3; ++j1) {
                        this.setBlockAndMetadata(world, i1, j1, k1, this.plankBlock, this.plankMeta);
                    }
                }
                if(i2 == 2 && k2 == 2) {
                    for(j1 = 1; j1 <= 2; ++j1) {
                        this.setBlockAndMetadata(world, i1, j1, k1, this.fenceBlock, this.fenceMeta);
                    }
                }
                if(i2 != 1 || k2 != 1) continue;
                this.setBlockAndMetadata(world, i1, 3, k1, this.fenceBlock, this.fenceMeta);
            }
        }
        for(i1 = -2; i1 <= 2; ++i1) {
            for(k1 = -2; k1 <= 2; ++k1) {
                i2 = Math.abs(i1);
                k2 = Math.abs(k1);
                if(i2 == 2 && k2 == 2) {
                    this.setBlockAndMetadata(world, i1, 3, k1, this.thatchSlabBlock, this.thatchSlabMeta | 8);
                }
                if(i2 == 2 && k2 == 0 || k2 == 2 && i2 == 0) {
                    this.setBlockAndMetadata(world, i1, 4, k1, this.thatchBlock, this.thatchMeta);
                }
                if(i2 + k2 == 3) {
                    this.setBlockAndMetadata(world, i1, 4, k1, this.thatchSlabBlock, this.thatchSlabMeta);
                }
                if(i2 == 1 && k2 == 1) {
                    this.setBlockAndMetadata(world, i1, 4, k1, this.thatchSlabBlock, this.thatchSlabMeta | 8);
                }
                if(i2 + k2 != 1) continue;
                this.setBlockAndMetadata(world, i1, 5, k1, this.thatchSlabBlock, this.thatchSlabMeta);
            }
        }
        this.setAir(world, 0, 1, -2);
        this.setAir(world, 0, 2, -2);
        this.dropFence(world, -1, 2, -3);
        this.dropFence(world, 1, 2, -3);
        this.setBlockAndMetadata(world, -1, 3, -3, this.thatchSlabBlock, this.thatchSlabMeta);
        this.setBlockAndMetadata(world, 0, 3, -3, this.thatchSlabBlock, this.thatchSlabMeta | 8);
        this.setBlockAndMetadata(world, 1, 3, -3, this.thatchSlabBlock, this.thatchSlabMeta);
        this.setBlockAndMetadata(world, -1, 1, 0, LOTRMod.strawBed, 0);
        this.setBlockAndMetadata(world, -1, 1, 1, LOTRMod.strawBed, 8);
        this.placeChest(world, random, 1, 1, 1, LOTRMod.chestBasket, 2, LOTRChestContents.MOREDAIN_HUT);
        this.setBlockAndMetadata(world, 0, 3, 1, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 0, 3, -1, Blocks.torch, 3);
        LOTREntityMoredainWarrior moredain = new LOTREntityMoredainWarrior(world);
        moredain.spawnRidingHorse = false;
        this.spawnNPCAndSetHome(moredain, world, 0, 1, 0, 8);
        return true;
    }
}
