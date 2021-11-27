package lotr.common.command;

import java.util.*;

import lotr.common.enchant.*;
import net.minecraft.command.*;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;

public class LOTRCommandEnchant extends CommandBase {
    @Override
    public String getCommandName() {
        return "lotrEnchant";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "commands.lotr.lotrEnchant.usage";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if(args.length >= 2) {
            EntityPlayerMP entityplayer = CommandBase.getPlayer(sender, args[0]);
            ItemStack itemstack = entityplayer.getHeldItem();
            if(itemstack == null) {
                throw new WrongUsageException("commands.lotr.lotrEnchant.noItem");
            }
            String option = args[1];
            if(option.equals("add") && args.length >= 3) {
                String enchName = args[2];
                LOTREnchantment ench = LOTREnchantment.getEnchantmentByName(enchName);
                if(ench != null) {
                    if(!LOTREnchantmentHelper.hasEnchant(itemstack, ench) && ench.canApply(itemstack, false) && LOTREnchantmentHelper.checkEnchantCompatible(itemstack, ench)) {
                        LOTREnchantmentHelper.setHasEnchant(itemstack, ench);
                        CommandBase.func_152373_a(sender, this, "commands.lotr.lotrEnchant.add", enchName, entityplayer.getCommandSenderName(), itemstack.getDisplayName());
                        return;
                    }
                    throw new WrongUsageException("commands.lotr.lotrEnchant.cannotAdd", enchName, itemstack.getDisplayName());
                }
                throw new WrongUsageException("commands.lotr.lotrEnchant.unknown", enchName);
            }
            if(option.equals("remove") && args.length >= 3) {
                String enchName = args[2];
                LOTREnchantment ench = LOTREnchantment.getEnchantmentByName(enchName);
                if(ench != null) {
                    if(LOTREnchantmentHelper.hasEnchant(itemstack, ench)) {
                        LOTREnchantmentHelper.removeEnchant(itemstack, ench);
                        CommandBase.func_152373_a(sender, this, "commands.lotr.lotrEnchant.remove", enchName, entityplayer.getCommandSenderName(), itemstack.getDisplayName());
                        return;
                    }
                    throw new WrongUsageException("commands.lotr.lotrEnchant.cannotRemove", enchName, itemstack.getDisplayName());
                }
                throw new WrongUsageException("commands.lotr.lotrEnchant.unknown", enchName);
            }
            if(option.equals("clear")) {
                LOTREnchantmentHelper.clearEnchantsAndProgress(itemstack);
                CommandBase.func_152373_a(sender, this, "commands.lotr.lotrEnchant.clear", entityplayer.getCommandSenderName(), itemstack.getDisplayName());
                return;
            }
        }
        throw new WrongUsageException(this.getCommandUsage(sender));
    }

    @Override
    public List addTabCompletionOptions(ICommandSender sender, String[] args) {
        if(args.length == 1) {
            return CommandBase.getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames());
        }
        if(args.length == 2) {
            return CommandBase.getListOfStringsMatchingLastWord(args, "add", "remove", "clear");
        }
        if(args.length == 3) {
            ItemStack itemstack;
            if(args[1].equals("add")) {
                EntityPlayerMP entityplayer2 = CommandBase.getPlayer(sender, args[0]);
                ItemStack itemstack2 = entityplayer2.getHeldItem();
                if(itemstack2 != null) {
                    ArrayList<String> enchNames = new ArrayList<>();
                    for(LOTREnchantment ench : LOTREnchantment.allEnchantments) {
                        if(LOTREnchantmentHelper.hasEnchant(itemstack2, ench) || !ench.canApply(itemstack2, false) || !LOTREnchantmentHelper.checkEnchantCompatible(itemstack2, ench)) continue;
                        enchNames.add(ench.enchantName);
                    }
                    return CommandBase.getListOfStringsMatchingLastWord(args, enchNames.toArray(new String[0]));
                }
            }
            else if(args[1].equals("remove") && (itemstack = (CommandBase.getPlayer(sender, args[0])).getHeldItem()) != null) {
                ArrayList<String> enchNames = new ArrayList<>();
                for(LOTREnchantment ench : LOTREnchantment.allEnchantments) {
                    if(!LOTREnchantmentHelper.hasEnchant(itemstack, ench)) continue;
                    enchNames.add(ench.enchantName);
                }
                return CommandBase.getListOfStringsMatchingLastWord(args, enchNames.toArray(new String[0]));
            }
        }
        return null;
    }

    @Override
    public boolean isUsernameIndex(String[] args, int i) {
        return i == 1;
    }
}
