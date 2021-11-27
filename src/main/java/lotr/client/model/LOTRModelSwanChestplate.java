package lotr.client.model;

import lotr.client.LOTRTickHandlerClient;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.*;
import net.minecraft.util.MathHelper;

public class LOTRModelSwanChestplate extends LOTRModelBiped {
    private ModelRenderer[] wingsRight;
    private ModelRenderer[] wingsLeft;

    public LOTRModelSwanChestplate() {
        this(0.0f);
    }

    public LOTRModelSwanChestplate(float f) {
        super(f);
        ModelRenderer wing;
        int i;
        this.bipedBody = new ModelRenderer(this, 0, 0);
        this.bipedBody.setRotationPoint(0.0f, 0.0f, 0.0f);
        this.bipedBody.addBox(-4.0f, 0.0f, -2.0f, 8, 12, 4, f);
        int wings = 12;
        this.wingsRight = new ModelRenderer[wings];
        for(i = 0; i < this.wingsRight.length; ++i) {
            wing = new ModelRenderer(this, 0, 16);
            wing.setRotationPoint(-2.0f, 0.0f, 0.0f);
            wing.addBox(-2.0f, 0.0f, 0.0f, 2, 1, 1, 0.0f);
            wing.setTextureOffset(6, 16).addBox(-2.0f, 1.0f, 0.5f, 2, 10, 0, 0.0f);
            this.wingsRight[i] = wing;
        }
        for(i = 0; i < this.wingsRight.length - 1; ++i) {
            this.wingsRight[i].addChild(this.wingsRight[i + 1]);
        }
        this.wingsRight[0].setRotationPoint(-2.0f, 1.0f, 1.0f);
        this.bipedBody.addChild(this.wingsRight[0]);
        this.wingsLeft = new ModelRenderer[wings];
        for(i = 0; i < this.wingsLeft.length; ++i) {
            wing = new ModelRenderer(this, 0, 16);
            wing.setRotationPoint(2.0f, 0.0f, 0.0f);
            wing.mirror = true;
            wing.addBox(0.0f, 0.0f, 0.0f, 2, 1, 1, 0.0f);
            wing.setTextureOffset(6, 16).addBox(0.0f, 1.0f, 0.5f, 2, 10, 0, 0.0f);
            this.wingsLeft[i] = wing;
        }
        for(i = 0; i < this.wingsLeft.length - 1; ++i) {
            this.wingsLeft[i].addChild(this.wingsLeft[i + 1]);
        }
        this.wingsLeft[0].setRotationPoint(2.0f, 1.0f, 1.0f);
        this.bipedBody.addChild(this.wingsLeft[0]);
        this.bipedRightArm = new ModelRenderer(this, 24, 0);
        this.bipedRightArm.setRotationPoint(-5.0f, 2.0f, 0.0f);
        this.bipedRightArm.addBox(-3.0f, -2.0f, -2.0f, 4, 12, 4, f);
        this.bipedLeftArm = new ModelRenderer(this, 24, 0);
        this.bipedLeftArm.setRotationPoint(5.0f, 2.0f, 0.0f);
        this.bipedLeftArm.mirror = true;
        this.bipedLeftArm.addBox(-1.0f, -2.0f, -2.0f, 4, 12, 4, f);
        this.bipedHead.cubeList.clear();
        this.bipedHeadwear.cubeList.clear();
        this.bipedRightLeg.cubeList.clear();
        this.bipedLeftLeg.cubeList.clear();
    }

    @Override
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        float motion = f1;
        float motionPhase = f;
        if(entity != null && entity.ridingEntity instanceof EntityLivingBase) {
            EntityLivingBase mount = (EntityLivingBase) entity.ridingEntity;
            float tick = LOTRTickHandlerClient.renderTick;
            motion = mount.prevLimbSwingAmount + (mount.limbSwingAmount - mount.prevLimbSwingAmount) * tick;
            motionPhase = mount.limbSwing - mount.limbSwingAmount * (1.0f - tick);
            motion *= 1.5f;
            motionPhase *= 2.0f;
        }
        float wingAngleBase = (float) Math.toRadians(10.0);
        wingAngleBase += MathHelper.sin(f2 * 0.02f) * 0.01f;
        wingAngleBase += MathHelper.sin(motionPhase * 0.2f) * 0.03f * motion;
        float wingYaw = (float) Math.toRadians(50.0);
        wingYaw += MathHelper.sin(f2 * 0.03f) * 0.05f;
        wingYaw += MathHelper.sin(motionPhase * 0.25f) * 0.12f * motion;
        for(int i = 0; i < this.wingsRight.length; ++i) {
            float wingAngle;
            float factor = i + 1;
            this.wingsRight[i].rotateAngleZ = wingAngle = wingAngleBase / (factor / 3.4f);
            this.wingsLeft[i].rotateAngleZ = -wingAngle;
        }
        this.wingsRight[0].rotateAngleY = MathHelper.sin(wingYaw);
        this.wingsRight[0].rotateAngleX = MathHelper.cos(wingYaw);
        this.wingsLeft[0].rotateAngleY = MathHelper.sin(-wingYaw);
        this.wingsLeft[0].rotateAngleX = MathHelper.cos(-wingYaw);
    }
}
