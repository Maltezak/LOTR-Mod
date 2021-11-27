package lotr.common.world.biome;

import net.minecraft.init.Blocks;

public class LOTRBiomeGenForodwaithGlacier extends LOTRBiomeGenForodwaithMountains {
    public LOTRBiomeGenForodwaithGlacier(int i, boolean major) {
        super(i, major);
        this.topBlock = Blocks.ice;
        this.fillerBlock = Blocks.ice;
    }
}
