package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.*;
import lotr.common.entity.npc.LOTREntityHarnedorBlacksmith;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRWorldGenHarnedorSmithy extends LOTRWorldGenHarnedorStructure {
    public LOTRWorldGenHarnedorSmithy(boolean flag) {
        super(flag);
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int j1;
        this.setOriginAndRotation(world, i, j, k, rotation, 5);
        this.setupRandomBlocks(random);
        if(this.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for(int i1 = -12; i1 <= 8; ++i1) {
                for(int k1 = -6; k1 <= 6; ++k1) {
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
        for(int i1 = -10; i1 <= 6; ++i1) {
            for(int k1 = -6; k1 <= 6; ++k1) {
                Math.abs(i1);
                int k2 = Math.abs(k1);
                if((i1 < -8 || i1 > 4 || k2 != 4) && (i1 < -10 || i1 > 6 || k2 > 3)) continue;
                j1 = -1;
                while(!this.isOpaque(world, i1, j1, k1) && this.getY(j1) >= 0) {
                    this.setBlockAndMetadata(world, i1, j1, k1, this.plank2Block, this.plank2Meta);
                    this.setGrassToDirt(world, i1, j1 - 1, k1);
                    --j1;
                }
                for(j1 = 1; j1 <= 8; ++j1) {
                    this.setAir(world, i1, j1, k1);
                }
            }
        }
        this.loadStrScan("harnedor_smithy");
        this.associateBlockMetaAlias("WOOD", this.woodBlock, this.woodMeta);
        this.associateBlockMetaAlias("PLANK", this.plankBlock, this.plankMeta);
        this.associateBlockMetaAlias("PLANK_SLAB", this.plankSlabBlock, this.plankSlabMeta);
        this.associateBlockMetaAlias("PLANK_SLAB_INV", this.plankSlabBlock, this.plankSlabMeta | 8);
        this.associateBlockAlias("PLANK_STAIR", this.plankStairBlock);
        this.associateBlockMetaAlias("FENCE", this.fenceBlock, this.fenceMeta);
        this.associateBlockAlias("FENCE_GATE", this.fenceGateBlock);
        this.associateBlockAlias("DOOR", this.doorBlock);
        this.associateBlockMetaAlias("PLANK2", this.plank2Block, this.plank2Meta);
        this.associateBlockMetaAlias("ROOF", this.roofBlock, this.roofMeta);
        this.generateStrScan(world, random, 0, 1, 0);
        this.placeWeaponRack(world, -1, 2, -1, 5, this.getRandomHarnedorWeapon(random));
        this.placeWeaponRack(world, -1, 2, 1, 5, this.getRandomHarnedorWeapon(random));
        this.placeArmorStand(world, 3, 1, 3, 2, null);
        if(random.nextBoolean()) {
            this.placeArmorStand(world, 0, 1, 3, 0, new ItemStack[] {new ItemStack(LOTRMod.helmetHarnedor), new ItemStack(LOTRMod.bodyHarnedor), new ItemStack(LOTRMod.legsHarnedor), new ItemStack(LOTRMod.bootsHarnedor)});
        }
        else {
            this.placeArmorStand(world, 0, 1, 3, 0, new ItemStack[] {null, new ItemStack(LOTRMod.bodyHarnedor), null, null});
        }
        this.placeChest(world, random, 5, 1, -2, LOTRMod.chestBasket, 5, LOTRChestContents.HARNENNOR_HOUSE);
        this.placeChest(world, random, -7, 1, 3, LOTRMod.chestBasket, 2, LOTRChestContents.HARNENNOR_HOUSE);
        this.placeBarrel(world, random, -3, 2, -1, 5, LOTRFoods.HARNEDOR_DRINK);
        this.placeMug(world, random, -3, 2, 0, 2, LOTRFoods.HARNEDOR_DRINK);
        this.placeMug(world, random, -9, 2, -2, 3, LOTRFoods.HARNEDOR_DRINK);
        this.placePlate(world, random, -5, 2, 3, LOTRMod.ceramicPlateBlock, LOTRFoods.HARNEDOR);
        this.placePlate(world, random, -3, 2, 3, LOTRMod.ceramicPlateBlock, LOTRFoods.HARNEDOR);
        this.placeFlowerPot(world, -4, 2, 3, this.getRandomFlower(world, random));
        this.setBlockAndMetadata(world, -8, 1, 1, this.bedBlock, 3);
        this.setBlockAndMetadata(world, -9, 1, 1, this.bedBlock, 11);
        LOTREntityHarnedorBlacksmith smith = new LOTREntityHarnedorBlacksmith(world);
        this.spawnNPCAndSetHome(smith, world, 0, 1, 0, 8);
        return true;
    }
}
