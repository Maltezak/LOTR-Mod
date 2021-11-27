package lotr.client;

import java.util.*;

import com.google.common.math.IntMath;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import lotr.common.*;
import lotr.common.fac.LOTRFaction;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;

public class LOTRKeyHandler {
    public static KeyBinding keyBindingMenu = new KeyBinding("Menu", 38, "LOTR");
    public static KeyBinding keyBindingMapTeleport = new KeyBinding("Map Teleport", 50, "LOTR");
    public static KeyBinding keyBindingFastTravel = new KeyBinding("Fast Travel", 33, "LOTR");
    public static KeyBinding keyBindingAlignmentCycleLeft = new KeyBinding("Alignment Cycle Left", 203, "LOTR");
    public static KeyBinding keyBindingAlignmentCycleRight = new KeyBinding("Alignment Cycle Right", 205, "LOTR");
    public static KeyBinding keyBindingAlignmentGroupPrev = new KeyBinding("Alignment Group Prev", 200, "LOTR");
    public static KeyBinding keyBindingAlignmentGroupNext = new KeyBinding("Alignment Group Next", 208, "LOTR");
    private static Minecraft mc = Minecraft.getMinecraft();
    private static int alignmentChangeTick;
    public LOTRKeyHandler() {
        FMLCommonHandler.instance().bus().register(this);
        ClientRegistry.registerKeyBinding(keyBindingMenu);
        ClientRegistry.registerKeyBinding(keyBindingMapTeleport);
        ClientRegistry.registerKeyBinding(keyBindingFastTravel);
        ClientRegistry.registerKeyBinding(keyBindingAlignmentCycleLeft);
        ClientRegistry.registerKeyBinding(keyBindingAlignmentCycleRight);
        ClientRegistry.registerKeyBinding(keyBindingAlignmentGroupPrev);
        ClientRegistry.registerKeyBinding(keyBindingAlignmentGroupNext);
    }

    @SubscribeEvent
    public void MouseInputEvent(InputEvent.MouseInputEvent event) {
        LOTRAttackTiming.doAttackTiming();
    }

    @SubscribeEvent
    public void KeyInputEvent(InputEvent.KeyInputEvent event) {
        LOTRAttackTiming.doAttackTiming();
        if (keyBindingMenu.getIsKeyPressed() && LOTRKeyHandler.mc.currentScreen == null) {
            LOTRKeyHandler.mc.thePlayer.openGui(LOTRMod.instance, 11, LOTRKeyHandler.mc.theWorld, 0, 0, 0);
        }
        LOTRPlayerData pd = LOTRLevelData.getData(LOTRKeyHandler.mc.thePlayer);
        boolean usedAlignmentKeys = false;
        HashMap<LOTRDimension.DimensionRegion, LOTRFaction> lastViewedRegions = new HashMap<>();
        LOTRDimension currentDimension = LOTRDimension.getCurrentDimensionWithFallback(LOTRKeyHandler.mc.theWorld);
        LOTRFaction currentFaction = pd.getViewingFaction();
        LOTRDimension.DimensionRegion currentRegion = currentFaction.factionRegion;
        List<LOTRDimension.DimensionRegion> regionList = currentDimension.dimensionRegions;
        List<LOTRFaction> factionList = currentRegion.factionList;
        if (LOTRKeyHandler.mc.currentScreen == null && alignmentChangeTick <= 0) {
            int i;
            if (keyBindingAlignmentCycleLeft.getIsKeyPressed()) {
                i = factionList.indexOf(currentFaction);
                --i;
                i = IntMath.mod(i, factionList.size());
                currentFaction = factionList.get(i);
                usedAlignmentKeys = true;
            }
            if (keyBindingAlignmentCycleRight.getIsKeyPressed()) {
                i = factionList.indexOf(currentFaction);
                ++i;
                i = IntMath.mod(i, factionList.size());
                currentFaction = factionList.get(i);
                usedAlignmentKeys = true;
            }
            if (regionList != null && currentRegion != null) {
                if (keyBindingAlignmentGroupPrev.getIsKeyPressed()) {
                    pd.setRegionLastViewedFaction(currentRegion, currentFaction);
                    lastViewedRegions.put(currentRegion, currentFaction);
                    i = regionList.indexOf(currentRegion);
                    --i;
                    i = IntMath.mod(i, regionList.size());
                    currentRegion = regionList.get(i);
                    factionList = currentRegion.factionList;
                    currentFaction = pd.getRegionLastViewedFaction(currentRegion);
                    usedAlignmentKeys = true;
                }
                if (keyBindingAlignmentGroupNext.getIsKeyPressed()) {
                    pd.setRegionLastViewedFaction(currentRegion, currentFaction);
                    lastViewedRegions.put(currentRegion, currentFaction);
                    i = regionList.indexOf(currentRegion);
                    ++i;
                    i = IntMath.mod(i, regionList.size());
                    currentRegion = regionList.get(i);
                    factionList = currentRegion.factionList;
                    currentFaction = pd.getRegionLastViewedFaction(currentRegion);
                    usedAlignmentKeys = true;
                }
            }
        }
        if (usedAlignmentKeys) {
            LOTRClientProxy.sendClientInfoPacket(currentFaction, lastViewedRegions);
            alignmentChangeTick = 2;
        }
    }

    public static void update() {
        if (alignmentChangeTick > 0) {
            --alignmentChangeTick;
        }
    }
}

