package com.pitaddons.clickgui.components;

import com.pitaddons.clickgui.Component;
import com.pitaddons.module.Module;
import net.minecraft.util.ResourceLocation;

public class CheckComponent extends Component {

    private final ResourceLocation CHECK = new ResourceLocation("pitaddons", "textures/clickgui/settings.png");
    private final Module module;

    private final int textureY = 20;
    private final int width = 10;
    private final int height = 10;

    private int x;
    private int y;

    public CheckComponent(Module module) {
        this.module = module;
    }

    @Override
    public void drawComponent(int x, int y, int mouseX, int mouseY, float partialTicks) {
        this.x = x;
        this.y = y;
        mc.getTextureManager().bindTexture(CHECK);
        if (!module.isEnabled())
            drawModalRectWithCustomSizedTexture(x, y, 0, textureY, width, height, 256, 256);
        else
            drawModalRectWithCustomSizedTexture(x, y, 0, textureY + height, width, height, 256, 256);
        super.drawComponent(x, y, mouseX, mouseY, partialTicks);
    }

    @Override
    public void onClick(int mouseX, int mouseY, int mouseButton) {
        if (!isHovered(mouseX, mouseY, x, y, x + width, y + height)) return;
        module.toggle();
        playSoundButtonPress();
        super.onClick(mouseX, mouseY, mouseButton);
    }
}
