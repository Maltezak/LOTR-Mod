package lotr.common.entity.npc;

import lotr.common.*;
import lotr.common.entity.ai.*;
import lotr.common.fac.LOTRFaction;
import lotr.common.item.LOTRItemMug;
import lotr.common.quest.*;
import lotr.common.world.biome.LOTRBiomeGenWoodlandRealm;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class LOTREntityWoodElf extends LOTREntityElf {
    public LOTREntityWoodElf(World world) {
        super(world);
        this.tasks.addTask(2, this.rangedAttackAI);
        this.addTargetTasks(true, LOTREntityAINearestAttackableTargetWoodElf.class);
    }

    @Override
    protected LOTRFoods getElfDrinks() {
        return LOTRFoods.WOOD_ELF_DRINK;
    }

    @Override
    protected EntityAIBase createElfMeleeAttackAI() {
        return this.createElfRangedAttackAI();
    }

    @Override
    protected EntityAIBase createElfRangedAttackAI() {
        return new LOTREntityAIRangedAttack(this, 1.25, 30, 50, 16.0f);
    }

    @Override
    public void setupNPCName() {
        this.familyInfo.setName(LOTRNames.getSindarinName(this.rand, this.familyInfo.isMale()));
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        this.npcItemsInv.setRangedWeapon(new ItemStack(LOTRMod.mirkwoodBow));
        this.npcItemsInv.setMeleeWeapon(this.npcItemsInv.getRangedWeapon());
        this.npcItemsInv.setIdleItem(null);
        return data;
    }

    @Override
    public LOTRFaction getFaction() {
        return LOTRFaction.WOOD_ELF;
    }

    @Override
    protected LOTRAchievement getKillAchievement() {
        return LOTRAchievement.killWoodElf;
    }

    @Override
    public float getAlignmentBonus() {
        return 1.0f;
    }

    @Override
    protected void dropElfItems(boolean flag, int i) {
        super.dropElfItems(flag, i);
        if(flag) {
            int dropChance = 20 - i * 4;
            if(this.rand.nextInt(dropChance = Math.max(dropChance, 1)) == 0) {
                ItemStack elfDrink = new ItemStack(LOTRMod.mugRedWine);
                elfDrink.setItemDamage(1 + this.rand.nextInt(3));
                LOTRItemMug.setVessel(elfDrink, LOTRFoods.ELF_DRINK.getRandomVessel(this.rand), true);
                this.entityDropItem(elfDrink, 0.0f);
            }
        }
        if(this.rand.nextInt(6) == 0) {
            this.dropChestContents(LOTRChestContents.WOOD_ELF_HOUSE, 1, 1 + i);
        }
    }

    @Override
    public boolean canElfSpawnHere() {
        int i = MathHelper.floor_double(this.posX);
        int j = MathHelper.floor_double(this.boundingBox.minY);
        int k = MathHelper.floor_double(this.posZ);
        return j > 62 && this.worldObj.getBlock(i, j - 1, k) == Blocks.grass;
    }

    @Override
    public float getBlockPathWeight(int i, int j, int k) {
        float f = 0.0f;
        BiomeGenBase biome = this.worldObj.getBiomeGenForCoords(i, k);
        if(biome instanceof LOTRBiomeGenWoodlandRealm) {
            f += 20.0f;
        }
        return f;
    }

    @Override
    public String getSpeechBank(EntityPlayer entityplayer) {
        if(this.isFriendly(entityplayer)) {
            if(this.hiredNPCInfo.getHiringPlayer() == entityplayer) {
                return "woodElf/elf/hired";
            }
            if(LOTRLevelData.getData(entityplayer).getAlignment(this.getFaction()) >= LOTREntityWoodElf.getWoodlandTrustLevel()) {
                return "woodElf/elf/friendly";
            }
            return "woodElf/elf/neutral";
        }
        return "woodElf/elf/hostile";
    }

    @Override
    public LOTRMiniQuest createMiniQuest() {
        return LOTRMiniQuestFactory.WOOD_ELF.createQuest(this);
    }

    @Override
    public LOTRMiniQuestFactory getBountyHelpSpeechDir() {
        return LOTRMiniQuestFactory.WOOD_ELF;
    }

    public static float getWoodlandTrustLevel() {
        return LOTRFaction.WOOD_ELF.getFirstRank().alignment;
    }
}
