package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityNomad;
import lotr.common.item.LOTRItemBanner;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.world.World;

public class LOTRWorldGenNomadTentLarge extends LOTRWorldGenNomadStructure {
    public LOTRWorldGenNomadTentLarge(boolean flag) {
        super(flag);
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int j1;
        this.setOriginAndRotation(world, i, j, k, rotation, 8);
        this.setupRandomBlocks(random);
        if(this.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for(int i1 = -10; i1 <= 10; ++i1) {
                for(int k1 = -7; k1 <= 7; ++k1) {
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
        for(int i1 = -10; i1 <= 10; ++i1) {
            for(int k1 = -7; k1 <= 7; ++k1) {
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
        this.loadStrScan("nomad_tent_large");
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
        this.setBlockAndMetadata(world, -3, 1, 4, this.bedBlock, 0);
        this.setBlockAndMetadata(world, -3, 1, 5, this.bedBlock, 8);
        this.setBlockAndMetadata(world, -4, 1, 4, this.bedBlock, 0);
        this.setBlockAndMetadata(world, -4, 1, 5, this.bedBlock, 8);
        this.placeWeaponRack(world, 0, 3, 6, 6, this.getRandomNomadWeapon(random));
        this.placeChest(world, random, -4, 1, -5, LOTRMod.chestBasket, 3, LOTRChestContents.NOMAD_TENT);
        this.placeWallBanner(world, 0, 5, 7, LOTRItemBanner.BannerType.HARAD_NOMAD, 2);
        int nomads = 1 + random.nextInt(3);
        for(int l = 0; l < nomads; ++l) {
            LOTREntityNomad nomad = new LOTREntityNomad(world);
            this.spawnNPCAndSetHome(nomad, world, 0, 1, 0, 16);
        }
        return true;
    }
}
