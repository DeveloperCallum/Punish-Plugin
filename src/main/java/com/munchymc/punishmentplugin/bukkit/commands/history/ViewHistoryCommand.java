package com.munchymc.punishmentplugin.bukkit.commands.history;

import com.munchymc.punishmentplugin.bukkit.commands.history.view.HistoryMenu;
import com.munchymc.punishmentplugin.common.commands.BukkitCommand;
import com.munchymc.punishmentplugin.common.database.Database;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.UUID;

public class ViewHistoryCommand extends BukkitCommand {

    private Database database;
    private Plugin plugin;

    public ViewHistoryCommand(Database database, Plugin plugin) {
        super("history");
        this.database = database;
        this.plugin = plugin;
    }

    @Override
    public String helpString() {
        return "View a users punishment history!";
    }

    @Override
    public void execute(CommandSender sender, List<String> args) {
        if (sender instanceof Player){
            Player player = (Player) sender;

            //FIXME: Load other players info!
            UUID targetUID;
            HistoryMenu historyMenu;

            Player targetMention = Bukkit.getPlayer(args.get(0));

            if (targetMention != null){
                historyMenu = new HistoryMenu(database, targetMention.getUniqueId(), player, plugin);
            }else{
                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args.get(0));
                if (offlinePlayer != null){
                    historyMenu = new HistoryMenu(database, offlinePlayer.getUniqueId(), player, plugin);
                }else{
                    historyMenu = new HistoryMenu(database, UUID.fromString(args.get(0)), player, plugin);
                }
            }

            Inventory invView = historyMenu.getCurrent().create();

            if (invView == null){
                player.sendMessage("Player has no punishment history!");
            }else{
                player.openInventory(invView);
            }
        }
    }
}
