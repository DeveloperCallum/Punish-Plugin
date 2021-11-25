package com.munchymc.punishmentplugin.bukkit.database.actions.query.punishments;

import com.munchymc.punishmentplugin.bukkit.database.actions.query.action.ActionsQuery;
import com.munchymc.punishmentplugin.common.database.Database;
import com.munchymc.punishmentplugin.common.database.DatabaseQuery;
import com.munchymc.punishmentplugin.common.database.wrappers.tables.punishments.PunishTable;
import org.bukkit.plugin.Plugin;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class CheckPlayerPunishments extends DatabaseQuery<PunishTable> {
    private final UUID checkUID;
    private final Database database;
    private final Plugin plugin;

    public CheckPlayerPunishments(Database database, UUID checkUID1, Plugin plugin) {
        super(database, plugin);
        this.database = database;
        this.checkUID = checkUID1;
        this.plugin = plugin;
    }

    @Override
    protected PreparedStatement prepare() throws SQLException {
        ActionsQuery actionsQuery = new ActionsQuery(database, plugin);
        return null;
    }

    @Override
    protected PunishTable createWrapper(ResultSet queryRes) throws SQLException {
        return null;
    }
}
