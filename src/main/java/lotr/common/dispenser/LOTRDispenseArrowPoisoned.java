package lotr.common.dispenser;

import lotr.common.entity.item.LOTREntityArrowPoisoned;
import net.minecraft.dispenser.*;
import net.minecraft.entity.IProjectile;
import net.minecraft.world.World;

public class LOTRDispenseArrowPoisoned extends BehaviorProjectileDispense {
    @Override
    protected IProjectile getProjectileEntity(World world, IPosition iposition) {
        LOTREntityArrowPoisoned arrow = new LOTREntityArrowPoisoned(world, iposition.getX(), iposition.getY(), iposition.getZ());
        arrow.canBePickedUp = 1;
        return arrow;
    }
}
