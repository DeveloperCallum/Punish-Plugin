package com.munchymc.punishmentplugin.common.database;

import com.munchymc.punishmentplugin.exceptions.database.DatabaseRuntimeException;

import java.io.Reader;
import java.sql.Connection;

/**
 * This is responsible for managing the connection (Example: Closing and opening).
 *
 */

public abstract class Database {

    /**
     * This method gets a usable connection to the database.
     * @return The connection that was created
     * @see Connection
     */

    protected abstract Connection acquireConnection();

    /**
     * This method takes an existing connection and executes the classes closing logic.
     * @param connection
     */
    protected abstract void closeConnection(Connection connection);

    /**
     * This method runs a file containing SQL code, this file must be converted into a readable input stream
     * @param scriptStream The stream of the code being executed.
     */
    protected void runScript(Reader scriptStream){
        RunScript runScript = new RunScript(this);
        runScript.run(scriptStream);
    }
}
