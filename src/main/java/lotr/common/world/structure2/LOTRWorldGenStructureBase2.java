package lotr.common.world.structure2;

import java.util.*;

import lotr.common.*;
import lotr.common.block.*;
import lotr.common.entity.*;
import lotr.common.entity.item.*;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.item.*;
import lotr.common.recipe.LOTRBrewingRecipes;
import lotr.common.tileentity.*;
import lotr.common.util.LOTRLog;
import lotr.common.world.biome.LOTRBiome;
import lotr.common.world.structure.*;
import lotr.common.world.structure2.scan.LOTRStructureScan;
import lotr.common.world.village.LOTRVillageGen;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.*;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class LOTRWorldGenStructureBase2 extends WorldGenerator {
    public boolean restrictions = true;
    protected boolean notifyChanges;
    public EntityPlayer usingPlayer = null;
    public boolean shouldFindSurface = false;
    public LOTRVillageGen.AbstractInstance villageInstance;
    public LOTRStructureTimelapse.ThreadTimelapse threadTimelapse;
    protected int originX;
    protected int originY;
    protected int originZ;
    private int rotationMode;
    private StructureBoundingBox sbb;
    private LOTRStructureScan currentStrScan;
    private Map<String, BlockAliasPool> scanAliases = new HashMap<>();
    private Map<String, Float> scanAliasChances = new HashMap<>();

    public LOTRWorldGenStructureBase2(boolean flag) {
        super(flag);
        this.notifyChanges = flag;
    }

    protected void placeRandomFlowerPot(World world, Random random, int i, int j, int k) {
        this.placeFlowerPot(world, i, j, k, this.getRandomFlower(world, random));
    }

    @Override
    public final boolean generate(World world, Random random, int i, int j, int k) {
        return this.generateWithSetRotation(world, random, i, j, k, random.nextInt(4));
    }

    public abstract boolean generateWithSetRotation(World var1, Random var2, int var3, int var4, int var5, int var6);

    protected void setupRandomBlocks(Random random) {
    }

    public int usingPlayerRotation() {
        return LOTRStructures.getRotationFromPlayer(this.usingPlayer);
    }

    public int getRotationMode() {
        return this.rotationMode;
    }

    protected void setOriginAndRotation(World world, int i, int j, int k, int rotation, int shift) {
        this.setOriginAndRotation(world, i, j, k, rotation, shift, 0);
    }

    protected void setOriginAndRotation(World world, int i, int j, int k, int rotation, int shift, int shiftX) {
        --j;
        this.rotationMode = rotation;
        switch(this.getRotationMode()) {
            case 0: {
                k += shift;
                i += shiftX;
                break;
            }
            case 1: {
                i -= shift;
                k += shiftX;
                break;
            }
            case 2: {
                k -= shift;
                i -= shiftX;
                break;
            }
            case 3: {
                i += shift;
                k -= shiftX;
            }
        }
        this.originX = i;
        this.originY = j;
        this.originZ = k;
        if(this.shouldFindSurface) {
            this.shouldFindSurface = false;
            this.findSurface(world, -shiftX, -shift);
        }
    }

    protected void findSurface(World world, int i, int k) {
        int j = 8;
        while(this.getY(j) >= 0) {
            if(this.isSurface(world, i, j, k)) {
                this.originY = this.getY(j);
                break;
            }
            --j;
        }
    }

    public void setStructureBB(StructureBoundingBox box) {
        this.sbb = box;
    }

    public boolean hasSBB() {
        return this.sbb != null;
    }

    private boolean isInSBB(int i, int j, int k) {
        return this.sbb == null ? true : this.sbb.isVecInside(i, j, k);
    }

    protected void setBlockAndMetadata(World world, int i, int j, int k, Block block, int meta) {
        int i1 = i;
        int k1 = k;
        i = this.getX(i1, k1);
        k = this.getZ(i1, k1);
        if(!this.isInSBB(i, j = this.getY(j), k)) {
            return;
        }
        meta = this.rotateMeta(block, meta);
        super.setBlockAndNotifyAdequately(world, i, j, k, block, meta);
        if(meta != 0 && (block instanceof BlockChest || block instanceof LOTRBlockChest || block instanceof LOTRBlockSpawnerChest || block instanceof BlockFurnace || block instanceof LOTRBlockHobbitOven || block instanceof LOTRBlockForgeBase)) {
            world.setBlockMetadataWithNotify(i, j, k, meta, this.notifyChanges ? 3 : 2);
        }
        if(block != Blocks.air && this.threadTimelapse != null) {
            this.threadTimelapse.onBlockSet();
        }
    }

    protected int rotateMeta(Block block, int meta) {
        if(block instanceof BlockRotatedPillar) {
            int i = meta & 3;
            int j = meta & 0xC;
            if(j == 0) {
                return meta;
            }
            if(this.rotationMode == 0 || this.rotationMode == 2) {
                return meta;
            }
            if(j == 4) {
                j = 8;
            }
            else if(j == 8) {
                j = 4;
            }
            return j | i;
        }
        if(block instanceof BlockStairs) {
            int i = meta & 3;
            int j = meta & 4;
            for(int l = 0; l < this.rotationMode; ++l) {
                if(i == 2) {
                    i = 1;
                    continue;
                }
                if(i == 1) {
                    i = 3;
                    continue;
                }
                if(i == 3) {
                    i = 0;
                    continue;
                }
                if(i != 0) continue;
                i = 2;
            }
            return j | i;
        }
        if(block instanceof LOTRBlockMug || block instanceof BlockTripWireHook || block instanceof BlockAnvil) {
            int i = meta;
            for(int l = 0; l < this.rotationMode; ++l) {
                i = Direction.rotateRight[i];
            }
            return i;
        }
        if(block instanceof LOTRBlockArmorStand) {
            int i = meta & 3;
            int j = meta & 4;
            for(int l = 0; l < this.rotationMode; ++l) {
                i = Direction.rotateRight[i];
            }
            return j | i;
        }
        if(block instanceof LOTRBlockWeaponRack) {
            int i = meta & 3;
            int j = meta & 4;
            for(int l = 0; l < this.rotationMode; ++l) {
                i = Direction.rotateRight[i];
            }
            return j | i;
        }
        if(block == Blocks.wall_sign || block instanceof BlockLadder || block instanceof BlockFurnace || block instanceof BlockChest || block instanceof LOTRBlockChest || block instanceof LOTRBlockBarrel || block instanceof LOTRBlockHobbitOven || block instanceof LOTRBlockForgeBase || block instanceof LOTRBlockKebabStand) {
            if(meta == 0 && (block instanceof BlockFurnace || block instanceof BlockChest || block instanceof LOTRBlockChest || block instanceof LOTRBlockHobbitOven || block instanceof LOTRBlockForgeBase)) {
                return meta;
            }
            int i = meta;
            for(int l = 0; l < this.rotationMode; ++l) {
                i = Direction.facingToDirection[i];
                i = Direction.rotateRight[i];
                i = Direction.directionToFacing[i];
            }
            return i;
        }
        if(block == Blocks.standing_sign) {
            int i = meta;
            i += this.rotationMode * 4;
            return i &= 0xF;
        }
        if(block instanceof BlockBed) {
            boolean flag;
            int i = meta;
            flag = meta >= 8;
            if(flag) {
                i -= 8;
            }
            for(int l = 0; l < this.rotationMode; ++l) {
                i = Direction.rotateRight[i];
            }
            if(flag) {
                i += 8;
            }
            return i;
        }
        if(block instanceof BlockTorch) {
            if(meta == 5) {
                return 5;
            }
            int i = meta;
            for(int l = 0; l < this.rotationMode; ++l) {
                if(i == 4) {
                    i = 1;
                    continue;
                }
                if(i == 1) {
                    i = 3;
                    continue;
                }
                if(i == 3) {
                    i = 2;
                    continue;
                }
                if(i != 2) continue;
                i = 4;
            }
            return i;
        }
        if(block instanceof BlockDoor) {
            if((meta & 8) != 0) {
                return meta;
            }
            int i = meta & 3;
            int j = meta & 4;
            for(int l = 0; l < this.rotationMode; ++l) {
                i = Direction.rotateRight[i];
            }
            return j | i;
        }
        if(block instanceof BlockTrapDoor) {
            int i = meta & 3;
            int j = meta & 4;
            int k = meta & 8;
            for(int l = 0; l < this.rotationMode; ++l) {
                if(i == 0) {
                    i = 3;
                    continue;
                }
                if(i == 1) {
                    i = 2;
                    continue;
                }
                if(i == 2) {
                    i = 0;
                    continue;
                }
                if(i != 3) continue;
                i = 1;
            }
            return k | j | i;
        }
        if(block instanceof BlockFenceGate) {
            int i = meta & 3;
            int j = meta & 4;
            for(int l = 0; l < this.rotationMode; ++l) {
                i = Direction.rotateRight[i];
            }
            return j | i;
        }
        if(block instanceof BlockPumpkin) {
            int i = meta;
            for(int l = 0; l < this.rotationMode; ++l) {
                i = Direction.rotateRight[i];
            }
            return i;
        }
        if(block instanceof BlockSkull) {
            if(meta < 2) {
                return meta;
            }
            int i = Direction.facingToDirection[meta];
            for(int l = 0; l < this.rotationMode; ++l) {
                i = Direction.rotateRight[i];
            }
            return Direction.directionToFacing[i];
        }
        if(block instanceof LOTRBlockGate) {
            int i = meta & 7;
            int j = meta & 8;
            if(i != 0 && i != 1) {
                for(int l = 0; l < this.rotationMode; ++l) {
                    i = Direction.facingToDirection[i];
                    i = Direction.rotateRight[i];
                    i = Direction.directionToFacing[i];
                }
            }
            return j | i;
        }
        if(block instanceof BlockLever) {
            int i = meta & 7;
            int j = meta & 8;
            if(i == 0 || i == 7) {
                for(int l = 0; l < this.rotationMode; ++l) {
                    i = i == 0 ? 7 : 0;
                }
            }
            else if(i == 5 || i == 6) {
                for(int l = 0; l < this.rotationMode; ++l) {
                    i = i == 5 ? 6 : 5;
                }
            }
            else {
                for(int l = 0; l < this.rotationMode; ++l) {
                    if(i == 4) {
                        i = 1;
                        continue;
                    }
                    if(i == 1) {
                        i = 3;
                        continue;
                    }
                    if(i == 3) {
                        i = 2;
                        continue;
                    }
                    if(i != 2) continue;
                    i = 4;
                }
            }
            return j | i;
        }
        if(block instanceof BlockButton) {
            int i = meta;
            int j = meta & 8;
            for(int l = 0; l < this.rotationMode; ++l) {
                if(i == 4) {
                    i = 1;
                    continue;
                }
                if(i == 1) {
                    i = 3;
                    continue;
                }
                if(i == 3) {
                    i = 2;
                    continue;
                }
                if(i != 2) continue;
                i = 4;
            }
            return j | i;
        }
        return meta;
    }

    protected Block getBlock(World world, int i, int j, int k) {
        int i1 = i;
        int k1 = k;
        i = this.getX(i1, k1);
        k = this.getZ(i1, k1);
        if(!this.isInSBB(i, j = this.getY(j), k)) {
            return Blocks.air;
        }
        return world.getBlock(i, j, k);
    }

    protected int getMeta(World world, int i, int j, int k) {
        int i1 = i;
        int k1 = k;
        i = this.getX(i1, k1);
        k = this.getZ(i1, k1);
        if(!this.isInSBB(i, j = this.getY(j), k)) {
            return 0;
        }
        return world.getBlockMetadata(i, j, k);
    }

    protected int getTopBlock(World world, int i, int k) {
        int i1 = i;
        int k1 = k;
        i = this.getX(i1, k1);
        if(!this.isInSBB(i, 0, k = this.getZ(i1, k1))) {
            return 0;
        }
        return world.getTopSolidOrLiquidBlock(i, k) - this.originY;
    }

    protected BiomeGenBase getBiome(World world, int i, int k) {
        int i1 = i;
        int k1 = k;
        i = this.getX(i1, k1);
        if(!this.isInSBB(i, 0, k = this.getZ(i1, k1))) {
            return null;
        }
        return world.getBiomeGenForCoords(i, k);
    }

    protected boolean isAir(World world, int i, int j, int k) {
        return this.getBlock(world, i, j, k).getMaterial() == Material.air;
    }

    protected boolean isOpaque(World world, int i, int j, int k) {
        return this.getBlock(world, i, j, k).isOpaqueCube();
    }

    protected boolean isReplaceable(World world, int i, int j, int k) {
        return this.getBlock(world, i, j, k).isReplaceable(world, this.getX(i, k), this.getY(j), this.getZ(i, k));
    }

    protected boolean isSideSolid(World world, int i, int j, int k, ForgeDirection side) {
        return this.getBlock(world, i, j, k).isSideSolid(world, this.getX(i, k), this.getY(j), this.getZ(i, k), side);
    }

    protected TileEntity getTileEntity(World world, int i, int j, int k) {
        int i1 = i;
        int k1 = k;
        i = this.getX(i1, k1);
        k = this.getZ(i1, k1);
        if(!this.isInSBB(i, j = this.getY(j), k)) {
            return null;
        }
        return world.getTileEntity(i, j, k);
    }

    protected void placeChest(World world, Random random, int i, int j, int k, int meta, LOTRChestContents contents) {
        this.placeChest(world, random, i, j, k, meta, contents, -1);
    }

    protected void placeChest(World world, Random random, int i, int j, int k, int meta, LOTRChestContents contents, int amount) {
        this.placeChest(world, random, i, j, k, Blocks.chest, meta, contents, amount);
    }

    protected void placeChest(World world, Random random, int i, int j, int k, Block chest, int meta, LOTRChestContents contents) {
        this.placeChest(world, random, i, j, k, chest, meta, contents, -1);
    }

    protected void placeChest(World world, Random random, int i, int j, int k, Block chest, int meta, LOTRChestContents contents, int amount) {
        this.setBlockAndMetadata(world, i, j, k, chest, meta);
        this.fillChest(world, random, i, j, k, contents, amount);
    }

    protected void fillChest(World world, Random random, int i, int j, int k, LOTRChestContents contents, int amount) {
        int i1 = i;
        int k1 = k;
        i = this.getX(i1, k1);
        k = this.getZ(i1, k1);
        if(!this.isInSBB(i, j = this.getY(j), k)) {
            return;
        }
        LOTRChestContents.fillChest(world, random, i, j, k, contents, amount);
    }

    protected void putInventoryInChest(World world, int i, int j, int k, IInventory inv) {
        TileEntity tileentity = this.getTileEntity(world, i, j, k);
        if(tileentity instanceof IInventory) {
            IInventory blockInv = (IInventory) (tileentity);
            for(int l = 0; l < blockInv.getSizeInventory() && l < inv.getSizeInventory(); ++l) {
                blockInv.setInventorySlotContents(l, inv.getStackInSlot(l));
            }
        }
    }

    protected void placeOrcTorch(World world, int i, int j, int k) {
        this.setBlockAndMetadata(world, i, j, k, LOTRMod.orcTorch, 0);
        this.setBlockAndMetadata(world, i, j + 1, k, LOTRMod.orcTorch, 1);
    }

    protected void placeMobSpawner(World world, int i, int j, int k, Class entityClass) {
        this.setBlockAndMetadata(world, i, j, k, LOTRMod.mobSpawner, 0);
        TileEntity tileentity = this.getTileEntity(world, i, j, k);
        if(tileentity instanceof LOTRTileEntityMobSpawner) {
            ((LOTRTileEntityMobSpawner) tileentity).setEntityClassID(LOTREntities.getEntityIDFromClass(entityClass));
        }
    }

    protected void placeSpawnerChest(World world, int i, int j, int k, Block block, int meta, Class entityClass) {
        this.placeSpawnerChest(world, null, i, j, k, block, meta, entityClass, null);
    }

    protected void placeSpawnerChest(World world, Random random, int i, int j, int k, Block block, int meta, Class entityClass, LOTRChestContents contents) {
        this.placeSpawnerChest(world, random, i, j, k, block, meta, entityClass, contents, -1);
    }

    protected void placeSpawnerChest(World world, Random random, int i, int j, int k, Block block, int meta, Class entityClass, LOTRChestContents contents, int amount) {
        this.setBlockAndMetadata(world, i, j, k, block, meta);
        TileEntity tileentity = this.getTileEntity(world, i, j, k);
        if(tileentity instanceof LOTRTileEntitySpawnerChest) {
            ((LOTRTileEntitySpawnerChest) tileentity).setMobID(entityClass);
        }
        if(contents != null) {
            this.fillChest(world, random, i, j, k, contents, amount);
        }
    }

    protected void placePlate(World world, Random random, int i, int j, int k, Block plateBlock, LOTRFoods foodList) {
        this.placePlate_list(world, random, i, j, k, plateBlock, foodList, false);
    }

    protected void placePlateWithCertainty(World world, Random random, int i, int j, int k, Block plateBlock, LOTRFoods foodList) {
        this.placePlate_list(world, random, i, j, k, plateBlock, foodList, true);
    }

    protected void placePlate_list(World world, Random random, int i, int j, int k, Block plateBlock, LOTRFoods foodList, boolean certain) {
        ItemStack food = foodList.getRandomFoodForPlate(random);
        if(random.nextInt(4) == 0) {
            food.stackSize += 1 + random.nextInt(3);
        }
        this.placePlate_item(world, random, i, j, k, plateBlock, food, certain);
    }

    protected void placePlate_item(World world, Random random, int i, int j, int k, Block plateBlock, ItemStack foodItem, boolean certain) {
        this.placePlate_do(world, random, i, j, k, plateBlock, foodItem, certain);
    }

    private void placePlate_do(World world, Random random, int i, int j, int k, Block plateBlock, ItemStack foodItem, boolean certain) {
        TileEntity tileentity;
        if(!certain && random.nextBoolean()) {
            return;
        }
        this.setBlockAndMetadata(world, i, j, k, plateBlock, 0);
        if((certain || random.nextBoolean()) && (tileentity = this.getTileEntity(world, i, j, k)) instanceof LOTRTileEntityPlate) {
            LOTRTileEntityPlate plate = (LOTRTileEntityPlate) tileentity;
            plate.setFoodItem(foodItem);
        }
    }

    protected void placeBarrel(World world, Random random, int i, int j, int k, int meta, LOTRFoods foodList) {
        this.placeBarrel(world, random, i, j, k, meta, foodList.getRandomBrewableDrink(random));
    }

    protected void placeBarrel(World world, Random random, int i, int j, int k, int meta, ItemStack drink) {
        this.setBlockAndMetadata(world, i, j, k, LOTRMod.barrel, meta);
        TileEntity tileentity = this.getTileEntity(world, i, j, k);
        if(tileentity instanceof LOTRTileEntityBarrel) {
            LOTRTileEntityBarrel barrel = (LOTRTileEntityBarrel) tileentity;
            barrel.barrelMode = 2;
            drink = drink.copy();
            LOTRItemMug.setStrengthMeta(drink, MathHelper.getRandomIntegerInRange(random, 1, 3));
            LOTRItemMug.setVessel(drink, LOTRItemMug.Vessel.MUG, true);
            drink.stackSize = MathHelper.getRandomIntegerInRange(random, LOTRBrewingRecipes.BARREL_CAPACITY / 2, LOTRBrewingRecipes.BARREL_CAPACITY);
            barrel.setInventorySlotContents(9, drink);
        }
    }

    protected void placeMug(World world, Random random, int i, int j, int k, int meta, LOTRFoods foodList) {
        this.placeMug(world, random, i, j, k, meta, foodList.getRandomPlaceableDrink(random), foodList);
    }

    protected void placeMug(World world, Random random, int i, int j, int k, int meta, ItemStack drink, LOTRFoods foodList) {
        this.placeMug(world, random, i, j, k, meta, drink, foodList.getPlaceableDrinkVessels());
    }

    protected void placeMug(World world, Random random, int i, int j, int k, int meta, ItemStack drink, LOTRItemMug.Vessel[] vesselTypes) {
        LOTRItemMug.Vessel vessel = vesselTypes[random.nextInt(vesselTypes.length)];
        this.setBlockAndMetadata(world, i, j, k, vessel.getBlock(), meta);
        if(random.nextInt(3) != 0) {
            int i1 = i;
            int k1 = k;
            i = this.getX(i1, k1);
            k = this.getZ(i1, k1);
            if(!this.isInSBB(i, j = this.getY(j), k)) {
                return;
            }
            drink = drink.copy();
            drink.stackSize = 1;
            if(drink.getItem() instanceof LOTRItemMug && ((LOTRItemMug) drink.getItem()).isBrewable) {
                LOTRItemMug.setStrengthMeta(drink, MathHelper.getRandomIntegerInRange(random, 1, 3));
            }
            LOTRItemMug.setVessel(drink, vessel, true);
            LOTRBlockMug.setMugItem(world, i, j, k, drink, vessel);
        }
    }

    protected void placeKebabStand(World world, Random random, int i, int j, int k, Block block, int meta) {
        this.setBlockAndMetadata(world, i, j, k, block, meta);
        TileEntity tileentity = this.getTileEntity(world, i, j, k);
        if(tileentity instanceof LOTRTileEntityKebabStand) {
            LOTRTileEntityKebabStand stand = (LOTRTileEntityKebabStand) tileentity;
            int kebab = MathHelper.getRandomIntegerInRange(random, 1, 8);
            stand.generateCookedKebab(kebab);
        }
    }

    protected void plantFlower(World world, Random random, int i, int j, int k) {
        ItemStack itemstack = this.getRandomFlower(world, random);
        this.setBlockAndMetadata(world, i, j, k, Block.getBlockFromItem(itemstack.getItem()), itemstack.getItemDamage());
    }

    protected void placeFlowerPot(World world, int i, int j, int k, ItemStack itemstack) {
        boolean vanilla;
        vanilla = itemstack == null || itemstack.getItem() == Item.getItemFromBlock(Blocks.cactus);
        if(vanilla) {
            this.setBlockAndMetadata(world, i, j, k, Blocks.flower_pot, 0);
        }
        else {
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.flowerPot, 0);
        }
        int i1 = i;
        int k1 = k;
        i = this.getX(i1, k1);
        k = this.getZ(i1, k1);
        j = this.getY(j);
        if(!this.isInSBB(i, j, k)) {
            return;
        }
        if(itemstack != null) {
            if(vanilla) {
                TileEntity te = world.getTileEntity(i, j, k);
                if(te instanceof TileEntityFlowerPot) {
                    TileEntityFlowerPot pot = (TileEntityFlowerPot) te;
                    pot.func_145964_a(itemstack.getItem(), itemstack.getItemDamage());
                    pot.markDirty();
                }
            }
            else {
                LOTRBlockFlowerPot.setPlant(world, i, j, k, itemstack);
            }
        }
    }

    protected ItemStack getRandomFlower(World world, Random random) {
        int i = 0;
        int j = 0;
        int k = 0;
        BiomeGenBase biome = this.getBiome(world, i, k);
        if (biome instanceof LOTRBiome) {
            BiomeGenBase.FlowerEntry fe = ((LOTRBiome)biome).getRandomFlower(world, random, this.getX(i, k), this.getY(j), this.getZ(i, k));
            return new ItemStack(fe.block, 1, fe.metadata);
        }
        if (random.nextBoolean()) {
            return new ItemStack(Blocks.yellow_flower, 0);
        }
        return new ItemStack(Blocks.red_flower, 0);
    }


    protected ItemStack getRandomTallGrass(World world, Random random) {
        BiomeGenBase biome = this.getBiome(world, 0, 0);
        if(biome instanceof LOTRBiome) {
            LOTRBiome.GrassBlockAndMeta gbm = ((LOTRBiome) biome).getRandomGrass(random);
            return new ItemStack(gbm.block, 1, gbm.meta);
        }
        return new ItemStack(Blocks.tallgrass, 1, 1);
    }

    protected void plantTallGrass(World world, Random random, int i, int j, int k) {
        ItemStack itemstack = this.getRandomTallGrass(world, random);
        this.setBlockAndMetadata(world, i, j, k, Block.getBlockFromItem(itemstack.getItem()), itemstack.getItemDamage());
    }

    protected void spawnItemFrame(World world, int i, int j, int k, int direction, ItemStack itemstack) {
        int i1 = i;
        int k1 = k;
        i = this.getX(i1, k1);
        k = this.getZ(i1, k1);
        if(!this.isInSBB(i, j = this.getY(j), k)) {
            return;
        }
        for(int l = 0; l < this.rotationMode; ++l) {
            direction = Direction.rotateRight[direction];
        }
        EntityItemFrame frame = new EntityItemFrame(world, i, j, k, direction);
        frame.setDisplayedItem(itemstack);
        world.spawnEntityInWorld(frame);
    }

    protected void placeArmorStand(World world, int i, int j, int k, int direction, ItemStack[] armor) {
        this.setBlockAndMetadata(world, i, j, k, LOTRMod.armorStand, direction);
        this.setBlockAndMetadata(world, i, j + 1, k, LOTRMod.armorStand, direction | 4);
        TileEntity tileentity = this.getTileEntity(world, i, j, k);
        if(tileentity instanceof LOTRTileEntityArmorStand) {
            LOTRTileEntityArmorStand armorStand = (LOTRTileEntityArmorStand) tileentity;
            if(armor != null) {
                for(int l = 0; l < armor.length; ++l) {
                    ItemStack armorPart = armor[l];
                    if(armorPart == null) {
                        armorStand.setInventorySlotContents(l, null);
                        continue;
                    }
                    armorStand.setInventorySlotContents(l, armor[l].copy());
                }
            }
        }
    }

    protected void placeWeaponRack(World world, int i, int j, int k, int meta, ItemStack weapon) {
        this.setBlockAndMetadata(world, i, j, k, LOTRMod.weaponRack, meta);
        TileEntity tileentity = this.getTileEntity(world, i, j, k);
        if(tileentity instanceof LOTRTileEntityWeaponRack) {
            LOTRTileEntityWeaponRack weaponRack = (LOTRTileEntityWeaponRack) tileentity;
            if(weapon != null) {
                weaponRack.setWeaponItem(weapon.copy());
            }
        }
    }

    protected void placeBanner(World world, int i, int j, int k, LOTRItemBanner.BannerType bt, int direction) {
        this.placeBanner(world, i, j, k, bt, direction, false, 0);
    }

    protected void placeBanner(World world, int i, int j, int k, LOTRItemBanner.BannerType bt, int direction, boolean protection, int r) {
        int i1 = i;
        int k1 = k;
        i = this.getX(i1, k1);
        k = this.getZ(i1, k1);
        if(!this.isInSBB(i, j = this.getY(j), k)) {
            return;
        }
        for(int l = 0; l < this.rotationMode; ++l) {
            direction = Direction.rotateRight[direction];
        }
        LOTREntityBanner banner = new LOTREntityBanner(world);
        banner.setLocationAndAngles(i + 0.5, j, k + 0.5, direction * 90.0f, 0.0f);
        banner.setBannerType(bt);
        if(protection) {
            banner.setStructureProtection(true);
            banner.setSelfProtection(false);
        }
        if(r > 0) {
            if(r > 64) {
                throw new RuntimeException("WARNING: Banner protection range " + r + " is too large!");
            }
            banner.setCustomRange(r);
        }
        world.spawnEntityInWorld(banner);
    }

    protected void placeWallBanner(World world, int i, int j, int k, LOTRItemBanner.BannerType bt, int direction) {
        int i1 = i;
        int k1 = k;
        i = this.getX(i1, k1);
        k = this.getZ(i1, k1);
        if(!this.isInSBB(i, j = this.getY(j), k)) {
            return;
        }
        for(int l = 0; l < this.rotationMode; ++l) {
            direction = Direction.rotateRight[direction];
        }
        LOTREntityBannerWall banner = new LOTREntityBannerWall(world, i, j, k, direction);
        banner.setBannerType(bt);
        world.spawnEntityInWorld(banner);
    }

    protected void setGrassToDirt(World world, int i, int j, int k) {
        int i1 = i;
        int k1 = k;
        i = this.getX(i1, k1);
        k = this.getZ(i1, k1);
        if(!this.isInSBB(i, j = this.getY(j), k)) {
            return;
        }
        world.getBlock(i, j, k).onPlantGrow(world, i, j, k, i, j, k);
    }

    protected void setBiomeTop(World world, int i, int j, int k) {
        BiomeGenBase biome = this.getBiome(world, i, k);
        Block topBlock = biome.topBlock;
        int topMeta = 0;
        if(biome instanceof LOTRBiome) {
            topMeta = ((LOTRBiome) biome).topBlockMeta;
        }
        this.setBlockAndMetadata(world, i, j, k, topBlock, topMeta);
    }

    protected void setBiomeFiller(World world, int i, int j, int k) {
        BiomeGenBase biome = this.getBiome(world, i, k);
        Block fillerBlock = biome.fillerBlock;
        int fillerMeta = 0;
        if(biome instanceof LOTRBiome) {
            fillerMeta = ((LOTRBiome) biome).fillerBlockMeta;
        }
        this.setBlockAndMetadata(world, i, j, k, fillerBlock, fillerMeta);
    }

    protected void setAir(World world, int i, int j, int k) {
        this.setBlockAndMetadata(world, i, j, k, Blocks.air, 0);
    }

    protected void placeSkull(World world, Random random, int i, int j, int k) {
        this.placeSkull(world, i, j, k, random.nextInt(16));
    }

    protected void placeSkull(World world, int i, int j, int k, int dir) {
        this.setBlockAndMetadata(world, i, j, k, Blocks.skull, 1);
        TileEntity tileentity = this.getTileEntity(world, i, j, k);
        if(tileentity instanceof TileEntitySkull) {
            TileEntitySkull skull = (TileEntitySkull) tileentity;
            dir += this.rotationMode * 4;
            skull.func_145903_a(dir %= 16);
        }
    }

    protected void placeSign(World world, int i, int j, int k, Block block, int meta, String[] text) {
        this.setBlockAndMetadata(world, i, j, k, block, meta);
        TileEntity te = this.getTileEntity(world, i, j, k);
        if(te instanceof TileEntitySign) {
            TileEntitySign sign = (TileEntitySign) te;
            for(int l = 0; l < sign.signText.length; ++l) {
                sign.signText[l] = text[l];
            }
        }
    }

    protected void placeAnimalJar(World world, int i, int j, int k, Block block, int meta, EntityLiving creature) {
        this.setBlockAndMetadata(world, i, j, k, block, meta);
        TileEntity te = this.getTileEntity(world, i, j, k);
        if(te instanceof LOTRTileEntityAnimalJar) {
            LOTRTileEntityAnimalJar jar = (LOTRTileEntityAnimalJar) te;
            NBTTagCompound nbt = new NBTTagCompound();
            if(creature != null) {
                int i1 = this.getX(i, k);
                int j1 = this.getY(j);
                int k1 = this.getZ(i, k);
                creature.setPosition(i1 + 0.5, j1, k1 + 0.5);
                creature.onSpawnWithEgg(null);
                if(creature.writeToNBTOptional(nbt)) {
                    jar.setEntityData(nbt);
                }
            }
        }
    }

    protected void placeIthildinDoor(World world, int i, int j, int k, Block block, int meta, LOTRBlockGateDwarvenIthildin.DoorSize doorSize) {
        int i1 = this.getX(i, k);
        int j1 = this.getY(j);
        int k1 = this.getZ(i, k);
        int xzFactorX = meta == 2 ? -1 : (xzFactorX = meta == 3 ? 1 : 0);
        int xzFactorZ = meta == 4 ? 1 : (meta == 5 ? -1 : 0);
        for(int y = 0; y < doorSize.height; ++y) {
            for(int xz = 0; xz < doorSize.width; ++xz) {
                int i2 = i + xz * xzFactorX;
                int j2 = j + y;
                int k2 = k + xz * xzFactorZ;
                this.setBlockAndMetadata(world, i2, j2, k2, block, meta);
                LOTRTileEntityDwarvenDoor door = (LOTRTileEntityDwarvenDoor) this.getTileEntity(world, i2, j2, k2);
                if(door == null) continue;
                door.setDoorSizeAndPos(doorSize, xz, y);
                door.setDoorBasePos(i1, j1, k1);
            }
        }
    }

    protected void spawnNPCAndSetHome(EntityCreature entity, World world, int i, int j, int k, int homeDistance) {
        int i1 = i;
        int k1 = k;
        i = this.getX(i1, k1);
        k = this.getZ(i1, k1);
        if(!this.isInSBB(i, j = this.getY(j), k)) {
            return;
        }
        entity.setLocationAndAngles(i + 0.5, j, k + 0.5, 0.0f, 0.0f);
        entity.onSpawnWithEgg(null);
        if(entity instanceof LOTREntityNPC) {
            ((LOTREntityNPC) entity).isNPCPersistent = true;
        }
        world.spawnEntityInWorld(entity);
        entity.setHomeArea(i, j, k, homeDistance);
    }

    protected void leashEntityTo(EntityCreature entity, World world, int i, int j, int k) {
        int i1 = i;
        int k1 = k;
        i = this.getX(i1, k1);
        k = this.getZ(i1, k1);
        if(!this.isInSBB(i, j = this.getY(j), k)) {
            return;
        }
        EntityLeashKnot leash = EntityLeashKnot.func_110129_a(world, i, j, k);
        entity.setLeashedToEntity(leash, true);
    }

    protected void placeNPCRespawner(LOTREntityNPCRespawner entity, World world, int i, int j, int k) {
        int i1 = i;
        int k1 = k;
        i = this.getX(i1, k1);
        k = this.getZ(i1, k1);
        if(!this.isInSBB(i, j = this.getY(j), k)) {
            return;
        }
        entity.setLocationAndAngles(i + 0.5, j, k + 0.5, 0.0f, 0.0f);
        world.spawnEntityInWorld(entity);
    }

    protected void placeRug(LOTREntityRugBase rug, World world, int i, int j, int k, float rotation) {
        int i1 = i;
        int k1 = k;
        i = this.getX(i1, k1);
        k = this.getZ(i1, k1);
        if(!this.isInSBB(i, j = this.getY(j), k)) {
            return;
        }
        float f = rotation;
        switch(this.rotationMode) {
            case 0: {
                f += 0.0f;
                break;
            }
            case 1: {
                f += 270.0f;
                break;
            }
            case 2: {
                f += 180.0f;
                break;
            }
            case 3: {
                f += 90.0f;
            }
        }
        rug.setLocationAndAngles(i + 0.5, j, k + 0.5, f %= 360.0f, 0.0f);
        world.spawnEntityInWorld(rug);
    }

    protected boolean generateSubstructure(LOTRWorldGenStructureBase2 str, World world, Random random, int i, int j, int k, int r) {
        return this.generateSubstructureWithRestrictionFlag(str, world, random, i, j, k, r, this.restrictions);
    }

    protected boolean generateSubstructureWithRestrictionFlag(LOTRWorldGenStructureBase2 str, World world, Random random, int i, int j, int k, int r, boolean isRestrict) {
        int i1 = i;
        int k1 = k;
        i = this.getX(i1, k1);
        k = this.getZ(i1, k1);
        j = this.getY(j);
        r += this.rotationMode;
        str.restrictions = isRestrict;
        str.usingPlayer = this.usingPlayer;
        str.villageInstance = this.villageInstance;
        str.threadTimelapse = this.threadTimelapse;
        str.setStructureBB(this.sbb);
        return str.generateWithSetRotation(world, random, i, j, k, r %= 4);
    }

    protected void loadStrScan(String name) {
        this.currentStrScan = LOTRStructureScan.getScanByName(name);
        if(this.currentStrScan == null) {
            LOTRLog.logger.error("LOTR: Structure Scan for name " + name + " does not exist!!!");
        }
        this.scanAliases.clear();
    }

    protected void associateBlockAlias(String alias, Block block) {
        this.addBlockAliasOption(alias, 1, block);
    }

    protected void addBlockAliasOption(String alias, int weight, Block block) {
        this.addBlockMetaAliasOption(alias, weight, block, -1);
    }

    protected void associateBlockMetaAlias(String alias, Block block, int meta) {
        this.addBlockMetaAliasOption(alias, 1, block, meta);
    }

    protected void addBlockMetaAliasOption(String alias, int weight, Block block, int meta) {
        BlockAliasPool pool = this.scanAliases.get(alias);
        if(pool == null) {
            pool = new BlockAliasPool();
            this.scanAliases.put(alias, pool);
        }
        pool.addEntry(1, block, meta);
    }

    protected void setBlockAliasChance(String alias, float chance) {
        this.scanAliasChances.put(alias, chance);
    }

    protected void clearScanAlias(String alias) {
        this.scanAliases.remove(alias);
        this.scanAliasChances.remove(alias);
    }

    protected void generateStrScan(World world, Random random, int i, int j, int k) {
        for(int pass = 0; pass <= 1; ++pass) {
            for(LOTRStructureScan.ScanStepBase step : this.currentStrScan.scanSteps) {
                int i1 = i - step.x;
                int j1 = j + step.y;
                int k1 = k + step.z;
                Block aliasBlock = null;
                int aliasMeta = -1;
                if(step.hasAlias()) {
                    String alias = step.getAlias();
                    BlockAliasPool pool = this.scanAliases.get(alias);
                    if(pool == null) {
                        throw new IllegalArgumentException("No block associated to alias " + alias + " !");
                    }
                    BlockAliasPool.BlockMetaEntry e = pool.getEntry(random);
                    aliasBlock = e.block;
                    aliasMeta = e.meta;
                    if(this.scanAliasChances.containsKey(alias)) {
                        float chance = this.scanAliasChances.get(alias);
                        if((random.nextFloat() >= chance)) continue;
                    }
                }
                Block block = step.getBlock(aliasBlock);
                int meta = step.getMeta(aliasMeta);
                boolean inThisPass = false;
                if(block.getMaterial().isOpaque() || block == Blocks.air) {
                    inThisPass = pass == 0;
                }
                else {
                    inThisPass = pass == 1;
                }
                if(!inThisPass) continue;
                if(step.findLowest) {
                    while(this.getY(j1) > 0 && !this.getBlock(world, i1, j1 - 1, k1).getMaterial().blocksMovement()) {
                        --j1;
                    }
                }
                if(step instanceof LOTRStructureScan.ScanStepSkull) {
                    this.placeSkull(world, random, i1, j1, k1);
                    continue;
                }
                this.setBlockAndMetadata(world, i1, j1, k1, block, meta);
                if((step.findLowest || j1 <= 1) && block.isOpaqueCube()) {
                    this.setGrassToDirt(world, i1, j1 - 1, k1);
                }
                if(!step.fillDown) continue;
                int j2 = j1 - 1;
                while(!this.isOpaque(world, i1, j2, k1) && this.getY(j2) >= 0) {
                    this.setBlockAndMetadata(world, i1, j2, k1, block, meta);
                    if(block.isOpaqueCube()) {
                        this.setGrassToDirt(world, i1, j2 - 1, k1);
                    }
                    --j2;
                }
            }
        }
        this.currentStrScan = null;
        this.scanAliases.clear();
    }

    protected int getX(int x, int z) {
        switch(this.rotationMode) {
            case 0: {
                return this.originX - x;
            }
            case 1: {
                return this.originX - z;
            }
            case 2: {
                return this.originX + x;
            }
            case 3: {
                return this.originX + z;
            }
        }
        return this.originX;
    }

    protected int getZ(int x, int z) {
        switch(this.rotationMode) {
            case 0: {
                return this.originZ + z;
            }
            case 1: {
                return this.originZ - x;
            }
            case 2: {
                return this.originZ - z;
            }
            case 3: {
                return this.originZ + x;
            }
        }
        return this.originZ;
    }

    protected int getY(int y) {
        return this.originY + y;
    }

    protected final boolean isSurface(World world, int i, int j, int k) {
        int i1 = i;
        int k1 = k;
        i = this.getX(i1, k1);
        k = this.getZ(i1, k1);
        if (LOTRWorldGenStructureBase2.isSurfaceStatic(world, i, j = this.getY(j), k)) {
            return true;
        }
        return this.villageInstance != null && this.villageInstance.isVillageSpecificSurface(world, i, j, k);
    }

    public static boolean isSurfaceStatic(World world, int i, int j, int k) {
        Block block = world.getBlock(i, j, k);
        BiomeGenBase biome = world.getBiomeGenForCoords(i, k);
        if(block instanceof BlockSlab && !block.isOpaqueCube()) {
            return LOTRWorldGenStructureBase2.isSurfaceStatic(world, i, j - 1, k);
        }
        Block above = world.getBlock(i, j + 1, k);
        if(above.getMaterial().isLiquid()) {
            return false;
        }
        if(block == biome.topBlock || block == biome.fillerBlock) {
            return true;
        }
        if(block == Blocks.grass || block == Blocks.dirt || block == Blocks.gravel || block == LOTRMod.dirtPath) {
            return true;
        }
        if(block == LOTRMod.mudGrass || block == LOTRMod.mud) {
            return true;
        }
        if(block == Blocks.sand || block == LOTRMod.whiteSand) {
            return true;
        }
        return block == LOTRMod.mordorDirt || block == LOTRMod.mordorGravel;
    }

    private static class BlockAliasPool {
        private List<BlockMetaEntry> entries = new ArrayList<>();
        private int totalWeight;

        private BlockAliasPool() {
        }

        public void addEntry(int w, Block b, int m) {
            this.entries.add(new BlockMetaEntry(w, b, m));
            this.totalWeight = WeightedRandom.getTotalWeight(this.entries);
        }

        public BlockMetaEntry getEntry(Random random) {
            return (BlockMetaEntry) WeightedRandom.getRandomItem(random, this.entries, this.totalWeight);
        }

        private static class BlockMetaEntry extends WeightedRandom.Item {
            public final Block block;
            public final int meta;

            public BlockMetaEntry(int w, Block b, int m) {
                super(w);
                this.block = b;
                this.meta = m;
            }
        }

    }

}
