package lotr.common.entity.npc;

import lotr.common.*;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityRohirrimWarrior extends LOTREntityRohanMan {
    public LOTREntityRohirrimWarrior(World world) {
        super(world);
        this.addTargetTasks(true);
        this.spawnRidingHorse = this.rand.nextInt(3) == 0;
        this.npcShield = LOTRShields.ALIGNMENT_ROHAN;
    }

    @Override
    public EntityAIBase createRohanAttackAI() {
        return new LOTREntityAIAttackOnCollide(this, 1.45, false);
    }

    @Override
    public void setupNPCGender() {
        this.familyInfo.setMale(true);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.2);
        this.getEntityAttribute(npcRangedAccuracy).setBaseValue(0.75);
        this.getEntityAttribute(horseAttackSpeed).setBaseValue(2.0);
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        if(this.rand.nextInt(3) == 0) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.battleaxeRohan));
        }
        else {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.swordRohan));
        }
        if(this.rand.nextInt(4) == 0) {
            this.npcItemsInv.setMeleeWeaponMounted(new ItemStack(LOTRMod.lanceRohan));
        }
        else {
            this.npcItemsInv.setMeleeWeaponMounted(this.npcItemsInv.getMeleeWeapon());
        }
        if(this.rand.nextInt(4) == 0) {
            this.npcItemsInv.setSpearBackup(this.npcItemsInv.getMeleeWeapon());
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearRohan));
        }
        this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
        this.npcItemsInv.setIdleItemMounted(this.npcItemsInv.getMeleeWeaponMounted());
        this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsRohan));
        this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsRohan));
        this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyRohan));
        this.setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetRohan));
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
    public float getAlignmentBonus() {
        return 2.0f;
    }

    @Override
    public String getSpeechBank(EntityPlayer entityplayer) {
        if(this.isFriendly(entityplayer)) {
            if(this.hiredNPCInfo.getHiringPlayer() == entityplayer) {
                return "rohan/warrior/hired";
            }
            return "rohan/warrior/friendly";
        }
        return "rohan/warrior/hostile";
    }
}
