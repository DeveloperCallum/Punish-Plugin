package com.munchymc.punishmentplugin.bukkit.database;

import com.munchymc.punishmentplugin.bukkit.database.actions.query.table.TableExistsQuery;
import com.munchymc.punishmentplugin.common.database.wrappers.liketable.LikeTable;
import com.munchymc.punishmentplugin.bukkit.database.pool.ConnectionPool;
import com.munchymc.punishmentplugin.common.database.Database;
import com.munchymc.punishmentplugin.common.database.pooling.ObjectPool;
import org.bukkit.plugin.Plugin;

import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Connection;

public class DatabaseProvider extends Database {
    private final ObjectPool<Connection> conPool;
    private Plugin plugin;

    public DatabaseProvider(int initialAmount, String connectionUrl, Plugin plugin) {
        super();
        this.plugin = plugin;

        this.conPool  = new ConnectionPool(initialAmount, connectionUrl);

        //Check tables inside the database | Async because other parts of the plugin cannot operate without it being there.
        checkTable("users");
        checkTable("actions");
        checkTable("punishments");
        checkTable("reports");
    }

    @Deprecated //TODO: Move to Database Command
    private void checkTable(String tableName) {
        String firstChar = String.valueOf(tableName.charAt(0));
        String capName = tableName.replaceFirst(firstChar, firstChar.toUpperCase()); //Convert to capitalised case

        TableExistsQuery tableExists = new TableExistsQuery(this, tableName, plugin);
        LikeTable likeTable = tableExists.executeQuery();

        if (!likeTable.exists()){
            Reader scriptStream = new InputStreamReader(this.getClass().getResourceAsStream("/MySQL/CreateTable/" + capName + "Table.sql"));
            runScript(scriptStream);
        }
    }

    @Override
    protected Connection acquireConnection() {
        return conPool.get();
    }

    @Override
    protected void closeConnection(Connection connection) {
        conPool.release(connection);
    }
}
