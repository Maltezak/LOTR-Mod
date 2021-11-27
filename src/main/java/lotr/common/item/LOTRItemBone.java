package lotr.common.item;

import lotr.common.LOTRCreativeTabs;
import net.minecraft.item.Item;

public class LOTRItemBone extends Item {
    public LOTRItemBone() {
        this.setCreativeTab(LOTRCreativeTabs.tabMaterials);
        this.setFull3D();
    }
}
