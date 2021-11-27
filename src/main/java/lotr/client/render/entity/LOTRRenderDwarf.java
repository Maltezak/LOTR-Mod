package lotr.client.render.entity;

import org.lwjgl.opengl.GL11;

import lotr.client.model.LOTRModelDwarf;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.*;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.*;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderDwarf extends LOTRRenderBiped {
    private static LOTRRandomSkins dwarfSkinsMale;
    private static LOTRRandomSkins dwarfSkinsFemale;
    private static LOTRRandomSkins blueDwarfSkinsMale;
    private static LOTRRandomSkins blueDwarfSkinsFemale;
    private static ResourceLocation ringTexture;
    protected ModelBiped standardRenderPassModel = new LOTRModelDwarf(0.5f, 64, 64);

    public LOTRRenderDwarf() {
        super(new LOTRModelDwarf(), 0.5f);
        this.setRenderPassModel(this.standardRenderPassModel);
        dwarfSkinsMale = LOTRRandomSkins.loadSkinsList("lotr:mob/dwarf/dwarf_male");
        dwarfSkinsFemale = LOTRRandomSkins.loadSkinsList("lotr:mob/dwarf/dwarf_female");
        blueDwarfSkinsMale = LOTRRandomSkins.loadSkinsList("lotr:mob/dwarf/blueMountains_male");
        blueDwarfSkinsFemale = LOTRRandomSkins.loadSkinsList("lotr:mob/dwarf/blueMountains_female");
    }

    @Override
    protected void func_82421_b() {
        this.field_82423_g = new LOTRModelDwarf(1.0f);
        this.field_82425_h = new LOTRModelDwarf(0.5f);
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity) {
        LOTREntityDwarf dwarf = (LOTREntityDwarf) entity;
        if(dwarf instanceof LOTREntityBlueDwarf) {
            if(dwarf.familyInfo.isMale()) {
                return blueDwarfSkinsMale.getRandomSkin(dwarf);
            }
            return blueDwarfSkinsFemale.getRandomSkin(dwarf);
        }
        if(dwarf.familyInfo.isMale()) {
            return dwarfSkinsMale.getRandomSkin(dwarf);
        }
        return dwarfSkinsFemale.getRandomSkin(dwarf);
    }

    @Override
    public int shouldRenderPass(EntityLiving entity, int pass, float f) {
        LOTREntityDwarf dwarf = (LOTREntityDwarf) entity;
        if(pass == 1 && dwarf.getClass() == dwarf.familyInfo.marriageEntityClass && dwarf.getEquipmentInSlot(4) != null && dwarf.getEquipmentInSlot(4).getItem() == dwarf.familyInfo.marriageRing) {
            this.bindTexture(ringTexture);
            this.setRenderPassModel(this.standardRenderPassModel);
            ((ModelBiped) this.renderPassModel).bipedRightArm.showModel = false;
            return 1;
        }
        return super.shouldRenderPass(entity, pass, f);
    }

    @Override
    protected void preRenderCallback(EntityLivingBase entity, float f) {
        super.preRenderCallback(entity, f);
        GL11.glScalef(0.8125f, 0.8125f, 0.8125f);
        if(LOTRMod.isAprilFools()) {
            GL11.glRotatef(180.0f, 0.0f, 0.0f, 1.0f);
        }
    }

    @Override
    public float getHeldItemYTranslation() {
        return 0.125f;
    }

    static {
        ringTexture = new ResourceLocation("lotr:mob/dwarf/ring.png");
    }
}
