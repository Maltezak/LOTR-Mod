package lotr.common.entity.item;

import java.util.*;

import com.mojang.authlib.GameProfile;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import lotr.common.*;
import lotr.common.fellowship.*;
import lotr.common.item.LOTRItemBanner;
import lotr.common.network.*;
import lotr.common.util.LOTRLog;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.*;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerManager;
import net.minecraft.util.*;
import net.minecraft.world.*;

public class LOTREntityBanner
extends Entity {
    private NBTTagCompound protectData;
    private boolean wasEverProtecting = false;
    private boolean playerSpecificProtection;
    private boolean structureProtection = false;
    private int customRange;
    private boolean selfProtection = true;
    public static float ALIGNMENT_PROTECTION_MIN = 1.0f;
    public static float ALIGNMENT_PROTECTION_MAX = 10000.0f;
    private float alignmentProtection = ALIGNMENT_PROTECTION_MIN;
    public static int WHITELIST_DEFAULT = 16;
    public static int WHITELIST_MIN = 1;
    public static int WHITELIST_MAX = 4000;
    private LOTRBannerWhitelistEntry[] allowedPlayers = new LOTRBannerWhitelistEntry[WHITELIST_DEFAULT];
    private Set<LOTRBannerProtection.Permission> defaultPermissions = new HashSet<>();
    private boolean clientside_playerHasPermission;

    public LOTREntityBanner(World world) {
        super(world);
        this.setSize(1.0f, 3.0f);
    }

    protected void entityInit() {
        this.dataWatcher.addObject(18, (byte)0);
    }

    private int getBannerTypeID() {
        return this.dataWatcher.getWatchableObjectByte(18);
    }

    private void setBannerTypeID(int i) {
        this.dataWatcher.updateObject(18, ((byte)i));
    }

    public void setBannerType(LOTRItemBanner.BannerType type) {
        this.setBannerTypeID(type.bannerID);
    }

    public LOTRItemBanner.BannerType getBannerType() {
        return LOTRItemBanner.BannerType.forID(this.getBannerTypeID());
    }

    public int getProtectionRange() {
        if (!this.structureProtection && !LOTRConfig.allowBannerProtection) {
            return 0;
        }
        if (this.customRange > 0) {
            return this.customRange;
        }
        int i = MathHelper.floor_double(this.posX);
        int j = MathHelper.floor_double(this.boundingBox.minY);
        int k = MathHelper.floor_double(this.posZ);
        Block block = this.worldObj.getBlock(i, j - 1, k);
        int meta = this.worldObj.getBlockMetadata(i, j - 1, k);
        return LOTRBannerProtection.getProtectionRange(block, meta);
    }

    public boolean isProtectingTerritory() {
        return this.getProtectionRange() > 0;
    }

    public AxisAlignedBB createProtectionCube() {
        int i = MathHelper.floor_double(this.posX);
        int j = MathHelper.floor_double(this.boundingBox.minY);
        int k = MathHelper.floor_double(this.posZ);
        int range = this.getProtectionRange();
        return AxisAlignedBB.getBoundingBox(i, j, k, i + 1, j + 1, k + 1).expand(range, range, range);
    }

    public boolean isPlayerSpecificProtection() {
        return this.playerSpecificProtection;
    }

    public void setPlayerSpecificProtection(boolean flag) {
        this.playerSpecificProtection = flag;
        if (!this.worldObj.isRemote) {
            this.updateForAllWatchers(this.worldObj);
        }
    }

    public boolean isSelfProtection() {
        if (!LOTRConfig.allowSelfProtectingBanners) {
            return false;
        }
        return this.selfProtection;
    }

    public void setSelfProtection(boolean flag) {
        this.selfProtection = flag;
        if (!this.worldObj.isRemote) {
            this.updateForAllWatchers(this.worldObj);
        }
    }

    public boolean isStructureProtection() {
        return this.structureProtection;
    }

    public void setStructureProtection(boolean flag) {
        this.structureProtection = flag;
        if (!this.worldObj.isRemote) {
            this.updateForAllWatchers(this.worldObj);
        }
    }

    public void setCustomRange(int i) {
        this.customRange = MathHelper.clamp_int(i, 0, 64);
        if (!this.worldObj.isRemote) {
            this.updateForAllWatchers(this.worldObj);
        }
    }

    public float getAlignmentProtection() {
        return this.alignmentProtection;
    }

    public void setAlignmentProtection(float f) {
        this.alignmentProtection = MathHelper.clamp_float(f, ALIGNMENT_PROTECTION_MIN, ALIGNMENT_PROTECTION_MAX);
        if (!this.worldObj.isRemote) {
            this.updateForAllWatchers(this.worldObj);
        }
    }

    public void setPlacingPlayer(EntityPlayer player) {
        this.whitelistPlayer(0, player.getGameProfile());
    }

    public GameProfile getPlacingPlayer() {
        return this.getWhitelistedPlayer(0);
    }

    public GameProfile getWhitelistedPlayer(int index) {
        if (this.allowedPlayers[index] == null) {
            return null;
        }
        return this.allowedPlayers[index].profile;
    }

    public LOTRBannerWhitelistEntry getWhitelistEntry(int index) {
        return this.allowedPlayers[index];
    }

    public void whitelistPlayer(int index, GameProfile profile) {
        ArrayList<LOTRBannerProtection.Permission> defaultPerms = new ArrayList<>();
        defaultPerms.add(LOTRBannerProtection.Permission.FULL);
        this.whitelistPlayer(index, profile, defaultPerms);
    }

    public void whitelistPlayer(int index, GameProfile profile, List<LOTRBannerProtection.Permission> perms) {
        if (index < 0 || index >= this.allowedPlayers.length) {
            return;
        }
        if (profile == null) {
            this.allowedPlayers[index] = null;
        } else {
            LOTRBannerWhitelistEntry entry = new LOTRBannerWhitelistEntry(profile);
            entry.setPermissions(perms);
            this.allowedPlayers[index] = entry;
        }
        if (!this.worldObj.isRemote) {
            this.updateForAllWatchers(this.worldObj);
        }
    }

    public void whitelistFellowship(int index, LOTRFellowship fs, List<LOTRBannerProtection.Permission> perms) {
        if (this.isValidFellowship(fs)) {
            this.whitelistPlayer(index, new LOTRFellowshipProfile(this, fs.getFellowshipID(), ""), perms);
        }
    }

    public LOTRFellowship getPlacersFellowshipByName(String fsName) {
        UUID ownerID;
        GameProfile owner = this.getPlacingPlayer();
        if (owner != null && (ownerID = owner.getId()) != null) {
            return LOTRLevelData.getData(ownerID).getFellowshipByName(fsName);
        }
        return null;
    }

    public boolean hasDefaultPermission(LOTRBannerProtection.Permission p) {
        return this.defaultPermissions.contains(p);
    }

    public void addDefaultPermission(LOTRBannerProtection.Permission p) {
        if (p == LOTRBannerProtection.Permission.FULL) {
            return;
        }
        this.defaultPermissions.add(p);
        if (!this.worldObj.isRemote) {
            this.updateForAllWatchers(this.worldObj);
        }
    }

    public void removeDefaultPermission(LOTRBannerProtection.Permission p) {
        this.defaultPermissions.remove(p);
        if (!this.worldObj.isRemote) {
            this.updateForAllWatchers(this.worldObj);
        }
    }

    public int getDefaultPermBitFlags() {
        return LOTRBannerWhitelistEntry.static_encodePermBitFlags(this.defaultPermissions);
    }

    public void setDefaultPermissions(Collection<LOTRBannerProtection.Permission> perms) {
        this.defaultPermissions.clear();
        for (LOTRBannerProtection.Permission p : perms) {
            if (p == LOTRBannerProtection.Permission.FULL) continue;
            this.defaultPermissions.add(p);
        }
        if (!this.worldObj.isRemote) {
            this.updateForAllWatchers(this.worldObj);
        }
    }

    public boolean isPlayerWhitelisted(EntityPlayer entityplayer, LOTRBannerProtection.Permission perm) {
        if (this.playerSpecificProtection) {
            if (this.hasDefaultPermission(perm)) {
                return true;
            }
            GameProfile playerProfile = entityplayer.getGameProfile();
            if (playerProfile != null && playerProfile.getId() != null) {
                String playerName = playerProfile.getName();
                UUID playerID = playerProfile.getId();
                for (LOTRBannerWhitelistEntry entry : this.allowedPlayers) {
                    if (entry == null) continue;
                    GameProfile profile = entry.profile;
                    boolean playerMatch = false;
                    if (profile instanceof LOTRFellowshipProfile) {
                        Object fs;
                        LOTRFellowshipProfile fsPro = (LOTRFellowshipProfile)profile;
                        if (!this.worldObj.isRemote) {
                            fs = fsPro.getFellowship();
                            if (fs != null && ((LOTRFellowship)fs).containsPlayer(playerID)) {
                                playerMatch = true;
                            }
                        } else {
                            fs = fsPro.getFellowshipClient();
                            if (fs != null && ((LOTRFellowshipClient)fs).isPlayerIn(playerName)) {
                                playerMatch = true;
                            }
                        }
                    } else if (profile.getId() != null && profile.getId().equals(playerID)) {
                        playerMatch = true;
                    }
                    if (!playerMatch || !entry.allowsPermission(perm)) continue;
                    return true;
                }
            }
        }
        return false;
    }

    public int getWhitelistLength() {
        return this.allowedPlayers.length;
    }

    public void resizeWhitelist(int length) {
        if ((length = MathHelper.clamp_int(length, WHITELIST_MIN, WHITELIST_MAX)) == this.allowedPlayers.length) {
            return;
        }
        LOTRBannerWhitelistEntry[] resized = new LOTRBannerWhitelistEntry[length];
        for (int i = 0; i < length; ++i) {
            if (i >= this.allowedPlayers.length) continue;
            resized[i] = this.allowedPlayers[i];
        }
        this.allowedPlayers = resized;
        if (!this.worldObj.isRemote) {
            this.updateForAllWatchers(this.worldObj);
        }
    }

    public boolean isPlayerAllowedByFaction(EntityPlayer entityplayer, LOTRBannerProtection.Permission perm) {
        if (!this.playerSpecificProtection) {
            if (this.hasDefaultPermission(perm)) {
                return true;
            }
            float alignment = LOTRLevelData.getData(entityplayer).getAlignment(this.getBannerType().faction);
            if (alignment >= this.getAlignmentProtection()) {
                return true;
            }
        }
        return false;
    }

    public boolean canBeCollidedWith() {
        return true;
    }

    public AxisAlignedBB getBoundingBox() {
        return null;
    }

    public void onUpdate() {
        boolean onSolidBlock;
        super.onUpdate();
        boolean protecting = this.isProtectingTerritory();
        if (!this.worldObj.isRemote && protecting) {
            this.wasEverProtecting = true;
        }
        if (!this.worldObj.isRemote && this.getPlacingPlayer() == null && this.playerSpecificProtection) {
            this.playerSpecificProtection = false;
        }
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.func_145771_j(this.posX, (this.boundingBox.minY + this.boundingBox.maxY) / 2.0, this.posZ);
        this.motionZ = 0.0;
        this.motionX = 0.0;
        this.motionY = 0.0;
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        int i = MathHelper.floor_double(this.posX);
        int j = MathHelper.floor_double(this.boundingBox.minY);
        int k = MathHelper.floor_double(this.posZ);
        onSolidBlock = World.doesBlockHaveSolidTopSurface(this.worldObj, i, j - 1, k) && this.boundingBox.minY == MathHelper.ceiling_double_int(this.boundingBox.minY);
        if (!this.worldObj.isRemote && !onSolidBlock) {
            this.dropAsItem(true);
        }
        this.ignoreFrustumCheck = protecting;
    }

    public void writeEntityToNBT(NBTTagCompound nbt) {
        nbt.setByte("BannerType", (byte)this.getBannerTypeID());
        if (this.protectData == null && this.wasEverProtecting) {
            this.protectData = new NBTTagCompound();
        }
        if (this.protectData != null) {
            this.writeProtectionToNBT(this.protectData);
            nbt.setTag("ProtectData", this.protectData);
        }
    }

    public void readEntityFromNBT(NBTTagCompound nbt) {
        this.setBannerTypeID(nbt.getByte("BannerType"));
        if (nbt.hasKey("PlayerProtection")) {
            this.readProtectionFromNBT(nbt);
            this.protectData = new NBTTagCompound();
            this.writeProtectionToNBT(this.protectData);
        } else if (nbt.hasKey("ProtectData")) {
            this.readProtectionFromNBT(nbt.getCompoundTag("ProtectData"));
        }
    }

    public final void writeProtectionToNBT(NBTTagCompound nbt) {
        nbt.setBoolean("PlayerProtection", this.playerSpecificProtection);
        nbt.setBoolean("StructureProtection", this.structureProtection);
        nbt.setShort("CustomRange", (short)this.customRange);
        nbt.setBoolean("SelfProtection", this.selfProtection);
        nbt.setFloat("AlignProtectF", this.alignmentProtection);
        nbt.setInteger("WhitelistLength", this.allowedPlayers.length);
        NBTTagList allowedPlayersTags = new NBTTagList();
        for (int i = 0; i < this.allowedPlayers.length; ++i) {
            GameProfile profile;
            LOTRBannerWhitelistEntry entry = this.allowedPlayers[i];
            if (entry == null || (profile = entry.profile) == null) continue;
            NBTTagCompound playerData = new NBTTagCompound();
            playerData.setInteger("Index", i);
            boolean isFellowship = profile instanceof LOTRFellowshipProfile;
            playerData.setBoolean("Fellowship", isFellowship);
            if (isFellowship) {
                LOTRFellowship fs = ((LOTRFellowshipProfile)profile).getFellowship();
                if (fs != null) {
                    playerData.setString("FellowshipID", fs.getFellowshipID().toString());
                }
            } else {
                NBTTagCompound profileData = new NBTTagCompound();
                NBTUtil.func_152460_a(profileData, profile);
                playerData.setTag("Profile", profileData);
            }
            NBTTagList permTags = new NBTTagList();
            for (LOTRBannerProtection.Permission p : entry.listPermissions()) {
                String pName = p.codeName;
                permTags.appendTag(new NBTTagString(pName));
            }
            playerData.setTag("Perms", permTags);
            playerData.setBoolean("PermsSaved", true);
            allowedPlayersTags.appendTag(playerData);
        }
        nbt.setTag("AllowedPlayers", allowedPlayersTags);
        if (!this.defaultPermissions.isEmpty()) {
            NBTTagList permTags = new NBTTagList();
            for (LOTRBannerProtection.Permission p : this.defaultPermissions) {
                String pName = p.codeName;
                permTags.appendTag(new NBTTagString(pName));
            }
            nbt.setTag("DefaultPerms", permTags);
        }
    }

    public final void readProtectionFromNBT(NBTTagCompound nbt) {
        this.protectData = (NBTTagCompound)nbt.copy();
        this.playerSpecificProtection = nbt.getBoolean("PlayerProtection");
        this.structureProtection = nbt.getBoolean("StructureProtection");
        this.customRange = nbt.getShort("CustomRange");
        this.customRange = MathHelper.clamp_int(this.customRange, 0, 64);
        this.selfProtection = nbt.hasKey("SelfProtection") ? nbt.getBoolean("SelfProtection") : true;
        if (nbt.hasKey("AlignmentProtection")) {
            this.setAlignmentProtection(nbt.getInteger("AlignmentProtection"));
        } else {
            this.setAlignmentProtection(nbt.getFloat("AlignProtectF"));
        }
        int wlength = WHITELIST_DEFAULT;
        if (nbt.hasKey("WhitelistLength")) {
            wlength = nbt.getInteger("WhitelistLength");
        }
        this.allowedPlayers = new LOTRBannerWhitelistEntry[wlength];
        NBTTagList allowedPlayersTags = nbt.getTagList("AllowedPlayers", 10);
        for (int i = 0; i < allowedPlayersTags.tagCount(); ++i) {
            LOTRBannerWhitelistEntry entry;
            NBTTagCompound playerData = allowedPlayersTags.getCompoundTagAt(i);
            int index = playerData.getInteger("Index");
            if (index < 0 || index >= wlength) continue;
            GameProfile profile = null;
            boolean isFellowship = playerData.getBoolean("Fellowship");
            if (isFellowship) {
                LOTRFellowshipProfile pr;
                UUID fsID;
                if (playerData.hasKey("FellowshipID") && (fsID = UUID.fromString(playerData.getString("FellowshipID"))) != null && (pr = new LOTRFellowshipProfile(this, fsID, "")).getFellowship() != null) {
                    profile = pr;
                }
            } else if (playerData.hasKey("Profile")) {
                NBTTagCompound profileData = playerData.getCompoundTag("Profile");
                profile = NBTUtil.func_152459_a(profileData);
            }
            if (profile == null) continue;
            this.allowedPlayers[i] = entry = new LOTRBannerWhitelistEntry(profile);
            boolean savedWithPerms = playerData.getBoolean("PermsSaved");
            if (savedWithPerms) {
                if (!playerData.hasKey("Perms", 9)) continue;
                NBTTagList permTags = playerData.getTagList("Perms", 8);
                for (int p = 0; p < permTags.tagCount(); ++p) {
                    String pName = permTags.getStringTagAt(p);
                    LOTRBannerProtection.Permission perm = LOTRBannerProtection.Permission.forName(pName);
                    if (perm == null) continue;
                    entry.addPermission(perm);
                }
                continue;
            }
            entry.setFullPerms();
        }
        this.validateWhitelistedFellowships();
        this.defaultPermissions.clear();
        if (nbt.hasKey("DefaultPerms")) {
            NBTTagList permTags = nbt.getTagList("DefaultPerms", 8);
            for (int p = 0; p < permTags.tagCount(); ++p) {
                String pName = permTags.getStringTagAt(p);
                LOTRBannerProtection.Permission perm = LOTRBannerProtection.Permission.forName(pName);
                if (perm == null) continue;
                this.defaultPermissions.add(perm);
            }
        }
    }

    private boolean isValidFellowship(LOTRFellowship fs) {
        GameProfile owner = this.getPlacingPlayer();
        return fs != null && !fs.isDisbanded() && owner != null && owner.getId() != null && fs.containsPlayer(owner.getId());
    }

    private void validateWhitelistedFellowships() {
        this.getPlacingPlayer();
        for (int i = 0; i < this.allowedPlayers.length; ++i) {
            GameProfile profile = this.getWhitelistedPlayer(i);
            if (!(profile instanceof LOTRFellowshipProfile) || this.isValidFellowship(((LOTRFellowshipProfile)profile).getFellowship())) continue;
            this.allowedPlayers[i] = null;
        }
    }

    public boolean canPlayerEditBanner(EntityPlayer entityplayer) {
        GameProfile owner = this.getPlacingPlayer();
        if (owner != null && owner.getId() != null && entityplayer.getUniqueID().equals(owner.getId())) {
            return true;
        }
        return !this.isStructureProtection() && MinecraftServer.getServer().getConfigurationManager().func_152596_g(entityplayer.getGameProfile()) && entityplayer.capabilities.isCreativeMode;
    }

    public boolean interactFirst(EntityPlayer entityplayer) {
        if (!this.worldObj.isRemote && this.isProtectingTerritory() && this.canPlayerEditBanner(entityplayer)) {
            this.sendBannerToPlayer(entityplayer, true, true);
        }
        return true;
    }

    public boolean attackEntityFrom(DamageSource damagesource, float f) {
        boolean isProtectionBanner = this.isProtectingTerritory();
        boolean isPlayerDamage = damagesource.getEntity() instanceof EntityPlayer;
        if (isProtectionBanner && !isPlayerDamage) {
            return false;
        }
        if (!this.isDead && !this.worldObj.isRemote) {
            if (isPlayerDamage) {
                EntityPlayer entityplayer = (EntityPlayer)damagesource.getEntity();
                if (LOTRBannerProtection.isProtected(this.worldObj, this, LOTRBannerProtection.forPlayer(entityplayer, LOTRBannerProtection.Permission.FULL), true)) {
                    if (isProtectionBanner) {
                        if (this.selfProtection) {
                            return false;
                        }
                        if (this.structureProtection && damagesource.getEntity() != damagesource.getSourceOfDamage()) {
                            return false;
                        }
                    } else {
                        return false;
                    }
                }
                if (isProtectionBanner && this.selfProtection && !this.canPlayerEditBanner(entityplayer)) {
                    return false;
                }
            }
            this.setBeenAttacked();
            this.worldObj.playSoundAtEntity(this, Blocks.planks.stepSound.getBreakSound(), (Blocks.planks.stepSound.getVolume() + 1.0f) / 2.0f, Blocks.planks.stepSound.getPitch() * 0.8f);
            boolean drop = true;
            if (damagesource.getEntity() instanceof EntityPlayer && ((EntityPlayer)damagesource.getEntity()).capabilities.isCreativeMode) {
                drop = false;
            }
            this.dropAsItem(drop);
        }
        return true;
    }

    private void dropAsItem(boolean drop) {
        this.setDead();
        if (drop) {
            this.entityDropItem(this.getBannerItem(), 0.0f);
        }
    }

    public ItemStack getPickedResult(MovingObjectPosition target) {
        return this.getBannerItem();
    }

    private ItemStack getBannerItem() {
        ItemStack item = new ItemStack(LOTRMod.banner, 1, this.getBannerType().bannerID);
        if (this.wasEverProtecting && this.protectData == null) {
            this.protectData = new NBTTagCompound();
        }
        if (this.protectData != null) {
            this.writeProtectionToNBT(this.protectData);
            if (!this.structureProtection) {
                LOTRItemBanner.setProtectionData(item, this.protectData);
            }
        }
        return item;
    }

    public void sendBannerToPlayer(EntityPlayer entityplayer, boolean sendWhitelist, boolean openGui) {
        this.sendBannerData(entityplayer, sendWhitelist, openGui);
    }

    private void updateForAllWatchers(World world) {
        int x = MathHelper.floor_double(this.posX) >> 4;
        int z = MathHelper.floor_double(this.posZ) >> 4;
        PlayerManager playermanager = ((WorldServer)this.worldObj).getPlayerManager();
        List players = this.worldObj.playerEntities;
        for (Object obj : players) {
            EntityPlayerMP entityplayer = (EntityPlayerMP)obj;
            if (!playermanager.isPlayerWatchingChunk(entityplayer, x, z)) continue;
            this.sendBannerData(entityplayer, false, false);
        }
    }

    private void sendBannerData(EntityPlayer entityplayer, boolean sendWhitelist, boolean openGui) {
        LOTRPacketBannerData packet = new LOTRPacketBannerData(this.getEntityId(), openGui);
        packet.playerSpecificProtection = this.playerSpecificProtection;
        packet.selfProtection = this.selfProtection;
        packet.structureProtection = this.structureProtection;
        packet.customRange = this.customRange;
        packet.alignmentProtection = this.getAlignmentProtection();
        packet.whitelistLength = this.getWhitelistLength();
        int maxSendIndex = sendWhitelist ? this.allowedPlayers.length : 1;
        String[] whitelistSlots = new String[maxSendIndex];
        int[] whitelistPerms = new int[maxSendIndex];
        for (int index = 0; index < maxSendIndex; ++index) {
            LOTRBannerWhitelistEntry entry = this.allowedPlayers[index];
            if (entry == null) {
                whitelistSlots[index] = null;
                continue;
            }
            GameProfile profile = entry.profile;
            if (profile == null) {
                whitelistSlots[index] = null;
                continue;
            }
            if (profile instanceof LOTRFellowshipProfile) {
                LOTRFellowshipProfile fsProfile = (LOTRFellowshipProfile)profile;
                LOTRFellowship fs = fsProfile.getFellowship();
                if (this.isValidFellowship(fs)) {
                    whitelistSlots[index] = LOTRFellowshipProfile.addFellowshipCode(fs.getName());
                }
            } else {
                String username;
                if (StringUtils.isNullOrEmpty(profile.getName())) {
                    MinecraftServer.getServer().func_147130_as().fillProfileProperties(profile, true);
                }
                if (StringUtils.isNullOrEmpty(username = profile.getName())) {
                    whitelistSlots[index] = null;
                    if (index == 0) {
                        LOTRLog.logger.info("LOTR: Banner needs to be replaced at " + MathHelper.floor_double(this.posX) + " " + MathHelper.floor_double(this.posY) + " " + MathHelper.floor_double(this.posZ) + " dim_" + this.dimension);
                    }
                } else {
                    whitelistSlots[index] = username;
                }
            }
            if (whitelistSlots[index] == null) continue;
            whitelistPerms[index] = entry.encodePermBitFlags();
        }
        packet.whitelistSlots = whitelistSlots;
        packet.whitelistPerms = whitelistPerms;
        packet.defaultPerms = this.getDefaultPermBitFlags();
        packet.thisPlayerHasPermission = this.isPlayerPermittedInSurvival(entityplayer);
        LOTRPacketHandler.networkWrapper.sendTo((IMessage)packet, (EntityPlayerMP)entityplayer);
    }

    private boolean isPlayerPermittedInSurvival(EntityPlayer entityplayer) {
        return new LOTRBannerProtection.FilterForPlayer(entityplayer, LOTRBannerProtection.Permission.FULL).ignoreCreativeMode().protects(this) == LOTRBannerProtection.ProtectType.NONE;
    }

    public boolean clientside_playerHasPermissionInSurvival() {
        return this.clientside_playerHasPermission;
    }

    public void setClientside_playerHasPermissionInSurvival(boolean flag) {
        this.clientside_playerHasPermission = flag;
    }
}

