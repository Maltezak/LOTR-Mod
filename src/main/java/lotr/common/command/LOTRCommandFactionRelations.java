package lotr.common.command;

import java.util.List;

import lotr.common.fac.*;
import net.minecraft.command.*;

public class LOTRCommandFactionRelations extends CommandBase {
    @Override
    public String getCommandName() {
        return "facRelations";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "commands.lotr.facRelations.usage";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if(args.length >= 1) {
            String function = args[0];
            if(function.equals("set")) {
                if(args.length >= 4) {
                    LOTRFaction fac1 = LOTRFaction.forName(args[1]);
                    if(fac1 == null) {
                        throw new WrongUsageException("commands.lotr.alignment.noFaction", args[1]);
                    }
                    LOTRFaction fac2 = LOTRFaction.forName(args[2]);
                    if(fac2 == null) {
                        throw new WrongUsageException("commands.lotr.alignment.noFaction", args[2]);
                    }
                    LOTRFactionRelations.Relation relation = LOTRFactionRelations.Relation.forName(args[3]);
                    if(relation == null) {
                        throw new WrongUsageException("commands.lotr.facRelations.noRelation", args[3]);
                    }
                    try {
                        LOTRFactionRelations.overrideRelations(fac1, fac2, relation);
                        CommandBase.func_152373_a(sender, this, "commands.lotr.facRelations.set", fac1.factionName(), fac2.factionName(), relation.getDisplayName());
                        return;
                    }
                    catch(IllegalArgumentException e) {
                        throw new WrongUsageException("commands.lotr.facRelations.error", e.getMessage());
                    }
                }
            }
            else if(function.equals("reset")) {
                LOTRFactionRelations.resetAllRelations();
                CommandBase.func_152373_a(sender, this, "commands.lotr.facRelations.reset");
                return;
            }
        }
        throw new WrongUsageException(this.getCommandUsage(sender));
    }

    @Override
    public List addTabCompletionOptions(ICommandSender sender, String[] args) {
        if(args.length == 1) {
            return CommandBase.getListOfStringsMatchingLastWord(args, "set", "reset");
        }
        if(args.length == 2 || args.length == 3) {
            List<String> list = LOTRFaction.getPlayableAlignmentFactionNames();
            return CommandBase.getListOfStringsMatchingLastWord(args, list.toArray(new String[0]));
        }
        if(args.length == 4) {
            List<String> list = LOTRFactionRelations.Relation.listRelationNames();
            return CommandBase.getListOfStringsMatchingLastWord(args, list.toArray(new String[0]));
        }
        return null;
    }

    @Override
    public boolean isUsernameIndex(String[] args, int i) {
        return false;
    }
}
