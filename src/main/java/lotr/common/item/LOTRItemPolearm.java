package lotr.common.item;

import net.minecraft.item.*;

public class LOTRItemPolearm extends LOTRItemSword {
    public LOTRItemPolearm(LOTRMaterial material) {
        this(material.toToolMaterial());
    }

    public LOTRItemPolearm(Item.ToolMaterial material) {
        super(material);
    }

    @Override
    public EnumAction getItemUseAction(ItemStack itemstack) {
        return EnumAction.none;
    }
}
