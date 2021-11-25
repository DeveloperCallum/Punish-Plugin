package com.munchymc.punishmentplugin.bukkit.commands.permissions;

import com.munchymc.punishmentplugin.common.commands.BukkitSubCommand;
import com.munchymc.punishmentplugin.bukkit.database.actions.command.permission.CreatePermissionCommand;
import com.munchymc.punishmentplugin.common.database.Database;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class CreatePermission extends BukkitSubCommand {
    private final Database database;
    public CreatePermission(Database database) {
        super("create");
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

        if (args.get(0).toLowerCase().equals("help")){
            player.sendMessage(helpString());
            return;
        }

        if(args.size() < 4){
            player.sendMessage("Args must contain 4 arguments.");
            return;
        }

        String name = args.get(0);
        String action = args.get(1).toLowerCase();

        if (!action.equals("ban") && !action.equals("warn") && !action.equals("kick")){
            player.sendMessage("Action argument incorrect, refer to '/punish permission create help'");
        }

        String duration = args.get(2);
        String usagePermStr = args.get(3);

        CreatePermissionCommand createPerm = new CreatePermissionCommand(database, name, action, duration, usagePermStr);
        createPerm.executeCommand();
        player.sendMessage(ChatColor.GREEN + "Permission Created!");
    }

    @Override
    public String helpString() {
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append(ChatColor.BLACK + "----------------------------------------------------" + ChatColor.RESET);
        strBuilder.append("\nHelp Menu");
        strBuilder.append("\nArguments:");
        strBuilder.append(ChatColor.GRAY + "\n- Name: Create a permission with this name");
        strBuilder.append(ChatColor.GRAY + "\n- Action: Give the permission this action (Ban, Kick, Warn)");
        strBuilder.append(ChatColor.GRAY + "\n- Duration: Default length (hh:mm:ss)");
        strBuilder.append(ChatColor.GRAY + "\n- Usage Permission: The permission created that allows users to punish players");
        strBuilder.append(ChatColor.BLACK + "----------------------------------------------------" + ChatColor.RESET);

        return strBuilder.toString();
    }
}

//Example Args: [Create, Name, Action, Duration, Usage Permission
