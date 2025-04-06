package com.pitaddons.clickgui;

import com.pitaddons.PitAddons;
import com.pitaddons.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class HudPosGui extends GuiScreen {

    private final ArrayList<Module> moveableModules;

    private int moduleX, moduleY, offsetX, offsetY;
    private final int width, height;
    private Module selectedModule;

    private HashMap<Module, int[]> modulePositions;

    Minecraft mc = Minecraft.getMinecraft();
    FontRenderer fr = mc.fontRendererObj;
    ScaledResolution sr = new ScaledResolution(mc);


    public HudPosGui() {
        moveableModules = new ArrayList<Module>();
        modulePositions = new HashMap<Module, int[]>();
        for (Module module : PitAddons.moduleManager.getModules()) {
            if (module.isMoveable()) {
                moveableModules.add(module);
                modulePositions.put(module, module.getHudPos());
            }
        }

        width = 100;
        height = 14;

    }


    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        if (selectedModule != null) {
            moduleX = mouseX - offsetX;
            moduleY = mouseY - offsetY;
            modulePositions.put(selectedModule, new int[]{moduleX, moduleY});
            selectedModule.setHudPos(modulePositions.get(selectedModule)[0], modulePositions.get(selectedModule)[1]);
        }

        for (Module module : moveableModules) {
            int moduleX = modulePositions.get(module)[0];
            int moduleY = modulePositions.get(module)[1];
            drawRect(moduleX, moduleY, moduleX + width, moduleY + height, Color.BLACK.getRGB());
            int textWidth = fr.getStringWidth(module.getName());
            fr.drawStringWithShadow(module.getName(), (int) (moduleX + ((float) width / 2 - (float) textWidth / 2)),
                    moduleY + 3, -1);
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        for (Module module : moveableModules) {
            int moduleX = modulePositions.get(module)[0];
            int moduleY = modulePositions.get(module)[1];
            if (!isHovered(mouseX, mouseY, moduleX, moduleY, moduleX + width, moduleY + width)) continue;
            selectedModule = module;
            offsetX = mouseX - moduleX;
            offsetY = mouseY - moduleY;
        }
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        super.mouseReleased(mouseX, mouseY, mouseButton);
        if (selectedModule == null) return;
        selectedModule.setHudPos(modulePositions.get(selectedModule)[0], modulePositions.get(selectedModule)[1]);
        selectedModule = null;
    }

    private boolean isHovered(int mouseX, int mouseY, int x1, int y1, int x2, int y2) {
        return x1 <= mouseX && mouseX < x2 && y1 <= mouseY && mouseY < y2;
    }
}
