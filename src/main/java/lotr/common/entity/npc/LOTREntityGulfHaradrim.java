package lotr.common.entity.npc;

import lotr.common.*;
import lotr.common.quest.*;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityGulfHaradrim extends LOTREntityNearHaradrimBase {
    public LOTREntityGulfHaradrim(World world) {
        super(world);
        this.addTargetTasks(true);
    }

    @Override
    protected LOTRFoods getHaradrimFoods() {
        return LOTRFoods.GULF_HARAD;
    }

    @Override
    protected LOTRFoods getHaradrimDrinks() {
        return LOTRFoods.GULF_HARAD_DRINK;
    }

    @Override
    public void setupNPCName() {
        this.familyInfo.setName(LOTRNames.getGulfHaradName(this.rand, this.familyInfo.isMale()));
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerHarad));
        this.npcItemsInv.setIdleItem(null);
        return data;
    }

    @Override
    protected void dropHaradrimItems(boolean flag, int i) {
        if(this.rand.nextInt(5) == 0) {
            this.dropChestContents(LOTRChestContents.GULF_HOUSE, 1, 2 + i);
        }
    }

    @Override
    protected LOTRAchievement getKillAchievement() {
        return LOTRAchievement.killNearHaradrim;
    }

    @Override
    public String getSpeechBank(EntityPlayer entityplayer) {
        if(this.isFriendly(entityplayer)) {
            return "nearHarad/gulf/haradrim/friendly";
        }
        return "nearHarad/gulf/haradrim/hostile";
    }

    @Override
    public LOTRMiniQuest createMiniQuest() {
        return LOTRMiniQuestFactory.GULF_HARAD.createQuest(this);
    }

    @Override
    public LOTRMiniQuestFactory getBountyHelpSpeechDir() {
        return LOTRMiniQuestFactory.GULF_HARAD;
    }
}
