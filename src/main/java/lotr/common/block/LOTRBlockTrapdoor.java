package lotr.common.block;

import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;

public class LOTRBlockTrapdoor
extends BlockTrapDoor {
    public LOTRBlockTrapdoor() {
        this(Material.wood);
        this.setStepSound(Block.soundTypeWood);
        this.setHardness(3.0f);
    }

    public LOTRBlockTrapdoor(Material material) {
        super(material);
        this.setCreativeTab(LOTRCreativeTabs.tabUtil);
    }
}

