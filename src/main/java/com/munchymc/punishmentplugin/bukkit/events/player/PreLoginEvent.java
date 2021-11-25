package com.munchymc.punishmentplugin.bukkit.events.player;

import com.munchymc.punishmentplugin.bukkit.database.actions.query.punish.BannedPlayerQuery;
import com.munchymc.punishmentplugin.bukkit.database.actions.query.punish.CheckIfPermBanned;
import com.munchymc.punishmentplugin.common.database.Database;
import com.munchymc.punishmentplugin.common.database.wrappers.tables.punishments.PunishTable;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.plugin.Plugin;

import java.util.Date;

public class PreLoginEvent implements Listener {
    private Database datebase;
    private Plugin plugin;

    public PreLoginEvent(Database database, Plugin plugin) {
        this.datebase = database;
        this.plugin = plugin;
    }

    @EventHandler(ignoreCancelled = true)
    public void onAsyncPlayerPreLogin(AsyncPlayerPreLoginEvent event) {
        //UPDATE: Check if player is banned
        CheckIfPermBanned check = new CheckIfPermBanned(datebase, event.getUniqueId(), plugin);
        PunishTable data = check.executeQuery();

        if (data == null){
            BannedPlayerQuery bannedPlayerQuery = new BannedPlayerQuery(datebase, event.getUniqueId(), plugin);
            data = bannedPlayerQuery.executeQuery();

            if (data == null) {
                event.allow();
                return;
            }
        }

        StringBuilder message = new StringBuilder();

        if (data.getExpire() != null) {
            if (new Date().before(data.getExpire())) {
                message.append(ChatColor.RED).append(ChatColor.BOLD).append("You have been temporarily banned for ").append(ChatColor.BOLD).append(data.getAction().getDisplayName()).append("\n");
                message.append("\n").append(ChatColor.RED).append("Banned by\n").append(ChatColor.GRAY).append(data.getIssuer().getPlayerName());
                message.append("\n\n").append(ChatColor.RED).append("Banned on (YYYY-MM-DD)\n").append(ChatColor.GRAY).append(data.getDateIssued().toString());
                message.append("\n\n").append(ChatColor.RED).append("Expires on (YYYY-MM-DD)\n").append(ChatColor.GRAY).append(data.getExpire().toString());
                message.append("\n\n").append(ChatColor.RED).append("Reason\n").append(ChatColor.GRAY).append(data.getReason());

                event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, message.toString());
            }
        } else {
            message.append(ChatColor.RED).append(ChatColor.BOLD).append("You have been permanently banned for ").append(ChatColor.BOLD).append(data.getAction().getDisplayName()).append("\n");
            message.append("\n").append(ChatColor.RED).append("Banned by\n").append(ChatColor.GRAY).append(data.getIssuer().getPlayerName());
            message.append("\n\n").append(ChatColor.RED).append("Banned on (YYYY-MM-DD)\n").append(ChatColor.GRAY).append(data.getDateIssued().toString());
            message.append("\n\n").append(ChatColor.RED).append("Expires: (YY/MM/DD)\n ").append(ChatColor.GRAY).append("Never");
            message.append("\n\n").append(ChatColor.RED).append("Reason\n").append(ChatColor.GRAY).append(data.getReason());

            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, message.toString());
        }
    }
}
