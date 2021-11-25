package com.munchymc.punishmentplugin.bukkit.database.actions.query.action;

import com.munchymc.punishmentplugin.common.database.Database;
import org.bukkit.plugin.Plugin;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GetAllActionsOffset extends GetAllActions{
    private final int offset;

    public GetAllActionsOffset(Database database, Plugin plugin, int offset) {
        super(database, plugin);
        this.offset = offset;
    }

    @Override
    protected PreparedStatement prepare() throws SQLException {
        PreparedStatement base = getConnection().prepareStatement("SELECT * FROM actions ORDER BY Action_Name limit 18 offset ?");
        base.setInt(1, offset);
        return base;
    }
}
