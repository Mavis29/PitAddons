package com.pitaddons.module;

import com.pitaddons.module.modules.render.EventList;
import com.pitaddons.module.modules.render.RingOutline;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager {

    private final List<Module> modules = new ArrayList<Module>();

    public ModuleManager() {
        registerModules();
    }

    public void registerModules() {
        modules.add(new RingOutline());
        modules.add(new EventList());
    }

    public List<Module> getModules() {
        return modules;
    }
}
