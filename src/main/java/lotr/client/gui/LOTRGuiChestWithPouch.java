package lotr.client.gui;

import org.lwjgl.opengl.GL11;

import lotr.common.inventory.LOTRContainerChestWithPouch;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.*;
import net.minecraft.util.*;

public class LOTRGuiChestWithPouch
extends GuiContainer {
    private static final ResourceLocation guiTexture = new ResourceLocation("lotr:gui/pouch_with_chest.png");
    private IInventory pouchInv;
    private IInventory chestInv;
    private int chestRows;
    private int pouchRows;

    public LOTRGuiChestWithPouch(EntityPlayer entityplayer, int slot, IInventory chest) {
        super(new LOTRContainerChestWithPouch(entityplayer, slot, chest));
        this.pouchInv = ((LOTRContainerChestWithPouch)this.inventorySlots).pouchContainer.pouchInventory;
        this.chestInv = chest;
        this.allowUserInput = false;
        this.chestRows = chest.getSizeInventory() / 9;
        this.pouchRows = this.pouchInv.getSizeInventory() / 9;
        this.ySize = 180 + this.chestRows * 18;
    }

    protected void drawGuiContainerForegroundLayer(int i, int j) {
        this.fontRendererObj.drawString(this.chestInv.hasCustomInventoryName() ? this.chestInv.getInventoryName() : StatCollector.translateToLocal(this.chestInv.getInventoryName()), 8, 6, 4210752);
        this.fontRendererObj.drawString(this.pouchInv.hasCustomInventoryName() ? this.pouchInv.getInventoryName() : StatCollector.translateToLocal(this.pouchInv.getInventoryName()), 8, this.ySize - 160, 4210752);
        this.fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 93, 4210752);
    }

    protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
        int l;
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(guiTexture);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, 17 + this.chestRows * 18);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop + 17 + this.chestRows * 18, 0, 125, this.xSize, 13);
        for (l = 0; l < 3; ++l) {
            this.drawTexturedModalRect(this.guiLeft, this.guiTop + 17 + this.chestRows * 18 + 13 + l * 18, 0, 138, this.xSize, 18);
        }
        this.drawTexturedModalRect(this.guiLeft, this.guiTop + 17 + this.chestRows * 18 + 67, 0, 156, this.xSize, 96);
        this.mc.getTextureManager().bindTexture(LOTRGuiPouch.texture);
        for (l = 0; l < this.pouchRows; ++l) {
            this.drawTexturedModalRect(this.guiLeft + 7, this.guiTop + 17 + this.chestRows * 18 + 13 + l * 18, 0, 180, 162, 18);
        }
    }
}

