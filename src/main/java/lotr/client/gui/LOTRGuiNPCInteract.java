package lotr.client.gui;

import lotr.common.entity.npc.LOTREntityNPC;

public abstract class LOTRGuiNPCInteract extends LOTRGuiScreenBase {
    protected LOTREntityNPC theEntity;

    public LOTRGuiNPCInteract(LOTREntityNPC entity) {
        this.theEntity = entity;
    }

    @Override
    public void drawScreen(int i, int j, float f) {
        this.drawDefaultBackground();
        String s = this.theEntity.getCommandSenderName();
        this.fontRendererObj.drawString(s, (this.width - this.fontRendererObj.getStringWidth(s)) / 2, this.height / 5 * 3 - 20, 16777215);
        super.drawScreen(i, j, f);
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        if(this.theEntity == null || !this.theEntity.isEntityAlive() || this.theEntity.getDistanceSqToEntity(this.mc.thePlayer) > 100.0) {
            this.mc.thePlayer.closeScreen();
        }
    }
}
