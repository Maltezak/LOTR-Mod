package lotr.client.gui;

import org.lwjgl.opengl.GL11;

import lotr.common.inventory.LOTRContainerUnsmeltery;
import lotr.common.tileentity.LOTRTileEntityUnsmeltery;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.*;

public class LOTRGuiUnsmeltery extends GuiContainer {
    private static ResourceLocation guiTexture = new ResourceLocation("lotr:gui/unsmelter.png");
    private LOTRTileEntityUnsmeltery theUnsmeltery;

    public LOTRGuiUnsmeltery(InventoryPlayer inv, LOTRTileEntityUnsmeltery unsmeltery) {
        super(new LOTRContainerUnsmeltery(inv, unsmeltery));
        this.theUnsmeltery = unsmeltery;
        this.ySize = 176;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int i, int j) {
        String s = this.theUnsmeltery.getInventoryName();
        this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
        this.fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, 72, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(guiTexture);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
        if(this.theUnsmeltery.isSmelting()) {
            int k = this.theUnsmeltery.getSmeltTimeRemainingScaled(13);
            this.drawTexturedModalRect(this.guiLeft + 56, this.guiTop + 36 + 12 - k, 176, 12 - k, 14, k + 1);
        }
        int l = this.theUnsmeltery.getSmeltProgressScaled(24);
        this.drawTexturedModalRect(this.guiLeft + 79, this.guiTop + 34, 176, 14, l + 1, 16);
    }
}
