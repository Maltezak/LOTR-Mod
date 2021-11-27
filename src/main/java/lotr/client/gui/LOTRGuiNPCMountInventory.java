package lotr.client.gui;

import org.lwjgl.opengl.GL11;

import lotr.common.entity.npc.LOTREntityNPCRideable;
import lotr.common.inventory.LOTRContainerNPCMountInventory;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class LOTRGuiNPCMountInventory extends GuiContainer {
    private static final ResourceLocation guiTexture = new ResourceLocation("textures/gui/container/horse.png");
    private IInventory thePlayerInv;
    private IInventory theMountInv;
    private LOTREntityNPCRideable theMount;
    private float mouseX;
    private float mouseY;

    public LOTRGuiNPCMountInventory(IInventory playerInv, IInventory mountInv, LOTREntityNPCRideable mount) {
        super(new LOTRContainerNPCMountInventory(playerInv, mountInv, mount));
        this.thePlayerInv = playerInv;
        this.theMountInv = mountInv;
        this.theMount = mount;
        this.allowUserInput = false;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int i, int j) {
        this.fontRendererObj.drawString(this.theMountInv.hasCustomInventoryName() ? this.theMountInv.getInventoryName() : I18n.format(this.theMountInv.getInventoryName()), 8, 6, 4210752);
        this.fontRendererObj.drawString(this.thePlayerInv.hasCustomInventoryName() ? this.thePlayerInv.getInventoryName() : I18n.format(this.thePlayerInv.getInventoryName()), 8, this.ySize - 96 + 2, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(guiTexture);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
        this.drawTexturedModalRect(k + 7, l + 35, 0, this.ySize + 54, 18, 18);
        GuiInventory.func_147046_a(k + 51, l + 60, 17, k + 51 - this.mouseX, l + 75 - 50 - this.mouseY, this.theMount);
    }

    @Override
    public void drawScreen(int i, int j, float f) {
        this.mouseX = i;
        this.mouseY = j;
        super.drawScreen(i, j, f);
    }
}
