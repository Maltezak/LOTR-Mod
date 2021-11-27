package lotr.common.item;

import java.util.*;

import cpw.mods.fml.relauncher.*;
import lotr.common.*;
import lotr.common.entity.item.*;
import lotr.common.fac.*;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTRItemBanner
extends Item {
    @SideOnly(value=Side.CLIENT)
    private IIcon[] bannerIcons;

    public LOTRItemBanner() {
        this.setCreativeTab(LOTRCreativeTabs.tabDeco);
        this.setMaxStackSize(16);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
        this.setFull3D();
    }

    public static BannerType getBannerType(ItemStack itemstack) {
        if (itemstack.getItem() instanceof LOTRItemBanner) {
            return LOTRItemBanner.getBannerType(itemstack.getItemDamage());
        }
        return null;
    }

    public static BannerType getBannerType(int i) {
        return BannerType.forID(i);
    }

    public static NBTTagCompound getProtectionData(ItemStack itemstack) {
        if (itemstack.getTagCompound() != null && itemstack.getTagCompound().hasKey("LOTRBannerData")) {
            NBTTagCompound data = itemstack.getTagCompound().getCompoundTag("LOTRBannerData");
            return data;
        }
        return null;
    }

    public static void setProtectionData(ItemStack itemstack, NBTTagCompound data) {
        if (data == null) {
            if (itemstack.getTagCompound() != null) {
                itemstack.getTagCompound().removeTag("LOTRBannerData");
            }
        } else {
            if (itemstack.getTagCompound() == null) {
                itemstack.setTagCompound(new NBTTagCompound());
            }
            itemstack.getTagCompound().setTag("LOTRBannerData", data);
        }
    }

    @SideOnly(value=Side.CLIENT)
    public IIcon getIconFromDamage(int i) {
        if (i >= this.bannerIcons.length) {
            i = 0;
        }
        return this.bannerIcons[i];
    }

    @SideOnly(value=Side.CLIENT)
    public void registerIcons(IIconRegister iconregister) {
        this.bannerIcons = new IIcon[BannerType.bannerTypes.size()];
        for (int i = 0; i < this.bannerIcons.length; ++i) {
            this.bannerIcons[i] = iconregister.registerIcon(this.getIconString() + "_" + BannerType.bannerTypes.get(i).bannerName);
        }
    }

    public String getUnlocalizedName(ItemStack itemstack) {
        return super.getUnlocalizedName() + "." + LOTRItemBanner.getBannerType(itemstack).bannerName;
    }

    @SideOnly(value=Side.CLIENT)
    public void addInformation(ItemStack itemstack, EntityPlayer entityplayer, List list, boolean flag) {
        NBTTagCompound protectData = LOTRItemBanner.getProtectionData(itemstack);
        if (protectData != null) {
            list.add(StatCollector.translateToLocal("item.lotr.banner.protect"));
        }
    }

    public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int side, float f, float f1, float f2) {
        BannerType bannerType = LOTRItemBanner.getBannerType(itemstack);
        NBTTagCompound protectData = LOTRItemBanner.getProtectionData(itemstack);
        if (world.getBlock(i, j, k).isReplaceable(world, i, j, k)) {
            side = 1;
        } else if (side == 1) {
            ++j;
        }
        if (side == 0) {
            return false;
        }
        if (side == 1) {
            if (!entityplayer.canPlayerEdit(i, j, k, side, itemstack)) {
                return false;
            }
            Block block = world.getBlock(i, j - 1, k);
            int meta = world.getBlockMetadata(i, j - 1, k);
            if (block.isSideSolid(world, i, j - 1, k, ForgeDirection.UP)) {
                int protectRange;
                if (LOTRConfig.allowBannerProtection && !entityplayer.capabilities.isCreativeMode && (protectRange = LOTRBannerProtection.getProtectionRange(block, meta)) > 0) {
                    LOTRFaction faction = bannerType.faction;
                    if (LOTRLevelData.getData(entityplayer).getAlignment(faction) < 1.0f) {
                        if (!world.isRemote) {
                            LOTRAlignmentValues.notifyAlignmentNotHighEnough(entityplayer, 1.0f, faction);
                        }
                        return false;
                    }
                    if (!world.isRemote && LOTRBannerProtection.isProtected(world, i, j, k, LOTRBannerProtection.forPlayer(entityplayer, LOTRBannerProtection.Permission.FULL), false, protectRange)) {
                        entityplayer.addChatMessage(new ChatComponentTranslation("chat.lotr.alreadyProtected"));
                        return false;
                    }
                }
                if (!world.isRemote) {
                    LOTREntityBanner banner = new LOTREntityBanner(world);
                    banner.setLocationAndAngles(i + 0.5, j, k + 0.5, 90.0f * (MathHelper.floor_double(entityplayer.rotationYaw * 4.0f / 360.0f + 0.5) & 3), 0.0f);
                    if (world.checkNoEntityCollision(banner.boundingBox) && world.getCollidingBoundingBoxes(banner, banner.boundingBox).size() == 0 && !world.isAnyLiquid(banner.boundingBox) && world.getEntitiesWithinAABB(LOTREntityBanner.class, banner.boundingBox).isEmpty()) {
                        banner.setBannerType(bannerType);
                        if (protectData != null) {
                            banner.readProtectionFromNBT(protectData);
                        }
                        if (banner.getPlacingPlayer() == null || LOTRItemBanner.shouldRepossessBannerOnPlacement(entityplayer, itemstack)) {
                            banner.setPlacingPlayer(entityplayer);
                        }
                        world.spawnEntityInWorld(banner);
                        if (banner.isProtectingTerritory()) {
                            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.bannerProtect);
                        }
                        world.playSoundAtEntity(banner, Blocks.planks.stepSound.func_150496_b(), (Blocks.planks.stepSound.getVolume() + 1.0f) / 2.0f, Blocks.planks.stepSound.getPitch() * 0.8f);
                        --itemstack.stackSize;
                        return true;
                    }
                    banner.setDead();
                }
            }
        } else {
            if (!entityplayer.canPlayerEdit(i, j, k, side, itemstack)) {
                return false;
            }
            if (!world.isRemote) {
                int l = Direction.facingToDirection[side];
                LOTREntityBannerWall banner = new LOTREntityBannerWall(world, i, j, k, l);
                if (banner.onValidSurface()) {
                    banner.setBannerType(bannerType);
                    if (protectData != null) {
                        banner.setProtectData((NBTTagCompound)protectData.copy());
                    }
                    world.spawnEntityInWorld(banner);
                    world.playSoundAtEntity(banner, Blocks.planks.stepSound.func_150496_b(), (Blocks.planks.stepSound.getVolume() + 1.0f) / 2.0f, Blocks.planks.stepSound.getPitch() * 0.8f);
                    --itemstack.stackSize;
                    return true;
                }
                banner.setDead();
            }
        }
        return false;
    }

    public static boolean shouldRepossessBannerOnPlacement(EntityPlayer entityplayer, ItemStack bannerItem) {
        return !LOTRItemBanner.hasChoiceToRepossessBanner(entityplayer, bannerItem) || !entityplayer.isSneaking();
    }

    public static boolean hasChoiceToRepossessBanner(EntityPlayer entityplayer, ItemStack bannerItem) {
        return entityplayer.capabilities.isCreativeMode;
    }

    public static boolean isHoldingBannerWithExistingProtection(EntityPlayer entityplayer) {
        ItemStack itemstack = entityplayer.getHeldItem();
        if (itemstack != null && itemstack.getItem() instanceof LOTRItemBanner) {
            NBTTagCompound protectData = LOTRItemBanner.getProtectionData(itemstack);
            return protectData != null && !protectData.hasNoTags();
        }
        return false;
    }

    @SideOnly(value=Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        for (BannerType type : BannerType.bannerTypes) {
            list.add(new ItemStack(item, 1, type.bannerID));
        }
    }

    public enum BannerType {
        GONDOR(0, "gondor", LOTRFaction.GONDOR),
        ROHAN(1, "rohan", LOTRFaction.ROHAN),
        MORDOR(2, "mordor", LOTRFaction.MORDOR),
        GALADHRIM(3, "lothlorien", LOTRFaction.LOTHLORIEN),
        WOOD_ELF(4, "mirkwood", LOTRFaction.WOOD_ELF),
        DUNLAND(5, "dunland", LOTRFaction.DUNLAND),
        ISENGARD(6, "isengard", LOTRFaction.ISENGARD),
        DWARF(7, "durin", LOTRFaction.DURINS_FOLK),
        ANGMAR(8, "angmar", LOTRFaction.ANGMAR),
        NEAR_HARAD(9, "nearHarad", LOTRFaction.NEAR_HARAD),
        HIGH_ELF(10, "highElf", LOTRFaction.HIGH_ELF),
        BLUE_MOUNTAINS(11, "blueMountains", LOTRFaction.BLUE_MOUNTAINS),
        RANGER_NORTH(12, "ranger", LOTRFaction.RANGER_NORTH),
        DOL_GULDUR(13, "dolGuldur", LOTRFaction.DOL_GULDUR),
        GUNDABAD(14, "gundabad", LOTRFaction.GUNDABAD),
        HALF_TROLL(15, "halfTroll", LOTRFaction.HALF_TROLL),
        DOL_AMROTH(16, "dolAmroth", LOTRFaction.GONDOR),
        MOREDAIN(17, "moredain", LOTRFaction.MORWAITH),
        TAUREDAIN(18, "tauredain", LOTRFaction.TAURETHRIM),
        DALE(19, "dale", LOTRFaction.DALE),
        DORWINION(20, "dorwinion", LOTRFaction.DORWINION),
        HOBBIT(21, "hobbit", LOTRFaction.HOBBIT),
        ANORIEN(22, "anorien", LOTRFaction.GONDOR),
        ITHILIEN(23, "ithilien", LOTRFaction.GONDOR),
        LOSSARNACH(24, "lossarnach", LOTRFaction.GONDOR),
        LEBENNIN(25, "lebennin", LOTRFaction.GONDOR),
        PELARGIR(26, "pelargir", LOTRFaction.GONDOR),
        BLACKROOT_VALE(27, "blackrootVale", LOTRFaction.GONDOR),
        PINNATH_GELIN(28, "pinnathGelin", LOTRFaction.GONDOR),
        MINAS_MORGUL(29, "minasMorgul", LOTRFaction.MORDOR),
        BLACK_URUK(30, "blackUruk", LOTRFaction.MORDOR),
        GONDOR_STEWARD(31, "gondorSteward", LOTRFaction.GONDOR),
        NAN_UNGOL(32, "nanUngol", LOTRFaction.MORDOR),
        RHUDAUR(33, "rhudaur", LOTRFaction.ANGMAR),
        LAMEDON(34, "lamedon", LOTRFaction.GONDOR),
        RHUN(35, "rhun", LOTRFaction.RHUDEL),
        RIVENDELL(36, "rivendell", LOTRFaction.HIGH_ELF),
        ESGAROTH(37, "esgaroth", LOTRFaction.DALE),
        UMBAR(38, "umbar", LOTRFaction.NEAR_HARAD),
        HARAD_NOMAD(39, "haradNomad", LOTRFaction.NEAR_HARAD),
        HARAD_GULF(40, "haradGulf", LOTRFaction.NEAR_HARAD),
        BREE(41, "bree", LOTRFaction.BREE);

        public static List<BannerType> bannerTypes;
        private static Map<Integer, BannerType> bannerForID;
        public final int bannerID;
        public final String bannerName;
        public final LOTRFaction faction;

        BannerType(int i, String s, LOTRFaction f) {
            this.bannerID = i;
            this.bannerName = s;
            this.faction = f;
            this.faction.factionBanners.add(this);
        }

        public static BannerType forID(int ID) {
            return bannerForID.get(ID);
        }

        static {
            bannerTypes = new ArrayList<>();
            bannerForID = new HashMap<>();
            for (BannerType t : BannerType.values()) {
                bannerTypes.add(t);
                bannerForID.put(t.bannerID, t);
            }
        }
    }

}

