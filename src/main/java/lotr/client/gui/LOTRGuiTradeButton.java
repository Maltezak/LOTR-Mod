package lotr.client.gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class LOTRGuiTradeButton
extends GuiButton {
    public LOTRGuiTradeButton(int i, int j, int k) {
        super(i, j, k, 18, 18, "Trade");
    }

    public void drawButton(Minecraft mc, int i, int j) {
        mc.getTextureManager().bindTexture(LOTRGuiTrade.guiTexture);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.field_146123_n = i >= this.xPosition && j >= this.yPosition && i < this.xPosition + this.width && j < this.yPosition + this.height;
        int hoverState = this.getHoverState(this.field_146123_n);
        LOTRGuiTradeButton.func_146110_a(this.xPosition, this.yPosition, 176.0f, hoverState * 18, this.width, this.height, 512.0f, 512.0f);
        this.mouseDragged(mc, i, j);
    }
}

