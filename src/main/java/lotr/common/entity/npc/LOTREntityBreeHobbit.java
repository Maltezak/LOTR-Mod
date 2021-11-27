package lotr.common.entity.npc;

import lotr.common.*;
import lotr.common.fac.LOTRFaction;
import lotr.common.quest.*;
import lotr.common.world.biome.LOTRBiomeGenBreeland;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class LOTREntityBreeHobbit
extends LOTREntityHobbit
implements IPickpocketable {
    public LOTREntityBreeHobbit(World world) {
        super(world);
        this.familyInfo.marriageEntityClass = LOTREntityBreeHobbit.class;
    }

    @Override
    protected LOTRFoods getHobbitFoods() {
        return LOTRFoods.BREE;
    }

    @Override
    protected LOTRFoods getHobbitDrinks() {
        return LOTRFoods.BREE_DRINK;
    }

    @Override
    public void setupNPCName() {
        this.familyInfo.setName(LOTRNames.getBreeHobbitName(this.rand, this.familyInfo.isMale()));
    }

    @Override
    public void createNPCChildName(LOTREntityNPC maleParent, LOTREntityNPC femaleParent) {
        this.familyInfo.setName(LOTRNames.getBreeHobbitChildNameForParent(this.rand, this.familyInfo.isMale(), (LOTREntityHobbit)maleParent));
    }

    @Override
    public void changeNPCNameForMarriage(LOTREntityNPC spouse) {
        super.changeNPCNameForMarriage(spouse);
    }

    @Override
    public LOTRFaction getFaction() {
        return LOTRFaction.BREE;
    }

    @Override
    protected LOTRAchievement getKillAchievement() {
        return LOTRAchievement.killBreeHobbit;
    }

    @Override
    public float getAlignmentBonus() {
        return 1.0f;
    }

    @Override
    protected void dropHobbitItems(boolean flag, int i) {
        if (this.rand.nextInt(6) == 0) {
            this.dropChestContents(LOTRChestContents.BREE_HOUSE, 1, 2 + i);
        }
    }

    @Override
    public float getBlockPathWeight(int i, int j, int k) {
        float f = 0.0f;
        BiomeGenBase biome = this.worldObj.getBiomeGenForCoords(i, k);
        if (biome instanceof LOTRBiomeGenBreeland) {
            f += 20.0f;
        }
        return f;
    }

    @Override
    public String getSpeechBank(EntityPlayer entityplayer) {
        if (this.isDrunkard()) {
            return "bree/hobbit/drunkard/neutral";
        }
        if (this.isFriendlyAndAligned(entityplayer)) {
            return this.isChild() ? "bree/hobbit/child/friendly" : "bree/hobbit/friendly";
        }
        return this.isChild() ? "bree/hobbit/child/hostile" : "bree/hobbit/hostile";
    }

    @Override
    public LOTRMiniQuest createMiniQuest() {
        return LOTRMiniQuestFactory.BREE.createQuest(this);
    }

    @Override
    public LOTRMiniQuestFactory getBountyHelpSpeechDir() {
        return LOTRMiniQuestFactory.BREE;
    }

    @Override
    public boolean canPickpocket() {
        return true;
    }

    @Override
    public ItemStack createPickpocketItem() {
        return LOTRChestContents.BREE_PICKPOCKET.getOneItem(this.rand, true);
    }
}

