package lotr.common.item;

import net.minecraft.item.*;

public class LOTRItemHammer extends LOTRItemSword {
    public LOTRItemHammer(LOTRMaterial material) {
        this(material.toToolMaterial());
        this.lotrWeaponDamage += 2.0f;
    }

    public LOTRItemHammer(Item.ToolMaterial material) {
        super(material);
    }

    @Override
    public EnumAction getItemUseAction(ItemStack itemstack) {
        return EnumAction.none;
    }
}
