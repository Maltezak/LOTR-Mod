package lotr.common.item;

import lotr.common.LOTRReflection;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.world.World;

public class LOTRItemMugTauredainCure extends LOTRItemMug {
    public LOTRItemMugTauredainCure() {
        super(true, false);
    }

    @Override
    public ItemStack onEaten(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        ItemStack result = super.onEaten(itemstack, world, entityplayer);
        if(!world.isRemote) {
            for(Potion potion : Potion.potionTypes) {
                if(potion == null || !LOTRReflection.isBadEffect(potion)) continue;
                entityplayer.removePotionEffect(potion.id);
            }
        }
        return result;
    }
}
