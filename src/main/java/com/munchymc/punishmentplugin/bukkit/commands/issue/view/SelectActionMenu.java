package com.munchymc.punishmentplugin.bukkit.commands.issue.view;

import com.munchymc.punishmentplugin.bukkit.commands.history.view.HistoryView;
import com.munchymc.punishmentplugin.bukkit.database.actions.command.punishments.CreatePunishmentCommand;
import com.munchymc.punishmentplugin.bukkit.database.actions.query.action.GetAction;
import com.munchymc.punishmentplugin.bukkit.events.player.MuteHandler;
import com.munchymc.punishmentplugin.common.database.Database;
import com.munchymc.punishmentplugin.common.database.wrappers.tables.actions.ActionsTable;
import com.munchymc.punishmentplugin.common.inventory.AbstractMenu;
import com.munchymc.punishmentplugin.common.inventory.AbstractPage;
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

public class SelectActionMenu extends AbstractMenu implements Listener {
    private Player player;
    private Database database;
    private Plugin plugin;
    private UUID target;
    public int index = 0;

    public SelectActionMenu(Player player, Database database, Plugin plugin, UUID target) {
        this.player = player;
        this.database = database;
        this.plugin = plugin;
        this.target = target;

        Bukkit.getPluginManager().registerEvents(this, plugin);
        SelectActionPage firstPage = new SelectActionPage(database, plugin);
        setFirst(firstPage);
    }

    @Override
    public AbstractPage getNext() {
        AbstractPage current = getCurrent();

        if (current.hasNext()) {
            index++;
            return current.getNext();
        }

        if (current instanceof SelectActionPage) {
            SelectActionPage sCurrent = (SelectActionPage) current;

            //Add 18 because that is the page size.
            SelectActionPage next = new SelectActionPage(database, plugin, sCurrent.getOffset() + 18);

            if (next == null) {
                return null;
            }

            index++;
            current.setNext(next);
            next.setLast(current);
            return next;
        }

        return null;
    }

    @Override
    public AbstractPage getLast() {
        AbstractPage current = getCurrent();

        if (current.hasLast()) {
            index--;
            return current.getLast();
        }

        return null;
    }

    @Override
    public AbstractPage getCurrent() {
        return getCurrentRec(getFirst(), index);
    }

    @EventHandler(ignoreCancelled = true)
    public void onInventoryClick(InventoryClickEvent event) {
        if (!event.getWhoClicked().getUniqueId().equals(player.getUniqueId()) || event.getCurrentItem() == null) {
            return;
        }

        if (event.getClickedInventory().getTitle().equals("Select Player Action")) {
            event.setCancelled(true);

            if (ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()).equals("Forwards")){
                Inventory inv = getNext().create();
                if (inv != null){
                    player.openInventory(inv);
                }

                return;
            }

            if (ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()).equals("Backwards")){
                Inventory inv = getLast().create();
                if (inv != null){
                    player.openInventory(inv);
                }

                return;
            }

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
