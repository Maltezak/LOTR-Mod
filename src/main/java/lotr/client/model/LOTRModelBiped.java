package lotr.client.model;

import lotr.client.LOTRTickHandlerClient;
import lotr.common.entity.npc.*;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class LOTRModelBiped extends ModelBiped {
    private boolean setup = false;
    private float base_bodyRotateX;
    private float base_armX;
    private float base_armY;
    private float base_armZ;
    private float base_legY;
    private float base_legZ;
    private float base_headY;
    private float base_headZ;
    private float base_bodyY;
    private float base_bodyZ;

    public LOTRModelBiped() {
    }

    public LOTRModelBiped(float f) {
        super(f);
    }

    public LOTRModelBiped(float f, float f1, int width, int height) {
        super(f, f1, width, height);
    }

    private void setupModelBiped() {
        this.base_bodyRotateX = this.bipedBody.rotateAngleX;
        this.base_armX = Math.abs(this.bipedRightArm.rotationPointX);
        this.base_armY = this.bipedRightArm.rotationPointY;
        this.base_armZ = this.bipedRightArm.rotationPointZ;
        this.base_legY = this.bipedRightLeg.rotationPointY;
        this.base_legZ = this.bipedRightLeg.rotationPointZ;
        this.base_headY = this.bipedHead.rotationPointY;
        this.base_headZ = this.bipedHead.rotationPointZ;
        this.base_bodyY = this.bipedBody.rotationPointY;
        this.base_bodyZ = this.bipedBody.rotationPointZ;
    }

    @Override
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
        LOTREntityNPC npc;
        float f6;
        float f7;
        if(!this.setup) {
            this.setupModelBiped();
            this.setup = true;
        }
        this.bipedHead.rotateAngleY = f3 / 57.295776f;
        this.bipedHead.rotateAngleX = f4 / 57.295776f;
        this.bipedHeadwear.rotateAngleY = this.bipedHead.rotateAngleY;
        this.bipedHeadwear.rotateAngleX = this.bipedHead.rotateAngleX;
        this.bipedRightArm.rotateAngleX = MathHelper.cos(f * 0.6662f + 3.1415927f) * 2.0f * f1 * 0.5f;
        this.bipedLeftArm.rotateAngleX = MathHelper.cos(f * 0.6662f) * 2.0f * f1 * 0.5f;
        this.bipedRightArm.rotateAngleZ = 0.0f;
        this.bipedLeftArm.rotateAngleZ = 0.0f;
        this.bipedRightLeg.rotateAngleX = MathHelper.cos(f * 0.6662f) * 1.4f * f1;
        this.bipedLeftLeg.rotateAngleX = MathHelper.cos(f * 0.6662f + 3.1415927f) * 1.4f * f1;
        if(entity instanceof LOTREntityNPC) {
            this.bipedRightLeg.rotateAngleY = (float) Math.toRadians(5.0);
            this.bipedLeftLeg.rotateAngleY = (float) Math.toRadians(-5.0);
        }
        if(this.isRiding) {
            this.bipedRightArm.rotateAngleX += -0.62831855f;
            this.bipedLeftArm.rotateAngleX += -0.62831855f;
            this.bipedRightLeg.rotateAngleX = -1.2566371f;
            this.bipedLeftLeg.rotateAngleX = -1.2566371f;
            this.bipedRightLeg.rotateAngleY = 0.31415927f;
            this.bipedLeftLeg.rotateAngleY = -0.31415927f;
        }
        if(this.heldItemLeft != 0) {
            this.bipedLeftArm.rotateAngleX = this.bipedLeftArm.rotateAngleX * 0.5f - 0.31415927f * this.heldItemLeft;
        }
        if(this.heldItemRight != 0) {
            this.bipedRightArm.rotateAngleX = this.bipedRightArm.rotateAngleX * 0.5f - 0.31415927f * this.heldItemRight;
        }
        this.bipedRightArm.rotateAngleY = 0.0f;
        this.bipedLeftArm.rotateAngleY = 0.0f;
        if(this.onGround > -9990.0f) {
            f6 = this.onGround;
            this.bipedBody.rotateAngleY = MathHelper.sin(MathHelper.sqrt_float(f6) * 3.1415927f * 2.0f) * 0.2f;
            this.bipedRightArm.rotationPointZ = MathHelper.sin(this.bipedBody.rotateAngleY) * this.base_armX;
            this.bipedRightArm.rotationPointX = -MathHelper.cos(this.bipedBody.rotateAngleY) * this.base_armX;
            this.bipedLeftArm.rotationPointZ = -MathHelper.sin(this.bipedBody.rotateAngleY) * this.base_armX;
            this.bipedLeftArm.rotationPointX = MathHelper.cos(this.bipedBody.rotateAngleY) * this.base_armX;
            this.bipedRightArm.rotateAngleY += this.bipedBody.rotateAngleY;
            this.bipedLeftArm.rotateAngleY += this.bipedBody.rotateAngleY;
            this.bipedLeftArm.rotateAngleX += this.bipedBody.rotateAngleY;
            f6 = 1.0f - this.onGround;
            f6 *= f6;
            f6 *= f6;
            f6 = 1.0f - f6;
            f7 = MathHelper.sin(f6 * 3.1415927f);
            float f8 = MathHelper.sin(this.onGround * 3.1415927f) * -(this.bipedHead.rotateAngleX - 0.7f) * 0.75f;
            this.bipedRightArm.rotateAngleX = (float) (this.bipedRightArm.rotateAngleX - (f7 * 1.2 + f8));
            this.bipedRightArm.rotateAngleY += this.bipedBody.rotateAngleY * 2.0f;
            this.bipedRightArm.rotateAngleZ = MathHelper.sin(this.onGround * 3.1415927f) * -0.4f;
        }
        if(this.isSneak) {
            this.bipedBody.rotateAngleX = this.base_bodyRotateX + 0.5f;
            this.bipedRightArm.rotateAngleX += 0.4f;
            this.bipedLeftArm.rotateAngleX += 0.4f;
            this.bipedRightLeg.rotationPointZ = this.base_legZ + 4.0f;
            this.bipedLeftLeg.rotationPointZ = this.base_legZ + 4.0f;
            this.bipedRightLeg.rotationPointY = this.base_legY - 3.0f;
            this.bipedLeftLeg.rotationPointY = this.base_legY - 3.0f;
            this.bipedHead.rotationPointY = this.base_headY + 1.0f;
            this.bipedHeadwear.rotationPointY = this.base_headY + 1.0f;
        }
        else {
            this.bipedBody.rotateAngleX = this.base_bodyRotateX;
            this.bipedRightLeg.rotationPointZ = this.base_legZ + 0.1f;
            this.bipedLeftLeg.rotationPointZ = this.base_legZ + 0.1f;
            this.bipedRightLeg.rotationPointY = this.base_legY;
            this.bipedLeftLeg.rotationPointY = this.base_legY;
            this.bipedHead.rotationPointY = this.base_headY;
            this.bipedHeadwear.rotationPointY = this.base_headY;
        }
        this.bipedRightArm.rotateAngleZ += MathHelper.cos(f2 * 0.09f) * 0.05f + 0.05f;
        this.bipedLeftArm.rotateAngleZ -= MathHelper.cos(f2 * 0.09f) * 0.05f + 0.05f;
        this.bipedRightArm.rotateAngleX += MathHelper.sin(f2 * 0.067f) * 0.05f;
        this.bipedLeftArm.rotateAngleX -= MathHelper.sin(f2 * 0.067f) * 0.05f;
        if(this.aimedBow) {
            f6 = 0.0f;
            f7 = 0.0f;
            this.bipedRightArm.rotateAngleZ = 0.0f;
            this.bipedLeftArm.rotateAngleZ = 0.0f;
            this.bipedRightArm.rotateAngleY = -(0.1f - f6 * 0.6f) + this.bipedHead.rotateAngleY;
            this.bipedLeftArm.rotateAngleY = 0.1f - f6 * 0.6f + this.bipedHead.rotateAngleY + 0.4f;
            this.bipedRightArm.rotateAngleX = -1.5707964f + this.bipedHead.rotateAngleX;
            this.bipedLeftArm.rotateAngleX = -1.5707964f + this.bipedHead.rotateAngleX;
            this.bipedRightArm.rotateAngleX -= f6 * 1.2f - f7 * 0.4f;
            this.bipedLeftArm.rotateAngleX -= f6 * 1.2f - f7 * 0.4f;
            this.bipedRightArm.rotateAngleZ += MathHelper.cos(f2 * 0.09f) * 0.05f + 0.05f;
            this.bipedLeftArm.rotateAngleZ -= MathHelper.cos(f2 * 0.09f) * 0.05f + 0.05f;
            this.bipedRightArm.rotateAngleX += MathHelper.sin(f2 * 0.067f) * 0.05f;
            this.bipedLeftArm.rotateAngleX -= MathHelper.sin(f2 * 0.067f) * 0.05f;
        }
        if(entity instanceof LOTREntityNPC && (npc = (LOTREntityNPC) entity).isDrunkard()) {
            float f62 = f2 / 80.0f;
            float f72 = (f2 + 40.0f) / 80.0f;
            float f8 = MathHelper.sin(f62 *= 6.2831855f) * 0.5f;
            float f9 = MathHelper.sin(f72 *= 6.2831855f) * 0.5f;
            this.bipedHead.rotateAngleX += f8;
            this.bipedHead.rotateAngleY += f9;
            this.bipedHeadwear.rotateAngleX += f8;
            this.bipedHeadwear.rotateAngleY += f9;
            if(npc.getHeldItem() != null) {
                this.bipedRightArm.rotateAngleX = -1.0471976f;
            }
        }
        boolean bowing = false;
        float bowAmount = 0.0f;
        if(entity instanceof LOTREntityElf) {
            bowAmount = ((LOTREntityElf) entity).getBowingAmount(LOTRTickHandlerClient.renderTick);
            bowing = bowAmount != 0.0f;
        }
        if(bowing) {
            float bowAmountRad = (float) Math.toRadians(bowAmount *= 30.0f);
            float bowCos = MathHelper.cos(bowAmountRad);
            float bowSin = MathHelper.sin(bowAmountRad);
            this.bipedHead.rotationPointY = this.base_headY + 12.0f * (1.0f - bowCos);
            this.bipedHead.rotationPointZ = this.base_headY - 12.0f * bowSin;
            this.bipedHeadwear.rotationPointY = this.bipedHead.rotationPointY;
            this.bipedHeadwear.rotationPointZ = this.bipedHead.rotationPointZ;
            this.bipedBody.rotationPointY = this.base_bodyY + 12.0f * (1.0f - bowCos);
            this.bipedBody.rotationPointZ = this.base_bodyZ - 12.0f * bowSin;
            this.bipedRightArm.rotationPointY = this.base_armY + 10.0f * (1.0f - bowCos);
            this.bipedRightArm.rotationPointZ = this.base_armY - 12.0f * bowSin;
            this.bipedLeftArm.rotationPointY = this.bipedRightArm.rotationPointY;
            this.bipedLeftArm.rotationPointZ = this.bipedRightArm.rotationPointZ;
            this.bipedHead.rotateAngleX = bowAmountRad;
            this.bipedHeadwear.rotateAngleX = bowAmountRad;
            this.bipedBody.rotateAngleX = bowAmountRad;
            this.bipedRightArm.rotateAngleX = bowAmountRad;
            this.bipedLeftArm.rotateAngleX = bowAmountRad;
        }
        else {
            if(!this.isSneak) {
                this.bipedHead.rotationPointY = this.base_headY;
                this.bipedHead.rotationPointZ = this.base_headZ;
                this.bipedHeadwear.rotationPointY = this.base_headY;
                this.bipedHeadwear.rotationPointZ = this.base_headZ;
            }
            this.bipedBody.rotationPointY = this.base_bodyY;
            this.bipedBody.rotationPointZ = this.base_bodyZ;
            this.bipedRightArm.rotationPointY = this.base_armY;
            this.bipedRightArm.rotationPointZ = this.base_armZ;
            this.bipedLeftArm.rotationPointY = this.base_armY;
            this.bipedLeftArm.rotationPointZ = this.base_armZ;
        }
    }
}
