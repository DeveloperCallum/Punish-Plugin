package com.munchymc.punishmentplugin.bukkit.database.actions.query.action;

import com.munchymc.punishmentplugin.common.database.parameterData.ParameterData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ActionParameterData implements ParameterData<ActionsSelectData, ActionsWhereData>{
    private ActionsSelectData selectData = new ActionsSelectData();
    private ActionsWhereData whereData = new ActionsWhereData();
    private Connection connection;

    public ActionParameterData(Connection connection) {
        this.connection = connection;
    }

    public ActionsSelectData getSelectData() {
        return selectData;
    }

    public ActionsWhereData getWhereData() {
        return whereData;
    }

    public PreparedStatement createQueryString() {
        String rawSQL = selectData.generateSQL() + " FROM actions " + whereData.generateSQL();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(rawSQL);
            whereData.setWhereData(preparedStatement);
            return preparedStatement;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
