package lotr.client.gui;

import org.lwjgl.opengl.GL11;

import lotr.common.LOTRSquadrons;
import lotr.common.entity.npc.*;
import lotr.common.network.*;
import net.minecraft.client.gui.*;
import net.minecraft.util.*;

public class LOTRGuiHiredWarrior
extends LOTRGuiHiredNPC {
    private static String[] pageTitles = new String[]{"overview", "options"};
    public static final int XP_COLOR = 16733440;
    private GuiButton buttonLeft;
    private GuiButton buttonRight;
    private LOTRGuiButtonOptions buttonOpenInv;
    private LOTRGuiButtonOptions buttonTeleport;
    private LOTRGuiButtonOptions buttonGuardMode;
    private LOTRGuiSlider sliderGuardRange;
    private GuiTextField squadronNameField;
    private boolean updatePage;
    private boolean sendSquadronUpdate = false;

    public LOTRGuiHiredWarrior(LOTREntityNPC npc) {
        super(npc);
    }

    @Override
    public void initGui() {
        super.initGui();
        int midX = this.guiLeft + this.xSize / 2;
        if (this.page == 0) {
            this.buttonOpenInv = new LOTRGuiButtonOptions(0, midX - 80, this.guiTop + 142, 160, 20, StatCollector.translateToLocal("lotr.gui.warrior.openInv"));
            this.buttonList.add(this.buttonOpenInv);
        } else if (this.page == 1) {
            this.buttonTeleport = new LOTRGuiButtonOptions(0, midX - 80, this.guiTop + 180, 160, 20, StatCollector.translateToLocal("lotr.gui.warrior.teleport"));
            this.buttonList.add(this.buttonTeleport);
            this.buttonGuardMode = new LOTRGuiButtonOptions(1, midX - 80, this.guiTop + 50, 160, 20, StatCollector.translateToLocal("lotr.gui.warrior.guardMode"));
            this.buttonList.add(this.buttonGuardMode);
            this.sliderGuardRange = new LOTRGuiSlider(2, midX - 80, this.guiTop + 74, 160, 20, StatCollector.translateToLocal("lotr.gui.warrior.guardRange"));
            this.buttonList.add(this.sliderGuardRange);
            this.sliderGuardRange.setMinMaxValues(LOTRHiredNPCInfo.GUARD_RANGE_MIN, LOTRHiredNPCInfo.GUARD_RANGE_MAX);
            this.sliderGuardRange.setSliderValue(this.theNPC.hiredNPCInfo.getGuardRange());
            this.squadronNameField = new GuiTextField(this.fontRendererObj, midX - 80, this.guiTop + 130, 160, 20);
            this.squadronNameField.setMaxStringLength(LOTRSquadrons.SQUADRON_LENGTH_MAX);
            String squadron = this.theNPC.hiredNPCInfo.getSquadron();
            if (!StringUtils.isNullOrEmpty(squadron)) {
                this.squadronNameField.setText(squadron);
            }
        }
        this.buttonLeft = new LOTRGuiButtonLeftRight(1000, true, this.guiLeft - 160, this.guiTop + 50, "");
        this.buttonRight = new LOTRGuiButtonLeftRight(1001, false, this.guiLeft + this.xSize + 40, this.guiTop + 50, "");
        this.buttonList.add(this.buttonLeft);
        this.buttonList.add(this.buttonRight);
        this.buttonLeft.displayString = this.page == 0 ? pageTitles[pageTitles.length - 1] : pageTitles[this.page - 1];
        this.buttonRight.displayString = this.page == pageTitles.length - 1 ? pageTitles[0] : pageTitles[this.page + 1];
        this.buttonLeft.displayString = StatCollector.translateToLocal("lotr.gui.warrior." + this.buttonLeft.displayString);
        this.buttonRight.displayString = StatCollector.translateToLocal("lotr.gui.warrior." + this.buttonRight.displayString);
    }

    protected void actionPerformed(GuiButton button) {
        if (button instanceof LOTRGuiSlider) {
            return;
        }
        if (button.enabled) {
            if (button instanceof LOTRGuiButtonLeftRight) {
                if (button == this.buttonLeft) {
                    --this.page;
                    if (this.page < 0) {
                        this.page = pageTitles.length - 1;
                    }
                } else if (button == this.buttonRight) {
                    ++this.page;
                    if (this.page >= pageTitles.length) {
                        this.page = 0;
                    }
                }
                this.buttonList.clear();
                this.updatePage = true;
            } else {
                this.sendActionPacket(button.id);
            }
        }
    }

    @Override
    public void drawScreen(int i, int j, float f) {
        super.drawScreen(i, j, f);
        if (this.page == 0) {
            int midX = this.guiLeft + this.xSize / 2;
            String s = StatCollector.translateToLocalFormatted("lotr.gui.warrior.health", Math.round(this.theNPC.getHealth()), Math.round(this.theNPC.getMaxHealth()));
            this.fontRendererObj.drawString(s, midX - this.fontRendererObj.getStringWidth(s) / 2, this.guiTop + 50, 4210752);
            s = this.theNPC.hiredNPCInfo.getStatusString();
            this.fontRendererObj.drawString(s, midX - this.fontRendererObj.getStringWidth(s) / 2, this.guiTop + 62, 4210752);
            s = StatCollector.translateToLocalFormatted("lotr.gui.warrior.level", this.theNPC.hiredNPCInfo.xpLevel);
            this.fontRendererObj.drawString(s, midX - this.fontRendererObj.getStringWidth(s) / 2, this.guiTop + 80, 4210752);
            float lvlProgress = this.theNPC.hiredNPCInfo.getProgressToNextLevel();
            String curLevel = EnumChatFormatting.BOLD + String.valueOf(this.theNPC.hiredNPCInfo.xpLevel);
            String nextLevel = EnumChatFormatting.BOLD + String.valueOf(this.theNPC.hiredNPCInfo.xpLevel + 1);
            String xpCurLevel = String.valueOf(this.theNPC.hiredNPCInfo.totalXPForLevel(this.theNPC.hiredNPCInfo.xpLevel));
            String xpNextLevel = String.valueOf(this.theNPC.hiredNPCInfo.totalXPForLevel(this.theNPC.hiredNPCInfo.xpLevel + 1));
            LOTRGuiHiredWarrior.drawRect(midX - 36, this.guiTop + 96, midX + 36, this.guiTop + 102, -16777216);
            LOTRGuiHiredWarrior.drawRect(midX - 35, this.guiTop + 97, midX + 35, this.guiTop + 101, -10658467);
            LOTRGuiHiredWarrior.drawRect(midX - 35, this.guiTop + 97, midX - 35 + (int)(lvlProgress * 70.0f), this.guiTop + 101, -43776);
            GL11.glPushMatrix();
            float scale = 0.67f;
            GL11.glScalef(scale, scale, 1.0f);
            this.fontRendererObj.drawString(curLevel, Math.round((midX - 38 - this.fontRendererObj.getStringWidth(curLevel) * scale) / scale), (int)((this.guiTop + 94) / scale), 4210752);
            this.fontRendererObj.drawString(nextLevel, Math.round((midX + 38) / scale), (int)((this.guiTop + 94) / scale), 4210752);
            this.fontRendererObj.drawString(xpCurLevel, Math.round((midX - 38 - this.fontRendererObj.getStringWidth(xpCurLevel) * scale) / scale), (int)((this.guiTop + 101) / scale), 4210752);
            this.fontRendererObj.drawString(xpNextLevel, Math.round((midX + 38) / scale), (int)((this.guiTop + 101) / scale), 4210752);
            GL11.glPopMatrix();
            s = StatCollector.translateToLocalFormatted("lotr.gui.warrior.xp", this.theNPC.hiredNPCInfo.xp);
            this.fontRendererObj.drawString(s, midX - this.fontRendererObj.getStringWidth(s) / 2, this.guiTop + 110, 4210752);
            s = StatCollector.translateToLocalFormatted("lotr.gui.warrior.kills", this.theNPC.hiredNPCInfo.mobKills);
            this.fontRendererObj.drawString(s, midX - this.fontRendererObj.getStringWidth(s) / 2, this.guiTop + 122, 4210752);
        }
        if (this.page == 1) {
            String s = StatCollector.translateToLocal("lotr.gui.warrior.squadron");
            this.fontRendererObj.drawString(s, this.squadronNameField.xPosition, this.squadronNameField.yPosition - this.fontRendererObj.FONT_HEIGHT - 3, 4210752);
            this.squadronNameField.drawTextBox();
        }
    }

    @Override
    public void updateScreen() {
        if (this.updatePage) {
            this.initGui();
            this.updatePage = false;
        }
        super.updateScreen();
        if (this.page == 1) {
            this.buttonTeleport.setState(this.theNPC.hiredNPCInfo.teleportAutomatically);
            this.buttonTeleport.enabled = !this.theNPC.hiredNPCInfo.isGuardMode();
            this.buttonGuardMode.setState(this.theNPC.hiredNPCInfo.isGuardMode());
            this.sliderGuardRange.visible = this.theNPC.hiredNPCInfo.isGuardMode();
            if (this.sliderGuardRange.dragging) {
                int i = this.sliderGuardRange.getSliderValue();
                this.theNPC.hiredNPCInfo.setGuardRange(i);
                this.sendActionPacket(this.sliderGuardRange.id, i);
            }
            this.squadronNameField.updateCursorCounter();
        }
    }

    @Override
    protected void keyTyped(char c, int i) {
        if (this.page == 1 && this.squadronNameField != null && this.squadronNameField.getVisible() && this.squadronNameField.textboxKeyTyped(c, i)) {
            this.theNPC.hiredNPCInfo.setSquadron(this.squadronNameField.getText());
            this.sendSquadronUpdate = true;
            return;
        }
        super.keyTyped(c, i);
    }

    protected void mouseClicked(int i, int j, int k) {
        super.mouseClicked(i, j, k);
        if (this.page == 1 && this.squadronNameField != null) {
            this.squadronNameField.mouseClicked(i, j, k);
        }
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        if (this.sendSquadronUpdate) {
            String squadron = this.theNPC.hiredNPCInfo.getSquadron();
            LOTRPacketNPCSquadron packet = new LOTRPacketNPCSquadron(this.theNPC, squadron);
            LOTRPacketHandler.networkWrapper.sendToServer(packet);
        }
    }
}

