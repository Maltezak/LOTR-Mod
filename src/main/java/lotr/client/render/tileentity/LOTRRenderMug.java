package lotr.client.render.tileentity;

import org.lwjgl.opengl.GL11;

import lotr.client.model.*;
import lotr.client.render.LOTRRenderBlocks;
import lotr.common.LOTRMod;
import lotr.common.item.LOTRItemMug;
import lotr.common.tileentity.LOTRTileEntityMug;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;

public class LOTRRenderMug extends TileEntitySpecialRenderer {
    private static ResourceLocation mugTexture = new ResourceLocation("lotr:item/mug.png");
    private static ResourceLocation mugClayTexture = new ResourceLocation("lotr:item/mugClay.png");
    private static ResourceLocation gobletGoldTexture = new ResourceLocation("lotr:item/gobletGold.png");
    private static ResourceLocation gobletSilverTexture = new ResourceLocation("lotr:item/gobletSilver.png");
    private static ResourceLocation gobletCopperTexture = new ResourceLocation("lotr:item/gobletCopper.png");
    private static ResourceLocation gobletWoodTexture = new ResourceLocation("lotr:item/gobletWood.png");
    private static ResourceLocation skullTexture = new ResourceLocation("lotr:item/skullCup.png");
    private static ResourceLocation glassTexture = new ResourceLocation("lotr:item/wineGlass.png");
    private static ResourceLocation bottleTexture = new ResourceLocation("lotr:item/glassBottle.png");
    private static ResourceLocation hornTexture = new ResourceLocation("lotr:item/aleHorn.png");
    private static ResourceLocation hornGoldTexture = new ResourceLocation("lotr:item/aleHornGold.png");
    private static ModelBase mugModel = new LOTRModelMug();
    private static ModelBase gobletModel = new LOTRModelGoblet();
    private static ModelBase skullModel = new LOTRModelSkullCup();
    private static ModelBase glassModel = new LOTRModelWineGlass();
    private static ModelBase bottleModel = new LOTRModelGlassBottle();
    private static LOTRModelAleHorn hornModel = new LOTRModelAleHorn();
    private static RenderBlocks renderBlocks = new RenderBlocks();

    @Override
    public void renderTileEntityAt(TileEntity tileentity, double d, double d1, double d2, float f) {
        LOTRTileEntityMug mug = (LOTRTileEntityMug) tileentity;
        ItemStack mugItemstack = mug.getMugItemForRender();
        Item mugItem = mugItemstack.getItem();
        boolean full = !mug.isEmpty();
        LOTRItemMug.Vessel vessel = mug.getVessel();
        GL11.glEnable(32826);
        GL11.glDisable(2884);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glPushMatrix();
        GL11.glTranslatef((float) d + 0.5f, ((float) d1), (float) d2 + 0.5f);
        GL11.glScalef(-1.0f, -1.0f, 1.0f);
        float mugScale = 0.75f;
        GL11.glScalef(mugScale, mugScale, mugScale);
        float scale = 0.0625f;
        switch(mug.getBlockMetadata()) {
            case 0: {
                GL11.glRotatef(90.0f, 0.0f, 1.0f, 0.0f);
                break;
            }
            case 1: {
                GL11.glRotatef(180.0f, 0.0f, 1.0f, 0.0f);
                break;
            }
            case 2: {
                GL11.glRotatef(270.0f, 0.0f, 1.0f, 0.0f);
                break;
            }
            case 3: {
                GL11.glRotatef(0.0f, 0.0f, 1.0f, 0.0f);
            }
        }
        if(vessel == LOTRItemMug.Vessel.SKULL || vessel == LOTRItemMug.Vessel.HORN || vessel == LOTRItemMug.Vessel.HORN_GOLD) {
            GL11.glRotatef(-90.0f, 0.0f, 1.0f, 0.0f);
        }
        if(full) {
            GL11.glDisable(2896);
            GL11.glPushMatrix();
            this.bindTexture(TextureMap.locationItemsTexture);
            IIcon liquidIcon = mugItem.getIconFromDamage(-1);
            if(vessel == LOTRItemMug.Vessel.MUG || vessel == LOTRItemMug.Vessel.MUG_CLAY) {
                this.renderMeniscus(liquidIcon, 6, 10, 2.0, 7.0, scale);
            }
            else if(vessel == LOTRItemMug.Vessel.GOBLET_GOLD || vessel == LOTRItemMug.Vessel.GOBLET_SILVER || vessel == LOTRItemMug.Vessel.GOBLET_COPPER || vessel == LOTRItemMug.Vessel.GOBLET_WOOD) {
                this.renderMeniscus(liquidIcon, 6, 9, 1.5, 8.0, scale);
            }
            else if(vessel == LOTRItemMug.Vessel.SKULL) {
                this.renderMeniscus(liquidIcon, 5, 11, 3.0, 9.0, scale);
            }
            else if(vessel == LOTRItemMug.Vessel.GLASS) {
                this.renderLiquid(liquidIcon, 6, 9, 6.0, 9.0, scale);
            }
            else if(vessel == LOTRItemMug.Vessel.BOTTLE) {
                this.renderLiquid(liquidIcon, 6, 10, 1.0, 5.0, scale);
            }
            else if(vessel == LOTRItemMug.Vessel.HORN || vessel == LOTRItemMug.Vessel.HORN_GOLD) {
                hornModel.prepareLiquid(scale);
                this.renderMeniscus(liquidIcon, 6, 9, -1.5, 5.0, scale);
            }
            GL11.glPopMatrix();
            GL11.glEnable(2896);
        }
        GL11.glPushMatrix();
        ModelBase model = null;
        if(vessel == LOTRItemMug.Vessel.MUG) {
            this.bindTexture(mugTexture);
            model = mugModel;
        }
        else if(vessel == LOTRItemMug.Vessel.MUG_CLAY) {
            this.bindTexture(mugClayTexture);
            model = mugModel;
        }
        else if(vessel == LOTRItemMug.Vessel.GOBLET_GOLD) {
            this.bindTexture(gobletGoldTexture);
            model = gobletModel;
        }
        else if(vessel == LOTRItemMug.Vessel.GOBLET_SILVER) {
            this.bindTexture(gobletSilverTexture);
            model = gobletModel;
        }
        else if(vessel == LOTRItemMug.Vessel.GOBLET_COPPER) {
            this.bindTexture(gobletCopperTexture);
            model = gobletModel;
        }
        else if(vessel == LOTRItemMug.Vessel.GOBLET_WOOD) {
            this.bindTexture(gobletWoodTexture);
            model = gobletModel;
        }
        else if(vessel == LOTRItemMug.Vessel.SKULL) {
            this.bindTexture(skullTexture);
            model = skullModel;
        }
        else if(vessel == LOTRItemMug.Vessel.GLASS) {
            this.bindTexture(glassTexture);
            model = glassModel;
            GL11.glEnable(2884);
        }
        else if(vessel == LOTRItemMug.Vessel.BOTTLE) {
            this.bindTexture(bottleTexture);
            model = bottleModel;
            GL11.glEnable(2884);
        }
        else if(vessel == LOTRItemMug.Vessel.HORN) {
            this.bindTexture(hornTexture);
            model = hornModel;
        }
        else if(vessel == LOTRItemMug.Vessel.HORN_GOLD) {
            this.bindTexture(hornGoldTexture);
            model = hornModel;
        }
        if(model != null) {
            model.render(null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, scale);
        }
        GL11.glPopMatrix();
        GL11.glPopMatrix();
        GL11.glDisable(3042);
        GL11.glEnable(2884);
        GL11.glDisable(32826);

    }

    private void renderMeniscus(IIcon icon, int uvMin, int uvMax, double width, double height, float scale) {
        float minU = icon.getInterpolatedU(uvMin);
        float maxU = icon.getInterpolatedU(uvMax);
        float minV = icon.getInterpolatedV(uvMin);
        float maxV = icon.getInterpolatedV(uvMax);
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(-(width *= scale), -(height *= scale), width, minU, maxV);
        tessellator.addVertexWithUV(width, -height, width, maxU, maxV);
        tessellator.addVertexWithUV(width, -height, -width, maxU, minV);
        tessellator.addVertexWithUV(-width, -height, -width, minU, minV);
        tessellator.draw();
    }

    private void renderLiquid(IIcon icon, int uvMin, int uvMax, double yMin, double yMax, float scale) {
        double edge = 0.001;
        double xzMin = (double) uvMin * (double) scale;
        double xzMax = (double) uvMax * (double) scale;
        float dxz = 0.5f - (uvMin + uvMax) / 2.0f * scale;
        yMin = 16.0 - yMin;
        yMax = 16.0 - yMax;
        yMin *= scale;
        yMax *= scale;
        GL11.glPushMatrix();
        GL11.glTranslatef(dxz, -0.5f, dxz);
        renderBlocks.setOverrideBlockTexture(icon);
        LOTRRenderBlocks.renderStandardInvBlock(renderBlocks, LOTRMod.mugBlock, xzMin += edge, yMax -= edge, xzMin, xzMax -= edge, yMin += edge, xzMax);
        renderBlocks.clearOverrideBlockTexture();
        GL11.glPopMatrix();
    }
}
