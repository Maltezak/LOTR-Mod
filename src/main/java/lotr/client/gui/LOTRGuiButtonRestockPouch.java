package lotr.client.gui;

import org.lwjgl.opengl.GL11;

import lotr.client.LOTRReflectionClient;
import lotr.common.LOTRMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class LOTRGuiButtonRestockPouch
extends GuiButton {
    private static final ResourceLocation texture = new ResourceLocation("lotr:gui/widgets.png");
    private final GuiContainer parentGUI;

    public LOTRGuiButtonRestockPouch(GuiContainer parent, int i, int j, int k) {
        super(i, j, k, 10, 10, "");
        this.parentGUI = parent;
    }

    public void drawButton(Minecraft mc, int i, int j) {
        this.checkPouchRestockEnabled(mc);
        if (this.visible) {
            mc.getTextureManager().bindTexture(texture);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            this.field_146123_n = i >= this.xPosition && j >= this.yPosition && i < this.xPosition + this.width && j < this.yPosition + this.height;
            int k = this.getHoverState(this.field_146123_n);
            this.drawTexturedModalRect(this.xPosition, this.yPosition, 0, 128 + k * 10, this.width, this.height);
            this.mouseDragged(mc, i, j);
        }
    }

    private void checkPouchRestockEnabled(Minecraft mc) {
        InventoryPlayer inv = mc.thePlayer.inventory;
        this.enabled = this.visible = inv.hasItem(LOTRMod.pouch);
        if (this.parentGUI instanceof GuiContainerCreative && (LOTRReflectionClient.getCreativeTabIndex((GuiContainerCreative)this.parentGUI)) != CreativeTabs.tabInventory.getTabIndex()) {
            this.visible = false;
            this.enabled = false;
        }
    }
}

