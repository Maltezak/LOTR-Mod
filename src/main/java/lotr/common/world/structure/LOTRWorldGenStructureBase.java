package lotr.common.world.structure;

import java.util.Random;

import lotr.common.*;
import lotr.common.block.*;
import lotr.common.entity.*;
import lotr.common.entity.item.*;
import lotr.common.item.*;
import lotr.common.recipe.LOTRBrewingRecipes;
import lotr.common.tileentity.*;
import lotr.common.world.structure2.LOTRStructureTimelapse;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.*;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public abstract class LOTRWorldGenStructureBase extends WorldGenerator {
    public boolean restrictions = true;
    public EntityPlayer usingPlayer = null;
    protected boolean notifyChanges;
    public LOTRStructureTimelapse.ThreadTimelapse threadTimelapse;

    public LOTRWorldGenStructureBase(boolean flag) {
        super(flag);
        this.notifyChanges = flag;
    }

    protected int usingPlayerRotation() {
        return MathHelper.floor_double(this.usingPlayer.rotationYaw * 4.0f / 360.0f + 0.5) & 3;
    }

    @Override
    protected void setBlockAndNotifyAdequately(World world, int i, int j, int k, Block block, int meta) {
        super.setBlockAndNotifyAdequately(world, i, j, k, block, meta);
        if(block != Blocks.air && this.threadTimelapse != null) {
            this.threadTimelapse.onBlockSet();
        }
    }

    protected void setBlockMetadata(World world, int i, int j, int k, int meta) {
        world.setBlockMetadataWithNotify(i, j, k, meta, this.notifyChanges ? 3 : 2);
    }

    protected void placeOrcTorch(World world, int i, int j, int k) {
        this.setBlockAndNotifyAdequately(world, i, j, k, LOTRMod.orcTorch, 0);
        this.setBlockAndNotifyAdequately(world, i, j + 1, k, LOTRMod.orcTorch, 1);
    }

    protected void placeMobSpawner(World world, int i, int j, int k, Class entityClass) {
        this.setBlockAndNotifyAdequately(world, i, j, k, LOTRMod.mobSpawner, 0);
        TileEntity tileentity = world.getTileEntity(i, j, k);
        if(tileentity instanceof LOTRTileEntityMobSpawner) {
            ((LOTRTileEntityMobSpawner) tileentity).setEntityClassID(LOTREntities.getEntityIDFromClass(entityClass));
        }
    }

    protected void placeSpawnerChest(World world, int i, int j, int k, Block block, int meta, Class entityClass) {
        this.setBlockAndNotifyAdequately(world, i, j, k, block, 0);
        this.setBlockMetadata(world, i, j, k, meta);
        TileEntity tileentity = world.getTileEntity(i, j, k);
        if(tileentity instanceof LOTRTileEntitySpawnerChest) {
            ((LOTRTileEntitySpawnerChest) tileentity).setMobID(entityClass);
        }
    }

    protected void placePlate(World world, Random random, int i, int j, int k, Block plateBlock, LOTRFoods foodList) {
        this.placePlate_do(world, random, i, j, k, plateBlock, foodList, false);
    }

    protected void placePlateWithCertainty(World world, Random random, int i, int j, int k, Block plateBlock, LOTRFoods foodList) {
        this.placePlate_do(world, random, i, j, k, plateBlock, foodList, true);
    }

    private void placePlate_do(World world, Random random, int i, int j, int k, Block plateBlock, LOTRFoods foodList, boolean certain) {
        TileEntity tileentity;
        if(!certain && random.nextBoolean()) {
            return;
        }
        this.setBlockAndNotifyAdequately(world, i, j, k, plateBlock, 0);
        if((certain || random.nextBoolean()) && (tileentity = world.getTileEntity(i, j, k)) != null && tileentity instanceof LOTRTileEntityPlate) {
            LOTRTileEntityPlate plate = (LOTRTileEntityPlate) tileentity;
            ItemStack food = foodList.getRandomFoodForPlate(random);
            if(random.nextInt(4) == 0) {
                food.stackSize += 1 + random.nextInt(3);
            }
            plate.setFoodItem(food);
        }
    }

    protected void placeBarrel(World world, Random random, int i, int j, int k, int meta, LOTRFoods foodList) {
        this.placeBarrel(world, random, i, j, k, meta, foodList.getRandomBrewableDrink(random));
    }

    protected void placeBarrel(World world, Random random, int i, int j, int k, int meta, ItemStack drink) {
        this.setBlockAndNotifyAdequately(world, i, j, k, LOTRMod.barrel, meta);
        TileEntity tileentity = world.getTileEntity(i, j, k);
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
        this.setBlockAndNotifyAdequately(world, i, j, k, vessel.getBlock(), meta);
        if(random.nextInt(3) != 0) {
            drink = drink.copy();
            drink.stackSize = 1;
            if(drink.getItem() instanceof LOTRItemMug && ((LOTRItemMug) drink.getItem()).isBrewable) {
                LOTRItemMug.setStrengthMeta(drink, MathHelper.getRandomIntegerInRange(random, 1, 3));
            }
            LOTRItemMug.setVessel(drink, vessel, true);
            LOTRBlockMug.setMugItem(world, i, j, k, drink, vessel);
        }
    }

    protected void placeFlowerPot(World world, int i, int j, int k, ItemStack itemstack) {
        this.setBlockAndNotifyAdequately(world, i, j, k, LOTRMod.flowerPot, 0);
        LOTRBlockFlowerPot.setPlant(world, i, j, k, itemstack);
    }

    protected void spawnItemFrame(World world, int i, int j, int k, int direction, ItemStack itemstack) {
        EntityItemFrame frame = new EntityItemFrame(world, i, j, k, direction);
        frame.setDisplayedItem(itemstack);
        world.spawnEntityInWorld(frame);
    }

    protected void placeArmorStand(World world, int i, int j, int k, int direction, ItemStack[] armor) {
        this.setBlockAndNotifyAdequately(world, i, j, k, LOTRMod.armorStand, direction);
        this.setBlockAndNotifyAdequately(world, i, j + 1, k, LOTRMod.armorStand, direction | 4);
        TileEntity tileentity = world.getTileEntity(i, j, k);
        if(tileentity instanceof LOTRTileEntityArmorStand) {
            LOTRTileEntityArmorStand armorStand = (LOTRTileEntityArmorStand) tileentity;
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

    protected void placeBanner(World world, int i, int j, int k, int direction, LOTRItemBanner.BannerType type) {
        LOTREntityBanner banner = new LOTREntityBanner(world);
        banner.setLocationAndAngles(i + 0.5, j, k + 0.5, direction * 90.0f, 0.0f);
        banner.setBannerType(type);
        world.spawnEntityInWorld(banner);
    }

    protected void placeWallBanner(World world, int i, int j, int k, int direction, LOTRItemBanner.BannerType type) {
        LOTREntityBannerWall banner = new LOTREntityBannerWall(world, i, j, k, direction);
        banner.setBannerType(type);
        world.spawnEntityInWorld(banner);
    }

    protected void placeNPCRespawner(LOTREntityNPCRespawner entity, World world, int i, int j, int k) {
        entity.setLocationAndAngles(i + 0.5, j, k + 0.5, 0.0f, 0.0f);
        world.spawnEntityInWorld(entity);
    }

    protected void setGrassToDirt(World world, int i, int j, int k) {
        world.getBlock(i, j, k).onPlantGrow(world, i, j, k, i, j, k);
    }

    protected void setAir(World world, int i, int j, int k) {
        this.setBlockAndNotifyAdequately(world, i, j, k, Blocks.air, 0);
    }

    protected void placeSkull(World world, Random random, int i, int j, int k) {
        this.setBlockAndNotifyAdequately(world, i, j, k, Blocks.skull, 1);
        TileEntity tileentity = world.getTileEntity(i, j, k);
        if(tileentity instanceof TileEntitySkull) {
            TileEntitySkull skull = (TileEntitySkull) tileentity;
            skull.func_145903_a(random.nextInt(16));
        }
    }
}
