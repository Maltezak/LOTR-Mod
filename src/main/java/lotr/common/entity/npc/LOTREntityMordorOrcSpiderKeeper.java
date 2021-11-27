package lotr.common.entity.npc;

import lotr.common.*;
import lotr.common.world.spawning.LOTRInvasions;
import net.minecraft.entity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityMordorOrcSpiderKeeper extends LOTREntityMordorOrc implements LOTRUnitTradeable {
    public LOTREntityMordorOrcSpiderKeeper(World world) {
        super(world);
        this.setSize(0.6f, 1.8f);
        this.addTargetTasks(false);
        this.isWeakOrc = false;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(25.0);
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.orcSkullStaff));
        this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
        this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsOrc));
        this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsOrc));
        this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyOrc));
        this.setCurrentItemOrArmor(4, null);
        if(!this.worldObj.isRemote) {
            LOTREntityMordorSpider spider = new LOTREntityMordorSpider(this.worldObj);
            spider.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0f);
            spider.setSpiderScale(3);
            if(this.worldObj.func_147461_a(spider.boundingBox).isEmpty() || this.liftSpawnRestrictions) {
                spider.onSpawnWithEgg(null);
                this.worldObj.spawnEntityInWorld(spider);
                this.mountEntity(spider);
            }
        }
        return data;
    }

    @Override
    public float getAlignmentBonus() {
        return 5.0f;
    }

    @Override
    public LOTRUnitTradeEntries getUnits() {
        return LOTRUnitTradeEntries.MORDOR_ORC_SPIDER_KEEPER;
    }

    @Override
    public LOTRInvasions getWarhorn() {
        return LOTRInvasions.MORDOR_NAN_UNGOL;
    }

    @Override
    public boolean canTradeWith(EntityPlayer entityplayer) {
        return LOTRLevelData.getData(entityplayer).getAlignment(this.getFaction()) >= 250.0f && this.isFriendly(entityplayer);
    }

    @Override
    public void onUnitTrade(EntityPlayer entityplayer) {
        LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.tradeOrcSpiderKeeper);
    }

    @Override
    public boolean shouldTraderRespawn() {
        return true;
    }

    @Override
    public String getSpeechBank(EntityPlayer entityplayer) {
        if(this.isFriendly(entityplayer)) {
            if(this.canTradeWith(entityplayer)) {
                return "mordor/chieftain/friendly";
            }
            return "mordor/chieftain/neutral";
        }
        return "mordor/orc/hostile";
    }
}
