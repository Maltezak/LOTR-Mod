package lotr.common.entity.npc;

import lotr.common.*;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityDwarfMiner extends LOTREntityDwarf implements LOTRTradeable {
    public LOTREntityDwarfMiner(World world) {
        super(world);
    }

    @Override
    public LOTRTradeEntries getBuyPool() {
        return LOTRTradeEntries.DWARF_MINER_BUY;
    }

    @Override
    public LOTRTradeEntries getSellPool() {
        return LOTRTradeEntries.DWARF_MINER_SELL;
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.pickaxeDwarven));
        this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
        return data;
    }

    @Override
    protected boolean canDwarfSpawnAboveGround() {
        return false;
    }

    @Override
    public float getAlignmentBonus() {
        return 2.0f;
    }

    @Override
    public boolean canTradeWith(EntityPlayer entityplayer) {
        return LOTRLevelData.getData(entityplayer).getAlignment(this.getFaction()) >= 100.0f && this.isFriendly(entityplayer);
    }

    @Override
    public void onPlayerTrade(EntityPlayer entityplayer, LOTRTradeEntries.TradeType type, ItemStack itemstack) {
        LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.tradeDwarfMiner);
    }

    @Override
    public boolean shouldTraderRespawn() {
        return false;
    }

    @Override
    protected void dropFewItems(boolean flag, int i) {
        super.dropFewItems(flag, i);
        if(flag) {
            if(this.rand.nextInt(4) == 0) {
                this.dropChestContents(LOTRChestContents.DWARVEN_MINE_CORRIDOR, 1, 2 + i);
            }
            if(this.rand.nextInt(15) == 0) {
                this.entityDropItem(new ItemStack(LOTRMod.mithrilNugget), 0.0f);
            }
        }
    }

    @Override
    public String getSpeechBank(EntityPlayer entityplayer) {
        if(this.isFriendly(entityplayer)) {
            if(this.canTradeWith(entityplayer)) {
                return "dwarf/miner/friendly";
            }
            return "dwarf/miner/neutral";
        }
        return "dwarf/dwarf/hostile";
    }
}
