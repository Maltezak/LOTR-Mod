package lotr.common.item;

import java.util.*;

import cpw.mods.fml.relauncher.*;
import lotr.common.*;
import lotr.common.world.LOTRUtumnoLevel;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.world.*;

public class LOTRItemUtumnoKey
extends Item {
    @SideOnly(value=Side.CLIENT)
    private static IIcon[] keyIcons;
    private static String[] keyTypes;

    public LOTRItemUtumnoKey() {
        this.setCreativeTab(LOTRCreativeTabs.tabMisc);
        this.setMaxStackSize(1);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }

    public static ItemStack getRandomKeyPart(Random rand) {
        ItemStack itemstack = new ItemStack(LOTRMod.utumnoKey);
        itemstack.setItemDamage(MathHelper.getRandomIntegerInRange(rand, 2, keyTypes.length - 1));
        return itemstack;
    }

    public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int side, float f, float f1, float f2) {
        if (LOTRDimension.getCurrentDimensionWithFallback(world) == LOTRDimension.UTUMNO && side == 1) {
            Block block = world.getBlock(i, j, k);
            int meta = world.getBlockMetadata(i, j, k);
            LOTRUtumnoLevel utumnoLevel = LOTRUtumnoLevel.forY(j);
            LOTRUtumnoLevel keyUsageLevel = null;
            if (itemstack.getItemDamage() == 0) {
                keyUsageLevel = LOTRUtumnoLevel.ICE;
            } else if (itemstack.getItemDamage() == 1) {
                keyUsageLevel = LOTRUtumnoLevel.OBSIDIAN;
            }
            if (utumnoLevel == keyUsageLevel && j < utumnoLevel.corridorBaseLevels[0] && block.isOpaqueCube()) {
                if (!world.isRemote) {
                    world.playSoundEffect(i + 0.5, j + 0.5, k + 0.5, "random.explode", 2.0f, 0.2f + world.rand.nextFloat() * 0.2f);
                }
                for (int l = 0; l < 60; ++l) {
                    world.spawnParticle("blockcrack_" + Block.getIdFromBlock(block) + "_" + meta, i + 0.5 + world.rand.nextGaussian() * 1.0, j + 1.5, k + 0.5 + world.rand.nextGaussian() * 1.0, 0.0, 0.0, 0.0);
                }
                if (!world.isRemote) {
                    LOTRUtumnoLevel targetLevel = LOTRUtumnoLevel.values()[keyUsageLevel.ordinal() + 1];
                    int stair = 0;
                    int stairX = i - 1;
                    int stairZ = k - 1;
                    int stairY = j;
                    while (((stairY >= targetLevel.corridorBaseLevels[0]) && ((LOTRUtumnoLevel.forY(stairY) != targetLevel) || !world.isAirBlock(stairX, stairY + 1, stairZ) || !World.doesBlockHaveSolidTopSurface(world, stairX, stairY, stairZ)))) {
                        if (stair == 0 || stair == 2 || stair == 4 || stair == 6) {
                            world.setBlock(stairX, stairY, stairZ, keyUsageLevel.brickBlock, keyUsageLevel.brickMeta, 3);
                        } else if (stair == 1) {
                            world.setBlock(stairX, stairY, stairZ, keyUsageLevel.brickStairBlock, 1, 3);
                        } else if (stair == 3) {
                            world.setBlock(stairX, stairY, stairZ, keyUsageLevel.brickStairBlock, 3, 3);
                        } else if (stair == 5) {
                            world.setBlock(stairX, stairY, stairZ, keyUsageLevel.brickStairBlock, 0, 3);
                        } else if (stair == 7) {
                            world.setBlock(stairX, stairY, stairZ, keyUsageLevel.brickStairBlock, 2, 3);
                        }
                        for (int j1 = 1; j1 <= 3; ++j1) {
                            world.setBlock(stairX, stairY + j1, stairZ, Blocks.air, 0, 3);
                        }
                        world.setBlock(i, stairY, k, keyUsageLevel.brickGlowBlock, keyUsageLevel.brickGlowMeta, 3);
                        if (stair <= 1) {
                            ++stairX;
                        } else if (stair <= 3) {
                            ++stairZ;
                        } else if (stair <= 5) {
                            --stairX;
                        } else if (stair <= 7) {
                            --stairZ;
                        }
                        if (stair % 2 == 1) {
                            --stairY;
                        }
                        ++stair;
                        stair %= 8;
                    }
                }
                --itemstack.stackSize;
                return true;
            }
            for (int l = 0; l < 8; ++l) {
                double d = (double)i + (double)world.rand.nextFloat();
                double d1 = j + 1.0;
                double d2 = (double)k + (double)world.rand.nextFloat();
                world.spawnParticle("smoke", d, d1, d2, 0.0, 0.0, 0.0);
            }
            return false;
        }
        return false;
    }

    @SideOnly(value=Side.CLIENT)
    public IIcon getIconFromDamage(int i) {
        if (i >= keyIcons.length) {
            i = 0;
        }
        return keyIcons[i];
    }

    public String getUnlocalizedName(ItemStack itemstack) {
        return super.getUnlocalizedName() + "." + itemstack.getItemDamage();
    }

    @SideOnly(value=Side.CLIENT)
    public void registerIcons(IIconRegister iconregister) {
        keyIcons = new IIcon[keyTypes.length];
        for (int i = 0; i < keyTypes.length; ++i) {
            LOTRItemUtumnoKey.keyIcons[i] = iconregister.registerIcon(this.getIconString() + "_" + keyTypes[i]);
        }
    }

    @SideOnly(value=Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        for (int j = 0; j < keyTypes.length; ++j) {
            list.add(new ItemStack(item, 1, j));
        }
    }

    static {
        keyTypes = new String[]{"ice", "obsidian", "ice0", "ice1", "ice2", "obsidian0", "obsidian1", "obsidian2"};
    }
}

