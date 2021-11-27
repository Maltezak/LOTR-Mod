package lotr.common.block;

import java.util.*;

import cpw.mods.fml.relauncher.*;
import lotr.common.*;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class LOTRBlockOreGem extends Block {
    @SideOnly(value = Side.CLIENT)
    private IIcon[] oreIcons;
    private String[] oreNames = new String[] {"topaz", "amethyst", "sapphire", "ruby", "amber", "diamond", "opal", "emerald"};

    public LOTRBlockOreGem() {
        super(Material.rock);
        this.setCreativeTab(LOTRCreativeTabs.tabBlock);
        this.setHardness(3.0f);
        this.setResistance(5.0f);
        this.setStepSound(Block.soundTypeStone);
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister iconregister) {
        this.oreIcons = new IIcon[this.oreNames.length];
        for(int i = 0; i < this.oreNames.length; ++i) {
            this.oreIcons[i] = iconregister.registerIcon(this.getTextureName() + "_" + this.oreNames[i]);
        }
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public IIcon getIcon(int i, int j) {
        if(j >= this.oreNames.length) {
            j = 0;
        }
        return this.oreIcons[j];
    }

    @Override
    public Item getItemDropped(int i, Random random, int j) {
        if(i == 0) {
            return LOTRMod.topaz;
        }
        if(i == 1) {
            return LOTRMod.amethyst;
        }
        if(i == 2) {
            return LOTRMod.sapphire;
        }
        if(i == 3) {
            return LOTRMod.ruby;
        }
        if(i == 4) {
            return LOTRMod.amber;
        }
        if(i == 5) {
            return LOTRMod.diamond;
        }
        if(i == 6) {
            return LOTRMod.opal;
        }
        if(i == 7) {
            return LOTRMod.emerald;
        }
        return Item.getItemFromBlock(this);
    }

    @Override
    public int quantityDropped(Random random) {
        return 1 + random.nextInt(2);
    }

    @Override
    public int quantityDroppedWithBonus(int i, Random random) {
        if(i > 0 && Item.getItemFromBlock(this) != this.getItemDropped(0, random, i)) {
            int drops = this.quantityDropped(random);
            return drops += random.nextInt(i + 1);
        }
        return this.quantityDropped(random);
    }

    @Override
    public void dropBlockAsItemWithChance(World world, int i, int j, int k, int meta, float f, int fortune) {
        super.dropBlockAsItemWithChance(world, i, j, k, meta, f, fortune);
        if(this.getItemDropped(meta, world.rand, fortune) != Item.getItemFromBlock(this)) {
            int amountXp = MathHelper.getRandomIntegerInRange(world.rand, 0, 2);
            this.dropXpOnBlockBreak(world, i, j, k, amountXp);
        }
    }

    @Override
    public int getDamageValue(World world, int i, int j, int k) {
        return world.getBlockMetadata(i, j, k);
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void getSubBlocks(Item item, CreativeTabs tab, List list) {
        for(int i = 0; i < this.oreNames.length; ++i) {
            list.add(new ItemStack(item, 1, i));
        }
    }
}
