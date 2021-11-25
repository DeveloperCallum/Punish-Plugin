package com.munchymc.punishmentplugin.bukkit.commands.issue;

import com.munchymc.punishmentplugin.bukkit.commands.issue.view.SelectActionMenu;
import com.munchymc.punishmentplugin.bukkit.commands.issue.view.deprecated.CreatePunishmentGUI;
import com.munchymc.punishmentplugin.common.commands.BukkitCommand;
import com.munchymc.punishmentplugin.common.database.Database;
import com.munchymc.punishmentplugin.exceptions.command.CommandRuntimeException;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.UUID;

public class IssueCommand extends BukkitCommand {
    private Plugin plugin;
    private Database database;

    public IssueCommand(Database database, Plugin plugin) {
        super("issue");
        this.database = database;
        this.plugin = plugin;
    }

    @Override
    public String helpString() {
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append(ChatColor.BLACK + "----------------------------------------------------" + ChatColor.RESET);
        strBuilder.append("\nHelp Menu");
        strBuilder.append(ChatColor.GRAY + "\n- Arguments: PlayerName/UUID");
        strBuilder.append(ChatColor.BLACK + "----------------------------------------------------" + ChatColor.RESET);

        return strBuilder.toString();
    }

    @Override //UPDATE: Convert to a complete GUI menu!
    public void execute(CommandSender sender, List<String> args) {

        if (!(sender instanceof Player)) {
            throw new CommandRuntimeException("Console Cannot execute this command!");
        }

        Player player = (Player) sender;

        if (args.size() < 1) {
            throw new CommandRuntimeException("Missing argument: UUID");
        }

        UUID targetUID = null;

        Player onlineTarget = Bukkit.getPlayer(args.get(0));

        if (onlineTarget != null){
            targetUID = onlineTarget.getUniqueId();
        }else{
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args.get(0));
            if (offlinePlayer != null){
                targetUID = offlinePlayer.getUniqueId();
            }else{
                try {
                    targetUID = UUID.fromString(args.get(0));
                } catch (IllegalArgumentException e) {
                    player.sendMessage("Could not find that player!");
                }
            }
        }

        if (targetUID == null) {
            throw new CommandRuntimeException("Invalid argument: UUID");
        }

//        CreatePunishmentGUI selectMenu = new CreatePunishmentGUI(database, plugin, player, targetUID);
//        player.openInventory(selectMenu.create());

        SelectActionMenu selectActionMenu = new SelectActionMenu(player, database, plugin, targetUID);
        player.openInventory(selectActionMenu.getFirst().create());
    }
}
