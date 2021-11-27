package lotr.common.item;

import lotr.common.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.world.World;

public class LOTRItemFood extends ItemFood {
    public LOTRItemFood(int healAmount, float saturation, boolean canWolfEat) {
        super(healAmount, saturation, canWolfEat);
        this.setCreativeTab(LOTRCreativeTabs.tabFood);
    }

    @Override
    public ItemStack onEaten(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        if(!world.isRemote && this == LOTRMod.maggotyBread) {
            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.eatMaggotyBread);
        }
        return super.onEaten(itemstack, world, entityplayer);
    }
}
