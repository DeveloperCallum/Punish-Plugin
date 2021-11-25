package com.munchymc.punishmentplugin.bukkit.commands.executor;

import com.munchymc.punishmentplugin.common.commands.BukkitCommand;
import com.munchymc.punishmentplugin.common.commands.Executor;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class CommandExecutor implements Executor {
    private Plugin plugin;
    private final Map<String, BukkitCommand> commandList = new HashMap<>();

    public CommandExecutor(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (args.length < 1) {
            //Basic Usage Information
            if (commandSender instanceof Player) {
                Player player = (Player) commandSender;

                player.sendMessage("Usage Information:\n" + "Punish <Command> Arguments/SubCommands\n");
            }
            return true;
        }

        BukkitCommand cmd = commandList.get(args[0].toLowerCase());

        if (cmd == null) {
            return true;
        }

        LinkedList<String> argList = new LinkedList<>();

        for (int i = 0; i < args.length; i++) {
            if (i == 0) {
                continue;
            }

            argList.add(args[i]);
        }

        try {
            cmd.execute(commandSender, argList);
        } catch (RuntimeException e) {
            commandSender.sendMessage(ChatColor.RED + "\nError Executing Command!\n\n\n" + e.getMessage());
            commandSender.sendMessage("");
            throw e;
        }

        return true;
    }

    private Plugin getPlugin() {
        return plugin;
    }

    public void registerCommand(BukkitCommand cmd) {
        commandList.putIfAbsent(cmd.getName().toLowerCase(), cmd);
    }

    public void registerCommands(BukkitCommand... cmdArr) {
        for (BukkitCommand cmd : cmdArr) {
            registerCommand(cmd);
        }
    }

    protected Map<String, BukkitCommand> getCommandList() {
        return commandList;
    }
}
