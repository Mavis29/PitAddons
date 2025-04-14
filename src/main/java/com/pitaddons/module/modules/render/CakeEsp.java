package com.pitaddons.module.modules.render;

import com.pitaddons.PitAddons;
import com.pitaddons.config.PitAddonsConfig;
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
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

@ModuleInfo(
        name = "Cake ESP",
        moveable = false,
        description = "Highlights the red concrete blocks in a cake",
        category = Category.RENDER
)
public class CakeEsp extends Module {

    private final ArrayList<String> espTypes;
    private HashSet<BlockPos> positions;
    private LinkedList<BlockPos> queue;

    private String espType;
    private int radius;

    private int faceRed, faceGreen, faceBlue, outlineRed, outlineGreen, outlineBlue;
    private float faceAlpha, outlineAlpha, outlineWidth;

    public CakeEsp() {
        espTypes = new ArrayList<String>();
        positions = new HashSet<BlockPos>();
        queue = new LinkedList<BlockPos>();
        espTypes.add("Full");
        espTypes.add("Outline");
        espTypes.add("FullOutline");
        setKey(new KeyBinding("Toggles the Cake ESP", Keyboard.KEY_K, "Pit Addons"));
        PitAddons.settingsManager.addSetting(new Setting("Radius", this, 1.0f, 50.0f, PitAddonsConfig.getInstance().getCakeEspRadius(), 1.0f));
        PitAddons.settingsManager.addSetting(new Setting("Esp Type", this, espTypes, PitAddonsConfig.getInstance().getCakeEspType()));
        PitAddons.settingsManager.addSetting(new Setting("Face Red", this, 0.0f, 255.0f, PitAddonsConfig.getInstance().getCakeEspFaceRed(), 1.0f));
        PitAddons.settingsManager.addSetting(new Setting("Face Green", this, 0.0f, 255.0f, PitAddonsConfig.getInstance().getCakeEspFaceGreen(), 1.0f));
        PitAddons.settingsManager.addSetting(new Setting("Face Blue", this, 0.0f, 255.0f, PitAddonsConfig.getInstance().getCakeEspFaceBlue(), 1.0f));
        PitAddons.settingsManager.addSetting(new Setting("Face Alpha", this, 0.0f, 1.0f, (float) PitAddonsConfig.getInstance().getCakeEspFaceAlpha(), 0.01f));
        PitAddons.settingsManager.addSetting(new Setting("Outline Red", this, 0.0f, 255.0f, PitAddonsConfig.getInstance().getCakeEspOutlineRed(), 1.0f));
        PitAddons.settingsManager.addSetting(new Setting("Outline Green", this, 0.0f, 255.0f, PitAddonsConfig.getInstance().getCakeEspOutlineGreen(), 1.0f));
        PitAddons.settingsManager.addSetting(new Setting("Outline Blue", this, 0.0f, 255.0f, PitAddonsConfig.getInstance().getCakeEspOutlineBlue(), 1.0f));
        PitAddons.settingsManager.addSetting(new Setting("Outline Alpha", this, 0.0f, 1.0f, (float) PitAddonsConfig.getInstance().getCakeEspOutlineAlpha(), 0.01f));
        PitAddons.settingsManager.addSetting(new Setting("Outline Width", this, 0.0f, 10.0f, (float) PitAddonsConfig.getInstance().getCakeEspOutlineWidth(), 0.01f));
        radius = PitAddonsConfig.getInstance().getCakeEspRadius();
        espType = PitAddonsConfig.getInstance().getCakeEspType();
        faceRed = PitAddonsConfig.getInstance().getCakeEspFaceRed();
        faceGreen = PitAddonsConfig.getInstance().getCakeEspFaceGreen();
        faceBlue = PitAddonsConfig.getInstance().getCakeEspFaceBlue();
        faceAlpha = (float) PitAddonsConfig.getInstance().getCakeEspFaceAlpha();
        outlineRed = PitAddonsConfig.getInstance().getCakeEspOutlineRed();
        outlineGreen = PitAddonsConfig.getInstance().getCakeEspOutlineGreen();
        outlineBlue = PitAddonsConfig.getInstance().getCakeEspOutlineBlue();
        outlineAlpha = (float) PitAddonsConfig.getInstance().getCakeEspOutlineAlpha();
        outlineWidth = (float) PitAddonsConfig.getInstance().getCakeEspOutlineWidth();
    }

    @SubscribeEvent
    public void onRenderLast(RenderWorldLastEvent event) {
        if (mc.thePlayer == null || mc.theWorld == null) return;

        if (queue.isEmpty()) {
            fillQueue(mc.thePlayer.getPosition(), radius);
        }

        searchBlocks();

        cleanPositions();
        renderBlocks(event.partialTicks);
    }

    private void fillQueue(BlockPos center, int radius) {
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    BlockPos pos = center.add(x, y, z);
                    queue.add(pos);
                }
            }
        }
    }

    private void searchBlocks() {

        int blocksToCheck = radius * radius * radius / 4;

        for (int i = 0; i < blocksToCheck && !queue.isEmpty(); i++) {
            BlockPos pos = queue.poll();
            IBlockState state = mc.theWorld.getBlockState(pos);
            Block block = state.getBlock();
            if (block.getUnlocalizedName().equals("tile.clayHardenedStained") && block.getMetaFromState(state) == 14) {
                positions.add(pos);
            }
        }
    }

    private void renderBlocks(float partialTicks) {
        if (espType.equals("Full"))
            for (BlockPos pos : positions)
                BlockRenderUtil.drawBlockFacesFilled(pos, faceRed, faceGreen, faceBlue, faceAlpha, partialTicks);
        else if (espType.equals("Outline"))
            for (BlockPos pos : positions)
                BlockRenderUtil.drawBlockFacesOutline(pos, outlineRed, outlineGreen, outlineBlue, outlineAlpha, outlineWidth, partialTicks);
        else if (espType.equals("FullOutline")) {
            for (BlockPos pos : positions) {
                BlockRenderUtil.drawBlockFacesFilled(pos, faceRed, faceGreen, faceBlue, faceAlpha, partialTicks);
                BlockRenderUtil.drawBlockFacesOutline(pos, outlineRed, outlineGreen, outlineBlue, outlineAlpha, outlineWidth, partialTicks);
            }
        }
    }

    private void cleanPositions() {
        Iterator<BlockPos> iterator = positions.iterator();
        while (iterator.hasNext()) {
            BlockPos pos = iterator.next();
            IBlockState state = mc.theWorld.getBlockState(pos);
            Block block = state.getBlock();
            if (!block.getUnlocalizedName().equals("tile.clayHardenedStained") || block.getMetaFromState(state) != 14 || !isWithinRadius(mc.thePlayer.getPosition(), pos)) {
                iterator.remove();
            }
        }
    }

    private boolean isWithinRadius(BlockPos playerPos, BlockPos blockPos) {
        int minX = playerPos.getX() - radius;
        int maxX = playerPos.getX() + radius;
        int blockX = blockPos.getX();
        if (blockX < minX || blockX > maxX) return false;

        int minY = playerPos.getY() - radius;
        int maxY = playerPos.getY() + radius;
        int blockY = blockPos.getY();
        if (blockY < minY || blockY > maxY) return false;

        int minZ = playerPos.getZ() - radius;
        int maxZ = playerPos.getZ() + radius;
        int blockZ = blockPos.getZ();
        if (blockZ < minZ || blockZ > maxZ) return false;

        return true;
    }

    @Override
    public void onSettingSave() {

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

        PitAddonsConfig.getInstance().setCakeEspRadius(radius);
        PitAddonsConfig.getInstance().setCakeEspType(espType);
        PitAddonsConfig.getInstance().setCakeEspFaceRed(faceRed);
        PitAddonsConfig.getInstance().setCakeEspFaceGreen(faceGreen);
        PitAddonsConfig.getInstance().setCakeEspFaceBlue(faceBlue);
        PitAddonsConfig.getInstance().setCakeEspFaceAlpha(faceAlpha);

        PitAddonsConfig.getInstance().setCakeEspOutlineRed(outlineRed);
        PitAddonsConfig.getInstance().setCakeEspOutlineGreen(outlineGreen);
        PitAddonsConfig.getInstance().setCakeEspOutlineBlue(outlineBlue);
        PitAddonsConfig.getInstance().setCakeEspOutlineAlpha(outlineAlpha);
        PitAddonsConfig.getInstance().setCakeEspOutlineWidth(outlineWidth);

        super.onSettingSave();
    }
}