package com.pitaddons.module;

import com.pitaddons.module.modules.combat.AutoClicker;
import com.pitaddons.module.modules.misc.EventTests;
import com.pitaddons.module.modules.player.RightClicker;
import com.pitaddons.module.modules.player.ToggleSprint;
import com.pitaddons.module.modules.render.CakeEsp;
import com.pitaddons.module.modules.render.ChestESP;
import com.pitaddons.module.modules.render.EventList;
import com.pitaddons.module.modules.render.HUD;
import com.pitaddons.module.modules.render.notifications.Notifications;

import java.util.ArrayList;

public class ModuleManager {

    private final ArrayList<Module> modules = new ArrayList<Module>();
    private ArrayList<Module> enabledModules = new ArrayList<Module>();
    private ArrayList<Module> moveableModules;

    public ModuleManager() {
        registerModules();
    }

    public void registerModules() {
        modules.add(new EventList());
        modules.add(new HUD());
        modules.add(new ToggleSprint());
        modules.add(new CakeEsp());
        modules.add(new AutoClicker());
        modules.add(new Notifications());
        modules.add(new EventTests());
        modules.add(new ChestESP());
        modules.add(new RightClicker());
    }

    public void addEnabledModule(Module module) {
        enabledModules.add(module);
    }

    public void removeEnabledModule(Module module) {
        enabledModules.remove(module);
    }

    public ArrayList<Module> getModules() {
        return modules;
    }

    public ArrayList<Module> getEnabledModules() {
        return enabledModules;
    }

    public Module getModuleByName(String name) {
        for (Module module : modules)
            if (module.getName().equals(name))
                return module;
        return null;
    }

    public Module getEnabledModuleByName(String name) {
        for (Module module : enabledModules)
            if (module.getName().equals(name))
                return module;
        return null;
    }
}
