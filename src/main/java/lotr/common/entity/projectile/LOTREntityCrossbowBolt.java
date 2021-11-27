package lotr.common.entity.projectile;

import lotr.common.item.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTREntityCrossbowBolt extends LOTREntityProjectileBase {
    public double boltDamageFactor = 2.0;
    public static final float BOLT_RELATIVE_TO_ARROW = 2.0f;

    public LOTREntityCrossbowBolt(World world) {
        super(world);
    }

    public LOTREntityCrossbowBolt(World world, ItemStack item, double d, double d1, double d2) {
        super(world, item, d, d1, d2);
    }

    public LOTREntityCrossbowBolt(World world, EntityLivingBase entityliving, ItemStack item, float charge) {
        super(world, entityliving, item, charge);
    }

    public LOTREntityCrossbowBolt(World world, EntityLivingBase entityliving, EntityLivingBase target, ItemStack item, float charge, float inaccuracy) {
        super(world, entityliving, target, item, charge, inaccuracy);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        nbt.setDouble("boltDamageFactor", this.boltDamageFactor);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        if(nbt.hasKey("boltDamageFactor")) {
            this.boltDamageFactor = nbt.getDouble("boltDamageFactor");
        }
    }

    @Override
    public float getBaseImpactDamage(Entity entity, ItemStack itemstack) {
        float speed = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
        return speed * 2.0f * (float) this.boltDamageFactor;
    }

    @Override
    protected void onCollideWithTarget(Entity entity) {
        Item item;
        ItemStack itemstack;
        if(!this.worldObj.isRemote && entity instanceof EntityLivingBase && (itemstack = this.getProjectileItem()) != null && (item = itemstack.getItem()) instanceof LOTRItemCrossbowBolt && ((LOTRItemCrossbowBolt) item).isPoisoned) {
            LOTRItemDagger.applyStandardPoison((EntityLivingBase) entity);
        }
        super.onCollideWithTarget(entity);
    }

    @Override
    public int maxTicksInGround() {
        return 1200;
    }
}
