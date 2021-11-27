package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityHarnedorHunter extends LOTREntityHarnedorTrader {
    public LOTREntityHarnedorHunter(World world) {
        super(world);
    }

    @Override
    public LOTRTradeEntries getBuyPool() {
        return LOTRTradeEntries.HARAD_HUNTER_BUY;
    }

    @Override
    public LOTRTradeEntries getSellPool() {
        return LOTRTradeEntries.HARAD_HUNTER_SELL;
    }

    @Override
    public void setupNPCGender() {
        this.familyInfo.setMale(true);
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearHarad));
        this.npcItemsInv.setIdleItem(new ItemStack(LOTRMod.fur));
        this.setCurrentItemOrArmor(1, new ItemStack(Items.leather_boots));
        this.setCurrentItemOrArmor(2, new ItemStack(Items.leather_leggings));
        this.setCurrentItemOrArmor(3, new ItemStack(Items.leather_chestplate));
        return data;
    }
}
