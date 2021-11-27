package lotr.common.entity.npc;

import lotr.common.*;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityBlueDwarfWarrior extends LOTREntityBlueDwarf {
    public LOTREntityBlueDwarfWarrior(World world) {
        super(world);
        this.npcShield = LOTRShields.ALIGNMENT_BLUE_MOUNTAINS;
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        int i = this.rand.nextInt(7);
        if(i == 0) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.swordBlueDwarven));
        }
        else if(i == 1 || i == 2) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.battleaxeBlueDwarven));
        }
        else if(i == 3 || i == 4) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.hammerBlueDwarven));
        }
        else if(i == 5) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.mattockBlueDwarven));
        }
        else if(i == 6) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.pikeBlueDwarven));
        }
        if(this.rand.nextInt(6) == 0) {
            this.npcItemsInv.setSpearBackup(this.npcItemsInv.getMeleeWeapon());
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearBlueDwarven));
        }
        this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
        this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsBlueDwarven));
        this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsBlueDwarven));
        this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyBlueDwarven));
        if(this.rand.nextInt(10) != 0) {
            this.setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetBlueDwarven));
        }
        return data;
    }

    @Override
    public float getAlignmentBonus() {
        return 2.0f;
    }
}
