package com.pitaddons.module.modules.render;

import com.pitaddons.PitAddons;
import com.pitaddons.module.Category;
import com.pitaddons.module.Module;
import com.pitaddons.module.ModuleInfo;
import com.pitaddons.module.settings.Setting;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

@ModuleInfo(
        name = "EventList",
        moveable = true,
        description = "Displays the next events on screen",
        category = Category.RENDER
)
public class EventList extends Module {

    public EventList() {
        setKey(new KeyBinding("Toggles the Event List", Keyboard.KEY_Y, "Pit Addons"));
        PitAddons.settingsManager.addSetting(new Setting("Hello", this, true));
        PitAddons.settingsManager.addSetting(new Setting("Bye", this, false));
        PitAddons.settingsManager.addSetting(new Setting("SLIDERR", this, true));
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (!PitAddons.settingsManager.getSettingByName("Hello", this).isEnabled()) return;
        if (mc.thePlayer == null) return;
        mc.thePlayer.addChatMessage(new ChatComponentText("Hello"));
    }


}
