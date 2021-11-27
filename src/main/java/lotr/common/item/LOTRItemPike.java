package lotr.common.item;

import net.minecraft.item.Item;

public class LOTRItemPike extends LOTRItemPolearmLong {
    public LOTRItemPike(LOTRMaterial material) {
        this(material.toToolMaterial());
    }

    public LOTRItemPike(Item.ToolMaterial material) {
        super(material);
    }
}
