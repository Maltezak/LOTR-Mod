package lotr.client.model;

import org.lwjgl.opengl.GL11;

import lotr.common.entity.item.LOTREntityStoneTroll;
import lotr.common.entity.npc.*;
import net.minecraft.client.model.*;
import net.minecraft.entity.*;
import net.minecraft.util.MathHelper;

public class LOTRModelTroll extends ModelBase {
    public ModelRenderer head;
    public ModelRenderer headHurt;
    public ModelRenderer body;
    public ModelRenderer rightArm;
    public ModelRenderer leftArm;
    public ModelRenderer rightLeg;
    public ModelRenderer leftLeg;
    public ModelRenderer woodenClub;
    public ModelRenderer woodenClubSpikes;
    public ModelRenderer warhammer;
    public ModelRenderer battleaxe;
    private boolean isOutiftModel = false;

    public LOTRModelTroll() {
        this(0.0f);
    }

    public LOTRModelTroll(float f) {
        this.textureWidth = 128;
        this.textureHeight = 128;
        this.head = new ModelRenderer(this, 0, 0);
        this.head.addBox(-6.0f, -6.0f, -12.0f, 12, 12, 12, f);
        this.head.setRotationPoint(0.0f, -27.0f, -6.0f);
        this.head.setTextureOffset(40, 0).addBox(6.0f, -2.0f, -8.0f, 1, 4, 3, f);
        this.head.mirror = true;
        this.head.setTextureOffset(40, 0).addBox(-7.0f, -2.0f, -8.0f, 1, 4, 3, f);
        this.head.mirror = false;
        this.head.setTextureOffset(0, 0).addBox(-1.0f, -1.0f, -14.0f, 2, 3, 2, f);
        this.headHurt = new ModelRenderer(this, 48, 44);
        this.headHurt.addBox(-6.0f, -6.0f, -12.0f, 12, 12, 12, f);
        this.headHurt.setRotationPoint(0.0f, -27.0f, -6.0f);
        this.headHurt.setTextureOffset(40, 0).addBox(6.0f, -2.0f, -8.0f, 1, 4, 3, f);
        this.headHurt.mirror = true;
        this.headHurt.setTextureOffset(40, 0).addBox(-7.0f, -2.0f, -8.0f, 1, 4, 3, f);
        this.headHurt.mirror = false;
        this.headHurt.setTextureOffset(0, 0).addBox(-1.0f, -1.0f, -14.0f, 2, 3, 2, f);
        this.body = new ModelRenderer(this, 48, 0);
        this.body.addBox(-12.0f, -28.0f, -8.0f, 24, 28, 16, f);
        this.body.setRotationPoint(0.0f, 0.0f, 0.0f);
        this.rightArm = new ModelRenderer(this, 0, 24);
        this.rightArm.mirror = true;
        this.rightArm.addBox(-12.0f, -3.0f, -6.0f, 12, 12, 12, f);
        this.rightArm.setRotationPoint(-12.0f, -23.0f, 0.0f);
        this.rightArm.setTextureOffset(0, 48).addBox(-11.0f, 9.0f, -5.0f, 10, 20, 10, f);
        this.leftArm = new ModelRenderer(this, 0, 24);
        this.leftArm.addBox(0.0f, -3.0f, -6.0f, 12, 12, 12, f);
        this.leftArm.setRotationPoint(12.0f, -23.0f, 0.0f);
        this.leftArm.setTextureOffset(0, 48).addBox(1.0f, 9.0f, -5.0f, 10, 20, 10, f);
        this.rightLeg = new ModelRenderer(this, 0, 78);
        this.rightLeg.mirror = true;
        this.rightLeg.addBox(-6.0f, 0.0f, -6.0f, 11, 12, 12, f);
        this.rightLeg.setRotationPoint(-6.0f, 0.0f, 0.0f);
        this.rightLeg.setTextureOffset(0, 102).addBox(-5.5f, 12.0f, -5.0f, 10, 12, 10);
        this.leftLeg = new ModelRenderer(this, 0, 78);
        this.leftLeg.addBox(-5.0f, 0.0f, -6.0f, 11, 12, 12, f);
        this.leftLeg.setRotationPoint(6.0f, 0.0f, 0.0f);
        this.leftLeg.setTextureOffset(0, 102).addBox(-4.5f, 12.0f, -5.0f, 10, 12, 10);
        this.woodenClub = new ModelRenderer(this, 0, 0);
        this.woodenClub.addBox(-9.0f, 5.0f, 21.0f, 6, 24, 6, f);
        this.woodenClub.setRotationPoint(-12.0f, -23.0f, 0.0f);
        this.woodenClubSpikes = new ModelRenderer(this, 24, 0);
        this.woodenClubSpikes.addBox(-12.0f, 25.0f, 23.5f, 12, 1, 1, f);
        this.woodenClubSpikes.addBox(-12.0f, 20.0f, 23.5f, 12, 1, 1, f);
        this.woodenClubSpikes.addBox(-12.0f, 15.0f, 23.5f, 12, 1, 1, f);
        this.woodenClubSpikes.setTextureOffset(24, 2);
        this.woodenClubSpikes.addBox(-6.5f, 25.0f, 18.0f, 1, 1, 12, f);
        this.woodenClubSpikes.addBox(-6.5f, 20.0f, 18.0f, 1, 1, 12, f);
        this.woodenClubSpikes.addBox(-6.5f, 15.0f, 18.0f, 1, 1, 12, f);
        this.woodenClubSpikes.setRotationPoint(-12.0f, -23.0f, 0.0f);
        this.warhammer = new ModelRenderer(this, 52, 29);
        this.warhammer.setRotationPoint(-12.0f, -23.0f, 0.0f);
        this.warhammer.addBox(-7.5f, 5.0f, 22.5f, 3, 20, 3, f);
        this.warhammer.setTextureOffset(0, 32).addBox(-12.0f, 25.0f, 14.0f, 12, 12, 20, f);
        this.battleaxe = new ModelRenderer(this, 64, 0);
        this.battleaxe.setRotationPoint(-12.0f, -23.0f, 0.0f);
        this.battleaxe.addBox(-7.0f, -40.0f, 22.5f, 2, 80, 2, f);
        this.battleaxe.setTextureOffset(72, 0);
        this.battleaxe.addBox(-6.0f, 20.0f, 24.0f, 0, 24, 16, f);
    }

    public LOTRModelTroll(float f, int i) {
        this(f);
        this.isOutiftModel = true;
        if(i == 0) {
            this.head.showModel = true;
            this.body.showModel = true;
            this.rightArm.showModel = true;
            this.leftArm.showModel = true;
            this.rightLeg.showModel = false;
            this.leftLeg.showModel = false;
        }
        else if(i == 1) {
            this.head.showModel = false;
            this.body.showModel = false;
            this.rightArm.showModel = false;
            this.leftArm.showModel = false;
            this.rightLeg.showModel = true;
            this.leftLeg.showModel = true;
        }
        else if(i == 2) {
            this.head.showModel = true;
            this.body.showModel = false;
            this.rightArm.showModel = false;
            this.leftArm.showModel = false;
            this.rightLeg.showModel = false;
            this.leftLeg.showModel = false;
        }
        else if(i == 3) {
            this.head.showModel = false;
            this.body.showModel = true;
            this.rightArm.showModel = true;
            this.leftArm.showModel = true;
            this.rightLeg.showModel = false;
            this.leftLeg.showModel = false;
        }
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        boolean isHurt = false;
        boolean hasTwoHeads = false;
        if(entity instanceof LOTREntityTroll) {
            LOTREntityTroll troll = (LOTREntityTroll) entity;
            isHurt = !this.isOutiftModel && troll.shouldRenderHeadHurt();
            hasTwoHeads = troll.hasTwoHeads();
        }
        else if(entity instanceof LOTREntityStoneTroll) {
            LOTREntityStoneTroll troll = (LOTREntityStoneTroll) entity;
            isHurt = false;
            hasTwoHeads = troll.hasTwoHeads();
        }
        if(hasTwoHeads) {
            GL11.glPushMatrix();
            GL11.glRotatef(-15.0f, 0.0f, 0.0f, 1.0f);
            GL11.glRotatef(10.0f, 0.0f, 1.0f, 0.0f);
            if(isHurt) {
                this.headHurt.render(f5);
            }
            else {
                this.head.render(f5);
            }
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glRotatef(15.0f, 0.0f, 0.0f, 1.0f);
            GL11.glRotatef(-10.0f, 0.0f, 1.0f, 0.0f);
            if(isHurt) {
                this.headHurt.render(f5);
            }
            else {
                this.head.render(f5);
            }
            GL11.glPopMatrix();
        }
        else if(isHurt) {
            this.headHurt.render(f5);
        }
        else {
            this.head.render(f5);
        }
        this.body.render(f5);
        this.rightArm.render(f5);
        this.leftArm.render(f5);
        this.rightLeg.render(f5);
        this.leftLeg.render(f5);
    }

    @Override
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
        float f6;
        this.head.rotationPointX = 0.0f;
        this.head.rotationPointY = -27.0f;
        this.head.rotateAngleZ = 0.0f;
        this.body.rotationPointX = 0.0f;
        this.body.rotationPointY = 0.0f;
        this.body.rotateAngleZ = 0.0f;
        this.rightArm.rotationPointX = -12.0f;
        this.rightArm.rotationPointY = -23.0f;
        this.rightArm.rotateAngleZ = 0.0f;
        this.leftArm.rotationPointX = 12.0f;
        this.leftArm.rotationPointY = -23.0f;
        this.leftArm.rotateAngleZ = 0.0f;
        this.head.rotateAngleY = f3 / 57.295776f;
        this.head.rotateAngleX = f4 / 57.295776f;
        if(entity instanceof LOTREntityTroll && ((LOTREntityTroll) entity).sniffTime > 0) {
            f6 = (((LOTREntityTroll) entity).sniffTime - (f2 - entity.ticksExisted)) / 8.0f;
            this.head.rotateAngleY = MathHelper.sin(f6 *= 6.2831855f) * 0.5f;
        }
        this.rightArm.rotateAngleX = MathHelper.cos(f * 0.6662f + 3.1415927f) * 2.0f * f1 * 0.5f;
        this.leftArm.rotateAngleX = MathHelper.cos(f * 0.6662f) * 2.0f * f1 * 0.5f;
        if(entity instanceof LOTREntityTroll) {
            this.rightArm.rotateAngleX = this.rightArm.rotateAngleX * 0.5f - 0.31415927f;
        }
        this.rightArm.rotateAngleZ = 0.0f;
        this.leftArm.rotateAngleZ = 0.0f;
        if(this.onGround > -9990.0f) {
            f6 = this.onGround;
            this.body.rotateAngleY = MathHelper.sin(MathHelper.sqrt_float(f6) * 3.1415927f * 2.0f) * 0.2f;
            this.rightArm.rotationPointZ = MathHelper.sin(this.body.rotateAngleY) * 5.0f;
            this.rightArm.rotationPointX = -MathHelper.cos(this.body.rotateAngleY) * 12.0f;
            this.leftArm.rotationPointZ = -MathHelper.sin(this.body.rotateAngleY) * 5.0f;
            this.leftArm.rotationPointX = MathHelper.cos(this.body.rotateAngleY) * 12.0f;
            this.rightArm.rotateAngleY += this.body.rotateAngleY;
            this.leftArm.rotateAngleY += this.body.rotateAngleY;
            this.leftArm.rotateAngleX += this.body.rotateAngleY;
            f6 = 1.0f - this.onGround;
            f6 *= f6;
            f6 *= f6;
            f6 = 1.0f - f6;
            float f7 = MathHelper.sin(f6 * 3.1415927f);
            float f8 = MathHelper.sin(this.onGround * 3.1415927f) * -(this.head.rotateAngleX - 0.7f) * 0.75f;
            this.rightArm.rotateAngleX = (float) (this.rightArm.rotateAngleX - (f7 * 1.2 + f8));
            this.rightArm.rotateAngleY += this.body.rotateAngleY * 2.0f;
            this.rightArm.rotateAngleZ = MathHelper.sin(this.onGround * 3.1415927f) * -0.4f;
        }
        this.rightLeg.rotateAngleX = MathHelper.cos(f * 0.6662f) * 1.4f * f1;
        this.leftLeg.rotateAngleX = MathHelper.cos(f * 0.6662f + 3.1415927f) * 1.4f * f1;
        this.rightLeg.rotateAngleY = 0.0f;
        this.leftLeg.rotateAngleY = 0.0f;
        this.rightArm.rotateAngleY = 0.0f;
        this.leftArm.rotateAngleY = 0.0f;
        this.rightArm.rotateAngleZ += MathHelper.cos(f2 * 0.09f) * 0.05f + 0.05f;
        this.leftArm.rotateAngleZ -= MathHelper.cos(f2 * 0.09f) * 0.05f + 0.05f;
        this.rightArm.rotateAngleX += MathHelper.sin(f2 * 0.067f) * 0.05f;
        this.leftArm.rotateAngleX -= MathHelper.sin(f2 * 0.067f) * 0.05f;
        boolean throwing = false;
        if(entity instanceof LOTREntityMountainTroll && ((LOTREntityMountainTroll) entity).isThrowingRocks()) {
            throwing = true;
        }
        if(entity instanceof LOTREntitySnowTroll && ((LOTREntitySnowTroll) entity).isThrowingSnow()) {
            throwing = true;
        }
        if(throwing) {
            this.rightArm.rotateAngleX -= 0.5f;
            this.rightArm.rotateAngleZ -= 0.4f;
            this.leftArm.rotateAngleX = this.rightArm.rotateAngleX;
            this.leftArm.rotateAngleY = -this.rightArm.rotateAngleY;
            this.leftArm.rotateAngleZ = -this.rightArm.rotateAngleZ;
        }
        if(entity instanceof EntityLivingBase) {
            float f62 = MathHelper.sin(f * 0.2f) * 0.3f * f1;
            this.head.rotationPointX += MathHelper.sin(f62) * 27.0f;
            this.head.rotationPointY += 27.0f - MathHelper.cos(f62) * 27.0f;
            this.head.rotateAngleZ += f62;
            this.body.rotateAngleZ += f62;
            float armRotationOffsetX = MathHelper.sin(f62) * 23.0f + MathHelper.cos(f62) * 12.0f - 12.0f;
            float armRotationOffsetY = MathHelper.cos(f62) * -23.0f + MathHelper.sin(f62) * 12.0f + 23.0f;
            this.rightArm.rotationPointX += armRotationOffsetX;
            this.rightArm.rotationPointY += -armRotationOffsetY;
            this.rightArm.rotateAngleZ += f62;
            this.leftArm.rotationPointX += armRotationOffsetX;
            this.leftArm.rotationPointY += armRotationOffsetY;
            this.leftArm.rotateAngleZ += f62;
        }
        this.headHurt.rotationPointX = this.head.rotationPointX;
        this.headHurt.rotationPointY = this.head.rotationPointY;
        this.headHurt.rotationPointZ = this.head.rotationPointZ;
        this.headHurt.rotateAngleX = this.head.rotateAngleX;
        this.headHurt.rotateAngleY = this.head.rotateAngleY;
        this.headHurt.rotateAngleZ = this.head.rotateAngleZ;
    }

    public void renderWoodenClub(float f) {
        this.woodenClub.rotationPointX = this.rightArm.rotationPointX;
        this.woodenClub.rotationPointY = this.rightArm.rotationPointY;
        this.woodenClub.rotationPointZ = this.rightArm.rotationPointZ;
        this.woodenClub.rotateAngleX = this.rightArm.rotateAngleX - 1.5707964f;
        this.woodenClub.rotateAngleY = this.rightArm.rotateAngleY;
        this.woodenClub.rotateAngleZ = this.rightArm.rotateAngleZ;
        this.woodenClub.render(f);
    }

    public void renderWoodenClubWithSpikes(float f) {
        this.renderWoodenClub(f);
        this.woodenClubSpikes.rotationPointX = this.woodenClub.rotationPointX;
        this.woodenClubSpikes.rotationPointY = this.woodenClub.rotationPointY;
        this.woodenClubSpikes.rotationPointZ = this.woodenClub.rotationPointZ;
        this.woodenClubSpikes.rotateAngleX = this.woodenClub.rotateAngleX;
        this.woodenClubSpikes.rotateAngleY = this.woodenClub.rotateAngleY;
        this.woodenClubSpikes.rotateAngleZ = this.woodenClub.rotateAngleZ;
        this.woodenClubSpikes.render(f);
    }

    public void renderWarhammer(float f) {
        this.warhammer.rotationPointX = this.rightArm.rotationPointX;
        this.warhammer.rotationPointY = this.rightArm.rotationPointY;
        this.warhammer.rotationPointZ = this.rightArm.rotationPointZ;
        this.warhammer.rotateAngleX = this.rightArm.rotateAngleX - 1.5707964f;
        this.warhammer.rotateAngleY = this.rightArm.rotateAngleY;
        this.warhammer.rotateAngleZ = this.rightArm.rotateAngleZ;
        this.warhammer.render(f);
    }

    public void renderBattleaxe(float f) {
        this.battleaxe.rotationPointX = this.rightArm.rotationPointX;
        this.battleaxe.rotationPointY = this.rightArm.rotationPointY;
        this.battleaxe.rotationPointZ = this.rightArm.rotationPointZ;
        this.battleaxe.rotateAngleX = this.rightArm.rotateAngleX - 1.5707964f;
        this.battleaxe.rotateAngleY = this.rightArm.rotateAngleY;
        this.battleaxe.rotateAngleZ = this.rightArm.rotateAngleZ;
        this.battleaxe.render(f);
    }
}
