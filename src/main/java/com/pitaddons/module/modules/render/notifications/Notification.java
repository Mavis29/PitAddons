package com.pitaddons.module.modules.render.notifications;

import net.minecraft.util.IChatComponent;

public class Notification {

    private final IChatComponent message;
    private final Long sendTime;
    private final int time;

    public Notification(IChatComponent message, Long sendTime) {
        this.message = message;
        this.sendTime = sendTime;
        this.time = 2569;
    }

    public IChatComponent getMessage() {
        return message;
    }

    public Long getSendTime() {
        return sendTime;
    }

    public int getTime() {
        return time;
    }

}
