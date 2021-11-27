package lotr.client.gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.util.ResourceLocation;

public class LOTRGuiButtonLeftRight extends GuiButton {
    private static ResourceLocation texture = new ResourceLocation("lotr:gui/widgets.png");
    private boolean leftOrRight;

    public LOTRGuiButtonLeftRight(int i, boolean flag, int j, int k, String s) {
        super(i, j, k, 120, 20, s);
        this.leftOrRight = flag;
    }

    @Override
    public void drawButton(Minecraft mc, int i, int j) {
        if(this.visible) {
            FontRenderer fontrenderer = mc.fontRenderer;
            mc.getTextureManager().bindTexture(texture);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            this.field_146123_n = i >= this.xPosition && j >= this.yPosition && i < this.xPosition + this.width && j < this.yPosition + this.height;
            int k = this.getHoverState(this.field_146123_n);
            this.drawTexturedModalRect(this.xPosition, this.yPosition, this.leftOrRight ? 0 : 136, k * 20, this.width, this.height);
            this.mouseDragged(mc, i, j);
            int l = 14737632;
            if(!this.enabled) {
                l = -6250336;
            }
            else if(this.field_146123_n) {
                l = 16777120;
            }
            if(this.leftOrRight) {
                this.drawCenteredString(fontrenderer, this.displayString, this.xPosition + 67, this.yPosition + (this.height - 8) / 2, l);
            }
            else {
                this.drawCenteredString(fontrenderer, this.displayString, this.xPosition + this.width - 67, this.yPosition + (this.height - 8) / 2, l);
            }
        }
    }
}
