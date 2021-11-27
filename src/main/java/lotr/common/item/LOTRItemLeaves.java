package lotr.common.item;

import cpw.mods.fml.relauncher.*;
import net.minecraft.block.Block;
import net.minecraft.item.*;
import net.minecraft.util.IIcon;

public class LOTRItemLeaves extends ItemBlock {
    public LOTRItemLeaves(Block block) {
        super(block);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }

    @Override
    public int getMetadata(int i) {
        return i | 4;
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public IIcon getIconFromDamage(int i) {
        return this.field_150939_a.getIcon(0, i);
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public int getColorFromItemStack(ItemStack itemstack, int i) {
        return 16777215;
    }

    @Override
    public String getUnlocalizedName(ItemStack itemstack) {
        return super.getUnlocalizedName() + "." + itemstack.getItemDamage();
    }
}
