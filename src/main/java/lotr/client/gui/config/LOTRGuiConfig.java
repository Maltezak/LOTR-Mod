package lotr.client.gui.config;

import cpw.mods.fml.client.config.GuiConfig;
import lotr.common.LOTRConfig;
import net.minecraft.client.gui.GuiScreen;

public class LOTRGuiConfig extends GuiConfig {
    public LOTRGuiConfig(GuiScreen parent) {
        super(parent, LOTRConfig.getConfigElements(), "lotr", false, false, GuiConfig.getAbridgedConfigPath(LOTRConfig.config.toString()));
    }
}
