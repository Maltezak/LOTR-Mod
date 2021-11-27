package lotr.client.gui;

import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.network.*;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.StatCollector;

public class LOTRGuiHiredInteract extends LOTRGuiNPCInteract {
    public LOTRGuiHiredInteract(LOTREntityNPC entity) {
        super(entity);
    }

    @Override
    public void initGui() {
        this.buttonList.add(new GuiButton(0, this.width / 2 - 65, this.height / 5 * 3, 60, 20, StatCollector.translateToLocal("lotr.gui.npc.talk")));
        this.buttonList.add(new GuiButton(1, this.width / 2 + 5, this.height / 5 * 3, 60, 20, StatCollector.translateToLocal("lotr.gui.npc.command")));
        this.buttonList.add(new GuiButton(2, this.width / 2 - 65, this.height / 5 * 3 + 25, 130, 20, StatCollector.translateToLocal("lotr.gui.npc.dismiss")));
        ((GuiButton) this.buttonList.get(0)).enabled = this.theEntity.getSpeechBank(this.mc.thePlayer) != null;
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if(button.enabled) {
            if(button.id == 2) {
                this.mc.displayGuiScreen(new LOTRGuiHiredDismiss(this.theEntity));
                return;
            }
            LOTRPacketHiredUnitInteract packet = new LOTRPacketHiredUnitInteract(this.theEntity.getEntityId(), button.id);
            LOTRPacketHandler.networkWrapper.sendToServer(packet);
        }
    }
}
