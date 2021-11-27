package lotr.common.command;

import java.util.List;

import lotr.common.LOTRLevelData;
import net.minecraft.command.*;
import net.minecraft.entity.player.*;
import net.minecraft.server.MinecraftServer;

public class LOTRCommandPledgeCooldown
extends CommandBase {
    public String getCommandName() {
        return "pledgeCooldown";
    }

    public int getRequiredPermissionLevel() {
        return 2;
    }

    public String getCommandUsage(ICommandSender sender) {
        return "commands.lotr.pledgeCooldown.usage";
    }

    public void processCommand(ICommandSender sender, String[] args) {
        if (args.length >= 1) {
            EntityPlayerMP entityplayer;
            int cd = LOTRCommandPledgeCooldown.parseIntBounded(sender, args[0], 0, 10000000);
            if (args.length >= 2) {
                entityplayer = LOTRCommandPledgeCooldown.getPlayer(sender, args[1]);
            } else {
                entityplayer = LOTRCommandPledgeCooldown.getCommandSenderAsPlayer(sender);
                if (entityplayer == null) {
                    throw new PlayerNotFoundException();
                }
            }
            LOTRLevelData.getData(entityplayer).setPledgeBreakCooldown(cd);
            LOTRCommandPledgeCooldown.func_152373_a(sender, (ICommand)this, "commands.lotr.pledgeCooldown.set", entityplayer.getCommandSenderName(), cd, LOTRLevelData.getHMSTime_Ticks(cd));
            return;
        }
        throw new WrongUsageException(this.getCommandUsage(sender));
    }

    public List addTabCompletionOptions(ICommandSender sender, String[] args) {
        if (args.length == 2) {
            return LOTRCommandPledgeCooldown.getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames());
        }
        return null;
    }

    public boolean isUsernameIndex(String[] args, int i) {
        return i == 1;
    }
}

