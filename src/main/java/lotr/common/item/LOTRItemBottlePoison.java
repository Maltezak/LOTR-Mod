package lotr.common.item;

import lotr.common.LOTRCreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.world.World;

public class LOTRItemBottlePoison extends Item {
    public LOTRItemBottlePoison() {
        this.setMaxStackSize(1);
        this.setCreativeTab(LOTRCreativeTabs.tabMisc);
        this.setContainerItem(Items.glass_bottle);
    }

    @Override
    public int getMaxItemUseDuration(ItemStack itemstack) {
        return 32;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack itemstack) {
        return EnumAction.drink;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        entityplayer.setItemInUse(itemstack, this.getMaxItemUseDuration(itemstack));
        return itemstack;
    }

    @Override
    public ItemStack onEaten(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        if(!world.isRemote) {
            LOTRPoisonedDrinks.addPoisonEffect(entityplayer, itemstack);
        }
        return !entityplayer.capabilities.isCreativeMode ? new ItemStack(Items.glass_bottle) : itemstack;
    }
}
