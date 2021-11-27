package lotr.client.gui;

import lotr.common.LOTRSquadrons;
import lotr.common.entity.npc.*;
import lotr.common.network.*;
import net.minecraft.client.gui.*;
import net.minecraft.util.*;

public class LOTRGuiHiredFarmer
extends LOTRGuiHiredNPC {
    private LOTRGuiButtonOptions buttonGuardMode;
    private LOTRGuiSlider sliderGuardRange;
    private GuiTextField squadronNameField;
    private boolean sendSquadronUpdate = false;

    public LOTRGuiHiredFarmer(LOTREntityNPC npc) {
        super(npc);
    }

    @Override
    public void initGui() {
        super.initGui();
        int midX = this.guiLeft + this.xSize / 2;
        this.buttonGuardMode = new LOTRGuiButtonOptions(0, midX - 80, this.guiTop + 60, 160, 20, StatCollector.translateToLocal("lotr.gui.farmer.mode"));
        this.buttonList.add(this.buttonGuardMode);
        this.buttonGuardMode.setState(this.theNPC.hiredNPCInfo.isGuardMode());
        this.sliderGuardRange = new LOTRGuiSlider(1, midX - 80, this.guiTop + 84, 160, 20, StatCollector.translateToLocal("lotr.gui.farmer.range"));
        this.buttonList.add(this.sliderGuardRange);
        this.sliderGuardRange.setMinMaxValues(LOTRHiredNPCInfo.GUARD_RANGE_MIN, LOTRHiredNPCInfo.GUARD_RANGE_MAX);
        this.sliderGuardRange.setSliderValue(this.theNPC.hiredNPCInfo.getGuardRange());
        this.sliderGuardRange.visible = this.theNPC.hiredNPCInfo.isGuardMode();
        this.squadronNameField = new GuiTextField(this.fontRendererObj, midX - 80, this.guiTop + 120, 160, 20);
        this.squadronNameField.setMaxStringLength(LOTRSquadrons.SQUADRON_LENGTH_MAX);
        String squadron = this.theNPC.hiredNPCInfo.getSquadron();
        if (!StringUtils.isNullOrEmpty(squadron)) {
            this.squadronNameField.setText(squadron);
        }
        this.buttonList.add(new LOTRGuiButtonOptions(2, midX - 80, this.guiTop + 144, 160, 20, StatCollector.translateToLocal("lotr.gui.farmer.openInv")));
    }

    protected void actionPerformed(GuiButton button) {
        if (button instanceof LOTRGuiSlider) {
            return;
        }
        if (button.enabled) {
            this.sendActionPacket(button.id);
        }
    }

    @Override
    public void drawScreen(int i, int j, float f) {
        super.drawScreen(i, j, f);
        String s = this.theNPC.hiredNPCInfo.getStatusString();
        this.fontRendererObj.drawString(s, this.guiLeft + this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, this.guiTop + 48, 4210752);
        s = StatCollector.translateToLocal("lotr.gui.farmer.squadron");
        this.fontRendererObj.drawString(s, this.squadronNameField.xPosition, this.squadronNameField.yPosition - this.fontRendererObj.FONT_HEIGHT - 3, 4210752);
        this.squadronNameField.drawTextBox();
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        this.buttonGuardMode.setState(this.theNPC.hiredNPCInfo.isGuardMode());
        this.sliderGuardRange.visible = this.theNPC.hiredNPCInfo.isGuardMode();
        if (this.sliderGuardRange.dragging) {
            int i = this.sliderGuardRange.getSliderValue();
            this.theNPC.hiredNPCInfo.setGuardRange(i);
            this.sendActionPacket(this.sliderGuardRange.id, i);
        }
        this.squadronNameField.updateCursorCounter();
    }

    @Override
    protected void keyTyped(char c, int i) {
        if (this.squadronNameField != null && this.squadronNameField.getVisible() && this.squadronNameField.textboxKeyTyped(c, i)) {
            this.theNPC.hiredNPCInfo.setSquadron(this.squadronNameField.getText());
            this.sendSquadronUpdate = true;
            return;
        }
        super.keyTyped(c, i);
    }

    protected void mouseClicked(int i, int j, int k) {
        super.mouseClicked(i, j, k);
        if (this.squadronNameField != null) {
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

