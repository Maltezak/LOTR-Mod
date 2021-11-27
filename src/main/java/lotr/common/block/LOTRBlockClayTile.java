package lotr.common.block;

import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class LOTRBlockClayTile extends Block {
    public LOTRBlockClayTile() {
        super(Material.rock);
        this.setCreativeTab(LOTRCreativeTabs.tabBlock);
        this.setHardness(1.25f);
        this.setResistance(7.0f);
        this.setStepSound(Block.soundTypeStone);
    }
}
