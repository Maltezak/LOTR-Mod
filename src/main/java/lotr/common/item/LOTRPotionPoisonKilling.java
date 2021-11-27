package lotr.common.item;

import cpw.mods.fml.relauncher.*;
import lotr.common.*;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.*;

public class LOTRPotionPoisonKilling extends Potion {
    public LOTRPotionPoisonKilling() {
        super(30, true, Potion.poison.getLiquidColor());
        this.setPotionName("potion.lotr.drinkPoison");
        this.setEffectiveness(Potion.poison.getEffectiveness());
        this.setIconIndex(0, 0);
    }

    @Override
    public void performEffect(EntityLivingBase entity, int level) {
        entity.attackEntityFrom(LOTRDamage.poisonDrink, 1.0f);
    }

    @Override
    public boolean isReady(int tick, int level) {
        int freq = 5 >> level;
        return freq > 0 ? tick % freq == 0 : true;
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public boolean hasStatusIcon() {
        return false;
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void renderInventoryEffect(int x, int y, PotionEffect effect, Minecraft mc) {
        LOTRMod.proxy.renderCustomPotionEffect(x, y, effect, mc);
    }
}
