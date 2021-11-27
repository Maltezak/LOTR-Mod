package lotr.common.item;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.*;

public class LOTRItemMattock extends LOTRItemPickaxe {
    private float efficiencyOnProperMaterial;

    public LOTRItemMattock(LOTRMaterial material) {
        this(material.toToolMaterial());
    }

    public LOTRItemMattock(Item.ToolMaterial material) {
        super(material);
        this.efficiencyOnProperMaterial = material.getEfficiencyOnProperMaterial();
        this.setHarvestLevel("axe", material.getHarvestLevel());
    }

    @Override
    public float func_150893_a(ItemStack itemstack, Block block) {
        float f = super.func_150893_a(itemstack, block);
        if(f == 1.0f && block != null && (block.getMaterial() == Material.wood || block.getMaterial() == Material.plants || block.getMaterial() == Material.vine)) {
            return this.efficiencyOnProperMaterial;
        }
        return f;
    }
}
