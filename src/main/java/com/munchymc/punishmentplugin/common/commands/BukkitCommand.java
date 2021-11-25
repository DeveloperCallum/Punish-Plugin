package com.munchymc.punishmentplugin.common.commands;

public abstract class BukkitCommand extends BaseCommand {
    public BukkitCommand(String commandName) {
        super(commandName);
    }

    public abstract String helpString();
}
