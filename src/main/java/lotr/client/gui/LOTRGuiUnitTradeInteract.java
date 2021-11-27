package lotr.client.gui;

import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.network.*;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.StatCollector;

public class LOTRGuiUnitTradeInteract extends LOTRGuiNPCInteract {
    private GuiButton buttonTalk;
    private GuiButton buttonHire;

    public LOTRGuiUnitTradeInteract(LOTREntityNPC entity) {
        super(entity);
    }

    @Override
    public void initGui() {
        this.buttonTalk = new GuiButton(0, this.width / 2 - 65, this.height / 5 * 3, 60, 20, StatCollector.translateToLocal("lotr.gui.npc.talk"));
        this.buttonHire = new GuiButton(1, this.width / 2 + 5, this.height / 5 * 3, 60, 20, StatCollector.translateToLocal("lotr.gui.npc.hire"));
        this.buttonList.add(this.buttonTalk);
        this.buttonList.add(this.buttonHire);
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if(button.enabled) {
            LOTRPacketUnitTraderInteract packet = new LOTRPacketUnitTraderInteract(this.theEntity.getEntityId(), button.id);
            LOTRPacketHandler.networkWrapper.sendToServer(packet);
        }
    }
}
