package com.pitaddons.clickgui.components;

import com.pitaddons.PitAddons;
import com.pitaddons.clickgui.Component;
import com.pitaddons.module.Module;
import com.pitaddons.module.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;

public class ModuleComponent extends Component {

    private final ResourceLocation CHECK = new ResourceLocation("pitaddons", "textures/clickgui/settings.png");

    Minecraft mc = Minecraft.getMinecraft();
    FontRenderer fr = mc.fontRendererObj;
    ScaledResolution sr = new ScaledResolution(mc);

    Module module;
    HashMap<Setting, ToggleComponent> toggleComponents;
    HashMap<Setting, SliderComponent> sliderComponents;
    HashMap<Setting, OptionsComponent> optionsComponents;
    CheckComponent checkComponent;

    private final int checkHeight = 10;
    private final int checkWidth = 10;
    private final int checkY = 30;

    public ModuleComponent(Module module) {
        this.module = module;
        toggleComponents = new HashMap<Setting, ToggleComponent>();
        sliderComponents = new HashMap<Setting, SliderComponent>();
        optionsComponents = new HashMap<Setting, OptionsComponent>();
        for (Setting setting : PitAddons.settingsManager.getSettingsForModule(module)) {
            if (setting.getType().equals(Setting.Type.TOGGLE))
                toggleComponents.put(setting, new ToggleComponent(setting));
            else if (setting.getType().equals(Setting.Type.SLIDER))
                sliderComponents.put(setting, new SliderComponent(setting));
            else if (setting.getType().equals(Setting.Type.OPTIONS))
                optionsComponents.put(setting, new OptionsComponent(setting));
        }
        checkComponent = new CheckComponent(module);
    }

    public void drawModuleComponent(int x, int y, int width, int mouseX, int mouseY, float partialTicks) {
        int shadow = 1;
        int origY = y;
        int textWidth = fr.getStringWidth(module.getName());
        int centeredText = x + width / 2 - textWidth / 2;
        fr.drawStringWithShadow(module.getName(), centeredText, y, colorText);
        checkComponent.drawComponent(x, y, mouseX, mouseY, partialTicks);

        y += 8 + 6; //8 : text size, 6 : offset

        for (Setting setting : PitAddons.settingsManager.getSettingsForModule(module)) {
            drawRect(x + shadow, y + shadow, x + width + shadow, y + 16 + shadow, 0xFF131319);
            drawRect(x, y, x + width, y + 16, colorDetails);
            fr.drawStringWithShadow(setting.getName(), x + 10, y + 4, colorText);
            drawButton(setting, x + width, y, mouseX, mouseY, partialTicks);
            y += 16;
        }
        if (PitAddons.settingsManager.getSettingsForModule(module).isEmpty()) return;
        drawRect(x - shadow, origY + 8 + 6, x, y - shadow, 0xFF595C72);
        drawRect(x, origY + 8 + 6 - shadow, x + width - shadow, origY + 8 + 6, 0xFF595C72);
    }

    public int getHeight() {
        int height = 0;
        height += 12;
        for (Setting setting : PitAddons.settingsManager.getSettingsForModule(module)) height += 16;
        return height;
    }

    private void drawButton(Setting setting, int x, int y, int mouseX, int mouseY, float partialTicks) {
        if (setting.getType().equals(Setting.Type.TOGGLE)) {
            ToggleComponent component = toggleComponents.get(setting);
            component.drawComponent(x - 30, y + 3, mouseX, mouseY, partialTicks);
        } else if (setting.getType().equals(Setting.Type.SLIDER)) {
            SliderComponent component = sliderComponents.get(setting);
            component.drawComponent(x - 40, y + 5, mouseX, mouseY, partialTicks);
        } else if (setting.getType().equals(Setting.Type.OPTIONS)) {
            OptionsComponent component = optionsComponents.get(setting);
            component.drawComponent(x - 55, y + 1, mouseX, mouseY, partialTicks);
        }
    }

    public Module getModule() {
        return module;
    }

    @Override
    public void onClick(int mouseX, int mouseY, int mouseButton) {
        super.onClick(mouseX, mouseY, mouseButton);
        for (ToggleComponent component : toggleComponents.values()) {
            component.onClick(mouseX, mouseY, mouseButton);
        }
        for (SliderComponent component : sliderComponents.values()) {
            component.onClick(mouseX, mouseY, mouseButton);
        }
        for (OptionsComponent component : optionsComponents.values()) {
            component.onClick(mouseX, mouseY, mouseButton);
        }
        checkComponent.onClick(mouseX, mouseY, mouseButton);
    }

    @Override
    public void onRelease(int mouseX, int mouseY, int mouseButton) {
        super.onRelease(mouseX, mouseY, mouseButton);
        for (SliderComponent component : sliderComponents.values()) {
            component.onRelease(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    public void handleKeyboardInput() {
        super.handleKeyboardInput();
        for (SliderComponent sliderComponent : sliderComponents.values()) {
            sliderComponent.handleKeyboardInput();
        }
    }
}
