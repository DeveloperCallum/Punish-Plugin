package com.munchymc.punishmentplugin.bukkit.commands.reports;

import com.munchymc.punishmentplugin.common.commands.BukkitSubCommand;
import com.munchymc.punishmentplugin.bukkit.commands.reports.view.ReportMenu;
import com.munchymc.punishmentplugin.bukkit.database.actions.query.player.GetPlayerInfo;
import com.munchymc.punishmentplugin.common.database.wrappers.tables.users.UsersTable;
import com.munchymc.punishmentplugin.common.database.Database;;
import com.munchymc.punishmentplugin.bukkit.events.playerinventory.ReportsEvents;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.UUID;

public class CreateReportCommand extends BukkitSubCommand {
    private final Database database;
    private final Plugin plugin;

    public CreateReportCommand(Plugin plugin, Database database) {
        super("create");
        this.database = database;
        this.plugin = plugin;
    }

    @Override
    public String helpString() {
        return null;
    }

    @Override
    public void execute(CommandSender sender, List<String> args) {
        if (!(sender instanceof Player)) {
            System.out.println("Only players can execute this command!");
            return;
        }

        Player player = (Player) sender;

        if (args.size() < 1) {
            player.sendMessage("You must specify Player Name");
            return;
        }

        String targetPlayerStr = args.get(0);

        //Database Query!
        GetPlayerInfo getPlayerInfo;

        try {
            UUID targetUID = UUID.fromString(targetPlayerStr);
            getPlayerInfo = new GetPlayerInfo(database, targetUID, plugin);
        } catch (IllegalArgumentException e) {
            getPlayerInfo = new GetPlayerInfo(database, targetPlayerStr, plugin);
            getPlayerInfo.getPlayerUID(true);
        }
        
        getPlayerInfo.getPlayerName(true);

        UsersTable targetData = getPlayerInfo.executeQuery();

        if (targetData.equals(null)) { //False IntelliJ Warning!
            player.sendMessage("That player could not be found!");
            return;
        }

        if (targetData.getPlayerUid().equals(player.getUniqueId())){
            player.sendMessage("You cannot report yourself!");
            return;
        }

        plugin.getServer().getPluginManager().registerEvents(new ReportsEvents(database,targetData.getPlayerUid(), player), plugin);

        //TODO: Add to menu manager!
        player.openInventory(new ReportMenu(database, plugin).getPageInventory());

        System.out.println("Reporting: " + targetPlayerStr);
    }

    //args: [Player, Reason]
}
