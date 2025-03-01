package com.pitaddons.module;

import com.pitaddons.PitAddons;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.common.MinecraftForge;
import org.apache.commons.lang3.Validate;

public abstract class Module {

    ModuleManager moduleManager = PitAddons.moduleManager;

    protected Minecraft mc = Minecraft.getMinecraft();

    private final String name, description;
    private final Category category;
    private KeyBinding keyBinding;
    private boolean enabled = false;

    public Module() {
        ModuleInfo info = getClass().getAnnotation(ModuleInfo.class);
        Validate.notNull(info, "CONFUSED ANNOTATION EXCEPTION");
        this.name = info.name();
        this.description = info.description();
        this.category = info.category();
    }

    public void onEnable() {
        mc.thePlayer.addChatMessage(new ChatComponentText(name + " enabled!"));
    }

    public void onDisable() {
        mc.thePlayer.addChatMessage(new ChatComponentText(name + " disabled!"));
    }

    public void setEnabled(boolean enabled) {
        if(this.enabled == enabled) return;
        this.enabled = enabled;

        if(enabled) {;
            MinecraftForge.EVENT_BUS.register(this);
            onEnable();
        }
        else {
            MinecraftForge.EVENT_BUS.unregister(this);
            onDisable();
        }
    }

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void toggle() {
        setEnabled(!enabled);
    }

    public void setKey(KeyBinding keyBinding) {
        this.keyBinding = keyBinding;
    }

    public KeyBinding getKey() {
        return keyBinding;
    }
}
