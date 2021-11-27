package lotr.client.gui;

import org.lwjgl.opengl.GL11;

import lotr.common.inventory.LOTRContainerHobbitOven;
import lotr.common.tileentity.LOTRTileEntityHobbitOven;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.*;

public class LOTRGuiHobbitOven extends GuiContainer {
    private static ResourceLocation guiTexture = new ResourceLocation("lotr:gui/oven.png");
    private LOTRTileEntityHobbitOven theOven;

    public LOTRGuiHobbitOven(InventoryPlayer inv, LOTRTileEntityHobbitOven oven) {
        super(new LOTRContainerHobbitOven(inv, oven));
        this.theOven = oven;
        this.ySize = 215;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int i, int j) {
        String s = this.theOven.getInventoryName();
        this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
        this.fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, 121, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(guiTexture);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
        if(this.theOven.isCooking()) {
            int k = this.theOven.getCookTimeRemainingScaled(12);
            this.drawTexturedModalRect(this.guiLeft + 80, this.guiTop + 94 + 12 - k, 176, 12 - k, 14, k + 2);
        }
        int l = this.theOven.getCookProgressScaled(24);
        this.drawTexturedModalRect(this.guiLeft + 80, this.guiTop + 40, 176, 14, 16, l + 1);
    }
}
