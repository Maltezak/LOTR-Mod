package lotr.common.network;

import java.io.IOException;
import java.util.*;

import com.mojang.authlib.GameProfile;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.*;
import lotr.common.LOTRMod;
import net.minecraft.nbt.*;
import net.minecraft.network.PacketBuffer;

public class LOTRPacketUpdatePlayerLocations implements IMessage {
    private List<PlayerLocationInfo> playerLocations = new ArrayList<>();
    private static Map<UUID, byte[]> cachedProfileBytes = new HashMap<>();

    public void addPlayerLocation(GameProfile profile, double x, double z) {
        if(profile.isComplete()) {
            this.playerLocations.add(new PlayerLocationInfo(profile, x, z));
        }
    }

    @Override
    public void toBytes(ByteBuf data) {
        int players = this.playerLocations.size();
        data.writeShort(players);
        for(PlayerLocationInfo player : this.playerLocations) {
            GameProfile profile = player.playerProfile;
            UUID playerID = profile.getId();
            byte[] profileBytes = null;
            if(cachedProfileBytes.containsKey(playerID)) {
                profileBytes = cachedProfileBytes.get(playerID);
            }
            else {
                ByteBuf tempBuf = Unpooled.buffer();
                try {
                    NBTTagCompound profileData = new NBTTagCompound();
                    NBTUtil.func_152460_a(profileData, profile);
                    new PacketBuffer(tempBuf).writeNBTTagCompoundToBuffer(profileData);
                    byte[] tempBytes = tempBuf.array();
                    profileBytes = Arrays.copyOf(tempBytes, tempBytes.length);
                    cachedProfileBytes.put(playerID, profileBytes);
                }
                catch(IOException e) {
                    FMLLog.severe("Error writing player profile data");
                    e.printStackTrace();
                }
            }
            if(profileBytes == null) {
                data.writeShort(-1);
                continue;
            }
            byte[] copied = Arrays.copyOf(profileBytes, profileBytes.length);
            data.writeShort(copied.length);
            data.writeBytes(copied);
            data.writeDouble(player.posX);
            data.writeDouble(player.posZ);
        }
    }

    @Override
    public void fromBytes(ByteBuf data) {
        this.playerLocations.clear();
        int players = data.readShort();
        for(int l = 0; l < players; ++l) {
            short len = data.readShort();
            if(len < 0) continue;
            ByteBuf profileBytes = data.readBytes(len);
            GameProfile profile = null;
            try {
                NBTTagCompound profileData = new PacketBuffer(profileBytes).readNBTTagCompoundFromBuffer();
                profile = NBTUtil.func_152459_a(profileData);
            }
            catch(IOException e) {
                FMLLog.severe("Error reading player profile data");
                e.printStackTrace();
            }
            double posX = data.readDouble();
            double posZ = data.readDouble();
            if(profile == null) continue;
            PlayerLocationInfo playerInfo = new PlayerLocationInfo(profile, posX, posZ);
            this.playerLocations.add(playerInfo);
        }
    }

    public static class Handler implements IMessageHandler<LOTRPacketUpdatePlayerLocations, IMessage> {
        @Override
        public IMessage onMessage(LOTRPacketUpdatePlayerLocations packet, MessageContext context) {
            LOTRMod.proxy.clearMapPlayerLocations();
            for(PlayerLocationInfo info : packet.playerLocations) {
                LOTRMod.proxy.addMapPlayerLocation(info.playerProfile, info.posX, info.posZ);
            }
            return null;
        }
    }

    private static class PlayerLocationInfo {
        public final GameProfile playerProfile;
        public final double posX;
        public final double posZ;

        public PlayerLocationInfo(GameProfile profile, double x, double z) {
            this.playerProfile = profile;
            this.posX = x;
            this.posZ = z;
        }
    }

}
