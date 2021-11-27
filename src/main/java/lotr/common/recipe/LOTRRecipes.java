package lotr.common.recipe;

import java.lang.reflect.Field;
import java.util.*;

import cpw.mods.fml.common.registry.GameRegistry;
import lotr.common.*;
import lotr.common.block.*;
import lotr.common.item.*;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.init.*;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.*;
import net.minecraft.item.crafting.*;
import net.minecraft.world.World;
import net.minecraftforge.oredict.*;

public class LOTRRecipes {
    public static List<IRecipe> woodenSlabRecipes = new ArrayList<>();
    public static List<IRecipe> morgulRecipes = new ArrayList<>();
    public static List<IRecipe> elvenRecipes = new ArrayList<>();
    public static List<IRecipe> dwarvenRecipes = new ArrayList<>();
    public static List<IRecipe> urukRecipes = new ArrayList<>();
    public static List<IRecipe> woodElvenRecipes = new ArrayList<>();
    public static List<IRecipe> gondorianRecipes = new ArrayList<>();
    public static List<IRecipe> rohirricRecipes = new ArrayList<>();
    public static List<IRecipe> dunlendingRecipes = new ArrayList<>();
    public static List<IRecipe> angmarRecipes = new ArrayList<>();
    public static List<IRecipe> nearHaradRecipes = new ArrayList<>();
    public static List<IRecipe> highElvenRecipes = new ArrayList<>();
    public static List<IRecipe> blueMountainsRecipes = new ArrayList<>();
    public static List<IRecipe> rangerRecipes = new ArrayList<>();
    public static List<IRecipe> dolGuldurRecipes = new ArrayList<>();
    public static List<IRecipe> gundabadRecipes = new ArrayList<>();
    public static List<IRecipe> halfTrollRecipes = new ArrayList<>();
    public static List<IRecipe> dolAmrothRecipes = new ArrayList<>();
    public static List<IRecipe> moredainRecipes = new ArrayList<>();
    public static List<IRecipe> tauredainRecipes = new ArrayList<>();
    public static List<IRecipe> daleRecipes = new ArrayList<>();
    public static List<IRecipe> dorwinionRecipes = new ArrayList<>();
    public static List<IRecipe> hobbitRecipes = new ArrayList<>();
    public static List<IRecipe> rhunRecipes = new ArrayList<>();
    public static List<IRecipe> rivendellRecipes = new ArrayList<>();
    public static List<IRecipe> umbarRecipes = new ArrayList<>();
    public static List<IRecipe> gulfRecipes = new ArrayList<>();
    public static List<IRecipe> breeRecipes = new ArrayList<>();
    public static List<IRecipe> uncraftableUnsmeltingRecipes = new ArrayList<>();
    private static List[] commonOrcRecipes = new List[]{morgulRecipes, urukRecipes, angmarRecipes, dolGuldurRecipes, gundabadRecipes, halfTrollRecipes};
    private static List[] commonMorgulRecipes = new List[]{morgulRecipes, angmarRecipes, dolGuldurRecipes};
    private static List[] commonElfRecipes = new List[]{elvenRecipes, woodElvenRecipes, highElvenRecipes, rivendellRecipes};
    private static List[] commonHighElfRecipes = new List[]{highElvenRecipes, rivendellRecipes};
    private static List[] commonDwarfRecipes = new List[]{dwarvenRecipes, blueMountainsRecipes};
    private static List[] commonNumenoreanRecipes = new List[]{gondorianRecipes, dolAmrothRecipes, umbarRecipes};
    private static List[] commonNearHaradRecipes = new List[]{nearHaradRecipes, umbarRecipes, gulfRecipes};
    private static List[] commonHobbitRecipes = new List[]{hobbitRecipes, breeRecipes};
    private static final String[] dyeOreNames = new String[]{"dyeBlack", "dyeRed", "dyeGreen", "dyeBrown", "dyeBlue", "dyePurple", "dyeCyan", "dyeLightGray", "dyeGray", "dyePink", "dyeLime", "dyeYellow", "dyeLightBlue", "dyeMagenta", "dyeOrange", "dyeWhite"};

    public static void createAllRecipes() {
        LOTRRecipes.registerOres();
        RecipeSorter.register("lotr:armorDyes", LOTRRecipesArmorDyes.class, RecipeSorter.Category.SHAPELESS, "after:minecraft:shapeless");
        RecipeSorter.register("lotr:hobbitPipe", LOTRRecipeHobbitPipe.class, RecipeSorter.Category.SHAPELESS, "after:minecraft:shapeless");
        RecipeSorter.register("lotr:pouch", LOTRRecipesPouch.class, RecipeSorter.Category.SHAPELESS, "after:minecraft:shapeless");
        RecipeSorter.register("lotr:leatherHatDye", LOTRRecipeLeatherHatDye.class, RecipeSorter.Category.SHAPELESS, "after:minecraft:shapeless");
        RecipeSorter.register("lotr:featherDye", LOTRRecipeFeatherDye.class, RecipeSorter.Category.SHAPELESS, "after:minecraft:shapeless");
        RecipeSorter.register("lotr:leatherHatFeather", LOTRRecipeLeatherHatFeather.class, RecipeSorter.Category.SHAPELESS, "after:minecraft:shapeless");
        RecipeSorter.register("lotr:haradRobesDye", LOTRRecipeHaradRobesDye.class, RecipeSorter.Category.SHAPELESS, "after:minecraft:shapeless");
        RecipeSorter.register("lotr:poisonWeapon", LOTRRecipePoisonWeapon.class, RecipeSorter.Category.SHAPELESS, "after:minecraft:shapeless");
        RecipeSorter.register("lotr:banners", LOTRRecipesBanners.class, RecipeSorter.Category.SHAPELESS, "after:minecraft:shapeless");
        RecipeSorter.register("lotr:poisonDrink", LOTRRecipesPoisonDrinks.class, RecipeSorter.Category.SHAPELESS, "after:minecraft:shapeless");
        RecipeSorter.register("lotr:treasurePile", LOTRRecipesTreasurePile.class, RecipeSorter.Category.SHAPELESS, "after:minecraft:shapeless");
        RecipeSorter.register("lotr:partyHatDye", LOTRRecipePartyHatDye.class, RecipeSorter.Category.SHAPELESS, "after:minecraft:shapeless");
        LOTRRecipes.modifyStandardRecipes();
        LOTRRecipes.createStandardRecipes();
        LOTRRecipes.createPoisonedDaggerRecipes();
        LOTRRecipes.createPoisonedArrowRecipes();
        LOTRRecipes.createWoodenSlabRecipes();
        CraftingManager.getInstance().getRecipeList().addAll(0, woodenSlabRecipes);
        LOTRRecipes.createSmeltingRecipes();
        LOTRRecipes.createCommonOrcRecipes();
        LOTRRecipes.createCommonMorgulRecipes();
        LOTRRecipes.createCommonElfRecipes();
        LOTRRecipes.createCommonHighElfRecipes();
        LOTRRecipes.createCommonDwarfRecipes();
        LOTRRecipes.createCommonDunedainRecipes();
        LOTRRecipes.createCommonNumenoreanRecipes();
        LOTRRecipes.createCommonNearHaradRecipes();
        LOTRRecipes.createCommonHobbitRecipes();
        LOTRRecipes.createMorgulRecipes();
        LOTRRecipes.createElvenRecipes();
        LOTRRecipes.createDwarvenRecipes();
        LOTRRecipes.createUrukRecipes();
        LOTRRecipes.createWoodElvenRecipes();
        LOTRRecipes.createGondorianRecipes();
        LOTRRecipes.createRohirricRecipes();
        LOTRRecipes.createDunlendingRecipes();
        LOTRRecipes.createAngmarRecipes();
        LOTRRecipes.createNearHaradRecipes();
        LOTRRecipes.createHighElvenRecipes();
        LOTRRecipes.createBlueMountainsRecipes();
        LOTRRecipes.createRangerRecipes();
        LOTRRecipes.createDolGuldurRecipes();
        LOTRRecipes.createGundabadRecipes();
        LOTRRecipes.createHalfTrollRecipes();
        LOTRRecipes.createDolAmrothRecipes();
        LOTRRecipes.createMoredainRecipes();
        LOTRRecipes.createTauredainRecipes();
        LOTRRecipes.createDaleRecipes();
        LOTRRecipes.createDorwinionRecipes();
        LOTRRecipes.createHobbitRecipes();
        LOTRRecipes.createRhunRecipes();
        LOTRRecipes.createRivendellRecipes();
        LOTRRecipes.createUmbarRecipes();
        LOTRRecipes.createGulfRecipes();
        LOTRRecipes.createBreeRecipes();
        LOTRRecipes.createUnsmeltingRecipes();
    }

    private static void registerOres() {
        for (Object obj : Block.blockRegistry) {
            Block block = (Block)obj;
            if (block instanceof LOTRBlockPlanksBase) {
                OreDictionary.registerOre("plankWood", new ItemStack(block, 1, 32767));
            }
            if (block instanceof LOTRBlockSlabBase && block.getMaterial() == Material.wood) {
                OreDictionary.registerOre("slabWood", new ItemStack(block, 1, 32767));
            }
            if (block instanceof LOTRBlockStairs && block.getMaterial() == Material.wood) {
                OreDictionary.registerOre("stairWood", new ItemStack(block, 1, 32767));
            }
            if (block instanceof LOTRBlockWoodBase) {
                OreDictionary.registerOre("logWood", new ItemStack(block, 1, 32767));
            }
            if (block instanceof LOTRBlockLeavesBase) {
                OreDictionary.registerOre("treeLeaves", new ItemStack(block, 1, 32767));
            }
            if (!(block instanceof LOTRBlockSaplingBase)) continue;
            OreDictionary.registerOre("treeSapling", new ItemStack(block, 1, 32767));
        }
        OreDictionary.registerOre("stickWood", LOTRMod.mallornStick);
        OreDictionary.registerOre("stickWood", LOTRMod.blackrootStick);
        for (Object obj : Item.itemRegistry) {
            Item item = (Item)obj;
            if (item == Items.bone || item instanceof LOTRItemBone) {
                OreDictionary.registerOre("bone", item);
            }
            if (!(item instanceof LOTRItemBerry)) continue;
            OreDictionary.registerOre("berry", item);
        }
        OreDictionary.registerOre("oreCopper", LOTRMod.oreCopper);
        OreDictionary.registerOre("oreTin", LOTRMod.oreTin);
        OreDictionary.registerOre("oreSilver", LOTRMod.oreSilver);
        OreDictionary.registerOre("oreSulfur", LOTRMod.oreSulfur);
        OreDictionary.registerOre("oreSaltpeter", LOTRMod.oreSaltpeter);
        OreDictionary.registerOre("oreSalt", LOTRMod.oreSalt);
        OreDictionary.registerOre("ingotCopper", LOTRMod.copper);
        OreDictionary.registerOre("ingotTin", LOTRMod.tin);
        OreDictionary.registerOre("ingotBronze", LOTRMod.bronze);
        OreDictionary.registerOre("ingotSilver", LOTRMod.silver);
        OreDictionary.registerOre("nuggetIron", LOTRMod.ironNugget);
        OreDictionary.registerOre("nuggetSilver", LOTRMod.silverNugget);
        OreDictionary.registerOre("sulfur", LOTRMod.sulfur);
        OreDictionary.registerOre("saltpeter", LOTRMod.saltpeter);
        OreDictionary.registerOre("salt", LOTRMod.salt);
        OreDictionary.registerOre("clayBall", Items.clay_ball);
        OreDictionary.registerOre("clayBall", LOTRMod.redClayBall);
        OreDictionary.registerOre("dyeYellow", new ItemStack(LOTRMod.dye, 1, 0));
        OreDictionary.registerOre("dyeWhite", new ItemStack(LOTRMod.dye, 1, 1));
        OreDictionary.registerOre("dyeBlue", new ItemStack(LOTRMod.dye, 1, 2));
        OreDictionary.registerOre("dyeGreen", new ItemStack(LOTRMod.dye, 1, 3));
        OreDictionary.registerOre("dyeBlack", new ItemStack(LOTRMod.dye, 1, 4));
        OreDictionary.registerOre("dyeBrown", new ItemStack(LOTRMod.dye, 1, 5));
        OreDictionary.registerOre("sand", new ItemStack(LOTRMod.whiteSand, 1, 32767));
        OreDictionary.registerOre("sandstone", new ItemStack(LOTRMod.redSandstone, 1, 32767));
        OreDictionary.registerOre("sandstone", new ItemStack(LOTRMod.whiteSandstone, 1, 32767));
        OreDictionary.registerOre("apple", Items.apple);
        OreDictionary.registerOre("apple", LOTRMod.appleGreen);
        OreDictionary.registerOre("feather", Items.feather);
        OreDictionary.registerOre("feather", LOTRMod.swanFeather);
        OreDictionary.registerOre("horn", LOTRMod.rhinoHorn);
        OreDictionary.registerOre("horn", LOTRMod.kineArawHorn);
        OreDictionary.registerOre("horn", LOTRMod.horn);
        OreDictionary.registerOre("arrowTip", Items.flint);
        OreDictionary.registerOre("arrowTip", LOTRMod.rhinoHorn);
        OreDictionary.registerOre("arrowTip", LOTRMod.kineArawHorn);
        OreDictionary.registerOre("arrowTip", LOTRMod.horn);
        OreDictionary.registerOre("poison", LOTRMod.bottlePoison);
        OreDictionary.registerOre("vine", Blocks.vine);
        OreDictionary.registerOre("vine", LOTRMod.willowVines);
        OreDictionary.registerOre("vine", LOTRMod.mirkVines);
    }

        private static void createStandardRecipes() {
        int i;
        int i2;
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.goldRing), "XXX", "X X", "XXX", Character.valueOf('X'), "nuggetGold"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.saddle), "XXX", "Y Y", Character.valueOf('X'), Items.leather, Character.valueOf('Y'), "ingotIron"));
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.bronze, 1), LOTRMod.copper, LOTRMod.tin);
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.shovelBronze), "X", "Y", "Y", Character.valueOf('X'), LOTRMod.bronze, Character.valueOf('Y'), "stickWood"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.pickaxeBronze), "XXX", " Y ", " Y ", Character.valueOf('X'), LOTRMod.bronze, Character.valueOf('Y'), "stickWood"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.axeBronze), "XX", "XY", " Y", Character.valueOf('X'), LOTRMod.bronze, Character.valueOf('Y'), "stickWood"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.swordBronze), "X", "X", "Y", Character.valueOf('X'), LOTRMod.bronze, Character.valueOf('Y'), "stickWood"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.hoeBronze), "XX", " Y", " Y", Character.valueOf('X'), LOTRMod.bronze, Character.valueOf('Y'), "stickWood"));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.helmetBronze), "XXX", "X X", Character.valueOf('X'), LOTRMod.bronze);
        GameRegistry.addRecipe(new ItemStack(LOTRMod.bodyBronze), "X X", "XXX", "XXX", Character.valueOf('X'), LOTRMod.bronze);
        GameRegistry.addRecipe(new ItemStack(LOTRMod.legsBronze), "XXX", "X X", "X X", Character.valueOf('X'), LOTRMod.bronze);
        GameRegistry.addRecipe(new ItemStack(LOTRMod.bootsBronze), "X X", "X X", Character.valueOf('X'), LOTRMod.bronze);
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.planks, 4, 0), new ItemStack(LOTRMod.wood, 1, 0));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsShirePine, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks, 1, 0));
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.silverNugget, 9), LOTRMod.silver);
        GameRegistry.addRecipe(new ItemStack(LOTRMod.silver), "XXX", "XXX", "XXX", Character.valueOf('X'), LOTRMod.silverNugget);
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.silverRing), "XXX", "X X", "XXX", Character.valueOf('X'), "nuggetSilver"));
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.mithrilNugget, 9), LOTRMod.mithril);
        GameRegistry.addRecipe(new ItemStack(LOTRMod.mithril), "XXX", "XXX", "XXX", Character.valueOf('X'), LOTRMod.mithrilNugget);
        GameRegistry.addRecipe(new ItemStack(LOTRMod.mithrilRing), "XXX", "X X", "XXX", Character.valueOf('X'), LOTRMod.mithrilNugget);
        GameRegistry.addShapelessRecipe(new ItemStack(Items.dye, 1, 13), LOTRMod.shireHeather);
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.mug, 2), "X", "Y", "X", Character.valueOf('X'), "ingotTin", Character.valueOf('Y'), "plankWood"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.clayMug, 2), "X", "Y", "X", Character.valueOf('X'), "ingotTin", Character.valueOf('Y'), "clayBall"));
        LOTRVesselRecipes.addRecipes(new ItemStack(LOTRMod.mugChocolate), LOTRMod.mugMilk, new Object[]{Items.sugar, new ItemStack(Items.dye, 1, 3)});
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.appleCrumbleItem), "AAA", "BCB", "DDD", Character.valueOf('A'), Items.milk_bucket, Character.valueOf('B'), "apple", Character.valueOf('C'), Items.sugar, Character.valueOf('D'), Items.wheat));
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.copper, 9), new ItemStack(LOTRMod.blockOreStorage, 1, 0));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.blockOreStorage, 1, 0), "XXX", "XXX", "XXX", Character.valueOf('X'), LOTRMod.copper);
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.tin, 9), new ItemStack(LOTRMod.blockOreStorage, 1, 1));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.blockOreStorage, 1, 1), "XXX", "XXX", "XXX", Character.valueOf('X'), LOTRMod.tin);
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.bronze, 9), new ItemStack(LOTRMod.blockOreStorage, 1, 2));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.blockOreStorage, 1, 2), "XXX", "XXX", "XXX", Character.valueOf('X'), LOTRMod.bronze);
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.silver, 9), new ItemStack(LOTRMod.blockOreStorage, 1, 3));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.blockOreStorage, 1, 3), "XXX", "XXX", "XXX", Character.valueOf('X'), LOTRMod.silver);
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.mithril, 9), new ItemStack(LOTRMod.blockOreStorage, 1, 4));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.blockOreStorage, 1, 4), "XXX", "XXX", "XXX", Character.valueOf('X'), LOTRMod.mithril);
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.chandelier, 2, 0), " X ", "YZY", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), Blocks.torch, Character.valueOf('Z'), "ingotBronze"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.chandelier, 2, 1), " X ", "YZY", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), Blocks.torch, Character.valueOf('Z'), "ingotIron"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.chandelier, 2, 2), " X ", "YZY", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), Blocks.torch, Character.valueOf('Z'), "ingotSilver"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.chandelier, 2, 3), " X ", "YZY", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), Blocks.torch, Character.valueOf('Z'), "ingotGold"));
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.pipeweedSeeds, 2), LOTRMod.pipeweedPlant);
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.pipeweedSeeds), LOTRMod.pipeweedLeaf);
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.shovelMithril), "X", "Y", "Y", Character.valueOf('X'), LOTRMod.mithril, Character.valueOf('Y'), "stickWood"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.pickaxeMithril), "XXX", " Y ", " Y ", Character.valueOf('X'), LOTRMod.mithril, Character.valueOf('Y'), "stickWood"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.axeMithril), "XX", "XY", " Y", Character.valueOf('X'), LOTRMod.mithril, Character.valueOf('Y'), "stickWood"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.swordMithril), "X", "X", "Y", Character.valueOf('X'), LOTRMod.mithril, Character.valueOf('Y'), "stickWood"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.hoeMithril), "XX", " Y", " Y", Character.valueOf('X'), LOTRMod.mithril, Character.valueOf('Y'), "stickWood"));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.helmetMithril), "XXX", "X X", Character.valueOf('X'), LOTRMod.mithrilMail);
        GameRegistry.addRecipe(new ItemStack(LOTRMod.bodyMithril), "X X", "XXX", "XXX", Character.valueOf('X'), LOTRMod.mithrilMail);
        GameRegistry.addRecipe(new ItemStack(LOTRMod.legsMithril), "XXX", "X X", "X X", Character.valueOf('X'), LOTRMod.mithrilMail);
        GameRegistry.addRecipe(new ItemStack(LOTRMod.bootsMithril), "X X", "X X", Character.valueOf('X'), LOTRMod.mithrilMail);
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.spearBronze), "  X", " Y ", "Y  ", Character.valueOf('X'), LOTRMod.bronze, Character.valueOf('Y'), "stickWood"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.spearIron), "  X", " Y ", "Y  ", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.spearMithril), "  X", " Y ", "Y  ", Character.valueOf('X'), LOTRMod.mithril, Character.valueOf('Y'), "stickWood"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.silverCoin, 4), "XX", "XX", Character.valueOf('X'), "nuggetSilver"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.clayPlate, 2), "XX", Character.valueOf('X'), "clayBall"));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.helmetFur), "XXX", "X X", Character.valueOf('X'), LOTRMod.fur);
        GameRegistry.addRecipe(new ItemStack(LOTRMod.bodyFur), "X X", "XXX", "XXX", Character.valueOf('X'), LOTRMod.fur);
        GameRegistry.addRecipe(new ItemStack(LOTRMod.legsFur), "XXX", "X X", "X X", Character.valueOf('X'), LOTRMod.fur);
        GameRegistry.addRecipe(new ItemStack(LOTRMod.bootsFur), "X X", "X X", Character.valueOf('X'), LOTRMod.fur);
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.chandelier, 2, 4), " X ", "YZY", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), Blocks.torch, Character.valueOf('Z'), LOTRMod.mithril));
        GameRegistry.addRecipe(new LOTRRecipeHobbitPipe());
        GameRegistry.addShapelessRecipe(new ItemStack(Items.dye, 4, 15), LOTRMod.wargBone);
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.planks, 4, 4), new ItemStack(LOTRMod.fruitWood, 1, 0));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsApple, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks, 1, 4));
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.planks, 4, 5), new ItemStack(LOTRMod.fruitWood, 1, 1));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsPear, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks, 1, 5));
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.planks, 4, 6), new ItemStack(LOTRMod.fruitWood, 1, 2));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsCherry, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks, 1, 6));
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.dye, 1, 2), LOTRMod.bluebell);
        GameRegistry.addRecipe(new ItemStack(LOTRMod.hearth, 3), "XXX", "YYY", Character.valueOf('X'), new ItemStack(Items.coal, 1, 32767), Character.valueOf('Y'), Items.brick);
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.daggerBronze), "X", "Y", Character.valueOf('X'), LOTRMod.bronze, Character.valueOf('Y'), "stickWood"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.daggerIron), "X", "Y", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.daggerMithril), "X", "Y", Character.valueOf('X'), LOTRMod.mithril, Character.valueOf('Y'), "stickWood"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.battleaxeMithril), "XXX", "XYX", " Y ", Character.valueOf('X'), LOTRMod.mithril, Character.valueOf('Y'), "stickWood"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.hammerMithril), "XYX", "XYX", " Y ", Character.valueOf('X'), LOTRMod.mithril, Character.valueOf('Y'), "stickWood"));
        GameRegistry.addShapelessRecipe(new ItemStack(Items.dye, 3, 15), LOTRMod.orcBone);
        GameRegistry.addShapelessRecipe(new ItemStack(Items.dye, 3, 15), LOTRMod.elfBone);
        GameRegistry.addShapelessRecipe(new ItemStack(Items.dye, 2, 15), LOTRMod.dwarfBone);
        GameRegistry.addShapelessRecipe(new ItemStack(Items.dye, 2, 15), LOTRMod.hobbitBone);
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.commandHorn), "XYX", Character.valueOf('X'), "ingotBronze", Character.valueOf('Y'), "horn"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.arrow, 4), "X", "Y", "Z", Character.valueOf('X'), "arrowTip", Character.valueOf('Y'), "stickWood", Character.valueOf('Z'), "feather"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.crossbowBolt, 4), "X", "Y", "Z", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood", Character.valueOf('Z'), "feather"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.crossbowBolt, 4), "X", "Y", "Z", Character.valueOf('X'), "ingotBronze", Character.valueOf('Y'), "stickWood", Character.valueOf('Z'), "feather"));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.cherryPieItem), "AAA", "BCB", "DDD", Character.valueOf('A'), Items.milk_bucket, Character.valueOf('B'), LOTRMod.cherry, Character.valueOf('C'), Items.sugar, Character.valueOf('D'), Items.wheat);
        GameRegistry.addShapelessRecipe(new ItemStack(Items.dye, 6, 15), LOTRMod.trollBone);
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.ironCrossbow), "XXY", "ZYX", "YZX", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood", Character.valueOf('Z'), Items.string));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.mithrilCrossbow), "XXY", "ZYX", "YZX", Character.valueOf('X'), LOTRMod.mithril, Character.valueOf('Y'), "stickWood", Character.valueOf('Z'), Items.string));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsMirkOak, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks, 1, 2));
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.planks, 4, 2), new ItemStack(LOTRMod.wood, 1, 2));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.horseArmorIron), "X  ", "XYX", "XXX", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), Items.leather));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.horseArmorGold), "X  ", "XYX", "XXX", Character.valueOf('X'), "ingotGold", Character.valueOf('Y'), Items.leather));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.horseArmorDiamond), "X  ", "XYX", "XXX", Character.valueOf('X'), Items.diamond, Character.valueOf('Y'), Items.leather);
        GameRegistry.addRecipe(new LOTRRecipesPouch());
        GameRegistry.addRecipe(new ItemStack(LOTRMod.ancientItem, 1, 0), "X", "Y", "Z", Character.valueOf('X'), new ItemStack(LOTRMod.ancientItemParts, 1, 0), Character.valueOf('Y'), new ItemStack(LOTRMod.ancientItemParts, 1, 1), Character.valueOf('Z'), new ItemStack(LOTRMod.ancientItemParts, 1, 2));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.ancientItem, 1, 1), "X", "Y", Character.valueOf('X'), new ItemStack(LOTRMod.ancientItemParts, 1, 0), Character.valueOf('Y'), new ItemStack(LOTRMod.ancientItemParts, 1, 2));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.ancientItem, 1, 2), "XXX", "X X", Character.valueOf('X'), new ItemStack(LOTRMod.ancientItemParts, 1, 3));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.ancientItem, 1, 3), "X X", "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.ancientItemParts, 1, 3));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.ancientItem, 1, 4), "XXX", "X X", "X X", Character.valueOf('X'), new ItemStack(LOTRMod.ancientItemParts, 1, 3));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.ancientItem, 1, 5), "X X", "X X", Character.valueOf('X'), new ItemStack(LOTRMod.ancientItemParts, 1, 3));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsCharred, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks, 1, 3));
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.planks, 4, 3), new ItemStack(LOTRMod.wood, 1, 3));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.barrel), "XXX", "YZY", "XXX", Character.valueOf('X'), "plankWood", Character.valueOf('Y'), "ingotIron", Character.valueOf('Z'), Items.bucket));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.armorStandItem), " X ", " X ", "YYY", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), Blocks.stone));
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.pebble, 4), Blocks.gravel);
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.sling), "XYX", "XZX", " X ", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), Items.leather, Character.valueOf('Z'), Items.string));
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.planks, 4, 8), new ItemStack(LOTRMod.wood2, 1, 0));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsLebethron, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks, 1, 8));
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.dye, 1, 1), LOTRMod.asphodel);
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.orcSteel, 9), new ItemStack(LOTRMod.blockOreStorage, 1, 5));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.blockOreStorage, 1, 5), "XXX", "XXX", "XXX", Character.valueOf('X'), LOTRMod.orcSteel);
        GameRegistry.addRecipe(new ItemStack(LOTRMod.pressurePlateMordorRock), "XX", Character.valueOf('X'), new ItemStack(LOTRMod.rock, 1, 0));
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.buttonMordorRock), new ItemStack(LOTRMod.rock, 1, 0));
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.nauriteGem, 9), new ItemStack(LOTRMod.blockOreStorage, 1, 10));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.blockOreStorage, 1, 10), "XXX", "XXX", "XXX", Character.valueOf('X'), LOTRMod.nauriteGem);
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.guldurilCrystal, 9), new ItemStack(LOTRMod.blockOreStorage, 1, 11));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.blockOreStorage, 1, 11), "XXX", "XXX", "XXX", Character.valueOf('X'), LOTRMod.guldurilCrystal);
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.dye, 1, 0), LOTRMod.elanor);
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.dye, 1, 1), LOTRMod.niphredil);
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.quenditeCrystal, 9), new ItemStack(LOTRMod.blockOreStorage, 1, 6));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.blockOreStorage, 1, 6), "XXX", "XXX", "XXX", Character.valueOf('X'), LOTRMod.quenditeCrystal);
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.galvorn, 9), new ItemStack(LOTRMod.blockOreStorage, 1, 8));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.blockOreStorage, 1, 8), "XXX", "XXX", "XXX", Character.valueOf('X'), LOTRMod.galvorn);
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.dwarfSteel, 9), new ItemStack(LOTRMod.blockOreStorage, 1, 7));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.blockOreStorage, 1, 7), "XXX", "XXX", "XXX", Character.valueOf('X'), LOTRMod.dwarfSteel);
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.urukSteel, 9), new ItemStack(LOTRMod.blockOreStorage, 1, 9));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.blockOreStorage, 1, 9), "XXX", "XXX", "XXX", Character.valueOf('X'), LOTRMod.urukSteel);
        GameRegistry.addRecipe(new ItemStack(LOTRMod.pressurePlateGondorRock), "XX", Character.valueOf('X'), new ItemStack(LOTRMod.rock, 1, 1));
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.buttonGondorRock), new ItemStack(LOTRMod.rock, 1, 1));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.pressurePlateRohanRock), "XX", Character.valueOf('X'), new ItemStack(LOTRMod.rock, 1, 2));
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.buttonRohanRock), new ItemStack(LOTRMod.rock, 1, 2));
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.planks, 4, 9), new ItemStack(LOTRMod.wood2, 1, 1));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsBeech, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks, 1, 9));
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.morgulSteel, 9), new ItemStack(LOTRMod.blockOreStorage, 1, 12));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.blockOreStorage, 1, 12), "XXX", "XXX", "XXX", Character.valueOf('X'), LOTRMod.morgulSteel);
        GameRegistry.addRecipe(new ItemStack(LOTRMod.leatherHat), " X ", "XXX", Character.valueOf('X'), Items.leather);
        GameRegistry.addRecipe(new LOTRRecipeLeatherHatDye());
        GameRegistry.addRecipe(new LOTRRecipeFeatherDye());
        GameRegistry.addRecipe(new LOTRRecipeLeatherHatFeather());
        GameRegistry.addRecipe(new ItemStack(LOTRMod.pressurePlateBlueRock), "XX", Character.valueOf('X'), new ItemStack(LOTRMod.rock, 1, 3));
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.buttonBlueRock), new ItemStack(LOTRMod.rock, 1, 3));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.slabSingle11, 6, 5), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.rock, 1, 3));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.smoothStone, 2, 3), "X", "X", Character.valueOf('X'), new ItemStack(LOTRMod.rock, 1, 3));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.slabSingle3, 6, 0), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.smoothStone, 1, 3));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.brick, 4, 14), "XX", "XX", Character.valueOf('X'), new ItemStack(LOTRMod.rock, 1, 3));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.wall, 6, 13), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.rock, 1, 3));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.slabSingle3, 6, 1), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick, 1, 14));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsBlueRockBrick, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick, 1, 14));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.wall, 6, 14), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick, 1, 14));
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.planks, 4, 10), new ItemStack(LOTRMod.wood2, 1, 2));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsHolly, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks, 1, 10));
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.rabbitStew), Items.bowl, LOTRMod.rabbitCooked, Items.potato, Items.potato);
        for (i = 0; i <= 15; ++i) {
            if (i == 1) continue;
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.fence, 3, i), "XYX", "XYX", Character.valueOf('X'), new ItemStack(LOTRMod.planks, 1, i), Character.valueOf('Y'), "stickWood"));
        }
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.dye, 2, 0), new ItemStack(LOTRMod.doubleFlower, 1, 1));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.pillar, 3, 3), "X", "X", "X", Character.valueOf('X'), new ItemStack(LOTRMod.rock, 1, 3));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.slabSingle3, 6, 2), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.pillar, 1, 3));
        GameRegistry.addShapelessRecipe(new ItemStack(Items.gunpowder, 2), LOTRMod.sulfur, LOTRMod.saltpeter, new ItemStack(Items.coal, 1, 1));
        GameRegistry.addShapelessRecipe(new ItemStack(Items.dye, 2, 15), LOTRMod.saltpeter, Blocks.dirt);
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.commandSword), "X", "Y", "Z", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "ingotBronze", Character.valueOf('Z'), "stickWood"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.sulfurMatch, 4), "X", "Y", Character.valueOf('X'), "sulfur", Character.valueOf('Y'), "stickWood"));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.blockOreStorage, 1, 13), "XXX", "XXX", "XXX", Character.valueOf('X'), LOTRMod.sulfur);
        GameRegistry.addRecipe(new ItemStack(LOTRMod.blockOreStorage, 1, 14), "XXX", "XXX", "XXX", Character.valueOf('X'), LOTRMod.saltpeter);
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.sulfur, 9), new ItemStack(LOTRMod.blockOreStorage, 1, 13));
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.saltpeter, 9), new ItemStack(LOTRMod.blockOreStorage, 1, 14));
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.planks, 4, 7), new ItemStack(LOTRMod.fruitWood, 1, 3));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsMango, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks, 1, 7));
        LOTRVesselRecipes.addRecipes(new ItemStack(LOTRMod.mugMangoJuice), new Object[]{LOTRMod.mango, LOTRMod.mango});
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.planks, 4, 11), new ItemStack(LOTRMod.wood2, 1, 3));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsBanana, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks, 1, 11));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.bananaCakeItem), "AAA", "BCB", "DDD", Character.valueOf('A'), Items.milk_bucket, Character.valueOf('B'), LOTRMod.banana, Character.valueOf('C'), Items.egg, Character.valueOf('D'), Items.wheat);
        GameRegistry.addRecipe(new ItemStack(LOTRMod.bananaBread), "XYX", Character.valueOf('X'), Items.wheat, Character.valueOf('Y'), LOTRMod.banana);
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.lionBedItem), "XXX", "YYY", Character.valueOf('X'), LOTRMod.lionFur, Character.valueOf('Y'), "plankWood"));
        GameRegistry.addShapelessRecipe(new ItemStack(Items.dye, 2, 13), new ItemStack(LOTRMod.doubleFlower, 1, 2));
        GameRegistry.addShapelessRecipe(new ItemStack(Items.dye, 2, 1), new ItemStack(LOTRMod.doubleFlower, 1, 3));
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.planks, 4, 12), new ItemStack(LOTRMod.wood3, 1, 0));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsMaple, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks, 1, 12));
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.mapleSyrup), new ItemStack(LOTRMod.wood3, 1, 0), Items.bowl);
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.planks, 4, 13), new ItemStack(LOTRMod.wood3, 1, 1));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsLarch, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks, 1, 13));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.helmetGemsbok), "Y Y", "XXX", "X X", Character.valueOf('X'), LOTRMod.gemsbokHide, Character.valueOf('Y'), LOTRMod.gemsbokHorn);
        GameRegistry.addRecipe(new ItemStack(LOTRMod.bodyGemsbok), "X X", "XXX", "XXX", Character.valueOf('X'), LOTRMod.gemsbokHide);
        GameRegistry.addRecipe(new ItemStack(LOTRMod.legsGemsbok), "XXX", "X X", "X X", Character.valueOf('X'), LOTRMod.gemsbokHide);
        GameRegistry.addRecipe(new ItemStack(LOTRMod.bootsGemsbok), "X X", "X X", Character.valueOf('X'), LOTRMod.gemsbokHide);
        GameRegistry.addRecipe(new ItemStack(LOTRMod.pressurePlateRedRock), "XX", Character.valueOf('X'), new ItemStack(LOTRMod.rock, 1, 4));
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.buttonRedRock), new ItemStack(LOTRMod.rock, 1, 4));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.slabSingle11, 6, 6), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.rock, 1, 4));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.smoothStone, 2, 4), "X", "X", Character.valueOf('X'), new ItemStack(LOTRMod.rock, 1, 4));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.slabSingle3, 6, 5), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.smoothStone, 1, 4));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.brick2, 4, 2), "XX", "XX", Character.valueOf('X'), new ItemStack(LOTRMod.rock, 1, 4));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.wall2, 6, 2), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.rock, 1, 4));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.slabSingle3, 6, 6), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick2, 1, 2));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsRedRockBrick, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick2, 1, 2));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.wall2, 6, 3), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick2, 1, 2));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.pillar, 3, 4), "X", "X", "X", Character.valueOf('X'), new ItemStack(LOTRMod.rock, 1, 4));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.slabSingle3, 6, 7), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.pillar, 1, 4));
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.planks, 4, 14), new ItemStack(LOTRMod.wood3, 1, 2));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsDatePalm, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks, 1, 14));
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.blueDwarfSteel, 9), new ItemStack(LOTRMod.blockOreStorage, 1, 15));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.blockOreStorage, 1, 15), "XXX", "XXX", "XXX", Character.valueOf('X'), LOTRMod.blueDwarfSteel);
        GameRegistry.addRecipe(new ItemStack(LOTRMod.thatch, 6, 0), "XYX", "YXY", "XYX", Character.valueOf('X'), Items.wheat, Character.valueOf('Y'), Blocks.dirt);
        GameRegistry.addRecipe(new ItemStack(LOTRMod.slabSingleThatch, 6, 0), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.thatch, 1, 0));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsThatch, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.thatch, 1, 0));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.horseArmorMithril), "X  ", "XYX", "XXX", Character.valueOf('X'), LOTRMod.mithrilMail, Character.valueOf('Y'), Items.leather);
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.strawBedItem), "XXX", "YYY", Character.valueOf('X'), Items.wheat, Character.valueOf('Y'), "plankWood"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.thatchFloor, 3), "XX", Character.valueOf('X'), new ItemStack(LOTRMod.thatch, 1, 0)));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(LOTRMod.redBook), Items.book, new ItemStack(Items.dye, 1, 1), "nuggetGold"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.termiteMound, 1, 1), " X ", "XYX", " X ", Character.valueOf('X'), LOTRMod.termite, Character.valueOf('Y'), Blocks.stone));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Items.gunpowder, 2), LOTRMod.termite));
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.planks, 4, 15), new ItemStack(LOTRMod.wood3, 1, 3));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsMangrove, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks, 1, 15));
        GameRegistry.addShapelessRecipe(new ItemStack(Items.dye, 1, 1), new ItemStack(LOTRMod.haradFlower, 1, 0));
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.dye, 1, 0), new ItemStack(LOTRMod.haradFlower, 1, 1));
        GameRegistry.addShapelessRecipe(new ItemStack(Items.dye, 1, 9), new ItemStack(LOTRMod.haradFlower, 1, 2));
        GameRegistry.addShapelessRecipe(new ItemStack(Items.dye, 1, 13), new ItemStack(LOTRMod.haradFlower, 1, 3));
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.flaxSeeds, 2), LOTRMod.flaxPlant);
        GameRegistry.addShapelessRecipe(new ItemStack(Items.string), LOTRMod.flax);
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.planks2, 4, 0), new ItemStack(LOTRMod.wood4, 1, 0));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsChestnut, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks2, 1, 0));
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.planks2, 4, 1), new ItemStack(LOTRMod.wood4, 1, 1));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsBaobab, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks2, 1, 1));
        for (i = 0; i <= 15; ++i) {
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.fence2, 3, i), "XYX", "XYX", Character.valueOf('X'), new ItemStack(LOTRMod.planks2, 1, i), Character.valueOf('Y'), "stickWood"));
        }
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.planks2, 4, 2), new ItemStack(LOTRMod.wood4, 1, 2));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsCedar, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks2, 1, 2));
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.blackUrukSteel, 9), new ItemStack(LOTRMod.blockOreStorage2, 1, 0));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.blockOreStorage2, 1, 0), "XXX", "XXX", "XXX", Character.valueOf('X'), LOTRMod.blackUrukSteel);
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.utumnoKey, 1, 0), new ItemStack(LOTRMod.utumnoKey, 1, 2), new ItemStack(LOTRMod.utumnoKey, 1, 3), new ItemStack(LOTRMod.utumnoKey, 1, 4));
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.utumnoKey, 1, 1), new ItemStack(LOTRMod.utumnoKey, 1, 5), new ItemStack(LOTRMod.utumnoKey, 1, 6), new ItemStack(LOTRMod.utumnoKey, 1, 7));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.battleaxeIron), "XXX", "XYX", " Y ", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.battleaxeBronze), "XXX", "XYX", " Y ", Character.valueOf('X'), LOTRMod.bronze, Character.valueOf('Y'), "stickWood"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.bronzeCrossbow), "XXY", "ZYX", "YZX", Character.valueOf('X'), LOTRMod.bronze, Character.valueOf('Y'), "stickWood", Character.valueOf('Z'), Items.string));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.commandTable), "XXX", "YYY", "ZZZ", Character.valueOf('X'), Items.paper, Character.valueOf('Y'), "plankWood", Character.valueOf('Z'), "ingotBronze"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.butterflyJar), "X", "Y", Character.valueOf('X'), "plankWood", Character.valueOf('Y'), Blocks.glass));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.berryPieItem), "AAA", "BBB", "CCC", Character.valueOf('A'), Items.milk_bucket, Character.valueOf('B'), "berry", Character.valueOf('C'), Items.wheat));
        LOTRVesselRecipes.addRecipes(new ItemStack(LOTRMod.mugBlueberryJuice), new Object[]{LOTRMod.blueberry, LOTRMod.blueberry, LOTRMod.blueberry});
        LOTRVesselRecipes.addRecipes(new ItemStack(LOTRMod.mugBlackberryJuice), new Object[]{LOTRMod.blackberry, LOTRMod.blackberry, LOTRMod.blackberry});
        LOTRVesselRecipes.addRecipes(new ItemStack(LOTRMod.mugRaspberryJuice), new Object[]{LOTRMod.raspberry, LOTRMod.raspberry, LOTRMod.raspberry});
        LOTRVesselRecipes.addRecipes(new ItemStack(LOTRMod.mugCranberryJuice), new Object[]{LOTRMod.cranberry, LOTRMod.cranberry, LOTRMod.cranberry});
        LOTRVesselRecipes.addRecipes(new ItemStack(LOTRMod.mugElderberryJuice), new Object[]{LOTRMod.elderberry, LOTRMod.elderberry, LOTRMod.elderberry});
        GameRegistry.addRecipe(new ItemStack(LOTRMod.brick3, 1, 0), "XX", "XX", Character.valueOf('X'), new ItemStack(LOTRMod.brick, 1, 14));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.brick3, 1, 1), "XX", "XX", Character.valueOf('X'), new ItemStack(LOTRMod.brick2, 1, 2));
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.elfSteel, 9), new ItemStack(LOTRMod.blockOreStorage2, 1, 1));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.blockOreStorage2, 1, 1), "XXX", "XXX", "XXX", Character.valueOf('X'), LOTRMod.elfSteel);
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.planks2, 4, 3), new ItemStack(LOTRMod.wood4, 1, 3));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsFir, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks2, 1, 3));
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.planks2, 4, 4), new ItemStack(LOTRMod.wood5, 1, 0));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsPine, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks2, 1, 4));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.helmetBone), "XXX", "X X", Character.valueOf('X'), "bone"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.bodyBone), "X X", "XXX", "XXX", Character.valueOf('X'), "bone"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.legsBone), "XXX", "X X", "X X", Character.valueOf('X'), "bone"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.bootsBone), "X X", "X X", Character.valueOf('X'), "bone"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.unsmeltery), "X X", "YXY", "ZZZ", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood", Character.valueOf('Z'), Blocks.cobblestone));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.throwingAxeBronze), " X ", " YX", "Y  ", Character.valueOf('X'), "ingotBronze", Character.valueOf('Y'), "stickWood"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.throwingAxeIron), " X ", " YX", "Y  ", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.bronzeBars, 16), "XXX", "XXX", Character.valueOf('X'), "ingotBronze"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.goldBars, 16), "XXX", "XXX", Character.valueOf('X'), "ingotGold"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.silverBars, 16), "XXX", "XXX", Character.valueOf('X'), "ingotSilver"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.mithrilBars, 16), "XXX", "XXX", Character.valueOf('X'), LOTRMod.mithril));
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.planksRotten, 4, 0), new ItemStack(LOTRMod.rottenLog, 1, 0));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsRotten, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planksRotten, 1, 0));
        for (i = 0; i <= 0; ++i) {
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.fenceRotten, 3, i), "XYX", "XYX", Character.valueOf('X'), new ItemStack(LOTRMod.planksRotten, 1, i), Character.valueOf('Y'), "stickWood"));
        }
        GameRegistry.addRecipe(new ItemStack(LOTRMod.scorchedSlabSingle, 6, 0), "XXX", Character.valueOf('X'), LOTRMod.scorchedStone);
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsScorchedStone, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), LOTRMod.scorchedStone);
        GameRegistry.addRecipe(new ItemStack(LOTRMod.scorchedWall, 6), "XXX", "XXX", Character.valueOf('X'), LOTRMod.scorchedStone);
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.planks2, 4, 5), new ItemStack(LOTRMod.wood5, 1, 1));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsLemon, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks2, 1, 5));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.lemonCakeItem), "AAA", "BCB", "DDD", Character.valueOf('A'), Items.milk_bucket, Character.valueOf('B'), LOTRMod.lemon, Character.valueOf('C'), Items.sugar, Character.valueOf('D'), Items.wheat);
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.planks2, 4, 6), new ItemStack(LOTRMod.wood5, 1, 2));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsOrange, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks2, 1, 6));
        LOTRVesselRecipes.addRecipes(new ItemStack(LOTRMod.mugOrangeJuice), new Object[]{LOTRMod.orange, LOTRMod.orange});
        LOTRVesselRecipes.addRecipes(new ItemStack(LOTRMod.mugLemonade), LOTRMod.mugWater, new Object[]{LOTRMod.lemon, Items.sugar});
        GameRegistry.addRecipe(new ItemStack(LOTRMod.alloyForge), "XXX", "X X", "XXX", Character.valueOf('X'), Blocks.stonebrick);
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.planks2, 4, 7), new ItemStack(LOTRMod.wood5, 1, 3));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsLime, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks2, 1, 7));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.compass), " X ", "XYX", " X ", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "ingotCopper"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.clock), " X ", "XYX", " X ", Character.valueOf('X'), "ingotGold", Character.valueOf('Y'), "ingotCopper"));
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.obsidianShard, 9), Blocks.obsidian);
        GameRegistry.addRecipe(new ItemStack(Blocks.obsidian, 1), "XXX", "XXX", "XXX", Character.valueOf('X'), LOTRMod.obsidianShard);
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Blocks.mossy_cobblestone, 1, 0), new ItemStack(Blocks.cobblestone, 1, 0), "vine"));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Blocks.stonebrick, 1, 1), new ItemStack(Blocks.stonebrick, 1, 0), "vine"));
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.planks2, 4, 8), new ItemStack(LOTRMod.wood6, 1, 0));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsMahogany, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks2, 1, 8));
        GameRegistry.addRecipe(new LOTRRecipesBanners());
        GameRegistry.addRecipe(new ItemStack(LOTRMod.redSandstone, 1, 0), "XX", "XX", Character.valueOf('X'), new ItemStack(Blocks.sand, 1, 1));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.slabSingle7, 6, 5), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.redSandstone, 1, 0));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsRedSandstone, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.redSandstone, 1, 0));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.thatch, 4, 1), "XX", "XX", Character.valueOf('X'), LOTRMod.driedReeds);
        GameRegistry.addRecipe(new ItemStack(LOTRMod.slabSingleThatch, 6, 1), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.thatch, 1, 1));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsReed, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.thatch, 1, 1));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.pikeIron), "  X", " YX", "Y  ", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
        for (i = 0; i <= 3; ++i) {
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.woodBeamV1, 3, i), "X", "X", "X", Character.valueOf('X'), new ItemStack(Blocks.log, 1, i)));
        }
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.reedBars, 16), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.thatch, 1, 1)));
        for (i = 0; i <= 1; ++i) {
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.woodBeamV2, 3, i), "X", "X", "X", Character.valueOf('X'), new ItemStack(Blocks.log2, 1, i)));
        }
        for (i = 0; i <= 3; ++i) {
            if (i == 1) continue;
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.woodBeam1, 3, i), "X", "X", "X", Character.valueOf('X'), new ItemStack(LOTRMod.wood, 1, i)));
        }
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.paper, 3), "XXX", Character.valueOf('X'), LOTRMod.reeds));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.paper, 3), "XXX", Character.valueOf('X'), LOTRMod.cornStalk));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.weaponRack), "X X", "YYY", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), "plankWood"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.wasteBlock, 4), "XY", "YZ", Character.valueOf('X'), Items.rotten_flesh, Character.valueOf('Y'), Blocks.dirt, Character.valueOf('Z'), "bone"));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.dirtPath, 2, 0), "XX", Character.valueOf('X'), Blocks.dirt);
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.planks2, 4, 9), new ItemStack(LOTRMod.wood6, 1, 1));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsWillow, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks2, 1, 9));
        for (i = 0; i <= 3; ++i) {
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.woodBeam2, 3, i), "X", "X", "X", Character.valueOf('X'), new ItemStack(LOTRMod.wood2, 1, i)));
        }
        for (i = 0; i <= 3; ++i) {
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.woodBeamFruit, 3, i), "X", "X", "X", Character.valueOf('X'), new ItemStack(LOTRMod.fruitWood, 1, i)));
        }
        for (i = 0; i <= 3; ++i) {
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.woodBeam3, 3, i), "X", "X", "X", Character.valueOf('X'), new ItemStack(LOTRMod.wood3, 1, i)));
        }
        for (i = 0; i <= 3; ++i) {
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.woodBeam4, 3, i), "X", "X", "X", Character.valueOf('X'), new ItemStack(LOTRMod.wood4, 1, i)));
        }
        for (i = 0; i <= 3; ++i) {
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.woodBeam5, 3, i), "X", "X", "X", Character.valueOf('X'), new ItemStack(LOTRMod.wood5, 1, i)));
        }
        for (i = 0; i <= 3; ++i) {
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.woodBeam6, 3, i), "X", "X", "X", Character.valueOf('X'), new ItemStack(LOTRMod.wood6, 1, i)));
        }
        for (i = 0; i <= 0; ++i) {
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.woodBeamRotten, 3, i), "X", "X", "X", Character.valueOf('X'), new ItemStack(LOTRMod.rottenLog, 1, i)));
        }
        GameRegistry.addRecipe(new ItemStack(LOTRMod.brick4, 4, 15), "XX", "XX", Character.valueOf('X'), new ItemStack(LOTRMod.rock, 1, 5));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsChalkBrick, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick4, 1, 15));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.wall3, 6, 6), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.rock, 1, 5));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.wall3, 6, 7), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick4, 1, 15));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.slabSingle11, 6, 7), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.rock, 1, 5));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.smoothStone, 2, 5), "X", "X", Character.valueOf('X'), new ItemStack(LOTRMod.rock, 1, 5));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.slabSingle8, 6, 7), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.smoothStone, 1, 5));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.slabSingle9, 6, 0), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick4, 1, 15));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.pillar2, 3, 1), "X", "X", "X", Character.valueOf('X'), new ItemStack(LOTRMod.rock, 1, 5));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.slabSingle9, 6, 1), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.pillar2, 1, 1));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.pressurePlateChalk), "XX", Character.valueOf('X'), new ItemStack(LOTRMod.rock, 1, 5));
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.buttonChalk), new ItemStack(LOTRMod.rock, 1, 5));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.wallStoneV, 6, 0), "XXX", "XXX", Character.valueOf('X'), new ItemStack(Blocks.stone, 1, 0));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.wallStoneV, 6, 1), "XXX", "XXX", Character.valueOf('X'), new ItemStack(Blocks.stonebrick, 1, 0));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.wallStoneV, 6, 2), "XXX", "XXX", Character.valueOf('X'), new ItemStack(Blocks.stonebrick, 1, 1));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.wallStoneV, 6, 3), "XXX", "XXX", Character.valueOf('X'), new ItemStack(Blocks.stonebrick, 1, 2));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.wallStoneV, 6, 4), "XXX", "XXX", Character.valueOf('X'), new ItemStack(Blocks.sandstone, 1, 0));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.wallStoneV, 6, 5), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.redSandstone, 1, 0));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.wallStoneV, 6, 6), "XXX", "XXX", Character.valueOf('X'), new ItemStack(Blocks.brick_block, 1, 0));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsStoneBrickMossy, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(Blocks.stonebrick, 1, 1));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsStoneBrickCracked, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(Blocks.stonebrick, 1, 2));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.slabSingleV, 6, 0), "XXX", Character.valueOf('X'), new ItemStack(Blocks.stonebrick, 1, 1));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.slabSingleV, 6, 1), "XXX", Character.valueOf('X'), new ItemStack(Blocks.stonebrick, 1, 2));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsStone, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(Blocks.stone, 1, 0));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsBlueRock, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.rock, 1, 3));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsRedRock, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.rock, 1, 4));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsChalk, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.rock, 1, 5));
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.dye, 1, 3), new ItemStack(LOTRMod.clover, 1, 32767));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.clayTile, 4, 0), "XX", "XX", Character.valueOf('X'), new ItemStack(Blocks.hardened_clay, 1, 0));
        for (i = 0; i <= 15; ++i) {
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.clayTileDyed, 8, i), "XXX", "XYX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.clayTile, 1, 0), Character.valueOf('Y'), dyeOreNames[BlockColored.func_150032_b(i)]));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.clayTileDyed, 4, i), "XX", "XX", Character.valueOf('X'), new ItemStack(Blocks.stained_hardened_clay, 1, i)));
        }
        GameRegistry.addRecipe(new ItemStack(LOTRMod.slabClayTileSingle, 6, 0), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.clayTile, 1, 0));
        for (i = 0; i <= 7; ++i) {
            GameRegistry.addRecipe(new ItemStack(LOTRMod.slabClayTileDyedSingle, 6, i), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.clayTileDyed, 1, i));
            GameRegistry.addRecipe(new ItemStack(LOTRMod.slabClayTileDyedSingle2, 6, i), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.clayTileDyed, 1, i + 8));
        }
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsClayTile, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.clayTile, 1, 0));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsClayTileDyedWhite, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.clayTileDyed, 1, 0));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsClayTileDyedOrange, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.clayTileDyed, 1, 1));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsClayTileDyedMagenta, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.clayTileDyed, 1, 2));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsClayTileDyedLightBlue, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.clayTileDyed, 1, 3));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsClayTileDyedYellow, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.clayTileDyed, 1, 4));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsClayTileDyedLime, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.clayTileDyed, 1, 5));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsClayTileDyedPink, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.clayTileDyed, 1, 6));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsClayTileDyedGray, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.clayTileDyed, 1, 7));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsClayTileDyedLightGray, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.clayTileDyed, 1, 8));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsClayTileDyedCyan, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.clayTileDyed, 1, 9));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsClayTileDyedPurple, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.clayTileDyed, 1, 10));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsClayTileDyedBlue, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.clayTileDyed, 1, 11));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsClayTileDyedBrown, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.clayTileDyed, 1, 12));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsClayTileDyedGreen, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.clayTileDyed, 1, 13));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsClayTileDyedRed, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.clayTileDyed, 1, 14));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsClayTileDyedBlack, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.clayTileDyed, 1, 15));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.pillar2, 3, 2), "X", "X", "X", Character.valueOf('X'), new ItemStack(Blocks.stone, 1, 0));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.slabSingle9, 6, 2), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.pillar2, 1, 2));
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.dye, 1, 4), new ItemStack(Items.coal, 1, 1));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(LOTRMod.dye, 3, 5), "dyeRed", "dyeYellow", "dyeBlack"));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(LOTRMod.dye, 2, 5), "dyeOrange", "dyeBlack"));
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.dye, 1, 1), LOTRMod.simbelmyne);
        GameRegistry.addShapelessRecipe(new ItemStack(Items.dye, 1, 12), LOTRMod.dwarfHerb);
        GameRegistry.addShapelessRecipe(new ItemStack(Items.dye, 1, 10), new ItemStack(LOTRMod.fangornPlant, 1, 0));
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.dye, 1, 5), new ItemStack(LOTRMod.fangornPlant, 1, 1));
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.dye, 1, 0), new ItemStack(LOTRMod.fangornPlant, 1, 2));
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.dye, 1, 0), new ItemStack(LOTRMod.fangornPlant, 1, 3));
        GameRegistry.addShapelessRecipe(new ItemStack(Items.dye, 1, 14), new ItemStack(LOTRMod.fangornPlant, 1, 4));
        GameRegistry.addShapelessRecipe(new ItemStack(Items.dye, 1, 7), new ItemStack(LOTRMod.fangornPlant, 1, 5));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.pillar2, 3, 3), "X", "X", "X", Character.valueOf('X'), Blocks.brick_block);
        GameRegistry.addRecipe(new ItemStack(LOTRMod.slabSingle9, 6, 3), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.pillar2, 1, 3));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.gateGear, 4), " X ", "XYX", " X ", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "plankWood"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.gateWooden, 4), "ZYZ", "YXY", "ZYZ", Character.valueOf('X'), LOTRMod.gateGear, Character.valueOf('Y'), "plankWood", Character.valueOf('Z'), "ingotIron"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.gateIronBars, 4), "YYY", "YXY", "YYY", Character.valueOf('X'), LOTRMod.gateGear, Character.valueOf('Y'), "ingotIron"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.gateBronzeBars, 4), "YYY", "YXY", "YYY", Character.valueOf('X'), LOTRMod.gateGear, Character.valueOf('Y'), "ingotBronze"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.gateWoodenCross, 4), "YYY", "YXY", "YYY", Character.valueOf('X'), LOTRMod.gateGear, Character.valueOf('Y'), "stickWood"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.saddle), "XXX", "Y Y", Character.valueOf('X'), LOTRMod.fur, Character.valueOf('Y'), "ingotIron"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.furBedItem), "XXX", "YYY", Character.valueOf('X'), LOTRMod.fur, Character.valueOf('Y'), "plankWood"));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.slabUtumnoSingle, 6, 0), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.utumnoBrick, 1, 0));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.slabUtumnoSingle, 6, 1), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.utumnoBrick, 1, 2));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.slabUtumnoSingle, 6, 2), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.utumnoBrick, 1, 4));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.slabUtumnoSingle, 6, 3), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.utumnoPillar, 1, 0));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.slabUtumnoSingle, 6, 4), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.utumnoPillar, 1, 1));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.slabUtumnoSingle, 6, 5), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.utumnoPillar, 1, 2));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsUtumnoBrickFire, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.utumnoBrick, 1, 0));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsUtumnoBrickIce, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.utumnoBrick, 1, 2));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsUtumnoBrickObsidian, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.utumnoBrick, 1, 4));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.wallUtumno, 6, 0), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.utumnoBrick, 1, 0));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.wallUtumno, 6, 1), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.utumnoBrick, 1, 2));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.wallUtumno, 6, 2), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.utumnoBrick, 1, 4));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.halberdMithril), " XX", " YX", "Y  ", Character.valueOf('X'), LOTRMod.mithril, Character.valueOf('Y'), "stickWood"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.gateSilver, 4), "YYY", "YXY", "YYY", Character.valueOf('X'), LOTRMod.gateGear, Character.valueOf('Y'), "ingotSilver"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.gateGold, 4), "YYY", "YXY", "YYY", Character.valueOf('X'), LOTRMod.gateGear, Character.valueOf('Y'), "ingotGold"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.gateMithril, 4), "YYY", "YXY", "YYY", Character.valueOf('X'), LOTRMod.gateGear, Character.valueOf('Y'), LOTRMod.mithril));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.brick5, 4, 0), "XX", "XX", Character.valueOf('X'), new ItemStack(LOTRMod.mud, 1, 0));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsMudBrick, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick5, 1, 0));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.wall3, 6, 8), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick5, 1, 0));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.slabSingle9, 6, 5), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick5, 1, 0));
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.leekSoup), Items.bowl, LOTRMod.leek, LOTRMod.leek, Items.potato);
        GameRegistry.addRecipe(new LOTRRecipesPoisonDrinks());
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.planks2, 4, 10), new ItemStack(LOTRMod.wood6, 1, 2));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsCypress, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks2, 1, 10));
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.planks2, 4, 11), new ItemStack(LOTRMod.wood6, 1, 3));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsOlive, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks2, 1, 11));
        LOTRVesselRecipes.addRecipes(new ItemStack(LOTRMod.mugAppleJuice), new Object[]{"apple", "apple"});
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(LOTRMod.redBrick, 1, 0), new ItemStack(Blocks.brick_block, 1, 0), "vine"));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.slabSingleV, 6, 2), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.redBrick, 1, 0));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.slabSingleV, 6, 3), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.redBrick, 1, 1));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsBrickMossy, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.redBrick, 1, 0));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsBrickCracked, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.redBrick, 1, 1));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.wallStoneV, 6, 7), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.redBrick, 1, 0));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.wallStoneV, 6, 8), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.redBrick, 1, 1));
        GameRegistry.addRecipe(new ItemStack(Blocks.dirt, 4, 1), "XY", "YX", Character.valueOf('X'), new ItemStack(Blocks.dirt, 1, 0), Character.valueOf('Y'), Blocks.gravel);
        GameRegistry.addRecipe(new ItemStack(LOTRMod.oliveBread), "XYX", Character.valueOf('X'), Items.wheat, Character.valueOf('Y'), LOTRMod.olive);
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.rollingPin), "XYX", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), "plankWood"));
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.seedsGrapeRed), LOTRMod.grapeRed);
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.seedsGrapeWhite), LOTRMod.grapeWhite);
        LOTRVesselRecipes.addRecipes(new ItemStack(LOTRMod.mugRedGrapeJuice), new Object[]{LOTRMod.grapeRed, LOTRMod.grapeRed, LOTRMod.grapeRed});
        LOTRVesselRecipes.addRecipes(new ItemStack(LOTRMod.mugWhiteGrapeJuice), new Object[]{LOTRMod.grapeWhite, LOTRMod.grapeWhite, LOTRMod.grapeWhite});
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.grapevine), "X", "X", "X", Character.valueOf('X'), "stickWood"));
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.melonSoup), Items.bowl, Items.melon, Items.melon, Items.sugar);
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.planks2, 4, 12), new ItemStack(LOTRMod.wood7, 1, 0));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsAspen, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks2, 1, 12));
        for (i = 0; i <= 3; ++i) {
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.woodBeam7, 3, i), "X", "X", "X", Character.valueOf('X'), new ItemStack(LOTRMod.wood7, 1, i)));
        }
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.planks2, 4, 13), new ItemStack(LOTRMod.wood7, 1, 1));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsGreenOak, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks2, 1, 13));
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.planks2, 4, 14), new ItemStack(LOTRMod.wood7, 1, 2));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsLairelosse, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks2, 1, 14));
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.planks2, 4, 15), new ItemStack(LOTRMod.wood7, 1, 3));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsAlmond, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks2, 1, 15));
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.bottlePoison), Items.glass_bottle, LOTRMod.wildberry);
        GameRegistry.addRecipe(new ItemStack(LOTRMod.slabSingleDirt, 6, 0), "XXX", Character.valueOf('X'), new ItemStack(Blocks.dirt, 1, 0));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.slabSingleDirt, 6, 1), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.dirtPath, 1, 0));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.slabSingleDirt, 6, 2), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.mud, 1, 0));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.slabSingleDirt, 6, 3), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.mordorDirt, 1, 0));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.slabSingleSand, 6, 0), "XXX", Character.valueOf('X'), new ItemStack(Blocks.sand, 1, 0));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.slabSingleSand, 6, 1), "XXX", Character.valueOf('X'), new ItemStack(Blocks.sand, 1, 1));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.slabSingleGravel, 6, 0), "XXX", Character.valueOf('X'), new ItemStack(Blocks.gravel, 1, 0));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.slabSingleGravel, 6, 1), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.mordorGravel, 1, 0));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.slabSingleGravel, 6, 2), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.obsidianGravel, 1, 0));
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.blackrootStick, 2), LOTRMod.blackroot);
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.planks3, 4, 0), new ItemStack(LOTRMod.wood8, 1, 0));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsPlum, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks3, 1, 0));
        for (i = 0; i <= 5; ++i) {
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.fence3, 3, i), "XYX", "XYX", Character.valueOf('X'), new ItemStack(LOTRMod.planks3, 1, i), Character.valueOf('Y'), "stickWood"));
        }
        for (i = 0; i <= 3; ++i) {
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.woodBeam8, 3, i), "X", "X", "X", Character.valueOf('X'), new ItemStack(LOTRMod.wood8, 1, i)));
        }
        GameRegistry.addRecipe(new ItemStack(LOTRMod.whiteSandstone, 1, 0), "XX", "XX", Character.valueOf('X'), new ItemStack(LOTRMod.whiteSand, 1, 0));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.slabSingle10, 6, 6), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.whiteSandstone, 1, 0));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsWhiteSandstone, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.whiteSandstone, 1, 0));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.wall3, 6, 14), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.whiteSandstone, 1, 0));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.slabSingleSand, 6, 2), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.whiteSand, 1, 0));
        LOTRBlockTreasurePile.generateTreasureRecipes(LOTRMod.treasureCopper, LOTRMod.copper);
        LOTRBlockTreasurePile.generateTreasureRecipes(LOTRMod.treasureSilver, LOTRMod.silver);
        LOTRBlockTreasurePile.generateTreasureRecipes(LOTRMod.treasureGold, Items.gold_ingot);
        GameRegistry.addRecipe(new ItemStack(LOTRMod.chestBasket), "XXX", "X X", "XXX", Character.valueOf('X'), LOTRMod.driedReeds);
        GameRegistry.addShapelessRecipe(new ItemStack(Items.dye, 1, 14), LOTRMod.marigold);
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.dye, 1, 2), new ItemStack(LOTRMod.rhunFlower, 1, 0));
        GameRegistry.addShapelessRecipe(new ItemStack(Items.dye, 1, 14), new ItemStack(LOTRMod.rhunFlower, 1, 1));
        GameRegistry.addShapelessRecipe(new ItemStack(Items.dye, 1, 9), new ItemStack(LOTRMod.rhunFlower, 1, 2));
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.dye, 1, 0), new ItemStack(LOTRMod.rhunFlower, 1, 3));
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.dye, 1, 1), new ItemStack(LOTRMod.rhunFlower, 1, 4));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.slabSingleV, 6, 4), "XXX", Character.valueOf('X'), Blocks.mossy_cobblestone);
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsCobblestoneMossy, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), Blocks.mossy_cobblestone);
        GameRegistry.addShapelessRecipe(new ItemStack(Items.dye, 2, 5), new ItemStack(LOTRMod.doubleFlower, 1, 0));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.spearStone), "  X", " Y ", "Y  ", Character.valueOf('X'), "cobblestone", Character.valueOf('Y'), "stickWood"));
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.marzipan), LOTRMod.almond, LOTRMod.almond, Items.sugar);
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.marzipanChocolate), LOTRMod.marzipan, new ItemStack(Items.dye, 1, 3));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.marzipanBlock), "XXX", Character.valueOf('X'), LOTRMod.marzipan);
        GameRegistry.addRecipe(new LOTRRecipePartyHatDye());
        GameRegistry.addRecipe(new ItemStack(LOTRMod.wallClayTile, 6, 0), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.clayTile, 1, 0));
        for (i = 0; i <= 15; ++i) {
            GameRegistry.addRecipe(new ItemStack(LOTRMod.wallClayTileDyed, 6, i), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.clayTileDyed, 1, i));
        }
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.gobletGold, 2), "X X", " X ", " X ", Character.valueOf('X'), "ingotGold"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.gobletSilver, 2), "X X", " X ", " X ", Character.valueOf('X'), "ingotSilver"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.gobletCopper, 2), "X X", " X ", " X ", Character.valueOf('X'), "ingotCopper"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.gobletWood, 2), "X X", " X ", " X ", Character.valueOf('X'), "plankWood"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.skullCup), "X", "Y", Character.valueOf('X'), new ItemStack(Items.skull, 1, 0), Character.valueOf('Y'), "ingotTin"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.wineGlass, 2), "X X", " X ", " X ", Character.valueOf('X'), Blocks.glass));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.waterskin, 2), " Y ", "X X", " X ", Character.valueOf('X'), Items.leather, Character.valueOf('Y'), Items.string));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.waterskin, 2), " Y ", "X X", " X ", Character.valueOf('X'), LOTRMod.gemsbokHide, Character.valueOf('Y'), Items.string));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.waterskin, 2), " Y ", "X X", " X ", Character.valueOf('X'), LOTRMod.fur, Character.valueOf('Y'), Items.string));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.waterskin, 2), " Y ", "X X", " X ", Character.valueOf('X'), LOTRMod.lionFur, Character.valueOf('Y'), Items.string));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.aleHorn), "X", "Y", Character.valueOf('X'), "horn", Character.valueOf('Y'), "ingotTin"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.aleHornGold), "X", "Y", Character.valueOf('X'), "horn", Character.valueOf('Y'), "ingotGold"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.oreGlowstone), "XXX", "XYX", "XXX", Character.valueOf('X'), Items.glowstone_dust, Character.valueOf('Y'), new ItemStack(Blocks.stone, 1, 0)));
        GameRegistry.addRecipe(new ItemStack(Blocks.stonebrick, 1, 3), "XX", "XX", Character.valueOf('X'), new ItemStack(Blocks.stonebrick, 1, 0));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.birdCage, 1, 0), "YYY", "Y Y", "XXX", Character.valueOf('X'), "ingotBronze", Character.valueOf('Y'), LOTRMod.bronzeBars));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.birdCage, 1, 1), "YYY", "Y Y", "XXX", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), Blocks.iron_bars));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.birdCage, 1, 2), "YYY", "Y Y", "XXX", Character.valueOf('X'), "ingotSilver", Character.valueOf('Y'), LOTRMod.silverBars));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.birdCage, 1, 3), "YYY", "Y Y", "XXX", Character.valueOf('X'), "ingotGold", Character.valueOf('Y'), LOTRMod.goldBars));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.birdCageWood, 1, 0), "YYY", "Y Y", "XXX", Character.valueOf('X'), "plankWood", Character.valueOf('Y'), "stickWood"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.chisel), "XY", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingleV, 6, 5), "XXX", Character.valueOf('X'), new ItemStack(Blocks.stone, 1, 0)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.smoothStoneV, 2, 0), "X", "X", Character.valueOf('X'), new ItemStack(Blocks.stone, 1, 0)));
        for (LOTRBlockFallenLeaves fallenLeafBlock : LOTRBlockFallenLeaves.allFallenLeaves) {
            for (Block leafBlock : fallenLeafBlock.getLeafBlocks()) {
                if (!(leafBlock instanceof LOTRBlockLeavesBase)) continue;
                String[] leafNames = ((LOTRBlockLeavesBase)leafBlock).getAllLeafNames();
                for (int leafMeta = 0; leafMeta < leafNames.length; ++leafMeta) {
                    Object[] fallenBlockMeta = LOTRBlockFallenLeaves.fallenBlockMetaFromLeafBlockMeta(leafBlock, leafMeta);
                    if (fallenBlockMeta == null) continue;
                    Block fallenBlock = (Block)fallenBlockMeta[0];
                    int fallenMeta = (Integer)fallenBlockMeta[1];
                    if (fallenBlock == null) continue;
                    GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(fallenBlock, 6, fallenMeta), "XXX", Character.valueOf('X'), new ItemStack(leafBlock, 1, leafMeta)));
                }
            }
        }
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.planks3, 4, 1), new ItemStack(LOTRMod.wood8, 1, 1));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsRedwood, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks3, 1, 1));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.daub, 4), "XYX", "YXY", "XYX", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), Blocks.dirt));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.daub, 4), "XYX", "YXY", "XYX", Character.valueOf('X'), LOTRMod.driedReeds, Character.valueOf('Y'), Blocks.dirt));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.kebabBlock, 1, 0), "XXX", "XXX", "XXX", Character.valueOf('X'), LOTRMod.kebab);
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.kebab, 9), new ItemStack(LOTRMod.kebabBlock, 1, 0));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.blockOreStorage2, 1, 2), "XXX", "XXX", "XXX", Character.valueOf('X'), LOTRMod.gildedIron);
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.gildedIron, 9), new ItemStack(LOTRMod.blockOreStorage2, 1, 2));
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.planks3, 4, 2), new ItemStack(LOTRMod.wood8, 1, 2));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsPomegranate, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks3, 1, 2));
        LOTRVesselRecipes.addRecipes(new ItemStack(LOTRMod.mugPomegranateJuice), new Object[]{LOTRMod.pomegranate, LOTRMod.pomegranate});
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.item_frame), "XXX", "XYX", "XXX", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), LOTRMod.fur));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.mattockMithril), "XXX", "XY ", " Y ", Character.valueOf('X'), LOTRMod.mithril, Character.valueOf('Y'), "stickWood"));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(LOTRMod.gammon), Items.cooked_porkchop, "salt"));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.blockOreStorage2, 1, 3), "XXX", "XXX", "XXX", Character.valueOf('X'), LOTRMod.salt);
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.salt, 9), new ItemStack(LOTRMod.blockOreStorage2, 1, 3));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(LOTRMod.saltedFlesh), Items.rotten_flesh, "salt"));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.cornBread), "XXX", Character.valueOf('X'), LOTRMod.corn);
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.fenceGateSpruce, 1), "XYX", "XYX", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), new ItemStack(Blocks.planks, 1, 1)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.fenceGateBirch, 1), "XYX", "XYX", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), new ItemStack(Blocks.planks, 1, 2)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.fenceGateJungle, 1), "XYX", "XYX", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), new ItemStack(Blocks.planks, 1, 3)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.fenceGateAcacia, 1), "XYX", "XYX", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), new ItemStack(Blocks.planks, 1, 4)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.fenceGateDarkOak, 1), "XYX", "XYX", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), new ItemStack(Blocks.planks, 1, 5)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.fenceGateShirePine, 1), "XYX", "XYX", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), new ItemStack(LOTRMod.planks, 1, 0)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.fenceGateMirkOak, 1), "XYX", "XYX", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), new ItemStack(LOTRMod.planks, 1, 2)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.fenceGateCharred, 1), "XYX", "XYX", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), new ItemStack(LOTRMod.planks, 1, 3)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.fenceGateApple, 1), "XYX", "XYX", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), new ItemStack(LOTRMod.planks, 1, 4)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.fenceGatePear, 1), "XYX", "XYX", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), new ItemStack(LOTRMod.planks, 1, 5)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.fenceGateCherry, 1), "XYX", "XYX", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), new ItemStack(LOTRMod.planks, 1, 6)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.fenceGateMango, 1), "XYX", "XYX", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), new ItemStack(LOTRMod.planks, 1, 7)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.fenceGateLebethron, 1), "XYX", "XYX", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), new ItemStack(LOTRMod.planks, 1, 8)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.fenceGateBeech, 1), "XYX", "XYX", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), new ItemStack(LOTRMod.planks, 1, 9)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.fenceGateHolly, 1), "XYX", "XYX", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), new ItemStack(LOTRMod.planks, 1, 10)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.fenceGateBanana, 1), "XYX", "XYX", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), new ItemStack(LOTRMod.planks, 1, 11)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.fenceGateMaple, 1), "XYX", "XYX", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), new ItemStack(LOTRMod.planks, 1, 12)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.fenceGateLarch, 1), "XYX", "XYX", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), new ItemStack(LOTRMod.planks, 1, 13)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.fenceGateDatePalm, 1), "XYX", "XYX", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), new ItemStack(LOTRMod.planks, 1, 14)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.fenceGateMangrove, 1), "XYX", "XYX", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), new ItemStack(LOTRMod.planks, 1, 15)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.fenceGateChestnut, 1), "XYX", "XYX", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), new ItemStack(LOTRMod.planks2, 1, 0)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.fenceGateBaobab, 1), "XYX", "XYX", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), new ItemStack(LOTRMod.planks2, 1, 1)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.fenceGateCedar, 1), "XYX", "XYX", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), new ItemStack(LOTRMod.planks2, 1, 2)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.fenceGateFir, 1), "XYX", "XYX", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), new ItemStack(LOTRMod.planks2, 1, 3)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.fenceGatePine, 1), "XYX", "XYX", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), new ItemStack(LOTRMod.planks2, 1, 4)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.fenceGateLemon, 1), "XYX", "XYX", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), new ItemStack(LOTRMod.planks2, 1, 5)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.fenceGateOrange, 1), "XYX", "XYX", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), new ItemStack(LOTRMod.planks2, 1, 6)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.fenceGateLime, 1), "XYX", "XYX", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), new ItemStack(LOTRMod.planks2, 1, 7)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.fenceGateMahogany, 1), "XYX", "XYX", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), new ItemStack(LOTRMod.planks2, 1, 8)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.fenceGateWillow, 1), "XYX", "XYX", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), new ItemStack(LOTRMod.planks2, 1, 9)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.fenceGateCypress, 1), "XYX", "XYX", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), new ItemStack(LOTRMod.planks2, 1, 10)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.fenceGateOlive, 1), "XYX", "XYX", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), new ItemStack(LOTRMod.planks2, 1, 11)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.fenceGateAspen, 1), "XYX", "XYX", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), new ItemStack(LOTRMod.planks2, 1, 12)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.fenceGateGreenOak, 1), "XYX", "XYX", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), new ItemStack(LOTRMod.planks2, 1, 13)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.fenceGateLairelosse, 1), "XYX", "XYX", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), new ItemStack(LOTRMod.planks2, 1, 14)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.fenceGateAlmond, 1), "XYX", "XYX", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), new ItemStack(LOTRMod.planks2, 1, 15)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.fenceGateRotten, 1), "XYX", "XYX", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), new ItemStack(LOTRMod.planksRotten, 1, 0)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.fenceGatePlum, 1), "XYX", "XYX", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), new ItemStack(LOTRMod.planks3, 1, 0)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.fenceGateRedwood, 1), "XYX", "XYX", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), new ItemStack(LOTRMod.planks3, 1, 1)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.fenceGatePomegranate, 1), "XYX", "XYX", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), new ItemStack(LOTRMod.planks3, 1, 2)));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.slabUtumnoSingle2, 6, 0), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.utumnoBrick, 1, 6));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.slabUtumnoSingle2, 6, 1), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.utumnoBrick, 1, 7));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.slabUtumnoSingle2, 6, 2), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.utumnoBrick, 1, 8));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsUtumnoTileIce, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.utumnoBrick, 1, 6));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsUtumnoTileObsidian, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.utumnoBrick, 1, 7));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsUtumnoTileFire, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.utumnoBrick, 1, 8));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.wallUtumno, 6, 3), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.utumnoBrick, 1, 6));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.wallUtumno, 6, 4), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.utumnoBrick, 1, 7));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.wallUtumno, 6, 5), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.utumnoBrick, 1, 8));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.millstone), "XYX", "XZX", "XXX", Character.valueOf('X'), Blocks.cobblestone, Character.valueOf('Y'), "ingotIron", Character.valueOf('Z'), "stickWood"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.millstone), "XYX", "XZX", "XXX", Character.valueOf('X'), Blocks.cobblestone, Character.valueOf('Y'), "ingotBronze", Character.valueOf('Z'), "stickWood"));
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.topaz, 9), new ItemStack(LOTRMod.blockGem, 1, 0));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.blockGem, 1, 0), "XXX", "XXX", "XXX", Character.valueOf('X'), LOTRMod.topaz);
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.amethyst, 9), new ItemStack(LOTRMod.blockGem, 1, 1));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.blockGem, 1, 1), "XXX", "XXX", "XXX", Character.valueOf('X'), LOTRMod.amethyst);
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.sapphire, 9), new ItemStack(LOTRMod.blockGem, 1, 2));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.blockGem, 1, 2), "XXX", "XXX", "XXX", Character.valueOf('X'), LOTRMod.sapphire);
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.ruby, 9), new ItemStack(LOTRMod.blockGem, 1, 3));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.blockGem, 1, 3), "XXX", "XXX", "XXX", Character.valueOf('X'), LOTRMod.ruby);
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.amber, 9), new ItemStack(LOTRMod.blockGem, 1, 4));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.blockGem, 1, 4), "XXX", "XXX", "XXX", Character.valueOf('X'), LOTRMod.amber);
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.diamond, 9), new ItemStack(LOTRMod.blockGem, 1, 5));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.blockGem, 1, 5), "XXX", "XXX", "XXX", Character.valueOf('X'), LOTRMod.diamond);
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.pearl, 9), new ItemStack(LOTRMod.blockGem, 1, 6));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.blockGem, 1, 6), "XXX", "XXX", "XXX", Character.valueOf('X'), LOTRMod.pearl);
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.opal, 9), new ItemStack(LOTRMod.blockGem, 1, 7));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.blockGem, 1, 7), "XXX", "XXX", "XXX", Character.valueOf('X'), LOTRMod.opal);
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.mushroomPie), Items.egg, Blocks.red_mushroom, Blocks.brown_mushroom);
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.coral, 4), new ItemStack(LOTRMod.blockGem, 1, 8));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.blockGem, 1, 8), "XX", "XX", Character.valueOf('X'), LOTRMod.coral);
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.emerald, 9), new ItemStack(LOTRMod.blockGem, 1, 9));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.blockGem, 1, 9), "XXX", "XXX", "XXX", Character.valueOf('X'), LOTRMod.emerald);
        GameRegistry.addRecipe(new ItemStack(LOTRMod.glass, 4), "XX", "XX", Character.valueOf('X'), Blocks.glass);
        GameRegistry.addRecipe(new ItemStack(LOTRMod.glassPane, 16), "XXX", "XXX", Character.valueOf('X'), LOTRMod.glass);
        for (i2 = 0; i2 <= 15; ++i2) {
            GameRegistry.addRecipe(new ItemStack(LOTRMod.stainedGlass, 4, i2), "XX", "XX", Character.valueOf('X'), new ItemStack(Blocks.stained_glass, 1, i2));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.stainedGlass, 8, i2), "XXX", "XYX", "XXX", Character.valueOf('X'), LOTRMod.glass, Character.valueOf('Y'), dyeOreNames[BlockColored.func_150031_c(i2)]));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.stainedGlassPane, 16, i2), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.stainedGlass, 1, i2)));
        }
        GameRegistry.addRecipe(new ItemStack(LOTRMod.rope, 3), "X", "X", "X", Character.valueOf('X'), Items.string);
        GameRegistry.addRecipe(new ItemStack(LOTRMod.mithrilMail, 8), "XX", "XX", Character.valueOf('X'), LOTRMod.mithril);
        GameRegistry.addRecipe(new ItemStack(LOTRMod.mithril, 2), "XX", "XX", Character.valueOf('X'), LOTRMod.mithrilMail);
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.planks3, 4, 3), new ItemStack(LOTRMod.wood8, 1, 3));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsPalm, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks3, 1, 3));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.fenceGatePalm, 1), "XYX", "XYX", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), new ItemStack(LOTRMod.planks3, 1, 3)));
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.planks3, 4, 4), new ItemStack(LOTRMod.wood9, 1, 0));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsDragon, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks3, 1, 4));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.fenceGateDragon, 1), "XYX", "XYX", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), new ItemStack(LOTRMod.planks3, 1, 4)));
        for (i2 = 0; i2 <= 1; ++i2) {
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.woodBeam9, 3, i2), "X", "X", "X", Character.valueOf('X'), new ItemStack(LOTRMod.wood9, 1, i2)));
        }
        GameRegistry.addShapelessRecipe(new ItemStack(Items.dye, 4, 1), new ItemStack(LOTRMod.wood9, 1, 0), new ItemStack(LOTRMod.wood9, 1, 0));
        GameRegistry.addRecipe(new ItemStack(Blocks.packed_ice), "XX", "XX", Character.valueOf('X'), Blocks.ice);
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.woodPlate, 2), "XX", Character.valueOf('X'), "logWood"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.item_frame), "XXX", "XYX", "XXX", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), LOTRMod.gemsbokHide));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.doorSpruce), "XX", "XX", "XX", Character.valueOf('X'), new ItemStack(Blocks.planks, 1, 1)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.doorBirch), "XX", "XX", "XX", Character.valueOf('X'), new ItemStack(Blocks.planks, 1, 2)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.doorJungle), "XX", "XX", "XX", Character.valueOf('X'), new ItemStack(Blocks.planks, 1, 3)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.doorAcacia), "XX", "XX", "XX", Character.valueOf('X'), new ItemStack(Blocks.planks, 1, 4)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.doorDarkOak), "XX", "XX", "XX", Character.valueOf('X'), new ItemStack(Blocks.planks, 1, 5)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.doorShirePine), "XX", "XX", "XX", Character.valueOf('X'), new ItemStack(LOTRMod.planks, 1, 0)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.doorMirkOak), "XX", "XX", "XX", Character.valueOf('X'), new ItemStack(LOTRMod.planks, 1, 2)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.doorCharred), "XX", "XX", "XX", Character.valueOf('X'), new ItemStack(LOTRMod.planks, 1, 3)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.doorApple), "XX", "XX", "XX", Character.valueOf('X'), new ItemStack(LOTRMod.planks, 1, 4)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.doorPear), "XX", "XX", "XX", Character.valueOf('X'), new ItemStack(LOTRMod.planks, 1, 5)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.doorCherry), "XX", "XX", "XX", Character.valueOf('X'), new ItemStack(LOTRMod.planks, 1, 6)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.doorMango), "XX", "XX", "XX", Character.valueOf('X'), new ItemStack(LOTRMod.planks, 1, 7)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.doorLebethron), "XX", "XX", "XX", Character.valueOf('X'), new ItemStack(LOTRMod.planks, 1, 8)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.doorBeech), "XX", "XX", "XX", Character.valueOf('X'), new ItemStack(LOTRMod.planks, 1, 9)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.doorHolly), "XX", "XX", "XX", Character.valueOf('X'), new ItemStack(LOTRMod.planks, 1, 10)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.doorBanana), "XX", "XX", "XX", Character.valueOf('X'), new ItemStack(LOTRMod.planks, 1, 11)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.doorMaple), "XX", "XX", "XX", Character.valueOf('X'), new ItemStack(LOTRMod.planks, 1, 12)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.doorLarch), "XX", "XX", "XX", Character.valueOf('X'), new ItemStack(LOTRMod.planks, 1, 13)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.doorDatePalm), "XX", "XX", "XX", Character.valueOf('X'), new ItemStack(LOTRMod.planks, 1, 14)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.doorMangrove), "XX", "XX", "XX", Character.valueOf('X'), new ItemStack(LOTRMod.planks, 1, 15)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.doorChestnut), "XX", "XX", "XX", Character.valueOf('X'), new ItemStack(LOTRMod.planks2, 1, 0)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.doorBaobab), "XX", "XX", "XX", Character.valueOf('X'), new ItemStack(LOTRMod.planks2, 1, 1)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.doorCedar), "XX", "XX", "XX", Character.valueOf('X'), new ItemStack(LOTRMod.planks2, 1, 2)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.doorFir), "XX", "XX", "XX", Character.valueOf('X'), new ItemStack(LOTRMod.planks2, 1, 3)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.doorPine), "XX", "XX", "XX", Character.valueOf('X'), new ItemStack(LOTRMod.planks2, 1, 4)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.doorLemon), "XX", "XX", "XX", Character.valueOf('X'), new ItemStack(LOTRMod.planks2, 1, 5)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.doorOrange), "XX", "XX", "XX", Character.valueOf('X'), new ItemStack(LOTRMod.planks2, 1, 6)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.doorLime), "XX", "XX", "XX", Character.valueOf('X'), new ItemStack(LOTRMod.planks2, 1, 7)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.doorMahogany), "XX", "XX", "XX", Character.valueOf('X'), new ItemStack(LOTRMod.planks2, 1, 8)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.doorWillow), "XX", "XX", "XX", Character.valueOf('X'), new ItemStack(LOTRMod.planks2, 1, 9)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.doorCypress), "XX", "XX", "XX", Character.valueOf('X'), new ItemStack(LOTRMod.planks2, 1, 10)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.doorOlive), "XX", "XX", "XX", Character.valueOf('X'), new ItemStack(LOTRMod.planks2, 1, 11)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.doorAspen), "XX", "XX", "XX", Character.valueOf('X'), new ItemStack(LOTRMod.planks2, 1, 12)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.doorGreenOak), "XX", "XX", "XX", Character.valueOf('X'), new ItemStack(LOTRMod.planks2, 1, 13)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.doorLairelosse), "XX", "XX", "XX", Character.valueOf('X'), new ItemStack(LOTRMod.planks2, 1, 14)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.doorAlmond), "XX", "XX", "XX", Character.valueOf('X'), new ItemStack(LOTRMod.planks2, 1, 15)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.doorPlum), "XX", "XX", "XX", Character.valueOf('X'), new ItemStack(LOTRMod.planks3, 1, 0)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.doorRedwood), "XX", "XX", "XX", Character.valueOf('X'), new ItemStack(LOTRMod.planks3, 1, 1)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.doorPomegranate), "XX", "XX", "XX", Character.valueOf('X'), new ItemStack(LOTRMod.planks3, 1, 2)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.doorPalm), "XX", "XX", "XX", Character.valueOf('X'), new ItemStack(LOTRMod.planks3, 1, 3)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.doorDragon), "XX", "XX", "XX", Character.valueOf('X'), new ItemStack(LOTRMod.planks3, 1, 4)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.doorRotten), "XX", "XX", "XX", Character.valueOf('X'), new ItemStack(LOTRMod.planksRotten, 1, 0)));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Blocks.dirt, 1, 0), new ItemStack(Blocks.dirt, 1, 1), Items.wheat_seeds));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.boneBlock, 1, 0), "XX", "XX", Character.valueOf('X'), "bone"));
        GameRegistry.addShapelessRecipe(new ItemStack(Items.dye, 8, 15), LOTRMod.boneBlock);
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.slabBoneSingle, 6, 0), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.boneBlock, 1, 0)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.stairsBone, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.boneBlock, 1, 0)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.wallBone, 6, 0), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.boneBlock, 1, 0)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.redClay), "XX", "XX", Character.valueOf('X'), LOTRMod.redClayBall));
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.planks3, 4, 5), new ItemStack(LOTRMod.wood9, 1, 1));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.stairsKanuka, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks3, 1, 5));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.fenceGateKanuka, 1), "XYX", "XYX", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), new ItemStack(LOTRMod.planks3, 1, 5)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.doorKanuka), "XX", "XX", "XX", Character.valueOf('X'), new ItemStack(LOTRMod.planks3, 1, 5)));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.mud, 4, 1), "XY", "YX", Character.valueOf('X'), new ItemStack(LOTRMod.mud, 1, 0), Character.valueOf('Y'), Blocks.gravel);
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(LOTRMod.mud, 1, 0), new ItemStack(LOTRMod.mud, 1, 1), Items.wheat_seeds));
        GameRegistry.addRecipe(new ItemStack(LOTRMod.dirtPath, 2, 1), "XX", Character.valueOf('X'), LOTRMod.mud);
        GameRegistry.addRecipe(new ItemStack(LOTRMod.slabSingleDirt, 6, 4), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.dirtPath, 1, 1));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.saddle), "XXX", "Y Y", Character.valueOf('X'), LOTRMod.gemsbokHide, Character.valueOf('Y'), "ingotIron"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.brandingIron), "  X", " Y ", "X  ", Character.valueOf('X'), Items.iron_ingot, Character.valueOf('Y'), Items.leather));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.brandingIron), "  X", " Y ", "X  ", Character.valueOf('X'), Items.iron_ingot, Character.valueOf('Y'), LOTRMod.gemsbokHide));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(LOTRMod.brandingIron), new ItemStack(LOTRMod.brandingIron, 1, 32767), Items.iron_ingot));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.cobblebrick, 4, 0), "XX", "XX", Character.valueOf('X'), Blocks.cobblestone));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.trapdoorSpruce, 2), "XXX", "XXX", Character.valueOf('X'), new ItemStack(Blocks.planks, 1, 1)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.trapdoorBirch, 2), "XXX", "XXX", Character.valueOf('X'), new ItemStack(Blocks.planks, 1, 2)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.trapdoorJungle, 2), "XXX", "XXX", Character.valueOf('X'), new ItemStack(Blocks.planks, 1, 3)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.trapdoorAcacia, 2), "XXX", "XXX", Character.valueOf('X'), new ItemStack(Blocks.planks, 1, 4)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.trapdoorDarkOak, 2), "XXX", "XXX", Character.valueOf('X'), new ItemStack(Blocks.planks, 1, 5)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.trapdoorShirePine, 2), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks, 1, 0)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.trapdoorMirkOak, 2), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks, 1, 2)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.trapdoorCharred, 2), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks, 1, 3)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.trapdoorApple, 2), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks, 1, 4)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.trapdoorPear, 2), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks, 1, 5)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.trapdoorCherry, 2), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks, 1, 6)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.trapdoorMango, 2), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks, 1, 7)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.trapdoorLebethron, 2), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks, 1, 8)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.trapdoorBeech, 2), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks, 1, 9)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.trapdoorHolly, 2), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks, 1, 10)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.trapdoorBanana, 2), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks, 1, 11)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.trapdoorMaple, 2), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks, 1, 12)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.trapdoorLarch, 2), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks, 1, 13)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.trapdoorDatePalm, 2), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks, 1, 14)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.trapdoorMangrove, 2), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks, 1, 15)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.trapdoorChestnut, 2), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks2, 1, 0)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.trapdoorBaobab, 2), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks2, 1, 1)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.trapdoorCedar, 2), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks2, 1, 2)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.trapdoorFir, 2), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks2, 1, 3)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.trapdoorPine, 2), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks2, 1, 4)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.trapdoorLemon, 2), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks2, 1, 5)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.trapdoorOrange, 2), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks2, 1, 6)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.trapdoorLime, 2), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks2, 1, 7)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.trapdoorMahogany, 2), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks2, 1, 8)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.trapdoorWillow, 2), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks2, 1, 9)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.trapdoorCypress, 2), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks2, 1, 10)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.trapdoorOlive, 2), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks2, 1, 11)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.trapdoorAspen, 2), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks2, 1, 12)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.trapdoorGreenOak, 2), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks2, 1, 13)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.trapdoorLairelosse, 2), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks2, 1, 14)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.trapdoorAlmond, 2), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks2, 1, 15)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.trapdoorPlum, 2), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks3, 1, 0)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.trapdoorRedwood, 2), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks3, 1, 1)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.trapdoorPomegranate, 2), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks3, 1, 2)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.trapdoorPalm, 2), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks3, 1, 3)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.trapdoorDragon, 2), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks3, 1, 4)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.trapdoorKanuka, 2), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks3, 1, 5)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(LOTRMod.trapdoorRotten, 2), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planksRotten, 1, 0)));
        GameRegistry.addShapelessRecipe(new ItemStack(Items.dye, 1, 5), LOTRMod.lavender);
        GameRegistry.addShapelessRecipe(new ItemStack(LOTRMod.ironNugget, 9), Items.iron_ingot);
        GameRegistry.addRecipe(new ItemStack(Items.iron_ingot), "XXX", "XXX", "XXX", Character.valueOf('X'), LOTRMod.ironNugget);
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.chainmail_helmet), "XXX", "Y Y", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "nuggetIron"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.chainmail_chestplate), "X X", "YYY", "XXX", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "nuggetIron"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.chainmail_leggings), "XXX", "Y Y", "X X", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "nuggetIron"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.chainmail_boots), "Y Y", "X X", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "nuggetIron"));
    }

    private static void createPoisonedDaggerRecipes() {
        GameRegistry.addRecipe(new LOTRRecipePoisonWeapon(LOTRMod.daggerOrc, LOTRMod.daggerOrcPoisoned));
        GameRegistry.addRecipe(new LOTRRecipePoisonWeapon(LOTRMod.daggerUruk, LOTRMod.daggerUrukPoisoned));
        GameRegistry.addRecipe(new LOTRRecipePoisonWeapon(LOTRMod.daggerBronze, LOTRMod.daggerBronzePoisoned));
        GameRegistry.addRecipe(new LOTRRecipePoisonWeapon(LOTRMod.daggerIron, LOTRMod.daggerIronPoisoned));
        GameRegistry.addRecipe(new LOTRRecipePoisonWeapon(LOTRMod.daggerMithril, LOTRMod.daggerMithrilPoisoned));
        GameRegistry.addRecipe(new LOTRRecipePoisonWeapon(LOTRMod.daggerGondor, LOTRMod.daggerGondorPoisoned));
        GameRegistry.addRecipe(new LOTRRecipePoisonWeapon(LOTRMod.daggerElven, LOTRMod.daggerElvenPoisoned));
        GameRegistry.addRecipe(new LOTRRecipePoisonWeapon(LOTRMod.daggerDwarven, LOTRMod.daggerDwarvenPoisoned));
        GameRegistry.addRecipe(new LOTRRecipePoisonWeapon(LOTRMod.daggerRohan, LOTRMod.daggerRohanPoisoned));
        GameRegistry.addRecipe(new LOTRRecipePoisonWeapon(LOTRMod.daggerWoodElven, LOTRMod.daggerWoodElvenPoisoned));
        GameRegistry.addRecipe(new LOTRRecipePoisonWeapon(LOTRMod.daggerAngmar, LOTRMod.daggerAngmarPoisoned));
        GameRegistry.addRecipe(new LOTRRecipePoisonWeapon(LOTRMod.daggerHighElven, LOTRMod.daggerHighElvenPoisoned));
        GameRegistry.addRecipe(new LOTRRecipePoisonWeapon(LOTRMod.daggerNearHarad, LOTRMod.daggerNearHaradPoisoned));
        GameRegistry.addRecipe(new LOTRRecipePoisonWeapon(LOTRMod.daggerBlueDwarven, LOTRMod.daggerBlueDwarvenPoisoned));
        GameRegistry.addRecipe(new LOTRRecipePoisonWeapon(LOTRMod.daggerDolGuldur, LOTRMod.daggerDolGuldurPoisoned));
        GameRegistry.addRecipe(new LOTRRecipePoisonWeapon(LOTRMod.daggerBlackUruk, LOTRMod.daggerBlackUrukPoisoned));
        GameRegistry.addRecipe(new LOTRRecipePoisonWeapon(LOTRMod.daggerUtumno, LOTRMod.daggerUtumnoPoisoned));
        GameRegistry.addRecipe(new LOTRRecipePoisonWeapon(LOTRMod.daggerHalfTroll, LOTRMod.daggerHalfTrollPoisoned));
        GameRegistry.addRecipe(new LOTRRecipePoisonWeapon(LOTRMod.daggerMoredain, LOTRMod.daggerMoredainPoisoned));
        GameRegistry.addRecipe(new LOTRRecipePoisonWeapon(LOTRMod.daggerTauredain, LOTRMod.daggerTauredainPoisoned));
        GameRegistry.addRecipe(new LOTRRecipePoisonWeapon(LOTRMod.daggerBarrow, LOTRMod.daggerBarrowPoisoned));
        GameRegistry.addRecipe(new LOTRRecipePoisonWeapon(LOTRMod.daggerDolAmroth, LOTRMod.daggerDolAmrothPoisoned));
        GameRegistry.addRecipe(new LOTRRecipePoisonWeapon(LOTRMod.daggerDale, LOTRMod.daggerDalePoisoned));
        GameRegistry.addRecipe(new LOTRRecipePoisonWeapon(LOTRMod.daggerGundabadUruk, LOTRMod.daggerGundabadUrukPoisoned));
        GameRegistry.addRecipe(new LOTRRecipePoisonWeapon(LOTRMod.daggerDorwinionElf, LOTRMod.daggerDorwinionElfPoisoned));
        GameRegistry.addRecipe(new LOTRRecipePoisonWeapon(LOTRMod.daggerRhun, LOTRMod.daggerRhunPoisoned));
        GameRegistry.addRecipe(new LOTRRecipePoisonWeapon(LOTRMod.daggerRivendell, LOTRMod.daggerRivendellPoisoned));
        GameRegistry.addRecipe(new LOTRRecipePoisonWeapon(LOTRMod.daggerArnor, LOTRMod.daggerArnorPoisoned));
        GameRegistry.addRecipe(new LOTRRecipePoisonWeapon(LOTRMod.daggerCorsair, LOTRMod.daggerCorsairPoisoned));
        GameRegistry.addRecipe(new LOTRRecipePoisonWeapon(LOTRMod.daggerHarad, LOTRMod.daggerHaradPoisoned));
        GameRegistry.addRecipe(new LOTRRecipePoisonWeapon(LOTRMod.daggerBlackNumenorean, LOTRMod.daggerBlackNumenoreanPoisoned));
    }

    private static void createPoisonedArrowRecipes() {
        ArrayList<List> recipeLists = new ArrayList<>(Arrays.asList(commonOrcRecipes));
        recipeLists.addAll(Arrays.asList(commonNearHaradRecipes));
        for (Object obj : recipeLists) {
            List recipes = (List)obj;
            recipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.arrowPoisoned, 4), " X ", "XYX", " X ", Character.valueOf('X'), Items.arrow, Character.valueOf('Y'), "poison"));
            recipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.crossbowBoltPoisoned, 4), " X ", "XYX", " X ", Character.valueOf('X'), LOTRMod.crossbowBolt, Character.valueOf('Y'), "poison"));
        }
    }

    private static void createWoodenSlabRecipes() {
        woodenSlabRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.woodSlabSingle, 6, 0), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks, 1, 0)));
        woodenSlabRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.woodSlabSingle, 6, 2), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks, 1, 2)));
        woodenSlabRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.woodSlabSingle, 6, 3), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks, 1, 3)));
        woodenSlabRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.woodSlabSingle, 6, 4), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks, 1, 4)));
        woodenSlabRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.woodSlabSingle, 6, 5), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks, 1, 5)));
        woodenSlabRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.woodSlabSingle, 6, 6), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks, 1, 6)));
        woodenSlabRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.woodSlabSingle, 6, 7), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks, 1, 7)));
        woodenSlabRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.woodSlabSingle2, 6, 0), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks, 1, 8)));
        woodenSlabRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.woodSlabSingle2, 6, 1), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks, 1, 9)));
        woodenSlabRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.woodSlabSingle2, 6, 2), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks, 1, 10)));
        woodenSlabRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.woodSlabSingle2, 6, 3), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks, 1, 11)));
        woodenSlabRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.woodSlabSingle2, 6, 4), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks, 1, 12)));
        woodenSlabRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.woodSlabSingle2, 6, 5), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks, 1, 13)));
        woodenSlabRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.woodSlabSingle2, 6, 6), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks, 1, 14)));
        woodenSlabRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.woodSlabSingle2, 6, 7), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks, 1, 15)));
        woodenSlabRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.woodSlabSingle3, 6, 0), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks2, 1, 0)));
        woodenSlabRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.woodSlabSingle3, 6, 1), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks2, 1, 1)));
        woodenSlabRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.woodSlabSingle3, 6, 2), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks2, 1, 2)));
        woodenSlabRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.woodSlabSingle3, 6, 3), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks2, 1, 3)));
        woodenSlabRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.woodSlabSingle3, 6, 4), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks2, 1, 4)));
        woodenSlabRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.woodSlabSingle3, 6, 5), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks2, 1, 5)));
        woodenSlabRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.woodSlabSingle3, 6, 6), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks2, 1, 6)));
        woodenSlabRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.woodSlabSingle3, 6, 7), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks2, 1, 7)));
        woodenSlabRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.woodSlabSingle4, 6, 0), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks2, 1, 8)));
        woodenSlabRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.woodSlabSingle4, 6, 1), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks2, 1, 9)));
        woodenSlabRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.woodSlabSingle4, 6, 2), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks2, 1, 10)));
        woodenSlabRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.woodSlabSingle4, 6, 3), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks2, 1, 11)));
        woodenSlabRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.woodSlabSingle4, 6, 4), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks2, 1, 12)));
        woodenSlabRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.woodSlabSingle4, 6, 5), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks2, 1, 13)));
        woodenSlabRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.woodSlabSingle4, 6, 6), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks2, 1, 14)));
        woodenSlabRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.woodSlabSingle4, 6, 7), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks2, 1, 15)));
        woodenSlabRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.woodSlabSingle5, 6, 0), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks3, 1, 0)));
        woodenSlabRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.woodSlabSingle5, 6, 1), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks3, 1, 1)));
        woodenSlabRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.woodSlabSingle5, 6, 2), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks3, 1, 2)));
        woodenSlabRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.woodSlabSingle5, 6, 3), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks3, 1, 3)));
        woodenSlabRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.woodSlabSingle5, 6, 4), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks3, 1, 4)));
        woodenSlabRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.woodSlabSingle5, 6, 5), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks3, 1, 5)));
        woodenSlabRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.rottenSlabSingle, 6, 0), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planksRotten, 1, 0)));
    }

    private static void modifyStandardRecipes() {
        List recipeList = CraftingManager.getInstance().getRecipeList();
        LOTRRecipes.removeRecipesItem(recipeList, Item.getItemFromBlock(Blocks.fence));
        for (int i = 0; i <= 5; ++i) {
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Blocks.fence, 3, i), "XYX", "XYX", Character.valueOf('X'), new ItemStack(Blocks.planks, 1, i), Character.valueOf('Y'), "stickWood"));
        }
        LOTRRecipes.removeRecipesItem(recipeList, Item.getItemFromBlock(Blocks.fence_gate));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Blocks.fence_gate, 1), "XYX", "XYX", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), new ItemStack(Blocks.planks, 1, 0)));
        LOTRRecipes.removeRecipesItem(recipeList, Items.wooden_door);
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.wooden_door), "XX", "XX", "XX", Character.valueOf('X'), new ItemStack(Blocks.planks, 1, 0)));
        LOTRRecipes.removeRecipesItem(recipeList, Item.getItemFromBlock(Blocks.trapdoor));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Blocks.trapdoor, 2), "XXX", "XXX", Character.valueOf('X'), new ItemStack(Blocks.planks, 1, 0)));
        if (LOTRConfig.removeGoldenAppleRecipes) {
            LOTRRecipes.removeRecipesItem(recipeList, Items.golden_apple);
        }
        if (LOTRConfig.removeDiamondArmorRecipes) {
            LOTRRecipes.removeRecipesItem(recipeList, Items.diamond_helmet);
            LOTRRecipes.removeRecipesItem(recipeList, Items.diamond_chestplate);
            LOTRRecipes.removeRecipesItem(recipeList, Items.diamond_leggings);
            LOTRRecipes.removeRecipesItem(recipeList, Items.diamond_boots);
        }
        LOTRRecipes.removeRecipesItemStack(recipeList, new ItemStack(Blocks.stone_slab, 1, 0));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Blocks.stone_slab, 6, 0), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.smoothStoneV, 1, 0)));
        LOTRRecipes.removeRecipesItemStack(recipeList, new ItemStack(Blocks.stone_slab, 1, 5));
        GameRegistry.addRecipe(new ItemStack(Blocks.stone_slab, 6, 5), "XXX", Character.valueOf('X'), new ItemStack(Blocks.stonebrick, 1, 0));
        LOTRRecipes.removeRecipesItem(recipeList, Item.getItemFromBlock(Blocks.stone_brick_stairs));
        GameRegistry.addRecipe(new ItemStack(Blocks.stone_brick_stairs, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(Blocks.stonebrick, 1, 0));
        LOTRRecipes.removeRecipesItem(recipeList, Item.getItemFromBlock(Blocks.anvil));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Blocks.anvil), "XXX", " Y ", "XXX", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "blockIron"));
        LOTRRecipes.removeRecipesClass(recipeList, RecipesArmorDyes.class);
        GameRegistry.addRecipe(new LOTRRecipesArmorDyes());
    }

    private static void removeRecipesItem(List recipeList, Item outputItem) {
        ArrayList<IRecipe> recipesToRemove = new ArrayList<>();
        for (Object obj : recipeList) {
            IRecipe recipe = (IRecipe)obj;
            ItemStack output = recipe.getRecipeOutput();
            if (output == null || output.getItem() != outputItem) continue;
            recipesToRemove.add(recipe);
        }
        recipeList.removeAll(recipesToRemove);
    }

    private static void removeRecipesItemStack(List recipeList, ItemStack outputItemStack) {
        ArrayList<IRecipe> recipesToRemove = new ArrayList<>();
        for (Object obj : recipeList) {
            IRecipe recipe = (IRecipe)obj;
            ItemStack output = recipe.getRecipeOutput();
            if (output == null || !output.isItemEqual(outputItemStack)) continue;
            recipesToRemove.add(recipe);
        }
        recipeList.removeAll(recipesToRemove);
    }

    private static void removeRecipesClass(List recipeList, Class<? extends IRecipe> recipeClass) {
        ArrayList<IRecipe> recipesToRemove = new ArrayList<>();
        for (Object obj : recipeList) {
            IRecipe recipe = (IRecipe)obj;
            if (!recipeClass.isAssignableFrom(recipe.getClass())) continue;
            recipesToRemove.add(recipe);
        }
        recipeList.removeAll(recipesToRemove);
    }

    private static void createSmeltingRecipes() {
        for (Object obj : Block.blockRegistry) {
            Block block = (Block)obj;
            if (!(block instanceof LOTRBlockWoodBase)) continue;
            GameRegistry.addSmelting(block, new ItemStack(Items.coal, 1, 1), 0.15f);
        }
        GameRegistry.addSmelting(Blocks.stone, new ItemStack(LOTRMod.scorchedStone), 0.1f);
        GameRegistry.addSmelting(LOTRMod.rock, new ItemStack(LOTRMod.scorchedStone), 0.1f);
        GameRegistry.addSmelting(LOTRMod.whiteSand, new ItemStack(Blocks.glass), 0.1f);
        GameRegistry.addSmelting(LOTRMod.oreCopper, new ItemStack(LOTRMod.copper), 0.35f);
        GameRegistry.addSmelting(LOTRMod.oreTin, new ItemStack(LOTRMod.tin), 0.35f);
        GameRegistry.addSmelting(LOTRMod.oreSilver, new ItemStack(LOTRMod.silver), 0.8f);
        GameRegistry.addSmelting(LOTRMod.oreNaurite, new ItemStack(LOTRMod.nauriteGem), 1.0f);
        GameRegistry.addSmelting(LOTRMod.oreQuendite, new ItemStack(LOTRMod.quenditeCrystal), 1.0f);
        GameRegistry.addSmelting(LOTRMod.oreGlowstone, new ItemStack(Items.glowstone_dust), 1.0f);
        GameRegistry.addSmelting(LOTRMod.oreGulduril, new ItemStack(LOTRMod.guldurilCrystal), 1.0f);
        GameRegistry.addSmelting(LOTRMod.oreSulfur, new ItemStack(LOTRMod.sulfur), 1.0f);
        GameRegistry.addSmelting(LOTRMod.oreSaltpeter, new ItemStack(LOTRMod.saltpeter), 1.0f);
        GameRegistry.addSmelting(LOTRMod.oreSalt, new ItemStack(LOTRMod.salt), 1.0f);
        GameRegistry.addSmelting(LOTRMod.clayMug, new ItemStack(LOTRMod.ceramicMug), 0.3f);
        GameRegistry.addSmelting(LOTRMod.clayPlate, new ItemStack(LOTRMod.ceramicPlate), 0.3f);
        GameRegistry.addSmelting(LOTRMod.ceramicPlate, new ItemStack(LOTRMod.plate), 0.3f);
        GameRegistry.addSmelting(LOTRMod.redClayBall, new ItemStack(Items.brick), 0.3f);
        GameRegistry.addSmelting(LOTRMod.redClay, new ItemStack(Blocks.hardened_clay), 0.35f);
        GameRegistry.addSmelting(LOTRMod.rabbitRaw, new ItemStack(LOTRMod.rabbitCooked), 0.35f);
        GameRegistry.addSmelting(LOTRMod.lionRaw, new ItemStack(LOTRMod.lionCooked), 0.35f);
        GameRegistry.addSmelting(LOTRMod.zebraRaw, new ItemStack(LOTRMod.zebraCooked), 0.35f);
        GameRegistry.addSmelting(LOTRMod.rhinoRaw, new ItemStack(LOTRMod.rhinoCooked), 0.35f);
        GameRegistry.addSmelting(LOTRMod.muttonRaw, new ItemStack(LOTRMod.muttonCooked), 0.35f);
        GameRegistry.addSmelting(LOTRMod.deerRaw, new ItemStack(LOTRMod.deerCooked), 0.35f);
        GameRegistry.addSmelting(LOTRMod.camelRaw, new ItemStack(LOTRMod.camelCooked), 0.35f);
        GameRegistry.addSmelting(new ItemStack(LOTRMod.reeds, 1, 0), new ItemStack(LOTRMod.driedReeds), 0.25f);
        GameRegistry.addSmelting(LOTRMod.pipeweedLeaf, new ItemStack(LOTRMod.pipeweed), 0.25f);
        GameRegistry.addSmelting(LOTRMod.chestnut, new ItemStack(LOTRMod.chestnutRoast), 0.3f);
        GameRegistry.addSmelting(LOTRMod.corn, new ItemStack(LOTRMod.cornCooked), 0.3f);
        GameRegistry.addSmelting(LOTRMod.turnip, new ItemStack(LOTRMod.turnipCooked), 0.3f);
        GameRegistry.addSmelting(LOTRMod.yam, new ItemStack(LOTRMod.yamRoast), 0.3f);
        GameRegistry.addSmelting(LOTRMod.grapeRed, new ItemStack(LOTRMod.raisins), 0.3f);
        GameRegistry.addSmelting(LOTRMod.grapeWhite, new ItemStack(LOTRMod.raisins), 0.3f);
        LOTRRecipes.addSmeltingXPForItem(LOTRMod.bronze, 0.7f);
        LOTRRecipes.addSmeltingXPForItem(LOTRMod.mithril, 1.0f);
        LOTRRecipes.addSmeltingXPForItem(LOTRMod.orcSteel, 0.7f);
        LOTRRecipes.addSmeltingXPForItem(LOTRMod.dwarfSteel, 0.7f);
        LOTRRecipes.addSmeltingXPForItem(LOTRMod.galvorn, 0.8f);
        LOTRRecipes.addSmeltingXPForItem(LOTRMod.urukSteel, 0.7f);
        LOTRRecipes.addSmeltingXPForItem(LOTRMod.morgulSteel, 0.8f);
        LOTRRecipes.addSmeltingXPForItem(LOTRMod.blueDwarfSteel, 0.7f);
        LOTRRecipes.addSmeltingXPForItem(LOTRMod.blackUrukSteel, 0.7f);
        LOTRRecipes.addSmeltingXPForItem(LOTRMod.elfSteel, 0.7f);
        LOTRRecipes.addSmeltingXPForItem(LOTRMod.ithildin, 0.8f);
        LOTRRecipes.addSmeltingXPForItem(LOTRMod.gildedIron, 0.7f);
    }

    private static void addSmeltingXPForItem(Item item, float xp) {
        try {
            Field field = FurnaceRecipes.class.getDeclaredFields()[2];
            field.setAccessible(true);
            HashMap map = (HashMap)field.get(FurnaceRecipes.smelting());
            map.put(new ItemStack(item, 1, 32767), Float.valueOf(xp));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addRecipeTo(List[] recipeLists, IRecipe recipe) {
        for (List list : recipeLists) {
            list.add(recipe);
        }
    }

    private static void createCommonOrcRecipes() {
        LOTRRecipes.addRecipeTo(commonOrcRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.orcBedItem), "XXX", "YYY", Character.valueOf('X'), Blocks.wool, Character.valueOf('Y'), "plankWood"));
        LOTRRecipes.addRecipeTo(commonOrcRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.maggotyBread), "XXX", Character.valueOf('X'), Items.wheat));
        for (int i = 0; i <= 2; ++i) {
            LOTRRecipes.addRecipeTo(commonOrcRecipes, new ShapelessOreRecipe(new ItemStack(LOTRMod.orcBomb, 1, i + 8), new ItemStack(LOTRMod.orcBomb, 1, i), Items.lava_bucket));
        }
        LOTRRecipes.addRecipeTo(commonOrcRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.orcSkullStaff), "X", "Y", "Y", Character.valueOf('X'), Items.skull, Character.valueOf('Y'), "stickWood"));
        LOTRRecipes.addRecipeTo(commonOrcRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.orcChain, 8), "X", "X", "X", Character.valueOf('X'), LOTRMod.orcSteel));
        LOTRRecipes.addRecipeTo(commonOrcRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.orcPlating, 8, 0), "XX", "XX", Character.valueOf('X'), LOTRMod.orcSteel));
        LOTRRecipes.addRecipeTo(commonOrcRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.orcPlating, 8, 1), "XXX", "XYX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.orcPlating, 1, 0), Character.valueOf('Y'), Items.water_bucket));
    }

    private static void createCommonMorgulRecipes() {
        LOTRRecipes.addRecipeTo(commonMorgulRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.morgulBlade), "X", "X", "Y", Character.valueOf('X'), LOTRMod.morgulSteel, Character.valueOf('Y'), "stickWood"));
        LOTRRecipes.addRecipeTo(commonMorgulRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.helmetMorgul), "XXX", "X X", Character.valueOf('X'), LOTRMod.morgulSteel));
        LOTRRecipes.addRecipeTo(commonMorgulRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.bodyMorgul), "X X", "XXX", "XXX", Character.valueOf('X'), LOTRMod.morgulSteel));
        LOTRRecipes.addRecipeTo(commonMorgulRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.legsMorgul), "XXX", "X X", "X X", Character.valueOf('X'), LOTRMod.morgulSteel));
        LOTRRecipes.addRecipeTo(commonMorgulRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.bootsMorgul), "X X", "X X", Character.valueOf('X'), LOTRMod.morgulSteel));
        LOTRRecipes.addRecipeTo(commonMorgulRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.morgulTorch, 4), "X", "Y", Character.valueOf('X'), LOTRMod.guldurilCrystal, Character.valueOf('Y'), "stickWood"));
        LOTRRecipes.addRecipeTo(commonMorgulRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.horseArmorMorgul), "X  ", "XYX", "XXX", Character.valueOf('X'), LOTRMod.morgulSteel, Character.valueOf('Y'), Items.leather));
        LOTRRecipes.addRecipeTo(commonMorgulRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.orcSteelBars, 16), "XXX", "XXX", Character.valueOf('X'), LOTRMod.orcSteel));
        LOTRRecipes.addRecipeTo(commonMorgulRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.orcBow), " XY", "X Y", " XY", Character.valueOf('X'), LOTRMod.orcSteel, Character.valueOf('Y'), Items.string));
        LOTRRecipes.addRecipeTo(commonMorgulRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.orcBomb, 4), "XYX", "YXY", "XYX", Character.valueOf('X'), Items.gunpowder, Character.valueOf('Y'), LOTRMod.orcSteel));
        LOTRRecipes.addRecipeTo(commonMorgulRecipes, new ShapelessOreRecipe(new ItemStack(LOTRMod.orcBomb, 1, 1), new ItemStack(LOTRMod.orcBomb, 1, 0), Items.gunpowder, LOTRMod.orcSteel));
        LOTRRecipes.addRecipeTo(commonMorgulRecipes, new ShapelessOreRecipe(new ItemStack(LOTRMod.orcBomb, 1, 2), new ItemStack(LOTRMod.orcBomb, 1, 1), Items.gunpowder, LOTRMod.orcSteel));
        LOTRRecipes.addRecipeTo(commonMorgulRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.chandelier, 2, 12), " X ", "YZY", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), LOTRMod.morgulTorch, Character.valueOf('Z'), LOTRMod.morgulSteel));
        LOTRRecipes.addRecipeTo(commonMorgulRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.mechanism), " X ", "YZY", " X ", Character.valueOf('X'), "ingotCopper", Character.valueOf('Y'), Items.flint, Character.valueOf('Z'), LOTRMod.orcSteel));
    }

    private static void createCommonElfRecipes() {
        LOTRRecipes.addRecipeTo(commonElfRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.helmetGalvorn), "XXX", "X X", Character.valueOf('X'), LOTRMod.galvorn));
        LOTRRecipes.addRecipeTo(commonElfRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.bodyGalvorn), "X X", "XXX", "XXX", Character.valueOf('X'), LOTRMod.galvorn));
        LOTRRecipes.addRecipeTo(commonElfRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.legsGalvorn), "XXX", "X X", "X X", Character.valueOf('X'), LOTRMod.galvorn));
        LOTRRecipes.addRecipeTo(commonElfRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.bootsGalvorn), "X X", "X X", Character.valueOf('X'), LOTRMod.galvorn));
        LOTRRecipes.addRecipeTo(commonElfRecipes, new LOTRRecipePoisonWeapon(LOTRMod.chisel, LOTRMod.chiselIthildin, new ItemStack(LOTRMod.ithildin, 1, 0)));
    }

    private static void createCommonHighElfRecipes() {
        LOTRRecipes.addRecipeTo(commonHighElfRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.highElvenTorch, 4), "X", "Y", Character.valueOf('X'), LOTRMod.quenditeCrystal, Character.valueOf('Y'), "stickWood"));
        LOTRRecipes.addRecipeTo(commonHighElfRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.chandelier, 2, 10), " X ", "YZY", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), LOTRMod.highElvenTorch, Character.valueOf('Z'), LOTRMod.elfSteel));
        LOTRRecipes.addRecipeTo(commonHighElfRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.highElvenBedItem), "XXX", "YYY", Character.valueOf('X'), Blocks.wool, Character.valueOf('Y'), "plankWood"));
        LOTRRecipes.addRecipeTo(commonHighElfRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.brick3, 4, 2), "XX", "XX", Character.valueOf('X'), Blocks.stone));
        LOTRRecipes.addRecipeTo(commonHighElfRecipes, new ShapelessOreRecipe(new ItemStack(LOTRMod.brick3, 1, 3), new ItemStack(LOTRMod.brick3, 1, 2), "vine"));
        LOTRRecipes.addRecipeTo(commonHighElfRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle5, 6, 5), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick3, 4, 2)));
        LOTRRecipes.addRecipeTo(commonHighElfRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle5, 6, 6), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick3, 4, 3)));
        LOTRRecipes.addRecipeTo(commonHighElfRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle5, 6, 7), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick3, 4, 4)));
        LOTRRecipes.addRecipeTo(commonHighElfRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.stairsHighElvenBrick, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick3, 1, 2)));
        LOTRRecipes.addRecipeTo(commonHighElfRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.stairsHighElvenBrickMossy, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick3, 1, 3)));
        LOTRRecipes.addRecipeTo(commonHighElfRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.stairsHighElvenBrickCracked, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick3, 1, 4)));
        LOTRRecipes.addRecipeTo(commonHighElfRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.wall2, 6, 11), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick3, 1, 2)));
        LOTRRecipes.addRecipeTo(commonHighElfRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.wall2, 6, 12), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick3, 1, 3)));
        LOTRRecipes.addRecipeTo(commonHighElfRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.wall2, 6, 13), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick3, 1, 4)));
        LOTRRecipes.addRecipeTo(commonHighElfRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.pillar, 3, 10), "X", "X", "X", Character.valueOf('X'), Blocks.stone));
        LOTRRecipes.addRecipeTo(commonHighElfRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle6, 6, 0), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.pillar, 1, 10)));
        LOTRRecipes.addRecipeTo(commonHighElfRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle6, 6, 1), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.pillar, 1, 11)));
        LOTRRecipes.addRecipeTo(commonHighElfRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.brick2, 1, 13), "XX", "XX", Character.valueOf('X'), new ItemStack(LOTRMod.brick3, 1, 2)));
        LOTRRecipes.addRecipeTo(commonHighElfRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.elvenForge), "XXX", "X X", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick3, 1, 2)));
        LOTRRecipes.addRecipeTo(commonHighElfRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.highElfBars, 16), "XXX", "XXX", Character.valueOf('X'), LOTRMod.elfSteel));
        LOTRRecipes.addRecipeTo(commonHighElfRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.highElfWoodBars, 8), "XXX", "XXX", Character.valueOf('X'), "stickWood"));
        LOTRRecipes.addRecipeTo(commonHighElfRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.brick4, 1, 8), " X ", "XYX", " X ", Character.valueOf('X'), "nuggetSilver", Character.valueOf('Y'), new ItemStack(LOTRMod.brick3, 1, 2)));
        LOTRRecipes.addRecipeTo(commonHighElfRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.brick4, 1, 11), " X ", "XYX", " X ", Character.valueOf('X'), "nuggetGold", Character.valueOf('Y'), new ItemStack(LOTRMod.brick3, 1, 2)));
        LOTRRecipes.addRecipeTo(commonHighElfRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.gateHighElven, 4), "ZYZ", "YXY", "ZYZ", Character.valueOf('X'), LOTRMod.gateGear, Character.valueOf('Y'), "plankWood", Character.valueOf('Z'), LOTRMod.elfSteel));
    }

    private static void createCommonDwarfRecipes() {
        LOTRRecipes.addRecipeTo(commonDwarfRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.brick, 4, 6), "XX", "XX", Character.valueOf('X'), Blocks.stone));
        LOTRRecipes.addRecipeTo(commonDwarfRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.stairsDwarvenBrick, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick, 1, 6)));
        LOTRRecipes.addRecipeTo(commonDwarfRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle, 6, 7), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick, 1, 6)));
        LOTRRecipes.addRecipeTo(commonDwarfRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.wall, 6, 7), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick, 1, 6)));
        LOTRRecipes.addRecipeTo(commonDwarfRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.pillar, 3, 0), "X", "X", "X", Character.valueOf('X'), Blocks.stone));
        LOTRRecipes.addRecipeTo(commonDwarfRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.dwarvenForge), "XXX", "X X", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick, 1, 6)));
        LOTRRecipes.addRecipeTo(commonDwarfRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle2, 6, 0), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.pillar, 1, 0)));
        LOTRRecipes.addRecipeTo(commonDwarfRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.dwarvenDoor), "XX", "XX", "XX", Character.valueOf('X'), Blocks.stone));
        LOTRRecipes.addRecipeTo(commonDwarfRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.dwarvenDoorIthildin), "XX", "XY", "XX", Character.valueOf('X'), Blocks.stone, Character.valueOf('Y'), new ItemStack(LOTRMod.ithildin, 1, 0)));
        LOTRRecipes.addRecipeTo(commonDwarfRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.dwarvenBedItem), "XXX", "YYY", Character.valueOf('X'), Blocks.wool, Character.valueOf('Y'), "plankWood"));
        LOTRRecipes.addRecipeTo(commonDwarfRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.brick, 1, 8), " X ", "XYX", " X ", Character.valueOf('X'), "nuggetSilver", Character.valueOf('Y'), new ItemStack(LOTRMod.brick, 1, 6)));
        LOTRRecipes.addRecipeTo(commonDwarfRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.brick, 1, 9), " X ", "XYX", " X ", Character.valueOf('X'), "nuggetGold", Character.valueOf('Y'), new ItemStack(LOTRMod.brick, 1, 6)));
        LOTRRecipes.addRecipeTo(commonDwarfRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.brick, 1, 10), " X ", "XYX", " X ", Character.valueOf('X'), LOTRMod.mithrilNugget, Character.valueOf('Y'), new ItemStack(LOTRMod.brick, 1, 6)));
        LOTRRecipes.addRecipeTo(commonDwarfRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.brick2, 1, 12), "XX", "XX", Character.valueOf('X'), new ItemStack(LOTRMod.brick, 1, 6)));
        LOTRRecipes.addRecipeTo(commonDwarfRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.brick3, 1, 12), " X ", "XYX", " X ", Character.valueOf('X'), Items.glowstone_dust, Character.valueOf('Y'), new ItemStack(LOTRMod.brick, 1, 6)));
        LOTRRecipes.addRecipeTo(commonDwarfRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.stairsDwarvenBrickCracked, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick4, 1, 5)));
        LOTRRecipes.addRecipeTo(commonDwarfRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle7, 6, 6), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick4, 1, 5)));
        LOTRRecipes.addRecipeTo(commonDwarfRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.wall4, 6, 5), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick4, 1, 5)));
        LOTRRecipes.addRecipeTo(commonDwarfRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle7, 6, 7), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.pillar2, 1, 0)));
        LOTRRecipes.addRecipeTo(commonDwarfRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.brick4, 4, 14), "XX", "XX", Character.valueOf('X'), LOTRMod.obsidianShard));
        LOTRRecipes.addRecipeTo(commonDwarfRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.stairsDwarvenBrickObsidian, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick4, 1, 14)));
        LOTRRecipes.addRecipeTo(commonDwarfRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle8, 6, 6), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick4, 1, 14)));
        LOTRRecipes.addRecipeTo(commonDwarfRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.wall4, 6, 6), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick4, 1, 14)));
        LOTRRecipes.addRecipeTo(commonDwarfRecipes, new LOTRRecipePoisonWeapon(LOTRMod.chisel, LOTRMod.chiselIthildin, new ItemStack(LOTRMod.ithildin, 1, 0)));
    }

    private static void createCommonDunedainRecipes() {
    }

    private static void createCommonNumenoreanRecipes() {
        LOTRRecipes.addRecipeTo(commonNumenoreanRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.brick2, 4, 11), "XX", "XX", Character.valueOf('X'), new ItemStack(LOTRMod.rock, 1, 0)));
        LOTRRecipes.addRecipeTo(commonNumenoreanRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle5, 6, 3), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick2, 1, 11)));
        LOTRRecipes.addRecipeTo(commonNumenoreanRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.stairsBlackGondorBrick, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick2, 1, 11)));
        LOTRRecipes.addRecipeTo(commonNumenoreanRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.wall2, 6, 10), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick2, 1, 11)));
        LOTRRecipes.addRecipeTo(commonNumenoreanRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.pillar, 3, 9), "X", "X", "X", Character.valueOf('X'), new ItemStack(LOTRMod.rock, 1, 0)));
        LOTRRecipes.addRecipeTo(commonNumenoreanRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle5, 6, 4), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.pillar, 1, 9)));
    }

    private static void createCommonNearHaradRecipes() {
        LOTRRecipes.addRecipeTo(commonNearHaradRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.brick, 4, 15), "XX", "XX", Character.valueOf('X'), new ItemStack(Blocks.sandstone, 1, 0)));
        LOTRRecipes.addRecipeTo(commonNearHaradRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle4, 6, 0), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick, 1, 15)));
        LOTRRecipes.addRecipeTo(commonNearHaradRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.stairsNearHaradBrick, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick, 1, 15)));
        LOTRRecipes.addRecipeTo(commonNearHaradRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.wall, 6, 15), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick, 1, 15)));
        LOTRRecipes.addRecipeTo(commonNearHaradRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.pillar, 3, 5), "X", "X", "X", Character.valueOf('X'), new ItemStack(Blocks.sandstone, 1, 0)));
        LOTRRecipes.addRecipeTo(commonNearHaradRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle4, 6, 7), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.pillar, 1, 5)));
        LOTRRecipes.addRecipeTo(commonNearHaradRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.brick3, 1, 8), "XX", "XX", Character.valueOf('X'), new ItemStack(LOTRMod.brick, 1, 15)));
        LOTRRecipes.addRecipeTo(commonNearHaradRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle7, 6, 1), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick3, 1, 11)));
        LOTRRecipes.addRecipeTo(commonNearHaradRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.stairsNearHaradBrickCracked, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick3, 1, 11)));
        LOTRRecipes.addRecipeTo(commonNearHaradRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.wall3, 6, 3), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick3, 1, 11)));
        LOTRRecipes.addDyeableWoolRobeRecipes(commonNearHaradRecipes, new ItemStack(LOTRMod.helmetHaradRobes), "XXX", "X X", Character.valueOf('X'), Blocks.wool);
        LOTRRecipes.addDyeableWoolRobeRecipes(commonNearHaradRecipes, new ItemStack(LOTRMod.bodyHaradRobes), "X X", "XXX", "XXX", Character.valueOf('X'), Blocks.wool);
        LOTRRecipes.addDyeableWoolRobeRecipes(commonNearHaradRecipes, new ItemStack(LOTRMod.legsHaradRobes), "XXX", "X X", "X X", Character.valueOf('X'), Blocks.wool);
        LOTRRecipes.addDyeableWoolRobeRecipes(commonNearHaradRecipes, new ItemStack(LOTRMod.bootsHaradRobes), "X X", "X X", Character.valueOf('X'), Blocks.wool);
        LOTRRecipes.addRecipeTo(commonNearHaradRecipes, new LOTRRecipeHaradRobesDye());
        LOTRRecipes.addRecipeTo(commonNearHaradRecipes, new LOTRRecipeHaradTurbanOrnament());
        LOTRRecipes.addRecipeTo(commonNearHaradRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.brick3, 4, 13), "XX", "XX", Character.valueOf('X'), new ItemStack(LOTRMod.redSandstone, 1, 0)));
        LOTRRecipes.addRecipeTo(commonNearHaradRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle7, 6, 2), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick3, 1, 13)));
        LOTRRecipes.addRecipeTo(commonNearHaradRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.stairsNearHaradBrickRed, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick3, 1, 13)));
        LOTRRecipes.addRecipeTo(commonNearHaradRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.wall3, 6, 4), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick3, 1, 13)));
        LOTRRecipes.addRecipeTo(commonNearHaradRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.brick3, 1, 15), "XX", "XX", Character.valueOf('X'), new ItemStack(LOTRMod.brick3, 1, 13)));
        LOTRRecipes.addRecipeTo(commonNearHaradRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle7, 6, 3), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick3, 1, 14)));
        LOTRRecipes.addRecipeTo(commonNearHaradRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.stairsNearHaradBrickRedCracked, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick3, 1, 14)));
        LOTRRecipes.addRecipeTo(commonNearHaradRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.wall3, 6, 5), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick3, 1, 14)));
        LOTRRecipes.addRecipeTo(commonNearHaradRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.pillar, 3, 15), "X", "X", "X", Character.valueOf('X'), new ItemStack(LOTRMod.redSandstone, 1, 0)));
        LOTRRecipes.addRecipeTo(commonNearHaradRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle7, 6, 4), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.pillar, 1, 15)));
        LOTRRecipes.addRecipeTo(commonNearHaradRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.brick4, 1, 7), " X ", "XYX", " X ", Character.valueOf('X'), "gemLapis", Character.valueOf('Y'), new ItemStack(LOTRMod.brick, 1, 15)));
        LOTRRecipes.addRecipeTo(commonNearHaradRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.gateNearHarad, 4), "ZYZ", "YXY", "ZYZ", Character.valueOf('X'), LOTRMod.gateGear, Character.valueOf('Y'), "plankWood", Character.valueOf('Z'), "ingotBronze"));
        LOTRRecipes.addRecipeTo(commonNearHaradRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.kebabStand), " X ", " Y ", "ZZZ", Character.valueOf('X'), "plankWood", Character.valueOf('Y'), "stickWood", Character.valueOf('Z'), Blocks.cobblestone));
        LOTRRecipes.addRecipeTo(commonNearHaradRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.shishKebab, 2), "  X", " X ", "Y  ", Character.valueOf('X'), LOTRMod.kebab, Character.valueOf('Y'), "stickWood"));
        LOTRRecipes.addRecipeTo(commonNearHaradRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.nearHaradBow), " XY", "X Y", " XY", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), Items.string));
        LOTRRecipes.addRecipeTo(commonNearHaradRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.helmetNomad), "XXX", "X X", Character.valueOf('X'), LOTRMod.driedReeds));
        LOTRRecipes.addRecipeTo(commonNearHaradRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.bodyNomad), "X X", "XXX", "XXX", Character.valueOf('X'), LOTRMod.driedReeds));
        LOTRRecipes.addRecipeTo(commonNearHaradRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.legsNomad), "XXX", "X X", "X X", Character.valueOf('X'), LOTRMod.driedReeds));
        LOTRRecipes.addRecipeTo(commonNearHaradRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.bootsNomad), "X X", "X X", Character.valueOf('X'), LOTRMod.driedReeds));
        LOTRRecipes.addRecipeTo(commonNearHaradRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.swordHarad), "X", "X", "Y", Character.valueOf('X'), "ingotBronze", Character.valueOf('Y'), "stickWood"));
        LOTRRecipes.addRecipeTo(commonNearHaradRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.daggerHarad), "X", "Y", Character.valueOf('X'), "ingotBronze", Character.valueOf('Y'), "stickWood"));
        LOTRRecipes.addRecipeTo(commonNearHaradRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.spearHarad), "  X", " Y ", "Y  ", Character.valueOf('X'), "ingotBronze", Character.valueOf('Y'), "stickWood"));
        LOTRRecipes.addRecipeTo(commonNearHaradRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.pikeHarad), "  X", " YX", "Y  ", Character.valueOf('X'), "ingotBronze", Character.valueOf('Y'), "stickWood"));
    }

    private static void createCommonHobbitRecipes() {
        LOTRRecipes.addRecipeTo(commonHobbitRecipes, new ShapedOreRecipe(new ItemStack(LOTRMod.hobbitOven), "XXX", "X X", "XXX", Character.valueOf('X'), Blocks.brick_block));
    }

    private static void createMorgulRecipes() {
        morgulRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.brick, 4, 0), "XX", "XX", Character.valueOf('X'), new ItemStack(LOTRMod.rock, 1, 0)));
        morgulRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.morgulTable), "XX", "YY", Character.valueOf('X'), "plankWood", Character.valueOf('Y'), new ItemStack(LOTRMod.rock, 1, 0)));
        morgulRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle10, 6, 7), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.rock, 1, 0)));
        morgulRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.smoothStone, 2, 0), "X", "X", Character.valueOf('X'), new ItemStack(LOTRMod.rock, 1, 0)));
        morgulRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle, 6, 0), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.smoothStone, 1, 0)));
        morgulRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle, 6, 1), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick, 1, 0)));
        morgulRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.stairsMordorBrick, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick, 1, 0)));
        morgulRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.wall, 6, 0), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.rock, 1, 0)));
        morgulRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.wall, 6, 1), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick, 1, 0)));
        morgulRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.orcTorchItem, 2), "X", "Y", "Y", Character.valueOf('X'), LOTRMod.nauriteGem, Character.valueOf('Y'), "stickWood"));
        morgulRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle2, 6, 2), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick, 1, 7)));
        morgulRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.stairsMordorBrickCracked, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick, 1, 7)));
        morgulRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.wall, 6, 9), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick, 1, 7)));
        morgulRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.orcForge), "XXX", "X X", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick, 1, 0)));
        morgulRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.banner, 1, LOTRItemBanner.BannerType.MORDOR.bannerID), "X", "Y", "Z", Character.valueOf('X'), Blocks.wool, Character.valueOf('Y'), "stickWood", Character.valueOf('Z'), "plankWood"));
        morgulRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.wargArmorMordor), "X  ", "XYX", "XXX", Character.valueOf('X'), LOTRMod.orcSteel, Character.valueOf('Y'), Items.leather));
        morgulRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.scimitarOrc), "X", "X", "Y", Character.valueOf('X'), LOTRMod.orcSteel, Character.valueOf('Y'), "stickWood"));
        morgulRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.battleaxeOrc), "XXX", "XYX", " Y ", Character.valueOf('X'), LOTRMod.orcSteel, Character.valueOf('Y'), "stickWood"));
        morgulRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.daggerOrc), "X", "Y", Character.valueOf('X'), LOTRMod.orcSteel, Character.valueOf('Y'), "stickWood"));
        morgulRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.helmetOrc), "XXX", "X X", Character.valueOf('X'), LOTRMod.orcSteel));
        morgulRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.bodyOrc), "X X", "XXX", "XXX", Character.valueOf('X'), LOTRMod.orcSteel));
        morgulRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.legsOrc), "XXX", "X X", "X X", Character.valueOf('X'), LOTRMod.orcSteel));
        morgulRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.bootsOrc), "X X", "X X", Character.valueOf('X'), LOTRMod.orcSteel));
        morgulRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.spearOrc), "  X", " Y ", "Y  ", Character.valueOf('X'), LOTRMod.orcSteel, Character.valueOf('Y'), "stickWood"));
        morgulRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.shovelOrc), "X", "Y", "Y", Character.valueOf('X'), LOTRMod.orcSteel, Character.valueOf('Y'), "stickWood"));
        morgulRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.pickaxeOrc), "XXX", " Y ", " Y ", Character.valueOf('X'), LOTRMod.orcSteel, Character.valueOf('Y'), "stickWood"));
        morgulRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.axeOrc), "XX", "XY", " Y", Character.valueOf('X'), LOTRMod.orcSteel, Character.valueOf('Y'), "stickWood"));
        morgulRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.hoeOrc), "XX", " Y", " Y", Character.valueOf('X'), LOTRMod.orcSteel, Character.valueOf('Y'), "stickWood"));
        morgulRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.hammerOrc), "XYX", "XYX", " Y ", Character.valueOf('X'), LOTRMod.orcSteel, Character.valueOf('Y'), "stickWood"));
        morgulRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.chandelier, 2, 7), " X ", "YZY", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), LOTRMod.orcTorchItem, Character.valueOf('Z'), LOTRMod.orcSteel));
        morgulRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.pillar, 3, 7), "X", "X", "X", Character.valueOf('X'), new ItemStack(LOTRMod.rock, 1, 0)));
        morgulRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle5, 6, 1), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.pillar, 1, 7)));
        morgulRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.brick2, 1, 10), "XX", "XX", Character.valueOf('X'), new ItemStack(LOTRMod.brick, 1, 0)));
        morgulRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.scimitarBlackUruk), "X", "X", "Y", Character.valueOf('X'), LOTRMod.blackUrukSteel, Character.valueOf('Y'), "stickWood"));
        morgulRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.daggerBlackUruk), "X", "Y", Character.valueOf('X'), LOTRMod.blackUrukSteel, Character.valueOf('Y'), "stickWood"));
        morgulRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.spearBlackUruk), "  X", " Y ", "Y  ", Character.valueOf('X'), LOTRMod.blackUrukSteel, Character.valueOf('Y'), "stickWood"));
        morgulRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.battleaxeBlackUruk), "XXX", "XYX", " Y ", Character.valueOf('X'), LOTRMod.blackUrukSteel, Character.valueOf('Y'), "stickWood"));
        morgulRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.hammerBlackUruk), "XYX", "XYX", " Y ", Character.valueOf('X'), LOTRMod.blackUrukSteel, Character.valueOf('Y'), "stickWood"));
        morgulRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.helmetBlackUruk), "XXX", "X X", Character.valueOf('X'), LOTRMod.blackUrukSteel));
        morgulRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.bodyBlackUruk), "X X", "XXX", "XXX", Character.valueOf('X'), LOTRMod.blackUrukSteel));
        morgulRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.legsBlackUruk), "XXX", "X X", "X X", Character.valueOf('X'), LOTRMod.blackUrukSteel));
        morgulRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.bootsBlackUruk), "X X", "X X", Character.valueOf('X'), LOTRMod.blackUrukSteel));
        morgulRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.blackUrukBow), " XY", "X Y", " XY", Character.valueOf('X'), LOTRMod.blackUrukSteel, Character.valueOf('Y'), Items.string));
        morgulRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.polearmOrc), " XX", " YX", "Y  ", Character.valueOf('X'), LOTRMod.orcSteel, Character.valueOf('Y'), "stickWood"));
        morgulRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.stairsMordorRock, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.rock, 1, 0)));
        morgulRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.gateOrc, 4), "YYY", "YXY", "YYY", Character.valueOf('X'), LOTRMod.gateGear, Character.valueOf('Y'), LOTRMod.orcSteel));
        morgulRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.banner, 1, LOTRItemBanner.BannerType.MINAS_MORGUL.bannerID), "XA", "Y ", "Z ", Character.valueOf('X'), Blocks.wool, Character.valueOf('Y'), "stickWood", Character.valueOf('Z'), "plankWood", Character.valueOf('A'), new ItemStack(Items.skull, 1, 0)));
        morgulRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.banner, 1, LOTRItemBanner.BannerType.BLACK_URUK.bannerID), "XA", "Y ", "Z ", Character.valueOf('X'), Blocks.wool, Character.valueOf('Y'), "stickWood", Character.valueOf('Z'), "plankWood", Character.valueOf('A'), LOTRMod.blackUrukSteel));
        morgulRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.banner, 1, LOTRItemBanner.BannerType.NAN_UNGOL.bannerID), "XA", "Y ", "Z ", Character.valueOf('X'), Blocks.wool, Character.valueOf('Y'), "stickWood", Character.valueOf('Z'), "plankWood", Character.valueOf('A'), Items.string));
    }

    private static void createElvenRecipes() {
        elvenRecipes.add(new ShapelessOreRecipe(new ItemStack(LOTRMod.planks, 4, 1), new ItemStack(LOTRMod.wood, 1, 1)));
        elvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.woodSlabSingle, 6, 1), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks, 1, 1)));
        elvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.stairsMallorn, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks, 1, 1)));
        elvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.elvenTable), "XX", "XX", Character.valueOf('X'), new ItemStack(LOTRMod.planks, 1, 1)));
        elvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.mallornStick, 4), "X", "X", Character.valueOf('X'), new ItemStack(LOTRMod.planks, 1, 1)));
        elvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.shovelMallorn), "X", "Y", "Y", Character.valueOf('X'), new ItemStack(LOTRMod.planks, 1, 1), Character.valueOf('Y'), LOTRMod.mallornStick));
        elvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.pickaxeMallorn), "XXX", " Y ", " Y ", Character.valueOf('X'), new ItemStack(LOTRMod.planks, 1, 1), Character.valueOf('Y'), LOTRMod.mallornStick));
        elvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.axeMallorn), "XX", "XY", " Y", Character.valueOf('X'), new ItemStack(LOTRMod.planks, 1, 1), Character.valueOf('Y'), LOTRMod.mallornStick));
        elvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.swordMallorn), "X", "X", "Y", Character.valueOf('X'), new ItemStack(LOTRMod.planks, 1, 1), Character.valueOf('Y'), LOTRMod.mallornStick));
        elvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.hoeMallorn), "XX", " Y", " Y", Character.valueOf('X'), new ItemStack(LOTRMod.planks, 1, 1), Character.valueOf('Y'), LOTRMod.mallornStick));
        elvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.shovelElven), "X", "Y", "Y", Character.valueOf('X'), LOTRMod.elfSteel, Character.valueOf('Y'), LOTRMod.mallornStick));
        elvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.pickaxeElven), "XXX", " Y ", " Y ", Character.valueOf('X'), LOTRMod.elfSteel, Character.valueOf('Y'), LOTRMod.mallornStick));
        elvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.axeElven), "XX", "XY", " Y", Character.valueOf('X'), LOTRMod.elfSteel, Character.valueOf('Y'), LOTRMod.mallornStick));
        elvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.swordElven), "X", "X", "Y", Character.valueOf('X'), LOTRMod.elfSteel, Character.valueOf('Y'), LOTRMod.mallornStick));
        elvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.hoeElven), "XX", " Y", " Y", Character.valueOf('X'), LOTRMod.elfSteel, Character.valueOf('Y'), LOTRMod.mallornStick));
        elvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.spearElven), "  X", " Y ", "Y  ", Character.valueOf('X'), LOTRMod.elfSteel, Character.valueOf('Y'), LOTRMod.mallornStick));
        elvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.mallornBow), " XY", "X Y", " XY", Character.valueOf('X'), LOTRMod.mallornStick, Character.valueOf('Y'), Items.string));
        elvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.helmetElven), "XXX", "X X", Character.valueOf('X'), LOTRMod.elfSteel));
        elvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.bodyElven), "X X", "XXX", "XXX", Character.valueOf('X'), LOTRMod.elfSteel));
        elvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.legsElven), "XXX", "X X", "X X", Character.valueOf('X'), LOTRMod.elfSteel));
        elvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.bootsElven), "X X", "X X", Character.valueOf('X'), LOTRMod.elfSteel));
        elvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.mallornLadder, 3), "X X", "XXX", "X X", Character.valueOf('X'), LOTRMod.mallornStick));
        elvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.elvenBow), " XY", "X Y", " XY", Character.valueOf('X'), LOTRMod.elfSteel, Character.valueOf('Y'), Items.string));
        elvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.daggerElven), "X", "Y", Character.valueOf('X'), LOTRMod.elfSteel, Character.valueOf('Y'), LOTRMod.mallornStick));
        elvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.elvenBedItem), "XXX", "YYY", Character.valueOf('X'), LOTRMod.hithlain, Character.valueOf('Y'), new ItemStack(LOTRMod.planks, 1, 1)));
        elvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.fence, 3, 1), "XYX", "XYX", Character.valueOf('X'), new ItemStack(LOTRMod.planks, 1, 1), Character.valueOf('Y'), LOTRMod.mallornStick));
        elvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.banner, 1, LOTRItemBanner.BannerType.GALADHRIM.bannerID), "X", "Y", "Z", Character.valueOf('X'), Blocks.wool, Character.valueOf('Y'), LOTRMod.mallornStick, Character.valueOf('Z'), new ItemStack(LOTRMod.planks, 1, 1)));
        elvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.horseArmorGaladhrim), "X  ", "XYX", "XXX", Character.valueOf('X'), LOTRMod.elfSteel, Character.valueOf('Y'), Items.leather));
        elvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.brick, 4, 11), "XX", "XX", Character.valueOf('X'), Blocks.stone));
        elvenRecipes.add(new ShapelessOreRecipe(new ItemStack(LOTRMod.brick, 1, 12), new ItemStack(LOTRMod.brick, 1, 11), "vine"));
        elvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle2, 6, 3), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick, 4, 11)));
        elvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle2, 6, 4), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick, 4, 12)));
        elvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle2, 6, 5), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick, 4, 13)));
        elvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.stairsElvenBrick, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick, 1, 11)));
        elvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.stairsElvenBrickMossy, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick, 1, 12)));
        elvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.stairsElvenBrickCracked, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick, 1, 13)));
        elvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.wall, 6, 10), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick, 1, 11)));
        elvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.wall, 6, 11), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick, 1, 12)));
        elvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.wall, 6, 12), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick, 1, 13)));
        elvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.pillar, 3, 1), "X", "X", "X", Character.valueOf('X'), Blocks.stone));
        elvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle2, 6, 6), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.pillar, 1, 1)));
        elvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle2, 6, 7), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.pillar, 1, 2)));
        elvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.brick2, 1, 15), "XX", "XX", Character.valueOf('X'), new ItemStack(LOTRMod.brick, 1, 11)));
        elvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.elvenForge), "XXX", "X X", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick, 1, 11)));
        elvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.galadhrimBars, 16), "XXX", "XXX", Character.valueOf('X'), LOTRMod.elfSteel));
        elvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.galadhrimWoodBars, 8), "XXX", "XXX", Character.valueOf('X'), LOTRMod.mallornStick));
        elvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.polearmElven), "  X", " Y ", "X  ", Character.valueOf('X'), LOTRMod.elfSteel, Character.valueOf('Y'), LOTRMod.mallornStick));
        elvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.hithlain, 3), "XXX", Character.valueOf('X'), Items.string));
        elvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.helmetHithlain), "XXX", "X X", Character.valueOf('X'), LOTRMod.hithlain));
        elvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.bodyHithlain), "X X", "XXX", "XXX", Character.valueOf('X'), LOTRMod.hithlain));
        elvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.legsHithlain), "XXX", "X X", "X X", Character.valueOf('X'), LOTRMod.hithlain));
        elvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.bootsHithlain), "X X", "X X", Character.valueOf('X'), LOTRMod.hithlain));
        elvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.hithlainLadder, 3), "X", "X", "X", Character.valueOf('X'), LOTRMod.hithlain));
        elvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.woodBeam1, 3, 1), "X", "X", "X", Character.valueOf('X'), new ItemStack(LOTRMod.wood, 1, 1)));
        elvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.brick4, 1, 9), " X ", "XYX", " X ", Character.valueOf('X'), "nuggetSilver", Character.valueOf('Y'), new ItemStack(LOTRMod.brick, 1, 11)));
        elvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.brick4, 1, 12), " X ", "XYX", " X ", Character.valueOf('X'), "nuggetGold", Character.valueOf('Y'), new ItemStack(LOTRMod.brick, 1, 11)));
        elvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.gateElven, 4), "ZYZ", "YXY", "ZYZ", Character.valueOf('X'), LOTRMod.gateGear, Character.valueOf('Y'), new ItemStack(LOTRMod.planks, 1, 1), Character.valueOf('Z'), LOTRMod.elfSteel));
        elvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.longspearElven), "  X", " YX", "Y  ", Character.valueOf('X'), LOTRMod.elfSteel, Character.valueOf('Y'), LOTRMod.mallornStick));
        elvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.chestMallorn), "XXX", "XYX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks, 1, 1)));
        elvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.mallornTorchSilver, 4), "Z", "X", "Y", Character.valueOf('X'), new ItemStack(Items.coal, 1, 32767), Character.valueOf('Y'), LOTRMod.mallornStick, Character.valueOf('Z'), new ItemStack(LOTRMod.leaves7, 1, 2)));
        elvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.mallornTorchSilver, 4), "Z", "X", "Y", Character.valueOf('X'), new ItemStack(Items.coal, 1, 32767), Character.valueOf('Y'), LOTRMod.mallornStick, Character.valueOf('Z'), new ItemStack(LOTRMod.niphredil, 1, 0)));
        elvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.chandelier, 2, 5), " X ", "YZY", Character.valueOf('X'), LOTRMod.mallornStick, Character.valueOf('Y'), LOTRMod.mallornTorchSilver, Character.valueOf('Z'), new ItemStack(LOTRMod.planks, 1, 1)));
        elvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.mallornTorchBlue, 4), "Z", "X", "Y", Character.valueOf('X'), new ItemStack(Items.coal, 1, 32767), Character.valueOf('Y'), LOTRMod.mallornStick, Character.valueOf('Z'), LOTRMod.quenditeCrystal));
        elvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.mallornTorchBlue, 4), "Z", "X", "Y", Character.valueOf('X'), new ItemStack(Items.coal, 1, 32767), Character.valueOf('Y'), LOTRMod.mallornStick, Character.valueOf('Z'), new ItemStack(LOTRMod.bluebell, 1, 0)));
        elvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.chandelier, 2, 13), " X ", "YZY", Character.valueOf('X'), LOTRMod.mallornStick, Character.valueOf('Y'), LOTRMod.mallornTorchBlue, Character.valueOf('Z'), new ItemStack(LOTRMod.planks, 1, 1)));
        elvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.mallornTorchGold, 4), "Z", "X", "Y", Character.valueOf('X'), new ItemStack(Items.coal, 1, 32767), Character.valueOf('Y'), LOTRMod.mallornStick, Character.valueOf('Z'), new ItemStack(LOTRMod.leaves, 1, 1)));
        elvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.mallornTorchGold, 4), "Z", "X", "Y", Character.valueOf('X'), new ItemStack(Items.coal, 1, 32767), Character.valueOf('Y'), LOTRMod.mallornStick, Character.valueOf('Z'), new ItemStack(LOTRMod.elanor, 1, 0)));
        elvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.chandelier, 2, 14), " X ", "YZY", Character.valueOf('X'), LOTRMod.mallornStick, Character.valueOf('Y'), LOTRMod.mallornTorchGold, Character.valueOf('Z'), new ItemStack(LOTRMod.planks, 1, 1)));
        elvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.mallornTorchGreen, 4), "Z", "X", "Y", Character.valueOf('X'), new ItemStack(Items.coal, 1, 32767), Character.valueOf('Y'), LOTRMod.mallornStick, Character.valueOf('Z'), new ItemStack(Blocks.leaves, 1, 0)));
        elvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.mallornTorchGreen, 4), "Z", "X", "Y", Character.valueOf('X'), new ItemStack(Items.coal, 1, 32767), Character.valueOf('Y'), LOTRMod.mallornStick, Character.valueOf('Z'), LOTRMod.clover));
        elvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.chandelier, 2, 15), " X ", "YZY", Character.valueOf('X'), LOTRMod.mallornStick, Character.valueOf('Y'), LOTRMod.mallornTorchGreen, Character.valueOf('Z'), new ItemStack(LOTRMod.planks, 1, 1)));
        elvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.fenceGateMallorn, 1), "XYX", "XYX", Character.valueOf('X'), LOTRMod.mallornStick, Character.valueOf('Y'), new ItemStack(LOTRMod.planks, 1, 1)));
        elvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.doorMallorn), "XX", "XX", "XX", Character.valueOf('X'), new ItemStack(LOTRMod.planks, 1, 1)));
        elvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.trapdoorMallorn, 2), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks, 1, 1)));
    }

    private static void createDwarvenRecipes() {
        dwarvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.dwarvenTable), "XX", "YY", Character.valueOf('X'), "plankWood", Character.valueOf('Y'), new ItemStack(LOTRMod.brick, 1, 6)));
        dwarvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.shovelDwarven), "X", "Y", "Y", Character.valueOf('X'), LOTRMod.dwarfSteel, Character.valueOf('Y'), "stickWood"));
        dwarvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.pickaxeDwarven), "XXX", " Y ", " Y ", Character.valueOf('X'), LOTRMod.dwarfSteel, Character.valueOf('Y'), "stickWood"));
        dwarvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.axeDwarven), "XX", "XY", " Y", Character.valueOf('X'), LOTRMod.dwarfSteel, Character.valueOf('Y'), "stickWood"));
        dwarvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.swordDwarven), "X", "X", "Y", Character.valueOf('X'), LOTRMod.dwarfSteel, Character.valueOf('Y'), "stickWood"));
        dwarvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.hoeDwarven), "XX", " Y", " Y", Character.valueOf('X'), LOTRMod.dwarfSteel, Character.valueOf('Y'), "stickWood"));
        dwarvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.daggerDwarven), "X", "Y", Character.valueOf('X'), LOTRMod.dwarfSteel, Character.valueOf('Y'), "stickWood"));
        dwarvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.battleaxeDwarven), "XXX", "XYX", " Y ", Character.valueOf('X'), LOTRMod.dwarfSteel, Character.valueOf('Y'), "stickWood"));
        dwarvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.hammerDwarven), "XYX", "XYX", " Y ", Character.valueOf('X'), LOTRMod.dwarfSteel, Character.valueOf('Y'), "stickWood"));
        dwarvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.helmetDwarven), "XXX", "X X", Character.valueOf('X'), LOTRMod.dwarfSteel));
        dwarvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.bodyDwarven), "X X", "XXX", "XXX", Character.valueOf('X'), LOTRMod.dwarfSteel));
        dwarvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.legsDwarven), "XXX", "X X", "X X", Character.valueOf('X'), LOTRMod.dwarfSteel));
        dwarvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.bootsDwarven), "X X", "X X", Character.valueOf('X'), LOTRMod.dwarfSteel));
        dwarvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.throwingAxeDwarven), " X ", " YX", "Y  ", Character.valueOf('X'), LOTRMod.dwarfSteel, Character.valueOf('Y'), "stickWood"));
        dwarvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.chandelier, 2, 8), " X ", "YZY", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), Blocks.torch, Character.valueOf('Z'), LOTRMod.dwarfSteel));
        dwarvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.mattockDwarven), "XXX", "XY ", " Y ", Character.valueOf('X'), LOTRMod.dwarfSteel, Character.valueOf('Y'), "stickWood"));
        dwarvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.banner, 1, LOTRItemBanner.BannerType.DWARF.bannerID), "X", "Y", "Z", Character.valueOf('X'), Blocks.wool, Character.valueOf('Y'), "stickWood", Character.valueOf('Z'), "plankWood"));
        dwarvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.spearDwarven), "  X", " Y ", "Y  ", Character.valueOf('X'), LOTRMod.dwarfSteel, Character.valueOf('Y'), "stickWood"));
        dwarvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.helmetDwarvenSilver), "XXX", "XYX", "XXX", Character.valueOf('X'), "nuggetSilver", Character.valueOf('Y'), LOTRMod.helmetDwarven));
        dwarvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.bodyDwarvenSilver), "XXX", "XYX", "XXX", Character.valueOf('X'), "nuggetSilver", Character.valueOf('Y'), LOTRMod.bodyDwarven));
        dwarvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.legsDwarvenSilver), "XXX", "XYX", "XXX", Character.valueOf('X'), "nuggetSilver", Character.valueOf('Y'), LOTRMod.legsDwarven));
        dwarvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.bootsDwarvenSilver), "XXX", "XYX", "XXX", Character.valueOf('X'), "nuggetSilver", Character.valueOf('Y'), LOTRMod.bootsDwarven));
        dwarvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.helmetDwarvenGold), "XXX", "XYX", "XXX", Character.valueOf('X'), "nuggetGold", Character.valueOf('Y'), LOTRMod.helmetDwarven));
        dwarvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.bodyDwarvenGold), "XXX", "XYX", "XXX", Character.valueOf('X'), "nuggetGold", Character.valueOf('Y'), LOTRMod.bodyDwarven));
        dwarvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.legsDwarvenGold), "XXX", "XYX", "XXX", Character.valueOf('X'), "nuggetGold", Character.valueOf('Y'), LOTRMod.legsDwarven));
        dwarvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.bootsDwarvenGold), "XXX", "XYX", "XXX", Character.valueOf('X'), "nuggetGold", Character.valueOf('Y'), LOTRMod.bootsDwarven));
        dwarvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.helmetDwarvenMithril), "XXX", "XYX", "XXX", Character.valueOf('X'), LOTRMod.mithrilNugget, Character.valueOf('Y'), LOTRMod.helmetDwarven));
        dwarvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.bodyDwarvenMithril), "XXX", "XYX", "XXX", Character.valueOf('X'), LOTRMod.mithrilNugget, Character.valueOf('Y'), LOTRMod.bodyDwarven));
        dwarvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.legsDwarvenMithril), "XXX", "XYX", "XXX", Character.valueOf('X'), LOTRMod.mithrilNugget, Character.valueOf('Y'), LOTRMod.legsDwarven));
        dwarvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.bootsDwarvenMithril), "XXX", "XYX", "XXX", Character.valueOf('X'), LOTRMod.mithrilNugget, Character.valueOf('Y'), LOTRMod.bootsDwarven));
        dwarvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.dwarfBars, 16), "XXX", "XXX", Character.valueOf('X'), LOTRMod.dwarfSteel));
        dwarvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.boarArmorDwarven), "X  ", "XYX", "XXX", Character.valueOf('X'), LOTRMod.dwarfSteel, Character.valueOf('Y'), Items.leather));
        dwarvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.pikeDwarven), "  X", " YX", "Y  ", Character.valueOf('X'), LOTRMod.dwarfSteel, Character.valueOf('Y'), "stickWood"));
        dwarvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.gateDwarven, 4), "ZYZ", "YXY", "ZYZ", Character.valueOf('X'), LOTRMod.gateGear, Character.valueOf('Y'), new ItemStack(LOTRMod.brick, 1, 6), Character.valueOf('Z'), LOTRMod.dwarfSteel));
        dwarvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.mechanism), " X ", "YZY", " X ", Character.valueOf('X'), "ingotCopper", Character.valueOf('Y'), Items.flint, Character.valueOf('Z'), LOTRMod.dwarfSteel));
    }

    private static void createUrukRecipes() {
        urukRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.urukTable), "XX", "YY", Character.valueOf('X'), "plankWood", Character.valueOf('Y'), new ItemStack(LOTRMod.brick2, 1, 7)));
        urukRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.orcTorchItem, 2), "X", "Y", "Y", Character.valueOf('X'), new ItemStack(Items.coal, 1, 32767), Character.valueOf('Y'), "stickWood"));
        urukRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.shovelUruk), "X", "Y", "Y", Character.valueOf('X'), LOTRMod.urukSteel, Character.valueOf('Y'), "stickWood"));
        urukRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.pickaxeUruk), "XXX", " Y ", " Y ", Character.valueOf('X'), LOTRMod.urukSteel, Character.valueOf('Y'), "stickWood"));
        urukRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.axeUruk), "XX", "XY", " Y", Character.valueOf('X'), LOTRMod.urukSteel, Character.valueOf('Y'), "stickWood"));
        urukRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.scimitarUruk), "X", "X", "Y", Character.valueOf('X'), LOTRMod.urukSteel, Character.valueOf('Y'), "stickWood"));
        urukRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.hoeUruk), "XX", " Y", " Y", Character.valueOf('X'), LOTRMod.urukSteel, Character.valueOf('Y'), "stickWood"));
        urukRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.daggerUruk), "X", "Y", Character.valueOf('X'), LOTRMod.urukSteel, Character.valueOf('Y'), "stickWood"));
        urukRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.battleaxeUruk), "XXX", "XYX", " Y ", Character.valueOf('X'), LOTRMod.urukSteel, Character.valueOf('Y'), "stickWood"));
        urukRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.hammerUruk), "XYX", "XYX", " Y ", Character.valueOf('X'), LOTRMod.urukSteel, Character.valueOf('Y'), "stickWood"));
        urukRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.spearUruk), "  X", " Y ", "Y  ", Character.valueOf('X'), LOTRMod.urukSteel, Character.valueOf('Y'), "stickWood"));
        urukRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.helmetUruk), "XXX", "X X", Character.valueOf('X'), LOTRMod.urukSteel));
        urukRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.bodyUruk), "X X", "XXX", "XXX", Character.valueOf('X'), LOTRMod.urukSteel));
        urukRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.legsUruk), "XXX", "X X", "X X", Character.valueOf('X'), LOTRMod.urukSteel));
        urukRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.bootsUruk), "X X", "X X", Character.valueOf('X'), LOTRMod.urukSteel));
        urukRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.urukCrossbow), "XXY", "ZYX", "YZX", Character.valueOf('X'), LOTRMod.urukSteel, Character.valueOf('Y'), "stickWood", Character.valueOf('Z'), Items.string));
        urukRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.chandelier, 2, 9), " X ", "YZY", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), LOTRMod.orcTorchItem, Character.valueOf('Z'), LOTRMod.urukSteel));
        urukRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.orcForge), "XXX", "X X", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick2, 1, 7)));
        urukRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.orcBomb, 4), "XYX", "YXY", "XYX", Character.valueOf('X'), Items.gunpowder, Character.valueOf('Y'), LOTRMod.urukSteel));
        urukRecipes.add(new ShapelessOreRecipe(new ItemStack(LOTRMod.orcBomb, 1, 1), new ItemStack(LOTRMod.orcBomb, 1, 0), Items.gunpowder, LOTRMod.urukSteel));
        urukRecipes.add(new ShapelessOreRecipe(new ItemStack(LOTRMod.orcBomb, 1, 2), new ItemStack(LOTRMod.orcBomb, 1, 1), Items.gunpowder, LOTRMod.urukSteel));
        urukRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.banner, 1, LOTRItemBanner.BannerType.ISENGARD.bannerID), "X", "Y", "Z", Character.valueOf('X'), Blocks.wool, Character.valueOf('Y'), "stickWood", Character.valueOf('Z'), "plankWood"));
        urukRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.wargArmorUruk), "X  ", "XYX", "XXX", Character.valueOf('X'), LOTRMod.urukSteel, Character.valueOf('Y'), Items.leather));
        urukRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.brick2, 4, 7), "XX", "XX", Character.valueOf('X'), Blocks.stone));
        urukRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle4, 6, 4), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick2, 1, 7)));
        urukRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.stairsUrukBrick, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick2, 1, 7)));
        urukRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.wall2, 6, 7), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick2, 1, 7)));
        urukRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.urukBars, 16), "XXX", "XXX", Character.valueOf('X'), LOTRMod.urukSteel));
        urukRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.pikeUruk), "  X", " YX", "Y  ", Character.valueOf('X'), LOTRMod.urukSteel, Character.valueOf('Y'), "stickWood"));
        urukRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.scimitarUrukBerserker), "XXX", " X ", " Y ", Character.valueOf('X'), LOTRMod.urukSteel, Character.valueOf('Y'), "stickWood"));
        urukRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.helmetUrukBerserker), "XYX", " Z ", Character.valueOf('X'), LOTRMod.urukSteel, Character.valueOf('Y'), "dyeWhite", Character.valueOf('Z'), new ItemStack(LOTRMod.helmetUruk, 1, 0)));
        urukRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.gateUruk, 4), "YYY", "YXY", "YYY", Character.valueOf('X'), LOTRMod.gateGear, Character.valueOf('Y'), LOTRMod.urukSteel));
        urukRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.pillar2, 3, 15), "X", "X", "X", Character.valueOf('X'), Blocks.stone));
        urukRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle14, 6, 3), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.pillar2, 1, 15)));
        urukRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.mechanism), " X ", "YZY", " X ", Character.valueOf('X'), "ingotCopper", Character.valueOf('Y'), Items.flint, Character.valueOf('Z'), LOTRMod.urukSteel));
    }

    private static void createWoodElvenRecipes() {
        woodElvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.woodElvenTable), "XX", "XX", Character.valueOf('X'), "plankWood"));
        woodElvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.woodElvenBedItem), "XXX", "YYY", Character.valueOf('X'), Blocks.wool, Character.valueOf('Y'), "plankWood"));
        woodElvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.helmetWoodElvenScout), "XXX", "X X", Character.valueOf('X'), Items.leather));
        woodElvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.bodyWoodElvenScout), "X X", "XXX", "XXX", Character.valueOf('X'), Items.leather));
        woodElvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.legsWoodElvenScout), "XXX", "X X", "X X", Character.valueOf('X'), Items.leather));
        woodElvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.bootsWoodElvenScout), "X X", "X X", Character.valueOf('X'), Items.leather));
        woodElvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.mirkwoodBow), " XY", "X Y", " XY", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), Items.string));
        woodElvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.woodElvenTorch, 4), "X", "Y", "Z", Character.valueOf('X'), new ItemStack(LOTRMod.leaves, 1, 3), Character.valueOf('Y'), new ItemStack(Items.coal, 1, 32767), Character.valueOf('Z'), "stickWood"));
        woodElvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.chandelier, 2, 6), " X ", "YZY", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), LOTRMod.woodElvenTorch, Character.valueOf('Z'), "plankWood"));
        woodElvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.shovelWoodElven), "X", "Y", "Y", Character.valueOf('X'), LOTRMod.elfSteel, Character.valueOf('Y'), "stickWood"));
        woodElvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.pickaxeWoodElven), "XXX", " Y ", " Y ", Character.valueOf('X'), LOTRMod.elfSteel, Character.valueOf('Y'), "stickWood"));
        woodElvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.axeWoodElven), "XX", "XY", " Y", Character.valueOf('X'), LOTRMod.elfSteel, Character.valueOf('Y'), "stickWood"));
        woodElvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.swordWoodElven), "X", "X", "Y", Character.valueOf('X'), LOTRMod.elfSteel, Character.valueOf('Y'), "stickWood"));
        woodElvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.hoeWoodElven), "XX", " Y", " Y", Character.valueOf('X'), LOTRMod.elfSteel, Character.valueOf('Y'), "stickWood"));
        woodElvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.daggerWoodElven), "X", "Y", Character.valueOf('X'), LOTRMod.elfSteel, Character.valueOf('Y'), "stickWood"));
        woodElvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.spearWoodElven), "  X", " Y ", "Y  ", Character.valueOf('X'), LOTRMod.elfSteel, Character.valueOf('Y'), "stickWood"));
        woodElvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.helmetWoodElven), "XXX", "X X", Character.valueOf('X'), LOTRMod.elfSteel));
        woodElvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.bodyWoodElven), "X X", "XXX", "XXX", Character.valueOf('X'), LOTRMod.elfSteel));
        woodElvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.legsWoodElven), "XXX", "X X", "X X", Character.valueOf('X'), LOTRMod.elfSteel));
        woodElvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.bootsWoodElven), "X X", "X X", Character.valueOf('X'), LOTRMod.elfSteel));
        woodElvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.banner, 1, LOTRItemBanner.BannerType.WOOD_ELF.bannerID), "X", "Y", "Z", Character.valueOf('X'), Blocks.wool, Character.valueOf('Y'), "stickWood", Character.valueOf('Z'), "plankWood"));
        woodElvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.elkArmorWoodElven), "X  ", "XYX", "XXX", Character.valueOf('X'), LOTRMod.elfSteel, Character.valueOf('Y'), Items.leather));
        woodElvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.brick3, 4, 5), "XX", "XX", Character.valueOf('X'), Blocks.stone));
        woodElvenRecipes.add(new ShapelessOreRecipe(new ItemStack(LOTRMod.brick3, 1, 6), new ItemStack(LOTRMod.brick3, 1, 5), "vine"));
        woodElvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle6, 6, 2), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick3, 4, 5)));
        woodElvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle6, 6, 3), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick3, 4, 6)));
        woodElvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle6, 6, 4), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick3, 4, 7)));
        woodElvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.stairsWoodElvenBrick, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick3, 1, 5)));
        woodElvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.stairsWoodElvenBrickMossy, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick3, 1, 6)));
        woodElvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.stairsWoodElvenBrickCracked, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick3, 1, 7)));
        woodElvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.wall3, 6, 0), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick3, 1, 5)));
        woodElvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.wall3, 6, 1), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick3, 1, 6)));
        woodElvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.wall3, 6, 2), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick3, 1, 7)));
        woodElvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.pillar, 3, 12), "X", "X", "X", Character.valueOf('X'), Blocks.stone));
        woodElvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle6, 6, 5), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.pillar, 1, 12)));
        woodElvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle6, 6, 6), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.pillar, 1, 13)));
        woodElvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.brick2, 1, 14), "XX", "XX", Character.valueOf('X'), new ItemStack(LOTRMod.brick3, 1, 5)));
        woodElvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.elvenForge), "XXX", "X X", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick3, 1, 5)));
        woodElvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.woodElfBars, 16), "XXX", "XXX", Character.valueOf('X'), LOTRMod.elfSteel));
        woodElvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.woodElfWoodBars, 8), "XXX", "XXX", Character.valueOf('X'), "stickWood"));
        woodElvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.polearmWoodElven), "  X", " Y ", "X  ", Character.valueOf('X'), LOTRMod.elfSteel, Character.valueOf('Y'), "stickWood"));
        woodElvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.brick4, 1, 10), " X ", "XYX", " X ", Character.valueOf('X'), "nuggetSilver", Character.valueOf('Y'), new ItemStack(LOTRMod.brick3, 1, 5)));
        woodElvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.brick4, 1, 13), " X ", "XYX", " X ", Character.valueOf('X'), "nuggetGold", Character.valueOf('Y'), new ItemStack(LOTRMod.brick3, 1, 5)));
        woodElvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.gateWoodElven, 4), "ZYZ", "YXY", "ZYZ", Character.valueOf('X'), LOTRMod.gateGear, Character.valueOf('Y'), "plankWood", Character.valueOf('Z'), LOTRMod.elfSteel));
        woodElvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.longspearWoodElven), "  X", " YX", "Y  ", Character.valueOf('X'), LOTRMod.elfSteel, Character.valueOf('Y'), "stickWood"));
    }

    private static void createGondorianRecipes() {
        gondorianRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.gondorianTable), "XX", "YY", Character.valueOf('X'), "plankWood", Character.valueOf('Y'), new ItemStack(LOTRMod.rock, 1, 1)));
        gondorianRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.beacon), "XXX", "XXX", "YYY", Character.valueOf('X'), "logWood", Character.valueOf('Y'), Blocks.cobblestone));
        gondorianRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle11, 6, 3), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.rock, 1, 1)));
        gondorianRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.smoothStone, 2, 1), "X", "X", Character.valueOf('X'), new ItemStack(LOTRMod.rock, 1, 1)));
        gondorianRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle, 6, 2), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.smoothStone, 1, 1)));
        gondorianRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.brick, 4, 1), "XX", "XX", Character.valueOf('X'), new ItemStack(LOTRMod.rock, 1, 1)));
        gondorianRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle, 6, 3), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick, 1, 1)));
        gondorianRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.stairsGondorBrick, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick, 1, 1)));
        gondorianRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.wall, 6, 2), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.rock, 1, 1)));
        gondorianRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.wall, 6, 3), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick, 1, 1)));
        gondorianRecipes.add(new ShapelessOreRecipe(new ItemStack(LOTRMod.brick, 1, 2), new ItemStack(LOTRMod.brick, 1, 1), "vine"));
        gondorianRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle, 6, 4), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick, 1, 2)));
        gondorianRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.stairsGondorBrickMossy, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick, 1, 2)));
        gondorianRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.wall, 6, 4), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick, 1, 2)));
        gondorianRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle, 6, 5), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick, 1, 3)));
        gondorianRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.stairsGondorBrickCracked, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick, 1, 3)));
        gondorianRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.wall, 6, 5), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick, 1, 3)));
        gondorianRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.brick, 1, 5), "XX", "XX", Character.valueOf('X'), new ItemStack(LOTRMod.brick, 1, 1)));
        gondorianRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.helmetGondor), "XXX", "X X", Character.valueOf('X'), "ingotIron"));
        gondorianRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.bodyGondor), "X X", "XXX", "XXX", Character.valueOf('X'), "ingotIron"));
        gondorianRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.legsGondor), "XXX", "X X", "X X", Character.valueOf('X'), "ingotIron"));
        gondorianRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.bootsGondor), "X X", "X X", Character.valueOf('X'), "ingotIron"));
        gondorianRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.swordGondor), "X", "X", "Y", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
        gondorianRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.spearGondor), "  X", " Y ", "Y  ", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
        gondorianRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.daggerGondor), "X", "Y", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
        gondorianRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.hammerGondor), "XYX", "XYX", " Y ", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
        gondorianRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.gondorBow), " XY", "X Y", " XY", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), Items.string));
        gondorianRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.helmetGondorWinged), "XYX", Character.valueOf('X'), "feather", Character.valueOf('Y'), new ItemStack(LOTRMod.helmetGondor, 1, 0)));
        gondorianRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.banner, 1, LOTRItemBanner.BannerType.GONDOR.bannerID), "X", "Y", "Z", Character.valueOf('X'), Blocks.wool, Character.valueOf('Y'), "stickWood", Character.valueOf('Z'), "plankWood"));
        gondorianRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.horseArmorGondor), "X  ", "XYX", "XXX", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), Items.leather));
        gondorianRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.pillar, 3, 6), "X", "X", "X", Character.valueOf('X'), new ItemStack(LOTRMod.rock, 1, 1)));
        gondorianRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle5, 6, 0), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.pillar, 1, 6)));
        gondorianRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.lanceGondor), "  X", " X ", "Y  ", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
        gondorianRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.stairsGondorRock, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.rock, 1, 1)));
        gondorianRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.gateGondor, 4), "ZYZ", "YXY", "ZYZ", Character.valueOf('X'), LOTRMod.gateGear, Character.valueOf('Y'), "plankWood", Character.valueOf('Z'), "ingotIron"));
        gondorianRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.helmetRangerIthilien), "XXX", "X X", Character.valueOf('X'), Items.leather));
        gondorianRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.bodyRangerIthilien), "X X", "XXX", "XXX", Character.valueOf('X'), Items.leather));
        gondorianRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.legsRangerIthilien), "XXX", "X X", "X X", Character.valueOf('X'), Items.leather));
        gondorianRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.bootsRangerIthilien), "X X", "X X", Character.valueOf('X'), Items.leather));
        gondorianRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.banner, 1, LOTRItemBanner.BannerType.ANORIEN.bannerID), "XA", "Y ", "Z ", Character.valueOf('X'), Blocks.wool, Character.valueOf('Y'), "stickWood", Character.valueOf('Z'), "plankWood", Character.valueOf('A'), "nuggetGold"));
        gondorianRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.banner, 1, LOTRItemBanner.BannerType.ITHILIEN.bannerID), "XA", "Y ", "Z ", Character.valueOf('X'), Blocks.wool, Character.valueOf('Y'), "stickWood", Character.valueOf('Z'), "plankWood", Character.valueOf('A'), "nuggetSilver"));
        gondorianRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.banner, 1, LOTRItemBanner.BannerType.LOSSARNACH.bannerID), "XA", "Y ", "Z ", Character.valueOf('X'), Blocks.wool, Character.valueOf('Y'), "stickWood", Character.valueOf('Z'), "plankWood", Character.valueOf('A'), new ItemStack(Blocks.double_plant, 1, 4)));
        gondorianRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.banner, 1, LOTRItemBanner.BannerType.PINNATH_GELIN.bannerID), "XA", "Y ", "Z ", Character.valueOf('X'), Blocks.wool, Character.valueOf('Y'), "stickWood", Character.valueOf('Z'), "plankWood", Character.valueOf('A'), "dyeGreen"));
        gondorianRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.banner, 1, LOTRItemBanner.BannerType.LEBENNIN.bannerID), "XA", "Y ", "Z ", Character.valueOf('X'), Blocks.wool, Character.valueOf('Y'), "stickWood", Character.valueOf('Z'), "plankWood", Character.valueOf('A'), "dyeLightBlue"));
        gondorianRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.banner, 1, LOTRItemBanner.BannerType.PELARGIR.bannerID), "XA", "Y ", "Z ", Character.valueOf('X'), Blocks.wool, Character.valueOf('Y'), "stickWood", Character.valueOf('Z'), "plankWood", Character.valueOf('A'), "dyeCyan"));
        gondorianRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.banner, 1, LOTRItemBanner.BannerType.BLACKROOT_VALE.bannerID), "XA", "Y ", "Z ", Character.valueOf('X'), Blocks.wool, Character.valueOf('Y'), "stickWood", Character.valueOf('Z'), "plankWood", Character.valueOf('A'), LOTRMod.blackroot));
        gondorianRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.banner, 1, LOTRItemBanner.BannerType.LAMEDON.bannerID), "XA", "Y ", "Z ", Character.valueOf('X'), Blocks.wool, Character.valueOf('Y'), "stickWood", Character.valueOf('Z'), "plankWood", Character.valueOf('A'), "dyeBlue"));
        gondorianRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.helmetLossarnach), "XXX", "Y Y", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), Items.leather));
        gondorianRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.bodyLossarnach), "X X", "YYY", "XXX", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), Items.leather));
        gondorianRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.legsLossarnach), "XXX", "Y Y", "X X", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), Items.leather));
        gondorianRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.bootsLossarnach), "Y Y", "X X", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), Items.leather));
        gondorianRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.helmetPinnathGelin), "XXX", "YZY", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), Items.leather, Character.valueOf('Z'), "dyeGreen"));
        gondorianRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.bodyPinnathGelin), "XZX", "YYY", "XXX", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), Items.leather, Character.valueOf('Z'), "dyeGreen"));
        gondorianRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.legsPinnathGelin), "XXX", "YZY", "X X", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), Items.leather, Character.valueOf('Z'), "dyeGreen"));
        gondorianRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.bootsPinnathGelin), "YZY", "X X", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), Items.leather, Character.valueOf('Z'), "dyeGreen"));
        gondorianRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.battleaxeLossarnach), "XXX", "XYX", " Y ", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
        gondorianRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.throwingAxeLossarnach), " X ", " YX", "Y  ", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
        gondorianRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.helmetPelargir), "XXX", "XYX", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "dyeCyan"));
        gondorianRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.bodyPelargir), "XYX", "XXX", "XXX", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "dyeCyan"));
        gondorianRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.legsPelargir), "XXX", "XYX", "X X", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "dyeCyan"));
        gondorianRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.bootsPelargir), "XYX", "X X", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "dyeCyan"));
        gondorianRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.tridentPelargir), " XX", " YX", "Y  ", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
        gondorianRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.swordPelargir), " X", "X ", "Y ", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
        gondorianRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.brick5, 4, 8), "XY", "YX", Character.valueOf('X'), new ItemStack(LOTRMod.rock, 1, 1), Character.valueOf('Y'), Blocks.cobblestone));
        gondorianRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle11, 6, 0), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick5, 1, 8)));
        gondorianRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.stairsGondorBrickRustic, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick5, 1, 8)));
        gondorianRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.wall4, 6, 7), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick5, 1, 8)));
        gondorianRecipes.add(new ShapelessOreRecipe(new ItemStack(LOTRMod.brick5, 1, 9), new ItemStack(LOTRMod.brick5, 1, 8), "vine"));
        gondorianRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle11, 6, 1), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick5, 1, 9)));
        gondorianRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.stairsGondorBrickRusticMossy, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick5, 1, 9)));
        gondorianRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.wall4, 6, 8), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick5, 1, 9)));
        gondorianRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle11, 6, 2), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick5, 1, 10)));
        gondorianRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.stairsGondorBrickRusticCracked, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick5, 1, 10)));
        gondorianRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.wall4, 6, 9), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick5, 1, 10)));
        gondorianRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.banner, 1, LOTRItemBanner.BannerType.GONDOR_STEWARD.bannerID), "XA", "Y ", "Z ", Character.valueOf('X'), Blocks.wool, Character.valueOf('Y'), "stickWood", Character.valueOf('Z'), "plankWood", Character.valueOf('A'), "dyeWhite"));
        gondorianRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.helmetBlackroot), "XXX", "XYX", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "dyeBlack"));
        gondorianRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.bodyBlackroot), "XYX", "XXX", "XXX", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "dyeBlack"));
        gondorianRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.legsBlackroot), "XXX", "XYX", "X X", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "dyeBlack"));
        gondorianRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.bootsBlackroot), "XYX", "X X", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "dyeBlack"));
        gondorianRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.blackrootBow), " XY", "Z Y", " XY", Character.valueOf('X'), LOTRMod.blackrootStick, Character.valueOf('Y'), Items.string, Character.valueOf('Z'), LOTRMod.blackroot));
        gondorianRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.pikeGondor), "  X", " YX", "Y  ", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
        gondorianRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.chestLebethron), "XXX", "XYX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.planks, 1, 8), Character.valueOf('Y'), "nuggetSilver"));
        gondorianRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.bodyGondorGambeson), "X X", "XXX", "XXX", Character.valueOf('X'), Blocks.wool));
        gondorianRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.bodyLebenninGambeson), "XYX", "XXX", "XXX", Character.valueOf('X'), Blocks.wool, Character.valueOf('Y'), "dyeLightBlue"));
        gondorianRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.helmetLamedon), "XXX", "YZY", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), Items.leather, Character.valueOf('Z'), "dyeBlue"));
        gondorianRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.bodyLamedon), "XZX", "YYY", "XXX", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), Items.leather, Character.valueOf('Z'), "dyeBlue"));
        gondorianRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.legsLamedon), "XXX", "YZY", "X X", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), Items.leather, Character.valueOf('Z'), "dyeBlue"));
        gondorianRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.bootsLamedon), "YZY", "X X", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), Items.leather, Character.valueOf('Z'), "dyeBlue"));
        gondorianRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.horseArmorLamedon), "XZ ", "XYX", "XXX", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), Items.leather, Character.valueOf('Z'), "dyeBlue"));
        gondorianRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.bodyLamedonJacket), "XYX", "XXX", "XXX", Character.valueOf('X'), Items.leather, Character.valueOf('Y'), "dyeBlue"));
        gondorianRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.brick4, 1, 6), "XX", "XX", Character.valueOf('X'), new ItemStack(LOTRMod.brick2, 1, 11)));
    }

    private static void createRohirricRecipes() {
        rohirricRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.rohirricTable), "XX", "XX", Character.valueOf('X'), "plankWood"));
        rohirricRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle11, 6, 4), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.rock, 1, 2)));
        rohirricRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.smoothStone, 2, 2), "X", "X", Character.valueOf('X'), new ItemStack(LOTRMod.rock, 1, 2)));
        rohirricRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle2, 6, 1), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.smoothStone, 1, 2)));
        rohirricRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.brick, 4, 4), "XX", "XX", Character.valueOf('X'), new ItemStack(LOTRMod.rock, 1, 2)));
        rohirricRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle, 6, 6), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick, 1, 4)));
        rohirricRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.stairsRohanBrick, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick, 1, 4)));
        rohirricRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.wall, 6, 8), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.rock, 1, 2)));
        rohirricRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.wall, 6, 6), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick, 1, 4)));
        rohirricRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.swordRohan), "X", "X", "Y", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
        rohirricRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.daggerRohan), "X", "Y", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
        rohirricRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.spearRohan), "  X", " Y ", "Y  ", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
        rohirricRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.helmetRohan), "XXX", "Y Y", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), Items.leather));
        rohirricRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.bodyRohan), "X X", "YYY", "XXX", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), Items.leather));
        rohirricRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.legsRohan), "XXX", "Y Y", "X X", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), Items.leather));
        rohirricRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.bootsRohan), "Y Y", "X X", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), Items.leather));
        rohirricRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.banner, 1, LOTRItemBanner.BannerType.ROHAN.bannerID), "X", "Y", "Z", Character.valueOf('X'), Blocks.wool, Character.valueOf('Y'), "stickWood", Character.valueOf('Z'), "plankWood"));
        rohirricRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.battleaxeRohan), "XXX", "XYX", " Y ", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
        rohirricRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.horseArmorRohan), "X  ", "XYX", "XXX", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), Items.leather));
        rohirricRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.pillar, 3, 8), "X", "X", "X", Character.valueOf('X'), new ItemStack(LOTRMod.rock, 1, 2)));
        rohirricRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle5, 6, 2), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.pillar, 1, 8)));
        rohirricRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.rohanBow), " XY", "X Y", " XY", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), Items.string));
        rohirricRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.helmetRohanMarshal), " X ", "YAY", " X ", Character.valueOf('X'), "nuggetGold", Character.valueOf('Y'), Items.leather, Character.valueOf('A'), new ItemStack(LOTRMod.helmetRohan, 1, 0)));
        rohirricRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.bodyRohanMarshal), " X ", "YAY", " X ", Character.valueOf('X'), "nuggetGold", Character.valueOf('Y'), Items.leather, Character.valueOf('A'), new ItemStack(LOTRMod.bodyRohan, 1, 0)));
        rohirricRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.legsRohanMarshal), " X ", "YAY", " X ", Character.valueOf('X'), "nuggetGold", Character.valueOf('Y'), Items.leather, Character.valueOf('A'), new ItemStack(LOTRMod.legsRohan, 1, 0)));
        rohirricRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.bootsRohanMarshal), " X ", "YAY", " X ", Character.valueOf('X'), "nuggetGold", Character.valueOf('Y'), Items.leather, Character.valueOf('A'), new ItemStack(LOTRMod.bootsRohan, 1, 0)));
        rohirricRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.stairsRohanRock, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.rock, 1, 2)));
        rohirricRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.lanceRohan), "  X", " X ", "Y  ", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
        rohirricRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.brick5, 1, 3), "XX", "XX", Character.valueOf('X'), new ItemStack(LOTRMod.brick, 1, 4)));
        rohirricRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.woodBeamS, 3, 0), "X", "X", "X", Character.valueOf('X'), "logWood"));
        rohirricRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.woodBeamS, 1, 1), " X ", "XYX", " X ", Character.valueOf('X'), "nuggetGold", Character.valueOf('Y'), new ItemStack(LOTRMod.woodBeamS, 1, 0)));
        rohirricRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.gateRohan, 4), "ZYZ", "YXY", "ZYZ", Character.valueOf('X'), LOTRMod.gateGear, Character.valueOf('Y'), "plankWood", Character.valueOf('Z'), "ingotIron"));
    }

    private static void createDunlendingRecipes() {
        dunlendingRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.dunlendingTable), "XX", "YY", Character.valueOf('X'), "plankWood", Character.valueOf('Y'), Blocks.cobblestone));
        dunlendingRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.helmetDunlending), "XXX", "Y Y", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), Items.leather));
        dunlendingRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.bodyDunlending), "X X", "YYY", "XXX", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), Items.leather));
        dunlendingRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.legsDunlending), "XXX", "Y Y", "X X", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), Items.leather));
        dunlendingRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.bootsDunlending), "Y Y", "X X", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), Items.leather));
        dunlendingRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.dunlendingClub), "X", "X", "X", Character.valueOf('X'), "plankWood"));
        dunlendingRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.dunlendingTrident), " XX", " YX", "Y  ", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
        dunlendingRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.banner, 1, LOTRItemBanner.BannerType.DUNLAND.bannerID), "X", "Y", "Z", Character.valueOf('X'), Blocks.wool, Character.valueOf('Y'), "stickWood", Character.valueOf('Z'), "plankWood"));
    }

    private static void createAngmarRecipes() {
        angmarRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.brick2, 4, 0), "XX", "XX", Character.valueOf('X'), Blocks.stone));
        angmarRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.angmarTable), "XX", "YY", Character.valueOf('X'), "plankWood", Character.valueOf('Y'), new ItemStack(LOTRMod.brick2, 1, 0)));
        angmarRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.swordAngmar), "X", "X", "Y", Character.valueOf('X'), LOTRMod.orcSteel, Character.valueOf('Y'), "stickWood"));
        angmarRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.battleaxeAngmar), "XXX", "XYX", " Y ", Character.valueOf('X'), LOTRMod.orcSteel, Character.valueOf('Y'), "stickWood"));
        angmarRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.daggerAngmar), "X", "Y", Character.valueOf('X'), LOTRMod.orcSteel, Character.valueOf('Y'), "stickWood"));
        angmarRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.helmetAngmar), "XXX", "X X", Character.valueOf('X'), LOTRMod.orcSteel));
        angmarRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.bodyAngmar), "X X", "XXX", "XXX", Character.valueOf('X'), LOTRMod.orcSteel));
        angmarRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.legsAngmar), "XXX", "X X", "X X", Character.valueOf('X'), LOTRMod.orcSteel));
        angmarRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.bootsAngmar), "X X", "X X", Character.valueOf('X'), LOTRMod.orcSteel));
        angmarRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle3, 6, 3), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick2, 1, 0)));
        angmarRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.stairsAngmarBrick, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick2, 1, 0)));
        angmarRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.wall2, 6, 0), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick2, 1, 0)));
        angmarRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.orcTorchItem, 2), "X", "Y", "Y", Character.valueOf('X'), new ItemStack(Items.coal, 1, 32767), Character.valueOf('Y'), "stickWood"));
        angmarRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.spearAngmar), "  X", " Y ", "Y  ", Character.valueOf('X'), LOTRMod.orcSteel, Character.valueOf('Y'), "stickWood"));
        angmarRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.shovelAngmar), "X", "Y", "Y", Character.valueOf('X'), LOTRMod.orcSteel, Character.valueOf('Y'), "stickWood"));
        angmarRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.pickaxeAngmar), "XXX", " Y ", " Y ", Character.valueOf('X'), LOTRMod.orcSteel, Character.valueOf('Y'), "stickWood"));
        angmarRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.axeAngmar), "XX", "XY", " Y", Character.valueOf('X'), LOTRMod.orcSteel, Character.valueOf('Y'), "stickWood"));
        angmarRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.hoeAngmar), "XX", " Y", " Y", Character.valueOf('X'), LOTRMod.orcSteel, Character.valueOf('Y'), "stickWood"));
        angmarRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.hammerAngmar), "XYX", "XYX", " Y ", Character.valueOf('X'), LOTRMod.orcSteel, Character.valueOf('Y'), "stickWood"));
        angmarRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle3, 6, 4), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick2, 1, 1)));
        angmarRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.stairsAngmarBrickCracked, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick2, 1, 1)));
        angmarRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.wall2, 6, 1), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick2, 1, 1)));
        angmarRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.orcForge), "XXX", "X X", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick2, 1, 0)));
        angmarRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.banner, 1, LOTRItemBanner.BannerType.ANGMAR.bannerID), "X", "Y", "Z", Character.valueOf('X'), Blocks.wool, Character.valueOf('Y'), "stickWood", Character.valueOf('Z'), "plankWood"));
        angmarRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.wargArmorAngmar), "X  ", "XYX", "XXX", Character.valueOf('X'), LOTRMod.orcSteel, Character.valueOf('Y'), Items.leather));
        angmarRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.gateOrc, 4), "YYY", "YXY", "YYY", Character.valueOf('X'), LOTRMod.gateGear, Character.valueOf('Y'), LOTRMod.orcSteel));
        angmarRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.pillar2, 3, 4), "X", "X", "X", Character.valueOf('X'), Blocks.stone));
        angmarRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle9, 6, 4), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.pillar2, 1, 4)));
        angmarRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.polearmAngmar), " XX", " YX", "Y  ", Character.valueOf('X'), LOTRMod.orcSteel, Character.valueOf('Y'), "stickWood"));
        angmarRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.banner, 1, LOTRItemBanner.BannerType.RHUDAUR.bannerID), "XA", "Y ", "Z ", Character.valueOf('X'), Blocks.wool, Character.valueOf('Y'), "stickWood", Character.valueOf('Z'), "plankWood", Character.valueOf('A'), "stickWood"));
        angmarRecipes.add(new ShapelessOreRecipe(new ItemStack(LOTRMod.brick6, 1, 10), new ItemStack(LOTRMod.brick2, 4, 0), Items.snowball));
        angmarRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle12, 6, 7), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick6, 1, 10)));
        angmarRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.stairsAngmarBrickSnow, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick6, 1, 10)));
        angmarRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.wall5, 6, 2), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick6, 1, 10)));
    }

    private static void createNearHaradRecipes() {
        nearHaradRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.nearHaradTable), "XX", "YY", Character.valueOf('X'), "plankWood", Character.valueOf('Y'), new ItemStack(Blocks.sandstone, 1, 0)));
        nearHaradRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.banner, 1, LOTRItemBanner.BannerType.NEAR_HARAD.bannerID), "X", "Y", "Z", Character.valueOf('X'), Blocks.wool, Character.valueOf('Y'), "stickWood", Character.valueOf('Z'), "plankWood"));
        nearHaradRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.banner, 1, LOTRItemBanner.BannerType.HARAD_NOMAD.bannerID), "XA", "Y ", "Z ", Character.valueOf('X'), Blocks.wool, Character.valueOf('Y'), "stickWood", Character.valueOf('Z'), "plankWood", Character.valueOf('A'), Blocks.sand));
        nearHaradRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.helmetNearHarad), "XXX", "X X", Character.valueOf('X'), "ingotBronze"));
        nearHaradRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.bodyNearHarad), "X X", "XXX", "XXX", Character.valueOf('X'), "ingotBronze"));
        nearHaradRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.legsNearHarad), "XXX", "X X", "X X", Character.valueOf('X'), "ingotBronze"));
        nearHaradRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.bootsNearHarad), "X X", "X X", Character.valueOf('X'), "ingotBronze"));
        nearHaradRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.helmetNearHaradWarlord), "XYX", " Z ", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), Items.leather, Character.valueOf('Z'), new ItemStack(LOTRMod.helmetNearHarad, 1, 0)));
        nearHaradRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.horseArmorNearHarad), "X  ", "XYX", "XXX", Character.valueOf('X'), "ingotBronze", Character.valueOf('Y'), Items.leather));
        nearHaradRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.helmetHarnedor), "XXX", "Y Y", Character.valueOf('X'), "ingotBronze", Character.valueOf('Y'), Items.leather));
        nearHaradRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.bodyHarnedor), "X X", "YYY", "XXX", Character.valueOf('X'), "ingotBronze", Character.valueOf('Y'), Items.leather));
        nearHaradRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.legsHarnedor), "XXX", "Y Y", "X X", Character.valueOf('X'), "ingotBronze", Character.valueOf('Y'), Items.leather));
        nearHaradRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.bootsHarnedor), "Y Y", "X X", Character.valueOf('X'), "ingotBronze", Character.valueOf('Y'), Items.leather));
    }

    private static void createHighElvenRecipes() {
        highElvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.highElvenTable), "XX", "XX", Character.valueOf('X'), "plankWood"));
        highElvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.shovelHighElven), "X", "Y", "Y", Character.valueOf('X'), LOTRMod.elfSteel, Character.valueOf('Y'), "stickWood"));
        highElvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.pickaxeHighElven), "XXX", " Y ", " Y ", Character.valueOf('X'), LOTRMod.elfSteel, Character.valueOf('Y'), "stickWood"));
        highElvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.axeHighElven), "XX", "XY", " Y", Character.valueOf('X'), LOTRMod.elfSteel, Character.valueOf('Y'), "stickWood"));
        highElvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.hoeHighElven), "XX", " Y", " Y", Character.valueOf('X'), LOTRMod.elfSteel, Character.valueOf('Y'), "stickWood"));
        highElvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.swordHighElven), "X", "X", "Y", Character.valueOf('X'), LOTRMod.elfSteel, Character.valueOf('Y'), "stickWood"));
        highElvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.daggerHighElven), "X", "Y", Character.valueOf('X'), LOTRMod.elfSteel, Character.valueOf('Y'), "stickWood"));
        highElvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.spearHighElven), "  X", " Y ", "Y  ", Character.valueOf('X'), LOTRMod.elfSteel, Character.valueOf('Y'), "stickWood"));
        highElvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.helmetHighElven), "XXX", "X X", Character.valueOf('X'), LOTRMod.elfSteel));
        highElvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.bodyHighElven), "X X", "XXX", "XXX", Character.valueOf('X'), LOTRMod.elfSteel));
        highElvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.legsHighElven), "XXX", "X X", "X X", Character.valueOf('X'), LOTRMod.elfSteel));
        highElvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.bootsHighElven), "X X", "X X", Character.valueOf('X'), LOTRMod.elfSteel));
        highElvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.banner, 1, LOTRItemBanner.BannerType.HIGH_ELF.bannerID), "X", "Y", "Z", Character.valueOf('X'), Blocks.wool, Character.valueOf('Y'), "stickWood", Character.valueOf('Z'), "plankWood"));
        highElvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.horseArmorHighElven), "X  ", "XYX", "XXX", Character.valueOf('X'), LOTRMod.elfSteel, Character.valueOf('Y'), Items.leather));
        highElvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.polearmHighElven), "  X", " Y ", "X  ", Character.valueOf('X'), LOTRMod.elfSteel, Character.valueOf('Y'), "stickWood"));
        highElvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.longspearHighElven), "  X", " YX", "Y  ", Character.valueOf('X'), LOTRMod.elfSteel, Character.valueOf('Y'), "stickWood"));
        highElvenRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.highElvenBow), " XY", "X Y", " XY", Character.valueOf('X'), LOTRMod.elfSteel, Character.valueOf('Y'), Items.string));
    }

    private static void createBlueMountainsRecipes() {
        blueMountainsRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.blueDwarvenTable), "XX", "YY", Character.valueOf('X'), "plankWood", Character.valueOf('Y'), new ItemStack(LOTRMod.brick, 1, 14)));
        blueMountainsRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.shovelBlueDwarven), "X", "Y", "Y", Character.valueOf('X'), LOTRMod.blueDwarfSteel, Character.valueOf('Y'), "stickWood"));
        blueMountainsRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.pickaxeBlueDwarven), "XXX", " Y ", " Y ", Character.valueOf('X'), LOTRMod.blueDwarfSteel, Character.valueOf('Y'), "stickWood"));
        blueMountainsRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.axeBlueDwarven), "XX", "XY", " Y", Character.valueOf('X'), LOTRMod.blueDwarfSteel, Character.valueOf('Y'), "stickWood"));
        blueMountainsRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.swordBlueDwarven), "X", "X", "Y", Character.valueOf('X'), LOTRMod.blueDwarfSteel, Character.valueOf('Y'), "stickWood"));
        blueMountainsRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.hoeBlueDwarven), "XX", " Y", " Y", Character.valueOf('X'), LOTRMod.blueDwarfSteel, Character.valueOf('Y'), "stickWood"));
        blueMountainsRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.daggerBlueDwarven), "X", "Y", Character.valueOf('X'), LOTRMod.blueDwarfSteel, Character.valueOf('Y'), "stickWood"));
        blueMountainsRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.battleaxeBlueDwarven), "XXX", "XYX", " Y ", Character.valueOf('X'), LOTRMod.blueDwarfSteel, Character.valueOf('Y'), "stickWood"));
        blueMountainsRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.hammerBlueDwarven), "XYX", "XYX", " Y ", Character.valueOf('X'), LOTRMod.blueDwarfSteel, Character.valueOf('Y'), "stickWood"));
        blueMountainsRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.helmetBlueDwarven), "XXX", "X X", Character.valueOf('X'), LOTRMod.blueDwarfSteel));
        blueMountainsRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.bodyBlueDwarven), "X X", "XXX", "XXX", Character.valueOf('X'), LOTRMod.blueDwarfSteel));
        blueMountainsRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.legsBlueDwarven), "XXX", "X X", "X X", Character.valueOf('X'), LOTRMod.blueDwarfSteel));
        blueMountainsRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.bootsBlueDwarven), "X X", "X X", Character.valueOf('X'), LOTRMod.blueDwarfSteel));
        blueMountainsRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.throwingAxeBlueDwarven), " X ", " YX", "Y  ", Character.valueOf('X'), LOTRMod.blueDwarfSteel, Character.valueOf('Y'), "stickWood"));
        blueMountainsRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.chandelier, 2, 11), " X ", "YZY", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), Blocks.torch, Character.valueOf('Z'), LOTRMod.blueDwarfSteel));
        blueMountainsRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.mattockBlueDwarven), "XXX", "XY ", " Y ", Character.valueOf('X'), LOTRMod.blueDwarfSteel, Character.valueOf('Y'), "stickWood"));
        blueMountainsRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.banner, 1, LOTRItemBanner.BannerType.BLUE_MOUNTAINS.bannerID), "X", "Y", "Z", Character.valueOf('X'), Blocks.wool, Character.valueOf('Y'), "stickWood", Character.valueOf('Z'), "plankWood"));
        blueMountainsRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.spearBlueDwarven), "  X", " Y ", "Y  ", Character.valueOf('X'), LOTRMod.blueDwarfSteel, Character.valueOf('Y'), "stickWood"));
        blueMountainsRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.blueDwarfBars, 16), "XXX", "XXX", Character.valueOf('X'), LOTRMod.blueDwarfSteel));
        blueMountainsRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.boarArmorBlueDwarven), "X  ", "XYX", "XXX", Character.valueOf('X'), LOTRMod.blueDwarfSteel, Character.valueOf('Y'), Items.leather));
        blueMountainsRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.pikeBlueDwarven), "  X", " YX", "Y  ", Character.valueOf('X'), LOTRMod.blueDwarfSteel, Character.valueOf('Y'), "stickWood"));
        blueMountainsRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.gateDwarven, 4), "ZYZ", "YXY", "ZYZ", Character.valueOf('X'), LOTRMod.gateGear, Character.valueOf('Y'), new ItemStack(LOTRMod.brick, 1, 6), Character.valueOf('Z'), LOTRMod.blueDwarfSteel));
        blueMountainsRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.mechanism), " X ", "YZY", " X ", Character.valueOf('X'), "ingotCopper", Character.valueOf('Y'), Items.flint, Character.valueOf('Z'), LOTRMod.blueDwarfSteel));
    }

    private static void createRangerRecipes() {
        rangerRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.rangerTable), "XX", "XX", Character.valueOf('X'), "plankWood"));
        rangerRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.banner, 1, LOTRItemBanner.BannerType.RANGER_NORTH.bannerID), "X", "Y", "Z", Character.valueOf('X'), Blocks.wool, Character.valueOf('Y'), "stickWood", Character.valueOf('Z'), "plankWood"));
        rangerRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.brick2, 4, 3), "XX", "XX", Character.valueOf('X'), Blocks.stone));
        rangerRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle4, 6, 1), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick2, 1, 3)));
        rangerRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.stairsArnorBrick, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick2, 1, 3)));
        rangerRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.wall2, 6, 4), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick2, 1, 3)));
        rangerRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.brick2, 1, 6), "XX", "XX", Character.valueOf('X'), new ItemStack(LOTRMod.brick2, 1, 3)));
        rangerRecipes.add(new ShapelessOreRecipe(new ItemStack(LOTRMod.brick2, 1, 4), new ItemStack(LOTRMod.brick2, 1, 3), "vine"));
        rangerRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle4, 6, 2), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick2, 1, 4)));
        rangerRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.stairsArnorBrickMossy, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick2, 1, 4)));
        rangerRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.wall2, 6, 5), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick2, 1, 4)));
        rangerRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle4, 6, 3), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick2, 1, 5)));
        rangerRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.stairsArnorBrickCracked, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick2, 1, 5)));
        rangerRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.wall2, 6, 6), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick2, 1, 5)));
        rangerRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.helmetRanger), "XXX", "X X", Character.valueOf('X'), Items.leather));
        rangerRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.bodyRanger), "X X", "YYY", "XXX", Character.valueOf('X'), Items.leather, Character.valueOf('Y'), "ingotIron"));
        rangerRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.legsRanger), "XXX", "Y Y", "X X", Character.valueOf('X'), Items.leather, Character.valueOf('Y'), "ingotIron"));
        rangerRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.bootsRanger), "X X", "X X", Character.valueOf('X'), Items.leather));
        rangerRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.rangerBow), " XY", "X Y", " XY", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), Items.string));
        rangerRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.pillar2, 3, 13), "X", "X", "X", Character.valueOf('X'), Blocks.stone));
        rangerRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle13, 6, 7), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.pillar2, 1, 13)));
        rangerRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle14, 6, 0), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.pillar2, 1, 14)));
    }

    private static void createDolGuldurRecipes() {
        dolGuldurRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.brick2, 4, 8), "XX", "XX", Character.valueOf('X'), Blocks.stone));
        dolGuldurRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.dolGuldurTable), "XX", "YY", Character.valueOf('X'), "plankWood", Character.valueOf('Y'), new ItemStack(LOTRMod.brick2, 1, 8)));
        dolGuldurRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle4, 6, 5), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick2, 1, 8)));
        dolGuldurRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.stairsDolGuldurBrick, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick2, 1, 8)));
        dolGuldurRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.wall2, 6, 8), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick2, 1, 8)));
        dolGuldurRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.orcTorchItem, 2), "X", "Y", "Y", Character.valueOf('X'), new ItemStack(Items.coal, 1, 32767), Character.valueOf('Y'), "stickWood"));
        dolGuldurRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle4, 6, 6), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick2, 1, 9)));
        dolGuldurRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.stairsDolGuldurBrickCracked, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick2, 1, 9)));
        dolGuldurRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.wall2, 6, 9), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick2, 1, 9)));
        dolGuldurRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.orcForge), "XXX", "X X", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick2, 1, 8)));
        dolGuldurRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.banner, 1, LOTRItemBanner.BannerType.DOL_GULDUR.bannerID), "X", "Y", "Z", Character.valueOf('X'), Blocks.wool, Character.valueOf('Y'), "stickWood", Character.valueOf('Z'), "plankWood"));
        dolGuldurRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.swordDolGuldur), "X", "X", "Y", Character.valueOf('X'), LOTRMod.orcSteel, Character.valueOf('Y'), "stickWood"));
        dolGuldurRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.battleaxeDolGuldur), "XXX", "XYX", " Y ", Character.valueOf('X'), LOTRMod.orcSteel, Character.valueOf('Y'), "stickWood"));
        dolGuldurRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.daggerDolGuldur), "X", "Y", Character.valueOf('X'), LOTRMod.orcSteel, Character.valueOf('Y'), "stickWood"));
        dolGuldurRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.helmetDolGuldur), "XXX", "X X", Character.valueOf('X'), LOTRMod.orcSteel));
        dolGuldurRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.bodyDolGuldur), "X X", "XXX", "XXX", Character.valueOf('X'), LOTRMod.orcSteel));
        dolGuldurRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.legsDolGuldur), "XXX", "X X", "X X", Character.valueOf('X'), LOTRMod.orcSteel));
        dolGuldurRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.bootsDolGuldur), "X X", "X X", Character.valueOf('X'), LOTRMod.orcSteel));
        dolGuldurRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.spearDolGuldur), "  X", " Y ", "Y  ", Character.valueOf('X'), LOTRMod.orcSteel, Character.valueOf('Y'), "stickWood"));
        dolGuldurRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.shovelDolGuldur), "X", "Y", "Y", Character.valueOf('X'), LOTRMod.orcSteel, Character.valueOf('Y'), "stickWood"));
        dolGuldurRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.pickaxeDolGuldur), "XXX", " Y ", " Y ", Character.valueOf('X'), LOTRMod.orcSteel, Character.valueOf('Y'), "stickWood"));
        dolGuldurRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.axeDolGuldur), "XX", "XY", " Y", Character.valueOf('X'), LOTRMod.orcSteel, Character.valueOf('Y'), "stickWood"));
        dolGuldurRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.hoeDolGuldur), "XX", " Y", " Y", Character.valueOf('X'), LOTRMod.orcSteel, Character.valueOf('Y'), "stickWood"));
        dolGuldurRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.hammerDolGuldur), "XYX", "XYX", " Y ", Character.valueOf('X'), LOTRMod.orcSteel, Character.valueOf('Y'), "stickWood"));
        dolGuldurRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.chandelier, 2, 7), " X ", "YZY", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), LOTRMod.orcTorchItem, Character.valueOf('Z'), LOTRMod.orcSteel));
        dolGuldurRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.gateOrc, 4), "YYY", "YXY", "YYY", Character.valueOf('X'), LOTRMod.gateGear, Character.valueOf('Y'), LOTRMod.orcSteel));
        dolGuldurRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.pikeDolGuldur), "  X", " YX", "Y  ", Character.valueOf('X'), LOTRMod.orcSteel, Character.valueOf('Y'), "stickWood"));
        dolGuldurRecipes.add(new ShapelessOreRecipe(new ItemStack(LOTRMod.brick6, 1, 11), new ItemStack(LOTRMod.brick2, 1, 8), "vine"));
        dolGuldurRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.brick6, 1, 12), "XX", "XX", Character.valueOf('X'), new ItemStack(LOTRMod.brick2, 1, 8)));
        dolGuldurRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle14, 6, 1), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick6, 1, 11)));
        dolGuldurRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.stairsDolGuldurBrickMossy, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick6, 1, 11)));
        dolGuldurRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.wall5, 6, 3), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick6, 1, 11)));
        dolGuldurRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.pillar3, 3, 0), "X", "X", "X", Character.valueOf('X'), Blocks.stone));
        dolGuldurRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle14, 6, 4), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.pillar3, 1, 0)));
    }

    private static void createGundabadRecipes() {
        gundabadRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.gundabadTable), "XX", "YY", Character.valueOf('X'), "plankWood", Character.valueOf('Y'), Blocks.cobblestone));
        gundabadRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.orcTorchItem, 2), "X", "Y", "Y", Character.valueOf('X'), new ItemStack(Items.coal, 1, 32767), Character.valueOf('Y'), "stickWood"));
        gundabadRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.orcForge), "XXX", "X X", "XXX", Character.valueOf('X'), Blocks.cobblestone));
        gundabadRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.banner, 1, LOTRItemBanner.BannerType.GUNDABAD.bannerID), "X", "Y", "Z", Character.valueOf('X'), Blocks.wool, Character.valueOf('Y'), "stickWood", Character.valueOf('Z'), "plankWood"));
        gundabadRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.chandelier, 2, 7), " X ", "YZY", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), LOTRMod.orcTorchItem, Character.valueOf('Z'), LOTRMod.orcSteel));
        gundabadRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.helmetGundabadUruk), "XXX", "X X", Character.valueOf('X'), LOTRMod.urukSteel));
        gundabadRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.bodyGundabadUruk), "X X", "XXX", "XXX", Character.valueOf('X'), LOTRMod.urukSteel));
        gundabadRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.legsGundabadUruk), "XXX", "X X", "X X", Character.valueOf('X'), LOTRMod.urukSteel));
        gundabadRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.bootsGundabadUruk), "X X", "X X", Character.valueOf('X'), LOTRMod.urukSteel));
        gundabadRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.swordGundabadUruk), "X", "X", "Y", Character.valueOf('X'), LOTRMod.urukSteel, Character.valueOf('Y'), "bone"));
        gundabadRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.battleaxeGundabadUruk), "XXX", "XYX", " Y ", Character.valueOf('X'), LOTRMod.urukSteel, Character.valueOf('Y'), "bone"));
        gundabadRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.hammerGundabadUruk), "XYX", "XYX", " Y ", Character.valueOf('X'), LOTRMod.urukSteel, Character.valueOf('Y'), "bone"));
        gundabadRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.gateOrc, 4), "YYY", "YXY", "YYY", Character.valueOf('X'), LOTRMod.gateGear, Character.valueOf('Y'), LOTRMod.orcSteel));
        gundabadRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.daggerGundabadUruk), "X", "Y", Character.valueOf('X'), LOTRMod.urukSteel, Character.valueOf('Y'), "bone"));
        gundabadRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.spearGundabadUruk), "  X", " Y ", "Y  ", Character.valueOf('X'), LOTRMod.urukSteel, Character.valueOf('Y'), "bone"));
        gundabadRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.pikeGundabadUruk), "  X", " YX", "Y  ", Character.valueOf('X'), LOTRMod.urukSteel, Character.valueOf('Y'), "bone"));
        gundabadRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.gundabadUrukBow), " XY", "X Y", " XY", Character.valueOf('X'), LOTRMod.urukSteel, Character.valueOf('Y'), Items.string));
        gundabadRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.mechanism), " X ", "YZY", " X ", Character.valueOf('X'), "ingotCopper", Character.valueOf('Y'), Items.flint, Character.valueOf('Z'), LOTRMod.orcSteel));
    }

    private static void createHalfTrollRecipes() {
        halfTrollRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.halfTrollTable), "XX", "YY", Character.valueOf('X'), "plankWood", Character.valueOf('Y'), Blocks.cobblestone));
        halfTrollRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.banner, 1, LOTRItemBanner.BannerType.HALF_TROLL.bannerID), "X", "Y", "Z", Character.valueOf('X'), Blocks.wool, Character.valueOf('Y'), "stickWood", Character.valueOf('Z'), "plankWood"));
        halfTrollRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.helmetHalfTroll), "XXX", "Y Y", Character.valueOf('X'), LOTRMod.gemsbokHide, Character.valueOf('Y'), Items.string));
        halfTrollRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.bodyHalfTroll), "X X", "XYX", "XYX", Character.valueOf('X'), LOTRMod.gemsbokHide, Character.valueOf('Y'), Items.string));
        halfTrollRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.legsHalfTroll), "XXX", "Y Y", "X X", Character.valueOf('X'), LOTRMod.gemsbokHide, Character.valueOf('Y'), Items.string));
        halfTrollRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.bootsHalfTroll), "Y Y", "X X", Character.valueOf('X'), LOTRMod.gemsbokHide, Character.valueOf('Y'), Items.string));
        halfTrollRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.scimitarHalfTroll), "X", "X", "Y", Character.valueOf('X'), Items.flint, Character.valueOf('Y'), "stickWood"));
        halfTrollRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.battleaxeHalfTroll), "XXX", "XYX", " Y ", Character.valueOf('X'), Items.flint, Character.valueOf('Y'), "stickWood"));
        halfTrollRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.daggerHalfTroll), "X", "Y", Character.valueOf('X'), Items.flint, Character.valueOf('Y'), "stickWood"));
        halfTrollRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.hammerHalfTroll), "XYX", "XYX", " Y ", Character.valueOf('X'), Items.flint, Character.valueOf('Y'), "stickWood"));
        halfTrollRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.maceHalfTroll), " XX", " XX", "Y  ", Character.valueOf('X'), Items.flint, Character.valueOf('Y'), "stickWood"));
        halfTrollRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.rhinoArmorHalfTroll), "X  ", "XYX", "XXX", Character.valueOf('X'), LOTRMod.gemsbokHide, Character.valueOf('Y'), Items.string));
        halfTrollRecipes.add(new ShapelessOreRecipe(new ItemStack(LOTRMod.torogStew), Items.bowl, Items.rotten_flesh, "bone", Blocks.dirt));
        halfTrollRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.pikeHalfTroll), "  X", " YX", "Y  ", Character.valueOf('X'), Items.flint, Character.valueOf('Y'), "stickWood"));
    }

    private static void createDolAmrothRecipes() {
        dolAmrothRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.dolAmrothTable), "XX", "YY", Character.valueOf('X'), "plankWood", Character.valueOf('Y'), new ItemStack(LOTRMod.rock, 1, 1)));
        dolAmrothRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.banner, 1, LOTRItemBanner.BannerType.DOL_AMROTH.bannerID), "X", "Y", "Z", Character.valueOf('X'), Blocks.wool, Character.valueOf('Y'), "stickWood", Character.valueOf('Z'), "plankWood"));
        dolAmrothRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.helmetDolAmroth), "Y Y", "XXX", "X X", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), LOTRMod.swanFeather));
        dolAmrothRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.bodyDolAmroth), "YXY", "XXX", "XXX", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), LOTRMod.swanFeather));
        dolAmrothRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.legsDolAmroth), "XXX", "X X", "X X", Character.valueOf('X'), "ingotIron"));
        dolAmrothRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.bootsDolAmroth), "X X", "X X", Character.valueOf('X'), "ingotIron"));
        dolAmrothRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.swordDolAmroth), "X", "X", "Y", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
        dolAmrothRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.horseArmorDolAmroth), "X  ", "XYX", "XXX", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), Items.leather));
        dolAmrothRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.brick3, 4, 9), "XX", "XX", Character.valueOf('X'), new ItemStack(LOTRMod.rock, 1, 1)));
        dolAmrothRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle6, 6, 7), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick3, 4, 9)));
        dolAmrothRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.stairsDolAmrothBrick, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick3, 4, 9)));
        dolAmrothRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.wall2, 6, 14), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick3, 4, 9)));
        dolAmrothRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.lanceDolAmroth), "  X", " X ", "Y  ", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
        dolAmrothRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.daggerDolAmroth), "X", "Y", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
        dolAmrothRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.gateDolAmroth, 4), "ZYZ", "YXY", "ZYZ", Character.valueOf('X'), LOTRMod.gateGear, Character.valueOf('Y'), "plankWood", Character.valueOf('Z'), "ingotIron"));
        dolAmrothRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.longspearDolAmroth), "  X", " YX", "Y  ", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
        dolAmrothRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.bodyDolAmrothGambeson), "X X", "XXX", "XXX", Character.valueOf('X'), Blocks.wool));
        dolAmrothRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.legsDolAmrothGambeson), "XXX", "X X", "X X", Character.valueOf('X'), Blocks.wool));
        dolAmrothRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.gondorBow), " XY", "X Y", " XY", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), Items.string));
        dolAmrothRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.brick4, 1, 6), "XX", "XX", Character.valueOf('X'), new ItemStack(LOTRMod.brick2, 1, 11)));
    }

    private static void createMoredainRecipes() {
        moredainRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.moredainTable), "XX", "YY", Character.valueOf('X'), "plankWood", Character.valueOf('Y'), LOTRMod.redClay));
        moredainRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.banner, 1, LOTRItemBanner.BannerType.MOREDAIN.bannerID), "X", "Y", "Z", Character.valueOf('X'), Blocks.wool, Character.valueOf('Y'), "stickWood", Character.valueOf('Z'), "plankWood"));
        moredainRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.helmetMoredain), "XXX", "X X", Character.valueOf('X'), LOTRMod.gemsbokHide));
        moredainRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.bodyMoredain), "X X", "XXX", "XXX", Character.valueOf('X'), LOTRMod.gemsbokHide));
        moredainRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.legsMoredain), "XXX", "X X", "X X", Character.valueOf('X'), LOTRMod.gemsbokHide));
        moredainRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.bootsMoredain), "X X", "X X", Character.valueOf('X'), LOTRMod.gemsbokHide));
        moredainRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.brick3, 4, 10), "XX", "XX", Character.valueOf('X'), LOTRMod.redClay));
        moredainRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle7, 6, 0), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick3, 4, 10)));
        moredainRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.stairsMoredainBrick, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick3, 4, 10)));
        moredainRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.wall2, 6, 15), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick3, 4, 10)));
        moredainRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.battleaxeMoredain), "XXX", "XYX", " Y ", Character.valueOf('X'), LOTRMod.rhinoHorn, Character.valueOf('Y'), "stickWood"));
        moredainRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.daggerMoredain), "X", "Y", Character.valueOf('X'), LOTRMod.rhinoHorn, Character.valueOf('Y'), "stickWood"));
        moredainRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.spearMoredain), "  X", " X ", "X  ", Character.valueOf('X'), LOTRMod.gemsbokHorn));
        moredainRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.helmetMoredainLion), "XXX", "X X", Character.valueOf('X'), LOTRMod.lionFur));
        moredainRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.bodyMoredainLion), "X X", "XXX", "XXX", Character.valueOf('X'), LOTRMod.lionFur));
        moredainRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.legsMoredainLion), "XXX", "X X", "X X", Character.valueOf('X'), LOTRMod.lionFur));
        moredainRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.bootsMoredainLion), "X X", "X X", Character.valueOf('X'), LOTRMod.lionFur));
        moredainRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.clubMoredain), "X", "X", "X", Character.valueOf('X'), "plankWood"));
        moredainRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.swordMoredain), "X", "X", "Y", Character.valueOf('X'), "ingotBronze", Character.valueOf('Y'), "stickWood"));
        moredainRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle14, 6, 2), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick6, 1, 13)));
        moredainRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.stairsMorwaithBrickCracked, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick6, 4, 13)));
        moredainRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.wall5, 6, 4), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick6, 1, 13)));
    }

    private static void createTauredainRecipes() {
        tauredainRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.tauredainTable), "XX", "YY", Character.valueOf('X'), "plankWood", Character.valueOf('Y'), new ItemStack(LOTRMod.brick4, 1, 0)));
        tauredainRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.banner, 1, LOTRItemBanner.BannerType.TAUREDAIN.bannerID), "X", "Y", "Z", Character.valueOf('X'), Blocks.wool, Character.valueOf('Y'), "stickWood", Character.valueOf('Z'), "plankWood"));
        tauredainRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.brick4, 4, 0), "XX", "XX", Character.valueOf('X'), Blocks.stone));
        tauredainRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle8, 6, 0), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick4, 4, 0)));
        tauredainRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.stairsTauredainBrick, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick4, 4, 0)));
        tauredainRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.wall4, 6, 0), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick4, 4, 0)));
        tauredainRecipes.add(new ShapelessOreRecipe(new ItemStack(LOTRMod.brick4, 1, 1), new ItemStack(LOTRMod.brick4, 1, 0), "vine"));
        tauredainRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle8, 6, 1), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick4, 4, 1)));
        tauredainRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.stairsTauredainBrickMossy, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick4, 4, 1)));
        tauredainRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.wall4, 6, 1), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick4, 4, 1)));
        tauredainRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle8, 6, 2), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick4, 4, 2)));
        tauredainRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.stairsTauredainBrickCracked, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick4, 4, 2)));
        tauredainRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.wall4, 6, 2), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick4, 4, 2)));
        tauredainRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.brick4, 4, 3), "XX", "XX", Character.valueOf('X'), "ingotGold"));
        tauredainRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle8, 6, 3), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick4, 4, 3)));
        tauredainRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.stairsTauredainBrickGold, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick4, 4, 3)));
        tauredainRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.wall4, 6, 3), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick4, 4, 3)));
        tauredainRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.brick4, 4, 4), "XX", "XX", Character.valueOf('X'), LOTRMod.obsidianShard));
        tauredainRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle8, 6, 4), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick4, 4, 4)));
        tauredainRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.stairsTauredainBrickObsidian, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick4, 4, 4)));
        tauredainRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.wall4, 6, 4), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick4, 4, 4)));
        tauredainRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.shovelTauredain), "X", "Y", "Y", Character.valueOf('X'), LOTRMod.obsidianShard, Character.valueOf('Y'), "stickWood"));
        tauredainRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.pickaxeTauredain), "XXX", " Y ", " Y ", Character.valueOf('X'), LOTRMod.obsidianShard, Character.valueOf('Y'), "stickWood"));
        tauredainRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.axeTauredain), "XX", "XY", " Y", Character.valueOf('X'), LOTRMod.obsidianShard, Character.valueOf('Y'), "stickWood"));
        tauredainRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.hoeTauredain), "XX", " Y", " Y", Character.valueOf('X'), LOTRMod.obsidianShard, Character.valueOf('Y'), "stickWood"));
        tauredainRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.daggerTauredain), "X", "Y", Character.valueOf('X'), LOTRMod.obsidianShard, Character.valueOf('Y'), "stickWood"));
        tauredainRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.spearTauredain), "  X", " Y ", "Y  ", Character.valueOf('X'), LOTRMod.obsidianShard, Character.valueOf('Y'), "stickWood"));
        tauredainRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.swordTauredain), "XZX", "XZX", " Y ", Character.valueOf('X'), LOTRMod.obsidianShard, Character.valueOf('Y'), "stickWood", Character.valueOf('Z'), "plankWood"));
        tauredainRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.helmetTauredain), "XXX", "X X", Character.valueOf('X'), "ingotBronze"));
        tauredainRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.bodyTauredain), "X X", "XXX", "XXX", Character.valueOf('X'), "ingotBronze"));
        tauredainRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.legsTauredain), "XXX", "X X", "X X", Character.valueOf('X'), "ingotBronze"));
        tauredainRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.bootsTauredain), "X X", "X X", Character.valueOf('X'), "ingotBronze"));
        tauredainRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.helmetTauredainChieftain), "X", "Y", Character.valueOf('X'), new ItemStack(LOTRMod.doubleFlower, 1, 3), Character.valueOf('Y'), new ItemStack(LOTRMod.helmetTauredain, 1, 0)));
        tauredainRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.tauredainBlowgun), "XYY", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), LOTRMod.reeds));
        tauredainRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.tauredainDart, 4), "X", "Y", "Z", Character.valueOf('X'), LOTRMod.obsidianShard, Character.valueOf('Y'), "stickWood", Character.valueOf('Z'), "feather"));
        tauredainRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.tauredainDartPoisoned, 4), " X ", "XYX", " X ", Character.valueOf('X'), LOTRMod.tauredainDart, Character.valueOf('Y'), "poison"));
        tauredainRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.tauredainDartTrap), "XXX", "XYX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick4, 1, 0), Character.valueOf('Y'), new ItemStack(LOTRMod.tauredainBlowgun, 1, 0)));
        tauredainRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.pillar, 3, 14), "X", "X", "X", Character.valueOf('X'), Blocks.stone));
        tauredainRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle8, 6, 5), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.pillar, 1, 14)));
        tauredainRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.tauredainDoubleTorchItem, 2), "X", "Y", "Y", Character.valueOf('X'), new ItemStack(Items.coal, 1, 32767), Character.valueOf('Y'), "stickWood"));
        tauredainRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.tauredainDartTrapGold), "XXX", "XYX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick4, 1, 3), Character.valueOf('Y'), new ItemStack(LOTRMod.tauredainBlowgun, 1, 0)));
        tauredainRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.gateTauredain, 4), "ZYZ", "YXY", "ZYZ", Character.valueOf('X'), LOTRMod.gateGear, Character.valueOf('Y'), "plankWood", Character.valueOf('Z'), "ingotGold"));
        tauredainRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.hammerTauredain), "XYX", "XYX", " Y ", Character.valueOf('X'), LOTRMod.obsidianShard, Character.valueOf('Y'), "stickWood"));
        tauredainRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.battleaxeTauredain), "XXX", "XYX", " Y ", Character.valueOf('X'), LOTRMod.obsidianShard, Character.valueOf('Y'), "stickWood"));
        tauredainRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.pikeTauredain), "  X", " YX", "Y  ", Character.valueOf('X'), LOTRMod.obsidianShard, Character.valueOf('Y'), "stickWood"));
        tauredainRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.tauredainDartTrapObsidian), "XXX", "XYX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick4, 1, 4), Character.valueOf('Y'), new ItemStack(LOTRMod.tauredainBlowgun, 1, 0)));
        tauredainRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.pillar2, 3, 11), "X", "X", "X", Character.valueOf('X'), "ingotGold"));
        tauredainRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.pillar2, 3, 12), "X", "X", "X", Character.valueOf('X'), LOTRMod.obsidianShard));
        tauredainRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle13, 6, 5), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.pillar2, 1, 11)));
        tauredainRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle13, 6, 6), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.pillar2, 1, 12)));
    }

    private static void createDaleRecipes() {
        daleRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.daleTable), "XX", "XX", Character.valueOf('X'), "plankWood"));
        daleRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.banner, 1, LOTRItemBanner.BannerType.DALE.bannerID), "X", "Y", "Z", Character.valueOf('X'), Blocks.wool, Character.valueOf('Y'), "stickWood", Character.valueOf('Z'), "plankWood"));
        daleRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.brick5, 4, 1), "XX", "XX", Character.valueOf('X'), Blocks.stone));
        daleRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle9, 6, 6), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick5, 1, 1)));
        daleRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.stairsDaleBrick, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick5, 1, 1)));
        daleRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.wall3, 6, 9), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick5, 1, 1)));
        daleRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.dalishPastryItem), "ABA", "CDC", "EEE", Character.valueOf('A'), LOTRMod.mapleSyrup, Character.valueOf('B'), Items.milk_bucket, Character.valueOf('C'), LOTRMod.raisins, Character.valueOf('D'), Items.egg, Character.valueOf('E'), Items.wheat));
        daleRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.cram), "XYX", Character.valueOf('X'), Items.wheat, Character.valueOf('Y'), LOTRMod.salt));
        daleRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.swordDale), "X", "X", "Y", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
        daleRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.daggerDale), "X", "Y", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
        daleRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.spearDale), "  X", " Y ", "Y  ", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
        daleRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.battleaxeDale), "XXX", "XYX", " Y ", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
        daleRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.helmetDale), "XXX", "X X", Character.valueOf('X'), "ingotIron"));
        daleRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.bodyDale), "X X", "XXX", "XXX", Character.valueOf('X'), "ingotIron"));
        daleRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.legsDale), "XXX", "X X", "X X", Character.valueOf('X'), "ingotIron"));
        daleRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.bootsDale), "X X", "X X", Character.valueOf('X'), "ingotIron"));
        daleRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.daleBow), " XY", "X Y", " XY", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), Items.string));
        daleRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.pillar2, 3, 5), "X", "X", "X", Character.valueOf('X'), Blocks.stone));
        daleRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle10, 6, 0), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.pillar2, 1, 5)));
        daleRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.horseArmorDale), "X  ", "XYX", "XXX", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), Items.leather));
        daleRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.pikeDale), "  X", " YX", "Y  ", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
        daleRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.bodyDaleGambeson), "X X", "XXX", "XXX", Character.valueOf('X'), Blocks.wool));
        daleRecipes.add(new ShapedOreRecipe(LOTRItemDaleCracker.setEmpty(new ItemStack(LOTRMod.daleCracker, 1, 0), true), " Z ", "XYX", Character.valueOf('X'), Items.paper, Character.valueOf('Y'), Items.gunpowder, Character.valueOf('Z'), "dyeRed"));
        daleRecipes.add(new ShapedOreRecipe(LOTRItemDaleCracker.setEmpty(new ItemStack(LOTRMod.daleCracker, 1, 1), true), " Z ", "XYX", Character.valueOf('X'), Items.paper, Character.valueOf('Y'), Items.gunpowder, Character.valueOf('Z'), "dyeBlue"));
        daleRecipes.add(new ShapedOreRecipe(LOTRItemDaleCracker.setEmpty(new ItemStack(LOTRMod.daleCracker, 1, 2), true), " Z ", "XYX", Character.valueOf('X'), Items.paper, Character.valueOf('Y'), Items.gunpowder, Character.valueOf('Z'), "dyeGreen"));
        daleRecipes.add(new ShapedOreRecipe(LOTRItemDaleCracker.setEmpty(new ItemStack(LOTRMod.daleCracker, 1, 3), true), " Z ", "XYX", Character.valueOf('X'), Items.paper, Character.valueOf('Y'), Items.gunpowder, Character.valueOf('Z'), "dyeWhite"));
        daleRecipes.add(new ShapedOreRecipe(LOTRItemDaleCracker.setEmpty(new ItemStack(LOTRMod.daleCracker, 1, 4), true), " Z ", "XYX", Character.valueOf('X'), Items.paper, Character.valueOf('Y'), Items.gunpowder, Character.valueOf('Z'), "dyeYellow"));
        daleRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.banner, 1, LOTRItemBanner.BannerType.ESGAROTH.bannerID), "XA", "Y ", "Z ", Character.valueOf('X'), Blocks.wool, Character.valueOf('Y'), "stickWood", Character.valueOf('Z'), "plankWood", Character.valueOf('A'), Items.fish));
        daleRecipes.add(new ShapelessOreRecipe(new ItemStack(LOTRMod.brick6, 1, 3), new ItemStack(LOTRMod.brick5, 1, 1), "vine"));
        daleRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle13, 6, 0), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick6, 1, 3)));
        daleRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.stairsDaleBrickMossy, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick6, 1, 3)));
        daleRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.wall4, 6, 14), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick6, 1, 3)));
        daleRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle13, 6, 1), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick6, 1, 4)));
        daleRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.stairsDaleBrickCracked, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick6, 1, 4)));
        daleRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.wall4, 6, 15), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick6, 1, 4)));
        daleRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.brick6, 1, 5), "XX", "XX", Character.valueOf('X'), new ItemStack(LOTRMod.brick5, 1, 1)));
    }

    private static void createDorwinionRecipes() {
        dorwinionRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.dorwinionTable), "XX", "YY", Character.valueOf('X'), "plankWood", Character.valueOf('Y'), new ItemStack(LOTRMod.rock, 1, 5)));
        dorwinionRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.banner, 1, LOTRItemBanner.BannerType.DORWINION.bannerID), "X", "Y", "Z", Character.valueOf('X'), Blocks.wool, Character.valueOf('Y'), "stickWood", Character.valueOf('Z'), "plankWood"));
        dorwinionRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.brick5, 4, 2), "XX", "XX", Character.valueOf('X'), new ItemStack(LOTRMod.rock, 1, 5)));
        dorwinionRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle9, 6, 7), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick5, 1, 2)));
        dorwinionRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.stairsDorwinionBrick, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick5, 1, 2)));
        dorwinionRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.wall3, 6, 10), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick5, 1, 2)));
        dorwinionRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.helmetDorwinion), "XXX", "X X", Character.valueOf('X'), "ingotIron"));
        dorwinionRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.bodyDorwinion), "X X", "XXX", "XXX", Character.valueOf('X'), "ingotIron"));
        dorwinionRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.legsDorwinion), "XXX", "X X", "X X", Character.valueOf('X'), "ingotIron"));
        dorwinionRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.bootsDorwinion), "X X", "X X", Character.valueOf('X'), "ingotIron"));
        dorwinionRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.helmetDorwinionElf), "XXX", "X X", Character.valueOf('X'), LOTRMod.elfSteel));
        dorwinionRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.bodyDorwinionElf), "X X", "XXX", "XXX", Character.valueOf('X'), LOTRMod.elfSteel));
        dorwinionRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.legsDorwinionElf), "XXX", "X X", "X X", Character.valueOf('X'), LOTRMod.elfSteel));
        dorwinionRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.bootsDorwinionElf), "X X", "X X", Character.valueOf('X'), LOTRMod.elfSteel));
        dorwinionRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.spearBladorthin), "  X", " Y ", "Y  ", Character.valueOf('X'), LOTRMod.dwarfSteel, Character.valueOf('Y'), "stickWood"));
        dorwinionRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.swordDorwinionElf), "X", "X", "Y", Character.valueOf('X'), LOTRMod.elfSteel, Character.valueOf('Y'), "stickWood"));
        dorwinionRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.daggerDorwinionElf), "X", "Y", Character.valueOf('X'), LOTRMod.elfSteel, Character.valueOf('Y'), "stickWood"));
        dorwinionRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.elvenForge), "XXX", "X X", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick5, 1, 2)));
        dorwinionRecipes.add(new ShapelessOreRecipe(new ItemStack(LOTRMod.brick5, 1, 4), new ItemStack(LOTRMod.brick5, 1, 2), "vine"));
        dorwinionRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle10, 6, 1), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick5, 1, 4)));
        dorwinionRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.stairsDorwinionBrickMossy, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick5, 1, 4)));
        dorwinionRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.wall3, 6, 11), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick5, 1, 4)));
        dorwinionRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle10, 6, 2), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick5, 1, 5)));
        dorwinionRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.stairsDorwinionBrickCracked, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick5, 1, 5)));
        dorwinionRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.wall3, 6, 12), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick5, 1, 5)));
        dorwinionRecipes.add(new ShapelessOreRecipe(new ItemStack(LOTRMod.brick5, 1, 7), new ItemStack(LOTRMod.brick5, 1, 2), new ItemStack(LOTRMod.rhunFlower, 1, 2)));
        dorwinionRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle10, 6, 3), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick5, 1, 7)));
        dorwinionRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.stairsDorwinionBrickFlowers, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick5, 1, 7)));
        dorwinionRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.wall3, 6, 13), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick5, 1, 7)));
        dorwinionRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.brick5, 1, 6), "XX", "XX", Character.valueOf('X'), new ItemStack(LOTRMod.brick5, 1, 2)));
        dorwinionRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.pillar2, 3, 6), "X", "X", "X", Character.valueOf('X'), new ItemStack(LOTRMod.rock, 1, 5)));
        dorwinionRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle10, 6, 4), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.pillar2, 1, 6)));
        dorwinionRecipes.add(new ShapelessOreRecipe(new ItemStack(LOTRMod.pillar2, 1, 7), new ItemStack(LOTRMod.pillar2, 1, 6), "vine"));
        dorwinionRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle10, 6, 5), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.pillar2, 1, 7)));
        dorwinionRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.dorwinionElfBow), " XY", "X Y", " XY", Character.valueOf('X'), LOTRMod.elfSteel, Character.valueOf('Y'), Items.string));
    }

    private static void createHobbitRecipes() {
        hobbitRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.hobbitTable), "XX", "YY", Character.valueOf('X'), Blocks.wool, Character.valueOf('Y'), "plankWood"));
        hobbitRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.banner, 1, LOTRItemBanner.BannerType.HOBBIT.bannerID), "X", "Y", "Z", Character.valueOf('X'), Blocks.wool, Character.valueOf('Y'), "stickWood", Character.valueOf('Z'), "plankWood"));
        hobbitRecipes.add(new ShapelessOreRecipe(new ItemStack(LOTRMod.hobbitPancake), Items.wheat, Items.egg, Items.milk_bucket));
        for (int i = 1; i <= 8; ++i) {
            Object[] ingredients = new Object[i + 1];
            ingredients[0] = LOTRMod.mapleSyrup;
            for (int j = 1; j < ingredients.length; ++j) {
                ingredients[j] = LOTRMod.hobbitPancake;
            }
            hobbitRecipes.add(new ShapelessOreRecipe(new ItemStack(LOTRMod.hobbitPancakeMapleSyrup, i), ingredients));
        }
        hobbitRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.gateHobbitGreen, 4), "YYY", "ZXZ", "YYY", Character.valueOf('X'), LOTRMod.gateGear, Character.valueOf('Y'), "plankWood", Character.valueOf('Z'), "dyeGreen"));
        hobbitRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.gateHobbitBlue, 4), "YYY", "ZXZ", "YYY", Character.valueOf('X'), LOTRMod.gateGear, Character.valueOf('Y'), "plankWood", Character.valueOf('Z'), "dyeBlue"));
        hobbitRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.gateHobbitRed, 4), "YYY", "ZXZ", "YYY", Character.valueOf('X'), LOTRMod.gateGear, Character.valueOf('Y'), "plankWood", Character.valueOf('Z'), "dyeRed"));
        hobbitRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.gateHobbitYellow, 4), "YYY", "ZXZ", "YYY", Character.valueOf('X'), LOTRMod.gateGear, Character.valueOf('Y'), "plankWood", Character.valueOf('Z'), "dyeYellow"));
    }

    private static void createRhunRecipes() {
        OreDictionary.registerOre("rhunStone", new ItemStack(Blocks.stone, 1, 0));
        OreDictionary.registerOre("rhunStone", new ItemStack(Blocks.sandstone, 1, 0));
        rhunRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.rhunTable), "XX", "YY", Character.valueOf('X'), "plankWood", Character.valueOf('Y'), new ItemStack(LOTRMod.brick5, 1, 11)));
        rhunRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.banner, 1, LOTRItemBanner.BannerType.RHUN.bannerID), "X", "Y", "Z", Character.valueOf('X'), Blocks.wool, Character.valueOf('Y'), "stickWood", Character.valueOf('Z'), "plankWood"));
        rhunRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.brick5, 4, 11), "XX", "XX", Character.valueOf('X'), "rhunStone"));
        rhunRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle12, 6, 0), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick5, 1, 11)));
        rhunRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.stairsRhunBrick, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick5, 1, 11)));
        rhunRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.wall3, 6, 15), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick5, 1, 11)));
        rhunRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.brick5, 1, 12), "XX", "XX", Character.valueOf('X'), new ItemStack(LOTRMod.brick5, 1, 11)));
        rhunRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.pillar2, 3, 8), "X", "X", "X", Character.valueOf('X'), "rhunStone"));
        rhunRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle12, 6, 4), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.pillar2, 1, 8)));
        rhunRecipes.add(new ShapelessOreRecipe(new ItemStack(LOTRMod.brick5, 1, 13), new ItemStack(LOTRMod.brick5, 1, 11), "vine"));
        rhunRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle12, 6, 1), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick5, 1, 13)));
        rhunRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.stairsRhunBrickMossy, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick5, 1, 13)));
        rhunRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.wall4, 6, 10), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick5, 1, 13)));
        rhunRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle12, 6, 2), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick5, 1, 14)));
        rhunRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.stairsRhunBrickCracked, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick5, 1, 14)));
        rhunRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.wall4, 6, 11), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick5, 1, 14)));
        rhunRecipes.add(new ShapelessOreRecipe(new ItemStack(LOTRMod.brick5, 1, 15), new ItemStack(LOTRMod.brick5, 1, 11), new ItemStack(LOTRMod.rhunFlower, 1, 1)));
        rhunRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle12, 6, 3), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick5, 1, 15)));
        rhunRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.stairsRhunBrickFlowers, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick5, 1, 15)));
        rhunRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.wall4, 6, 12), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick5, 1, 15)));
        rhunRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.brick6, 1, 0), " X ", "XYX", " X ", Character.valueOf('X'), "nuggetGold", Character.valueOf('Y'), new ItemStack(LOTRMod.brick5, 1, 11)));
        rhunRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.brick6, 4, 1), "XX", "XX", Character.valueOf('X'), new ItemStack(LOTRMod.rock, 1, 4)));
        rhunRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle12, 6, 5), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick6, 1, 1)));
        rhunRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.stairsRhunBrickRed, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick6, 1, 1)));
        rhunRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.wall4, 6, 13), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick6, 1, 1)));
        rhunRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.brick6, 1, 2), "XX", "XX", Character.valueOf('X'), new ItemStack(LOTRMod.brick6, 1, 1)));
        rhunRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.pillar2, 3, 9), "X", "X", "X", Character.valueOf('X'), new ItemStack(LOTRMod.rock, 1, 4)));
        rhunRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle12, 6, 6), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.pillar2, 1, 9)));
        rhunRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.swordRhun), "X", "X", "Y", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
        rhunRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.daggerRhun), "X", "Y", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
        rhunRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.spearRhun), "  X", " Y ", "Y  ", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
        rhunRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.polearmRhun), " XX", " YX", "Y  ", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
        rhunRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.pikeRhun), "  X", " YX", "Y  ", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
        rhunRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.helmetRhun), "XXX", "Y Y", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), Items.leather));
        rhunRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.bodyRhun), "X X", "YYY", "XXX", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), Items.leather));
        rhunRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.legsRhun), "XXX", "Y Y", "X X", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), Items.leather));
        rhunRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.bootsRhun), "Y Y", "X X", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), Items.leather));
        rhunRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.rhunBow), " XY", "X Y", " XY", Character.valueOf('X'), "stickWood", Character.valueOf('Y'), Items.string));
        rhunRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.horseArmorRhunGold), "X  ", "XYX", "XXX", Character.valueOf('X'), LOTRMod.gildedIron, Character.valueOf('Y'), Items.leather));
        rhunRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.rhunFireJar), "XYX", "YZY", "XYX", Character.valueOf('X'), LOTRMod.gildedIron, Character.valueOf('Y'), Items.gunpowder, Character.valueOf('Z'), LOTRMod.nauriteGem));
        rhunRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.rhunFirePot, 4), "Z", "Y", "X", Character.valueOf('X'), LOTRMod.gildedIron, Character.valueOf('Y'), Items.gunpowder, Character.valueOf('Z'), LOTRMod.nauriteGem));
        rhunRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.gateRhun, 4), "ZYZ", "YXY", "ZYZ", Character.valueOf('X'), LOTRMod.gateGear, Character.valueOf('Y'), "plankWood", Character.valueOf('Z'), LOTRMod.gildedIron));
        LOTRRecipes.addDyeableWoolRobeRecipes(rhunRecipes, new ItemStack(LOTRMod.bodyKaftan), "X X", "XXX", "XXX", Character.valueOf('X'), Blocks.wool);
        LOTRRecipes.addDyeableWoolRobeRecipes(rhunRecipes, new ItemStack(LOTRMod.legsKaftan), "XXX", "X X", "X X", Character.valueOf('X'), Blocks.wool);
        rhunRecipes.add(new LOTRRecipeHaradRobesDye(LOTRMaterial.KAFTAN));
        rhunRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.helmetRhunGold), "XXX", "X X", Character.valueOf('X'), LOTRMod.gildedIron));
        rhunRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.bodyRhunGold), "X X", "XXX", "XXX", Character.valueOf('X'), LOTRMod.gildedIron));
        rhunRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.legsRhunGold), "XXX", "X X", "X X", Character.valueOf('X'), LOTRMod.gildedIron));
        rhunRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.bootsRhunGold), "X X", "X X", Character.valueOf('X'), LOTRMod.gildedIron));
        rhunRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.helmetRhunWarlord), "XYX", Character.valueOf('X'), LOTRMod.kineArawHorn, Character.valueOf('Y'), new ItemStack(LOTRMod.helmetRhunGold, 1, 0)));
        rhunRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.battleaxeRhun), "XXX", "XYX", " Y ", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
    }

    private static void createRivendellRecipes() {
        rivendellRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.rivendellTable), "XX", "XX", Character.valueOf('X'), "plankWood"));
        rivendellRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.shovelRivendell), "X", "Y", "Y", Character.valueOf('X'), LOTRMod.elfSteel, Character.valueOf('Y'), "stickWood"));
        rivendellRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.pickaxeRivendell), "XXX", " Y ", " Y ", Character.valueOf('X'), LOTRMod.elfSteel, Character.valueOf('Y'), "stickWood"));
        rivendellRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.axeRivendell), "XX", "XY", " Y", Character.valueOf('X'), LOTRMod.elfSteel, Character.valueOf('Y'), "stickWood"));
        rivendellRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.hoeRivendell), "XX", " Y", " Y", Character.valueOf('X'), LOTRMod.elfSteel, Character.valueOf('Y'), "stickWood"));
        rivendellRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.swordRivendell), "X", "X", "Y", Character.valueOf('X'), LOTRMod.elfSteel, Character.valueOf('Y'), "stickWood"));
        rivendellRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.daggerRivendell), "X", "Y", Character.valueOf('X'), LOTRMod.elfSteel, Character.valueOf('Y'), "stickWood"));
        rivendellRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.spearRivendell), "  X", " Y ", "Y  ", Character.valueOf('X'), LOTRMod.elfSteel, Character.valueOf('Y'), "stickWood"));
        rivendellRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.helmetRivendell), "XXX", "X X", Character.valueOf('X'), LOTRMod.elfSteel));
        rivendellRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.bodyRivendell), "X X", "XXX", "XXX", Character.valueOf('X'), LOTRMod.elfSteel));
        rivendellRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.legsRivendell), "XXX", "X X", "X X", Character.valueOf('X'), LOTRMod.elfSteel));
        rivendellRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.bootsRivendell), "X X", "X X", Character.valueOf('X'), LOTRMod.elfSteel));
        rivendellRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.banner, 1, LOTRItemBanner.BannerType.RIVENDELL.bannerID), "X", "Y", "Z", Character.valueOf('X'), Blocks.wool, Character.valueOf('Y'), "stickWood", Character.valueOf('Z'), "plankWood"));
        rivendellRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.horseArmorRivendell), "X  ", "XYX", "XXX", Character.valueOf('X'), LOTRMod.elfSteel, Character.valueOf('Y'), Items.leather));
        rivendellRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.polearmRivendell), "  X", " Y ", "X  ", Character.valueOf('X'), LOTRMod.elfSteel, Character.valueOf('Y'), "stickWood"));
        rivendellRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.longspearRivendell), "  X", " YX", "Y  ", Character.valueOf('X'), LOTRMod.elfSteel, Character.valueOf('Y'), "stickWood"));
        rivendellRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.rivendellBow), " XY", "X Y", " XY", Character.valueOf('X'), LOTRMod.elfSteel, Character.valueOf('Y'), Items.string));
    }

    private static void createUmbarRecipes() {
        umbarRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.umbarTable), "XX", "YY", Character.valueOf('X'), "plankWood", Character.valueOf('Y'), new ItemStack(Blocks.sandstone, 1, 0)));
        umbarRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.banner, 1, LOTRItemBanner.BannerType.UMBAR.bannerID), "X", "Y", "Z", Character.valueOf('X'), Blocks.wool, Character.valueOf('Y'), "stickWood", Character.valueOf('Z'), "plankWood"));
        umbarRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.helmetCorsair), "XXX", "Y Y", Character.valueOf('X'), "ingotBronze", Character.valueOf('Y'), Items.leather));
        umbarRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.bodyCorsair), "X X", "YYY", "XXX", Character.valueOf('X'), "ingotBronze", Character.valueOf('Y'), Items.leather));
        umbarRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.legsCorsair), "XXX", "Y Y", "X X", Character.valueOf('X'), "ingotBronze", Character.valueOf('Y'), Items.leather));
        umbarRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.bootsCorsair), "Y Y", "X X", Character.valueOf('X'), "ingotBronze", Character.valueOf('Y'), Items.leather));
        umbarRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.swordCorsair), "X ", "X ", "YA", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood", Character.valueOf('A'), Items.string));
        umbarRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.daggerCorsair), "X ", "YA", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood", Character.valueOf('A'), Items.string));
        umbarRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.spearCorsair), "  X", " Y ", "YA ", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood", Character.valueOf('A'), Items.string));
        umbarRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.battleaxeCorsair), "XXX", "XYX", " YA", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood", Character.valueOf('A'), Items.string));
        umbarRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.scimitarNearHarad), "X", "X", "Y", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
        umbarRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.daggerNearHarad), "X", "Y", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
        umbarRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.spearNearHarad), "  X", " Y ", "Y  ", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
        umbarRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.maceNearHarad), " XX", " XX", "Y  ", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
        umbarRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.pikeNearHarad), "  X", " YX", "Y  ", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
        umbarRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.poleaxeNearHarad), " XX", " YX", "Y  ", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
        umbarRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.helmetUmbar), "XXX", "X X", Character.valueOf('X'), "ingotIron"));
        umbarRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.bodyUmbar), "X X", "XXX", "XXX", Character.valueOf('X'), "ingotIron"));
        umbarRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.legsUmbar), "XXX", "X X", "X X", Character.valueOf('X'), "ingotIron"));
        umbarRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.bootsUmbar), "X X", "X X", Character.valueOf('X'), "ingotIron"));
        umbarRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.brick6, 4, 6), "XX", "XX", Character.valueOf('X'), Blocks.stone));
        umbarRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle13, 6, 2), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick6, 1, 6)));
        umbarRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.stairsUmbarBrick, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick6, 1, 6)));
        umbarRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.wall5, 6, 0), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick6, 1, 6)));
        umbarRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle13, 6, 3), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick6, 1, 7)));
        umbarRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.stairsUmbarBrickCracked, 4), "X  ", "XX ", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick6, 1, 7)));
        umbarRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.wall5, 6, 1), "XXX", "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.brick6, 1, 7)));
        umbarRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.brick6, 1, 8), "XX", "XX", Character.valueOf('X'), new ItemStack(LOTRMod.brick6, 1, 6)));
        umbarRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.brick6, 1, 9), "XX", "XX", Character.valueOf('X'), new ItemStack(LOTRMod.brick2, 1, 11)));
        umbarRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.pillar2, 3, 10), "X", "X", "X", Character.valueOf('X'), Blocks.stone));
        umbarRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.slabSingle13, 6, 4), "XXX", Character.valueOf('X'), new ItemStack(LOTRMod.pillar2, 1, 10)));
        umbarRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.horseArmorUmbar), "X  ", "XYX", "XXX", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), Items.leather));
    }

    private static void createGulfRecipes() {
        gulfRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.gulfTable), "XX", "YY", Character.valueOf('X'), "plankWood", Character.valueOf('Y'), new ItemStack(Blocks.sandstone, 1, 0)));
        gulfRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.banner, 1, LOTRItemBanner.BannerType.HARAD_GULF.bannerID), "X", "Y", "Z", Character.valueOf('X'), Blocks.wool, Character.valueOf('Y'), "stickWood", Character.valueOf('Z'), "plankWood"));
        gulfRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.helmetGulfHarad), "XXX", "Y Y", Character.valueOf('X'), "ingotBronze", Character.valueOf('Y'), LOTRMod.driedReeds));
        gulfRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.bodyGulfHarad), "X X", "YYY", "XXX", Character.valueOf('X'), "ingotBronze", Character.valueOf('Y'), LOTRMod.driedReeds));
        gulfRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.legsGulfHarad), "XXX", "Y Y", "X X", Character.valueOf('X'), "ingotBronze", Character.valueOf('Y'), LOTRMod.driedReeds));
        gulfRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.bootsGulfHarad), "Y Y", "X X", Character.valueOf('X'), "ingotBronze", Character.valueOf('Y'), LOTRMod.driedReeds));
        gulfRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.swordGulfHarad), " X", "X ", "Y ", Character.valueOf('X'), "ingotBronze", Character.valueOf('Y'), "stickWood"));
    }

    private static void createBreeRecipes() {
        breeRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.breeTable), "XX", "XX", Character.valueOf('X'), "plankWood"));
        breeRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.banner, 1, LOTRItemBanner.BannerType.BREE.bannerID), "X", "Y", "Z", Character.valueOf('X'), Blocks.wool, Character.valueOf('Y'), "stickWood", Character.valueOf('Z'), "plankWood"));
    }

    private static void createUnsmeltingRecipes() {
        LOTRRecipes.createUtumnoUnsmeltingRecipes();
        LOTRRecipes.createGondolinUnsmeltingRecipes();
        LOTRRecipes.createTauredainUnsmeltingRecipes();
        LOTRRecipes.createArnorUnsmeltingRecipes();
        LOTRRecipes.createBNUnsmeltingRecipes();
        uncraftableUnsmeltingRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.blacksmithHammer), "XYX", "XYX", " Y ", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
        uncraftableUnsmeltingRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.daggerAncientHarad), "X", "Y", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
    }

    private static void createUtumnoUnsmeltingRecipes() {
        uncraftableUnsmeltingRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.helmetUtumno), "XXX", "X X", Character.valueOf('X'), LOTRMod.orcSteel));
        uncraftableUnsmeltingRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.bodyUtumno), "X X", "XXX", "XXX", Character.valueOf('X'), LOTRMod.orcSteel));
        uncraftableUnsmeltingRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.legsUtumno), "XXX", "X X", "X X", Character.valueOf('X'), LOTRMod.orcSteel));
        uncraftableUnsmeltingRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.bootsUtumno), "X X", "X X", Character.valueOf('X'), LOTRMod.orcSteel));
        uncraftableUnsmeltingRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.swordUtumno), "X", "X", "Y", Character.valueOf('X'), LOTRMod.orcSteel, Character.valueOf('Y'), "stickWood"));
        uncraftableUnsmeltingRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.daggerUtumno), "X", "Y", Character.valueOf('X'), LOTRMod.orcSteel, Character.valueOf('Y'), "stickWood"));
        uncraftableUnsmeltingRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.spearUtumno), "  X", " Y ", "Y  ", Character.valueOf('X'), LOTRMod.orcSteel, Character.valueOf('Y'), "stickWood"));
        uncraftableUnsmeltingRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.battleaxeUtumno), "XXX", "XYX", " Y ", Character.valueOf('X'), LOTRMod.orcSteel, Character.valueOf('Y'), "stickWood"));
        uncraftableUnsmeltingRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.hammerUtumno), "XYX", "XYX", " Y ", Character.valueOf('X'), LOTRMod.orcSteel, Character.valueOf('Y'), "stickWood"));
        uncraftableUnsmeltingRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.utumnoBow), " XY", "X Y", " XY", Character.valueOf('X'), LOTRMod.orcSteel, Character.valueOf('Y'), Items.string));
        uncraftableUnsmeltingRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.utumnoPickaxe), "XXX", " Y ", " Y ", Character.valueOf('X'), LOTRMod.orcSteel, Character.valueOf('Y'), "stickWood"));
    }

    private static void createGondolinUnsmeltingRecipes() {
        uncraftableUnsmeltingRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.helmetGondolin), "XXX", "X X", Character.valueOf('X'), LOTRMod.elfSteel));
        uncraftableUnsmeltingRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.bodyGondolin), "X X", "XXX", "XXX", Character.valueOf('X'), LOTRMod.elfSteel));
        uncraftableUnsmeltingRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.legsGondolin), "XXX", "X X", "X X", Character.valueOf('X'), LOTRMod.elfSteel));
        uncraftableUnsmeltingRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.bootsGondolin), "X X", "X X", Character.valueOf('X'), LOTRMod.elfSteel));
        uncraftableUnsmeltingRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.swordGondolin), "X", "X", "Y", Character.valueOf('X'), LOTRMod.elfSteel, Character.valueOf('Y'), "stickWood"));
    }

    private static void createTauredainUnsmeltingRecipes() {
        uncraftableUnsmeltingRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.helmetTauredainGold), "XXX", "X X", Character.valueOf('X'), "ingotGold"));
        uncraftableUnsmeltingRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.bodyTauredainGold), "X X", "XXX", "XXX", Character.valueOf('X'), "ingotGold"));
        uncraftableUnsmeltingRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.legsTauredainGold), "XXX", "X X", "X X", Character.valueOf('X'), "ingotGold"));
        uncraftableUnsmeltingRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.bootsTauredainGold), "X X", "X X", Character.valueOf('X'), "ingotGold"));
    }

    private static void createArnorUnsmeltingRecipes() {
        uncraftableUnsmeltingRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.daggerBarrow), "X", "Y", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
        uncraftableUnsmeltingRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.helmetArnor), "XXX", "X X", Character.valueOf('X'), "ingotIron"));
        uncraftableUnsmeltingRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.bodyArnor), "X X", "XXX", "XXX", Character.valueOf('X'), "ingotIron"));
        uncraftableUnsmeltingRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.legsArnor), "XXX", "X X", "X X", Character.valueOf('X'), "ingotIron"));
        uncraftableUnsmeltingRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.bootsArnor), "X X", "X X", Character.valueOf('X'), "ingotIron"));
        uncraftableUnsmeltingRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.swordArnor), "X", "X", "Y", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
        uncraftableUnsmeltingRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.daggerArnor), "X", "Y", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
        uncraftableUnsmeltingRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.spearArnor), "  X", " Y ", "Y  ", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
    }

    private static void createBNUnsmeltingRecipes() {
        uncraftableUnsmeltingRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.helmetBlackNumenorean), "XXX", "X X", Character.valueOf('X'), "ingotIron"));
        uncraftableUnsmeltingRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.bodyBlackNumenorean), "X X", "XXX", "XXX", Character.valueOf('X'), "ingotIron"));
        uncraftableUnsmeltingRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.legsBlackNumenorean), "XXX", "X X", "X X", Character.valueOf('X'), "ingotIron"));
        uncraftableUnsmeltingRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.bootsBlackNumenorean), "X X", "X X", Character.valueOf('X'), "ingotIron"));
        uncraftableUnsmeltingRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.swordBlackNumenorean), "X", "X", "Y", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
        uncraftableUnsmeltingRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.daggerBlackNumenorean), "X", "Y", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
        uncraftableUnsmeltingRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.spearBlackNumenorean), "  X", " Y ", "Y  ", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
        uncraftableUnsmeltingRecipes.add(new ShapedOreRecipe(new ItemStack(LOTRMod.maceBlackNumenorean), " XX", " XX", "Y  ", Character.valueOf('X'), "ingotIron", Character.valueOf('Y'), "stickWood"));
    }

    public static ItemStack findMatchingRecipe(List recipeList, InventoryCrafting inv, World world) {
        for (int i = 0; i < recipeList.size(); ++i) {
            IRecipe recipe = (IRecipe)recipeList.get(i);
            if (!recipe.matches(inv, world)) continue;
            return recipe.getCraftingResult(inv);
        }
        return null;
    }

    public static boolean checkItemEquals(ItemStack target, ItemStack input) {
        return target.getItem().equals(input.getItem()) && (target.getItemDamage() == 32767 || target.getItemDamage() == input.getItemDamage());
    }

    private static void addDyeableWoolRobeRecipes(List[] recipeLists, ItemStack result, Object ... params) {
        for (List list : recipeLists) {
            LOTRRecipes.addDyeableWoolRobeRecipes(list, result, params);
        }
    }

    private static void addDyeableWoolRobeRecipes(List recipeList, ItemStack result, Object ... params) {
        for (int i = 0; i <= 15; ++i) {
            Object[] paramsDyed = Arrays.copyOf(params, params.length);
            ItemStack wool = new ItemStack(Blocks.wool, 1, i);
            for (int l = 0; l < paramsDyed.length; ++l) {
                Object param = paramsDyed[l];
                if (param instanceof Block && (Block)param == Block.getBlockFromItem(wool.getItem())) {
                    paramsDyed[l] = wool.copy();
                    continue;
                }
                if (!(param instanceof ItemStack) || ((ItemStack)param).getItem() != wool.getItem()) continue;
                paramsDyed[l] = wool.copy();
            }
            ItemStack resultDyed = result.copy();
            float[] colors = EntitySheep.fleeceColorTable[i];
            float r = colors[0];
            float g = colors[1];
            float b = colors[2];
            if (r != 1.0f || g != 1.0f || b != 1.0f) {
                int rI = (int)(r * 255.0f);
                int gI = (int)(g * 255.0f);
                int bI = (int)(b * 255.0f);
                int rgb = rI << 16 | gI << 8 | bI;
                LOTRItemHaradRobes.setRobesColor(resultDyed, rgb);
            }
            recipeList.add(new ShapedOreRecipe(resultDyed, paramsDyed));
        }
    }
}

