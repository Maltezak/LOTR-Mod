package lotr.common.item;

import java.util.UUID;

import com.google.common.collect.Multimap;

import cpw.mods.fml.relauncher.*;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class LOTRItemSword extends ItemSword {
    @SideOnly(value = Side.CLIENT)
    public IIcon glowingIcon;
    private boolean isElvenBlade = false;
    protected float lotrWeaponDamage;

    public LOTRItemSword(LOTRMaterial material) {
        this(material.toToolMaterial());
    }

    public LOTRItemSword(Item.ToolMaterial material) {
        super(material);
        this.setCreativeTab(LOTRCreativeTabs.tabCombat);
        this.lotrWeaponDamage = material.getDamageVsEntity() + 4.0f;
    }

    public LOTRItemSword addWeaponDamage(float f) {
        this.lotrWeaponDamage += f;
        return this;
    }

    public float getLOTRWeaponDamage() {
        return this.lotrWeaponDamage;
    }

    public LOTRItemSword setIsElvenBlade() {
        this.isElvenBlade = true;
        return this;
    }

    public boolean isElvenBlade() {
        return this.isElvenBlade;
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void registerIcons(IIconRegister iconregister) {
        super.registerIcons(iconregister);
        if(this.isElvenBlade) {
            this.glowingIcon = iconregister.registerIcon(this.getIconString() + "_glowing");
        }
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        if(this.getItemUseAction(itemstack) == EnumAction.none) {
            return itemstack;
        }
        return super.onItemRightClick(itemstack, world, entityplayer);
    }

    @Override
    public Multimap getItemAttributeModifiers() {
        Multimap multimap = super.getItemAttributeModifiers();
        multimap.removeAll(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName());
        multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "LOTR Weapon modifier", this.lotrWeaponDamage, 0));
        return multimap;
    }

    public static UUID accessWeaponDamageModifier() {
        return field_111210_e;
    }
}
