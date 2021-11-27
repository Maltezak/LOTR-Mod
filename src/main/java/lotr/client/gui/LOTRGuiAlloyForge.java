package lotr.client.gui;

import org.lwjgl.opengl.GL11;

import lotr.common.inventory.LOTRContainerAlloyForge;
import lotr.common.tileentity.LOTRTileEntityAlloyForgeBase;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.*;

public class LOTRGuiAlloyForge extends GuiContainer {
    private static ResourceLocation guiTexture = new ResourceLocation("lotr:gui/forge.png");
    private LOTRTileEntityAlloyForgeBase theForge;

    public LOTRGuiAlloyForge(InventoryPlayer inv, LOTRTileEntityAlloyForgeBase forge) {
        super(new LOTRContainerAlloyForge(inv, forge));
        this.theForge = forge;
        this.ySize = 233;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int i, int j) {
        String s = this.theForge.getInventoryName();
        this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
        this.fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, 139, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(guiTexture);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
        if(this.theForge.isSmelting()) {
            int k = this.theForge.getSmeltTimeRemainingScaled(12);
            this.drawTexturedModalRect(this.guiLeft + 80, this.guiTop + 112 + 12 - k, 176, 12 - k, 14, k + 2);
        }
        int l = this.theForge.getSmeltProgressScaled(24);
        this.drawTexturedModalRect(this.guiLeft + 80, this.guiTop + 58, 176, 14, 16, l + 1);
    }
}
