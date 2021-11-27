package lotr.common.entity.npc;

import lotr.common.*;
import lotr.common.world.spawning.LOTRInvasions;
import net.minecraft.entity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityRohirrimMarshal extends LOTREntityRohirrimWarrior implements LOTRUnitTradeable {
    public LOTREntityRohirrimMarshal(World world) {
        super(world);
        this.addTargetTasks(false);
        this.npcCape = LOTRCapes.ROHAN;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(25.0);
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.swordRohan));
        this.npcItemsInv.setMeleeWeaponMounted(this.npcItemsInv.getMeleeWeapon());
        this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
        this.npcItemsInv.setIdleItemMounted(this.npcItemsInv.getMeleeWeaponMounted());
        this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsRohanMarshal));
        this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsRohanMarshal));
        this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyRohanMarshal));
        this.setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetRohanMarshal));
        return data;
    }

    @Override
    public float getAlignmentBonus() {
        return 5.0f;
    }

    @Override
    public LOTRUnitTradeEntries getUnits() {
        return LOTRUnitTradeEntries.ROHIRRIM_MARSHAL;
    }

    @Override
    public LOTRInvasions getWarhorn() {
        return LOTRInvasions.ROHAN;
    }

    @Override
    public boolean canTradeWith(EntityPlayer entityplayer) {
        return LOTRLevelData.getData(entityplayer).getAlignment(this.getFaction()) >= 150.0f && this.isFriendly(entityplayer);
    }

    @Override
    public void onUnitTrade(EntityPlayer entityplayer) {
        LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.tradeRohirrimMarshal);
    }

    @Override
    public boolean shouldTraderRespawn() {
        return true;
    }

    @Override
    public String getSpeechBank(EntityPlayer entityplayer) {
        if(this.isFriendly(entityplayer)) {
            if(this.canTradeWith(entityplayer)) {
                return "rohan/marshal/friendly";
            }
            return "rohan/marshal/neutral";
        }
        return "rohan/warrior/hostile";
    }
}
