package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityRohanBarrowWraith extends LOTREntitySkeletalWraith {
    public LOTREntityRohanBarrowWraith(World world) {
        super(world);
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.swordRohan));
        this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
        this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsRohanMarshal));
        this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsRohanMarshal));
        this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyRohanMarshal));
        return data;
    }
}
