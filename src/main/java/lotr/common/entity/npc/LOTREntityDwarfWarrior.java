package lotr.common.entity.npc;

import lotr.common.*;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityDwarfWarrior extends LOTREntityDwarf {
    public LOTREntityDwarfWarrior(World world) {
        super(world);
        this.npcShield = LOTRShields.ALIGNMENT_DWARF;
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        int i = this.rand.nextInt(7);
        if(i == 0) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.swordDwarven));
        }
        else if(i == 1 || i == 2) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.battleaxeDwarven));
        }
        else if(i == 3 || i == 4) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.hammerDwarven));
        }
        else if(i == 5) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.mattockDwarven));
        }
        else if(i == 6) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.pikeDwarven));
        }
        if(this.rand.nextInt(6) == 0) {
            this.npcItemsInv.setSpearBackup(this.npcItemsInv.getMeleeWeapon());
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearDwarven));
        }
        this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
        this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsDwarven));
        this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsDwarven));
        this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyDwarven));
        if(this.rand.nextInt(10) != 0) {
            this.setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetDwarven));
        }
        return data;
    }

    @Override
    public float getAlignmentBonus() {
        return 2.0f;
    }
}
