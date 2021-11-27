package lotr.client;

import java.util.*;

import org.lwjgl.opengl.GL11;

import com.mojang.authlib.GameProfile;

import cpw.mods.fml.client.registry.*;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import lotr.client.fx.*;
import lotr.client.gui.*;
import lotr.client.model.LOTRArmorModels;
import lotr.client.render.*;
import lotr.client.render.entity.*;
import lotr.client.render.tileentity.*;
import lotr.client.sound.LOTRMusic;
import lotr.common.*;
import lotr.common.entity.*;
import lotr.common.entity.animal.*;
import lotr.common.entity.item.*;
import lotr.common.entity.npc.*;
import lotr.common.entity.projectile.*;
import lotr.common.fac.*;
import lotr.common.network.*;
import lotr.common.quest.LOTRMiniQuest;
import lotr.common.tileentity.*;
import lotr.common.util.LOTRFunctions;
import lotr.common.world.biome.LOTRBiome;
import lotr.common.world.map.*;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.potion.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.chunk.*;

public class LOTRClientProxy
extends LOTRCommonProxy {
    public static final ResourceLocation enchantmentTexture = new ResourceLocation("textures/misc/enchanted_item_glint.png");
    public static final ResourceLocation alignmentTexture = new ResourceLocation("lotr:gui/alignment.png");
    public static final ResourceLocation particlesTexture = new ResourceLocation("lotr:misc/particles.png");
    public static final ResourceLocation particles2Texture = new ResourceLocation("lotr:misc/particles2.png");
    public static final ResourceLocation customPotionsTexture = new ResourceLocation("lotr:gui/effects.png");
    public static final int TESSELLATOR_MAX_BRIGHTNESS = 15728880;
    public static final int FONTRENDERER_ALPHA_MIN = 4;
    public static LOTREffectRenderer customEffectRenderer;
    public static LOTRRenderPlayer specialPlayerRenderer;
    public static LOTRSwingHandler swingHandler;
    public static LOTRTickHandlerClient tickHandler;
    public static LOTRKeyHandler keyHandler;
    private static LOTRGuiHandler guiHandler;
    public static LOTRMusic musicHandler;
    private int beaconRenderID;
    private int barrelRenderID;
    private int orcBombRenderID;
    private int doubleTorchRenderID;
    private int mobSpawnerRenderID;
    private int plateRenderID;
    private int stalactiteRenderID;
    private int flowerPotRenderID;
    private int cloverRenderID;
    private int entJarRenderID;
    private int trollTotemRenderID;
    private int fenceRenderID;
    private int grassRenderID;
    private int fallenLeavesRenderID;
    private int commandTableRenderID;
    private int butterflyJarRenderID;
    private int unsmelteryRenderID;
    private int chestRenderID;
    private int reedsRenderID;
    private int wasteRenderID;
    private int beamRenderID;
    private int vCauldronRenderID;
    private int grapevineRenderID;
    private int thatchFloorRenderID;
    private int treasureRenderID;
    private int flowerRenderID;
    private int doublePlantRenderID;
    private int birdCageRenderID;
    private int rhunFireJarRenderID;
    private int coralRenderID;
    private int doorRenderID;
    private int ropeRenderID;
    private int orcChainRenderID;
    private int guldurilRenderID;
    private int orcPlatingRenderID;
    private int trapdoorRenderID;

    @Override
    public void onPreload() {
        System.setProperty("fml.skipFirstTextureLoad", "false");
        LOTRItemRendererManager.load();
        LOTRArmorModels.setupArmorModels();
    }

    @Override
    public void onLoad() {
        customEffectRenderer = new LOTREffectRenderer(Minecraft.getMinecraft());
        LOTRTextures.load();
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityPortal.class, new LOTRRenderPortal());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityHorse.class, new LOTRRenderHorse());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityHobbit.class, new LOTRRenderHobbit());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityHobbitBartender.class, new LOTRRenderHobbitTrader("outfit_bartender"));
        RenderingRegistry.registerEntityRenderingHandler(LOTREntitySmokeRing.class, new LOTRRenderSmokeRing());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityOrc.class, new LOTRRenderOrc());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityShirePony.class, new LOTRRenderShirePony());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityOrcBomb.class, new LOTRRenderOrcBomb());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityWarg.class, new LOTRRenderWarg());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityGandalfFireball.class, new LOTRRenderGandalfFireball());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntitySpear.class, new LOTRRenderSpear());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntitySauron.class, new LOTRRenderSauron());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityElf.class, new LOTRRenderElf());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityPlate.class, new LOTRRenderPlate());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityWargskinRug.class, new LOTRRenderWargskinRug());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntitySkeletalWraith.class, new LOTRRenderSkeleton());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityGondorBlacksmith.class, new LOTRRenderGondorTrader("outfit_blacksmith"));
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityGaladhrimTrader.class, new LOTRRenderElvenTrader("galadhrimTrader_cloak"));
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityAlignmentBonus.class, new LOTRRenderAlignmentBonus());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityDwarf.class, new LOTRRenderDwarf());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityMarshWraith.class, new LOTRRenderMarshWraith());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityMarshWraithBall.class, new LOTRRenderWraithBall());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityDwarfCommander.class, new LOTRRenderDwarfCommander());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityBlueDwarfCommander.class, new LOTRRenderDwarfCommander());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityBlueDwarfMerchant.class, new LOTRRenderDwarfCommander());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityThrowingAxe.class, new LOTRRenderThrowingAxe());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityCrossbowBolt.class, new LOTRRenderCrossbowBolt());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityTroll.class, new LOTRRenderTroll());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityOlogHai.class, new LOTRRenderOlogHai());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityStoneTroll.class, new LOTRRenderStoneTroll());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityGollum.class, new LOTRRenderGollum());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityMirkwoodSpider.class, new LOTRRenderMirkwoodSpider());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityRohanMan.class, new LOTRRenderRohirrim());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityPebble.class, new RenderSnowball(LOTRMod.pebble));
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityMysteryWeb.class, new RenderSnowball(LOTRMod.mysteryWeb));
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityRohanBlacksmith.class, new LOTRRenderRohanTrader("outfit_blacksmith"));
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityRanger.class, new LOTRRenderRanger());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityDunlending.class, new LOTRRenderDunlending());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityDunlendingWarrior.class, new LOTRRenderDunlendingBase());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityEnt.class, new LOTRRenderEnt());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityTraderRespawn.class, new LOTRRenderTraderRespawn());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityMountainTroll.class, new LOTRRenderMountainTroll());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityThrownRock.class, new LOTRRenderThrownRock());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityMountainTrollChieftain.class, new LOTRRenderMountainTrollChieftain());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityHuornBase.class, new LOTRRenderHuorn());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityRohanMeadhost.class, new LOTRRenderRohanTrader("outfit_meadhost"));
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityButterfly.class, new LOTRRenderButterfly());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityBarrel.class, new LOTRRenderEntityBarrel());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityMidges.class, new LOTRRenderMidges());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityDeadMarshFace.class, new LOTRRenderDeadMarshFace());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityNurnSlave.class, new LOTRRenderNurnSlave());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityRabbit.class, new LOTRRenderRabbit());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityWildBoar.class, new LOTRRenderWildBoar());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityMordorSpider.class, new LOTRRenderMordorSpider());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityBanner.class, new LOTRRenderBanner());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityBannerWall.class, new LOTRRenderBannerWall());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityLionBase.class, new LOTRRenderLion());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityGiraffe.class, new LOTRRenderGiraffe());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityZebra.class, new LOTRRenderZebra());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityRhino.class, new LOTRRenderRhino());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityCrocodile.class, new LOTRRenderCrocodile());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityNearHaradrimBase.class, new LOTRRenderNearHaradrim());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityNearHaradrimWarlord.class, new LOTRRenderNearHaradrimWarlord());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityGemsbok.class, new LOTRRenderGemsbok());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityFlamingo.class, new LOTRRenderFlamingo());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityScorpion.class, new LOTRRenderScorpion());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityBird.class, new LOTRRenderBird());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityCamel.class, new LOTRRenderCamel());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityBandit.class, new LOTRRenderBandit("bandit"));
        RenderingRegistry.registerEntityRenderingHandler(LOTREntitySaruman.class, new LOTRRenderSaruman());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityInvasionSpawner.class, new LOTRRenderInvasionSpawner());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityElk.class, new LOTRRenderElk());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityMirkTroll.class, new LOTRRenderMirkTroll());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityTermite.class, new LOTRRenderTermite());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityThrownTermite.class, new RenderSnowball(LOTRMod.termite));
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityDikDik.class, new LOTRRenderDikDik());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityUtumnoIceSpider.class, new LOTRRenderUtumnoIceSpider());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityConker.class, new RenderSnowball(LOTRMod.chestnut));
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityUtumnoTroll.class, new LOTRRenderUtumnoTroll());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityBalrog.class, new LOTRRenderBalrog());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityHalfTroll.class, new LOTRRenderHalfTroll());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityHalfTrollScavenger.class, new LOTRRenderHalfTrollScavenger());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityGaladhrimSmith.class, new LOTRRenderElvenSmith("galadhrimSmith_cloak", "galadhrimSmith_cape"));
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityHighElfSmith.class, new LOTRRenderElvenSmith("highElfSmith_cloak", "highElfSmith_cape"));
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityWoodElfSmith.class, new LOTRRenderElvenSmith("woodElfSmith_cloak", "woodElfSmith_cape"));
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityDolAmrothSoldier.class, new LOTRRenderSwanKnight());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntitySwan.class, new LOTRRenderSwan());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityMoredain.class, new LOTRRenderMoredain());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityAngmarHillman.class, new LOTRRenderAngmarHillman(true));
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityAngmarHillmanWarrior.class, new LOTRRenderAngmarHillman(false));
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityIronHillsMerchant.class, new LOTRRenderDwarfCommander());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityBossTrophy.class, new LOTRRenderBossTrophy());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityMallornEnt.class, new LOTRRenderMallornEnt());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityMallornLeafBomb.class, new LOTRRenderMallornLeafBomb());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityScrapTrader.class, new LOTRRenderScrapTrader());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityTauredain.class, new LOTRRenderTauredain());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityDart.class, new LOTRRenderDart());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityBarrowWight.class, new LOTRRenderBarrowWight());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityTauredainShaman.class, new LOTRRenderTauredainShaman());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityGaladhrimWarden.class, new LOTRRenderGaladhrimWarden());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityDwarfSmith.class, new LOTRRenderDwarfSmith());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityBlueMountainsSmith.class, new LOTRRenderDwarfSmith());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityBanditHarad.class, new LOTRRenderBandit("harad"));
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityDeer.class, new LOTRRenderDeer());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityDaleMan.class, new LOTRRenderDaleMan());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityDaleBlacksmith.class, new LOTRRenderDaleTrader("blacksmith_apron"));
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityNPCRespawner.class, new LOTRRenderNPCRespawner());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityDorwinionMan.class, new LOTRRenderDorwinionMan());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityDaleBaker.class, new LOTRRenderDaleTrader("baker_apron"));
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityDorwinionElfVintner.class, new LOTRRenderDorwinionElfVintner());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityAurochs.class, new LOTRRenderAurochs());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityKineAraw.class, new LOTRRenderKineAraw());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityGondorMan.class, new LOTRRenderGondorMan());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityFallingTreasure.class, new LOTRRenderFallingCoinPile());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityGondorBartender.class, new LOTRRenderGondorTrader("outfit_bartender"));
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityGondorGreengrocer.class, new LOTRRenderGondorTrader("outfit_greengrocer"));
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityGondorMason.class, new LOTRRenderGondorTrader("outfit_mason"));
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityGondorBrewer.class, new LOTRRenderGondorTrader("outfit_brewer"));
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityGondorFlorist.class, new LOTRRenderGondorTrader("outfit_florist"));
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityGondorButcher.class, new LOTRRenderGondorTrader("outfit_butcher"));
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityGondorBaker.class, new LOTRRenderGondorTrader("outfit_baker"));
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityDunedain.class, new LOTRRenderDunedain());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityDunedainBlacksmith.class, new LOTRRenderDunedainTrader("outfit_blacksmith"));
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityRohanBuilder.class, new LOTRRenderRohanTrader("outfit_builder"));
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityRohanBrewer.class, new LOTRRenderRohanTrader("outfit_brewer"));
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityRohanButcher.class, new LOTRRenderRohanTrader("outfit_butcher"));
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityRohanBaker.class, new LOTRRenderRohanTrader("outfit_baker"));
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityRohanOrcharder.class, new LOTRRenderRohanTrader("outfit_orcharder"));
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityBear.class, new LOTRRenderBear());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityEasterling.class, new LOTRRenderEasterling());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityEasterlingBlacksmith.class, new LOTRRenderEasterlingTrader("outfit_blacksmith"));
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityFallingFireJar.class, new LOTRRenderFallingFireJar());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityFirePot.class, new RenderSnowball(LOTRMod.rhunFirePot));
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityRivendellSmith.class, new LOTRRenderElvenSmith("rivendellSmith_cloak", "rivendellSmith_cape"));
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityRivendellTrader.class, new LOTRRenderElvenTrader("rivendellTrader_cloak"));
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityFish.class, new LOTRRenderFish());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityArrowPoisoned.class, new LOTRRenderArrowPoisoned());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityNearHaradBlacksmith.class, new LOTRRenderNearHaradTrader("outfit_blacksmith"));
        RenderingRegistry.registerEntityRenderingHandler(LOTREntitySnowTroll.class, new LOTRRenderSnowTroll());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityLionRug.class, new LOTRRenderLionRug());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityBearRug.class, new LOTRRenderBearRug());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityGiraffeRug.class, new LOTRRenderGiraffeRug());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityHaradSlave.class, new LOTRRenderHaradSlave());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityGondorRenegade.class, new LOTRRenderGondorRenegade());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityHarnedorBartender.class, new LOTRRenderHaradrimTrader("outfit_bartender"));
        RenderingRegistry.registerEntityRenderingHandler(LOTREntitySouthronBartender.class, new LOTRRenderHaradrimTrader("outfit_bartender"));
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityUmbarBartender.class, new LOTRRenderHaradrimTrader("outfit_bartender"));
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityGulfBartender.class, new LOTRRenderHaradrimTrader("outfit_bartender"));
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityWhiteOryx.class, new LOTRRenderWhiteOryx());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityGandalf.class, new LOTRRenderGandalf());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityWickedDwarf.class, new LOTRRenderWickedDwarf());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityBreeMan.class, new LOTRRenderBreeMan());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntitySwordCommandMarker.class, new LOTRRenderSwordCommandMarker());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityBreeBlacksmith.class, new LOTRRenderBreeTrader("outfit_blacksmith"));
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityBreeInnkeeper.class, new LOTRRenderBreeTrader("outfit_innkeeper"));
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityBreeRuffian.class, new LOTRRenderBreeRuffian());
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityBreeHobbitInnkeeper.class, new LOTRRenderHobbitTrader("outfit_bartender"));
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityBreeBaker.class, new LOTRRenderBreeTrader("outfit_baker"));
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityBreeButcher.class, new LOTRRenderBreeTrader("outfit_butcher"));
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityBreeBrewer.class, new LOTRRenderBreeTrader("outfit_brewer"));
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityBreeMason.class, new LOTRRenderBreeTrader("outfit_mason"));
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityBreeFlorist.class, new LOTRRenderBreeTrader("outfit_florist"));
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityBreeHobbitBaker.class, new LOTRRenderHobbitTrader("outfit_baker"));
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityBreeHobbitButcher.class, new LOTRRenderHobbitTrader("outfit_butcher"));
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityBreeHobbitBrewer.class, new LOTRRenderHobbitTrader("outfit_brewer"));
        RenderingRegistry.registerEntityRenderingHandler(LOTREntityBreeHobbitFlorist.class, new LOTRRenderHobbitTrader("outfit_florist"));
        RenderingRegistry.registerEntityRenderingHandler(EntityPotion.class, new RenderSnowball(Items.potionitem, 16384));
        this.beaconRenderID = RenderingRegistry.getNextAvailableRenderId();
        this.barrelRenderID = RenderingRegistry.getNextAvailableRenderId();
        this.orcBombRenderID = RenderingRegistry.getNextAvailableRenderId();
        this.doubleTorchRenderID = RenderingRegistry.getNextAvailableRenderId();
        this.mobSpawnerRenderID = RenderingRegistry.getNextAvailableRenderId();
        this.plateRenderID = RenderingRegistry.getNextAvailableRenderId();
        this.stalactiteRenderID = RenderingRegistry.getNextAvailableRenderId();
        this.flowerPotRenderID = RenderingRegistry.getNextAvailableRenderId();
        this.cloverRenderID = RenderingRegistry.getNextAvailableRenderId();
        this.entJarRenderID = RenderingRegistry.getNextAvailableRenderId();
        this.trollTotemRenderID = RenderingRegistry.getNextAvailableRenderId();
        this.fenceRenderID = RenderingRegistry.getNextAvailableRenderId();
        this.grassRenderID = RenderingRegistry.getNextAvailableRenderId();
        this.fallenLeavesRenderID = RenderingRegistry.getNextAvailableRenderId();
        this.commandTableRenderID = RenderingRegistry.getNextAvailableRenderId();
        this.butterflyJarRenderID = RenderingRegistry.getNextAvailableRenderId();
        this.unsmelteryRenderID = RenderingRegistry.getNextAvailableRenderId();
        this.chestRenderID = RenderingRegistry.getNextAvailableRenderId();
        this.reedsRenderID = RenderingRegistry.getNextAvailableRenderId();
        this.wasteRenderID = RenderingRegistry.getNextAvailableRenderId();
        this.beamRenderID = RenderingRegistry.getNextAvailableRenderId();
        this.vCauldronRenderID = RenderingRegistry.getNextAvailableRenderId();
        this.grapevineRenderID = RenderingRegistry.getNextAvailableRenderId();
        this.thatchFloorRenderID = RenderingRegistry.getNextAvailableRenderId();
        this.treasureRenderID = RenderingRegistry.getNextAvailableRenderId();
        this.flowerRenderID = RenderingRegistry.getNextAvailableRenderId();
        this.doublePlantRenderID = RenderingRegistry.getNextAvailableRenderId();
        this.birdCageRenderID = RenderingRegistry.getNextAvailableRenderId();
        this.rhunFireJarRenderID = RenderingRegistry.getNextAvailableRenderId();
        this.coralRenderID = RenderingRegistry.getNextAvailableRenderId();
        this.doorRenderID = RenderingRegistry.getNextAvailableRenderId();
        this.ropeRenderID = RenderingRegistry.getNextAvailableRenderId();
        this.orcChainRenderID = RenderingRegistry.getNextAvailableRenderId();
        this.guldurilRenderID = RenderingRegistry.getNextAvailableRenderId();
        this.orcPlatingRenderID = RenderingRegistry.getNextAvailableRenderId();
        this.trapdoorRenderID = RenderingRegistry.getNextAvailableRenderId();
        RenderingRegistry.registerBlockHandler(this.beaconRenderID, new LOTRRenderBlocks(true));
        RenderingRegistry.registerBlockHandler(this.barrelRenderID, new LOTRRenderBlocks(true));
        RenderingRegistry.registerBlockHandler(this.orcBombRenderID, new LOTRRenderBlocks(true));
        RenderingRegistry.registerBlockHandler(this.doubleTorchRenderID, new LOTRRenderBlocks(false));
        RenderingRegistry.registerBlockHandler(this.mobSpawnerRenderID, new LOTRRenderBlocks(true));
        RenderingRegistry.registerBlockHandler(this.plateRenderID, new LOTRRenderBlocks(false));
        RenderingRegistry.registerBlockHandler(this.stalactiteRenderID, new LOTRRenderBlocks(true));
        RenderingRegistry.registerBlockHandler(this.flowerPotRenderID, new LOTRRenderBlocks(false));
        RenderingRegistry.registerBlockHandler(this.cloverRenderID, new LOTRRenderBlocks(true));
        RenderingRegistry.registerBlockHandler(this.entJarRenderID, new LOTRRenderBlocks(true));
        RenderingRegistry.registerBlockHandler(this.trollTotemRenderID, new LOTRRenderBlocks(true));
        RenderingRegistry.registerBlockHandler(this.fenceRenderID, new LOTRRenderBlocks(true));
        RenderingRegistry.registerBlockHandler(this.grassRenderID, new LOTRRenderBlocks(false));
        RenderingRegistry.registerBlockHandler(this.fallenLeavesRenderID, new LOTRRenderBlocks(false));
        RenderingRegistry.registerBlockHandler(this.commandTableRenderID, new LOTRRenderBlocks(true));
        RenderingRegistry.registerBlockHandler(this.butterflyJarRenderID, new LOTRRenderBlocks(true));
        RenderingRegistry.registerBlockHandler(this.unsmelteryRenderID, new LOTRRenderBlocks(true));
        RenderingRegistry.registerBlockHandler(this.chestRenderID, new LOTRRenderBlocks(true));
        RenderingRegistry.registerBlockHandler(this.reedsRenderID, new LOTRRenderBlocks(false));
        RenderingRegistry.registerBlockHandler(this.wasteRenderID, new LOTRRenderBlocks(true));
        RenderingRegistry.registerBlockHandler(this.beamRenderID, new LOTRRenderBlocks(true));
        RenderingRegistry.registerBlockHandler(this.vCauldronRenderID, new LOTRRenderBlocks(false));
        RenderingRegistry.registerBlockHandler(this.grapevineRenderID, new LOTRRenderBlocks(true));
        RenderingRegistry.registerBlockHandler(this.thatchFloorRenderID, new LOTRRenderBlocks(false));
        RenderingRegistry.registerBlockHandler(this.treasureRenderID, new LOTRRenderBlocks(true));
        RenderingRegistry.registerBlockHandler(this.flowerRenderID, new LOTRRenderBlocks(false));
        RenderingRegistry.registerBlockHandler(this.doublePlantRenderID, new LOTRRenderBlocks(false));
        RenderingRegistry.registerBlockHandler(this.birdCageRenderID, new LOTRRenderBlocks(true));
        RenderingRegistry.registerBlockHandler(this.rhunFireJarRenderID, new LOTRRenderBlocks(true));
        RenderingRegistry.registerBlockHandler(this.coralRenderID, new LOTRRenderBlocks(true));
        RenderingRegistry.registerBlockHandler(this.doorRenderID, new LOTRRenderBlocks(false));
        RenderingRegistry.registerBlockHandler(this.ropeRenderID, new LOTRRenderBlocks(false));
        RenderingRegistry.registerBlockHandler(this.orcChainRenderID, new LOTRRenderBlocks(false));
        RenderingRegistry.registerBlockHandler(this.guldurilRenderID, new LOTRRenderBlocks(true));
        RenderingRegistry.registerBlockHandler(this.orcPlatingRenderID, new LOTRRenderBlocks(true));
        RenderingRegistry.registerBlockHandler(this.trapdoorRenderID, new LOTRRenderBlocks(true));
        ClientRegistry.bindTileEntitySpecialRenderer(LOTRTileEntityBeacon.class, new LOTRRenderBeacon());
        ClientRegistry.bindTileEntitySpecialRenderer(LOTRTileEntityMobSpawner.class, new LOTRTileEntityMobSpawnerRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(LOTRTileEntityPlate.class, new LOTRRenderPlateFood());
        ClientRegistry.bindTileEntitySpecialRenderer(LOTRTileEntityElvenPortal.class, new LOTRRenderElvenPortal());
        ClientRegistry.bindTileEntitySpecialRenderer(LOTRTileEntitySpawnerChest.class, new LOTRRenderSpawnerChest());
        ClientRegistry.bindTileEntitySpecialRenderer(LOTRTileEntityGulduril.class, new LOTRRenderGuldurilGlow());
        ClientRegistry.bindTileEntitySpecialRenderer(LOTRTileEntityDwarvenDoor.class, new LOTRRenderDwarvenDoor());
        ClientRegistry.bindTileEntitySpecialRenderer(LOTRTileEntityMorgulPortal.class, new LOTRRenderMorgulPortal());
        ClientRegistry.bindTileEntitySpecialRenderer(LOTRTileEntityArmorStand.class, new LOTRRenderArmorStand());
        ClientRegistry.bindTileEntitySpecialRenderer(LOTRTileEntityMug.class, new LOTRRenderMug());
        ClientRegistry.bindTileEntitySpecialRenderer(LOTRTileEntityEntJar.class, new LOTRRenderEntJar());
        ClientRegistry.bindTileEntitySpecialRenderer(LOTRTileEntityTrollTotem.class, new LOTRRenderTrollTotem());
        ClientRegistry.bindTileEntitySpecialRenderer(LOTRTileEntityUtumnoPortal.class, new LOTRRenderUtumnoPortal());
        ClientRegistry.bindTileEntitySpecialRenderer(LOTRTileEntityUtumnoReturnPortal.class, new LOTRRenderUtumnoReturnPortal());
        ClientRegistry.bindTileEntitySpecialRenderer(LOTRTileEntityCommandTable.class, new LOTRRenderCommandTable());
        ClientRegistry.bindTileEntitySpecialRenderer(LOTRTileEntityAnimalJar.class, new LOTRRenderAnimalJar());
        ClientRegistry.bindTileEntitySpecialRenderer(LOTRTileEntityUnsmeltery.class, new LOTRRenderUnsmeltery());
        ClientRegistry.bindTileEntitySpecialRenderer(LOTRTileEntityDartTrap.class, new LOTRRenderDartTrap());
        ClientRegistry.bindTileEntitySpecialRenderer(LOTRTileEntityChest.class, new LOTRRenderChest());
        ClientRegistry.bindTileEntitySpecialRenderer(LOTRTileEntityWeaponRack.class, new LOTRRenderWeaponRack());
        ClientRegistry.bindTileEntitySpecialRenderer(LOTRTileEntityKebabStand.class, new LOTRRenderKebabStand());
        ClientRegistry.bindTileEntitySpecialRenderer(LOTRTileEntitySignCarved.class, new LOTRRenderSignCarved());
        ClientRegistry.bindTileEntitySpecialRenderer(LOTRTileEntitySignCarvedIthildin.class, new LOTRRenderSignCarvedIthildin());
    }

    @Override
    public void onPostload() {
        if (LOTRConfig.updateLangFiles) {
            LOTRLang.runUpdateThread();
        }
        musicHandler = new LOTRMusic();
    }

    @Override
    public void testReflection(World world) {
        super.testReflection(world);
        LOTRReflectionClient.testAll(world, Minecraft.getMinecraft());
    }

    public static void renderEnchantmentEffect() {
        Tessellator tessellator = Tessellator.instance;
        TextureManager texturemanager = Minecraft.getMinecraft().getTextureManager();
        GL11.glDepthFunc(514);
        GL11.glDisable(2896);
        texturemanager.bindTexture(enchantmentTexture);
        GL11.glEnable(3042);
        GL11.glBlendFunc(768, 1);
        float shade = 0.76f;
        GL11.glColor4f(0.5f * shade, 0.25f * shade, 0.8f * shade, 1.0f);
        GL11.glMatrixMode(5890);
        GL11.glPushMatrix();
        float scale = 0.125f;
        GL11.glScalef(scale, scale, scale);
        float randomShift = Minecraft.getSystemTime() % 3000L / 3000.0f * 8.0f;
        GL11.glTranslatef(randomShift, 0.0f, 0.0f);
        GL11.glRotatef(-50.0f, 0.0f, 0.0f, 1.0f);
        ItemRenderer.renderItemIn2D(tessellator, 0.0f, 0.0f, 1.0f, 1.0f, 256, 256, 0.0625f);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glScalef(scale, scale, scale);
        randomShift = Minecraft.getSystemTime() % 4873L / 4873.0f * 8.0f;
        GL11.glTranslatef((-randomShift), 0.0f, 0.0f);
        GL11.glRotatef(10.0f, 0.0f, 0.0f, 1.0f);
        ItemRenderer.renderItemIn2D(tessellator, 0.0f, 0.0f, 1.0f, 1.0f, 256, 256, 0.0625f);
        GL11.glPopMatrix();
        GL11.glMatrixMode(5888);
        GL11.glDisable(3042);
        GL11.glEnable(2896);
        GL11.glDepthFunc(515);
    }

    public static void sendClientInfoPacket(LOTRFaction viewingFaction, Map<LOTRDimension.DimensionRegion, LOTRFaction> changedRegionMap) {
        boolean showWP = LOTRGuiMap.showWP;
        boolean showCWP = LOTRGuiMap.showCWP;
        boolean showHiddenSWP = LOTRGuiMap.showHiddenSWP;
        LOTRPacketClientInfo packet = new LOTRPacketClientInfo(viewingFaction, changedRegionMap, showWP, showCWP, showHiddenSWP);
        LOTRPacketHandler.networkWrapper.sendToServer(packet);
    }

    public static int getAlphaInt(float alphaF) {
        int alphaI = (int)(alphaF * 255.0f);
        alphaI = MathHelper.clamp_int(alphaI, 4, 255);
        return alphaI;
    }

    @Override
    public boolean isClient() {
        return FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT;
    }

    @Override
    public boolean isSingleplayer() {
        return Minecraft.getMinecraft().isSingleplayer();
    }

    @Override
    public World getClientWorld() {
        return Minecraft.getMinecraft().theWorld;
    }

    @Override
    public EntityPlayer getClientPlayer() {
        return Minecraft.getMinecraft().thePlayer;
    }

    public static boolean doesClientChunkExist(World world, int i, int k) {
        int chunkX = i >> 4;
        int chunkZ = k >> 4;
        Chunk chunk = world.getChunkProvider().provideChunk(chunkX, chunkZ);
        return !(chunk instanceof EmptyChunk);
    }

    @Override
    public boolean isPaused() {
        return Minecraft.getMinecraft().isGamePaused();
    }

    @Override
    public void setClientDifficulty(EnumDifficulty difficulty) {
        Minecraft.getMinecraft().gameSettings.difficulty = difficulty;
    }

    @Override
    public void setWaypointModes(boolean showWP, boolean showCWP, boolean showHiddenSWP) {
        LOTRGuiMap.showWP = showWP;
        LOTRGuiMap.showCWP = showCWP;
        LOTRGuiMap.showHiddenSWP = showHiddenSWP;
    }

    @Override
    public void spawnAlignmentBonus(LOTRFaction faction, float prevMainAlignment, LOTRAlignmentBonusMap factionBonusMap, String name, boolean isKill, boolean isHiredKill, float conquestBonus, double posX, double posY, double posZ) {
        World world = this.getClientWorld();
        if (world != null) {
            LOTREntityAlignmentBonus entity = new LOTREntityAlignmentBonus(world, posX, posY, posZ, name, faction, prevMainAlignment, factionBonusMap, isKill, isHiredKill, conquestBonus);
            world.spawnEntityInWorld(entity);
        }
    }

    @Override
    public void displayAlignDrain(int numFactions) {
        LOTRTickHandlerClient.alignDrainTick = 200;
        LOTRTickHandlerClient.alignDrainNum = numFactions;
    }

    @Override
    public void queueAchievement(LOTRAchievement achievement) {
        LOTRTickHandlerClient.notificationDisplay.queueAchievement(achievement);
    }

    @Override
    public void queueFellowshipNotification(IChatComponent message) {
        LOTRTickHandlerClient.notificationDisplay.queueFellowshipNotification(message);
    }

    @Override
    public void queueConquestNotification(LOTRFaction fac, float conq, boolean isCleansing) {
        LOTRTickHandlerClient.notificationDisplay.queueConquest(fac, conq, isCleansing);
    }

    @Override
    public void displayMessage(LOTRGuiMessageTypes message) {
        Minecraft.getMinecraft().displayGuiScreen(new LOTRGuiMessage(message));
    }

    @Override
    public void openHiredNPCGui(LOTREntityNPC npc) {
        Minecraft mc = Minecraft.getMinecraft();
        if (npc.hiredNPCInfo.getTask() == LOTRHiredNPCInfo.Task.WARRIOR) {
            mc.displayGuiScreen(new LOTRGuiHiredWarrior(npc));
        } else if (npc.hiredNPCInfo.getTask() == LOTRHiredNPCInfo.Task.FARMER) {
            mc.displayGuiScreen(new LOTRGuiHiredFarmer(npc));
        }
    }

    @Override
    public void setMapIsOp(boolean isOp) {
        Minecraft mc = Minecraft.getMinecraft();
        GuiScreen gui = mc.currentScreen;
        if (gui instanceof LOTRGuiMap) {
            LOTRGuiMap map = (LOTRGuiMap)gui;
            map.isPlayerOp = isOp;
        }
    }

    @Override
    public void displayFTScreen(LOTRAbstractWaypoint waypoint, int startX, int startZ) {
        Minecraft mc = Minecraft.getMinecraft();
        mc.displayGuiScreen(new LOTRGuiFastTravel(waypoint, startX, startZ));
    }

    @Override
    public void showFrostOverlay() {
        tickHandler.onFrostDamage();
    }

    @Override
    public void showBurnOverlay() {
        tickHandler.onBurnDamage();
    }

    @Override
    public void clearMapPlayerLocations() {
        LOTRGuiMap.clearPlayerLocations();
    }

    @Override
    public void addMapPlayerLocation(GameProfile player, double posX, double posZ) {
        LOTRGuiMap.addPlayerLocationInfo(player, posX, posZ);
    }

    @Override
    public void setMapCWPProtectionMessage(IChatComponent message) {
        Minecraft mc = Minecraft.getMinecraft();
        GuiScreen gui = mc.currentScreen;
        if (gui instanceof LOTRGuiMap) {
            ((LOTRGuiMap)gui).setCWPProtectionMessage(message);
        }
    }

    @Override
    public void displayBannerGui(LOTREntityBanner banner) {
        Minecraft mc = Minecraft.getMinecraft();
        LOTRGuiBanner gui = new LOTRGuiBanner(banner);
        mc.displayGuiScreen(gui);
    }

    @Override
    public void validateBannerUsername(LOTREntityBanner banner, int slot, String prevText, boolean valid) {
        Minecraft mc = Minecraft.getMinecraft();
        GuiScreen gui = mc.currentScreen;
        if (gui instanceof LOTRGuiBanner) {
            LOTRGuiBanner guiBanner = (LOTRGuiBanner)gui;
            if (guiBanner.theBanner == banner) {
                guiBanner.validateUsername(slot, prevText, valid);
            }
        }
    }

    @Override
    public void clientReceiveSpeech(LOTREntityNPC npc, String speech) {
        LOTRSpeechClient.receiveSpeech(npc, speech);
    }

    @Override
    public void displayNewDate() {
        tickHandler.updateDate();
    }

    @Override
    public void displayMiniquestOffer(LOTRMiniQuest quest, LOTREntityNPC npc) {
        Minecraft mc = Minecraft.getMinecraft();
        mc.displayGuiScreen(new LOTRGuiMiniquestOffer(quest, npc));
    }

    @Override
    public void setTrackedQuest(LOTRMiniQuest quest) {
        LOTRTickHandlerClient.miniquestTracker.setTrackedQuest(quest);
    }

    @Override
    public void displayAlignmentSee(String username, Map<LOTRFaction, Float> alignments) {
        LOTRGuiFactions gui = new LOTRGuiFactions();
        gui.setOtherPlayer(username, alignments);
        Minecraft mc = Minecraft.getMinecraft();
        mc.displayGuiScreen(gui);
    }

    @Override
    public void displayAlignmentChoice() {
        Minecraft mc = Minecraft.getMinecraft();
        mc.displayGuiScreen(new LOTRGuiAlignmentChoices());
    }

    @Override
    public void cancelItemHighlight() {
        LOTRClientProxy.tickHandler.cancelItemHighlight = true;
    }

    @Override
    public void receiveConquestGrid(LOTRFaction conqFac, List<LOTRConquestZone> allZones) {
        Minecraft mc = Minecraft.getMinecraft();
        GuiScreen gui = mc.currentScreen;
        if (gui instanceof LOTRGuiMap) {
            ((LOTRGuiMap)gui).receiveConquestGrid(conqFac, allZones);
        }
    }

    @Override
    public void handleInvasionWatch(int invasionEntityID, boolean overrideAlreadyWatched) {
        Entity e;
        LOTRInvasionStatus status = LOTRTickHandlerClient.watchedInvasion;
        if ((overrideAlreadyWatched || !status.isActive()) && (e = (this.getClientWorld()).getEntityByID(invasionEntityID)) instanceof LOTREntityInvasionSpawner) {
            status.setWatchedInvasion((LOTREntityInvasionSpawner)e);
        }
    }

    @Override
    public void setInPortal(EntityPlayer entityplayer) {
        if (!LOTRTickHandlerClient.playersInPortals.containsKey(entityplayer)) {
            LOTRTickHandlerClient.playersInPortals.put(entityplayer, 0);
        }
        if (Minecraft.getMinecraft().isSingleplayer() && !LOTRTickHandlerServer.playersInPortals.containsKey(entityplayer)) {
            LOTRTickHandlerServer.playersInPortals.put(entityplayer, 0);
        }
    }

    @Override
    public void setInElvenPortal(EntityPlayer entityplayer) {
        if (!LOTRTickHandlerClient.playersInElvenPortals.containsKey(entityplayer)) {
            LOTRTickHandlerClient.playersInElvenPortals.put(entityplayer, 0);
        }
        if (Minecraft.getMinecraft().isSingleplayer() && !LOTRTickHandlerServer.playersInElvenPortals.containsKey(entityplayer)) {
            LOTRTickHandlerServer.playersInElvenPortals.put(entityplayer, 0);
        }
    }

    @Override
    public void setInMorgulPortal(EntityPlayer entityplayer) {
        if (!LOTRTickHandlerClient.playersInMorgulPortals.containsKey(entityplayer)) {
            LOTRTickHandlerClient.playersInMorgulPortals.put(entityplayer, 0);
        }
        if (Minecraft.getMinecraft().isSingleplayer() && !LOTRTickHandlerServer.playersInMorgulPortals.containsKey(entityplayer)) {
            LOTRTickHandlerServer.playersInMorgulPortals.put(entityplayer, 0);
        }
    }

    @Override
    public void setInUtumnoReturnPortal(EntityPlayer entityplayer) {
        if (entityplayer == Minecraft.getMinecraft().thePlayer) {
            LOTRClientProxy.tickHandler.inUtumnoReturnPortal = true;
        }
    }

    @Override
    public void setUtumnoReturnPortalCoords(EntityPlayer entityplayer, int x, int z) {
        if (entityplayer == Minecraft.getMinecraft().thePlayer) {
            LOTRClientProxy.tickHandler.inUtumnoReturnPortal = true;
            LOTRClientProxy.tickHandler.utumnoReturnX = x;
            LOTRClientProxy.tickHandler.utumnoReturnZ = z;
        }
    }

    @Override
    public void spawnParticle(String type, double d, double d1, double d2, double d3, double d4, double d5) {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.renderViewEntity != null && mc.theWorld != null) {
            WorldClient world = mc.theWorld;
            Random rand = world.rand;
            int i = mc.gameSettings.particleSetting;
            if (i == 1 && rand.nextInt(3) == 0) {
                i = 2;
            }
            if (i > 1) {
                return;
            }
            if (type.equals("angry")) {
                customEffectRenderer.addEffect(new LOTREntityAngryFX(world, d, d1, d2, d3, d4, d5));
            } else if (type.equals("blueFlame")) {
                customEffectRenderer.addEffect(new LOTREntityBlueFlameFX(world, d, d1, d2, d3, d4, d5));
            } else if (type.equals("chill")) {
                mc.effectRenderer.addEffect(new LOTREntityChillFX(world, d, d1, d2, d3, d4, d5));
            } else if (type.startsWith("elvenGlow")) {
                LOTREntityElvenGlowFX fx = new LOTREntityElvenGlowFX(world, d, d1, d2, d3, d4, d5);
                int subIndex = type.indexOf("_");
                if (subIndex > -1) {
                    String hex = type.substring(subIndex + 1);
                    int color = Integer.parseInt(hex, 16);
                    fx.setElvenGlowColor(color);
                }
                mc.effectRenderer.addEffect(fx);
            } else if (type.equals("gandalfFireball")) {
                mc.effectRenderer.addEffect(new LOTREntityGandalfFireballExplodeFX(world, d, d1, d2));
            } else if (type.equals("largeStone")) {
                mc.effectRenderer.addEffect(new LOTREntityLargeBlockFX(world, d, d1, d2, d3, d4, d5, Blocks.stone, 0));
            } else if (type.startsWith("leaf")) {
                String s = type.substring("leaf".length());
                int[] texIndices = null;
                if (s.startsWith("Gold")) {
                    texIndices = rand.nextBoolean() ? LOTRFunctions.intRange(0, 5) : LOTRFunctions.intRange(8, 13);
                } else if (s.startsWith("Red")) {
                    texIndices = rand.nextBoolean() ? LOTRFunctions.intRange(16, 21) : LOTRFunctions.intRange(24, 29);
                } else if (s.startsWith("Mirk")) {
                    texIndices = rand.nextBoolean() ? LOTRFunctions.intRange(32, 37) : LOTRFunctions.intRange(40, 45);
                } else if (s.startsWith("Green")) {
                    texIndices = rand.nextBoolean() ? LOTRFunctions.intRange(48, 53) : LOTRFunctions.intRange(56, 61);
                }
                if (texIndices != null) {
                    if (type.indexOf("_") > -1) {
                        int age = Integer.parseInt(type.substring(type.indexOf("_") + 1));
                        customEffectRenderer.addEffect(new LOTREntityLeafFX(world, d, d1, d2, d3, d4, d5, texIndices, age));
                    } else {
                        customEffectRenderer.addEffect(new LOTREntityLeafFX(world, d, d1, d2, d3, d4, d5, texIndices));
                    }
                }
            } else if (type.equals("marshFlame")) {
                mc.effectRenderer.addEffect(new LOTREntityMarshFlameFX(world, d, d1, d2, d3, d4, d5));
            } else if (type.equals("marshLight")) {
                customEffectRenderer.addEffect(new LOTREntityMarshLightFX(world, d, d1, d2, d3, d4, d5));
            } else if (type.startsWith("mEntHeal")) {
                String[] args = type.split("_", 3);
                Block block = Block.getBlockById(Integer.parseInt(args[1]));
                int meta = Integer.parseInt(args[2]);
                int color = block.getRenderColor(meta);
                mc.effectRenderer.addEffect(new LOTREntityMallornEntHealFX(world, d, d1, d2, d3, d4, d5, block, meta, color));
            } else if (type.equals("mEntJumpSmash")) {
                mc.effectRenderer.addEffect(new LOTREntityLargeBlockFX(world, d, d1, d2, d3, d4, d5, LOTRMod.wood, 1));
            } else if (type.equals("mEntSpawn")) {
                Block block = null;
                int meta = 0;
                if (world.rand.nextBoolean()) {
                    block = Blocks.dirt;
                    meta = 0;
                } else {
                    block = LOTRMod.wood;
                    meta = 1;
                }
                mc.effectRenderer.addEffect(new LOTREntityBossSpawnFX(world, d, d1, d2, d3, d4, d5, block, meta));
            } else if (type.startsWith("mEntSummon")) {
                String[] args = type.split("_", 6);
                int summonerID = Integer.parseInt(args[1]);
                int summonedID = Integer.parseInt(args[2]);
                float arcParam = Float.parseFloat(args[3]);
                Block block = Block.getBlockById(Integer.parseInt(args[4]));
                int meta = Integer.parseInt(args[5]);
                int color = block.getRenderColor(meta);
                mc.effectRenderer.addEffect(new LOTREntityMallornEntSummonFX(world, d, d1, d2, d3, d4, d5, summonerID, summonedID, arcParam, block, meta, color));
            } else if (type.equals("mirkwoodWater")) {
                mc.effectRenderer.addEffect(new LOTREntityRiverWaterFX(world, d, d1, d2, d3, d4, d5, LOTRBiome.mirkwoodCorrupted.getWaterColorMultiplier()));
            } else if (type.equals("morgulPortal")) {
                mc.effectRenderer.addEffect(new LOTREntityMorgulPortalFX(world, d, d1, d2, d3, d4, d5));
            } else if (type.equals("morgulWater")) {
                mc.effectRenderer.addEffect(new LOTREntityRiverWaterFX(world, d, d1, d2, d3, d4, d5, LOTRBiome.morgulVale.getWaterColorMultiplier()));
            } else if (type.equals("mtcArmor")) {
                mc.effectRenderer.addEffect(new LOTREntityLargeBlockFX(world, d, d1, d2, d3, d4, d5, Blocks.iron_block, 0));
            } else if (type.equals("mtcHeal")) {
                mc.effectRenderer.addEffect(new LOTREntityMTCHealFX(world, d, d1, d2, d3, d4, d5));
            } else if (type.equals("mtcSpawn")) {
                Block block = null;
                int meta = 0;
                if (world.rand.nextBoolean()) {
                    block = Blocks.stone;
                    meta = 0;
                } else if (world.rand.nextBoolean()) {
                    block = Blocks.dirt;
                    meta = 0;
                } else if (world.rand.nextBoolean()) {
                    block = Blocks.gravel;
                    meta = 0;
                } else {
                    block = Blocks.sand;
                    meta = 0;
                }
                mc.effectRenderer.addEffect(new LOTREntityBossSpawnFX(world, d, d1, d2, d3, d4, d5, block, meta));
            } else if (type.equals("music")) {
                double pitch = world.rand.nextDouble();
                LOTREntityMusicFX note = new LOTREntityMusicFX(world, d, d1, d2, d3, d4, d5, pitch);
                mc.effectRenderer.addEffect(note);
            } else if (type.equals("pickpocket")) {
                customEffectRenderer.addEffect(new LOTREntityPickpocketFX(world, d, d1, d2, d3, d4, d5));
            } else if (type.equals("pickpocketFail")) {
                customEffectRenderer.addEffect(new LOTREntityPickpocketFailFX(world, d, d1, d2, d3, d4, d5));
            } else if (type.equals("quenditeSmoke")) {
                mc.effectRenderer.addEffect(new LOTREntityQuenditeSmokeFX(world, d, d1, d2, d3, d4, d5));
            } else if (type.equals("utumnoKill")) {
                customEffectRenderer.addEffect(new LOTREntityUtumnoKillFX(world, d, d1, d2, d3, d4, d5));
            } else if (type.equals("wave")) {
                mc.effectRenderer.addEffect(new LOTREntityWaveFX(world, d, d1, d2, d3, d4, d5));
            } else if (type.equals("whiteSmoke")) {
                mc.effectRenderer.addEffect(new LOTREntityWhiteSmokeFX(world, d, d1, d2, d3, d4, d5));
            }
        }
    }

    @Override
    public void placeFlowerInPot(World world, int i, int j, int k, int side, ItemStack itemstack) {
        if (!world.isRemote) {
            super.placeFlowerInPot(world, i, j, k, side, itemstack);
        } else {
            Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(i, j, k, side, itemstack, 0.0f, 0.0f, 0.0f));
        }
    }

    @Override
    public void fillMugFromCauldron(World world, int i, int j, int k, int side, ItemStack itemstack) {
        if (!world.isRemote) {
            super.fillMugFromCauldron(world, i, j, k, side, itemstack);
        } else {
            Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(i, j, k, side, itemstack, 0.0f, 0.0f, 0.0f));
        }
    }

    @Override
    public void usePouchOnChest(EntityPlayer entityplayer, World world, int i, int j, int k, int side, ItemStack itemstack, int pouchSlot) {
        if (!world.isRemote) {
            super.usePouchOnChest(entityplayer, world, i, j, k, side, itemstack, pouchSlot);
        } else {
            ((EntityClientPlayerMP)entityplayer).sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(i, j, k, side, itemstack, 0.0f, 0.0f, 0.0f));
        }
    }

    @Override
    public void renderCustomPotionEffect(int x, int y, PotionEffect effect, Minecraft mc) {
        Potion potion = Potion.potionTypes[effect.getPotionID()];
        mc.getTextureManager().bindTexture(customPotionsTexture);
        int l = potion.getStatusIconIndex();
        GuiScreen screen = mc.currentScreen;
        if (screen != null) {
            screen.drawTexturedModalRect(x + 6, y + 7, 0 + l % 8 * 18, 0 + l / 8 * 18, 18, 18);
        }
    }

    @Override
    public int getBeaconRenderID() {
        return this.beaconRenderID;
    }

    @Override
    public int getBarrelRenderID() {
        return this.barrelRenderID;
    }

    @Override
    public int getOrcBombRenderID() {
        return this.orcBombRenderID;
    }

    @Override
    public int getDoubleTorchRenderID() {
        return this.doubleTorchRenderID;
    }

    @Override
    public int getMobSpawnerRenderID() {
        return this.mobSpawnerRenderID;
    }

    @Override
    public int getPlateRenderID() {
        return this.plateRenderID;
    }

    @Override
    public int getStalactiteRenderID() {
        return this.stalactiteRenderID;
    }

    @Override
    public int getFlowerPotRenderID() {
        return this.flowerPotRenderID;
    }

    @Override
    public int getCloverRenderID() {
        return this.cloverRenderID;
    }

    @Override
    public int getEntJarRenderID() {
        return this.entJarRenderID;
    }

    @Override
    public int getTrollTotemRenderID() {
        return this.trollTotemRenderID;
    }

    @Override
    public int getFenceRenderID() {
        return this.fenceRenderID;
    }

    @Override
    public int getGrassRenderID() {
        return this.grassRenderID;
    }

    @Override
    public int getFallenLeavesRenderID() {
        return this.fallenLeavesRenderID;
    }

    @Override
    public int getCommandTableRenderID() {
        return this.commandTableRenderID;
    }

    @Override
    public int getButterflyJarRenderID() {
        return this.butterflyJarRenderID;
    }

    @Override
    public int getUnsmelteryRenderID() {
        return this.unsmelteryRenderID;
    }

    @Override
    public int getChestRenderID() {
        return this.chestRenderID;
    }

    @Override
    public int getReedsRenderID() {
        return this.reedsRenderID;
    }

    @Override
    public int getWasteRenderID() {
        return this.wasteRenderID;
    }

    @Override
    public int getBeamRenderID() {
        return this.beamRenderID;
    }

    @Override
    public int getVCauldronRenderID() {
        return this.vCauldronRenderID;
    }

    @Override
    public int getGrapevineRenderID() {
        return this.grapevineRenderID;
    }

    @Override
    public int getThatchFloorRenderID() {
        return this.thatchFloorRenderID;
    }

    @Override
    public int getTreasureRenderID() {
        return this.treasureRenderID;
    }

    @Override
    public int getFlowerRenderID() {
        return this.flowerRenderID;
    }

    @Override
    public int getDoublePlantRenderID() {
        return this.doublePlantRenderID;
    }

    @Override
    public int getBirdCageRenderID() {
        return this.birdCageRenderID;
    }

    @Override
    public int getRhunFireJarRenderID() {
        return this.rhunFireJarRenderID;
    }

    @Override
    public int getCoralRenderID() {
        return this.coralRenderID;
    }

    @Override
    public int getDoorRenderID() {
        return this.doorRenderID;
    }

    @Override
    public int getRopeRenderID() {
        return this.ropeRenderID;
    }

    @Override
    public int getOrcChainRenderID() {
        return this.orcChainRenderID;
    }

    @Override
    public int getGuldurilRenderID() {
        return this.guldurilRenderID;
    }

    @Override
    public int getOrcPlatingRenderID() {
        return this.orcPlatingRenderID;
    }

    @Override
    public int getTrapdoorRenderID() {
        return this.trapdoorRenderID;
    }

    static {
        specialPlayerRenderer = new LOTRRenderPlayer();
        swingHandler = new LOTRSwingHandler();
        tickHandler = new LOTRTickHandlerClient();
        keyHandler = new LOTRKeyHandler();
        guiHandler = new LOTRGuiHandler();
    }
}

