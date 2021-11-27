package lotr.common.item;

import lotr.common.LOTRCreativeTabs;
import lotr.common.dispenser.LOTRDispenseTermite;
import lotr.common.entity.projectile.LOTREntityThrownTermite;
import net.minecraft.block.BlockDispenser;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.world.World;

public class LOTRItemTermite extends Item {
    public LOTRItemTermite() {
        this.setMaxStackSize(16);
        this.setCreativeTab(LOTRCreativeTabs.tabMisc);
        BlockDispenser.dispenseBehaviorRegistry.putObject(this, new LOTRDispenseTermite());
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        if(!world.isRemote) {
            world.spawnEntityInWorld(new LOTREntityThrownTermite(world, entityplayer));
            world.playSoundAtEntity(entityplayer, "random.bow", 0.5f, 0.4f / (itemRand.nextFloat() * 0.4f + 0.8f));
            if(!entityplayer.capabilities.isCreativeMode) {
                --itemstack.stackSize;
            }
        }
        return itemstack;
    }
}
