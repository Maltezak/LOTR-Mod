package lotr.common.recipe;

import lotr.common.LOTRMod;
import lotr.common.item.LOTRItemHaradTurban;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class LOTRRecipeHaradTurbanOrnament implements IRecipe {
    @Override
    public boolean matches(InventoryCrafting inv, World world) {
        ItemStack turban = null;
        ItemStack gold = null;
        for(int i = 0; i < inv.getSizeInventory(); ++i) {
            ItemStack itemstack = inv.getStackInSlot(i);
            if(itemstack == null) continue;
            if(itemstack.getItem() == LOTRMod.helmetHaradRobes && !LOTRItemHaradTurban.hasOrnament(itemstack)) {
                if(turban != null) {
                    return false;
                }
                turban = itemstack;
                continue;
            }
            if(LOTRMod.isOreNameEqual(itemstack, "nuggetGold")) {
                if(gold != null) {
                    return false;
                }
                gold = itemstack;
                continue;
            }
            return false;
        }
        return turban != null && gold != null;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        ItemStack turban = null;
        ItemStack gold = null;
        for(int i = 0; i < inv.getSizeInventory(); ++i) {
            ItemStack itemstack = inv.getStackInSlot(i);
            if(itemstack == null) continue;
            if(itemstack.getItem() == LOTRMod.helmetHaradRobes && !LOTRItemHaradTurban.hasOrnament(itemstack)) {
                if(turban != null) {
                    return null;
                }
                turban = itemstack.copy();
                continue;
            }
            if(LOTRMod.isOreNameEqual(itemstack, "nuggetGold")) {
                if(gold != null) {
                    return null;
                }
                gold = itemstack.copy();
                continue;
            }
            return null;
        }
        if(turban == null || gold == null) {
            return null;
        }
        LOTRItemHaradTurban.setHasOrnament(turban, true);
        return turban;
    }

    @Override
    public int getRecipeSize() {
        return 2;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return null;
    }
}
