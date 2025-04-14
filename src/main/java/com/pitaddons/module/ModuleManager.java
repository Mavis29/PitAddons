package com.pitaddons.module;

import com.pitaddons.module.modules.combat.AutoClicker;
import com.pitaddons.module.modules.player.ToggleSprint;
import com.pitaddons.module.modules.render.CakeEsp;
import com.pitaddons.module.modules.render.EventList;
import com.pitaddons.module.modules.render.HUD;

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
}
