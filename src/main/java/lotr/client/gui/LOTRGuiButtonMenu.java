package lotr.client.gui;

import org.lwjgl.opengl.GL11;

import lotr.common.LOTRMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.multiplayer.WorldClient;

public class LOTRGuiButtonMenu extends GuiButton {
    private Class<? extends LOTRGuiMenuBase> menuScreenClass;
    public final int menuKeyCode;

    public LOTRGuiButtonMenu(LOTRGuiMenu gui, int i, int x, int y, Class<? extends LOTRGuiMenuBase> cls, String s, int key) {
        super(i, x, y, 32, 32, s);
        this.menuScreenClass = cls;
        this.menuKeyCode = key;
    }

    public LOTRGuiMenuBase openMenu() {
        try {
            return this.menuScreenClass.newInstance();
        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean canDisplayMenu() {
        if(this.menuScreenClass == LOTRGuiMap.class) {
            WorldClient world = Minecraft.getMinecraft().theWorld;
            return world != null && world.getWorldInfo().getTerrainType() != LOTRMod.worldTypeMiddleEarthClassic;
        }
        return true;
    }

    @Override
    public void drawButton(Minecraft mc, int i, int j) {
        if(this.visible) {
            mc.getTextureManager().bindTexture(LOTRGuiMenu.menuIconsTexture);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            this.field_146123_n = i >= this.xPosition && j >= this.yPosition && i < this.xPosition + this.width && j < this.yPosition + this.height;
            this.drawTexturedModalRect(this.xPosition, this.yPosition, 0 + (this.enabled ? 0 : this.width * 2) + (this.field_146123_n ? this.width : 0), this.id * this.height, this.width, this.height);
            this.mouseDragged(mc, i, j);
        }
    }
}
