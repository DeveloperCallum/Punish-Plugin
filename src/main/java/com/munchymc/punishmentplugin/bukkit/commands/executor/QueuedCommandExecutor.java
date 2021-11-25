package com.munchymc.punishmentplugin.bukkit.commands.executor;

import com.munchymc.punishmentplugin.common.commands.BukkitCommand;
import com.munchymc.punishmentplugin.exceptions.command.CommandRuntimeException;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class QueuedCommandExecutor extends CommandExecutor {
    private Plugin plugin;

    public QueuedCommandExecutor(Plugin plugin) {
        super(plugin);
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

        BukkitCommand cmd = getCommandList().get(args[0].toLowerCase());

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

        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try{
                new BukkitCommandData(cmd, argList, commandSender).getCommand().execute(commandSender, argList);
            }catch (CommandRuntimeException e){
                commandSender.sendMessage(e.getMessage());
            }
        });

        return true;
    }
}
