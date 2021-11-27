package lotr.common.entity.npc;

import java.util.*;

import lotr.common.*;
import lotr.common.entity.ai.LOTREntityAIEat;
import lotr.common.entity.animal.LOTREntityRabbit;
import lotr.common.fac.LOTRFaction;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.S27PacketExplosion;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class LOTREntitySaruman extends LOTREntityNPC {
    private LOTREntityRabbit targetingRabbit;
    private String randomNameTag;

    public LOTREntitySaruman(World world) {
        super(world);
        this.setSize(0.6f, 1.8f);
        this.getNavigator().setAvoidsWater(true);
        this.getNavigator().setBreakDoors(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIOpenDoor(this, true));
        this.tasks.addTask(2, new EntityAIWander(this, 1.0));
        this.tasks.addTask(3, new LOTREntityAIEat(this, new LOTRFoods(new ItemStack[] {new ItemStack(Blocks.log)}), 200));
        this.tasks.addTask(4, new EntityAIWatchClosest(this, EntityLivingBase.class, 20.0f, 0.05f));
        this.tasks.addTask(5, new EntityAILookIdle(this));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(40.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5);
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        this.setCurrentItemOrArmor(0, new ItemStack(LOTRMod.gandalfStaffWhite));
        return data;
    }

    @Override
    public String getCustomNameTag() {
        if(this.randomNameTag == null) {
            StringBuilder tmp = new StringBuilder();
            for(int l = 0; l < 100; ++l) {
                tmp.append((char) this.rand.nextInt(1000));
            }
            this.randomNameTag = tmp.toString();
        }
        return this.randomNameTag;
    }

    @Override
    public boolean hasCustomNameTag() {
        return true;
    }

    @Override
    public boolean getAlwaysRenderNameTag() {
        return true;
    }

    @Override
    public LOTRFaction getFaction() {
        return LOTRFaction.ISENGARD;
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if(!this.worldObj.isRemote) {
            List rabbits;
            if(this.rand.nextInt(10) == 0) {
                this.playSound(this.getLivingSound(), this.getSoundVolume(), this.getSoundPitch());
            }
            List allMobsExcludingRabbits = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(24.0, 24.0, 24.0));
            for(Object allMobsExcludingRabbit : allMobsExcludingRabbits) {
                Entity entity = (Entity) allMobsExcludingRabbit;
                if(entity instanceof LOTREntityRabbit || entity instanceof LOTREntityGandalf) continue;
                double dSq = this.getDistanceSqToEntity(entity);
                if(dSq <= 0.0) {
                    dSq = 1.0E-5;
                }
                float strength = 1.0f;
                if(entity instanceof EntityPlayer) {
                    strength /= 3.0f;
                }
                double force = strength / dSq;
                double x = entity.posX - this.posX;
                double y = entity.posY - this.posY;
                double z = entity.posZ - this.posZ;
                x *= force;
                y *= force;
                z *= force;
                if(entity instanceof EntityPlayerMP) {
                    ((EntityPlayerMP) entity).playerNetServerHandler.sendPacket(new S27PacketExplosion(this.posX, this.posY, this.posZ, 0.0f, new ArrayList(), Vec3.createVectorHelper(x, y, z)));
                    continue;
                }
                entity.motionX += x;
                entity.motionY += y;
                entity.motionZ += z;
            }
            if(this.rand.nextInt(40) == 0) {
                LOTREntityRabbit rabbit = new LOTREntityRabbit(this.worldObj);
                int i = MathHelper.floor_double(this.posX) - this.rand.nextInt(16) + this.rand.nextInt(16);
                int j = MathHelper.floor_double(this.boundingBox.minY) - this.rand.nextInt(8) + this.rand.nextInt(8);
                int k = MathHelper.floor_double(this.posZ) - this.rand.nextInt(16) + this.rand.nextInt(16);
                rabbit.setLocationAndAngles(i, j, k, 0.0f, 0.0f);
                AxisAlignedBB aabb = rabbit.boundingBox;
                if(this.worldObj.checkNoEntityCollision(aabb) && this.worldObj.getCollidingBoundingBoxes(rabbit, aabb).isEmpty() && !this.worldObj.isAnyLiquid(aabb)) {
                    this.worldObj.spawnEntityInWorld(rabbit);
                }
            }
            if(this.targetingRabbit == null && this.rand.nextInt(20) == 0 && !(rabbits = this.worldObj.getEntitiesWithinAABB(LOTREntityRabbit.class, this.boundingBox.expand(24.0, 24.0, 24.0))).isEmpty()) {
                LOTREntityRabbit rabbit = (LOTREntityRabbit) rabbits.get(this.rand.nextInt(rabbits.size()));
                if(rabbit.ridingEntity == null) {
                    this.targetingRabbit = rabbit;
                }
            }
            if(this.targetingRabbit != null) {
                if(!this.targetingRabbit.isEntityAlive()) {
                    this.targetingRabbit = null;
                }
                else {
                    this.getNavigator().tryMoveToEntityLiving(this.targetingRabbit, 1.0);
                    if(this.getDistanceToEntity(this.targetingRabbit) < 1.0) {
                        Entity entityToMount = this;
                        while(entityToMount.riddenByEntity != null) {
                            entityToMount = entityToMount.riddenByEntity;
                        }
                        this.targetingRabbit.mountEntity(entityToMount);
                        this.targetingRabbit = null;
                    }
                }
            }
        }
    }

    @Override
    protected String getLivingSound() {
        return "lotr:orc.say";
    }

    @Override
    public int getTalkInterval() {
        return 10;
    }

    @Override
    protected void dropFewItems(boolean flag, int i) {
        super.dropFewItems(flag, i);
        int j = this.rand.nextInt(2) + this.rand.nextInt(i + 1);
        for(int k = 0; k < j; ++k) {
            this.dropItem(Items.bone, 1);
        }
    }
}
