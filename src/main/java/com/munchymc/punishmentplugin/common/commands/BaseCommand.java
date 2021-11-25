package com.munchymc.punishmentplugin.common.commands;

import org.bukkit.command.CommandSender;

import java.util.List;

public abstract class BaseCommand {
    private final String commandName;

    public BaseCommand(String commandName) {
        this.commandName = commandName.toLowerCase();
    }

    public String getName() {
        return commandName;
    }

    public abstract void execute(CommandSender sender, List<String> args);

    public void executeSubCommand(BukkitSubCommand cmd, CommandSender sender, List<String> args){
        args.remove(0);
        cmd.execute(sender, args);
    }
}