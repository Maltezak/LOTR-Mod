package lotr.common.entity.npc;

import lotr.common.*;
import lotr.common.entity.ai.*;
import lotr.common.fac.LOTRFaction;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;

public class LOTREntityHaradSlave extends LOTREntityMan implements LOTRFarmhand {
    public Item seedsItem;

    public LOTREntityHaradSlave(World world) {
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
        this.tasks.addTask(6, new LOTREntityAIEat(this, LOTRFoods.HARAD_SLAVE, 12000));
        this.tasks.addTask(6, new LOTREntityAIDrink(this, LOTRFoods.HARAD_SLAVE_DRINK, 8000));
        this.tasks.addTask(7, new EntityAIWatchClosest2(this, EntityPlayer.class, 8.0f, 0.02f));
        this.tasks.addTask(7, new EntityAIWatchClosest2(this, LOTREntityNPC.class, 5.0f, 0.02f));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityLiving.class, 8.0f, 0.02f));
        this.tasks.addTask(9, new EntityAILookIdle(this));
        this.targetTasks.taskEntries.clear();
        this.targetTasks.addTask(1, new LOTREntityAINPCHurtByTarget(this, false));
    }

    @Override
    public void setupNPCGender() {
        this.familyInfo.setMale(true);
    }

    @Override
    public void setupNPCName() {
        this.dataWatcher.addObject(20, (byte) 0);
        float f = this.rand.nextFloat();
        if(f < 0.05f) {
            this.setSlaveType(SlaveType.TAURETHRIM);
        }
        else if(f < 0.2f) {
            this.setSlaveType(SlaveType.MORWAITH);
        }
        else if(f < 0.7f) {
            this.setSlaveType(SlaveType.NEAR_HARAD);
        }
        else {
            this.setSlaveType(SlaveType.GONDOR);
        }
        SlaveType type = this.getSlaveType();
        if(type == SlaveType.GONDOR) {
            this.familyInfo.setName(LOTRNames.getGondorName(this.rand, this.familyInfo.isMale()));
        }
        else if(type == SlaveType.NEAR_HARAD) {
            if(this.rand.nextBoolean()) {
                this.familyInfo.setName(LOTRNames.getHarnennorName(this.rand, this.familyInfo.isMale()));
            }
            else {
                this.familyInfo.setName(LOTRNames.getNomadName(this.rand, this.familyInfo.isMale()));
            }
        }
        else if(type == SlaveType.MORWAITH) {
            this.familyInfo.setName(LOTRNames.getMoredainName(this.rand, this.familyInfo.isMale()));
        }
        else if(type == SlaveType.TAURETHRIM) {
            this.familyInfo.setName(LOTRNames.getTauredainName(this.rand, this.familyInfo.isMale()));
        }
    }

    public SlaveType getSlaveType() {
        int i = this.dataWatcher.getWatchableObjectByte(20);
        i = MathHelper.clamp_int(i, 0, SlaveType.values().length);
        return SlaveType.values()[i];
    }

    public void setSlaveType(SlaveType t) {
        int i = t.ordinal();
        this.dataWatcher.updateObject(20, (byte) i);
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
        this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.hoeBronze));
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
        if(this.seedsItem == null) {
            return (IPlantable) (Items.wheat_seeds);
        }
        return (IPlantable) (this.seedsItem);
    }

    @Override
    public LOTRFaction getFaction() {
        return this.getSlaveType().faction;
    }

    @Override
    public LOTRFaction getHiringFaction() {
        return LOTRFaction.NEAR_HARAD;
    }

    @Override
    public boolean canBeFreelyTargetedBy(EntityLiving attacker) {
        if(!LOTRMod.getNPCFaction(attacker).isBadRelation(this.getHiringFaction())) {
            return false;
        }
        return super.canBeFreelyTargetedBy(attacker);
    }

    @Override
    public String getNPCName() {
        return this.familyInfo.getName();
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        nbt.setString("SlaveType", this.getSlaveType().saveName());
        if(this.seedsItem != null) {
            nbt.setInteger("SeedsID", Item.getIdFromItem(this.seedsItem));
        }
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt) {
        Item item;
        SlaveType type;
        super.readEntityFromNBT(nbt);
        if(nbt.hasKey("SlaveType") && (type = SlaveType.forName(nbt.getString("SlaveType"))) != null) {
            this.setSlaveType(type);
        }
        if(nbt.hasKey("SeedsID") && (item = Item.getItemById(nbt.getInteger("SeedsID"))) != null && item instanceof IPlantable) {
            this.seedsItem = item;
        }
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
    public String getSpeechBank(EntityPlayer entityplayer) {
        if(this.hiredNPCInfo.getHiringPlayer() == entityplayer) {
            return "nearHarad/slave/hired";
        }
        return "nearHarad/slave/neutral";
    }

    public enum SlaveType {
        GONDOR(LOTRFaction.GONDOR, "gondor"),
        NEAR_HARAD(LOTRFaction.NEAR_HARAD, "nearHarad"),
        MORWAITH(LOTRFaction.MORWAITH, "morwaith"),
        TAURETHRIM(LOTRFaction.TAURETHRIM, "taurethrim");

        public LOTRFaction faction;
        public String skinDir;

        SlaveType(LOTRFaction f, String s) {
            this.faction = f;
            this.skinDir = s;
        }

        public String saveName() {
            return this.name();
        }

        public static SlaveType forName(String s) {
            for(SlaveType type : SlaveType.values()) {
                if(!type.saveName().equals(s)) continue;
                return type;
            }
            return null;
        }
    }

}
