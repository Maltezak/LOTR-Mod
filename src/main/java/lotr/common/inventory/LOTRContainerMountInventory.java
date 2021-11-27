package lotr.common.inventory;

import java.util.ArrayList;

import cpw.mods.fml.relauncher.*;
import lotr.common.entity.animal.LOTREntityHorse;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;

public class LOTRContainerMountInventory extends ContainerHorseInventory {
    public LOTRContainerMountInventory(IInventory playerInv, IInventory horseInv, final LOTREntityHorse horse) {
        super(playerInv, horseInv, horse);
        ArrayList slots = new ArrayList(this.inventorySlots);
        this.inventorySlots.clear();
        this.inventoryItemStacks.clear();
        this.addSlotToContainer((Slot) slots.get(0));
        Slot armorSlot = (Slot) slots.get(1);
        this.addSlotToContainer(new Slot(armorSlot.inventory, armorSlot.slotNumber, armorSlot.xDisplayPosition, armorSlot.yDisplayPosition) {

            @Override
            public boolean isItemValid(ItemStack itemstack) {
                return super.isItemValid(itemstack) && horse.func_110259_cr() && horse.isMountArmorValid(itemstack);
            }

            @SideOnly(value = Side.CLIENT)
            @Override
            public boolean func_111238_b() {
                return horse.func_110259_cr();
            }
        });
        for(int i = 2; i < slots.size(); ++i) {
            this.addSlotToContainer((Slot) slots.get(i));
        }
    }

}
