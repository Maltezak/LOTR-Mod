package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityEasterlingWarrior extends LOTREntityEasterlingLevyman {
    public LOTREntityEasterlingWarrior(World world) {
        super(world);
        this.addTargetTasks(true);
        this.spawnRidingHorse = this.rand.nextInt(6) == 0;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(npcRangedAccuracy).setBaseValue(0.75);
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        int i = this.rand.nextInt(10);
        if(i == 0 || i == 1 || i == 2) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.swordRhun));
        }
        else if(i == 3 || i == 4 || i == 5) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.battleaxeRhun));
        }
        else if(i == 6) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.polearmRhun));
        }
        else if(i == 7) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerRhun));
        }
        else if(i == 8) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerRhunPoisoned));
        }
        else if(i == 9) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.pikeRhun));
        }
        if(this.rand.nextInt(5) == 0) {
            this.npcItemsInv.setSpearBackup(this.npcItemsInv.getMeleeWeapon());
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearRhun));
        }
        this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
        this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsRhun));
        this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsRhun));
        this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyRhun));
        this.setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetRhun));
        return data;
    }
}
