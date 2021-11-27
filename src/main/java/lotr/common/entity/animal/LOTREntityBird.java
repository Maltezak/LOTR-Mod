package lotr.common.entity.animal;

import java.util.*;

import lotr.common.LOTRMod;
import lotr.common.block.LOTRBlockBerryBush;
import lotr.common.entity.*;
import lotr.common.inventory.LOTREntityInventory;
import lotr.common.item.LOTRValuableItems;
import lotr.common.world.biome.LOTRBiomeGenFarHarad;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.*;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTREntityBird
extends EntityLiving
implements LOTRAmbientCreature,
LOTRRandomSkinEntity,
AnimalJarUpdater {
    private ChunkCoordinates currentFlightTarget;
    private int flightTargetTime = 0;
    public int flapTime = 0;
    private LOTREntityInventory birdInv = new LOTREntityInventory("BirdItems", this, 9);
    private EntityItem stealTargetItem;
    private EntityPlayer stealTargetPlayer;
    private int stolenTime = 0;
    private boolean stealingCrops = false;

    public LOTREntityBird(World world) {
        super(world);
        this.setSize(0.5f, 0.5f);
        this.tasks.addTask(0, new EntityAIWatchClosest(this, EntityPlayer.class, 12.0f, 0.05f));
        this.tasks.addTask(1, new EntityAIWatchClosest(this, EntityLiving.class, 12.0f, 0.1f));
        this.tasks.addTask(2, new EntityAILookIdle(this));
    }

    public void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, (byte)0);
        this.dataWatcher.addObject(17, (byte)1);
    }

    public BirdType getBirdType() {
        byte i = this.dataWatcher.getWatchableObjectByte(16);
        if (i < 0 || i >= BirdType.values().length) {
            i = 0;
        }
        return BirdType.values()[i];
    }

    public void setBirdType(BirdType type) {
        this.setBirdType(type.ordinal());
    }

    public void setBirdType(int i) {
        this.dataWatcher.updateObject(16, ((byte)i));
    }
 
    public boolean isBirdStill() {
        return this.dataWatcher.getWatchableObjectByte(17) == 1;
    }

    public void setBirdStill(boolean flag) {
        this.dataWatcher.updateObject(17, flag ? (byte)1 : 0);
    }

    public String getBirdTextureDir() {
        return this.getBirdType().textureDir;
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(4.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(MathHelper.getRandomDoubleInRange(this.rand, 0.08, 0.13));
    }

    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        int i = MathHelper.floor_double(this.posX);
        MathHelper.floor_double(this.posY);
        int k = MathHelper.floor_double(this.posZ);
        BiomeGenBase biome = this.worldObj.getBiomeGenForCoords(i, k);
        if (biome instanceof LOTRBiomeGenFarHarad) {
            if (this.rand.nextInt(8) == 0) {
                this.setBirdType(BirdType.CROW);
            } else {
                this.setBirdType(BirdType.FAR_HARAD);
            }
        } else if (this.rand.nextInt(6) == 0) {
            this.setBirdType(BirdType.CROW);
        } else if (this.rand.nextInt(10) == 0) {
            this.setBirdType(BirdType.MAGPIE);
        } else {
            this.setBirdType(BirdType.COMMON);
        }
        return data;
    }

    @Override
    public void setUniqueID(UUID uuid) {
        this.entityUniqueID = uuid;
    }

    public boolean canBePushed() {
        return false;
    }

    protected void collideWithEntity(Entity entity) {
    }

    protected void collideWithNearbyEntities() {
    }

    protected boolean isAIEnabled() {
        return true;
    }

    protected boolean canStealItems() {
        return this.getBirdType().canSteal;
    }

    protected boolean isStealable(ItemStack itemstack) {
        BirdType type = this.getBirdType();
        Item item = itemstack.getItem();
        if (type == BirdType.COMMON) {
            return item instanceof IPlantable && ((IPlantable)item).getPlantType(this.worldObj, -1, -1, -1) == EnumPlantType.Crop;
        }
        if (type == BirdType.CROW) {
            return item instanceof ItemFood || LOTRMod.isOreNameEqual(itemstack, "bone");
        }
        if (type == BirdType.MAGPIE) {
            return LOTRValuableItems.canMagpieSteal(itemstack);
        }
        return false;
    }

    public ItemStack getStolenItem() {
        return this.getEquipmentInSlot(4);
    }

    public void setStolenItem(ItemStack itemstack) {
        this.setCurrentItemOrArmor(4, itemstack);
    }

    public void onUpdate() {
        super.onUpdate();
        if (this.isBirdStill()) {
            this.motionZ = 0.0;
            this.motionY = 0.0;
            this.motionX = 0.0;
            this.posY = MathHelper.floor_double(this.posY);
            if (this.worldObj.isRemote) {
                if (this.rand.nextInt(200) == 0) {
                    this.flapTime = 40;
                }
                if (this.flapTime > 0) {
                    --this.flapTime;
                }
            }
        } else {
            this.motionY *= 0.6;
            if (this.worldObj.isRemote) {
                this.flapTime = 0;
            }
        }
    }

    @Override
    public void updateInAnimalJar() {
        this.setBirdStill(false);
    }

    protected void updateAITasks() {
        super.updateAITasks();
        if (this.getStolenItem() != null) {
            ++this.stolenTime;
            if (this.stolenTime >= 200) {
                this.setStolenItem(null);
                this.stolenTime = 0;
            }
        }
        if (this.isBirdStill()) {
            if (!this.canBirdSit()) {
                this.setBirdStill(false);
            } else if (this.rand.nextInt(400) == 0 || this.worldObj.getClosestPlayerToEntity(this, 6.0) != null) {
                this.setBirdStill(false);
            }
        } else {
            if (this.canStealItems() && !this.stealingCrops && this.stealTargetItem == null && this.stealTargetPlayer == null && !this.birdInv.isFull() && this.rand.nextInt(100) == 0) {
                double range = 16.0;
                List players = this.worldObj.selectEntitiesWithinAABB(EntityPlayer.class, this.boundingBox.expand(range, range, range), new IEntitySelector(){

                    public boolean isEntityApplicable(Entity e) {
                        EntityPlayer entityplayer;
                        if (e instanceof EntityPlayer && LOTREntityBird.this.canStealPlayer(entityplayer = (EntityPlayer)e)) {
                            ChunkCoordinates coords = LOTREntityBird.this.getPlayerFlightTarget(entityplayer);
                            return LOTREntityBird.this.isValidFlightTarget(coords);
                        }
                        return false;
                    }
                });
                if (!players.isEmpty()) {
                    this.stealTargetPlayer = (EntityPlayer)players.get(this.rand.nextInt(players.size()));
                    this.currentFlightTarget = this.getPlayerFlightTarget(this.stealTargetPlayer);
                    this.newFlight();
                } else {
                    List entityItems = this.worldObj.selectEntitiesWithinAABB(EntityItem.class, this.boundingBox.expand(range, range, range), new IEntitySelector(){

                        public boolean isEntityApplicable(Entity e) {
                            EntityItem eItem;
                            if (e instanceof EntityItem && LOTREntityBird.this.canStealItem(eItem = (EntityItem)e)) {
                                ChunkCoordinates coords = LOTREntityBird.this.getItemFlightTarget(eItem);
                                return LOTREntityBird.this.isValidFlightTarget(coords);
                            }
                            return false;
                        }
                    });
                    if (!entityItems.isEmpty()) {
                        this.stealTargetItem = (EntityItem)entityItems.get(this.rand.nextInt(entityItems.size()));
                        this.currentFlightTarget = this.getItemFlightTarget(this.stealTargetItem);
                        this.newFlight();
                    }
                }
            }
            if (this.stealTargetItem != null || this.stealTargetPlayer != null) {
                if (this.birdInv.isFull() || this.currentFlightTarget == null || !this.isValidFlightTarget(this.currentFlightTarget)) {
                    this.cancelFlight();
                } else if (this.stealTargetItem != null && !this.canStealItem(this.stealTargetItem)) {
                    this.cancelFlight();
                } else if (this.stealTargetPlayer != null && !this.canStealPlayer(this.stealTargetPlayer)) {
                    this.cancelFlight();
                } else {
                    if (this.stealTargetItem != null) {
                        this.currentFlightTarget = this.getItemFlightTarget(this.stealTargetItem);
                    } else if (this.stealTargetPlayer != null) {
                        this.currentFlightTarget = this.getPlayerFlightTarget(this.stealTargetPlayer);
                    }
                    if (this.getDistanceSqToFlightTarget() < 1.0) {
                        ItemStack stolenItem = null;
                        if (this.stealTargetItem != null) {
                            ItemStack itemstack = this.stealTargetItem.getEntityItem();
                            ItemStack stealCopy = itemstack.copy();
                            stealCopy.stackSize = MathHelper.getRandomIntegerInRange(this.rand, 1, Math.min(stealCopy.stackSize, 4));
                            ItemStack safeCopy = stealCopy.copy();
                            if (this.birdInv.addItemToInventory(stealCopy)) {
                                itemstack.stackSize -= safeCopy.stackSize - stealCopy.stackSize;
                                if (itemstack.stackSize <= 0) {
                                    this.stealTargetItem.setDead();
                                }
                                stolenItem = safeCopy;
                            }
                        } else if (this.stealTargetPlayer != null) {
                            List<Integer> slots = this.getStealablePlayerSlots(this.stealTargetPlayer);
                            int randSlot = slots.get(this.rand.nextInt(slots.size()));
                            ItemStack itemstack = this.stealTargetPlayer.inventory.getStackInSlot(randSlot);
                            ItemStack stealCopy = itemstack.copy();
                            stealCopy.stackSize = MathHelper.getRandomIntegerInRange(this.rand, 1, Math.min(stealCopy.stackSize, 4));
                            ItemStack safeCopy = stealCopy.copy();
                            if (this.birdInv.addItemToInventory(stealCopy)) {
                                itemstack.stackSize -= safeCopy.stackSize - stealCopy.stackSize;
                                if (itemstack.stackSize <= 0) {
                                    itemstack = null;
                                }
                                this.stealTargetPlayer.inventory.setInventorySlotContents(randSlot, itemstack);
                                stolenItem = safeCopy;
                            }
                        }
                        if (stolenItem != null) {
                            this.stolenTime = 0;
                            this.setStolenItem(stolenItem);
                            this.playSound("random.pop", 0.5f, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7f + 1.0f) * 2.0f);
                        }
                        this.cancelFlight();
                    }
                }
            } else if (this.stealingCrops) {
                if (!LOTRMod.canGrief(this.worldObj)) {
                    this.stealingCrops = false;
                } else if (this.currentFlightTarget == null || !this.isValidFlightTarget(this.currentFlightTarget)) {
                    this.cancelFlight();
                } else {
                    int i = this.currentFlightTarget.posX;
                    int j = this.currentFlightTarget.posY;
                    int k = this.currentFlightTarget.posZ;
                    if (this.getDistanceSqToFlightTarget() < 1.0) {
                        if (this.canStealCrops(i, j, k)) {
                            this.eatCropBlock(i, j, k);
                            this.playSound("random.eat", 1.0f, (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2f + 1.0f);
                        }
                        this.cancelFlight();
                    } else if (!this.canStealCrops(i, j, k)) {
                        this.cancelFlight();
                    } else if (this.flightTargetTime % 100 == 0 && LOTRScarecrows.anyScarecrowsNearby(this.worldObj, i, j, k)) {
                        this.cancelFlight();
                    }
                }
            } else {
                int j;
                if (LOTRMod.canGrief(this.worldObj) && !this.stealingCrops && this.rand.nextInt(100) == 0) {
                    int i = MathHelper.floor_double(this.posX);
                    j = MathHelper.floor_double(this.posY);
                    int k = MathHelper.floor_double(this.posZ);
                    int range = 16;
                    int yRange = 8;
                    int attempts = 32;
                    for (int l = 0; l < attempts; ++l) {
                        int k1;
                        int j1;
                        int i1 = i + MathHelper.getRandomIntegerInRange(this.rand, (-range), range);
                        if (!this.canStealCrops(i1, j1 = j + MathHelper.getRandomIntegerInRange(this.rand, (-yRange), yRange), k1 = k + MathHelper.getRandomIntegerInRange(this.rand, (-range), range)) || LOTRScarecrows.anyScarecrowsNearby(this.worldObj, i1, j1, k1)) continue;
                        this.stealingCrops = true;
                        this.currentFlightTarget = new ChunkCoordinates(i1, j1, k1);
                        this.newFlight();
                        break;
                    }
                }
                if (!this.stealingCrops) {
                    if (this.currentFlightTarget != null && !this.isValidFlightTarget(this.currentFlightTarget)) {
                        this.cancelFlight();
                    }
                    if (this.currentFlightTarget == null || this.rand.nextInt(50) == 0 || this.getDistanceSqToFlightTarget() < 4.0) {
                        int i = MathHelper.floor_double(this.posX);
                        j = MathHelper.floor_double(this.posY);
                        int k = MathHelper.floor_double(this.posZ);
                        this.currentFlightTarget = new ChunkCoordinates(i += this.rand.nextInt(16) - this.rand.nextInt(16), j += MathHelper.getRandomIntegerInRange(this.rand, -2, 3), k += this.rand.nextInt(16) - this.rand.nextInt(16));
                        this.newFlight();
                    }
                }
            }
            if (this.currentFlightTarget != null) {
                double speed = this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue();
                double d0 = this.currentFlightTarget.posX + 0.5 - this.posX;
                double d1 = this.currentFlightTarget.posY + 0.5 - this.posY;
                double d2 = this.currentFlightTarget.posZ + 0.5 - this.posZ;
                this.motionX += (Math.signum(d0) * 0.5 - this.motionX) * speed;
                this.motionY += (Math.signum(d1) * 0.8 - this.motionY) * speed;
                this.motionZ += (Math.signum(d2) * 0.5 - this.motionZ) * speed;
                float f = (float)(Math.atan2(this.motionZ, this.motionX) * 180.0 / 3.141592653589793) - 90.0f;
                float f1 = MathHelper.wrapAngleTo180_float(f - this.rotationYaw);
                this.moveForward = 0.5f;
                this.rotationYaw += f1;
                ++this.flightTargetTime;
                if (this.flightTargetTime >= 400) {
                    this.cancelFlight();
                }
            }
            if (this.rand.nextInt(200) == 0 && this.canBirdSit()) {
                this.setBirdStill(true);
                this.cancelFlight();
            }
        }
    }

    private boolean canBirdSit() {
        int i = MathHelper.floor_double(this.posX);
        int j = MathHelper.floor_double(this.posY);
        int k = MathHelper.floor_double(this.posZ);
        Block block = this.worldObj.getBlock(i, j, k);
        Block below = this.worldObj.getBlock(i, j - 1, k);
        return block.getBlocksMovement(this.worldObj, i, j, k) && below.isSideSolid(this.worldObj, i, j - 1, k, ForgeDirection.UP);
    }

    private boolean isValidFlightTarget(ChunkCoordinates coords) {
        int i = coords.posX;
        int j = coords.posY;
        int k = coords.posZ;
        if (j >= 1) {
            Block block = this.worldObj.getBlock(i, j, k);
            return block.getBlocksMovement(this.worldObj, i, j, k);
        }
        return false;
    }

    private double getDistanceSqToFlightTarget() {
        double d = this.currentFlightTarget.posX + 0.5;
        double d1 = this.currentFlightTarget.posY + 0.5;
        double d2 = this.currentFlightTarget.posZ + 0.5;
        return this.getDistanceSq(d, d1, d2);
    }

    private void cancelFlight() {
        this.currentFlightTarget = null;
        this.flightTargetTime = 0;
        this.stealTargetItem = null;
        this.stealTargetPlayer = null;
        this.stealingCrops = false;
    }

    private void newFlight() {
        this.flightTargetTime = 0;
    }

    private boolean canStealItem(EntityItem entity) {
        return entity.isEntityAlive() && this.isStealable(entity.getEntityItem());
    }

    private boolean canStealPlayer(EntityPlayer entityplayer) {
        if (entityplayer.capabilities.isCreativeMode || !entityplayer.isEntityAlive()) {
            return false;
        }
        List<Integer> slots = this.getStealablePlayerSlots(entityplayer);
        return !slots.isEmpty();
    }

    private List<Integer> getStealablePlayerSlots(EntityPlayer entityplayer) {
        ArrayList<Integer> slots = new ArrayList<>();
        for (int i = 0; i <= 8; ++i) {
            ItemStack itemstack;
            if (i != entityplayer.inventory.currentItem || (itemstack = entityplayer.inventory.getStackInSlot(i)) == null || !this.isStealable(itemstack)) continue;
            slots.add(i);
        }
        return slots;
    }

    private ChunkCoordinates getItemFlightTarget(EntityItem entity) {
        int i = MathHelper.floor_double(entity.posX);
        int j = MathHelper.floor_double(entity.boundingBox.minY);
        int k = MathHelper.floor_double(entity.posZ);
        return new ChunkCoordinates(i, j, k);
    }

    private ChunkCoordinates getPlayerFlightTarget(EntityPlayer entityplayer) {
        int i = MathHelper.floor_double(entityplayer.posX);
        int j = MathHelper.floor_double(entityplayer.boundingBox.minY + 1.0);
        int k = MathHelper.floor_double(entityplayer.posZ);
        return new ChunkCoordinates(i, j, k);
    }

    private boolean canStealCrops(int i, int j, int k) {
        Block block = this.worldObj.getBlock(i, j, k);
        if (block instanceof BlockCrops) {
            return true;
        }
        if (block instanceof LOTRBlockBerryBush) {
            int meta = this.worldObj.getBlockMetadata(i, j, k);
            return LOTRBlockBerryBush.hasBerries(meta);
        }
        return false;
    }

    private void eatCropBlock(int i, int j, int k) {
        Block block = this.worldObj.getBlock(i, j, k);
        if (block instanceof LOTRBlockBerryBush) {
            int meta = this.worldObj.getBlockMetadata(i, j, k);
            meta = LOTRBlockBerryBush.setHasBerries(meta, false);
            this.worldObj.setBlockMetadataWithNotify(i, j, k, meta, 3);
        } else {
            this.worldObj.setBlockToAir(i, j, k);
        }
    }

    protected boolean canTriggerWalking() {
        return false;
    }

    protected void fall(float f) {
    }

    protected void updateFallState(double d, boolean flag) {
    }

    public boolean doesEntityNotTriggerPressurePlate() {
        return true;
    }

    public boolean attackEntityFrom(DamageSource damagesource, float f) {
        boolean flag = super.attackEntityFrom(damagesource, f);
        if (flag && !this.worldObj.isRemote && this.isBirdStill()) {
            this.setBirdStill(false);
        }
        return flag;
    }

    protected void dropFewItems(boolean flag, int i) {
        int feathers = this.rand.nextInt(3) + this.rand.nextInt(i + 1);
        for (int l = 0; l < feathers; ++l) {
            this.dropItem(Items.feather, 1);
        }
    }

    public void onDeath(DamageSource damagesource) {
        super.onDeath(damagesource);
        if (!this.worldObj.isRemote) {
            this.birdInv.dropAllItems();
        }
    }

    public void readEntityFromNBT(NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        this.setBirdType(nbt.getInteger("BirdType"));
        this.setBirdStill(nbt.getBoolean("BirdStill"));
        this.birdInv.writeToNBT(nbt);
        nbt.setShort("StealTime", (short)this.stolenTime);
    }

    public void writeEntityToNBT(NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        nbt.setInteger("BirdType", this.getBirdType().ordinal());
        nbt.setBoolean("BirdStill", this.isBirdStill());
        this.birdInv.readFromNBT(nbt);
        this.stolenTime = nbt.getShort("StealTime");
    }

    protected boolean canDespawn() {
        return super.canDespawn();
    }

    public boolean getCanSpawnHere() {
        if (super.getCanSpawnHere()) {
            return this.canBirdSpawnHere();
        }
        return false;
    }

    protected boolean canBirdSpawnHere() {
        return LOTRAmbientSpawnChecks.canSpawn(this, 8, 12, 40, 4, Material.leaves);
    }

    public boolean allowLeashing() {
        return false;
    }

    protected boolean interact(EntityPlayer entityplayer) {
        return false;
    }

    public int getTalkInterval() {
        return 60;
    }

    public void playLivingSound() {
        boolean sound = true;
        if (!this.worldObj.isDaytime()) {
            sound = this.rand.nextInt(20) == 0;
        }
        if (sound) {
            super.playLivingSound();
        }
    }

    protected float getSoundVolume() {
        return 1.0f;
    }

    protected String getLivingSound() {
        BirdType type = this.getBirdType();
        if (type == BirdType.CROW) {
            return "lotr:bird.crow.say";
        }
        return "lotr:bird.say";
    }

    protected String getHurtSound() {
        BirdType type = this.getBirdType();
        if (type == BirdType.CROW) {
            return "lotr:bird.crow.hurt";
        }
        return "lotr:bird.hurt";
    }

    protected String getDeathSound() {
        BirdType type = this.getBirdType();
        if (type == BirdType.CROW) {
            return "lotr:bird.crow.hurt";
        }
        return "lotr:bird.hurt";
    }

    public ItemStack getPickedResult(MovingObjectPosition target) {
        return new ItemStack(LOTRMod.spawnEgg, 1, LOTREntities.getEntityID(this));
    }

    public enum BirdType {
        COMMON("common", true),
        CROW("crow", true),
        MAGPIE("magpie", true),
        FAR_HARAD("farHarad", true);

        public final String textureDir;
        public final boolean canSteal;

        BirdType(String s, boolean flag) {
            this.textureDir = s;
            this.canSteal = flag;
        }
    }

}

