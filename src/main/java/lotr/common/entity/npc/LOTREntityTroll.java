package lotr.common.entity.npc;

import cpw.mods.fml.relauncher.*;
import lotr.common.*;
import lotr.common.entity.ai.*;
import lotr.common.entity.item.LOTREntityStoneTroll;
import lotr.common.fac.LOTRFaction;
import lotr.common.world.biome.LOTRBiome;
import net.minecraft.block.Block;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.*;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class LOTREntityTroll extends LOTREntityNPC {
    private int sneeze;
    public int sniffTime;
    public boolean trollImmuneToSun = false;

    public LOTREntityTroll(World world) {
        super(world);
        float f = this.getTrollScale();
        this.setSize(1.6f * f, 3.2f * f);
        this.getNavigator().setAvoidsWater(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIRestrictSun(this));
        this.tasks.addTask(2, new LOTREntityAIHiredRemainStill(this));
        this.tasks.addTask(3, new LOTREntityAITrollFleeSun(this, 2.5));
        this.tasks.addTask(4, this.getTrollAttackAI());
        this.tasks.addTask(5, new LOTREntityAIFollowHiringPlayer(this));
        this.tasks.addTask(6, new EntityAIWander(this, 1.0));
        this.tasks.addTask(7, new EntityAIWatchClosest2(this, EntityPlayer.class, 12.0f, 0.02f));
        this.tasks.addTask(7, new EntityAIWatchClosest2(this, LOTREntityNPC.class, 8.0f, 0.02f));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityLiving.class, 12.0f, 0.01f));
        this.tasks.addTask(9, new EntityAILookIdle(this));
        this.addTargetTasks(true, LOTREntityAINearestAttackableTargetTroll.class);
        this.spawnsInDarkness = true;
    }

    public float getTrollScale() {
        return 1.0f;
    }

    public EntityAIBase getTrollAttackAI() {
        return new LOTREntityAIAttackOnCollide(this, 1.4, false);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, (byte) this.rand.nextInt(3));
        this.dataWatcher.addObject(17, (short) -1);
        this.dataWatcher.addObject(18, (byte) 0);
        this.dataWatcher.addObject(19, (byte) 0);
    }

    @Override
    public void setupNPCName() {
        this.familyInfo.setName(LOTRNames.getTrollName(this.rand));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(60.0);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(24.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.2);
        this.getEntityAttribute(npcAttackDamage).setBaseValue(5.0);
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        if(this.rand.nextInt(10) == 0) {
            this.setHasTwoHeads(true);
            double maxHealth = this.getEntityAttribute(SharedMonsterAttributes.maxHealth).getBaseValue();
            this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(maxHealth *= 1.5);
            this.setHealth(this.getMaxHealth());
            double attack = this.getEntityAttribute(npcAttackDamage).getBaseValue();
            this.getEntityAttribute(npcAttackDamage).setBaseValue(attack += 3.0);
            double speed = this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getBaseValue();
            this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(speed *= 1.4);
        }
        return data;
    }

    @Override
    public int getTotalArmorValue() {
        return 8;
    }

    @Override
    public LOTRFaction getFaction() {
        return LOTRFaction.ANGMAR;
    }

    protected boolean hasTrollName() {
        return true;
    }

    @Override
    public String getNPCName() {
        if(this.hasTrollName()) {
            return this.familyInfo.getName();
        }
        return super.getNPCName();
    }

    public int getTrollOutfit() {
        return this.dataWatcher.getWatchableObjectByte(16);
    }

    public void setTrollOutfit(int i) {
        this.dataWatcher.updateObject(16, (byte) i);
    }

    public int getTrollBurnTime() {
        return this.dataWatcher.getWatchableObjectShort(17);
    }

    public void setTrollBurnTime(int i) {
        this.dataWatcher.updateObject(17, (short) i);
    }

    public int getSneezingTime() {
        return this.dataWatcher.getWatchableObjectByte(18);
    }

    public void setSneezingTime(int i) {
        this.dataWatcher.updateObject(18, (byte) i);
    }

    public boolean hasTwoHeads() {
        return this.dataWatcher.getWatchableObjectByte(19) == 1;
    }

    public void setHasTwoHeads(boolean flag) {
        this.dataWatcher.updateObject(19, flag ? (byte) 1 : 0);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        nbt.setByte("TrollOutfit", (byte) this.getTrollOutfit());
        nbt.setInteger("TrollBurnTime", this.getTrollBurnTime());
        nbt.setInteger("Sneeze", this.sneeze);
        nbt.setInteger("SneezeTime", this.getSneezingTime());
        nbt.setBoolean("ImmuneToSun", this.trollImmuneToSun);
        nbt.setBoolean("TwoHeads", this.hasTwoHeads());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        this.setTrollOutfit(nbt.getByte("TrollOutfit"));
        this.setTrollBurnTime(nbt.getInteger("TrollBurnTime"));
        this.sneeze = nbt.getInteger("Sneeze");
        this.setSneezingTime(nbt.getInteger("SneezeTime"));
        this.trollImmuneToSun = nbt.getBoolean("ImmuneToSun");
        this.setHasTwoHeads(nbt.getBoolean("TwoHeads"));
        if(nbt.hasKey("TrollName")) {
            this.familyInfo.setName(nbt.getString("TrollName"));
        }
    }

    @Override
    protected boolean conquestSpawnIgnoresDarkness() {
        return this.trollImmuneToSun;
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if(this.getTrollBurnTime() >= 0 && this.isEntityAlive()) {
            if(!this.worldObj.isRemote) {
                BiomeGenBase biome = this.worldObj.getBiomeGenForCoords(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posZ));
                if(this.trollImmuneToSun || biome instanceof LOTRBiome && ((LOTRBiome) biome).canSpawnHostilesInDay() || !this.worldObj.isDaytime() || !this.worldObj.canBlockSeeTheSky(MathHelper.floor_double(this.posX), (int) this.boundingBox.minY, MathHelper.floor_double(this.posZ))) {
                    this.setTrollBurnTime(-1);
                }
                else {
                    this.setTrollBurnTime(this.getTrollBurnTime() - 1);
                    if(this.getTrollBurnTime() == 0) {
                        this.onTrollDeathBySun();
                        if(this.hiredNPCInfo.isActive && this.hiredNPCInfo.getHiringPlayer() != null) {
                            this.hiredNPCInfo.getHiringPlayer().addChatMessage(new ChatComponentTranslation("lotr.hiredNPC.trollStone", this.getCommandSenderName()));
                        }
                    }
                }
            }
            else {
                this.worldObj.spawnParticle("largesmoke", this.posX + (this.rand.nextDouble() - 0.5) * this.width, this.posY + this.rand.nextDouble() * this.height, this.posZ + (this.rand.nextDouble() - 0.5) * this.width, 0.0, 0.0, 0.0);
            }
        }
        if(this.sniffTime > 0) {
            --this.sniffTime;
        }
        if(!this.worldObj.isRemote && this.getSneezingTime() > 0) {
            this.setSneezingTime(this.getSneezingTime() - 1);
            if(this.getSneezingTime() == 8) {
                this.worldObj.playSoundAtEntity(this, "lotr:troll.sneeze", this.getSoundVolume() * 1.5f, this.getSoundPitch());
            }
            if(this.getSneezingTime() == 4) {
                int slimes = 2 + this.rand.nextInt(3);
                for(int i = 0; i < slimes; ++i) {
                    EntityItem entityitem = new EntityItem(this.worldObj, this.posX, this.posY + this.getEyeHeight(), this.posZ, new ItemStack(Items.slime_ball));
                    entityitem.delayBeforeCanPickup = 40;
                    float f = 1.0f;
                    entityitem.motionX = -MathHelper.sin(this.rotationYawHead / 180.0f * 3.1415927f) * MathHelper.cos(this.rotationPitch / 180.0f * 3.1415927f) * f;
                    entityitem.motionZ = MathHelper.cos(this.rotationYawHead / 180.0f * 3.1415927f) * MathHelper.cos(this.rotationPitch / 180.0f * 3.1415927f) * f;
                    entityitem.motionY = -MathHelper.sin(this.rotationPitch / 180.0f * 3.1415927f) * f + 0.1f;
                    f = 0.02f;
                    float f1 = this.rand.nextFloat() * 3.1415927f * 2.0f;
                    entityitem.motionX += Math.cos(f1) * (f *= this.rand.nextFloat());
                    entityitem.motionY += (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1f;
                    entityitem.motionZ += Math.sin(f1) * f;
                    this.worldObj.spawnEntityInWorld(entityitem);
                }
            }
            if(this.getSneezingTime() == 0) {
                this.sneeze = 0;
            }
        }
    }

    public void onTrollDeathBySun() {
        this.worldObj.playSoundAtEntity(this, "lotr:troll.transform", this.getSoundVolume(), this.getSoundPitch());
        this.worldObj.setEntityState(this, (byte) 15);
        this.setDead();
        LOTREntityStoneTroll stoneTroll = new LOTREntityStoneTroll(this.worldObj);
        stoneTroll.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0f);
        stoneTroll.setTrollOutfit(this.getTrollOutfit());
        stoneTroll.setHasTwoHeads(this.hasTwoHeads());
        this.worldObj.spawnEntityInWorld(stoneTroll);
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void handleHealthUpdate(byte b) {
        if(b == 15) {
            this.spawnExplosionParticle();
        }
        else if(b == 16) {
            this.sniffTime = 16;
        }
        else {
            super.handleHealthUpdate(b);
        }
    }

    @Override
    public boolean interact(EntityPlayer entityplayer) {
        ItemStack itemstack;
        if(!this.worldObj.isRemote && this.canTrollBeTickled(entityplayer) && (itemstack = entityplayer.inventory.getCurrentItem()) != null && LOTRMod.isOreNameEqual(itemstack, "feather") && this.getSneezingTime() == 0) {
            if(this.rand.nextBoolean()) {
                ++this.sneeze;
            }
            if(!entityplayer.capabilities.isCreativeMode) {
                --itemstack.stackSize;
            }
            if(itemstack.stackSize <= 0) {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }
            this.npcTalkTick = this.getNPCTalkInterval() / 2;
            if(this.sneeze >= 3) {
                this.setSneezingTime(16);
                LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.makeTrollSneeze);
            }
            else {
                LOTRSpeech.sendSpeech(entityplayer, this, LOTRSpeech.getRandomSpeechForPlayer(this, "troll/tickle", entityplayer));
                this.worldObj.playSoundAtEntity(this, "lotr:troll.sniff", this.getSoundVolume(), this.getSoundPitch());
                this.worldObj.setEntityState(this, (byte) 16);
            }
        }
        return super.interact(entityplayer);
    }

    protected boolean canTrollBeTickled(EntityPlayer entityplayer) {
        return this.canNPCTalk() && this.isFriendly(entityplayer) && LOTRLevelData.getData(entityplayer).getAlignment(this.getFaction()) >= 100.0f && this.getAttackTarget() == null && this.getTrollBurnTime() == -1;
    }

    @Override
    public void knockBack(Entity entity, float f, double d, double d1) {
        super.knockBack(entity, f, d, d1);
        this.motionX /= 2.0;
        this.motionY /= 2.0;
        this.motionZ /= 2.0;
    }

    @Override
    public boolean attackEntityAsMob(Entity entity) {
        if(super.attackEntityAsMob(entity)) {
            float attackDamage = (float) this.getEntityAttribute(LOTREntityNPC.npcAttackDamage).getAttributeValue();
            float knockbackModifier = 0.25f * attackDamage;
            entity.addVelocity(-MathHelper.sin(this.rotationYaw * 3.1415927f / 180.0f) * knockbackModifier * 0.5f, knockbackModifier * 0.1, MathHelper.cos(this.rotationYaw * 3.1415927f / 180.0f) * knockbackModifier * 0.5f);
            return true;
        }
        return false;
    }

    @Override
    public void onDeath(DamageSource damagesource) {
        super.onDeath(damagesource);
        if(!this.worldObj.isRemote && damagesource.getEntity() instanceof EntityPlayer && this.getTrollBurnTime() >= 0) {
            LOTRLevelData.getData((EntityPlayer) damagesource.getEntity()).addAchievement(LOTRAchievement.killTrollFleeingSun);
        }
    }

    @Override
    protected LOTRAchievement getKillAchievement() {
        return LOTRAchievement.killTroll;
    }

    @Override
    public float getAlignmentBonus() {
        return 3.0f;
    }

    @Override
    protected int getExperiencePoints(EntityPlayer entityplayer) {
        return 4 + this.rand.nextInt(5);
    }

    @Override
    protected void dropFewItems(boolean flag, int i) {
        super.dropFewItems(flag, i);
        int bones = 2 + this.rand.nextInt(3) + this.rand.nextInt(i + 1);
        for(int l = 0; l < bones; ++l) {
            this.dropItem(LOTRMod.trollBone, 1);
        }
        this.dropTrollItems(flag, i);
    }

    public void dropTrollItems(boolean flag, int i) {
        if(this.rand.nextInt(3) == 0) {
            int j = 1 + this.rand.nextInt(3) + this.rand.nextInt(i + 1);
            for(int k = 0; k < j; ++k) {
                this.dropItem(Items.slime_ball, 1);
            }
        }
        int animalDrops = 1 + this.rand.nextInt(3) + this.rand.nextInt(i + 1);
        block13: for(int l = 0; l < animalDrops; ++l) {
            int drop = this.rand.nextInt(10);
            switch(drop) {
                case 0: {
                    this.entityDropItem(new ItemStack(Items.leather, 1 + this.rand.nextInt(3)), 0.0f);
                    continue block13;
                }
                case 1: {
                    this.entityDropItem(new ItemStack(Items.beef, 1 + this.rand.nextInt(2)), 0.0f);
                    continue block13;
                }
                case 2: {
                    this.entityDropItem(new ItemStack(Items.chicken, 1 + this.rand.nextInt(2)), 0.0f);
                    continue block13;
                }
                case 3: {
                    this.entityDropItem(new ItemStack(Items.feather, 1 + this.rand.nextInt(3)), 0.0f);
                    continue block13;
                }
                case 4: {
                    this.entityDropItem(new ItemStack(Items.porkchop, 1 + this.rand.nextInt(2)), 0.0f);
                    continue block13;
                }
                case 5: {
                    this.entityDropItem(new ItemStack(Blocks.wool, 1 + this.rand.nextInt(3)), 0.0f);
                    continue block13;
                }
                case 6: {
                    this.entityDropItem(new ItemStack(Items.rotten_flesh, 1 + this.rand.nextInt(3)), 0.0f);
                    continue block13;
                }
                case 7: {
                    this.entityDropItem(new ItemStack(LOTRMod.rabbitRaw, 1 + this.rand.nextInt(2)), 0.0f);
                    continue block13;
                }
                case 8: {
                    this.entityDropItem(new ItemStack(LOTRMod.muttonRaw, 1 + this.rand.nextInt(2)), 0.0f);
                    continue block13;
                }
                case 9: {
                    this.entityDropItem(new ItemStack(LOTRMod.deerRaw, 1 + this.rand.nextInt(2)), 0.0f);
                }
            }
        }
    }

    @Override
    public String getLivingSound() {
        return "lotr:troll.say";
    }

    @Override
    public String getHurtSound() {
        return "lotr:troll.say";
    }

    @Override
    public String getDeathSound() {
        return "lotr:troll.say";
    }

    @Override
    protected float getSoundVolume() {
        return 1.5f;
    }

    @Override
    protected void func_145780_a(int i, int j, int k, Block block) {
        this.playSound("lotr:troll.step", 0.75f, this.getSoundPitch());
    }

    @Override
    public String getSpeechBank(EntityPlayer entityplayer) {
        if(this.getTrollBurnTime() >= 0) {
            return null;
        }
        if(LOTRLevelData.getData(entityplayer).getAlignment(this.getFaction()) >= 100.0f && this.isFriendly(entityplayer)) {
            if(this.hiredNPCInfo.getHiringPlayer() == entityplayer) {
                return "troll/hired";
            }
            return "troll/friendly";
        }
        return "troll/hostile";
    }

    public boolean shouldRenderHeadHurt() {
        return this.hurtTime > 0 || this.getSneezingTime() > 0;
    }

    @Override
    public boolean canReEquipHired(int slot, ItemStack itemstack) {
        return false;
    }
}
