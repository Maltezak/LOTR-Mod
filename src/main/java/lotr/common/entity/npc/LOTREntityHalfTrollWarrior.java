package lotr.common.entity.npc;

import lotr.common.*;
import lotr.common.entity.animal.LOTREntityRhino;
import net.minecraft.entity.*;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityHalfTrollWarrior extends LOTREntityHalfTroll {
    public LOTREntityHalfTrollWarrior(World world) {
        super(world);
        this.npcShield = LOTRShields.ALIGNMENT_HALF_TROLL;
        this.spawnRidingHorse = this.rand.nextInt(12) == 0;
    }

    @Override
    public LOTRNPCMount createMountToRide() {
        LOTREntityRhino rhino = new LOTREntityRhino(this.worldObj);
        if(this.rand.nextBoolean()) {
            rhino.setMountArmor(new ItemStack(LOTRMod.rhinoArmorHalfTroll));
        }
        return rhino;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.24);
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        int i = this.rand.nextInt(7);
        if(i == 0) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.battleaxeHalfTroll));
        }
        else if(i == 1) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.hammerHalfTroll));
        }
        else if(i == 2) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.maceHalfTroll));
        }
        else if(i == 3) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.scimitarHalfTroll));
        }
        else if(i == 4) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerHalfTroll));
        }
        else if(i == 5) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerHalfTrollPoisoned));
        }
        else if(i == 6) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.pikeHalfTroll));
        }
        this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
        this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsHalfTroll));
        this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsHalfTroll));
        this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyHalfTroll));
        if(this.rand.nextInt(4) != 0) {
            this.setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetHalfTroll));
        }
        return data;
    }

    @Override
    public float getAlignmentBonus() {
        return 2.0f;
    }
}
