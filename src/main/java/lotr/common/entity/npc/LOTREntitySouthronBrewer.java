package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import lotr.common.item.LOTRItemMug;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntitySouthronBrewer extends LOTREntitySouthronTrader {
    public LOTREntitySouthronBrewer(World world) {
        super(world);
    }

    @Override
    public LOTRTradeEntries getBuyPool() {
        return LOTRTradeEntries.HARAD_BREWER_BUY;
    }

    @Override
    public LOTRTradeEntries getSellPool() {
        return LOTRTradeEntries.HARAD_BREWER_SELL;
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        ItemStack drink = new ItemStack(LOTRMod.mugAraq);
        LOTRItemMug.setVessel(drink, this.getHaradrimDrinks().getRandomVessel(this.rand), true);
        this.npcItemsInv.setIdleItem(drink);
        return data;
    }
}
