package lotr.common.entity;

import java.util.*;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.relauncher.*;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.fac.LOTRFaction;
import lotr.common.network.*;
import net.minecraft.block.Block;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.Blocks;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTREntityNPCRespawner
extends Entity {
    public float spawnerSpin;
    public float prevSpawnerSpin;
    public int spawnInterval = 3600;
    public int noPlayerRange = 24;
    public Class spawnClass1;
    public Class spawnClass2;
    public int checkHorizontalRange = 8;
    public int checkVerticalMin = -4;
    public int checkVerticalMax = 4;
    public int spawnCap = 4;
    public int spawnHorizontalRange = 4;
    public int spawnVerticalMin = -2;
    public int spawnVerticalMax = 2;
    public int homeRange = -1;
    private boolean setHomePosFromSpawn = false;
    public int mountSetting = 0;
    public int blockEnemySpawns = 0;
    public static final int MAX_SPAWN_BLOCK_RANGE = 64;

    public LOTREntityNPCRespawner(World world) {
        super(world);
        this.setSize(1.0f, 1.0f);
        this.spawnerSpin = this.rand.nextFloat() * 360.0f;
    }

    public void setSpawnClass(Class c) {
        this.spawnClass1 = c;
    }

    public void setSpawnClasses(Class c1, Class c2) {
        this.spawnClass1 = c1;
        this.spawnClass2 = c2;
    }

    public void setCheckRanges(int xz, int y, int y1, int l) {
        this.checkHorizontalRange = xz;
        this.checkVerticalMin = y;
        this.checkVerticalMax = y1;
        this.spawnCap = l;
    }

    public void setSpawnRanges(int xz, int y, int y1, int h) {
        this.spawnHorizontalRange = xz;
        this.spawnVerticalMin = y;
        this.spawnVerticalMax = y1;
        this.homeRange = h;
    }

    public void setHomePosFromSpawn() {
        this.setHomePosFromSpawn = true;
    }

    public boolean hasHomeRange() {
        return this.homeRange >= 0;
    }

    public void setMountSetting(int i) {
        this.mountSetting = i;
    }

    public void toggleMountSetting() {
        this.mountSetting = this.mountSetting == 0 ? 1 : (this.mountSetting == 1 ? 2 : 0);
    }

    public void setNoPlayerRange(int i) {
        this.noPlayerRange = i;
    }

    public boolean blockEnemySpawns() {
        return this.blockEnemySpawns > 0;
    }

    public void setBlockEnemySpawnRange(int i) {
        this.blockEnemySpawns = i = Math.min(i, 64);
    }

    public void setSpawnInterval(int i) {
        this.spawnInterval = i;
    }

    public void setSpawnIntervalMinutes(int m) {
        int s = m * 60;
        int t = s * 20;
        this.setSpawnInterval(t);
    }

    protected void entityInit() {
    }

    public boolean isInvisible() {
        if (!this.worldObj.isRemote) {
            return super.isInvisible();
        }
        EntityPlayer entityplayer = LOTRMod.proxy.getClientPlayer();
        return entityplayer == null || !entityplayer.capabilities.isCreativeMode;
    }

    public void writeEntityToNBT(NBTTagCompound nbt) {
        this.writeSpawnerDataToNBT(nbt);
    }

    public void writeSpawnerDataToNBT(NBTTagCompound nbt) {
        nbt.setInteger("SpawnInterval", this.spawnInterval);
        nbt.setByte("NoPlayerRange", (byte)this.noPlayerRange);
        String class1String = "";
        String class2String = "";
        if (this.spawnClass1 != null) {
            class1String = LOTREntities.getStringFromClass(this.spawnClass1);
        }
        if (this.spawnClass2 != null) {
            class2String = LOTREntities.getStringFromClass(this.spawnClass2);
        }
        nbt.setString("SpawnClass1", class1String == null ? "" : class1String);
        nbt.setString("SpawnClass2", class2String == null ? "" : class2String);
        nbt.setByte("CheckHorizontal", (byte)this.checkHorizontalRange);
        nbt.setByte("CheckVerticalMin", (byte)this.checkVerticalMin);
        nbt.setByte("CheckVerticalMax", (byte)this.checkVerticalMax);
        nbt.setByte("SpawnCap", (byte)this.spawnCap);
        nbt.setByte("SpawnHorizontal", (byte)this.spawnHorizontalRange);
        nbt.setByte("SpawnVerticalMin", (byte)this.spawnVerticalMin);
        nbt.setByte("SpawnVerticalMax", (byte)this.spawnVerticalMax);
        nbt.setByte("HomeRange", (byte)this.homeRange);
        nbt.setBoolean("HomeSpawn", this.setHomePosFromSpawn);
        nbt.setByte("MountSetting", (byte)this.mountSetting);
        nbt.setByte("BlockEnemy", (byte)this.blockEnemySpawns);
    }

    public void readEntityFromNBT(NBTTagCompound nbt) {
        this.readSpawnerDataFromNBT(nbt);
    }

    public void readSpawnerDataFromNBT(NBTTagCompound nbt) {
        this.spawnInterval = nbt.getInteger("SpawnInterval");
        if (this.spawnInterval <= 0) {
            this.spawnInterval = 3600;
        }
        this.noPlayerRange = nbt.getByte("NoPlayerRange");
        this.spawnClass1 = LOTREntities.getClassFromString(nbt.getString("SpawnClass1"));
        this.spawnClass2 = LOTREntities.getClassFromString(nbt.getString("SpawnClass2"));
        if (this.spawnClass1 != null && !LOTREntityNPC.class.isAssignableFrom(this.spawnClass1)) {
            this.spawnClass1 = null;
        }
        if (this.spawnClass2 != null && !LOTREntityNPC.class.isAssignableFrom(this.spawnClass2)) {
            this.spawnClass2 = null;
        }
        this.checkHorizontalRange = nbt.getByte("CheckHorizontal");
        this.checkVerticalMin = nbt.getByte("CheckVerticalMin");
        this.checkVerticalMax = nbt.getByte("CheckVerticalMax");
        this.spawnCap = nbt.getByte("SpawnCap");
        this.spawnHorizontalRange = nbt.getByte("SpawnHorizontal");
        this.spawnVerticalMin = nbt.getByte("SpawnVerticalMin");
        this.spawnVerticalMax = nbt.getByte("SpawnVerticalMax");
        this.homeRange = nbt.getByte("HomeRange");
        this.setHomePosFromSpawn = nbt.getBoolean("HomeSpawn");
        this.mountSetting = nbt.getByte("MountSetting");
        this.blockEnemySpawns = nbt.getByte("BlockEnemy");
    }

    public void onBreak() {
        this.worldObj.playSoundAtEntity(this, Blocks.glass.stepSound.getBreakSound(), (Blocks.glass.stepSound.getVolume() + 1.0f) / 2.0f, Blocks.glass.stepSound.getPitch() * 0.8f);
        this.worldObj.setEntityState(this, (byte)16);
        this.setDead();
    }

    @SideOnly(value=Side.CLIENT)
    public void handleHealthUpdate(byte b) {
        if (b == 16) {
            for (int l = 0; l < 16; ++l) {
                double d = this.posX + (this.rand.nextDouble() - 0.5) * this.width;
                double d1 = this.posY + this.rand.nextDouble() * this.height;
                double d2 = this.posZ + (this.rand.nextDouble() - 0.5) * this.width;
                this.worldObj.spawnParticle("iconcrack_" + Item.getIdFromItem(LOTRMod.npcRespawner), d, d1, d2, 0.0, 0.0, 0.0);
            }
        } else {
            super.handleHealthUpdate(b);
        }
    }

    public void onUpdate() {
        int maxX;
        int entities;
        int maxY;
        int minZ;
        int minX;
        int minY;
        int i;
        int j;
        int maxZ;
        int k;
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.prevSpawnerSpin = this.spawnerSpin;
        this.spawnerSpin += 6.0f;
        this.prevSpawnerSpin = MathHelper.wrapAngleTo180_float(this.prevSpawnerSpin);
        this.spawnerSpin = MathHelper.wrapAngleTo180_float(this.spawnerSpin);
        this.motionX = 0.0;
        this.motionY = 0.0;
        this.motionZ = 0.0;
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        if (!this.worldObj.isRemote && this.ticksExisted % this.spawnInterval == 0 && (this.spawnClass1 != null || this.spawnClass2 != null) && this.worldObj.checkChunksExist(minX = (i = MathHelper.floor_double(this.posX)) - this.checkHorizontalRange, minY = (j = MathHelper.floor_double(this.boundingBox.minY)) + this.checkVerticalMin, minZ = (k = MathHelper.floor_double(this.posZ)) - this.checkHorizontalRange, maxX = i + this.checkHorizontalRange, maxY = j + this.checkVerticalMax, maxZ = k + this.checkHorizontalRange) && this.worldObj.getClosestPlayer(i + 0.5, j + 0.5, k + 0.5, this.noPlayerRange) == null && (entities = (this.worldObj.selectEntitiesWithinAABB(EntityLiving.class, AxisAlignedBB.getBoundingBox(minX, minY, minZ, maxX + 1, maxY + 1, maxZ + 1), new IEntitySelector(){

            public boolean isEntityApplicable(Entity entity) {
                if (!entity.isEntityAlive()) {
                    return false;
                }
                Class<?> entityClass = entity.getClass();
                return LOTREntityNPCRespawner.this.spawnClass1 != null && LOTREntityNPCRespawner.this.spawnClass1.isAssignableFrom(entityClass) || LOTREntityNPCRespawner.this.spawnClass2 != null && LOTREntityNPCRespawner.this.spawnClass2.isAssignableFrom(entityClass);
            }
        })).size()) < this.spawnCap) {
            int attempts = 16;
            for (int l = 0; l < attempts; ++l) {
                int spawnX = i + MathHelper.getRandomIntegerInRange(this.rand, (-this.spawnHorizontalRange), this.spawnHorizontalRange);
                int spawnY = j + MathHelper.getRandomIntegerInRange(this.rand, this.spawnVerticalMin, this.spawnVerticalMax);
                int spawnZ = k + MathHelper.getRandomIntegerInRange(this.rand, (-this.spawnHorizontalRange), this.spawnHorizontalRange);
                Block belowBlock = this.worldObj.getBlock(spawnX, spawnY - 1, spawnZ);
                this.worldObj.getBlockMetadata(spawnX, spawnY - 1, spawnZ);
                boolean belowSolid = belowBlock.isSideSolid(this.worldObj, spawnX, spawnY - 1, spawnZ, ForgeDirection.UP);
                if (!belowSolid || this.worldObj.getBlock(spawnX, spawnY, spawnZ).isNormalCube() || this.worldObj.getBlock(spawnX, spawnY + 1, spawnZ).isNormalCube()) continue;
                Class entityClass = null;
                if (this.spawnClass1 != null && this.spawnClass2 != null) {
                    entityClass = this.rand.nextInt(3) == 0 ? this.spawnClass2 : this.spawnClass1;
                } else if (this.spawnClass1 != null) {
                    entityClass = this.spawnClass1;
                } else if (this.spawnClass2 != null) {
                    entityClass = this.spawnClass2;
                }
                String entityName = LOTREntities.getStringFromClass(entityClass);
                LOTREntityNPC entity = (LOTREntityNPC)EntityList.createEntityByName(entityName, this.worldObj);
                entity.setLocationAndAngles(spawnX + 0.5, spawnY, spawnZ + 0.5, this.rand.nextFloat() * 360.0f, 0.0f);
                entity.isNPCPersistent = true;
                entity.liftSpawnRestrictions = true;
                if (!entity.getCanSpawnHere()) continue;
                entity.liftSpawnRestrictions = false;
                this.worldObj.spawnEntityInWorld(entity);
                if (this.mountSetting == 0) {
                    entity.spawnRidingHorse = false;
                } else if (this.mountSetting == 1) {
                    entity.spawnRidingHorse = true;
                }
                entity.onSpawnWithEgg(null);
                if (this.hasHomeRange()) {
                    if (this.setHomePosFromSpawn) {
                        entity.setHomeArea(spawnX, spawnY, spawnZ, this.homeRange);
                    } else {
                        entity.setHomeArea(i, j, k, this.homeRange);
                    }
                } else {
                    entity.detachHome();
                }
                if (++entities >= this.spawnCap) break;
            }
        }
    }

    public boolean interactFirst(EntityPlayer entityplayer) {
        if (entityplayer.capabilities.isCreativeMode) {
            if (!this.worldObj.isRemote) {
                LOTRPacketNPCRespawner packet = new LOTRPacketNPCRespawner(this);
                LOTRPacketHandler.networkWrapper.sendTo((IMessage)packet, (EntityPlayerMP)entityplayer);
            }
            return true;
        }
        return false;
    }

    public boolean canBeCollidedWith() {
        if (!this.worldObj.isRemote) {
            return false;
        }
        EntityPlayer entityplayer = LOTRMod.proxy.getClientPlayer();
        if (entityplayer == null) {
            return false;
        }
        return entityplayer.capabilities.isCreativeMode;
    }

    public void applyEntityCollision(Entity entity) {
    }

    public ItemStack getPickedResult(MovingObjectPosition target) {
        return new ItemStack(LOTRMod.npcRespawner);
    }

    public AxisAlignedBB createSpawnBlockRegion() {
        if (!this.blockEnemySpawns()) {
            return null;
        }
        int i = MathHelper.floor_double(this.posX);
        int j = MathHelper.floor_double(this.boundingBox.minY);
        int k = MathHelper.floor_double(this.posZ);
        int range = this.blockEnemySpawns;
        return AxisAlignedBB.getBoundingBox(i, j, k, i + 1, j + 1, k + 1).expand(range, range, range);
    }

    public boolean isEnemySpawnBlocked(LOTREntityNPC npc) {
        return this.isEnemySpawnBlocked(npc.getFaction());
    }

    public boolean isEnemySpawnBlocked(LOTRFaction spawnFaction) {
        LOTRFaction faction1;
        LOTRFaction faction2;
        if (this.spawnClass1 != null && (faction1 = ((LOTREntityNPC)EntityList.createEntityByName(LOTREntities.getStringFromClass(this.spawnClass1), this.worldObj)).getFaction()) != null && faction1.isBadRelation(spawnFaction)) {
            return true;
        }
        return this.spawnClass2 != null && (faction2 = ((LOTREntityNPC)EntityList.createEntityByName(LOTREntities.getStringFromClass(this.spawnClass2), this.worldObj)).getFaction()) != null && faction2.isBadRelation(spawnFaction);
    }

    public static boolean isSpawnBlocked(LOTREntityNPC npc) {
        return LOTREntityNPCRespawner.isSpawnBlocked(npc, npc.getFaction());
    }

    public static boolean isSpawnBlocked(Entity entity, LOTRFaction spawnFaction) {
        int j;
        int k;
        int range;
        World world = entity.worldObj;
        int i = MathHelper.floor_double(entity.posX);
        AxisAlignedBB originBB = AxisAlignedBB.getBoundingBox(i, j = MathHelper.floor_double(entity.boundingBox.minY), k = MathHelper.floor_double(entity.posZ), i + 1, j + 1, k + 1);
        AxisAlignedBB searchBB = originBB.expand(range = 64, range, range);
        List spawners = world.getEntitiesWithinAABB(LOTREntityNPCRespawner.class, searchBB);
        if (!spawners.isEmpty()) {
            for (Object obj : spawners) {
                AxisAlignedBB spawnBlockBB;
                LOTREntityNPCRespawner spawner = (LOTREntityNPCRespawner)(obj);
                if (!spawner.blockEnemySpawns() || !(spawnBlockBB = spawner.createSpawnBlockRegion()).intersectsWith(searchBB) || !spawnBlockBB.intersectsWith(originBB) || !spawner.isEnemySpawnBlocked(spawnFaction)) continue;
                return true;
            }
        }
        return false;
    }

}

