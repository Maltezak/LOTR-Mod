package lotr.client.gui;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import lotr.common.entity.LOTREntities;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.network.*;
import lotr.common.tileentity.LOTRTileEntityMobSpawner;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class LOTRGuiMobSpawner extends LOTRGuiScreenBase {
    private static ResourceLocation guiTexture = new ResourceLocation("lotr:gui/mob_spawner.png");
    private World worldObj;
    private int posX;
    private int posY;
    private int posZ;
    private LOTRTileEntityMobSpawner tileEntity;
    private int xSize = 256;
    private int ySize = 256;
    private int guiLeft;
    private int guiTop;
    private ArrayList pageControls = new ArrayList();
    private ArrayList spawnerControls = new ArrayList();
    private ArrayList mobControls = new ArrayList();
    private int page;
    private String[] pageNames = new String[] {"Spawner Properties", "Entity Properties"};
    private GuiButton buttonPage;
    private GuiButton buttonRedstone;
    private LOTRGuiSlider sliderMinSpawnDelay;
    private LOTRGuiSlider sliderMaxSpawnDelay;
    private LOTRGuiSlider sliderNearbyMobLimit;
    private LOTRGuiSlider sliderNearbyMobRange;
    private LOTRGuiSlider sliderPlayerRange;
    private LOTRGuiSlider sliderMaxSpawnCount;
    private LOTRGuiSlider sliderXZRange;
    private LOTRGuiSlider sliderYRange;
    private GuiButton buttonMobType;
    private LOTRGuiSlider sliderMaxHealth;
    private LOTRGuiSlider sliderFollowRange;
    private LOTRGuiSlider sliderMoveSpeed;
    private LOTRGuiSlider sliderAttackDamage;
    private int active;
    private int minSpawnDelay;
    private int maxSpawnDelay;
    private int nearbyMobLimit;
    private int nearbyMobCheckRange;
    private int requiredPlayerRange;
    private int maxSpawnCount;
    private int maxSpawnRange;
    private int maxSpawnRangeVertical;
    private boolean spawnsPersistentNPCs;
    private int maxHealth;
    private int navigatorRange;
    private float moveSpeed;
    private float attackDamage;
    private boolean needsUpdate;
    private Class mobClass;
    private String mobName;

    public LOTRGuiMobSpawner(World world, int i, int j, int k) {
        this.worldObj = world;
        this.posX = i;
        this.posY = j;
        this.posZ = k;
        this.tileEntity = (LOTRTileEntityMobSpawner) this.worldObj.getTileEntity(this.posX, this.posY, this.posZ);
        this.syncTileEntityDataToGui();
    }

    private void syncTileEntityDataToGui() {
        this.active = this.tileEntity.active;
        this.spawnsPersistentNPCs = this.tileEntity.spawnsPersistentNPCs;
        this.minSpawnDelay = this.tileEntity.minSpawnDelay;
        this.maxSpawnDelay = this.tileEntity.maxSpawnDelay;
        this.nearbyMobLimit = this.tileEntity.nearbyMobLimit;
        this.nearbyMobCheckRange = this.tileEntity.nearbyMobCheckRange;
        this.requiredPlayerRange = this.tileEntity.requiredPlayerRange;
        this.maxSpawnCount = this.tileEntity.maxSpawnCount;
        this.maxSpawnRange = this.tileEntity.maxSpawnRange;
        this.maxSpawnRangeVertical = this.tileEntity.maxSpawnRangeVertical;
        this.maxHealth = this.tileEntity.maxHealth;
        this.navigatorRange = this.tileEntity.navigatorRange;
        this.moveSpeed = this.tileEntity.moveSpeed;
        this.attackDamage = this.tileEntity.attackDamage;
        this.mobClass = LOTREntities.getClassFromString(this.tileEntity.getEntityClassName());
        this.mobName = this.tileEntity.getEntityClassName();
    }

    private void sendGuiDataToClientTileEntity() {
        this.tileEntity.active = this.active;
        this.tileEntity.spawnsPersistentNPCs = this.spawnsPersistentNPCs;
        this.tileEntity.minSpawnDelay = this.minSpawnDelay;
        this.tileEntity.maxSpawnDelay = this.maxSpawnDelay;
        this.tileEntity.nearbyMobLimit = this.nearbyMobLimit;
        this.tileEntity.nearbyMobCheckRange = this.nearbyMobCheckRange;
        this.tileEntity.requiredPlayerRange = this.requiredPlayerRange;
        this.tileEntity.maxSpawnCount = this.maxSpawnCount;
        this.tileEntity.maxSpawnRange = this.maxSpawnRange;
        this.tileEntity.maxSpawnRangeVertical = this.maxSpawnRangeVertical;
        this.tileEntity.maxHealth = this.maxHealth;
        this.tileEntity.navigatorRange = this.navigatorRange;
        this.tileEntity.moveSpeed = this.moveSpeed;
        this.tileEntity.attackDamage = this.attackDamage;
    }

    private void sendGuiDataToServerTileEntity() {
        LOTRPacketMobSpawner packet = new LOTRPacketMobSpawner(this.posX, this.posY, this.posZ, this.mc.thePlayer.dimension);
        packet.active = this.active;
        packet.spawnsPersistentNPCs = this.spawnsPersistentNPCs;
        packet.minSpawnDelay = this.minSpawnDelay;
        packet.maxSpawnDelay = this.maxSpawnDelay;
        packet.nearbyMobLimit = this.nearbyMobLimit;
        packet.nearbyMobCheckRange = this.nearbyMobCheckRange;
        packet.requiredPlayerRange = this.requiredPlayerRange;
        packet.maxSpawnCount = this.maxSpawnCount;
        packet.maxSpawnRange = this.maxSpawnRange;
        packet.maxSpawnRangeVertical = this.maxSpawnRangeVertical;
        packet.maxHealth = this.maxHealth;
        packet.navigatorRange = this.navigatorRange;
        packet.moveSpeed = this.moveSpeed;
        packet.attackDamage = this.attackDamage;
        LOTRPacketHandler.networkWrapper.sendToServer(packet);
        this.needsUpdate = false;
    }

    @Override
    public void initGui() {
        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;
        this.buttonPage = new GuiButton(1, this.guiLeft + this.xSize / 2 + 2, this.guiTop + 52, 110, 20, this.pageNames[this.page]);
        this.pageControls.add(this.buttonPage);
        this.buttonRedstone = new GuiButton(0, this.guiLeft + this.xSize / 2 - 112, this.guiTop + 52, 110, 20, this.active == 2 ? "Redstone Activated" : (this.active == 1 ? "Active" : "Inactive"));
        this.spawnerControls.add(this.buttonRedstone);
        this.sliderMinSpawnDelay = new LOTRGuiSlider(1, this.guiLeft + this.xSize / 2 - 112, this.guiTop + 76, 224, 20, "Min Spawn Delay");
        this.spawnerControls.add(this.sliderMinSpawnDelay);
        this.sliderMinSpawnDelay.setMinutesSecondsTime();
        this.sliderMinSpawnDelay.setMinMaxValues(0, 60);
        this.sliderMinSpawnDelay.setSliderValue(this.minSpawnDelay / 20);
        this.sliderMaxSpawnDelay = new LOTRGuiSlider(2, this.guiLeft + this.xSize / 2 - 112, this.guiTop + 100, 224, 20, "Max Spawn Delay");
        this.spawnerControls.add(this.sliderMaxSpawnDelay);
        this.sliderMaxSpawnDelay.setMinutesSecondsTime();
        this.sliderMaxSpawnDelay.setMinMaxValues(0, 60);
        this.sliderMaxSpawnDelay.setSliderValue(this.maxSpawnDelay / 20);
        this.sliderNearbyMobLimit = new LOTRGuiSlider(3, this.guiLeft + this.xSize / 2 - 112, this.guiTop + 124, 224, 20, "Nearby Mob Limit");
        this.spawnerControls.add(this.sliderNearbyMobLimit);
        this.sliderNearbyMobLimit.setMinMaxValues(0, 32);
        this.sliderNearbyMobLimit.setSliderValue(this.nearbyMobLimit);
        this.sliderNearbyMobRange = new LOTRGuiSlider(4, this.guiLeft + this.xSize / 2 - 112, this.guiTop + 148, 224, 20, "Nearby Mob Check Range");
        this.spawnerControls.add(this.sliderNearbyMobRange);
        this.sliderNearbyMobRange.setMinMaxValues(0, 64);
        this.sliderNearbyMobRange.setSliderValue(this.nearbyMobCheckRange);
        this.sliderPlayerRange = new LOTRGuiSlider(5, this.guiLeft + this.xSize / 2 - 112, this.guiTop + 172, 224, 20, "Required Player Range");
        this.spawnerControls.add(this.sliderPlayerRange);
        this.sliderPlayerRange.setMinMaxValues(1, 100);
        this.sliderPlayerRange.setSliderValue(this.requiredPlayerRange);
        this.sliderMaxSpawnCount = new LOTRGuiSlider(6, this.guiLeft + this.xSize / 2 - 112, this.guiTop + 196, 224, 20, "Max Spawn Count");
        this.spawnerControls.add(this.sliderMaxSpawnCount);
        this.sliderMaxSpawnCount.setMinMaxValues(1, 16);
        this.sliderMaxSpawnCount.setSliderValue(this.maxSpawnCount);
        this.sliderXZRange = new LOTRGuiSlider(7, this.guiLeft + this.xSize / 2 - 112, this.guiTop + 220, 110, 20, "Horizontal Range");
        this.spawnerControls.add(this.sliderXZRange);
        this.sliderXZRange.setMinMaxValues(0, 64);
        this.sliderXZRange.setSliderValue(this.maxSpawnRange);
        this.sliderYRange = new LOTRGuiSlider(8, this.guiLeft + this.xSize / 2 + 2, this.guiTop + 220, 110, 20, "Vertical Range");
        this.spawnerControls.add(this.sliderYRange);
        this.sliderYRange.setMinMaxValues(0, 16);
        this.sliderYRange.setSliderValue(this.maxSpawnRangeVertical);
        this.buttonMobType = new GuiButton(0, this.guiLeft + this.xSize / 2 - 112, this.guiTop + 52, 110, 20, this.spawnsPersistentNPCs ? "Persistent NPCs" : "Normal NPCs");
        this.mobControls.add(this.buttonMobType);
        this.buttonMobType.enabled = LOTREntityNPC.class.isAssignableFrom(this.mobClass);
        this.sliderMaxHealth = new LOTRGuiSlider(1, this.guiLeft + this.xSize / 2 - 112, this.guiTop + 76, 224, 20, "Max Health");
        this.mobControls.add(this.sliderMaxHealth);
        this.sliderMaxHealth.setMinMaxValues(0, 200);
        this.sliderMaxHealth.setSliderValue(this.maxHealth);
        this.sliderFollowRange = new LOTRGuiSlider(2, this.guiLeft + this.xSize / 2 - 112, this.guiTop + 100, 224, 20, "Navigator Range");
        this.mobControls.add(this.sliderFollowRange);
        this.sliderFollowRange.setMinMaxValues(0, 100);
        this.sliderFollowRange.setSliderValue(this.navigatorRange);
        this.sliderMoveSpeed = new LOTRGuiSlider(3, this.guiLeft + this.xSize / 2 - 112, this.guiTop + 124, 224, 20, "Movement Speed");
        this.mobControls.add(this.sliderMoveSpeed);
        this.sliderMoveSpeed.setFloat();
        this.sliderMoveSpeed.setMinMaxValues_F(0.0f, 1.0f);
        this.sliderMoveSpeed.setSliderValue_F(this.moveSpeed);
        this.sliderAttackDamage = new LOTRGuiSlider(4, this.guiLeft + this.xSize / 2 - 112, this.guiTop + 148, 224, 20, "Base Attack Damage");
        this.mobControls.add(this.sliderAttackDamage);
        this.sliderAttackDamage.setFloat();
        this.sliderAttackDamage.setMinMaxValues_F(0.0f, 20.0f);
        this.sliderAttackDamage.setSliderValue_F(this.attackDamage);
    }

    @Override
    public void setWorldAndResolution(Minecraft mc, int i, int j) {
        this.pageControls.clear();
        this.spawnerControls.clear();
        this.mobControls.clear();
        super.setWorldAndResolution(mc, i, j);
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if(button.enabled && button.getClass() == GuiButton.class) {
            if(button == this.buttonRedstone && this.page == 0) {
                ++this.active;
                if(this.active > 2) {
                    this.active = 0;
                }
                switch(this.active) {
                    case 0: {
                        button.displayString = "Inactive";
                        break;
                    }
                    case 1: {
                        button.displayString = "Active";
                        break;
                    }
                    case 2: {
                        button.displayString = "Redstone Activated";
                    }
                }
            }
            if(button == this.buttonMobType) {
                if(this.page == 1) {
                    this.spawnsPersistentNPCs = !this.spawnsPersistentNPCs;
                    button.displayString = this.spawnsPersistentNPCs ? "Persistent NPCs" : "Normal NPCs";
                }
                this.needsUpdate = true;
            }
            if(button == this.buttonPage) {
                this.page = 1 - this.page;
                button.displayString = this.pageNames[this.page];
            }
        }
    }

    @Override
    public void drawScreen(int i, int j, float f) {
        block4: {
            int k;
            block3: {
                this.drawDefaultBackground();
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                this.mc.getTextureManager().bindTexture(guiTexture);
                this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
                this.fontRendererObj.drawString("Mob Spawner", this.guiLeft + 127 - this.fontRendererObj.getStringWidth("Mob Spawner") / 2, this.guiTop + 11, 4210752);
                this.fontRendererObj.drawString(this.mobName, this.guiLeft + 127 - this.fontRendererObj.getStringWidth(this.mobName) / 2, this.guiTop + 26, 4210752);
                super.drawScreen(i, j, f);
                for(k = 0; k < this.pageControls.size(); ++k) {
                    ((GuiButton) this.pageControls.get(k)).drawButton(this.mc, i, j);
                }
                if(this.page != 0) break block3;
                for(k = 0; k < this.spawnerControls.size(); ++k) {
                    ((GuiButton) this.spawnerControls.get(k)).drawButton(this.mc, i, j);
                }
                break block4;
            }
            if(this.page != 1) break block4;
            for(k = 0; k < this.mobControls.size(); ++k) {
                ((GuiButton) this.mobControls.get(k)).drawButton(this.mc, i, j);
            }
        }
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        if(this.page == 0) {
            if(this.sliderMinSpawnDelay.dragging) {
                this.minSpawnDelay = this.sliderMinSpawnDelay.getSliderValue() * 20;
                this.needsUpdate = true;
            }
            if(this.sliderMaxSpawnDelay.dragging) {
                this.maxSpawnDelay = this.sliderMaxSpawnDelay.getSliderValue() * 20;
                this.needsUpdate = true;
            }
            if(this.minSpawnDelay > this.maxSpawnDelay) {
                if(this.sliderMinSpawnDelay.dragging) {
                    this.sliderMaxSpawnDelay.setSliderValue(this.sliderMinSpawnDelay.getSliderValue());
                    this.maxSpawnDelay = this.minSpawnDelay;
                    this.needsUpdate = true;
                }
                else if(this.sliderMaxSpawnDelay.dragging) {
                    this.sliderMinSpawnDelay.setSliderValue(this.sliderMaxSpawnDelay.getSliderValue());
                    this.minSpawnDelay = this.maxSpawnDelay;
                    this.needsUpdate = true;
                }
            }
            if(this.sliderNearbyMobLimit.dragging) {
                this.nearbyMobLimit = this.sliderNearbyMobLimit.getSliderValue();
                this.needsUpdate = true;
            }
            if(this.sliderNearbyMobRange.dragging) {
                this.nearbyMobCheckRange = this.sliderNearbyMobRange.getSliderValue();
                this.needsUpdate = true;
            }
            if(this.sliderPlayerRange.dragging) {
                this.requiredPlayerRange = this.sliderPlayerRange.getSliderValue();
                this.needsUpdate = true;
            }
            if(this.sliderMaxSpawnCount.dragging) {
                this.maxSpawnCount = this.sliderMaxSpawnCount.getSliderValue();
                this.needsUpdate = true;
            }
            if(this.sliderXZRange.dragging) {
                this.maxSpawnRange = this.sliderXZRange.getSliderValue();
                this.needsUpdate = true;
            }
            if(this.sliderYRange.dragging) {
                this.maxSpawnRangeVertical = this.sliderYRange.getSliderValue();
                this.needsUpdate = true;
            }
        }
        else if(this.page == 1) {
            if(this.sliderMaxHealth.dragging) {
                this.maxHealth = this.sliderMaxHealth.getSliderValue();
                this.needsUpdate = true;
            }
            if(this.sliderFollowRange.dragging) {
                this.navigatorRange = this.sliderFollowRange.getSliderValue();
                this.needsUpdate = true;
            }
            if(this.sliderMoveSpeed.dragging) {
                this.moveSpeed = this.sliderMoveSpeed.getSliderValue_F();
                this.needsUpdate = true;
            }
            if(this.sliderAttackDamage.dragging) {
                this.attackDamage = this.sliderAttackDamage.getSliderValue_F();
                this.needsUpdate = true;
            }
        }
        if(this.needsUpdate) {
            this.sendGuiDataToClientTileEntity();
            this.sendGuiDataToServerTileEntity();
        }
    }

    @Override
    protected void mouseClicked(int i, int j, int k) {
        this.buttonList.addAll(this.pageControls);
        if(this.page == 0) {
            this.buttonList.addAll(this.spawnerControls);
        }
        else if(this.page == 1) {
            this.buttonList.addAll(this.mobControls);
        }
        super.mouseClicked(i, j, k);
        this.buttonList.clear();
    }
}
