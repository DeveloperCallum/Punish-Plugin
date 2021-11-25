package com.munchymc.punishmentplugin.bukkit.database.actions.query.action;

import com.munchymc.punishmentplugin.common.database.Database;
import com.munchymc.punishmentplugin.common.database.DatabaseQuery;
import com.munchymc.punishmentplugin.common.database.wrappers.tables.actions.ActionBuilder;
import com.munchymc.punishmentplugin.common.database.wrappers.tables.actions.ActionsTable;
import org.bukkit.plugin.Plugin;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GetAllActions extends DatabaseQuery<List<ActionsTable>> {
    public GetAllActions(Database database, Plugin plugin) {
        super(database, plugin);
    }

    @Override
    protected PreparedStatement prepare() throws SQLException {
        return getConnection().prepareStatement("SELECT * FROM actions");
    }

    @Override
    protected List<ActionsTable> createWrapper(ResultSet queryRes) {
        try {
            if (!queryRes.next()) {
                return null;
            }
            List<ActionsTable> actionTables = new ArrayList<>();

            do {
                ActionBuilder actionBuilder = new ActionBuilder(queryRes.getString("Action_Name"));
                actionBuilder.setRespondingAction(queryRes.getString("Responding_Action"));
                actionBuilder.setPermission(queryRes.getString("Usage_Permission"));
                actionBuilder.setDefaultTime(queryRes.getString("Default_Duration"));

                actionTables.add(actionBuilder.build());
            } while (queryRes.next());

            return actionTables;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
