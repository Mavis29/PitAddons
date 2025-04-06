package com.pitaddons.clickgui.components;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.pitaddons.clickgui.Component;
import com.pitaddons.module.settings.Setting;
import com.pitaddons.util.MathUtil;
import net.minecraft.util.ChatComponentText;
import org.lwjgl.input.Keyboard;

public class SliderInputComponent extends Component {

    private int x, y, width;
    private String text;
    private boolean clicked;
    private int stage = 0;
    private Setting setting;

    public SliderInputComponent(Setting setting) {
        this.setting = setting;
        text = String.valueOf(setting.getCurrent());
        clicked = false;
    }

    public void drawComponent(int x, int y, String text, int mouseX, int mouseY, float partialTicks) {
        this.y = y;

        if (!clicked) this.text = text;

        width = fr.getStringWidth(this.text);
        x -= width;
        this.x = x;

        if (clicked) drawRect(x, y + 9, x + width, y + 10, colorText);
        fr.drawStringWithShadow(this.text, x, y, colorText);
    }

    public String getText() {
        return text;
    }

    @Override
    public void onClick(int mouseX, int mouseY, int mouseButton) {
        super.onClick(mouseX, mouseY, mouseButton);
        if (!isHovered(mouseX, mouseY, x, y, x + width, y + 8)) {
            deselect();
            return;
        }
        clicked = true;
    }

    @Override
    protected void handleKeyboardInput() {
        super.handleKeyboardInput();
        char key = Keyboard.getEventCharacter();
        if (key == Keyboard.CHAR_NONE) return;
        int keyCode = Keyboard.getEventKey();
        if (keyCode == 14) {
            if (!text.isEmpty()) {
                text = text.substring(0, text.length() - 1);
            }
            return;
        } else if (keyCode == 28) {
            deselect();
        }
        if (keyCode >= 2 && keyCode <= 11 || keyCode == 52) {
            text += Keyboard.getEventCharacter();
            width = fr.getStringWidth(text);
        }
    }

    private void deselect() {
        clicked = false;
        try {
            float current = Float.parseFloat(text);
            current = MathUtil.approximate(current, setting.getIncrement());
            if (current < setting.getMin())
                current = setting.getMin();
            else if (current > setting.getMax()) {
                current = setting.getMax();
            }
            setting.setCurrent(current);
        } catch (NumberFormatException e) {
            mc.thePlayer.addChatMessage(new ChatComponentText(ChatFormatting.RED + "NumberFormatException."));
        }
    }
}
