package lotr.client.gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;

public class LOTRGuiButtonBanner
extends GuiButton {
    public boolean activated;
    private int iconU;
    private int iconV;

    public LOTRGuiButtonBanner(int i, int x, int y, int u, int v) {
        super(i, x, y, 16, 16, "");
        this.iconU = u;
        this.iconV = v;
    }

    public void drawButton(Minecraft mc, int i, int j) {
        if (this.visible) {
            mc.getTextureManager().bindTexture(LOTRGuiBanner.bannerTexture);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            this.field_146123_n = i >= this.xPosition && j >= this.yPosition && i < this.xPosition + this.width && j < this.yPosition + this.height;
            int state = this.getHoverState(this.field_146123_n);
            this.drawTexturedModalRect(this.xPosition, this.yPosition, this.iconU + state % 2 * this.width, this.iconV + state / 2 * this.height, this.width, this.height);
            this.mouseDragged(mc, i, j);
        }
    }

    public int getHoverState(boolean mouseover) {
        return (this.activated ? 0 : 2) + (mouseover ? 1 : 0);
    }
}

