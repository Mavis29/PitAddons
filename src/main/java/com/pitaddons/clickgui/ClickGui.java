package com.pitaddons.clickgui;

import com.pitaddons.PitAddons;
import com.pitaddons.clickgui.components.ButtonComponent;
import com.pitaddons.clickgui.components.ModuleComponent;
import com.pitaddons.module.Category;
import com.pitaddons.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.io.IOException;
import java.util.HashMap;

public class ClickGui extends GuiScreen {

    // Color palette: https://coolors.co/1e1e28-13b458-2d93ad-3a3c4b-ddcecd
    private final ResourceLocation GUI_BACKGROUND = new ResourceLocation("pitaddons", "textures/clickgui/background.png");

    // Background values
    private final int width;
    private final int height;
    private int x;
    private int y;

    // Rendering System
    private Minecraft mc;
    private ScaledResolution sr;
    private FontRenderer fr;
    private int screenWidth;
    private int screenHeight;

    private Category currentCategory;

    private HashMap<Category, ButtonComponent> categoryButtons = new HashMap<Category, ButtonComponent>();
    private HashMap<Module, ModuleComponent> moduleComponents = new HashMap<Module, ModuleComponent>();

    // Scrolling
    private int scrollOffset;
    private int maxScroll;
    private int scrollSpeed;

    // Colors
    private final int colorText = 0xFFDDCECD;
    private final int colorDetails = 0xFF3A3C4B;
    private final int colorBackground = 0x1E1E28;

    public ClickGui() {
        mc = Minecraft.getMinecraft();
        sr = new ScaledResolution(mc);
        fr = mc.fontRendererObj;

        screenWidth = sr.getScaledWidth();
        screenHeight = sr.getScaledHeight();

        width = 316;
        height = 232;

        x = screenWidth / 2 - width / 2;
        y = screenHeight / 2 - height / 2;

        int buttonHeight = 24;
        int currentY = y + height / 2 - 35;

        for (Category category : Category.values()) {
            categoryButtons.put(category, new ButtonComponent(category, x, currentY));
            currentY += buttonHeight + 2;
        }
        currentCategory = null;

        for (Module module : PitAddons.moduleManager.getModules()) {
            moduleComponents.put(module, new ModuleComponent(module));
        }

        // Scrolling
        scrollOffset = 0;
        if (currentCategory != null) {
            maxScroll = getMaxScroll(currentCategory);
        }
        scrollSpeed = 10;
    }

    @Override
    public void initGui() {
        super.initGui();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawBackground();
        drawButtons(mouseX, mouseY, partialTicks);
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        scissor(x, y + 3, width, height - 6);
        listModules(currentCategory, x + 90, y + 20 - scrollOffset, mouseX, mouseY, partialTicks);
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        for (ButtonComponent button : categoryButtons.values()) {
            if (!button.isButtonHovered(mouseX, mouseY)) continue;
            button.onClick(mouseX, mouseY, mouseButton);
            if (button.isToggled()) currentCategory = button.getCategory();
            else currentCategory = null;
            for (ButtonComponent button2 : categoryButtons.values()) {
                if (button2.equals(button)) continue;
                button2.setToggled(false);
            }
            scrollOffset = 0;
            if (currentCategory != null) {
                maxScroll = getMaxScroll(currentCategory);
            }
            break; // Leaves the loop early if a button that has been interacted with was found
        }
        if (currentCategory == null) return;
        for (ModuleComponent component : moduleComponents.values()) {
            if (component.getModule().getCategory().equals(currentCategory))
                component.onClick(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        super.mouseReleased(mouseX, mouseY, mouseButton);
        if (currentCategory == null) return;
        for (ModuleComponent component : moduleComponents.values()) {
            if (component.getModule().getCategory().equals(currentCategory))
                component.onRelease(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    public void handleMouseInput() throws IOException {
        int scrollAmount = Mouse.getEventDWheel();
        if (scrollAmount != 0) {
            scrollOffset -= (scrollAmount > 0) ? +scrollSpeed : -scrollSpeed;
            scrollOffset = Math.max(0, Math.min(scrollOffset, maxScroll)); // Clamp scroll
        }
        super.handleMouseInput();
    }

    private void scissor(int x, int y, int width, int height) {
        double scale = sr.getScaleFactor();

        GL11.glScissor((int) (x * scale), (int) (mc.displayHeight - (y + height) * scale), (int) (width * scale), (int) (height * scale));
    }

    private void drawBackground() {
        drawDefaultBackground();

        // Draws the custom GUI background
        mc.getTextureManager().bindTexture(GUI_BACKGROUND);
        drawModalRectWithCustomSizedTexture(screenWidth / 2 - width / 2, screenHeight / 2 - height / 2, 0, 0, width, height, 512, 512);
    }

    private void drawButtons(int mouseX, int mouseY, float partialTicks) {
        for (ButtonComponent button : categoryButtons.values()) button.drawComponent(mouseX, mouseY, partialTicks);
    }

    private void listModules(Category category, int x, int y, int mouseX, int mouseY, float partialTicks) {
        int currentY = y;
        for (Module module : moduleComponents.keySet()) {
            if (!module.getCategory().equals(category)) continue;
            ModuleComponent component = moduleComponents.get(module);
            component.drawModuleComponent(x, currentY, width - 110, mouseX, mouseY, partialTicks);
            currentY += component.getHeight();
            currentY += 20;
        }
    }

    private int getMaxScroll(Category category) {
        int height = 0;
        for (Module module : moduleComponents.keySet()) {
            if (!module.getCategory().equals(category)) continue;
            ModuleComponent component = moduleComponents.get(module);
            height += component.getHeight();
            height += 20;
        }
        height -= this.height;
        if (height < 0) height = 0;
        if (height != 0) height += 20;
        return height;
    }

    @Override
    public void handleKeyboardInput() throws IOException {
        super.handleKeyboardInput();
        for (ModuleComponent component : moduleComponents.values()) {
            if (component.getModule().getCategory().equals(currentCategory))
                component.handleKeyboardInput();
        }
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        for (Module module : PitAddons.moduleManager.getModules()) {
            module.onSettingSave();
        }
    }
}
