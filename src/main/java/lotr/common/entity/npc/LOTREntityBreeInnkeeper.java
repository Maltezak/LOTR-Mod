package lotr.common.entity.npc;

import lotr.common.*;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.world.World;

public class LOTREntityBreeInnkeeper
extends LOTREntityBreeMan
implements LOTRTradeable.Bartender {
    public LOTREntityBreeInnkeeper(World world) {
        super(world);
        this.addTargetTasks(false);
        this.npcLocationName = "entity.lotr.BreeInnkeeper.locationName";
    }

    @Override
    public LOTRTradeEntries getBuyPool() {
        return LOTRTradeEntries.BREE_INNKEEPER_BUY;
    }

    @Override
    public LOTRTradeEntries getSellPool() {
        return LOTRTradeEntries.BREE_INNKEEPER_SELL;
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        this.npcItemsInv.setIdleItem(new ItemStack(LOTRMod.mug));
        return data;
    }

    @Override
    public void dropBreeItems(boolean flag, int i) {
        int j = this.rand.nextInt(3) + this.rand.nextInt(i + 1);
        block6: for (int k = 0; k < j; ++k) {
            int l = this.rand.nextInt(7);
            switch (l) {
                case 0: 
                case 1: 
                case 2: {
                    Item food = LOTRFoods.BREE.getRandomFood(this.rand).getItem();
                    this.entityDropItem(new ItemStack(food), 0.0f);
                    continue block6;
                }
                case 3: {
                    this.entityDropItem(new ItemStack(Items.gold_nugget, 2 + this.rand.nextInt(3)), 0.0f);
                    continue block6;
                }
                case 4: 
                case 5: {
                    this.entityDropItem(new ItemStack(LOTRMod.mug), 0.0f);
                    continue block6;
                }
                case 6: {
                    Item drink = LOTRFoods.BREE.getRandomFood(this.rand).getItem();
                    this.entityDropItem(new ItemStack(drink, 1, 1 + this.rand.nextInt(3)), 0.0f);
                }
            }
        }
    }

    @Override
    public float getAlignmentBonus() {
        return 2.0f;
    }

    @Override
    public boolean canTradeWith(EntityPlayer entityplayer) {
        return this.isFriendlyAndAligned(entityplayer);
    }

    @Override
    public void onPlayerTrade(EntityPlayer entityplayer, LOTRTradeEntries.TradeType type, ItemStack itemstack) {
        LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.tradeBreeInnkeeper);
    }

    @Override
    public String getSpeechBank(EntityPlayer entityplayer) {
        if (this.isFriendlyAndAligned(entityplayer)) {
            return "bree/innkeeper/man/friendly";
        }
        return "bree/innkeeper/man/hostile";
    }
}

