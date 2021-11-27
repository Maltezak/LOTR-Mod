package lotr.common.item;

import cpw.mods.fml.relauncher.*;
import net.minecraft.block.Block;
import net.minecraft.item.*;
import net.minecraft.util.IIcon;

public class LOTRItemBlockMetadata extends ItemBlock {
    public LOTRItemBlockMetadata(Block block) {
        super(block);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public IIcon getIconFromDamage(int i) {
        return this.field_150939_a.getIcon(2, i);
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public int getColorFromItemStack(ItemStack itemstack, int pass) {
        return this.field_150939_a.getRenderColor(itemstack.getItemDamage());
    }

    @Override
    public int getMetadata(int i) {
        return i;
    }

    @Override
    public String getUnlocalizedName(ItemStack itemstack) {
        return super.getUnlocalizedName() + "." + itemstack.getItemDamage();
    }
}
