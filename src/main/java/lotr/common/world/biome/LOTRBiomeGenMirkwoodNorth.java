package lotr.common.world.biome;

import lotr.common.entity.animal.LOTREntityGorcrow;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.spawning.*;
import net.minecraft.world.biome.BiomeGenBase;

public class LOTRBiomeGenMirkwoodNorth extends LOTRBiomeGenMirkwood {
    public LOTRBiomeGenMirkwoodNorth(int i, boolean major) {
        super(i, major);
        this.spawnableLOTRAmbientList.add(new BiomeGenBase.SpawnListEntry(LOTREntityGorcrow.class, 4, 4, 4));
        this.addBiomeVariantSet(LOTRBiomeVariant.SET_FOREST);
        this.decorator.treesPerChunk = 12;
        this.decorator.willowPerChunk = 1;
        this.decorator.logsPerChunk = 1;
        this.decorator.flowersPerChunk = 2;
        this.decorator.doubleFlowersPerChunk = 1;
        this.decorator.grassPerChunk = 8;
        this.decorator.doubleGrassPerChunk = 6;
        this.decorator.enableFern = true;
        this.decorator.addTree(LOTRTreeType.GREEN_OAK, 1000);
        this.decorator.addTree(LOTRTreeType.GREEN_OAK_LARGE, 50);
        this.decorator.addTree(LOTRTreeType.RED_OAK, 15);
        this.decorator.addTree(LOTRTreeType.RED_OAK_LARGE, 10);
        this.decorator.addTree(LOTRTreeType.OAK, 50);
        this.decorator.addTree(LOTRTreeType.OAK_LARGE, 100);
        this.decorator.addTree(LOTRTreeType.MIRK_OAK, 50);
        this.decorator.addTree(LOTRTreeType.SPRUCE, 100);
        this.decorator.addTree(LOTRTreeType.SPRUCE_THIN, 50);
        this.decorator.addTree(LOTRTreeType.SPRUCE_MEGA, 20);
        this.decorator.addTree(LOTRTreeType.SPRUCE_MEGA_THIN, 20);
        this.decorator.addTree(LOTRTreeType.CHESTNUT, 20);
        this.decorator.addTree(LOTRTreeType.CHESTNUT_LARGE, 50);
        this.decorator.addTree(LOTRTreeType.LARCH, 200);
        this.decorator.addTree(LOTRTreeType.FIR, 200);
        this.decorator.addTree(LOTRTreeType.PINE, 400);
        this.decorator.addTree(LOTRTreeType.ASPEN, 50);
        this.decorator.addTree(LOTRTreeType.ASPEN_LARGE, 10);
        this.setBanditChance(LOTREventSpawner.EventChance.BANDIT_RARE);
        this.invasionSpawns.addInvasion(LOTRInvasions.WOOD_ELF, LOTREventSpawner.EventChance.UNCOMMON);
        this.invasionSpawns.addInvasion(LOTRInvasions.DOL_GULDUR, LOTREventSpawner.EventChance.UNCOMMON);
    }

    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return LOTRMusicRegion.MIRKWOOD.getSubregion("north");
    }

    @Override
    public float getChanceToSpawnAnimals() {
        return 0.2f;
    }

    @Override
    public int spawnCountMultiplier() {
        return 4;
    }
}
