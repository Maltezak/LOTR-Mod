package lotr.common.entity.npc;

import lotr.common.*;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.world.World;

public class LOTREntityRohanMeadhost extends LOTREntityRohanMan implements LOTRTradeable.Bartender {
    public LOTREntityRohanMeadhost(World world) {
        super(world);
        this.addTargetTasks(false);
        this.npcLocationName = "entity.lotr.RohanMeadhost.locationName";
    }

    @Override
    public LOTRTradeEntries getBuyPool() {
        return LOTRTradeEntries.ROHAN_MEADHOST_BUY;
    }

    @Override
    public LOTRTradeEntries getSellPool() {
        return LOTRTradeEntries.ROHAN_MEADHOST_SELL;
    }

    @Override
    public EntityAIBase createRohanAttackAI() {
        return new LOTREntityAIAttackOnCollide(this, 1.3, false);
    }

    @Override
    public void setupNPCGender() {
        this.familyInfo.setMale(this.rand.nextBoolean());
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.2);
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.mugMead));
        this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
        return data;
    }

    @Override
    public float getAlignmentBonus() {
        return 2.0f;
    }

    @Override
    protected void dropFewItems(boolean flag, int i) {
        super.dropFewItems(flag, i);
        int j = this.rand.nextInt(3) + this.rand.nextInt(i + 1);
        block9: for(int k = 0; k < j; ++k) {
            int l = this.rand.nextInt(11);
            switch(l) {
                case 0:
                case 1:
                case 2: {
                    Item food = LOTRFoods.ROHAN.getRandomFood(this.rand).getItem();
                    this.entityDropItem(new ItemStack(food), 0.0f);
                    continue block9;
                }
                case 3: {
                    this.entityDropItem(new ItemStack(Items.gold_nugget, 2 + this.rand.nextInt(3)), 0.0f);
                    continue block9;
                }
                case 4: {
                    this.entityDropItem(new ItemStack(Items.wheat, 1 + this.rand.nextInt(4)), 0.0f);
                    continue block9;
                }
                case 5: {
                    this.entityDropItem(new ItemStack(Items.sugar, 1 + this.rand.nextInt(3)), 0.0f);
                    continue block9;
                }
                case 6: {
                    this.entityDropItem(new ItemStack(Items.paper, 1 + this.rand.nextInt(2)), 0.0f);
                    continue block9;
                }
                case 7:
                case 8: {
                    this.entityDropItem(new ItemStack(LOTRMod.mug), 0.0f);
                    continue block9;
                }
                case 9:
                case 10: {
                    Item drink = LOTRMod.mugMead;
                    this.entityDropItem(new ItemStack(drink, 1, 1 + this.rand.nextInt(3)), 0.0f);
                }
            }
        }
    }

    @Override
    public boolean canTradeWith(EntityPlayer entityplayer) {
        return this.isFriendly(entityplayer);
    }

    @Override
    public void onPlayerTrade(EntityPlayer entityplayer, LOTRTradeEntries.TradeType type, ItemStack itemstack) {
        if(type == LOTRTradeEntries.TradeType.BUY && itemstack.getItem() == LOTRMod.mugMead) {
            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.buyRohanMead);
        }
    }

    @Override
    public boolean shouldTraderRespawn() {
        return true;
    }

    @Override
    public String getSpeechBank(EntityPlayer entityplayer) {
        if(this.isFriendly(entityplayer)) {
            return "rohan/meadhost/friendly";
        }
        return "rohan/meadhost/hostile";
    }
}
