package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import lotr.common.entity.ai.*;
import lotr.common.fac.LOTRFaction;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class LOTREntitySauron extends LOTREntityNPC {
    public LOTREntitySauron(World world) {
        super(world);
        this.setSize(0.8f, 2.2f);
        this.isImmuneToFire = true;
        this.getNavigator().setAvoidsWater(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new LOTREntityAISauronUseMace(this));
        this.tasks.addTask(2, new LOTREntityAIAttackOnCollide(this, 2.0, false));
        this.tasks.addTask(3, new EntityAIWander(this, 1.0));
        this.tasks.addTask(4, new EntityAIWatchClosest(this, EntityPlayer.class, 10.0f, 0.02f));
        this.tasks.addTask(5, new EntityAILookIdle(this));
        this.addTargetTasks(true);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(17, (byte) 0);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(500.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.18);
        this.getEntityAttribute(npcAttackDamage).setBaseValue(8.0);
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        this.setCurrentItemOrArmor(0, new ItemStack(LOTRMod.sauronMace));
        return data;
    }

    @Override
    public LOTRFaction getFaction() {
        return LOTRFaction.MORDOR;
    }

    @Override
    public int getTotalArmorValue() {
        return 20;
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if(!this.worldObj.isRemote && this.getHealth() < this.getMaxHealth() && this.ticksExisted % 10 == 0) {
            this.heal(2.0f);
        }
        if(this.getIsUsingMace() && this.worldObj.isRemote) {
            for(int i = 0; i < 6; ++i) {
                double d = this.posX - 2.0 + this.rand.nextFloat() * 4.0f;
                double d1 = this.posY + this.rand.nextFloat() * 3.0f;
                double d2 = this.posZ - 2.0 + this.rand.nextFloat() * 4.0f;
                double d3 = (this.posX - d) / 8.0;
                double d4 = (this.posY + 0.5 - d1) / 8.0;
                double d5 = (this.posZ - d2) / 8.0;
                double d6 = Math.sqrt(d3 * d3 + d4 * d4 + d5 * d5);
                double d7 = 1.0 - d6;
                double d8 = 0.0;
                double d9 = 0.0;
                double d10 = 0.0;
                if(d7 > 0.0) {
                    d7 *= d7;
                    d8 += d3 / d6 * d7 * 0.2;
                    d9 += d4 / d6 * d7 * 0.2;
                    d10 += d5 / d6 * d7 * 0.2;
                }
                this.worldObj.spawnParticle("smoke", d, d1, d2, d8, d9, d10);
            }
        }
    }

    @Override
    protected void fall(float f) {
    }

    @Override
    public void addPotionEffect(PotionEffect effect) {
    }

    @Override
    protected int decreaseAirSupply(int i) {
        return i;
    }

    @Override
    public void onDeath(DamageSource damagesource) {
        super.onDeath(damagesource);
        if(!this.worldObj.isRemote) {
            this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 3.0f, false);
            this.setDead();
        }
    }

    public boolean getIsUsingMace() {
        return this.dataWatcher.getWatchableObjectByte(17) == 1;
    }

    public void setIsUsingMace(boolean flag) {
        this.dataWatcher.updateObject(17, flag ? (byte) 1 : 0);
    }
}
