package com.munchymc.punishmentplugin.bukkit.commands.permissions;

import com.munchymc.punishmentplugin.bukkit.database.actions.command.permission.CreatePermissionCommand;
import com.munchymc.punishmentplugin.common.commands.BukkitSubCommand;
import com.munchymc.punishmentplugin.common.database.Database;
import com.munchymc.punishmentplugin.common.permission.PermissionManager;
import com.munchymc.punishmentplugin.util.Logger;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class RemovePermission extends BukkitSubCommand {
    PermissionManager permControl;
    Database database;


    public RemovePermission(PermissionManager permControl, Database database) {
        super("remove");
        this.permControl = permControl;
        this.database = database;
    }

    @Override
    public void execute(CommandSender sender, List<String> args) {
        if (!(sender instanceof Player)) {
            Logger.log("Console cannot use this command!");
            return;
        }

        Player player = (Player) sender;

        if (args.size() < 2) {
            player.sendMessage("Must supply arguments!");
            return;
        }

        String name = args.get(1);
        try{
            new CreatePermissionCommand(database, name).unExecuteCommand();
        }catch (RuntimeException e){
            player.sendMessage(ChatColor.RED + "Failed to remove permission!");
        }
    }

    @Override
    public String helpString() {
        return null;
    }
}

//Example Args: [Remove, Name]