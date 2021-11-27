package lotr.client.gui;

import org.lwjgl.opengl.GL11;

import lotr.common.entity.npc.LOTREntityGollum;
import lotr.common.inventory.LOTRContainerGollum;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.*;

public class LOTRGuiGollum extends GuiContainer {
    private static ResourceLocation guiTexture = new ResourceLocation("lotr:gui/npc/gollum.png");
    private LOTREntityGollum theGollum;

    public LOTRGuiGollum(InventoryPlayer inv, LOTREntityGollum gollum) {
        super(new LOTRContainerGollum(inv, gollum));
        this.theGollum = gollum;
        this.ySize = 168;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int i, int j) {
        String s = this.theGollum.getCommandSenderName();
        this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
        this.fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, 74, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(guiTexture);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
    }
}
