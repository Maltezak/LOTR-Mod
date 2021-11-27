package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.*;
import lotr.common.entity.animal.LOTREntityCamel;
import lotr.common.entity.npc.*;
import lotr.common.item.LOTRItemBanner;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenNomadChieftainTent extends LOTRWorldGenNomadStructure {
    public LOTRWorldGenNomadChieftainTent(boolean flag) {
        super(flag);
    }

    @Override
    protected void setupRandomBlocks(Random random) {
        super.setupRandomBlocks(random);
        this.bedBlock = LOTRMod.lionBed;
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int j1;
        this.setOriginAndRotation(world, i, j, k, rotation, 9);
        this.setupRandomBlocks(random);
        if(this.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for(int i1 = -12; i1 <= 12; ++i1) {
                for(int k1 = -8; k1 <= 8; ++k1) {
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
        for(int i1 = -12; i1 <= 12; ++i1) {
            for(int k1 = -8; k1 <= 8; ++k1) {
                Math.abs(i1);
                Math.abs(k1);
                if(!this.isSurface(world, i1, 0, k1)) {
                    this.laySandBase(world, i1, 0, k1);
                }
                for(j1 = 1; j1 <= 8; ++j1) {
                    this.setAir(world, i1, j1, k1);
                }
            }
        }
        this.loadStrScan("nomad_tent_chieftain");
        this.associateBlockMetaAlias("PLANK", this.plankBlock, this.plankMeta);
        this.associateBlockMetaAlias("PLANK_SLAB", this.plankSlabBlock, this.plankSlabMeta);
        this.associateBlockMetaAlias("PLANK_SLAB_INV", this.plankSlabBlock, this.plankSlabMeta | 8);
        this.associateBlockAlias("PLANK_STAIR", this.plankStairBlock);
        this.associateBlockMetaAlias("FENCE", this.fenceBlock, this.fenceMeta);
        this.associateBlockMetaAlias("TENT", this.tentBlock, this.tentMeta);
        this.associateBlockMetaAlias("TENT2", this.tent2Block, this.tent2Meta);
        this.associateBlockMetaAlias("CARPET", this.carpetBlock, this.carpetMeta);
        this.associateBlockMetaAlias("CARPET2", this.carpet2Block, this.carpet2Meta);
        this.generateStrScan(world, random, 0, 1, 0);
        this.setBlockAndMetadata(world, -6, 1, 4, this.bedBlock, 0);
        this.setBlockAndMetadata(world, -6, 1, 5, this.bedBlock, 8);
        this.setBlockAndMetadata(world, -5, 1, 4, this.bedBlock, 0);
        this.setBlockAndMetadata(world, -5, 1, 5, this.bedBlock, 8);
        this.setBlockAndMetadata(world, 5, 1, 4, this.bedBlock, 0);
        this.setBlockAndMetadata(world, 5, 1, 5, this.bedBlock, 8);
        this.setBlockAndMetadata(world, 6, 1, 4, this.bedBlock, 0);
        this.setBlockAndMetadata(world, 6, 1, 5, this.bedBlock, 8);
        this.placeChest(world, random, -11, 1, 0, LOTRMod.chestBasket, 4, LOTRChestContents.NOMAD_TENT);
        this.placeChest(world, random, 11, 1, 0, LOTRMod.chestBasket, 5, LOTRChestContents.NOMAD_TENT);
        this.placeWeaponRack(world, -5, 3, -5, 4, this.getRandomUmbarWeapon(random));
        this.placeWeaponRack(world, 5, 3, -5, 4, this.getRandomUmbarWeapon(random));
        this.placeMug(world, random, -4, 2, -5, 2, LOTRFoods.NOMAD_DRINK);
        this.placePlateWithCertainty(world, random, -6, 2, -5, LOTRMod.ceramicPlateBlock, LOTRFoods.NOMAD);
        this.placePlateWithCertainty(world, random, 6, 2, -5, LOTRMod.ceramicPlateBlock, LOTRFoods.NOMAD);
        this.placeMug(world, random, 4, 2, -5, 2, LOTRFoods.NOMAD_DRINK);
        this.placeWallBanner(world, 0, 3, 7, LOTRItemBanner.BannerType.NEAR_HARAD, 2);
        this.placeWallBanner(world, -5, 4, 6, LOTRItemBanner.BannerType.HARAD_NOMAD, 2);
        this.placeWallBanner(world, 5, 4, 6, LOTRItemBanner.BannerType.HARAD_NOMAD, 2);
        this.placeWallBanner(world, -12, 4, 0, LOTRItemBanner.BannerType.HARAD_NOMAD, 1);
        this.placeWallBanner(world, 12, 4, 0, LOTRItemBanner.BannerType.HARAD_NOMAD, 3);
        this.placeWallBanner(world, 0, 5, -8, LOTRItemBanner.BannerType.HARAD_NOMAD, 2);
        this.setBlockAndMetadata(world, -1, 4, -9, Blocks.skull, 2);
        this.setBlockAndMetadata(world, 1, 4, -9, Blocks.skull, 2);
        LOTREntityNomadChieftain chief = new LOTREntityNomadChieftain(world);
        this.spawnNPCAndSetHome(chief, world, 0, 1, 0, 8);
        int warriors = 2 + random.nextInt(2);
        for(int l = 0; l < warriors; ++l) {
            LOTREntityNomadWarrior warrior = new LOTREntityNomadWarrior(world);
            warrior.spawnRidingHorse = false;
            this.spawnNPCAndSetHome(warrior, world, random.nextBoolean() ? -6 : 6, 1, 0, 8);
        }
        for(int i1 : new int[] {-5, 5}) {
            int j12 = 1;
            int k1 = -8;
            if(!this.isOpaque(world, i1, j12 - 1, k1) || !this.isAir(world, i1, j12, k1)) continue;
            this.setBlockAndMetadata(world, i1, j12, k1, this.fenceBlock, this.fenceMeta);
            this.setBlockAndMetadata(world, i1, j12 + 1, k1, this.fenceBlock, this.fenceMeta);
            LOTREntityCamel camel = new LOTREntityCamel(world);
            this.spawnNPCAndSetHome(camel, world, i1, j12, k1, 0);
            camel.saddleMountForWorldGen();
            camel.detachHome();
            camel.setNomadChestAndCarpet();
            this.leashEntityTo(camel, world, i1, j12, k1);
        }
        return true;
    }
}
