package lotr.common.block;

import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class LOTRBlockBone extends Block {
    public LOTRBlockBone() {
        super(Material.rock);
        this.setCreativeTab(LOTRCreativeTabs.tabBlock);
        this.setHardness(1.0f);
        this.setResistance(5.0f);
        this.setStepSound(Block.soundTypeStone);
    }
}
