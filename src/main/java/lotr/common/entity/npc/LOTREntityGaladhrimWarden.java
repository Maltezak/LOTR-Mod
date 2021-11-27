package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import lotr.common.entity.ai.LOTREntityAIRangedAttack;
import net.minecraft.block.Block;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class LOTREntityGaladhrimWarden extends LOTREntityGaladhrimElf {
    private int sneakCooldown = 0;
    private EntityLivingBase prevElfTarget;

    public LOTREntityGaladhrimWarden(World world) {
        super(world);
        this.tasks.addTask(2, this.rangedAttackAI);
    }

    @Override
    protected EntityAIBase createElfRangedAttackAI() {
        return new LOTREntityAIRangedAttack(this, 1.25, 25, 35, 24.0f);
    }

    @Override
    public void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(17, (byte) 0);
    }

    public boolean isElfSneaking() {
        return this.dataWatcher.getWatchableObjectByte(17) == 1;
    }

    public void setElfSneaking(boolean flag) {
        this.dataWatcher.updateObject(17, flag ? (byte) 1 : 0);
        if(flag) {
            this.sneakCooldown = 20;
        }
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(24.0);
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerElven));
        this.npcItemsInv.setRangedWeapon(new ItemStack(LOTRMod.mallornBow));
        this.npcItemsInv.setIdleItem(this.npcItemsInv.getRangedWeapon());
        this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsHithlain));
        this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsHithlain));
        this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyHithlain));
        if(this.rand.nextInt(10) != 0) {
            this.setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetHithlain));
        }
        return data;
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if(!this.worldObj.isRemote) {
            if(this.isElfSneaking()) {
                if(this.getAttackTarget() == null) {
                    if(this.sneakCooldown > 0) {
                        --this.sneakCooldown;
                    }
                    else {
                        this.setElfSneaking(false);
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
    }

    @Override
    public void setAttackTarget(EntityLivingBase target, boolean speak) {
        super.setAttackTarget(target, speak);
        if(target != null && target != this.prevElfTarget) {
            this.prevElfTarget = target;
            if(!this.worldObj.isRemote && !this.isElfSneaking()) {
                this.setElfSneaking(true);
            }
        }
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float f) {
        boolean flag = super.attackEntityFrom(damagesource, f);
        if(flag && !this.worldObj.isRemote && this.isElfSneaking()) {
            this.setElfSneaking(false);
        }
        return flag;
    }

    @Override
    public void swingItem() {
        super.swingItem();
        if(!this.worldObj.isRemote && this.isElfSneaking()) {
            this.setElfSneaking(false);
        }
    }

    @Override
    public float getAlignmentBonus() {
        return 2.0f;
    }

    @Override
    public String getSpeechBank(EntityPlayer entityplayer) {
        if(this.isFriendly(entityplayer)) {
            if(this.hiredNPCInfo.getHiringPlayer() == entityplayer) {
                return "galadhrim/elf/hired";
            }
            return "galadhrim/warrior/friendly";
        }
        return "galadhrim/warrior/hostile";
    }

    @Override
    protected void func_145780_a(int i, int j, int k, Block block) {
        if(!this.isElfSneaking()) {
            super.func_145780_a(i, j, k, block);
        }
    }
}
