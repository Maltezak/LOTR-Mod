package lotr.common.entity.animal;

import java.util.UUID;

import lotr.common.LOTRMod;
import lotr.common.entity.LOTRRandomSkinEntity;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class LOTREntityDeer extends LOTREntityAnimalMF implements LOTRRandomSkinEntity {
    public LOTREntityDeer(World world) {
        super(world);
        this.setSize(0.8f, 1.0f);
        this.getNavigator().setAvoidsWater(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIPanic(this, 1.8));
        this.tasks.addTask(2, new EntityAIMate(this, 1.0));
        this.tasks.addTask(3, new EntityAITempt(this, 1.2, Items.wheat, false));
        this.tasks.addTask(4, new EntityAIFollowParent(this, 1.4));
        this.tasks.addTask(5, new EntityAIWander(this, 1.4));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0f));
        this.tasks.addTask(7, new EntityAILookIdle(this));
    }

    @Override
    public Class getAnimalMFBaseClass() {
        return this.getClass();
    }

    @Override
    public void setUniqueID(UUID uuid) {
        this.entityUniqueID = uuid;
    }

    @Override
    public void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(20, (byte) 0);
        this.setMale(this.rand.nextBoolean());
    }

    @Override
    public boolean isMale() {
        return this.dataWatcher.getWatchableObjectByte(20) == 1;
    }

    public void setMale(boolean flag) {
        this.dataWatcher.updateObject(20, flag ? (byte) 1 : 0);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25);
    }

    @Override
    public boolean isAIEnabled() {
        return true;
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        nbt.setBoolean("DeerMale", this.isMale());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        this.setMale(nbt.getBoolean("DeerMale"));
    }

    @Override
    public boolean isBreedingItem(ItemStack itemstack) {
        return itemstack.getItem() == Items.wheat;
    }

    @Override
    public EntityAgeable createChild(EntityAgeable entity) {
        LOTREntityDeer deer = new LOTREntityDeer(this.worldObj);
        deer.setMale(this.rand.nextBoolean());
        return deer;
    }

    @Override
    protected void dropFewItems(boolean flag, int i) {
        int hide = this.rand.nextInt(3) + this.rand.nextInt(1 + i);
        for(int l = 0; l < hide; ++l) {
            this.dropItem(Items.leather, 1);
        }
        int meat = this.rand.nextInt(3) + this.rand.nextInt(1 + i);
        for(int l = 0; l < meat; ++l) {
            if(this.isBurning()) {
                this.dropItem(LOTRMod.deerCooked, 1);
                continue;
            }
            this.dropItem(LOTRMod.deerRaw, 1);
        }
    }

    @Override
    public int getTalkInterval() {
        return 300;
    }

    @Override
    protected float getSoundVolume() {
        return 0.5f;
    }

    @Override
    protected String getLivingSound() {
        return "lotr:deer.say";
    }

    @Override
    protected String getHurtSound() {
        return "lotr:deer.hurt";
    }

    @Override
    protected String getDeathSound() {
        return "lotr:deer.death";
    }
}
