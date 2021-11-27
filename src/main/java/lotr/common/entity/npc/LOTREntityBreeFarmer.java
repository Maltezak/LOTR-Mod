package lotr.common.entity.npc;

import lotr.common.*;
import lotr.common.item.LOTRItemLeatherHat;
import lotr.common.world.spawning.LOTRInvasions;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityBreeFarmer
extends LOTREntityBreeMan
implements LOTRTradeable,
LOTRUnitTradeable {
    public LOTREntityBreeFarmer(World world) {
        super(world);
        this.addTargetTasks(false);
    }

    @Override
    public LOTRTradeEntries getBuyPool() {
        return LOTRTradeEntries.BREE_FARMER_BUY;
    }

    @Override
    public LOTRTradeEntries getSellPool() {
        return LOTRTradeEntries.BREE_FARMER_SELL;
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        this.npcItemsInv.setMeleeWeapon(new ItemStack(Items.iron_hoe));
        this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
        ItemStack hat = new ItemStack(LOTRMod.leatherHat);
        LOTRItemLeatherHat.setHatColor(hat, 10390131);
        this.setCurrentItemOrArmor(4, hat);
        return data;
    }

    @Override
    public float getAlignmentBonus() {
        return 2.0f;
    }

    @Override
    public LOTRInvasions getWarhorn() {
        return null;
    }

    @Override
    public boolean canTradeWith(EntityPlayer entityplayer) {
        return LOTRLevelData.getData(entityplayer).getAlignment(this.getFaction()) >= 0.0f && this.isFriendlyAndAligned(entityplayer);
    }

    @Override
    public void onPlayerTrade(EntityPlayer entityplayer, LOTRTradeEntries.TradeType type, ItemStack itemstack) {
        if (type == LOTRTradeEntries.TradeType.BUY && (itemstack.getItem() == Items.apple || itemstack.getItem() == LOTRMod.appleGreen)) {
            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.buyAppleBreeFarmer);
        }
    }

    @Override
    public LOTRUnitTradeEntries getUnits() {
        return LOTRUnitTradeEntries.BREE_FARMER;
    }

    @Override
    public void onUnitTrade(EntityPlayer entityplayer) {
        LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.hireBreeFarmer);
    }

    @Override
    public String getSpeechBank(EntityPlayer entityplayer) {
        if (this.isFriendlyAndAligned(entityplayer)) {
            return "bree/farmer/friendly";
        }
        return "bree/farmer/hostile";
    }
}

