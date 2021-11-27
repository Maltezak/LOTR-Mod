package lotr.common.block;

import lotr.common.world.LOTRWorldProviderUtumno;
import net.minecraft.block.Block;

public class LOTRBlockUtumnoPillar extends LOTRBlockPillarBase implements LOTRWorldProviderUtumno.UtumnoBlock {
    public LOTRBlockUtumnoPillar() {
        this.setPillarNames("fire", "ice", "obsidian");
        this.setHardness(1.5f);
        this.setResistance(Float.MAX_VALUE);
        this.setStepSound(Block.soundTypeStone);
    }
}
