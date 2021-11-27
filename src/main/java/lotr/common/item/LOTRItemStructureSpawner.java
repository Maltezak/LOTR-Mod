package lotr.common.item;

import java.util.List;

import cpw.mods.fml.relauncher.*;
import lotr.common.*;
import lotr.common.world.structure.LOTRStructures;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class LOTRItemStructureSpawner extends Item {
    @SideOnly(value = Side.CLIENT)
    private IIcon iconBase;
    @SideOnly(value = Side.CLIENT)
    private IIcon iconOverlay;
    @SideOnly(value = Side.CLIENT)
    private IIcon iconVillageBase;
    @SideOnly(value = Side.CLIENT)
    private IIcon iconVillageOverlay;
    public static int lastStructureSpawnTick = 0;

    public LOTRItemStructureSpawner() {
        this.setHasSubtypes(true);
        this.setCreativeTab(LOTRCreativeTabs.tabSpawn);
    }

    @Override
    public String getItemStackDisplayName(ItemStack itemstack) {
        String s = ("" + StatCollector.translateToLocal(this.getUnlocalizedName() + ".name")).trim();
        String structureName = LOTRStructures.getNameFromID(itemstack.getItemDamage());
        if(structureName != null) {
            s = s + " " + StatCollector.translateToLocal("lotr.structure." + structureName + ".name");
        }
        return s;
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void registerIcons(IIconRegister iconregister) {
        this.iconBase = iconregister.registerIcon(this.getIconString() + "_base");
        this.iconOverlay = iconregister.registerIcon(this.getIconString() + "_overlay");
        this.iconVillageBase = iconregister.registerIcon(this.getIconString() + "_village_base");
        this.iconVillageOverlay = iconregister.registerIcon(this.getIconString() + "_village_overlay");
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public boolean requiresMultipleRenderPasses() {
        return true;
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public IIcon getIconFromDamageForRenderPass(int i, int pass) {
        LOTRStructures.StructureColorInfo info = LOTRStructures.structureItemSpawners.get(i);
        if(info != null) {
            if(info.isVillage) {
                if(pass == 0) {
                    return this.iconVillageBase;
                }
                return this.iconVillageOverlay;
            }
            if(pass == 0) {
                return this.iconBase;
            }
            return this.iconOverlay;
        }
        return this.iconBase;
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public int getColorFromItemStack(ItemStack itemstack, int pass) {
        LOTRStructures.StructureColorInfo info = LOTRStructures.structureItemSpawners.get(itemstack.getItemDamage());
        if(info != null) {
            if(pass == 0) {
                return info.colorBackground;
            }
            return info.colorForeground;
        }
        return 16777215;
    }

    @Override
    public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int side, float f, float f1, float f2) {
        if(world.isRemote) {
            return true;
        }
        if(LOTRLevelData.structuresBanned()) {
            entityplayer.addChatMessage(new ChatComponentTranslation("chat.lotr.spawnStructure.disabled"));
            return false;
        }
        if(LOTRLevelData.isPlayerBannedForStructures(entityplayer)) {
            entityplayer.addChatMessage(new ChatComponentTranslation("chat.lotr.spawnStructure.banned"));
            return false;
        }
        if(lastStructureSpawnTick > 0) {
            entityplayer.addChatMessage(new ChatComponentTranslation("chat.lotr.spawnStructure.wait", lastStructureSpawnTick / 20.0));
            return false;
        }
        if(this.spawnStructure(entityplayer, world, itemstack.getItemDamage(), i += Facing.offsetsXForSide[side], j += Facing.offsetsYForSide[side], k += Facing.offsetsZForSide[side]) && !entityplayer.capabilities.isCreativeMode) {
            --itemstack.stackSize;
        }
        return true;
    }

    private boolean spawnStructure(EntityPlayer entityplayer, World world, int id, int i, int j, int k) {
        if(!LOTRStructures.structureItemSpawners.containsKey(id)) {
            return false;
        }
        LOTRStructures.IStructureProvider strProvider = LOTRStructures.getStructureForID(id);
        if(strProvider != null) {
            boolean generated = strProvider.generateStructure(world, entityplayer, i, j, k);
            if(generated) {
                lastStructureSpawnTick = 20;
                world.playSoundAtEntity(entityplayer, "lotr:item.structureSpawner", 1.0f, 1.0f);
            }
            return generated;
        }
        return false;
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        for(LOTRStructures.StructureColorInfo info : LOTRStructures.structureItemSpawners.values()) {
            if(info.isHidden) continue;
            list.add(new ItemStack(item, 1, info.spawnedID));
        }
    }
}
