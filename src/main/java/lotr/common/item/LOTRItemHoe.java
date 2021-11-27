package lotr.common.item;

import lotr.common.LOTRCreativeTabs;
import net.minecraft.item.*;

public class LOTRItemHoe extends ItemHoe {
    public LOTRItemHoe(LOTRMaterial material) {
        this(material.toToolMaterial());
    }

    public LOTRItemHoe(Item.ToolMaterial material) {
        super(material);
        this.setCreativeTab(LOTRCreativeTabs.tabTools);
    }
}
