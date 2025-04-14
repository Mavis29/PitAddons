package com.pitaddons.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class EventListCommand extends CommandBase {

    private static final String EVENTS_URL = "https://raw.githubusercontent.com/BrookeAFK/brookeafk-api/refs/heads/main/events.js";

    @Override
    public String getCommandName() {
        return "eventlist";
    }

    @Override
    public String getCommandUsage(ICommandSender iCommandSender) {
        return "/eventlist";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
       
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }
}
