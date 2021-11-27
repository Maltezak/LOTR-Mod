package lotr.client.gui;

import lotr.client.LOTRKeyHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.util.StatCollector;

public abstract class LOTRGuiMenuBase extends LOTRGuiScreenBase {
    public static RenderItem renderItem = new RenderItem();
    public int xSize = 200;
    public int ySize = 256;
    public int guiLeft;
    public int guiTop;
    protected GuiButton buttonMenuReturn;

    @Override
    public void initGui() {
        super.initGui();
        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;
        int buttonH = 20;
        int buttonGap = 35;
        int minGap = 10;
        this.buttonMenuReturn = new LOTRGuiButtonLeftRight(1000, true, 0, this.guiTop + (this.ySize + buttonH) / 4, StatCollector.translateToLocal("lotr.gui.menuButton"));
        this.buttonList.add(this.buttonMenuReturn);
        this.buttonMenuReturn.xPosition = Math.min(0 + buttonGap, this.guiLeft - minGap - this.buttonMenuReturn.width);
    }

    @Override
    protected void keyTyped(char c, int i) {
        if(i == LOTRKeyHandler.keyBindingMenu.getKeyCode()) {
            this.mc.displayGuiScreen(new LOTRGuiMenu());
            return;
        }
        super.keyTyped(c, i);
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if(button.enabled && button == this.buttonMenuReturn) {
            this.mc.displayGuiScreen(new LOTRGuiMenu());
        }
        super.actionPerformed(button);
    }
}
