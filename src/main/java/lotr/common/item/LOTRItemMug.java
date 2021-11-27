package lotr.common.item;

import java.util.*;

import cpw.mods.fml.relauncher.*;
import lotr.client.render.LOTRDrinkIcons;
import lotr.common.*;
import lotr.common.block.LOTRBlockMug;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.potion.*;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class LOTRItemMug extends Item {
    private static String[] strengthNames = new String[] {"weak", "light", "moderate", "strong", "potent"};
    private static float[] strengths = new float[] {0.25f, 0.5f, 1.0f, 2.0f, 3.0f};
    private static float[] foodStrengths = new float[] {0.5f, 0.75f, 1.0f, 1.25f, 1.5f};
    public static int vesselMeta = 100;
    @SideOnly(value = Side.CLIENT)
    private IIcon[] drinkIcons;
    @SideOnly(value = Side.CLIENT)
    private IIcon liquidIcon;
    @SideOnly(value = Side.CLIENT)
    public static IIcon barrelGui_emptyBucketSlotIcon;
    @SideOnly(value = Side.CLIENT)
    public static IIcon barrelGui_emptyMugSlotIcon;
    public final boolean isFullMug;
    public final boolean isFoodDrink;
    public final boolean isBrewable;
    public final float alcoholicity;
    protected int foodHealAmount;
    protected float foodSaturationAmount;
    protected List<PotionEffect> potionEffects = new ArrayList<>();
    protected int damageAmount;
    protected boolean curesEffects;

    public LOTRItemMug(boolean full, boolean food, boolean brew, float alc) {
        if(full) {
            this.setMaxStackSize(1);
            this.setHasSubtypes(true);
            this.setMaxDamage(0);
        }
        else {
            this.setMaxStackSize(64);
        }
        this.setCreativeTab(LOTRCreativeTabs.tabFood);
        this.isFullMug = full;
        this.isFoodDrink = food;
        this.isBrewable = brew;
        this.alcoholicity = alc;
    }

    public LOTRItemMug(boolean full, boolean food) {
        this(full, food, false, 0.0f);
    }

    public LOTRItemMug(float alc) {
        this(true, false, true, alc);
    }

    public LOTRItemMug setDrinkStats(int i, float f) {
        this.foodHealAmount = i;
        this.foodSaturationAmount = f;
        return this;
    }

    public LOTRItemMug addPotionEffect(int i, int j) {
        this.potionEffects.add(new PotionEffect(i, j * 20));
        return this;
    }

    public LOTRItemMug setDamageAmount(int i) {
        this.damageAmount = i;
        return this;
    }

    public LOTRItemMug setCuresEffects() {
        this.curesEffects = true;
        return this;
    }

    public static boolean isItemFullDrink(ItemStack itemstack) {
        if(itemstack != null) {
            Item item = itemstack.getItem();
            if(item instanceof LOTRItemMug) {
                return ((LOTRItemMug) item).isFullMug;
            }
            if(item == Items.potionitem && itemstack.getItemDamage() == 0) {
                return true;
            }
        }
        return false;
    }

    public static boolean isItemEmptyDrink(ItemStack itemstack) {
        if(itemstack != null) {
            Item item = itemstack.getItem();
            if(item instanceof LOTRItemMug) {
                return !((LOTRItemMug) item).isFullMug;
            }
            if(item == Items.glass_bottle) {
                return true;
            }
        }
        return false;
    }

    public static ItemStack getEquivalentDrink(ItemStack itemstack) {
        if(itemstack != null) {
            Item item = itemstack.getItem();
            if(item instanceof LOTRItemMug) {
                return itemstack;
            }
            if(item == Items.potionitem && itemstack.getItemDamage() == 0) {
                ItemStack water = itemstack.copy();
                water.func_150996_a(LOTRMod.mugWater);
                LOTRItemMug.setVessel(water, Vessel.BOTTLE, false);
                return water;
            }
        }
        return itemstack;
    }

    public static ItemStack getRealDrink(ItemStack itemstack) {
        if(itemstack != null && (itemstack.getItem()) == LOTRMod.mugWater && LOTRItemMug.getVessel(itemstack) == Vessel.BOTTLE) {
            ItemStack water = itemstack.copy();
            water.func_150996_a(Items.potionitem);
            water.setItemDamage(0);
            return water;
        }
        return itemstack;
    }

    private static int getStrengthMeta(int damage) {
        int i = damage % vesselMeta;
        if(i < 0 || i >= strengths.length) {
            i = 0;
        }
        return i;
    }

    public static int getStrengthMeta(ItemStack itemstack) {
        return LOTRItemMug.getStrengthMeta(itemstack.getItemDamage());
    }

    public static void setStrengthMeta(ItemStack itemstack, int i) {
        Vessel v = LOTRItemMug.getVessel(itemstack);
        itemstack.setItemDamage(i);
        LOTRItemMug.setVessel(itemstack, v, true);
    }

    public static float getStrength(ItemStack itemstack) {
        Item item = itemstack.getItem();
        if(item instanceof LOTRItemMug && ((LOTRItemMug) item).isBrewable) {
            int i = LOTRItemMug.getStrengthMeta(itemstack);
            return strengths[i];
        }
        return 1.0f;
    }

    public static float getFoodStrength(ItemStack itemstack) {
        Item item = itemstack.getItem();
        if(item instanceof LOTRItemMug && ((LOTRItemMug) item).isBrewable) {
            int i = LOTRItemMug.getStrengthMeta(itemstack);
            return foodStrengths[i];
        }
        return 1.0f;
    }

    public static Vessel getVessel(ItemStack itemstack) {
        Item item = itemstack.getItem();
        if(item instanceof LOTRItemMug) {
            LOTRItemMug itemMug = (LOTRItemMug) item;
            if(itemMug.isFullMug) {
                return LOTRItemMug.getVessel(itemstack.getItemDamage());
            }
            return itemMug.getEmptyVesselType();
        }
        if(item == Items.glass_bottle) {
            return Vessel.BOTTLE;
        }
        if(item == Items.potionitem && itemstack.getItemDamage() == 0) {
            return Vessel.BOTTLE;
        }
        return null;
    }

    private static Vessel getVessel(int damage) {
        int i = damage / vesselMeta;
        return Vessel.forMeta(i);
    }

    public static void setVessel(ItemStack itemstack, Vessel v, boolean correctItem) {
        if(correctItem && itemstack.getItem() == Items.potionitem && itemstack.getItemDamage() == 0) {
            itemstack.func_150996_a(LOTRMod.mugWater);
            itemstack.setItemDamage(0);
        }
        int i = itemstack.getItemDamage();
        itemstack.setItemDamage(v.id * vesselMeta + (i %= vesselMeta));
        if(correctItem && itemstack.getItem() == LOTRMod.mugWater && v == Vessel.BOTTLE) {
            itemstack.func_150996_a(Items.potionitem);
            itemstack.setItemDamage(0);
        }
    }

    @Override
    public IIcon getIconFromDamage(int i) {
        if(this.isFullMug) {
            if(i == -1) {
                return this.liquidIcon;
            }
            int vessel = LOTRItemMug.getVessel(i).id;
            return this.drinkIcons[vessel];
        }
        return super.getIconFromDamage(i);
    }

    @Override
    public void registerIcons(IIconRegister iconregister) {
        if(this.isFullMug) {
            this.drinkIcons = new IIcon[Vessel.values().length];
            for(int i = 0; i < Vessel.values().length; ++i) {
                this.drinkIcons[i] = LOTRDrinkIcons.registerDrinkIcon(iconregister, this, this.getIconString(), Vessel.values()[i].name);
            }
            this.liquidIcon = LOTRDrinkIcons.registerLiquidIcon(iconregister, this, this.getIconString());
            barrelGui_emptyBucketSlotIcon = iconregister.registerIcon("lotr:barrel_emptyBucketSlot");
            barrelGui_emptyMugSlotIcon = iconregister.registerIcon("lotr:barrel_emptyMugSlot");
        }
        else {
            super.registerIcons(iconregister);
        }
    }

    private List<PotionEffect> convertPotionEffectsForStrength(float strength) {
        ArrayList<PotionEffect> list = new ArrayList<>();
        for(PotionEffect base : this.potionEffects) {
            PotionEffect modified = new PotionEffect(base.getPotionID(), (int) (base.getDuration() * strength));
            list.add(modified);
        }
        return list;
    }

    public static String getStrengthSubtitle(ItemStack itemstack) {
        Item item;
        if(itemstack != null && (item = itemstack.getItem()) instanceof LOTRItemMug && ((LOTRItemMug) item).isBrewable) {
            int i = LOTRItemMug.getStrengthMeta(itemstack);
            return StatCollector.translateToLocal("item.lotr.drink." + strengthNames[i]);
        }
        return null;
    }

    @Override
    public String getItemStackDisplayName(ItemStack itemstack) {
        if(LOTRMod.isAprilFools() && this.isFullMug) {
            return "Hooch";
        }
        if(itemstack.getItem() == LOTRMod.mugWater && LOTRItemMug.getVessel(itemstack) == Vessel.BOTTLE) {
            return "\u00c2\u00a7cMY DUDE YOU DONE MESSED UP";
        }
        return super.getItemStackDisplayName(itemstack);
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void addInformation(ItemStack itemstack, EntityPlayer entityplayer, List list, boolean flag) {
        if(this.isBrewable) {
            float strength = LOTRItemMug.getStrength(itemstack);
            list.add(LOTRItemMug.getStrengthSubtitle(itemstack));
            if(this.alcoholicity > 0.0f) {
                EnumChatFormatting c;
                float f = this.alcoholicity * strength * 10.0f;
                c = f < 2.0f ? EnumChatFormatting.GREEN : (f < 5.0f ? EnumChatFormatting.YELLOW : (f < 10.0f ? EnumChatFormatting.GOLD : (f < 20.0f ? EnumChatFormatting.RED : EnumChatFormatting.DARK_RED)));
                list.add((c) + StatCollector.translateToLocal("item.lotr.drink.alcoholicity") + ": " + String.format("%.2f", Float.valueOf(f)) + "%");
            }
            LOTRItemMug.addPotionEffectsToTooltip(itemstack, entityplayer, list, flag, this.convertPotionEffectsForStrength(strength));
        }
    }

    public static void addPotionEffectsToTooltip(ItemStack itemstack, EntityPlayer entityplayer, List list, boolean flag, List itemEffects) {
        if(!itemEffects.isEmpty()) {
            ItemStack potionEquivalent = new ItemStack(Items.potionitem);
            potionEquivalent.setItemDamage(69);
            NBTTagList effectsData = new NBTTagList();
            for(Object itemEffect : itemEffects) {
                PotionEffect effect = (PotionEffect) itemEffect;
                NBTTagCompound nbt = new NBTTagCompound();
                effect.writeCustomPotionEffectToNBT(nbt);
                effectsData.appendTag(nbt);
            }
            potionEquivalent.setTagCompound(new NBTTagCompound());
            potionEquivalent.getTagCompound().setTag("CustomPotionEffects", effectsData);
            ArrayList effectTooltips = new ArrayList();
            potionEquivalent.getItem().addInformation(potionEquivalent, entityplayer, effectTooltips, flag);
            list.addAll(effectTooltips);
        }
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        if(this.isFullMug) {
            Vessel[] vesselTypes = new Vessel[] {Vessel.MUG};
            if(tab == null || tab.hasSearchBar()) {
                vesselTypes = Vessel.values();
            }
            for(Vessel v : vesselTypes) {
                if(this.isBrewable) {
                    for(int str = 0; str < strengths.length; ++str) {
                        ItemStack drink = new ItemStack(item);
                        LOTRItemMug.setStrengthMeta(drink, str);
                        LOTRItemMug.setVessel(drink, v, true);
                        if(drink.getItem() != item) continue;
                        list.add(drink);
                    }
                    continue;
                }
                ItemStack drink = new ItemStack(item);
                LOTRItemMug.setVessel(drink, v, true);
                if(drink.getItem() != item) continue;
                list.add(drink);
            }
        }
        else {
            super.getSubItems(item, tab, list);
        }
    }

    protected final Vessel getEmptyVesselType() {
        for(Vessel v : Vessel.values()) {
            if(v.getEmptyVesselItem() != this) continue;
            return v;
        }
        return Vessel.MUG;
    }

    @Override
    public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int side, float f, float f1, float f2) {
        return LOTRItemMug.tryPlaceMug(itemstack, entityplayer, world, i, j, k, side);
    }

    public static boolean tryPlaceMug(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int side) {
        Vessel vessel = LOTRItemMug.getVessel(itemstack);
        if(vessel == null || !vessel.canPlace) {
            return false;
        }
        Block mugBlock = vessel.getBlock();
        Block block = world.getBlock(i += Facing.offsetsXForSide[side], j += Facing.offsetsYForSide[side], k += Facing.offsetsZForSide[side]);
        if(block != null && !block.isReplaceable(world, i, j, k)) {
            return false;
        }
        if(block.getMaterial() == Material.water) {
            return false;
        }
        if(entityplayer.canPlayerEdit(i, j, k, side, itemstack)) {
            if(!mugBlock.canPlaceBlockAt(world, i, j, k)) {
                return false;
            }
            int l = MathHelper.floor_double(entityplayer.rotationYaw * 4.0f / 360.0f + 0.5) & 3;
            world.setBlock(i, j, k, mugBlock, l, 3);
            ItemStack mugFill = itemstack.copy();
            mugFill.stackSize = 1;
            LOTRBlockMug.setMugItem(world, i, j, k, mugFill, vessel);
            world.playSoundEffect(i + 0.5, j + 0.5, k + 0.5, mugBlock.stepSound.func_150496_b(), (mugBlock.stepSound.getVolume() + 1.0f) / 2.0f, mugBlock.stepSound.getPitch() * 0.8f);
            --itemstack.stackSize;
            return true;
        }
        return false;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack itemstack) {
        return 32;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack itemstack) {
        return EnumAction.drink;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        if(!this.isFullMug) {
            ItemStack filled = new ItemStack(LOTRMod.mugWater);
            LOTRItemMug.setVessel(filled, this.getEmptyVesselType(), true);
            MovingObjectPosition m = this.getMovingObjectPositionFromPlayer(world, entityplayer, true);
            if(m == null) {
                return itemstack;
            }
            if(m.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                int i = m.blockX;
                int j = m.blockY;
                int k = m.blockZ;
                if(!world.canMineBlock(entityplayer, i, j, k)) {
                    return itemstack;
                }
                if(!entityplayer.canPlayerEdit(i, j, k, m.sideHit, itemstack)) {
                    return itemstack;
                }
                if(world.getBlock(i, j, k).getMaterial() == Material.water && world.getBlockMetadata(i, j, k) == 0) {
                    --itemstack.stackSize;
                    if(itemstack.stackSize <= 0) {
                        world.playSoundAtEntity(entityplayer, "lotr:item.mug_fill", 0.5f, 0.8f + world.rand.nextFloat() * 0.4f);
                        return filled.copy();
                    }
                    if(!entityplayer.inventory.addItemStackToInventory(filled.copy())) {
                        entityplayer.dropPlayerItemWithRandomChoice(filled.copy(), false);
                    }
                    world.playSoundAtEntity(entityplayer, "lotr:item.mug_fill", 0.5f, 0.8f + world.rand.nextFloat() * 0.4f);
                    return itemstack;
                }
            }
            return itemstack;
        }
        if(this.canPlayerDrink(entityplayer)) {
            entityplayer.setItemInUse(itemstack, this.getMaxItemUseDuration(itemstack));
        }
        return itemstack;
    }

    @Override
    public ItemStack onEaten(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        Vessel vessel = LOTRItemMug.getVessel(itemstack);
        float strength = LOTRItemMug.getStrength(itemstack);
        float foodStrength = LOTRItemMug.getFoodStrength(itemstack);
        if(entityplayer.canEat(false)) {
            entityplayer.getFoodStats().addStats(Math.round(this.foodHealAmount * foodStrength), this.foodSaturationAmount * foodStrength);
        }
        if(this.alcoholicity > 0.0f) {
            int duration;
            float alcoholPower = this.alcoholicity * strength;
            int tolerance = LOTRLevelData.getData(entityplayer).getAlcoholTolerance();
            if(tolerance > 0) {
                float f = (float) Math.pow(0.99, tolerance);
                alcoholPower *= f;
            }
            if(!world.isRemote && itemRand.nextFloat() < alcoholPower && (duration = (int) (60.0f * (1.0f + itemRand.nextFloat() * 0.5f) * alcoholPower)) >= 1) {
                int durationTicks = duration * 20;
                entityplayer.addPotionEffect(new PotionEffect(Potion.confusion.id, durationTicks));
                LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.getDrunk);
                int toleranceAdd = Math.round(duration / 20.0f);
                LOTRLevelData.getData(entityplayer).setAlcoholTolerance(tolerance += toleranceAdd);
            }
        }
        if(!world.isRemote && this.shouldApplyPotionEffects(itemstack, entityplayer)) {
            List<PotionEffect> effects = this.convertPotionEffectsForStrength(strength);
            for(PotionEffect effect : effects) {
                entityplayer.addPotionEffect(effect);
            }
        }
        if(this.damageAmount > 0) {
            entityplayer.attackEntityFrom(DamageSource.magic, this.damageAmount * strength);
        }
        if(!world.isRemote && this.curesEffects) {
            entityplayer.curePotionEffects(new ItemStack(Items.milk_bucket));
        }
        if(!world.isRemote) {
            if(vessel == Vessel.SKULL) {
                LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.drinkSkull);
            }
            if(this == LOTRMod.mugMangoJuice) {
                LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.drinkMangoJuice);
            }
            if(this == LOTRMod.mugOrcDraught) {
                LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.drinkOrcDraught);
            }
            if(this == LOTRMod.mugAthelasBrew) {
                LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.drinkAthelasBrew);
                for(Potion potion : Potion.potionTypes) {
                    if(potion == null || !LOTRReflection.isBadEffect(potion)) continue;
                    entityplayer.removePotionEffect(potion.id);
                }
            }
            if(this == LOTRMod.mugRedWine || this == LOTRMod.mugWhiteWine) {
                LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.drinkWine);
            }
            if(this == LOTRMod.mugDwarvenTonic) {
                LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.drinkDwarvenTonic);
            }
        }
        return !entityplayer.capabilities.isCreativeMode ? new ItemStack(vessel.getEmptyVesselItem()) : itemstack;
    }

    protected boolean shouldApplyPotionEffects(ItemStack itemstack, EntityPlayer entityplayer) {
        return true;
    }

    public void applyToNPC(LOTREntityNPC npc, ItemStack itemstack) {
        float strength = LOTRItemMug.getStrength(itemstack);
        npc.heal(this.foodHealAmount * strength);
        List<PotionEffect> effects = this.convertPotionEffectsForStrength(strength);
        for(PotionEffect effect : effects) {
            npc.addPotionEffect(effect);
        }
        if(this.damageAmount > 0) {
            npc.attackEntityFrom(DamageSource.magic, this.damageAmount * strength);
        }
        if(this.curesEffects) {
            npc.curePotionEffects(new ItemStack(Items.milk_bucket));
        }
    }

    public boolean canPlayerDrink(EntityPlayer entityplayer) {
        if(!this.isFullMug) {
            return false;
        }
        return !this.isFoodDrink || entityplayer.canEat(false);
    }

    public enum Vessel {
        MUG(0, "mug", true, 0),
        MUG_CLAY(1, "clay", true, 1),
        GOBLET_GOLD(2, "gobletGold", true, 10),
        GOBLET_SILVER(3, "gobletSilver", true, 8),
        GOBLET_COPPER(4, "gobletCopper", true, 5),
        GOBLET_WOOD(5, "gobletWood", true, 0),
        SKULL(6, "skull", true, 3),
        GLASS(7, "glass", true, 3),
        BOTTLE(8, "bottle", true, 2),
        SKIN(9, "skin", false, 0),
        HORN(10, "horn", true, 5),
        HORN_GOLD(11, "hornGold", true, 8);

        public final String name;
        public final int id;
        public final boolean canPlace;
        public final int extraPrice;

        Vessel(int i, String s, boolean flag, int p) {
            this.id = i;
            this.name = s;
            this.canPlace = flag;
            this.extraPrice = p;
        }

        public static Vessel forMeta(int i) {
            for(Vessel v : Vessel.values()) {
                if(v.id != i) continue;
                return v;
            }
            return MUG;
        }

        public Item getEmptyVesselItem() {
            if(this == MUG) {
                return LOTRMod.mug;
            }
            if(this == MUG_CLAY) {
                return LOTRMod.ceramicMug;
            }
            if(this == GOBLET_GOLD) {
                return LOTRMod.gobletGold;
            }
            if(this == GOBLET_SILVER) {
                return LOTRMod.gobletSilver;
            }
            if(this == GOBLET_COPPER) {
                return LOTRMod.gobletCopper;
            }
            if(this == GOBLET_WOOD) {
                return LOTRMod.gobletWood;
            }
            if(this == SKULL) {
                return LOTRMod.skullCup;
            }
            if(this == GLASS) {
                return LOTRMod.wineGlass;
            }
            if(this == BOTTLE) {
                return Items.glass_bottle;
            }
            if(this == SKIN) {
                return LOTRMod.waterskin;
            }
            if(this == HORN) {
                return LOTRMod.aleHorn;
            }
            if(this == HORN_GOLD) {
                return LOTRMod.aleHornGold;
            }
            return LOTRMod.mug;
        }

        public ItemStack getEmptyVessel() {
            return new ItemStack(this.getEmptyVesselItem());
        }

        public Block getBlock() {
            if(this == MUG) {
                return LOTRMod.mugBlock;
            }
            if(this == MUG_CLAY) {
                return LOTRMod.ceramicMugBlock;
            }
            if(this == GOBLET_GOLD) {
                return LOTRMod.gobletGoldBlock;
            }
            if(this == GOBLET_SILVER) {
                return LOTRMod.gobletSilverBlock;
            }
            if(this == GOBLET_COPPER) {
                return LOTRMod.gobletCopperBlock;
            }
            if(this == GOBLET_WOOD) {
                return LOTRMod.gobletWoodBlock;
            }
            if(this == SKULL) {
                return LOTRMod.skullCupBlock;
            }
            if(this == GLASS) {
                return LOTRMod.wineGlassBlock;
            }
            if(this == BOTTLE) {
                return LOTRMod.glassBottleBlock;
            }
            if(this == SKIN) {
                return null;
            }
            if(this == HORN) {
                return LOTRMod.aleHornBlock;
            }
            if(this == HORN_GOLD) {
                return LOTRMod.aleHornGoldBlock;
            }
            return LOTRMod.mugBlock;
        }
    }

}
