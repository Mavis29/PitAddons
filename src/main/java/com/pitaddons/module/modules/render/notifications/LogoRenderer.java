package com.pitaddons.module.modules.render.notifications;

import net.minecraft.client.gui.FontRenderer;

import java.util.HashMap;

public class LogoRenderer {

    private String unformattedText = "Pit Addons";
    private int currentChar, currentX;
    HashMap<Integer, Boolean> color;
    private boolean isNormal;
    private long lastUpdate, lastAnimation;
    private final int x, y, colorNormal = 0xFF16CC5F, colorShadow = 0xFF0E823C;


    public LogoRenderer() {
        lastAnimation = 0;
        lastUpdate = 0;
        currentChar = 0;
        color = new HashMap<Integer, Boolean>();
        x = 2;
        y = 2;
        currentX = x;
        for (int i = 0; i < unformattedText.length(); i++) color.put(i, true);
    }

    public void render(FontRenderer fr) {
        currentX = x;
        for (int character = 0; character < unformattedText.length(); character++) {
            int currentColor;
            if (color.get(character)) currentColor = colorNormal;
            else currentColor = colorShadow;
            fr.drawStringWithShadow(String.valueOf(unformattedText.charAt(character)), currentX, y, currentColor);
            currentX += fr.getCharWidth(unformattedText.charAt(character));
        }
        if (hasTimePassed(50) && shouldAnimate(5000)) {
            animate();
            lastUpdate = System.currentTimeMillis();
        }
    }

    private void animate() {
        if (isNormal) {
            if (currentChar < 10) {
                currentChar++;
                for (int i = 0; i <= currentChar; i++) {
                    color.put(i, false);
                }
            } else {
                lastAnimation = System.currentTimeMillis();
                currentChar = -1;
                isNormal = false;
            }
        } else {
            if (currentChar < 10) {
                currentChar++;
                for (int i = 0; i <= currentChar; i++) {
                    color.put(i, true);
                }
            } else {
                lastAnimation = System.currentTimeMillis();
                currentChar = -1;
                isNormal = true;
            }
        }
    }

    private boolean hasTimePassed(int time) {
        return lastUpdate + time <= System.currentTimeMillis();
    }

    private boolean shouldAnimate(int time) {
        return lastAnimation + time <= System.currentTimeMillis();
    }


}
