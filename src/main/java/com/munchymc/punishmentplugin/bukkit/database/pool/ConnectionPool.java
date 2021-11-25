package com.munchymc.punishmentplugin.bukkit.database.pool;

import com.munchymc.punishmentplugin.common.database.pooling.RawObjectPool;
import com.munchymc.punishmentplugin.common.database.pooling.interfaces.PoolObjectCreationException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//Connection Users will be responsible for using "Connection" in a new Thread.
public class ConnectionPool extends RawObjectPool<Connection> {
    String conURL;

    public ConnectionPool(int initialAmount, String url) {
        super(initialAmount);
        conURL = url;

        createInitial();
        System.out.println("Initial Connection(s) Created!");
    }

    @Override
    protected Connection createPoolObj() {
        try {
            synchronized (this) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                return DriverManager.getConnection(conURL);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new PoolObjectCreationException(e.getMessage(), e.getCause());
        }
    }
}
