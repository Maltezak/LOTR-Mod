package lotr.client.gui;

import org.lwjgl.opengl.GL11;

import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.inventory.LOTRContainerHiredFarmerInventory;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;

public class LOTRGuiHiredFarmerInventory extends GuiContainer {
    private static ResourceLocation guiTexture = new ResourceLocation("lotr:gui/npc/hiredFarmer.png");
    private LOTREntityNPC theNPC;

    public LOTRGuiHiredFarmerInventory(InventoryPlayer inv, LOTREntityNPC entity) {
        super(new LOTRContainerHiredFarmerInventory(inv, entity));
        this.theNPC = entity;
        this.ySize = 161;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int i, int j) {
        String s = this.theNPC.getNPCName();
        this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
        this.fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, 67, 4210752);
        ItemStack seeds = this.inventorySlots.getSlot(0).getStack();
        if(seeds != null && seeds.stackSize == 1) {
            s = StatCollector.translateToLocal("lotr.gui.farmer.oneSeed");
            s = (EnumChatFormatting.RED) + s;
            this.fontRendererObj.drawSplitString(s, this.xSize + 10, 20, 120, 16777215);
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(guiTexture);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
        ItemStack seeds = this.inventorySlots.getSlot(0).getStack();
        if(seeds == null) {
            this.drawTexturedModalRect(this.guiLeft + 80, this.guiTop + 21, 176, 0, 16, 16);
        }
        if((this.inventorySlots.getSlot(3).getStack()) == null) {
            this.drawTexturedModalRect(this.guiLeft + 123, this.guiTop + 34, 176, 16, 16, 16);
        }
    }
}
