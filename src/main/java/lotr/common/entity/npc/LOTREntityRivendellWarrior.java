package lotr.common.entity.npc;

import lotr.common.*;
import lotr.common.entity.ai.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityRivendellWarrior extends LOTREntityRivendellElf {
    public LOTREntityRivendellWarrior(World world) {
        super(world);
        this.tasks.addTask(2, this.meleeAttackAI);
        this.spawnRidingHorse = this.rand.nextInt(4) == 0;
        this.npcShield = LOTRShields.ALIGNMENT_RIVENDELL;
    }

    @Override
    protected EntityAIBase createElfRangedAttackAI() {
        return new LOTREntityAIRangedAttack(this, 1.25, 25, 40, 24.0f);
    }

    @Override
    protected EntityAIBase createElfMeleeAttackAI() {
        return new LOTREntityAIAttackOnCollide(this, 1.5, false);
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
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.polearmRivendell));
        }
        else if(i == 1) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.longspearRivendell));
        }
        else {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.swordRivendell));
        }
        this.npcItemsInv.setRangedWeapon(new ItemStack(LOTRMod.rivendellBow));
        if(this.rand.nextInt(5) == 0) {
            this.npcItemsInv.setSpearBackup(this.npcItemsInv.getMeleeWeapon());
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearRivendell));
        }
        this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
        this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsRivendell));
        this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsRivendell));
        this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyRivendell));
        if(this.rand.nextInt(10) != 0) {
            this.setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetRivendell));
        }
        return data;
    }

    @Override
    public float getAlignmentBonus() {
        return 2.0f;
    }

    @Override
    public String getSpeechBank(EntityPlayer entityplayer) {
        if(this.isFriendly(entityplayer)) {
            if(this.hiredNPCInfo.getHiringPlayer() == entityplayer) {
                return "rivendell/elf/hired";
            }
            return "rivendell/warrior/friendly";
        }
        return "rivendell/warrior/hostile";
    }
}
