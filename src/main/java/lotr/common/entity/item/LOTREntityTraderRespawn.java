package lotr.common.entity.item;

import cpw.mods.fml.relauncher.*;
import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntities;
import lotr.common.entity.npc.*;
import net.minecraft.block.Block;
import net.minecraft.entity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class LOTREntityTraderRespawn
extends Entity {
    private static int MAX_SCALE = 40;
    private int timeUntilSpawn;
    private int prevBobbingTime;
    private int bobbingTime;
    private String traderClassID;
    private boolean traderHasHome;
    private int traderHomeX;
    private int traderHomeY;
    private int traderHomeZ;
    private float traderHomeRadius;
    private String traderLocationName;
    private boolean shouldTraderRespawn;
    private NBTTagCompound traderData;
    public float spawnerSpin;
    public float prevSpawnerSpin;

    public LOTREntityTraderRespawn(World world) {
        super(world);
        this.setSize(0.75f, 0.75f);
        this.spawnerSpin = this.rand.nextFloat() * 360.0f;
    }

    protected void entityInit() {
        this.dataWatcher.addObject(16, 0);
        this.dataWatcher.addObject(17, (byte) 0);
        this.dataWatcher.addObject(18, "");
    }

    public int getScale() {
        return this.dataWatcher.getWatchableObjectInt(16);
    }

    public void setScale(int i) {
        this.dataWatcher.updateObject(16, i);
    }

    public boolean isSpawnImminent() {
        return this.dataWatcher.getWatchableObjectByte(17) == 1;
    }

    public void setSpawnImminent() {
        this.dataWatcher.updateObject(17, 1);
    }

    public String getClientTraderString() {
        return this.dataWatcher.getWatchableObjectString(18);
    }

    public void setClientTraderString(String s) {
        this.dataWatcher.updateObject(18, s);
    }

    public void writeEntityToNBT(NBTTagCompound nbt) {
        nbt.setInteger("Scale", this.getScale());
        nbt.setInteger("TimeUntilSpawn", this.timeUntilSpawn);
        nbt.setString("TraderClassID", this.traderClassID);
        nbt.setBoolean("TraderHasHome", this.traderHasHome);
        nbt.setInteger("TraderHomeX", this.traderHomeX);
        nbt.setInteger("TraderHomeY", this.traderHomeY);
        nbt.setInteger("TraderHomeZ", this.traderHomeZ);
        nbt.setFloat("TraderHomeRadius", this.traderHomeRadius);
        nbt.setBoolean("TraderShouldRespawn", this.shouldTraderRespawn);
        if (this.traderLocationName != null) {
            nbt.setString("TraderLocationName", this.traderLocationName);
        }
        if (this.traderData != null) {
            nbt.setTag("TraderData", this.traderData);
        }
    }

    public void readEntityFromNBT(NBTTagCompound nbt) {
        this.setScale(nbt.getInteger("Scale"));
        this.timeUntilSpawn = nbt.getInteger("TimeUntilSpawn");
        if (this.timeUntilSpawn <= 1200) {
            this.setSpawnImminent();
        }
        this.traderClassID = nbt.getString("TraderClassID");
        this.traderHasHome = nbt.getBoolean("TraderHasHome");
        this.traderHomeX = nbt.getInteger("TraderHomeX");
        this.traderHomeY = nbt.getInteger("TraderHomeY");
        this.traderHomeZ = nbt.getInteger("TraderHomeZ");
        this.traderHomeRadius = nbt.getFloat("TraderHomeRadius");
        this.shouldTraderRespawn = nbt.hasKey("TraderShouldRespawn") ? nbt.getBoolean("TraderShouldRespawn") : true;
        if (nbt.hasKey("TraderLocationName")) {
            this.traderLocationName = nbt.getString("TraderLocationName");
        }
        if (nbt.hasKey("TraderData")) {
            this.traderData = nbt.getCompoundTag("TraderData");
        }
    }

    public void copyTraderDataFrom(LOTREntityNPC entity) {
        this.traderClassID = LOTREntities.getStringFromClass(entity.getClass());
        this.traderHasHome = entity.hasHome();
        if (this.traderHasHome) {
            ChunkCoordinates home = entity.getHomePosition();
            this.traderHomeX = home.posX;
            this.traderHomeY = home.posY;
            this.traderHomeZ = home.posZ;
            this.traderHomeRadius = entity.func_110174_bM();
        }
        this.shouldTraderRespawn = entity.shouldTraderRespawn();
        if (entity.getHasSpecificLocationName()) {
            this.traderLocationName = entity.npcLocationName;
        }
        if (entity instanceof LOTRTradeable) {
            LOTRTraderNPCInfo traderInfo = entity.traderNPCInfo;
            this.traderData = new NBTTagCompound();
            traderInfo.writeToNBT(this.traderData);
        }
    }

    public void onSpawn() {
        this.motionY = 0.25;
        this.timeUntilSpawn = MathHelper.getRandomIntegerInRange(this.rand, 10, 30) * 1200;
    }

    public boolean canBeCollidedWith() {
        return true;
    }

    public void applyEntityCollision(Entity entity) {
    }

    public boolean hitByEntity(Entity entity) {
        if (entity instanceof EntityPlayer) {
            return this.attackEntityFrom(DamageSource.causePlayerDamage(((EntityPlayer)entity)), 0.0f);
        }
        return false;
    }

    public boolean attackEntityFrom(DamageSource damagesource, float f) {
        Entity entity = damagesource.getEntity();
        if (entity instanceof EntityPlayer && ((EntityPlayer)entity).capabilities.isCreativeMode) {
            if (!this.worldObj.isRemote) {
                Block.SoundType sound = Blocks.glass.stepSound;
                this.worldObj.playSoundAtEntity(this, sound.getBreakSound(), (sound.getVolume() + 1.0f) / 2.0f, sound.getPitch() * 0.8f);
                this.worldObj.setEntityState(this, (byte)16);
                this.setDead();
            }
            return true;
        }
        return false;
    }

    @SideOnly(value=Side.CLIENT)
    public void handleHealthUpdate(byte b) {
        if (b == 16) {
            for (int l = 0; l < 16; ++l) {
                this.worldObj.spawnParticle("iconcrack_" + Item.getIdFromItem(LOTRMod.silverCoin), this.posX + (this.rand.nextDouble() - 0.5) * this.width, this.posY + this.rand.nextDouble() * this.height, this.posZ + (this.rand.nextDouble() - 0.5) * this.width, 0.0, 0.0, 0.0);
            }
        } else {
            super.handleHealthUpdate(b);
        }
    }

    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.prevSpawnerSpin = this.spawnerSpin;
        this.spawnerSpin = this.isSpawnImminent() ? (this.spawnerSpin += 24.0f) : (this.spawnerSpin += 6.0f);
        this.prevSpawnerSpin = MathHelper.wrapAngleTo180_float(this.prevSpawnerSpin);
        this.spawnerSpin = MathHelper.wrapAngleTo180_float(this.spawnerSpin);
        if (this.getScale() < MAX_SCALE) {
            if (!this.worldObj.isRemote) {
                this.setScale(this.getScale() + 1);
            }
            this.motionX = 0.0;
            this.motionY *= 0.9;
            this.motionZ = 0.0;
        } else {
            this.motionX = 0.0;
            this.motionY = 0.0;
            this.motionZ = 0.0;
        }
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        if (!this.worldObj.isRemote) {
            this.setClientTraderString(this.traderClassID);
            if (!this.isSpawnImminent() && this.timeUntilSpawn <= 1200) {
                this.setSpawnImminent();
            }
            if (this.timeUntilSpawn > 0) {
                --this.timeUntilSpawn;
            } else {
                boolean flag = false;
                Entity entity = EntityList.createEntityByName(this.traderClassID, this.worldObj);
                if (entity instanceof LOTREntityNPC) {
                    LOTREntityNPC trader = (LOTREntityNPC)entity;
                    trader.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rand.nextFloat() * 360.0f, 0.0f);
                    trader.spawnRidingHorse = false;
                    trader.liftSpawnRestrictions = true;
                    this.boundingBox.offset(0.0, 100.0, 0.0);
                    if (trader.getCanSpawnHere()) {
                        trader.liftSpawnRestrictions = false;
                        trader.onSpawnWithEgg(null);
                        if (this.traderHasHome) {
                            trader.setHomeArea(this.traderHomeX, this.traderHomeY, this.traderHomeZ, Math.round(this.traderHomeRadius));
                        }
                        if (this.traderLocationName != null) {
                            trader.setSpecificLocationName(this.traderLocationName);
                        }
                        trader.setShouldTraderRespawn(this.shouldTraderRespawn);
                        flag = this.worldObj.spawnEntityInWorld(trader);
                        if (trader instanceof LOTRTradeable && this.traderData != null) {
                            trader.traderNPCInfo.readFromNBT(this.traderData);
                        }
                    }
                    this.boundingBox.offset(0.0, -100.0, 0.0);
                }
                if (flag) {
                    this.playSound("random.pop", 1.0f, 0.5f + this.rand.nextFloat() * 0.5f);
                    this.setDead();
                } else {
                    this.timeUntilSpawn = 60;
                    this.setLocationAndAngles(this.posX, this.posY + 1.0, this.posZ, this.rotationYaw, this.rotationPitch);
                }
            }
        } else if (this.isSpawnImminent()) {
            this.prevBobbingTime = this.bobbingTime++;
        }
    }

    public float getScaleFloat(float tick) {
        float scale = this.getScale();
        if (scale < MAX_SCALE) {
            scale += tick;
        }
        return scale / MAX_SCALE;
    }

    public float getBobbingOffset(float tick) {
        float f = this.bobbingTime - this.prevBobbingTime;
        return MathHelper.sin((this.prevBobbingTime + (f *= tick)) / 5.0f) * 0.25f;
    }

    public ItemStack getPickedResult(MovingObjectPosition target) {
        int entityID = LOTREntities.getIDFromString(this.getClientTraderString());
        if (entityID > 0) {
            return new ItemStack(LOTRMod.spawnEgg, 1, entityID);
        }
        return null;
    }
}

