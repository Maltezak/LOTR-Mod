package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import net.minecraft.entity.*;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityHaradPyramidWraith extends LOTREntitySkeletalWraith {
    public LOTREntityHaradPyramidWraith(World world) {
        super(world);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30.0);
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        if(this.rand.nextBoolean()) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerNearHaradPoisoned));
            this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
            this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsNearHarad));
            this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsNearHarad));
            this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyNearHarad));
        }
        else {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerHaradPoisoned));
            this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
            this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsGulfHarad));
            this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsGulfHarad));
            this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyGulfHarad));
        }
        return data;
    }
}
