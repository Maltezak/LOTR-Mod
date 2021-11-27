package lotr.client.gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;

public class LOTRGuiButtonRedBook extends GuiButton {
    public LOTRGuiButtonRedBook(int i, int x, int y, int w, int h, String s) {
        super(i, x, y, w, h, s);
    }

    @Override
    public void drawButton(Minecraft mc, int i, int j) {
        if(this.visible) {
            FontRenderer fontrenderer = mc.fontRenderer;
            mc.getTextureManager().bindTexture(LOTRGuiRedBook.guiTexture);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            this.field_146123_n = i >= this.xPosition && j >= this.yPosition && i < this.xPosition + this.width && j < this.yPosition + this.height;
            int k = this.getHoverState(this.field_146123_n);
            Gui.func_146110_a(this.xPosition, this.yPosition, 170.0f, 256 + k * 20, this.width, this.height, 512.0f, 512.0f);
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            Gui.func_146110_a(this.xPosition, this.yPosition, 170.0f, 316.0f, this.width / 2, this.height, 512.0f, 512.0f);
            Gui.func_146110_a(this.xPosition + this.width / 2, this.yPosition, 370 - this.width / 2, 316.0f, this.width / 2, this.height, 512.0f, 512.0f);
            this.mouseDragged(mc, i, j);
            int color = 8019267;
            if(!this.enabled) {
                color = 5521198;
            }
            else if(this.field_146123_n) {
                color = 8019267;
            }
            fontrenderer.drawString(this.displayString, this.xPosition + this.width / 2 - fontrenderer.getStringWidth(this.displayString) / 2, this.yPosition + (this.height - 8) / 2, color);
        }
    }
}
