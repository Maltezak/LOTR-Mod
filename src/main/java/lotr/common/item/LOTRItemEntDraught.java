package lotr.common.item;

import java.util.*;

import cpw.mods.fml.relauncher.*;
import lotr.common.*;
import lotr.common.block.LOTRBlockSaplingBase;
import lotr.common.entity.npc.*;
import lotr.common.fac.*;
import net.minecraft.block.*;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.potion.*;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class LOTRItemEntDraught
extends Item {
    @SideOnly(value=Side.CLIENT)
    private IIcon[] draughtIcons;
    public static DraughtInfo[] draughtTypes = new DraughtInfo[]{new DraughtInfo("green", 0, 0.0f).addEffect(Potion.moveSpeed.id, 120).addEffect(Potion.digSpeed.id, 120).addEffect(Potion.damageBoost.id, 120), new DraughtInfo("brown", 20, 3.0f), new DraughtInfo("gold", 0, 0.0f), new DraughtInfo("yellow", 0, 0.0f).addEffect(Potion.regeneration.id, 60), new DraughtInfo("red", 0, 0.0f).addEffect(Potion.fireResistance.id, 180), new DraughtInfo("silver", 0, 0.0f).addEffect(Potion.nightVision.id, 180), new DraughtInfo("blue", 0, 0.0f).addEffect(Potion.waterBreathing.id, 150)};

    public LOTRItemEntDraught() {
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        this.setMaxStackSize(1);
        this.setCreativeTab(LOTRCreativeTabs.tabFood);
    }

    private DraughtInfo getDraughtInfo(ItemStack itemstack) {
        int i = itemstack.getItemDamage();
        if (i >= draughtTypes.length) {
            i = 0;
        }
        return draughtTypes[i];
    }

    @SideOnly(value=Side.CLIENT)
    public IIcon getIconFromDamage(int i) {
        if (i >= this.draughtIcons.length) {
            i = 0;
        }
        return this.draughtIcons[i];
    }

    public String getUnlocalizedName(ItemStack itemstack) {
        return super.getUnlocalizedName() + "." + itemstack.getItemDamage();
    }

    @SideOnly(value=Side.CLIENT)
    public void registerIcons(IIconRegister iconregister) {
        this.draughtIcons = new IIcon[draughtTypes.length];
        for (int i = 0; i < draughtTypes.length; ++i) {
            this.draughtIcons[i] = iconregister.registerIcon(this.getIconString() + "_" + LOTRItemEntDraught.draughtTypes[i].name);
        }
    }

    @SideOnly(value=Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        for (int i = 0; i < draughtTypes.length; ++i) {
            list.add(new ItemStack(item, 1, i));
        }
    }

    @SideOnly(value=Side.CLIENT)
    public void addInformation(ItemStack itemstack, EntityPlayer entityplayer, List list, boolean flag) {
        LOTRItemMug.addPotionEffectsToTooltip(itemstack, entityplayer, list, flag, this.getDraughtInfo(itemstack).effects);
    }

    public int getMaxItemUseDuration(ItemStack itemstack) {
        return 32;
    }

    public EnumAction getItemUseAction(ItemStack itemstack) {
        return EnumAction.drink;
    }

    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        if (this.canPlayerDrink(entityplayer, itemstack)) {
            entityplayer.setItemInUse(itemstack, this.getMaxItemUseDuration(itemstack));
        }
        return itemstack;
    }

    public boolean canPlayerDrink(EntityPlayer entityplayer, ItemStack itemstack) {
        return !this.getDraughtInfo(itemstack).effects.isEmpty() || entityplayer.canEat(true);
    }

    public ItemStack onEaten(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        if (LOTRLevelData.getData(entityplayer).getAlignment(LOTRFaction.FANGORN) < 0.0f) {
            if (!world.isRemote) {
                entityplayer.addPotionEffect(new PotionEffect(Potion.poison.id, 100));
            }
        } else {
            if (entityplayer.canEat(false)) {
                entityplayer.getFoodStats().addStats(this.getDraughtInfo(itemstack).heal, this.getDraughtInfo(itemstack).saturation);
            }
            if (!world.isRemote) {
                List effects = this.getDraughtInfo(itemstack).effects;
                for (int i = 0; i < effects.size(); ++i) {
                    PotionEffect effect = (PotionEffect)effects.get(i);
                    entityplayer.addPotionEffect(new PotionEffect(effect.getPotionID(), effect.getDuration()));
                }
            }
            if (!world.isRemote && entityplayer.getCurrentEquippedItem() == itemstack) {
                LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.drinkEntDraught);
            }
        }
        return !entityplayer.capabilities.isCreativeMode ? new ItemStack(Items.bowl) : itemstack;
    }

    public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int side, float f, float f1, float f2) {
        if (this.getDraughtInfo(itemstack).name.equals("gold")) {
            if (LOTRLevelData.getData(entityplayer).getAlignment(LOTRFaction.FANGORN) < 500.0f) {
                if (!world.isRemote) {
                    LOTRAlignmentValues.notifyAlignmentNotHighEnough(entityplayer, 500.0f, LOTRFaction.FANGORN);
                }
                return false;
            }
            Block block = world.getBlock(i, j, k);
            int meta = world.getBlockMetadata(i, j, k);
            if (block instanceof BlockSapling || block instanceof LOTRBlockSaplingBase) {
                meta &= 7;
                for (int huornType = 0; huornType < LOTREntityTree.TYPES.length; ++huornType) {
                    if (block != LOTREntityTree.SAPLING_BLOCKS[huornType] || meta != LOTREntityTree.SAPLING_META[huornType]) continue;
                    LOTREntityHuorn huorn = new LOTREntityHuorn(world);
                    huorn.setTreeType(huornType);
                    huorn.isNPCPersistent = true;
                    huorn.liftSpawnRestrictions = true;
                    huorn.setLocationAndAngles(i + 0.5, j, k + 0.5, 0.0f, 0.0f);
                    if (!huorn.getCanSpawnHere()) continue;
                    if (!world.isRemote) {
                        world.spawnEntityInWorld(huorn);
                        huorn.initCreatureForHire(null);
                        huorn.hiredNPCInfo.isActive = true;
                        huorn.hiredNPCInfo.alignmentRequiredToCommand = 500.0f;
                        huorn.hiredNPCInfo.setHiringPlayer(entityplayer);
                        huorn.hiredNPCInfo.setTask(LOTRHiredNPCInfo.Task.WARRIOR);
                        LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.summonHuorn);
                    }
                    for (int l = 0; l < 24; ++l) {
                        double d = i + 0.5 - world.rand.nextDouble() * 2.0 + world.rand.nextDouble() * 2.0;
                        double d1 = j + world.rand.nextDouble() * 4.0;
                        double d2 = k + 0.5 - world.rand.nextDouble() * 2.0 + world.rand.nextDouble() * 2.0;
                        world.spawnParticle("happyVillager", d, d1, d2, 0.0, 0.0, 0.0);
                    }
                    if (!entityplayer.capabilities.isCreativeMode) {
                        entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, new ItemStack(Items.bowl));
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public static class DraughtInfo {
        public String name;
        public int heal;
        public float saturation;
        public List effects = new ArrayList();

        public DraughtInfo(String s, int i, float f) {
            this.name = s;
            this.heal = i;
            this.saturation = f;
        }

        public DraughtInfo addEffect(int i, int j) {
            this.effects.add(new PotionEffect(i, j * 20));
            return this;
        }
    }

}

