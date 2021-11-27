package lotr.common.block;

import java.util.Random;

import cpw.mods.fml.relauncher.*;
import lotr.common.*;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.item.Item;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class LOTRBlockCoralReef extends Block {
    private IIcon[] plantIcons;
    private static final String[] plantNames = new String[] {"purple", "yellow", "blue", "red", "green"};
    private static final Random iconRand = new Random();

    public LOTRBlockCoralReef() {
        super(Material.rock);
        this.setCreativeTab(LOTRCreativeTabs.tabBlock);
        this.setHardness(1.0f);
        this.setResistance(5.0f);
        this.setStepSound(Block.soundTypeStone);
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister iconregister) {
        super.registerBlockIcons(iconregister);
        this.plantIcons = new IIcon[plantNames.length];
        for(int i = 0; i < plantNames.length; ++i) {
            this.plantIcons[i] = iconregister.registerIcon(this.getTextureName() + "_" + plantNames[i]);
        }
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public IIcon getIcon(int i, int j) {
        return super.getIcon(i, j);
    }

    public IIcon getRandomPlantIcon(int i, int j, int k) {
        int hash = i * 25799626 ^ k * 6879038 ^ j;
        iconRand.setSeed(hash);
        iconRand.setSeed(iconRand.nextLong());
        return this.plantIcons[iconRand.nextInt(this.plantIcons.length)];
    }

    @Override
    public int getRenderType() {
        return LOTRMod.proxy.getCoralRenderID();
    }

    @Override
    public Item getItemDropped(int i, Random random, int j) {
        return LOTRMod.coral;
    }

    @Override
    public int quantityDropped(Random random) {
        return 1 + random.nextInt(2);
    }

    @Override
    public int quantityDroppedWithBonus(int i, Random random) {
        int drops = this.quantityDropped(random);
        if(i > 0) {
            int factor = random.nextInt(i + 2) - 1;
            factor = Math.max(factor, 0);
            drops *= factor + 1;
        }
        return drops;
    }

    @Override
    public void dropBlockAsItemWithChance(World world, int i, int j, int k, int meta, float f, int fortune) {
        super.dropBlockAsItemWithChance(world, i, j, k, meta, f, fortune);
        int amountXp = MathHelper.getRandomIntegerInRange(world.rand, 0, 2);
        this.dropXpOnBlockBreak(world, i, j, k, amountXp);
    }

    @Override
    public void onEntityWalking(World world, int i, int j, int k, Entity entity) {
        if(entity instanceof EntityLivingBase && !(entity instanceof EntityWaterMob)) {
            entity.attackEntityFrom(DamageSource.cactus, 0.5f);
        }
    }
}
