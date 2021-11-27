package lotr.client.gui;

import org.lwjgl.opengl.GL11;

import lotr.common.entity.npc.*;
import lotr.common.inventory.*;
import lotr.common.network.*;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class LOTRGuiTrade
extends GuiContainer {
    public static final ResourceLocation guiTexture = new ResourceLocation("lotr:gui/npc/trade.png");
    private static int lockedTradeColor = -1610612736;
    public LOTREntityNPC theEntity;
    private LOTRContainerTrade containerTrade;
    private GuiButton buttonSell;
    public LOTRGuiTrade(InventoryPlayer inv, LOTRTradeable trader, World world) {
        super(new LOTRContainerTrade(inv, trader, world));
        this.containerTrade = (LOTRContainerTrade)this.inventorySlots;
        this.theEntity = (LOTREntityNPC)(trader);
        this.ySize = 270;
    }

    public void initGui() {
        super.initGui();
        this.buttonSell = new LOTRGuiTradeButton(0, this.guiLeft + 79, this.guiTop + 164);
        this.buttonSell.enabled = false;
        this.buttonList.add(this.buttonSell);
    }

    protected void drawGuiContainerForegroundLayer(int i, int j) {
        int l;
        this.drawCenteredString(this.theEntity.getNPCName(), 89, 11, 4210752);
        this.fontRendererObj.drawString(StatCollector.translateToLocal("container.lotr.trade.buy"), 8, 28, 4210752);
        this.fontRendererObj.drawString(StatCollector.translateToLocal("container.lotr.trade.sell"), 8, 79, 4210752);
        this.fontRendererObj.drawString(StatCollector.translateToLocal("container.lotr.trade.sellOffer"), 8, 129, 4210752);
        this.fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, 176, 4210752);
        for (l = 0; l < this.containerTrade.tradeInvBuy.getSizeInventory(); ++l) {
            LOTRSlotTrade slotBuy = (LOTRSlotTrade)this.containerTrade.getSlotFromInventory(this.containerTrade.tradeInvBuy, l);
            this.renderTradeSlot(slotBuy);
        }
        for (l = 0; l < this.containerTrade.tradeInvSell.getSizeInventory(); ++l) {
            LOTRSlotTrade slotSell = (LOTRSlotTrade)this.containerTrade.getSlotFromInventory(this.containerTrade.tradeInvSell, l);
            this.renderTradeSlot(slotSell);
        }
        int totalSellPrice = 0;
        for (int l2 = 0; l2 < this.containerTrade.tradeInvSellOffer.getSizeInventory(); ++l2) {
            LOTRTradeSellResult sellResult;
            Slot slotSell = this.containerTrade.getSlotFromInventory(this.containerTrade.tradeInvSellOffer, l2);
            ItemStack item = slotSell.getStack();
            if (item == null || (sellResult = LOTRTradeEntries.getItemSellResult(item, this.theEntity)) == null) continue;
            totalSellPrice += sellResult.totalSellValue;
        }
        if (totalSellPrice > 0) {
            this.fontRendererObj.drawString(StatCollector.translateToLocalFormatted("container.lotr.trade.sellPrice", totalSellPrice), 100, 169, 4210752);
        }
        this.buttonSell.enabled = totalSellPrice > 0;
    }

    private void renderTradeSlot(LOTRSlotTrade slot) {
        LOTRTradeEntry trade = slot.getTrade();
        if (trade != null) {
            int lockedPixels = 0;
            boolean inFront = false;
            if (!trade.isAvailable()) {
                lockedPixels = 16;
                inFront = true;
            } else {
                lockedPixels = trade.getLockedProgressForSlot();
                inFront = false;
            }
            if (lockedPixels > 0) {
                GL11.glPushMatrix();
                if (inFront) {
                    GL11.glTranslatef(0.0f, 0.0f, 200.0f);
                }
                int x = slot.xDisplayPosition;
                int y = slot.yDisplayPosition;
                LOTRGuiTrade.drawRect(x, y, x + lockedPixels, y + 16, lockedTradeColor);
                GL11.glPopMatrix();
            }
            if (trade.isAvailable()) {
                int cost = slot.cost();
                if (cost > 0) {
                    this.renderCost(Integer.toString(cost), slot.xDisplayPosition + 8, slot.yDisplayPosition + 22);
                }
            } else {
                this.drawCenteredString(StatCollector.translateToLocal("container.lotr.trade.locked"), slot.xDisplayPosition + 8, slot.yDisplayPosition + 22, 4210752);
            }
        }
    }

    private void renderCost(String s, int x, int y) {
        boolean halfSize;
        int l = this.fontRendererObj.getStringWidth(s);
        halfSize = l > 15;
        if (halfSize) {
            GL11.glPushMatrix();
            GL11.glScalef(0.5f, 0.5f, 1.0f);
            x *= 2;
            y *= 2;
            y += this.fontRendererObj.FONT_HEIGHT / 2;
        }
        this.drawCenteredString(s, x, y, 4210752);
        if (halfSize) {
            GL11.glPopMatrix();
        }
    }

    protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(guiTexture);
        LOTRGuiTrade.func_146110_a(this.guiLeft, this.guiTop, 0.0f, 0.0f, this.xSize, this.ySize, 512.0f, 512.0f);
    }

    protected void actionPerformed(GuiButton button) {
        if (button.enabled && button == this.buttonSell) {
            LOTRPacketSell packet = new LOTRPacketSell();
            LOTRPacketHandler.networkWrapper.sendToServer(packet);
        }
    }

    private void drawCenteredString(String s, int i, int j, int k) {
        this.fontRendererObj.drawString(s, i - this.fontRendererObj.getStringWidth(s) / 2, j, k);
    }

    static {
    }
}

