package lotr.client.render.tileentity;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

import javax.imageio.ImageIO;

import org.lwjgl.opengl.GL11;

import lotr.common.tileentity.LOTRTileEntitySignCarved;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;

public class LOTRRenderSignCarved extends TileEntitySpecialRenderer {
    private static Map<IIcon, Integer> iconAverageColors = new HashMap<>();
    private static Map<IIcon, Integer> iconContrastColors = new HashMap<>();

    @Override
    public void renderTileEntityAt(TileEntity tileentity, double d, double d1, double d2, float f) {
        LOTRTileEntitySignCarved sign = (LOTRTileEntitySignCarved) tileentity;
        int meta = tileentity.getBlockMetadata();
        float rotation = Direction.facingToDirection[meta] * 90.0f;
        float f1 = 0.6666667f;
        float f3 = 0.016666668f * f1;
        GL11.glDisable(32826);
        GL11.glPushMatrix();
        GL11.glTranslatef((float) d + 0.5f, (float) d1 + 0.75f * f1, (float) d2 + 0.5f);
        GL11.glRotatef(-rotation, 0.0f, 1.0f, 0.0f);
        GL11.glTranslatef(0.0f, -0.3125f, -0.4375f);
        GL11.glTranslatef(0.0f, 0.5f * f1, -0.09f * f1);
        GL11.glScalef(f3, -f3, f3);
        GL11.glNormal3f(0.0f, 0.0f, -1.0f * f3);
        GL11.glDepthMask(false);
        FontRenderer fontrenderer = this.func_147498_b();
        int color = this.getTextColor(sign, f);
        int signLines = sign.signText.length;
        int lineHeight = fontrenderer.FONT_HEIGHT + 1;
        int lineBase = -signLines * 5;
        if(signLines > 4) {
            lineBase = -((signLines - 1) * lineHeight) / 2;
        }
        for(int l = 0; l < signLines; ++l) {
            String s = sign.signText[l];
            if(l == sign.lineBeingEdited) {
                s = "> " + s + " <";
            }
            int lineX = -fontrenderer.getStringWidth(s) / 2;
            int lineY = lineBase + l * lineHeight;
            fontrenderer.drawString(s, lineX, lineY, color);
        }
        GL11.glDepthMask(true);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glPopMatrix();
    }

    protected int getTextColor(LOTRTileEntitySignCarved sign, float f) {
        return this.getContrastingColor(sign.getOnBlockIcon());
    }

    private int getContrastingColor(IIcon icon) {
        if(iconContrastColors.containsKey(icon)) {
            return iconContrastColors.get(icon);
        }
        int color = this.averageIconColor(icon);
        color = this.calculateContrast(color);
        iconContrastColors.put(icon, color);
        return color;
    }

    private int calculateContrast(int color) {
        Color c = new Color(color);
        float[] hsb = Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), null);
        float h = hsb[0];
        float s = hsb[1];
        float b = hsb[2];
        b = b > 0.5f ? (b -= 0.5f) : (b += 0.5f);
        b = MathHelper.clamp_float(b, 0.0f, 1.0f);
        return Color.HSBtoRGB(h, s *= 0.5f, b);
    }

    private int averageIconColor(IIcon icon) {
        if(iconAverageColors.containsKey(icon)) {
            return iconAverageColors.get(icon);
        }
        try {
            Minecraft mc = Minecraft.getMinecraft();
            String iconName = icon.getIconName();
            String iconPath = "";
            String iconBaseName = iconName;
            int indexP = iconName.indexOf(":");
            if(indexP > 0) {
                iconPath = iconName.substring(0, indexP);
                iconBaseName = iconName.substring(indexP + 1);
            }
            ResourceLocation res = new ResourceLocation(iconPath, "textures/blocks/" + iconBaseName + ".png");
            BufferedImage iconImage = ImageIO.read(mc.getResourceManager().getResource(res).getInputStream());
            int width = iconImage.getWidth();
            int height = iconImage.getHeight();
            int totalR = 0;
            int totalG = 0;
            int totalB = 0;
            int count = 0;
            for(int y = 0; y < height; ++y) {
                for(int x = 0; x < width; ++x) {
                    int rgb = iconImage.getRGB(x, y);
                    int r = rgb >> 16 & 0xFF;
                    int g = rgb >> 8 & 0xFF;
                    int b = rgb >> 0 & 0xFF;
                    totalR += r;
                    totalG += g;
                    totalB += b;
                    ++count;
                }
            }
            int avgR = totalR / count & 0xFF;
            int avgG = totalG / count & 0xFF;
            int avgB = totalB / count & 0xFF;
            int avgColor = 0xFF000000 | avgR << 16 | avgG << 8 | avgB << 0;
            iconAverageColors.put(icon, avgColor);
            return avgColor;
        }
        catch(IOException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
