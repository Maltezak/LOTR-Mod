package lotr.client.gui;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import lotr.common.entity.npc.*;
import lotr.common.fac.*;
import lotr.common.network.*;
import net.minecraft.util.*;

public abstract class LOTRGuiHiredNPC
extends LOTRGuiScreenBase {
    private static ResourceLocation guiTexture = new ResourceLocation("lotr:gui/npc/hired.png");
    public int xSize = 200;
    public int ySize = 220;
    public int guiLeft;
    public int guiTop;
    public LOTREntityNPC theNPC;
    public int page;

    public LOTRGuiHiredNPC(LOTREntityNPC npc) {
        this.theNPC = npc;
    }

    public void initGui() {
        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;
    }

    public void drawScreen(int i, int j, float f) {
        this.drawDefaultBackground();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(guiTexture);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
        String s = this.theNPC.getNPCName();
        this.fontRendererObj.drawString(s, this.guiLeft + this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, this.guiTop + 11, 3618615);
        s = this.theNPC.getEntityClassName();
        this.fontRendererObj.drawString(s, this.guiLeft + this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, this.guiTop + 26, 3618615);
        if (this.page == 0 && this.theNPC.hiredNPCInfo.hasHiringRequirements()) {
            int x = this.guiLeft + 6;
            int y = this.guiTop + 170;
            s = StatCollector.translateToLocal("lotr.hiredNPC.commandReq");
            this.fontRendererObj.drawString(s, x, y, 3618615);
            y += this.fontRendererObj.FONT_HEIGHT;
            x += 4;
            ArrayList requirementLines = new ArrayList();
            int maxWidth = this.xSize - 12 - 4;
            LOTRFaction fac = this.theNPC.getHiringFaction();
            String alignS = LOTRAlignmentValues.formatAlignForDisplay(this.theNPC.hiredNPCInfo.alignmentRequiredToCommand);
            String alignReq = StatCollector.translateToLocalFormatted("lotr.hiredNPC.commandReq.align", alignS, fac.factionName());
            requirementLines.addAll(this.fontRendererObj.listFormattedStringToWidth(alignReq, maxWidth));
            LOTRUnitTradeEntry.PledgeType pledge = this.theNPC.hiredNPCInfo.pledgeType;
            String pledgeReq = pledge.getCommandReqText(fac);
            if (pledgeReq != null) {
                requirementLines.addAll(this.fontRendererObj.listFormattedStringToWidth(pledgeReq, maxWidth));
            }
            for (Object obj : requirementLines) {
                String line = (String)obj;
                this.fontRendererObj.drawString(line, x, y, 3618615);
                y += this.fontRendererObj.FONT_HEIGHT;
            }
        }
        super.drawScreen(i, j, f);
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        if (!this.theNPC.isEntityAlive() || this.theNPC.hiredNPCInfo.getHiringPlayer() != this.mc.thePlayer || this.theNPC.getDistanceSqToEntity(this.mc.thePlayer) > 64.0) {
            this.mc.thePlayer.closeScreen();
        }
    }

    public void onGuiClosed() {
        super.onGuiClosed();
        this.sendActionPacket(-1);
    }

    public void sendActionPacket(int action) {
        this.sendActionPacket(action, 0);
    }

    public void sendActionPacket(int action, int value) {
        LOTRPacketHiredUnitCommand packet = new LOTRPacketHiredUnitCommand(this.theNPC.getEntityId(), this.page, action, value);
        LOTRPacketHandler.networkWrapper.sendToServer(packet);
    }
}

