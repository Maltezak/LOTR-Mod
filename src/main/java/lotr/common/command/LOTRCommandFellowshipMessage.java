package lotr.common.command;

import java.util.*;

import lotr.common.*;
import lotr.common.fellowship.LOTRFellowship;
import net.minecraft.command.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;

public class LOTRCommandFellowshipMessage extends CommandBase {
    @Override
    public String getCommandName() {
        return "fmsg";
    }

    @Override
    public List getCommandAliases() {
        return Arrays.asList("fchat");
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        if(sender instanceof EntityPlayer) {
            return true;
        }
        return super.canCommandSenderUseCommand(sender);
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "commands.lotr.fmsg.usage";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        EntityPlayerMP entityplayer = CommandBase.getCommandSenderAsPlayer(sender);
        LOTRPlayerData playerData = LOTRLevelData.getData(entityplayer);
        if(args.length >= 1) {
            if(args[0].equals("bind") && args.length >= 2) {
                String fsName = (args = LOTRCommandFellowship.fixArgsForFellowship(args, 1, false))[1];
                LOTRFellowship fellowship = playerData.getFellowshipByName(fsName);
                if(fellowship != null && !fellowship.isDisbanded() && fellowship.containsPlayer(entityplayer.getUniqueID())) {
                    playerData.setChatBoundFellowship(fellowship);
                    ChatComponentTranslation notif = new ChatComponentTranslation("commands.lotr.fmsg.bind", fellowship.getName());
                    notif.getChatStyle().setColor(EnumChatFormatting.GRAY);
                    notif.getChatStyle().setItalic(true);
                    sender.addChatMessage(notif);
                    return;
                }
                throw new WrongUsageException("commands.lotr.fmsg.notFound", fsName);
            }
            if(args[0].equals("unbind")) {
                LOTRFellowship preBoundFellowship = playerData.getChatBoundFellowship();
                playerData.setChatBoundFellowshipID(null);
                ChatComponentTranslation notif = new ChatComponentTranslation("commands.lotr.fmsg.unbind", preBoundFellowship.getName());
                notif.getChatStyle().setColor(EnumChatFormatting.GRAY);
                notif.getChatStyle().setItalic(true);
                sender.addChatMessage(notif);
                return;
            }
            LOTRFellowship fellowship = null;
            int msgStartIndex = 0;
            if(args[0].startsWith("\"")) {
                String fsName = (args = LOTRCommandFellowship.fixArgsForFellowship(args, 0, false))[0];
                fellowship = playerData.getFellowshipByName(fsName);
                if(fellowship == null) {
                    throw new WrongUsageException("commands.lotr.fmsg.notFound", fsName);
                }
                msgStartIndex = 1;
            }
            if(fellowship == null) {
                fellowship = playerData.getChatBoundFellowship();
                if(fellowship == null) {
                    throw new WrongUsageException("commands.lotr.fmsg.boundNone");
                }
                if(fellowship.isDisbanded() || !fellowship.containsPlayer(entityplayer.getUniqueID())) {
                    throw new WrongUsageException("commands.lotr.fmsg.boundNotMember", fellowship.getName());
                }
            }
            if(fellowship != null) {
                IChatComponent message = CommandBase.func_147176_a(sender, args, msgStartIndex, false);
                fellowship.sendFellowshipMessage(entityplayer, message.getUnformattedText());
                return;
            }
        }
        throw new WrongUsageException(this.getCommandUsage(sender));
    }

    @Override
    public List addTabCompletionOptions(ICommandSender sender, String[] args) {
        LOTRPlayerData playerData = LOTRLevelData.getData(CommandBase.getCommandSenderAsPlayer(sender));
        String[] argsOriginal = Arrays.copyOf(args, args.length);
        if(args.length >= 2 && args[0].equals("bind")) {
            args = LOTRCommandFellowship.fixArgsForFellowship(args, 1, true);
            return LOTRCommandFellowship.listFellowshipsMatchingLastWord(args, argsOriginal, 1, playerData, false);
        }
        if(args.length >= 1) {
            args = LOTRCommandFellowship.fixArgsForFellowship(args, 0, true);
            ArrayList<String> matches = new ArrayList<>();
            if(args.length == 1 && !argsOriginal[0].startsWith("\"")) {
                matches.addAll(CommandBase.getListOfStringsMatchingLastWord(args, "bind", "unbind"));
            }
            matches.addAll(LOTRCommandFellowship.listFellowshipsMatchingLastWord(args, argsOriginal, 0, playerData, false));
            return matches;
        }
        return null;
    }

    @Override
    public boolean isUsernameIndex(String[] args, int i) {
        return false;
    }
}
