package io.gitlab.dwarfyassassin.lotrucp.core.hooks;

import java.util.*;

import cpw.mods.fml.common.registry.*;
import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.block.Block;
import net.minecraft.item.*;
import net.minecraftforge.oredict.OreDictionary;

public class GenericModHooks {
	public static void setItemDelagateName(Item item, String name) {
		RegistryDelegate.Delegate delegate = (RegistryDelegate.Delegate) item.delegate;
		ReflectionHelper.setPrivateValue(RegistryDelegate.Delegate.class, delegate, (Object) name, "name");
	}

	public static void setBlockDelagateName(Block block, String name) {
		RegistryDelegate.Delegate delegate = (RegistryDelegate.Delegate) block.delegate;
		ReflectionHelper.setPrivateValue(RegistryDelegate.Delegate.class, delegate, (Object) name, "name");
	}

	public static void removeBlockFromOreDictionary(Block block) {
		GenericModHooks.removeItemFromOreDictionary(Item.getItemFromBlock(block));
	}

	public static void removeItemFromOreDictionary(Item item) {
		if (item == null) {
			return;
		}
		ItemStack stack = new ItemStack(item, 1, 32767);
		int[] oreIDs = OreDictionary.getOreIDs(stack);
		List oreIdToStacks = (List) ReflectionHelper.getPrivateValue(OreDictionary.class, null, "idToStack");
		for (int oreID : oreIDs) {
			ArrayList<ItemStack> oreStacks = (ArrayList) oreIdToStacks.get(oreID);
			if (oreStacks == null) {
				continue;
			}
			HashSet<ItemStack> toRemove = new HashSet<>();
			for (ItemStack oreStack : oreStacks) {
				if (oreStack.getItem() != stack.getItem()) {
					continue;
				}
				toRemove.add(oreStack);
			}
			oreStacks.removeAll(toRemove);
		}
		String registryName = stack.getItem().delegate.name();
		if (registryName == null) {
			return;
		}
		int stackId = GameData.getItemRegistry().getId(registryName);
		Map stackIdToOreId = (Map) ReflectionHelper.getPrivateValue(OreDictionary.class, null, "stackToId");
		stackIdToOreId.remove(stackId);
	}
}
