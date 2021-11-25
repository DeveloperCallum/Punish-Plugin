package com.munchymc.punishmentplugin.bukkit;

import com.munchymc.punishmentplugin.bukkit.commands.executor.QueuedCommandExecutor;
import com.munchymc.punishmentplugin.bukkit.commands.history.ViewHistoryCommand;
import com.munchymc.punishmentplugin.bukkit.commands.issue.IssueCommand;
import com.munchymc.punishmentplugin.bukkit.commands.permissions.Permissions;
import com.munchymc.punishmentplugin.bukkit.commands.reports.ReportCommand;
import com.munchymc.punishmentplugin.bukkit.database.DatabaseProvider;
import com.munchymc.punishmentplugin.bukkit.events.player.PreLoginEvent;
import com.munchymc.punishmentplugin.common.FileHandler;
import com.munchymc.punishmentplugin.common.database.Database;
import com.munchymc.punishmentplugin.bukkit.events.player.OnPlayerJoin;
import com.munchymc.punishmentplugin.bukkit.events.player.OnPlayerQuit;
import com.munchymc.punishmentplugin.bukkit.permissions.PermControl;
import com.munchymc.punishmentplugin.common.permission.PermissionManager;
import com.munchymc.punishmentplugin.util.Logger;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public final class PunishMainClass extends JavaPlugin {
    private Database database;
    private QueuedCommandExecutor executor;
    private PermissionManager permControl;

    @Override
    public void onEnable() {
        permControl = new PermControl(this);

        try {
            Logger.printTitle("Database");
            database = new DatabaseProvider(50, getDriverUrl(), this);
            Logger.log("Done!");

            Logger.printTitle("Events");
            registerEvents(
                    new OnPlayerJoin(this, database, permControl),
                    new OnPlayerQuit(permControl),
                    new PreLoginEvent(database, this)
            );
            Logger.log("Done!");

            Logger.printTitle("Commands");
            this.executor = new QueuedCommandExecutor(this);

            //Register Commands!
            executor.registerCommands(
                    new Permissions(database, permControl, this),
                    new ReportCommand(this, database),
                    new IssueCommand(database, this),
                    new ViewHistoryCommand(database, this)
            );

            this.getCommand("punish").setExecutor(executor);
            Logger.log("Done!");

        } catch (Exception e) {
            Logger.log("Start Up failed!");

            e.printStackTrace();
            this.getServer().shutdown();
        }

        Logger.printTitle("End");
    }

    private String getDriverUrl() throws IOException {
        FileHandler fileHandler = new FileHandler(this.getDataFolder());

        String ip = fileHandler.getIP();
        String port = String.valueOf(fileHandler.getPort());
        String dbName = fileHandler.getDatabase();
        String user = fileHandler.getUser();
        String password = fileHandler.getPassword();

        return "jdbc:mysql://" + ip + ":" + port + "/" + dbName + "?characterEncoding=utf8&" + "user=" + user + "&password=" + password;
    }

    private void registerEvent(Listener event) {
        getServer().getPluginManager().registerEvents(event, this);
    }

    private void registerEvents(Listener... events) {
        for (Listener event : events) {
            registerEvent(event);
        }
    }
}