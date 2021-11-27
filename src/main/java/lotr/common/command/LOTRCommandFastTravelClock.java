package lotr.common.command;

import java.util.List;

import lotr.common.LOTRLevelData;
import net.minecraft.command.*;
import net.minecraft.entity.player.*;
import net.minecraft.server.MinecraftServer;

public class LOTRCommandFastTravelClock
extends CommandBase {
    public String getCommandName() {
        return "fastTravelClock";
    }

    public int getRequiredPermissionLevel() {
        return 2;
    }

    public String getCommandUsage(ICommandSender sender) {
        return "commands.lotr.fastTravelClock.usage";
    }

    public void processCommand(ICommandSender sender, String[] args) {
        if (args.length >= 1) {
            EntityPlayerMP entityplayer;
            String argSeconds = args[0];
            int seconds = argSeconds.equals("max") ? 1000000 : LOTRCommandFastTravelClock.parseIntWithMin(sender, args[0], 0);
            if (args.length >= 2) {
                entityplayer = LOTRCommandFastTravelClock.getPlayer(sender, args[1]);
            } else {
                entityplayer = LOTRCommandFastTravelClock.getCommandSenderAsPlayer(sender);
                if (entityplayer == null) {
                    throw new PlayerNotFoundException();
                }
            }
            int ticks = seconds * 20;
            LOTRLevelData.getData(entityplayer).setTimeSinceFTWithUpdate(ticks);
            LOTRCommandFastTravelClock.func_152373_a(sender, (ICommand)this, "commands.lotr.fastTravelClock.set", entityplayer.getCommandSenderName(), seconds, LOTRLevelData.getHMSTime_Seconds(seconds));
            return;
        }
        throw new WrongUsageException(this.getCommandUsage(sender));
    }

    public List addTabCompletionOptions(ICommandSender sender, String[] args) {
        if (args.length == 1) {
            return LOTRCommandFastTravelClock.getListOfStringsMatchingLastWord(args, "0", "max");
        }
        if (args.length == 2) {
            return LOTRCommandFastTravelClock.getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames());
        }
        return null;
    }

    public boolean isUsernameIndex(String[] args, int i) {
        return i == 1;
    }
}

