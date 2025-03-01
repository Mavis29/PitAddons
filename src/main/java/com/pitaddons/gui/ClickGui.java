package com.pitaddons.gui;

import com.pitaddons.gui.components.CategoryComponent;
import com.pitaddons.module.Category;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ClickGui extends GuiScreen {

    // Color palette: https://coolors.co/1e1e28-13b458-2d93ad-3a3c4b-ddcecd

    ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
    FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
    SoundHandler sh = Minecraft.getMinecraft().getSoundHandler();

    private float topX = 0;
    private float topY = 0;
    private int origX = (int) (sr.getScaledWidth() * 0.15f);
    private float bottomX = sr.getScaledWidth() * 0.15f;
    private float bottomY = sr.getScaledHeight();

    private int animationX = 0;
    private final int animationSpeed = 3;

    private ArrayList<Category> enabledCategories;
    private HashMap<Category, CategoryComponent> categoryComponents;
    private boolean clicked = false;
    private boolean dragging = false;

    CategoryComponent currentDragging;



    public ClickGui() {
        enabledCategories = GuiManager.getEnabledCategories();
        categoryComponents = GuiManager.getCategoryComponents();
        if(categoryComponents.isEmpty()) {
            for(Category category : Category.values()) {
                CategoryComponent component = new CategoryComponent(sr, 0.3f, 0.4f);
                categoryComponents.put(category, component);
            }
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        // used for animation
        if(animationX < origX) {
            if(animationX > origX - animationSpeed) animationX = origX;
            else animationX += animationSpeed;
        }
        topX = animationX - origX;
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
        fr.drawStringWithShadow("PIT ADDONS", (int) (guiWidth / 2 - textWidth / 2) + topX, (int) (height * 0.12f), 0xFF13B458);


        // Categories
        // And check if the current Category is hovered or selected
        float categoryY = 0.42f;
        float categoryHeight = 0.06f;

        for (Category category : Category.values()) {

            String categoryName = category.name();
            textWidth = fr.getStringWidth(categoryName);

            // draws the list of modules for the current Category
            if(enabledCategories.contains(category)) {
                CategoryComponent currentComponent = categoryComponents.get(category);
                currentComponent.drawComponent(category, sr, fr, mouseX, mouseY);

                // if there's nothing selected atm and there was a click on a module set it to current dragging
                if(currentComponent.isHeaderHovered(mouseX, mouseY) && this.dragging && currentDragging == null) {
                    currentDragging = currentComponent;
                }

                // if the module is currently being dragged
                if(currentDragging == currentComponent) {
                    if(clicked) currentComponent.onClick(mouseX, mouseY);
                    currentComponent.onDragging(mouseX, mouseY);
                }
            }

            // Hover check for buttons
            if(0 <= mouseX && mouseX <= guiWidth && height * categoryY <= mouseY && mouseY <= height * (categoryY + categoryHeight)) {

                // check if there was a click last update
                if(clicked) {
                    // add the Category to the enabled list if it was clicked
                    sh.playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
                    if(!enabledCategories.contains(category)) enabledCategories.add(category);
                    else enabledCategories.remove(category);
                }

                // Only draw hover effect if Category isn't enabled
                if(!enabledCategories.contains(category)) {
                    // Dark gray highlight
                    drawRect(0, (int) (height * categoryY), guiWidth, (int) (height * (categoryY + categoryHeight)), 0xFF21242F);
                } else {
                    // Green line on the side marking its enabled
                    drawRect(0, (int) (height * categoryY),2, (int) (height * (categoryY + categoryHeight)), 0xFF13B458);
                    // Dark gray highlight
                    drawRect(2, (int) (height * categoryY), guiWidth, (int) (height * (categoryY + categoryHeight)), 0xFF21242F);
                }
                // Blue text
                fr.drawStringWithShadow(categoryName, (int) (guiWidth / 2 - textWidth / 2) + topX, (int) (height * (categoryY + categoryHeight / 2)) - 3, 0xFF2D93AD);
                categoryY += categoryHeight;
                continue;
            }

            // Check that if the category is currently enabled
            if(enabledCategories.contains(category)) {
                // Green line on the side marking its enabled
                drawRect((int) topX, (int) (height * categoryY), (int) (topX + 2), (int) (height * (categoryY + categoryHeight)), 0xFF13B458);
            }

            // Standard white text
            fr.drawStringWithShadow(categoryName, (int) (guiWidth / 2 - textWidth / 2) + topX, (int) (height * (categoryY + categoryHeight / 2)) - 3, 0xDDCECD);
            categoryY += categoryHeight;
        }
        clicked = false;
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        clicked = true;
        dragging = true;
        updateScreen();
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        dragging = false;
        currentDragging = null;
        for(CategoryComponent component : categoryComponents.values()) {
            component.onMouseRelease();
        }
        super.mouseReleased(mouseX, mouseY, mouseButton);
    }

    @Override
    public void onGuiClosed() {
        GuiManager.setEnabledCategories(enabledCategories);
        GuiManager.setCategoryComponents(categoryComponents);
        super.onGuiClosed();
    }

    @Override
    public void onResize(Minecraft mc, int width, int height) {
        sr = new ScaledResolution(mc); // Update ScaledResolution
        for (Category category : enabledCategories) {
            CategoryComponent currentComponent = categoryComponents.get(category);
        }
        mc.displayGuiScreen(new ClickGui());
        super.onResize(mc, width, height);
    }
}