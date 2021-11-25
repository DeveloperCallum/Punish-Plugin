package com.munchymc.punishmentplugin.bukkit.commands.permissions;

import com.munchymc.punishmentplugin.common.commands.BukkitSubCommand;
import com.munchymc.punishmentplugin.bukkit.database.actions.command.permission.AddPermissionCommand;
import com.munchymc.punishmentplugin.common.database.Database;
import com.munchymc.punishmentplugin.common.permission.PermissionManager;
import com.munchymc.punishmentplugin.util.Logger;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.UUID;

public class AddPermission extends BukkitSubCommand {
    PermissionManager permControl;
    Database database;
    private Plugin plugin;

    public AddPermission(PermissionManager permControl, Database database, Plugin plugin) {
        super("add");
        this.permControl = permControl;
        this.database = database;
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, List<String> args) {
        if (!(sender instanceof Player)){
            Logger.log("Console cannot use this command!");
            return;
        }

        Player playerSender = (Player) sender;

        if (args == null){
            playerSender.sendMessage("Args must contain arguments.");
            return;
        }

        if(args.size() < 1){
            playerSender.sendMessage("Args must contain 1 argument.");
            return;
        }

        if (args.get(0).toLowerCase().equals("help")){
            playerSender.sendMessage(helpString());
            return;
        }

        String identity = args.get(1);

        Player target = Bukkit.getPlayer(identity);
        if (target == null){
            target = Bukkit.getPlayer(UUID.fromString(identity));

            if (target == null){
                playerSender.sendMessage("Target player could not be found");
                return;
            }
        }

        if (target.getUniqueId().equals(playerSender.getUniqueId())){
            playerSender.sendMessage("You cannot assign permission to yourself!");
            return;
        }

        permControl.addPermToPlayer(target, args.get(0));
        new AddPermissionCommand(database, plugin, target.getUniqueId(), args.get(0));
    }
    //Example Args: [Perm, Playername/UUID

    @Override
    public String helpString() {
        return null;
    }
}
