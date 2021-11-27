package lotr.common.entity.npc;

import lotr.common.*;
import lotr.common.entity.ai.*;
import lotr.common.fac.LOTRFaction;
import lotr.common.quest.*;
import lotr.common.world.biome.*;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.material.Material;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.*;
import net.minecraft.world.biome.BiomeGenBase;

public class LOTREntityDwarf extends LOTREntityNPC {
    public LOTREntityDwarf(World world) {
        super(world);
        this.setSize(0.5f, 1.5f);
        this.getNavigator().setAvoidsWater(true);
        this.getNavigator().setBreakDoors(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new LOTREntityAIHiredRemainStill(this));
        this.tasks.addTask(2, new LOTREntityAINPCAvoidEvilPlayer(this, 8.0f, 1.5, 1.8));
        this.tasks.addTask(3, this.getDwarfAttackAI());
        this.tasks.addTask(4, new LOTREntityAIFollowHiringPlayer(this));
        this.tasks.addTask(5, new LOTREntityAINPCMarry(this, 1.3));
        this.tasks.addTask(6, new LOTREntityAINPCMate(this, 1.3));
        this.tasks.addTask(7, new LOTREntityAINPCFollowParent(this, 1.4));
        this.tasks.addTask(8, new LOTREntityAINPCFollowSpouse(this, 1.1));
        this.tasks.addTask(9, new EntityAIOpenDoor(this, true));
        this.tasks.addTask(10, new EntityAIWander(this, 1.0));
        this.tasks.addTask(11, new LOTREntityAIEat(this, this.getDwarfFoods(), 6000));
        this.tasks.addTask(11, new LOTREntityAIDrink(this, LOTRFoods.DWARF_DRINK, 6000));
        this.tasks.addTask(12, new EntityAIWatchClosest2(this, EntityPlayer.class, 8.0f, 0.02f));
        this.tasks.addTask(12, new EntityAIWatchClosest2(this, LOTREntityNPC.class, 5.0f, 0.02f));
        this.tasks.addTask(13, new EntityAIWatchClosest(this, EntityLiving.class, 8.0f, 0.02f));
        this.tasks.addTask(14, new EntityAILookIdle(this));
        this.addTargetTasks(true);
        this.familyInfo.marriageEntityClass = LOTREntityDwarf.class;
        this.familyInfo.marriageRing = LOTRMod.dwarvenRing;
        this.familyInfo.marriageAlignmentRequired = 200.0f;
        this.familyInfo.marriageAchievement = LOTRAchievement.marryDwarf;
        this.familyInfo.potentialMaxChildren = 3;
        this.familyInfo.timeToMature = 72000;
        this.familyInfo.breedingDelay = 48000;
    }

    protected EntityAIBase getDwarfAttackAI() {
        return new LOTREntityAIAttackOnCollide(this, 1.4, false);
    }

    protected LOTRFoods getDwarfFoods() {
        return LOTRFoods.DWARF;
    }

    @Override
    public void setupNPCGender() {
        this.familyInfo.setMale(true);
    }

    @Override
    public void setupNPCName() {
        this.familyInfo.setName(LOTRNames.getDwarfName(this.rand, this.familyInfo.isMale()));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(26.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.2);
    }

    @Override
    public IEntityLivingData initCreatureForHire(IEntityLivingData data) {
        data = super.initCreatureForHire(data);
        data = this.onSpawnWithEgg(data);
        if(this.getClass() == this.familyInfo.marriageEntityClass && this.rand.nextInt(3) == 0) {
            this.familyInfo.setMale(false);
            this.setupNPCName();
        }
        return data;
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerDwarven));
        this.npcItemsInv.setIdleItem(null);
        return data;
    }

    @Override
    public LOTRFaction getFaction() {
        return LOTRFaction.DURINS_FOLK;
    }

    @Override
    public String getNPCName() {
        return this.familyInfo.getName();
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        if(nbt.hasKey("DwarfName")) {
            this.familyInfo.setName(nbt.getString("DwarfName"));
        }
    }

    @Override
    public void createNPCChildName(LOTREntityNPC maleParent, LOTREntityNPC femaleParent) {
        this.familyInfo.setName(LOTRNames.getDwarfChildNameForParent(this.rand, this.familyInfo.isMale(), (LOTREntityDwarf) maleParent));
    }

    @Override
    public boolean interact(EntityPlayer entityplayer) {
        if(this.familyInfo.interact(entityplayer)) {
            return true;
        }
        return super.interact(entityplayer);
    }

    @Override
    public void onAttackModeChange(LOTREntityNPC.AttackMode mode, boolean mounted) {
        if(mode == LOTREntityNPC.AttackMode.IDLE) {
            this.setCurrentItemOrArmor(0, this.npcItemsInv.getIdleItem());
        }
        else {
            this.setCurrentItemOrArmor(0, this.npcItemsInv.getMeleeWeapon());
        }
    }

    @Override
    protected LOTRAchievement getKillAchievement() {
        return LOTRAchievement.killDwarf;
    }

    @Override
    public float getAlignmentBonus() {
        return 1.0f;
    }

    @Override
    protected void dropFewItems(boolean flag, int i) {
        super.dropFewItems(flag, i);
        int bones = this.rand.nextInt(2) + this.rand.nextInt(i + 1);
        for(int l = 0; l < bones; ++l) {
            this.dropItem(LOTRMod.dwarfBone, 1);
        }
        if(this.rand.nextInt(4) == 0) {
            this.dropChestContents(this.getLarderDrops(), 1, 2 + i);
        }
        if(this.rand.nextInt(8) == 0) {
            this.dropChestContents(this.getGenericDrops(), 1, 2 + i);
        }
        if(flag) {
            int rareDropChance = 20 - i * 4;
            if(this.rand.nextInt(rareDropChance = Math.max(rareDropChance, 1)) == 0) {
                int randDrop = this.rand.nextInt(4);
                switch(randDrop) {
                    case 0: {
                        this.entityDropItem(new ItemStack(Items.iron_ingot), 0.0f);
                        break;
                    }
                    case 1: {
                        this.entityDropItem(new ItemStack(this.getDwarfSteelDrop()), 0.0f);
                        break;
                    }
                    case 2: {
                        this.entityDropItem(new ItemStack(Items.gold_nugget, 1 + this.rand.nextInt(3)), 0.0f);
                        break;
                    }
                    case 3: {
                        this.entityDropItem(new ItemStack(LOTRMod.silverNugget, 1 + this.rand.nextInt(3)), 0.0f);
                    }
                }
            }
            int mithrilBookChance = 40 - i * 5;
            if(this.rand.nextInt(mithrilBookChance = Math.max(mithrilBookChance, 1)) == 0) {
                this.entityDropItem(new ItemStack(LOTRMod.mithrilBook), 0.0f);
            }
        }
    }

    protected Item getDwarfSteelDrop() {
        return LOTRMod.dwarfSteel;
    }

    protected LOTRChestContents getLarderDrops() {
        return LOTRChestContents.DWARF_HOUSE_LARDER;
    }

    protected LOTRChestContents getGenericDrops() {
        return LOTRChestContents.DWARVEN_TOWER;
    }

    @Override
    public boolean getCanSpawnHere() {
        if(super.getCanSpawnHere()) {
            if(this.liftSpawnRestrictions) {
                return true;
            }
            return this.canDwarfSpawnHere();
        }
        return false;
    }

    protected boolean canDwarfSpawnHere() {
        int i = MathHelper.floor_double(this.posX);
        int j = MathHelper.floor_double(this.boundingBox.minY);
        int k = MathHelper.floor_double(this.posZ);
        if(this.rand.nextInt(200) == 0) {
            return this.canDwarfSpawnAboveGround();
        }
        return j < 60 && this.worldObj.getBlock(i, j - 1, k).getMaterial() == Material.rock && !this.worldObj.canBlockSeeTheSky(i, j, k) && this.worldObj.getSavedLightValue(EnumSkyBlock.Block, i, j, k) >= 10;
    }

    protected boolean canDwarfSpawnAboveGround() {
        return true;
    }

    @Override
    public float getBlockPathWeight(int i, int j, int k) {
        float f = 0.0f;
        BiomeGenBase biome = this.worldObj.getBiomeGenForCoords(i, k);
        if(biome instanceof LOTRBiomeGenIronHills || biome instanceof LOTRBiomeGenErebor || biome instanceof LOTRBiomeGenBlueMountains || biome instanceof LOTRBiomeGenRedMountains) {
            f += 20.0f;
        }
        return f;
    }

    @Override
    public int getMaxSpawnedInChunk() {
        return 6;
    }

    @Override
    protected float getSoundPitch() {
        float f = super.getSoundPitch();
        if(!this.familyInfo.isMale()) {
            f *= 1.4f;
        }
        return f;
    }

    @Override
    public String getHurtSound() {
        return "lotr:dwarf.hurt";
    }

    @Override
    public String getDeathSound() {
        return "lotr:dwarf.hurt";
    }

    @Override
    public String getAttackSound() {
        return "lotr:dwarf.attack";
    }

    @Override
    public void onKillEntity(EntityLivingBase entity) {
        super.onKillEntity(entity);
        this.playSound("lotr:dwarf.kill", this.getSoundVolume(), this.getSoundPitch());
    }

    @Override
    protected LOTRAchievement getTalkAchievement() {
        if(!this.familyInfo.isMale()) {
            return LOTRAchievement.talkDwarfWoman;
        }
        return super.getTalkAchievement();
    }

    @Override
    public String getSpeechBank(EntityPlayer entityplayer) {
        if(this.isFriendly(entityplayer)) {
            if(this.hiredNPCInfo.getHiringPlayer() == entityplayer) {
                return "dwarf/dwarf/hired";
            }
            return this.isChild() ? "dwarf/child/friendly" : "dwarf/dwarf/friendly";
        }
        return this.isChild() ? "dwarf/child/hostile" : "dwarf/dwarf/hostile";
    }

    @Override
    public LOTRMiniQuest createMiniQuest() {
        return LOTRMiniQuestFactory.DURIN.createQuest(this);
    }

    @Override
    public LOTRMiniQuestFactory getBountyHelpSpeechDir() {
        return LOTRMiniQuestFactory.DURIN;
    }

    @Override
    public void onArtificalSpawn() {
        if(this.getClass() == this.familyInfo.marriageEntityClass) {
            if(this.rand.nextInt(3) == 0) {
                this.familyInfo.setMale(false);
                this.setupNPCName();
            }
            if(this.rand.nextInt(20) == 0) {
                this.familyInfo.setChild();
            }
        }
    }
}
