package lotr.common.entity.npc;

import lotr.common.*;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.quest.*;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityGondorSoldier extends LOTREntityGondorLevyman {
    public LOTREntityGondorSoldier(World world) {
        super(world);
        this.spawnRidingHorse = this.rand.nextInt(6) == 0;
        this.npcShield = LOTRShields.ALIGNMENT_GONDOR;
    }

    @Override
    public EntityAIBase createGondorAttackAI() {
        return new LOTREntityAIAttackOnCollide(this, 1.45, false);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(npcRangedAccuracy).setBaseValue(0.75);
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        int i = this.rand.nextInt(6);
        if(i == 0 || i == 1 || i == 2 || i == 3) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.hammerGondor));
        }
        else if(i == 4) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.swordGondor));
        }
        else if(i == 5) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.pikeGondor));
        }
        if(this.rand.nextInt(3) == 0) {
            this.npcItemsInv.setMeleeWeaponMounted(new ItemStack(LOTRMod.lanceGondor));
        }
        else {
            this.npcItemsInv.setMeleeWeaponMounted(this.npcItemsInv.getMeleeWeapon());
        }
        if(this.rand.nextInt(5) == 0) {
            this.npcItemsInv.setSpearBackup(this.npcItemsInv.getMeleeWeapon());
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearGondor));
        }
        this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
        this.npcItemsInv.setIdleItemMounted(this.npcItemsInv.getMeleeWeaponMounted());
        this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsGondor));
        this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsGondor));
        this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyGondor));
        this.setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetGondor));
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
    public LOTRMiniQuest createMiniQuest() {
        if(this.rand.nextInt(8) == 0) {
            return LOTRMiniQuestFactory.GONDOR_KILL_RENEGADE.createQuest(this);
        }
        return super.createMiniQuest();
    }
}
