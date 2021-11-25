package com.munchymc.punishmentplugin.common.database;

import com.munchymc.punishmentplugin.common.database.Database;
import com.munchymc.punishmentplugin.common.database.DatabaseAction;
import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.Reader;
import java.sql.Connection;

class RunScript extends DatabaseAction {

    public RunScript(Database database) {
        super(database);
    }

    public void run(Reader scriptStream) {
        Connection connection = getConnection();
        ScriptRunner scriptRunner = new ScriptRunner(connection);
        scriptRunner.setLogWriter(null);
        scriptRunner.runScript(scriptStream);
    }

    @Override
    public void close() {
        releaseConnection();
    }
}