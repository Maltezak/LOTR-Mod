package lotr.common.item;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.*;
import net.minecraft.world.World;

public class LOTRItemBattleaxe extends LOTRItemSword {
    private float efficiencyOnProperMaterial;

    public LOTRItemBattleaxe(LOTRMaterial material) {
        this(material.toToolMaterial());
    }

    public LOTRItemBattleaxe(Item.ToolMaterial material) {
        super(material);
        this.efficiencyOnProperMaterial = material.getEfficiencyOnProperMaterial();
        this.setHarvestLevel("axe", material.getHarvestLevel());
        this.lotrWeaponDamage += 2.0f;
    }

    @Override
    public float func_150893_a(ItemStack itemstack, Block block) {
        float f = super.func_150893_a(itemstack, block);
        if(f == 1.0f && block != null && (block.getMaterial() == Material.wood || block.getMaterial() == Material.plants || block.getMaterial() == Material.vine)) {
            return this.efficiencyOnProperMaterial;
        }
        return f;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack itemstack, World world, Block block, int i, int j, int k, EntityLivingBase entity) {
        if(block.getBlockHardness(world, i, j, k) != 0.0) {
            itemstack.damageItem(1, entity);
        }
        return true;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack itemstack) {
        return EnumAction.none;
    }
}
