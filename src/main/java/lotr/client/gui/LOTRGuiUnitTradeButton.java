package lotr.client.gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;

public class LOTRGuiUnitTradeButton extends GuiButton {
    private static ResourceLocation guiTexture = new ResourceLocation("lotr:gui/npc/unit_trade_buttons.png");

    public LOTRGuiUnitTradeButton(int i, int j, int k, int width, int height) {
        super(i, j, k, width, height, "");
    }

    @Override
    public void drawButton(Minecraft mc, int i, int j) {
        if(this.visible) {
            mc.getTextureManager().bindTexture(guiTexture);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            boolean flag = i >= this.xPosition && j >= this.yPosition && i < this.xPosition + this.width && j < this.yPosition + this.height;
            int k = this.id * 19;
            int l = 0;
            if(!this.enabled) {
                l += this.width * 2;
            }
            else if(flag) {
                l += this.width;
            }
            this.drawTexturedModalRect(this.xPosition, this.yPosition, l, k, this.width, this.height);
        }
    }
}
