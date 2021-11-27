package lotr.common.tileentity;

import java.util.*;

import lotr.common.entity.*;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.entity.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.*;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class LOTRTileEntityMobSpawner extends TileEntity {
    public int delay = 20;
    private String entityClassName = "";
    public double yaw;
    public double prevYaw = 0.0;
    private Entity spawnedMob;
    public int active = 1;
    public boolean spawnsPersistentNPCs = false;
    public int minSpawnDelay = 200;
    public int maxSpawnDelay = 800;
    public int nearbyMobLimit = 6;
    public int nearbyMobCheckRange = 8;
    public int requiredPlayerRange = 16;
    public int maxSpawnCount = 4;
    public int maxSpawnRange = 4;
    public int maxSpawnRangeVertical = 1;
    public int maxHealth = 20;
    public int navigatorRange = 16;
    public float moveSpeed = 0.2f;
    public float attackDamage = 2.0f;
    private NBTTagCompound customSpawnData;

    public String getEntityClassName() {
        return this.entityClassName;
    }

    public void setEntityClassID(int i) {
        this.setEntityClassName(LOTREntities.getStringFromID(i));
    }

    public void setEntityClassName(String s) {
        Entity entity;
        this.entityClassName = s;
        if(!this.worldObj.isRemote && (entity = EntityList.createEntityByName(this.entityClassName, this.worldObj)) instanceof EntityLiving) {
            EntityLiving entityliving = (EntityLiving) entity;
            if(entityliving.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.maxHealth) != null) {
                this.maxHealth = (int) entityliving.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.maxHealth).getBaseValue();
            }
            if(entityliving.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.followRange) != null) {
                this.navigatorRange = (int) entityliving.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.followRange).getBaseValue();
            }
            if(entityliving.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.movementSpeed) != null) {
                this.moveSpeed = (float) entityliving.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.movementSpeed).getBaseValue();
            }
            if(entityliving.getAttributeMap().getAttributeInstance(LOTREntityNPC.npcAttackDamage) != null) {
                this.attackDamage = (float) entityliving.getAttributeMap().getAttributeInstance(LOTREntityNPC.npcAttackDamage).getBaseValue();
            }
        }
    }

    public boolean anyPlayerInRange() {
        return this.worldObj.getClosestPlayer(this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5, this.requiredPlayerRange) != null;
    }

    public boolean isActive() {
        if(this.active == 1) {
            return true;
        }
        if(this.active == 2) {
            return this.worldObj.isBlockIndirectlyGettingPowered(this.xCoord, this.yCoord, this.zCoord);
        }
        return false;
    }

    @Override
    public void updateEntity() {
        if(this.anyPlayerInRange() && this.isActive()) {
            if(this.worldObj.isRemote) {
                double d = this.xCoord + this.worldObj.rand.nextFloat();
                double d1 = this.yCoord + this.worldObj.rand.nextFloat();
                double d2 = this.zCoord + this.worldObj.rand.nextFloat();
                this.worldObj.spawnParticle("smoke", d, d1, d2, 0.0, 0.0, 0.0);
                this.worldObj.spawnParticle("flame", d, d1, d2, 0.0, 0.0, 0.0);
                if(this.delay > 0) {
                    --this.delay;
                }
                this.prevYaw = this.yaw;
                this.yaw = (this.yaw + 1000.0f / (this.delay + 200.0f)) % 360.0;
            }
            else {
                if(this.delay == -1) {
                    this.updateDelay();
                }
                if(this.delay > 0) {
                    --this.delay;
                    return;
                }
                boolean needsDelayUpdate = false;
                for(int i = 0; i < this.maxSpawnCount; ++i) {
                    Entity entity = EntityList.createEntityByName(this.entityClassName, this.worldObj);
                    if(entity == null) {
                        return;
                    }
                    List nearbyEntitiesList = this.worldObj.getEntitiesWithinAABB(entity.getClass(), AxisAlignedBB.getBoundingBox(this.xCoord, this.yCoord, this.zCoord, this.xCoord + 1, this.yCoord + 1, this.zCoord + 1).expand(this.nearbyMobCheckRange, this.nearbyMobCheckRange, this.nearbyMobCheckRange));
                    ArrayList<Entity> nearbySameEntitiesList = new ArrayList<>();
                    for(Object element : nearbyEntitiesList) {
                        Entity nearbyEntity = (Entity) element;
                        if(nearbyEntity.getClass() != entity.getClass()) continue;
                        nearbySameEntitiesList.add(nearbyEntity);
                    }
                    int nearbyEntities = nearbySameEntitiesList.size();
                    if(nearbyEntities >= this.nearbyMobLimit) {
                        this.updateDelay();
                        return;
                    }
                    double d = this.xCoord + (this.worldObj.rand.nextDouble() - this.worldObj.rand.nextDouble()) * this.maxSpawnRange;
                    double d1 = this.yCoord + (this.worldObj.rand.nextDouble() - this.worldObj.rand.nextDouble()) * this.maxSpawnRangeVertical;
                    double d2 = this.zCoord + (this.worldObj.rand.nextDouble() - this.worldObj.rand.nextDouble()) * this.maxSpawnRange;
                    EntityLiving entityliving = entity instanceof EntityLiving ? (EntityLiving) entity : null;
                    entity.setLocationAndAngles(d, d1, d2, this.worldObj.rand.nextFloat() * 360.0f, 0.0f);
                    if(entityliving instanceof LOTREntityNPC) {
                        ((LOTREntityNPC) entityliving).isNPCPersistent = this.spawnsPersistentNPCs;
                        ((LOTREntityNPC) entityliving).liftSpawnRestrictions = true;
                    }
                    if(entity instanceof LOTRMobSpawnerCondition) {
                        ((LOTRMobSpawnerCondition) (entity)).setSpawningFromMobSpawner(true);
                    }
                    if(entityliving != null && !entityliving.getCanSpawnHere()) continue;
                    if(entityliving instanceof LOTREntityNPC) {
                        ((LOTREntityNPC) entityliving).liftSpawnRestrictions = false;
                    }
                    if(entity instanceof LOTRMobSpawnerCondition) {
                        ((LOTRMobSpawnerCondition) (entity)).setSpawningFromMobSpawner(false);
                    }
                    this.writeNBTTagsTo(entity);
                    if(entity instanceof LOTREntityNPC) {
                        ((LOTREntityNPC) entity).onArtificalSpawn();
                    }
                    this.worldObj.spawnEntityInWorld(entity);
                    this.worldObj.playAuxSFX(2004, this.xCoord, this.yCoord, this.zCoord, 0);
                    if(entityliving != null) {
                        entityliving.spawnExplosionParticle();
                    }
                    needsDelayUpdate = true;
                    if(++nearbyEntities >= this.nearbyMobLimit) break;
                }
                if(needsDelayUpdate) {
                    this.updateDelay();
                }
            }
            super.updateEntity();
        }
    }

    public void writeNBTTagsTo(Entity entity) {
        if(entity instanceof EntityLiving && entity.worldObj != null) {
            EntityLiving entityliving = (EntityLiving) entity;
            if(!this.worldObj.isRemote) {
                if(entityliving.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.maxHealth) != null) {
                    entityliving.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(this.maxHealth);
                }
                if(entityliving.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.followRange) != null) {
                    entityliving.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(this.navigatorRange);
                }
                if(entityliving.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.movementSpeed) != null) {
                    entityliving.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(this.moveSpeed);
                }
                if(entityliving.getAttributeMap().getAttributeInstance(LOTREntityNPC.npcAttackDamage) != null) {
                    entityliving.getEntityAttribute(LOTREntityNPC.npcAttackDamage).setBaseValue(this.attackDamage);
                }
            }
            entityliving.onSpawnWithEgg(null);
            if(this.customSpawnData != null) {
                entityliving.readFromNBT(this.customSpawnData);
            }
        }
    }

    private void updateDelay() {
        this.delay = this.maxSpawnDelay <= this.minSpawnDelay ? this.minSpawnDelay : this.minSpawnDelay + this.worldObj.rand.nextInt(this.maxSpawnDelay - this.minSpawnDelay);
        this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, this.getBlockType(), 1, 0);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        if(nbt.hasKey("EntityID", 3)) {
            int id = nbt.getInteger("EntityID");
            this.entityClassName = LOTREntities.getStringFromID(id);
        }
        else {
            this.entityClassName = nbt.getString("EntityID");
        }
        this.delay = nbt.getShort("Delay");
        if(nbt.hasKey("MinSpawnDelay")) {
            this.minSpawnDelay = nbt.getShort("MinSpawnDelay");
            this.maxSpawnDelay = nbt.getShort("MaxSpawnDelay");
            this.maxSpawnCount = nbt.getShort("MaxSpawnCount");
        }
        if(nbt.hasKey("NearbyMobLimit")) {
            this.nearbyMobLimit = nbt.getShort("NearbyMobLimit");
            this.requiredPlayerRange = nbt.getShort("RequiredPlayerRange");
        }
        if(nbt.hasKey("MaxSpawnRange")) {
            this.maxSpawnRange = nbt.getShort("MaxSpawnRange");
        }
        if(nbt.hasKey("MaxSpawnRangeVertical")) {
            this.maxSpawnRangeVertical = nbt.getShort("MaxSpawnRangeVertical");
        }
        if(nbt.hasKey("SpawnsPersistentNPCs")) {
            this.spawnsPersistentNPCs = nbt.getBoolean("SpawnsPersistentNPCs");
            this.active = nbt.getByte("ActiveMode");
            this.nearbyMobCheckRange = nbt.getShort("MobCheckRange");
        }
        if(nbt.hasKey("MaxHealth")) {
            this.maxHealth = nbt.getShort("MaxHealth");
            this.navigatorRange = nbt.getShort("NavigatorRange");
            this.moveSpeed = nbt.getFloat("MoveSpeed");
            this.attackDamage = nbt.getFloat("AttackDamage");
        }
        if(nbt.hasKey("CustomSpawnData")) {
            this.customSpawnData = nbt.getCompoundTag("CustomSpawnData");
        }
        if(this.worldObj != null && this.worldObj.isRemote) {
            this.spawnedMob = null;
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setString("EntityID", this.getEntityClassName());
        nbt.setShort("Delay", (short) this.delay);
        nbt.setShort("MinSpawnDelay", (short) this.minSpawnDelay);
        nbt.setShort("MaxSpawnDelay", (short) this.maxSpawnDelay);
        nbt.setShort("MaxSpawnCount", (short) this.maxSpawnCount);
        nbt.setShort("NearbyMobLimit", (short) this.nearbyMobLimit);
        nbt.setShort("RequiredPlayerRange", (short) this.requiredPlayerRange);
        nbt.setShort("MaxSpawnRange", (short) this.maxSpawnRange);
        nbt.setShort("MaxSpawnRangeVertical", (short) this.maxSpawnRangeVertical);
        nbt.setBoolean("SpawnsPersistentNPCs", this.spawnsPersistentNPCs);
        nbt.setByte("ActiveMode", (byte) this.active);
        nbt.setShort("MobCheckRange", (short) this.nearbyMobCheckRange);
        nbt.setShort("MaxHealth", (short) this.maxHealth);
        nbt.setShort("NavigatorRange", (short) this.navigatorRange);
        nbt.setFloat("MoveSpeed", this.moveSpeed);
        nbt.setFloat("AttackDamage", this.attackDamage);
        if(this.customSpawnData != null) {
            nbt.setTag("CustomSpawnData", this.customSpawnData);
        }
    }

    public Entity getMobEntity(World world) {
        if(this.spawnedMob == null) {
            Entity entity = EntityList.createEntityByName(this.entityClassName, world);
            if(entity instanceof LOTREntityNPC) {
                ((LOTREntityNPC) entity).onArtificalSpawn();
            }
            this.writeNBTTagsTo(entity);
            this.spawnedMob = entity;
        }
        return this.spawnedMob;
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound data = new NBTTagCompound();
        this.writeToNBT(data);
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 0, data);
    }

    @Override
    public void onDataPacket(NetworkManager manager, S35PacketUpdateTileEntity packet) {
        NBTTagCompound data = packet.func_148857_g();
        this.readFromNBT(data);
    }

    @Override
    public boolean receiveClientEvent(int i, int j) {
        if(i == 1 && this.worldObj.isRemote) {
            this.delay = this.minSpawnDelay;
            return true;
        }
        if(i == 2 && this.worldObj.isRemote) {
            this.delay = -1;
            return true;
        }
        return false;
    }
}
