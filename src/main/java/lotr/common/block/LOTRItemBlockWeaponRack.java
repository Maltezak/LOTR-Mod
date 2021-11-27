package lotr.common.block;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public class LOTRItemBlockWeaponRack extends ItemBlock {
    public LOTRItemBlockWeaponRack(Block block) {
        super(block);
        this.setMaxStackSize(1);
    }
}
