package com.munchymc.punishmentplugin.bukkit.database.actions.query.player;

import com.munchymc.punishmentplugin.common.database.DatabaseQuery;
import com.munchymc.punishmentplugin.common.database.wrappers.tables.users.UsersBuilder;
import com.munchymc.punishmentplugin.common.database.wrappers.tables.users.UsersTable;
import com.munchymc.punishmentplugin.exceptions.database.DatabaseRuntimeException;
import com.munchymc.punishmentplugin.common.database.Database;
import org.bukkit.plugin.Plugin;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class GetPlayerInfo extends DatabaseQuery<UsersTable> {
    private PlayerParameterData parameterData;

    private UUID playerUIDData = null;
    private String playerNameData = null;

    public GetPlayerInfo(Database database, UUID playerUID, Plugin plugin) {
        super(database, plugin);
        this.playerUIDData = playerUID;
        parameterData = new PlayerParameterData(true);
    }

    public GetPlayerInfo(Database database, String playerName, Plugin plugin) {
        super(database, plugin);
        this.playerNameData = playerName;
        parameterData = new PlayerParameterData(false);
    }

    public void getPlayerName(boolean playerName) {
        parameterData.setGetPlayerName(playerName);
    }

    public void getDateJoined(boolean dateJoined) {
        parameterData.setGetDateJoined(dateJoined);
    }

    public void getPermissions(boolean permissions) {
        parameterData.setGetPermissions(permissions);
    }

    public void getPlayerUID(boolean playerUID) {
        parameterData.setGetPlayerUID(playerUID);
    }

    @Override
    protected PreparedStatement prepare() {
        //TODO: Check something is being retrieved
        String query = parameterData.createQueryString();

        try {
            PreparedStatement base = getConnection().prepareStatement(query);

            if (parameterData.isUsingPlayerUID()) {
                base.setString(1, playerUIDData.toString());
            } else {
                base.setString(1, playerNameData);
            }

            return base;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseRuntimeException(e.getMessage(), e.getCause());
        }
    }

    @Override
    protected UsersTable createWrapper(ResultSet queryRes) {
        UsersBuilder usersBuilder;

        try {
            if (!queryRes.next()) {
                return null;
            }

            if (playerUIDData != null) {
                usersBuilder = new UsersBuilder(playerUIDData);
            } else {
                usersBuilder = new UsersBuilder(UUID.fromString(queryRes.getString("Player_UID")));
            }

            if (parameterData.isUsingPlayerName()) {
                usersBuilder.setPlayerName(queryRes.getString("Player_Name"));
            }

            if (parameterData.isGettingDateJoined()) {
                usersBuilder.setDateJoined(queryRes.getTimestamp("Date_Joined"));
            }

            if (parameterData.isGettingPermissions()) {
                usersBuilder.setPermissionCSV(queryRes.getString("Permissions"));
            }

            return usersBuilder.build();
        } catch (SQLException e) {
            throw new DatabaseRuntimeException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void close() {
        releaseConnection();
    }
}
//Started making the report command