package lotr.common.block;

import lotr.common.world.LOTRWorldProviderUtumno;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public abstract class LOTRBlockUtumnoSlabBase extends LOTRBlockSlabBase implements LOTRWorldProviderUtumno.UtumnoBlock {
    public LOTRBlockUtumnoSlabBase(boolean flag, int n) {
        super(flag, Material.rock, n);
        this.setHardness(1.5f);
        this.setResistance(Float.MAX_VALUE);
        this.setStepSound(Block.soundTypeStone);
    }
}
