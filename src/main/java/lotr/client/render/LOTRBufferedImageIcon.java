package lotr.client.render;

import java.awt.image.BufferedImage;
import java.util.*;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;

public class LOTRBufferedImageIcon extends TextureAtlasSprite {
    private final String iconName;
    private final BufferedImage imageRGB;
    private static Set<String> loadedResources = new HashSet<>();

    public LOTRBufferedImageIcon(String s, BufferedImage rgb) {
        super(s);
        this.iconName = s;
        this.imageRGB = rgb;
        if(!loadedResources.contains(s)) {
            TextureManager texManager = Minecraft.getMinecraft().getTextureManager();
            ResourceLocation r = new ResourceLocation(this.iconName);
            ResourceLocation r1 = new ResourceLocation(r.getResourceDomain(), String.format("%s%s", r.getResourcePath(), ".png"));
            texManager.deleteTexture(r1);
            texManager.loadTexture(r1, new DynamicTexture(this.imageRGB));
            loadedResources.add(s);
        }
    }

    @Override
    public boolean load(IResourceManager resourceManager, ResourceLocation resourceLocation) {
        BufferedImage[] imageArray = new BufferedImage[1 + Minecraft.getMinecraft().gameSettings.mipmapLevels];
        imageArray[0] = this.imageRGB;
        this.loadSprite(imageArray, null, Minecraft.getMinecraft().gameSettings.anisotropicFiltering > 1.0f);
        return false;
    }

    @Override
    public boolean hasCustomLoader(IResourceManager resourceManager, ResourceLocation resourceLocation) {
        return true;
    }
}
