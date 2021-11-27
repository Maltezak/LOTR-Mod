package lotr.common.item;

import lotr.common.LOTRCreativeTabs;
import net.minecraft.item.*;

public class LOTRItemPickaxe extends ItemPickaxe {
    public LOTRItemPickaxe(LOTRMaterial material) {
        this(material.toToolMaterial());
    }

    public LOTRItemPickaxe(Item.ToolMaterial material) {
        super(material);
        this.setCreativeTab(LOTRCreativeTabs.tabTools);
    }
}
