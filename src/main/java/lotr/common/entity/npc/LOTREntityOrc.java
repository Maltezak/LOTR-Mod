package lotr.common.entity.npc;

import java.util.List;

import lotr.common.*;
import lotr.common.block.LOTRBlockOrcBomb;
import lotr.common.entity.ai.*;
import lotr.common.entity.animal.LOTREntityRabbit;
import lotr.common.entity.item.LOTREntityOrcBomb;
import lotr.common.item.LOTRItemMug;
import lotr.common.world.biome.LOTRBiome;
import net.minecraft.block.Block;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.*;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public abstract class LOTREntityOrc extends LOTREntityNPC {
    public boolean isWeakOrc = true;
    private int orcSkirmishTick;
    public EntityLivingBase currentRevengeTarget;

    public LOTREntityOrc(World world) {
        super(world);
        this.setSize(0.5f, 1.55f);
        this.getNavigator().setAvoidsWater(true);
        this.getNavigator().setBreakDoors(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new LOTREntityAIHiredRemainStill(this));
        this.tasks.addTask(2, new EntityAIAvoidEntity(this, LOTREntityOrcBomb.class, 12.0f, 1.5, 2.0));
        this.tasks.addTask(3, new LOTREntityAIOrcAvoidGoodPlayer(this, 8.0f, 1.5));
        this.tasks.addTask(4, this.createOrcAttackAI());
        this.tasks.addTask(5, new LOTREntityAIFollowHiringPlayer(this));
        this.tasks.addTask(6, new EntityAIOpenDoor(this, true));
        this.tasks.addTask(7, new EntityAIWander(this, 1.0));
        this.tasks.addTask(8, new LOTREntityAIEat(this, LOTRFoods.ORC, 6000));
        this.tasks.addTask(8, new LOTREntityAIDrink(this, LOTRFoods.ORC_DRINK, 6000));
        this.tasks.addTask(9, new EntityAIWatchClosest2(this, EntityPlayer.class, 8.0f, 0.05f));
        this.tasks.addTask(9, new EntityAIWatchClosest2(this, LOTREntityNPC.class, 5.0f, 0.05f));
        this.tasks.addTask(10, new EntityAIWatchClosest(this, EntityLiving.class, 8.0f, 0.02f));
        this.tasks.addTask(11, new EntityAILookIdle(this));
        int target = this.addTargetTasks(true, LOTREntityAINearestAttackableTargetOrc.class);
        this.targetTasks.addTask(target + 1, new LOTREntityAIOrcSkirmish(this, true));
        if(!this.isOrcBombardier()) {
            this.targetTasks.addTask(target + 2, new LOTREntityAINearestAttackableTargetOrc(this, LOTREntityRabbit.class, 2000, false));
        }
        this.spawnsInDarkness = true;
    }

    public abstract EntityAIBase createOrcAttackAI();

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(17, (byte) -1);
    }

    public boolean isOrcBombardier() {
        return false;
    }

    @Override
    public void setupNPCName() {
        this.familyInfo.setName(LOTRNames.getOrcName(this.rand));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(18.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.2);
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        return data;
    }

    @Override
    protected void onAttackModeChange(LOTREntityNPC.AttackMode mode, boolean mounted) {
        if(this.npcItemsInv.getBomb() != null) {
            this.setCurrentItemOrArmor(0, this.npcItemsInv.getBombingItem());
        }
        else if(mode == LOTREntityNPC.AttackMode.IDLE) {
            this.setCurrentItemOrArmor(0, this.npcItemsInv.getIdleItem());
        }
        else {
            this.setCurrentItemOrArmor(0, this.npcItemsInv.getMeleeWeapon());
        }
    }

    @Override
    public int getTotalArmorValue() {
        if(this.isWeakOrc) {
            return MathHelper.floor_double(super.getTotalArmorValue() * 0.75);
        }
        return super.getTotalArmorValue();
    }

    @Override
    public String getNPCName() {
        return this.familyInfo.getName();
    }

    @Override
    public void setRevengeTarget(EntityLivingBase entity) {
        super.setRevengeTarget(entity);
        if(entity != null) {
            this.currentRevengeTarget = entity;
        }
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if(!this.worldObj.isRemote && this.getAttackTarget() == null) {
            this.currentRevengeTarget = null;
        }
        if(!this.worldObj.isRemote && this.isWeakOrc) {
            boolean flag;
            int i = MathHelper.floor_double(this.posX);
            int j = MathHelper.floor_double(this.boundingBox.minY);
            int k = MathHelper.floor_double(this.posZ);
            BiomeGenBase biome = this.worldObj.getBiomeGenForCoords(i, k);
            flag = this.worldObj.isDaytime() && this.worldObj.canBlockSeeTheSky(i, j, k);
            if(biome instanceof LOTRBiome && ((LOTRBiome) biome).canSpawnHostilesInDay()) {
                flag = false;
            }
            if(flag && this.ticksExisted % 20 == 0) {
                this.addPotionEffect(new PotionEffect(Potion.resistance.id, 200, -1));
                this.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 200));
            }
        }
        if(!this.worldObj.isRemote && this.isOrcSkirmishing()) {
            if(!LOTRConfig.enableOrcSkirmish) {
                this.orcSkirmishTick = 0;
            }
            else if(!(this.getAttackTarget() instanceof LOTREntityOrc)) {
                --this.orcSkirmishTick;
            }
        }
        if(this.isOrcBombardier()) {
            if(!this.worldObj.isRemote) {
                ItemStack bomb = this.npcItemsInv.getBomb();
                int meta = -1;
                if(bomb != null && Block.getBlockFromItem(bomb.getItem()) instanceof LOTRBlockOrcBomb) {
                    meta = bomb.getItemDamage();
                }
                this.dataWatcher.updateObject(17, (byte) meta);
            }
            else {
                byte meta = this.dataWatcher.getWatchableObjectByte(17);
                if(meta == -1) {
                    this.npcItemsInv.setBomb(null);
                }
                else {
                    this.npcItemsInv.setBomb(new ItemStack(LOTRMod.orcBomb, 1, meta));
                }
            }
        }
    }

    public boolean canOrcSkirmish() {
        return !this.questInfo.anyActiveQuestPlayers();
    }

    public boolean isOrcSkirmishing() {
        return this.orcSkirmishTick > 0;
    }

    public void setOrcSkirmishing() {
        int prevSkirmishTick = this.orcSkirmishTick;
        this.orcSkirmishTick = 160;
        if(!this.worldObj.isRemote && prevSkirmishTick == 0) {
            List nearbyPlayers = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, this.boundingBox.expand(24.0, 24.0, 24.0));
            for(Object nearbyPlayer : nearbyPlayers) {
                EntityPlayer entityplayer = (EntityPlayer) nearbyPlayer;
                LOTRSpeech.sendSpeech(entityplayer, this, LOTRSpeech.getRandomSpeechForPlayer(this, this.getOrcSkirmishSpeech(), entityplayer));
            }
        }
    }

    protected String getOrcSkirmishSpeech() {
        return "";
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        nbt.setInteger("OrcSkirmish", this.orcSkirmishTick);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        if(nbt.hasKey("OrcName")) {
            this.familyInfo.setName(nbt.getString("OrcName"));
        }
        this.orcSkirmishTick = nbt.getInteger("OrcSkirmish");
    }

    @Override
    protected float getPoisonedArrowChance() {
        return 0.06666667f;
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
            this.dropItem(LOTRMod.orcBone, 1);
        }
        if(this.rand.nextInt(10) == 0) {
            int breads = 1 + this.rand.nextInt(2) + this.rand.nextInt(i + 1);
            for(int l = 0; l < breads; ++l) {
                this.dropItem(LOTRMod.maggotyBread, 1);
            }
        }
        if(flag) {
            int rareDropChance = 20 - i * 4;
            if(this.rand.nextInt(rareDropChance = Math.max(rareDropChance, 1)) == 0) {
                int dropType = this.rand.nextInt(2);
                if(dropType == 0) {
                    ItemStack orcDrink = new ItemStack(LOTRMod.mugOrcDraught);
                    orcDrink.setItemDamage(1 + this.rand.nextInt(3));
                    LOTRItemMug.setVessel(orcDrink, LOTRFoods.ORC_DRINK.getRandomVessel(this.rand), true);
                    this.entityDropItem(orcDrink, 0.0f);
                }
                else if(dropType == 1) {
                    int ingots = 1 + this.rand.nextInt(2) + this.rand.nextInt(i + 1);
                    for(int l = 0; l < ingots; ++l) {
                        if(this instanceof LOTREntityUrukHai || this instanceof LOTREntityGundabadUruk) {
                            this.dropItem(LOTRMod.urukSteel, 1);
                            continue;
                        }
                        if(this instanceof LOTREntityBlackUruk) {
                            this.dropItem(LOTRMod.blackUrukSteel, 1);
                            continue;
                        }
                        this.dropItem(LOTRMod.orcSteel, 1);
                    }
                }
            }
        }
        this.dropOrcItems(flag, i);
    }

    protected void dropOrcItems(boolean flag, int i) {
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
            if(biome instanceof LOTRBiome && ((LOTRBiome) biome).isDwarvenBiome(this.worldObj)) {
                return this.worldObj.getBlock(i, j - 1, k) == biome.topBlock;
            }
            return true;
        }
        return false;
    }

    @Override
    protected String getLivingSound() {
        return "lotr:orc.say";
    }

    @Override
    protected String getHurtSound() {
        return "lotr:orc.hurt";
    }

    @Override
    protected String getDeathSound() {
        return "lotr:orc.death";
    }

    @Override
    public ItemStack getHeldItemLeft() {
        if(this.isOrcBombardier() && this.npcItemsInv.getBomb() != null) {
            return this.npcItemsInv.getBomb();
        }
        return super.getHeldItemLeft();
    }
}
