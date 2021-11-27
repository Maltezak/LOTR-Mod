package lotr.client;

import java.util.*;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.MathHelper;

public class LOTRTextBody {
    private List<TextColor> list = new ArrayList<>();
    private int defaultColor;
    private int textWidth;
    private static final String LINEBREAK = "<BR>";

    public LOTRTextBody(int c) {
        this.defaultColor = c;
        this.textWidth = 100;
    }

    public void add(String s) {
        this.add(s, this.defaultColor);
    }

    public void add(String s, int c) {
        this.list.add(new TextColor(s, c));
    }

    public void addLinebreak() {
        this.add(LINEBREAK);
    }

    public void set(int i, String s) {
        this.list.get(i).text = s;
    }

    public void set(int i, String s, int c) {
        this.list.get(i).text = s;
        this.list.get(i).color = c;
    }

    public String getText(int i) {
        return this.list.get(i).text;
    }

    public int getColor(int i) {
        return this.list.get(i).color;
    }

    public int size() {
        return this.list.size();
    }

    public void setTextWidth(int w) {
        this.textWidth = w;
    }

    public void render(FontRenderer fr, int x, int y) {
        this.renderAndReturnScroll(fr, x, y, Integer.MAX_VALUE, Float.MAX_VALUE);
    }

    public float renderAndReturnScroll(FontRenderer fr, int x, int yTop, int yBottom, float scroll) {
        int ySize = yBottom - yTop;
        int numLines = this.getTotalLines(fr);
        int lineHeight = fr.FONT_HEIGHT;
        scroll = Math.max(scroll, 0.0f);
        scroll = Math.min(scroll, numLines - MathHelper.floor_double((float) ySize / (float) lineHeight));
        int d1 = Math.round(scroll);
        int y = yTop;
        y += ySize / lineHeight * lineHeight;
        y -= lineHeight;
        int maxLines = ySize / lineHeight;
        if(numLines < maxLines) {
            y -= (maxLines - numLines) * lineHeight;
        }
        block0: for(int i = this.size() - 1; i >= 0; --i) {
            String part = this.getText(i);
            int color = this.getColor(i);
            List lineList = fr.listFormattedStringToWidth(part, this.textWidth);
            for(int l = lineList.size() - 1; l >= 0; --l) {
                String line = (String) lineList.get(l);
                if(d1 > 0) {
                    --d1;
                    continue;
                }
                if(y < yTop) break block0;
                if(line.equals(LINEBREAK)) {
                    line = "";
                    char br = '-';
                    while(fr.getStringWidth(line + br) < this.textWidth) {
                        line = line + br;
                    }
                }
                fr.drawString(line, x, y, color);
                y -= lineHeight;
            }
        }
        return scroll;
    }

    public int getTotalLines(FontRenderer fr) {
        int lines = 0;
        for(int i = 0; i < this.size(); ++i) {
            String part = this.getText(i);
            List<String> lineList = fr.listFormattedStringToWidth(part, this.textWidth);
            for(String line : lineList) {
                ++lines;
            }
        }
        return lines;
    }

    private static class TextColor {
        public String text;
        public int color;

        public TextColor(String s, int c) {
            this.text = s;
            this.color = c;
        }
    }

}
