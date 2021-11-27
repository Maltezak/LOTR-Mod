package lotr.client.gui;

import java.util.*;

import org.apache.commons.lang3.StringUtils;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import lotr.common.*;
import lotr.common.fellowship.LOTRFellowshipClient;
import lotr.common.network.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;

public class LOTRGuiFellowships
extends LOTRGuiMenuBase {
    public static final ResourceLocation iconsTextures = new ResourceLocation("lotr:gui/fellowships.png");
    private Page page = Page.LIST;
    private List<LOTRFellowshipClient> allFellowshipsLeading = new ArrayList<>();
    private List<LOTRFellowshipClient> allFellowshipsOther = new ArrayList<>();
    private List<LOTRFellowshipClient> allFellowshipInvites = new ArrayList<>();
    private LOTRFellowshipClient mouseOverFellowship;
    private LOTRFellowshipClient viewingFellowship;
    private String mouseOverPlayer;
    private boolean mouseOverPlayerRemove;
    private boolean mouseOverPlayerOp;
    private boolean mouseOverPlayerDeop;
    private boolean mouseOverPlayerTransfer;
    private String removingPlayer;
    private String oppingPlayer;
    private String deoppingPlayer;
    private String transferringPlayer;
    private boolean mouseOverInviteAccept;
    private boolean mouseOverInviteReject;
    private GuiButton buttonCreate;
    private GuiButton buttonCreateThis;
    private LOTRGuiButtonFsOption buttonInvitePlayer;
    private GuiButton buttonInviteThis;
    private LOTRGuiButtonFsOption buttonDisband;
    private GuiButton buttonDisbandThis;
    private GuiButton buttonLeave;
    private GuiButton buttonLeaveThis;
    private LOTRGuiButtonFsOption buttonSetIcon;
    private GuiButton buttonRemove;
    private GuiButton buttonTransfer;
    private LOTRGuiButtonFsOption buttonRename;
    private GuiButton buttonRenameThis;
    private GuiButton buttonBack;
    private GuiButton buttonInvites;
    private LOTRGuiButtonFsOption buttonPVP;
    private LOTRGuiButtonFsOption buttonHiredFF;
    private LOTRGuiButtonFsOption buttonMapShow;
    private GuiButton buttonOp;
    private GuiButton buttonDeop;
    private List<LOTRGuiButtonFsOption> orderedFsOptionButtons = new ArrayList<>();
    private GuiTextField textFieldName;
    private GuiTextField textFieldPlayer;
    private GuiTextField textFieldRename;
    public static final int entrySplit = 5;
    public static final int entryBorder = 10;
    public static final int selectBorder = 2;
    private int scrollWidgetWidth;
    private int scrollWidgetHeight;
    private int scrollBarX;
    private LOTRGuiScrollPane scrollPaneLeading;
    private LOTRGuiScrollPane scrollPaneOther;
    private LOTRGuiScrollPane scrollPaneMembers;
    private LOTRGuiScrollPane scrollPaneInvites;
    private int displayedFellowshipsLeading;
    private int displayedFellowshipsOther;
    private int displayedMembers;
    private int displayedInvites;

    public LOTRGuiFellowships() {
        this.xSize = 256;
        this.scrollWidgetWidth = 9;
        this.scrollWidgetHeight = 8;
        this.scrollBarX = this.xSize + 2 + 1;
        this.scrollPaneLeading = new LOTRGuiScrollPane(this.scrollWidgetWidth, this.scrollWidgetHeight);
        this.scrollPaneOther = new LOTRGuiScrollPane(this.scrollWidgetWidth, this.scrollWidgetHeight);
        this.scrollPaneMembers = new LOTRGuiScrollPane(this.scrollWidgetWidth, this.scrollWidgetHeight);
        this.scrollPaneInvites = new LOTRGuiScrollPane(this.scrollWidgetWidth, this.scrollWidgetHeight);
    }

    @Override
    public void initGui() {
        super.initGui();
        if (this.mc.thePlayer != null) {
            this.refreshFellowshipList();
        }
        int midX = this.guiLeft + this.xSize / 2;
        this.buttonCreate = new GuiButton(0, midX - 100, this.guiTop + 230, 200, 20, StatCollector.translateToLocal("lotr.gui.fellowships.create"));
        this.buttonList.add(this.buttonCreate);
        this.buttonCreateThis = new GuiButton(1, midX - 100, this.guiTop + 170, 200, 20, StatCollector.translateToLocal("lotr.gui.fellowships.createThis"));
        this.buttonList.add(this.buttonCreateThis);
        this.buttonInvitePlayer = new LOTRGuiButtonFsOption(2, midX, this.guiTop + 232, 0, 48, StatCollector.translateToLocal("lotr.gui.fellowships.invite"));
        this.buttonList.add(this.buttonInvitePlayer);
        this.buttonInviteThis = new GuiButton(3, midX - 100, this.guiTop + 170, 200, 20, StatCollector.translateToLocal("lotr.gui.fellowships.inviteThis"));
        this.buttonList.add(this.buttonInviteThis);
        this.buttonDisband = new LOTRGuiButtonFsOption(4, midX, this.guiTop + 232, 16, 48, StatCollector.translateToLocal("lotr.gui.fellowships.disband"));
        this.buttonList.add(this.buttonDisband);
        this.buttonDisbandThis = new GuiButton(5, midX - 100, this.guiTop + 170, 200, 20, StatCollector.translateToLocal("lotr.gui.fellowships.disbandThis"));
        this.buttonList.add(this.buttonDisbandThis);
        this.buttonLeave = new GuiButton(6, midX - 60, this.guiTop + 230, 120, 20, StatCollector.translateToLocal("lotr.gui.fellowships.leave"));
        this.buttonList.add(this.buttonLeave);
        this.buttonLeaveThis = new GuiButton(7, midX - 100, this.guiTop + 170, 200, 20, StatCollector.translateToLocal("lotr.gui.fellowships.leaveThis"));
        this.buttonList.add(this.buttonLeaveThis);
        this.buttonSetIcon = new LOTRGuiButtonFsOption(8, midX, this.guiTop + 232, 48, 48, StatCollector.translateToLocal("lotr.gui.fellowships.setIcon"));
        this.buttonList.add(this.buttonSetIcon);
        this.buttonRemove = new GuiButton(9, midX - 100, this.guiTop + 170, 200, 20, StatCollector.translateToLocal("lotr.gui.fellowships.remove"));
        this.buttonList.add(this.buttonRemove);
        this.buttonTransfer = new GuiButton(10, midX - 100, this.guiTop + 170, 200, 20, StatCollector.translateToLocal("lotr.gui.fellowships.transfer"));
        this.buttonList.add(this.buttonTransfer);
        this.buttonRename = new LOTRGuiButtonFsOption(11, midX, this.guiTop + 232, 32, 48, StatCollector.translateToLocal("lotr.gui.fellowships.rename"));
        this.buttonList.add(this.buttonRename);
        this.buttonRenameThis = new GuiButton(12, midX - 100, this.guiTop + 170, 200, 20, StatCollector.translateToLocal("lotr.gui.fellowships.renameThis"));
        this.buttonList.add(this.buttonRenameThis);
        this.buttonBack = new GuiButton(13, this.guiLeft - 10, this.guiTop, 20, 20, "<");
        this.buttonList.add(this.buttonBack);
        this.buttonInvites = new LOTRGuiButtonFsInvites(14, this.guiLeft + this.xSize - 16, this.guiTop, "");
        this.buttonList.add(this.buttonInvites);
        this.buttonPVP = new LOTRGuiButtonFsOption(15, midX, this.guiTop + 232, 64, 48, StatCollector.translateToLocal("lotr.gui.fellowships.togglePVP"));
        this.buttonList.add(this.buttonPVP);
        this.buttonHiredFF = new LOTRGuiButtonFsOption(16, midX, this.guiTop + 232, 80, 48, StatCollector.translateToLocal("lotr.gui.fellowships.toggleHiredFF"));
        this.buttonList.add(this.buttonHiredFF);
        this.buttonMapShow = new LOTRGuiButtonFsOption(17, midX, this.guiTop + 232, 96, 48, StatCollector.translateToLocal("lotr.gui.fellowships.toggleMapShow"));
        this.buttonList.add(this.buttonMapShow);
        this.buttonOp = new GuiButton(18, midX - 100, this.guiTop + 170, 200, 20, StatCollector.translateToLocal("lotr.gui.fellowships.op"));
        this.buttonList.add(this.buttonOp);
        this.buttonDeop = new GuiButton(19, midX - 100, this.guiTop + 170, 200, 20, StatCollector.translateToLocal("lotr.gui.fellowships.deop"));
        this.buttonList.add(this.buttonDeop);
        this.orderedFsOptionButtons.clear();
        this.orderedFsOptionButtons.add(this.buttonInvitePlayer);
        this.orderedFsOptionButtons.add(this.buttonDisband);
        this.orderedFsOptionButtons.add(this.buttonRename);
        this.orderedFsOptionButtons.add(this.buttonSetIcon);
        this.orderedFsOptionButtons.add(this.buttonMapShow);
        this.orderedFsOptionButtons.add(this.buttonPVP);
        this.orderedFsOptionButtons.add(this.buttonHiredFF);
        this.textFieldName = new GuiTextField(this.fontRendererObj, midX - 80, this.guiTop + 40, 160, 20);
        this.textFieldName.setMaxStringLength(40);
        this.textFieldPlayer = new GuiTextField(this.fontRendererObj, midX - 80, this.guiTop + 40, 160, 20);
        this.textFieldRename = new GuiTextField(this.fontRendererObj, midX - 80, this.guiTop + 40, 160, 20);
        this.textFieldRename.setMaxStringLength(40);
    }

    private void refreshFellowshipList() {
        this.allFellowshipsLeading.clear();
        this.allFellowshipsOther.clear();
        ArrayList<LOTRFellowshipClient> fellowships = new ArrayList<>(LOTRLevelData.getData(this.mc.thePlayer).getClientFellowships());
        for (LOTRFellowshipClient fs : fellowships) {
            if (fs.isOwned()) {
                this.allFellowshipsLeading.add(fs);
                continue;
            }
            this.allFellowshipsOther.add(fs);
        }
        this.allFellowshipInvites.clear();
        this.allFellowshipInvites.addAll(LOTRLevelData.getData(this.mc.thePlayer).getClientFellowshipInvites());
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        this.refreshFellowshipList();
        this.textFieldName.updateCursorCounter();
        if (this.page != Page.CREATE) {
            this.textFieldName.setText("");
        }
        this.textFieldPlayer.updateCursorCounter();
        if (this.page != Page.INVITE) {
            this.textFieldPlayer.setText("");
        }
        this.textFieldRename.updateCursorCounter();
        if (this.page != Page.RENAME) {
            this.textFieldRename.setText("");
        }
    }

    private void alignOptionButtons() {
        ArrayList<GuiButton> activeOptionButtons = new ArrayList<>();
        for (GuiButton button : this.orderedFsOptionButtons) {
            if (!button.visible) continue;
            activeOptionButtons.add(button);
        }
        if (this.buttonLeave.visible) {
            activeOptionButtons.add(this.buttonLeave);
        }
        int midX = this.guiLeft + this.xSize / 2;
        int numActive = activeOptionButtons.size();
        if (numActive > 0) {
            int gap = 8;
            int allWidth = 0;
            for (GuiButton button : activeOptionButtons) {
                if (allWidth > 0) {
                    allWidth += gap;
                }
                allWidth += button.width;
            }
            int x = midX - allWidth / 2;
            for (int i = 0; i < activeOptionButtons.size(); ++i) {
                GuiButton button = activeOptionButtons.get(i);
                button.xPosition = x;
                x += button.width;
                x += gap;
            }
        }
    }

    public void drawScreen(int i, int j, float f) {
        LOTRPlayerData playerData = LOTRLevelData.getData(this.mc.thePlayer);
        boolean viewingOwned = this.viewingFellowship != null && this.viewingFellowship.isOwned();
        boolean viewingAdminned = this.viewingFellowship != null && this.viewingFellowship.isAdminned();
        this.mouseOverFellowship = null;
        this.mouseOverPlayer = null;
        this.mouseOverPlayerRemove = false;
        this.mouseOverPlayerOp = false;
        this.mouseOverPlayerDeop = false;
        this.mouseOverPlayerTransfer = false;
        if (this.page != Page.REMOVE) {
            this.removingPlayer = null;
        }
        if (this.page != Page.OP) {
            this.oppingPlayer = null;
        }
        if (this.page != Page.DEOP) {
            this.deoppingPlayer = null;
        }
        if (this.page != Page.TRANSFER) {
            this.transferringPlayer = null;
        }
        this.mouseOverInviteAccept = false;
        this.mouseOverInviteReject = false;
        boolean creationEnabled = LOTRConfig.isFellowshipCreationEnabled(this.mc.theWorld);
        boolean canPlayerCreateNew = playerData.canCreateFellowships(true);
        this.buttonCreate.visible = this.page == Page.LIST;
        this.buttonCreate.enabled = this.buttonCreate.visible && creationEnabled && canPlayerCreateNew;
        this.buttonCreateThis.visible = this.page == Page.CREATE;
        String checkValidName = this.checkValidFellowshipName(this.textFieldName.getText());
        this.buttonCreateThis.enabled = this.buttonCreateThis.visible && checkValidName == null;
        this.buttonInvitePlayer.enabled = this.page == Page.FELLOWSHIP && (viewingOwned || viewingAdminned);
        this.buttonInvitePlayer.visible = this.buttonInvitePlayer.enabled;
        this.buttonInviteThis.visible = this.page == Page.INVITE;
        String checkValidPlayer = this.checkValidPlayerName(this.textFieldPlayer.getText());
        this.buttonInviteThis.enabled = this.buttonInviteThis.visible && checkValidPlayer == null;
        this.buttonDisband.enabled = this.page == Page.FELLOWSHIP && viewingOwned;
        this.buttonDisband.visible = this.buttonDisband.enabled;
        this.buttonDisbandThis.enabled = this.page == Page.DISBAND;
        this.buttonDisbandThis.visible = this.buttonDisbandThis.enabled;
        this.buttonLeave.enabled = this.page == Page.FELLOWSHIP && !viewingOwned;
        this.buttonLeave.visible = this.buttonLeave.enabled;
        this.buttonLeaveThis.enabled = this.page == Page.LEAVE;
        this.buttonLeaveThis.visible = this.buttonLeaveThis.enabled;
        this.buttonSetIcon.enabled = this.page == Page.FELLOWSHIP && (viewingOwned || viewingAdminned);
        this.buttonSetIcon.visible = this.buttonSetIcon.enabled;
        this.buttonRemove.enabled = this.page == Page.REMOVE;
        this.buttonRemove.visible = this.buttonRemove.enabled;
        this.buttonTransfer.enabled = this.page == Page.TRANSFER;
        this.buttonTransfer.visible = this.buttonTransfer.enabled;
        this.buttonRename.enabled = this.page == Page.FELLOWSHIP && viewingOwned;
        this.buttonRename.visible = this.buttonRename.enabled;
        this.buttonRenameThis.visible = this.page == Page.RENAME;
        String checkValidRename = this.checkValidFellowshipName(this.textFieldRename.getText());
        this.buttonRenameThis.enabled = this.buttonRenameThis.visible && checkValidRename == null;
        this.buttonBack.enabled = this.page != Page.LIST;
        this.buttonBack.visible = this.buttonBack.enabled;
        this.buttonInvites.enabled = this.page == Page.LIST;
        this.buttonInvites.visible = this.buttonInvites.enabled;
        this.buttonPVP.enabled = this.page == Page.FELLOWSHIP && (viewingOwned || viewingAdminned);
        this.buttonPVP.visible = this.buttonPVP.enabled;
        if (this.buttonPVP.enabled) {
            this.buttonPVP.setIconUV(64, this.viewingFellowship.getPreventPVP() ? 80 : 48);
        }
        this.buttonHiredFF.enabled = this.page == Page.FELLOWSHIP && (viewingOwned || viewingAdminned);
        this.buttonHiredFF.visible = this.buttonHiredFF.enabled;
        if (this.buttonHiredFF.enabled) {
            this.buttonHiredFF.setIconUV(80, this.viewingFellowship.getPreventHiredFriendlyFire() ? 80 : 48);
        }
        this.buttonMapShow.enabled = this.page == Page.FELLOWSHIP && viewingOwned;
        this.buttonMapShow.visible = this.buttonMapShow.enabled;
        if (this.buttonMapShow.enabled) {
            this.buttonMapShow.setIconUV(96, this.viewingFellowship.getShowMapLocations() ? 48 : 80);
        }
        this.buttonOp.enabled = this.page == Page.OP;
        this.buttonOp.visible = this.buttonOp.enabled;
        this.buttonDeop.enabled = this.page == Page.DEOP;
        this.buttonDeop.visible = this.buttonDeop.enabled;
        this.alignOptionButtons();
        this.setupScrollBars(i, j);
        this.drawDefaultBackground();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        super.drawScreen(i, j, f);
        String s = StatCollector.translateToLocal("lotr.gui.fellowships.title");
        this.drawCenteredString(s, this.guiLeft + this.xSize / 2, this.guiTop - 30, 16777215);
        if (this.page == Page.LIST) {
            int x = this.guiLeft;
            int y = this.scrollPaneLeading.paneY0;
            s = StatCollector.translateToLocal("lotr.gui.fellowships.leading");
            this.drawCenteredString(s, this.guiLeft + this.xSize / 2, y, 16777215);
            y += this.fontRendererObj.FONT_HEIGHT + 10;
            List<LOTRFellowshipClient> sortedLeading = this.sortFellowshipsForDisplay(this.allFellowshipsLeading);
            int[] leadingMinMax = this.scrollPaneLeading.getMinMaxIndices(sortedLeading, this.displayedFellowshipsLeading);
            for (int index = leadingMinMax[0]; index <= leadingMinMax[1]; ++index) {
                LOTRFellowshipClient fs = sortedLeading.get(index);
                this.drawFellowshipEntry(fs, x, y, i, j, false);
                y += this.fontRendererObj.FONT_HEIGHT + 5;
            }
            y = this.scrollPaneOther.paneY0;
            s = StatCollector.translateToLocal("lotr.gui.fellowships.member");
            this.drawCenteredString(s, this.guiLeft + this.xSize / 2, y, 16777215);
            y += this.fontRendererObj.FONT_HEIGHT + 10;
            List<LOTRFellowshipClient> sortedOther = this.sortFellowshipsForDisplay(this.allFellowshipsOther);
            int[] otherMinMax = this.scrollPaneOther.getMinMaxIndices(sortedOther, this.displayedFellowshipsOther);
            for (int index = otherMinMax[0]; index <= otherMinMax[1]; ++index) {
                LOTRFellowshipClient fs = sortedOther.get(index);
                this.drawFellowshipEntry(fs, x, y, i, j, false);
                y += this.fontRendererObj.FONT_HEIGHT + 5;
            }
            String invites = String.valueOf(playerData.getClientFellowshipInvites().size());
            int invitesX = this.buttonInvites.xPosition - 2 - this.fontRendererObj.getStringWidth(invites);
            int invitesY = this.buttonInvites.yPosition + this.buttonInvites.height / 2 - this.fontRendererObj.FONT_HEIGHT / 2;
            this.fontRendererObj.drawString(invites, invitesX, invitesY, 16777215);
            if (this.buttonInvites.func_146115_a()) {
                this.renderIconTooltip(i, j, StatCollector.translateToLocal("lotr.gui.fellowships.invitesTooltip"));
            }
            if (this.buttonCreate.func_146115_a()) {
                if (!creationEnabled) {
                    s = StatCollector.translateToLocal("lotr.gui.fellowships.creationDisabled");
                    this.drawCenteredString(s, this.guiLeft + this.xSize / 2, this.buttonCreate.yPosition + this.buttonCreate.height + 4, 16777215);
                } else if (!canPlayerCreateNew) {
                    s = StatCollector.translateToLocal("lotr.gui.fellowships.createLimit");
                    this.drawCenteredString(s, this.guiLeft + this.xSize / 2, this.buttonCreate.yPosition + this.buttonCreate.height + 4, 16777215);
                }
            }
            if (this.scrollPaneLeading.hasScrollBar) {
                this.scrollPaneLeading.drawScrollBar();
            }
            if (this.scrollPaneOther.hasScrollBar) {
                this.scrollPaneOther.drawScrollBar();
            }
        } else if (this.page == Page.CREATE) {
            s = StatCollector.translateToLocal("lotr.gui.fellowships.createName");
            this.drawCenteredString(s, this.guiLeft + this.xSize / 2, this.textFieldName.yPosition - 4 - this.fontRendererObj.FONT_HEIGHT, 16777215);
            this.textFieldName.drawTextBox();
            if (checkValidName != null) {
                this.drawCenteredString(checkValidName, this.guiLeft + this.xSize / 2, this.textFieldName.yPosition + this.textFieldName.height + this.fontRendererObj.FONT_HEIGHT, 16711680);
            }
        } else if (this.page == Page.FELLOWSHIP) {
            int x = this.guiLeft;
            int y = this.guiTop + 10;
            s = StatCollector.translateToLocalFormatted("lotr.gui.fellowships.nameAndPlayers", this.viewingFellowship.getName(), this.viewingFellowship.getMemberCount());
            this.drawCenteredString(s, this.guiLeft + this.xSize / 2, y, 16777215);
            y += this.fontRendererObj.FONT_HEIGHT;
            y += 5;
            if (this.viewingFellowship.getIcon() != null) {
                this.drawFellowshipIcon(this.viewingFellowship, this.guiLeft + this.xSize / 2 - 8, y, 1.0f);
            }
            boolean preventPVP = this.viewingFellowship.getPreventPVP();
            boolean preventHiredFF = this.viewingFellowship.getPreventHiredFriendlyFire();
            boolean mapShow = this.viewingFellowship.getShowMapLocations();
            int iconPVPX = this.guiLeft + this.xSize - 36;
            int iconHFFX = this.guiLeft + this.xSize - 16;
            int iconMapX = this.guiLeft + this.xSize - 56;
            int iconY = y;
            int iconSize = 16;
            this.mc.getTextureManager().bindTexture(iconsTextures);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            this.drawTexturedModalRect(iconPVPX, iconY, 64, preventPVP ? 80 : 48, iconSize, iconSize);
            this.drawTexturedModalRect(iconHFFX, iconY, 80, preventHiredFF ? 80 : 48, iconSize, iconSize);
            this.drawTexturedModalRect(iconMapX, iconY, 96, mapShow ? 48 : 80, iconSize, iconSize);
            if (i >= iconPVPX && i < iconPVPX + iconSize && j >= iconY && j < iconY + iconSize) {
                this.renderIconTooltip(i, j, StatCollector.translateToLocal(preventPVP ? "lotr.gui.fellowships.pvp.prevent" : "lotr.gui.fellowships.pvp.allow"));
            }
            if (i >= iconHFFX && i < iconHFFX + iconSize && j >= iconY && j < iconY + iconSize) {
                this.renderIconTooltip(i, j, StatCollector.translateToLocal(preventHiredFF ? "lotr.gui.fellowships.hiredFF.prevent" : "lotr.gui.fellowships.hiredFF.allow"));
            }
            if (i >= iconMapX && i < iconMapX + iconSize && j >= iconY && j < iconY + iconSize) {
                this.renderIconTooltip(i, j, StatCollector.translateToLocal(mapShow ? "lotr.gui.fellowships.mapShow.on" : "lotr.gui.fellowships.mapShow.off"));
            }
            y += iconSize;
            y += 10;
            int titleOffset = 0;
            for (String name : this.viewingFellowship.getAllPlayerNames()) {
                LOTRTitle.PlayerTitle title = this.viewingFellowship.getTitleFor(name);
                if (title == null) continue;
                String titleName = title.getFormattedTitle(this.mc.thePlayer);
                int thisTitleWidth = this.fontRendererObj.getStringWidth(titleName + " ");
                titleOffset = Math.max(titleOffset, thisTitleWidth);
            }
            this.drawPlayerEntry(this.viewingFellowship.getOwnerName(), x, y, titleOffset, i, j);
            y += this.fontRendererObj.FONT_HEIGHT + 10;
            List<String> membersSorted = this.sortMemberNamesForDisplay(this.viewingFellowship);
            int[] membersMinMax = this.scrollPaneMembers.getMinMaxIndices(membersSorted, this.displayedMembers);
            for (int index = membersMinMax[0]; index <= membersMinMax[1]; ++index) {
                String name = membersSorted.get(index);
                this.drawPlayerEntry(name, x, y, titleOffset, i, j);
                y += this.fontRendererObj.FONT_HEIGHT + 5;
            }
            for (Object bObj : this.buttonList) {
                GuiButton button = (GuiButton)bObj;
                if (!(button instanceof LOTRGuiButtonFsOption) || !button.visible || !button.func_146115_a()) continue;
                s = button.displayString;
                this.drawCenteredString(s, this.guiLeft + this.xSize / 2, button.yPosition + button.height + 4, 16777215);
            }
            if (this.scrollPaneMembers.hasScrollBar) {
                this.scrollPaneMembers.drawScrollBar();
            }
        } else if (this.page == Page.INVITE) {
            s = StatCollector.translateToLocalFormatted("lotr.gui.fellowships.inviteName", this.viewingFellowship.getName());
            this.drawCenteredString(s, this.guiLeft + this.xSize / 2, this.textFieldPlayer.yPosition - 4 - this.fontRendererObj.FONT_HEIGHT, 16777215);
            this.textFieldPlayer.drawTextBox();
            if (checkValidPlayer != null) {
                this.drawCenteredString(checkValidPlayer, this.guiLeft + this.xSize / 2, this.textFieldPlayer.yPosition + this.textFieldPlayer.height + this.fontRendererObj.FONT_HEIGHT, 16711680);
            }
        } else if (this.page == Page.DISBAND) {
            int x = this.guiLeft + this.xSize / 2;
            int y = this.guiTop + 30;
            s = StatCollector.translateToLocalFormatted("lotr.gui.fellowships.disbandCheck1", this.viewingFellowship.getName());
            this.drawCenteredString(s, x, y, 16777215);
            s = StatCollector.translateToLocal("lotr.gui.fellowships.disbandCheck2");
            this.drawCenteredString(s, x, y += this.fontRendererObj.FONT_HEIGHT, 16777215);
            s = StatCollector.translateToLocal("lotr.gui.fellowships.disbandCheck3");
            this.drawCenteredString(s, x, y += this.fontRendererObj.FONT_HEIGHT * 2, 16777215);
            y += this.fontRendererObj.FONT_HEIGHT;
        } else if (this.page == Page.LEAVE) {
            int x = this.guiLeft + this.xSize / 2;
            int y = this.guiTop + 30;
            s = StatCollector.translateToLocalFormatted("lotr.gui.fellowships.leaveCheck1", this.viewingFellowship.getName());
            this.drawCenteredString(s, x, y, 16777215);
            s = StatCollector.translateToLocal("lotr.gui.fellowships.leaveCheck2");
            this.drawCenteredString(s, x, y += this.fontRendererObj.FONT_HEIGHT, 16777215);
            y += this.fontRendererObj.FONT_HEIGHT * 2;
        } else if (this.page == Page.REMOVE) {
            int x = this.guiLeft + this.xSize / 2;
            int y = this.guiTop + 30;
            s = StatCollector.translateToLocalFormatted("lotr.gui.fellowships.removeCheck", this.viewingFellowship.getName(), this.removingPlayer);
            List<String> lines = this.fontRendererObj.listFormattedStringToWidth(s, this.xSize);
            for (String line : lines) {
                this.drawCenteredString(line, x, y, 16777215);
                y += this.fontRendererObj.FONT_HEIGHT;
            }
        } else if (this.page == Page.OP) {
            int x = this.guiLeft + this.xSize / 2;
            int y = this.guiTop + 30;
            s = StatCollector.translateToLocalFormatted("lotr.gui.fellowships.opCheck1", this.viewingFellowship.getName(), this.oppingPlayer);
            List<String> lines = this.fontRendererObj.listFormattedStringToWidth(s, this.xSize);
            for (String line : lines) {
                this.drawCenteredString(line, x, y, 16777215);
                y += this.fontRendererObj.FONT_HEIGHT;
            }
            y += this.fontRendererObj.FONT_HEIGHT;
            s = StatCollector.translateToLocalFormatted("lotr.gui.fellowships.opCheck2", this.viewingFellowship.getName(), this.oppingPlayer);
            lines = this.fontRendererObj.listFormattedStringToWidth(s, this.xSize);
            for (String line : lines) {
                this.drawCenteredString(line, x, y, 16777215);
                y += this.fontRendererObj.FONT_HEIGHT;
            }
        } else if (this.page == Page.DEOP) {
            int x = this.guiLeft + this.xSize / 2;
            int y = this.guiTop + 30;
            s = StatCollector.translateToLocalFormatted("lotr.gui.fellowships.deopCheck", this.viewingFellowship.getName(), this.deoppingPlayer);
            List<String> lines = this.fontRendererObj.listFormattedStringToWidth(s, this.xSize);
            for (String line : lines) {
                this.drawCenteredString(line, x, y, 16777215);
                y += this.fontRendererObj.FONT_HEIGHT;
            }
        } else if (this.page == Page.TRANSFER) {
            int x = this.guiLeft + this.xSize / 2;
            int y = this.guiTop + 30;
            s = StatCollector.translateToLocalFormatted("lotr.gui.fellowships.transferCheck1", this.viewingFellowship.getName(), this.transferringPlayer);
            List<String> lines = this.fontRendererObj.listFormattedStringToWidth(s, this.xSize);
            for (String line : lines) {
                this.drawCenteredString(line, x, y, 16777215);
                y += this.fontRendererObj.FONT_HEIGHT;
            }
            s = StatCollector.translateToLocal("lotr.gui.fellowships.transferCheck2");
            this.drawCenteredString(s, x, y += this.fontRendererObj.FONT_HEIGHT, 16777215);
            y += this.fontRendererObj.FONT_HEIGHT;
        } else if (this.page == Page.RENAME) {
            s = StatCollector.translateToLocalFormatted("lotr.gui.fellowships.renameName", this.viewingFellowship.getName());
            this.drawCenteredString(s, this.guiLeft + this.xSize / 2, this.textFieldRename.yPosition - 4 - this.fontRendererObj.FONT_HEIGHT, 16777215);
            this.textFieldRename.drawTextBox();
            if (checkValidRename != null) {
                this.drawCenteredString(checkValidRename, this.guiLeft + this.xSize / 2, this.textFieldRename.yPosition + this.textFieldRename.height + this.fontRendererObj.FONT_HEIGHT, 16711680);
            }
        } else if (this.page == Page.INVITATIONS) {
            int x = this.guiLeft;
            int y = this.guiTop + 10;
            s = StatCollector.translateToLocal("lotr.gui.fellowships.invites");
            this.drawCenteredString(s, this.guiLeft + this.xSize / 2, y, 16777215);
            y += this.fontRendererObj.FONT_HEIGHT + 10;
            if (this.allFellowshipInvites.isEmpty()) {
                s = StatCollector.translateToLocal("lotr.gui.fellowships.invitesNone");
                this.drawCenteredString(s, this.guiLeft + this.xSize / 2, y += this.fontRendererObj.FONT_HEIGHT, 16777215);
            } else {
                int[] invitesMinMax = this.scrollPaneInvites.getMinMaxIndices(this.allFellowshipInvites, this.displayedInvites);
                for (int index = invitesMinMax[0]; index <= invitesMinMax[1]; ++index) {
                    LOTRFellowshipClient fs = this.allFellowshipInvites.get(index);
                    this.drawFellowshipEntry(fs, x, y, i, j, true);
                    y += this.fontRendererObj.FONT_HEIGHT + 5;
                }
            }
            if (this.scrollPaneInvites.hasScrollBar) {
                this.scrollPaneInvites.drawScrollBar();
            }
        }
    }

    private void drawFellowshipEntry(LOTRFellowshipClient fs, int x, int y, int mouseX, int mouseY, boolean isInvite) {
        this.drawFellowshipEntry(fs, x, y, mouseX, mouseY, isInvite, this.xSize);
    }

    public void drawFellowshipEntry(LOTRFellowshipClient fs, int x, int y, int mouseX, int mouseY, boolean isInvite, int selectWidth) {
        int selectX0 = x - 2;
        int selectX1 = x + selectWidth + 2;
        int selectY0 = y - 2;
        int selectY1 = y + this.fontRendererObj.FONT_HEIGHT + 2;
        if (mouseX >= selectX0 && mouseX <= selectX1 && mouseY >= selectY0 && mouseY <= selectY1) {
            LOTRGuiFellowships.drawRect(selectX0, selectY0, selectX1, selectY1, 1442840575);
            this.mouseOverFellowship = fs;
        }
        boolean isMouseOver = this.mouseOverFellowship == fs;
        this.drawFellowshipIcon(fs, x, y, 0.5f);
        String fsName = fs.getName();
        int maxLength = 110;
        if (this.fontRendererObj.getStringWidth(fsName) > maxLength) {
            String ellipsis = "...";
            while (this.fontRendererObj.getStringWidth(fsName + ellipsis) > maxLength) {
                fsName = fsName.substring(0, fsName.length() - 1);
            }
            fsName = fsName + ellipsis;
        }
        String ownerName = fs.getOwnerName();
        boolean ownerOnline = LOTRGuiFellowships.isPlayerOnline(ownerName);
        this.fontRendererObj.drawString(fsName, x + 15, y, 16777215);
        this.fontRendererObj.drawString(ownerName, x + 130, y, ownerOnline ? 16777215 : (isMouseOver ? 12303291 : 7829367));
        if (isInvite) {
            int iconWidth = 8;
            int iconAcceptX = x + this.xSize - 18;
            int iconRejectX = x + this.xSize - 8;
            boolean accept = false;
            boolean reject = false;
            if (isMouseOver) {
                accept = this.mouseOverInviteAccept = mouseX >= iconAcceptX && mouseX <= iconAcceptX + iconWidth && mouseY >= y && mouseY <= y + iconWidth;
                reject = this.mouseOverInviteReject = mouseX >= iconRejectX && mouseX <= iconRejectX + iconWidth && mouseY >= y && mouseY <= y + iconWidth;
            }
            this.mc.getTextureManager().bindTexture(iconsTextures);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            this.drawTexturedModalRect(iconAcceptX, y, 16, 16 + (accept ? 0 : iconWidth), iconWidth, iconWidth);
            this.drawTexturedModalRect(iconRejectX, y, 8, 16 + (reject ? 0 : iconWidth), iconWidth, iconWidth);
        } else {
            String memberCount = String.valueOf(fs.getMemberCount());
            String onlineMemberCount = String.valueOf(this.countOnlineMembers(fs)) + " | ";
            this.fontRendererObj.drawString(memberCount, x + this.xSize - this.fontRendererObj.getStringWidth(memberCount), y, isMouseOver ? 12303291 : 7829367);
            this.fontRendererObj.drawString(onlineMemberCount, x + this.xSize - this.fontRendererObj.getStringWidth(memberCount) - this.fontRendererObj.getStringWidth(onlineMemberCount), y, 16777215);
        }
    }

    private void drawPlayerEntry(String player, int x, int y, int titleOffset, int mouseX, int mouseY) {
        int selectX0 = x - 2;
        int selectX1 = x + this.xSize + 2;
        int selectY0 = y - 2;
        int selectY1 = y + this.fontRendererObj.FONT_HEIGHT + 2;
        if (mouseX >= selectX0 && mouseX <= selectX1 && mouseY >= selectY0 && mouseY <= selectY1) {
            LOTRGuiFellowships.drawRect(selectX0, selectY0, selectX1, selectY1, 1442840575);
            this.mouseOverPlayer = player;
        }
        boolean isMouseOver = this.mouseOverPlayer == player;
        String titleName = null;
        LOTRTitle.PlayerTitle title = this.viewingFellowship.getTitleFor(player);
        if (title != null) {
            titleName = title.getFormattedTitle(this.mc.thePlayer);
        }
        if (titleName != null) {
            this.fontRendererObj.drawString(titleName, x, y, 16777215);
        }
        this.fontRendererObj.drawString(player, x + titleOffset, y, (LOTRGuiFellowships.isPlayerOnline(player)) ? 16777215 : (isMouseOver ? 12303291 : 7829367));
        boolean isOwner = this.viewingFellowship.getOwnerName().equals(player);
        boolean isAdmin = this.viewingFellowship.isAdmin(player);
        if (isOwner) {
            this.mc.getTextureManager().bindTexture(iconsTextures);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            this.drawTexturedModalRect(x + titleOffset + this.fontRendererObj.getStringWidth(player + " "), y, 0, 0, 8, 8);
        } else if (isAdmin) {
            this.mc.getTextureManager().bindTexture(iconsTextures);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            this.drawTexturedModalRect(x + titleOffset + this.fontRendererObj.getStringWidth(player + " "), y, 8, 0, 8, 8);
        }
        boolean owned = this.viewingFellowship.isOwned();
        boolean adminned = this.viewingFellowship.isAdminned();
        if (!isOwner && (owned || adminned)) {
            int iconWidth = 8;
            int iconRemoveX = x + this.xSize - 28;
            int iconOpDeopX = x + this.xSize - 18;
            int iconTransferX = x + this.xSize - 8;
            if (adminned) {
                iconRemoveX = x + this.xSize - 8;
            }
            boolean remove = false;
            boolean opDeop = false;
            boolean transfer = false;
            if (isMouseOver) {
                remove = this.mouseOverPlayerRemove = mouseX >= iconRemoveX && mouseX <= iconRemoveX + iconWidth && mouseY >= y && mouseY <= y + iconWidth;
                if (owned) {
                    opDeop = isAdmin ? (this.mouseOverPlayerDeop = mouseX >= iconOpDeopX && mouseX <= iconOpDeopX + iconWidth && mouseY >= y && mouseY <= y + iconWidth) : (this.mouseOverPlayerOp = mouseX >= iconOpDeopX && mouseX <= iconOpDeopX + iconWidth && mouseY >= y && mouseY <= y + iconWidth);
                    transfer = this.mouseOverPlayerTransfer = mouseX >= iconTransferX && mouseX <= iconTransferX + iconWidth && mouseY >= y && mouseY <= y + iconWidth;
                }
            }
            this.mc.getTextureManager().bindTexture(iconsTextures);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            this.drawTexturedModalRect(iconRemoveX, y, 8, 16 + (remove ? 0 : iconWidth), iconWidth, iconWidth);
            if (owned) {
                if (isAdmin) {
                    this.drawTexturedModalRect(iconOpDeopX, y, 32, 16 + (opDeop ? 0 : iconWidth), iconWidth, iconWidth);
                } else {
                    this.drawTexturedModalRect(iconOpDeopX, y, 24, 16 + (opDeop ? 0 : iconWidth), iconWidth, iconWidth);
                }
                this.drawTexturedModalRect(iconTransferX, y, 0, 16 + (transfer ? 0 : iconWidth), iconWidth, iconWidth);
            }
        }
    }

    private void drawFellowshipIcon(LOTRFellowshipClient fsClient, int x, int y, float scale) {
        ItemStack fsIcon = fsClient.getIcon();
        if (fsIcon != null) {
            GL11.glDisable(3042);
            GL11.glDisable(3008);
            RenderHelper.enableGUIStandardItemLighting();
            GL11.glDisable(2896);
            GL11.glEnable(32826);
            GL11.glEnable(2896);
            GL11.glEnable(2884);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glPushMatrix();
            GL11.glScalef(scale, scale, 1.0f);
            renderItem.renderItemAndEffectIntoGUI(this.mc.fontRenderer, this.mc.getTextureManager(), fsIcon, Math.round(x / scale), Math.round(y / scale));
            GL11.glPopMatrix();
            GL11.glDisable(2896);
        }
    }

    private void renderIconTooltip(int x, int y, String s) {
        float z = this.zLevel;
        int stringWidth = 200;
        List desc = this.fontRendererObj.listFormattedStringToWidth(s, stringWidth);
        this.func_146283_a(desc, x, y);
        GL11.glDisable(2896);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.zLevel = z;
    }

    public static boolean isPlayerOnline(String player) {
        EntityClientPlayerMP mcPlayer = Minecraft.getMinecraft().thePlayer;
        List list = mcPlayer.sendQueue.playerInfoList;
        for (Object obj : list) {
            GuiPlayerInfo info = (GuiPlayerInfo)obj;
            if (!info.name.equalsIgnoreCase(player)) continue;
            return true;
        }
        return false;
    }

    private int countOnlineMembers(LOTRFellowshipClient fs) {
        int i = 0;
        ArrayList<String> allPlayers = new ArrayList<>(fs.getAllPlayerNames());
        for (String player : allPlayers) {
            if (!LOTRGuiFellowships.isPlayerOnline(player)) continue;
            ++i;
        }
        return i;
    }

    private List<LOTRFellowshipClient> sortFellowshipsForDisplay(List<LOTRFellowshipClient> list) {
        ArrayList<LOTRFellowshipClient> sorted = new ArrayList<>(list);
        Collections.sort(sorted, new Comparator<LOTRFellowshipClient>(){

            @Override
            public int compare(LOTRFellowshipClient fs1, LOTRFellowshipClient fs2) {
                int count2;
                int count1 = fs1.getMemberCount();
                if (count1 == (count2 = fs2.getMemberCount())) {
                    return fs1.getName().toLowerCase().compareTo(fs2.getName().toLowerCase());
                }
                return -Integer.valueOf(count1).compareTo(count2);
            }
        });
        return sorted;
    }

    private List<String> sortMemberNamesForDisplay(final LOTRFellowshipClient fs) {
        ArrayList<String> members = new ArrayList<>(fs.getMemberNames());
        Collections.sort(members, new Comparator<String>(){

            @Override
            public int compare(String player1, String player2) {
                boolean online2;
                boolean admin1 = fs.isAdmin(player1);
                boolean admin2 = fs.isAdmin(player2);
                boolean online1 = LOTRGuiFellowships.isPlayerOnline(player1);
                if (online1 == (online2 = LOTRGuiFellowships.isPlayerOnline(player2))) {
                    if (admin1 == admin2) {
                        return player1.toLowerCase().compareTo(player2.toLowerCase());
                    }
                    if (admin1 && !admin2) {
                        return -1;
                    }
                    if (!admin1 && admin2) {
                        return 1;
                    }
                } else {
                    if (online1 && !online2) {
                        return -1;
                    }
                    if (!online1 && online2) {
                        return 1;
                    }
                }
                return 0;
            }
        });
        return members;
    }

    @Override
    protected void keyTyped(char c, int i) {
        if (this.page == Page.CREATE && this.textFieldName.textboxKeyTyped(c, i)) {
            return;
        }
        if (this.page == Page.INVITE && this.textFieldPlayer.textboxKeyTyped(c, i)) {
            return;
        }
        if (this.page == Page.RENAME && this.textFieldRename.textboxKeyTyped(c, i)) {
            return;
        }
        if (this.page != Page.LIST) {
            if (i == 1 || i == this.mc.gameSettings.keyBindInventory.getKeyCode()) {
                this.page = this.page == Page.INVITE || this.page == Page.DISBAND || this.page == Page.LEAVE || this.page == Page.REMOVE || this.page == Page.OP || this.page == Page.DEOP || this.page == Page.TRANSFER || this.page == Page.RENAME ? Page.FELLOWSHIP : Page.LIST;
            }
        } else {
            super.keyTyped(c, i);
        }
    }

    protected void mouseClicked(int i, int j, int k) {
        LOTRPacketFellowshipRespondInvite packet;
        super.mouseClicked(i, j, k);
        if (this.page == Page.LIST && this.mouseOverFellowship != null) {
            this.buttonSound();
            this.page = Page.FELLOWSHIP;
            this.viewingFellowship = this.mouseOverFellowship;
        }
        if (this.page == Page.CREATE) {
            this.textFieldName.mouseClicked(i, j, k);
        }
        if (this.page == Page.INVITE) {
            this.textFieldPlayer.mouseClicked(i, j, k);
        }
        if (this.page == Page.RENAME) {
            this.textFieldRename.mouseClicked(i, j, k);
        }
        if (this.page == Page.FELLOWSHIP && this.mouseOverPlayer != null && this.mouseOverPlayerRemove) {
            this.buttonSound();
            this.page = Page.REMOVE;
            this.removingPlayer = this.mouseOverPlayer;
        }
        if (this.page == Page.FELLOWSHIP && this.mouseOverPlayer != null && this.mouseOverPlayerOp) {
            this.buttonSound();
            this.page = Page.OP;
            this.oppingPlayer = this.mouseOverPlayer;
        }
        if (this.page == Page.FELLOWSHIP && this.mouseOverPlayer != null && this.mouseOverPlayerDeop) {
            this.buttonSound();
            this.page = Page.DEOP;
            this.deoppingPlayer = this.mouseOverPlayer;
        }
        if (this.page == Page.FELLOWSHIP && this.mouseOverPlayer != null && this.mouseOverPlayerTransfer) {
            this.buttonSound();
            this.page = Page.TRANSFER;
            this.transferringPlayer = this.mouseOverPlayer;
        }
        if (this.page == Page.INVITATIONS && this.mouseOverFellowship != null && this.mouseOverInviteAccept) {
            this.buttonSound();
            packet = new LOTRPacketFellowshipRespondInvite(this.mouseOverFellowship, true);
            LOTRPacketHandler.networkWrapper.sendToServer(packet);
            this.mouseOverFellowship = null;
        }
        if (this.page == Page.INVITATIONS && this.mouseOverFellowship != null && this.mouseOverInviteReject) {
            this.buttonSound();
            packet = new LOTRPacketFellowshipRespondInvite(this.mouseOverFellowship, false);
            LOTRPacketHandler.networkWrapper.sendToServer(packet);
            this.mouseOverFellowship = null;
        }
    }

    private void buttonSound() {
        this.buttonBack.func_146113_a(this.mc.getSoundHandler());
    }

    private void setupScrollBars(int i, int j) {
        if (this.page == Page.LIST) {
            this.displayedFellowshipsLeading = this.allFellowshipsLeading.size();
            this.displayedFellowshipsOther = this.allFellowshipsOther.size();
            this.scrollPaneLeading.hasScrollBar = false;
            this.scrollPaneOther.hasScrollBar = false;
            while (this.displayedFellowshipsLeading + this.displayedFellowshipsOther > 12) {
                if (this.displayedFellowshipsOther >= this.displayedFellowshipsLeading) {
                    --this.displayedFellowshipsOther;
                    this.scrollPaneOther.hasScrollBar = true;
                    continue;
                }
                --this.displayedFellowshipsLeading;
                this.scrollPaneLeading.hasScrollBar = true;
            }
            this.scrollPaneLeading.paneX0 = this.guiLeft;
            this.scrollPaneLeading.scrollBarX0 = this.guiLeft + this.scrollBarX;
            this.scrollPaneLeading.paneY0 = this.guiTop + 10;
            this.scrollPaneLeading.paneY1 = this.scrollPaneLeading.paneY0 + this.fontRendererObj.FONT_HEIGHT + 10 + (this.fontRendererObj.FONT_HEIGHT + 5) * this.displayedFellowshipsLeading;
            this.scrollPaneLeading.mouseDragScroll(i, j);
            this.scrollPaneOther.paneX0 = this.guiLeft;
            this.scrollPaneOther.scrollBarX0 = this.guiLeft + this.scrollBarX;
            this.scrollPaneOther.paneY0 = this.scrollPaneLeading.paneY1 + 5;
            this.scrollPaneOther.paneY1 = this.scrollPaneOther.paneY0 + this.fontRendererObj.FONT_HEIGHT + 10 + (this.fontRendererObj.FONT_HEIGHT + 5) * this.displayedFellowshipsOther;
            this.scrollPaneOther.mouseDragScroll(i, j);
        }
        if (this.page == Page.FELLOWSHIP) {
            this.displayedMembers = this.viewingFellowship.getMemberNames().size();
            this.scrollPaneMembers.hasScrollBar = false;
            if (this.displayedMembers > 11) {
                this.displayedMembers = 11;
                this.scrollPaneMembers.hasScrollBar = true;
            }
            this.scrollPaneMembers.paneX0 = this.guiLeft;
            this.scrollPaneMembers.scrollBarX0 = this.guiLeft + this.scrollBarX;
            this.scrollPaneMembers.paneY0 = this.guiTop + 10 + this.fontRendererObj.FONT_HEIGHT + 5 + 16 + 10 + this.fontRendererObj.FONT_HEIGHT + 10;
            this.scrollPaneMembers.paneY1 = this.scrollPaneMembers.paneY0 + (this.fontRendererObj.FONT_HEIGHT + 5) * this.displayedMembers;
            this.scrollPaneMembers.mouseDragScroll(i, j);
        } else {
            this.scrollPaneMembers.hasScrollBar = false;
            this.scrollPaneMembers.mouseDragScroll(i, j);
        }
        if (this.page == Page.INVITATIONS) {
            this.displayedInvites = this.allFellowshipInvites.size();
            this.scrollPaneInvites.hasScrollBar = false;
            if (this.displayedInvites > 15) {
                this.displayedInvites = 15;
                this.scrollPaneInvites.hasScrollBar = true;
            }
            this.scrollPaneInvites.paneX0 = this.guiLeft;
            this.scrollPaneInvites.scrollBarX0 = this.guiLeft + this.scrollBarX;
            this.scrollPaneInvites.paneY0 = this.guiTop + 10 + this.fontRendererObj.FONT_HEIGHT + 10;
            this.scrollPaneInvites.paneY1 = this.scrollPaneInvites.paneY0 + (this.fontRendererObj.FONT_HEIGHT + 5) * this.displayedInvites;
            this.scrollPaneInvites.mouseDragScroll(i, j);
        }
    }

    public void handleMouseInput() {
        super.handleMouseInput();
        int k = Mouse.getEventDWheel();
        if (k != 0) {
            int l;
            k = Integer.signum(k);
            if (this.page == Page.LIST) {
                if (this.scrollPaneLeading.hasScrollBar && this.scrollPaneLeading.mouseOver) {
                    l = this.allFellowshipsLeading.size() - this.displayedFellowshipsLeading;
                    this.scrollPaneLeading.mouseWheelScroll(k, l);
                }
                if (this.scrollPaneOther.hasScrollBar && this.scrollPaneOther.mouseOver) {
                    l = this.allFellowshipsOther.size() - this.displayedFellowshipsOther;
                    this.scrollPaneOther.mouseWheelScroll(k, l);
                }
            }
            if (this.page == Page.FELLOWSHIP && this.scrollPaneMembers.hasScrollBar && this.scrollPaneMembers.mouseOver) {
                l = this.viewingFellowship.getMemberNames().size() - this.displayedMembers;
                this.scrollPaneMembers.mouseWheelScroll(k, l);
            }
            if (this.page == Page.INVITATIONS && this.scrollPaneInvites.hasScrollBar && this.scrollPaneInvites.mouseOver) {
                l = this.allFellowshipInvites.size() - this.displayedInvites;
                this.scrollPaneInvites.mouseWheelScroll(k, l);
            }
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button.enabled) {
            if (button == this.buttonCreate) {
                this.page = Page.CREATE;
            } else if (button == this.buttonCreateThis) {
                String name = this.textFieldName.getText();
                if (this.checkValidFellowshipName(name) == null) {
                    name = StringUtils.trim(name);
                    LOTRPacketFellowshipCreate packet = new LOTRPacketFellowshipCreate(name);
                    LOTRPacketHandler.networkWrapper.sendToServer(packet);
                }
                this.page = Page.LIST;
            } else if (button == this.buttonInvitePlayer) {
                this.page = Page.INVITE;
            } else if (button == this.buttonInviteThis) {
                String name = this.textFieldPlayer.getText();
                if (this.checkValidPlayerName(name) == null) {
                    name = StringUtils.trim(name);
                    LOTRPacketFellowshipDoPlayer packet = new LOTRPacketFellowshipDoPlayer(this.viewingFellowship, name, LOTRPacketFellowshipDoPlayer.PlayerFunction.INVITE);
                    LOTRPacketHandler.networkWrapper.sendToServer(packet);
                }
                this.page = Page.FELLOWSHIP;
            } else if (button == this.buttonDisband) {
                this.page = Page.DISBAND;
            } else if (button == this.buttonDisbandThis) {
                LOTRPacketFellowshipDisband packet = new LOTRPacketFellowshipDisband(this.viewingFellowship);
                LOTRPacketHandler.networkWrapper.sendToServer(packet);
                this.page = Page.LIST;
            } else if (button == this.buttonLeave) {
                this.page = Page.LEAVE;
            } else if (button == this.buttonLeaveThis) {
                LOTRPacketFellowshipLeave packet = new LOTRPacketFellowshipLeave(this.viewingFellowship);
                LOTRPacketHandler.networkWrapper.sendToServer(packet);
                this.page = Page.LIST;
            } else if (button == this.buttonSetIcon) {
                LOTRPacketFellowshipSetIcon packet = new LOTRPacketFellowshipSetIcon(this.viewingFellowship);
                LOTRPacketHandler.networkWrapper.sendToServer(packet);
            } else if (button == this.buttonRemove) {
                LOTRPacketFellowshipDoPlayer packet = new LOTRPacketFellowshipDoPlayer(this.viewingFellowship, this.removingPlayer, LOTRPacketFellowshipDoPlayer.PlayerFunction.REMOVE);
                LOTRPacketHandler.networkWrapper.sendToServer(packet);
                this.page = Page.FELLOWSHIP;
            } else if (button == this.buttonOp) {
                LOTRPacketFellowshipDoPlayer packet = new LOTRPacketFellowshipDoPlayer(this.viewingFellowship, this.oppingPlayer, LOTRPacketFellowshipDoPlayer.PlayerFunction.OP);
                LOTRPacketHandler.networkWrapper.sendToServer(packet);
                this.page = Page.FELLOWSHIP;
            } else if (button == this.buttonDeop) {
                LOTRPacketFellowshipDoPlayer packet = new LOTRPacketFellowshipDoPlayer(this.viewingFellowship, this.deoppingPlayer, LOTRPacketFellowshipDoPlayer.PlayerFunction.DEOP);
                LOTRPacketHandler.networkWrapper.sendToServer(packet);
                this.page = Page.FELLOWSHIP;
            } else if (button == this.buttonTransfer) {
                LOTRPacketFellowshipDoPlayer packet = new LOTRPacketFellowshipDoPlayer(this.viewingFellowship, this.transferringPlayer, LOTRPacketFellowshipDoPlayer.PlayerFunction.TRANSFER);
                LOTRPacketHandler.networkWrapper.sendToServer(packet);
                this.page = Page.FELLOWSHIP;
            } else if (button == this.buttonRename) {
                this.page = Page.RENAME;
            } else if (button == this.buttonRenameThis) {
                String name = this.textFieldRename.getText();
                if (this.checkValidFellowshipName(name) == null) {
                    name = StringUtils.trim(name);
                    LOTRPacketFellowshipRename packet = new LOTRPacketFellowshipRename(this.viewingFellowship, name);
                    LOTRPacketHandler.networkWrapper.sendToServer(packet);
                }
                this.page = Page.FELLOWSHIP;
            } else if (button == this.buttonBack) {
                this.keyTyped('E', 1);
            } else if (button == this.buttonInvites) {
                this.page = Page.INVITATIONS;
            } else if (button == this.buttonPVP) {
                LOTRPacketFellowshipToggle packet = new LOTRPacketFellowshipToggle(this.viewingFellowship, LOTRPacketFellowshipToggle.ToggleFunction.PVP);
                LOTRPacketHandler.networkWrapper.sendToServer(packet);
            } else if (button == this.buttonHiredFF) {
                LOTRPacketFellowshipToggle packet = new LOTRPacketFellowshipToggle(this.viewingFellowship, LOTRPacketFellowshipToggle.ToggleFunction.HIRED_FF);
                LOTRPacketHandler.networkWrapper.sendToServer(packet);
            } else if (button == this.buttonMapShow) {
                LOTRPacketFellowshipToggle packet = new LOTRPacketFellowshipToggle(this.viewingFellowship, LOTRPacketFellowshipToggle.ToggleFunction.MAP_SHOW);
                LOTRPacketHandler.networkWrapper.sendToServer(packet);
            } else {
                super.actionPerformed(button);
            }
        }
    }

    private String checkValidFellowshipName(String name) {
        if (!StringUtils.isWhitespace(name)) {
            if (LOTRLevelData.getData(this.mc.thePlayer).anyMatchingFellowshipNames(name, true)) {
                return StatCollector.translateToLocal("lotr.gui.fellowships.nameExists");
            }
            return null;
        }
        return "";
    }

    private String checkValidPlayerName(String name) {
        if (!StringUtils.isWhitespace(name)) {
            if (this.viewingFellowship.isPlayerIn(name)) {
                return StatCollector.translateToLocalFormatted("lotr.gui.fellowships.playerExists", name);
            }
            return null;
        }
        return "";
    }

    public LOTRFellowshipClient getMouseOverFellowship() {
        return this.mouseOverFellowship;
    }

    public void clearMouseOverFellowship() {
        this.mouseOverFellowship = null;
    }

    public enum Page {
        LIST,
        CREATE,
        FELLOWSHIP,
        INVITE,
        DISBAND,
        LEAVE,
        REMOVE,
        OP,
        DEOP,
        TRANSFER,
        RENAME,
        INVITATIONS;

    }

}

