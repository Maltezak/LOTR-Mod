package lotr.common.entity.npc;

import lotr.common.*;
import lotr.common.entity.ai.*;
import net.minecraft.block.Block;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public abstract class LOTREntityRanger extends LOTREntityDunedain {
    public EntityAIBase rangedAttackAI = this.createDunedainRangedAI();
    public EntityAIBase meleeAttackAI;
    private int sneakCooldown = 0;
    private EntityLivingBase prevRangerTarget;

    public LOTREntityRanger(World world) {
        super(world);
        this.addTargetTasks(true);
        this.npcCape = LOTRCapes.RANGER;
    }

    @Override
    protected EntityAIBase createDunedainAttackAI() {
        this.meleeAttackAI = new LOTREntityAIAttackOnCollide(this, 1.5, true);
        return this.meleeAttackAI;
    }

    protected EntityAIBase createDunedainRangedAI() {
        return new LOTREntityAIRangedAttack(this, 1.25, 20, 40, 20.0f);
    }

    @Override
    public void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(17, (byte) 0);
    }

    @Override
    public void setupNPCGender() {
        this.familyInfo.setMale(true);
    }

    public boolean isRangerSneaking() {
        return this.dataWatcher.getWatchableObjectByte(17) == 1;
    }

    public void setRangerSneaking(boolean flag) {
        this.dataWatcher.updateObject(17, flag ? (byte) 1 : 0);
        if(flag) {
            this.sneakCooldown = 20;
        }
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(25.0);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(24.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25);
        this.getEntityAttribute(npcRangedAccuracy).setBaseValue(0.5);
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerIron));
        this.npcItemsInv.setRangedWeapon(new ItemStack(Items.bow));
        this.npcItemsInv.setIdleItem(null);
        this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsRanger));
        this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsRanger));
        this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyRanger));
        this.setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetRanger));
        return data;
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if(!this.worldObj.isRemote) {
            if(this.ridingEntity == null) {
                if(this.isRangerSneaking()) {
                    if(this.getAttackTarget() == null) {
                        if(this.sneakCooldown > 0) {
                            --this.sneakCooldown;
                        }
                        else {
                            this.setRangerSneaking(false);
                        }
                    }
                    else {
                        this.sneakCooldown = 20;
                    }
                }
                else {
                    this.sneakCooldown = 0;
                }
            }
            else if(this.isRangerSneaking()) {
                this.setRangerSneaking(false);
            }
        }
    }

    @Override
    public void onAttackModeChange(LOTREntityNPC.AttackMode mode, boolean mounted) {
        if(mode == LOTREntityNPC.AttackMode.IDLE) {
            this.tasks.removeTask(this.meleeAttackAI);
            this.tasks.removeTask(this.rangedAttackAI);
            this.setCurrentItemOrArmor(0, this.npcItemsInv.getIdleItem());
        }
        if(mode == LOTREntityNPC.AttackMode.MELEE) {
            this.tasks.removeTask(this.meleeAttackAI);
            this.tasks.removeTask(this.rangedAttackAI);
            this.tasks.addTask(2, this.meleeAttackAI);
            this.setCurrentItemOrArmor(0, this.npcItemsInv.getMeleeWeapon());
        }
        if(mode == LOTREntityNPC.AttackMode.RANGED) {
            this.tasks.removeTask(this.meleeAttackAI);
            this.tasks.removeTask(this.rangedAttackAI);
            this.tasks.addTask(2, this.rangedAttackAI);
            this.setCurrentItemOrArmor(0, this.npcItemsInv.getRangedWeapon());
        }
    }

    @Override
    public void setAttackTarget(EntityLivingBase target, boolean speak) {
        super.setAttackTarget(target, speak);
        if (target != null && target != this.prevRangerTarget) {
            this.prevRangerTarget = target;
            if (!this.worldObj.isRemote && !this.isRangerSneaking() && this.ridingEntity == null) {
                this.setRangerSneaking(true);
            }
        }
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float f) {
        boolean flag = super.attackEntityFrom(damagesource, f);
        if(flag && !this.worldObj.isRemote && this.isRangerSneaking()) {
            this.setRangerSneaking(false);
        }
        return flag;
    }

    @Override
    public void swingItem() {
        super.swingItem();
        if(!this.worldObj.isRemote && this.isRangerSneaking()) {
            this.setRangerSneaking(false);
        }
    }

    @Override
    protected void dropFewItems(boolean flag, int i) {
        super.dropFewItems(flag, i);
        this.dropNPCArrows(i);
    }

    @Override
    protected void func_145780_a(int i, int j, int k, Block block) {
        if(!this.isRangerSneaking()) {
            super.func_145780_a(i, j, k, block);
        }
    }
}
