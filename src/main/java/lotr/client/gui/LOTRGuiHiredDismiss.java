package lotr.client.gui;

import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.network.*;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.Entity;
import net.minecraft.util.StatCollector;

public class LOTRGuiHiredDismiss extends LOTRGuiNPCInteract {
    public LOTRGuiHiredDismiss(LOTREntityNPC entity) {
        super(entity);
    }

    @Override
    public void initGui() {
        this.buttonList.add(new GuiButton(0, this.width / 2 - 65, this.height / 5 * 3 + 40, 60, 20, StatCollector.translateToLocal("lotr.gui.dismiss.dismiss")));
        this.buttonList.add(new GuiButton(1, this.width / 2 + 5, this.height / 5 * 3 + 40, 60, 20, StatCollector.translateToLocal("lotr.gui.dismiss.cancel")));
    }

    @Override
    public void drawScreen(int i, int j, float f) {
        boolean hasRider;
        super.drawScreen(i, j, f);
        String s = StatCollector.translateToLocal("lotr.gui.dismiss.warning1");
        int y = this.height / 5 * 3;
        this.fontRendererObj.drawString(s, (this.width - this.fontRendererObj.getStringWidth(s)) / 2, y, 16777215);
        s = StatCollector.translateToLocal("lotr.gui.dismiss.warning2");
        this.fontRendererObj.drawString(s, (this.width - this.fontRendererObj.getStringWidth(s)) / 2, y += this.fontRendererObj.FONT_HEIGHT, 16777215);
        y += this.fontRendererObj.FONT_HEIGHT;
        Entity mount = this.theEntity.ridingEntity;
        Entity rider = this.theEntity.riddenByEntity;
        boolean hasMount = mount instanceof LOTREntityNPC && ((LOTREntityNPC) mount).hiredNPCInfo.getHiringPlayer() == this.mc.thePlayer;
        hasRider = rider instanceof LOTREntityNPC && ((LOTREntityNPC) rider).hiredNPCInfo.getHiringPlayer() == this.mc.thePlayer;
        if(hasMount) {
            s = StatCollector.translateToLocal("lotr.gui.dismiss.mount");
            this.fontRendererObj.drawString(s, (this.width - this.fontRendererObj.getStringWidth(s)) / 2, y, 11184810);
            y += this.fontRendererObj.FONT_HEIGHT;
        }
        if(hasRider) {
            s = StatCollector.translateToLocal("lotr.gui.dismiss.rider");
            this.fontRendererObj.drawString(s, (this.width - this.fontRendererObj.getStringWidth(s)) / 2, y, 11184810);
            y += this.fontRendererObj.FONT_HEIGHT;
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if(button.enabled) {
            if(button.id == 1) {
                this.mc.displayGuiScreen(new LOTRGuiHiredInteract(this.theEntity));
                return;
            }
            LOTRPacketHiredUnitDismiss packet = new LOTRPacketHiredUnitDismiss(this.theEntity.getEntityId(), button.id);
            LOTRPacketHandler.networkWrapper.sendToServer(packet);
        }
    }
}
