package lotr.common.world.biome;

import java.util.Random;

import lotr.common.LOTRAchievement;
import lotr.common.entity.animal.*;
import lotr.common.entity.npc.LOTREntityBanditHarad;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.spawning.*;
import lotr.common.world.structure2.*;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.NoiseGeneratorPerlin;

public class LOTRBiomeGenFarHaradCoast extends LOTRBiomeGenFarHaradSavannah {
    protected static NoiseGeneratorPerlin noiseGrass = new NoiseGeneratorPerlin(new Random(75796728360672L), 1);
    protected static NoiseGeneratorPerlin noiseDirt = new NoiseGeneratorPerlin(new Random(63275968906L), 1);
    protected static NoiseGeneratorPerlin noiseSand = new NoiseGeneratorPerlin(new Random(127425276902L), 1);
    protected static NoiseGeneratorPerlin noiseSandstone = new NoiseGeneratorPerlin(new Random(267215026920L), 1);

    public LOTRBiomeGenFarHaradCoast(int i, boolean major) {
        super(i, major);
        this.spawnableCreatureList.clear();
        this.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(LOTREntityLion.class, 4, 2, 4));
        this.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(LOTREntityLioness.class, 4, 2, 4));
        this.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(LOTREntityZebra.class, 8, 4, 8));
        this.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(LOTREntityRhino.class, 8, 4, 4));
        this.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(LOTREntityGemsbok.class, 8, 4, 8));
        this.npcSpawnList.clear();
        LOTRBiomeSpawnList.SpawnListContainer[] arrspawnListContainer = new LOTRBiomeSpawnList.SpawnListContainer[1];
        arrspawnListContainer[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.CORSAIRS, 10).setSpawnChance(5000);
        this.npcSpawnList.newFactionList(100).add(arrspawnListContainer);
        this.populatedSpawnList.clear();
        this.topBlock = Blocks.stone;
        this.topBlockMeta = 0;
        this.fillerBlock = this.topBlock;
        this.fillerBlockMeta = this.topBlockMeta;
        this.biomeTerrain.setXZScale(30.0);
        this.clearBiomeVariants();
        this.decorator.addTree(LOTRTreeType.PALM, 4000);
        this.decorator.treesPerChunk = 1;
        this.decorator.clearRandomStructures();
        this.decorator.addRandomStructure(new LOTRWorldGenCorsairCove(false), 10);
        this.decorator.addRandomStructure(new LOTRWorldGenCorsairCamp(false), 100);
        this.clearTravellingTraders();
        this.setBanditChance(LOTREventSpawner.EventChance.BANDIT_COMMON);
        this.setBanditEntityClass(LOTREntityBanditHarad.class);
        this.invasionSpawns.clearInvasions();
        this.invasionSpawns.addInvasion(LOTRInvasions.NEAR_HARAD_CORSAIR, LOTREventSpawner.EventChance.COMMON);
    }

    @Override
    public LOTRAchievement getBiomeAchievement() {
        return LOTRAchievement.enterCorsairCoasts;
    }

    @Override
    public void generateBiomeTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, double stoneNoise, int height, LOTRBiomeVariant variant) {
        Block topBlock_pre = this.topBlock;
        int topBlockMeta_pre = this.topBlockMeta;
        Block fillerBlock_pre = this.fillerBlock;
        int fillerBlockMeta_pre = this.fillerBlockMeta;
        double d1 = noiseGrass.func_151601_a(i * 0.06, k * 0.06);
        double d2 = noiseGrass.func_151601_a(i * 0.47, k * 0.47);
        double d3 = noiseDirt.func_151601_a(i * 0.06, k * 0.06);
        double d4 = noiseDirt.func_151601_a(i * 0.47, k * 0.47);
        double d5 = noiseSand.func_151601_a(i * 0.06, k * 0.06);
        double d6 = noiseSand.func_151601_a(i * 0.47, k * 0.47);
        double d7 = noiseSandstone.func_151601_a(i * 0.06, k * 0.06);
        if(d7 + (noiseSandstone.func_151601_a(i * 0.47, k * 0.47)) > 0.8) {
            this.topBlock = Blocks.sandstone;
            this.topBlockMeta = 0;
            this.fillerBlock = Blocks.sand;
            this.fillerBlockMeta = 0;
        }
        else if(d5 + d6 > 0.6) {
            this.topBlock = Blocks.sand;
            this.topBlockMeta = 0;
            this.fillerBlock = this.topBlock;
            this.fillerBlockMeta = this.topBlockMeta;
        }
        else if(d3 + d4 > 0.5) {
            this.topBlock = Blocks.dirt;
            this.topBlockMeta = 1;
            this.fillerBlock = this.topBlock;
            this.fillerBlockMeta = this.topBlockMeta;
        }
        else if(d1 + d2 > 0.4) {
            this.topBlock = Blocks.grass;
            this.topBlockMeta = 0;
            this.fillerBlock = Blocks.dirt;
            this.fillerBlockMeta = 0;
        }
        super.generateBiomeTerrain(world, random, blocks, meta, i, k, stoneNoise, height, variant);
        this.topBlock = topBlock_pre;
        this.topBlockMeta = topBlockMeta_pre;
        this.fillerBlock = fillerBlock_pre;
        this.fillerBlockMeta = fillerBlockMeta_pre;
    }

    @Override
    public float getChanceToSpawnAnimals() {
        return 0.1f;
    }
}
