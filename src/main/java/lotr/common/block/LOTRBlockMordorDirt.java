package lotr.common.block;

import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class LOTRBlockMordorDirt extends Block {
    public LOTRBlockMordorDirt() {
        super(Material.ground);
        this.setHardness(0.5f);
        this.setStepSound(Block.soundTypeGravel);
        this.setCreativeTab(LOTRCreativeTabs.tabBlock);
    }
}
