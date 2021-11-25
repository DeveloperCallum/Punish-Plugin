package com.munchymc.punishmentplugin.common.database;

import java.sql.Connection;

public abstract class DatabaseAction {
    private Connection connection;
    private final Database database;

    public DatabaseAction(Database databaseService) {
        this.database = databaseService;
    }

    protected Connection getConnection() {
        if (connection == null) {
            connection = database.acquireConnection();
        }

        return connection;
    }

    protected void releaseConnection() {
        database.closeConnection(connection);
    }

    public void close() {
        releaseConnection();
    }
}
