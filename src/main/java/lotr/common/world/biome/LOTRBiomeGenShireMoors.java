package lotr.common.world.biome;

import java.util.Random;

import lotr.common.LOTRMod;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.*;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.structure2.*;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRBiomeGenShireMoors extends LOTRBiomeGenShire {
    private WorldGenerator boulderSmall = new LOTRWorldGenBoulder(Blocks.stone, 0, 1, 2);
    private WorldGenerator boulderLarge = new LOTRWorldGenBoulder(Blocks.stone, 0, 3, 5);

    public LOTRBiomeGenShireMoors(int i, boolean major) {
        super(i, major);
        this.clearBiomeVariants();
        this.variantChance = 0.2f;
        this.addBiomeVariantSet(LOTRBiomeVariant.SET_MOUNTAINS);
        this.decorator.treesPerChunk = 0;
        this.decorator.flowersPerChunk = 16;
        this.decorator.doubleFlowersPerChunk = 0;
        this.decorator.grassPerChunk = 16;
        this.decorator.doubleGrassPerChunk = 1;
        this.decorator.addTree(LOTRTreeType.OAK_LARGE, 8000);
        this.decorator.addTree(LOTRTreeType.CHESTNUT_LARGE, 2000);
        this.addFlower(LOTRMod.shireHeather, 0, 100);
        this.biomeColors.resetGrass();
        this.decorator.addRandomStructure(new LOTRWorldGenHobbitWindmill(false), 500);
        this.decorator.addRandomStructure(new LOTRWorldGenHobbitFarm(false), 1000);
        this.decorator.addRandomStructure(new LOTRWorldGenHobbitTavern(false), 200);
        this.setBanditChance(LOTREventSpawner.EventChance.BANDIT_RARE);
    }

    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return LOTRMusicRegion.SHIRE.getSubregion("moors");
    }

    @Override
    public void decorate(World world, Random random, int i, int k) {
        int k1;
        int i1;
        int l;
        super.decorate(world, random, i, k);
        if(random.nextInt(8) == 0) {
            for(l = 0; l < 4; ++l) {
                i1 = i + random.nextInt(16) + 8;
                k1 = k + random.nextInt(16) + 8;
                this.boulderSmall.generate(world, random, i1, world.getHeightValue(i1, k1), k1);
            }
        }
        if(random.nextInt(30) == 0) {
            for(l = 0; l < 4; ++l) {
                i1 = i + random.nextInt(16) + 8;
                k1 = k + random.nextInt(16) + 8;
                this.boulderLarge.generate(world, random, i1, world.getHeightValue(i1, k1), k1);
            }
        }
    }

    @Override
    public float getTreeIncreaseChance() {
        return 0.1f;
    }

    @Override
    public float getChanceToSpawnAnimals() {
        return 0.25f;
    }

    @Override
    public int spawnCountMultiplier() {
        return super.spawnCountMultiplier() * 2;
    }
}
