package lotr.client.gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.util.ResourceLocation;

public class LOTRGuiButtonLock extends GuiButton {
    private static ResourceLocation texture = new ResourceLocation("lotr:gui/widgets.png");

    public LOTRGuiButtonLock(int i, int j, int k) {
        super(i, j, k, 20, 20, "");
    }

    @Override
    public void drawButton(Minecraft mc, int i, int j) {
        if(this.visible) {
            mc.getTextureManager().bindTexture(texture);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            this.field_146123_n = i >= this.xPosition && j >= this.yPosition && i < this.xPosition + this.width && j < this.yPosition + this.height;
            int k = this.getHoverState(this.field_146123_n);
            this.drawTexturedModalRect(this.xPosition, this.yPosition, 0, 196 + k * 20, this.width, this.height);
            this.mouseDragged(mc, i, j);
        }
    }
}
