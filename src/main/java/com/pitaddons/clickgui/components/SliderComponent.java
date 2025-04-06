package com.pitaddons.clickgui.components;

import com.pitaddons.clickgui.Component;
import com.pitaddons.module.settings.Setting;
import com.pitaddons.util.MathUtil;
import net.minecraft.util.ResourceLocation;

public class SliderComponent extends Component {

    Setting setting;

    private final ResourceLocation BODY = new ResourceLocation("pitaddons", "textures/clickgui/settings.png");
    private final ResourceLocation MOVEABLE = new ResourceLocation("pitaddons", "textures/clickgui/settings.png");

    private final int bodyTextureY = 40;
    private final int bodyWidth = 35;
    private final int bodyHeight = 6;

    private final int moveableTextureY = 46;
    private final int moveableWidth = 5;
    private final int moveableHeight = 10;

    private final float min;
    private final float max;
    private final float increment;
    private float current;

    private boolean clicked = false;

    SliderInputComponent sliderInputComponent;

    int x, y;

    public SliderComponent(Setting setting) {
        this.setting = setting;
        this.min = setting.getMin();
        this.max = setting.getMax();
        this.current = setting.getCurrent();
        this.increment = setting.getIncrement();
        sliderInputComponent = new SliderInputComponent(setting);
    }

    @Override
    public void drawComponent(int x, int y, int mouseX, int mouseY, float partialTicks) {
        this.x = x;
        this.y = y;
        this.current = setting.getCurrent();

        if (clicked) {
            current = (float) (mouseX - x) / bodyWidth * max;
            current = MathUtil.approximate(current, increment);
            if (current > max) current = max;
            if (current < min) current = min;
        }

        // Drawing Body
        mc.getTextureManager().bindTexture(BODY);
        drawModalRectWithCustomSizedTexture(x, y, 0, bodyTextureY, bodyWidth, bodyHeight, 256, 256);

        // Drawing Slider
        int drawingX = x + (int) ((current / max) * (bodyWidth - moveableWidth));

        mc.getTextureManager().bindTexture(MOVEABLE);
        drawModalRectWithCustomSizedTexture(drawingX, y - 2, 0, moveableTextureY, moveableWidth, moveableHeight, 256, 256);

        // Drawing numbers
        int textWidthMin = fr.getStringWidth(String.valueOf(min));
        fr.drawStringWithShadow(String.valueOf(min), x - textWidthMin - 2, y, colorText);
        fr.drawStringWithShadow(String.valueOf(max), x + bodyWidth + 2, y, colorText);

        sliderInputComponent.drawComponent(x - textWidthMin - 4, y, String.valueOf(current),
                mouseX, mouseY, partialTicks);

        super.drawComponent(x, y, mouseX, mouseY, partialTicks);
    }

    @Override
    public void onClick(int mouseX, int mouseY, int mouseButton) {
        super.onClick(mouseX, mouseY, mouseButton);
        sliderInputComponent.onClick(mouseX, mouseY, mouseButton);
        if (!isHovered(mouseX, mouseY, x, y, x + bodyWidth, y + bodyHeight)) return;
        clicked = true;
        current = (float) (mouseX - x) / bodyWidth * max;
        current = MathUtil.approximate(current, increment);
        setting.setCurrent(current);
    }

    @Override
    public void onRelease(int mouseX, int mouseY, int mouseButton) {
        super.onRelease(mouseX, mouseY, mouseButton);
        if (clicked) clicked = false;
        setting.setCurrent(current);
    }

    @Override
    protected void handleKeyboardInput() {
        super.handleKeyboardInput();
        sliderInputComponent.handleKeyboardInput();
    }
}
