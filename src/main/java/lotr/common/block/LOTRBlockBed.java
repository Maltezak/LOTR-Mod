package lotr.common.block;

import java.util.Random;

import cpw.mods.fml.relauncher.*;
import net.minecraft.block.*;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.util.*;
import net.minecraft.world.*;

public class LOTRBlockBed extends BlockBed {
    public Item bedItem;
    private Block bedBottomBlock;
    private int bedBottomMetadata;
    @SideOnly(value = Side.CLIENT)
    private IIcon[] bedIconsEnd;
    @SideOnly(value = Side.CLIENT)
    private IIcon[] bedIconsSide;
    @SideOnly(value = Side.CLIENT)
    private IIcon[] bedIconsTop;

    public LOTRBlockBed(Block block, int k) {
        this.bedBottomBlock = block;
        this.bedBottomMetadata = k;
        this.setHardness(0.2f);
        this.setStepSound(Block.soundTypeWood);
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public IIcon getIcon(int i, int j) {
        int i1;
        if(i == 0) {
            return this.bedBottomBlock.getIcon(0, this.bedBottomMetadata);
        }
        int k = BlockDirectional.getDirection(j);
        int l = Direction.bedDirection[k][i];
        i1 = BlockBed.isBlockHeadOfBed(j) ? 1 : 0;
        return (((i1 != 1) || (l != 2)) && ((i1 != 0) || (l != 3))) ? (l != 5 && l != 4 ? this.bedIconsTop[i1] : this.bedIconsSide[i1]) : this.bedIconsEnd[i1];
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister iconregister) {
        this.bedIconsTop = new IIcon[] {iconregister.registerIcon(this.getTextureName() + "_feet_top"), iconregister.registerIcon(this.getTextureName() + "_head_top")};
        this.bedIconsEnd = new IIcon[] {iconregister.registerIcon(this.getTextureName() + "_feet_end"), iconregister.registerIcon(this.getTextureName() + "_head_end")};
        this.bedIconsSide = new IIcon[] {iconregister.registerIcon(this.getTextureName() + "_feet_side"), iconregister.registerIcon(this.getTextureName() + "_head_side")};
    }

    @Override
    public Item getItemDropped(int i, Random random, int j) {
        return BlockBed.isBlockHeadOfBed(i) ? null : this.bedItem;
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public Item getItem(World world, int i, int j, int k) {
        return this.bedItem;
    }

    @Override
    public boolean isBed(IBlockAccess world, int i, int j, int k, EntityLivingBase entity) {
        return true;
    }
}
