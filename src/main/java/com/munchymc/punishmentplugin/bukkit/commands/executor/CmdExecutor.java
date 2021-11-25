package com.munchymc.punishmentplugin.bukkit.commands.executor;

import com.munchymc.punishmentplugin.common.commands.BukkitCommand;
import org.bukkit.command.CommandExecutor;

public interface CmdExecutor extends CommandExecutor {
    public void registerCommand(BukkitCommand cmd);

    public void registerCommands(BukkitCommand... cmdArr);
}
