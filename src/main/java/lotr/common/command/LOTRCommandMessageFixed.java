package lotr.common.command;

import lotr.common.LOTRConfig;
import net.minecraft.command.server.CommandMessage;

public class LOTRCommandMessageFixed extends CommandMessage {
    @Override
    public boolean isUsernameIndex(String[] args, int i) {
        if(LOTRConfig.preventMessageExploit) {
            return false;
        }
        return super.isUsernameIndex(args, i);
    }
}
