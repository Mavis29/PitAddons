package com.pitaddons.clickgui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;

public abstract class Component extends Gui {

    // Rendering System
    protected Minecraft mc = Minecraft.getMinecraft();
    protected ScaledResolution sr = new ScaledResolution(mc);
    protected FontRenderer fr = mc.fontRendererObj;
    protected SoundHandler sh = mc.getSoundHandler();

    // Colors
    protected final int colorText = 0xFFDDCECD;
    protected final int colorDetails = 0xFF3A3C4B;
    protected final int colorBackground = 0x1E1E28;

    public Component() {

    }

    public void drawComponent(int mouseX, int mouseY, float partialTicks) {

    }

    public void drawComponent(int x, int y, int mouseX, int mouseY, float partialTicks) {

    }

    public void onClick(int mouseX, int mouseY, int mouseButton) {

    }

    public void onRelease(int mouseX, int mouseY, int mouseButton) {

    }

    protected void handleKeyboardInput() {

    }

    public void onHover(int mouseX, int mouseY) {

    }

    public boolean isHovered(int mouseX, int mouseY, int x1, int y1, int x2, int y2) {
        return x1 <= mouseX && mouseX < x2 && y1 <= mouseY && mouseY < y2;
    }

    public void onResize(Minecraft mc, int width, int height) {
        sr = new ScaledResolution(mc);
    }

    public void playSoundButtonPress() {
        sh.playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
    }
}
