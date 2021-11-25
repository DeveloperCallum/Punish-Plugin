package com.munchymc.punishmentplugin.common.database.wrappers.tables.users;

import java.sql.Timestamp;
import java.util.UUID;

public class UsersBuilder {
    private UUID playerUid;
    private String playerName;
    private Timestamp dateJoined;
    private String[] permissionCSV;

    public UsersBuilder(UUID playerUid) {
        this.playerUid = playerUid;
    }

    public UsersBuilder setPlayerName(String playerName) {
        this.playerName = playerName;
        return this;
    }

    public UsersBuilder setDateJoined(Timestamp dateJoined) {
        this.dateJoined = dateJoined;
        return this;
    }

    public void setPermissionCSV(String permissionCSV) {
        if (permissionCSV == null){
            return;
        }

        this.permissionCSV = permissionCSV.trim().split(",");
    }

    public UsersTable build(){
        UsersTable usersTable = new UsersTable(playerUid);
        usersTable.dateJoined = dateJoined;
        usersTable.playerName = playerName;
        usersTable.permissionCSV = permissionCSV;

        return usersTable;
    }
}
