package com.munchymc.punishmentplugin.bukkit.database.actions.command.permission;

import com.munchymc.punishmentplugin.common.database.DatabaseCommand;
import com.munchymc.punishmentplugin.bukkit.database.actions.query.player.PlayerPermissions;
import com.munchymc.punishmentplugin.common.database.wrappers.tables.users.UsersTable;
import com.munchymc.punishmentplugin.common.database.Database;
import org.bukkit.plugin.Plugin;

import java.util.UUID;

public class AddPermissionCommand extends DatabaseCommand {
    private Database database;
    private UUID targetUID;
    private String permStr;
    private Plugin plugin;

    public AddPermissionCommand(Database database, Plugin plugin, UUID targetUID, String permStr) {
        super(database);
        this.database = database;
        this.plugin = plugin;
        this.permStr = permStr;
        this.targetUID = targetUID;
    }

    @Override
    protected void execute() {
        try {
            UsersTable userTable = new PlayerPermissions(database, targetUID, plugin).executeQuery();
            String[] csvArr = userTable.getPermissionCSV();

            String csv = "";

            for (String s : csvArr){
                csv += (s + ",");
            }

            csv += permStr;

            String finalCsv = csv;
            makeStatement("UPDATE IGNORE munchymc_dev.users SET Permissions = ? where Player_UID = ?", preparedStatement -> {
                preparedStatement.setString(1, finalCsv);
                preparedStatement.setString(2, targetUID.toString());
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void unExecute() {
        try {
            UsersTable userTable = new PlayerPermissions(database, targetUID, plugin).executeQuery();
            String[] csvArr = userTable.getPermissionCSV();
            String csv = "";

            String permString = "";

            for (int i = 0; i < csvArr.length; i++) {
                String perm = csvArr[i];

                if (perm.equals(permStr)){
                    continue;
                }

                if (i >= csvArr.length -1){
                    permString += perm;
                    continue;
                }

                permString += (perm + ",");
            }

            csv += permStr;

            String finalCsv = csv;
            makeStatement("UPDATE IGNORE munchymc_dev.users SET Permissions = ? where Player_UID = ?", preparedStatement -> {
                preparedStatement.setString(1, finalCsv);
                preparedStatement.setString(2, targetUID.toString());
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        releaseConnection();
    }
}
