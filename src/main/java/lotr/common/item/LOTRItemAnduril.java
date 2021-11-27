package lotr.common.item;

import lotr.common.LOTRCreativeTabs;
import net.minecraft.item.Item;

public class LOTRItemAnduril extends LOTRItemSword implements LOTRStoryItem {
    public LOTRItemAnduril() {
        super(Item.ToolMaterial.IRON);
        this.setMaxDamage(1500);
        this.lotrWeaponDamage = 9.0f;
        this.setCreativeTab(LOTRCreativeTabs.tabStory);
    }
}
