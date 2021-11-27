package lotr.common.command;

import java.util.*;

import lotr.common.LOTRSpawnDamping;
import net.minecraft.command.*;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class LOTRCommandSpawnDamping extends CommandBase {
    @Override
    public String getCommandName() {
        return "spawnDamping";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "commands.lotr.spawnDamping.usage";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if(args.length >= 1) {
            String option = args[0];
            if(option.equals("reset")) {
                LOTRSpawnDamping.resetAll();
                CommandBase.func_152373_a(sender, this, "commands.lotr.spawnDamping.reset");
                return;
            }
            if(args.length >= 2) {
                String type = args[1];
                if(!type.equals(LOTRSpawnDamping.TYPE_NPC) && EnumCreatureType.valueOf(type) == null) {
                    throw new WrongUsageException("commands.lotr.spawnDamping.noType", type);
                }
                if(option.equals("set") && args.length >= 3) {
                    float damping = (float) CommandBase.parseDoubleBounded(sender, args[2], 0.0, 1.0);
                    LOTRSpawnDamping.setSpawnDamping(type, damping);
                    CommandBase.func_152373_a(sender, this, "commands.lotr.spawnDamping.set", type, Float.valueOf(damping));
                    return;
                }
                if(option.equals("calc")) {
                    World world = sender.getEntityWorld();
                    int dim = world.provider.dimensionId;
                    String dimName = world.provider.getDimensionName();
                    float damping = LOTRSpawnDamping.getSpawnDamping(type);
                    int players = world.playerEntities.size();
                    int expectedChunks = 196;
                    int baseCap = LOTRSpawnDamping.getBaseSpawnCapForInfo(type, world);
                    int cap = LOTRSpawnDamping.getSpawnCap(type, baseCap, players);
                    int capXPlayers = cap * players;
                    ChatComponentTranslation msg = new ChatComponentTranslation("commands.lotr.spawnDamping.calc", dim, dimName, type, Float.valueOf(damping), players, expectedChunks, cap, baseCap, capXPlayers);
                    msg.getChatStyle().setColor(EnumChatFormatting.GREEN);
                    sender.addChatMessage(msg);
                    return;
                }
            }
        }
        throw new WrongUsageException(this.getCommandUsage(sender));
    }

    @Override
    public List addTabCompletionOptions(ICommandSender sender, String[] args) {
        if(args.length == 1) {
            return CommandBase.getListOfStringsMatchingLastWord(args, "set", "calc", "reset");
        }
        if(args.length == 2 && (args[0].equals("set") || args[0].equals("calc"))) {
            ArrayList<String> types = new ArrayList<>();
            for(EnumCreatureType type : EnumCreatureType.values()) {
                types.add(type.name());
            }
            types.add(LOTRSpawnDamping.TYPE_NPC);
            return CommandBase.getListOfStringsMatchingLastWord(args, types.toArray(new String[0]));
        }
        return null;
    }

    @Override
    public boolean isUsernameIndex(String[] args, int i) {
        return false;
    }
}
