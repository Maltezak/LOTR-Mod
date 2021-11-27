package lotr.common.entity.ai;

import java.util.*;

import com.mojang.authlib.GameProfile;

import lotr.common.*;
import lotr.common.block.*;
import lotr.common.entity.npc.*;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.*;
import net.minecraftforge.common.util.*;

public class LOTREntityAIFarm extends EntityAIBase {
  public static final int DEPOSIT_THRESHOLD = 16;
  
  public static final int COLLECT_THRESHOLD = 16;
  
  public static final int MIN_CHEST_RANGE = 24;
  
  public LOTREntityNPC theEntity;
  
  public LOTRFarmhand theEntityFarmer;
  
  public World theWorld;
  
  public double moveSpeed;
  
  public float farmingEfficiency;
  
  public enum Action {
    HOEING, PLANTING, HARVESTING, DEPOSITING, BONEMEALING, COLLECTING;
  }
  
  public static class TargetPair {
    public final ChunkCoordinates actionTarget;
    
    public final ChunkCoordinates pathTarget;
    
    public TargetPair(ChunkCoordinates action, ChunkCoordinates path) {
      this.actionTarget = action;
      this.pathTarget = path;
    }
  }
  
  public Action action = null;
  
  public ChunkCoordinates actionTarget;
  
  public ChunkCoordinates pathTarget;
  
  public int pathingTick;
  
  public int rePathDelay;
  
  public boolean harvestingSolidBlock;
  
  public FakePlayer fakePlayer;
  
  public LOTREntityAIFarm(LOTRFarmhand npc, double d, float f) {
    this.theEntity = (LOTREntityNPC)npc;
    this.theEntityFarmer = npc;
    this.theWorld = this.theEntity.worldObj;
    this.moveSpeed = d;
    setMutexBits(1);
    if (this.theWorld instanceof WorldServer)
      this.fakePlayer = FakePlayerFactory.get((WorldServer)this.theWorld, new GameProfile(null, "LOTRFarming")); 
    this.farmingEfficiency = f;
  }
  
  public boolean shouldExecute() {
    boolean flag = shouldFarmhandExecute();
    return flag;
  }
  
  public boolean shouldFarmhandExecute() {
    if (this.theEntity.hiredNPCInfo.isActive && !this.theEntity.hiredNPCInfo.isGuardMode())
      return false; 
    setAppropriateHomeRange((Action)null);
    if (this.theEntity.hasHome() && !this.theEntity.isWithinHomeDistanceCurrentPosition())
      return false; 
    if (this.theEntity.getRNG().nextFloat() < this.farmingEfficiency * 0.1F) {
      if (canDoDepositing()) {
        TargetPair depositTarget = findTarget(Action.DEPOSITING);
        if (depositTarget != null) {
          this.actionTarget = depositTarget.actionTarget;
          this.pathTarget = depositTarget.pathTarget;
          this.action = Action.DEPOSITING;
          return true;
        } 
      } 
      if (canDoHoeing()) {
        TargetPair hoeTarget = findTarget(Action.HOEING);
        if (hoeTarget != null) {
          this.actionTarget = hoeTarget.actionTarget;
          this.pathTarget = hoeTarget.pathTarget;
          this.action = Action.HOEING;
          return true;
        } 
      } 
      if (canDoPlanting()) {
        TargetPair plantTarget = findTarget(Action.PLANTING);
        if (plantTarget != null) {
          this.actionTarget = plantTarget.actionTarget;
          this.pathTarget = plantTarget.pathTarget;
          this.action = Action.PLANTING;
          return true;
        } 
      } 
      if (canDoHarvesting()) {
        TargetPair harvestTarget = findTarget(Action.HARVESTING);
        if (harvestTarget != null) {
          this.actionTarget = harvestTarget.actionTarget;
          this.pathTarget = harvestTarget.pathTarget;
          this.action = Action.HARVESTING;
          return true;
        } 
      } 
      if (canDoBonemealing()) {
        TargetPair bonemealTarget = findTarget(Action.BONEMEALING);
        if (bonemealTarget != null) {
          this.actionTarget = bonemealTarget.actionTarget;
          this.pathTarget = bonemealTarget.pathTarget;
          this.action = Action.BONEMEALING;
          return true;
        } 
      } 
      if (canDoCollecting()) {
        TargetPair collectTarget = findTarget(Action.COLLECTING);
        if (collectTarget != null) {
          this.actionTarget = collectTarget.actionTarget;
          this.pathTarget = collectTarget.pathTarget;
          this.action = Action.COLLECTING;
          return true;
        } 
      } 
    } 
    return false;
  }
  
  public boolean isFarmingGrapes() {
    IPlantable seed = getSeedsToPlant();
    return seed.getPlant(this.theWorld, -1, -1, -1) instanceof LOTRBlockGrapevine;
  }
  
  public boolean canDoHoeing() {
    return true;
  }
  
  public boolean canDoPlanting() {
    if (this.theEntity.hiredNPCInfo.isActive) {
      ItemStack invSeeds = getInventorySeeds();
      return (invSeeds != null && invSeeds.stackSize > 1);
    } 
    return true;
  }
  
  public boolean canDoHarvesting() {
    if (this.theEntity.hiredNPCInfo.isActive)
      return (getInventorySeeds() != null && hasSpaceForCrops() && getCropForSeed(getSeedsToPlant()) != null); 
    return false;
  }
  
  public boolean canDoDepositing() {
    if (this.theEntity.hiredNPCInfo.isActive)
      for (int l = 1; l <= 2; l++) {
        ItemStack itemstack = this.theEntity.hiredNPCInfo.getHiredInventory().getStackInSlot(l);
        if (itemstack != null && itemstack.stackSize >= 16)
          return true; 
      }  
    return false;
  }
  
  public boolean canDoBonemealing() {
    if (this.theEntity.hiredNPCInfo.isActive) {
      ItemStack invBmeal = getInventoryBonemeal();
      return (invBmeal != null);
    } 
    return false;
  }
  
  public boolean canDoCollecting() {
    if (this.theEntity.hiredNPCInfo.isActive) {
      ItemStack seeds = getInventorySeeds();
      if (seeds != null && seeds.stackSize <= 16)
        return true; 
      ItemStack bonemeal = getInventoryBonemeal();
      if (bonemeal == null || (bonemeal != null && bonemeal.stackSize <= 16))
        return true; 
    } 
    return false;
  }
  
  public ItemStack getInventorySeeds() {
    if (this.theEntity.hiredNPCInfo.getHiredInventory() == null)
      return null; 
    ItemStack itemstack = this.theEntity.hiredNPCInfo.getHiredInventory().getStackInSlot(0);
    if (itemstack != null) {
      Item item = itemstack.getItem();
      if (item instanceof IPlantable) {
        IPlantable iplantable = (IPlantable)item;
        if (iplantable.getPlantType(this.theWorld, -1, -1, -1) == EnumPlantType.Crop)
          return itemstack; 
      } 
    } 
    return null;
  }
  
  public IPlantable getSeedsToPlant() {
    if (this.theEntity.hiredNPCInfo.isActive) {
      ItemStack invSeeds = getInventorySeeds();
      if (invSeeds != null)
        return (IPlantable)invSeeds.getItem(); 
    } 
    return this.theEntityFarmer.getUnhiredSeeds();
  }
  
  public boolean hasSpaceForCrops() {
    if (this.theEntity.hiredNPCInfo.getHiredInventory() == null)
      return false; 
    for (int l = 1; l <= 2; l++) {
      ItemStack itemstack = this.theEntity.hiredNPCInfo.getHiredInventory().getStackInSlot(l);
      if (itemstack == null || (itemstack.stackSize < itemstack.getMaxStackSize() && itemstack.isItemEqual(getCropForSeed(getSeedsToPlant()))))
        return true; 
    } 
    return false;
  }
  
  public ItemStack getInventoryBonemeal() {
    if (this.theEntity.hiredNPCInfo.getHiredInventory() == null)
      return null; 
    ItemStack itemstack = this.theEntity.hiredNPCInfo.getHiredInventory().getStackInSlot(3);
    if (itemstack != null && itemstack.getItem() == Items.dye && itemstack.getItemDamage() == 15)
      return itemstack; 
    return null;
  }
  
  public ItemStack getCropForSeed(IPlantable seed) {
    Block block = seed.getPlant(this.theWorld, -1, -1, -1);
    if (block instanceof BlockCrops)
      return new ItemStack(LOTRReflection.getCropItem((BlockCrops)block)); 
    if (block instanceof BlockStem)
      return new ItemStack(LOTRReflection.getStemFruitBlock((BlockStem)block).getItemDropped(0, this.theWorld.rand, 0), 1, 0); 
    if (block instanceof LOTRBlockCorn)
      return new ItemStack(LOTRMod.corn); 
    if (block instanceof LOTRBlockGrapevine)
      return new ItemStack(((LOTRBlockGrapevine)block).getGrapeItem()); 
    return null;
  }
  
  public void startExecuting() {
    super.startExecuting();
    setAppropriateHomeRange(this.action);
  }
  
  public void setAppropriateHomeRange(Action targetAction) {
    if (this.theEntity.hiredNPCInfo.isActive) {
      int hRange = this.theEntity.hiredNPCInfo.getGuardRange();
      ChunkCoordinates home = this.theEntity.getHomePosition();
      if (targetAction != null && (targetAction == Action.DEPOSITING || targetAction == Action.COLLECTING))
        if (hRange < 24)
          hRange = 24;  
      this.theEntity.setHomeArea(home.posX, home.posY, home.posZ, hRange);
    } 
  }
  
  public boolean continueExecuting() {
    if (this.theEntity.hiredNPCInfo.isActive && !this.theEntity.hiredNPCInfo.isGuardMode())
      return false; 
    if (this.theEntity.getNavigator().noPath())
      return false; 
    if (this.pathingTick < 200) {
      if (this.action == Action.HOEING)
        return (canDoHoeing() && isSuitableForHoeing(this.actionTarget)); 
      if (this.action == Action.PLANTING)
        return (canDoPlanting() && isSuitableForPlanting(this.actionTarget)); 
      if (this.action == Action.HARVESTING)
        return (canDoHarvesting() && isSuitableForHarvesting(this.actionTarget)); 
      if (this.action == Action.DEPOSITING)
        return (canDoDepositing() && isSuitableForDepositing(this.actionTarget)); 
      if (this.action == Action.BONEMEALING)
        return (canDoBonemealing() && isSuitableForBonemealing(this.actionTarget)); 
      if (this.action == Action.COLLECTING)
        return (canDoCollecting() && isSuitableForCollecting(this.actionTarget)); 
    } 
    return false;
  }
  
  public void resetTask() {
    this.action = null;
    setAppropriateHomeRange(this.action);
    this.actionTarget = null;
    this.pathTarget = null;
    this.pathingTick = 0;
    this.rePathDelay = 0;
    this.harvestingSolidBlock = false;
  }
  
  public void updateTask() {
    boolean canDoAction = false;
    double distSq = this.theEntity.getDistanceSq(this.pathTarget.posX + 0.5D, this.pathTarget.posY, this.pathTarget.posZ + 0.5D);
    if (this.action == Action.HOEING || this.action == Action.PLANTING) {
      int i = MathHelper.floor_double(this.theEntity.posX);
      int j = MathHelper.floor_double(this.theEntity.boundingBox.minY);
      int k = MathHelper.floor_double(this.theEntity.posZ);
      canDoAction = (i == this.pathTarget.posX && j == this.pathTarget.posY && k == this.pathTarget.posZ);
    } else {
      canDoAction = (distSq < 9.0D);
    } 
    if (!canDoAction) {
      this.theEntity.getLookHelper().setLookPosition(this.actionTarget.posX + 0.5D, this.actionTarget.posY + 0.5D, this.actionTarget.posZ + 0.5D, 10.0F, this.theEntity.getVerticalFaceSpeed());
      this.rePathDelay--;
      if (this.rePathDelay <= 0) {
        this.rePathDelay = 10;
        this.theEntity.getNavigator().tryMoveToXYZ(this.pathTarget.posX + 0.5D, this.pathTarget.posY, this.pathTarget.posZ + 0.5D, this.moveSpeed);
      } 
      this.pathingTick++;
    } else if (this.action == Action.HOEING) {
      boolean canHoe = isSuitableForHoeing(this.actionTarget);
      if (canHoe) {
        this.theEntity.swingItem();
        ItemStack proxyHoe = new ItemStack(Items.iron_hoe);
        int hoeRange = 1;
        for (int i1 = -hoeRange; i1 <= hoeRange; i1++) {
          for (int k1 = -hoeRange; k1 <= hoeRange; k1++) {
            if (Math.abs(i1) + Math.abs(k1) <= hoeRange) {
              int x = this.actionTarget.posX + i1;
              int z = this.actionTarget.posZ + k1;
              int y = this.actionTarget.posY;
              boolean alreadyChecked = (i1 == 0 && k1 == 0);
              if (alreadyChecked || isSuitableForHoeing(x, y, z)) {
                if (isReplaceable(x, y + 1, z))
                  this.theWorld.setBlockToAir(x, y + 1, z); 
                proxyHoe.tryPlaceItemIntoWorld(this.fakePlayer, this.theWorld, x, y, z, 1, 0.5F, 0.5F, 0.5F);
              } 
            } 
          } 
        } 
      } 
    } else if (this.action == Action.PLANTING) {
      boolean canPlant = isSuitableForPlanting(this.actionTarget.posX, this.actionTarget.posY, this.actionTarget.posZ);
      if (canPlant) {
        this.theEntity.swingItem();
        IPlantable seed = getSeedsToPlant();
        Block plant = seed.getPlant(this.theWorld, this.actionTarget.posX, this.actionTarget.posY, this.actionTarget.posZ);
        int meta = seed.getPlantMetadata(this.theWorld, this.actionTarget.posX, this.actionTarget.posY, this.actionTarget.posZ);
        this.theWorld.setBlock(this.actionTarget.posX, this.actionTarget.posY, this.actionTarget.posZ, plant, meta, 3);
        if (this.theEntity.hiredNPCInfo.isActive)
          this.theEntity.hiredNPCInfo.getHiredInventory().decrStackSize(0, 1); 
      } 
    } else if (this.action == Action.HARVESTING) {
      boolean canHarvest = isSuitableForHarvesting(this.actionTarget.posX, this.actionTarget.posY, this.actionTarget.posZ);
      if (canHarvest) {
        this.theEntity.swingItem();
        Block block = this.theWorld.getBlock(this.actionTarget.posX, this.actionTarget.posY, this.actionTarget.posZ);
        ArrayList drops = new ArrayList();
        if (block instanceof LOTRBlockCorn) {
          int x = this.actionTarget.posX;
          int z = this.actionTarget.posZ;
          for (int j1 = 0; j1 <= LOTRBlockCorn.MAX_GROW_HEIGHT - 1; j1++) {
            int y = this.actionTarget.posY + j1;
            if (this.theWorld.getBlock(x, y, z) == block && LOTRBlockCorn.hasCorn(this.theWorld, x, y, z)) {
              int meta = this.theWorld.getBlockMetadata(x, y, z);
              drops.addAll(((LOTRBlockCorn)block).getCornDrops(this.theWorld, x, y, z, meta));
              LOTRBlockCorn.setHasCorn(this.theWorld, x, y, z, false);
            } 
          } 
        } else if (block instanceof LOTRBlockGrapevine) {
          int meta = this.theWorld.getBlockMetadata(this.actionTarget.posX, this.actionTarget.posY, this.actionTarget.posZ);
          drops.addAll(block.getDrops(this.theWorld, this.actionTarget.posX, this.actionTarget.posY, this.actionTarget.posZ, meta, 0));
          block.removedByPlayer(this.theWorld, this.fakePlayer, this.actionTarget.posX, this.actionTarget.posY, this.actionTarget.posZ, true);
        } else {
          int meta = this.theWorld.getBlockMetadata(this.actionTarget.posX, this.actionTarget.posY, this.actionTarget.posZ);
          drops.addAll(block.getDrops(this.theWorld, this.actionTarget.posX, this.actionTarget.posY, this.actionTarget.posZ, meta, 0));
          this.theWorld.setBlockToAir(this.actionTarget.posX, this.actionTarget.posY, this.actionTarget.posZ);
        } 
        Block.SoundType cropSound = block.stepSound;
        this.theWorld.playSoundEffect(this.actionTarget.posX + 0.5D, this.actionTarget.posY + 0.5D, this.actionTarget.posZ + 0.5D, cropSound.getBreakSound(), (cropSound.getVolume() + 1.0F) / 2.0F, cropSound.getPitch() * 0.8F);
        ItemStack seedItem = this.theEntity.hiredNPCInfo.getHiredInventory().getStackInSlot(0);
        ItemStack cropItem = getCropForSeed(getSeedsToPlant());
        boolean addedOneCropSeed = false;
        for (Object obj : drops) {
          ItemStack drop = (ItemStack)obj;
          if (drop.isItemEqual(cropItem)) {
            if (drop.isItemEqual(seedItem) && !addedOneCropSeed) {
              addedOneCropSeed = true;
              ItemStack itemstack = this.theEntity.hiredNPCInfo.getHiredInventory().getStackInSlot(0);
              if (itemstack.stackSize + drop.stackSize <= itemstack.getMaxStackSize()) {
                itemstack.stackSize++;
                this.theEntity.hiredNPCInfo.getHiredInventory().setInventorySlotContents(0, itemstack);
                continue;
              } 
            } 
            for (int l = 1; l <= 2; l++) {
              ItemStack itemstack = this.theEntity.hiredNPCInfo.getHiredInventory().getStackInSlot(l);
              if (itemstack == null) {
                this.theEntity.hiredNPCInfo.getHiredInventory().setInventorySlotContents(l, drop);
                break;
              } 
              if (itemstack.stackSize + drop.stackSize <= itemstack.getMaxStackSize() && itemstack.isItemEqual(cropItem)) {
                itemstack.stackSize++;
                this.theEntity.hiredNPCInfo.getHiredInventory().setInventorySlotContents(l, itemstack);
                break;
              } 
            } 
            continue;
          } 
          if (drop.isItemEqual(seedItem)) {
            ItemStack itemstack = this.theEntity.hiredNPCInfo.getHiredInventory().getStackInSlot(0);
            if (itemstack.stackSize + drop.stackSize <= itemstack.getMaxStackSize()) {
              itemstack.stackSize++;
              this.theEntity.hiredNPCInfo.getHiredInventory().setInventorySlotContents(0, itemstack);
            } 
          } 
        } 
      } 
    } else if (this.action == Action.DEPOSITING) {
      boolean canDeposit = isSuitableForDepositing(this.actionTarget.posX, this.actionTarget.posY, this.actionTarget.posZ);
      if (canDeposit) {
        this.theEntity.swingItem();
        TileEntity te = this.theWorld.getTileEntity(this.actionTarget.posX, this.actionTarget.posY, this.actionTarget.posZ);
        if (te instanceof TileEntityChest) {
          TileEntityChest chest = (TileEntityChest)te;
          for (int l = 1; l <= 2; l++) {
            ItemStack itemstack = this.theEntity.hiredNPCInfo.getHiredInventory().getStackInSlot(l);
            if (itemstack != null)
              for (int slot = 0; slot < chest.getSizeInventory(); slot++) {
                ItemStack chestItem = chest.getStackInSlot(slot);
                if (chestItem == null || (chestItem.isItemEqual(itemstack) && ItemStack.areItemStackTagsEqual(chestItem, itemstack) && chestItem.stackSize < chestItem.getMaxStackSize())) {
                  if (chestItem == null) {
                    chestItem = itemstack.copy();
                    chestItem.stackSize = 0;
                  } 
                  while (itemstack.stackSize > 0 && chestItem.stackSize < chestItem.getMaxStackSize()) {
                    chestItem.stackSize++;
                    itemstack.stackSize--;
                  } 
                  chest.setInventorySlotContents(slot, chestItem);
                  if (itemstack.stackSize <= 0) {
                    this.theEntity.hiredNPCInfo.getHiredInventory().setInventorySlotContents(l, null);
                    break;
                  } 
                } 
              }  
          } 
          this.theWorld.playSoundEffect(this.actionTarget.posX + 0.5D, this.actionTarget.posY + 0.5D, this.actionTarget.posZ + 0.5D, "random.chestclosed", 0.5F, this.theWorld.rand.nextFloat() * 0.1F + 0.9F);
        } 
      } 
    } else if (this.action == Action.BONEMEALING) {
      boolean canBonemeal = isSuitableForBonemealing(this.actionTarget.posX, this.actionTarget.posY, this.actionTarget.posZ);
      if (canBonemeal) {
        this.theEntity.swingItem();
        ItemStack bonemeal = getInventoryBonemeal();
        if (ItemDye.applyBonemeal(getInventoryBonemeal(), this.theWorld, this.actionTarget.posX, this.actionTarget.posY, this.actionTarget.posZ, this.fakePlayer))
          this.theWorld.playAuxSFX(2005, this.actionTarget.posX, this.actionTarget.posY, this.actionTarget.posZ, 0); 
        if (bonemeal.stackSize <= 0)
          bonemeal = null; 
        this.theEntity.hiredNPCInfo.getHiredInventory().setInventorySlotContents(3, bonemeal);
      } 
    } else if (this.action == Action.COLLECTING) {
      boolean canCollect = isSuitableForCollecting(this.actionTarget.posX, this.actionTarget.posY, this.actionTarget.posZ);
      if (canCollect) {
        this.theEntity.swingItem();
        TileEntity te = this.theWorld.getTileEntity(this.actionTarget.posX, this.actionTarget.posY, this.actionTarget.posZ);
        if (te instanceof TileEntityChest) {
          TileEntityChest chest = (TileEntityChest)te;
          int[] invSlots = { 0, 3 };
          for (int l : invSlots) {
            ItemStack itemstack = this.theEntity.hiredNPCInfo.getHiredInventory().getStackInSlot(l);
            if (itemstack == null)
              if (l == 3)
                itemstack = new ItemStack(Items.dye, 0, 15);  
            if (itemstack != null)
              for (int slot = 0; slot < chest.getSizeInventory(); slot++) {
                ItemStack chestItem = chest.getStackInSlot(slot);
                if (chestItem != null && chestItem.isItemEqual(itemstack) && ItemStack.areItemStackTagsEqual(chestItem, itemstack) && chestItem.stackSize > 0) {
                  while (itemstack.stackSize < itemstack.getMaxStackSize() && chestItem.stackSize > 0) {
                    chestItem.stackSize--;
                    itemstack.stackSize++;
                  } 
                  if (itemstack.stackSize <= 0)
                    itemstack = null; 
                  if (chestItem.stackSize <= 0)
                    chestItem = null; 
                  this.theEntity.hiredNPCInfo.getHiredInventory().setInventorySlotContents(l, itemstack);
                  chest.setInventorySlotContents(slot, chestItem);
                  if (itemstack.stackSize >= itemstack.getMaxStackSize())
                    break; 
                } 
              }  
          } 
          this.theWorld.playSoundEffect(this.actionTarget.posX + 0.5D, this.actionTarget.posY + 0.5D, this.actionTarget.posZ + 0.5D, "random.chestopen", 0.5F, this.theWorld.rand.nextFloat() * 0.1F + 0.9F);
        } 
      } 
    } 
  }
  
  public TargetPair findTarget(Action targetAction) {
    setAppropriateHomeRange(targetAction);
    Random rand = this.theEntity.getRNG();
    boolean isChestAction = (targetAction == Action.DEPOSITING || targetAction == Action.COLLECTING);
    List<TileEntityChest> chests = new ArrayList<>();
    if (isChestAction)
      chests = gatherNearbyChests(); 
    for (int l = 0; l < 32; l++) {
      int i = 0;
      int j = 0;
      int k = 0;
      boolean suitable = false;
      if (isChestAction) {
        if (!chests.isEmpty()) {
          TileEntityChest chest = chests.get(rand.nextInt(chests.size()));
          i = chest.xCoord;
          j = chest.yCoord;
          k = chest.zCoord;
          if (targetAction == Action.DEPOSITING) {
            suitable = isSuitableForDepositing(i, j, k);
          } else if (targetAction == Action.COLLECTING) {
            suitable = isSuitableForCollecting(i, j, k);
          } 
        } else {
          suitable = false;
        } 
      } else {
        i = MathHelper.floor_double(this.theEntity.posX) + MathHelper.getRandomIntegerInRange(rand, -8, 8);
        j = MathHelper.floor_double(this.theEntity.boundingBox.minY) + MathHelper.getRandomIntegerInRange(rand, -4, 4);
        k = MathHelper.floor_double(this.theEntity.posZ) + MathHelper.getRandomIntegerInRange(rand, -8, 8);
        if (targetAction == Action.HOEING) {
          suitable = isSuitableForHoeing(i, j, k);
        } else if (targetAction == Action.PLANTING) {
          suitable = isSuitableForPlanting(i, j, k);
        } else if (targetAction == Action.HARVESTING) {
          suitable = isSuitableForHarvesting(i, j, k);
        } else if (targetAction == Action.BONEMEALING) {
          suitable = isSuitableForBonemealing(i, j, k);
        } 
      } 
      if (suitable && this.theEntity.isWithinHomeDistance(i, j, k)) {
        ChunkCoordinates target = new ChunkCoordinates(i, j, k);
        ChunkCoordinates path = getPathTarget(i, j, k, targetAction);
        PathEntity pathCheck = this.theEntity.getNavigator().getPathToXYZ(path.posX, path.posY, path.posZ);
        if (pathCheck != null)
          return new TargetPair(target, path); 
      } 
    } 
    return null;
  }
  
  public List<TileEntityChest> gatherNearbyChests() {
    int x = MathHelper.floor_double(this.theEntity.posX);
    MathHelper.floor_double(this.theEntity.boundingBox.minY);
    int z = MathHelper.floor_double(this.theEntity.posZ);
    int searchRange = (int)this.theEntity.func_110174_bM();
    int chunkX = x >> 4;
    int chunkZ = z >> 4;
    int chunkRange = (searchRange >> 4) + 1;
    List<TileEntityChest> nearbyChests = new ArrayList<>();
    for (int i = -chunkRange; i <= chunkRange; i++) {
      for (int k = -chunkRange; k <= chunkRange; k++) {
        int nearChunkX = chunkX + i;
        int nearChunkZ = chunkZ + k;
        if (this.theWorld.getChunkProvider().chunkExists(nearChunkX, nearChunkZ)) {
          Chunk chunk = this.theWorld.getChunkFromChunkCoords(nearChunkX, nearChunkZ);
          for (Object obj : chunk.chunkTileEntityMap.values()) {
            TileEntity te = (TileEntity)obj;
            if (te instanceof TileEntityChest && !te.isInvalid()) {
              TileEntityChest chest = (TileEntityChest)te;
              if (this.theEntity.isWithinHomeDistance(chest.xCoord, chest.yCoord, chest.zCoord))
                nearbyChests.add(chest); 
            } 
          } 
        } 
      } 
    } 
    return nearbyChests;
  }
  
  public ChunkCoordinates getPathTarget(int i, int j, int k, Action targetAction) {
    if (targetAction == Action.HOEING) {
      if (isReplaceable(i, j + 1, k))
        return new ChunkCoordinates(i, j + 1, k); 
      return getAdjacentSolidOpenWalkTarget(i, j + 1, k);
    } 
    if (targetAction == Action.PLANTING || targetAction == Action.HARVESTING || targetAction == Action.BONEMEALING) {
      if (this.harvestingSolidBlock)
        return new ChunkCoordinates(i, j + 1, k); 
      if (isFarmingGrapes()) {
        int groundY = j;
        for (int j1 = 1; j1 <= 2; j1++) {
          if (!(this.theWorld.getBlock(i, j - j1 - 1, k) instanceof LOTRBlockGrapevine)) {
            groundY = j - j1 - 1;
            break;
          } 
        } 
        return getAdjacentSolidOpenWalkTarget(i, groundY + 1, k);
      } 
      return new ChunkCoordinates(i, j, k);
    } 
    if (targetAction == Action.DEPOSITING || targetAction == Action.COLLECTING)
      return getAdjacentSolidOpenWalkTarget(i, j, k); 
    return new ChunkCoordinates(i, j, k);
  }
  
  public boolean isSolidOpenWalkTarget(int i, int j, int k) {
    Block below = this.theWorld.getBlock(i, j - 1, k);
    if (below.isOpaqueCube() || below.canSustainPlant((IBlockAccess)this.theWorld, i, j - 1, k, ForgeDirection.UP, (IPlantable)Blocks.wheat)) {
      List bounds = new ArrayList();
      AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(i, j, k, (i + 1), (j + 2), (k + 1));
      for (int j1 = j; j1 <= j + 1; j1++)
        this.theWorld.getBlock(i, j1, k).addCollisionBoxesToList(this.theWorld, i, j1, k, aabb, bounds, this.theEntity); 
      if (bounds.isEmpty())
        return (this.theEntity.getNavigator().getPathToXYZ(i, j, k) != null); 
    } 
    return false;
  }
  
  public ChunkCoordinates getAdjacentSolidOpenWalkTarget(int i, int j, int k) {
    List<ChunkCoordinates> possibleCoords = new ArrayList<>();
    for (int i1 = -1; i1 <= 1; i1++) {
      for (int k1 = -1; k1 <= 1; k1++) {
        int i2 = i + i1;
        int k2 = k + k1;
        for (int j1 = 1; j1 >= -1; j1--) {
          int j2 = j + j1;
          if (isSolidOpenWalkTarget(i2, j2, k2))
            possibleCoords.add(new ChunkCoordinates(i2, j2, k2)); 
        } 
      } 
    } 
    if (!possibleCoords.isEmpty())
      return possibleCoords.get(0); 
    return new ChunkCoordinates(i, j, k);
  }
  
  public boolean isSuitableForHoeing(ChunkCoordinates pos) {
    return isSuitableForHoeing(pos.posX, pos.posY, pos.posZ);
  }
  
  public boolean isSuitableForHoeing(int i, int j, int k) {
    this.harvestingSolidBlock = false;
    Block block = this.theWorld.getBlock(i, j, k);
    boolean isGrassDirt = block.canSustainPlant(this.theWorld, i, j, k, ForgeDirection.UP, Blocks.tallgrass);
    boolean isFarmland = block.canSustainPlant((IBlockAccess)this.theWorld, i, j, k, ForgeDirection.UP, (IPlantable)Blocks.wheat);
    if (isGrassDirt && !isFarmland && (isReplaceable(i, j + 1, k) || this.theWorld.getBlock(i, j + 1, k) == LOTRMod.grapevine)) {
      Block below = this.theWorld.getBlock(i, j - 1, k);
      if (below == Blocks.sand)
        return false; 
      boolean waterNearby = false;
      int range = 4;
      int i1;
      label24: for (i1 = i - range; i1 <= i + range; i1++) {
        for (int k1 = k - range; k1 <= k + range; k1++) {
          if (this.theWorld.getBlock(i1, j, k1).getMaterial() == Material.water) {
            waterNearby = true;
            break label24;
          } 
        } 
      } 
      return waterNearby;
    } 
    return false;
  }
  
  public boolean isSuitableForPlanting(ChunkCoordinates pos) {
    return isSuitableForPlanting(pos.posX, pos.posY, pos.posZ);
  }
  
  public boolean isSuitableForPlanting(int i, int j, int k) {
    this.harvestingSolidBlock = false;
    if (isFarmingGrapes())
      return (this.theWorld.getBlock(i, j, k) == LOTRMod.grapevine && LOTRBlockGrapevine.canPlantGrapesAt(this.theWorld, i, j, k, getSeedsToPlant())); 
    return (this.theWorld.getBlock(i, j - 1, k).isFertile(this.theWorld, i, j - 1, k) && isReplaceable(i, j, k));
  }
  
  public boolean isSuitableForHarvesting(ChunkCoordinates pos) {
    return isSuitableForHarvesting(pos.posX, pos.posY, pos.posZ);
  }
  
  public boolean isSuitableForHarvesting(int i, int j, int k) {
    this.harvestingSolidBlock = false;
    IPlantable seed = getSeedsToPlant();
    Block plantBlock = seed.getPlant(this.theWorld, i, j, k);
    if (plantBlock instanceof BlockCrops) {
      this.harvestingSolidBlock = false;
      return (this.theWorld.getBlock(i, j, k) == plantBlock && this.theWorld.getBlockMetadata(i, j, k) >= 7);
    } 
    if (plantBlock instanceof BlockStem) {
      this.harvestingSolidBlock = true;
      return (this.theWorld.getBlock(i, j, k) == LOTRReflection.getStemFruitBlock((BlockStem)plantBlock));
    } 
    if (plantBlock instanceof LOTRBlockCorn) {
      this.harvestingSolidBlock = false;
      if (this.theWorld.getBlock(i, j, k) == plantBlock)
        for (int j1 = 0; j1 <= LOTRBlockCorn.MAX_GROW_HEIGHT - 1; j1++) {
          int j2 = j + j1;
          if (this.theWorld.getBlock(i, j2, k) == plantBlock && LOTRBlockCorn.hasCorn(this.theWorld, i, j2, k))
            return true; 
        }  
    } else if (plantBlock instanceof LOTRBlockGrapevine) {
      this.harvestingSolidBlock = false;
      return (this.theWorld.getBlock(i, j, k) == seed.getPlant(this.theWorld, i, j, k) && this.theWorld.getBlockMetadata(i, j, k) >= 7);
    } 
    return false;
  }
  
  public boolean isSuitableForDepositing(ChunkCoordinates pos) {
    return isSuitableForDepositing(pos.posX, pos.posY, pos.posZ);
  }
  
  public boolean isSuitableForDepositing(int i, int j, int k) {
    this.harvestingSolidBlock = false;
    TileEntityChest chest = getSuitableChest(i, j, k);
    if (chest != null)
      for (int l = 1; l <= 2; l++) {
        ItemStack depositItem = this.theEntity.hiredNPCInfo.getHiredInventory().getStackInSlot(l);
        if (depositItem != null)
          for (int slot = 0; slot < chest.getSizeInventory(); slot++) {
            ItemStack chestItem = chest.getStackInSlot(slot);
            if (chestItem == null || (chestItem.isItemEqual(depositItem) && ItemStack.areItemStackTagsEqual(chestItem, depositItem) && chestItem.stackSize < chestItem.getMaxStackSize()))
              return true; 
          }  
      }  
    return false;
  }
  
  public boolean isSuitableForBonemealing(ChunkCoordinates pos) {
    return isSuitableForBonemealing(pos.posX, pos.posY, pos.posZ);
  }
  
  public boolean isSuitableForBonemealing(int i, int j, int k) {
    this.harvestingSolidBlock = false;
    IPlantable seed = getSeedsToPlant();
    Block plantBlock = seed.getPlant(this.theWorld, i, j, k);
    if (plantBlock instanceof IGrowable && this.theWorld.getBlock(i, j, k) == plantBlock) {
      IGrowable growableBlock = (IGrowable)plantBlock;
      if (growableBlock.func_149851_a(this.theWorld, i, j, k, this.theWorld.isRemote)) {
        this.harvestingSolidBlock = plantBlock.isOpaqueCube();
        return true;
      } 
    } 
    return false;
  }
  
  public boolean isSuitableForCollecting(ChunkCoordinates pos) {
    return isSuitableForCollecting(pos.posX, pos.posY, pos.posZ);
  }
  
  public boolean isSuitableForCollecting(int i, int j, int k) {
    this.harvestingSolidBlock = false;
    TileEntityChest chest = getSuitableChest(i, j, k);
    if (chest != null) {
      int[] invSlots = { 0, 3 };
      for (int l : invSlots) {
        ItemStack collectMatch = this.theEntity.hiredNPCInfo.getHiredInventory().getStackInSlot(l);
        if (collectMatch == null)
          if (l == 3)
            collectMatch = new ItemStack(Items.dye, 0, 15);  
        if (collectMatch != null && collectMatch.stackSize <= 16)
          for (int slot = 0; slot < chest.getSizeInventory(); slot++) {
            ItemStack chestItem = chest.getStackInSlot(slot);
            if (chestItem != null && chestItem.isItemEqual(collectMatch) && ItemStack.areItemStackTagsEqual(chestItem, collectMatch) && chestItem.stackSize > 0)
              return true; 
          }  
      } 
    } 
    return false;
  }
  
  public TileEntityChest getSuitableChest(int i, int j, int k) {
    Block block = this.theWorld.getBlock(i, j, k);
    int meta = this.theWorld.getBlockMetadata(i, j, k);
    TileEntityChest suitableChest = null;
    if (block.hasTileEntity(meta)) {
      TileEntity te = this.theWorld.getTileEntity(i, j, k);
      if (te instanceof TileEntityChest) {
        TileEntityChest chest = (TileEntityChest)te;
        boolean flag = false;
        if (isFarmhandMarked(chest)) {
          flag = true;
        } else if (chest.adjacentChestXNeg != null && isFarmhandMarked(chest.adjacentChestXNeg)) {
          flag = true;
        } else if (chest.adjacentChestXPos != null && isFarmhandMarked(chest.adjacentChestXPos)) {
          flag = true;
        } else if (chest.adjacentChestZNeg != null && isFarmhandMarked(chest.adjacentChestZNeg)) {
          flag = true;
        } else if (chest.adjacentChestZPos != null && isFarmhandMarked(chest.adjacentChestZPos)) {
          flag = true;
        } 
        if (flag)
          suitableChest = chest; 
      } 
    } 
    return suitableChest;
  }
  
  public boolean isFarmhandMarked(TileEntityChest chest) {
    int i = chest.xCoord;
    int j = chest.yCoord;
    int k = chest.zCoord;
    AxisAlignedBB chestBB = AxisAlignedBB.getBoundingBox(i, j, k, (i + 1), (j + 1), (k + 1));
    List entities = this.theWorld.getEntitiesWithinAABB(EntityItemFrame.class, chestBB.expand(2.0D, 2.0D, 2.0D));
    for (Object obj : entities) {
      EntityItemFrame frame = (EntityItemFrame)obj;
      if (frame.field_146063_b == i && frame.field_146064_c == j && frame.field_146062_d == k) {
        ItemStack frameItem = frame.getDisplayedItem();
        if (frameItem != null && frameItem.getItem() instanceof net.minecraft.item.ItemHoe)
          return true; 
      } 
    } 
    return false;
  }
  
  public boolean isReplaceable(int i, int j, int k) {
    Block block = this.theWorld.getBlock(i, j, k);
    return (!block.getMaterial().isLiquid() && block.isReplaceable(this.theWorld, i, j, k));
  }
}