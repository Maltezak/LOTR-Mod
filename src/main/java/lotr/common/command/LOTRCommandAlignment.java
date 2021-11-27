package lotr.common.command;

import java.util.*;

import lotr.common.LOTRLevelData;
import lotr.common.fac.LOTRFaction;
import net.minecraft.command.*;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

public class LOTRCommandAlignment extends CommandBase {
    @Override
    public String getCommandName() {
        return "alignment";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "commands.lotr.alignment.usage";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if(args.length >= 2) {
            List<LOTRFaction> factions = new ArrayList();
            if(args[1].equalsIgnoreCase("all")) {
                factions = LOTRFaction.getPlayableAlignmentFactions();
            }
            else {
                LOTRFaction faction = LOTRFaction.forName(args[1]);
                if(faction == null) {
                    throw new WrongUsageException("commands.lotr.alignment.noFaction", args[1]);
                }
                factions.add(faction);
            }
            if(args[0].equals("set")) {
                EntityPlayerMP entityplayer;
                float alignment = (float) CommandBase.parseDoubleBounded(sender, args[2], -10000.0, 10000.0);
                if(args.length >= 4) {
                    entityplayer = CommandBase.getPlayer(sender, args[3]);
                }
                else {
                    entityplayer = CommandBase.getCommandSenderAsPlayer(sender);
                    if(entityplayer == null) {
                        throw new PlayerNotFoundException();
                    }
                }
                for(LOTRFaction f : factions) {
                    LOTRLevelData.getData(entityplayer).setAlignmentFromCommand(f, alignment);
                    CommandBase.func_152373_a(sender, this, "commands.lotr.alignment.set", entityplayer.getCommandSenderName(), f.factionName(), Float.valueOf(alignment));
                }
                return;
            }
            if(args[0].equals("add")) {
                EntityPlayerMP entityplayer;
                float newAlignment;
                float alignment = (float) CommandBase.parseDouble(sender, args[2]);
                if(args.length >= 4) {
                    entityplayer = CommandBase.getPlayer(sender, args[3]);
                }
                else {
                    entityplayer = CommandBase.getCommandSenderAsPlayer(sender);
                    if(entityplayer == null) {
                        throw new PlayerNotFoundException();
                    }
                }
                HashMap<LOTRFaction, Float> newAlignments = new HashMap<>();
                for(LOTRFaction f : factions) {
                    newAlignment = LOTRLevelData.getData(entityplayer).getAlignment(f) + alignment;
                    if(newAlignment < -10000.0f) {
                        throw new WrongUsageException("commands.lotr.alignment.tooLow", Float.valueOf(-10000.0f));
                    }
                    if(newAlignment > 10000.0f) {
                        throw new WrongUsageException("commands.lotr.alignment.tooHigh", Float.valueOf(10000.0f));
                    }
                    newAlignments.put(f, newAlignment);
                }
                for(LOTRFaction f : factions) {
                    newAlignment = newAlignments.get(f);
                    LOTRLevelData.getData(entityplayer).addAlignmentFromCommand(f, alignment);
                    CommandBase.func_152373_a(sender, this, "commands.lotr.alignment.add", Float.valueOf(alignment), entityplayer.getCommandSenderName(), f.factionName());
                }
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
        if(args.length == 2) {
            List<String> list = LOTRFaction.getPlayableAlignmentFactionNames();
            list.add("all");
            return CommandBase.getListOfStringsMatchingLastWord(args, list.toArray(new String[0]));
        }
        if(args.length == 4) {
            return CommandBase.getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames());
        }
        return null;
    }

    @Override
    public boolean isUsernameIndex(String[] args, int i) {
        return i == 3;
    }
}
