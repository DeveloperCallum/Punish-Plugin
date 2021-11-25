package com.munchymc.punishmentplugin.bukkit.database.actions.query.action;

import com.munchymc.punishmentplugin.common.database.Database;
import com.munchymc.punishmentplugin.common.database.DatabaseQuery;
import com.munchymc.punishmentplugin.common.database.wrappers.tables.actions.ActionBuilder;
import com.munchymc.punishmentplugin.common.database.wrappers.tables.actions.ActionsTable;
import org.bukkit.plugin.Plugin;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetAction extends DatabaseQuery<ActionsTable> {
    private String name;

    public GetAction(Database database, Plugin plugin, String name) {
        super(database, plugin);
        this.name = name;
    }

    @Override
    protected PreparedStatement prepare() throws SQLException {
        PreparedStatement base = getConnection().prepareStatement("SELECT * from actions where Action_Name = ?");
        base.setString(1, name);
        return base;
    }

    @Override
    protected ActionsTable createWrapper(ResultSet queryRes) {
        ActionBuilder actionBuilder = new ActionBuilder(name);

        try {
            queryRes.next();

            actionBuilder.setRespondingAction(queryRes.getString("Responding_Action"));
            actionBuilder.setPermission(queryRes.getString("Usage_Permission"));
            actionBuilder.setDefaultTime(queryRes.getString("Default_Duration"));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return actionBuilder.build();
    }
}
