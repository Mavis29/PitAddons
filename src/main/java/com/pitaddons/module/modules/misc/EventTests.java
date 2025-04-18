package com.pitaddons.module.modules.misc;

import com.pitaddons.module.Category;
import com.pitaddons.module.Module;
import com.pitaddons.module.ModuleInfo;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import org.lwjgl.input.Keyboard;


@ModuleInfo(
        name = "Event Test",
        moveable = false,
        description = "Just testing events",
        category = Category.MISC
)
public class EventTests extends Module {

    public EventTests() {
        setKey(new KeyBinding("Event Test", Keyboard.KEY_N, "Pit Addons"));
    }

    @SubscribeEvent
    public void onRenderLivingSpecial(RenderLivingEvent.Specials.Pre event) {
        // Renders Name tags
        // Cancelable
    }

    @SubscribeEvent
    public void onRenderLiving(RenderLivingEvent.Pre event) {
        // Cancels all Player Rendering except shadow
        // Cancelable
    }

    @SubscribeEvent
    public void onPlayerLogIn(PlayerEvent.PlayerLoggedInEvent event) {
        // Only gets called in single player
        // Not cancelable
    }

    @SubscribeEvent
    public void onHighLightBlock(DrawBlockHighlightEvent event) {
        // Draws block outlines
        // Cancelable
    }

    @SubscribeEvent
    public void onRenderTextOverlay(RenderGameOverlayEvent.Text event) {
        // Draws custom HUD elements
        // Cancelable
    }

    @SubscribeEvent
    public void onRenderChatOverlay(RenderGameOverlayEvent.Chat event) {
        // Draws chat
        // Cancelable
    }

    @SubscribeEvent
    public void onBlockBreak(BlockEvent.BreakEvent event) {
        // Triggers on Block Break
        // Only single player
    }


}

/*
RenderLivingEvent.Specials.Pre // Renders Nametags
 */
