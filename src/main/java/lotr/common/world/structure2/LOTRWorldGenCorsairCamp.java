package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.*;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTRWorldGenCorsairCamp extends LOTRWorldGenCampBase {
    public LOTRWorldGenCorsairCamp(boolean flag) {
        super(flag);
    }

    @Override
    protected LOTRWorldGenStructureBase2 createTent(boolean flag, Random random) {
        return new LOTRWorldGenCorsairTent(false);
    }

    @Override
    protected LOTREntityNPC getCampCaptain(World world, Random random) {
        return null;
    }

    @Override
    protected void placeNPCRespawner(World world, Random random, int i, int j, int k) {
        LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
        respawner.setSpawnClass(LOTREntityCorsair.class);
        respawner.setCheckRanges(24, -12, 12, 10);
        respawner.setSpawnRanges(8, -4, 4, 16);
        this.placeNPCRespawner(respawner, world, i, j, k);
        int corsairs = 3 + random.nextInt(5);
        for(int l = 0; l < corsairs; ++l) {
            LOTREntityCorsair corsair = new LOTREntityCorsair(world);
            if(l == 0) {
                corsair = random.nextBoolean() ? new LOTREntityCorsairCaptain(world) : new LOTREntityCorsairSlaver(world);
            }
            int r = 4;
            float ang = random.nextFloat() * 3.1415927f * 2.0f;
            int i1 = (int) (r * MathHelper.cos(ang));
            int k1 = (int) (r * MathHelper.sin(ang));
            int j1 = this.getTopBlock(world, i1, k1);
            this.spawnNPCAndSetHome(corsair, world, i1, j1, k1, 16);
        }
    }

    @Override
    protected boolean generateFarm() {
        return false;
    }

    @Override
    protected void generateCentrepiece(World world, Random random, int i, int j, int k) {
        this.loadStrScan("corsair_camp_centre");
        this.generateStrScan(world, random, 0, 0, 0);
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        if(super.generateWithSetRotation(world, random, i, j, k, rotation)) {
            int i1;
            int k1;
            float ang;
            int r;
            for(int att = 0; att < 16 && !this.generateSubstructureWithRestrictionFlag(new LOTRWorldGenCorsairCampCage(this.notifyChanges), world, random, i1 = (int) ((r = MathHelper.getRandomIntegerInRange(random, 8, 20)) * MathHelper.cos(ang = random.nextFloat() * 3.1415927f * 2.0f)), this.getTopBlock(world, i1, k1 = (int) (r * MathHelper.sin(ang))), k1, random.nextInt(4), true); ++att) {
            }
            int chestPiles = 1 + random.nextInt(2);
            block1: for(int l = 0; l < chestPiles; ++l) {
                for(int att = 0; att < 16; ++att) {
                    int j12;
                    float ang2;
                    int k12;
                    int r2 = MathHelper.getRandomIntegerInRange(random, 8, 20);
                    int i12 = (int) (r2 * MathHelper.cos(ang2 = random.nextFloat() * 3.1415927f * 2.0f));
                    if(!this.isOpaque(world, i12, (j12 = this.getTopBlock(world, i12, k12 = (int) (r2 * MathHelper.sin(ang2)))) - 1, k12) || !this.isAir(world, i12, j12, k12) || !this.isAir(world, i12, j12 + 1, k12)) continue;
                    this.setBlockAndMetadata(world, i12, j12, k12, LOTRMod.wood8, 3);
                    this.setGrassToDirt(world, i12, j12 - 1, k12);
                    this.placeChest(world, random, i12, j12 + 1, k12, LOTRMod.chestBasket, 2, LOTRChestContents.CORSAIR, 3 + random.nextInt(3));
                    this.tryPlaceSideChest(world, random, i12 - 1, j12, k12, 5);
                    this.tryPlaceSideChest(world, random, i12 + 1, j12, k12, 4);
                    this.tryPlaceSideChest(world, random, i12, j12, k12 - 1, 2);
                    this.tryPlaceSideChest(world, random, i12, j12, k12 + 1, 3);
                    continue block1;
                }
            }
            return true;
        }
        return false;
    }

    private void tryPlaceSideChest(World world, Random random, int i, int j, int k, int meta) {
        if(this.isOpaque(world, i, j - 1, k) && this.isAir(world, i, j, k)) {
            if(random.nextBoolean()) {
                this.setBlockAndMetadata(world, i, j, k, LOTRMod.chestBasket, meta);
            }
            else {
                this.placeChest(world, random, i, j, k, LOTRMod.chestBasket, meta, LOTRChestContents.CORSAIR, 1);
            }
        }
    }
}
