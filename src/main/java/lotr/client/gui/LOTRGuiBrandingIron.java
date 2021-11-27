package lotr.client.gui;

import org.apache.commons.lang3.StringUtils;
import org.lwjgl.opengl.GL11;

import lotr.common.item.LOTRItemBrandingIron;
import lotr.common.network.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;

public class LOTRGuiBrandingIron extends LOTRGuiScreenBase {
    private static final ResourceLocation guiTexture = new ResourceLocation("lotr:gui/brandingIron.png");
    private static final RenderItem itemRenderer = new RenderItem();
    private int xSize = 200;
    private int ySize = 132;
    private int guiLeft;
    private int guiTop;
    private GuiButton buttonDone;
    private GuiTextField brandNameField;
    private ItemStack theItem;

    @Override
    public void initGui() {
        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;
        this.buttonDone = new GuiButton(1, this.guiLeft + this.xSize / 2 - 40, this.guiTop + 97, 80, 20, StatCollector.translateToLocal("lotr.gui.brandingIron.done"));
        this.buttonList.add(this.buttonDone);
        ItemStack itemstack = this.mc.thePlayer.inventory.getCurrentItem();
        if(itemstack != null && itemstack.getItem() instanceof LOTRItemBrandingIron) {
            this.theItem = itemstack;
            this.brandNameField = new GuiTextField(this.fontRendererObj, this.guiLeft + this.xSize / 2 - 80, this.guiTop + 50, 160, 20);
        }
        if(this.theItem == null) {
            this.mc.thePlayer.closeScreen();
        }
    }

    @Override
    public void drawScreen(int i, int j, float f) {
        this.drawDefaultBackground();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(guiTexture);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
        String s = StatCollector.translateToLocal("lotr.gui.brandingIron.title");
        this.fontRendererObj.drawString(s, this.guiLeft + this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, this.guiTop + 11, 4210752);
        s = StatCollector.translateToLocal("lotr.gui.brandingIron.naming");
        this.fontRendererObj.drawString(s, this.brandNameField.xPosition, this.brandNameField.yPosition - this.fontRendererObj.FONT_HEIGHT - 3, 4210752);
        s = StatCollector.translateToLocal("lotr.gui.brandingIron.unnameHint");
        this.fontRendererObj.drawString(s, this.brandNameField.xPosition, this.brandNameField.yPosition + this.brandNameField.height + 3, 4210752);
        this.brandNameField.drawTextBox();
        this.buttonDone.enabled = !StringUtils.isBlank(this.brandNameField.getText());
        super.drawScreen(i, j, f);
        if(this.theItem != null) {
            itemRenderer.renderItemIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), this.theItem, this.guiLeft + 8, this.guiTop + 8);
        }
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        this.brandNameField.updateCursorCounter();
        ItemStack itemstack = this.mc.thePlayer.getCurrentEquippedItem();
        if(itemstack == null || !(itemstack.getItem() instanceof LOTRItemBrandingIron)) {
            this.mc.thePlayer.closeScreen();
        }
        else {
            this.theItem = itemstack;
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if(button == this.buttonDone) {
            this.mc.thePlayer.closeScreen();
        }
    }

    @Override
    protected void keyTyped(char c, int i) {
        if(this.brandNameField.getVisible() && this.brandNameField.textboxKeyTyped(c, i)) {
            return;
        }
        super.keyTyped(c, i);
    }

    @Override
    protected void mouseClicked(int i, int j, int k) {
        super.mouseClicked(i, j, k);
        this.brandNameField.mouseClicked(i, j, k);
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        String brandName = this.brandNameField.getText();
        if(!StringUtils.isBlank(brandName)) {
            LOTRPacketBrandingIron packet = new LOTRPacketBrandingIron(brandName);
            LOTRPacketHandler.networkWrapper.sendToServer(packet);
        }
    }
}
