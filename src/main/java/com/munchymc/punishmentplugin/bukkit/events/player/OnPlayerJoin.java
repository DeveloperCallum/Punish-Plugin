package com.munchymc.punishmentplugin.bukkit.events.player;

import com.munchymc.punishmentplugin.bukkit.database.actions.command.player.AddPlayerCommand;
import com.munchymc.punishmentplugin.bukkit.database.actions.query.player.PlayerPermissions;
import com.munchymc.punishmentplugin.common.database.wrappers.tables.users.UsersTable;
import com.munchymc.punishmentplugin.common.database.Database;
import com.munchymc.punishmentplugin.common.permission.PermissionManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

public class OnPlayerJoin implements Listener {
    private final Plugin plugin;
    private final Database database;
    private final PermissionManager permControl;

    public OnPlayerJoin(Plugin plugin, Database database, PermissionManager permControl) {
        this.plugin = plugin;
        this.database = database;
        this.permControl = permControl;
    }

    //If the player can join.
    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                AddPlayerCommand addPlayerCommand = new AddPlayerCommand(database, player.getUniqueId(), player.getName());
                addPlayerCommand.executeCommand();

                PlayerPermissions pPerm = new PlayerPermissions(database, player.getUniqueId(), plugin);
                UsersTable usersTable = pPerm.executeQuery();

                String[] perms = usersTable.getPermissionCSV();

                if (perms != null) {
                    for (String s : perms) {
                        permControl.addPermToPlayer(player, s.trim());
                    }
                }

            } catch (Exception e) {
                Bukkit.getScheduler().runTask(plugin, () -> {
                    player.kickPlayer(ChatColor.RED + "Database Error!");
                });

                e.printStackTrace();
            }
        });
    }
}
