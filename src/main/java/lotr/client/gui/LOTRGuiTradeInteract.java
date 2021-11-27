package lotr.client.gui;

import lotr.common.entity.npc.*;
import lotr.common.network.*;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.StatCollector;

public class LOTRGuiTradeInteract extends LOTRGuiNPCInteract {
    private GuiButton buttonTalk;
    private GuiButton buttonTrade;
    private GuiButton buttonExchange;
    private GuiButton buttonSmith;

    public LOTRGuiTradeInteract(LOTREntityNPC entity) {
        super(entity);
    }

    @Override
    public void initGui() {
        this.buttonTalk = new GuiButton(0, this.width / 2 - 65, this.height / 5 * 3, 60, 20, StatCollector.translateToLocal("lotr.gui.npc.talk"));
        this.buttonTrade = new GuiButton(1, this.width / 2 + 5, this.height / 5 * 3, 60, 20, StatCollector.translateToLocal("lotr.gui.npc.trade"));
        this.buttonExchange = new GuiButton(2, this.width / 2 - 65, this.height / 5 * 3 + 25, 130, 20, StatCollector.translateToLocal("lotr.gui.npc.exchange"));
        this.buttonList.add(this.buttonTalk);
        this.buttonList.add(this.buttonTrade);
        this.buttonList.add(this.buttonExchange);
        if(this.theEntity instanceof LOTRTradeable.Smith) {
            this.buttonTalk.xPosition -= 35;
            this.buttonTrade.xPosition -= 35;
            this.buttonSmith = new GuiButton(3, this.width / 2 + 40, this.height / 5 * 3, 60, 20, StatCollector.translateToLocal("lotr.gui.npc.smith"));
            this.buttonList.add(this.buttonSmith);
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if(button.enabled) {
            LOTRPacketTraderInteract packet = new LOTRPacketTraderInteract(this.theEntity.getEntityId(), button.id);
            LOTRPacketHandler.networkWrapper.sendToServer(packet);
        }
    }
}
