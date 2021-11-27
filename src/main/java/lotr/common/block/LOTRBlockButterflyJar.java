package lotr.common.block;

import cpw.mods.fml.relauncher.*;
import lotr.common.LOTRMod;
import lotr.common.entity.animal.LOTREntityButterfly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.util.IIcon;

public class LOTRBlockButterflyJar extends LOTRBlockAnimalJar {
    @SideOnly(value = Side.CLIENT)
    private IIcon glassIcon;
    @SideOnly(value = Side.CLIENT)
    private IIcon lidIcon;

    public LOTRBlockButterflyJar() {
        super(Material.glass);
        this.setBlockBounds(0.1875f, 0.0f, 0.1875f, 0.8125f, 0.75f, 0.8125f);
        this.setHardness(0.0f);
        this.setStepSound(Block.soundTypeGlass);
    }

    @Override
    public boolean canCapture(Entity entity) {
        return entity instanceof LOTREntityButterfly;
    }

    @Override
    public float getJarEntityHeight() {
        return 0.25f;
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public IIcon getIcon(int i, int j) {
        if(i == -1) {
            return this.lidIcon;
        }
        return this.glassIcon;
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister iconregister) {
        this.glassIcon = iconregister.registerIcon(this.getTextureName() + "_glass");
        this.lidIcon = iconregister.registerIcon(this.getTextureName() + "_lid");
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public int getRenderBlockPass() {
        return 1;
    }

    @Override
    public int getRenderType() {
        return LOTRMod.proxy.getButterflyJarRenderID();
    }
}
