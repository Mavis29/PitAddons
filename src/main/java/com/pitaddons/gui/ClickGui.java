package com.pitaddons.gui;

import com.pitaddons.module.Category;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.io.IOException;
import java.util.HashMap;

public class ClickGui extends GuiScreen {

    // Color palette: https://coolors.co/1e1e28-13b458-2d93ad-3a3c4b-ddcecd

    ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
    FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;

    private float topX = 0;
    private float topY = 0;
    private int origX = (int) (sr.getScaledWidth() * 0.15f);
    private float bottomX = sr.getScaledWidth() * 0.15f;
    private float bottomY = sr.getScaledHeight();
    int animationX = 0;

    private HashMap<Category, Boolean> clickedCategories = new HashMap<Category, Boolean>();
    private boolean clicked = false;



    public ClickGui() {

    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {

        // used for animation
        if(animationX < origX) {
            animationX += 3;
        }

        topX = animationX - bottomX;
        bottomX = animationX;
        // until here

        int width = sr.getScaledWidth();
        int height = sr.getScaledHeight();

        // Color format: 0xAARRGGBB -> 0x88 (alpha) 00 (red) FF (green) 00 (blue)

        // gray background
        drawDefaultBackground();

        // sidebar
        drawRect((int) topX, (int) topY, (int) bottomX, (int) bottomY, 0xFF1E1E28);

        // green highlight sidebar
        drawRect((int) bottomX, (int) topY, (int) bottomX + 2, (int) bottomY, 0xFF13B458);

        // PIT ADDONS TEXT
        int textWidth = fr.getStringWidth("PIT ADDONS");
        int guiWidth = (int) (bottomX);
        fr.drawStringWithShadow("PIT ADDONS", (int) (guiWidth / 2 - textWidth / 2), (int) (height * 0.12f), 0xFF13B458);


        // Categories
        // And check if the current Category is hovered or selected
        float categoryY = 0.42f;
        float categoryHeight = 0.06f;

        for (Category category : Category.values()) {

            String categoryName = category.name();
            textWidth = fr.getStringWidth(categoryName);

            // Hover check
            if(0 <= mouseX && mouseX <= guiWidth && height * categoryY <= mouseY && mouseY <= height * (categoryY + categoryHeight)) {

                // check if there was a click last update
                if(clicked) {
                    // if its hasn't been clicked before meaning it was false before (by default) and is now true
                    if(!clickedCategories.containsKey(category)) clickedCategories.put(category, true);
                    else {
                        if(clickedCategories.get(category)) clickedCategories.put(category, false);
                        else clickedCategories.put(category, true);
                    }
                }

                // Only draw hover effect if Category isn't enabled
                if(!clickedCategories.containsKey(category) || !clickedCategories.get(category)) {
                    drawRect(0, (int) (height * categoryY), guiWidth, (int) (height * (categoryY + categoryHeight)), 0xFF21242F);
                    fr.drawStringWithShadow(categoryName, (int) (guiWidth / 2 - textWidth / 2), (int) (height * (categoryY + categoryHeight / 2)) - 3, 0xFF2D93AD);
                    categoryY += categoryHeight;
                    continue;
                } else {
                    drawRect(0, (int) (height * categoryY),2, (int) (height * (categoryY + categoryHeight)), 0xFF13B458);
                    drawRect(2, (int) (height * categoryY), guiWidth, (int) (height * (categoryY + categoryHeight)), 0xFF21242F);
                    fr.drawStringWithShadow(categoryName, (int) (guiWidth / 2 - textWidth / 2), (int) (height * (categoryY + categoryHeight / 2)) - 3, 0xFF2D93AD);
                    categoryY += categoryHeight;
                    continue;
                }
            }

            // Check that if the category has been clicked before and it's currently enabled - FF2F303D
            if(clickedCategories.containsKey(category) && clickedCategories.get(category)) {
                drawRect(0, (int) (height * categoryY),2, (int) (height * (categoryY + categoryHeight)), 0xFF13B458);
                fr.drawStringWithShadow(categoryName, (int) (guiWidth / 2 - textWidth / 2), (int) (height * (categoryY + categoryHeight / 2)) - 3, 0xFFDDCECD);
                categoryY += categoryHeight;
                continue;
            }

            fr.drawStringWithShadow(categoryName, (int) (guiWidth / 2 - textWidth / 2), (int) (height * (categoryY + categoryHeight / 2)) - 3, 0xDDCECD);
            categoryY += categoryHeight;
        }
        clicked = false;
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        clicked = true;
        updateScreen();
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }
}