package lotr.common.dispenser;

import lotr.common.entity.projectile.LOTREntityPebble;
import net.minecraft.dispenser.*;
import net.minecraft.entity.IProjectile;
import net.minecraft.world.World;

public class LOTRDispensePebble extends BehaviorProjectileDispense {
    @Override
    protected IProjectile getProjectileEntity(World world, IPosition position) {
        return new LOTREntityPebble(world, position.getX(), position.getY(), position.getZ());
    }
}
