package lotr.common.item;

import lotr.common.LOTRCreativeTabs;
import lotr.common.dispenser.LOTRDispensePebble;
import lotr.common.entity.projectile.LOTREntityPebble;
import net.minecraft.block.BlockDispenser;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.world.World;

public class LOTRItemPebble extends Item {
    public LOTRItemPebble() {
        this.setMaxStackSize(64);
        this.setCreativeTab(LOTRCreativeTabs.tabCombat);
        BlockDispenser.dispenseBehaviorRegistry.putObject(this, new LOTRDispensePebble());
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        if(!world.isRemote) {
            world.spawnEntityInWorld(new LOTREntityPebble(world, entityplayer));
            world.playSoundAtEntity(entityplayer, "random.bow", 0.5f, 0.4f / (itemRand.nextFloat() * 0.4f + 0.8f));
            if(!entityplayer.capabilities.isCreativeMode) {
                --itemstack.stackSize;
            }
        }
        return itemstack;
    }
}
