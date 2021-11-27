package lotr.client.gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;

public class LOTRGuiButtonCoinExchange extends GuiButton {
    public LOTRGuiButtonCoinExchange(int i, int j, int k) {
        super(i, j, k, 32, 17, "");
    }

    @Override
    public void drawButton(Minecraft mc, int i, int j) {
        if(this.visible) {
            mc.getTextureManager().bindTexture(LOTRGuiCoinExchange.guiTexture);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            this.field_146123_n = i >= this.xPosition && j >= this.yPosition && i < this.xPosition + this.width && j < this.yPosition + this.height;
            int k = this.getHoverState(this.field_146123_n);
            int u = 176 + this.id * this.width;
            int v = 0 + k * this.height;
            this.drawTexturedModalRect(this.xPosition, this.yPosition, u, v, this.width, this.height);
            this.mouseDragged(mc, i, j);
        }
    }
}
