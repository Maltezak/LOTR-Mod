package lotr.client;

import java.lang.reflect.Method;

import cpw.mods.fml.common.ObfuscationReflectionHelper;
import lotr.common.LOTRReflection;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.client.renderer.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class LOTRReflectionClient {
    private static int[] colorCodes;

    public static void testAll(World world, Minecraft mc) {
        LOTRReflectionClient.setCameraRoll(mc.entityRenderer, LOTRReflectionClient.getCameraRoll(mc.entityRenderer));
        LOTRReflectionClient.setHandFOV(mc.entityRenderer, LOTRReflectionClient.getHandFOV(mc.entityRenderer));
        LOTRReflectionClient.getColorCodes(mc.fontRenderer);
        LOTRReflectionClient.setHighlightedItemTicks(mc.ingameGUI, LOTRReflectionClient.getHighlightedItemTicks(mc.ingameGUI));
        LOTRReflectionClient.getHighlightedItemStack(mc.ingameGUI);
    }

    public static void setCameraRoll(EntityRenderer renderer, float roll) {
        try {
            ObfuscationReflectionHelper.setPrivateValue(EntityRenderer.class, renderer, (Object)Float.valueOf(roll), "camRoll", "field_78495_O");
        }
        catch (Exception e) {
            LOTRReflection.logFailure(e);
        }
    }

    public static float getCameraRoll(EntityRenderer renderer) {
        try {
            return ((Float)ObfuscationReflectionHelper.getPrivateValue(EntityRenderer.class, renderer, "camRoll", "field_78495_O"));
        }
        catch (Exception e) {
            LOTRReflection.logFailure(e);
            return 0.0f;
        }
    }

    public static void setHandFOV(EntityRenderer renderer, float fov) {
        try {
            ObfuscationReflectionHelper.setPrivateValue(EntityRenderer.class, renderer, (Object)Float.valueOf(fov), "fovModifierHand", "field_78507_R");
        }
        catch (Exception e) {
            LOTRReflection.logFailure(e);
        }
    }

    public static float getHandFOV(EntityRenderer renderer) {
        try {
            return ((Float)ObfuscationReflectionHelper.getPrivateValue(EntityRenderer.class, renderer, "fovModifierHand", "field_78507_R"));
        }
        catch (Exception e) {
            LOTRReflection.logFailure(e);
            return 0.0f;
        }
    }

    public static float getFOVModifier(EntityRenderer renderer, float tick, boolean flag) {
        try {
            Method method = LOTRReflection.getPrivateMethod(EntityRenderer.class, renderer, new Class[]{Float.TYPE, Boolean.TYPE}, "getFOVModifier", "func_78481_a");
            return ((Float)method.invoke(renderer, Float.valueOf(tick), flag));
        }
        catch (Exception e) {
            LOTRReflection.logFailure(e);
            return 0.0f;
        }
    }

    private static int[] getColorCodes(FontRenderer fontRenderer) {
        if (colorCodes == null) {
            try {
                colorCodes = (int[])ObfuscationReflectionHelper.getPrivateValue(FontRenderer.class, fontRenderer, "colorCode", "field_78285_g");
            }
            catch (Exception e) {
                LOTRReflection.logFailure(e);
            }
        }
        return colorCodes;
    }

    public static int getFormattingColor(EnumChatFormatting ecf) {
        FontRenderer fr = Minecraft.getMinecraft().fontRenderer;
        int colorIndex = ecf.ordinal();
        return LOTRReflectionClient.getColorCodes(fr)[colorIndex];
    }

    public static void setHighlightedItemTicks(GuiIngame gui, int ticks) {
        try {
            ObfuscationReflectionHelper.setPrivateValue(GuiIngame.class, gui, (Object)ticks, "remainingHighlightTicks", "field_92017_k");
        }
        catch (Exception e) {
            LOTRReflection.logFailure(e);
        }
    }

    public static int getHighlightedItemTicks(GuiIngame gui) {
        try {
            return (Integer)ObfuscationReflectionHelper.getPrivateValue(GuiIngame.class, gui, "remainingHighlightTicks", "field_92017_k");
        }
        catch (Exception e) {
            LOTRReflection.logFailure(e);
            return 0;
        }
    }

    public static ItemStack getHighlightedItemStack(GuiIngame gui) {
        try {
            return (ItemStack)ObfuscationReflectionHelper.getPrivateValue(GuiIngame.class, gui, "highlightingItemStack", "field_92016_l");
        }
        catch (Exception e) {
            LOTRReflection.logFailure(e);
            return null;
        }
    }

    public static int getGuiLeft(GuiContainer gui) {
        try {
            return (Integer)ObfuscationReflectionHelper.getPrivateValue(GuiContainer.class, gui, "guiLeft", "field_147003_i");
        }
        catch (Exception e) {
            LOTRReflection.logFailure(e);
            return 0;
        }
    }

    public static int getGuiTop(GuiContainer gui) {
        try {
            return (Integer)ObfuscationReflectionHelper.getPrivateValue(GuiContainer.class, gui, "guiTop", "field_147009_r");
        }
        catch (Exception e) {
            LOTRReflection.logFailure(e);
            return 0;
        }
    }

    public static int getGuiXSize(GuiContainer gui) {
        try {
            return (Integer)ObfuscationReflectionHelper.getPrivateValue(GuiContainer.class, gui, "xSize", "field_146999_f");
        }
        catch (Exception e) {
            LOTRReflection.logFailure(e);
            return 0;
        }
    }

    public static boolean hasGuiPotionEffects(InventoryEffectRenderer gui) {
        try {
            return (Boolean)ObfuscationReflectionHelper.getPrivateValue(InventoryEffectRenderer.class, gui, "field_147045_u");
        }
        catch (Exception e) {
            LOTRReflection.logFailure(e);
            return false;
        }
    }

    public static int getCreativeTabIndex(GuiContainerCreative gui) {
        try {
            return (Integer)ObfuscationReflectionHelper.getPrivateValue(GuiContainerCreative.class, gui, "selectedTabIndex", "field_147058_w");
        }
        catch (Exception e) {
            LOTRReflection.logFailure(e);
            return 0;
        }
    }
}

