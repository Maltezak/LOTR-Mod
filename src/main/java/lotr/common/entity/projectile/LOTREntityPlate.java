package lotr.common.entity.projectile;

import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import io.netty.buffer.ByteBuf;
import lotr.common.*;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.*;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class LOTREntityPlate
extends EntityThrowable
implements IEntityAdditionalSpawnData {
    private int plateSpin;
    private Block plateBlock;

    public LOTREntityPlate(World world) {
        super(world);
        this.setSize(0.5f, 0.5f);
    }

    public LOTREntityPlate(World world, Block block, EntityLivingBase entityliving) {
        super(world, entityliving);
        this.setSize(0.5f, 0.5f);
        this.plateBlock = block;
    }

    public LOTREntityPlate(World world, Block block, double d, double d1, double d2) {
        super(world, d, d1, d2);
        this.setSize(0.5f, 0.5f);
        this.plateBlock = block;
    }

    public void writeSpawnData(ByteBuf data) {
        data.writeShort(Block.getIdFromBlock(this.plateBlock));
    }

    public void readSpawnData(ByteBuf data) {
        Block block = Block.getBlockById(data.readShort());
        if (block == null) {
            block = LOTRMod.plateBlock;
        }
        this.plateBlock = block;
    }

    public Block getPlateBlock() {
        return this.plateBlock;
    }

    public void onUpdate() {
        super.onUpdate();
        ++this.plateSpin;
        this.rotationYaw = this.plateSpin % 12 / 12.0f * 360.0f;
        float speed = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
        if (speed > 0.1f && this.motionY < 0.0 && this.isInWater()) {
            float factor = MathHelper.randomFloatClamp(this.rand, 0.4f, 0.8f);
            this.motionX *= factor;
            this.motionZ *= factor;
            this.motionY += factor;
        }
    }

    public void writeEntityToNBT(NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        if (this.plateBlock != null) {
            nbt.setShort("PlateBlockID", (short)Block.getIdFromBlock(this.plateBlock));
        }
    }

    public void readEntityFromNBT(NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        if (nbt.hasKey("PlateBlockID")) {
            this.plateBlock = Block.getBlockById(nbt.getShort("PlateBlockID"));
        }
        if (this.plateBlock == null) {
            this.plateBlock = LOTRMod.plateBlock;
        }
    }

    protected void onImpact(MovingObjectPosition m) {
        int i;
        int k;
        int j;
        if (m.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY) {
            if (m.entityHit == this.getThrower()) {
                return;
            }
            m.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 1.0f);
        } else if (m.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && !this.worldObj.isRemote && this.breakGlass(i = m.blockX, j = m.blockY, k = m.blockZ)) {
            int range = 2;
            for (int i1 = i - range; i1 <= i + range; ++i1) {
                for (int j1 = j - range; j1 <= j + range; ++j1) {
                    for (int k1 = k - range; k1 <= k + range; ++k1) {
                        if (this.rand.nextInt(4) == 0) continue;
                        this.breakGlass(i1, j1, k1);
                    }
                }
            }
            return;
        }
        for (i = 0; i < 8; ++i) {
            double d = this.posX + MathHelper.randomFloatClamp(this.rand, -0.25f, 0.25f);
            double d1 = this.posY + MathHelper.randomFloatClamp(this.rand, -0.25f, 0.25f);
            double d2 = this.posZ + MathHelper.randomFloatClamp(this.rand, -0.25f, 0.25f);
            this.worldObj.spawnParticle("blockcrack_" + Block.getIdFromBlock(this.plateBlock) + "_0", d, d1, d2, 0.0, 0.0, 0.0);
        }
        if (!this.worldObj.isRemote) {
            this.worldObj.playSoundAtEntity(this, this.plateBlock.stepSound.getBreakSound(), 1.0f, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f);
            this.setDead();
        }
    }

    private boolean breakGlass(int i, int j, int k) {
        Block block = this.worldObj.getBlock(i, j, k);
        if (block.getMaterial() == Material.glass && !(LOTRBannerProtection.isProtected(this.worldObj, i, j, k, LOTRBannerProtection.forThrown(this), true))) {
            this.worldObj.playAuxSFX(2001, i, j, k, Block.getIdFromBlock(block) + (this.worldObj.getBlockMetadata(i, j, k) << 12));
            this.worldObj.setBlockToAir(i, j, k);
            return true;
        }
        return false;
    }

    protected float func_70182_d() {
        return 1.5f;
    }

    protected float getGravityVelocity() {
        return 0.02f;
    }
}

