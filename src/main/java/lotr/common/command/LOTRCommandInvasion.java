package lotr.common.command;

import java.util.List;

import lotr.common.entity.LOTREntityInvasionSpawner;
import lotr.common.world.spawning.LOTRInvasions;
import net.minecraft.command.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class LOTRCommandInvasion
extends CommandBase {
    public String getCommandName() {
        return "invasion";
    }

    public int getRequiredPermissionLevel() {
        return 2;
    }

    public String getCommandUsage(ICommandSender sender) {
        return "commands.lotr.invasion.usage";
    }

    public void processCommand(ICommandSender sender, String[] args) {
        EntityPlayer player = sender instanceof EntityPlayer ? (EntityPlayer)sender : null;
        World world = sender.getEntityWorld();
        if (args.length >= 1) {
            String typeName = args[0];
            LOTRInvasions type = LOTRInvasions.forName(typeName);
            if (type != null) {
                double posX = sender.getPlayerCoordinates().posX + 0.5;
                double posY = sender.getPlayerCoordinates().posY;
                double posZ = sender.getPlayerCoordinates().posZ + 0.5;
                if (args.length >= 4) {
                    posX = LOTRCommandInvasion.func_110666_a(sender, posX, args[1]);
                    posY = LOTRCommandInvasion.func_110666_a(sender, posY, args[2]);
                    posZ = LOTRCommandInvasion.func_110666_a(sender, posZ, args[3]);
                } else {
                    posY += 3.0;
                }
                int size = -1;
                if (args.length >= 5) {
                    size = LOTRCommandInvasion.parseIntBounded(sender, args[4], 0, 10000);
                }
                LOTREntityInvasionSpawner invasion = new LOTREntityInvasionSpawner(world);
                invasion.setInvasionType(type);
                invasion.setLocationAndAngles(posX, posY, posZ, 0.0f, 0.0f);
                world.spawnEntityInWorld(invasion);
                invasion.selectAppropriateBonusFactions();
                invasion.startInvasion(player, size);
                LOTRCommandInvasion.func_152373_a(sender, (ICommand)this, "commands.lotr.invasion.start", type.invasionName(), invasion.getInvasionSize(), posX, posY, posZ);
                return;
            }
            throw new WrongUsageException("commands.lotr.invasion.noType", typeName);
        }
        throw new WrongUsageException(this.getCommandUsage(sender));
    }

    public List addTabCompletionOptions(ICommandSender sender, String[] args) {
        if (args.length == 1) {
            return LOTRCommandInvasion.getListOfStringsMatchingLastWord(args, LOTRInvasions.listInvasionNames());
        }
        return null;
    }

    public boolean isUsernameIndex(String[] args, int i) {
        return false;
    }
}

