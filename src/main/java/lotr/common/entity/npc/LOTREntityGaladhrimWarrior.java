package lotr.common.entity.npc;

import lotr.common.*;
import lotr.common.entity.ai.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class LOTREntityGaladhrimWarrior extends LOTREntityGaladhrimElf {
    public boolean isDefendingTree;

    public LOTREntityGaladhrimWarrior(World world) {
        super(world);
        this.tasks.addTask(2, this.meleeAttackAI);
        this.spawnRidingHorse = this.rand.nextInt(4) == 0;
        this.npcShield = LOTRShields.ALIGNMENT_GALADHRIM;
    }

    @Override
    protected EntityAIBase createElfRangedAttackAI() {
        return new LOTREntityAIRangedAttack(this, 1.25, 30, 40, 24.0f);
    }

    @Override
    protected EntityAIBase createElfMeleeAttackAI() {
        return new LOTREntityAIAttackOnCollide(this, 1.4, false);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(24.0);
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        int i = this.rand.nextInt(6);
        if(i == 0) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.polearmElven));
        }
        else if(i == 1) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.longspearElven));
        }
        else {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.swordElven));
        }
        this.npcItemsInv.setRangedWeapon(new ItemStack(LOTRMod.elvenBow));
        if(this.rand.nextInt(5) == 0) {
            this.npcItemsInv.setSpearBackup(this.npcItemsInv.getMeleeWeapon());
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearElven));
        }
        this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
        this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsElven));
        this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsElven));
        this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyElven));
        if(this.rand.nextInt(10) != 0) {
            this.setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetElven));
        }
        return data;
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        nbt.setBoolean("DefendingTree", this.isDefendingTree);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        this.isDefendingTree = nbt.getBoolean("DefendingTree");
    }

    @Override
    public float getAlignmentBonus() {
        return 2.0f;
    }

    @Override
    public void onDeath(DamageSource damagesource) {
        super.onDeath(damagesource);
        if(!this.worldObj.isRemote && this.isDefendingTree && damagesource.getEntity() instanceof EntityPlayer) {
            EntityPlayer entityplayer = (EntityPlayer) damagesource.getEntity();
            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.takeMallornWood);
        }
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
}
