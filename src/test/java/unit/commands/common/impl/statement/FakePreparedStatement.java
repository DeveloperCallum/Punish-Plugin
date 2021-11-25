package unit.commands.common.impl.statement;

import unit.commands.common.impl.database.BaseDatabase;
import unit.commands.common.base.BasePreparedStatement;

import java.sql.*;

public class FakePreparedStatement extends BasePreparedStatement {
    private String statement;
    private String[] splitData;
    private BaseDatabase database;

    public FakePreparedStatement(String statement, BaseDatabase database) {
        this.statement = (statement += ";");
        this.database = database;
        this.splitData = statement.split("\\?");
    }

    public String getStatement() {
        return statement;
    }

    @Override
    public int executeUpdate() throws SQLException {
        database.addExecutedString(statement);

        return 0;
    }

    @Override
    public void setString(int parameterIndex, String x) throws SQLException {
        String setString = "";
        splitData[parameterIndex] = x + splitData[parameterIndex];

        for(String str : splitData){
            setString += str;
        }

        statement = setString;
    }

    public ResultSet executeQuery() throws SQLException {
        database.addExecutedString(statement);
        return null;
    }
}
