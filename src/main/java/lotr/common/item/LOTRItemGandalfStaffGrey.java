package lotr.common.item;

import lotr.common.LOTRCreativeTabs;
import net.minecraft.item.Item;

public class LOTRItemGandalfStaffGrey extends LOTRItemSword implements LOTRStoryItem {
    public LOTRItemGandalfStaffGrey() {
        super(Item.ToolMaterial.WOOD);
        this.setMaxDamage(1000);
        this.setCreativeTab(LOTRCreativeTabs.tabStory);
        this.lotrWeaponDamage = 4.0f;
    }
}
