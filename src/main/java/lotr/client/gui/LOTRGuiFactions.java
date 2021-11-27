package lotr.client.gui;

import java.util.*;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.google.common.math.IntMath;

import lotr.client.*;
import lotr.common.*;
import lotr.common.fac.*;
import lotr.common.network.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.*;

public class LOTRGuiFactions
extends LOTRGuiMenuBase {
    public static final ResourceLocation factionsTexture = new ResourceLocation("lotr:gui/factions.png");
    public static final ResourceLocation factionsTextureFull = new ResourceLocation("lotr:gui/factions_full.png");
    private static LOTRDimension currentDimension;
    private static LOTRDimension prevDimension;
    private static LOTRDimension.DimensionRegion currentRegion;
    private static LOTRDimension.DimensionRegion prevRegion;
    private static List<LOTRFaction> currentFactionList;
    private int currentFactionIndex = 0;
    private int prevFactionIndex = 0;
    private LOTRFaction currentFaction;
    private static Page currentPage;
    private int pageY = 46;
    private int pageWidth = 256;
    private int pageHeight = 128;
    private int pageBorderLeft = 16;
    private int pageBorderTop = 12;
    private int pageMapX = 159;
    private int pageMapY = 22;
    private int pageMapSize = 80;
    private LOTRGuiMap mapDrawGui;
    private GuiButton buttonRegions;
    private GuiButton buttonPagePrev;
    private GuiButton buttonPageNext;
    private GuiButton buttonFactionMap;
    private LOTRGuiButtonPledge buttonPledge;
    private LOTRGuiButtonPledge buttonPledgeConfirm;
    private LOTRGuiButtonPledge buttonPledgeRevoke;
    private float currentScroll;
    private boolean isScrolling;
    private boolean wasMouseDown;
    private int scrollBarWidth;
    private int scrollBarHeight;
    private int scrollBarX;
    private int scrollBarY;
    private int scrollBarBorder;
    private int scrollWidgetWidth;
    private int scrollWidgetHeight;
    private LOTRGuiScrollPane scrollPaneAlliesEnemies;
    private int scrollAlliesEnemiesX;
    private int numDisplayedAlliesEnemies;
    private List currentAlliesEnemies;
    private boolean isOtherPlayer;
    private String otherPlayerName;
    private Map<LOTRFaction, Float> playerAlignmentMap;
    private boolean isPledging;
    private boolean isUnpledging;

    public LOTRGuiFactions() {
        this.xSize = this.pageWidth;
        this.currentScroll = 0.0f;
        this.isScrolling = false;
        this.scrollBarWidth = 240;
        this.scrollBarHeight = 14;
        this.scrollBarX = this.xSize / 2 - this.scrollBarWidth / 2;
        this.scrollBarY = 180;
        this.scrollBarBorder = 1;
        this.scrollWidgetWidth = 17;
        this.scrollWidgetHeight = 12;
        this.scrollPaneAlliesEnemies = new LOTRGuiScrollPane(7, 7).setColors(5521198, 8019267);
        this.scrollAlliesEnemiesX = 138;
        this.isOtherPlayer = false;
        this.isPledging = false;
        this.isUnpledging = false;
        this.mapDrawGui = new LOTRGuiMap();
    }

    public void setOtherPlayer(String name, Map<LOTRFaction, Float> alignments) {
        this.isOtherPlayer = true;
        this.otherPlayerName = name;
        this.playerAlignmentMap = alignments;
    }

    public void setWorldAndResolution(Minecraft mc, int i, int j) {
        super.setWorldAndResolution(mc, i, j);
        this.mapDrawGui.setWorldAndResolution(mc, i, j);
    }

    @Override
    public void initGui() {
        super.initGui();
        if (this.isOtherPlayer) {
            this.buttonList.remove(this.buttonMenuReturn);
        }
        this.buttonRegions = new LOTRGuiButtonRedBook(0, this.guiLeft + this.xSize / 2 - 60, this.guiTop + 200, 120, 20, "");
        this.buttonList.add(this.buttonRegions);
        this.buttonPagePrev = new LOTRGuiButtonFactionsPage(1, this.guiLeft + 8, this.guiTop + this.pageY + 104, false);
        this.buttonList.add(this.buttonPagePrev);
        this.buttonPageNext = new LOTRGuiButtonFactionsPage(2, this.guiLeft + 232, this.guiTop + this.pageY + 104, true);
        this.buttonList.add(this.buttonPageNext);
        this.buttonFactionMap = new LOTRGuiButtonFactionsMap(3, this.guiLeft + this.pageMapX + this.pageMapSize - 3 - 8, this.guiTop + this.pageY + this.pageMapY + 3);
        this.buttonList.add(this.buttonFactionMap);
        this.buttonPledge = new LOTRGuiButtonPledge(this, 4, this.guiLeft + 14, this.guiTop + this.pageY + this.pageHeight - 42, "");
        this.buttonList.add(this.buttonPledge);
        this.buttonPledgeConfirm = new LOTRGuiButtonPledge(this, 5, this.guiLeft + this.pageWidth / 2 - 16, this.guiTop + this.pageY + this.pageHeight - 44, "");
        this.buttonList.add(this.buttonPledgeConfirm);
        this.buttonPledgeRevoke = new LOTRGuiButtonPledge(this, 6, this.guiLeft + this.pageWidth / 2 - 16, this.guiTop + this.pageY + this.pageHeight - 44, "");
        this.buttonList.add(this.buttonPledgeRevoke);
        this.buttonPledgeRevoke.isBroken = true;
        prevDimension = currentDimension = LOTRDimension.getCurrentDimensionWithFallback(this.mc.theWorld);
        this.currentFaction = LOTRLevelData.getData(this.mc.thePlayer).getViewingFaction();
        prevRegion = currentRegion = this.currentFaction.factionRegion;
        currentFactionList = LOTRGuiFactions.currentRegion.factionList;
        this.prevFactionIndex = this.currentFactionIndex = currentFactionList.indexOf(this.currentFaction);
        this.setCurrentScrollFromFaction();
        if (this.mc.currentScreen == this) {
            LOTRPacketClientMQEvent packet = new LOTRPacketClientMQEvent(LOTRPacketClientMQEvent.ClientMQEvent.FACTIONS);
            LOTRPacketHandler.networkWrapper.sendToServer(packet);
        }
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        this.updateCurrentDimensionAndFaction();
        LOTRPlayerData playerData = LOTRLevelData.getData(this.mc.thePlayer);
        if (this.isPledging && !playerData.hasPledgeAlignment(this.currentFaction)) {
            this.isPledging = false;
        }
        if (this.isUnpledging && !playerData.isPledgedTo(this.currentFaction)) {
            this.isUnpledging = false;
        }
    }

    private void updateCurrentDimensionAndFaction() {
        boolean changes;
        LOTRPlayerData pd = LOTRLevelData.getData(this.mc.thePlayer);
        HashMap<LOTRDimension.DimensionRegion, LOTRFaction> lastViewedRegions = new HashMap<>();
        if (this.currentFactionIndex != this.prevFactionIndex) {
            this.currentFaction = currentFactionList.get(this.currentFactionIndex);
        }
        this.prevFactionIndex = this.currentFactionIndex;
        currentDimension = LOTRDimension.getCurrentDimensionWithFallback(this.mc.theWorld);
        if (currentDimension != prevDimension) {
            currentRegion = LOTRGuiFactions.currentDimension.dimensionRegions.get(0);
        }
        if (currentRegion != prevRegion) {
            pd.setRegionLastViewedFaction(prevRegion, this.currentFaction);
            lastViewedRegions.put(prevRegion, this.currentFaction);
            currentFactionList = LOTRGuiFactions.currentRegion.factionList;
            this.currentFaction = pd.getRegionLastViewedFaction(currentRegion);
            this.prevFactionIndex = this.currentFactionIndex = currentFactionList.indexOf(this.currentFaction);
        }
        prevDimension = currentDimension;
        prevRegion = currentRegion;
        LOTRFaction prevFaction = pd.getViewingFaction();
        changes = this.currentFaction != prevFaction;
        if (changes) {
            pd.setViewingFaction(this.currentFaction);
            LOTRClientProxy.sendClientInfoPacket(this.currentFaction, lastViewedRegions);
            this.isPledging = false;
            this.isUnpledging = false;
        }
    }

    private boolean useFullPageTexture() {
        return this.isPledging || this.isUnpledging || currentPage == Page.RANKS;
    }

    public void drawScreen(int i, int j, float f) {
        List desc;
        int stringWidth;
        final LOTRPlayerData clientPD = LOTRLevelData.getData(this.mc.thePlayer);
        boolean mouseOverAlignLock = false;
        boolean mouseOverWarCrimes = false;
        if (!this.isPledging && !this.isUnpledging) {
            this.buttonPagePrev.enabled = currentPage.prev() != null;
            this.buttonPageNext.enabled = currentPage.next() != null;
            this.buttonFactionMap.enabled = currentPage != Page.RANKS && this.currentFaction.isPlayableAlignmentFaction() && LOTRDimension.getCurrentDimensionWithFallback(this.mc.theWorld) == this.currentFaction.factionDimension;
            this.buttonFactionMap.visible = this.buttonFactionMap.enabled;
            if (!LOTRFaction.controlZonesEnabled(this.mc.theWorld)) {
                this.buttonFactionMap.enabled = false;
                this.buttonFactionMap.visible = false;
            }
            if (!this.isOtherPlayer && currentPage == Page.FRONT) {
                if (clientPD.isPledgedTo(this.currentFaction)) {
                    this.buttonPledge.isBroken = this.buttonPledge.func_146115_a();
                    this.buttonPledge.enabled = true;
                    this.buttonPledge.visible = true;
                    this.buttonPledge.setDisplayLines(StatCollector.translateToLocal("lotr.gui.factions.unpledge"));
                } else {
                    this.buttonPledge.isBroken = false;
                    this.buttonPledge.visible = clientPD.getPledgeFaction() == null && this.currentFaction.isPlayableAlignmentFaction() && clientPD.getAlignment(this.currentFaction) >= 0.0f;
                    this.buttonPledge.enabled = this.buttonPledge.visible && clientPD.hasPledgeAlignment(this.currentFaction);
                    String desc1 = StatCollector.translateToLocal("lotr.gui.factions.pledge");
                    String desc2 = StatCollector.translateToLocalFormatted("lotr.gui.factions.pledgeReq", LOTRAlignmentValues.formatAlignForDisplay(this.currentFaction.getPledgeAlignment()));
                    this.buttonPledge.setDisplayLines(desc1, desc2);
                }
            } else {
                this.buttonPledge.enabled = false;
                this.buttonPledge.visible = false;
            }
            this.buttonPledgeConfirm.enabled = false;
            this.buttonPledgeConfirm.visible = false;
            this.buttonPledgeRevoke.enabled = false;
            this.buttonPledgeRevoke.visible = false;
        } else {
            this.buttonPagePrev.enabled = false;
            this.buttonPageNext.enabled = false;
            this.buttonFactionMap.enabled = false;
            this.buttonFactionMap.visible = false;
            this.buttonPledge.enabled = false;
            this.buttonPledge.visible = false;
            if (this.isPledging) {
                this.buttonPledgeConfirm.visible = true;
                this.buttonPledgeConfirm.enabled = clientPD.canMakeNewPledge() && clientPD.canPledgeTo(this.currentFaction);
                this.buttonPledgeConfirm.setDisplayLines(StatCollector.translateToLocal("lotr.gui.factions.pledge"));
                this.buttonPledgeRevoke.enabled = false;
                this.buttonPledgeRevoke.visible = false;
            } else if (this.isUnpledging) {
                this.buttonPledgeConfirm.enabled = false;
                this.buttonPledgeConfirm.visible = false;
                this.buttonPledgeRevoke.enabled = true;
                this.buttonPledgeRevoke.visible = true;
                this.buttonPledgeRevoke.setDisplayLines(StatCollector.translateToLocal("lotr.gui.factions.unpledge"));
            }
        }
        this.setupScrollBar(i, j);
        this.drawDefaultBackground();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        if (this.useFullPageTexture()) {
            this.mc.getTextureManager().bindTexture(factionsTextureFull);
        } else {
            this.mc.getTextureManager().bindTexture(factionsTexture);
        }
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop + this.pageY, 0, 0, this.pageWidth, this.pageHeight);
        String title = StatCollector.translateToLocalFormatted("lotr.gui.factions.title", currentDimension.getDimensionName());
        if (this.isOtherPlayer) {
            title = StatCollector.translateToLocalFormatted("lotr.gui.factions.titleOther", this.otherPlayerName);
        }
        this.fontRendererObj.drawString(title, this.guiLeft + this.xSize / 2 - this.fontRendererObj.getStringWidth(title) / 2, this.guiTop - 30, 16777215);
        if (currentRegion != null && LOTRGuiFactions.currentDimension.dimensionRegions.size() > 1) {
            this.buttonRegions.displayString = currentRegion.getRegionName();
            this.buttonRegions.enabled = true;
            this.buttonRegions.visible = true;
        } else {
            this.buttonRegions.displayString = "";
            this.buttonRegions.enabled = false;
            this.buttonRegions.visible = false;
        }
        if (this.currentFaction != null) {
            float alignment = this.isOtherPlayer && this.playerAlignmentMap != null ? this.playerAlignmentMap.get(this.currentFaction) : clientPD.getAlignment(this.currentFaction);
            int x = this.guiLeft + this.xSize / 2;
            int y = this.guiTop;
            LOTRTickHandlerClient.renderAlignmentBar(alignment, this.isOtherPlayer, this.currentFaction, x, y, true, false, true, true);
            String s = this.currentFaction.factionSubtitle();
            this.drawCenteredString(s, x, y += this.fontRendererObj.FONT_HEIGHT + 22, 16777215);
            y += this.fontRendererObj.FONT_HEIGHT * 3;
            if (!this.useFullPageTexture()) {
                if (this.currentFaction.factionMapInfo != null) {
                    LOTRMapRegion mapInfo = this.currentFaction.factionMapInfo;
                    int mapX = mapInfo.mapX;
                    int mapY = mapInfo.mapY;
                    int mapR = mapInfo.radius;
                    int xMin = this.guiLeft + this.pageMapX;
                    int xMax = xMin + this.pageMapSize;
                    int yMin = this.guiTop + this.pageY + this.pageMapY;
                    int yMax = yMin + this.pageMapSize;
                    int mapBorder = 1;
                    LOTRGuiFactions.drawRect(xMin - mapBorder, yMin - mapBorder, xMax + mapBorder, yMax + mapBorder, -16777216);
                    float zoom = (float)this.pageMapSize / (float)(mapR * 2);
                    float zoomExp = (float)Math.log(zoom) / (float)Math.log(2.0);
                    this.mapDrawGui.setFakeMapProperties(mapX, mapY, zoom, zoomExp, zoom);
                    int[] statics = LOTRGuiMap.setFakeStaticProperties(this.pageMapSize, this.pageMapSize, xMin, xMax, yMin, yMax);
                    this.mapDrawGui.enableZoomOutWPFading = false;
                    boolean sepia = LOTRConfig.enableSepiaMap;
                    this.mapDrawGui.renderMapAndOverlay(sepia, 1.0f, true);
                    LOTRGuiMap.setFakeStaticProperties(statics[0], statics[1], statics[2], statics[3], statics[4], statics[5]);
                }
                int wcX = this.guiLeft + this.pageMapX + 3;
                int wcY = this.guiTop + this.pageY + this.pageMapY + this.pageMapSize + 5;
                int wcWidth = 8;
                this.mc.getTextureManager().bindTexture(factionsTexture);
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                if (this.currentFaction.approvesWarCrimes) {
                    this.drawTexturedModalRect(wcX, wcY, 33, 142, wcWidth, wcWidth);
                } else {
                    this.drawTexturedModalRect(wcX, wcY, 41, 142, wcWidth, wcWidth);
                }
                if (i >= wcX && i < wcX + wcWidth && j >= wcY && j < wcY + wcWidth) {
                    mouseOverWarCrimes = true;
                }
            }
            x = this.guiLeft + this.pageBorderLeft;
            y = this.guiTop + this.pageY + this.pageBorderTop;
            if (!this.isPledging && !this.isUnpledging) {
                int index;
                if (currentPage == Page.FRONT) {
                    if (this.isOtherPlayer) {
                        s = StatCollector.translateToLocalFormatted("lotr.gui.factions.pageOther", this.otherPlayerName);
                        this.fontRendererObj.drawString(s, x, y, 8019267);
                        y += this.fontRendererObj.FONT_HEIGHT * 2;
                    }
                    String alignmentInfo = StatCollector.translateToLocal("lotr.gui.factions.alignment");
                    this.fontRendererObj.drawString(alignmentInfo, x, y, 8019267);
                    String alignmentString = LOTRAlignmentValues.formatAlignForDisplay(alignment);
                    LOTRTickHandlerClient.drawAlignmentText(this.fontRendererObj, x += this.fontRendererObj.getStringWidth(alignmentInfo) + 5, y, alignmentString, 1.0f);
                    if (clientPD.isPledgeEnemyAlignmentLimited(this.currentFaction)) {
                        this.mc.getTextureManager().bindTexture(factionsTexture);
                        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                        int lockX = x + this.fontRendererObj.getStringWidth(alignmentString) + 5;
                        int lockY = y;
                        int lockWidth = 16;
                        this.drawTexturedModalRect(lockX, lockY, 0, 200, lockWidth, lockWidth);
                        if (i >= lockX && i < lockX + lockWidth && j >= lockY && j < lockY + lockWidth) {
                            mouseOverAlignLock = true;
                        }
                    }
                    x = this.guiLeft + this.pageBorderLeft;
                    LOTRFactionRank curRank = this.currentFaction.getRank(alignment);
                    String rankName = curRank.getFullNameWithGender(clientPD);
                    rankName = StatCollector.translateToLocalFormatted("lotr.gui.factions.alignment.state", rankName);
                    this.fontRendererObj.drawString(rankName, x, y += this.fontRendererObj.FONT_HEIGHT, 8019267);
                    y += this.fontRendererObj.FONT_HEIGHT * 2;
                    if (!this.isOtherPlayer) {
                        LOTRFactionData factionData = clientPD.getFactionData(this.currentFaction);
                        if (alignment >= 0.0f) {
                            float conq;
                            s = StatCollector.translateToLocalFormatted("lotr.gui.factions.data.enemiesKilled", factionData.getEnemiesKilled());
                            this.fontRendererObj.drawString(s, x, y, 8019267);
                            s = StatCollector.translateToLocalFormatted("lotr.gui.factions.data.trades", factionData.getTradeCount());
                            this.fontRendererObj.drawString(s, x, y += this.fontRendererObj.FONT_HEIGHT, 8019267);
                            s = StatCollector.translateToLocalFormatted("lotr.gui.factions.data.hires", factionData.getHireCount());
                            this.fontRendererObj.drawString(s, x, y += this.fontRendererObj.FONT_HEIGHT, 8019267);
                            s = StatCollector.translateToLocalFormatted("lotr.gui.factions.data.miniquests", factionData.getMiniQuestsCompleted());
                            this.fontRendererObj.drawString(s, x, y += this.fontRendererObj.FONT_HEIGHT, 8019267);
                            y += this.fontRendererObj.FONT_HEIGHT;
                            if (clientPD.isPledgedTo(this.currentFaction) && (conq = factionData.getConquestEarned()) != 0.0f) {
                                int conqInt = Math.round(conq);
                                s = StatCollector.translateToLocalFormatted("lotr.gui.factions.data.conquest", conqInt);
                                this.fontRendererObj.drawString(s, x, y, 8019267);
                                y += this.fontRendererObj.FONT_HEIGHT;
                            }
                        }
                        if (alignment <= 0.0f) {
                            s = StatCollector.translateToLocalFormatted("lotr.gui.factions.data.npcsKilled", factionData.getNPCsKilled());
                            this.fontRendererObj.drawString(s, x, y, 8019267);
                            y += this.fontRendererObj.FONT_HEIGHT;
                        }
                        if (this.buttonPledge.visible && clientPD.isPledgedTo(this.currentFaction)) {
                            s = StatCollector.translateToLocal("lotr.gui.factions.pledged");
                            int px = this.buttonPledge.xPosition + this.buttonPledge.width + 8;
                            int py = this.buttonPledge.yPosition + this.buttonPledge.height / 2 - this.fontRendererObj.FONT_HEIGHT / 2;
                            this.fontRendererObj.drawString(s, px, py, 16711680);
                        }
                    }
                } else if (currentPage == Page.RANKS) {
                    LOTRFactionRank curRank = this.currentFaction.getRank(clientPD);
                    int[] minMax = this.scrollPaneAlliesEnemies.getMinMaxIndices(this.currentAlliesEnemies, this.numDisplayedAlliesEnemies);
                    for (index = minMax[0]; index <= minMax[1]; ++index) {
                        Object listObj = this.currentAlliesEnemies.get(index);
                        if (listObj instanceof String) {
                            s = (String)listObj;
                            this.fontRendererObj.drawString(s, x, y, 8019267);
                        } else if (listObj instanceof LOTRFactionRank) {
                            LOTRFactionRank rank = (LOTRFactionRank)listObj;
                            String rankName = rank.getShortNameWithGender(clientPD);
                            String rankAlign = LOTRAlignmentValues.formatAlignForDisplay(rank.alignment);
                            if (rank == LOTRFactionRank.RANK_ENEMY) {
                                rankAlign = "-";
                            }
                            boolean hiddenRankName = false;
                            if (!clientPD.isPledgedTo(this.currentFaction) && rank.alignment > this.currentFaction.getPledgeAlignment() && rank.alignment > this.currentFaction.getRankAbove(curRank).alignment) {
                                hiddenRankName = true;
                            }
                            if (hiddenRankName) {
                                rankName = StatCollector.translateToLocal("lotr.gui.factions.rank?");
                            }
                            s = StatCollector.translateToLocalFormatted("lotr.gui.factions.listRank", rankName, rankAlign);
                            if (rank == curRank) {
                                LOTRTickHandlerClient.drawAlignmentText(this.fontRendererObj, x, y, s, 1.0f);
                            } else {
                                this.fontRendererObj.drawString(s, x, y, 8019267);
                            }
                        }
                        y += this.fontRendererObj.FONT_HEIGHT;
                    }
                } else if (currentPage == Page.ALLIES || currentPage == Page.ENEMIES) {
                    int avgBgColor = LOTRTextures.computeAverageFactionPageColor(factionsTexture, 20, 20, 120, 80);
                    int[] minMax = this.scrollPaneAlliesEnemies.getMinMaxIndices(this.currentAlliesEnemies, this.numDisplayedAlliesEnemies);
                    for (index = minMax[0]; index <= minMax[1]; ++index) {
                        Object listObj = this.currentAlliesEnemies.get(index);
                        if (listObj instanceof LOTRFactionRelations.Relation) {
                            LOTRFactionRelations.Relation rel = (LOTRFactionRelations.Relation)(listObj);
                            s = StatCollector.translateToLocalFormatted("lotr.gui.factions.relationHeader", rel.getDisplayName());
                            this.fontRendererObj.drawString(s, x, y, 8019267);
                        } else if (listObj instanceof LOTRFaction) {
                            LOTRFaction fac = (LOTRFaction)(listObj);
                            s = StatCollector.translateToLocalFormatted("lotr.gui.factions.list", fac.factionName());
                            this.fontRendererObj.drawString(s, x, y, LOTRTextures.findContrastingColor(fac.getFactionColor(), avgBgColor));
                        }
                        y += this.fontRendererObj.FONT_HEIGHT;
                    }
                }
                if (this.scrollPaneAlliesEnemies.hasScrollBar) {
                    this.scrollPaneAlliesEnemies.drawScrollBar();
                }
            } else {
                int stringWidth2 = this.pageWidth - this.pageBorderLeft * 2;
                ArrayList<String> displayLines = new ArrayList<>();
                if (this.isPledging) {
                    List<LOTRFaction> facsPreventingPledge = clientPD.getFactionsPreventingPledgeTo(this.currentFaction);
                    if (facsPreventingPledge.isEmpty()) {
                        if (clientPD.canMakeNewPledge()) {
                            if (clientPD.canPledgeTo(this.currentFaction)) {
                                String desc2 = StatCollector.translateToLocalFormatted("lotr.gui.factions.pledgeDesc1", this.currentFaction.factionName());
                                displayLines.addAll(this.fontRendererObj.listFormattedStringToWidth(desc2, stringWidth2));
                                displayLines.add("");
                                desc2 = StatCollector.translateToLocalFormatted("lotr.gui.factions.pledgeDesc2");
                                displayLines.addAll(this.fontRendererObj.listFormattedStringToWidth(desc2, stringWidth2));
                            }
                        } else {
                            LOTRFaction brokenPledge = clientPD.getBrokenPledgeFaction();
                            String brokenPledgeName = brokenPledge == null ? StatCollector.translateToLocal("lotr.gui.factions.pledgeUnknown") : brokenPledge.factionName();
                            String desc3 = StatCollector.translateToLocalFormatted("lotr.gui.factions.pledgeBreakCooldown", this.currentFaction.factionName(), brokenPledgeName);
                            displayLines.addAll(this.fontRendererObj.listFormattedStringToWidth(desc3, stringWidth2));
                            displayLines.add("");
                            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                            this.mc.getTextureManager().bindTexture(factionsTexture);
                            this.drawTexturedModalRect(this.guiLeft + this.pageWidth / 2 - 97, this.guiTop + this.pageY + 56, 0, 240, 194, 16);
                            float cdFrac = (float)clientPD.getPledgeBreakCooldown() / (float)clientPD.getPledgeBreakCooldownStart();
                            this.drawTexturedModalRect(this.guiLeft + this.pageWidth / 2 - 75, this.guiTop + this.pageY + 60, 22, 232, MathHelper.ceiling_float_int(cdFrac * 150.0f), 8);
                        }
                    } else {
                        Collections.sort(facsPreventingPledge, new Comparator<LOTRFaction>(){

                            @Override
                            public int compare(LOTRFaction o1, LOTRFaction o2) {
                                float align1 = clientPD.getAlignment(o1);
                                float align2 = clientPD.getAlignment(o2);
                                return -Float.valueOf(align1).compareTo(align2);
                            }
                        });
                        String facNames = "If you are reading this, something has gone hideously wrong.";
                        if (facsPreventingPledge.size() == 1) {
                            facNames = StatCollector.translateToLocalFormatted("lotr.gui.factions.enemies1", facsPreventingPledge.get(0).factionName());
                        } else if (facsPreventingPledge.size() == 2) {
                            facNames = StatCollector.translateToLocalFormatted("lotr.gui.factions.enemies2", facsPreventingPledge.get(0).factionName(), facsPreventingPledge.get(1).factionName());
                        } else if (facsPreventingPledge.size() == 3) {
                            facNames = StatCollector.translateToLocalFormatted("lotr.gui.factions.enemies3", facsPreventingPledge.get(0).factionName(), facsPreventingPledge.get(1).factionName(), facsPreventingPledge.get(2).factionName());
                        } else if (facsPreventingPledge.size() > 3) {
                            facNames = StatCollector.translateToLocalFormatted("lotr.gui.factions.enemies3+", facsPreventingPledge.get(0).factionName(), facsPreventingPledge.get(1).factionName(), facsPreventingPledge.get(2).factionName(), facsPreventingPledge.size() - 3);
                        }
                        String desc4 = StatCollector.translateToLocalFormatted("lotr.gui.factions.pledgeEnemies", this.currentFaction.factionName(), facNames);
                        displayLines.addAll(this.fontRendererObj.listFormattedStringToWidth(desc4, stringWidth2));
                        displayLines.add("");
                    }
                } else if (this.isUnpledging) {
                    String desc5 = StatCollector.translateToLocalFormatted("lotr.gui.factions.unpledgeDesc1", this.currentFaction.factionName());
                    displayLines.addAll(this.fontRendererObj.listFormattedStringToWidth(desc5, stringWidth2));
                    displayLines.add("");
                    desc5 = StatCollector.translateToLocalFormatted("lotr.gui.factions.unpledgeDesc2");
                    displayLines.addAll(this.fontRendererObj.listFormattedStringToWidth(desc5, stringWidth2));
                }
                for (String line : displayLines) {
                    this.fontRendererObj.drawString(line, x, y, 8019267);
                    y += this.mc.fontRenderer.FONT_HEIGHT;
                }
            }
        }
        if (this.hasScrollBar()) {
            this.mc.getTextureManager().bindTexture(factionsTexture);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            this.drawTexturedModalRect(this.guiLeft + this.scrollBarX, this.guiTop + this.scrollBarY, 0, 128, this.scrollBarWidth, this.scrollBarHeight);
            int factions = currentFactionList.size();
            for (int index = 0; index < factions; ++index) {
                LOTRFaction faction = currentFactionList.get(index);
                float[] factionColors = faction.getFactionRGB();
                float shade = 0.6f;
                GL11.glColor4f(factionColors[0] * shade, factionColors[1] * shade, factionColors[2] * shade, 1.0f);
                float xMin = (float)index / (float)factions;
                float xMax = (float)(index + 1) / (float)factions;
                xMin = this.guiLeft + this.scrollBarX + this.scrollBarBorder + xMin * (this.scrollBarWidth - this.scrollBarBorder * 2);
                xMax = this.guiLeft + this.scrollBarX + this.scrollBarBorder + xMax * (this.scrollBarWidth - this.scrollBarBorder * 2);
                float yMin = this.guiTop + this.scrollBarY + this.scrollBarBorder;
                float yMax = this.guiTop + this.scrollBarY + this.scrollBarHeight - this.scrollBarBorder;
                float minU = (0 + this.scrollBarBorder) / 256.0f;
                float maxU = (0 + this.scrollBarWidth - this.scrollBarBorder) / 256.0f;
                float minV = (128 + this.scrollBarBorder) / 256.0f;
                float maxV = (128 + this.scrollBarHeight - this.scrollBarBorder) / 256.0f;
                Tessellator tessellator = Tessellator.instance;
                tessellator.startDrawingQuads();
                tessellator.addVertexWithUV(xMin, yMax, this.zLevel, minU, maxV);
                tessellator.addVertexWithUV(xMax, yMax, this.zLevel, maxU, maxV);
                tessellator.addVertexWithUV(xMax, yMin, this.zLevel, maxU, minV);
                tessellator.addVertexWithUV(xMin, yMin, this.zLevel, minU, minV);
                tessellator.draw();
            }
            this.mc.getTextureManager().bindTexture(factionsTexture);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            if (this.canScroll()) {
                int scroll = (int)(this.currentScroll * (this.scrollBarWidth - this.scrollBarBorder * 2 - this.scrollWidgetWidth));
                this.drawTexturedModalRect(this.guiLeft + this.scrollBarX + this.scrollBarBorder + scroll, this.guiTop + this.scrollBarY + this.scrollBarBorder, 0, 142, this.scrollWidgetWidth, this.scrollWidgetHeight);
            }
        }
        super.drawScreen(i, j, f);
        if (this.buttonFactionMap.enabled && this.buttonFactionMap.func_146115_a()) {
            float z = this.zLevel;
            String s = StatCollector.translateToLocal("lotr.gui.factions.viewMap");
            stringWidth = 200;
            desc = this.fontRendererObj.listFormattedStringToWidth(s, stringWidth);
            this.func_146283_a(desc, i, j);
            GL11.glDisable(2896);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            this.zLevel = z;
        }
        if (mouseOverAlignLock) {
            float z = this.zLevel;
            String alignLimit = LOTRAlignmentValues.formatAlignForDisplay(clientPD.getPledgeEnemyAlignmentLimit(this.currentFaction));
            String lockDesc = StatCollector.translateToLocalFormatted("lotr.gui.factions.pledgeLocked", alignLimit, clientPD.getPledgeFaction().factionName());
            int stringWidth3 = 200;
            List desc6 = this.fontRendererObj.listFormattedStringToWidth(lockDesc, stringWidth3);
            this.func_146283_a(desc6, i, j);
            GL11.glDisable(2896);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            this.zLevel = z;
        }
        if (mouseOverWarCrimes) {
            float z = this.zLevel;
            String warCrimes = this.currentFaction.approvesWarCrimes ? "lotr.gui.factions.warCrimesYes" : "lotr.gui.factions.warCrimesNo";
            warCrimes = StatCollector.translateToLocal(warCrimes);
            stringWidth = 200;
            desc = this.fontRendererObj.listFormattedStringToWidth(warCrimes, stringWidth);
            this.func_146283_a(desc, i, j);
            GL11.glDisable(2896);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            this.zLevel = z;
        }
    }

    private boolean hasScrollBar() {
        return currentFactionList.size() > 1;
    }

    private boolean canScroll() {
        return true;
    }

    private void setupScrollBar(int i, int j) {
        boolean isMouseDown = Mouse.isButtonDown(0);
        int i1 = this.guiLeft + this.scrollBarX;
        int j1 = this.guiTop + this.scrollBarY;
        int i2 = i1 + this.scrollBarWidth;
        int j2 = j1 + this.scrollBarHeight;
        if (!this.wasMouseDown && isMouseDown && i >= i1 && j >= j1 && i < i2 && j < j2) {
            this.isScrolling = this.canScroll();
        }
        if (!isMouseDown) {
            this.isScrolling = false;
        }
        this.wasMouseDown = isMouseDown;
        if (this.isScrolling) {
            this.currentScroll = (i - i1 - this.scrollWidgetWidth / 2.0f) / ((float)(i2 - i1) - (float)this.scrollWidgetWidth);
            this.currentScroll = MathHelper.clamp_float(this.currentScroll, 0.0f, 1.0f);
            this.currentFactionIndex = Math.round(this.currentScroll * (currentFactionList.size() - 1));
            this.scrollPaneAlliesEnemies.resetScroll();
        }
        if (currentPage == Page.ALLIES || currentPage == Page.ENEMIES || currentPage == Page.RANKS) {
            if (currentPage == Page.ALLIES) {
                List<LOTRFaction> friends;
                this.currentAlliesEnemies = new ArrayList();
                List<LOTRFaction> allies = this.currentFaction.getOthersOfRelation(LOTRFactionRelations.Relation.ALLY);
                if (!allies.isEmpty()) {
                    this.currentAlliesEnemies.add(LOTRFactionRelations.Relation.ALLY);
                    this.currentAlliesEnemies.addAll(allies);
                }
                if (!(friends = this.currentFaction.getOthersOfRelation(LOTRFactionRelations.Relation.FRIEND)).isEmpty()) {
                    if (!this.currentAlliesEnemies.isEmpty()) {
                        this.currentAlliesEnemies.add(null);
                    }
                    this.currentAlliesEnemies.add(LOTRFactionRelations.Relation.FRIEND);
                    this.currentAlliesEnemies.addAll(friends);
                }
            } else if (currentPage == Page.ENEMIES) {
                List<LOTRFaction> enemies;
                this.currentAlliesEnemies = new ArrayList();
                List<LOTRFaction> mortals = this.currentFaction.getOthersOfRelation(LOTRFactionRelations.Relation.MORTAL_ENEMY);
                if (!mortals.isEmpty()) {
                    this.currentAlliesEnemies.add(LOTRFactionRelations.Relation.MORTAL_ENEMY);
                    this.currentAlliesEnemies.addAll(mortals);
                }
                if (!(enemies = this.currentFaction.getOthersOfRelation(LOTRFactionRelations.Relation.ENEMY)).isEmpty()) {
                    if (!this.currentAlliesEnemies.isEmpty()) {
                        this.currentAlliesEnemies.add(null);
                    }
                    this.currentAlliesEnemies.add(LOTRFactionRelations.Relation.ENEMY);
                    this.currentAlliesEnemies.addAll(enemies);
                }
            } else if (currentPage == Page.RANKS) {
                this.currentAlliesEnemies = new ArrayList();
                this.currentAlliesEnemies.add(StatCollector.translateToLocal("lotr.gui.factions.rankHeader"));
                if (LOTRLevelData.getData(this.mc.thePlayer).getAlignment(this.currentFaction) <= 0.0f) {
                    this.currentAlliesEnemies.add(LOTRFactionRank.RANK_ENEMY);
                }
                LOTRFactionRank rank = LOTRFactionRank.RANK_NEUTRAL;
                do {
                    this.currentAlliesEnemies.add(rank);
                    LOTRFactionRank nextRank = this.currentFaction.getRankAbove(rank);
                    if (nextRank == null || nextRank.isDummyRank() || this.currentAlliesEnemies.contains(nextRank)) break;
                    rank = nextRank;
                } while (true);
            }
            this.scrollPaneAlliesEnemies.hasScrollBar = false;
            this.numDisplayedAlliesEnemies = this.currentAlliesEnemies.size();
            if (this.numDisplayedAlliesEnemies > 10) {
                this.numDisplayedAlliesEnemies = 10;
                this.scrollPaneAlliesEnemies.hasScrollBar = true;
            }
            this.scrollPaneAlliesEnemies.paneX0 = this.guiLeft;
            this.scrollPaneAlliesEnemies.scrollBarX0 = this.guiLeft + this.scrollAlliesEnemiesX;
            if (currentPage == Page.RANKS) {
                this.scrollPaneAlliesEnemies.scrollBarX0 += 50;
            }
            this.scrollPaneAlliesEnemies.paneY0 = this.guiTop + this.pageY + this.pageBorderTop;
            this.scrollPaneAlliesEnemies.paneY1 = this.scrollPaneAlliesEnemies.paneY0 + this.fontRendererObj.FONT_HEIGHT * this.numDisplayedAlliesEnemies;
            this.scrollPaneAlliesEnemies.mouseDragScroll(i, j);
        } else {
            this.scrollPaneAlliesEnemies.hasScrollBar = false;
            this.scrollPaneAlliesEnemies.mouseDragScroll(i, j);
        }
    }

    @Override
    protected void keyTyped(char c, int i) {
        if (i == 1 || i == this.mc.gameSettings.keyBindInventory.getKeyCode()) {
            if (this.isPledging) {
                this.isPledging = false;
                return;
            }
            if (this.isUnpledging) {
                this.isUnpledging = false;
                return;
            }
            if (this.isOtherPlayer) {
                this.mc.thePlayer.closeScreen();
                return;
            }
        }
        super.keyTyped(c, i);
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button.enabled) {
            if (button == this.buttonRegions) {
                List<LOTRDimension.DimensionRegion> regionList = LOTRGuiFactions.currentDimension.dimensionRegions;
                if (!regionList.isEmpty()) {
                    int i = regionList.indexOf(currentRegion);
                    ++i;
                    i = IntMath.mod(i, regionList.size());
                    currentRegion = regionList.get(i);
                    this.updateCurrentDimensionAndFaction();
                    this.setCurrentScrollFromFaction();
                    this.scrollPaneAlliesEnemies.resetScroll();
                    this.isPledging = false;
                    this.isUnpledging = false;
                }
            } else if (button == this.buttonPagePrev) {
                Page newPage = currentPage.prev();
                if (newPage != null) {
                    currentPage = newPage;
                    this.scrollPaneAlliesEnemies.resetScroll();
                    this.isPledging = false;
                    this.isUnpledging = false;
                }
            } else if (button == this.buttonPageNext) {
                Page newPage = currentPage.next();
                if (newPage != null) {
                    currentPage = newPage;
                    this.scrollPaneAlliesEnemies.resetScroll();
                    this.isPledging = false;
                    this.isUnpledging = false;
                }
            } else if (button == this.buttonFactionMap) {
                LOTRGuiMap factionGuiMap = new LOTRGuiMap();
                factionGuiMap.setControlZone(this.currentFaction);
                this.mc.displayGuiScreen(factionGuiMap);
            } else if (button == this.buttonPledge) {
                if (LOTRLevelData.getData(this.mc.thePlayer).isPledgedTo(this.currentFaction)) {
                    this.isUnpledging = true;
                } else {
                    this.isPledging = true;
                }
            } else if (button == this.buttonPledgeConfirm) {
                LOTRPacketPledgeSet packet = new LOTRPacketPledgeSet(this.currentFaction);
                LOTRPacketHandler.networkWrapper.sendToServer(packet);
                this.isPledging = false;
            } else if (button == this.buttonPledgeRevoke) {
                LOTRPacketPledgeSet packet = new LOTRPacketPledgeSet(null);
                LOTRPacketHandler.networkWrapper.sendToServer(packet);
                this.isUnpledging = false;
                this.mc.displayGuiScreen(null);
            } else {
                super.actionPerformed(button);
            }
        }
    }

    public void handleMouseInput() {
        super.handleMouseInput();
        int k = Mouse.getEventDWheel();
        if (k != 0) {
            k = Integer.signum(k);
            if (this.scrollPaneAlliesEnemies.hasScrollBar && this.scrollPaneAlliesEnemies.mouseOver) {
                int l = this.currentAlliesEnemies.size() - this.numDisplayedAlliesEnemies;
                this.scrollPaneAlliesEnemies.mouseWheelScroll(k, l);
            } else {
                if (k < 0) {
                    this.currentFactionIndex = Math.min(this.currentFactionIndex + 1, Math.max(0, currentFactionList.size() - 1));
                }
                if (k > 0) {
                    this.currentFactionIndex = Math.max(this.currentFactionIndex - 1, 0);
                }
                this.setCurrentScrollFromFaction();
                this.scrollPaneAlliesEnemies.resetScroll();
                this.isPledging = false;
                this.isUnpledging = false;
            }
        }
    }

    private void setCurrentScrollFromFaction() {
        this.currentScroll = (float)this.currentFactionIndex / (float)(currentFactionList.size() - 1);
    }

    public void drawButtonHoveringText(List list, int i, int j) {
        this.func_146283_a(list, i, j);
    }

    static {
        currentPage = Page.FRONT;
    }

    public enum Page {
        FRONT,
        RANKS,
        ALLIES,
        ENEMIES;


        public Page prev() {
            int i = this.ordinal();
            if (i == 0) {
                return null;
            }
            return Page.values()[--i];
        }

        public Page next() {
            int i = this.ordinal();
            if (i == Page.values().length - 1) {
                return null;
            }
            return Page.values()[++i];
        }
    }

}

