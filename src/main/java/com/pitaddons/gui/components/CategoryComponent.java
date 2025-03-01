package com.pitaddons.gui.components;

import com.pitaddons.PitAddons;
import com.pitaddons.module.Category;
import com.pitaddons.module.Module;
import com.pitaddons.module.ModuleManager;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

public class CategoryComponent extends Gui {

    private float x;
    private float yHeader;
    private float y;
    private float width;
    private float heightHeader;
    private float heightBody;

    private float dragOffsetX;
    private float dragOffsetY;

    private float relativeX;
    private float relativeYHeader;

    private boolean dragging = false;

    ModuleManager moduleManager = PitAddons.moduleManager;

    public CategoryComponent(ScaledResolution sr, float relativeX, float relativeYHeader) {

        x = sr.getScaledWidth() * 0.3f;
        yHeader = sr.getScaledHeight() * 0.4f;

        y = yHeader + 11;
        heightHeader = 12;
        heightBody = 4;
    }

    public void drawComponent(Category category, ScaledResolution sr, FontRenderer fr, int mouseX, int mouseY) {

        heightBody = 8;

        for(Module module : moduleManager.getModules()) {
            if(module.getCategory() != category) continue;
            heightBody += 12;
        }

        y = yHeader + 14;

        if(!dragging) {
            x = relativeX * sr.getScaledWidth();
            yHeader = relativeYHeader * sr.getScaledHeight();
        }

        width = sr.getScaledWidth() * 0.25f;

        float nameSize = fr.getStringWidth(category.name());
        float nameX = x + width/2 - nameSize/2;

        drawRect((int) x, (int) yHeader, (int) (x + width), (int) (yHeader + heightHeader), 0xFF3A3C4B);
        fr.drawStringWithShadow(category.name(), nameX, yHeader+2 , 0xFFDDCECD);

        drawRect((int) x, (int) y, (int) (x + width), (int) (y + heightBody), 0xFF1E1E28);

        float moduleY = y + 4;
        float moduleOffset = 14;
        for(Module module : moduleManager.getModules()) {
            if(module.getCategory() != category) continue;
            float textWidth = fr.getStringWidth(module.getName());
            fr.drawStringWithShadow(module.getName(), x + width/2 - textWidth/2, moduleY, 0xFFDDCECD);
            moduleY += moduleOffset;
            heightBody += 20;
        }

        relativeX = x/sr.getScaledWidth();
        relativeYHeader = yHeader/sr.getScaledHeight();
    }

    public boolean isHeaderHovered(int mouseX, int mouseY) {
        return x <= mouseX && mouseX <= x + width && yHeader <= mouseY && mouseY <= yHeader + heightHeader;
    }

    public void onDragging(int mouseX, int mouseY) {
        dragging = true;
        this.x = mouseX - dragOffsetX;
        this.yHeader = mouseY - dragOffsetY;
    }

    public void onMouseRelease() {
        dragging = false;
    }

    public void onClick(int mouseX, int mouseY) {
        dragOffsetX = mouseX - x;
        dragOffsetY = mouseY - yHeader;
    }
}
