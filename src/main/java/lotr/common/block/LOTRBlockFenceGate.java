package lotr.common.block;

import cpw.mods.fml.relauncher.*;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.*;
import net.minecraft.util.IIcon;

public class LOTRBlockFenceGate extends BlockFenceGate {
    private Block plankBlock;
    private int plankMeta;

    public LOTRBlockFenceGate(Block block, int meta) {
        this.plankBlock = block;
        this.plankMeta = meta;
        this.setCreativeTab(LOTRCreativeTabs.tabUtil);
        this.setHardness(2.0f);
        this.setResistance(5.0f);
        this.setStepSound(soundTypeWood);
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public IIcon getIcon(int i, int j) {
        return this.plankBlock.getIcon(i, this.plankMeta);
    }
}
