package unit.commands.common.impl.connection;

import unit.commands.common.impl.database.BaseDatabase;
import unit.commands.common.base.BaseConnection;
import unit.commands.common.impl.statement.FakePreparedStatement;

import java.sql.*;

public class FakeConnection extends BaseConnection {
    private final BaseDatabase database;

    public FakeConnection(BaseDatabase database) {
        this.database = database;
    }

    @Override
    public PreparedStatement prepareStatement(String sql) throws SQLException {
        return new FakePreparedStatement(sql, database);
    }
}
