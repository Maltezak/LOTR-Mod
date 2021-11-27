package lotr.common.entity.animal;

import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntities;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class LOTREntityFlamingo extends EntityAnimal {
    public boolean field_753_a = false;
    public float field_752_b;
    public float destPos;
    public float field_757_d;
    public float field_756_e;
    public float field_755_h = 5.0f;
    public static final int NECK_TIME = 20;
    public static final int FISHING_TIME = 160;
    public static final int FISHING_TIME_TOTAL = 200;

    public LOTREntityFlamingo(World world) {
        super(world);
        this.setSize(0.6f, 1.8f);
        this.getNavigator().setAvoidsWater(false);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIPanic(this, 1.3));
        this.tasks.addTask(2, new EntityAIMate(this, 1.0));
        this.tasks.addTask(3, new EntityAITempt(this, 1.2, Items.fish, false));
        this.tasks.addTask(4, new EntityAIFollowParent(this, 1.2));
        this.tasks.addTask(5, new EntityAIWander(this, 1.0));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0f));
        this.tasks.addTask(7, new EntityAILookIdle(this));
    }

    @Override
    public void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, 0);
    }

    private int getFishingTick() {
        int i = this.dataWatcher.getWatchableObjectInt(16);
        return i;
    }

    public int getFishingTickPre() {
        return this.getFishingTick() >> 16;
    }

    public int getFishingTickCur() {
        return this.getFishingTick() & 0xFFFF;
    }

    public void setFishingTick(int pre, int cur) {
        int i = pre << 16 | cur & 0xFFFF;
        this.dataWatcher.updateObject(16, i);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(8.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25);
    }

    @Override
    public boolean isAIEnabled() {
        return true;
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        this.field_756_e = this.field_752_b;
        this.field_757_d = this.destPos;
        this.destPos = (float) (this.destPos + (this.onGround || this.inWater ? -1 : 4) * 0.3);
        if(this.destPos < 0.0f) {
            this.destPos = 0.0f;
        }
        if(this.destPos > 1.0f) {
            this.destPos = 1.0f;
        }
        if(!this.onGround && !this.inWater && this.field_755_h < 1.0f) {
            this.field_755_h = 1.0f;
        }
        this.field_755_h = (float) (this.field_755_h * 0.9);
        if(!this.onGround && !this.inWater && this.motionY < 0.0) {
            this.motionY *= 0.6;
        }
        this.field_752_b += this.field_755_h * 2.0f;
        if((!this.worldObj.isRemote && !this.isChild() && !this.isInLove() && (this.getFishingTickCur() == 0) && (this.rand.nextInt(600) == 0) && ((this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.boundingBox.minY), MathHelper.floor_double(this.posZ))) == Blocks.water))) {
            this.setFishingTick(200, 200);
        }
        if(this.getFishingTickCur() > 0) {
            if(!this.worldObj.isRemote) {
                int cur = this.getFishingTickCur();
                this.setFishingTick(cur, cur - 1);
            }
            else {
                for(int i = 0; i < 3; ++i) {
                    double d = this.posX + MathHelper.getRandomDoubleInRange(this.rand, -0.3, 0.3);
                    double d1 = this.boundingBox.minY + MathHelper.getRandomDoubleInRange(this.rand, -0.3, 0.3);
                    double d2 = this.posZ + MathHelper.getRandomDoubleInRange(this.rand, -0.3, 0.3);
                    this.worldObj.spawnParticle("bubble", d, d1, d2, 0.0, 0.0, 0.0);
                }
            }
        }
        if(!this.worldObj.isRemote && this.isInLove() && this.getFishingTickCur() > 20) {
            this.setFishingTick(20, 20);
        }
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float f) {
        boolean flag = super.attackEntityFrom(source, f);
        if(flag && !this.worldObj.isRemote && this.getFishingTickCur() > 20) {
            this.setFishingTick(20, 20);
        }
        return flag;
    }

    @Override
    protected void fall(float f) {
    }

    @Override
    protected String getLivingSound() {
        return "lotr:flamingo.say";
    }

    @Override
    protected String getHurtSound() {
        return "lotr:flamingo.hurt";
    }

    @Override
    protected String getDeathSound() {
        return "lotr:flamingo.death";
    }

    @Override
    protected Item getDropItem() {
        return Items.feather;
    }

    @Override
    public boolean isBreedingItem(ItemStack itemstack) {
        return itemstack.getItem() == Items.fish;
    }

    @Override
    public EntityAgeable createChild(EntityAgeable entity) {
        return new LOTREntityFlamingo(this.worldObj);
    }

    @Override
    public ItemStack getPickedResult(MovingObjectPosition target) {
        return new ItemStack(LOTRMod.spawnEgg, 1, LOTREntities.getEntityID(this));
    }
}
