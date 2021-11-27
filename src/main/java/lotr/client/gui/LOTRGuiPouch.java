package lotr.client.gui;

import org.lwjgl.opengl.GL11;

import lotr.common.inventory.LOTRContainerPouch;
import lotr.common.network.*;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.*;

public class LOTRGuiPouch
extends GuiContainer {
    public static ResourceLocation texture = new ResourceLocation("lotr:gui/pouch.png");
    private LOTRContainerPouch thePouch;
    private int pouchRows;
    private GuiTextField theGuiTextField;

    public LOTRGuiPouch(EntityPlayer entityplayer, int slot) {
        super(new LOTRContainerPouch(entityplayer, slot));
        this.thePouch = (LOTRContainerPouch)this.inventorySlots;
        this.pouchRows = this.thePouch.capacity / 9;
        this.ySize = 180;
    }

    public void initGui() {
        super.initGui();
        this.theGuiTextField = new GuiTextField(this.fontRendererObj, this.guiLeft + this.xSize / 2 - 80, this.guiTop + 7, 160, 20);
        this.theGuiTextField.setText(this.thePouch.getDisplayName());
    }

    protected void drawGuiContainerForegroundLayer(int i, int j) {
        this.fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
    }

    protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(texture);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
        for (int l = 0; l < this.pouchRows; ++l) {
            this.drawTexturedModalRect(this.guiLeft + 7, this.guiTop + 29 + l * 18, 0, 180, 162, 18);
        }
        GL11.glDisable(2896);
        this.theGuiTextField.drawTextBox();
        GL11.glEnable(2896);
    }

    public void updateScreen() {
        super.updateScreen();
        this.theGuiTextField.updateCursorCounter();
    }

    protected void keyTyped(char c, int i) {
        if (this.theGuiTextField.textboxKeyTyped(c, i)) {
            this.renamePouch();
        } else {
            super.keyTyped(c, i);
        }
    }

    protected void mouseClicked(int i, int j, int k) {
        super.mouseClicked(i, j, k);
        this.theGuiTextField.mouseClicked(i, j, k);
    }

    private void renamePouch() {
        String name = this.theGuiTextField.getText();
        this.thePouch.renamePouch(name);
        LOTRPacketRenamePouch packet = new LOTRPacketRenamePouch(name);
        LOTRPacketHandler.networkWrapper.sendToServer(packet);
    }
}

