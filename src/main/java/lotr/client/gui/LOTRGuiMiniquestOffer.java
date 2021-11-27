package lotr.client.gui;

import java.util.*;

import org.lwjgl.opengl.GL11;

import lotr.client.render.entity.LOTRRenderBiped;
import lotr.common.entity.npc.*;
import lotr.common.network.LOTRPacketMiniquestOffer;
import lotr.common.quest.LOTRMiniQuest;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.util.*;

public class LOTRGuiMiniquestOffer
extends LOTRGuiScreenBase {
    private static ResourceLocation guiTexture = new ResourceLocation("lotr:gui/quest/miniquest.png");
    private static RenderItem renderItem = new RenderItem();
    private LOTRMiniQuest theMiniQuest;
    private LOTREntityNPC theNPC;
    private String description;
    private Random rand;
    private int openTick;
    public int xSize = 256;
    public int ySize = 200;
    private int guiLeft;
    private int guiTop;
    private int descriptionX = 85;
    private int descriptionY = 30;
    private int descriptionWidth = 160;
    private int npcX = 46;
    private int npcY = 90;
    private GuiButton buttonAccept;
    private GuiButton buttonDecline;
    private boolean sentClosePacket = false;
    private NPCAction npcAction;
    private int actionTick = 0;
    private int actionTime;
    private float actionSlow;
    private float headYaw;
    private float prevHeadYaw;
    private float headPitch;
    private float prevHeadPitch;

    public LOTRGuiMiniquestOffer(LOTRMiniQuest quest, LOTREntityNPC npc) {
        this.theMiniQuest = quest;
        this.theNPC = npc;
        this.rand = this.theNPC.getRNG();
        this.openTick = 0;
    }

    public void initGui() {
        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;
        this.buttonAccept = new LOTRGuiButtonRedBook(0, this.guiLeft + this.xSize / 2 - 20 - 80, this.guiTop + this.ySize - 30, 80, 20, StatCollector.translateToLocal("lotr.gui.miniquestOffer.accept"));
        this.buttonList.add(this.buttonAccept);
        this.buttonDecline = new LOTRGuiButtonRedBook(1, this.guiLeft + this.xSize / 2 + 20, this.guiTop + this.ySize - 30, 80, 20, StatCollector.translateToLocal("lotr.gui.miniquestOffer.decline"));
        this.buttonList.add(this.buttonDecline);
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        if (!this.theNPC.isEntityAlive() || this.mc.thePlayer.getDistanceToEntity(this.theNPC) > 8.0f) {
            this.mc.thePlayer.closeScreen();
        }
        this.prevHeadYaw = this.headYaw;
        this.prevHeadPitch = this.headPitch;
        if (this.npcAction == null) {
            if (this.openTick < 100) {
                this.npcAction = NPCAction.TALKING;
                this.actionTime = 100;
                this.actionSlow = 1.0f;
            } else if (this.rand.nextInt(200) == 0) {
                this.npcAction = NPCAction.getRandomAction(this.rand);
                if (this.npcAction == NPCAction.TALKING) {
                    this.actionTime = 40 + this.rand.nextInt(60);
                    this.actionSlow = 1.0f;
                } else if (this.npcAction == NPCAction.LOOKING) {
                    this.actionTime = 60 + this.rand.nextInt(60);
                    this.actionSlow = 1.0f;
                } else if (this.npcAction == NPCAction.SHAKING) {
                    this.actionTime = 100 + this.rand.nextInt(60);
                    this.actionSlow = 1.0f;
                } else if (this.npcAction == NPCAction.LOOKING_UP) {
                    this.actionTime = 30 + this.rand.nextInt(50);
                    this.actionSlow = 1.0f;
                }
            }
        } else {
            ++this.actionTick;
        }
        if (this.npcAction != null) {
            if (this.actionTick >= this.actionTime) {
                this.npcAction = null;
                this.actionTick = 0;
                this.actionTime = 0;
            } else if (this.npcAction == NPCAction.TALKING) {
                if (this.actionTick % 20 == 0) {
                    this.actionSlow = 0.7f + this.rand.nextFloat() * 1.5f;
                }
                float slow = this.actionSlow * 2.0f;
                this.headYaw = MathHelper.sin(this.actionTick / slow) * (float)Math.toRadians(10.0);
                this.headPitch = (MathHelper.sin(this.actionTick / slow * 2.0f) + 1.0f) / 2.0f * (float)Math.toRadians(-20.0);
            } else if (this.npcAction == NPCAction.SHAKING) {
                this.actionSlow += 0.01f;
                this.headYaw = MathHelper.sin(this.actionTick / this.actionSlow) * (float)Math.toRadians(30.0);
                this.headPitch += (float)Math.toRadians(0.4);
            } else if (this.npcAction == NPCAction.LOOKING) {
                float slow = this.actionSlow * 16.0f;
                this.headYaw = MathHelper.sin(this.actionTick / slow) * (float)Math.toRadians(60.0);
                this.headPitch = (MathHelper.sin(this.actionTick / slow * 2.0f) + 1.0f) / 2.0f * (float)Math.toRadians(-15.0);
            } else if (this.npcAction == NPCAction.LOOKING_UP) {
                this.headYaw = 0.0f;
                this.headPitch = (float)Math.toRadians(-20.0);
            }
        } else {
            this.headYaw = 0.0f;
            this.headPitch = MathHelper.sin(this.openTick * 0.07f) * (float)Math.toRadians(5.0);
        }
        ++this.openTick;
    }

    public void drawScreen(int i, int j, float f) {
        int objY;
        if (this.description == null) {
            this.description = LOTRSpeech.formatSpeech(this.theMiniQuest.quoteStart, this.mc.thePlayer, null, this.theMiniQuest.getObjectiveInSpeech());
        }
        this.drawDefaultBackground();
        this.mc.getTextureManager().bindTexture(guiTexture);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
        String name = this.theNPC.getNPCName();
        this.drawCenteredString(name, this.guiLeft + this.xSize / 2, this.guiTop + 8, 8019267);
        this.renderNPC(this.guiLeft + this.npcX, this.guiTop + this.npcY, this.guiLeft + this.npcX - i, this.guiTop + this.npcY - j, f);
        this.fontRendererObj.drawSplitString(this.description, this.guiLeft + this.descriptionX, this.guiTop + this.descriptionY, this.descriptionWidth, 8019267);
        String objective = this.theMiniQuest.getQuestObjective();
        int objLineWidth = this.xSize - 64;
        List objectiveLines = this.fontRendererObj.listFormattedStringToWidth(objective, objLineWidth);
        int objFirstLineY = objY = this.guiTop + this.ySize - 50;
        for (Object obj : objectiveLines) {
            String line = (String)obj;
            this.drawCenteredString(line, this.guiLeft + this.xSize / 2, objY, 8019267);
            objY += this.fontRendererObj.FONT_HEIGHT;
        }
        int objFirstLineWidth = this.fontRendererObj.getStringWidth((String)objectiveLines.get(0));
        RenderHelper.enableGUIStandardItemLighting();
        GL11.glDisable(2896);
        GL11.glEnable(32826);
        GL11.glEnable(2896);
        GL11.glEnable(2884);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        int iconW = 16;
        int iconB = 6;
        int iconY = objFirstLineY + this.fontRendererObj.FONT_HEIGHT / 2 - iconW / 2;
        renderItem.renderItemAndEffectIntoGUI(this.mc.fontRenderer, this.mc.getTextureManager(), this.theMiniQuest.getQuestIcon(), this.guiLeft + this.xSize / 2 - objFirstLineWidth / 2 - iconW - iconB, iconY);
        renderItem.renderItemAndEffectIntoGUI(this.mc.fontRenderer, this.mc.getTextureManager(), this.theMiniQuest.getQuestIcon(), this.guiLeft + this.xSize / 2 + objFirstLineWidth / 2 + iconB, iconY);
        GL11.glDisable(2896);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        super.drawScreen(i, j, f);
    }

    private void renderNPC(int i, int j, float dx, float dy, float f) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        float scale = 70.0f;
        GL11.glEnable(2903);
        GL11.glPushMatrix();
        GL11.glTranslatef(i, j, 40.0f);
        GL11.glScalef((-scale), (-scale), (-scale));
        GL11.glRotatef(180.0f, 0.0f, 0.0f, 1.0f);
        GL11.glRotatef(135.0f, 0.0f, 1.0f, 0.0f);
        RenderHelper.enableStandardItemLighting();
        GL11.glRotatef(-135.0f, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef((float)Math.atan(dx / 40.0f) * 20.0f, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(-((float)Math.atan(dy / 40.0f)) * 20.0f, 1.0f, 0.0f, 0.0f);
        GL11.glTranslatef(0.0f, this.theNPC.yOffset, 0.0f);
        RenderManager.instance.playerViewY = 180.0f;
        GL11.glDisable(2884);
        GL11.glEnable(32826);
        GL11.glEnable(3008);
        Render render = RenderManager.instance.getEntityRenderObject(this.theNPC);
        if (render instanceof LOTRRenderBiped) {
            LOTRRenderBiped npcRenderer = (LOTRRenderBiped)render;
            ModelBiped model = npcRenderer.modelBipedMain;
            model.isChild = this.theNPC.isChild();
            this.mc.getTextureManager().bindTexture(npcRenderer.getEntityTexture(this.theNPC));
            GL11.glTranslatef(0.0f, -model.bipedHead.rotationPointY / 16.0f, 0.0f);
            float yaw = this.prevHeadYaw + (this.headYaw - this.prevHeadYaw) * f;
            float pitch = this.prevHeadPitch + (this.headPitch - this.prevHeadPitch) * f;
            yaw = (float)Math.toDegrees(yaw);
            pitch = (float)Math.toDegrees(pitch);
            GL11.glRotatef(yaw, 0.0f, 1.0f, 0.0f);
            GL11.glRotatef(pitch, 1.0f, 0.0f, 0.0f);
            model.bipedHeadwear.rotateAngleX = 0.0f;
            model.bipedHead.rotateAngleX = 0.0f;
            model.bipedHeadwear.rotateAngleY = 0.0f;
            model.bipedHead.rotateAngleY = 0.0f;
            model.bipedHeadwear.rotateAngleZ = 0.0f;
            model.bipedHead.rotateAngleZ = 0.0f;
            model.bipedHead.render(0.0625f);
            model.bipedHeadwear.render(0.0625f);
            for (int pass = 0; pass < 4; ++pass) {
                int l;
                ModelRenderer part;
                int shouldRenderPass = npcRenderer.shouldRenderPass(this.theNPC, pass, 1.0f);
                if (shouldRenderPass <= 0) continue;
                model = npcRenderer.npcRenderPassModel;
                model.isChild = this.theNPC.isChild();
                List modelParts = model.boxList;
                boolean[] prevShowModels = new boolean[modelParts.size()];
                for (l = 0; l < modelParts.size(); ++l) {
                    part = (ModelRenderer)modelParts.get(l);
                    prevShowModels[l] = part.showModel;
                    boolean isHeadPart = false;
                    if (this.recursiveCheckForModel(model.bipedHead, part)) {
                        isHeadPart = true;
                    } else if (this.recursiveCheckForModel(model.bipedHeadwear, part)) {
                        isHeadPart = true;
                    }
                    if (isHeadPart) continue;
                    part.showModel = false;
                }
                model.render(this.theNPC, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
                if ((shouldRenderPass & 0xF0) == 16) {
                    npcRenderer.func_82408_c(this.theNPC, pass, 1.0f);
                    model.render(this.theNPC, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
                }
                for (l = 0; l < modelParts.size(); ++l) {
                    part = (ModelRenderer)modelParts.get(l);
                    part.showModel = prevShowModels[l];
                }
            }
        }
        GL11.glEnable(2884);
        GL11.glDisable(32826);
        GL11.glPopMatrix();
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(32826);
        OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GL11.glDisable(3553);
        OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }

    private boolean recursiveCheckForModel(ModelRenderer base, ModelRenderer match) {
        if (base == match) {
            return true;
        }
        if (base.childModels != null) {
            for (Object obj : base.childModels) {
                ModelRenderer part = (ModelRenderer)obj;
                if (!this.recursiveCheckForModel(part, match)) continue;
                return true;
            }
        }
        return false;
    }

    protected void actionPerformed(GuiButton button) {
        if (button.enabled) {
            boolean close = false;
            if (button == this.buttonAccept) {
                LOTRPacketMiniquestOffer.sendClosePacket(this.mc.thePlayer, this.theNPC, true);
                close = true;
            } else if (button == this.buttonDecline) {
                LOTRPacketMiniquestOffer.sendClosePacket(this.mc.thePlayer, this.theNPC, false);
                close = true;
            }
            if (close) {
                this.sentClosePacket = true;
                this.mc.thePlayer.closeScreen();
            }
        }
    }

    public void onGuiClosed() {
        super.onGuiClosed();
        if (!this.sentClosePacket) {
            LOTRPacketMiniquestOffer.sendClosePacket(this.mc.thePlayer, this.theNPC, false);
            this.sentClosePacket = true;
        }
    }

    private enum NPCAction {
        TALKING(1.0f),
        SHAKING(0.1f),
        LOOKING(0.3f),
        LOOKING_UP(0.4f);

        private static float totalWeight;
        private final float weight;

        NPCAction(float f) {
            this.weight = f;
        }

        public static NPCAction getRandomAction(Random rand) {
            if (totalWeight <= 0.0f) {
                totalWeight = 0.0f;
                for (NPCAction action : NPCAction.values()) {
                    totalWeight += action.weight;
                }
            }
            float f = rand.nextFloat();
            f *= totalWeight;
            NPCAction chosen = null;
            for (NPCAction action : NPCAction.values()) {
                if (((f -= action.weight) > 0.0f)) continue;
                chosen = action;
                break;
            }
            return chosen;
        }

        static {
            totalWeight = -1.0f;
        }
    }

}

