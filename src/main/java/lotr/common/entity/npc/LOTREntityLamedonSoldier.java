package lotr.common.entity.npc;

import lotr.common.*;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.entity.animal.LOTREntityHorse;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityLamedonSoldier extends LOTREntityGondorSoldier {
    public LOTREntityLamedonSoldier(World world) {
        super(world);
        this.spawnRidingHorse = this.rand.nextInt(6) == 0;
        this.npcShield = LOTRShields.ALIGNMENT_LAMEDON;
        this.npcCape = LOTRCapes.LAMEDON;
    }

    @Override
    public EntityAIBase createGondorAttackAI() {
        return new LOTREntityAIAttackOnCollide(this, 1.5, false);
    }

    @Override
    public LOTRNPCMount createMountToRide() {
        LOTREntityHorse horse = (LOTREntityHorse) super.createMountToRide();
        horse.setMountArmor(new ItemStack(LOTRMod.horseArmorLamedon));
        return horse;
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        int i = this.rand.nextInt(3);
        if(i == 0) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.swordGondor));
        }
        else if(i == 1) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.hammerGondor));
        }
        else if(i == 2) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.pikeGondor));
        }
        this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
        this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsLamedon));
        this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsLamedon));
        this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyLamedon));
        if(this.rand.nextInt(10) != 0) {
            this.setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetLamedon));
        }
        else {
            this.setCurrentItemOrArmor(4, null);
        }
        return data;
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
}
