package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityGondorRuinsWraith extends LOTREntitySkeletalWraith {
    public LOTREntityGondorRuinsWraith(World world) {
        super(world);
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.swordGondor));
        this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
        this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsGondor));
        this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsGondor));
        this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyGondor));
        return data;
    }
}
