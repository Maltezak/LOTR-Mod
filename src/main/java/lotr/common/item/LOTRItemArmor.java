package lotr.common.item;

import lotr.common.LOTRCreativeTabs;
import lotr.common.entity.npc.LOTREntityHalfTroll;
import net.minecraft.entity.Entity;
import net.minecraft.item.*;
import net.minecraft.util.StringUtils;

public class LOTRItemArmor extends ItemArmor {
    private LOTRMaterial lotrMaterial;
    private String extraName;

    public LOTRItemArmor(LOTRMaterial material, int slotType) {
        this(material, slotType, "");
    }

    public LOTRItemArmor(LOTRMaterial material, int slotType, String s) {
        super(material.toArmorMaterial(), 0, slotType);
        this.lotrMaterial = material;
        this.setCreativeTab(LOTRCreativeTabs.tabCombat);
        this.extraName = s;
    }

    public LOTRMaterial getLOTRArmorMaterial() {
        return this.lotrMaterial;
    }

    @Override
    public String getArmorTexture(ItemStack itemstack, Entity entity, int slot, String type) {
        String path = "lotr:armor/";
        if(entity instanceof LOTREntityHalfTroll) {
            path = "lotr:mob/halfTroll/";
        }
        String armorName = this.getArmorName();
        String texture = path + armorName;
        if(type != null) {
            texture = texture + "_" + type;
        }
        return texture + ".png";
    }

    private String getArmorName() {
        String suffix;
        String prefix = this.getArmorMaterial().name().substring("lotr".length() + 1).toLowerCase();
        suffix = this.armorType == 2 ? "2" : "1";
        if(!StringUtils.isNullOrEmpty(this.extraName)) {
            suffix = this.extraName;
        }
        return prefix + "_" + suffix;
    }

    @Override
    public boolean isDamageable() {
        return this.lotrMaterial.isDamageable();
    }
}
