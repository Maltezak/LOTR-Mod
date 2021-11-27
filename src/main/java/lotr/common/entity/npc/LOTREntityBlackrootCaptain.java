package lotr.common.entity.npc;

import lotr.common.*;
import lotr.common.world.spawning.LOTRInvasions;
import net.minecraft.entity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityBlackrootCaptain extends LOTREntityBlackrootArcher implements LOTRUnitTradeable {
    public LOTREntityBlackrootCaptain(World world) {
        super(world);
        this.addTargetTasks(false);
        this.npcCape = LOTRCapes.BLACKROOT;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(25.0);
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        this.npcItemsInv.setRangedWeapon(new ItemStack(LOTRMod.blackrootBow));
        this.npcItemsInv.setIdleItem(this.npcItemsInv.getRangedWeapon());
        this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsBlackroot));
        this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsBlackroot));
        this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyBlackroot));
        this.setCurrentItemOrArmor(4, null);
        return data;
    }

    @Override
    public float getAlignmentBonus() {
        return 5.0f;
    }

    @Override
    public LOTRUnitTradeEntries getUnits() {
        return LOTRUnitTradeEntries.BLACKROOT_CAPTAIN;
    }

    @Override
    public LOTRInvasions getWarhorn() {
        return LOTRInvasions.GONDOR_BLACKROOT;
    }

    @Override
    public boolean canTradeWith(EntityPlayer entityplayer) {
        return LOTRLevelData.getData(entityplayer).getAlignment(this.getFaction()) >= 150.0f && this.isFriendly(entityplayer);
    }

    @Override
    public void onUnitTrade(EntityPlayer entityplayer) {
        LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.tradeBlackrootCaptain);
    }

    @Override
    public boolean shouldTraderRespawn() {
        return true;
    }

    @Override
    public String getSpeechBank(EntityPlayer entityplayer) {
        if(this.isFriendly(entityplayer)) {
            if(this.canTradeWith(entityplayer)) {
                return "gondor/blackrootCaptain/friendly";
            }
            return "gondor/blackrootCaptain/neutral";
        }
        return "gondor/soldier/hostile";
    }
}
