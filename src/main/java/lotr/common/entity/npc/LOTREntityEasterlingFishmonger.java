package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import lotr.common.item.LOTRItemHaradRobes;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityEasterlingFishmonger extends LOTREntityEasterlingMarketTrader {
    public LOTREntityEasterlingFishmonger(World world) {
        super(world);
    }

    @Override
    public LOTRTradeEntries getBuyPool() {
        return LOTRTradeEntries.RHUN_FISHMONGER_BUY;
    }

    @Override
    public LOTRTradeEntries getSellPool() {
        return LOTRTradeEntries.RHUN_FISHMONGER_SELL;
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        this.npcItemsInv.setIdleItem(new ItemStack(Items.fishing_rod));
        int robeColor = 4882395;
        ItemStack body = new ItemStack(LOTRMod.bodyKaftan);
        ItemStack legs = new ItemStack(LOTRMod.legsKaftan);
        LOTRItemHaradRobes.setRobesColor(body, robeColor);
        LOTRItemHaradRobes.setRobesColor(legs, robeColor);
        this.setCurrentItemOrArmor(3, body);
        this.setCurrentItemOrArmor(2, legs);
        return data;
    }
}
