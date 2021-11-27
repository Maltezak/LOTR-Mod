package lotr.client.gui;

import org.lwjgl.opengl.GL11;

import lotr.client.LOTRTextures;
import lotr.common.world.map.LOTRWaypoint;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.ResourceLocation;

public class LOTRGuiRendererMap {
    private static final ResourceLocation vignetteTexture = new ResourceLocation("textures/misc/vignette.png");
    public float prevMapX;
    public float mapX;
    public float prevMapY;
    public float mapY;
    public float zoomExp;
    public float zoomStable;
    private boolean sepia = false;

    public void setSepia(boolean flag) {
        this.sepia = flag;
    }

    public void updateTick() {
        this.prevMapX = this.mapX;
        this.prevMapY = this.mapY;
    }

    public void renderMap(GuiScreen gui, LOTRGuiMap mapGui, float f) {
        this.renderMap(gui, mapGui, f, 0, 0, gui.width, gui.height);
    }

    public void renderMap(GuiScreen gui, LOTRGuiMap mapGui, float f, int x0, int y0, int x1, int y1) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        int oceanColor = LOTRTextures.getMapOceanColor(this.sepia);
        Gui.drawRect(x0, y0, x1, y1, oceanColor);
        float zoom = (float) Math.pow(2.0, this.zoomExp);
        float mapPosX = this.prevMapX + (this.mapX - this.prevMapX) * f;
        float mapPosY = this.prevMapY + (this.mapY - this.prevMapY) * f;
        mapGui.setFakeMapProperties(mapPosX, mapPosY, zoom, this.zoomExp, this.zoomStable);
        int[] statics = LOTRGuiMap.setFakeStaticProperties(x1 - x0, y1 - y0, x0, x1, y0, y1);
        mapGui.enableZoomOutWPFading = false;
        mapGui.renderMapAndOverlay(this.sepia, 1.0f, true);
        mapGui.renderRoads(false);
        mapGui.renderWaypoints(LOTRWaypoint.listAllWaypoints(), 0, 0, 0, false, true);
        LOTRGuiMap.setFakeStaticProperties(statics[0], statics[1], statics[2], statics[3], statics[4], statics[5]);
    }

    public void renderVignette(GuiScreen gui, double zLevel) {
        this.renderVignette(gui, zLevel, 0, 0, gui.width, gui.height);
    }

    public void renderVignette(GuiScreen gui, double zLevel, int x0, int y0, int x1, int y1) {
        GL11.glEnable(3042);
        OpenGlHelper.glBlendFunc(771, 769, 1, 0);
        float alpha = 1.0f;
        GL11.glColor4f(alpha, alpha, alpha, 1.0f);
        gui.mc.getTextureManager().bindTexture(vignetteTexture);
        double u0 = (double) x0 / (double) gui.width;
        double u1 = (double) x1 / (double) gui.width;
        double v0 = (double) y0 / (double) gui.height;
        double v1 = (double) y1 / (double) gui.height;
        double z = zLevel;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(x0, y1, z, u0, v1);
        tessellator.addVertexWithUV(x1, y1, z, u1, v1);
        tessellator.addVertexWithUV(x1, y0, z, u1, v0);
        tessellator.addVertexWithUV(x0, y0, z, u0, v0);
        tessellator.draw();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
    }

    public void renderVignettes(GuiScreen gui, double zLevel, int count) {
        for(int l = 0; l < count; ++l) {
            this.renderVignette(gui, zLevel);
        }
    }

    public void renderVignettes(GuiScreen gui, double zLevel, int count, int x0, int y0, int x1, int y1) {
        for(int l = 0; l < count; ++l) {
            this.renderVignette(gui, zLevel, x0, y0, x1, y1);
        }
    }
}
