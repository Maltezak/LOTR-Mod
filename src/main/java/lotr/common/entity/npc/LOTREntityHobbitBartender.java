package lotr.common.entity.npc;

import lotr.common.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.world.World;

public class LOTREntityHobbitBartender extends LOTREntityHobbit implements LOTRTradeable.Bartender {
    public LOTREntityHobbitBartender(World world) {
        super(world);
        this.npcLocationName = "entity.lotr.HobbitBartender.locationName";
    }

    @Override
    public LOTRTradeEntries getBuyPool() {
        return LOTRTradeEntries.HOBBIT_BARTENDER_BUY;
    }

    @Override
    public LOTRTradeEntries getSellPool() {
        return LOTRTradeEntries.HOBBIT_BARTENDER_SELL;
    }

    @Override
    protected void dropHobbitItems(boolean flag, int i) {
        int count = this.rand.nextInt(3) + this.rand.nextInt(i + 1);
        block9: for(int k = 0; k < count; ++k) {
            int j = this.rand.nextInt(10);
            switch(j) {
                case 0:
                case 1: {
                    this.entityDropItem(LOTRFoods.HOBBIT.getRandomFood(this.rand), 0.0f);
                    continue block9;
                }
                case 2: {
                    this.entityDropItem(new ItemStack(Items.gold_nugget, 2 + this.rand.nextInt(3)), 0.0f);
                    continue block9;
                }
                case 3: {
                    this.entityDropItem(new ItemStack(Items.bowl, 1 + this.rand.nextInt(4)), 0.0f);
                    continue block9;
                }
                case 4: {
                    this.entityDropItem(new ItemStack(LOTRMod.hobbitPipe, 1, this.rand.nextInt(100)), 0.0f);
                    continue block9;
                }
                case 5: {
                    this.entityDropItem(new ItemStack(LOTRMod.pipeweed, 1 + this.rand.nextInt(2)), 0.0f);
                    continue block9;
                }
                case 6:
                case 7:
                case 8: {
                    this.entityDropItem(new ItemStack(LOTRMod.mug), 0.0f);
                    continue block9;
                }
                case 9: {
                    Item drink = LOTRFoods.HOBBIT_DRINK.getRandomFood(this.rand).getItem();
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
        return this.isFriendly(entityplayer);
    }

    @Override
    public void onPlayerTrade(EntityPlayer entityplayer, LOTRTradeEntries.TradeType type, ItemStack itemstack) {
        LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.tradeBartender);
        if(type == LOTRTradeEntries.TradeType.SELL && itemstack.getItem() == LOTRMod.pipeweedLeaf) {
            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.sellPipeweedLeaf);
        }
    }

    @Override
    public boolean shouldTraderRespawn() {
        return true;
    }

    @Override
    public String getSpeechBank(EntityPlayer entityplayer) {
        if(this.isFriendly(entityplayer)) {
            return "hobbit/bartender/friendly";
        }
        return "hobbit/bartender/hostile";
    }
}
