package com.pitaddons;

import com.pitaddons.gui.ClickGui;
import com.pitaddons.module.Module;
import com.pitaddons.module.ModuleManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.*;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

@Mod(modid = PitAddons.MODID, name = PitAddons.NAME, version = PitAddons.VERSION)
public class PitAddons {
    public static final String MODID = "pitaddons";
    public static final String NAME = "Pit Addons";
    public static final String VERSION = "Beta-1.0";

    // Declaring moduleManager
    public static ModuleManager moduleManager = new ModuleManager();

    private final KeyBinding clickGuiKey = new KeyBinding("Open's the Click GUI", Keyboard.KEY_RSHIFT, "Pit Addons");


    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {

    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        // Register Keybindings
        for(Module module : moduleManager.getModules()) {
            ClientRegistry.registerKeyBinding(module.getKey());
        }

        // Registering clickGUI key
        ClientRegistry.registerKeyBinding(clickGuiKey);

        // Registering main class so KeyListener works
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onKeyPress(InputEvent.KeyInputEvent event) {
        if(Minecraft.getMinecraft().thePlayer == null) return;
        if(clickGuiKey.isKeyDown()) {
            Minecraft.getMinecraft().displayGuiScreen(new ClickGui());
        }
        for(Module module : moduleManager.getModules()) {
            if(module.getKey().isKeyDown()) module.toggle();
        }
    }
}
