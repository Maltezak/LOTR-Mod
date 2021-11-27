package lotr.common.entity.npc;

import lotr.common.*;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityDunlendingWarrior extends LOTREntityDunlending {
    public LOTREntityDunlendingWarrior(World world) {
        super(world);
        this.npcShield = LOTRShields.ALIGNMENT_DUNLAND;
    }

    @Override
    public EntityAIBase getDunlendingAttackAI() {
        return new LOTREntityAIAttackOnCollide(this, 1.6, false);
    }

    @Override
    public void setupNPCGender() {
        this.familyInfo.setMale(true);
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        int i = this.rand.nextInt(7);
        if(i == 0) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(Items.iron_sword));
        }
        else if(i == 1) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.swordBronze));
        }
        else if(i == 2) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerIron));
        }
        else if(i == 3) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerBronze));
        }
        else if(i == 4) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.battleaxeIron));
        }
        else if(i == 5) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.battleaxeBronze));
        }
        else if(i == 6) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.pikeIron));
        }
        if(this.rand.nextInt(5) == 0) {
            this.npcItemsInv.setSpearBackup(this.npcItemsInv.getMeleeWeapon());
            if(this.rand.nextBoolean()) {
                this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearIron));
            }
            else {
                this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearBronze));
            }
        }
        this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
        this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsDunlending));
        this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsDunlending));
        this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyDunlending));
        if(this.rand.nextInt(10) != 0) {
            this.setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetDunlending));
        }
        return data;
    }

    @Override
    public float getAlignmentBonus() {
        return 2.0f;
    }
}
