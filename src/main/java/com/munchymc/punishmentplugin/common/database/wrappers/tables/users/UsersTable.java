package com.munchymc.punishmentplugin.common.database.wrappers.tables.users;

import com.munchymc.punishmentplugin.common.database.wrappers.Wrapper;

import java.sql.Timestamp;
import java.util.UUID;

public class UsersTable implements Wrapper {
    protected UUID playerUid;
    protected String playerName;
    protected Timestamp dateJoined;
    protected String[] permissionCSV;

    protected UsersTable(UUID playerUid) {
        this.playerUid = playerUid;
    }

    public UUID getPlayerUid() {
        return playerUid;
    }

    public String getPlayerName() {
        return playerName;
    }

    public Timestamp getDateJoined() {
        return dateJoined;
    }

    public String[] getPermissionCSV() {
        return permissionCSV;
    }
}
