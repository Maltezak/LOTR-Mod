package lotr.common.entity.npc;

import lotr.common.*;
import lotr.common.world.spawning.LOTRInvasions;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityRivendellLord extends LOTREntityRivendellWarrior implements LOTRUnitTradeable {
    public LOTREntityRivendellLord(World world) {
        super(world);
        this.addTargetTasks(false);
        this.npcCape = LOTRCapes.RIVENDELL;
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.swordRivendell));
        this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
        this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsRivendell));
        this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsRivendell));
        this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyRivendell));
        this.setCurrentItemOrArmor(4, null);
        return data;
    }

    @Override
    public float getAlignmentBonus() {
        return 5.0f;
    }

    @Override
    public LOTRUnitTradeEntries getUnits() {
        return LOTRUnitTradeEntries.RIVENDELL_LORD;
    }

    @Override
    public LOTRInvasions getWarhorn() {
        return LOTRInvasions.HIGH_ELF_RIVENDELL;
    }

    @Override
    public boolean canTradeWith(EntityPlayer entityplayer) {
        return LOTRLevelData.getData(entityplayer).getAlignment(this.getFaction()) >= 300.0f && this.isFriendly(entityplayer);
    }

    @Override
    public void onUnitTrade(EntityPlayer entityplayer) {
        LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.tradeRivendellLord);
    }

    @Override
    public boolean shouldTraderRespawn() {
        return true;
    }

    @Override
    public String getSpeechBank(EntityPlayer entityplayer) {
        if(this.isFriendly(entityplayer)) {
            if(this.canTradeWith(entityplayer)) {
                return "rivendell/lord/friendly";
            }
            return "rivendell/lord/neutral";
        }
        return "rivendell/warrior/hostile";
    }
}
