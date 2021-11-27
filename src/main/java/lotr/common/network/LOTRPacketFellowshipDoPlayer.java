package lotr.common.network;

import java.util.UUID;

import com.google.common.base.Charsets;
import com.mojang.authlib.GameProfile;

import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.ByteBuf;
import lotr.common.*;
import lotr.common.fellowship.*;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

public class LOTRPacketFellowshipDoPlayer extends LOTRPacketFellowshipDo {
    private String subjectUsername;
    private PlayerFunction function;

    public LOTRPacketFellowshipDoPlayer() {
    }

    public LOTRPacketFellowshipDoPlayer(LOTRFellowshipClient fs, String name, PlayerFunction f) {
        super(fs);
        this.subjectUsername = name;
        this.function = f;
    }

    @Override
    public void toBytes(ByteBuf data) {
        super.toBytes(data);
        byte[] nameBytes = this.subjectUsername.getBytes(Charsets.UTF_8);
        data.writeByte(nameBytes.length);
        data.writeBytes(nameBytes);
        data.writeByte(this.function.ordinal());
    }

    @Override
    public void fromBytes(ByteBuf data) {
        super.fromBytes(data);
        byte nameLength = data.readByte();
        ByteBuf nameBytes = data.readBytes(nameLength);
        this.subjectUsername = nameBytes.toString(Charsets.UTF_8);
        this.function = PlayerFunction.values()[data.readByte()];
    }

    public UUID getSubjectPlayerUUID() {
        GameProfile profile = MinecraftServer.getServer().func_152358_ax().func_152655_a(this.subjectUsername);
        if(profile != null && profile.getId() != null) {
            return profile.getId();
        }
        return null;
    }

    public static class Handler implements IMessageHandler<LOTRPacketFellowshipDoPlayer, IMessage> {
        @Override
        public IMessage onMessage(LOTRPacketFellowshipDoPlayer packet, MessageContext context) {
            EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
            LOTRFellowship fellowship = packet.getFellowship();
            UUID subjectPlayer = packet.getSubjectPlayerUUID();
            if(fellowship != null && subjectPlayer != null) {
                LOTRPlayerData playerData = LOTRLevelData.getData(entityplayer);
                if(packet.function == PlayerFunction.INVITE) {
                    playerData.invitePlayerToFellowship(fellowship, subjectPlayer);
                }
                else if(packet.function == PlayerFunction.REMOVE) {
                    playerData.removePlayerFromFellowship(fellowship, subjectPlayer);
                }
                else if(packet.function == PlayerFunction.TRANSFER) {
                    playerData.transferFellowship(fellowship, subjectPlayer);
                }
                else if(packet.function == PlayerFunction.OP) {
                    playerData.setFellowshipAdmin(fellowship, subjectPlayer, true);
                }
                else if(packet.function == PlayerFunction.DEOP) {
                    playerData.setFellowshipAdmin(fellowship, subjectPlayer, false);
                }
            }
            return null;
        }
    }

    public enum PlayerFunction {
        INVITE, REMOVE, TRANSFER, OP, DEOP;

    }

}
