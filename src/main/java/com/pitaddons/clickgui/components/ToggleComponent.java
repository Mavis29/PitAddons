package com.pitaddons.clickgui.components;

import com.pitaddons.clickgui.Component;
import com.pitaddons.module.settings.Setting;
import net.minecraft.util.ResourceLocation;

public class ToggleComponent extends Component {

    private final ResourceLocation BUTTON = new ResourceLocation("pitaddons", "textures/clickgui/settings.png");

    private final Setting setting;

    private final int width = 20;
    private final int height = 10;

    private boolean toggled;
    private int x;
    private int y;

    public ToggleComponent(Setting setting) {
        this.setting = setting;
        toggled = setting.isEnabled();
    }

    @Override
    public void drawComponent(int x, int y, int mouseX, int mouseY, float partialTicks) {
        mc.getTextureManager().bindTexture(BUTTON);

        this.x = x;
        this.y = y;

        if (toggled) drawModalRectWithCustomSizedTexture(x, y, 0, 0, width, height, 256, 256);
        else drawModalRectWithCustomSizedTexture(x, y, 0, height, width, height, 256, 256);

        super.drawComponent(x, y, mouseX, mouseY, partialTicks);
    }

    @Override
    public void onClick(int mouseX, int mouseY, int mouseButton) {
        if (!isHovered(mouseX, mouseY, x, y, x + width, y + height)) return;
        toggled = !toggled;
        setting.setEnabled(toggled);
        playSoundButtonPress();
    }
}
