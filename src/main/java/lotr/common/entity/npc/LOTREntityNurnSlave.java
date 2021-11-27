package lotr.common.entity.npc;

import lotr.common.*;
import lotr.common.entity.ai.*;
import lotr.common.fac.LOTRFaction;
import lotr.common.world.biome.LOTRBiomeGenNurn;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.IPlantable;

public class LOTREntityNurnSlave extends LOTREntityMan implements LOTRFarmhand {
    private boolean isFree = false;

    public LOTREntityNurnSlave(World world) {
        super(world);
        this.setSize(0.6f, 1.8f);
        this.getNavigator().setAvoidsWater(true);
        this.getNavigator().setBreakDoors(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new LOTREntityAIAttackOnCollide(this, 1.3, false));
        this.tasks.addTask(2, new LOTREntityAIFollowHiringPlayer(this));
        this.tasks.addTask(3, new LOTREntityAIFarm(this, 1.0, 1.0f));
        this.tasks.addTask(4, new EntityAIOpenDoor(this, true));
        this.tasks.addTask(5, new EntityAIWander(this, 1.0));
        this.tasks.addTask(6, new LOTREntityAIEat(this, LOTRFoods.NURN_SLAVE, 12000));
        this.tasks.addTask(6, new LOTREntityAIDrink(this, LOTRFoods.NURN_SLAVE_DRINK, 12000));
        this.tasks.addTask(7, new EntityAIWatchClosest2(this, EntityPlayer.class, 8.0f, 0.02f));
        this.tasks.addTask(7, new EntityAIWatchClosest2(this, LOTREntityNPC.class, 5.0f, 0.02f));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityLiving.class, 8.0f, 0.02f));
        this.tasks.addTask(9, new EntityAILookIdle(this));
        this.targetTasks.taskEntries.clear();
        this.targetTasks.addTask(1, new LOTREntityAINPCHurtByTarget(this, false));
    }

    @Override
    public void setupNPCGender() {
        this.familyInfo.setMale(this.rand.nextBoolean());
    }

    @Override
    public void setupNPCName() {
        this.familyInfo.setName(LOTRNames.getGondorName(this.rand, this.familyInfo.isMale()));
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
        this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.hoeOrc));
        this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
        return data;
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
    public IPlantable getUnhiredSeeds() {
        return (IPlantable) (Items.wheat_seeds);
    }

    @Override
    public LOTRFaction getFaction() {
        return LOTRFaction.GONDOR;
    }

    @Override
    public LOTRFaction getHiringFaction() {
        if(!this.isFree) {
            return LOTRFaction.MORDOR;
        }
        return super.getHiringFaction();
    }

    @Override
    public boolean canBeFreelyTargetedBy(EntityLiving attacker) {
        if(!this.isFree && !LOTRMod.getNPCFaction(attacker).isBadRelation(this.getHiringFaction())) {
            return false;
        }
        return super.canBeFreelyTargetedBy(attacker);
    }

    @Override
    public String getNPCName() {
        return this.familyInfo.getName();
    }

    @Override
    protected void dropFewItems(boolean flag, int i) {
        super.dropFewItems(flag, i);
        int bones = this.rand.nextInt(2) + this.rand.nextInt(i + 1);
        for(int l = 0; l < bones; ++l) {
            this.dropItem(Items.bone, 1);
        }
    }

    @Override
    public float getBlockPathWeight(int i, int j, int k) {
        float f = 0.0f;
        BiomeGenBase biome = this.worldObj.getBiomeGenForCoords(i, k);
        if(!this.isFree && biome instanceof LOTRBiomeGenNurn) {
            f += 20.0f;
        }
        return f;
    }

    @Override
    public String getSpeechBank(EntityPlayer entityplayer) {
        if(this.isFree) {
            if(this.isFriendly(entityplayer)) {
                if(this.hiredNPCInfo.getHiringPlayer() == entityplayer) {
                    return "mordor/nurnSlave/free_hired";
                }
                return "mordor/nurnSlave/free_friendly";
            }
            return "mordor/nurnSlave/free_hostile";
        }
        if(this.hiredNPCInfo.getHiringPlayer() == entityplayer) {
            return "mordor/nurnSlave/hired";
        }
        return "mordor/nurnSlave/neutral";
    }
}
