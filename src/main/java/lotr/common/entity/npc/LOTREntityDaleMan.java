package lotr.common.entity.npc;

import lotr.common.*;
import lotr.common.entity.ai.*;
import lotr.common.fac.LOTRFaction;
import lotr.common.quest.*;
import lotr.common.world.biome.LOTRBiomeGenDale;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class LOTREntityDaleMan extends LOTREntityMan {
    private static ItemStack[] weapons = new ItemStack[] {new ItemStack(LOTRMod.daggerDale), new ItemStack(LOTRMod.daggerIron), new ItemStack(LOTRMod.daggerBronze), new ItemStack(Items.iron_axe), new ItemStack(LOTRMod.axeBronze), new ItemStack(Items.stone_axe)};

    public LOTREntityDaleMan(World world) {
        super(world);
        this.setSize(0.6f, 1.8f);
        this.getNavigator().setAvoidsWater(true);
        this.getNavigator().setBreakDoors(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new LOTREntityAIHiredRemainStill(this));
        this.tasks.addTask(2, this.createDaleAttackAI());
        this.tasks.addTask(3, new LOTREntityAIFollowHiringPlayer(this));
        this.tasks.addTask(4, new EntityAIOpenDoor(this, true));
        this.tasks.addTask(5, new EntityAIWander(this, 1.0));
        this.tasks.addTask(6, new LOTREntityAIEat(this, LOTRFoods.DALE, 8000));
        this.tasks.addTask(6, new LOTREntityAIDrink(this, LOTRFoods.DALE_DRINK, 8000));
        this.tasks.addTask(7, new EntityAIWatchClosest2(this, EntityPlayer.class, 8.0f, 0.02f));
        this.tasks.addTask(7, new EntityAIWatchClosest2(this, LOTREntityNPC.class, 5.0f, 0.02f));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityLiving.class, 8.0f, 0.02f));
        this.tasks.addTask(9, new EntityAILookIdle(this));
        this.addTargetTasks(false);
    }

    protected EntityAIBase createDaleAttackAI() {
        return new LOTREntityAIAttackOnCollide(this, 1.3, false);
    }

    @Override
    public void setupNPCGender() {
        this.familyInfo.setMale(this.rand.nextBoolean());
    }

    @Override
    public void setupNPCName() {
        this.familyInfo.setName(LOTRNames.getDalishName(this.rand, this.familyInfo.isMale()));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.2);
        this.getEntityAttribute(npcRangedAccuracy).setBaseValue(0.5);
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        int i = this.rand.nextInt(weapons.length);
        this.npcItemsInv.setMeleeWeapon(weapons[i].copy());
        this.npcItemsInv.setIdleItem(null);
        return data;
    }

    @Override
    public LOTRFaction getFaction() {
        return LOTRFaction.DALE;
    }

    @Override
    public String getNPCName() {
        return this.familyInfo.getName();
    }

    @Override
    public String getNPCFormattedName(String npcName, String entityName) {
        if(this.getClass() == LOTREntityDaleMan.class) {
            return StatCollector.translateToLocalFormatted("entity.lotr.DaleMan.entityName", npcName);
        }
        return super.getNPCFormattedName(npcName, entityName);
    }

    @Override
    protected void onAttackModeChange(LOTREntityNPC.AttackMode mode, boolean mounted) {
        if(mode == LOTREntityNPC.AttackMode.IDLE) {
            this.setCurrentItemOrArmor(0, this.npcItemsInv.getIdleItem());
        }
        else {
            this.setCurrentItemOrArmor(0, this.npcItemsInv.getMeleeWeapon());
        }
    }

    @Override
    protected void dropFewItems(boolean flag, int i) {
        super.dropFewItems(flag, i);
        int bones = this.rand.nextInt(2) + this.rand.nextInt(i + 1);
        for(int l = 0; l < bones; ++l) {
            this.dropItem(Items.bone, 1);
        }
        this.dropDaleItems(flag, i);
    }

    protected void dropDaleItems(boolean flag, int i) {
        if(this.rand.nextInt(6) == 0) {
            this.dropChestContents(LOTRChestContents.DALE_HOUSE, 1, 2 + i);
        }
    }

    @Override
    protected LOTRAchievement getKillAchievement() {
        return LOTRAchievement.killDalish;
    }

    @Override
    public float getAlignmentBonus() {
        return 1.0f;
    }

    @Override
    public boolean getCanSpawnHere() {
        if(super.getCanSpawnHere()) {
            if(this.liftSpawnRestrictions) {
                return true;
            }
            int i = MathHelper.floor_double(this.posX);
            int j = MathHelper.floor_double(this.boundingBox.minY);
            int k = MathHelper.floor_double(this.posZ);
            if(j > 62 && this.worldObj.getBlock(i, j - 1, k) == this.worldObj.getBiomeGenForCoords(i, k).topBlock) {
                return true;
            }
        }
        return false;
    }

    @Override
    public float getBlockPathWeight(int i, int j, int k) {
        float f = 0.0f;
        BiomeGenBase biome = this.worldObj.getBiomeGenForCoords(i, k);
        if(biome instanceof LOTRBiomeGenDale) {
            f += 20.0f;
        }
        return f;
    }

    @Override
    public String getSpeechBank(EntityPlayer entityplayer) {
        if(this.isFriendly(entityplayer)) {
            return "dale/man/friendly";
        }
        return "dale/man/hostile";
    }

    @Override
    public LOTRMiniQuest createMiniQuest() {
        return LOTRMiniQuestFactory.DALE.createQuest(this);
    }

    @Override
    public LOTRMiniQuestFactory getBountyHelpSpeechDir() {
        return LOTRMiniQuestFactory.DALE;
    }
}
