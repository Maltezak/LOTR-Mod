package lotr.common.network;

import com.google.common.base.Charsets;

import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.ByteBuf;
import lotr.common.*;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class LOTRPacketNPCSpeech
implements IMessage {
    private int entityID;
    private String speech;
    private boolean forceChatMsg;

    public LOTRPacketNPCSpeech() {
    }

    public LOTRPacketNPCSpeech(int id, String s, boolean forceChat) {
        this.entityID = id;
        this.speech = s;
        this.forceChatMsg = forceChat;
    }

    public void toBytes(ByteBuf data) {
        data.writeInt(this.entityID);
        byte[] speechBytes = this.speech.getBytes(Charsets.UTF_8);
        data.writeInt(speechBytes.length);
        data.writeBytes(speechBytes);
        data.writeBoolean(this.forceChatMsg);
    }

    public void fromBytes(ByteBuf data) {
        this.entityID = data.readInt();
        int length = data.readInt();
        this.speech = data.readBytes(length).toString(Charsets.UTF_8);
        this.forceChatMsg = data.readBoolean();
    }

    public static class Handler
    implements IMessageHandler<LOTRPacketNPCSpeech, IMessage> {
        public IMessage onMessage(LOTRPacketNPCSpeech packet, MessageContext context) {
            World world = LOTRMod.proxy.getClientWorld();
            EntityPlayer entityplayer = LOTRMod.proxy.getClientPlayer();
            Entity entity = world.getEntityByID(packet.entityID);
            if (entity instanceof LOTREntityNPC) {
                LOTREntityNPC npc = (LOTREntityNPC)entity;
                if (LOTRConfig.immersiveSpeech) {
                    LOTRMod.proxy.clientReceiveSpeech(npc, packet.speech);
                }
                if (!LOTRConfig.immersiveSpeech || LOTRConfig.immersiveSpeechChatLog || packet.forceChatMsg) {
                    String name = npc.getCommandSenderName();
                    String message = EnumChatFormatting.YELLOW + "<" + name + "> " + EnumChatFormatting.WHITE + packet.speech;
                    entityplayer.addChatMessage(new ChatComponentText(message));
                }
            }
            return null;
        }
    }

}

