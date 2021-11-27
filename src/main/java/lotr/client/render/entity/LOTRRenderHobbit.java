package lotr.client.render.entity;

import org.lwjgl.opengl.GL11;

import lotr.client.model.LOTRModelHobbit;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityHobbit;
import net.minecraft.client.model.*;
import net.minecraft.entity.*;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderHobbit
extends LOTRRenderBiped {
    private static LOTRRandomSkins hobbitSkinsMale;
    private static LOTRRandomSkins hobbitSkinsFemale;
    private static LOTRRandomSkins hobbitSkinsMaleChild;
    private static LOTRRandomSkins hobbitSkinsFemaleChild;
    private static ResourceLocation ringTexture;
    protected ModelBiped outfitModel = new LOTRModelHobbit(0.5f, 64, 64);

    public LOTRRenderHobbit() {
        super(new LOTRModelHobbit(), 0.5f);
        this.setRenderPassModel(this.outfitModel);
        hobbitSkinsMale = LOTRRandomSkins.loadSkinsList("lotr:mob/hobbit/hobbit_male");
        hobbitSkinsFemale = LOTRRandomSkins.loadSkinsList("lotr:mob/hobbit/hobbit_female");
        hobbitSkinsMaleChild = LOTRRandomSkins.loadSkinsList("lotr:mob/hobbit/child_male");
        hobbitSkinsFemaleChild = LOTRRandomSkins.loadSkinsList("lotr:mob/hobbit/child_female");
    }

    @Override
    protected void func_82421_b() {
        this.field_82423_g = new LOTRModelHobbit(1.0f);
        this.field_82425_h = new LOTRModelHobbit(0.5f);
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity) {
        LOTREntityHobbit hobbit = (LOTREntityHobbit)entity;
        boolean child = hobbit.isChild();
        if (hobbit.familyInfo.isMale()) {
            if (child) {
                return hobbitSkinsMaleChild.getRandomSkin(hobbit);
            }
            return hobbitSkinsMale.getRandomSkin(hobbit);
        }
        if (child) {
            return hobbitSkinsFemaleChild.getRandomSkin(hobbit);
        }
        return hobbitSkinsFemale.getRandomSkin(hobbit);
    }

    @Override
    public int shouldRenderPass(EntityLiving entity, int pass, float f) {
        LOTREntityHobbit hobbit = (LOTREntityHobbit)entity;
        this.outfitModel.bipedRightArm.showModel = true;
        if (pass == 1 && hobbit.getClass() == hobbit.familyInfo.marriageEntityClass && hobbit.getEquipmentInSlot(4) != null && hobbit.getEquipmentInSlot(4).getItem() == hobbit.familyInfo.marriageRing) {
            this.bindTexture(ringTexture);
            this.outfitModel.bipedRightArm.showModel = false;
            this.setRenderPassModel(this.outfitModel);
            return 1;
        }
        return super.shouldRenderPass(entity, pass, f);
    }

    @Override
    protected void preRenderCallback(EntityLivingBase entity, float f) {
        super.preRenderCallback(entity, f);
        if (LOTRMod.isAprilFools()) {
            GL11.glScalef(2.0f, 2.0f, 2.0f);
        } else {
            GL11.glScalef(0.75f, 0.75f, 0.75f);
        }
    }

    @Override
    public float getHeldItemYTranslation() {
        return 0.075f;
    }

    static {
        ringTexture = new ResourceLocation("lotr:mob/hobbit/ring.png");
    }
}

