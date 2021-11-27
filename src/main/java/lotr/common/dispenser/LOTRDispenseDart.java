package lotr.common.dispenser;

import lotr.common.entity.projectile.LOTREntityDart;
import lotr.common.item.LOTRItemDart;
import net.minecraft.dispenser.*;
import net.minecraft.entity.IProjectile;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRDispenseDart extends BehaviorProjectileDispense {
    private LOTRItemDart theDartItem;

    public LOTRDispenseDart(LOTRItemDart item) {
        this.theDartItem = item;
    }

    @Override
    protected IProjectile getProjectileEntity(World world, IPosition iposition) {
        ItemStack itemstack = new ItemStack(this.theDartItem);
        LOTREntityDart dart = this.theDartItem.createDart(world, itemstack, iposition.getX(), iposition.getY(), iposition.getZ());
        dart.canBePickedUp = 1;
        return dart;
    }

    @Override
    protected float func_82500_b() {
        return 1.5f;
    }

    @Override
    protected void playDispenseSound(IBlockSource source) {
        source.getWorld().playSoundEffect(source.getXInt(), source.getYInt(), source.getZInt(), "lotr:item.dart", 1.0f, 1.2f);
    }
}
