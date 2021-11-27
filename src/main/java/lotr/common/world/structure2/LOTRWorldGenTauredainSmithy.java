package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.*;
import lotr.common.entity.npc.LOTREntityTauredainSmith;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRWorldGenTauredainSmithy extends LOTRWorldGenTauredainHouse {
    public LOTRWorldGenTauredainSmithy(boolean flag) {
        super(flag);
    }

    @Override
    protected int getOffset() {
        return 6;
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int j1;
        this.setOriginAndRotation(world, i, j, k, rotation, 6);
        this.setupRandomBlocks(random);
        if(this.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for(int i1 = -5; i1 <= 5; ++i1) {
                for(int k1 = -5; k1 <= 7; ++k1) {
                    j1 = this.getTopBlock(world, i1, k1) - 1;
                    if(!this.isSurface(world, i1, j1, k1)) {
                        return false;
                    }
                    if(j1 < minHeight) {
                        minHeight = j1;
                    }
                    if(j1 > maxHeight) {
                        maxHeight = j1;
                    }
                    if(maxHeight - minHeight <= 8) continue;
                    return false;
                }
            }
        }
        for(int i1 = -5; i1 <= 5; ++i1) {
            for(int k1 = -5; k1 <= 7; ++k1) {
                int i2 = Math.abs(i1);
                int k2 = Math.abs(k1);
                if(i2 <= 4 && k1 >= -2 && k1 <= 5) {
                    for(j1 = -4; j1 <= 0; ++j1) {
                        this.setAir(world, i1, j1, k1);
                    }
                }
                if(i2 <= 2 && k1 == 6) {
                    for(j1 = -4; j1 <= -1; ++j1) {
                        this.setAir(world, i1, j1, k1);
                    }
                }
                if(i2 <= 5 && k2 <= 5) {
                    for(j1 = 1; j1 <= 8; ++j1) {
                        this.setAir(world, i1, j1, k1);
                    }
                }
                if(i2 > 3 || k1 < 1 || k1 > 7) continue;
                for(j1 = 1; j1 <= 8; ++j1) {
                    this.setAir(world, i1, j1, k1);
                }
            }
        }
        this.loadStrScan("taurethrim_smithy");
        this.associateBlockMetaAlias("STONEBRICK", LOTRMod.brick4, 0);
        this.associateBlockAlias("STONEBRICK_STAIR", LOTRMod.stairsTauredainBrick);
        this.associateBlockMetaAlias("STONEBRICK_WALL", LOTRMod.wall4, 0);
        this.associateBlockMetaAlias("OBBRICK", LOTRMod.brick4, 4);
        this.associateBlockMetaAlias("OBBRICK_SLAB", LOTRMod.slabSingle8, 4);
        this.associateBlockAlias("OBBRICK_STAIR", LOTRMod.stairsTauredainBrickObsidian);
        this.associateBlockMetaAlias("BRICK", this.brickBlock, this.brickMeta);
        this.associateBlockAlias("BRICK_STAIR", this.brickStairBlock);
        this.associateBlockMetaAlias("BRICK_WALL", this.brickWallBlock, this.brickWallMeta);
        this.associateBlockMetaAlias("WOOD", this.woodBlock, this.woodMeta);
        this.associateBlockMetaAlias("WOOD|4", this.woodBlock, this.woodMeta | 4);
        this.associateBlockMetaAlias("WOOD|8", this.woodBlock, this.woodMeta | 8);
        this.associateBlockMetaAlias("PLANK", this.plankBlock, this.plankMeta);
        this.associateBlockMetaAlias("FENCE", this.fenceBlock, this.fenceMeta);
        this.associateBlockAlias("FENCE_GATE", this.fenceGateBlock);
        this.addBlockMetaAliasOption("FLOOR", 10, Blocks.stained_hardened_clay, 7);
        this.addBlockMetaAliasOption("FLOOR", 10, LOTRMod.mud, 0);
        this.associateBlockMetaAlias("WALL", Blocks.stained_hardened_clay, 12);
        this.associateBlockMetaAlias("ROOF", this.thatchBlock, this.thatchMeta);
        this.associateBlockMetaAlias("ROOF_SLAB", this.thatchSlabBlock, this.thatchSlabMeta);
        this.associateBlockAlias("ROOF_STAIR", this.thatchStairBlock);
        this.generateStrScan(world, random, 0, 0, 0);
        this.setBlockAndMetadata(world, 0, 5, 5, this.bedBlock, 1);
        this.setBlockAndMetadata(world, 1, 5, 5, this.bedBlock, 9);
        this.placeChest(world, random, 2, 5, 4, LOTRMod.chestBasket, 5, LOTRChestContents.TAUREDAIN_HOUSE);
        this.placeTauredainFlowerPot(world, 2, 6, 5, random);
        this.placePlateWithCertainty(world, random, 2, 6, 3, LOTRMod.woodPlateBlock, LOTRFoods.TAUREDAIN);
        this.placeTauredainTorch(world, -4, 2, -4);
        this.placeTauredainTorch(world, 4, 2, -4);
        this.placeWeaponRack(world, -3, -2, 2, 5, this.getRandomTaurethrimWeapon(random));
        this.placeArmorStand(world, 3, -3, 2, 1, new ItemStack[] {null, new ItemStack(LOTRMod.bodyTauredain), null, null});
        LOTREntityTauredainSmith smith = new LOTREntityTauredainSmith(world);
        this.spawnNPCAndSetHome(smith, world, 0, -3, 3, 12);
        return true;
    }

    protected ItemStack getRandomTaurethrimWeapon(Random random) {
        ItemStack[] items = new ItemStack[] {new ItemStack(LOTRMod.swordTauredain), new ItemStack(LOTRMod.daggerTauredain), new ItemStack(LOTRMod.spearTauredain), new ItemStack(LOTRMod.pikeTauredain), new ItemStack(LOTRMod.hammerTauredain), new ItemStack(LOTRMod.battleaxeTauredain)};
        return items[random.nextInt(items.length)].copy();
    }
}
