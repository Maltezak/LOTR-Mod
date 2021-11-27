package lotr.common.enchant;

import java.util.*;

import net.minecraft.entity.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;

public class LOTREnchantmentBane extends LOTREnchantmentDamage {
    private List<Class<? extends EntityLivingBase>> entityClasses;
    private EnumCreatureAttribute entityAttribute;
    public final float baneDamage;
    public boolean isAchievable = true;

    private LOTREnchantmentBane(String s, float boost) {
        super(s, 0.0f);
        this.baneDamage = boost;
        this.setValueModifier((10.0f + this.baneDamage) / 10.0f);
        this.setPersistsReforge();
        this.setBypassAnvilLimit();
    }

    public LOTREnchantmentBane(String s, float boost, Class<? extends EntityLivingBase>... classes) {
        this(s, boost);
        this.entityClasses = Arrays.asList(classes);
    }

    public LOTREnchantmentBane(String s, float boost, EnumCreatureAttribute attr) {
        this(s, boost);
        this.entityAttribute = attr;
    }

    public LOTREnchantmentBane setUnachievable() {
        this.isAchievable = false;
        return this;
    }

    public boolean isEntityType(EntityLivingBase entity) {
        if(this.entityClasses != null) {
            for(Class<? extends EntityLivingBase> cls : this.entityClasses) {
                if(!cls.isAssignableFrom(entity.getClass())) continue;
                return true;
            }
        }
        else if(this.entityAttribute != null) {
            return entity.getCreatureAttribute() == this.entityAttribute;
        }
        return false;
    }

    @Override
    public float getBaseDamageBoost() {
        return 0.0f;
    }

    @Override
    public float getEntitySpecificDamage(EntityLivingBase entity) {
        if(this.isEntityType(entity)) {
            return this.baneDamage;
        }
        return 0.0f;
    }

    public int getRandomKillsRequired(Random random) {
        return MathHelper.getRandomIntegerInRange(random, 100, 250);
    }

    @Override
    public String getDescription(ItemStack itemstack) {
        return StatCollector.translateToLocalFormatted("lotr.enchant." + this.enchantName + ".desc", this.formatAdditive(this.baneDamage));
    }

    @Override
    public boolean isBeneficial() {
        return true;
    }
}
