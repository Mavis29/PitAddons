package com.pitaddons.module.modules.player;

import com.pitaddons.module.Category;
import com.pitaddons.module.Module;
import com.pitaddons.module.ModuleInfo;
import net.minecraft.client.settings.KeyBinding;
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