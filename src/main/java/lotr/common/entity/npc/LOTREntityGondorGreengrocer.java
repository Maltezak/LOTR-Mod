package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityGondorGreengrocer extends LOTREntityGondorMarketTrader {
    public LOTREntityGondorGreengrocer(World world) {
        super(world);
    }

    @Override
    public LOTRTradeEntries getBuyPool() {
        return LOTRTradeEntries.GONDOR_GREENGROCER_BUY;
    }

    @Override
    public LOTRTradeEntries getSellPool() {
        return LOTRTradeEntries.GONDOR_GREENGROCER_SELL;
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        int i = this.rand.nextInt(3);
        if(i == 0) {
            this.npcItemsInv.setIdleItem(new ItemStack(Items.apple));
        }
        else if(i == 1) {
            this.npcItemsInv.setIdleItem(new ItemStack(LOTRMod.appleGreen));
        }
        else if(i == 2) {
            this.npcItemsInv.setIdleItem(new ItemStack(LOTRMod.pear));
        }
        return data;
    }
}
