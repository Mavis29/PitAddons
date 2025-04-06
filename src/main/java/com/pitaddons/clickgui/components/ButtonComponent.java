package com.pitaddons.clickgui.components;

import com.pitaddons.clickgui.Component;
import com.pitaddons.module.Category;
import net.minecraft.util.ResourceLocation;

public class ButtonComponent extends Component {

    private final ResourceLocation CATEGORY_BUTTON = new ResourceLocation("pitaddons", "textures/clickgui/buttons.png");

    private Category category;

    private int x;
    private int y;

    private int width;
    private int height;
    private int widthHovered;

    private int height1 = 0;
    private int height2 = 24;
    private int height3 = 48;
    private int height4 = 72;

    private boolean toggled;

    private final int colorText = 0xFFDDCECD;

    public ButtonComponent(Category category, int x, int y) {
        this.category = category;
        this.x = x;
        this.y = y;
        this.width = 72;
        this.height = 24;
        this.widthHovered = 78;
    }

    @Override
    public void drawComponent(int mouseX, int mouseY, float partialTicks) {
        super.drawComponent(mouseX, mouseY, partialTicks);
        drawTexture(mouseX, mouseY);
        fr.drawStringWithShadow(category.name(), x + 10,
                y + 9, colorText);
    }

    private void drawTexture(int mouseX, int mouseY) {
        mc.getTextureManager().bindTexture(CATEGORY_BUTTON);
        if (!isButtonHovered(mouseX, mouseY)) {
            if (!toggled) drawModalRectWithCustomSizedTexture(x, y, 0, height1, width, height, 256, 256);
            else drawModalRectWithCustomSizedTexture(x, y, 0, height2, width, height, 256, 256);
        } else {
            if (!toggled) drawModalRectWithCustomSizedTexture(x, y, 0, height3, widthHovered, height, 256, 256);
            else drawModalRectWithCustomSizedTexture(x, y, 0, height4, widthHovered, height, 256, 256);
        }
    }

    @Override
    public void onClick(int mouseX, int mouseY, int mouseButton) {
        super.onClick(mouseX, mouseY, mouseButton);
        if (!isHovered(mouseX, mouseY, x, y, x + width, y + height)) return;
        toggled = !toggled;
        playSoundButtonPress();
    }

    public Category getCategory() {
        return category;
    }

    public boolean isButtonHovered(int mouseX, int mouseY) {
        return isHovered(mouseX, mouseY, x, y, x + width, y + height);
    }

    public void setToggled(boolean toggled) {
        this.toggled = toggled;
    }

    public boolean isToggled() {
        return this.toggled;
    }
}
