package lotr.common.item;

import lotr.common.LOTRCreativeTabs;

public class LOTRItemGlamdring extends LOTRItemSword implements LOTRStoryItem {
    public LOTRItemGlamdring() {
        super(LOTRMaterial.GONDOLIN);
        this.setCreativeTab(LOTRCreativeTabs.tabStory);
        this.setIsElvenBlade();
    }
}
