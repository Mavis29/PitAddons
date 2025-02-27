package com.pitaddons.gui.components;

import com.pitaddons.module.Category;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

public class CategoryComponent extends Gui {

    float x;
    float yHeader;
    float y;
    float width;
    float heightHeader;
    float heightBody;

    float dragOffsetX;
    float dragOffsetY;

    int origSrWidth;
    int origSrHeight;

    boolean headerHovered = false;
    boolean dragging = false;

    public CategoryComponent(ScaledResolution sr) {

        x = sr.getScaledWidth() * 0.3f;
        yHeader = sr.getScaledHeight() * 0.4f;
        y = yHeader + 10;
        heightHeader = 12;
        heightBody = 20;

        origSrWidth = sr.getScaledWidth();
        origSrHeight = sr.getScaledHeight();
    }

    public void drawComponent(Category category, ScaledResolution sr, FontRenderer fr, int mouseX, int mouseY) {
        width = sr.getScaledWidth() * 0.2f;

        float nameSize = fr.getStringWidth(category.name());
        float nameX = x + width/2 - nameSize/2;

        drawRect((int) x, (int) yHeader, (int) (x + width), (int) (yHeader + heightHeader), 0xFF1E1E28);
        fr.drawStringWithShadow(category.name(), nameX, yHeader+2 , 0xFFDDCECD);
    }

    public boolean isHeaderHovered(int mouseX, int mouseY) {
        return x <= mouseX && mouseX <= x + width && yHeader <= mouseY && mouseY <= yHeader + heightHeader;
    }

    public void onDragging(int mouseX, int mouseY) {
        this.x = mouseX - dragOffsetX;
        this.yHeader = mouseY - dragOffsetY;
    }

    public void onClick(int mouseX, int mouseY) {
        dragOffsetX = mouseX - x;
        dragOffsetY = mouseY - yHeader;
    }

    public void onResize(ScaledResolution sr) {
        // Calculate the current percentage positions relative to the original screen dimensions

        float xRatio = x / (float) origSrWidth;
        float yHeaderRatio = yHeader / (float) origSrHeight;

        // Update x and yHeader based on new screen dimensions
        this.x = xRatio * sr.getScaledWidth();
        this.yHeader = yHeaderRatio * sr.getScaledHeight();

        origSrWidth = sr.getScaledWidth();
        origSrHeight = sr.getScaledHeight();
    }
}
