package lotr.client.gui;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import lotr.common.network.*;
import lotr.common.tileentity.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.*;

public class LOTRGuiEditSign extends GuiScreen {
    private LOTRTileEntitySign tileSign;
    private int updateCounter;
    private int editLine;
    private GuiButton buttonDone;
    private static RenderItem itemRenderer = new RenderItem();

    public LOTRGuiEditSign(LOTRTileEntitySign sign) {
        this.tileSign = sign;
    }

    @Override
    public void initGui() {
        this.buttonList.clear();
        Keyboard.enableRepeatEvents(true);
        this.buttonDone = new GuiButton(0, this.width / 2 - 100, this.height / 4 + 120, StatCollector.translateToLocal("gui.done"));
        this.buttonList.add(this.buttonDone);
        this.tileSign.setEditable(false);
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
        LOTRPacketEditSign packet = new LOTRPacketEditSign(this.tileSign);
        LOTRPacketHandler.networkWrapper.sendToServer(packet);
        this.tileSign.setEditable(true);
    }

    @Override
    public void updateScreen() {
        ++this.updateCounter;
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if(button.enabled) {
            this.tileSign.markDirty();
            this.mc.displayGuiScreen(null);
        }
    }

    @Override
    protected void keyTyped(char c, int i) {
        if(i == 200) {
            --this.editLine;
        }
        if(i == 208 || i == 28 || i == 156) {
            ++this.editLine;
        }
        this.editLine &= this.tileSign.getNumLines() - 1;
        if(i == 14 && this.tileSign.signText[this.editLine].length() > 0) {
            String s = this.tileSign.signText[this.editLine];
            this.tileSign.signText[this.editLine] = s.substring(0, s.length() - 1);
        }
        if(ChatAllowedCharacters.isAllowedCharacter(c) && (this.tileSign.signText[this.editLine].length() < 15)) {
		    int n = this.editLine;
		    this.tileSign.signText[n] = this.tileSign.signText[n] + c;
		}
        if(i == 1) {
            this.actionPerformed(this.buttonDone);
        }
    }

    @Override
    public void drawScreen(int i, int j, float f) {
        this.tileSign.getBlockType();
        int meta = this.tileSign.getBlockMetadata();
        float rotation = Direction.facingToDirection[meta] * 90.0f;
        IIcon onIcon = ((LOTRTileEntitySignCarved) this.tileSign).getOnBlockIcon();
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, StatCollector.translateToLocal("sign.edit"), this.width / 2, 40, 16777215);
        GL11.glPushMatrix();
        GL11.glTranslatef(this.width / 2, 0.0f, 50.0f);
        float f1 = 93.75f;
        GL11.glScalef(-f1, -f1, -f1);
        GL11.glTranslatef(0.0f, -1.0625f, 0.0f);
        GL11.glDisable(2929);
        GL11.glPushMatrix();
        float iconScale = 0.5f;
        GL11.glScalef(-iconScale, -iconScale, iconScale);
        GL11.glTranslatef(0.0f, 0.5f, 0.0f);
        this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
        itemRenderer.renderIcon(-1, -1, onIcon, 2, 2);
        GL11.glPopMatrix();
        GL11.glEnable(2929);
        if(this.updateCounter / 6 % 2 == 0) {
            this.tileSign.lineBeingEdited = this.editLine;
        }
        GL11.glRotatef(rotation + 180.0f, 0.0f, 1.0f, 0.0f);
        TileEntityRendererDispatcher.instance.renderTileEntityAt(this.tileSign, -0.5, -0.75, -0.5, 0.0f);
        GL11.glDisable(2896);
        this.tileSign.lineBeingEdited = -1;
        GL11.glPopMatrix();
        super.drawScreen(i, j, f);
    }
}
