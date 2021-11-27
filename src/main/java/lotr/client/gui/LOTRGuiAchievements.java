package lotr.client.gui;

import java.util.*;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.google.common.math.IntMath;

import lotr.common.*;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.util.*;

public class LOTRGuiAchievements
extends LOTRGuiMenuBase {
    public static ResourceLocation pageTexture = new ResourceLocation("lotr:gui/achievements/page.png");
    public static ResourceLocation iconsTexture = new ResourceLocation("lotr:gui/achievements/icons.png");
    private static LOTRDimension currentDimension;
    private static LOTRDimension prevDimension;
    private static LOTRAchievement.Category currentCategory;
    private ArrayList currentCategoryTakenAchievements = new ArrayList();
    private ArrayList currentCategoryUntakenAchievements = new ArrayList();
    private int currentCategoryTakenCount;
    private int currentCategoryUntakenCount;
    private LOTRGuiButtonAchievements buttonCategoryPrev;
    private LOTRGuiButtonAchievements buttonCategoryNext;
    private int totalTakenCount;
    private int totalAvailableCount;
    private float currentScroll = 0.0f;
    private boolean isScrolling = false;
    private boolean wasMouseDown;
    private int catScrollAreaX0;
    private int catScrollAreaX1;
    private int catScrollAreaY0;
    private int catScrollAreaY1;
    private boolean wasInCategoryScrollBar;
    private int prevMouseX;
    private int mouseX;
    private int mouseY;

    @Override
    public void initGui() {
        this.xSize = 220;
        super.initGui();
        this.buttonCategoryPrev = new LOTRGuiButtonAchievements(0, true, this.guiLeft + 14, this.guiTop + 13);
        this.buttonList.add(this.buttonCategoryPrev);
        this.buttonCategoryNext = new LOTRGuiButtonAchievements(1, false, this.guiLeft + 191, this.guiTop + 13);
        this.buttonList.add(this.buttonCategoryNext);
        this.updateAchievementLists();
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        this.updateAchievementLists();
        this.prevMouseX = this.mouseX;
        this.wasInCategoryScrollBar = this.isMouseInCategoryScrollBar();
    }

    private boolean isMouseInCategoryScrollBar() {
        return Mouse.isButtonDown(0) && this.mouseX >= this.catScrollAreaX0 && this.mouseX < this.catScrollAreaX1 && this.mouseY >= this.catScrollAreaY0 && this.mouseY < this.catScrollAreaY1;
    }

    public void drawScreen(int i, int j, float f) {
        this.mouseX = i;
        this.mouseY = j;
        if (this.wasInCategoryScrollBar) {
            int diff = this.mouseX - this.prevMouseX;
            boolean changed = false;
            if (diff >= 4) {
                this.prevCategory();
                changed = true;
            } else if (diff <= -4) {
                this.nextCategory();
                changed = true;
            }
            if (changed) {
                this.mouseX = this.prevMouseX;
                this.wasInCategoryScrollBar = false;
            }
        }
        boolean isMouseDown = Mouse.isButtonDown(0);
        int scrollBarX0 = this.guiLeft + 201;
        int scrollBarX1 = scrollBarX0 + 12;
        int scrollBarY0 = this.guiTop + 48;
        int scrollBarY1 = scrollBarY0 + 200;
        if (!this.wasMouseDown && isMouseDown && i >= scrollBarX0 && i < scrollBarX1 && j >= scrollBarY0 && j < scrollBarY1) {
            this.isScrolling = this.hasScrollBar();
        }
        if (!isMouseDown) {
            this.isScrolling = false;
        }
        this.wasMouseDown = isMouseDown;
        if (this.isScrolling) {
            this.currentScroll = (j - scrollBarY0 - 8.5f) / (scrollBarY1 - scrollBarY0 - 17.0f);
            this.currentScroll = Math.max(this.currentScroll, 0.0f);
            this.currentScroll = Math.min(this.currentScroll, 1.0f);
        }
        this.drawDefaultBackground();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(pageTexture);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
        String title = StatCollector.translateToLocalFormatted("lotr.gui.achievements.title", currentDimension.getDimensionName(), this.totalTakenCount, this.totalAvailableCount);
        this.drawCenteredString(title, this.guiLeft + this.xSize / 2, this.guiTop - 30, 16777215);
        String categoryName = currentCategory.getDisplayName();
        categoryName = StatCollector.translateToLocalFormatted("lotr.gui.achievements.category", categoryName, this.currentCategoryTakenCount, this.currentCategoryTakenCount + this.currentCategoryUntakenCount);
        this.drawCenteredString(categoryName, this.guiLeft + this.xSize / 2, this.guiTop + 28, 8019267);
        this.buttonCategoryPrev.buttonCategory = this.getCategoryAtRelativeIndex(-1);
        this.buttonCategoryNext.buttonCategory = this.getCategoryAtRelativeIndex(1);
        super.drawScreen(i, j, f);
        int catScrollCentre = this.guiLeft + this.xSize / 2;
        int catScrollX = catScrollCentre - 76;
        int catScrollY = this.guiTop + 13;
        this.mc.getTextureManager().bindTexture(iconsTexture);
        this.drawTexturedModalRect(catScrollX, catScrollY, 0, 100, 152, 10);
        this.catScrollAreaX0 = catScrollX;
        this.catScrollAreaX1 = catScrollX + 152;
        this.catScrollAreaY0 = catScrollY;
        this.catScrollAreaY1 = catScrollY + 10;
        int catWidth = 16;
        int catCentreWidth = 50;
        int catsEitherSide = (this.catScrollAreaX1 - this.catScrollAreaX0) / catWidth + 1;
        for (int l = -catsEitherSide; l <= catsEitherSide; ++l) {
            int thisCatWidth = l == 0 ? catCentreWidth : catWidth;
            int catX = catScrollCentre;
            if (l != 0) {
                int signum = Integer.signum(l);
                catX += (catCentreWidth + catWidth) / 2 * signum;
                catX += (Math.abs(l) - 1) * signum * catWidth;
            }
            int catX0 = catX - thisCatWidth / 2;
            int catX1 = catX + thisCatWidth;
            if (catX0 < this.catScrollAreaX0) {
                catX0 = this.catScrollAreaX0;
            }
            if (catX1 > this.catScrollAreaX1) {
                catX1 = this.catScrollAreaX1;
            }
            int catY0 = this.catScrollAreaY0;
            int catY1 = this.catScrollAreaY1;
            LOTRAchievement.Category thisCategory = this.getCategoryAtRelativeIndex(l);
            float[] catColors = thisCategory.getCategoryRGB();
            this.mc.getTextureManager().bindTexture(iconsTexture);
            GL11.glColor4f(catColors[0], catColors[1], catColors[2], 1.0f);
            this.drawTexturedModalRect(catX0, catY0, catX0 - this.catScrollAreaX0 + 0, 100, catX1 - catX0, catY1 - catY0);
        }
        this.mc.getTextureManager().bindTexture(iconsTexture);
        this.drawTexturedModalRect(catScrollX, catScrollY, 0, 110, 152, 10);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(iconsTexture);
        if (this.hasScrollBar()) {
            int offset = (int)(this.currentScroll * 181.0f);
            this.drawTexturedModalRect(scrollBarX0, scrollBarY0 + offset, 190, 0, 10, 17);
        } else {
            this.drawTexturedModalRect(scrollBarX0, scrollBarY0, 200, 0, 10, 17);
        }
        this.drawAchievements(i, j);
    }

    private void drawAchievements(int mouseX, int mouseY) {
        RenderHelper.enableGUIStandardItemLighting();
        GL11.glDisable(2896);
        GL11.glEnable(32826);
        GL11.glEnable(2903);
        int size = this.currentCategoryTakenCount + this.currentCategoryUntakenCount;
        int min = 0 + Math.round(this.currentScroll * (size - 4));
        int max = 3 + Math.round(this.currentScroll * (size - 4));
        if (max > size - 1) {
            max = size - 1;
        }
        for (int i = min; i <= max; ++i) {
            LOTRAchievement achievement;
            boolean hasAchievement;
            if (i < this.currentCategoryTakenCount) {
                achievement = (LOTRAchievement)this.currentCategoryTakenAchievements.get(i);
                hasAchievement = true;
            } else {
                achievement = (LOTRAchievement)this.currentCategoryUntakenAchievements.get(i - this.currentCategoryTakenCount);
                hasAchievement = false;
            }
            int offset = 47 + 50 * (i - min);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            this.mc.getTextureManager().bindTexture(iconsTexture);
            this.drawTexturedModalRect(this.guiLeft + 9, this.guiTop + offset, 0, hasAchievement ? 0 : 50, 190, 50);
            int iconLeft = this.guiLeft + 12;
            int iconTop = this.guiTop + offset + 3;
            GL11.glEnable(2896);
            GL11.glEnable(2884);
            renderItem.renderItemAndEffectIntoGUI(this.mc.fontRenderer, this.mc.getTextureManager(), achievement.icon, iconLeft, iconTop);
            GL11.glDisable(2896);
            if (!hasAchievement) {
                GL11.glPushMatrix();
                GL11.glTranslatef(0.0f, 0.0f, 300.0f);
                LOTRGuiAchievements.drawRect(iconLeft, iconTop, iconLeft + 16, iconTop + 16, -2013265920);
                GL11.glPopMatrix();
            }
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            int textColour = hasAchievement ? 8019267 : 5652783;
            this.mc.fontRenderer.drawString(achievement.getTitle(this.mc.thePlayer), this.guiLeft + 33, this.guiTop + offset + 5, textColour);
            this.mc.fontRenderer.drawSplitString(achievement.getDescription(this.mc.thePlayer), this.guiLeft + 12, this.guiTop + offset + 24, 184, textColour);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            if (!hasAchievement) continue;
            this.mc.getTextureManager().bindTexture(iconsTexture);
            this.drawTexturedModalRect(this.guiLeft + 179, this.guiTop + offset + 2, 190, 17, 16, 16);
        }
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button.enabled) {
            if (button == this.buttonCategoryPrev) {
                this.prevCategory();
            } else if (button == this.buttonCategoryNext) {
                this.nextCategory();
            } else {
                super.actionPerformed(button);
            }
        }
    }

    private LOTRAchievement.Category getCategoryAtRelativeIndex(int i) {
        List<LOTRAchievement.Category> categories = LOTRGuiAchievements.currentDimension.achievementCategories;
        int index = categories.indexOf(currentCategory);
        index += i;
        index = IntMath.mod(index, LOTRGuiAchievements.currentDimension.achievementCategories.size());
        return LOTRGuiAchievements.currentDimension.achievementCategories.get(index);
    }

    private void prevCategory() {
        currentCategory = this.getCategoryAtRelativeIndex(-1);
        this.currentScroll = 0.0f;
    }

    private void nextCategory() {
        currentCategory = this.getCategoryAtRelativeIndex(1);
        this.currentScroll = 0.0f;
    }

    public void handleMouseInput() {
        super.handleMouseInput();
        int i = Mouse.getEventDWheel();
        if (i != 0 && this.hasScrollBar()) {
            int j = this.currentCategoryTakenCount + this.currentCategoryUntakenCount - 4;
            if (i > 0) {
                i = 1;
            }
            if (i < 0) {
                i = -1;
            }
            this.currentScroll = (float)(this.currentScroll - (double)i / (double)j);
            if (this.currentScroll < 0.0f) {
                this.currentScroll = 0.0f;
            }
            if (this.currentScroll > 1.0f) {
                this.currentScroll = 1.0f;
            }
        }
    }

    private boolean hasScrollBar() {
        return this.currentCategoryTakenCount + this.currentCategoryUntakenCount > 4;
    }

    private void updateAchievementLists() {
        currentDimension = LOTRDimension.getCurrentDimensionWithFallback(this.mc.theWorld);
        if (currentDimension != prevDimension) {
            currentCategory = LOTRGuiAchievements.currentDimension.achievementCategories.get(0);
        }
        prevDimension = currentDimension;
        this.currentCategoryTakenAchievements.clear();
        this.currentCategoryUntakenAchievements.clear();
        for (LOTRAchievement achievement : LOTRGuiAchievements.currentCategory.list) {
            if (!achievement.canPlayerEarn(this.mc.thePlayer)) continue;
            if (LOTRLevelData.getData(this.mc.thePlayer).hasAchievement(achievement)) {
                this.currentCategoryTakenAchievements.add(achievement);
                continue;
            }
            this.currentCategoryUntakenAchievements.add(achievement);
        }
        this.currentCategoryTakenCount = this.currentCategoryTakenAchievements.size();
        this.currentCategoryUntakenCount = this.currentCategoryUntakenAchievements.size();
        this.totalTakenCount = LOTRLevelData.getData(this.mc.thePlayer).getEarnedAchievements(currentDimension).size();
        this.totalAvailableCount = 0;
        for (LOTRAchievement achievement : LOTRGuiAchievements.currentDimension.allAchievements) {
            if (!achievement.canPlayerEarn(this.mc.thePlayer)) continue;
            ++this.totalAvailableCount;
        }
        Comparator<LOTRAchievement> sorter = LOTRAchievement.sortForDisplay(this.mc.thePlayer);
        Collections.sort(this.currentCategoryTakenAchievements, sorter);
        Collections.sort(this.currentCategoryUntakenAchievements, sorter);
    }
}

