package lotr.client.gui;

import java.util.ArrayList;
import java.util.regex.Pattern;

import org.lwjgl.opengl.GL11;

import lotr.client.LOTRTickHandlerClient;
import lotr.common.LOTRGuiMessageTypes;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.*;

public class LOTRGuiMessage extends LOTRGuiScreenBase {
    private static ResourceLocation guiTexture = new ResourceLocation("lotr:gui/message.png");
    private LOTRGuiMessageTypes type;
    public int xSize = 240;
    public int ySize = 160;
    private int border = 10;
    private int guiLeft;
    private int guiTop;
    private GuiButton buttonDismiss;
    private int buttonTimer = 60;

    public LOTRGuiMessage(LOTRGuiMessageTypes t) {
        this.type = t;
    }

    @Override
    public void initGui() {
        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;
        this.buttonDismiss = new LOTRGuiButtonRedBook(0, this.guiLeft + this.xSize / 2 - 40, this.guiTop + this.ySize + 20, 80, 20, StatCollector.translateToLocal("lotr.gui.message.dismiss"));
        this.buttonList.add(this.buttonDismiss);
    }

    @Override
    public void drawScreen(int i, int j, float f) {
        this.drawDefaultBackground();
        this.mc.getTextureManager().bindTexture(guiTexture);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
        String msg = this.type.getMessage();
        int pageWidth = this.xSize - this.border * 2;
        String[] splitNewline = msg.split(Pattern.quote("\\n"));
        ArrayList<String> msgLines = new ArrayList();
        for(String line : splitNewline) {
            msgLines.addAll(this.fontRendererObj.listFormattedStringToWidth(line, pageWidth));
        }
        int x = this.guiLeft + this.border;
        int y = this.guiTop + this.border;
        for(String line : msgLines) {
            this.fontRendererObj.drawString(line, x, y, 8019267);
            y += this.fontRendererObj.FONT_HEIGHT;
        }
        String s = StatCollector.translateToLocal("lotr.gui.message.notDisplayedAgain");
        this.drawCenteredString(s, this.guiLeft + this.xSize / 2, this.guiTop + this.ySize - this.border / 2 - this.fontRendererObj.FONT_HEIGHT, 9666921);
        if(this.type == LOTRGuiMessageTypes.ALIGN_DRAIN) {
            int numIcons = 3;
            int iconGap = 40;
            for(int l = 0; l < numIcons; ++l) {
                int iconX = this.guiLeft + this.xSize / 2;
                iconX -= (numIcons - 1) * iconGap / 2;
                int iconY = this.guiTop + this.border + 14;
                int num = l + 1;
                LOTRTickHandlerClient.renderAlignmentDrain(this.mc, iconX += l * iconGap - 8, iconY, num);
            }
        }
        if(this.buttonTimer > 0) {
            --this.buttonTimer;
        }
        this.buttonDismiss.enabled = this.buttonTimer == 0;
        super.drawScreen(i, j, f);
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if(button.enabled && button == this.buttonDismiss) {
            this.mc.thePlayer.closeScreen();
        }
    }

    @Override
    protected void keyTyped(char c, int i) {
    }
}
