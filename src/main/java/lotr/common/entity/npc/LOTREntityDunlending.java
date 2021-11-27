package lotr.common.entity.npc;

import lotr.common.*;
import lotr.common.entity.ai.*;
import lotr.common.fac.LOTRFaction;
import lotr.common.quest.*;
import lotr.common.world.biome.*;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class LOTREntityDunlending extends LOTREntityMan {
    public LOTREntityDunlending(World world) {
        super(world);
        this.setSize(0.6f, 1.8f);
        this.getNavigator().setAvoidsWater(true);
        this.getNavigator().setBreakDoors(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new LOTREntityAIHiredRemainStill(this));
        this.tasks.addTask(2, this.getDunlendingAttackAI());
        this.tasks.addTask(3, new LOTREntityAIFollowHiringPlayer(this));
        this.tasks.addTask(4, new EntityAIOpenDoor(this, true));
        this.tasks.addTask(5, new EntityAIWander(this, 1.0));
        this.tasks.addTask(6, new LOTREntityAIEat(this, LOTRFoods.DUNLENDING, 8000));
        this.tasks.addTask(6, new LOTREntityAIDrink(this, LOTRFoods.DUNLENDING_DRINK, 8000));
        this.tasks.addTask(7, new EntityAIWatchClosest2(this, EntityPlayer.class, 8.0f, 0.02f));
        this.tasks.addTask(7, new EntityAIWatchClosest2(this, LOTREntityNPC.class, 5.0f, 0.02f));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityLiving.class, 8.0f, 0.02f));
        this.tasks.addTask(9, new EntityAILookIdle(this));
        this.addTargetTasks(true);
    }

    public EntityAIBase getDunlendingAttackAI() {
        return new LOTREntityAIAttackOnCollide(this, 1.5, false);
    }

    @Override
    public void setupNPCGender() {
        this.familyInfo.setMale(this.rand.nextBoolean());
    }

    @Override
    public void setupNPCName() {
        this.familyInfo.setName(LOTRNames.getDunlendingName(this.rand, this.familyInfo.isMale()));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.2);
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        int i = this.rand.nextInt(9);
        if(i == 0 || i == 1) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.dunlendingClub));
        }
        else if(i == 2 || i == 3) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.dunlendingTrident));
        }
        else if(i == 4) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(Items.wooden_sword));
        }
        else if(i == 5) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(Items.stone_sword));
        }
        else if(i == 6) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(Items.stone_axe));
        }
        else if(i == 7) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(Items.stone_hoe));
        }
        else if(i == 8) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearStone));
        }
        this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
        if(this.rand.nextInt(4) == 0) {
            this.setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetFur));
        }
        if(this.rand.nextInt(10000) == 0) {
            LOTREntityUrukWargBombardier warg = new LOTREntityUrukWargBombardier(this.worldObj);
            warg.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0f);
            warg.onSpawnWithEgg(null);
            warg.isNPCPersistent = this.isNPCPersistent;
            this.worldObj.spawnEntityInWorld(warg);
            this.mountEntity(warg);
            this.setCurrentItemOrArmor(4, new ItemStack(LOTRMod.orcBomb));
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.orcBomb));
            this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
        }
        return data;
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
    public LOTRFaction getFaction() {
        return LOTRFaction.DUNLAND;
    }

    @Override
    public String getNPCName() {
        return this.familyInfo.getName();
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        if(nbt.hasKey("DunlendingName")) {
            this.familyInfo.setName(nbt.getString("DunlendingName"));
        }
    }

    @Override
    protected void dropFewItems(boolean flag, int i) {
        super.dropFewItems(flag, i);
        int bones = this.rand.nextInt(2) + this.rand.nextInt(i + 1);
        for(int l = 0; l < bones; ++l) {
            this.dropItem(Items.bone, 1);
        }
        this.dropDunlendingItems(flag, i);
    }

    public void dropDunlendingItems(boolean flag, int i) {
        if(this.rand.nextInt(5) == 0) {
            this.dropChestContents(LOTRChestContents.DUNLENDING_HOUSE, 1, 2 + i);
        }
    }

    @Override
    protected LOTRAchievement getKillAchievement() {
        return LOTRAchievement.killDunlending;
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
            BiomeGenBase biome = this.worldObj.getBiomeGenForCoords(i, k);
            if(j > 62 && this.worldObj.getBlock(i, j - 1, k) == biome.topBlock) {
                return true;
            }
        }
        return false;
    }

    @Override
    public float getBlockPathWeight(int i, int j, int k) {
        float f = 0.0f;
        BiomeGenBase biome = this.worldObj.getBiomeGenForCoords(i, k);
        if(biome instanceof LOTRBiomeGenDunland || biome instanceof LOTRBiomeGenAdornland) {
            f += 20.0f;
        }
        return f;
    }

    @Override
    public String getSpeechBank(EntityPlayer entityplayer) {
        if(this.isDrunkard()) {
            return "dunlending/drunkard/neutral";
        }
        if(this.isFriendly(entityplayer)) {
            if(this.hiredNPCInfo.getHiringPlayer() == entityplayer) {
                return "dunlending/dunlending/hired";
            }
            return "dunlending/dunlending/friendly";
        }
        return "dunlending/dunlending/hostile";
    }

    @Override
    public LOTRMiniQuest createMiniQuest() {
        return LOTRMiniQuestFactory.DUNLAND.createQuest(this);
    }

    @Override
    public LOTRMiniQuestFactory getBountyHelpSpeechDir() {
        return LOTRMiniQuestFactory.DUNLAND;
    }
}
