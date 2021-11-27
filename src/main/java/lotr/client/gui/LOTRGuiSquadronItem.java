package lotr.client.gui;

import org.lwjgl.opengl.GL11;

import lotr.common.LOTRSquadrons;
import lotr.common.network.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;

public class LOTRGuiSquadronItem extends LOTRGuiScreenBase {
    private static final ResourceLocation guiTexture = new ResourceLocation("lotr:gui/squadronItem.png");
    private static final RenderItem itemRenderer = new RenderItem();
    private int xSize = 200;
    private int ySize = 120;
    private int guiLeft;
    private int guiTop;
    private GuiButton buttonDone;
    private GuiTextField squadronNameField;
    private ItemStack theItem;
    @Override
    public void initGui() {
        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;
        this.buttonDone = new GuiButton(1, this.guiLeft + this.xSize / 2 - 40, this.guiTop + 85, 80, 20, StatCollector.translateToLocal("lotr.gui.squadronItem.done"));
        this.buttonList.add(this.buttonDone);
        ItemStack itemstack = this.mc.thePlayer.inventory.getCurrentItem();
        if(itemstack != null && itemstack.getItem() instanceof LOTRSquadrons.SquadronItem) {
            this.theItem = itemstack;
            this.squadronNameField = new GuiTextField(this.fontRendererObj, this.guiLeft + this.xSize / 2 - 80, this.guiTop + 50, 160, 20);
            this.squadronNameField.setMaxStringLength(LOTRSquadrons.SQUADRON_LENGTH_MAX);
            String squadron = LOTRSquadrons.getSquadron(this.theItem);
            if(!StringUtils.isNullOrEmpty(squadron)) {
                this.squadronNameField.setText(squadron);
            }
        }
        if(this.theItem == null) {
            this.mc.thePlayer.closeScreen();
        }
    }

    @Override
    public void drawScreen(int i, int j, float f) {
        boolean noSquadron;
        this.drawDefaultBackground();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(guiTexture);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
        String s = this.theItem.getDisplayName();
        this.fontRendererObj.drawString(s, this.guiLeft + this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, this.guiTop + 11, 4210752);
        s = StatCollector.translateToLocal("lotr.gui.squadronItem.squadron");
        this.fontRendererObj.drawString(s, this.squadronNameField.xPosition, this.squadronNameField.yPosition - this.fontRendererObj.FONT_HEIGHT - 3, 4210752);
        noSquadron = StringUtils.isNullOrEmpty(this.squadronNameField.getText()) && !this.squadronNameField.isFocused();
        if(noSquadron) {
            String squadronMessage = StatCollector.translateToLocal("lotr.gui.squadronItem.none");
            this.squadronNameField.setText((EnumChatFormatting.DARK_GRAY) + squadronMessage);
        }
        this.squadronNameField.drawTextBox();
        if(noSquadron) {
            this.squadronNameField.setText("");
        }
        super.drawScreen(i, j, f);
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        this.squadronNameField.updateCursorCounter();
        ItemStack itemstack = this.mc.thePlayer.getCurrentEquippedItem();
        if(itemstack == null || !(itemstack.getItem() instanceof LOTRSquadrons.SquadronItem)) {
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
        if(this.squadronNameField.getVisible() && this.squadronNameField.textboxKeyTyped(c, i)) {
            return;
        }
        super.keyTyped(c, i);
    }

    @Override
    protected void mouseClicked(int i, int j, int k) {
        super.mouseClicked(i, j, k);
        this.squadronNameField.mouseClicked(i, j, k);
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        String squadron = this.squadronNameField.getText();
        LOTRPacketItemSquadron packet = new LOTRPacketItemSquadron(squadron);
        LOTRPacketHandler.networkWrapper.sendToServer(packet);
    }
}
