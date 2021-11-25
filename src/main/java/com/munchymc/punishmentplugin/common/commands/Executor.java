package com.munchymc.punishmentplugin.common.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.jetbrains.annotations.TestOnly;

import java.util.HashMap;
import java.util.Map;

public interface Executor extends CommandExecutor {
    void registerCommand(BukkitCommand cmd);

    void registerCommands(BukkitCommand... cmdArr);
}
