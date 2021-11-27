package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.*;
import lotr.common.entity.npc.*;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.*;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRWorldGenBlueMountainsHouse extends LOTRWorldGenDwarfHouse {
    public LOTRWorldGenBlueMountainsHouse(boolean flag) {
        super(flag);
    }

    @Override
    protected LOTREntityDwarf createDwarf(World world) {
        return new LOTREntityBlueDwarf(world);
    }

    @Override
    protected void setupRandomBlocks(Random random) {
        super.setupRandomBlocks(random);
        this.stoneBlock = Blocks.stone;
        this.stoneMeta = 0;
        this.fillerBlock = LOTRMod.rock;
        this.fillerMeta = 3;
        this.topBlock = LOTRMod.rock;
        this.topMeta = 3;
        this.brick2Block = LOTRMod.brick;
        this.brick2Meta = 14;
        this.pillarBlock = LOTRMod.pillar;
        this.pillarMeta = 3;
        this.chandelierBlock = LOTRMod.chandelier;
        this.chandelierMeta = 11;
        this.tableBlock = LOTRMod.blueDwarvenTable;
        this.barsBlock = LOTRMod.blueDwarfBars;
        this.larderContents = LOTRChestContents.BLUE_DWARF_HOUSE_LARDER;
        this.personalContents = LOTRChestContents.BLUE_MOUNTAINS_STRONGHOLD;
        this.plateFoods = LOTRFoods.BLUE_DWARF;
        this.drinkFoods = LOTRFoods.DWARF_DRINK;
    }

    @Override
    protected ItemStack getRandomWeaponItem(Random random) {
        ItemStack[] items = new ItemStack[] {new ItemStack(LOTRMod.swordBlueDwarven), new ItemStack(LOTRMod.daggerBlueDwarven), new ItemStack(LOTRMod.hammerBlueDwarven), new ItemStack(LOTRMod.battleaxeBlueDwarven), new ItemStack(LOTRMod.pickaxeBlueDwarven), new ItemStack(LOTRMod.mattockBlueDwarven), new ItemStack(LOTRMod.throwingAxeBlueDwarven), new ItemStack(LOTRMod.pikeBlueDwarven)};
        return items[random.nextInt(items.length)].copy();
    }

    @Override
    protected ItemStack getRandomOtherItem(Random random) {
        ItemStack[] items = new ItemStack[] {new ItemStack(LOTRMod.helmetBlueDwarven), new ItemStack(LOTRMod.bodyBlueDwarven), new ItemStack(LOTRMod.legsBlueDwarven), new ItemStack(LOTRMod.bootsBlueDwarven), new ItemStack(LOTRMod.blueDwarfSteel), new ItemStack(LOTRMod.bronze), new ItemStack(Items.iron_ingot), new ItemStack(LOTRMod.silver), new ItemStack(LOTRMod.silverNugget), new ItemStack(Items.gold_ingot), new ItemStack(Items.gold_nugget)};
        return items[random.nextInt(items.length)].copy();
    }
}
