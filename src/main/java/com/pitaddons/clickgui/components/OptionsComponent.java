package com.pitaddons.clickgui.components;

import com.pitaddons.clickgui.Component;
import com.pitaddons.module.settings.Setting;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;

public class OptionsComponent extends Component {

    private final ResourceLocation BUTTON = new ResourceLocation("pitaddons", "textures/clickgui/settings.png");

    Setting setting;
    String current;
    ArrayList<String> options;
    private final int textureY = 68;
    private final int height = 14;
    private final int width = 50;
    private int x, y;

    public OptionsComponent(Setting setting) {
        this.setting = setting;
        options = new ArrayList<String>();
        options = setting.getOptions();
        current = setting.getCurrentOption();
    }

    @Override
    public void drawComponent(int x, int y, int mouseX, int mouseY, float partialTicks) {
        this.x = x;
        this.y = y;
        mc.getTextureManager().bindTexture(BUTTON);
        drawModalRectWithCustomSizedTexture(x, y, 0, textureY, width, height, 256, 256);
        int textWidth = fr.getStringWidth(current);
        int centeredText = x + width / 2 - textWidth / 2;
        fr.drawStringWithShadow(current, centeredText, y + 3, colorText);
        super.drawComponent(x, y, mouseX, mouseY, partialTicks);
    }

    @Override
    public void onClick(int mouseX, int mouseY, int mouseButton) {
        if (!isHovered(mouseX, mouseY, x, y, x + width, y + height)) return;
        int currentPos = options.indexOf(current);
        if (currentPos + 1 >= options.size()) currentPos = 0;
        else currentPos++;
        current = options.get(currentPos);
        setting.setCurrentOption(current);
        playSoundButtonPress();
        super.onClick(mouseX, mouseY, mouseButton);
    }
}
