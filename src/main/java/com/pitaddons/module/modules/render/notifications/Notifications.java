package com.pitaddons.module.modules.render.notifications;

import com.pitaddons.module.Category;
import com.pitaddons.module.Module;
import com.pitaddons.module.ModuleInfo;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

@ModuleInfo(
        name = "Notifications",
        moveable = false,
        description = "Sends relevant information in this Hud-Module",
        category = Category.RENDER
)
public class Notifications extends Module {

    private LinkedHashMap<Notification, Integer> notifications;
    private HashMap<Notification, Long> lastUpdated;

    private int x, y, startX;
    private int size, sizeY, startY;
    private FontRenderer fr;
    private LogoRenderer lr;

    public Notifications() {
        notifications = new LinkedHashMap<Notification, Integer>();
        lastUpdated = new HashMap<Notification, Long>();
        lr = new LogoRenderer();
        setKey(new KeyBinding("Toggles Notifications", Keyboard.KEY_NONE, "Pit Addons"));
        x = 2;
        y = 12;
        startX = -100;
        startY = y + sizeY;
        fr = mc.fontRendererObj;
    }

    public void addNotification(Notification notification) {
        notifications.put(notification, startX);
        lastUpdated.put(notification, (System.currentTimeMillis()));
        size = notifications.size();
        sizeY = 9 * size;
        startY = y + sizeY;
    }

    @SubscribeEvent
    public void onRenderText(RenderGameOverlayEvent.Text event) {
        if (mc.thePlayer == null) return;
        fr = mc.fontRendererObj;

        lr.render(fr);

        ScaledResolution sr = new ScaledResolution(mc);
        int scaleFactor = sr.getScaleFactor();

        int scissorX = x * scaleFactor;
        int scissorY = (mc.displayHeight - (y + sizeY) * scaleFactor);

        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        GL11.glScissor(scissorX, scissorY, 1000, 1000);
        renderNotifications();
        GL11.glDisable(GL11.GL_SCISSOR_TEST);

    }

    private void renderNotifications() {

        int currY = startY;
        Iterator<Notification> iterator = notifications.keySet().iterator();
        while (iterator.hasNext()) {
            Notification notification = iterator.next();
            if (isJoining(notification)) join(notification, 4);
            if (isLeaving(notification)) leave(notification, 4);
            if (shouldRemove(notification)) {
                iterator.remove();
                size = notifications.size();
                sizeY = 9 * size;
                startY = y + sizeY;
                currY -= 9;
                continue;
            }
            currY -= 9;

            fr.drawStringWithShadow(notification.getMessage().getFormattedText(), notifications.get(notification), currY, -1);
        }
    }

    private boolean isJoining(Notification notification) {
        long current = System.currentTimeMillis();
        long timeJoin = notification.getSendTime() + 1000;
        int currX = notifications.get(notification);

        return (currX < x && timeJoin >= current);
    }

    private boolean isLeaving(Notification notification) {
        long timeJoin = notification.getSendTime() + 200;
        long timeLeave = timeJoin + notification.getTime();
        long current = System.currentTimeMillis();

        return timeLeave <= current;
    }

    private boolean shouldRemove(Notification notification) {
        int messageWidth = fr.getStringWidth(notification.getMessage().getUnformattedText());
        int currX = notifications.get(notification);

        return (currX + messageWidth < 0);
    }

    private void join(Notification notification, int movement) {
        if (!hasTimePassed(notification, 20)) return;
        int currX = notifications.get(notification);
        ;
        if (currX + movement <= x)
            currX += movement;
        else currX = x;
        notifications.put(notification, currX);
    }

    private void leave(Notification notification, int movement) {
        if (!hasTimePassed(notification, 20)) return;
        int currX = notifications.get(notification);
        currX -= movement;
        notifications.put(notification, currX);
    }

    private boolean hasTimePassed(Notification notification, int time) {
        long lastUpdate = lastUpdated.get(notification);
        long current = System.currentTimeMillis();
        if (lastUpdate + time <= current) {
            lastUpdated.put(notification, current);
            return true;
        }
        return false;
    }

    @Override
    public boolean isEnabled() {
        return super.isEnabled();
    }
}
