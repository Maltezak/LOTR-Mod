package lotr.common.entity.npc;

import lotr.common.*;
import lotr.common.fac.LOTRFaction;
import lotr.common.quest.*;
import lotr.common.world.biome.LOTRBiomeGenIthilien;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class LOTREntityRangerIthilien extends LOTREntityRanger {
    public LOTREntityRangerIthilien(World world) {
        super(world);
        this.npcCape = LOTRCapes.RANGER_ITHILIEN;
    }

    @Override
    protected LOTRFoods getDunedainFoods() {
        return LOTRFoods.GONDOR;
    }

    @Override
    protected LOTRFoods getDunedainDrinks() {
        return LOTRFoods.GONDOR_DRINK;
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        int i = this.rand.nextInt(4);
        if(i == 0 || i == 1 || i == 2) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerGondor));
        }
        else if(i == 3) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.swordGondor));
        }
        this.npcItemsInv.setRangedWeapon(new ItemStack(LOTRMod.gondorBow));
        this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsRangerIthilien));
        this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsRangerIthilien));
        this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyRangerIthilien));
        this.setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetRangerIthilien));
        return data;
    }

    @Override
    public LOTRFaction getFaction() {
        return LOTRFaction.GONDOR;
    }

    @Override
    public float getBlockPathWeight(int i, int j, int k) {
        float f = super.getBlockPathWeight(i, j, k);
        BiomeGenBase biome = this.worldObj.getBiomeGenForCoords(i, k);
        if(biome instanceof LOTRBiomeGenIthilien) {
            f += 20.0f;
        }
        return f;
    }

    @Override
    protected void dropDunedainItems(boolean flag, int i) {
        if(this.rand.nextInt(6) == 0) {
            this.dropChestContents(LOTRChestContents.GONDOR_HOUSE, 1, 2 + i);
        }
    }

    @Override
    protected LOTRAchievement getKillAchievement() {
        return LOTRAchievement.killRangerIthilien;
    }

    @Override
    public float getAlignmentBonus() {
        return 2.0f;
    }

    @Override
    public String getSpeechBank(EntityPlayer entityplayer) {
        if(this.isFriendly(entityplayer)) {
            if(this.hiredNPCInfo.getHiringPlayer() == entityplayer) {
                return "gondor/ranger/hired";
            }
            return "gondor/ranger/friendly";
        }
        return "gondor/ranger/hostile";
    }

    @Override
    public LOTRMiniQuest createMiniQuest() {
        return LOTRMiniQuestFactory.GONDOR.createQuest(this);
    }

    @Override
    public LOTRMiniQuestFactory getBountyHelpSpeechDir() {
        return LOTRMiniQuestFactory.GONDOR;
    }
}
