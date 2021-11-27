package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import lotr.common.item.LOTRItemHaradRobes;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityEasterlingLumberman extends LOTREntityEasterlingMarketTrader {
    public LOTREntityEasterlingLumberman(World world) {
        super(world);
    }

    @Override
    public LOTRTradeEntries getBuyPool() {
        return LOTRTradeEntries.RHUN_LUMBERMAN_BUY;
    }

    @Override
    public LOTRTradeEntries getSellPool() {
        return LOTRTradeEntries.RHUN_LUMBERMAN_SELL;
    }

    @Override
    public void setupNPCGender() {
        this.familyInfo.setMale(true);
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        this.npcItemsInv.setMeleeWeapon(new ItemStack(Items.iron_axe));
        this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
        int robeColor = 8538153;
        ItemStack body = new ItemStack(LOTRMod.bodyKaftan);
        ItemStack legs = new ItemStack(LOTRMod.legsKaftan);
        LOTRItemHaradRobes.setRobesColor(body, robeColor);
        LOTRItemHaradRobes.setRobesColor(legs, robeColor);
        this.setCurrentItemOrArmor(3, body);
        this.setCurrentItemOrArmor(2, legs);
        return data;
    }
}
