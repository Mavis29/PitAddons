package com.pitaddons.module.modules.render;

import com.pitaddons.module.Category;
import com.pitaddons.module.Module;
import com.pitaddons.module.ModuleInfo;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

@ModuleInfo(
        name = "Focus",
        moveable = false,
        description = "Stops players from Rendering",
        category = Category.RENDER
)
public class Focus extends Module {

    public Focus() {
        setKey(new KeyBinding("Toggles Focus", Keyboard.KEY_NONE, "Pit Addons"));
    }

    @SubscribeEvent
    public void onRenderLiving(RenderLivingEvent.Pre event) {
        if (!(event.entity instanceof EntityPlayer)) return;
        event.setCanceled(true);
    }
}
