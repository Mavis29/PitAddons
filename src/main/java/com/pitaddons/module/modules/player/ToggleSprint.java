package com.pitaddons.module.modules.player;

import com.pitaddons.PitAddons;
import com.pitaddons.module.Category;
import com.pitaddons.module.Module;
import com.pitaddons.module.ModuleInfo;
import com.pitaddons.module.settings.Setting;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

@ModuleInfo(
        name = "Toggle Sprint",
        moveable = false,
        description = "Toggles Sprint",
        category = Category.PLAYER
)
public class ToggleSprint extends Module {

    private boolean sprinting = false;
    private int lastHeight = -1;
    private int lastWidth = -1;


    public ToggleSprint() {
        setKey(new KeyBinding("Toggle Sprint key", Keyboard.KEY_B, "Pit Addons"));
        PitAddons.settingsManager.addSetting(new Setting("Permanent", this, true));
        PitAddons.settingsManager.addSetting(new Setting("Show on Hud", this, false));
    }

    @SubscribeEvent
    public void onPlayerUpdate(LivingEvent.LivingUpdateEvent event) {
        if (mc.thePlayer.isCollidedHorizontally) return;
        if (mc.thePlayer.moveForward <= 0) return;
        if (mc.thePlayer.isUsingItem()) return;
        if (mc.thePlayer.isSneaking()) return;
        if (mc.thePlayer.isSprinting()) return;

        mc.thePlayer.setSprinting(true);
    }

    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent.Text event) {
        if (mc.thePlayer == null) return;
        if (!PitAddons.settingsManager.getSettingByName("Show on Hud", this).isEnabled()) return;
        FontRenderer fr = mc.fontRendererObj;
        if (mc.displayWidth != lastWidth || mc.displayHeight != lastHeight) {
            lastWidth = mc.displayWidth;
            lastHeight = mc.displayHeight;
            sr = new ScaledResolution(mc);
        }
        if (PitAddons.settingsManager.getSettingByName("Permanent", this).isEnabled()) {
            fr.drawStringWithShadow("[Toggle Sprint: Sprinting]", 2, sr.getScaledHeight() - 10, -1);
        }
    }

    @Override
    public void onEnable() {
        super.onEnable();
        sprinting = true;
    }

    @Override
    public void onDisable() {
        super.onDisable();
        sprinting = false;
    }
}