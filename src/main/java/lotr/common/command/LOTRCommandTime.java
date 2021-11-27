package lotr.common.command;

import java.util.List;

import lotr.common.LOTRTime;
import net.minecraft.command.*;

public class LOTRCommandTime extends CommandBase {
    @Override
    public String getCommandName() {
        return "lotr_time";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "commands.lotr.time.usage";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if(args.length >= 2) {
            if(args[0].equals("set")) {
                long time;
                time = args[1].equals("day") ? Math.round(LOTRTime.DAY_LENGTH * 0.03) : (args[1].equals("night") ? Math.round(LOTRTime.DAY_LENGTH * 0.6) : (long) CommandBase.parseIntWithMin(sender, args[1], 0));
                LOTRTime.setWorldTime(time);
                CommandBase.func_152373_a(sender, this, "commands.lotr.time.set", time);
                return;
            }
            if(args[0].equals("add")) {
                int time = CommandBase.parseIntWithMin(sender, args[1], 0);
                LOTRTime.addWorldTime(time);
                CommandBase.func_152373_a(sender, this, "commands.lotr.time.add", time);
                return;
            }
        }
        throw new WrongUsageException(this.getCommandUsage(sender));
    }

    @Override
    public List addTabCompletionOptions(ICommandSender sender, String[] args) {
        if(args.length == 1) {
            return CommandBase.getListOfStringsMatchingLastWord(args, "set", "add");
        }
        if(args.length == 2 && args[0].equals("set")) {
            return CommandBase.getListOfStringsMatchingLastWord(args, "day", "night");
        }
        return null;
    }
}
