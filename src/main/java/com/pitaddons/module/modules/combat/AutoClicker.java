package com.pitaddons.module.modules.combat;

import com.pitaddons.PitAddons;
import com.pitaddons.module.Category;
import com.pitaddons.module.Module;
import com.pitaddons.module.ModuleInfo;
import com.pitaddons.module.settings.Setting;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

@ModuleInfo(
        name = "AutoClicker",
        moveable = false,
        description = "Clicks automatically",
        category = Category.COMBAT
)
public class AutoClicker extends Module {

    private long nextClick;
    private float maxCps, minCps;
    private boolean needMouseDown, blockHit, needMouseDownRight, canMine, hasClicked;

    public AutoClicker() {
        setKey(new KeyBinding("Auto Clicker", Keyboard.KEY_NONE, "Pit Addons"));
        PitAddons.settingsManager.addSetting(new Setting("Min cps", this, 0.0f, 50.0f, 10, 0.1f));
        PitAddons.settingsManager.addSetting(new Setting("Max cps", this, 0.0f, 50.0f, 14, 0.1f));
        PitAddons.settingsManager.addSetting(new Setting("Require mouse down", this, true));
        PitAddons.settingsManager.addSetting(new Setting("BlockHit", this, true));
        PitAddons.settingsManager.addSetting(new Setting("Require Right click BlockHit", this, true));
        PitAddons.settingsManager.addSetting(new Setting("Allow mining", this, true));

        minCps = PitAddons.settingsManager.getSettingByName("Min cps", this).getCurrent();
        maxCps = PitAddons.settingsManager.getSettingByName("Max cps", this).getCurrent();
        needMouseDown = PitAddons.settingsManager.getSettingByName("Require mouse down", this).isEnabled();
        blockHit = PitAddons.settingsManager.getSettingByName("BlockHit", this).isEnabled();
        needMouseDownRight = PitAddons.settingsManager.getSettingByName("Require Right click BlockHit", this).isEnabled();
        canMine = PitAddons.settingsManager.getSettingByName("Allow mining", this).isEnabled();
        nextClick = calculateNext();
    }

    @SubscribeEvent
    public void onRenderTick(TickEvent.RenderTickEvent event) {
        if (mc.thePlayer == null || mc.theWorld == null) return;
        if (System.currentTimeMillis() < nextClick) return;
        hasClicked = false;

        nextClick = calculateNext();

        if (canMine && isHoveringSolid()) return;

        leftClick(needMouseDown);

        if (!blockHit || !hasClicked) return;
        rightClick(needMouseDownRight);
    }

    private boolean isHoveringSolid() {
        BlockPos pos = mc.objectMouseOver.getBlockPos();
        if (pos != null) {
            Block block = mc.theWorld.getBlockState(pos).getBlock();
            return block != Blocks.air && !(block instanceof BlockLiquid);
        }
        return false;
    }

    private void leftClick(boolean needMouseDown) {
        if (needMouseDown && !Mouse.isButtonDown(0)) return;
        KeyBinding.setKeyBindState(mc.gameSettings.keyBindAttack.getKeyCode(), false);
        KeyBinding.onTick(mc.gameSettings.keyBindAttack.getKeyCode());
        hasClicked = true;
    }

    private void rightClick(boolean needMouseDown) {
        if (needMouseDown && !Mouse.isButtonDown(1)) return;
        KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), false);
        KeyBinding.onTick(mc.gameSettings.keyBindUseItem.getKeyCode());
    }


    private long calculateNext() {
        long currentTime = System.currentTimeMillis();

        float difference = maxCps - minCps;

        float randomCps = (float) ((Math.random() * difference) + minCps);
        int clickTime = (int) (1000 / randomCps);

        return currentTime + clickTime;
    }

    @Override
    public void onSettingSave() {
        super.onSettingSave();
        nextClick = calculateNext();
        minCps = PitAddons.settingsManager.getSettingByName("Min cps", this).getCurrent();
        maxCps = PitAddons.settingsManager.getSettingByName("Max cps", this).getCurrent();
        needMouseDown = PitAddons.settingsManager.getSettingByName("Require mouse down", this).isEnabled();
        blockHit = PitAddons.settingsManager.getSettingByName("BlockHit", this).isEnabled();
        needMouseDownRight = PitAddons.settingsManager.getSettingByName("Require Right click BlockHit", this).isEnabled();
        canMine = PitAddons.settingsManager.getSettingByName("Allow mining", this).isEnabled();
    }
}
