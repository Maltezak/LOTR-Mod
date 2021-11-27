package lotr.common.entity.npc;

import lotr.common.*;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.entity.animal.LOTREntityHorse;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityDaleSoldier extends LOTREntityDaleLevyman {
    public LOTREntityDaleSoldier(World world) {
        super(world);
        this.npcShield = LOTRShields.ALIGNMENT_DALE;
        this.spawnRidingHorse = this.rand.nextInt(8) == 0;
    }

    @Override
    protected EntityAIBase createDaleAttackAI() {
        return new LOTREntityAIAttackOnCollide(this, 1.5, true);
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        int i = this.rand.nextInt(5);
        if(i == 0 || i == 1 || i == 2) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.swordDale));
        }
        else if(i == 3) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.battleaxeDale));
        }
        else if(i == 4) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.pikeDale));
        }
        if(this.rand.nextInt(6) == 0) {
            this.npcItemsInv.setSpearBackup(this.npcItemsInv.getMeleeWeapon());
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearDale));
        }
        this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
        this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsDale));
        this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsDale));
        this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyDale));
        if(this.rand.nextInt(10) != 0) {
            this.setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetDale));
        }
        else {
            this.setCurrentItemOrArmor(4, null);
        }
        return data;
    }

    @Override
    public LOTRNPCMount createMountToRide() {
        LOTREntityHorse horse = (LOTREntityHorse) super.createMountToRide();
        horse.setMountArmor(new ItemStack(LOTRMod.horseArmorDale));
        return horse;
    }

    @Override
    public float getAlignmentBonus() {
        return 2.0f;
    }
}
