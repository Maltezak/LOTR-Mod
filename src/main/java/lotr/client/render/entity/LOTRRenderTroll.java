package lotr.client.render.entity;

import org.lwjgl.opengl.GL11;

import lotr.client.model.LOTRModelTroll;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.*;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderTroll extends RenderLiving {
    private static LOTRRandomSkins trollSkins;
    public static ResourceLocation[] trollOutfits;
    private static ResourceLocation weaponsTexture;
    private LOTRModelTroll shirtModel = new LOTRModelTroll(1.0f, 0);
    private LOTRModelTroll trousersModel = new LOTRModelTroll(0.75f, 1);

    public LOTRRenderTroll() {
        super(new LOTRModelTroll(), 0.5f);
        trollSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/troll/troll");
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return trollSkins.getRandomSkin((LOTREntityTroll) entity);
    }

    @Override
    public void doRender(EntityLiving entity, double d, double d1, double d2, float f, float f1) {
        super.doRender(entity, d, d1, d2, f, f1);
        if(Minecraft.isGuiEnabled() && ((LOTREntityNPC) entity).hiredNPCInfo.getHiringPlayer() == this.renderManager.livingPlayer) {
            LOTRNPCRendering.renderHiredIcon(entity, d, d1 + 1.0, d2);
            LOTRNPCRendering.renderNPCHealthBar(entity, d, d1 + 1.0, d2);
        }
    }

    @Override
    protected void preRenderCallback(EntityLivingBase entity, float f) {
        LOTREntityTroll troll = (LOTREntityTroll) entity;
        this.scaleTroll(troll, false);
        if(LOTRMod.isAprilFools() || troll.familyInfo.getName().toLowerCase().equals("shrek")) {
            GL11.glColor3f(0.0f, 1.0f, 0.0f);
        }
        else if(troll.familyInfo.getName().toLowerCase().equals("drek")) {
            GL11.glColor3f(0.2f, 0.4f, 1.0f);
        }
    }

    protected void scaleTroll(LOTREntityTroll troll, boolean inverse) {
        float scale = troll.getTrollScale();
        if(inverse) {
            scale = 1.0f / scale;
        }
        GL11.glScalef(scale, scale, scale);
    }

    @Override
    protected void renderEquippedItems(EntityLivingBase entity, float f) {
        GL11.glColor3f(1.0f, 1.0f, 1.0f);
        super.renderEquippedItems(entity, f);
        GL11.glPushMatrix();
        this.bindTexture(weaponsTexture);
        this.renderTrollWeapon(entity, f);
        GL11.glPopMatrix();
    }

    protected void renderTrollWeapon(EntityLivingBase entity, float f) {
        ((LOTRModelTroll) this.mainModel).renderWoodenClub(0.0625f);
    }

    @Override
    protected int shouldRenderPass(EntityLivingBase entity, int pass, float f) {
        this.bindTrollOutfitTexture(entity);
        if(pass == 0) {
            this.shirtModel.onGround = this.mainModel.onGround;
            this.setRenderPassModel(this.shirtModel);
            return 1;
        }
        if(pass == 1) {
            this.trousersModel.onGround = this.trousersModel.onGround;
            this.setRenderPassModel(this.trousersModel);
            return 1;
        }
        return super.shouldRenderPass(entity, pass, f);
    }

    protected void bindTrollOutfitTexture(EntityLivingBase entity) {
        int j = ((LOTREntityTroll) entity).getTrollOutfit();
        if(j < 0 || j >= trollOutfits.length) {
            j = 0;
        }
        this.bindTexture(trollOutfits[j]);
    }

    @Override
    protected void rotateCorpse(EntityLivingBase entity, float f, float f1, float f2) {
        if(((LOTREntityTroll) entity).getSneezingTime() > 0) {
            f1 += (float) (Math.cos(entity.ticksExisted * 3.25) * 3.141592653589793);
        }
        super.rotateCorpse(entity, f, f1, f2);
    }

    static {
        trollOutfits = new ResourceLocation[] {new ResourceLocation("lotr:mob/troll/outfit_0.png"), new ResourceLocation("lotr:mob/troll/outfit_1.png"), new ResourceLocation("lotr:mob/troll/outfit_2.png")};
        weaponsTexture = new ResourceLocation("lotr:mob/troll/weapons.png");
    }
}
