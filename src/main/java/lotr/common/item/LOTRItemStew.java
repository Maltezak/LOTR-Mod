package lotr.common.item;

import lotr.common.LOTRCreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.world.World;

public class LOTRItemStew extends ItemFood {
    public LOTRItemStew(int j, float f, boolean flag) {
        super(j, f, flag);
        this.setMaxStackSize(1);
        this.setCreativeTab(LOTRCreativeTabs.tabFood);
        this.setContainerItem(Items.bowl);
    }

    @Override
    public ItemStack onEaten(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        super.onEaten(itemstack, world, entityplayer);
        return new ItemStack(Items.bowl);
    }
}
