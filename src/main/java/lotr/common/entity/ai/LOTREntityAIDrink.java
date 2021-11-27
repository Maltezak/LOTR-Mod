package lotr.common.entity.ai;

import java.util.List;

import lotr.common.*;
import lotr.common.entity.npc.*;
import lotr.common.item.LOTRItemMug;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.item.*;
import net.minecraft.util.MathHelper;

public class LOTREntityAIDrink extends LOTREntityAIConsumeBase {
    public LOTREntityAIDrink(LOTREntityNPC entity, LOTRFoods foods, int chance) {
        super(entity, foods, chance);
    }

    @Override
    protected ItemStack createConsumable() {
        ItemStack drink = this.foodPool.getRandomFood(this.rand);
        Item item = drink.getItem();
        if(item instanceof LOTRItemMug && ((LOTRItemMug) item).isBrewable) {
            LOTRItemMug.setStrengthMeta(drink, 1 + this.rand.nextInt(3));
        }
        return drink;
    }

    @Override
    protected boolean shouldConsume() {
        if(this.theEntity.isDrunkard() && this.rand.nextInt(100) == 0) {
            return true;
        }
        return super.shouldConsume();
    }

    @Override
    protected int getConsumeTime() {
        int time = super.getConsumeTime();
        if(this.theEntity.isDrunkard()) {
            time *= 1 + this.rand.nextInt(4);
        }
        return time;
    }

    @Override
    protected void updateConsumeTick(int tick) {
        if(tick % 4 == 0) {
            this.theEntity.playSound("random.drink", 0.5f, this.rand.nextFloat() * 0.1f + 0.9f);
        }
    }

    @Override
    protected void consume() {
        ItemStack itemstack = this.theEntity.getHeldItem();
        Item item = itemstack.getItem();
        if(item instanceof LOTRItemMug) {
            LOTRItemMug drink = (LOTRItemMug) item;
            drink.applyToNPC(this.theEntity, itemstack);
            if(drink.alcoholicity > 0.0f && this.theEntity.canGetDrunk() && !this.theEntity.isDrunkard() && this.rand.nextInt(3) == 0) {
                double range = 12.0;
                IEntitySelector selectNonEnemyBartenders = new IEntitySelector() {

                    @Override
                    public boolean isEntityApplicable(Entity entity) {
                        return entity.isEntityAlive() && !LOTRMod.getNPCFaction(entity).isBadRelation(LOTREntityAIDrink.this.theEntity.getFaction());
                    }
                };
                List nearbyBartenders = this.theEntity.worldObj.selectEntitiesWithinAABB(LOTRTradeable.Bartender.class, this.theEntity.boundingBox.expand(range, range, range), selectNonEnemyBartenders);
                if(!nearbyBartenders.isEmpty()) {
                    int drunkTime = MathHelper.getRandomIntegerInRange(this.rand, 30, 1500);
                    this.theEntity.familyInfo.setDrunkTime(drunkTime *= 20);
                }
            }
        }
    }

}
