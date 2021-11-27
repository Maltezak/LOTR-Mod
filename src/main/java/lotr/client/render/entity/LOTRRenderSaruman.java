package lotr.client.render.entity;

import java.awt.Color;
import java.util.Random;

import org.lwjgl.opengl.GL11;

import lotr.client.model.LOTRModelHuman;
import net.minecraft.entity.*;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderSaruman extends LOTRRenderBiped {
    private static ResourceLocation skin = new ResourceLocation("lotr:mob/char/saruman.png");
    private Random rand = new Random();
    private boolean twitch;

    public LOTRRenderSaruman() {
        super(new LOTRModelHuman(), 0.5f);
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity) {
        return skin;
    }

    @Override
    protected void preRenderCallback(EntityLivingBase entity, float f) {
        super.preRenderCallback(entity, f);
        if(entity.ticksExisted % 60 == 0) {
            this.twitch = !this.twitch;
        }
        if(this.twitch) {
            GL11.glRotatef(this.rand.nextFloat() * 40.0f, 0.0f, 0.0f, 1.0f);
            GL11.glRotatef(this.rand.nextFloat() * 40.0f, 0.0f, 1.0f, 0.0f);
            GL11.glRotatef(this.rand.nextFloat() * 40.0f, 1.0f, 0.0f, 0.0f);
            GL11.glTranslatef(this.rand.nextFloat() * 0.5f, this.rand.nextFloat() * 0.5f, this.rand.nextFloat() * 0.5f);
        }
        int i = entity.ticksExisted % 360;
        float hue = i / 360.0f;
        Color color = Color.getHSBColor(hue, 1.0f, 1.0f);
        float r = color.getRed() / 255.0f;
        float g = color.getGreen() / 255.0f;
        float b = color.getBlue() / 255.0f;
        GL11.glColor3f(r, g, b);
    }
}
