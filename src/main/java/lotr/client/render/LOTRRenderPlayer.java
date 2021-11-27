package lotr.client.render;

import java.util.List;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import lotr.client.*;
import lotr.client.render.entity.LOTRNPCRendering;
import lotr.common.*;
import lotr.common.fac.LOTRAlignmentValues;
import lotr.common.fellowship.LOTRFellowshipClient;
import lotr.common.world.LOTRWorldProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.common.MinecraftForge;

public class LOTRRenderPlayer {
    private Minecraft mc = Minecraft.getMinecraft();
    private RenderManager renderManager = RenderManager.instance;

    public LOTRRenderPlayer() {
        FMLCommonHandler.instance().bus().register(this);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void preRenderSpecials(RenderPlayerEvent.Specials.Pre event) {
        EntityPlayer entityplayer = event.entityPlayer;
        LOTRShields shield = LOTRLevelData.getData(entityplayer).getShield();
        if(shield != null) {
            if(!entityplayer.isInvisible()) {
                LOTRRenderShield.renderShield(shield, entityplayer, event.renderer.modelBipedMain);
            }
            else if(!entityplayer.isInvisibleToPlayer(this.mc.thePlayer)) {
                GL11.glPushMatrix();
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.15f);
                GL11.glDepthMask(false);
                GL11.glEnable(3042);
                GL11.glBlendFunc(770, 771);
                GL11.glAlphaFunc(516, 0.003921569f);
                LOTRRenderShield.renderShield(shield, entityplayer, event.renderer.modelBipedMain);
                GL11.glDisable(3042);
                GL11.glAlphaFunc(516, 0.1f);
                GL11.glPopMatrix();
                GL11.glDepthMask(true);
            }
        }
    }

    @SubscribeEvent
    public void postRender(RenderPlayerEvent.Post event) {
        float yOffset;
        EntityPlayer entityplayer = event.entityPlayer;
        float tick = event.partialRenderTick;
        double d0 = RenderManager.renderPosX;
        double d1 = RenderManager.renderPosY;
        double d2 = RenderManager.renderPosZ;
        float f0 = (float) entityplayer.lastTickPosX + (float) (entityplayer.posX - entityplayer.lastTickPosX) * tick;
        float f1 = (float) entityplayer.lastTickPosY + (float) (entityplayer.posY - entityplayer.lastTickPosY) * tick;
        float f2 = (float) entityplayer.lastTickPosZ + (float) (entityplayer.posZ - entityplayer.lastTickPosZ) * tick;
        float fr0 = f0 - (float) d0;
        float fr1 = f1 - (float) d1;
        float fr2 = f2 - (float) d2;
        yOffset = entityplayer.isPlayerSleeping() ? -1.5f : 0.0f;
        if(this.shouldRenderAlignment(entityplayer) && (this.mc.theWorld.provider instanceof LOTRWorldProvider || LOTRConfig.alwaysShowAlignment)) {
            float range;
            LOTRPlayerData clientPD = LOTRLevelData.getData(this.mc.thePlayer);
            LOTRPlayerData otherPD = LOTRLevelData.getData(entityplayer);
            float alignment = otherPD.getAlignment(clientPD.getViewingFaction());
            double dist = entityplayer.getDistanceSqToEntity(this.renderManager.livingPlayer);
            if(dist < (range = RendererLivingEntity.NAME_TAG_RANGE) * range) {
                FontRenderer fr = Minecraft.getMinecraft().fontRenderer;
                GL11.glPushMatrix();
                GL11.glTranslatef(fr0, fr1, fr2);
                GL11.glTranslatef(0.0f, entityplayer.height + 0.6f + yOffset, 0.0f);
                GL11.glNormal3f(0.0f, 1.0f, 0.0f);
                GL11.glRotatef(-this.renderManager.playerViewY, 0.0f, 1.0f, 0.0f);
                GL11.glRotatef(this.renderManager.playerViewX, 1.0f, 0.0f, 0.0f);
                GL11.glScalef(-1.0f, -1.0f, 1.0f);
                float scale = 0.025f;
                GL11.glScalef(scale, scale, scale);
                GL11.glDisable(2896);
                GL11.glDepthMask(false);
                GL11.glDisable(2929);
                GL11.glEnable(3042);
                GL11.glBlendFunc(770, 771);
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                String sAlign = LOTRAlignmentValues.formatAlignForDisplay(alignment);
                this.mc.getTextureManager().bindTexture(LOTRClientProxy.alignmentTexture);
                LOTRTickHandlerClient.drawTexturedModalRect(-MathHelper.floor_double((fr.getStringWidth(sAlign) + 18) / 2.0), -19.0, 0, 36, 16, 16);
                LOTRTickHandlerClient.drawAlignmentText(fr, 18 - MathHelper.floor_double((fr.getStringWidth(sAlign) + 18) / 2.0), -12, sAlign, 1.0f);
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                GL11.glDisable(3042);
                GL11.glEnable(2929);
                GL11.glDepthMask(true);
                GL11.glEnable(2896);
                GL11.glDisable(32826);
                GL11.glPopMatrix();
            }
        }
        if(this.shouldRenderFellowPlayerHealth(entityplayer)) {
            LOTRNPCRendering.renderHealthBar(entityplayer, fr0, fr1, fr2, new int[] {16375808, 12006707}, null);
        }
    }

    private boolean shouldRenderPlayerHUD(EntityPlayer entityplayer) {
        if(Minecraft.isGuiEnabled()) {
            return entityplayer != this.renderManager.livingPlayer && !entityplayer.isSneaking() && !entityplayer.isInvisibleToPlayer(this.mc.thePlayer);
        }
        return false;
    }

    private boolean shouldRenderAlignment(EntityPlayer entityplayer) {
        if(LOTRConfig.displayAlignmentAboveHead && this.shouldRenderPlayerHUD(entityplayer)) {
            if(LOTRLevelData.getData(entityplayer).getHideAlignment()) {
                String playerName = entityplayer.getCommandSenderName();
                List<LOTRFellowshipClient> fellowships = LOTRLevelData.getData(this.mc.thePlayer).getClientFellowships();
                for(LOTRFellowshipClient fs : fellowships) {
                    if(!fs.isPlayerIn(playerName)) continue;
                    return true;
                }
                return false;
            }
            return true;
        }
        return false;
    }

    private boolean shouldRenderFellowPlayerHealth(EntityPlayer entityplayer) {
        if(LOTRConfig.fellowPlayerHealthBars && this.shouldRenderPlayerHUD(entityplayer)) {
            List<LOTRFellowshipClient> fellowships = LOTRLevelData.getData(this.mc.thePlayer).getClientFellowships();
            for(LOTRFellowshipClient fs : fellowships) {
                if(!fs.isPlayerIn(entityplayer.getCommandSenderName())) continue;
                return true;
            }
        }
        return false;
    }
}
