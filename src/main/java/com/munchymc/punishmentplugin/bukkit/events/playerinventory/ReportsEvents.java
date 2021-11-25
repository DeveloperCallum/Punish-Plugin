package com.munchymc.punishmentplugin.bukkit.events.playerinventory;

import com.munchymc.punishmentplugin.bukkit.database.actions.command.report.ReportCreate;
import com.munchymc.punishmentplugin.common.database.Database;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.util.UUID;

public class ReportsEvents implements Listener {
    private final Database database;
    private final Player player;
    private final UUID targetUID;

    public ReportsEvents(Database database, UUID targetUID, Player player) {
        this.database = database;
        this.targetUID = targetUID;
        this.player = player;
    }

    @EventHandler(ignoreCancelled = true)
    public void onInventoryClick(InventoryClickEvent event) {
        if (player.getUniqueId() != event.getWhoClicked().getUniqueId()) {
            return;
        }

        if (event.getClickedInventory().getTitle().equals("Reports Menu: Select Reason")) {
            String reason = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());

            ReportCreate reportCreate = new ReportCreate(database, targetUID, player.getUniqueId(), reason);
            reportCreate.executeCommand();

            player.sendMessage(ChatColor.GREEN + "Your report has been recorded!");

            InventoryClickEvent.getHandlerList().unregister(this);
            System.out.println("Event Unregistered -> InventoryClickEvent \\ ReportsEvents");

            event.setCancelled(true);
            player.closeInventory();
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onInventoryClose(InventoryCloseEvent event) {
        if (player.getUniqueId() != event.getPlayer().getUniqueId()) {
            return;
        }

        if (event.getInventory().getTitle().equals("Reports Menu: Select Reason")) {
            InventoryClickEvent.getHandlerList().unregister(this);
            System.out.println("Event Unregistered -> InventoryClickEvent \\ ReportsEvent");
            InventoryCloseEvent.getHandlerList().unregister(this);
            System.out.println("Event Unregistered -> InventoryCloseEvent \\ ReportsEvent");
        }
    }
}

//UPDATE: Move this inside the GUI itself.