package lotr.common.entity.npc;

import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.fac.LOTRFaction;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public abstract class LOTREntitySkeletalWraith extends LOTREntityNPC {
    public LOTREntitySkeletalWraith(World world) {
        super(world);
        this.setSize(0.6f, 1.8f);
        this.isImmuneToFire = true;
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIRestrictSun(this));
        this.tasks.addTask(2, new EntityAIFleeSun(this, 1.0));
        this.tasks.addTask(3, new LOTREntityAIAttackOnCollide(this, 1.2, false));
        this.tasks.addTask(4, new EntityAIWander(this, 1.0));
        this.tasks.addTask(5, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0f, 0.02f));
        this.tasks.addTask(6, new EntityAILookIdle(this));
        this.addTargetTasks(true);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(24.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25);
    }

    @Override
    public LOTRFaction getFaction() {
        return LOTRFaction.HOSTILE;
    }

    @Override
    protected void onAttackModeChange(LOTREntityNPC.AttackMode mode, boolean mounted) {
        if(mode == LOTREntityNPC.AttackMode.IDLE) {
            this.setCurrentItemOrArmor(0, this.npcItemsInv.getIdleItem());
        }
        else {
            this.setCurrentItemOrArmor(0, this.npcItemsInv.getMeleeWeapon());
        }
    }

    @Override
    public void onLivingUpdate() {
        float f;
        if(this.worldObj.isDaytime() && !this.worldObj.isRemote && (f = this.getBrightness(1.0f)) > 0.5f && this.rand.nextFloat() * 30.0f < (f - 0.4f) * 2.0f && this.worldObj.canBlockSeeTheSky(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ))) {
            boolean flag = true;
            ItemStack itemstack = this.getEquipmentInSlot(4);
            if(itemstack != null) {
                if(itemstack.isItemStackDamageable()) {
                    itemstack.setItemDamage(itemstack.getItemDamageForDisplay() + this.rand.nextInt(2));
                    if(itemstack.getItemDamageForDisplay() >= itemstack.getMaxDamage()) {
                        this.renderBrokenItemStack(itemstack);
                        this.setCurrentItemOrArmor(4, null);
                    }
                }
                flag = false;
            }
            if(flag) {
                this.setFire(8);
            }
        }
        super.onLivingUpdate();
        if(this.rand.nextBoolean()) {
            this.worldObj.spawnParticle("smoke", this.posX + (this.rand.nextDouble() - 0.5) * this.width, this.posY + this.rand.nextDouble() * this.height, this.posZ + (this.rand.nextDouble() - 0.5) * this.width, 0.0, 0.0, 0.0);
        }
    }

    @Override
    protected void dropFewItems(boolean flag, int i) {
        super.dropFewItems(flag, i);
        int bones = this.rand.nextInt(3) + this.rand.nextInt(i + 1);
        for(int l = 0; l < bones; ++l) {
            this.dropItem(Items.bone, 1);
        }
    }

    @Override
    public boolean canDropRares() {
        return false;
    }

    @Override
    protected String getLivingSound() {
        return "mob.skeleton.say";
    }

    @Override
    protected String getHurtSound() {
        return "mob.skeleton.hurt";
    }

    @Override
    protected String getDeathSound() {
        return "mob.skeleton.death";
    }

    @Override
    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.UNDEAD;
    }
}
