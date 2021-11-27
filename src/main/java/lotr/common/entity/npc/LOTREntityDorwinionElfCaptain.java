package lotr.common.entity.npc;

import lotr.common.*;
import lotr.common.world.spawning.LOTRInvasions;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityDorwinionElfCaptain extends LOTREntityDorwinionElfWarrior implements LOTRUnitTradeable {
    public LOTREntityDorwinionElfCaptain(World world) {
        super(world);
        this.addTargetTasks(false);
        this.npcCape = LOTRCapes.DORWINION_ELF_CAPTAIN;
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearBladorthin));
        this.npcItemsInv.setSpearBackup(null);
        this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
        this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsDorwinionElf));
        this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsDorwinionElf));
        this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyDorwinionElf));
        this.setCurrentItemOrArmor(4, null);
        return data;
    }

    @Override
    public float getAlignmentBonus() {
        return 5.0f;
    }

    @Override
    public LOTRUnitTradeEntries getUnits() {
        return LOTRUnitTradeEntries.DORWINION_ELF_CAPTAIN;
    }

    @Override
    public LOTRInvasions getWarhorn() {
        return LOTRInvasions.DORWINION_ELF;
    }

    @Override
    public boolean canTradeWith(EntityPlayer entityplayer) {
        return LOTRLevelData.getData(entityplayer).getAlignment(this.getFaction()) >= 250.0f && this.isFriendly(entityplayer);
    }

    @Override
    public void onUnitTrade(EntityPlayer entityplayer) {
        LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.tradeDorwinionElfCaptain);
    }

    @Override
    public boolean shouldTraderRespawn() {
        return true;
    }

    @Override
    public String getSpeechBank(EntityPlayer entityplayer) {
        if(this.isFriendly(entityplayer)) {
            if(this.canTradeWith(entityplayer)) {
                return "dorwinion/elfCaptain/friendly";
            }
            return "dorwinion/elfCaptain/neutral";
        }
        return "dorwinion/elfWarrior/hostile";
    }
}
