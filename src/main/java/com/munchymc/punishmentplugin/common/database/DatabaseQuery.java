package com.munchymc.punishmentplugin.common.database;

import com.munchymc.punishmentplugin.exceptions.database.DatabaseRuntimeException;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class DatabaseQuery<T> extends DatabaseAction {
    private Plugin plugin;

    public DatabaseQuery(Database database, Plugin plugin) {
        super(database);
        this.plugin = plugin;
    }

    //Prepared Statements will close ResultSet instances they create.
    protected abstract PreparedStatement prepare() throws SQLException;

    //Each database query could be different, and might need to be handled differently.
    protected abstract T createWrapper(ResultSet queryRes) throws SQLException;

    public T executeQuery() {
        try(PreparedStatement prep = prepare()){
            try(ResultSet resultSet = prep.executeQuery()){
                T wrapper = createWrapper(resultSet);
                this.releaseConnection(); //If it is needed again, I will just get another. DO NOT let connections remain idle.

                return wrapper;
            }
        }catch (SQLException e){
            throw new DatabaseRuntimeException(e.getMessage(), e.getCause());
        }
    }
}
