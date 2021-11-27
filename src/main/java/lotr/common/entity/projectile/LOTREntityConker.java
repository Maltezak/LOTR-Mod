package lotr.common.entity.projectile;

import lotr.common.LOTRMod;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class LOTREntityConker extends EntityThrowable {
    public LOTREntityConker(World world) {
        super(world);
    }

    public LOTREntityConker(World world, EntityLivingBase entity) {
        super(world, entity);
    }

    public LOTREntityConker(World world, double d, double d1, double d2) {
        super(world, d, d1, d2);
    }

    @Override
    protected void onImpact(MovingObjectPosition m) {
        if(m.entityHit != null) {
            m.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 1.0f);
        }
        if(!this.worldObj.isRemote) {
            this.entityDropItem(new ItemStack(LOTRMod.chestnut), 0.0f);
            this.setDead();
        }
    }

    @Override
    protected float func_70182_d() {
        return 1.0f;
    }

    @Override
    protected float getGravityVelocity() {
        return 0.04f;
    }
}
