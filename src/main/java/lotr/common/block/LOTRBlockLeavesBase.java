package lotr.common.block;

import java.util.*;

import cpw.mods.fml.relauncher.*;
import lotr.common.*;
import lotr.common.world.LOTRWorldProvider;
import net.minecraft.block.BlockLeaves;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.*;
import net.minecraft.util.IIcon;
import net.minecraft.world.*;

public class LOTRBlockLeavesBase
extends BlockLeaves {
    public static List allLeafBlocks = new ArrayList();
    @SideOnly(value=Side.CLIENT)
    private IIcon[][] leafIcons;
    private String[] leafNames;
    private String vanillaTextureName;

    public LOTRBlockLeavesBase() {
        this(false, null);
    }

    public LOTRBlockLeavesBase(boolean vanilla, String vname) {
        if (vanilla) {
            this.setCreativeTab(CreativeTabs.tabDecorations);
            this.vanillaTextureName = vname;
        } else {
            this.setCreativeTab(LOTRCreativeTabs.tabDeco);
        }
        allLeafBlocks.add(this);
    }

    protected void setLeafNames(String ... s) {
        this.leafNames = s;
        this.setSeasonal(new boolean[s.length]);
    }

    public String[] func_150125_e() {
        return this.leafNames;
    }

    public String[] getAllLeafNames() {
        return this.leafNames;
    }

    protected void setSeasonal(boolean ... b) {
        if (b.length != this.leafNames.length) {
            throw new IllegalArgumentException("Leaf seasons length must match number of types");
        }
    }

    @SideOnly(value=Side.CLIENT)
    public int getRenderColor(int i) {
        return 16777215;
    }

    @SideOnly(value=Side.CLIENT)
    public int colorMultiplier(IBlockAccess world, int i, int j, int k) {
        return 16777215;
    }

    protected static int getBiomeLeafColor(IBlockAccess world, int i, int j, int k) {
        int totalR = 0;
        int totalG = 0;
        int totalB = 0;
        int count = 0;
        int range = 1;
        for (int i1 = -range; i1 <= range; ++i1) {
            for (int k1 = -range; k1 <= range; ++k1) {
                int biomeColor = world.getBiomeGenForCoords(i + i1, k + k1).getBiomeFoliageColor(i + i1, j, k + k1);
                totalR += (biomeColor & 0xFF0000) >> 16;
                totalG += (biomeColor & 0xFF00) >> 8;
                totalB += biomeColor & 0xFF;
                ++count;
            }
        }
        int avgR = totalR / count & 0xFF;
        int avgG = totalG / count & 0xFF;
        int avgB = totalB / count & 0xFF;
        return avgR << 16 | avgG << 8 | avgB;
    }

    protected boolean shouldOakUseBiomeColor() {
        LOTRDate.Season season = LOTRDate.ShireReckoning.getSeason();
        return season == LOTRDate.Season.SPRING || season == LOTRDate.Season.SUMMER || !(LOTRMod.proxy.getClientWorld().provider instanceof LOTRWorldProvider);
    }

    public ArrayList<ItemStack> getDrops(World world, int i, int j, int k, int meta, int fortune) {
        ArrayList<ItemStack> drops = new ArrayList<>();
        int saplingChanceBase = this.getSaplingChance(meta & 3);
        int saplingChance = this.calcFortuneModifiedDropChance(saplingChanceBase, fortune);
        if (world.rand.nextInt(saplingChance) == 0) {
            drops.add(new ItemStack(this.getItemDropped(meta, world.rand, fortune), 1, this.damageDropped(meta)));
        }
        this.addSpecialLeafDrops(drops, world, i, j, k, meta, fortune);
        return drops;
    }

    protected int getSaplingChance(int meta) {
        return 20;
    }

    protected void addSpecialLeafDrops(ArrayList drops, World world, int i, int j, int k, int meta, int fortune) {
    }

    protected int calcFortuneModifiedDropChance(int baseChance, int fortune) {
        int chance = baseChance;
        if (fortune > 0) {
            chance -= 2 << fortune;
            chance = Math.max(chance, baseChance / 2);
            chance = Math.max(chance, 1);
        }
        return chance;
    }

    @SideOnly(value=Side.CLIENT)
    public IIcon getIcon(int i, int j) {
        int meta = j & 3;
        if (meta >= this.leafNames.length) {
            meta = 0;
        }
        return this.leafIcons[meta][this.field_150121_P ? 0 : 1];
    }

    @SideOnly(value=Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconregister) {
        this.leafIcons = new IIcon[this.leafNames.length][2];
        for (int i = 0; i < this.leafNames.length; ++i) {
            IIcon fancy = iconregister.registerIcon(this.getTextureName() + "_" + this.leafNames[i] + "_fancy");
            IIcon fast = iconregister.registerIcon(this.getTextureName() + "_" + this.leafNames[i] + "_fast");
            this.leafIcons[i][0] = fancy;
            this.leafIcons[i][1] = fast;
        }
    }

    public String getTextureName() {
        if (this.vanillaTextureName != null) {
            return this.vanillaTextureName;
        }
        return super.getTextureName();
    }

    @SideOnly(value=Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List list) {
        for (int j = 0; j < this.leafNames.length; ++j) {
            list.add(new ItemStack(item, 1, j));
        }
    }

    public static void setAllGraphicsLevels(boolean flag) {
        for (int i = 0; i < allLeafBlocks.size(); ++i) {
            ((LOTRBlockLeavesBase)(allLeafBlocks.get(i))).setGraphicsLevel(flag);
        }
    }
}

