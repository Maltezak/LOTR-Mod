package lotr.common.inventory;

public class LOTRSlotStackSize implements Comparable {
    public int slot;
    public int stackSize;

    public LOTRSlotStackSize(int i, int j) {
        this.slot = i;
        this.stackSize = j;
    }

    @Override
    public int compareTo(Object obj) {
        if(obj instanceof LOTRSlotStackSize) {
            LOTRSlotStackSize obj1 = (LOTRSlotStackSize) obj;
            if(obj1.stackSize < this.stackSize) {
                return 1;
            }
            if(obj1.stackSize > this.stackSize) {
                return -1;
            }
            if(obj1.stackSize == this.stackSize) {
                if(obj1.slot < this.slot) {
                    return 1;
                }
                if(obj1.slot > this.slot) {
                    return -1;
                }
            }
        }
        return 0;
    }
}
