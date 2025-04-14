package com.pitaddons.module;

import com.pitaddons.PitAddons;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.common.MinecraftForge;
import org.apache.commons.lang3.Validate;

public abstract class Module {

    ModuleManager moduleManager = PitAddons.moduleManager;

    protected Minecraft mc = Minecraft.getMinecraft();
    protected ScaledResolution sr = new ScaledResolution(mc);

    private final String name, description;
    private final Category category;
    private final boolean moveable;
    private KeyBinding keyBinding;
    private boolean enabled = false;
    private int hudX, hudY;

    public Module() {
        ModuleInfo info = getClass().getAnnotation(ModuleInfo.class);
        Validate.notNull(info, "CONFUSED ANNOTATION EXCEPTION");
        this.name = info.name();
        this.description = info.description();
        this.category = info.category();
        this.moveable = info.moveable();
    }

    public void onEnable() {
        mc.thePlayer.addChatMessage(new ChatComponentText(name + " enabled!"));
    }

    public void onDisable() {
        mc.thePlayer.addChatMessage(new ChatComponentText(name + " disabled!"));
    }

    public void setEnabled(boolean enabled) {
        if (this.enabled == enabled) return;
        this.enabled = enabled;

        if (enabled) {
            MinecraftForge.EVENT_BUS.register(this);
            PitAddons.moduleManager.addEnabledModule(this);
            onEnable();
        } else {
            MinecraftForge.EVENT_BUS.unregister(this);
            PitAddons.moduleManager.removeEnabledModule(this);
            onDisable();
        }
    }

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }

    public boolean isMoveable() {
        return moveable;
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

    public void setHudPos(int hudX, int hudY) {
        this.hudX = hudX;
        this.hudY = hudY;
    }

    public int[] getHudPos() {
        return new int[]{hudX, hudY};
    }

    public void onSettingSave() {
        System.out.println("Saving settings: " + this.name);
    }
}
