package lotr.client.gui;

import org.lwjgl.opengl.GL11;

import lotr.common.inventory.LOTRContainerMillstone;
import lotr.common.tileentity.LOTRTileEntityMillstone;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.*;

public class LOTRGuiMillstone extends GuiContainer {
    private static ResourceLocation guiTexture = new ResourceLocation("lotr:gui/millstone.png");
    private LOTRTileEntityMillstone theMillstone;

    public LOTRGuiMillstone(InventoryPlayer inv, LOTRTileEntityMillstone millstone) {
        super(new LOTRContainerMillstone(inv, millstone));
        this.theMillstone = millstone;
        this.ySize = 182;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int i, int j) {
        String s = this.theMillstone.getInventoryName();
        this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
        this.fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, 88, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(guiTexture);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
        if(this.theMillstone.isMilling()) {
            int k = this.theMillstone.getMillProgressScaled(14);
            this.drawTexturedModalRect(this.guiLeft + 85, this.guiTop + 47, 176, 0, 14, k);
        }
    }
}
