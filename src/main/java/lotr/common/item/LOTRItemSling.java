package lotr.common.item;

import lotr.common.*;
import lotr.common.entity.projectile.LOTREntityPebble;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.world.World;

public class LOTRItemSling extends Item {
    public LOTRItemSling() {
        this.setMaxStackSize(1);
        this.setMaxDamage(250);
        this.setCreativeTab(LOTRCreativeTabs.tabCombat);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        if(entityplayer.inventory.hasItem(LOTRMod.pebble) || entityplayer.capabilities.isCreativeMode) {
            itemstack.damageItem(1, entityplayer);
            if(!entityplayer.capabilities.isCreativeMode) {
                entityplayer.inventory.consumeInventoryItem(LOTRMod.pebble);
            }
            world.playSoundAtEntity(entityplayer, "random.bow", 0.5f, 0.4f / (itemRand.nextFloat() * 0.4f + 0.8f));
            if(!world.isRemote) {
                world.spawnEntityInWorld(new LOTREntityPebble(world, entityplayer).setSling());
            }
        }
        return itemstack;
    }

    @Override
    public boolean getIsRepairable(ItemStack itemstack, ItemStack repairItem) {
        return repairItem.getItem() == Items.leather ? true : super.getIsRepairable(itemstack, repairItem);
    }
}
