package lotr.common;

import cpw.mods.fml.relauncher.*;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.*;
import net.minecraft.util.StatCollector;

public class LOTRCreativeTabs extends CreativeTabs {
    public static LOTRCreativeTabs tabBlock = new LOTRCreativeTabs("blocks");
    public static LOTRCreativeTabs tabUtil = new LOTRCreativeTabs("util");
    public static LOTRCreativeTabs tabDeco = new LOTRCreativeTabs("decorations");
    public static LOTRCreativeTabs tabFood = new LOTRCreativeTabs("food");
    public static LOTRCreativeTabs tabMaterials = new LOTRCreativeTabs("materials");
    public static LOTRCreativeTabs tabMisc = new LOTRCreativeTabs("misc");
    public static LOTRCreativeTabs tabTools = new LOTRCreativeTabs("tools");
    public static LOTRCreativeTabs tabCombat = new LOTRCreativeTabs("combat");
    public static LOTRCreativeTabs tabStory = new LOTRCreativeTabs("story");
    public static LOTRCreativeTabs tabSpawn = new LOTRCreativeTabs("spawning");
    public ItemStack theIcon;

    public LOTRCreativeTabs(String label) {
        super(label);
    }

    public static void setupIcons() {
        LOTRCreativeTabs.tabBlock.theIcon = new ItemStack(LOTRMod.brick, 1, 11);
        LOTRCreativeTabs.tabUtil.theIcon = new ItemStack(LOTRMod.dwarvenForge);
        LOTRCreativeTabs.tabDeco.theIcon = new ItemStack(LOTRMod.simbelmyne);
        LOTRCreativeTabs.tabFood.theIcon = new ItemStack(LOTRMod.lembas);
        LOTRCreativeTabs.tabMaterials.theIcon = new ItemStack(LOTRMod.mithril);
        LOTRCreativeTabs.tabMisc.theIcon = new ItemStack(LOTRMod.hobbitPipe);
        LOTRCreativeTabs.tabTools.theIcon = new ItemStack(LOTRMod.pickaxeOrc);
        LOTRCreativeTabs.tabCombat.theIcon = new ItemStack(LOTRMod.helmetGondor);
        LOTRCreativeTabs.tabStory.theIcon = new ItemStack(LOTRMod.anduril);
        LOTRCreativeTabs.tabSpawn.theIcon = new ItemStack(LOTRMod.spawnEgg, 1, 55);
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public String getTranslatedTabLabel() {
        return StatCollector.translateToLocal("lotr.creativetab." + this.getTabLabel());
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public Item getTabIconItem() {
        return this.theIcon.getItem();
    }

    @Override
    public ItemStack getIconItemStack() {
        return this.theIcon;
    }
}
