package lotr.client.gui;

import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import lotr.common.enchant.LOTREnchantmentHelper;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.inventory.LOTRContainerAnvil;
import lotr.common.network.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;

public class LOTRGuiAnvil
extends GuiContainer {
    public static final ResourceLocation anvilTexture = new ResourceLocation("lotr:gui/anvil.png");
    private LOTRContainerAnvil theAnvil;
    private ItemStack prevItemStack;
    private GuiButton buttonReforge;
    private GuiButton buttonEngraveOwner;
    private GuiTextField textFieldRename;
    private static int[] colorCodes = new int[16];

    public LOTRGuiAnvil(EntityPlayer entityplayer, int i, int j, int k) {
        super(new LOTRContainerAnvil(entityplayer, i, j, k));
        this.theAnvil = (LOTRContainerAnvil)this.inventorySlots;
        this.xSize = 176;
        this.ySize = 198;
    }

    public LOTRGuiAnvil(EntityPlayer entityplayer, LOTREntityNPC npc) {
        super(new LOTRContainerAnvil(entityplayer, npc));
        this.theAnvil = (LOTRContainerAnvil)this.inventorySlots;
        this.xSize = 176;
        this.ySize = 198;
    }

    public void initGui() {
        super.initGui();
        this.buttonReforge = new LOTRGuiButtonReforge(0, this.guiLeft + 25, this.guiTop + 78, 176, 39);
        this.buttonEngraveOwner = new LOTRGuiButtonReforge(1, this.guiLeft + 5, this.guiTop + 78, 176, 59);
        this.buttonList.add(this.buttonReforge);
        this.buttonList.add(this.buttonEngraveOwner);
        Keyboard.enableRepeatEvents(true);
        this.textFieldRename = new GuiTextField(this.fontRendererObj, this.guiLeft + 62, this.guiTop + 24, 103, 12);
        this.textFieldRename.setTextColor(-1);
        this.textFieldRename.setDisabledTextColour(-1);
        this.textFieldRename.setEnableBackgroundDrawing(false);
        this.textFieldRename.setMaxStringLength(40);
        this.prevItemStack = null;
    }

    public void onGuiClosed() {
        super.onGuiClosed();
        Keyboard.enableRepeatEvents(false);
    }

    public void updateScreen() {
        ItemStack itemstack;
        super.updateScreen();
        if (this.theAnvil.clientReforgeTime > 0) {
            --this.theAnvil.clientReforgeTime;
        }
        if ((itemstack = this.theAnvil.invInput.getStackInSlot(0)) != this.prevItemStack) {
            this.prevItemStack = itemstack;
            String textFieldText = itemstack == null ? "" : LOTRContainerAnvil.stripFormattingCodes(itemstack.getDisplayName());
            boolean textFieldEnabled = itemstack != null;
            this.textFieldRename.setText(textFieldText);
            this.textFieldRename.setEnabled(textFieldEnabled);
            if (itemstack != null) {
                this.renameItem(textFieldText);
            }
        }
    }

    public void drawScreen(int i, int j, float f) {
        float z;
        Object tooltip;
        ItemStack inputItem = this.theAnvil.invInput.getStackInSlot(0);
        boolean canReforge = inputItem != null && LOTREnchantmentHelper.isReforgeable(inputItem) && this.theAnvil.reforgeCost > 0;
        boolean canEngrave = inputItem != null && LOTREnchantmentHelper.isReforgeable(inputItem) && this.theAnvil.engraveOwnerCost > 0;
        this.buttonReforge.visible = this.buttonReforge.enabled = canReforge;
        this.buttonEngraveOwner.enabled = canEngrave && this.theAnvil.canEngraveNewOwner(inputItem, this.mc.thePlayer);
        this.buttonEngraveOwner.visible = this.buttonEngraveOwner.enabled;
        super.drawScreen(i, j, f);
        if (this.buttonReforge.visible && this.buttonReforge.func_146115_a()) {
            z = this.zLevel;
            tooltip = StatCollector.translateToLocal("container.lotr.anvil.reforge");
            this.drawCreativeTabHoveringText((String)tooltip, i - 12, j + 24);
            GL11.glDisable(2896);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            this.zLevel = z;
        }
        if (this.buttonEngraveOwner.visible && this.buttonEngraveOwner.func_146115_a()) {
            z = this.zLevel;
            tooltip = StatCollector.translateToLocal("container.lotr.anvil.engraveOwner");
            this.drawCreativeTabHoveringText((String)tooltip, i - this.fontRendererObj.getStringWidth((String)tooltip), j + 24);
            GL11.glDisable(2896);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            this.zLevel = z;
        }
        GL11.glDisable(2896);
        GL11.glDisable(3042);
        List<EnumChatFormatting> itemNameFormatting = this.theAnvil.getActiveItemNameFormatting();
        for (EnumChatFormatting formatting : itemNameFormatting) {
            int formattingID = formatting.ordinal();
            if (!formatting.isColor() || formattingID >= colorCodes.length) continue;
            int color = colorCodes[formattingID];
            this.textFieldRename.setTextColor(color);
        }
        this.textFieldRename.drawTextBox();
        this.textFieldRename.setTextColor(-1);
    }

    protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(anvilTexture);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
        if (this.theAnvil.isTrader) {
            this.drawTexturedModalRect(this.guiLeft + 75, this.guiTop + 69, 176, 21, 18, 18);
        }
        this.drawTexturedModalRect(this.guiLeft + 59, this.guiTop + 20, 0, this.ySize + (this.theAnvil.invInput.getStackInSlot(0) != null ? 0 : 16), 110, 16);
        if (this.theAnvil.invOutput.getStackInSlot(0) == null) {
            boolean flag = false;
            for (int l = 0; l < this.theAnvil.invInput.getSizeInventory(); ++l) {
                if (this.theAnvil.invInput.getStackInSlot(l) == null) continue;
                flag = true;
                break;
            }
            if (flag) {
                this.drawTexturedModalRect(this.guiLeft + 99, this.guiTop + 56, this.xSize, 0, 28, 21);
            }
        }
        if (this.buttonReforge.visible && this.buttonEngraveOwner.visible) {
            this.drawTexturedModalRect(this.guiLeft + 5, this.guiTop + 78, 176, 99, 40, 20);
        } else if (this.buttonReforge.visible) {
            this.drawTexturedModalRect(this.guiLeft + 25, this.guiTop + 78, 176, 79, 20, 20);
        }
    }

    protected void drawGuiContainerForegroundLayer(int i, int j) {
        GL11.glDisable(2896);
        GL11.glDisable(3042);
        String s = this.theAnvil.isTrader ? StatCollector.translateToLocal("container.lotr.smith") : StatCollector.translateToLocal("container.lotr.anvil");
        this.fontRendererObj.drawString(s, 60, 6, 4210752);
        boolean reforge = this.buttonReforge.enabled && this.buttonReforge.func_146115_a();
        boolean engraveOwner = this.buttonEngraveOwner.enabled && this.buttonEngraveOwner.func_146115_a();
        String costText = null;
        int color = 8453920;
        ItemStack inputItem = this.theAnvil.invInput.getStackInSlot(0);
        ItemStack outputItem = this.theAnvil.invOutput.getStackInSlot(0);
        if (inputItem != null) {
            if (reforge && this.theAnvil.reforgeCost > 0) {
                costText = StatCollector.translateToLocalFormatted("container.lotr.anvil.reforgeCost", this.theAnvil.reforgeCost);
                if (!this.theAnvil.hasMaterialOrCoinAmount(this.theAnvil.reforgeCost)) {
                    color = 16736352;
                }
            } else if (engraveOwner && this.theAnvil.engraveOwnerCost > 0) {
                costText = StatCollector.translateToLocalFormatted("container.lotr.anvil.engraveOwnerCost", this.theAnvil.engraveOwnerCost);
                if (!this.theAnvil.hasMaterialOrCoinAmount(this.theAnvil.engraveOwnerCost)) {
                    color = 16736352;
                }
            } else if (this.theAnvil.materialCost > 0 && outputItem != null) {
                costText = this.theAnvil.isTrader ? StatCollector.translateToLocalFormatted("container.lotr.smith.cost", this.theAnvil.materialCost) : StatCollector.translateToLocalFormatted("container.lotr.anvil.cost", this.theAnvil.materialCost);
                if (!this.theAnvil.getSlotFromInventory(this.theAnvil.invOutput, 0).canTakeStack(this.mc.thePlayer)) {
                    color = 16736352;
                }
            }
        }
        if (costText != null) {
            int colorF = 0xFF000000 | (color & 0xFCFCFC) >> 2 | color & 0xFF000000;
            int x = this.xSize - 8 - this.fontRendererObj.getStringWidth(costText);
            int y = 94;
            if (this.fontRendererObj.getUnicodeFlag()) {
                LOTRGuiAnvil.drawRect(x - 3, y - 2, this.xSize - 7, y + 10, -16777216);
                LOTRGuiAnvil.drawRect(x - 2, y - 1, this.xSize - 8, y + 9, -12895429);
            } else {
                this.fontRendererObj.drawString(costText, x, y + 1, colorF);
                this.fontRendererObj.drawString(costText, x + 1, y, colorF);
                this.fontRendererObj.drawString(costText, x + 1, y + 1, colorF);
            }
            this.fontRendererObj.drawString(costText, x, y, color);
        }
        GL11.glEnable(2896);
        if (this.theAnvil.clientReforgeTime > 0) {
            float f = this.theAnvil.clientReforgeTime / 40.0f;
            int alpha = (int)(f * 255.0f);
            alpha = MathHelper.clamp_int(alpha, 0, 255);
            int overlayColor = 0xFFFFFF | alpha << 24;
            Slot slot = this.theAnvil.getSlotFromInventory(this.theAnvil.invInput, 0);
            LOTRGuiAnvil.drawRect(slot.xDisplayPosition, slot.yDisplayPosition, slot.xDisplayPosition + 16, slot.yDisplayPosition + 16, overlayColor);
        }
    }

    protected void keyTyped(char c, int i) {
        if (this.textFieldRename.textboxKeyTyped(c, i)) {
            this.renameItem(this.textFieldRename.getText());
        } else {
            super.keyTyped(c, i);
        }
    }

    private void renameItem(String rename) {
        ItemStack itemstack = this.theAnvil.invInput.getStackInSlot(0);
        if (itemstack != null && !itemstack.hasDisplayName()) {
            String displayNameStripped = LOTRContainerAnvil.stripFormattingCodes(itemstack.getDisplayName());
            String renameStripped = LOTRContainerAnvil.stripFormattingCodes(rename);
            if (renameStripped.equals(displayNameStripped)) {
                rename = "";
            }
        }
        this.theAnvil.updateItemName(rename);
        LOTRPacketAnvilRename packet = new LOTRPacketAnvilRename(rename);
        LOTRPacketHandler.networkWrapper.sendToServer(packet);
    }

    protected void mouseClicked(int i, int j, int k) {
        super.mouseClicked(i, j, k);
        this.textFieldRename.mouseClicked(i, j, k);
    }

    protected void actionPerformed(GuiButton button) {
        if (button.enabled) {
            if (button == this.buttonReforge) {
                ItemStack inputItem2 = this.theAnvil.invInput.getStackInSlot(0);
                if (inputItem2 != null && this.theAnvil.reforgeCost > 0 && this.theAnvil.hasMaterialOrCoinAmount(this.theAnvil.reforgeCost)) {
                    LOTRPacketAnvilReforge packet = new LOTRPacketAnvilReforge();
                    LOTRPacketHandler.networkWrapper.sendToServer(packet);
                }
            } else if (button == this.buttonEngraveOwner && (this.theAnvil.invInput.getStackInSlot(0)) != null && this.theAnvil.engraveOwnerCost > 0 && this.theAnvil.hasMaterialOrCoinAmount(this.theAnvil.engraveOwnerCost)) {
                LOTRPacketAnvilEngraveOwner packet = new LOTRPacketAnvilEngraveOwner();
                LOTRPacketHandler.networkWrapper.sendToServer(packet);
            }
        }
    }

    static {
        for (int i = 0; i < 16; ++i) {
            int baseBrightness = (i >> 3 & 1) * 85;
            int r = (i >> 2 & 1) * 170 + baseBrightness;
            int g = (i >> 1 & 1) * 170 + baseBrightness;
            int b = (i >> 0 & 1) * 170 + baseBrightness;
            if (i == 6) {
                r += 85;
            }
            LOTRGuiAnvil.colorCodes[i] = (r & 0xFF) << 16 | (g & 0xFF) << 8 | b & 0xFF;
        }
    }
}

