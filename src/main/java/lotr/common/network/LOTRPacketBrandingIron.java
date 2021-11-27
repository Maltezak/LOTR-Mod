package lotr.common.network;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Charsets;

import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.ByteBuf;
import lotr.common.item.LOTRItemBrandingIron;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;

public class LOTRPacketBrandingIron implements IMessage {
    private String brandName;

    public LOTRPacketBrandingIron() {
    }

    public LOTRPacketBrandingIron(String s) {
        this.brandName = s;
    }

    @Override
    public void toBytes(ByteBuf data) {
        if(StringUtils.isBlank(this.brandName)) {
            data.writeInt(-1);
        }
        else {
            byte[] brandBytes = this.brandName.getBytes(Charsets.UTF_8);
            data.writeInt(brandBytes.length);
            data.writeBytes(brandBytes);
        }
    }

    @Override
    public void fromBytes(ByteBuf data) {
        int length = data.readInt();
        if(length > -1) {
            this.brandName = data.readBytes(length).toString(Charsets.UTF_8);
        }
    }

    public static class Handler implements IMessageHandler<LOTRPacketBrandingIron, IMessage> {
        @Override
        public IMessage onMessage(LOTRPacketBrandingIron packet, MessageContext context) {
            EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
            ItemStack itemstack = entityplayer.getCurrentEquippedItem();
            if(itemstack != null && itemstack.getItem() instanceof LOTRItemBrandingIron) {
                String brandName = packet.brandName;
                if(!StringUtils.isBlank(brandName = LOTRItemBrandingIron.trimAcceptableBrandName(brandName)) && !LOTRItemBrandingIron.hasBrandName(itemstack)) {
                    LOTRItemBrandingIron.setBrandName(itemstack, brandName);
                }
            }
            return null;
        }
    }

}
