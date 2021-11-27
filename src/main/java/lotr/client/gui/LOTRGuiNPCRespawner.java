package lotr.client.gui;

import lotr.common.entity.*;
import lotr.common.network.*;
import net.minecraft.client.gui.*;
import net.minecraft.util.*;

public class LOTRGuiNPCRespawner extends LOTRGuiScreenBase {
    private int xSize = 256;
    private int ySize = 280;
    private int guiLeft;
    private int guiTop;
    private LOTREntityNPCRespawner theSpawner;
    private GuiTextField textSpawnClass1;
    private GuiTextField textSpawnClass2;
    private LOTRGuiSlider sliderCheckHorizontal;
    private LOTRGuiSlider sliderCheckVerticalMin;
    private LOTRGuiSlider sliderCheckVerticalMax;
    private LOTRGuiSlider sliderSpawnCap;
    private LOTRGuiSlider sliderBlockEnemy;
    private LOTRGuiSlider sliderSpawnHorizontal;
    private LOTRGuiSlider sliderSpawnVerticalMin;
    private LOTRGuiSlider sliderSpawnVerticalMax;
    private LOTRGuiSlider sliderHomeRange;
    private LOTRGuiButtonOptions buttonMounts;
    private LOTRGuiSlider sliderSpawnIntervalM;
    private LOTRGuiSlider sliderSpawnIntervalS;
    private LOTRGuiSlider sliderNoPlayerRange;
    private GuiButton buttonDestroy;
    private boolean destroySpawner = false;

    public LOTRGuiNPCRespawner(LOTREntityNPCRespawner entity) {
        this.theSpawner = entity;
    }

    @Override
    public void initGui() {
        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;
        this.textSpawnClass1 = new GuiTextField(this.fontRendererObj, this.guiLeft + this.xSize / 2 - 190, this.guiTop + 35, 180, 20);
        if(this.theSpawner.spawnClass1 != null) {
            this.textSpawnClass1.setText(LOTREntities.getStringFromClass(this.theSpawner.spawnClass1));
        }
        this.textSpawnClass2 = new GuiTextField(this.fontRendererObj, this.guiLeft + this.xSize / 2 + 10, this.guiTop + 35, 180, 20);
        if(this.theSpawner.spawnClass2 != null) {
            this.textSpawnClass2.setText(LOTREntities.getStringFromClass(this.theSpawner.spawnClass2));
        }
        this.sliderCheckHorizontal = new LOTRGuiSlider(0, this.guiLeft + this.xSize / 2 - 180, this.guiTop + 70, 160, 20, StatCollector.translateToLocal("lotr.gui.npcRespawner.checkHorizontal"));
        this.buttonList.add(this.sliderCheckHorizontal);
        this.sliderCheckHorizontal.setMinMaxValues(0, 64);
        this.sliderCheckHorizontal.setSliderValue(this.theSpawner.checkHorizontalRange);
        this.sliderCheckVerticalMin = new LOTRGuiSlider(0, this.guiLeft + this.xSize / 2 - 180, this.guiTop + 95, 160, 20, StatCollector.translateToLocal("lotr.gui.npcRespawner.checkVerticalMin"));
        this.buttonList.add(this.sliderCheckVerticalMin);
        this.sliderCheckVerticalMin.setMinMaxValues(-64, 64);
        this.sliderCheckVerticalMin.setSliderValue(this.theSpawner.checkVerticalMin);
        this.sliderCheckVerticalMax = new LOTRGuiSlider(0, this.guiLeft + this.xSize / 2 - 180, this.guiTop + 120, 160, 20, StatCollector.translateToLocal("lotr.gui.npcRespawner.checkVerticalMax"));
        this.buttonList.add(this.sliderCheckVerticalMax);
        this.sliderCheckVerticalMax.setMinMaxValues(-64, 64);
        this.sliderCheckVerticalMax.setSliderValue(this.theSpawner.checkVerticalMax);
        this.sliderSpawnCap = new LOTRGuiSlider(0, this.guiLeft + this.xSize / 2 - 180, this.guiTop + 145, 160, 20, StatCollector.translateToLocal("lotr.gui.npcRespawner.spawnCap"));
        this.buttonList.add(this.sliderSpawnCap);
        this.sliderSpawnCap.setMinMaxValues(0, 64);
        this.sliderSpawnCap.setSliderValue(this.theSpawner.spawnCap);
        this.sliderBlockEnemy = new LOTRGuiSlider(0, this.guiLeft + this.xSize / 2 - 180, this.guiTop + 170, 160, 20, StatCollector.translateToLocal("lotr.gui.npcRespawner.blockEnemy"));
        this.buttonList.add(this.sliderBlockEnemy);
        this.sliderBlockEnemy.setMinMaxValues(0, 64);
        this.sliderBlockEnemy.setSliderValue(this.theSpawner.blockEnemySpawns);
        this.sliderSpawnHorizontal = new LOTRGuiSlider(0, this.guiLeft + this.xSize / 2 + 20, this.guiTop + 70, 160, 20, StatCollector.translateToLocal("lotr.gui.npcRespawner.spawnHorizontal"));
        this.buttonList.add(this.sliderSpawnHorizontal);
        this.sliderSpawnHorizontal.setMinMaxValues(0, 64);
        this.sliderSpawnHorizontal.setSliderValue(this.theSpawner.spawnHorizontalRange);
        this.sliderSpawnVerticalMin = new LOTRGuiSlider(0, this.guiLeft + this.xSize / 2 + 20, this.guiTop + 95, 160, 20, StatCollector.translateToLocal("lotr.gui.npcRespawner.spawnVerticalMin"));
        this.buttonList.add(this.sliderSpawnVerticalMin);
        this.sliderSpawnVerticalMin.setMinMaxValues(-64, 64);
        this.sliderSpawnVerticalMin.setSliderValue(this.theSpawner.spawnVerticalMin);
        this.sliderSpawnVerticalMax = new LOTRGuiSlider(0, this.guiLeft + this.xSize / 2 + 20, this.guiTop + 120, 160, 20, StatCollector.translateToLocal("lotr.gui.npcRespawner.spawnVerticalMax"));
        this.buttonList.add(this.sliderSpawnVerticalMax);
        this.sliderSpawnVerticalMax.setMinMaxValues(-64, 64);
        this.sliderSpawnVerticalMax.setSliderValue(this.theSpawner.spawnVerticalMax);
        this.sliderHomeRange = new LOTRGuiSlider(0, this.guiLeft + this.xSize / 2 + 20, this.guiTop + 145, 160, 20, StatCollector.translateToLocal("lotr.gui.npcRespawner.homeRange"));
        this.buttonList.add(this.sliderHomeRange);
        this.sliderHomeRange.setMinMaxValues(-1, 64);
        this.sliderHomeRange.setSliderValue(this.theSpawner.homeRange);
        this.buttonMounts = new LOTRGuiButtonOptions(0, this.guiLeft + this.xSize / 2 + 20, this.guiTop + 170, 160, 20, StatCollector.translateToLocal("lotr.gui.npcRespawner.mounts"));
        this.buttonList.add(this.buttonMounts);
        this.sliderSpawnIntervalM = new LOTRGuiSlider(0, this.guiLeft + this.xSize / 2 - 100 - 5, this.guiTop + 195, 100, 20, StatCollector.translateToLocal("lotr.gui.npcRespawner.spawnIntervalM"));
        this.buttonList.add(this.sliderSpawnIntervalM);
        this.sliderSpawnIntervalM.setMinMaxValues(0, 60);
        this.sliderSpawnIntervalM.setValueOnly();
        this.sliderSpawnIntervalM.setSliderValue(this.theSpawner.spawnInterval / 20 / 60);
        this.sliderSpawnIntervalS = new LOTRGuiSlider(0, this.guiLeft + this.xSize / 2 + 5, this.guiTop + 195, 100, 20, StatCollector.translateToLocal("lotr.gui.npcRespawner.spawnIntervalS"));
        this.buttonList.add(this.sliderSpawnIntervalS);
        this.sliderSpawnIntervalS.setMinMaxValues(0, 59);
        this.sliderSpawnIntervalS.setValueOnly();
        this.sliderSpawnIntervalS.setNumberDigits(2);
        this.sliderSpawnIntervalS.setSliderValue(this.theSpawner.spawnInterval / 20 % 60);
        this.sliderNoPlayerRange = new LOTRGuiSlider(0, this.guiLeft + this.xSize / 2 - 80, this.guiTop + 220, 160, 20, StatCollector.translateToLocal("lotr.gui.npcRespawner.noPlayerRange"));
        this.buttonList.add(this.sliderNoPlayerRange);
        this.sliderNoPlayerRange.setMinMaxValues(0, 64);
        this.sliderNoPlayerRange.setSliderValue(this.theSpawner.noPlayerRange);
        this.buttonDestroy = new GuiButton(0, this.guiLeft + this.xSize / 2 - 50, this.guiTop + 255, 100, 20, StatCollector.translateToLocal("lotr.gui.npcRespawner.destroy"));
        this.buttonList.add(this.buttonDestroy);
    }

    @Override
    public void drawScreen(int i, int j, float f) {
        this.drawDefaultBackground();
        String s = StatCollector.translateToLocal("lotr.gui.npcRespawner.title");
        this.fontRendererObj.drawString(s, this.guiLeft + this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, this.guiTop, 16777215);
        this.textSpawnClass1.drawTextBox();
        this.textSpawnClass2.drawTextBox();
        s = StatCollector.translateToLocal("lotr.gui.npcRespawner.spawnClass1");
        this.fontRendererObj.drawString(s, this.textSpawnClass1.xPosition + 3, this.textSpawnClass1.yPosition - this.fontRendererObj.FONT_HEIGHT - 3, 13421772);
        s = StatCollector.translateToLocal("lotr.gui.npcRespawner.spawnClass2");
        this.fontRendererObj.drawString(s, this.textSpawnClass2.xPosition + 3, this.textSpawnClass2.yPosition - this.fontRendererObj.FONT_HEIGHT - 3, 13421772);
        if(this.theSpawner.mountSetting == 0) {
            this.buttonMounts.setState(StatCollector.translateToLocal("lotr.gui.npcRespawner.mounts.0"));
        }
        else if(this.theSpawner.mountSetting == 1) {
            this.buttonMounts.setState(StatCollector.translateToLocal("lotr.gui.npcRespawner.mounts.1"));
        }
        else {
            this.buttonMounts.setState(StatCollector.translateToLocal("lotr.gui.npcRespawner.mounts.2"));
        }
        if(!this.theSpawner.blockEnemySpawns()) {
            this.sliderBlockEnemy.setOverrideStateString(StatCollector.translateToLocal("lotr.gui.npcRespawner.blockEnemy.off"));
        }
        else {
            this.sliderBlockEnemy.setOverrideStateString(null);
        }
        if(!this.theSpawner.hasHomeRange()) {
            this.sliderHomeRange.setOverrideStateString(StatCollector.translateToLocal("lotr.gui.npcRespawner.homeRange.off"));
        }
        else {
            this.sliderHomeRange.setOverrideStateString(null);
        }
        String timepre = StatCollector.translateToLocal("lotr.gui.npcRespawner.spawnInterval");
        int timepreX = this.sliderSpawnIntervalM.xPosition - 5 - this.fontRendererObj.getStringWidth(timepre);
        int timepreY = this.sliderSpawnIntervalM.yPosition + this.sliderSpawnIntervalM.height / 2 - this.fontRendererObj.FONT_HEIGHT / 2;
        this.fontRendererObj.drawString(timepre, timepreX, timepreY, 16777215);
        String timesplit = ":";
        int timesplitX = (this.sliderSpawnIntervalM.xPosition + this.sliderSpawnIntervalM.width + this.sliderSpawnIntervalS.xPosition) / 2 - this.fontRendererObj.getStringWidth(timesplit) / 2;
        int timesplitY = this.sliderSpawnIntervalM.yPosition + this.sliderSpawnIntervalM.height / 2 - this.fontRendererObj.FONT_HEIGHT / 2;
        this.fontRendererObj.drawString(timesplit, timesplitX, timesplitY, 16777215);
        super.drawScreen(i, j, f);
        this.updateSliders();
        if(this.sliderBlockEnemy.enabled && this.sliderBlockEnemy.func_146115_a() && !this.sliderBlockEnemy.dragging) {
            String tooltip = StatCollector.translateToLocal("lotr.gui.npcRespawner.blockEnemy.tooltip");
            int border = 3;
            int stringWidth = this.mc.fontRenderer.getStringWidth(tooltip);
            int stringHeight = this.mc.fontRenderer.FONT_HEIGHT;
            int offset = 10;
            Gui.drawRect(i += offset, j += offset, i + stringWidth + border * 2, j + stringHeight + border * 2, -1073741824);
            this.mc.fontRenderer.drawString(tooltip, i + border, j + border, 16777215);
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if(button instanceof LOTRGuiSlider) {
            return;
        }
        if(button.enabled) {
            if(button == this.buttonMounts) {
                this.theSpawner.toggleMountSetting();
            }
            if(button == this.buttonDestroy) {
                this.destroySpawner = true;
                this.mc.thePlayer.closeScreen();
            }
        }
    }

    private void updateSliders() {
        if(this.sliderCheckHorizontal.dragging) {
            this.theSpawner.checkHorizontalRange = this.sliderCheckHorizontal.getSliderValue();
        }
        if(this.sliderCheckVerticalMin.dragging) {
            this.theSpawner.checkVerticalMin = this.sliderCheckVerticalMin.getSliderValue();
            if(this.theSpawner.checkVerticalMax < this.theSpawner.checkVerticalMin) {
                this.theSpawner.checkVerticalMax = this.theSpawner.checkVerticalMin;
                this.sliderCheckVerticalMax.setSliderValue(this.theSpawner.checkVerticalMax);
            }
        }
        if(this.sliderCheckVerticalMax.dragging) {
            this.theSpawner.checkVerticalMax = this.sliderCheckVerticalMax.getSliderValue();
            if(this.theSpawner.checkVerticalMin > this.theSpawner.checkVerticalMax) {
                this.theSpawner.checkVerticalMin = this.theSpawner.checkVerticalMax;
                this.sliderCheckVerticalMin.setSliderValue(this.theSpawner.checkVerticalMin);
            }
        }
        if(this.sliderSpawnCap.dragging) {
            this.theSpawner.spawnCap = this.sliderSpawnCap.getSliderValue();
        }
        if(this.sliderBlockEnemy.dragging) {
            this.theSpawner.blockEnemySpawns = this.sliderBlockEnemy.getSliderValue();
        }
        if(this.sliderSpawnHorizontal.dragging) {
            this.theSpawner.spawnHorizontalRange = this.sliderSpawnHorizontal.getSliderValue();
        }
        if(this.sliderSpawnVerticalMin.dragging) {
            this.theSpawner.spawnVerticalMin = this.sliderSpawnVerticalMin.getSliderValue();
            if(this.theSpawner.spawnVerticalMax < this.theSpawner.spawnVerticalMin) {
                this.theSpawner.spawnVerticalMax = this.theSpawner.spawnVerticalMin;
                this.sliderSpawnVerticalMax.setSliderValue(this.theSpawner.spawnVerticalMax);
            }
        }
        if(this.sliderSpawnVerticalMax.dragging) {
            this.theSpawner.spawnVerticalMax = this.sliderSpawnVerticalMax.getSliderValue();
            if(this.theSpawner.spawnVerticalMin > this.theSpawner.spawnVerticalMax) {
                this.theSpawner.spawnVerticalMin = this.theSpawner.spawnVerticalMax;
                this.sliderSpawnVerticalMin.setSliderValue(this.theSpawner.spawnVerticalMin);
            }
        }
        if(this.sliderHomeRange.dragging) {
            this.theSpawner.homeRange = this.sliderHomeRange.getSliderValue();
        }
        if(this.sliderSpawnIntervalM.dragging || this.sliderSpawnIntervalS.dragging) {
            if(this.sliderSpawnIntervalM.getSliderValue() == 0) {
                int s = this.sliderSpawnIntervalS.getSliderValue();
                s = Math.max(s, 1);
                this.sliderSpawnIntervalS.setSliderValue(s);
            }
            this.theSpawner.spawnInterval = (this.sliderSpawnIntervalM.getSliderValue() * 60 + this.sliderSpawnIntervalS.getSliderValue()) * 20;
        }
        if(this.sliderNoPlayerRange.dragging) {
            this.theSpawner.noPlayerRange = this.sliderNoPlayerRange.getSliderValue();
        }
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        this.textSpawnClass1.updateCursorCounter();
        this.textSpawnClass2.updateCursorCounter();
    }

    @Override
    protected void keyTyped(char c, int i) {
        if(this.textSpawnClass1.getVisible() && this.textSpawnClass1.textboxKeyTyped(c, i)) {
            return;
        }
        if(this.textSpawnClass2.getVisible() && this.textSpawnClass2.textboxKeyTyped(c, i)) {
            return;
        }
        super.keyTyped(c, i);
    }

    @Override
    protected void mouseClicked(int i, int j, int k) {
        super.mouseClicked(i, j, k);
        this.textSpawnClass1.mouseClicked(i, j, k);
        this.textSpawnClass2.mouseClicked(i, j, k);
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        this.sendSpawnerData();
    }

    private void sendSpawnerData() {
        String s1 = this.textSpawnClass1.getText();
        String s2 = this.textSpawnClass2.getText();
        if(!StringUtils.isNullOrEmpty(s1)) {
            this.theSpawner.spawnClass1 = LOTREntities.getClassFromString(s1);
        }
        if(!StringUtils.isNullOrEmpty(s2)) {
            this.theSpawner.spawnClass2 = LOTREntities.getClassFromString(s2);
        }
        LOTRPacketEditNPCRespawner packet = new LOTRPacketEditNPCRespawner(this.theSpawner);
        packet.destroy = this.destroySpawner;
        LOTRPacketHandler.networkWrapper.sendToServer(packet);
    }
}
