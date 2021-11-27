package lotr.common.inventory;

import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.item.ItemStack;

public class LOTRInventoryNPC
extends LOTREntityInventory {
    protected LOTREntityNPC theNPC;

    public LOTRInventoryNPC(String s, LOTREntityNPC npc, int i) {
        super(s, npc, i);
        this.theNPC = npc;
    }

    @Override
    protected void dropItem(ItemStack itemstack) {
        this.theNPC.npcDropItem(itemstack, 0.0f, false, true);
    }
}

