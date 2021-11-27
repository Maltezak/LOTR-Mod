package lotr.client.gui;

import java.util.*;

import org.lwjgl.opengl.GL11;

import lotr.client.*;
import lotr.common.LOTRAchievement;
import lotr.common.fac.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.util.*;

public class LOTRGuiNotificationDisplay extends Gui {
    private static int guiXSize = 190;
    private static int guiYSize = 32;
    private static RenderItem itemRenderer = new RenderItem();
    private Minecraft mc;
    private int windowWidth;
    private int windowHeight;
    private List<Notification> notifications = new ArrayList<>();
    private Set<Notification> notificationsToRemove = new HashSet<>();

    public LOTRGuiNotificationDisplay() {
        this.mc = Minecraft.getMinecraft();
    }

    public void queueAchievement(LOTRAchievement achievement) {
        this.notifications.add(new NotificationAchievement(achievement));
    }

    public void queueFellowshipNotification(IChatComponent message) {
        this.notifications.add(new NotificationFellowship(message));
    }

    public void queueConquest(LOTRFaction fac, float conq, boolean cleansing) {
        this.notifications.add(new NotificationConquest(fac, conq, cleansing));
    }

    private void updateWindowScale() {
        GL11.glViewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
        GL11.glMatrixMode(5889);
        GL11.glLoadIdentity();
        GL11.glMatrixMode(5888);
        GL11.glLoadIdentity();
        this.windowWidth = this.mc.displayWidth;
        this.windowHeight = this.mc.displayHeight;
        ScaledResolution scaledresolution = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
        this.windowWidth = scaledresolution.getScaledWidth();
        this.windowHeight = scaledresolution.getScaledHeight();
        GL11.glClear(256);
        GL11.glMatrixMode(5889);
        GL11.glLoadIdentity();
        GL11.glOrtho(0.0, this.windowWidth, this.windowHeight, 0.0, 1000.0, 3000.0);
        GL11.glMatrixMode(5888);
        GL11.glLoadIdentity();
        GL11.glTranslatef(0.0f, 0.0f, -2000.0f);
    }

    public void updateWindow() {
        if(!this.notifications.isEmpty()) {
            int index = 0;
            for(Notification notif : this.notifications) {
                long notifTime = notif.getNotificationTime();
                double d0 = (double) (Minecraft.getSystemTime() - notifTime) / (double) notif.getDurationMs();
                if(d0 < 0.0 || d0 > 1.0) {
                    this.notificationsToRemove.add(notif);
                }
                else {
                    this.updateWindowScale();
                    if(Minecraft.isGuiEnabled()) {
                        GL11.glEnable(3008);
                        GL11.glDisable(2929);
                        GL11.glDepthMask(false);
                        double d1 = d0 * 2.0;
                        if(d1 > 1.0) {
                            d1 = 2.0 - d1;
                        }
                        d1 *= 4.0;
                        if((d1 = 1.0 - d1) < 0.0) {
                            d1 = 0.0;
                        }
                        d1 *= d1;
                        d1 *= d1;
                        int i = this.windowWidth - guiXSize;
                        int j = 0 - (int) (d1 * 36.0);
                        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                        GL11.glEnable(3553);
                        this.mc.getTextureManager().bindTexture(LOTRGuiAchievements.iconsTexture);
                        GL11.glDisable(2896);
                        this.drawTexturedModalRect(i, j += index * (guiYSize + 8), 0, 200, guiXSize, guiYSize);
                        notif.renderText(i + 30, j + 7);
                        GL11.glEnable(3008);
                        notif.renderIcon(i + 8, j + 8);
                    }
                }
                ++index;
            }
        }
        if(!this.notificationsToRemove.isEmpty()) {
            this.notifications.removeAll(this.notificationsToRemove);
        }
    }

    private class NotificationConquest extends Notification {
        private LOTRFaction conqFac;
        private float conqValue;
        private boolean isCleansing;

        public NotificationConquest(LOTRFaction fac, float conq, boolean clean) {
            this.conqFac = fac;
            this.conqValue = conq;
            this.isCleansing = clean;
        }

        @Override
        public int getDurationMs() {
            return 6000;
        }

        @Override
        public void renderText(int x, int y) {
            String conqS = LOTRAlignmentValues.formatConqForDisplay(this.conqValue, false);
            LOTRTickHandlerClient.drawConquestText(mc.fontRenderer, x + 1, y, conqS, this.isCleansing, 1.0f);
            mc.fontRenderer.drawString(StatCollector.translateToLocal("lotr.gui.map.conquest.notif"), x + mc.fontRenderer.getStringWidth(conqS + " ") + 2, y, 8019267);
            mc.fontRenderer.drawString((EnumChatFormatting.ITALIC) + this.conqFac.factionName(), x, y + 11, 9666921);
        }

        @Override
        public void renderIcon(int x, int y) {
            LOTRGuiNotificationDisplay.this.mc.getTextureManager().bindTexture(LOTRClientProxy.alignmentTexture);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            LOTRGuiNotificationDisplay.this.drawTexturedModalRect(x, y, this.isCleansing ? 16 : 0, 228, 16, 16);
        }
    }

    private class NotificationFellowship extends Notification {
        private IChatComponent message;

        public NotificationFellowship(IChatComponent msg) {
            this.message = msg;
        }

        @Override
        public int getDurationMs() {
            return 6000;
        }

        @Override
        public void renderText(int x, int y) {
            mc.fontRenderer.drawSplitString(this.message.getFormattedText(), x, y, 152, 8019267);
        }

        @Override
        public void renderIcon(int x, int y) {
            LOTRGuiNotificationDisplay.this.mc.getTextureManager().bindTexture(LOTRGuiFellowships.iconsTextures);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            LOTRGuiNotificationDisplay.this.drawTexturedModalRect(x, y, 80, 0, 16, 16);
        }
    }

    private class NotificationAchievement extends Notification {
        private LOTRAchievement achievement;

        public NotificationAchievement(LOTRAchievement ach) {
            this.achievement = ach;
        }

        @Override
        public int getDurationMs() {
            return 3000;
        }

        @Override
        public void renderText(int x, int y) {
            mc.fontRenderer.drawString(StatCollector.translateToLocal("achievement.get"), x, y, 8019267);
            mc.fontRenderer.drawString(this.achievement.getTitle(mc.thePlayer), x, y + 11, 8019267);
        }

        @Override
        public void renderIcon(int x, int y) {
            RenderHelper.enableGUIStandardItemLighting();
            GL11.glDisable(2896);
            GL11.glEnable(32826);
            GL11.glEnable(2903);
            GL11.glEnable(2896);
            itemRenderer.renderItemAndEffectIntoGUI(mc.fontRenderer, LOTRGuiNotificationDisplay.this.mc.getTextureManager(), this.achievement.icon, x, y);
            GL11.glDisable(2896);
            GL11.glDepthMask(true);
            GL11.glEnable(2929);
            GL11.glEnable(3008);
            LOTRGuiNotificationDisplay.this.mc.getTextureManager().bindTexture(LOTRGuiAchievements.iconsTexture);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            LOTRGuiNotificationDisplay.this.drawTexturedModalRect(x + 162, y + 1, 190, 17, 16, 16);
        }
    }

    private abstract class Notification {
        private Long notificationTime = Minecraft.getSystemTime();

        private Notification() {
        }

        public Long getNotificationTime() {
            return this.notificationTime;
        }

        public abstract int getDurationMs();

        public abstract void renderText(int var1, int var2);

        public abstract void renderIcon(int var1, int var2);
    }

}
