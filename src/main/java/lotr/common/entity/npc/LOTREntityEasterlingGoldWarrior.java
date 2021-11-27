package lotr.common.entity.npc;

import lotr.common.*;
import lotr.common.entity.animal.LOTREntityHorse;
import net.minecraft.entity.*;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityEasterlingGoldWarrior extends LOTREntityEasterlingWarrior {
    public LOTREntityEasterlingGoldWarrior(World world) {
        super(world);
        this.npcShield = LOTRShields.ALIGNMENT_RHUN;
    }

    @Override
    public LOTRNPCMount createMountToRide() {
        LOTREntityHorse horse = (LOTREntityHorse) super.createMountToRide();
        horse.setMountArmor(new ItemStack(LOTRMod.horseArmorRhunGold));
        return horse;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(25.0);
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsRhunGold));
        this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsRhunGold));
        this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyRhunGold));
        this.setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetRhunGold));
        return data;
    }
}
