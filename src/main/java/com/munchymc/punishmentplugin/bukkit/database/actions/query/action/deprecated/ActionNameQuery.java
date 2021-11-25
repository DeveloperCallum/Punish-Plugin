package com.munchymc.punishmentplugin.bukkit.database.actions.query.action.deprecated;

import com.munchymc.punishmentplugin.common.database.DatabaseQuery;
import com.munchymc.punishmentplugin.common.database.wrappers.tables.actions.ActionBuilder;
import com.munchymc.punishmentplugin.common.database.wrappers.tables.actions.ActionsTable;
import com.munchymc.punishmentplugin.common.database.Database;
import org.bukkit.plugin.Plugin;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Deprecated
public class ActionNameQuery  extends DatabaseQuery<List<ActionsTable>> {

    public ActionNameQuery(Database database, Plugin plugin) {
        super(database, plugin);
    }

    @Override
    protected PreparedStatement prepare() throws SQLException {
        try{
            return getConnection().prepareStatement("select Action_Name from actions");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected List<ActionsTable> createWrapper(ResultSet queryRes) throws SQLException {
        try{
            if (!queryRes.next()) {
                return null;
            }
            List<ActionsTable> actionTables = new ArrayList<>();

            do {
                ActionBuilder actionBuilder = new ActionBuilder(queryRes.getString("Action_Name"));
                actionTables.add(actionBuilder.build());
            } while (queryRes.next());

            return actionTables;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
