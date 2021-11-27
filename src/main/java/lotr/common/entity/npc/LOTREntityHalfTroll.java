package lotr.common.entity.npc;

import lotr.common.*;
import lotr.common.entity.ai.*;
import lotr.common.entity.animal.LOTREntityRabbit;
import lotr.common.fac.LOTRFaction;
import lotr.common.item.LOTRMaterial;
import lotr.common.quest.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class LOTREntityHalfTroll extends LOTREntityNPC {
    public LOTREntityHalfTroll(World world) {
        super(world);
        this.setSize(1.0f, 2.4f);
        this.getNavigator().setAvoidsWater(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new LOTREntityAIHiredRemainStill(this));
        this.tasks.addTask(2, this.createHalfTrollAttackAI());
        this.tasks.addTask(3, new LOTREntityAIFollowHiringPlayer(this));
        this.tasks.addTask(4, new EntityAIWander(this, 1.0));
        this.tasks.addTask(5, new LOTREntityAIEat(this, LOTRFoods.HALF_TROLL, 6000));
        this.tasks.addTask(5, new LOTREntityAIDrink(this, LOTRFoods.HALF_TROLL_DRINK, 6000));
        this.tasks.addTask(6, new EntityAIWatchClosest2(this, EntityPlayer.class, 8.0f, 0.02f));
        this.tasks.addTask(6, new EntityAIWatchClosest2(this, LOTREntityNPC.class, 5.0f, 0.02f));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityLiving.class, 8.0f, 0.02f));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        int target = this.addTargetTasks(true);
        this.targetTasks.addTask(target + 1, new LOTREntityAINearestAttackableTargetBasic(this, LOTREntityRabbit.class, 1000, false));
        this.spawnsInDarkness = true;
    }

    public EntityAIBase createHalfTrollAttackAI() {
        return new LOTREntityAIAttackOnCollide(this, 1.4, false);
    }

    @Override
    public void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(17, (byte) 0);
    }

    @Override
    public void setupNPCName() {
        this.familyInfo.setName(LOTRNames.getOrcName(this.rand));
    }

    private boolean getHalfTrollModelFlag(int part) {
        byte i = this.dataWatcher.getWatchableObjectByte(17);
        return (i & (1 << part)) != 0;
    }

    private void setHalfTrollModelFlag(int part, boolean flag) {
        int i = this.dataWatcher.getWatchableObjectByte(17);
        int pow2 = 1 << part;
        i = flag ? (i |= pow2) : (i &= ~pow2);
        this.dataWatcher.updateObject(17, (byte) i);
    }

    public boolean hasMohawk() {
        return this.getHalfTrollModelFlag(1);
    }

    public void setHasMohawk(boolean flag) {
        this.setHalfTrollModelFlag(1, flag);
    }

    public boolean hasHorns() {
        return this.getHalfTrollModelFlag(2);
    }

    public void setHasHorns(boolean flag) {
        this.setHalfTrollModelFlag(2, flag);
    }

    public boolean hasFullHorns() {
        return this.getHalfTrollModelFlag(3);
    }

    public void setHasFullHorns(boolean flag) {
        this.setHalfTrollModelFlag(3, flag);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(35.0);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(24.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.2);
        this.getEntityAttribute(npcAttackDamage).setBaseValue(6.0);
        this.getEntityAttribute(horseAttackSpeed).setBaseValue(1.5);
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        this.setHasMohawk(this.rand.nextBoolean());
        if(this.rand.nextBoolean()) {
            this.setHasHorns(true);
            this.setHasFullHorns(this.rand.nextBoolean());
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
        return LOTRFaction.HALF_TROLL;
    }

    @Override
    public String getNPCName() {
        return this.familyInfo.getName();
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        nbt.setBoolean("Mohawk", this.hasMohawk());
        nbt.setBoolean("Horns", this.hasHorns());
        nbt.setBoolean("HornsFull", this.hasFullHorns());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        this.setHasMohawk(nbt.getBoolean("Mohawk"));
        this.setHasHorns(nbt.getBoolean("Horns"));
        this.setHasFullHorns(nbt.getBoolean("HornsFull"));
        if(nbt.hasKey("HalfTrollName")) {
            this.familyInfo.setName(nbt.getString("HalfTrollName"));
        }
    }

    @Override
    protected void dropFewItems(boolean flag, int i) {
        super.dropFewItems(flag, i);
        int flesh = this.rand.nextInt(3) + this.rand.nextInt(i + 1);
        for(int l = 0; l < flesh; ++l) {
            this.dropItem(Items.rotten_flesh, 1);
        }
        int bones = this.rand.nextInt(2) + this.rand.nextInt(i + 1);
        for(int l = 0; l < bones; ++l) {
            this.dropItem(LOTRMod.trollBone, 1);
        }
    }

    @Override
    public float getAlignmentBonus() {
        return 1.0f;
    }

    @Override
    protected LOTRAchievement getKillAchievement() {
        return LOTRAchievement.killHalfTroll;
    }

    @Override
    protected String getLivingSound() {
        return "lotr:halfTroll.say";
    }

    @Override
    protected String getHurtSound() {
        return "lotr:halfTroll.hurt";
    }

    @Override
    protected String getDeathSound() {
        return "lotr:halfTroll.death";
    }

    @Override
    public String getSpeechBank(EntityPlayer entityplayer) {
        if(this.isFriendly(entityplayer)) {
            if(this.hiredNPCInfo.getHiringPlayer() == entityplayer) {
                return "halfTroll/halfTroll/hired";
            }
            return "halfTroll/halfTroll/friendly";
        }
        return "halfTroll/halfTroll/hostile";
    }

    @Override
    public LOTRMiniQuest createMiniQuest() {
        return LOTRMiniQuestFactory.HALF_TROLL.createQuest(this);
    }

    @Override
    public LOTRMiniQuestFactory getBountyHelpSpeechDir() {
        return LOTRMiniQuestFactory.HALF_TROLL;
    }

    @Override
    public boolean canReEquipHired(int slot, ItemStack itemstack) {
        block3: {
            block2: {
                if(slot == 0) break block2;
                if(slot == 1) break block2;
                if(slot == 2) break block2;
                if(slot != 3) break block3;
            }
            return itemstack != null && itemstack.getItem() instanceof ItemArmor && ((ItemArmor) itemstack.getItem()).getArmorMaterial() == LOTRMaterial.HALF_TROLL.toArmorMaterial();
        }
        return super.canReEquipHired(slot, itemstack);
    }
}
