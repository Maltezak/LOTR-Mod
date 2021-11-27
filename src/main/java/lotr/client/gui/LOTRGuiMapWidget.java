package lotr.client.gui;

import net.minecraft.util.StatCollector;

public class LOTRGuiMapWidget {
    private int xPos;
    private int yPos;
    public final int width;
    private String name;
    private final int texUBase;
    private final int texVBase;
    private int texVIndex;
    public boolean visible = true;

    public LOTRGuiMapWidget(int x, int y, int w, String s, int u, int v) {
        this.xPos = x;
        this.yPos = y;
        this.width = w;
        this.name = s;
        this.texUBase = u;
        this.texVBase = v;
    }

    public String getTranslatedName() {
        return StatCollector.translateToLocal("lotr.gui.map.widget." + this.name);
    }

    public void setTexVIndex(int i) {
        this.texVIndex = i;
    }

    public int getTexU() {
        return this.texUBase;
    }

    public int getTexV() {
        return this.texVBase + this.texVIndex * this.width;
    }

    public int getMapXPos(int mapWidth) {
        return this.xPos < 0 ? mapWidth + this.xPos : this.xPos;
    }

    public int getMapYPos(int mapHeight) {
        return this.yPos < 0 ? mapHeight + this.yPos : this.yPos;
    }

    public boolean isMouseOver(int mouseX, int mouseY, int mapWidth, int mapHeight) {
        return this.visible && mouseX >= this.getMapXPos(mapWidth) && mouseX < this.getMapXPos(mapWidth) + this.width && mouseY >= this.getMapYPos(mapHeight) && mouseY < this.getMapYPos(mapHeight) + this.width;
    }
}
