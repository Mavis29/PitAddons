package com.pitaddons.module.modules.render;

import com.pitaddons.PitAddons;
import com.pitaddons.module.Category;
import com.pitaddons.module.Module;
import com.pitaddons.module.ModuleInfo;
import com.pitaddons.module.settings.Setting;
import com.pitaddons.util.BlockRenderUtil;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

@ModuleInfo(
        name = "Chest ESP",
        moveable = false,
        description = "Highlights Chest",
        category = Category.RENDER
)
public class ChestESP extends Module {

    ArrayList<String> espTypes;

    private String espType;
    private int radius, faceRed, faceGreen, faceBlue, outlineRed, outlineGreen, outlineBlue;
    private float faceAlpha, outlineAlpha, outlineWidth;

    public ChestESP() {
        // TODO: - add to config system
        setKey(new KeyBinding("Toggles ChestESP", Keyboard.KEY_NONE, "Pit Addons"));
        espTypes = new ArrayList<String>();
        espTypes.add("Full");
        espTypes.add("Outline");
        espTypes.add("FullOutline");
        PitAddons.settingsManager.addSetting(new Setting("Radius", this, 1.0f, 100.0f,
                100.0f, 1.0f));
        PitAddons.settingsManager.addSetting(new Setting("Esp Type", this, espTypes, "Outline"));
        PitAddons.settingsManager.addSetting(new Setting("Face Red", this, 0.0f, 255.0f, 255.0f, 1.0f));
        PitAddons.settingsManager.addSetting(new Setting("Face Green", this, 0.0f, 255.0f, 0.0f, 1.0f));
        PitAddons.settingsManager.addSetting(new Setting("Face Blue", this, 0.0f, 255.0f, 0.0f, 1.0f));
        PitAddons.settingsManager.addSetting(new Setting("Face Alpha", this, 0.0f, 1.0f, 0.5f, 0.01f));
        PitAddons.settingsManager.addSetting(new Setting("Outline Red", this, 0.0f, 255.0f, 0.0f, 1.0f));
        PitAddons.settingsManager.addSetting(new Setting("Outline Green", this, 0.0f, 255.0f, 255.0f, 1.0f));
        PitAddons.settingsManager.addSetting(new Setting("Outline Blue", this, 0.0f, 255.0f, 0.0f, 1.0f));
        PitAddons.settingsManager.addSetting(new Setting("Outline Alpha", this, 0.0f, 1.0f, 1.0f, 0.01f));
        PitAddons.settingsManager.addSetting(new Setting("Outline Width", this, 0.0f, 10.0f, 1.0f, 0.01f));
        radius = (int) PitAddons.settingsManager.getSettingByName("Radius", this).getCurrent();
        espType = PitAddons.settingsManager.getSettingByName("Esp Type", this).getCurrentOption();
        faceRed = (int) PitAddons.settingsManager.getSettingByName("Face Red", this).getCurrent();
        faceGreen = (int) PitAddons.settingsManager.getSettingByName("Face Green", this).getCurrent();
        faceBlue = (int) PitAddons.settingsManager.getSettingByName("Face Blue", this).getCurrent();
        faceAlpha = PitAddons.settingsManager.getSettingByName("Face Alpha", this).getCurrent();
        outlineRed = (int) PitAddons.settingsManager.getSettingByName("Outline Red", this).getCurrent();
        outlineGreen = (int) PitAddons.settingsManager.getSettingByName("Outline Green", this).getCurrent();
        outlineBlue = (int) PitAddons.settingsManager.getSettingByName("Outline Blue", this).getCurrent();
        outlineAlpha = PitAddons.settingsManager.getSettingByName("Outline Alpha", this).getCurrent();
        outlineWidth = PitAddons.settingsManager.getSettingByName("Outline Width", this).getCurrent();
    }

    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        renderChests(espType, event.partialTicks);
    }

    private void renderChests(String type, float partialTicks) {
        if (type.equals("Full")) {
            for (TileEntity tileEntity : mc.theWorld.loadedTileEntityList) {
                if (!(tileEntity instanceof TileEntityChest)) continue;
                if (mc.thePlayer.getDistanceSq(tileEntity.getPos()) > radius * radius) continue;
                TileEntityChest tileEntityChest = (TileEntityChest) tileEntity;
                BlockRenderUtil.drawBlockFacesFilled(tileEntityChest.getPos(), faceRed, faceGreen, faceBlue,
                        faceAlpha, partialTicks);
            }
        } else if (type.equals("Outline")) {
            for (TileEntity tileEntity : mc.theWorld.loadedTileEntityList) {
                if (!(tileEntity instanceof TileEntityChest)) continue;
                if (mc.thePlayer.getDistanceSq(tileEntity.getPos()) > radius * radius) continue;
                TileEntityChest tileEntityChest = (TileEntityChest) tileEntity;
                BlockRenderUtil.drawBlockFacesOutline(tileEntityChest.getPos(), outlineRed, outlineGreen, outlineBlue,
                        outlineAlpha, outlineWidth, partialTicks);
            }
        } else if (type.equals("FullOutline")) {
            for (TileEntity tileEntity : mc.theWorld.loadedTileEntityList) {
                if (!(tileEntity instanceof TileEntityChest)) continue;
                if (mc.thePlayer.getDistanceSq(tileEntity.getPos()) > radius * radius) continue;
                TileEntityChest tileEntityChest = (TileEntityChest) tileEntity;
                BlockRenderUtil.drawBlockFacesFilled(tileEntityChest.getPos(), faceRed, faceGreen, faceBlue, faceAlpha,
                        partialTicks);
                BlockRenderUtil.drawBlockFacesOutline(tileEntityChest.getPos(), outlineRed, outlineGreen, outlineBlue,
                        outlineAlpha, outlineWidth, partialTicks);
            }
        }
    }


    @Override
    public void onSettingSave() {
        super.onSettingSave();
        radius = (int) PitAddons.settingsManager.getSettingByName("Radius", this).getCurrent();
        espType = PitAddons.settingsManager.getSettingByName("Esp Type", this).getCurrentOption();
        faceRed = (int) PitAddons.settingsManager.getSettingByName("Face Red", this).getCurrent();
        faceGreen = (int) PitAddons.settingsManager.getSettingByName("Face Green", this).getCurrent();
        faceBlue = (int) PitAddons.settingsManager.getSettingByName("Face Blue", this).getCurrent();
        faceAlpha = PitAddons.settingsManager.getSettingByName("Face Alpha", this).getCurrent();
        outlineRed = (int) PitAddons.settingsManager.getSettingByName("Outline Red", this).getCurrent();
        outlineGreen = (int) PitAddons.settingsManager.getSettingByName("Outline Green", this).getCurrent();
        outlineBlue = (int) PitAddons.settingsManager.getSettingByName("Outline Blue", this).getCurrent();
        outlineAlpha = PitAddons.settingsManager.getSettingByName("Outline Alpha", this).getCurrent();
        outlineWidth = PitAddons.settingsManager.getSettingByName("Outline Width", this).getCurrent();
    }
}
