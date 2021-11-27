package lotr.common.item;

import lotr.common.LOTRCreativeTabs;

public class LOTRItemRingil extends LOTRItemSword implements LOTRStoryItem {
    public LOTRItemRingil() {
        super(LOTRMaterial.HIGH_ELVEN);
        this.setMaxDamage(1500);
        this.lotrWeaponDamage = 9.0f;
        this.setIsElvenBlade();
        this.setCreativeTab(LOTRCreativeTabs.tabStory);
    }
}
