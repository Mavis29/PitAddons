package com.pitaddons.module.settings;


import com.pitaddons.module.Module;

import java.util.ArrayList;

public class Setting {

    public enum Type {
        SLIDER,
        OPTIONS,
        TOGGLE
    }

    private Module parent;
    private Type type;
    private String name;
    private String currentOption;
    private boolean enabled;
    private float min;
    private float max;
    private float current;
    private float increment;
    private ArrayList<String> options;

    public Setting(String name, Module parent, float min, float max, float current, float increment) {
        this.type = Type.SLIDER;
        this.name = name;
        this.parent = parent;
        this.min = min;
        this.max = max;
        this.current = current;
        this.increment = increment;
    }

    public Setting(String name, Module parent, boolean enabled) {
        this.parent = parent;
        this.type = Type.TOGGLE;
        this.name = name;
        this.enabled = enabled;
    }

    public Setting(String name, Module parent, ArrayList<String> options, String currentOption) {
        this.parent = parent;
        this.type = Type.OPTIONS;
        this.name = name;
        this.options = options;
        this.currentOption = currentOption;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getCurrentOption() {
        return currentOption;
    }

    public void setCurrentOption(String currentOption) {
        this.currentOption = currentOption;
    }

    public Type getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public float getMin() {
        return min;
    }

    public float getMax() {
        return max;
    }

    public float getCurrent() {
        return current;
    }

    public float getIncrement() {
        return increment;
    }

    public ArrayList<String> getOptions() {
        return options;
    }

    public Module getParent() {
        return parent;
    }

    public void setParent(Module parent) {
        this.parent = parent;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMin(float min) {
        this.min = min;
    }

    public void setMax(float max) {
        this.max = max;
    }

    public void setCurrent(float current) {
        this.current = current;
    }

    public void setIncrement(float increment) {
        this.increment = increment;
    }

    public void setOptions(ArrayList<String> options) {
        this.options = options;
    }
}

