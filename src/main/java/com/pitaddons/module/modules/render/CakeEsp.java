package com.pitaddons.module.modules.render;

import com.pitaddons.PitAddons;
import com.pitaddons.module.Category;
import com.pitaddons.module.Module;
import com.pitaddons.module.ModuleInfo;
import com.pitaddons.module.settings.Setting;
import com.pitaddons.util.BlockRenderUtil;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

@ModuleInfo(
        name = "Cake ESP",
        moveable = false,
        description = "Highlights the red concrete blocks in a cake",
        category = Category.RENDER
)
public class CakeEsp extends Module {

    private final ArrayList<String> espTypes;

    private String espType;
    private int radius;

    private float faceRed, faceGreen, faceBlue, faceAlpha, outlineRed, outlineGreen, outlineBlue, outlineAlpha, outlineWidth;

    public CakeEsp() {
        espTypes = new ArrayList<String>();
        espTypes.add("Full");
        espTypes.add("Outline");
        espTypes.add("FullOutline");
        setKey(new KeyBinding("Toggles the Cake ESP", Keyboard.KEY_K, "Pit Addons"));
        PitAddons.settingsManager.addSetting(new Setting("Radius", this, 1.0f, 50.0f, 20.0f, 1.0f));
        PitAddons.settingsManager.addSetting(new Setting("Esp Type", this, espTypes, "FullOutline"));
        PitAddons.settingsManager.addSetting(new Setting("Face Red", this, 0.0f, 1.0f, 1.0f, 0.01f));
        PitAddons.settingsManager.addSetting(new Setting("Face Green", this, 0.0f, 1.0f, 0.0f, 0.01f));
        PitAddons.settingsManager.addSetting(new Setting("Face Blue", this, 0.0f, 1.0f, 0.0f, 0.01f));
        PitAddons.settingsManager.addSetting(new Setting("Face Alpha", this, 0.0f, 1.0f, 0.5f, 0.01f));
        PitAddons.settingsManager.addSetting(new Setting("Outline Red", this, 0.0f, 1.0f, 1.0f, 0.01f));
        PitAddons.settingsManager.addSetting(new Setting("Outline Green", this, 0.0f, 1.0f, 0.0f, 0.01f));
        PitAddons.settingsManager.addSetting(new Setting("Outline Blue", this, 0.0f, 1.0f, 0.0f, 0.01f));
        PitAddons.settingsManager.addSetting(new Setting("Outline Alpha", this, 0.0f, 1.0f, 0.5f, 0.01f));
        PitAddons.settingsManager.addSetting(new Setting("Outline Width", this, 0.0f, 10.0f, 0.5f, 0.01f));
        radius = (int) PitAddons.settingsManager.getSettingByName("Radius", this).getCurrent();
        espType = PitAddons.settingsManager.getSettingByName("Esp Type", this).getCurrentOption();
        faceRed = PitAddons.settingsManager.getSettingByName("Face Red", this).getCurrent();
        faceGreen = PitAddons.settingsManager.getSettingByName("Face Green", this).getCurrent();
        faceBlue = PitAddons.settingsManager.getSettingByName("Face Blue", this).getCurrent();
        faceAlpha = PitAddons.settingsManager.getSettingByName("Face Alpha", this).getCurrent();
        outlineRed = PitAddons.settingsManager.getSettingByName("Outline Red", this).getCurrent();
        outlineGreen = PitAddons.settingsManager.getSettingByName("Outline Green", this).getCurrent();
        outlineBlue = PitAddons.settingsManager.getSettingByName("Outline Blue", this).getCurrent();
        outlineAlpha = PitAddons.settingsManager.getSettingByName("Outline Alpha", this).getCurrent();
        outlineWidth = PitAddons.settingsManager.getSettingByName("Outline Width", this).getCurrent();
    }

    @SubscribeEvent
    public void onRenderLast(RenderWorldLastEvent event) {
        if (mc.thePlayer == null || mc.theWorld == null) return;
        radius = (int) PitAddons.settingsManager.getSettingByName("Radius", this).getCurrent();
        espType = PitAddons.settingsManager.getSettingByName("Esp Type", this).getCurrentOption();
        faceRed = PitAddons.settingsManager.getSettingByName("Face Red", this).getCurrent();
        faceGreen = PitAddons.settingsManager.getSettingByName("Face Green", this).getCurrent();
        faceBlue = PitAddons.settingsManager.getSettingByName("Face Blue", this).getCurrent();
        faceAlpha = PitAddons.settingsManager.getSettingByName("Face Alpha", this).getCurrent();
        outlineRed = PitAddons.settingsManager.getSettingByName("Outline Red", this).getCurrent();
        outlineGreen = PitAddons.settingsManager.getSettingByName("Outline Green", this).getCurrent();
        outlineBlue = PitAddons.settingsManager.getSettingByName("Outline Blue", this).getCurrent();
        outlineAlpha = PitAddons.settingsManager.getSettingByName("Outline Alpha", this).getCurrent();
        outlineWidth = PitAddons.settingsManager.getSettingByName("Outline Width", this).getCurrent();
        BlockPos currentPos = mc.thePlayer.getPosition();
        searchBlocks(currentPos, radius, Block.getBlockFromName("minecraft:hardened_clay"), event.partialTicks);
    }

    private void searchBlocks(BlockPos currentPos, int radius, Block block, float partialTicks) {
        for (int x = currentPos.getX() - radius; x < currentPos.getX() + radius; x++) {
            for (int z = currentPos.getZ() - radius; z < currentPos.getZ() + radius; z++) {
                for (int y = currentPos.getY() - radius; y < currentPos.getY() + radius; y++) {
                    BlockPos pos = new BlockPos(x, y, z);
                    IBlockState blockState = mc.theWorld.getBlockState(pos);
                    Block blockAtPos = blockState.getBlock();
                    if (block.getUnlocalizedName().equals("tile.clayHardened")) {
                        int meta = blockAtPos.getMetaFromState(blockState);
                        if (meta != 14) continue;
                    }
                    if (espType.equals("Full"))
                        BlockRenderUtil.drawBlockFacesFilled(pos, faceRed, faceGreen, faceBlue, faceAlpha, partialTicks);
                    else if (espType.equals("Outline"))
                        BlockRenderUtil.drawBlockFacesOutline(pos, outlineRed, outlineGreen, outlineBlue, outlineAlpha, outlineWidth, partialTicks);
                    else if (espType.equals("FullOutline")) {
                        BlockRenderUtil.drawBlockFacesFilled(pos, faceRed, faceGreen, faceBlue, faceAlpha, partialTicks);
                        BlockRenderUtil.drawBlockFacesOutline(pos, outlineRed, outlineGreen, outlineBlue, outlineAlpha, outlineWidth, partialTicks);
                    }
                }
            }
        }
    }


}
