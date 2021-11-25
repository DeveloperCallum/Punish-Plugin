package com.munchymc.punishmentplugin.bukkit.commands.issue.view.deprecated;

import com.munchymc.punishmentplugin.bukkit.database.actions.command.punishments.CreatePunishmentCommand;
import com.munchymc.punishmentplugin.bukkit.database.actions.query.action.GetAction;
import com.munchymc.punishmentplugin.bukkit.events.player.MuteHandler;
import com.munchymc.punishmentplugin.common.database.Database;
import com.munchymc.punishmentplugin.common.database.wrappers.tables.actions.ActionsTable;
import com.munchymc.punishmentplugin.common.inventory.deprecated.PlainMenu;
import com.munchymc.punishmentplugin.exceptions.command.CommandRuntimeException;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

public class CreatePunishmentGUI extends PlainMenu implements Listener {
//    private static boolean registered = false;
    private Database database;
    private Plugin plugin;
    private Player player;
    private UUID target;

    public CreatePunishmentGUI(Database database, Plugin plugin, Player player, UUID target) {
        this.database = database;
        this.plugin = plugin;
        this.player = player;
        this.target = target;

        if (player.getUniqueId().equals(target)) {
            player.sendMessage(ChatColor.RED + "You cannot punish yourself");
            throw new CommandRuntimeException("User cannot execute command on self!");
        }

        if (!player.hasPermission("punish.func.create")) {
            throw new CommandRuntimeException("User cannot execute command!");
        }
    }

    @Override
    protected Inventory createProcess() {
//        synchronized (CreatePunishmentGUI.class) {
//            if (!registered) {
                Bukkit.getPluginManager().registerEvents(this, plugin);
//                registered = true;
//            }
//        }

        SelectPage selectPage = new SelectPage(database, plugin);
        addPage(selectPage);

        return selectPage.create();
    }

    @EventHandler(ignoreCancelled = true)
    public void onInventoryClick(InventoryClickEvent event) {
        if (!event.getWhoClicked().getUniqueId().equals(player.getUniqueId()) || event.getCurrentItem() == null) {
            return;
        }

        if (event.getClickedInventory().getTitle().equals(getPage(0).getTitle())) {
            event.setCancelled(true);

            //Run the command with the default settings.
//            ActionsQuery getData = new ActionsQuery(database, plugin);
//
//            ActionParameterData actionParameterData = getData.getParameterData();
//            actionParameterData.getSelectData().selectRespondingAction(true);
//            actionParameterData.getSelectData().selectUsagePermission(true);
//            actionParameterData.getWhereData().setName(event.getCurrentItem().getItemMeta().getDisplayName());
//
//            List<ActionsTable> actionsTables = getData.executeQuery();
//            ActionsTable actionType = actionsTables.get(0);

            GetAction actionQuery = new GetAction(database, plugin, event.getCurrentItem().getItemMeta().getDisplayName());
            ActionsTable actionType = actionQuery.executeQuery();

            if (!player.hasPermission("punish." + actionType.getPermission())) {
                player.closeInventory();
                player.sendMessage(ChatColor.RED + "You cannot issue this punishment!");
                player.closeInventory();
                return;
            }

            String reason = "Some reason here!";

            CreatePunishmentCommand action = new CreatePunishmentCommand(database, target, player.getUniqueId(), actionType.getName(), reason);
            action.executeCommand();
            Player targetPlayer = Bukkit.getPlayer(target);

            switch (actionType.getRespondingAction().toLowerCase()) {
                case "ban":
                case "kick":

                    if (targetPlayer != null) {
                        targetPlayer.kickPlayer(reason);
                    }

                    break;

                case "mute":
                    String[] timeSplit = actionType.getDefaultTime().split(":");

                    if (timeSplit.length != 3){
                        throw new CommandRuntimeException("Got incorrect time format from database.");
                    }

                    int hr = Integer.parseInt(timeSplit[0]);
                    int min = Integer.parseInt(timeSplit[1]);
                    int sec = Integer.parseInt(timeSplit[2]);

                    Date now = new Date();
                    Timestamp timestamp = new Timestamp(now.getYear(), now.getMonth(), now.getDay(), hr, min, sec, 0);

                    MuteHandler.addMute(target, timestamp);
                    break;

                case "warn":
                    if (targetPlayer != null) {
                        targetPlayer.sendMessage(ChatColor.GOLD + "You have been warned!");
                        targetPlayer.sendMessage(ChatColor.GOLD + "Reason: " + reason);
                    }
                    break;
            }

            player.sendMessage("Punishment issued!");
        }
    }
}
