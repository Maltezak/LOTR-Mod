package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityRohanOrcharder extends LOTREntityRohanMarketTrader {
    public LOTREntityRohanOrcharder(World world) {
        super(world);
    }

    @Override
    public LOTRTradeEntries getBuyPool() {
        return LOTRTradeEntries.ROHAN_ORCHARDER_BUY;
    }

    @Override
    public LOTRTradeEntries getSellPool() {
        return LOTRTradeEntries.ROHAN_ORCHARDER_SELL;
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        if(this.rand.nextBoolean()) {
            this.npcItemsInv.setIdleItem(new ItemStack(Items.apple));
        }
        else {
            this.npcItemsInv.setIdleItem(new ItemStack(LOTRMod.appleGreen));
        }
        return data;
    }
}
