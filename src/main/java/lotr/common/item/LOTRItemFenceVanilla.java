package lotr.common.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class LOTRItemFenceVanilla extends LOTRItemBlockMetadata {
    public LOTRItemFenceVanilla(Block block) {
        super(block);
    }

    @Override
    public String getUnlocalizedName(ItemStack itemstack) {
        return "tile.lotr.fenceVanilla." + itemstack.getItemDamage();
    }
}
