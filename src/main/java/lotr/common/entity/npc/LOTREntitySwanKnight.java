package lotr.common.entity.npc;

import lotr.common.*;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.entity.animal.LOTREntityHorse;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntitySwanKnight extends LOTREntityDolAmrothSoldier {
    public LOTREntitySwanKnight(World world) {
        super(world);
        this.addTargetTasks(true);
        this.spawnRidingHorse = this.rand.nextInt(4) == 0;
        this.npcShield = LOTRShields.ALIGNMENT_DOL_AMROTH;
    }

    @Override
    public EntityAIBase createGondorAttackAI() {
        return new LOTREntityAIAttackOnCollide(this, 1.5, false);
    }

    @Override
    public LOTRNPCMount createMountToRide() {
        LOTREntityHorse horse = (LOTREntityHorse) super.createMountToRide();
        horse.setMountArmor(new ItemStack(LOTRMod.horseArmorDolAmroth));
        return horse;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30.0);
        this.getEntityAttribute(horseAttackSpeed).setBaseValue(2.0);
        this.getEntityAttribute(npcRangedAccuracy).setBaseValue(0.75);
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        if(this.rand.nextInt(4) == 0) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.longspearDolAmroth));
        }
        else {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.swordDolAmroth));
        }
        if(this.rand.nextInt(3) == 0) {
            this.npcItemsInv.setMeleeWeaponMounted(new ItemStack(LOTRMod.lanceDolAmroth));
        }
        else {
            this.npcItemsInv.setMeleeWeaponMounted(this.npcItemsInv.getMeleeWeapon());
        }
        this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
        this.npcItemsInv.setIdleItemMounted(this.npcItemsInv.getMeleeWeaponMounted());
        this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsDolAmroth));
        this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsDolAmroth));
        this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyDolAmroth));
        this.setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetDolAmroth));
        return data;
    }

    @Override
    protected void onAttackModeChange(LOTREntityNPC.AttackMode mode, boolean mounted) {
        if(mode == LOTREntityNPC.AttackMode.IDLE) {
            if(mounted) {
                this.setCurrentItemOrArmor(0, this.npcItemsInv.getIdleItemMounted());
            }
            else {
                this.setCurrentItemOrArmor(0, this.npcItemsInv.getIdleItem());
            }
        }
        else if(mounted) {
            this.setCurrentItemOrArmor(0, this.npcItemsInv.getMeleeWeaponMounted());
        }
        else {
            this.setCurrentItemOrArmor(0, this.npcItemsInv.getMeleeWeapon());
        }
    }

    @Override
    protected LOTRAchievement getKillAchievement() {
        return LOTRAchievement.killSwanKnight;
    }

    @Override
    public float getAlignmentBonus() {
        return 2.0f;
    }

    @Override
    public String getSpeechBank(EntityPlayer entityplayer) {
        if(this.isFriendly(entityplayer)) {
            if(this.hiredNPCInfo.getHiringPlayer() == entityplayer) {
                return "gondor/swanKnight/hired";
            }
            return "gondor/swanKnight/friendly";
        }
        return "gondor/swanKnight/hostile";
    }
}
