package com.munchymc.punishmentplugin.bukkit.commands.executor;

import com.munchymc.punishmentplugin.common.commands.BukkitCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

public class BukkitCommandData {
    private BukkitCommand command;
    private List<String> args;
    private CommandSender commandSender;

    public BukkitCommandData(BukkitCommand command, List<String> args, CommandSender commandSender) {
        this.command = command;
        this.args = args;
        this.commandSender = commandSender;
    }

    public BukkitCommand getCommand() {
        return command;
    }

    public List<String> getArgs() {
        return args;
    }

    public CommandSender getCommandSender() {
        return commandSender;
    }
}
