package lotr.common.command;

import java.util.List;

import lotr.common.LOTRDate;
import net.minecraft.command.*;
import net.minecraft.util.ChatComponentTranslation;

public class LOTRCommandDate extends CommandBase {
    @Override
    public String getCommandName() {
        return "lotrDate";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "commands.lotr.lotrDate.usage";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if(args.length >= 1 && args[0].equals("get")) {
            int date = LOTRDate.ShireReckoning.currentDay;
            String dateName = LOTRDate.ShireReckoning.getShireDate().getDateName(false);
            ChatComponentTranslation message = new ChatComponentTranslation("commands.lotr.lotrDate.get", date, dateName);
            sender.addChatMessage(message);
            return;
        }
        if(args.length >= 2) {
            int newDate = LOTRDate.ShireReckoning.currentDay;
            if(args[0].equals("set")) {
                newDate = CommandBase.parseInt(sender, args[1]);
            }
            else if(args[0].equals("add")) {
                int date = CommandBase.parseInt(sender, args[1]);
                newDate += date;
            }
            if(Math.abs(newDate) > 1000000) {
                throw new WrongUsageException("commands.lotr.lotrDate.outOfBounds");
            }
            LOTRDate.setDate(newDate);
            String dateName = LOTRDate.ShireReckoning.getShireDate().getDateName(false);
            CommandBase.func_152373_a(sender, this, "commands.lotr.lotrDate.set", newDate, dateName);
            return;
        }
        throw new WrongUsageException(this.getCommandUsage(sender));
    }

    @Override
    public List addTabCompletionOptions(ICommandSender sender, String[] args) {
        if(args.length == 1) {
            return CommandBase.getListOfStringsMatchingLastWord(args, "get", "set", "add");
        }
        return null;
    }

    @Override
    public boolean isUsernameIndex(String[] args, int i) {
        return false;
    }
}
