package com.munchymc.punishmentplugin.bukkit.database.actions.query.player;

import com.munchymc.punishmentplugin.common.database.DatabaseQuery;
import com.munchymc.punishmentplugin.common.database.wrappers.tables.users.UsersBuilder;
import com.munchymc.punishmentplugin.common.database.wrappers.tables.users.UsersTable;
import com.munchymc.punishmentplugin.common.database.Database;
import org.bukkit.plugin.Plugin;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class PlayerPermissions extends DatabaseQuery<UsersTable> {
    private final UUID playerUid;

    public PlayerPermissions(Database database, UUID playerUid, Plugin plugin) {
        super(database, plugin);
        this.playerUid = playerUid;
    }

    @Override
    protected PreparedStatement prepare() {
        try{
            PreparedStatement base = getConnection().prepareStatement("SELECT Player_UID, Permissions from users where Player_UID = ?");
            base.setString(1, String.valueOf(playerUid));
            return base;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected UsersTable createWrapper(ResultSet queryRes) {
        try{
            UsersBuilder userBuilder = new UsersBuilder(playerUid);

            if (queryRes.next()){
                userBuilder.setPermissionCSV(queryRes.getString("Permissions"));
            }

            return userBuilder.build();
        } catch (SQLException r) {
            r.printStackTrace();
        }

        return null;
    }

    @Override
    public void close() {
        releaseConnection();
    }
}
