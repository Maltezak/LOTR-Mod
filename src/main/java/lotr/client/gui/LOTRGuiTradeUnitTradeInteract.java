package lotr.client.gui;

import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.network.*;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.StatCollector;

public class LOTRGuiTradeUnitTradeInteract extends LOTRGuiTradeInteract {
    private GuiButton buttonHire;

    public LOTRGuiTradeUnitTradeInteract(LOTREntityNPC entity) {
        super(entity);
    }

    @Override
    public void initGui() {
        super.initGui();
        this.buttonHire = new GuiButton(-1, this.width / 2 - 65, this.height / 5 * 3 + 50, 130, 20, StatCollector.translateToLocal("lotr.gui.npc.hire"));
        this.buttonList.add(this.buttonHire);
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if(button.enabled) {
            if(button == this.buttonHire) {
                LOTRPacketUnitTraderInteract packet = new LOTRPacketUnitTraderInteract(this.theEntity.getEntityId(), 1);
                LOTRPacketHandler.networkWrapper.sendToServer(packet);
            }
            else {
                super.actionPerformed(button);
            }
        }
    }
}
