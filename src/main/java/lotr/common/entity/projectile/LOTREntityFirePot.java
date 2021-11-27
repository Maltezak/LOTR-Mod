package lotr.common.entity.projectile;

import java.util.List;

import lotr.common.*;
import lotr.common.block.*;
import lotr.common.entity.animal.LOTREntityBird;
import net.minecraft.block.Block;
import net.minecraft.entity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class LOTREntityFirePot extends EntityThrowable {
    public LOTREntityFirePot(World world) {
        super(world);
    }

    public LOTREntityFirePot(World world, EntityLivingBase entityliving) {
        super(world, entityliving);
    }

    public LOTREntityFirePot(World world, double d, double d1, double d2) {
        super(world, d, d1, d2);
    }

    @Override
    protected void onImpact(MovingObjectPosition m) {
        if(!this.worldObj.isRemote) {
            Block block;
            EntityLivingBase thrower = this.getThrower();
            Entity hitEntity = m.entityHit;
            double range = 3.0;
            List entities = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, this.boundingBox.expand(range, range, range));
            if(hitEntity instanceof EntityLivingBase && !entities.contains(hitEntity)) {
                entities.add(hitEntity);
            }
            for(Object obj : entities) {
                EntityLivingBase entity = (EntityLivingBase) obj;
                float damage = 1.0f;
                if(entity == hitEntity) {
                    damage = 3.0f;
                }
                if(entity == hitEntity && thrower instanceof EntityPlayer && hitEntity instanceof LOTREntityBird && !((LOTREntityBird) hitEntity).isBirdStill()) {
                    LOTRLevelData.getData((EntityPlayer) thrower).addAchievement(LOTRAchievement.hitBirdFirePot);
                }
                if(!entity.attackEntityFrom(DamageSource.causeThrownDamage(this, thrower), damage)) continue;
                int fire = 2 + this.rand.nextInt(3);
                if(entity == hitEntity) {
                    fire += 2 + this.rand.nextInt(3);
                }
                entity.setFire(fire);
            }
            if(m.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && (block = this.worldObj.getBlock(m.blockX, m.blockY, m.blockZ)) instanceof LOTRBlockRhunFireJar) {
                ((LOTRBlockRhunFireJar) block).explode(this.worldObj, m.blockX, m.blockY, m.blockZ);
            }
            this.worldObj.playSoundAtEntity(this, LOTRBlockPlate.soundTypePlate.getBreakSound(), 1.0f, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f);
            this.setDead();
        }
        for(int i = 0; i < 8; ++i) {
            double d = this.posX + MathHelper.randomFloatClamp(this.rand, -0.25f, 0.25f);
            double d1 = this.posY + MathHelper.randomFloatClamp(this.rand, -0.25f, 0.25f);
            double d2 = this.posZ + MathHelper.randomFloatClamp(this.rand, -0.25f, 0.25f);
            this.worldObj.spawnParticle("blockcrack_" + Block.getIdFromBlock(LOTRMod.rhunFireJar) + "_0", d, d1, d2, 0.0, 0.0, 0.0);
        }
        for(int l = 0; l < 16; ++l) {
            String s = this.rand.nextBoolean() ? "flame" : "smoke";
            double d = this.posX;
            double d1 = this.posY;
            double d2 = this.posZ;
            double d3 = MathHelper.randomFloatClamp(this.rand, -0.1f, 0.1f);
            double d4 = MathHelper.randomFloatClamp(this.rand, 0.2f, 0.3f);
            double d5 = MathHelper.randomFloatClamp(this.rand, -0.1f, 0.1f);
            this.worldObj.spawnParticle(s, d, d1, d2, d3, d4, d5);
        }
    }

    @Override
    protected float func_70182_d() {
        return 1.2f;
    }

    @Override
    protected float getGravityVelocity() {
        return 0.04f;
    }
}
