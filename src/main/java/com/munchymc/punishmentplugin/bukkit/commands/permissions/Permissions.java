package com.munchymc.punishmentplugin.bukkit.commands.permissions;

import com.munchymc.punishmentplugin.common.commands.BukkitCommand;
import com.munchymc.punishmentplugin.common.database.Database;
import com.munchymc.punishmentplugin.common.permission.PermissionManager;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class Permissions extends BukkitCommand {
    private final Database database;
    private final PermissionManager permControl;
    private Plugin plugin;

    public Permissions(Database database, PermissionManager permControl, Plugin plugin) {
        super("Permission");
        this.database = database;
        this.permControl = permControl;
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, List<String> args) {
        if (args.size() < 1) {
            return;
        }

        String subCommand = args.get(0);

        switch (subCommand.toLowerCase()) {
            case "create":
                executeSubCommand(new CreatePermission(database), sender, args);
                break;

            case "delete":
                executeSubCommand(new DeletePermission(database), sender, args);
                break;

            case "add":
                executeSubCommand(new AddPermission(permControl, database, plugin), sender, args);
                break;

            case "remove":
                executeSubCommand(new RemovePermission(permControl, database), sender, args);
                break;

            case "help":
                sender.sendMessage(helpString());
                break;
        }
    }

    @Override
    public String helpString() {
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append(ChatColor.BLACK + "----------------------------------------------------" + ChatColor.RESET);
        strBuilder.append("\nHelp Menu");
        strBuilder.append(ChatColor.GRAY + "\n- Create: This will create a permission");
        strBuilder.append(ChatColor.GRAY + "\n- Delete: This will delete a permission");
        strBuilder.append(ChatColor.GRAY + "\n- Remove: This will remove a permission from a user");
        strBuilder.append(ChatColor.GRAY + "\n- Add: This will add a permission to a user");
        strBuilder.append(ChatColor.BLACK + "----------------------------------------------------" + ChatColor.RESET);

        return strBuilder.toString();
    }
}
