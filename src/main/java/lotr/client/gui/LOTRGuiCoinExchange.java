package lotr.client.gui;

import org.lwjgl.opengl.GL11;

import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.inventory.LOTRContainerCoinExchange;
import lotr.common.network.*;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.util.*;

public class LOTRGuiCoinExchange extends GuiContainer {
    public static ResourceLocation guiTexture = new ResourceLocation("lotr:gui/coin_exchange.png");
    private LOTRContainerCoinExchange theContainer;
    private GuiButton buttonLeft;
    private GuiButton buttonRight;

    public LOTRGuiCoinExchange(EntityPlayer entityplayer, LOTREntityNPC npc) {
        super(new LOTRContainerCoinExchange(entityplayer, npc));
        this.theContainer = (LOTRContainerCoinExchange) this.inventorySlots;
        this.ySize = 188;
    }

    @Override
    public void initGui() {
        super.initGui();
        int i = this.guiLeft + this.xSize / 2;
        int j = 28;
        int k = 16;
        this.buttonLeft = new LOTRGuiButtonCoinExchange(0, i - j - k, this.guiTop + 45);
        this.buttonList.add(this.buttonLeft);
        this.buttonRight = new LOTRGuiButtonCoinExchange(1, i + j - k, this.guiTop + 45);
        this.buttonList.add(this.buttonRight);
    }

    @Override
    public void drawScreen(int i, int j, float f) {
        this.buttonLeft.enabled = !this.theContainer.exchanged && this.theContainer.exchangeInv.getStackInSlot(0) != null;
        this.buttonRight.enabled = !this.theContainer.exchanged && this.theContainer.exchangeInv.getStackInSlot(1) != null;
        super.drawScreen(i, j, f);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int i, int j) {
        this.drawCenteredString(StatCollector.translateToLocal("container.lotr.coinExchange"), 89, 11, 4210752);
        this.fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, 94, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(guiTexture);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
        if(this.theContainer.exchanged) {
            for(int l = 0; l < this.theContainer.exchangeInv.getSizeInventory(); ++l) {
                Slot slot = this.theContainer.getSlotFromInventory(this.theContainer.exchangeInv, l);
                if(!slot.getHasStack()) continue;
                this.drawTexturedModalRect(this.guiLeft + slot.xDisplayPosition - 5, this.guiTop + slot.yDisplayPosition - 5, 176, 51, 26, 26);
            }
        }
    }

    private void drawCenteredString(String s, int i, int j, int k) {
        this.fontRendererObj.drawString(s, i - this.fontRendererObj.getStringWidth(s) / 2, j, k);
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if(button.enabled) {
            if(button == this.buttonLeft || button == this.buttonRight) {
                LOTRPacketCoinExchange packet = new LOTRPacketCoinExchange(button.id);
                LOTRPacketHandler.networkWrapper.sendToServer(packet);
            }
            else {
                super.actionPerformed(button);
            }
        }
    }
}
