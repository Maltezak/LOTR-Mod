package lotr.common.command;

import lotr.common.world.LOTRWorldProvider;
import net.minecraft.command.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;

public class LOTRCommandTimeVanilla extends CommandTime {
    @Override
    protected void setTime(ICommandSender sender, int time) {
        for(WorldServer world : MinecraftServer.getServer().worldServers) {
            if(world.provider instanceof LOTRWorldProvider) continue;
            world.setWorldTime(time);
        }
    }

    @Override
    protected void addTime(ICommandSender sender, int time) {
        for(WorldServer world : MinecraftServer.getServer().worldServers) {
            if(world.provider instanceof LOTRWorldProvider) continue;
            world.setWorldTime(world.getWorldTime() + time);
        }
    }
}
