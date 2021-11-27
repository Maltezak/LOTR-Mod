package lotr.client.gui;

import org.lwjgl.opengl.GL11;

import lotr.common.*;
import lotr.common.network.*;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.StatCollector;

public class LOTRGuiOptions extends LOTRGuiMenuBase {
    private LOTRGuiButtonOptions buttonFriendlyFire;
    private LOTRGuiButtonOptions buttonHiredDeathMessages;
    private LOTRGuiButtonOptions buttonAlignment;
    private LOTRGuiButtonOptions buttonMapLocation;
    private LOTRGuiButtonOptions buttonConquest;
    private LOTRGuiButtonOptions buttonFeminineRank;

    @Override
    public void initGui() {
        super.initGui();
        int buttonX = this.guiLeft + this.xSize / 2 - 100;
        int buttonY = this.guiTop + 40;
        this.buttonFriendlyFire = new LOTRGuiButtonOptions(0, buttonX, buttonY, 200, 20, "lotr.gui.options.friendlyFire");
        this.buttonList.add(this.buttonFriendlyFire);
        this.buttonHiredDeathMessages = new LOTRGuiButtonOptions(1, buttonX, buttonY + 24, 200, 20, "lotr.gui.options.hiredDeathMessages");
        this.buttonList.add(this.buttonHiredDeathMessages);
        this.buttonAlignment = new LOTRGuiButtonOptions(2, buttonX, buttonY + 48, 200, 20, "lotr.gui.options.showAlignment");
        this.buttonList.add(this.buttonAlignment);
        this.buttonMapLocation = new LOTRGuiButtonOptions(3, buttonX, buttonY + 72, 200, 20, "lotr.gui.options.showMapLocation");
        this.buttonList.add(this.buttonMapLocation);
        this.buttonConquest = new LOTRGuiButtonOptions(5, buttonX, buttonY + 96, 200, 20, "lotr.gui.options.conquest");
        this.buttonList.add(this.buttonConquest);
        this.buttonFeminineRank = new LOTRGuiButtonOptions(4, buttonX, buttonY + 120, 200, 20, "lotr.gui.options.femRank");
        this.buttonList.add(this.buttonFeminineRank);
    }

    @Override
    public void drawScreen(int i, int j, float f) {
        this.drawDefaultBackground();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        String s = StatCollector.translateToLocal("lotr.gui.options.title");
        this.fontRendererObj.drawString(s, this.guiLeft + 100 - this.fontRendererObj.getStringWidth(s) / 2, this.guiTop - 30, 16777215);
        s = StatCollector.translateToLocal("lotr.gui.options.worldSettings");
        this.fontRendererObj.drawString(s, this.guiLeft + 100 - this.fontRendererObj.getStringWidth(s) / 2, this.guiTop + 10, 16777215);
        LOTRPlayerData pd = LOTRLevelData.getData(this.mc.thePlayer);
        this.buttonFriendlyFire.setState(pd.getFriendlyFire());
        this.buttonHiredDeathMessages.setState(pd.getEnableHiredDeathMessages());
        this.buttonAlignment.setState(!pd.getHideAlignment());
        this.buttonMapLocation.setState(!pd.getHideMapLocation());
        this.buttonConquest.setState(pd.getEnableConquestKills());
        this.buttonFeminineRank.setState(pd.getFemRankOverride());
        super.drawScreen(i, j, f);
        for(Object element : this.buttonList) {
            GuiButton button = (GuiButton) element;
            if(!(button instanceof LOTRGuiButtonOptions)) continue;
            ((LOTRGuiButtonOptions) button).drawTooltip(this.mc, i, j);
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if(button.enabled) {
            if(button instanceof LOTRGuiButtonOptions) {
                LOTRPacketSetOption packet = new LOTRPacketSetOption(button.id);
                LOTRPacketHandler.networkWrapper.sendToServer(packet);
            }
            else {
                super.actionPerformed(button);
            }
        }
    }
}
