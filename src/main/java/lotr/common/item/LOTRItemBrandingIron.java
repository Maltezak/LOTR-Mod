package lotr.common.item;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import cpw.mods.fml.relauncher.*;
import lotr.common.*;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.tileentity.*;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTRItemBrandingIron extends Item {
    @SideOnly(value = Side.CLIENT)
    private IIcon iconCool;
    @SideOnly(value = Side.CLIENT)
    private IIcon iconHot;
    public LOTRItemBrandingIron() {
        this.setCreativeTab(LOTRCreativeTabs.tabTools);
        this.setMaxStackSize(1);
        this.setMaxDamage(100);
        this.setFull3D();
    }

    private static boolean isHeated(ItemStack itemstack) {
        if(itemstack.hasTagCompound()) {
            return itemstack.getTagCompound().getBoolean("HotIron");
        }
        return false;
    }

    private static void setHeated(ItemStack itemstack, boolean flag) {
        if(!itemstack.hasTagCompound()) {
            itemstack.setTagCompound(new NBTTagCompound());
        }
        itemstack.getTagCompound().setBoolean("HotIron", flag);
    }

    public static String trimAcceptableBrandName(String s) {
        s = StringUtils.trim(s);
        int maxLength = 64;
        if(s.length() > maxLength) {
            s = s.substring(0, maxLength);
        }
        return s;
    }

    public static String getBrandName(ItemStack itemstack) {
        String s;
        if(itemstack.hasTagCompound() && !StringUtils.isBlank(s = itemstack.getTagCompound().getString("BrandName"))) {
            return s;
        }
        return null;
    }

    public static boolean hasBrandName(ItemStack itemstack) {
        return LOTRItemBrandingIron.getBrandName(itemstack) != null;
    }

    public static void setBrandName(ItemStack itemstack, String s) {
        if(!itemstack.hasTagCompound()) {
            itemstack.setTagCompound(new NBTTagCompound());
        }
        itemstack.getTagCompound().setString("BrandName", s);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        if(!LOTRItemBrandingIron.hasBrandName(itemstack)) {
            entityplayer.openGui(LOTRMod.instance, 61, world, 0, 0, 0);
        }
        return itemstack;
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack itemstack, EntityPlayer entityplayer, EntityLivingBase entity) {
        if(LOTRItemBrandingIron.isHeated(itemstack) && LOTRItemBrandingIron.hasBrandName(itemstack)) {
            String brandName = LOTRItemBrandingIron.getBrandName(itemstack);
            if(entity instanceof EntityLiving) {
                EntityLiving entityliving = (EntityLiving) entity;
                boolean acceptableEntity = false;
                if(entityliving instanceof EntityAnimal) {
                    acceptableEntity = true;
                }
                else if(entityliving instanceof LOTREntityNPC && ((LOTREntityNPC) entityliving).canRenameNPC()) {
                    acceptableEntity = true;
                }
                if(acceptableEntity && !entityliving.getCustomNameTag().equals(brandName)) {
                    entityliving.setCustomNameTag(brandName);
                    entityliving.func_110163_bv();
                    entityliving.playLivingSound();
                    entityliving.getJumpHelper().setJumping();
                    World world = entityliving.worldObj;
                    world.playSoundAtEntity(entityliving, "random.fizz", 0.5f, 2.6f + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8f);
                    entityplayer.swingItem();
                    int preDamage = itemstack.getItemDamage();
                    itemstack.damageItem(1, entityplayer);
                    int newDamage = itemstack.getItemDamage();
                    if(preDamage / 5 != newDamage / 5) {
                        LOTRItemBrandingIron.setHeated(itemstack, false);
                    }
                    if(!world.isRemote) {
                        LOTRItemBrandingIron.setBrandingPlayer(entityliving, entityplayer.getUniqueID());
                    }
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int side, float f, float f1, float f2) {
        if(LOTRItemBrandingIron.hasBrandName(itemstack) && !LOTRItemBrandingIron.isHeated(itemstack)) {
            boolean isHotBlock = false;
            TileEntity te = world.getTileEntity(i, j, k);
            if(te instanceof TileEntityFurnace && ((TileEntityFurnace) te).isBurning()) {
                isHotBlock = true;
            }
            else if(te instanceof LOTRTileEntityForgeBase && ((LOTRTileEntityForgeBase) te).isSmelting()) {
                isHotBlock = true;
            }
            else if(te instanceof LOTRTileEntityHobbitOven && ((LOTRTileEntityHobbitOven) te).isCooking()) {
                isHotBlock = true;
            }
            if(!isHotBlock) {
                ForgeDirection dir = ForgeDirection.getOrientation(side);
                Block block = world.getBlock(i + dir.offsetX, j + dir.offsetY, k + dir.offsetZ);
                if(block.getMaterial() == Material.fire) {
                    isHotBlock = true;
                }
            }
            if(isHotBlock) {
                LOTRItemBrandingIron.setHeated(itemstack, true);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean getIsRepairable(ItemStack itemstack, ItemStack repairItem) {
        return repairItem.getItem() == Items.iron_ingot;
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public IIcon getIconIndex(ItemStack itemstack) {
        return this.getIcon(itemstack, 0);
    }

    @Override
    public IIcon getIcon(ItemStack itemstack, int pass) {
        if(LOTRItemBrandingIron.isHeated(itemstack)) {
            return this.iconHot;
        }
        return this.iconCool;
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void registerIcons(IIconRegister iconregister) {
        this.iconCool = iconregister.registerIcon(this.getIconString());
        this.iconHot = iconregister.registerIcon(this.getIconString() + "_hot");
    }

    @Override
    public String getItemStackDisplayName(ItemStack itemstack) {
        String name = super.getItemStackDisplayName(itemstack);
        if(LOTRItemBrandingIron.hasBrandName(itemstack)) {
            String brandName = LOTRItemBrandingIron.getBrandName(itemstack);
            name = StatCollector.translateToLocalFormatted("item.lotr.brandingIron.named", name, brandName);
        }
        else {
            name = StatCollector.translateToLocalFormatted("item.lotr.brandingIron.unnamed", name);
        }
        return name;
    }

    public static UUID getBrandingPlayer(Entity entity) {
        NBTTagCompound nbt = entity.getEntityData();
        if(nbt.hasKey("LOTRBrander")) {
            String s = nbt.getString("LOTRBrander");
            return UUID.fromString(s);
        }
        return null;
    }

    public static void setBrandingPlayer(Entity entity, UUID player) {
        String s = player.toString();
        entity.getEntityData().setString("LOTRBrander", s);
    }
}
