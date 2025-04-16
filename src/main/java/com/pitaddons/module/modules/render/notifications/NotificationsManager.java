package com.pitaddons.module.modules.render.notifications;

import com.pitaddons.PitAddons;

public class NotificationsManager {

    public void addNotification(Notification notification) {
        if (PitAddons.moduleManager.getEnabledModuleByName("Notifications") == null) return;
        Notifications notifications = (Notifications) PitAddons.moduleManager.
                getEnabledModuleByName("Notifications");
        notifications.addNotification(notification);
    }
}
