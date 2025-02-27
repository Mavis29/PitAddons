package com.pitaddons.gui;

import com.pitaddons.gui.components.CategoryComponent;
import com.pitaddons.module.Category;

import java.util.ArrayList;
import java.util.HashMap;

public class GuiManager {

    private static ArrayList<Category> enabledCategories = new ArrayList<Category>();
    private static HashMap<Category, CategoryComponent> categoryComponents = new HashMap<Category, CategoryComponent>();

    public static ArrayList<Category> getEnabledCategories() {
        return enabledCategories;
    }

    public static void setEnabledCategories(ArrayList<Category> enabledCategories) {
        GuiManager.enabledCategories = enabledCategories;
    }

    public static HashMap<Category, CategoryComponent> getCategoryComponents() {
        return categoryComponents;
    }

    public static void setCategoryComponents(HashMap<Category, CategoryComponent> categoryComponents) {
        GuiManager.categoryComponents = categoryComponents;
    }
}
