package lotr.client.gui;

import org.lwjgl.opengl.GL11;

import lotr.common.LOTRAchievement;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class LOTRGuiButtonAchievements extends GuiButton {
    private boolean leftOrRight;
    public LOTRAchievement.Category buttonCategory;

    public LOTRGuiButtonAchievements(int i, boolean flag, int j, int k) {
        super(i, j, k, 15, 21, "");
        this.leftOrRight = flag;
    }

    @Override
    public void drawButton(Minecraft mc, int i, int j) {
        if(this.visible) {
            boolean highlighted;
            mc.getTextureManager().bindTexture(LOTRGuiAchievements.iconsTexture);
            int texU = this.leftOrRight ? 0 : this.width * 3;
            int texV = 124;
            highlighted = i >= this.xPosition && j >= this.yPosition && i < this.xPosition + this.width && j < this.yPosition + this.height;
            if(!this.enabled) {
                texU += this.width * 2;
            }
            else if(highlighted) {
                texU += this.width;
            }
            float[] catColors = this.buttonCategory.getCategoryRGB();
            GL11.glColor4f(catColors[0], catColors[1], catColors[2], 1.0f);
            this.drawTexturedModalRect(this.xPosition, this.yPosition, texU, texV, this.width, this.height);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            this.drawTexturedModalRect(this.xPosition, this.yPosition, texU, texV + this.height, this.width, this.height);
        }
    }
}
