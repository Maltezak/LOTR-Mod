package lotr.client.render.item;

import java.io.IOException;
import java.util.*;

import org.lwjgl.opengl.GL11;

import lotr.client.LOTRClientProxy;
import lotr.common.item.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.resources.IResource;
import net.minecraft.entity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraftforge.client.IItemRenderer;

public class LOTRRenderLargeItem
implements IItemRenderer {
    private static Map<String, Float> sizeFolders = new HashMap<>();
    private final Item theItem;
    private final String folderName;
    private final float largeIconScale;
    private IIcon largeIcon;
    private List<ExtraLargeIconToken> extraTokens = new ArrayList<>();

    private static ResourceLocation getLargeTexturePath(Item item, String folder) {
        String prefix = "lotr:";
        String itemName = item.getUnlocalizedName();
        itemName = itemName.substring(itemName.indexOf(prefix) + prefix.length());
        String s = prefix + "textures/items/" + folder + "/" + itemName;
        s = s + ".png";
        return new ResourceLocation(s);
    }

    public static LOTRRenderLargeItem getRendererIfLarge(Item item) {
        for (String folder : sizeFolders.keySet()) {
            float iconScale = sizeFolders.get(folder);
            try {
                ResourceLocation resLoc = LOTRRenderLargeItem.getLargeTexturePath(item, folder);
                IResource res = Minecraft.getMinecraft().getResourceManager().getResource(resLoc);
                if (res == null) continue;
                return new LOTRRenderLargeItem(item, folder, iconScale);
            }
            catch (IOException resLoc) {
            }
        }
        return null;
    }

    public LOTRRenderLargeItem(Item item, String dir, float f) {
        this.theItem = item;
        this.folderName = dir;
        this.largeIconScale = f;
    }

    public ExtraLargeIconToken extraIcon(String name) {
        ExtraLargeIconToken token = new ExtraLargeIconToken(name);
        this.extraTokens.add(token);
        return token;
    }

    public void registerIcons(IIconRegister register) {
        this.largeIcon = this.registerLargeIcon(register, null);
        for (ExtraLargeIconToken token : this.extraTokens) {
            token.icon = this.registerLargeIcon(register, token.name);
        }
    }

    private IIcon registerLargeIcon(IIconRegister register, String extra) {
        String prefix = "lotr:";
        String itemName = this.theItem.getUnlocalizedName();
        itemName = itemName.substring(itemName.indexOf(prefix) + prefix.length());
        String path = prefix + this.folderName + "/" + itemName;
        if (!StringUtils.isNullOrEmpty(extra)) {
            path = path + "_" + extra;
        }
        return register.registerIcon(path);
    }

    private void doTransformations() {
        GL11.glTranslatef(-(this.largeIconScale - 1.0f) / 2.0f, -(this.largeIconScale - 1.0f) / 2.0f, 0.0f);
        GL11.glScalef(this.largeIconScale, this.largeIconScale, 1.0f);
    }

    public void renderLargeItem() {
        this.renderLargeItem(this.largeIcon);
    }

    public void renderLargeItem(ExtraLargeIconToken token) {
        this.renderLargeItem(token.icon);
    }

    private void renderLargeItem(IIcon icon) {
        this.doTransformations();
        Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.locationItemsTexture);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        Tessellator tess = Tessellator.instance;
        ItemRenderer.renderItemIn2D(tess, icon.getMaxU(), icon.getMinV(), icon.getMinU(), icon.getMaxV(), icon.getIconWidth(), icon.getIconHeight(), 0.0625f);
    }

    public boolean handleRenderType(ItemStack itemstack, IItemRenderer.ItemRenderType type) {
        return type == IItemRenderer.ItemRenderType.EQUIPPED || type == IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON;
    }

    public boolean shouldUseRenderHelper(IItemRenderer.ItemRenderType type, ItemStack itemstack, IItemRenderer.ItemRendererHelper helper) {
        return false;
    }

    public void renderItem(IItemRenderer.ItemRenderType type, ItemStack itemstack, Object ... data) {
        EntityLivingBase entityliving;
        GL11.glPushMatrix();
        Entity holder = (Entity)data[1];
        boolean isFirstPerson = holder == Minecraft.getMinecraft().thePlayer && Minecraft.getMinecraft().gameSettings.thirdPersonView == 0;
        Item item = itemstack.getItem();
        if (item instanceof LOTRItemSpear && holder instanceof EntityPlayer && ((EntityPlayer)holder).getItemInUse() == itemstack) {
            GL11.glRotatef(260.0f, 0.0f, 0.0f, 1.0f);
            GL11.glTranslatef(-1.0f, 0.0f, 0.0f);
        }
        if (item instanceof LOTRItemPike && holder instanceof EntityLivingBase && (entityliving = (EntityLivingBase)holder).getHeldItem() == itemstack && entityliving.swingProgress <= 0.0f) {
            if (entityliving.isSneaking()) {
                if (isFirstPerson) {
                    GL11.glRotatef(270.0f, 0.0f, 0.0f, 1.0f);
                    GL11.glTranslatef(-1.0f, 0.0f, 0.0f);
                } else {
                    GL11.glTranslatef(0.0f, -0.1f, 0.0f);
                    GL11.glRotatef(20.0f, 0.0f, 0.0f, 1.0f);
                }
            } else if (!isFirstPerson) {
                GL11.glTranslatef(0.0f, -0.3f, 0.0f);
                GL11.glRotatef(40.0f, 0.0f, 0.0f, 1.0f);
            }
        }
        if (item instanceof LOTRItemLance && holder instanceof EntityLivingBase && (entityliving = (EntityLivingBase)holder).getHeldItem() == itemstack) {
            if (isFirstPerson) {
                GL11.glRotatef(260.0f, 0.0f, 0.0f, 1.0f);
                GL11.glTranslatef(-1.0f, 0.0f, 0.0f);
            } else {
                GL11.glTranslatef(0.7f, 0.0f, 0.0f);
                GL11.glRotatef(-30.0f, 0.0f, 0.0f, 1.0f);
                GL11.glTranslatef(-1.0f, 0.0f, 0.0f);
            }
        }
        this.renderLargeItem();
        if (itemstack != null && itemstack.hasEffect(0)) {
            LOTRClientProxy.renderEnchantmentEffect();
        }
        GL11.glPopMatrix();
    }

    static {
        sizeFolders.put("large", 2.0f);
        sizeFolders.put("large2", 3.0f);
    }

    public static class ExtraLargeIconToken {
        public String name;
        public IIcon icon;

        public ExtraLargeIconToken(String s) {
            this.name = s;
        }
    }

}

