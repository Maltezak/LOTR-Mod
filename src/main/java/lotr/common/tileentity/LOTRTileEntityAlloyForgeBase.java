package lotr.common.tileentity;

import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.item.crafting.FurnaceRecipes;

public abstract class LOTRTileEntityAlloyForgeBase
extends LOTRTileEntityForgeBase {
    @Override
    public int getForgeInvSize() {
        return 13;
    }

    @Override
    public void setupForgeSlots() {
        this.inputSlots = new int[]{4, 5, 6, 7};
        this.outputSlots = new int[]{8, 9, 10, 11};
        this.fuelSlot = 12;
    }

    @Override
    protected boolean canMachineInsertInput(ItemStack itemstack) {
        return itemstack != null && this.getSmeltingResult(itemstack) != null;
    }

    @Override
    public int getSmeltingDuration() {
        return 200;
    }

    @Override
    protected boolean canDoSmelting() {
        for (int i = 4; i < 8; ++i) {
            if (!this.canSmelt(i)) continue;
            return true;
        }
        return false;
    }

    @Override
    protected void doSmelt() {
        for (int i = 4; i < 8; ++i) {
            this.smeltItemInSlot(i);
        }
    }

    private boolean canSmelt(int i) {
        int resultSize;
        ItemStack alloyResult;
        ItemStack result;
        if (this.inventory[i] == null) {
            return false;
        }
        if (this.inventory[i - 4] != null && (alloyResult = this.getAlloySmeltingResult(this.inventory[i], this.inventory[i - 4])) != null) {
            if (this.inventory[i + 4] == null) {
                return true;
            }
            resultSize = this.inventory[i + 4].stackSize + alloyResult.stackSize;
            if (this.inventory[i + 4].isItemEqual(alloyResult) && resultSize <= this.getInventoryStackLimit() && resultSize <= alloyResult.getMaxStackSize()) {
                return true;
            }
        }
        if ((result = this.getSmeltingResult(this.inventory[i])) == null) {
            return false;
        }
        if (this.inventory[i + 4] == null) {
            return true;
        }
        if (!this.inventory[i + 4].isItemEqual(result)) {
            return false;
        }
        resultSize = this.inventory[i + 4].stackSize + result.stackSize;
        return resultSize <= this.getInventoryStackLimit() && resultSize <= result.getMaxStackSize();
    }

    private void smeltItemInSlot(int i) {
        if (this.canSmelt(i)) {
            ItemStack alloyResult;
            boolean smeltedAlloyItem = false;
            if (this.inventory[i - 4] != null && (alloyResult = this.getAlloySmeltingResult(this.inventory[i], this.inventory[i - 4])) != null && (this.inventory[i + 4] == null || this.inventory[i + 4].isItemEqual(alloyResult))) {
                if (this.inventory[i + 4] == null) {
                    this.inventory[i + 4] = alloyResult.copy();
                } else if (this.inventory[i + 4].isItemEqual(alloyResult)) {
                    this.inventory[i + 4].stackSize += alloyResult.stackSize;
                }
                --this.inventory[i].stackSize;
                if (this.inventory[i].stackSize <= 0) {
                    this.inventory[i] = null;
                }
                --this.inventory[i - 4].stackSize;
                if (this.inventory[i - 4].stackSize <= 0) {
                    this.inventory[i - 4] = null;
                }
                smeltedAlloyItem = true;
            }
            if (!smeltedAlloyItem) {
                ItemStack result = this.getSmeltingResult(this.inventory[i]);
                if (this.inventory[i + 4] == null) {
                    this.inventory[i + 4] = result.copy();
                } else if (this.inventory[i + 4].isItemEqual(result)) {
                    this.inventory[i + 4].stackSize += result.stackSize;
                }
                --this.inventory[i].stackSize;
                if (this.inventory[i].stackSize <= 0) {
                    this.inventory[i] = null;
                }
            }
        }
    }

    public ItemStack getSmeltingResult(ItemStack itemstack) {
        boolean isStoneMaterial = false;
        Item item = itemstack.getItem();
        Block block = Block.getBlockFromItem(item);
        if (block != null && block != Blocks.air) {
            Material material = block.getMaterial();
            if (material == Material.rock || material == Material.sand || material == Material.clay) {
                isStoneMaterial = true;
            }
        } else if (item == Items.clay_ball || item == LOTRMod.redClayBall || item == LOTRMod.clayMug || item == LOTRMod.clayPlate || item == LOTRMod.ceramicPlate) {
            isStoneMaterial = true;
        }
        if (isStoneMaterial || this.isWood(itemstack)) {
            return FurnaceRecipes.smelting().getSmeltingResult(itemstack);
        }
        return null;
    }

    protected ItemStack getAlloySmeltingResult(ItemStack itemstack, ItemStack alloyItem) {
        if (this.isCopper(itemstack) && this.isTin(alloyItem) || this.isTin(itemstack) && this.isCopper(alloyItem)) {
            return new ItemStack(LOTRMod.bronze, 2);
        }
        return null;
    }

    protected boolean isCopper(ItemStack itemstack) {
        return LOTRMod.isOreNameEqual(itemstack, "oreCopper") || LOTRMod.isOreNameEqual(itemstack, "ingotCopper");
    }

    protected boolean isTin(ItemStack itemstack) {
        return LOTRMod.isOreNameEqual(itemstack, "oreTin") || LOTRMod.isOreNameEqual(itemstack, "ingotTin");
    }

    protected boolean isIron(ItemStack itemstack) {
        return LOTRMod.isOreNameEqual(itemstack, "oreIron") || LOTRMod.isOreNameEqual(itemstack, "ingotIron");
    }

    protected boolean isGold(ItemStack itemstack) {
        return LOTRMod.isOreNameEqual(itemstack, "oreGold") || LOTRMod.isOreNameEqual(itemstack, "ingotGold");
    }

    protected boolean isGoldNugget(ItemStack itemstack) {
        return LOTRMod.isOreNameEqual(itemstack, "nuggetGold");
    }

    protected boolean isSilver(ItemStack itemstack) {
        return LOTRMod.isOreNameEqual(itemstack, "oreSilver") || LOTRMod.isOreNameEqual(itemstack, "ingotSilver");
    }

    protected boolean isSilverNugget(ItemStack itemstack) {
        return LOTRMod.isOreNameEqual(itemstack, "nuggetSilver");
    }

    protected boolean isMithril(ItemStack itemstack) {
        return itemstack.getItem() == Item.getItemFromBlock(LOTRMod.oreMithril) || itemstack.getItem() == LOTRMod.mithril;
    }

    protected boolean isMithrilNugget(ItemStack itemstack) {
        return itemstack.getItem() == LOTRMod.mithrilNugget;
    }

    protected boolean isOrcSteel(ItemStack itemstack) {
        return itemstack.getItem() == Item.getItemFromBlock(LOTRMod.oreMorgulIron) || itemstack.getItem() == LOTRMod.orcSteel;
    }

    protected boolean isWood(ItemStack itemstack) {
        return LOTRMod.isOreNameEqual(itemstack, "logWood");
    }

    protected boolean isCoal(ItemStack itemstack) {
        return itemstack.getItem() == Items.coal;
    }
}

