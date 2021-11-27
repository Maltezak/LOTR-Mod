package lotr.client.gui;

import org.lwjgl.opengl.GL11;

import lotr.common.inventory.LOTRContainerDaleCracker;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.*;

public class LOTRGuiDaleCracker extends GuiContainer {
    private static ResourceLocation texture = new ResourceLocation("lotr:gui/daleCracker.png");
    private LOTRContainerDaleCracker theCracker;
    private GuiButton buttonSeal;

    public LOTRGuiDaleCracker(EntityPlayer entityplayer) {
        super(new LOTRContainerDaleCracker(entityplayer));
        this.theCracker = (LOTRContainerDaleCracker) this.inventorySlots;
    }

    @Override
    public void initGui() {
        super.initGui();
        this.buttonSeal = new GuiButton(0, this.guiLeft + this.xSize / 2 - 40, this.guiTop + 48, 80, 20, StatCollector.translateToLocal("lotr.gui.daleCracker.seal"));
        this.buttonList.add(this.buttonSeal);
        this.buttonSeal.enabled = false;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int i, int j) {
        String s = StatCollector.translateToLocal("lotr.gui.daleCracker");
        this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
        this.fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(texture);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        this.buttonSeal.enabled = !this.theCracker.isCrackerInvEmpty();
    }

    @Override
    protected boolean checkHotbarKeys(int i) {
        return false;
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if(button.enabled && button == this.buttonSeal && !this.theCracker.isCrackerInvEmpty()) {
            this.theCracker.sendSealingPacket(this.mc.thePlayer);
            this.mc.displayGuiScreen(null);
        }
    }
}
