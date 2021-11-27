package lotr.client.render;

import java.util.Random;

import org.lwjgl.opengl.*;
import org.lwjgl.util.glu.Project;

import com.google.common.math.IntMath;

import lotr.client.LOTRReflectionClient;
import lotr.common.*;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;
import net.minecraftforge.client.IRenderHandler;

public class LOTRCloudRenderer
extends IRenderHandler {
    public static final ResourceLocation cloudTexture = new ResourceLocation("lotr:sky/clouds.png");
    private static int cloudRange;
    private static Random cloudRand;
    private static CloudProperty cloudOpacity;
    private static CloudProperty cloudSpeed;
    private static CloudProperty cloudAngle;
    private static double cloudPosXPre;
    private static double cloudPosX;
    private static double cloudPosZPre;
    private static double cloudPosZ;

    public static void updateClouds(WorldClient world) {
        cloudOpacity.update(world);
        cloudSpeed.update(world);
        cloudAngle.update(world);
        float angle = cloudAngle.getValue(1.0f);
        float speed = cloudSpeed.getValue(1.0f);
        cloudPosXPre = cloudPosX;
        cloudPosX += MathHelper.cos(angle) * speed;
        cloudPosZPre = cloudPosZ;
        cloudPosZ += MathHelper.sin(angle) * speed;
    }

    public static void resetClouds() {
        cloudOpacity.reset();
        cloudSpeed.reset();
        cloudAngle.reset();
    }

    public void render(float partialTicks, WorldClient world, Minecraft mc) {
        world.theProfiler.startSection("lotrClouds");
        if (world.provider.isSurfaceWorld()) {
            Block block = ActiveRenderInfo.getBlockAtEntityViewpoint(world, mc.renderViewEntity, partialTicks);
            if (block.getMaterial() == Material.water) {
                return;
            }
            cloudRange = LOTRConfig.cloudRange;
            GL11.glMatrixMode(5889);
            GL11.glPushMatrix();
            GL11.glLoadIdentity();
            float fov = LOTRReflectionClient.getFOVModifier(mc.entityRenderer, partialTicks, true);
            Project.gluPerspective(fov, (float)mc.displayWidth / (float)mc.displayHeight, 0.05f, cloudRange);
            GL11.glMatrixMode(5888);
            GL11.glPushMatrix();
            GL11.glDisable(2884);
            GL11.glDepthMask(false);
            GL11.glEnable(3008);
            float alphaFunc = GL11.glGetFloat(3010);
            GL11.glAlphaFunc(516, 0.01f);
            GL11.glEnable(3042);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            GL11.glFogi(2917, 9729);
            GL11.glFogf(2915, cloudRange * 0.9f);
            GL11.glFogf(2916, cloudRange);
            if (GLContext.getCapabilities().GL_NV_fog_distance) {
                GL11.glFogi(34138, 34139);
            }
            Tessellator tessellator = Tessellator.instance;
            mc.renderEngine.bindTexture(cloudTexture);
            Vec3 cloudColor = world.getCloudColour(partialTicks);
            float r = (float)cloudColor.xCoord;
            float g = (float)cloudColor.yCoord;
            float b = (float)cloudColor.zCoord;
            if (mc.gameSettings.anaglyph) {
                float r1 = (r * 30.0f + g * 59.0f + b * 11.0f) / 100.0f;
                float g1 = (r * 30.0f + g * 70.0f) / 100.0f;
                float b1 = (r * 30.0f + b * 70.0f) / 100.0f;
                r = r1;
                g = g1;
                b = b1;
            }
            Vec3 pos = mc.renderViewEntity.getPosition(partialTicks);
            for (int pass = 0; pass < 2; ++pass) {
                int scale = 4096 * IntMath.pow(2, pass);
                double invScaleD = 1.0 / scale;
                double posX = pos.xCoord;
                double posZ = pos.zCoord;
                double posY = pos.yCoord;
                double cloudPosXAdd = cloudPosXPre + (cloudPosX - cloudPosXPre) * partialTicks;
                double cloudPosZAdd = cloudPosZPre + (cloudPosZ - cloudPosZPre) * partialTicks;
                int x = MathHelper.floor_double((posX += (cloudPosXAdd /= pass + 1)) / scale);
                int z = MathHelper.floor_double((posZ += (cloudPosZAdd /= pass + 1)) / scale);
                double cloudX = posX - x * scale;
                double cloudZ = posZ - z * scale;
                double cloudY = world.provider.getCloudHeight() - posY + 0.33000001311302185 + pass * 50.0f;
                tessellator.startDrawingQuads();
                tessellator.setColorRGBA_F(r, g, b, (0.8f - pass * 0.5f) * cloudOpacity.getValue(partialTicks));
                int interval = cloudRange;
                for (int i = -cloudRange; i < cloudRange; i += interval) {
                    for (int k = -cloudRange; k < cloudRange; k += interval) {
                        int xMin = i + 0;
                        int xMax = i + interval;
                        int zMin = k + 0;
                        int zMax = k + interval;
                        double uMin = (xMin + cloudX) * invScaleD;
                        double uMax = (xMax + cloudX) * invScaleD;
                        double vMin = (zMin + cloudZ) * invScaleD;
                        double vMax = (zMax + cloudZ) * invScaleD;
                        tessellator.addVertexWithUV(xMin, cloudY, zMax, uMin, vMax);
                        tessellator.addVertexWithUV(xMax, cloudY, zMax, uMax, vMax);
                        tessellator.addVertexWithUV(xMax, cloudY, zMin, uMax, vMin);
                        tessellator.addVertexWithUV(xMin, cloudY, zMin, uMin, vMin);
                    }
                }
                tessellator.draw();
            }
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glEnable(2884);
            GL11.glDepthMask(true);
            GL11.glAlphaFunc(516, alphaFunc);
            GL11.glDisable(3042);
            GL11.glMatrixMode(5889);
            GL11.glPopMatrix();
            GL11.glMatrixMode(5888);
            GL11.glPopMatrix();
        }
        world.theProfiler.endSection();
    }

    static {
        cloudRand = new Random();
        cloudOpacity = new CloudProperty(233591206262L, 0.1f, 1.0f, 0.001f);
        cloudSpeed = new CloudProperty(6283905602629L, 0.0f, 0.5f, 0.001f);
        cloudAngle = new CloudProperty(360360635650636L, 0.0f, 6.2831855f, 0.01f);
    }

    private static class CloudProperty {
        private final long baseSeed;
        private float currentDayValue;
        private float value;
        private float prevValue;
        private final float minValue;
        private final float maxValue;
        private final float interval;

        public CloudProperty(long l, float min, float max, float i) {
            this.baseSeed = l;
            this.value = -1.0f;
            this.minValue = min;
            this.maxValue = max;
            this.interval = i;
        }

        public void reset() {
            this.value = -1.0f;
        }

        public float getValue(float f) {
            return this.prevValue + (this.value - this.prevValue) * f;
        }

        public void update(WorldClient world) {
            this.currentDayValue = this.getCurrentDayValue(world);
            if (this.value == -1.0f) {
                this.prevValue = this.value = this.currentDayValue;
            } else {
                this.prevValue = this.value;
                if (this.value > this.currentDayValue) {
                    this.value -= this.interval;
                    this.value = Math.max(this.value, this.currentDayValue);
                } else if (this.value < this.currentDayValue) {
                    this.value += this.interval;
                    this.value = Math.min(this.value, this.currentDayValue);
                }
            }
        }

        private float getCurrentDayValue(WorldClient world) {
            int day = LOTRDate.ShireReckoning.currentDay;
            long seed = day * this.baseSeed + day + 83025820626792L;
            cloudRand.setSeed(seed);
            float f = MathHelper.randomFloatClamp(cloudRand, this.minValue, this.maxValue);
            return f;
        }
    }

}

