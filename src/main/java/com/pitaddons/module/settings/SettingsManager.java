package com.pitaddons.module.settings;

import com.pitaddons.module.Module;

import java.util.ArrayList;

public class SettingsManager {
    private ArrayList<Setting> settings = new ArrayList<Setting>();

    public void addSetting(Setting setting) {
        settings.add(setting);
    }

    public ArrayList<Setting> getSettings() {
        return settings;
    }

    public ArrayList<Setting> getSettingsForModule(Module module) {
        ArrayList<Setting> output = new ArrayList<Setting>();
        for (Setting setting : settings)
            if (setting.getParent().equals(module)) output.add(setting);
        return output;
    }

    public Setting getSettingByName(String name, Module module) {
        for (Setting setting : settings) {
            if (!setting.getParent().equals(module)) continue;
            if (setting.getName().equals(name)) return setting;
        }
        // Throw pit addons exception setting not found
        return null;
    }
}
