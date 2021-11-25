package com.munchymc.punishmentplugin.bukkit.commands.reports;

import com.munchymc.punishmentplugin.common.commands.BukkitCommand;
import com.munchymc.punishmentplugin.bukkit.commands.reports.view.ViewMenu;
import com.munchymc.punishmentplugin.common.database.Database;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class ReportCommand extends BukkitCommand {

    private final Plugin plugin;
    private final Database database;

    public ReportCommand(Plugin plugin, Database database) {
        super("report");
        this.plugin = plugin;
        this.database = database;
    }

    public ReportCommand(String commandName, Plugin plugin, Database database) {
        super(commandName);
        this.plugin = plugin;
        this.database = database;
    }

    @Override
    public String helpString() {
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append(ChatColor.BLACK + "----------------------------------------------------" + ChatColor.RESET);
        strBuilder.append("\nHelp Menu");
        strBuilder.append(ChatColor.GRAY + "\n- Create: This will create a new report");
        strBuilder.append(ChatColor.GRAY + "\n- View: This will allow you to view a unhandled report");
        strBuilder.append(ChatColor.BLACK + "----------------------------------------------------" + ChatColor.RESET);

        return strBuilder.toString();
    }

    @Override
    public void execute(CommandSender sender, List<String> args) {
        if (args.size() < 1) {
            return;
        }

        if (!(sender instanceof Player)){
            return;
        }

        Player player = (Player) sender;
        String subCommand = args.get(0);

        switch (subCommand.toLowerCase()) {
            case "create":
                executeSubCommand(new CreateReportCommand(plugin, database), sender, args);
                break;

            case "view": //TODO Implement Permissions Check!
                ViewMenu viewMenu = new ViewMenu(database, plugin);
                player.openInventory(viewMenu.create());
                break;
        }
    }
}