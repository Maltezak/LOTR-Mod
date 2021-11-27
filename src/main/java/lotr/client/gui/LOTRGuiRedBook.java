package lotr.client.gui;

import java.util.*;

import org.apache.commons.lang3.tuple.Pair;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.google.common.collect.Lists;

import lotr.client.LOTRTextBody;
import lotr.common.*;
import lotr.common.entity.npc.LOTRSpeech;
import lotr.common.fac.LOTRAlignmentValues;
import lotr.common.network.*;
import lotr.common.quest.LOTRMiniQuest;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.*;
import net.minecraft.util.*;

public class LOTRGuiRedBook
extends LOTRGuiScreenBase {
    public static ResourceLocation guiTexture = new ResourceLocation("lotr:gui/quest/redBook.png");
    public static ResourceLocation guiTexture_miniquests = new ResourceLocation("lotr:gui/quest/redBook_miniquests.png");
    private static RenderItem renderItem = new RenderItem();
    public int xSize = 420;
    public int ySize = 256;
    private int guiLeft;
    private int guiTop;
    private int pageWidth = 186;
    private int pageTop = 18;
    private int pageBorder = 10;
    private boolean wasMouseDown;
    private int lastMouseY;
    private int scrollBarWidth = 12;
    private int scrollBarHeight = 216;
    private int scrollBarX = this.xSize / 2 + this.pageWidth;
    private int scrollBarY = 18;
    private int scrollBarActiveHeight = 203;
    private int scrollBarActiveYOffset = 3;
    private int scrollBarActiveXBorder = 1;
    private int scrollWidgetWidth = 10;
    private int scrollWidgetHeight = 17;
    private boolean mouseInScrollBar = false;
    private boolean isScrolling = false;
    private float currentScroll = 0.0f;
    private Map<LOTRMiniQuest, Pair<Integer, Integer>> displayedMiniQuests = new HashMap<>();
    private int maxDisplayedMiniQuests = 4;
    private int qPanelWidth = 170;
    private int qPanelHeight = 45;
    private int qPanelBorder = 4;
    private int qDelX = 158;
    private int qDelY = 4;
    private int qTrackX = 148;
    private int qTrackY = 4;
    private int qWidgetSize = 8;
    private int diaryWidth = 170;
    private int diaryHeight = 218;
    private int diaryX = this.xSize / 2 - this.pageBorder - this.pageWidth / 2 - this.diaryWidth / 2;
    private int diaryY = this.ySize / 2 - this.diaryHeight / 2 - 1;
    private int diaryBorder = 6;
    private boolean mouseInDiary = false;
    private boolean isDiaryScrolling = false;
    private float diaryScroll;
    private static boolean viewCompleted = false;
    private LOTRMiniQuest selectedMiniquest;
    private LOTRMiniQuest deletingMiniquest;
    private int trackTicks;
    private GuiButton buttonViewActive;
    private GuiButton buttonViewCompleted;
    private GuiButton buttonQuestDelete;
    private GuiButton buttonQuestDeleteCancel;
    public static final int textColor = 8019267;
    public static final int textColorDark = 5521198;
    public static final int textColorFaded = 9666921;
    public static final int textColorRed = 16711680;
    private static Page page;

    public void initGui() {
        if (page == null) {
            page = Page.values()[0];
        }
        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;
        int buttonX = this.guiLeft + this.xSize / 2 - this.pageBorder - this.pageWidth / 2;
        int buttonY = this.guiTop + 80;
        this.buttonViewActive = new LOTRGuiButtonRedBook(2, buttonX - 10 - 60, buttonY, 60, 20, StatCollector.translateToLocal("lotr.gui.redBook.mq.viewActive"));
        this.buttonList.add(this.buttonViewActive);
        this.buttonViewCompleted = new LOTRGuiButtonRedBook(3, buttonX + 10, buttonY, 60, 20, StatCollector.translateToLocal("lotr.gui.redBook.mq.viewComplete"));
        this.buttonList.add(this.buttonViewCompleted);
        buttonX = this.guiLeft + this.xSize / 2 + this.pageBorder + this.pageWidth / 2;
        buttonY = this.guiTop + this.ySize - 60;
        this.buttonQuestDelete = new LOTRGuiButtonRedBook(2, buttonX - 10 - 60, buttonY, 60, 20, "");
        this.buttonList.add(this.buttonQuestDelete);
        this.buttonQuestDeleteCancel = new LOTRGuiButtonRedBook(3, buttonX + 10, buttonY, 60, 20, "");
        this.buttonList.add(this.buttonQuestDeleteCancel);
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        if (this.trackTicks > 0) {
            --this.trackTicks;
        }
    }

    public void drawScreen(int i, int j, float f) {
        boolean hasQuestViewButtons;
        boolean hasQuestDeleteButtons;
        this.displayedMiniQuests.clear();
        this.setupScrollBar(i, j);
        this.drawDefaultBackground();
        this.mc.getTextureManager().bindTexture(guiTexture);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize, 512);
        int x = this.guiLeft + this.xSize / 2 - this.pageBorder - this.pageWidth / 2;
        int y = this.guiTop + 30;
        if (page == Page.MINIQUESTS && this.selectedMiniquest == null) {
            float scale = 2.0f;
            float invScale = 1.0f / scale;
            x = (int)(x * invScale);
            y = (int)(y * invScale);
            GL11.glScalef(scale, scale, scale);
            this.drawCenteredString(page.getTitle(), x, y, 8019267);
            GL11.glScalef(invScale, invScale, invScale);
            x = this.guiLeft + this.xSize / 2 - this.pageBorder - this.pageWidth / 2;
            y = this.guiTop + 50;
            if (viewCompleted) {
                this.drawCenteredString(StatCollector.translateToLocal("lotr.gui.redBook.mq.viewComplete"), x, y, 8019267);
            } else {
                this.drawCenteredString(StatCollector.translateToLocal("lotr.gui.redBook.mq.viewActive"), x, y, 8019267);
            }
        }
        if (page == Page.MINIQUESTS) {
            if (this.selectedMiniquest == null) {
                this.drawCenteredString(LOTRDate.ShireReckoning.getShireDate().getDateName(false), this.guiLeft + this.xSize / 2 - this.pageBorder - this.pageWidth / 2, this.guiTop + this.ySize - 30, 8019267);
                this.drawCenteredString(StatCollector.translateToLocalFormatted("lotr.gui.redBook.mq.numActive", this.getPlayerData().getActiveMiniQuests().size()), x, this.guiTop + 120, 8019267);
                this.drawCenteredString(StatCollector.translateToLocalFormatted("lotr.gui.redBook.mq.numComplete", this.getPlayerData().getCompletedMiniQuestsTotal()), x, this.guiTop + 140, 8019267);
            } else {
                LOTRMiniQuest quest = this.selectedMiniquest;
                this.mc.getTextureManager().bindTexture(guiTexture);
                float[] questRGB = quest.getQuestColorComponents();
                GL11.glColor4f(questRGB[0], questRGB[1], questRGB[2], 1.0f);
                x = this.guiLeft + this.diaryX;
                y = this.guiTop + this.diaryY;
                this.drawTexturedModalRect(x, y, 0, 256, this.diaryWidth, this.diaryHeight, 512);
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                int textW = this.diaryWidth - this.diaryBorder * 2;
                int textBottom = y + this.diaryHeight - this.diaryBorder;
                x += this.diaryBorder;
                y += this.diaryBorder;
                boolean completed = quest.isCompleted();
                boolean failed = !completed && quest.isFailed();
                String entityName = quest.entityName;
                quest.getFactionSubtitle();
                LOTRTextBody pageText = new LOTRTextBody(8019267);
                pageText.setTextWidth(textW);
                String[] dayYear = LOTRDate.ShireReckoning.getShireDate(quest.dateGiven).getDayAndYearNames(false);
                pageText.add(dayYear[0]);
                pageText.add(dayYear[1]);
                if (quest.biomeGiven != null) {
                    pageText.add(quest.biomeGiven.getBiomeDisplayName());
                }
                pageText.add("");
                String startQuote = LOTRSpeech.formatSpeech(quest.quoteStart, this.mc.thePlayer, null, quest.getObjectiveInSpeech());
                startQuote = StatCollector.translateToLocalFormatted("lotr.gui.redBook.mq.diary.quote", startQuote);
                pageText.add(startQuote);
                pageText.add("");
                List<String> quotesStages = quest.quotesStages;
                if (!quotesStages.isEmpty()) {
                    for (String s : quotesStages) {
                        String stageQuote = LOTRSpeech.formatSpeech(s, this.mc.thePlayer, null, quest.getObjectiveInSpeech());
                        stageQuote = StatCollector.translateToLocalFormatted("lotr.gui.redBook.mq.diary.quote", stageQuote);
                        pageText.add(stageQuote);
                        pageText.add("");
                    }
                }
                String asked = StatCollector.translateToLocalFormatted("lotr.gui.redBook.mq.diary.asked", entityName, quest.getQuestObjective());
                pageText.add(asked);
                pageText.add("");
                String progress = StatCollector.translateToLocalFormatted("lotr.gui.redBook.mq.diary.progress", quest.getQuestProgress());
                pageText.add(progress);
                if (quest.willHire) {
                    pageText.add("");
                    String willHire = StatCollector.translateToLocalFormatted("lotr.gui.redBook.mq.diary.willHire", entityName);
                    pageText.add(willHire);
                }
                if (failed) {
                    for (int l = 0; l < pageText.size(); ++l) {
                        String line = pageText.getText(l);
                        line = EnumChatFormatting.STRIKETHROUGH + line;
                        pageText.set(l, line);
                    }
                    String failureText = quest.getQuestFailure();
                    pageText.add(failureText, 16711680);
                }
                if (completed) {
                    pageText.add("");
                    pageText.addLinebreak();
                    pageText.add("");
                    dayYear = LOTRDate.ShireReckoning.getShireDate(quest.dateCompleted).getDayAndYearNames(false);
                    pageText.add(dayYear[0]);
                    pageText.add(dayYear[1]);
                    pageText.add("");
                    String completeQuote = LOTRSpeech.formatSpeech(quest.quoteComplete, this.mc.thePlayer, null, quest.getObjectiveInSpeech());
                    completeQuote = StatCollector.translateToLocalFormatted("lotr.gui.redBook.mq.diary.quote", completeQuote);
                    pageText.add(completeQuote);
                    pageText.add("");
                    String completedText = StatCollector.translateToLocal("lotr.gui.redBook.mq.diary.complete");
                    pageText.add(completedText);
                    if (quest.anyRewardsGiven()) {
                        pageText.add("");
                        String rewardText = StatCollector.translateToLocalFormatted("lotr.gui.redBook.mq.diary.reward", entityName);
                        pageText.add(rewardText);
                        if (quest.alignmentRewarded != 0.0f) {
                            String alignS = LOTRAlignmentValues.formatAlignForDisplay(quest.alignmentRewarded);
                            String alignFacName = quest.getAlignmentRewardFaction().factionName();
                            String rewardAlign = StatCollector.translateToLocalFormatted("lotr.gui.redBook.mq.diary.reward.align", alignS, alignFacName);
                            pageText.add(rewardAlign);
                        }
                        if (quest.coinsRewarded != 0.0f) {
                            String rewardCoins = StatCollector.translateToLocalFormatted("lotr.gui.redBook.mq.diary.reward.coins", quest.coinsRewarded);
                            pageText.add(rewardCoins);
                        }
                        if (!quest.itemsRewarded.isEmpty()) {
                            for (ItemStack item : quest.itemsRewarded) {
                                String rewardItem = item.getItem() instanceof ItemEditableBook ? StatCollector.translateToLocalFormatted("lotr.gui.redBook.mq.diary.reward.book", item.getDisplayName()) : StatCollector.translateToLocalFormatted("lotr.gui.redBook.mq.diary.reward.item", item.getDisplayName(), item.stackSize);
                                pageText.add(rewardItem);
                            }
                        }
                    }
                    if (quest.wasHired) {
                        pageText.add("");
                        String rewardHired = StatCollector.translateToLocalFormatted("lotr.gui.redBook.mq.diary.reward.hired", entityName);
                        pageText.add(rewardHired);
                    }
                }
                this.diaryScroll = pageText.renderAndReturnScroll(this.fontRendererObj, x, y, textBottom, this.diaryScroll);
            }
            if (this.deletingMiniquest == null) {
                List miniquests = this.getMiniQuests();
                if (!(miniquests = new ArrayList<LOTRMiniQuest>(miniquests)).isEmpty()) {
                    if (viewCompleted) {
                        miniquests = Lists.reverse(miniquests);
                    } else {
                        Collections.sort(miniquests, new LOTRMiniQuest.SorterAlphabetical());
                    }
                    int size = miniquests.size();
                    int min = 0 + Math.round(this.currentScroll * (size - this.maxDisplayedMiniQuests));
                    int max = this.maxDisplayedMiniQuests - 1 + Math.round(this.currentScroll * (size - this.maxDisplayedMiniQuests));
                    min = Math.max(min, 0);
                    max = Math.min(max, size - 1);
                    for (int index = min; index <= max; ++index) {
                        LOTRMiniQuest quest = (LOTRMiniQuest)miniquests.get(index);
                        int displayIndex = index - min;
                        int questX = this.guiLeft + this.xSize / 2 + this.pageBorder;
                        int questY = this.guiTop + this.pageTop + displayIndex * (4 + this.qPanelHeight);
                        this.renderMiniQuestPanel(quest, questX, questY, i, j);
                        this.displayedMiniQuests.put(quest, Pair.of(questX, questY));
                    }
                }
            } else {
                String deleteText = viewCompleted ? StatCollector.translateToLocal("lotr.gui.redBook.mq.deleteCmp") : StatCollector.translateToLocal("lotr.gui.redBook.mq.delete");
                List deleteTextLines = this.fontRendererObj.listFormattedStringToWidth(deleteText, this.pageWidth);
                int lineX = this.guiLeft + this.xSize / 2 + this.pageBorder + this.pageWidth / 2;
                int lineY = this.guiTop + 50;
                for (Object obj : deleteTextLines) {
                    String line = (String)obj;
                    this.drawCenteredString(line, lineX, lineY, 8019267);
                    lineY += this.fontRendererObj.FONT_HEIGHT;
                }
                int questX = this.guiLeft + this.xSize / 2 + this.pageBorder + this.pageWidth / 2 - this.qPanelWidth / 2;
                int questY = this.guiTop + this.pageTop + 80;
                this.renderMiniQuestPanel(this.deletingMiniquest, questX, questY, i, j);
            }
        }
        if (this.hasScrollBar()) {
            this.mc.getTextureManager().bindTexture(guiTexture_miniquests);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            this.drawTexturedModalRect(this.guiLeft + this.scrollBarX, this.guiTop + this.scrollBarY, 244, 0, this.scrollBarWidth, this.scrollBarHeight);
            if (this.canScroll()) {
                int scroll = (int)(this.currentScroll * (this.scrollBarActiveHeight - this.scrollWidgetHeight));
                this.drawTexturedModalRect(this.guiLeft + this.scrollBarX + this.scrollBarActiveXBorder, this.guiTop + this.scrollBarY + this.scrollBarActiveYOffset + scroll, 224, 0, this.scrollWidgetWidth, this.scrollWidgetHeight);
            } else {
                this.drawTexturedModalRect(this.guiLeft + this.scrollBarX + this.scrollBarActiveXBorder, this.guiTop + this.scrollBarY + this.scrollBarActiveYOffset, 234, 0, this.scrollWidgetWidth, this.scrollWidgetHeight);
            }
        }
        this.buttonViewActive.enabled = this.buttonViewActive.visible = (hasQuestViewButtons = page == Page.MINIQUESTS && this.selectedMiniquest == null);
        this.buttonViewCompleted.enabled = this.buttonViewCompleted.visible = hasQuestViewButtons;
        this.buttonQuestDelete.enabled = this.buttonQuestDelete.visible = (hasQuestDeleteButtons = page == Page.MINIQUESTS && this.deletingMiniquest != null);
        this.buttonQuestDeleteCancel.enabled = this.buttonQuestDeleteCancel.visible = hasQuestDeleteButtons;
        if (viewCompleted) {
            this.buttonQuestDelete.displayString = StatCollector.translateToLocal("lotr.gui.redBook.mq.deleteCmpYes");
            this.buttonQuestDeleteCancel.displayString = StatCollector.translateToLocal("lotr.gui.redBook.mq.deleteCmpNo");
        } else {
            this.buttonQuestDelete.displayString = StatCollector.translateToLocal("lotr.gui.redBook.mq.deleteYes");
            this.buttonQuestDeleteCancel.displayString = StatCollector.translateToLocal("lotr.gui.redBook.mq.deleteNo");
        }
        super.drawScreen(i, j, f);
    }

    private void renderMiniQuestPanel(LOTRMiniQuest quest, int questX, int questY, int mouseX, int mouseY) {
        GL11.glPushMatrix();
        boolean mouseInPanel = mouseX >= questX && mouseX < questX + this.qPanelWidth && mouseY >= questY && mouseY < questY + this.qPanelHeight;
        boolean mouseInDelete = mouseX >= questX + this.qDelX && mouseX < questX + this.qDelX + this.qWidgetSize && mouseY >= questY + this.qDelY && mouseY < questY + this.qDelY + this.qWidgetSize;
        boolean mouseInTrack = mouseX >= questX + this.qTrackX && mouseX < questX + this.qTrackX + this.qWidgetSize && mouseY >= questY + this.qTrackY && mouseY < questY + this.qTrackY + this.qWidgetSize;
        boolean isTracking = quest == this.getPlayerData().getTrackingMiniQuest();
        this.mc.getTextureManager().bindTexture(guiTexture_miniquests);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        if (mouseInPanel || quest == this.selectedMiniquest) {
            this.drawTexturedModalRect(questX, questY, 0, this.qPanelHeight, this.qPanelWidth, this.qPanelHeight);
        } else {
            this.drawTexturedModalRect(questX, questY, 0, 0, this.qPanelWidth, this.qPanelHeight);
        }
        float[] questRGB = quest.getQuestColorComponents();
        GL11.glColor4f(questRGB[0], questRGB[1], questRGB[2], 1.0f);
        GL11.glEnable(3008);
        this.drawTexturedModalRect(questX, questY, 0, this.qPanelHeight * 2, this.qPanelWidth, this.qPanelHeight);
        GL11.glDisable(3008);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        String questName = quest.entityName;
        String factionName = quest.getFactionSubtitle();
        if (quest.isFailed()) {
            questName = EnumChatFormatting.STRIKETHROUGH + questName;
            factionName = EnumChatFormatting.STRIKETHROUGH + factionName;
        }
        this.fontRendererObj.drawString(questName, questX + this.qPanelBorder, questY + this.qPanelBorder, 8019267);
        this.fontRendererObj.drawString(factionName, questX + this.qPanelBorder, questY + this.qPanelBorder + this.fontRendererObj.FONT_HEIGHT, 8019267);
        if (quest.isFailed()) {
            this.fontRendererObj.drawString(quest.getQuestFailureShorthand(), questX + this.qPanelBorder, questY + 25, 16711680);
        } else if (isTracking && this.trackTicks > 0) {
            this.fontRendererObj.drawString(StatCollector.translateToLocal("lotr.gui.redBook.mq.tracking"), questX + this.qPanelBorder, questY + 25, 8019267);
        } else {
            String objective = quest.getQuestObjective();
            int maxObjLength = this.qPanelWidth - this.qPanelBorder * 2 - 18;
            if (this.fontRendererObj.getStringWidth(objective) >= maxObjLength) {
                String ellipsis = "...";
                while (this.fontRendererObj.getStringWidth(objective + ellipsis) >= maxObjLength) {
                    objective = objective.substring(0, objective.length() - 1);
                    while (Character.isWhitespace(objective.charAt(objective.length() - 1))) {
                        objective = objective.substring(0, objective.length() - 1);
                    }
                }
                objective = objective + ellipsis;
            }
            this.fontRendererObj.drawString(objective, questX + this.qPanelBorder, questY + 25, 8019267);
            String progress = quest.getQuestProgress();
            if (quest.isCompleted()) {
                progress = StatCollector.translateToLocal("lotr.gui.redBook.mq.complete");
            }
            this.fontRendererObj.drawString(progress, questX + this.qPanelBorder, questY + 25 + this.fontRendererObj.FONT_HEIGHT, 8019267);
        }
        if (this.deletingMiniquest == null) {
            this.mc.getTextureManager().bindTexture(guiTexture_miniquests);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            int delU = this.qPanelWidth;
            int delV = 0;
            if (mouseInDelete) {
                delV += this.qWidgetSize;
            }
            this.drawTexturedModalRect(questX + this.qDelX, questY + this.qDelY, delU, delV, this.qWidgetSize, this.qWidgetSize);
            if (!viewCompleted) {
                int trackU = this.qPanelWidth + this.qWidgetSize;
                int trackV = 0;
                if (mouseInTrack) {
                    trackV += this.qWidgetSize;
                }
                if (isTracking) {
                    trackU += this.qWidgetSize;
                }
                this.drawTexturedModalRect(questX + this.qTrackX, questY + this.qTrackY, trackU, trackV, this.qWidgetSize, this.qWidgetSize);
            }
        }
        RenderHelper.enableGUIStandardItemLighting();
        GL11.glDisable(2896);
        GL11.glEnable(32826);
        GL11.glEnable(2896);
        GL11.glEnable(2884);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        renderItem.renderItemAndEffectIntoGUI(this.mc.fontRenderer, this.mc.getTextureManager(), quest.getQuestIcon(), questX + 149, questY + 24);
        GL11.glDisable(2896);
        GL11.glEnable(3008);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glPopMatrix();
    }

    private void setupScrollBar(int i, int j) {
        boolean isMouseDown = Mouse.isButtonDown(0);
        int i1 = i - this.guiLeft;
        int j1 = j - this.guiTop;
        this.mouseInDiary = this.selectedMiniquest != null ? i1 >= this.diaryX && i1 < this.diaryX + this.diaryWidth && j1 >= this.diaryY && j1 < this.diaryY + this.diaryHeight : false;
        this.mouseInScrollBar = i1 >= this.scrollBarX + this.scrollBarActiveXBorder && i1 < this.scrollBarX + this.scrollBarWidth - this.scrollBarActiveXBorder * 2 && j1 >= this.scrollBarY + this.scrollBarActiveYOffset && j1 < this.scrollBarY + this.scrollBarActiveYOffset + this.scrollBarActiveHeight;
        if (!this.wasMouseDown && isMouseDown) {
            if (this.mouseInScrollBar) {
                this.isScrolling = this.canScroll();
            } else if (this.mouseInDiary) {
                this.isDiaryScrolling = true;
            }
        }
        if (!isMouseDown) {
            this.isScrolling = false;
            this.isDiaryScrolling = false;
        }
        this.wasMouseDown = isMouseDown;
        if (this.isScrolling) {
            this.currentScroll = (j - (this.guiTop + this.scrollBarY + this.scrollBarActiveYOffset) - this.scrollWidgetHeight / 2.0f) / ((float)this.scrollBarActiveHeight - (float)this.scrollWidgetHeight);
            this.currentScroll = Math.max(this.currentScroll, 0.0f);
            this.currentScroll = Math.min(this.currentScroll, 1.0f);
        } else if (this.isDiaryScrolling) {
            float d = (float)(this.lastMouseY - j) / (float)this.fontRendererObj.FONT_HEIGHT;
            this.diaryScroll -= d;
        }
        this.lastMouseY = j;
    }

    private boolean hasScrollBar() {
        return page == Page.MINIQUESTS && this.deletingMiniquest == null;
    }

    private boolean canScroll() {
        return this.hasScrollBar() && this.getMiniQuests().size() > this.maxDisplayedMiniQuests;
    }

    protected void actionPerformed(GuiButton button) {
        if (button.enabled) {
            if (button == this.buttonViewActive) {
                viewCompleted = false;
            }
            if (button == this.buttonViewCompleted) {
                viewCompleted = true;
            }
            if (button == this.buttonQuestDelete && this.deletingMiniquest != null) {
                LOTRPacketDeleteMiniquest packet = new LOTRPacketDeleteMiniquest(this.deletingMiniquest);
                LOTRPacketHandler.networkWrapper.sendToServer(packet);
                this.deletingMiniquest = null;
                this.selectedMiniquest = null;
                this.diaryScroll = 0.0f;
            }
            if (button == this.buttonQuestDeleteCancel && this.deletingMiniquest != null) {
                this.deletingMiniquest = null;
            }
        }
    }

    protected void mouseClicked(int i, int j, int mouse) {
        if (mouse == 0) {
            int i1;
            int questY;
            LOTRMiniQuest quest;
            int j1;
            int i2;
            int questX;
            int j2;
            if (page == Page.MINIQUESTS && this.deletingMiniquest == null) {
                for (Map.Entry<LOTRMiniQuest, Pair<Integer, Integer>> entry : this.displayedMiniQuests.entrySet()) {
                    quest = entry.getKey();
                    questX = entry.getValue().getLeft();
                    questY = entry.getValue().getRight();
                    i1 = questX + this.qDelX;
                    j1 = questY + this.qDelY;
                    i2 = i1 + this.qWidgetSize;
                    j2 = j1 + this.qWidgetSize;
                    if (i >= i1 && j >= j1 && i < i2 && j < j2) {
                        this.selectedMiniquest = this.deletingMiniquest = quest;
                        this.diaryScroll = 0.0f;
                        return;
                    }
                    if (viewCompleted) continue;
                    i1 = questX + this.qTrackX;
                    j1 = questY + this.qTrackY;
                    i2 = i1 + this.qWidgetSize;
                    j2 = j1 + this.qWidgetSize;
                    if (i < i1 || j < j1 || i >= i2 || j >= j2) continue;
                    this.trackOrUntrack(quest);
                    return;
                }
            }
            if (page == Page.MINIQUESTS && this.deletingMiniquest == null) {
                for (Map.Entry<LOTRMiniQuest, Pair<Integer, Integer>> entry : this.displayedMiniQuests.entrySet()) {
                    quest = entry.getKey();
                    questX = entry.getValue().getLeft();
                    questY = entry.getValue().getRight();
                    i1 = questX;
                    j1 = questY;
                    i2 = i1 + this.qPanelWidth;
                    j2 = j1 + this.qPanelHeight;
                    if (i < i1 || j < j1 || i >= i2 || j >= j2) continue;
                    this.selectedMiniquest = quest;
                    this.diaryScroll = 0.0f;
                    return;
                }
                if (!this.mouseInDiary && !this.isScrolling) {
                    this.selectedMiniquest = null;
                    this.diaryScroll = 0.0f;
                }
            }
        }
        super.mouseClicked(i, j, mouse);
    }

    @Override
    protected void keyTyped(char c, int i) {
        if (i == 1 || i == this.mc.gameSettings.keyBindInventory.getKeyCode()) {
            if (this.deletingMiniquest != null) {
                this.deletingMiniquest = null;
                return;
            }
            if (this.selectedMiniquest != null) {
                this.selectedMiniquest = null;
                return;
            }
        }
        super.keyTyped(c, i);
    }

    public void handleMouseInput() {
        super.handleMouseInput();
        int i = Mouse.getEventDWheel();
        if (i != 0 && (this.canScroll() || this.mouseInDiary)) {
            if (i > 0) {
                i = 1;
            }
            if (i < 0) {
                i = -1;
            }
            if (this.mouseInDiary) {
                this.diaryScroll += i;
            } else {
                int j = this.getMiniQuests().size() - this.maxDisplayedMiniQuests;
                this.currentScroll -= (float)i / (float)j;
                this.currentScroll = Math.max(this.currentScroll, 0.0f);
                this.currentScroll = Math.min(this.currentScroll, 1.0f);
            }
        }
    }

    private LOTRPlayerData getPlayerData() {
        return LOTRLevelData.getData(this.mc.thePlayer);
    }

    private List<LOTRMiniQuest> getMiniQuests() {
        if (viewCompleted) {
            return this.getPlayerData().getMiniQuestsCompleted();
        }
        return this.getPlayerData().getMiniQuests();
    }

    private void trackOrUntrack(LOTRMiniQuest quest) {
        LOTRMiniQuest tracking = this.getPlayerData().getTrackingMiniQuest();
        LOTRMiniQuest newTracking;
        newTracking = quest == tracking ? null : quest;
        LOTRPacketMiniquestTrack packet = new LOTRPacketMiniquestTrack(newTracking);
        LOTRPacketHandler.networkWrapper.sendToServer(packet);
        this.getPlayerData().setTrackingMiniQuest(newTracking);
        this.trackTicks = 40;
    }

    private enum Page {
        MINIQUESTS("miniquests");

        private String name;

        Page(String s) {
            this.name = s;
        }

        public String getTitle() {
            return StatCollector.translateToLocal("lotr.gui.redBook.page." + this.name);
        }
    }

}

