package lotr.common.entity.npc;

import java.util.*;

import cpw.mods.fml.relauncher.*;
import lotr.common.*;
import lotr.common.entity.ai.*;
import lotr.common.fac.LOTRFaction;
import lotr.common.quest.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class LOTREntityGandalf extends LOTREntityNPC {
    public LOTREntityGandalf(World world) {
        super(world);
        this.setSize(0.6f, 1.8f);
        this.getNavigator().setAvoidsWater(true);
        this.getNavigator().setBreakDoors(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new LOTREntityAIAttackOnCollide(this, 1.8, false));
        this.tasks.addTask(2, new EntityAIOpenDoor(this, true));
        this.tasks.addTask(3, new EntityAIWander(this, 1.0));
        this.tasks.addTask(4, new LOTREntityAIGandalfSmoke(this, 3000));
        this.tasks.addTask(5, new EntityAIWatchClosest2(this, EntityPlayer.class, 8.0f, 0.05f));
        this.tasks.addTask(5, new EntityAIWatchClosest2(this, LOTREntityNPC.class, 5.0f, 0.05f));
        this.tasks.addTask(5, new EntityAIWatchClosest(this, EntityLiving.class, 8.0f, 0.02f));
        this.tasks.addTask(6, new EntityAILookIdle(this));
        int target = this.addTargetTasks(false);
        this.targetTasks.addTask(target + 1, new LOTREntityAINearestAttackableTargetBasic(this, LOTREntityBalrog.class, 0, true));
        this.targetTasks.addTask(target + 2, new LOTREntityAINearestAttackableTargetBasic(this, LOTREntitySaruman.class, 0, true));
        this.npcCape = LOTRCapes.GANDALF;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.2);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30.0);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(40.0);
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.glamdring));
        this.npcItemsInv.setIdleItem(new ItemStack(LOTRMod.gandalfStaffGrey));
        return data;
    }

    @Override
    public void onArtificalSpawn() {
        LOTRGreyWandererTracker.addNewWanderer(this.getUniqueID());
        this.arriveAt(null);
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
    public ItemStack getHeldItemLeft() {
        ItemStack heldItem = this.getHeldItem();
        if(heldItem != null && heldItem.getItem() == LOTRMod.glamdring) {
            return new ItemStack(LOTRMod.gandalfStaffGrey);
        }
        return super.getHeldItemLeft();
    }

    @Override
    public LOTRFaction getFaction() {
        return LOTRFaction.UNALIGNED;
    }

    @Override
    public boolean canBeFreelyTargetedBy(EntityLiving attacker) {
        return false;
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float f) {
        Entity entity = damagesource.getEntity();
        if(entity instanceof EntityPlayer && ((EntityPlayer) entity).capabilities.isCreativeMode) {
            return super.attackEntityFrom(damagesource, f);
        }
        f = 0.0f;
        return super.attackEntityFrom(damagesource, f);
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if(!this.worldObj.isRemote && !LOTRGreyWandererTracker.isWandererActive(this.getUniqueID()) && this.getAttackTarget() == null) {
            this.depart();
        }
    }

    private void doGandalfFX() {
        this.playSound("random.pop", 2.0f, 0.5f + this.rand.nextFloat() * 0.5f);
        this.worldObj.setEntityState(this, (byte) 16);
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void handleHealthUpdate(byte b) {
        if(b == 16) {
            for(int i = 0; i < 20; ++i) {
                double d0 = this.posX + MathHelper.randomFloatClamp(this.rand, -1.0f, 1.0f) * this.width;
                double d1 = this.posY + MathHelper.randomFloatClamp(this.rand, 0.0f, 1.0f) * this.height;
                double d2 = this.posZ + MathHelper.randomFloatClamp(this.rand, -1.0f, 1.0f) * this.width;
                double d3 = this.rand.nextGaussian() * 0.02;
                double d4 = 0.05 + this.rand.nextGaussian() * 0.02;
                double d5 = this.rand.nextGaussian() * 0.02;
                this.worldObj.spawnParticle("explode", d0, d1, d2, d3, d4, d5);
            }
        }
        else {
            super.handleHealthUpdate(b);
        }
    }

    public void arriveAt(EntityPlayer entityplayer) {
        ArrayList<EntityPlayer> msgPlayers = new ArrayList<>();
        if(entityplayer != null) {
            msgPlayers.add(entityplayer);
        }
        List worldPlayers = this.worldObj.playerEntities;
        for(Object obj : worldPlayers) {
            EntityPlayer player = (EntityPlayer) obj;
            if(msgPlayers.contains(player)) continue;
            double d = 64.0;
            double dSq = d * d;
            if((this.getDistanceSqToEntity(player) >= dSq)) continue;
            msgPlayers.add(player);
        }
        for(EntityPlayer player : msgPlayers) {
            LOTRSpeech.sendSpeechAndChatMessage(player, this, "char/gandalf/arrive");
        }
        this.doGandalfFX();
    }

    private void depart() {
        ArrayList<EntityPlayer> msgPlayers = new ArrayList<>();
        List worldPlayers = this.worldObj.playerEntities;
        for(Object obj : worldPlayers) {
            EntityPlayer player = (EntityPlayer) obj;
            if(msgPlayers.contains(player)) continue;
            double d = 64.0;
            double dSq = d * d;
            if((this.getDistanceSqToEntity(player) >= dSq)) continue;
            msgPlayers.add(player);
        }
        for(EntityPlayer player : msgPlayers) {
            LOTRSpeech.sendSpeechAndChatMessage(player, this, "char/gandalf/depart");
        }
        this.doGandalfFX();
        this.setDead();
    }

    @Override
    public String getSpeechBank(EntityPlayer entityplayer) {
        if(this.isFriendly(entityplayer)) {
            return "char/gandalf/friendly";
        }
        return "char/gandalf/hostile";
    }

    @Override
    public boolean speakTo(EntityPlayer entityplayer) {
        if(LOTRGreyWandererTracker.isWandererActive(this.getUniqueID())) {
            if(this.questInfo.getOfferFor(entityplayer) != null) {
                return super.speakTo(entityplayer);
            }
            if(this.addMQOfferFor(entityplayer)) {
                LOTRGreyWandererTracker.setWandererActive(this.getUniqueID());
                String speechBank = "char/gandalf/welcome";
                this.sendSpeechBank(entityplayer, speechBank);
                return true;
            }
        }
        return super.speakTo(entityplayer);
    }

    private boolean addMQOfferFor(EntityPlayer entityplayer) {
        LOTRMiniQuestWelcome quest;
        LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
        if(pd.getMiniQuestsForEntity(this, true).isEmpty() && ((LOTRMiniQuest) (quest = new LOTRMiniQuestWelcome(null, this))).canPlayerAccept(entityplayer)) {
            this.questInfo.setPlayerSpecificOffer(entityplayer, quest);
            return true;
        }
        return false;
    }
}
