package lotr.common.dispenser;

import lotr.common.entity.projectile.LOTREntityCrossbowBolt;
import net.minecraft.dispenser.*;
import net.minecraft.entity.IProjectile;
import net.minecraft.item.*;
import net.minecraft.world.World;

public class LOTRDispenseCrossbowBolt extends BehaviorProjectileDispense {
    private Item theBoltItem;

    public LOTRDispenseCrossbowBolt(Item item) {
        this.theBoltItem = item;
    }

    @Override
    protected IProjectile getProjectileEntity(World world, IPosition iposition) {
        ItemStack itemstack = new ItemStack(this.theBoltItem);
        LOTREntityCrossbowBolt bolt = new LOTREntityCrossbowBolt(world, itemstack, iposition.getX(), iposition.getY(), iposition.getZ());
        bolt.canBePickedUp = 1;
        return bolt;
    }

    @Override
    protected void playDispenseSound(IBlockSource source) {
        source.getWorld().playSoundEffect(source.getXInt(), source.getYInt(), source.getZInt(), "lotr:item.crossbow", 1.0f, 1.2f);
    }
}
