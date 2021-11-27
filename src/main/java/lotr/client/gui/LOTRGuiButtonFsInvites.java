package lotr.client.gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;

public class LOTRGuiButtonFsInvites extends GuiButton {
    public LOTRGuiButtonFsInvites(int i, int x, int y, String s) {
        super(i, x, y, 16, 16, s);
    }

    @Override
    public void drawButton(Minecraft mc, int i, int j) {
        if(this.visible) {
            FontRenderer fontrenderer = mc.fontRenderer;
            mc.getTextureManager().bindTexture(LOTRGuiFellowships.iconsTextures);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            this.field_146123_n = i >= this.xPosition && j >= this.yPosition && i < this.xPosition + this.width && j < this.yPosition + this.height;
            this.drawTexturedModalRect(this.xPosition, this.yPosition, 80, 0 + (this.field_146123_n ? this.height : 0), this.width, this.height);
            this.mouseDragged(mc, i, j);
            int color = 0;
            fontrenderer.drawString(this.displayString, this.xPosition + this.width / 2 - fontrenderer.getStringWidth(this.displayString) / 2, this.yPosition + (this.height - 8) / 2, color);
        }
    }
}
