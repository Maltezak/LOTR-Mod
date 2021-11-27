package lotr.client.gui;

import org.lwjgl.opengl.GL11;

import lotr.common.inventory.LOTRContainerBarrel;
import lotr.common.network.*;
import lotr.common.tileentity.LOTRTileEntityBarrel;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;

public class LOTRGuiBarrel extends GuiContainer {
    private static ResourceLocation guiTexture = new ResourceLocation("lotr:gui/barrel/barrel.png");
    private static ResourceLocation brewingTexture = new ResourceLocation("lotr:gui/barrel/brewing.png");
    private LOTRTileEntityBarrel theBarrel;
    private GuiButton brewingButton;
    private float prevBrewAnim = -1.0f;
    private float brewAnim = -1.0f;

    public LOTRGuiBarrel(InventoryPlayer inv, LOTRTileEntityBarrel barrel) {
        super(new LOTRContainerBarrel(inv, barrel));
        this.theBarrel = barrel;
        this.xSize = 210;
        this.ySize = 221;
    }

    @Override
    public void initGui() {
        super.initGui();
        this.brewingButton = new GuiButton(0, this.guiLeft + 25, this.guiTop + 97, 100, 20, StatCollector.translateToLocal("container.lotr.barrel.startBrewing"));
        this.buttonList.add(this.brewingButton);
    }

    @Override
    public void drawScreen(int i, int j, float f) {
        if(this.theBarrel.barrelMode == 0) {
            this.brewingButton.enabled = this.theBarrel.getStackInSlot(9) != null;
            this.brewingButton.displayString = StatCollector.translateToLocal("container.lotr.barrel.startBrewing");
        }
        if(this.theBarrel.barrelMode == 1) {
            this.brewingButton.enabled = this.theBarrel.getStackInSlot(9) != null && this.theBarrel.getStackInSlot(9).getItemDamage() > 0;
            this.brewingButton.displayString = StatCollector.translateToLocal("container.lotr.barrel.stopBrewing");
        }
        if(this.theBarrel.barrelMode == 2) {
            this.brewingButton.enabled = false;
            this.brewingButton.displayString = StatCollector.translateToLocal("container.lotr.barrel.startBrewing");
        }
        super.drawScreen(i, j, f);
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if(button.enabled && button.id == 0) {
            LOTRPacketBrewingButton packet = new LOTRPacketBrewingButton();
            LOTRPacketHandler.networkWrapper.sendToServer(packet);
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int i, int j) {
        String s = this.theBarrel.getInventoryName();
        String s1 = this.theBarrel.getInvSubtitle();
        this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
        this.fontRendererObj.drawString(s1, this.xSize / 2 - this.fontRendererObj.getStringWidth(s1) / 2, 17, 4210752);
        this.fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 25, 127, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(guiTexture);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
        int brewMode = this.theBarrel.barrelMode;
        int fullAmount = this.theBarrel.getBarrelFullAmountScaled(96);
        if(brewMode == 1) {
            fullAmount = this.theBarrel.getBrewProgressScaled(96);
        }
        this.prevBrewAnim = this.brewAnim;
        this.brewAnim = this.theBarrel.getBrewAnimationProgressScaledF(97, f);
        float brewAnimF = this.prevBrewAnim + (this.brewAnim - this.prevBrewAnim) * f;
        float brewAnimPc = this.theBarrel.getBrewAnimationProgressScaledF(1, f);
        if(brewMode == 1 || brewMode == 2) {
            IIcon liquidIcon;
            int x0 = this.guiLeft + 148;
            int x1 = this.guiLeft + 196;
            int y0 = this.guiTop + 34;
            int y1 = this.guiTop + 130;
            int yFull = y1 - fullAmount;
            float yAnim = y1 - brewAnimF;
            ItemStack itemstack = this.theBarrel.getStackInSlot(9);
            if(itemstack != null && (liquidIcon = itemstack.getItem().getIconFromDamage(-1)) != null) {
                this.mc.getTextureManager().bindTexture(TextureMap.locationItemsTexture);
                float minU = liquidIcon.getInterpolatedU(7.0);
                float maxU = liquidIcon.getInterpolatedU(8.0);
                float minV = liquidIcon.getInterpolatedV(7.0);
                float maxV = liquidIcon.getInterpolatedV(8.0);
                Tessellator tessellator = Tessellator.instance;
                tessellator.startDrawingQuads();
                tessellator.addVertexWithUV(x0, y1, this.zLevel, minU, maxV);
                tessellator.addVertexWithUV(x1, y1, this.zLevel, maxU, maxV);
                tessellator.addVertexWithUV(x1, yFull, this.zLevel, maxU, minV);
                tessellator.addVertexWithUV(x0, yFull, this.zLevel, minU, minV);
                tessellator.draw();
                int fullColor = 2167561;
                this.drawGradientRect(x0, yFull, x1, y1, 0, 0xFF000000 | fullColor);
            }
            if(brewMode == 1) {
                this.mc.getTextureManager().bindTexture(brewingTexture);
                GL11.glEnable(3042);
                OpenGlHelper.glBlendFunc(770, 771, 1, 0);
                GL11.glDisable(3008);
                GL11.glColor4f(1.0f, 1.0f, 1.0f, brewAnimPc);
                LOTRGuiScreenBase.drawTexturedModalRectFloat(x0, yAnim, 51.0, 0.0, x1 - x0, y1 - yAnim, 256, this.zLevel);
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                GL11.glEnable(3008);
                GL11.glDisable(3042);
            }
            this.mc.getTextureManager().bindTexture(brewingTexture);
            this.drawTexturedModalRect(x0, y0, 1, 0, x1 - x0, y1 - y0);
        }
    }
}
