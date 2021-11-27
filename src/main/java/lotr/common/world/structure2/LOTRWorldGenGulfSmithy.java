package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.*;
import lotr.common.entity.npc.LOTREntityGulfBlacksmith;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRWorldGenGulfSmithy extends LOTRWorldGenGulfStructure {
    public LOTRWorldGenGulfSmithy(boolean flag) {
        super(flag);
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int k1;
        int i1;
        int j1;
        this.setOriginAndRotation(world, i, j, k, rotation, 6);
        this.setupRandomBlocks(random);
        if(this.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for(int i12 = -14; i12 <= 6; ++i12) {
                for(int k12 = -6; k12 <= 6; ++k12) {
                    j1 = this.getTopBlock(world, i12, k12) - 1;
                    if(!this.isSurface(world, i12, j1, k12)) {
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
        for(int i13 = -14; i13 <= 6; ++i13) {
            for(int k13 = -6; k13 <= 6; ++k13) {
                int k2;
                int i2 = Math.abs(i13);
                if(i2 * i2 + (k2 = Math.abs(k13)) * k2 >= 25 && (i13 > -7 || k2 > 5)) continue;
                for(j1 = 1; j1 <= 5; ++j1) {
                    this.setAir(world, i13, j1, k13);
                }
            }
        }
        this.loadStrScan("gulf_smithy");
        this.associateBlockMetaAlias("BRICK", this.brickBlock, this.brickMeta);
        this.associateBlockAlias("BRICK_STAIR", this.brickStairBlock);
        this.associateBlockMetaAlias("BRICK_WALL", this.brickWallBlock, this.brickWallMeta);
        this.associateBlockMetaAlias("WOOD", this.woodBlock, this.woodMeta);
        this.associateBlockMetaAlias("PLANK", this.plankBlock, this.plankMeta);
        this.associateBlockAlias("PLANK_STAIR", this.plankStairBlock);
        this.associateBlockMetaAlias("FENCE", this.fenceBlock, this.fenceMeta);
        this.associateBlockAlias("FENCE_GATE", this.fenceGateBlock);
        this.associateBlockAlias("DOOR", this.doorBlock);
        this.associateBlockMetaAlias("BEAM", this.beamBlock, this.beamMeta);
        this.associateBlockMetaAlias("PLANK2", this.plank2Block, this.plank2Meta);
        this.associateBlockMetaAlias("PLANK2_SLAB", this.plank2SlabBlock, this.plank2SlabMeta);
        this.associateBlockMetaAlias("PLANK2_SLAB_INV", this.plank2SlabBlock, this.plank2SlabMeta | 8);
        this.associateBlockAlias("PLANK2_STAIR", this.plank2StairBlock);
        this.associateBlockMetaAlias("ROOF", this.roofBlock, this.roofMeta);
        this.associateBlockMetaAlias("ROOF_SLAB", this.roofSlabBlock, this.roofSlabMeta);
        this.associateBlockMetaAlias("ROOF_SLAB_INV", this.roofSlabBlock, this.roofSlabMeta | 8);
        this.associateBlockAlias("ROOF_STAIR", this.roofStairBlock);
        this.associateBlockMetaAlias("FLAG", this.flagBlock, this.flagMeta);
        this.associateBlockMetaAlias("BONE", this.boneBlock, this.boneMeta);
        this.generateStrScan(world, random, 0, 0, 0);
        this.setBlockAndMetadata(world, 0, 1, 3, this.bedBlock, 0);
        this.setBlockAndMetadata(world, 0, 1, 4, this.bedBlock, 8);
        this.placeChest(world, random, -4, 1, -2, LOTRMod.chestBasket, 3, LOTRChestContents.GULF_HOUSE);
        this.placeFlowerPot(world, 2, 2, -4, this.getRandomFlower(world, random));
        this.placeFlowerPot(world, -2, 2, 4, this.getRandomFlower(world, random));
        this.placeFlowerPot(world, -4, 1, 1, new ItemStack(Blocks.cactus));
        this.placeMug(world, random, 4, 2, -1, 1, LOTRFoods.GULF_HARAD_DRINK);
        this.placeMug(world, random, 2, 2, 4, 0, LOTRFoods.GULF_HARAD_DRINK);
        this.placePlate(world, random, 4, 2, 0, LOTRMod.woodPlateBlock, LOTRFoods.GULF_HARAD);
        this.placePlate(world, random, 4, 2, 1, LOTRMod.woodPlateBlock, LOTRFoods.GULF_HARAD);
        if(random.nextBoolean()) {
            this.placeArmorStand(world, -7, 1, -2, 1, new ItemStack[] {new ItemStack(LOTRMod.helmetGulfHarad), new ItemStack(LOTRMod.bodyGulfHarad), new ItemStack(LOTRMod.legsGulfHarad), new ItemStack(LOTRMod.bootsGulfHarad)});
        }
        else {
            this.placeArmorStand(world, -7, 1, -2, 1, new ItemStack[] {null, new ItemStack(LOTRMod.bodyGulfHarad), null, null});
        }
        this.placeWeaponRack(world, -13, 3, 0, 5, this.getRandomGulfWeapon(random));
        LOTREntityGulfBlacksmith smith = new LOTREntityGulfBlacksmith(world);
        this.spawnNPCAndSetHome(smith, world, -6, 1, 0, 8);
        int maxSteps = 12;
        for(int step = 0; step < maxSteps && !this.isOpaque(world, i1 = -9, j1 = 0 - step, k1 = -5 - step); ++step) {
            this.setBlockAndMetadata(world, i1, j1, k1, LOTRMod.stairsRedSandstone, 2);
            this.setGrassToDirt(world, i1, j1 - 1, k1);
            int j2 = j1 - 1;
            while(!this.isOpaque(world, i1, j2, k1) && this.getY(j2) >= 0) {
                this.setBlockAndMetadata(world, i1, j2, k1, LOTRMod.redSandstone, 0);
                this.setGrassToDirt(world, i1, j2 - 1, k1);
                --j2;
            }
        }
        return true;
    }
}
