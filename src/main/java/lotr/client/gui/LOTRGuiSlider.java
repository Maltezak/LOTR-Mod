package lotr.client.gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.MathHelper;

public class LOTRGuiSlider extends GuiButton {
    private String baseDisplayString;
    private String overrideStateString;
    private boolean isTime = false;
    private boolean isFloat = false;
    private boolean valueOnly = false;
    private int numberDigits = 0;
    private int minValue;
    private int maxValue;
    private float minValueF;
    private float maxValueF;
    private float sliderValue = 1.0f;
    public boolean dragging = false;

    public LOTRGuiSlider(int id, int x, int y, int width, int height, String s) {
        super(id, x, y, width, height, s);
        this.baseDisplayString = s;
    }

    public void setFloat() {
        this.isFloat = true;
    }

    public void setMinutesSecondsTime() {
        this.isTime = true;
    }

    public void setValueOnly() {
        this.valueOnly = true;
    }

    public void setNumberDigits(int i) {
        this.numberDigits = i;
    }

    @Override
    public int getHoverState(boolean flag) {
        return 0;
    }

    public void setMinMaxValues(int min, int max) {
        this.minValue = min;
        this.maxValue = max;
    }

    public int getSliderValue() {
        return this.minValue + Math.round(this.sliderValue * (this.maxValue - this.minValue));
    }

    public void setSliderValue(int value) {
        value = MathHelper.clamp_int(value, this.minValue, this.maxValue);
        this.sliderValue = (float) (value - this.minValue) / (float) (this.maxValue - this.minValue);
    }

    public void setMinMaxValues_F(float min, float max) {
        this.minValueF = min;
        this.maxValueF = max;
    }

    public float getSliderValue_F() {
        return this.minValueF + this.sliderValue * (this.maxValueF - this.minValueF);
    }

    public void setSliderValue_F(float value) {
        value = MathHelper.clamp_float(value, this.minValueF, this.maxValueF);
        this.sliderValue = (value - this.minValueF) / (this.maxValueF - this.minValueF);
    }

    public void setOverrideStateString(String s) {
        this.overrideStateString = s;
    }

    @Override
    public void drawButton(Minecraft mc, int i, int j) {
        if(this.overrideStateString != null) {
            this.displayString = this.overrideStateString;
        }
        else if(this.isTime) {
            int value = this.getSliderValue();
            int seconds = value % 60;
            int minutes = value / 60;
            this.displayString = String.format("%d:%02d", minutes, seconds);
        }
        else if(this.isFloat) {
            this.displayString = String.format("%.2f", Float.valueOf(this.getSliderValue_F()));
        }
        else {
            int value = this.getSliderValue();
            this.displayString = String.valueOf(value);
            if(this.numberDigits > 0) {
                this.displayString = String.format("%0" + this.numberDigits + "d", value);
            }
        }
        if(!this.valueOnly) {
            this.displayString = this.baseDisplayString + ": " + this.displayString;
        }
        super.drawButton(mc, i, j);
    }

    @Override
    protected void mouseDragged(Minecraft mc, int i, int j) {
        if(this.visible && this.enabled) {
            if(this.dragging) {
                this.sliderValue = (float) (i - (this.xPosition + 4)) / (float) (this.width - 8);
                if(this.sliderValue < 0.0f) {
                    this.sliderValue = 0.0f;
                }
                if(this.sliderValue > 1.0f) {
                    this.sliderValue = 1.0f;
                }
            }
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            this.drawTexturedModalRect(this.xPosition + (int) (this.sliderValue * (this.width - 8)), this.yPosition, 0, 66, 4, 20);
            this.drawTexturedModalRect(this.xPosition + (int) (this.sliderValue * (this.width - 8)) + 4, this.yPosition, 196, 66, 4, 20);
        }
    }

    @Override
    public boolean mousePressed(Minecraft mc, int i, int j) {
        if(super.mousePressed(mc, i, j)) {
            this.sliderValue = (float) (i - (this.xPosition + 4)) / (float) (this.width - 8);
            if(this.sliderValue < 0.0f) {
                this.sliderValue = 0.0f;
            }
            if(this.sliderValue > 1.0f) {
                this.sliderValue = 1.0f;
            }
            this.dragging = true;
            return true;
        }
        return false;
    }

    @Override
    public void mouseReleased(int i, int j) {
        this.dragging = false;
    }
}
