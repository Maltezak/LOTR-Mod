package lotr.common.item;

import lotr.common.entity.item.*;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRItemLionRug extends LOTRItemRugBase {
    public LOTRItemLionRug() {
        super(LionRugType.lionRugNames());
    }

    @Override
    protected LOTREntityRugBase createRug(World world, ItemStack itemstack) {
        LOTREntityLionRug rug = new LOTREntityLionRug(world);
        rug.setRugType(LionRugType.forID(itemstack.getItemDamage()));
        return rug;
    }

    public enum LionRugType {
        LION(0), LIONESS(1);

        public final int lionID;

        LionRugType(int i) {
            this.lionID = i;
        }

        public String textureName() {
            return this.name().toLowerCase();
        }

        public static LionRugType forID(int ID) {
            for(LionRugType t : LionRugType.values()) {
                if(t.lionID != ID) continue;
                return t;
            }
            return LION;
        }

        public static String[] lionRugNames() {
            String[] names = new String[LionRugType.values().length];
            for(int i = 0; i < names.length; ++i) {
                names[i] = LionRugType.values()[i].textureName();
            }
            return names;
        }
    }

}
