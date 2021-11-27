package lotr.common.block;

import net.minecraft.creativetab.CreativeTabs;

public class LOTRBlockRedBrick extends LOTRBlockBrickBase {
    public LOTRBlockRedBrick() {
        this.setBrickNames("mossy", "cracked");
        this.setCreativeTab(CreativeTabs.tabBlock);
        this.setHardness(2.0f);
        this.setResistance(10.0f);
    }
}
