package com.pitaddons.module.settings;


import java.util.ArrayList;

public class Setting {

    private SettingType type;

    private String name;
    private float min;
    private float max;
    private float current;
    private float increment;
    private ArrayList<String> options;

    public Setting(String name, float min, float max, float current, float increment) {
        this.type = SettingType.SLIDER;
        this.name = name;
        this.min = min;
        this.max = max;
        this.current = current;
        this.increment = increment;
    }

    public Setting(String name) {
        this.type = SettingType.TOGGLE;
        this.name = name;
    }

    public Setting(String name, ArrayList<String> options) {
        this.type = SettingType.OPTIONS;
        this.name = name;
        this.options = options;
    }

    public SettingType getType() {
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

    public void setType(SettingType type) {
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
