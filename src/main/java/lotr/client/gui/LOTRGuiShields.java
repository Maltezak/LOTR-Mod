package lotr.client.gui;

import java.util.List;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import lotr.client.render.LOTRRenderShield;
import lotr.common.*;
import lotr.common.network.*;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;

public class LOTRGuiShields extends LOTRGuiMenuBase {
    private static ModelBiped playerModel = new ModelBiped();
    private int modelX;
    private int modelY;
    private float modelRotation;
    private float modelRotationPrev;
    private int isMouseDown;
    private int mouseX;
    private int mouseY;
    private int prevMouseX;
    private LOTRShields.ShieldType currentShieldType;
    private static int currentShieldTypeID;
    private LOTRShields currentShield;
    private static int currentShieldID;
    private GuiButton shieldLeft;
    private GuiButton shieldRight;
    private GuiButton shieldSelect;
    private GuiButton shieldRemove;
    private GuiButton changeCategory;

    public LOTRGuiShields() {
        this.modelRotationPrev = this.modelRotation = -140.0f;
    }

    @Override
    public void initGui() {
        super.initGui();
        this.modelX = this.guiLeft + this.xSize / 2;
        this.modelY = this.guiTop + 40;
        this.shieldLeft = new LOTRGuiButtonShieldsArrows(0, true, this.guiLeft + this.xSize / 2 - 64, this.guiTop + 207);
        this.buttonList.add(this.shieldLeft);
        this.shieldSelect = new GuiButton(1, this.guiLeft + this.xSize / 2 - 40, this.guiTop + 195, 80, 20, StatCollector.translateToLocal("lotr.gui.shields.select"));
        this.buttonList.add(this.shieldSelect);
        this.shieldRight = new LOTRGuiButtonShieldsArrows(2, false, this.guiLeft + this.xSize / 2 + 44, this.guiTop + 207);
        this.buttonList.add(this.shieldRight);
        this.shieldRemove = new GuiButton(3, this.guiLeft + this.xSize / 2 - 40, this.guiTop + 219, 80, 20, StatCollector.translateToLocal("lotr.gui.shields.remove"));
        this.buttonList.add(this.shieldRemove);
        this.changeCategory = new GuiButton(4, this.guiLeft + this.xSize / 2 - 80, this.guiTop + 250, 160, 20, "");
        this.buttonList.add(this.changeCategory);
        LOTRShields equippedShield = this.getPlayerEquippedShield();
        if(equippedShield != null) {
            currentShieldTypeID = equippedShield.shieldType.ordinal();
            currentShieldID = equippedShield.shieldID;
        }
        this.updateCurrentShield(0, 0);
    }

    private void updateCurrentShield(int shield, int type) {
        if(shield != 0) {
            currentShieldID += shield;
            currentShieldID = Math.max(currentShieldID, 0);
            currentShieldID = Math.min(currentShieldID, this.currentShieldType.list.size() - 1);
        }
        if(type != 0) {
            if((currentShieldTypeID += type) > LOTRShields.ShieldType.values().length - 1) {
                currentShieldTypeID = 0;
            }
            if(currentShieldTypeID < 0) {
                currentShieldTypeID = LOTRShields.ShieldType.values().length - 1;
            }
            currentShieldID = 0;
        }
        this.currentShieldType = LOTRShields.ShieldType.values()[currentShieldTypeID];
        this.currentShield = this.currentShieldType.list.get(currentShieldID);
        while(!this.currentShield.canDisplay(this.mc.thePlayer)) {
            if((shield < 0 || type != 0) && this.canGoLeft()) {
                this.updateCurrentShield(-1, 0);
                continue;
            }
            if((shield > 0 || type != 0) && this.canGoRight()) {
                this.updateCurrentShield(1, 0);
                continue;
            }
            this.updateCurrentShield(0, 1);
        }
    }

    private boolean canGoLeft() {
        for(int i = 0; i <= currentShieldID - 1; ++i) {
            LOTRShields shield = this.currentShieldType.list.get(i);
            if(!shield.canDisplay(this.mc.thePlayer)) continue;
            return true;
        }
        return false;
    }

    private boolean canGoRight() {
        for(int i = currentShieldID + 1; i <= this.currentShieldType.list.size() - 1; ++i) {
            LOTRShields shield = this.currentShieldType.list.get(i);
            if(!shield.canDisplay(this.mc.thePlayer)) continue;
            return true;
        }
        return false;
    }

    private LOTRShields getPlayerEquippedShield() {
        return LOTRLevelData.getData(this.mc.thePlayer).getShield();
    }

    @Override
    public void updateScreen() {
        boolean mouseWithinModel;
        super.updateScreen();
        this.modelRotationPrev = this.modelRotation;
        this.modelRotationPrev = MathHelper.wrapAngleTo180_float(this.modelRotationPrev);
        this.modelRotation = MathHelper.wrapAngleTo180_float(this.modelRotation);
        mouseWithinModel = Math.abs(this.mouseX - this.modelX) <= 60 && Math.abs(this.mouseY - this.modelY) <= 80;
        if(Mouse.isButtonDown(0)) {
            if(this.isMouseDown == 0 || this.isMouseDown == 1) {
                if(this.isMouseDown == 0) {
                    if(mouseWithinModel) {
                        this.isMouseDown = 1;
                    }
                }
                else if(this.mouseX != this.prevMouseX) {
                    float move = (-(this.mouseX - this.prevMouseX)) * 1.0f;
                    this.modelRotation += move;
                }
                this.prevMouseX = this.mouseX;
            }
        }
        else {
            this.isMouseDown = 0;
        }
    }

    @Override
    public void drawScreen(int i, int j, float f) {
        this.mouseX = i;
        this.mouseY = j;
        this.drawDefaultBackground();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        String s = StatCollector.translateToLocal("lotr.gui.shields.title");
        this.drawCenteredString(s, this.guiLeft + this.xSize / 2, this.guiTop - 30, 16777215);
        GL11.glEnable(2903);
        RenderHelper.enableStandardItemLighting();
        GL11.glPushMatrix();
        GL11.glDisable(2884);
        GL11.glEnable(32826);
        GL11.glEnable(3008);
        GL11.glTranslatef(this.modelX, this.modelY, 50.0f);
        float scale = 55.0f;
        GL11.glScalef(-scale, scale, scale);
        GL11.glRotatef(-30.0f, 1.0f, 0.0f, 0.0f);
        GL11.glRotatef(this.modelRotationPrev + (this.modelRotation - this.modelRotationPrev) * f, 0.0f, 1.0f, 0.0f);
        this.mc.getTextureManager().bindTexture(this.mc.thePlayer.getLocationSkin());
        playerModel.render(null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
        LOTRRenderShield.renderShield(this.currentShield, null, playerModel);
        GL11.glDisable(32826);
        GL11.glEnable(2884);
        GL11.glPopMatrix();
        RenderHelper.disableStandardItemLighting();
        OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GL11.glDisable(3553);
        OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        int x = this.guiLeft + this.xSize / 2;
        int y = this.guiTop + 145;
        s = this.currentShield.getShieldName();
        this.drawCenteredString(s, x, y, 16777215);
        y += this.fontRendererObj.FONT_HEIGHT * 2;
        List desc = this.fontRendererObj.listFormattedStringToWidth(this.currentShield.getShieldDesc(), 220);
        for(Object element : desc) {
            s = (String) element;
            this.drawCenteredString(s, x, y, 16777215);
            y += this.fontRendererObj.FONT_HEIGHT;
        }
        this.shieldLeft.enabled = this.canGoLeft();
        this.shieldSelect.enabled = this.currentShield.canPlayerWear(this.mc.thePlayer);
        this.shieldSelect.displayString = this.getPlayerEquippedShield() == this.currentShield ? StatCollector.translateToLocal("lotr.gui.shields.selected") : StatCollector.translateToLocal("lotr.gui.shields.select");
        this.shieldRight.enabled = this.canGoRight();
        this.shieldRemove.enabled = this.getPlayerEquippedShield() != null && this.getPlayerEquippedShield() == this.currentShield;
        this.changeCategory.displayString = this.currentShieldType.getDisplayName();
        super.drawScreen(i, j, f);
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if(button.enabled) {
            if(button == this.shieldLeft) {
                this.updateCurrentShield(-1, 0);
            }
            else if(button == this.shieldSelect) {
                this.updateCurrentShield(0, 0);
                LOTRPacketSelectShield packet = new LOTRPacketSelectShield(this.currentShield);
                LOTRPacketHandler.networkWrapper.sendToServer(packet);
            }
            else if(button == this.shieldRight) {
                this.updateCurrentShield(1, 0);
            }
            else if(button == this.shieldRemove) {
                this.updateCurrentShield(0, 0);
                LOTRPacketSelectShield packet = new LOTRPacketSelectShield(null);
                LOTRPacketHandler.networkWrapper.sendToServer(packet);
            }
            else if(button == this.changeCategory) {
                this.updateCurrentShield(0, 1);
            }
            else {
                super.actionPerformed(button);
            }
        }
    }

    static {
        LOTRGuiShields.playerModel.isChild = false;
    }
}
