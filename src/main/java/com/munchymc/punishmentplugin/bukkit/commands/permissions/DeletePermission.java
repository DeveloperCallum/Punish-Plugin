package com.munchymc.punishmentplugin.bukkit.commands.permissions;

import com.munchymc.punishmentplugin.common.commands.BukkitSubCommand;
import com.munchymc.punishmentplugin.bukkit.database.actions.command.permission.CreatePermissionCommand;
import com.munchymc.punishmentplugin.common.database.Database;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class DeletePermission extends BukkitSubCommand {
    private final Database database;

    public DeletePermission(Database database) {
        super("delete");
        this.database = database;
    }

    @Override
    public void execute(CommandSender sender, List<String> args) {
        if (!(sender instanceof Player)){
            System.out.println("Command can only be used by a player");
            return;
        }

        Player player = (Player) sender;

        if (args == null){
            player.sendMessage("Args must contain arguments.");
            return;
        }

        if(args.size() < 1){
            player.sendMessage("Args must contain 1 argument.");
            return;
        }

        if (args.get(0).toLowerCase().equals("help")){
            player.sendMessage(helpString());
            return;
        }

        String name = args.get(0);
        CreatePermissionCommand permCmd = new CreatePermissionCommand(database, name);
        permCmd.unExecuteCommand();
        player.sendMessage(ChatColor.GREEN + "Permission was deleted!");

    }

    @Override
    public String helpString() {
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append(ChatColor.BLACK + "----------------------------------------------------" + ChatColor.RESET);
        strBuilder.append("\nHelp Menu");
        strBuilder.append("\nArguments:");
        strBuilder.append(ChatColor.GRAY + "\n- Name: Remove a permission with this name");
        strBuilder.append(ChatColor.BLACK + "----------------------------------------------------" + ChatColor.RESET);

        return strBuilder.toString();
    }
}
