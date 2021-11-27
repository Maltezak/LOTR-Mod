package lotr.common.world.biome;

import lotr.common.entity.animal.LOTREntitySeagull;
import net.minecraft.block.Block;
import net.minecraft.world.biome.BiomeGenBase;

public class LOTRBiomeGenBeach extends LOTRBiomeGenOcean {
    public LOTRBiomeGenBeach(int i, boolean major) {
        super(i, major);
        this.setMinMaxHeight(0.1f, 0.0f);
        this.setTemperatureRainfall(0.8f, 0.4f);
        this.spawnableCreatureList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableLOTRAmbientList.clear();
        this.spawnableLOTRAmbientList.add(new BiomeGenBase.SpawnListEntry(LOTREntitySeagull.class, 20, 4, 4));
    }

    public LOTRBiomeGenBeach setBeachBlock(Block block, int meta) {
        this.topBlock = block;
        this.topBlockMeta = meta;
        this.fillerBlock = block;
        this.fillerBlockMeta = meta;
        return this;
    }
}
