package lotr.common.command;

import java.util.List;

import lotr.common.LOTRLevelData;
import net.minecraft.command.*;

public class LOTRCommandEnableAlignmentZones extends CommandBase {
    @Override
    public String getCommandName() {
        return "alignmentZones";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "commands.lotr.alignmentZones.usage";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if(args.length >= 1) {
            String flag = args[0];
            if(flag.equals("enable")) {
                LOTRLevelData.setEnableAlignmentZones(true);
                CommandBase.func_152373_a(sender, this, "commands.lotr.alignmentZones.enable");
                return;
            }
            if(flag.equals("disable")) {
                LOTRLevelData.setEnableAlignmentZones(false);
                CommandBase.func_152373_a(sender, this, "commands.lotr.alignmentZones.disable");
                return;
            }
        }
        throw new WrongUsageException(this.getCommandUsage(sender));
    }

    @Override
    public List addTabCompletionOptions(ICommandSender sender, String[] args) {
        if(args.length == 1) {
            return CommandBase.getListOfStringsMatchingLastWord(args, "enable", "disable");
        }
        return null;
    }

    @Override
    public boolean isUsernameIndex(String[] args, int i) {
        return false;
    }
}
