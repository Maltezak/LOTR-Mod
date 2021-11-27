package lotr.common.item;

import java.util.List;

import cpw.mods.fml.relauncher.*;
import lotr.common.*;
import lotr.common.enchant.*;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.*;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class LOTRItemAncientItem extends Item {
    @SideOnly(value = Side.CLIENT)
    private IIcon[] icons;
    private String[] itemNames = new String[] {"sword", "dagger", "helmet", "body", "legs", "boots"};

    public LOTRItemAncientItem() {
        this.setMaxStackSize(1);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        this.setCreativeTab(LOTRCreativeTabs.tabMisc);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        if(!world.isRemote) {
            ItemStack ancientItem = LOTRItemAncientItem.getRandomItem(itemstack);
            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.craftAncientItem);
            world.playSoundAtEntity(entityplayer, "random.pop", 0.2f, ((world.rand.nextFloat() - world.rand.nextFloat()) * 0.7f + 1.0f) * 2.0f);
            return ancientItem;
        }
        return itemstack;
    }

    public static ItemStack getRandomItem(ItemStack itemstack) {
        ItemStack randomItem;
        InventoryBasic randomItemInv = new InventoryBasic("ancientItem", true, 1);
        LOTRChestContents itemPool = null;
        if(itemstack.getItemDamage() == 0) {
            itemPool = LOTRChestContents.ANCIENT_SWORD;
        }
        else if(itemstack.getItemDamage() == 1) {
            itemPool = LOTRChestContents.ANCIENT_DAGGER;
        }
        else if(itemstack.getItemDamage() == 2) {
            itemPool = LOTRChestContents.ANCIENT_HELMET;
        }
        else if(itemstack.getItemDamage() == 3) {
            itemPool = LOTRChestContents.ANCIENT_BODY;
        }
        else if(itemstack.getItemDamage() == 4) {
            itemPool = LOTRChestContents.ANCIENT_LEGS;
        }
        else if(itemstack.getItemDamage() == 5) {
            itemPool = LOTRChestContents.ANCIENT_BOOTS;
        }
        LOTRChestContents.fillInventory(randomItemInv, itemRand, itemPool, 1);
        randomItem = randomItemInv.getStackInSlot(0);
        if(randomItem != null) {
            LOTREnchantment wraithbane;
            if(LOTRConfig.enchantingLOTR && (wraithbane = LOTREnchantment.baneWraith).canApply(randomItem, false) && itemRand.nextInt(4) == 0) {
                LOTREnchantmentHelper.setHasEnchant(randomItem, wraithbane);
            }
            return randomItem;
        }
        return itemstack;
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public IIcon getIconFromDamage(int i) {
        if(i >= this.icons.length) {
            i = 0;
        }
        return this.icons[i];
    }

    @Override
    public String getUnlocalizedName(ItemStack itemstack) {
        return super.getUnlocalizedName() + "." + itemstack.getItemDamage();
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void registerIcons(IIconRegister iconregister) {
        this.icons = new IIcon[this.itemNames.length];
        for(int i = 0; i < this.itemNames.length; ++i) {
            this.icons[i] = iconregister.registerIcon(this.getIconString() + "_" + this.itemNames[i]);
        }
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        for(int i = 0; i <= 5; ++i) {
            list.add(new ItemStack(item, 1, i));
        }
    }
}
