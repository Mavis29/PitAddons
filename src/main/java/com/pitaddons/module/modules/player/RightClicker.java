package com.pitaddons.module.modules.player;

import com.pitaddons.PitAddons;
import com.pitaddons.config.PitAddonsConfig;
import com.pitaddons.module.Category;
import com.pitaddons.module.Module;
import com.pitaddons.module.ModuleInfo;
import com.pitaddons.module.settings.Setting;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

@ModuleInfo(
        name = "Right Clicker",
        moveable = false,
        description = "Right clicks at the speed of light.",
        category = Category.PLAYER
)
public class RightClicker extends Module {

    private float minCps, maxCps;
    private boolean needMouseDown;
    private long nextClick;


    public RightClicker() {
        setKey(new KeyBinding("Toggles Right Clicker", Keyboard.KEY_NONE, "Pit Addons"));
        PitAddons.settingsManager.addSetting(new Setting("Min cps", this, 0.0f, 100.0f,
                50, 0.1f));
        PitAddons.settingsManager.addSetting(new Setting("Max cps", this, 0.0f, 100.0f,
                70, 0.1f));
        PitAddons.settingsManager.addSetting(new Setting("Require mouse down", this,
                PitAddonsConfig.getInstance().isAutoClickerNeedLeftDown()));
        minCps = PitAddons.settingsManager.getSettingByName("Min cps", this).getCurrent();
        maxCps = PitAddons.settingsManager.getSettingByName("Max cps", this).getCurrent();
        needMouseDown = PitAddons.settingsManager.getSettingByName("Require mouse down", this).isEnabled();
        nextClick = calculateNext();
    }

    @SubscribeEvent
    public void onRenderTick(TickEvent.RenderTickEvent event) {
        if (mc.thePlayer == null || mc.theWorld == null) return;
        if (System.currentTimeMillis() < nextClick) return;

        nextClick = calculateNext();
        rightClick(needMouseDown);
    }

    private void rightClick(boolean needMouseDown) {
        if (needMouseDown && !Mouse.isButtonDown(1)) return;
        KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), false);
        KeyBinding.onTick(mc.gameSettings.keyBindUseItem.getKeyCode());
    }

    private long calculateNext() {
        long currentTime = System.currentTimeMillis();

        float difference = maxCps - minCps;

        float randomCps = (float) ((Math.random() * difference) + minCps);
        int clickTime = (int) (1000 / randomCps);

        return currentTime + clickTime;
    }

    @Override
    public void onSettingSave() {
        super.onSettingSave();
        minCps = PitAddons.settingsManager.getSettingByName("Min cps", this).getCurrent();
        maxCps = PitAddons.settingsManager.getSettingByName("Max cps", this).getCurrent();
        needMouseDown = PitAddons.settingsManager.getSettingByName("Require mouse down", this).isEnabled();
        nextClick = calculateNext();
    }
}
