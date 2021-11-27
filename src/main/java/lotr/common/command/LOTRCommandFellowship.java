package lotr.common.command;

import java.util.*;

import org.apache.commons.lang3.StringUtils;

import com.mojang.authlib.GameProfile;

import lotr.common.*;
import lotr.common.fellowship.LOTRFellowship;
import net.minecraft.command.*;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.*;
import net.minecraft.server.MinecraftServer;

public class LOTRCommandFellowship extends CommandBase {
    @Override
    public String getCommandName() {
        return "fellowship";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 3;
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "commands.lotr.fellowship.usage";
    }

    private UUID getPlayerIDByName(ICommandSender sender, String username) {
        try {
            EntityPlayerMP entityplayer = CommandBase.getPlayer(sender, username);
            if(entityplayer != null) {
                return entityplayer.getUniqueID();
            }
        }
        catch(PlayerNotFoundException entityplayer) {
        }
        GameProfile profile = MinecraftServer.getServer().func_152358_ax().func_152655_a(username);
        if(profile != null) {
            return profile.getId();
        }
        return null;
    }

    public static String[] fixArgsForFellowship(String[] args, int startIndex, boolean autocompleting) {
        if(args[startIndex].startsWith("\"")) {
            int endIndex = startIndex;
            boolean foundEnd = false;
            while(!foundEnd) {
                if(args[endIndex].endsWith("\"")) {
                    foundEnd = true;
                    continue;
                }
                if(endIndex >= args.length - 1) {
                    if(autocompleting) break;
                    throw new WrongUsageException("commands.lotr.fellowship.edit.nameError");
                }
                ++endIndex;
            }
            String fsName = "";
            for(int i = startIndex; i <= endIndex; ++i) {
                if(i > startIndex) {
                    fsName = fsName + " ";
                }
                fsName = fsName + args[i];
            }
            if(!autocompleting || foundEnd) {
                fsName = fsName.replace("\"", "");
            }
            int diff = endIndex - startIndex;
            String[] argsNew = new String[args.length - diff];
            for(int i = 0; i < argsNew.length; ++i) {
                argsNew[i] = i < startIndex ? args[i] : (i == startIndex ? fsName : args[i + diff]);
            }
            return argsNew;
        }
        if(!autocompleting) {
            throw new WrongUsageException("commands.lotr.fellowship.edit.nameError");
        }
        return args;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if(args.length >= 3 && args[0].equals("create")) {
            args = LOTRCommandFellowship.fixArgsForFellowship(args, 2, false);
            String playerName = args[1];
            String fsName = args[2];
            if(fsName == null) {
                throw new WrongUsageException("commands.lotr.fellowship.edit.notFound", playerName, fsName);
            }
            UUID playerID = this.getPlayerIDByName(sender, playerName);
            if(playerID == null) throw new PlayerNotFoundException();
            LOTRPlayerData playerData = LOTRLevelData.getData(playerID);
            LOTRFellowship fellowship = playerData.getFellowshipByName(fsName);
            if(fellowship != null) throw new WrongUsageException("commands.lotr.fellowship.create.exists", playerName, fsName);
            playerData.createFellowship(fsName, false);
            CommandBase.func_152373_a(sender, this, "commands.lotr.fellowship.create", playerName, fsName);
            return;
        }
        if(!args[0].equals("option")) throw new WrongUsageException(this.getCommandUsage(sender));
        if((args = LOTRCommandFellowship.fixArgsForFellowship(args, 2, false)).length < 4) throw new PlayerNotFoundException();
        String ownerName = args[1];
        String fsName = args[2];
        if(fsName == null) {
            throw new WrongUsageException("commands.lotr.fellowship.edit.notFound", ownerName, fsName);
        }
        String option = args[3];
        UUID ownerID = this.getPlayerIDByName(sender, ownerName);
        if(ownerID == null) throw new WrongUsageException(this.getCommandUsage(sender));
        LOTRPlayerData ownerData = LOTRLevelData.getData(ownerID);
        LOTRFellowship fellowship = ownerData.getFellowshipByName(fsName);
        if(fellowship == null || !fellowship.isOwner(ownerID)) throw new WrongUsageException("commands.lotr.fellowship.edit.notFound", ownerName, fsName);
        if(option.equals("disband")) {
            ownerData.disbandFellowship(fellowship);
            CommandBase.func_152373_a(sender, this, "commands.lotr.fellowship.disband", ownerName, fsName);
            return;
        }
        if(option.equals("rename")) {
            String newName = "";
            int startIndex = 4;
            if(args[startIndex].startsWith("\"")) {
                int endIndex = startIndex;
                while(!args[endIndex].endsWith("\"")) {
                    if(++endIndex < args.length) continue;
                    throw new WrongUsageException("commands.lotr.fellowship.rename.error");
                }
                for(int i = startIndex; i <= endIndex; ++i) {
                    if(i > startIndex) {
                        newName = newName + " ";
                    }
                    newName = newName + args[i];
                }
                newName = newName.replace("\"", "");
            }
            if(StringUtils.isBlank(newName)) throw new WrongUsageException("commands.lotr.fellowship.rename.error");
            ownerData.renameFellowship(fellowship, newName);
            CommandBase.func_152373_a(sender, this, "commands.lotr.fellowship.rename", ownerName, fsName, newName);
            return;
        }
        if(option.equals("icon")) {
            String iconData = CommandBase.func_147178_a(sender, args, 4).getUnformattedText();
            if(iconData.equals("clear")) {
                ownerData.setFellowshipIcon(fellowship, null);
                CommandBase.func_152373_a(sender, this, "commands.lotr.fellowship.icon", ownerName, fsName, "[none]");
                return;
            }
            ItemStack itemstack = null;
            try {
                NBTBase nbt = JsonToNBT.func_150315_a(iconData);
                if(!(nbt instanceof NBTTagCompound)) {
                    CommandBase.func_152373_a(sender, this, "commands.lotr.fellowship.icon.tagError", "Not a valid tag");
                    return;
                }
                NBTTagCompound compound = (NBTTagCompound) nbt;
                itemstack = ItemStack.loadItemStackFromNBT(compound);
            }
            catch(NBTException nbtexception) {
                CommandBase.func_152373_a(sender, this, "commands.lotr.fellowship.icon.tagError", nbtexception.getMessage());
                return;
            }
            if(itemstack != null) {
                ownerData.setFellowshipIcon(fellowship, itemstack);
                CommandBase.func_152373_a(sender, this, "commands.lotr.fellowship.icon", ownerName, fsName, itemstack.getDisplayName());
                return;
            }
            CommandBase.func_152373_a(sender, this, "commands.lotr.fellowship.icon.tagError", "No item");
            return;
        }
        if(option.equals("pvp") || option.equals("hired-ff")) {
            boolean prevent;
            String setting = args[4];
            if(setting.equals("prevent")) {
                prevent = true;
            }
            else {
                if(!setting.equals("allow")) throw new WrongUsageException(this.getCommandUsage(sender));
                prevent = false;
            }
            if(option.equals("pvp")) {
                ownerData.setFellowshipPreventPVP(fellowship, prevent);
                if(prevent) {
                    CommandBase.func_152373_a(sender, this, "commands.lotr.fellowship.pvp.prevent", ownerName, fsName);
                    return;
                }
                else {
                    CommandBase.func_152373_a(sender, this, "commands.lotr.fellowship.pvp.allow", ownerName, fsName);
                }
                return;
            }
            if(!option.equals("hired-ff")) throw new WrongUsageException(this.getCommandUsage(sender));
            ownerData.setFellowshipPreventHiredFF(fellowship, prevent);
            if(prevent) {
                CommandBase.func_152373_a(sender, this, "commands.lotr.fellowship.hiredFF.prevent", ownerName, fsName);
                return;
            }
            else {
                CommandBase.func_152373_a(sender, this, "commands.lotr.fellowship.hiredFF.allow", ownerName, fsName);
            }
            return;
        }
        if(option.equals("map-show")) {
            boolean show;
            String setting = args[4];
            if(setting.equals("on")) {
                show = true;
            }
            else {
                if(!setting.equals("off")) throw new WrongUsageException(this.getCommandUsage(sender));
                show = false;
            }
            ownerData.setFellowshipShowMapLocations(fellowship, show);
            if(show) {
                CommandBase.func_152373_a(sender, this, "commands.lotr.fellowship.mapShow.on", ownerName, fsName);
                return;
            }
            else {
                CommandBase.func_152373_a(sender, this, "commands.lotr.fellowship.mapShow.off", ownerName, fsName);
            }
            return;
        }
        if(args.length < 3) throw new WrongUsageException(this.getCommandUsage(sender));
        String playerName = args[4];
        UUID playerID = this.getPlayerIDByName(sender, playerName);
        if(playerID == null) throw new PlayerNotFoundException();
        LOTRPlayerData playerData = LOTRLevelData.getData(playerID);
        if(option.equals("invite")) {
            if(fellowship.containsPlayer(playerID)) throw new WrongUsageException("commands.lotr.fellowship.edit.alreadyIn", ownerName, fsName, playerName);
            ownerData.invitePlayerToFellowship(fellowship, playerID);
            CommandBase.func_152373_a(sender, this, "commands.lotr.fellowship.invite", ownerName, fsName, playerName);
            return;
        }
        if(option.equals("add")) {
            if(fellowship.containsPlayer(playerID)) throw new WrongUsageException("commands.lotr.fellowship.edit.alreadyIn", ownerName, fsName, playerName);
            ownerData.invitePlayerToFellowship(fellowship, playerID);
            playerData.acceptFellowshipInvite(fellowship);
            CommandBase.func_152373_a(sender, this, "commands.lotr.fellowship.add", ownerName, fsName, playerName);
            return;
        }
        if(option.equals("remove")) {
            if(!fellowship.hasMember(playerID)) throw new WrongUsageException("commands.lotr.fellowship.edit.notMember", ownerName, fsName, playerName);
            ownerData.removePlayerFromFellowship(fellowship, playerID);
            CommandBase.func_152373_a(sender, this, "commands.lotr.fellowship.remove", ownerName, fsName, playerName);
            return;
        }
        if(option.equals("transfer")) {
            if(!fellowship.hasMember(playerID)) throw new WrongUsageException("commands.lotr.fellowship.edit.notMember", ownerName, fsName, playerName);
            ownerData.transferFellowship(fellowship, playerID);
            CommandBase.func_152373_a(sender, this, "commands.lotr.fellowship.transfer", ownerName, fsName, playerName);
            return;
        }
        if(option.equals("op")) {
            if(!fellowship.hasMember(playerID)) throw new WrongUsageException("commands.lotr.fellowship.edit.notMember", ownerName, fsName, playerName);
            if(fellowship.isAdmin(playerID)) throw new WrongUsageException("commands.lotr.fellowship.edit.alreadyOp", ownerName, fsName, playerName);
            ownerData.setFellowshipAdmin(fellowship, playerID, true);
            CommandBase.func_152373_a(sender, this, "commands.lotr.fellowship.op", ownerName, fsName, playerName);
            return;
        }
        if(!option.equals("deop")) throw new WrongUsageException(this.getCommandUsage(sender));
        if(!fellowship.hasMember(playerID)) throw new WrongUsageException("commands.lotr.fellowship.edit.notMember", ownerName, fsName, playerName);
        if(!fellowship.isAdmin(playerID)) throw new WrongUsageException("commands.lotr.fellowship.edit.notOp", ownerName, fsName, playerName);
        ownerData.setFellowshipAdmin(fellowship, playerID, false);
        CommandBase.func_152373_a(sender, this, "commands.lotr.fellowship.deop", ownerName, fsName, playerName);
    }

    @Override
    public List addTabCompletionOptions(ICommandSender sender, String[] args) {
        if(args.length == 1) {
            return CommandBase.getListOfStringsMatchingLastWord(args, "create", "option");
        }
        if(args.length == 2) {
            return CommandBase.getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames());
        }
        if(args.length > 2) {
            String function = args[0];
            if(function.equals("create")) {
                return null;
            }
            if(function.equals("option")) {
                String[] argsOriginal = Arrays.copyOf(args, args.length);
                String ownerName = (args = LOTRCommandFellowship.fixArgsForFellowship(args, 2, true))[1];
                UUID ownerID = this.getPlayerIDByName(sender, ownerName);
                if(ownerID != null) {
                    LOTRFellowship fellowship;
                    LOTRPlayerData playerData = LOTRLevelData.getData(ownerID);
                    String fsName = args[2];
                    if(args.length == 3) {
                        return LOTRCommandFellowship.listFellowshipsMatchingLastWord(args, argsOriginal, 2, playerData, true);
                    }
                    if(fsName != null && (fellowship = playerData.getFellowshipByName(fsName)) != null) {
                        if(args.length == 4) {
                            return CommandBase.getListOfStringsMatchingLastWord(args, "invite", "add", "remove", "transfer", "op", "deop", "disband", "rename", "icon", "pvp", "hired-ff", "map-show");
                        }
                        String option = args[3];
                        if(option.equals("invite") || option.equals("add")) {
                            ArrayList<String> notInFellowshipNames = new ArrayList<>();
                            for(GameProfile playerProfile : MinecraftServer.getServer().getConfigurationManager().func_152600_g()) {
                                UUID playerID = playerProfile.getId();
                                if(fellowship.containsPlayer(playerID)) continue;
                                notInFellowshipNames.add(playerProfile.getName());
                            }
                            return CommandBase.getListOfStringsMatchingLastWord(args, notInFellowshipNames.toArray(new String[0]));
                        }
                        if(option.equals("remove") || option.equals("transfer")) {
                            ArrayList<String> memberNames = new ArrayList<>();
                            for(UUID playerID : fellowship.getMemberUUIDs()) {
                                GameProfile playerProfile = MinecraftServer.getServer().func_152358_ax().func_152652_a(playerID);
                                if(playerProfile == null || playerProfile.getName() == null) continue;
                                memberNames.add(playerProfile.getName());
                            }
                            return CommandBase.getListOfStringsMatchingLastWord(args, memberNames.toArray(new String[0]));
                        }
                        if(option.equals("op")) {
                            ArrayList<String> notAdminNames = new ArrayList<>();
                            for(UUID playerID : fellowship.getMemberUUIDs()) {
                                GameProfile playerProfile;
                                if(fellowship.isAdmin(playerID) || (playerProfile = MinecraftServer.getServer().func_152358_ax().func_152652_a(playerID)) == null || playerProfile.getName() == null) continue;
                                notAdminNames.add(playerProfile.getName());
                            }
                            return CommandBase.getListOfStringsMatchingLastWord(args, notAdminNames.toArray(new String[0]));
                        }
                        if(option.equals("deop")) {
                            ArrayList<String> adminNames = new ArrayList<>();
                            for(UUID playerID : fellowship.getMemberUUIDs()) {
                                GameProfile playerProfile;
                                if(!fellowship.isAdmin(playerID) || (playerProfile = MinecraftServer.getServer().func_152358_ax().func_152652_a(playerID)) == null || playerProfile.getName() == null) continue;
                                adminNames.add(playerProfile.getName());
                            }
                            return CommandBase.getListOfStringsMatchingLastWord(args, adminNames.toArray(new String[0]));
                        }
                        if(option.equals("pvp") || option.equals("hired-ff")) {
                            return CommandBase.getListOfStringsMatchingLastWord(args, "prevent", "allow");
                        }
                        if(option.equals("map-show")) {
                            return CommandBase.getListOfStringsMatchingLastWord(args, "on", "off");
                        }
                    }
                }
            }
        }
        return null;
    }

    public static List<String> listFellowshipsMatchingLastWord(String[] argsFixed, String[] argsOriginal, int fsNameIndex, LOTRPlayerData playerData, boolean leadingOnly) {
        String fsName = argsFixed[fsNameIndex];
        List<String> allFellowshipNames = leadingOnly ? playerData.listAllLeadingFellowshipNames() : playerData.listAllFellowshipNames();
        ArrayList<String> autocompletes = new ArrayList<>();
        for(String nextFsName : allFellowshipNames) {
            String autocompFsName = "\"" + nextFsName + "\"";
            if(!autocompFsName.toLowerCase().startsWith(fsName.toLowerCase())) continue;
            if(argsOriginal.length > argsFixed.length) {
                int diff = argsOriginal.length - argsFixed.length;
                for(int j = 0; j < diff; ++j) {
                    autocompFsName = autocompFsName.substring(autocompFsName.indexOf(" ") + 1);
                }
            }
            if(autocompFsName.indexOf(" ") >= 0) {
                autocompFsName = autocompFsName.substring(0, autocompFsName.indexOf(" "));
            }
            autocompletes.add(autocompFsName);
        }
        return CommandBase.getListOfStringsMatchingLastWord(argsOriginal, autocompletes.toArray(new String[0]));
    }

    @Override
    public boolean isUsernameIndex(String[] args, int i) {
        String option;
        if(args.length >= 5 && args[0].equals("option") && ((option = args[3]).equals("invite") || option.equals("add") || option.equals("remove") || option.equals("transfer"))) {
            return i == 4;
        }
        return false;
    }
}
