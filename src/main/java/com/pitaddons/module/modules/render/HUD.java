package com.pitaddons.module.modules.render;

import com.pitaddons.PitAddons;
import com.pitaddons.config.PitAddonsConfig;
import com.pitaddons.module.Category;
import com.pitaddons.module.Module;
import com.pitaddons.module.ModuleInfo;
import com.pitaddons.module.settings.Setting;
import com.pitaddons.util.HUDRenderUtil;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.util.HashMap;

@ModuleInfo(
        name = "HUD",
        moveable = true,
        description = "Displays all the enabled Modules",
        category = Category.RENDER
)
public class HUD extends Module {

    private int x, y, red, green, blue, width, heightHeader, yModules;
    private float alpha;

    HashMap<Category, Color> categoryColors;

    public HUD() {
        setKey(new KeyBinding("Toggles the Hud", Keyboard.KEY_NONE, "Pit Addons"));
        PitAddons.settingsManager.addSetting(new Setting("Red", this, 0.0F, 255.0f, 105.0f, 1));
        PitAddons.settingsManager.addSetting(new Setting("Green", this, 0.0F, 255.0f, 105.0f, 1));
        PitAddons.settingsManager.addSetting(new Setting("Blue", this, 0.0F, 255.0f, 105.0f, 1));
        PitAddons.settingsManager.addSetting(new Setting("Alpha", this, 0.0F, 1.0f, 0.5F, 0.01F));
        red = (int) PitAddons.settingsManager.getSettingByName("Red", this).getCurrent();
        green = (int) PitAddons.settingsManager.getSettingByName("Green", this).getCurrent();
        blue = (int) PitAddons.settingsManager.getSettingByName("Blue", this).getCurrent();
        alpha = PitAddons.settingsManager.getSettingByName("Alpha", this).getCurrent();
        x = PitAddonsConfig.getInstance().getHudXPos();
        y = PitAddonsConfig.getInstance().getHudYPos();
        width = 100;
        heightHeader = 10;

        categoryColors = new HashMap<Category, Color>();
        categoryColors.put(Category.RENDER, new Color(252, 232, 3));
        categoryColors.put(Category.COMBAT, new Color(225, 0, 0));
        categoryColors.put(Category.PLAYER, new Color(6, 115, 204));
        categoryColors.put(Category.MISC, new Color(6, 204, 52));
    }

    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent.Text event) {
        FontRenderer fr = mc.fontRendererObj;

        red = (int) PitAddons.settingsManager.getSettingByName("Red", this).getCurrent();
        green = (int) PitAddons.settingsManager.getSettingByName("Green", this).getCurrent();
        blue = (int) PitAddons.settingsManager.getSettingByName("Blue", this).getCurrent();
        alpha = PitAddons.settingsManager.getSettingByName("Alpha", this).getCurrent();

        yModules = y + heightHeader;

        HUDRenderUtil.drawRectangle(x, y, width, heightHeader, 58, 60, 75, 1.0F);
        int textWidth = fr.getStringWidth("HUD");
        fr.drawStringWithShadow("HUD", x + ((float) width / 2 - (float) textWidth / 2), y + 1, -1);
        for (Module module : PitAddons.moduleManager.getEnabledModules()) {
            if (module.getName().equals("HUD")) continue;
            HUDRenderUtil.drawRectangle(x, yModules, width, 10, red, green, blue, alpha);
            Color color = categoryColors.get(module.getCategory());
            textWidth = fr.getStringWidth(module.getName());
            fr.drawStringWithShadow(module.getName(), (int) (x + ((float) width / 2 - (float) textWidth / 2)), yModules + 1, color.getRGB());
            yModules += 10;
        }
    }

    @Override
    public void setHudPos(int hudX, int hudY) {
        this.x = hudX;
        this.y = hudY;
        PitAddonsConfig.getInstance().setHudXPos(hudX);
        PitAddonsConfig.getInstance().setHudYPos(hudY);
    }

    @Override
    public int[] getHudPos() {
        return new int[]{x, y};
    }
}
