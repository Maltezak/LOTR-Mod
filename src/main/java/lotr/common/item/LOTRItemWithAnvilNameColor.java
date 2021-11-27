package lotr.common.item;

import net.minecraft.item.Item;
import net.minecraft.util.EnumChatFormatting;

public class LOTRItemWithAnvilNameColor
extends Item
implements AnvilNameColorProvider {
    private final EnumChatFormatting anvilNameColor;

    public LOTRItemWithAnvilNameColor(EnumChatFormatting color) {
        this.anvilNameColor = color;
    }

    @Override
    public EnumChatFormatting getAnvilNameColor() {
        return this.anvilNameColor;
    }
}

