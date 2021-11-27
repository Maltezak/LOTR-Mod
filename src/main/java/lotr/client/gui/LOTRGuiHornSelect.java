package lotr.client.gui;

import org.lwjgl.opengl.GL11;

import lotr.common.LOTRMod;
import lotr.common.network.*;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;

public class LOTRGuiHornSelect extends LOTRGuiScreenBase {
    private static final ResourceLocation guiTexture = new ResourceLocation("lotr:gui/horn_select.png");
    private static final RenderItem itemRenderer = new RenderItem();
    private int xSize = 176;
    private int ySize = 256;
    private int guiLeft;
    private int guiTop;

    @Override
    public void initGui() {
        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;
        this.buttonList.add(new GuiButton(1, this.guiLeft + 40, this.guiTop + 40, 120, 20, StatCollector.translateToLocal("lotr.gui.hornSelect.haltReady")));
        this.buttonList.add(new GuiButton(3, this.guiLeft + 40, this.guiTop + 75, 120, 20, StatCollector.translateToLocal("lotr.gui.hornSelect.summon")));
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if(button.enabled) {
            LOTRPacketHornSelect packet = new LOTRPacketHornSelect(button.id);
            LOTRPacketHandler.networkWrapper.sendToServer(packet);
            this.mc.thePlayer.closeScreen();
        }
    }

    @Override
    public void drawScreen(int i, int j, float f) {
        this.drawDefaultBackground();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(guiTexture);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
        String s = StatCollector.translateToLocal("lotr.gui.hornSelect.title");
        this.fontRendererObj.drawString(s, this.guiLeft + this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, this.guiTop + 11, 4210752);
        super.drawScreen(i, j, f);
        for(Object element : this.buttonList) {
            GuiButton button = (GuiButton) element;
            itemRenderer.renderItemIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), new ItemStack(LOTRMod.commandHorn, 1, button.id), button.xPosition - 22, button.yPosition + 2);
        }
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        ItemStack itemstack = this.mc.thePlayer.inventory.getCurrentItem();
        if(itemstack == null || itemstack.getItem() != LOTRMod.commandHorn || itemstack.getItemDamage() != 0) {
            this.mc.thePlayer.closeScreen();
        }
    }
}
