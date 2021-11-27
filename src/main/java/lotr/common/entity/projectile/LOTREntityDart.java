package lotr.common.entity.projectile;

import lotr.common.item.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTREntityDart extends LOTREntityProjectileBase {
    public static final float DEF_DART_DAMAGE = 1.0f;
    public float dartDamageFactor = 1.0f;

    public LOTREntityDart(World world) {
        super(world);
    }

    public LOTREntityDart(World world, ItemStack item, double d, double d1, double d2) {
        super(world, item, d, d1, d2);
    }

    public LOTREntityDart(World world, EntityLivingBase entityliving, ItemStack item, float charge) {
        super(world, entityliving, item, charge);
    }

    public LOTREntityDart(World world, EntityLivingBase entityliving, EntityLivingBase target, ItemStack item, float charge, float inaccuracy) {
        super(world, entityliving, target, item, charge, inaccuracy);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        nbt.setFloat("DartDamage", this.dartDamageFactor);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        this.dartDamageFactor = nbt.getFloat("DartDamage");
    }

    @Override
    public float getBaseImpactDamage(Entity entity, ItemStack itemstack) {
        float speed = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
        return speed * this.dartDamageFactor;
    }

    @Override
    protected float getKnockbackFactor() {
        return 0.5f;
    }

    @Override
    protected void onCollideWithTarget(Entity entity) {
        Item item;
        ItemStack itemstack;
        if(!this.worldObj.isRemote && entity instanceof EntityLivingBase && (itemstack = this.getProjectileItem()) != null && (item = itemstack.getItem()) instanceof LOTRItemDart && ((LOTRItemDart) item).isPoisoned) {
            LOTRItemDagger.applyStandardPoison((EntityLivingBase) entity);
        }
        super.onCollideWithTarget(entity);
    }

    @Override
    public int maxTicksInGround() {
        return 1200;
    }
}
