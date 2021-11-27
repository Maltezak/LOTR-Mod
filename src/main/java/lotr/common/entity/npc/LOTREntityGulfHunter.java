package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityGulfHunter extends LOTREntityGulfTrader {
    public LOTREntityGulfHunter(World world) {
        super(world);
    }

    @Override
    public LOTRTradeEntries getBuyPool() {
        return LOTRTradeEntries.GULF_HUNTER_BUY;
    }

    @Override
    public LOTRTradeEntries getSellPool() {
        return LOTRTradeEntries.GULF_HUNTER_SELL;
    }

    @Override
    public void setupNPCGender() {
        this.familyInfo.setMale(true);
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearHarad));
        this.npcItemsInv.setIdleItem(new ItemStack(LOTRMod.lionFur));
        this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsGemsbok));
        this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsGemsbok));
        this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyGemsbok));
        return data;
    }
}
