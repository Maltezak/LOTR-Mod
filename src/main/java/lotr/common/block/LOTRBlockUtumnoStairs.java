package lotr.common.block;

import lotr.common.world.LOTRWorldProviderUtumno;
import net.minecraft.block.Block;

public class LOTRBlockUtumnoStairs extends LOTRBlockStairs implements LOTRWorldProviderUtumno.UtumnoBlock {
    public LOTRBlockUtumnoStairs(Block block, int i) {
        super(block, i);
    }
}
