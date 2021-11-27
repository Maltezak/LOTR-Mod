package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityNomad;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.world.World;

public class LOTRWorldGenNomadTent extends LOTRWorldGenNomadStructure {
    public LOTRWorldGenNomadTent(boolean flag) {
        super(flag);
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int j1;
        this.setOriginAndRotation(world, i, j, k, rotation, 7);
        this.setupRandomBlocks(random);
        if(this.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for(int i1 = -6; i1 <= 6; ++i1) {
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
                    if(maxHeight - minHeight <= 6) continue;
                    return false;
                }
            }
        }
        for(int i1 = -6; i1 <= 6; ++i1) {
            for(int k1 = -6; k1 <= 6; ++k1) {
                int i2 = Math.abs(i1);
                if(i2 + (Math.abs(k1)) > 9) continue;
                if(!this.isSurface(world, i1, 0, k1)) {
                    this.laySandBase(world, i1, 0, k1);
                }
                for(j1 = 1; j1 <= 8; ++j1) {
                    this.setAir(world, i1, j1, k1);
                }
            }
        }
        this.loadStrScan("nomad_tent");
        this.associateBlockMetaAlias("TENT", this.tentBlock, this.tentMeta);
        this.associateBlockMetaAlias("TENT2", this.tent2Block, this.tent2Meta);
        this.associateBlockMetaAlias("FENCE", this.fenceBlock, this.fenceMeta);
        this.generateStrScan(world, random, 0, 1, 0);
        this.setBlockAndMetadata(world, -3, 1, -2, this.bedBlock, 3);
        this.setBlockAndMetadata(world, -4, 1, -2, this.bedBlock, 11);
        this.setBlockAndMetadata(world, -3, 1, 2, this.bedBlock, 3);
        this.setBlockAndMetadata(world, -4, 1, 2, this.bedBlock, 11);
        this.placeWeaponRack(world, 0, 3, 5, 6, this.getRandomNomadWeapon(random));
        this.placeChest(world, random, 0, 1, 5, LOTRMod.chestBasket, 2, LOTRChestContents.NOMAD_TENT);
        int nomads = 1 + random.nextInt(2);
        for(int l = 0; l < nomads; ++l) {
            LOTREntityNomad nomad = new LOTREntityNomad(world);
            this.spawnNPCAndSetHome(nomad, world, 0, 1, -1, 16);
        }
        return true;
    }
}
