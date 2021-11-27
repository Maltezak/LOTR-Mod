package lotr.common.item;

import lotr.common.block.LOTRBlockGoran;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class LOTRItemBlockGoran extends LOTRItemBlockMetadata {
    public LOTRItemBlockGoran(Block block) {
        super(block);
    }

    @Override
    public String getItemStackDisplayName(ItemStack itemstack) {
        int i = itemstack.getItemDamage();
        if(i >= LOTRBlockGoran.displayNames.length) {
            i = 0;
        }
        return LOTRBlockGoran.displayNames[i];
    }
}
