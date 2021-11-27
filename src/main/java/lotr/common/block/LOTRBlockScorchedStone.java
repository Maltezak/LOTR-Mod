package lotr.common.block;

import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class LOTRBlockScorchedStone extends Block {
    public LOTRBlockScorchedStone() {
        super(Material.rock);
        this.setHardness(2.0f);
        this.setResistance(10.0f);
        this.setStepSound(Block.soundTypeStone);
        this.setCreativeTab(LOTRCreativeTabs.tabBlock);
    }
}
