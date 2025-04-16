package com.pitaddons.config;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.Loader;

import java.io.File;

public class PitAddonsConfig {

    public static PitAddonsConfig INSTANCE = new PitAddonsConfig();

    private File configFile;
    private Configuration config;

    // Properties

    // Hud
    private Property hudXPosProperty;
    private Property hudYPosProperty;

    // Cake-ESP
    private Property cakeEspRadiusProperty;
    private Property cakeEspTypeProperty;
    private Property cakeEspFaceRedProperty;
    private Property cakeEspFaceGreenProperty;
    private Property cakeEspFaceBlueProperty;
    private Property cakeEspFaceAlphaProperty;
    private Property cakeEspOutlineRedProperty;
    private Property cakeEspOutlineGreenProperty;
    private Property cakeEspOutlineBlueProperty;
    private Property cakeEspOutlineAlphaProperty;
    private Property cakeEspOutlineWidthProperty;

    // Auto Clicker
    private Property autoClickerMinCpsProperty;
    private Property autoClickerMaxCpsProperty;
    private Property autoClickerNeedLeftDownProperty;
    private Property autoClickerBlockHitProperty;
    private Property autoClickerNeedRightDownProperty;
    private Property autoClickerAllowMiningProperty;


    // Values

    //Hud
    private int hudXPos;
    private int hudYPos;

    // Cake-ESP
    private int cakeEspRadius;
    private String cakeEspType;
    private int cakeEspFaceRed;
    private int cakeEspFaceGreen;
    private int cakeEspFaceBlue;
    private double cakeEspFaceAlpha;
    private int cakeEspOutlineRed;
    private int cakeEspOutlineGreen;
    private int cakeEspOutlineBlue;
    private double cakeEspOutlineAlpha;
    private double cakeEspOutlineWidth;

    // Auto Clicker
    private double autoClickerMinCps;
    private double autoClickerMaxCps;
    private boolean autoClickerNeedLeftDown;
    private boolean autoClickerBlockHit;
    private boolean autoClickerNeedRightDown;
    private boolean autoClickerAllowMining;

    public PitAddonsConfig() {
        load();
    }

    public void load() {
        configFile = new File(Loader.instance().getConfigDir(), "PitAddonsConfig.cfg");
        System.out.println("Config filing...");

        // Check for null file
        if (!configFile.exists()) {
            // Create config file
            try {
                configFile.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        config = new Configuration(configFile);
        config.load();

        reload();
        save();
    }

    public void reload() {
        // Initialising

        // HUD
        hudXPosProperty = config.get("HUD", "xPos", 0);
        hudYPosProperty = config.get("HUD", "yPos", 0);

        // Cake-ESP
        cakeEspRadiusProperty = config.get("CakeESP", "radius", 10);
        cakeEspTypeProperty = config.get("CakeEsp", "type", "FullOutline");
        cakeEspFaceRedProperty = config.get("CakeESP", "faceRed", 255);
        cakeEspFaceGreenProperty = config.get("CakeESP", "faceGreen", 0);
        cakeEspFaceBlueProperty = config.get("CakeESP", "faceBlue", 0);
        cakeEspFaceAlphaProperty = config.get("CakeESP", "faceAlpha", 0.5);
        cakeEspOutlineRedProperty = config.get("CakeESP", "outlineRed", 0);
        cakeEspOutlineGreenProperty = config.get("CakeESP", "outlineGreen", 0);
        cakeEspOutlineBlueProperty = config.get("CakeESP", "outlineBlue", 0);
        cakeEspOutlineAlphaProperty = config.get("CakeESP", "outlineAlpha", 0.5);
        cakeEspOutlineWidthProperty = config.get("CakeESP", "outlineWidth", 0.5);

        // Auto Clicker
        autoClickerMinCpsProperty = config.get("AutoClicker", "minCps", 10.0);
        autoClickerMaxCpsProperty = config.get("AutoClicker", "maxCps", 14.0);
        autoClickerNeedLeftDownProperty = config.get("AutoClicker", "needLeftDown", true);
        autoClickerBlockHitProperty = config.get("AutoClicker", "blockHit", true);
        autoClickerNeedRightDownProperty = config.get("AutoClicker", "needRightDown", true);
        autoClickerAllowMiningProperty = config.get("AutoClicker", "allowMining", true);


        // HUD
        hudXPos = hudXPosProperty.getInt();
        hudYPos = hudYPosProperty.getInt();

        // Cake-ESP
        cakeEspRadius = cakeEspRadiusProperty.getInt();
        cakeEspType = cakeEspTypeProperty.getString();

        cakeEspFaceRed = cakeEspFaceRedProperty.getInt();
        cakeEspFaceGreen = cakeEspFaceGreenProperty.getInt();
        cakeEspFaceBlue = cakeEspFaceBlueProperty.getInt();
        cakeEspFaceAlpha = cakeEspFaceAlphaProperty.getDouble();
        cakeEspOutlineRed = cakeEspOutlineRedProperty.getInt();
        cakeEspOutlineGreen = cakeEspOutlineGreenProperty.getInt();
        cakeEspOutlineBlue = cakeEspOutlineBlueProperty.getInt();
        cakeEspOutlineAlpha = cakeEspOutlineAlphaProperty.getDouble();
        cakeEspOutlineWidth = cakeEspOutlineWidthProperty.getDouble();

        // Auto Clicker
        autoClickerMinCps = autoClickerMinCpsProperty.getDouble();
        autoClickerMaxCps = autoClickerMaxCpsProperty.getDouble();
        autoClickerNeedLeftDown = autoClickerNeedLeftDownProperty.getBoolean();
        autoClickerBlockHit = autoClickerBlockHitProperty.getBoolean();
        autoClickerNeedRightDown = autoClickerNeedRightDownProperty.getBoolean();
        autoClickerAllowMining = autoClickerAllowMiningProperty.getBoolean();


        save();
    }

    public void save() {
        config.save();
    }

    public static PitAddonsConfig getInstance() {
        return INSTANCE;
    }

    // SETTERS

    // HUD
    public void setHudXPos(int hudXPos) {
        this.hudXPos = hudXPos;
        hudXPosProperty.set(hudXPos);
        save();
    }


    public void setHudYPos(int hudYPos) {
        this.hudYPos = hudYPos;
        hudYPosProperty.set(hudYPos);
        save();
    }

    // Cake-ESP
    public void setCakeEspRadius(int cakeEspRadius) {
        this.cakeEspRadius = cakeEspRadius;
        cakeEspRadiusProperty.set(cakeEspRadius);
        save();
    }

    public void setCakeEspType(String cakeEspType) {
        this.cakeEspType = cakeEspType;
        cakeEspTypeProperty.set(cakeEspType);
        save();
    }

    public void setCakeEspFaceRed(int cakeEspFaceRed) {
        this.cakeEspFaceRed = cakeEspFaceRed;
        cakeEspFaceRedProperty.set(cakeEspFaceRed);
        save();
    }

    public void setCakeEspFaceGreen(int cakeEspFaceGreen) {
        this.cakeEspFaceGreen = cakeEspFaceGreen;
        cakeEspFaceGreenProperty.set(cakeEspFaceGreen);
        save();
    }

    public void setCakeEspFaceBlue(int cakeEspFaceBlue) {
        this.cakeEspFaceBlue = cakeEspFaceBlue;
        cakeEspFaceBlueProperty.set(cakeEspFaceBlue);
        save();
    }

    public void setCakeEspFaceAlpha(double cakeEspFaceAlpha) {
        this.cakeEspFaceAlpha = cakeEspFaceAlpha;
        cakeEspFaceAlphaProperty.set(cakeEspFaceAlpha);
        save();
    }

    public void setCakeEspOutlineRed(int cakeEspOutlineRed) {
        this.cakeEspOutlineRed = cakeEspOutlineRed;
        cakeEspOutlineRedProperty.set(cakeEspOutlineRed);
        save();
    }

    public void setCakeEspOutlineGreen(int cakeEspOutlineGreen) {
        this.cakeEspOutlineGreen = cakeEspOutlineGreen;
        cakeEspOutlineGreenProperty.set(cakeEspOutlineGreen);
        save();
    }

    public void setCakeEspOutlineBlue(int cakeEspOutlineBlue) {
        this.cakeEspOutlineBlue = cakeEspOutlineBlue;
        cakeEspOutlineBlueProperty.set(cakeEspOutlineBlue);
        save();
    }

    public void setCakeEspOutlineAlpha(double cakeEspOutlineAlpha) {
        this.cakeEspOutlineAlpha = cakeEspOutlineAlpha;
        cakeEspOutlineAlphaProperty.set(cakeEspOutlineAlpha);
        save();
    }

    public void setCakeEspOutlineWidth(double cakeEspOutlineWidth) {
        this.cakeEspOutlineWidth = cakeEspOutlineWidth;
        cakeEspOutlineWidthProperty.set(cakeEspOutlineWidth);
        save();
    }

    // Auto Clicker
    public void setAutoClickerMinCps(double autoClickerMinCps) {
        this.autoClickerMinCps = autoClickerMinCps;
        autoClickerMinCpsProperty.set(autoClickerMinCps);
        save();
    }

    public void setAutoClickerMaxCps(double autoClickerMaxCps) {
        this.autoClickerMaxCps = autoClickerMaxCps;
        autoClickerMaxCpsProperty.set(autoClickerMaxCps);
        save();
    }

    public void setAutoClickerNeedLeftDown(boolean autoClickerNeedLeftDown) {
        this.autoClickerNeedLeftDown = autoClickerNeedLeftDown;
        autoClickerNeedLeftDownProperty.set(autoClickerNeedLeftDown);
        save();
    }

    public void setAutoClickerBlockHit(boolean autoClickerBlockHit) {
        this.autoClickerBlockHit = autoClickerBlockHit;
        autoClickerBlockHitProperty.set(autoClickerBlockHit);
        save();
    }

    public void setAutoClickerNeedRightDown(boolean autoClickerNeedRightDown) {
        this.autoClickerNeedRightDown = autoClickerNeedRightDown;
        autoClickerNeedRightDownProperty.set(autoClickerNeedRightDown);
        save();
    }

    public void setAutoClickerAllowMining(boolean autoClickerAllowMining) {
        this.autoClickerAllowMining = autoClickerAllowMining;
        autoClickerAllowMiningProperty.set(autoClickerAllowMining);
        save();
    }

    // GETTERS

    // HUD
    public int getHudXPos() {
        return hudXPos;
    }

    public int getHudYPos() {
        return hudYPos;
    }

    // Cake-ESP
    public int getCakeEspRadius() {
        return cakeEspRadius;
    }

    public String getCakeEspType() {
        return cakeEspType;
    }

    public int getCakeEspFaceRed() {
        return cakeEspFaceRed;
    }

    public int getCakeEspFaceGreen() {
        return cakeEspFaceGreen;
    }

    public int getCakeEspFaceBlue() {
        return cakeEspFaceBlue;
    }

    public double getCakeEspFaceAlpha() {
        return cakeEspFaceAlpha;
    }

    public int getCakeEspOutlineRed() {
        return cakeEspOutlineRed;
    }

    public int getCakeEspOutlineGreen() {
        return cakeEspOutlineGreen;
    }

    public int getCakeEspOutlineBlue() {
        return cakeEspOutlineBlue;
    }

    public double getCakeEspOutlineAlpha() {
        return cakeEspOutlineAlpha;
    }

    public double getCakeEspOutlineWidth() {
        return cakeEspOutlineWidth;
    }

    // Auto Clicker
    public double getAutoClickerMinCps() {
        return autoClickerMinCps;
    }

    public double getAutoClickerMaxCps() {
        return autoClickerMaxCps;
    }

    public boolean isAutoClickerNeedLeftDown() {
        return autoClickerNeedLeftDown;
    }

    public boolean isAutoClickerBlockHit() {
        return autoClickerBlockHit;
    }

    public boolean isAutoClickerNeedRightDown() {
        return autoClickerNeedRightDown;
    }

    public boolean isAutoClickerAllowMining() {
        return autoClickerAllowMining;
    }
}
