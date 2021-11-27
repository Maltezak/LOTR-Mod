package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityMoredainHuntsman extends LOTREntityMoredainVillageTrader {
    public LOTREntityMoredainHuntsman(World world) {
        super(world);
    }

    @Override
    public LOTRTradeEntries getBuyPool() {
        return LOTRTradeEntries.MOREDAIN_HUNTSMAN_BUY;
    }

    @Override
    public LOTRTradeEntries getSellPool() {
        return LOTRTradeEntries.MOREDAIN_HUNTSMAN_SELL;
    }

    @Override
    public void setupNPCGender() {
        this.familyInfo.setMale(true);
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearMoredain));
        this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
        this.npcItemsInv.setSpearBackup(null);
        this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsMoredain));
        this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsMoredain));
        this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyMoredain));
        return data;
    }
}
