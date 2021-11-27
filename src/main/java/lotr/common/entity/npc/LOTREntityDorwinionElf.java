package lotr.common.entity.npc;

import lotr.common.*;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.fac.LOTRFaction;
import lotr.common.item.LOTRItemMug;
import lotr.common.quest.*;
import lotr.common.world.biome.LOTRBiomeGenDorwinion;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class LOTREntityDorwinionElf extends LOTREntityElf {
    public LOTREntityDorwinionElf(World world) {
        super(world);
    }

    @Override
    protected LOTRFoods getElfDrinks() {
        return LOTRFoods.DORWINION_DRINK;
    }

    @Override
    protected EntityAIBase createElfMeleeAttackAI() {
        return new LOTREntityAIAttackOnCollide(this, 1.5, false);
    }

    @Override
    protected EntityAIBase createElfRangedAttackAI() {
        return this.createElfMeleeAttackAI();
    }

    @Override
    public void setupNPCName() {
        this.familyInfo.setName(LOTRNames.getSindarinName(this.rand, this.familyInfo.isMale()));
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerDorwinionElf));
        this.npcItemsInv.setIdleItem(null);
        return data;
    }

    @Override
    protected void onAttackModeChange(LOTREntityNPC.AttackMode mode, boolean mounted) {
        if(mode == LOTREntityNPC.AttackMode.IDLE) {
            this.tasks.removeTask(this.meleeAttackAI);
            this.tasks.removeTask(this.rangedAttackAI);
            this.setCurrentItemOrArmor(0, this.npcItemsInv.getIdleItem());
        }
        else {
            this.tasks.removeTask(this.meleeAttackAI);
            this.tasks.removeTask(this.rangedAttackAI);
            this.tasks.addTask(2, this.meleeAttackAI);
            this.setCurrentItemOrArmor(0, this.npcItemsInv.getMeleeWeapon());
        }
    }

    @Override
    public LOTRFaction getFaction() {
        return LOTRFaction.DORWINION;
    }

    @Override
    protected LOTRAchievement getKillAchievement() {
        return LOTRAchievement.killDorwinionElf;
    }

    @Override
    public float getAlignmentBonus() {
        return 2.0f;
    }

    @Override
    protected void dropElfItems(boolean flag, int i) {
        super.dropElfItems(flag, i);
        if(flag) {
            int dropChance = 20 - i * 4;
            if(this.rand.nextInt(dropChance = Math.max(dropChance, 1)) == 0) {
                ItemStack drink = LOTRFoods.DORWINION_DRINK.getRandomBrewableDrink(this.rand);
                LOTRItemMug.setStrengthMeta(drink, 1 + this.rand.nextInt(3));
                this.entityDropItem(drink, 0.0f);
            }
        }
        if(this.rand.nextInt(6) == 0) {
            this.dropChestContents(LOTRChestContents.DORWINION_HOUSE, 1, 1 + i);
        }
    }

    @Override
    public boolean canElfSpawnHere() {
        int i = MathHelper.floor_double(this.posX);
        int j = MathHelper.floor_double(this.boundingBox.minY);
        int k = MathHelper.floor_double(this.posZ);
        BiomeGenBase biome = this.worldObj.getBiomeGenForCoords(i, k);
        return j > 62 && this.worldObj.getBlock(i, j - 1, k) == biome.topBlock;
    }

    @Override
    public float getBlockPathWeight(int i, int j, int k) {
        float f = 0.0f;
        BiomeGenBase biome = this.worldObj.getBiomeGenForCoords(i, k);
        if(biome instanceof LOTRBiomeGenDorwinion) {
            f += 20.0f;
        }
        return f;
    }

    @Override
    public String getSpeechBank(EntityPlayer entityplayer) {
        if(this.isFriendly(entityplayer)) {
            return "dorwinion/elf/friendly";
        }
        return "dorwinion/elf/hostile";
    }

    @Override
    public LOTRMiniQuest createMiniQuest() {
        return LOTRMiniQuestFactory.DORWINION_ELF.createQuest(this);
    }

    @Override
    public LOTRMiniQuestFactory getBountyHelpSpeechDir() {
        return LOTRMiniQuestFactory.DORWINION_ELF;
    }
}
