package lotr.common.dispenser;

import lotr.common.entity.projectile.LOTREntityPlate;
import net.minecraft.block.Block;
import net.minecraft.dispenser.*;
import net.minecraft.entity.IProjectile;
import net.minecraft.world.World;

public class LOTRDispensePlate extends BehaviorProjectileDispense {
    private Block plateBlock;

    public LOTRDispensePlate(Block block) {
        this.plateBlock = block;
    }

    @Override
    protected IProjectile getProjectileEntity(World world, IPosition position) {
        return new LOTREntityPlate(world, this.plateBlock, position.getX(), position.getY(), position.getZ());
    }
}
