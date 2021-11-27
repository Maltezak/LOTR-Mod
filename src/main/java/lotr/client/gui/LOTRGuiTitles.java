package lotr.client.gui;

import java.awt.Color;
import java.util.*;

import org.apache.commons.lang3.tuple.Pair;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import lotr.client.LOTRReflectionClient;
import lotr.common.*;
import lotr.common.network.*;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.*;

public class LOTRGuiTitles
extends LOTRGuiMenuBase {
    private LOTRTitle.PlayerTitle currentTitle;
    private List<LOTRTitle> displayedTitles = new ArrayList<>();
    private Map<LOTRTitle, Pair<Boolean, Pair<Integer, Integer>>> displayedTitleInfo = new HashMap<>();
    private LOTRTitle selectedTitle;
    private EnumChatFormatting selectedColor = EnumChatFormatting.WHITE;
    private int colorBoxWidth = 8;
    private int colorBoxGap = 4;
    private Map<EnumChatFormatting, Pair<Integer, Integer>> displayedColorBoxes = new HashMap<>();
    private GuiButton selectButton;
    private GuiButton removeButton;
    private float currentScroll = 0.0f;
    private boolean isScrolling = false;
    private boolean wasMouseDown;
    private int scrollBarWidth = 11;
    private int scrollBarHeight = 144;
    private int scrollBarX = 197 - (this.scrollBarWidth - 1) / 2;
    private int scrollBarY = 30;
    private int scrollWidgetWidth = 11;
    private int scrollWidgetHeight = 8;

    @Override
    public void initGui() {
        this.xSize = 256;
        super.initGui();
        this.selectButton = new GuiButton(0, this.guiLeft + this.xSize / 2 - 10 - 80, this.guiTop + 220, 80, 20, StatCollector.translateToLocal("lotr.gui.titles.select"));
        this.buttonList.add(this.selectButton);
        this.removeButton = new GuiButton(1, this.guiLeft + this.xSize / 2 + 10, this.guiTop + 220, 80, 20, StatCollector.translateToLocal("lotr.gui.titles.remove"));
        this.buttonList.add(this.removeButton);
        this.updateScreen();
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        this.currentTitle = LOTRLevelData.getData(this.mc.thePlayer).getPlayerTitle();
        this.displayedTitles.clear();
        ArrayList<LOTRTitle> availableTitles = new ArrayList<>();
        ArrayList<LOTRTitle> unavailableTitles = new ArrayList<>();
        for (LOTRTitle title : LOTRTitle.allTitles) {
            if (title.canPlayerUse(this.mc.thePlayer)) {
                availableTitles.add(title);
                continue;
            }
            if (!title.canDisplay(this.mc.thePlayer)) continue;
            unavailableTitles.add(title);
        }
        Comparator<LOTRTitle> sorter = LOTRTitle.createTitleSorter(this.mc.thePlayer);
        Collections.sort(availableTitles, sorter);
        Collections.sort(unavailableTitles, sorter);
        this.displayedTitles.addAll(availableTitles);
        this.displayedTitles.add(null);
        this.displayedTitles.addAll(unavailableTitles);
    }

    public void drawScreen(int i, int j, float f) {
        this.drawDefaultBackground();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.setupScrollBar(i, j);
        String s = StatCollector.translateToLocal("lotr.gui.titles.title");
        this.drawCenteredString(s, this.guiLeft + this.xSize / 2, this.guiTop - 30, 16777215);
        String titleName = this.currentTitle == null ? StatCollector.translateToLocal("lotr.gui.titles.currentTitle.none") : this.currentTitle.getTitle().getDisplayName(this.mc.thePlayer);
        EnumChatFormatting currentColor = this.currentTitle == null ? EnumChatFormatting.WHITE : this.currentTitle.getColor();
        titleName = currentColor + titleName + EnumChatFormatting.RESET;
        this.drawCenteredString(StatCollector.translateToLocalFormatted("lotr.gui.titles.currentTitle", titleName), this.guiLeft + this.xSize / 2, this.guiTop, 16777215);
        this.displayedTitleInfo.clear();
        int titleX = this.guiLeft + this.xSize / 2;
        int titleY = this.guiTop + 30;
        int yIncrement = 12;
        this.drawVerticalLine(titleX - 70, titleY - 1, titleY + yIncrement * 12, -1711276033);
        this.drawVerticalLine(titleX + 70 - 1, titleY - 1, titleY + yIncrement * 12, -1711276033);
        int size = this.displayedTitles.size();
        int min = 0 + Math.round(this.currentScroll * (size - 12));
        int max = 11 + Math.round(this.currentScroll * (size - 12));
        min = Math.max(min, 0);
        max = Math.min(max, size - 1);
        for (int index = min; index <= max; ++index) {
            String name;
            boolean isCurrentTitle;
            boolean mouseOver;
            LOTRTitle title = this.displayedTitles.get(index);
            isCurrentTitle = this.currentTitle != null && this.currentTitle.getTitle() == title;
            if (title != null) {
                name = title.getDisplayName(this.mc.thePlayer);
                if (isCurrentTitle) {
                    name = "[" + name + "]";
                    name = this.currentTitle.getColor() + name;
                }
            } else {
                name = "---";
            }
            int nameWidth = this.fontRendererObj.getStringWidth(name);
            int nameHeight = this.mc.fontRenderer.FONT_HEIGHT;
            int nameXMin = titleX - nameWidth / 2;
            int nameXMax = titleX + nameWidth / 2;
            int nameYMin = titleY;
            int nameYMax = titleY + nameHeight;
            mouseOver = i >= nameXMin && i < nameXMax && j >= nameYMin && j < nameYMax;
            if (title != null) {
                this.displayedTitleInfo.put(title, Pair.of(mouseOver, Pair.of(titleX, titleY)));
            }
            int textColor = title != null ? (title.canPlayerUse(this.mc.thePlayer) ? (mouseOver ? 16777120 : 16777215) : (mouseOver ? 12303291 : 7829367)) : 7829367;
            this.drawCenteredString(name, titleX, titleY, textColor);
            titleY += yIncrement;
        }
        this.displayedColorBoxes.clear();
        if (this.selectedTitle != null) {
            String title = this.selectedColor + this.selectedTitle.getDisplayName(this.mc.thePlayer);
            this.drawCenteredString(title, this.guiLeft + this.xSize / 2, this.guiTop + 185, 16777215);
            ArrayList<EnumChatFormatting> colorCodes = new ArrayList<>();
            for (EnumChatFormatting ecf : EnumChatFormatting.values()) {
                if (!ecf.isColor()) continue;
                colorCodes.add(ecf);
            }
            int colorX = this.guiLeft + this.xSize / 2 - (this.colorBoxWidth * colorCodes.size() + this.colorBoxGap * (colorCodes.size() - 1)) / 2;
            int colorY = this.guiTop + 200;
            for (EnumChatFormatting code : colorCodes) {
                int color = LOTRReflectionClient.getFormattingColor(code);
                float[] rgb = new Color(color).getColorComponents(null);
                GL11.glColor4f(rgb[0], rgb[1], rgb[2], 1.0f);
                boolean mouseOver = i >= colorX && i < colorX + this.colorBoxWidth && j >= colorY && j < colorY + this.colorBoxWidth;
                GL11.glDisable(3553);
                this.drawTexturedModalRect(colorX, colorY + (mouseOver ? -1 : 0), 0, 0, this.colorBoxWidth, this.colorBoxWidth);
                GL11.glEnable(3553);
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                this.displayedColorBoxes.put(code, Pair.of(colorX, colorY));
                colorX += this.colorBoxWidth + this.colorBoxGap;
            }
        }
        if (this.displayedTitles.size() > 12) {
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            int scroll = (int)(this.currentScroll * (this.scrollBarHeight - this.scrollWidgetHeight));
            int x1 = this.guiLeft + this.scrollBarX;
            int y1 = this.guiTop + this.scrollBarY + scroll;
            int x2 = x1 + this.scrollWidgetWidth;
            int y2 = y1 + this.scrollWidgetHeight;
            LOTRGuiTitles.drawRect(x1, y1, x2, y2, -1426063361);
        }
        this.selectButton.enabled = this.selectedTitle != null;
        this.removeButton.enabled = this.currentTitle != null;
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        super.drawScreen(i, j, f);
        for (Map.Entry<LOTRTitle, Pair<Boolean, Pair<Integer, Integer>>> entry : this.displayedTitleInfo.entrySet()) {
            LOTRTitle title = entry.getKey();
            String desc = title.getDescription(this.mc.thePlayer);
            titleX = (Integer)((Pair)entry.getValue().getRight()).getLeft();
            titleY = (Integer)((Pair)entry.getValue().getRight()).getRight();
            boolean mouseOver = entry.getValue().getLeft();
            if (!mouseOver) continue;
            int stringWidth = 200;
            List titleLines = this.fontRendererObj.listFormattedStringToWidth(desc, stringWidth);
            int offset = 10;
            int x = i + offset;
            int y = j + offset;
            this.func_146283_a(titleLines, x, y);
            GL11.glDisable(2896);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        }
    }

    private void setupScrollBar(int i, int j) {
        boolean isMouseDown = Mouse.isButtonDown(0);
        int i1 = this.guiLeft + this.scrollBarX;
        int j1 = this.guiTop + this.scrollBarY;
        int i2 = i1 + this.scrollBarWidth;
        int j2 = j1 + this.scrollBarHeight;
        if (!this.wasMouseDown && isMouseDown && i >= i1 && j >= j1 && i < i2 && j < j2) {
            this.isScrolling = true;
        }
        if (!isMouseDown) {
            this.isScrolling = false;
        }
        this.wasMouseDown = isMouseDown;
        if (this.isScrolling) {
            this.currentScroll = (j - j1 - this.scrollWidgetHeight / 2.0f) / ((float)(j2 - j1) - (float)this.scrollWidgetHeight);
            this.currentScroll = MathHelper.clamp_float(this.currentScroll, 0.0f, 1.0f);
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button.enabled) {
            if (button == this.selectButton && (this.currentTitle == null || this.selectedTitle != this.currentTitle.getTitle() || this.selectedColor != this.currentTitle.getColor())) {
                LOTRPacketSelectTitle packet = new LOTRPacketSelectTitle(new LOTRTitle.PlayerTitle(this.selectedTitle, this.selectedColor));
                LOTRPacketHandler.networkWrapper.sendToServer(packet);
            } else if (button == this.removeButton) {
                LOTRPacketSelectTitle packet = new LOTRPacketSelectTitle(null);
                LOTRPacketHandler.networkWrapper.sendToServer(packet);
            } else {
                super.actionPerformed(button);
            }
        }
    }

    protected void mouseClicked(int i, int j, int mouse) {
        if (mouse == 0) {
            for (Map.Entry<LOTRTitle, Pair<Boolean, Pair<Integer, Integer>>> entry : this.displayedTitleInfo.entrySet()) {
                LOTRTitle title = entry.getKey();
                boolean mouseOver = entry.getValue().getLeft();
                if (!mouseOver || !title.canPlayerUse(this.mc.thePlayer)) continue;
                this.selectedTitle = title;
                this.selectedColor = EnumChatFormatting.WHITE;
                return;
            }
            if (!this.displayedColorBoxes.isEmpty()) {
            	for (Map.Entry<EnumChatFormatting, Pair<Integer, Integer>> entry : displayedColorBoxes.entrySet()) {
					EnumChatFormatting color = entry.getKey();
					int colorX = (Integer) ((Pair) entry.getValue()).getLeft();
					int colorY = (Integer) ((Pair) entry.getValue()).getRight();
					if (i >= colorX && i < colorX + colorBoxWidth && j >= colorY && j < colorY + colorBoxWidth) {
						selectedColor = color;
						break;
					}
				}
            }
        }
        super.mouseClicked(i, j, mouse);
    }

    public void handleMouseInput() {
        super.handleMouseInput();
        int i = Mouse.getEventDWheel();
        if (i != 0) {
            i = Integer.signum(i);
            int j = this.displayedTitles.size() - 12;
            this.currentScroll -= (float)i / (float)j;
            this.currentScroll = MathHelper.clamp_float(this.currentScroll, 0.0f, 1.0f);
        }
    }
}

