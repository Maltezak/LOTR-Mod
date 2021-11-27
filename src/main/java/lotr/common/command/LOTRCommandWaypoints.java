package lotr.common.command;

import java.util.*;

import lotr.common.*;
import lotr.common.world.map.LOTRWaypoint;
import net.minecraft.command.*;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

public class LOTRCommandWaypoints extends CommandBase {
    @Override
    public String getCommandName() {
        return "lotrWaypoints";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "commands.lotr.lotrWaypoints.usage";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if(args.length >= 2) {
            String regionName = args[1];
            EntityPlayerMP entityplayer = args.length >= 3 ? CommandBase.getPlayer(sender, args[2]) : CommandBase.getCommandSenderAsPlayer(sender);
            LOTRPlayerData playerData = LOTRLevelData.getData(entityplayer);
            if(args[0].equalsIgnoreCase("unlock")) {
                if(regionName.equalsIgnoreCase("all")) {
                    for(LOTRWaypoint.Region region : LOTRWaypoint.Region.values()) {
                        playerData.unlockFTRegion(region);
                    }
                    CommandBase.func_152373_a(sender, this, "commands.lotr.lotrWaypoints.unlockAll", entityplayer.getCommandSenderName());
                    return;
                }
                LOTRWaypoint.Region region = this.findRegionByName(regionName);
                if(playerData.isFTRegionUnlocked(region)) {
                    throw new WrongUsageException("commands.lotr.lotrWaypoints.unlock.fail", entityplayer.getCommandSenderName(), region.name());
                }
                playerData.unlockFTRegion(region);
                CommandBase.func_152373_a(sender, this, "commands.lotr.lotrWaypoints.unlock", entityplayer.getCommandSenderName(), region.name());
                return;
            }
            if(args[0].equalsIgnoreCase("lock")) {
                if(regionName.equalsIgnoreCase("all")) {
                    for(LOTRWaypoint.Region region : LOTRWaypoint.Region.values()) {
                        playerData.lockFTRegion(region);
                    }
                    CommandBase.func_152373_a(sender, this, "commands.lotr.lotrWaypoints.lockAll", entityplayer.getCommandSenderName());
                    return;
                }
                LOTRWaypoint.Region region = this.findRegionByName(regionName);
                if(!playerData.isFTRegionUnlocked(region)) {
                    throw new WrongUsageException("commands.lotr.lotrWaypoints.lock.fail", entityplayer.getCommandSenderName(), region.name());
                }
                playerData.lockFTRegion(region);
                CommandBase.func_152373_a(sender, this, "commands.lotr.lotrWaypoints.lock", entityplayer.getCommandSenderName(), region.name());
                return;
            }
        }
        throw new WrongUsageException(this.getCommandUsage(sender));
    }

    private LOTRWaypoint.Region findRegionByName(String name) {
        LOTRWaypoint.Region region = LOTRWaypoint.regionForName(name);
        if(region == null) {
            throw new CommandException("commands.lotr.lotrWaypoints.unknown", name);
        }
        return region;
    }

    @Override
    public List addTabCompletionOptions(ICommandSender sender, String[] args) {
        if(args.length == 1) {
            return CommandBase.getListOfStringsMatchingLastWord(args, "unlock", "lock");
        }
        if(args.length == 2) {
            ArrayList<String> names = new ArrayList<>();
            for(LOTRWaypoint.Region r : LOTRWaypoint.Region.values()) {
                names.add(r.name());
            }
            names.add("all");
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
