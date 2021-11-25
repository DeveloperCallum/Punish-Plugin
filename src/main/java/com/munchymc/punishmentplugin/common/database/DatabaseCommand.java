package com.munchymc.punishmentplugin.common.database;


import com.munchymc.punishmentplugin.common.database.callbacks.SingleParamCallback;
import org.bukkit.ChatColor;
import org.intellij.lang.annotations.Language;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class DatabaseCommand extends DatabaseAction {

    public DatabaseCommand(Database database) {
        super(database);
    }

    public void executeCommand(){
        execute();
        this.releaseConnection();
    }

    public void unExecuteCommand(){
        unExecute();
        this.releaseConnection();
    }

    protected abstract void execute();
    protected abstract void unExecute();

    protected void makeStatement(@Language("MySQL") String commandString, SingleParamCallback<PreparedStatement> callback){
        try (PreparedStatement prep = getConnection().prepareStatement(commandString)) {
            callback.run(prep);
            prep.executeUpdate();
        } catch (SQLException e) {
                e.printStackTrace();
        }
    }
}

