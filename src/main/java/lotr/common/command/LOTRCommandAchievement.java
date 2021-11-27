package lotr.common.command;

import java.util.*;

import lotr.common.*;
import net.minecraft.command.*;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

public class LOTRCommandAchievement extends CommandBase {
    @Override
    public String getCommandName() {
        return "lotrAchievement";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "commands.lotr.lotrAchievement.usage";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if(args.length >= 2) {
            String achName = args[1];
            EntityPlayerMP entityplayer = args.length >= 3 ? CommandBase.getPlayer(sender, args[2]) : CommandBase.getCommandSenderAsPlayer(sender);
            LOTRPlayerData playerData = LOTRLevelData.getData(entityplayer);
            if(args[0].equalsIgnoreCase("give")) {
                LOTRAchievement ach = this.findAchievementByName(achName);
                if(playerData.hasAchievement(ach)) {
                    throw new WrongUsageException("commands.lotr.lotrAchievement.give.fail", entityplayer.getCommandSenderName(), ach.getTitle(entityplayer));
                }
                playerData.addAchievement(ach);
                CommandBase.func_152373_a(sender, this, "commands.lotr.lotrAchievement.give", entityplayer.getCommandSenderName(), ach.getTitle(entityplayer));
                return;
            }
            if(args[0].equalsIgnoreCase("remove")) {
                if(achName.equalsIgnoreCase("all")) {
                    ArrayList<LOTRAchievement> allAchievements = new ArrayList<>(playerData.getAchievements());
                    for(LOTRAchievement ach : allAchievements) {
                        playerData.removeAchievement(ach);
                    }
                    CommandBase.func_152373_a(sender, this, "commands.lotr.lotrAchievement.removeAll", entityplayer.getCommandSenderName());
                    return;
                }
                LOTRAchievement ach = this.findAchievementByName(achName);
                if(!playerData.hasAchievement(ach)) {
                    throw new WrongUsageException("commands.lotr.lotrAchievement.remove.fail", entityplayer.getCommandSenderName(), ach.getTitle(entityplayer));
                }
                playerData.removeAchievement(ach);
                CommandBase.func_152373_a(sender, this, "commands.lotr.lotrAchievement.remove", entityplayer.getCommandSenderName(), ach.getTitle(entityplayer));
                return;
            }
        }
        throw new WrongUsageException(this.getCommandUsage(sender));
    }

    private LOTRAchievement findAchievementByName(String name) {
        LOTRAchievement ach = LOTRAchievement.findByName(name);
        if(ach == null) {
            throw new CommandException("commands.lotr.lotrAchievement.unknown", name);
        }
        return ach;
    }

    @Override
    public List addTabCompletionOptions(ICommandSender sender, String[] args) {
        if(args.length == 1) {
            return CommandBase.getListOfStringsMatchingLastWord(args, "give", "remove");
        }
        if(args.length == 2) {
            List<LOTRAchievement> achievements = LOTRAchievement.getAllAchievements();
            ArrayList<String> names = new ArrayList<>();
            for(LOTRAchievement a : achievements) {
                names.add(a.getCodeName());
            }
            if(args[0].equals("remove")) {
                names.add("all");
            }
            return CommandBase.getListOfStringsMatchingLastWord(args, names.toArray(new String[0]));
        }
        if(args.length == 3) {
            return CommandBase.getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames());
        }
        return null;
    }

    @Override
    public boolean isUsernameIndex(String[] args, int i) {
        return i == 2;
    }
}
