package lotr.common.command;

import java.util.List;

import lotr.common.LOTRLevelData;
import net.minecraft.command.*;

public class LOTRCommandWaypointCooldown
extends CommandBase {
    public static int MAX_COOLDOWN = 86400;

    public String getCommandName() {
        return "wpCooldown";
    }

    public int getRequiredPermissionLevel() {
        return 2;
    }

    public String getCommandUsage(ICommandSender sender) {
        return "commands.lotr.wpCooldown.usage";
    }

    public void processCommand(ICommandSender sender, String[] args) {
        String function = null;
        int cooldown = -1;
        if (args.length == 1) {
            function = "max";
            cooldown = LOTRCommandWaypointCooldown.parseIntBounded(sender, args[0], 0, MAX_COOLDOWN);
        } else if (args.length >= 2) {
            function = args[0];
            cooldown = LOTRCommandWaypointCooldown.parseIntBounded(sender, args[1], 0, MAX_COOLDOWN);
        }
        if (function != null && cooldown >= 0) {
            int max = LOTRLevelData.getWaypointCooldownMax();
            int min = LOTRLevelData.getWaypointCooldownMin();
            if (function.equals("max")) {
                boolean updatedMin = false;
                max = cooldown;
                if (max < min) {
                    min = max;
                    updatedMin = true;
                }
                LOTRLevelData.setWaypointCooldown(max, min);
                LOTRCommandWaypointCooldown.func_152373_a(sender, (ICommand)this, "commands.lotr.wpCooldown.setMax", max, LOTRLevelData.getHMSTime_Seconds(max));
                if (updatedMin) {
                    LOTRCommandWaypointCooldown.func_152373_a(sender, (ICommand)this, "commands.lotr.wpCooldown.updateMin", min);
                }
                return;
            }
            if (function.equals("min")) {
                boolean updatedMax = false;
                min = cooldown;
                if (min > max) {
                    max = min;
                    updatedMax = true;
                }
                LOTRLevelData.setWaypointCooldown(max, min);
                LOTRCommandWaypointCooldown.func_152373_a(sender, (ICommand)this, "commands.lotr.wpCooldown.setMin", min, LOTRLevelData.getHMSTime_Seconds(min));
                if (updatedMax) {
                    LOTRCommandWaypointCooldown.func_152373_a(sender, (ICommand)this, "commands.lotr.wpCooldown.updateMax", max);
                }
                return;
            }
        }
        throw new WrongUsageException(this.getCommandUsage(sender));
    }

    public List addTabCompletionOptions(ICommandSender sender, String[] args) {
        if (args.length == 1) {
            return LOTRCommandWaypointCooldown.getListOfStringsMatchingLastWord(args, "max", "min");
        }
        return null;
    }

    public boolean isUsernameIndex(String[] args, int i) {
        return false;
    }
}

