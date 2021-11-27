package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.*;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.animal.*;
import lotr.common.entity.item.LOTREntityBearRug;
import lotr.common.entity.npc.*;
import lotr.common.item.LOTRItemBanner;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRWorldGenDunlandHillFort extends LOTRWorldGenDunlandStructure {
    public LOTRWorldGenDunlandHillFort(boolean flag) {
        super(flag);
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int i1;
        int j1;
        this.setOriginAndRotation(world, i, j, k, rotation, 10);
        this.setupRandomBlocks(random);
        if(this.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for(int i12 = -12; i12 <= 12; ++i12) {
                for(int k1 = -12; k1 <= 12; ++k1) {
                    j1 = this.getTopBlock(world, i12, k1) - 1;
                    if(!this.isSurface(world, i12, j1, k1)) {
                        return false;
                    }
                    if(j1 < minHeight) {
                        minHeight = j1;
                    }
                    if(j1 > maxHeight) {
                        maxHeight = j1;
                    }
                    if(maxHeight - minHeight <= 12) continue;
                    return false;
                }
            }
        }
        for(i1 = -11; i1 <= 11; ++i1) {
            for(int k1 = -11; k1 <= 11; ++k1) {
                int i2 = Math.abs(i1);
                int k2 = Math.abs(k1);
                for(j1 = 1; j1 <= 8; ++j1) {
                    this.setAir(world, i1, j1, k1);
                }
                if(i2 <= 8 && k2 <= 8 || i2 <= 1 && k1 < 0) {
                    int randomGround = random.nextInt(3);
                    if(randomGround == 0) {
                        this.setBlockAndMetadata(world, i1, 0, k1, Blocks.grass, 0);
                    }
                    else if(randomGround == 1) {
                        this.setBlockAndMetadata(world, i1, 0, k1, Blocks.dirt, 1);
                    }
                    else if(randomGround == 2) {
                        this.setBlockAndMetadata(world, i1, 0, k1, LOTRMod.dirtPath, 0);
                    }
                    if((i2 > 3 || k1 < -3 || k1 > 2) && random.nextInt(5) == 0) {
                        this.setBlockAndMetadata(world, i1, 1, k1, LOTRMod.thatchFloor, 0);
                    }
                }
                else {
                    this.setBlockAndMetadata(world, i1, 0, k1, Blocks.cobblestone, 0);
                }
                this.setGrassToDirt(world, i1, -1, k1);
                j1 = -1;
                while(!this.isOpaque(world, i1, j1, k1) && this.getY(j1) >= 0) {
                    this.setBlockAndMetadata(world, i1, j1, k1, Blocks.cobblestone, 0);
                    this.setGrassToDirt(world, i1, j1 - 1, k1);
                    --j1;
                }
            }
        }
        this.loadStrScan("dunland_fort");
        this.associateBlockMetaAlias("FLOOR", this.floorBlock, this.floorMeta);
        this.associateBlockMetaAlias("WOOD", this.woodBlock, this.woodMeta);
        this.associateBlockMetaAlias("WOOD|8", this.woodBlock, this.woodMeta | 8);
        this.associateBlockMetaAlias("PLANK", this.plankBlock, this.plankMeta);
        this.associateBlockMetaAlias("PLANK_SLAB", this.plankSlabBlock, this.plankSlabMeta);
        this.associateBlockMetaAlias("PLANK_SLAB_INV", this.plankSlabBlock, this.plankSlabMeta | 8);
        this.associateBlockAlias("PLANK_STAIR", this.plankStairBlock);
        this.associateBlockMetaAlias("FENCE", this.fenceBlock, this.fenceMeta);
        this.associateBlockAlias("DOOR", this.doorBlock);
        this.associateBlockMetaAlias("ROOF", this.roofBlock, this.roofMeta);
        this.associateBlockMetaAlias("ROOF_SLAB", this.roofSlabBlock, this.roofSlabMeta);
        this.associateBlockMetaAlias("ROOF_SLAB_INV", this.roofSlabBlock, this.roofSlabMeta | 8);
        this.associateBlockAlias("ROOF_STAIR", this.roofStairBlock);
        this.associateBlockMetaAlias("BARS", this.barsBlock, this.barsMeta);
        this.generateStrScan(world, random, 0, 1, 0);
        this.setBlockAndMetadata(world, 8, 1, 5, this.bedBlock, 9);
        this.setBlockAndMetadata(world, 7, 1, 5, this.bedBlock, 1);
        this.setBlockAndMetadata(world, 7, 1, 7, this.bedBlock, 0);
        this.setBlockAndMetadata(world, 7, 1, 8, this.bedBlock, 8);
        this.setBlockAndMetadata(world, 5, 1, 7, this.bedBlock, 0);
        this.setBlockAndMetadata(world, 5, 1, 8, this.bedBlock, 8);
        this.placeChest(world, random, 5, 1, 5, LOTRMod.chestBasket, 3, LOTRChestContents.DUNLENDING_HOUSE);
        this.placeChest(world, random, -4, 1, 8, LOTRMod.chestBasket, 2, LOTRChestContents.DUNLENDING_HOUSE);
        this.placeChest(world, random, 6, 1, -8, Blocks.chest, 3, LOTRChestContents.DUNLENDING_HOUSE);
        this.placeChest(world, random, 5, 1, -8, Blocks.chest, 3, LOTRChestContents.DUNLENDING_HOUSE);
        for(i1 = -6; i1 <= -5; ++i1) {
            int j12 = 1;
            int k1 = 8;
            if(random.nextBoolean()) {
                this.placeArmorStand(world, i1, j12, k1, 0, new ItemStack[] {new ItemStack(LOTRMod.helmetDunlending), new ItemStack(LOTRMod.bodyDunlending), new ItemStack(LOTRMod.legsDunlending), new ItemStack(LOTRMod.bootsDunlending)});
                continue;
            }
            this.placeArmorStand(world, i1, j12, k1, 0, new ItemStack[] {new ItemStack(LOTRMod.helmetFur), new ItemStack(LOTRMod.bodyFur), new ItemStack(LOTRMod.legsFur), new ItemStack(LOTRMod.bootsFur)});
        }
        this.placeWeaponRack(world, -7, 2, -3, 5, this.getRandomDunlandWeapon(random));
        this.placeBarrel(world, random, 8, 2, 7, 2, LOTRFoods.DUNLENDING_DRINK);
        this.placeSkull(world, random, -2, 7, -11);
        this.placeSkull(world, random, 2, 7, -11);
        this.placeSkull(world, random, -11, 7, 2);
        this.placeSkull(world, random, 3, 7, 8);
        this.placeSkull(world, random, 11, 8, -3);
        this.placeAnimalJar(world, 8, 2, -6, LOTRMod.birdCageWood, 0, new LOTREntityCrebain(world));
        this.setBlockAndMetadata(world, 6, 1, -3, LOTRMod.commandTable, 0);
        this.placeWallBanner(world, -2, 5, -11, LOTRItemBanner.BannerType.DUNLAND, 2);
        this.placeWallBanner(world, 2, 5, -11, LOTRItemBanner.BannerType.DUNLAND, 2);
        this.placeWallBanner(world, -8, 4, 0, LOTRItemBanner.BannerType.DUNLAND, 1);
        this.placeWallBanner(world, 8, 4, 0, LOTRItemBanner.BannerType.DUNLAND, 3);
        LOTREntityBearRug rug = new LOTREntityBearRug(world);
        LOTREntityBear.BearType[] bearTypes = new LOTREntityBear.BearType[] {LOTREntityBear.BearType.LIGHT, LOTREntityBear.BearType.DARK, LOTREntityBear.BearType.BLACK};
        rug.setRugType(bearTypes[random.nextInt(bearTypes.length)]);
        this.placeRug(rug, world, -5, 1, -4, -45.0f);
        LOTREntityDunlendingWarlord warlord = new LOTREntityDunlendingWarlord(world);
        this.spawnNPCAndSetHome(warlord, world, 0, 1, 2, 8);
        int warriors = 6;
        for(int l = 0; l < warriors; ++l) {
            LOTREntityDunlendingWarrior warrior = random.nextInt(3) == 0 ? new LOTREntityDunlendingArcher(world) : new LOTREntityDunlendingWarrior(world);
            warrior.spawnRidingHorse = false;
            this.spawnNPCAndSetHome(warrior, world, 0, 1, 2, 16);
        }
        LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
        respawner.setSpawnClasses(LOTREntityDunlendingWarrior.class, LOTREntityDunlendingArcher.class);
        respawner.setCheckRanges(20, -8, 12, 12);
        respawner.setSpawnRanges(6, -1, 4, 16);
        this.placeNPCRespawner(respawner, world, 0, 0, 0);
        return true;
    }
}
