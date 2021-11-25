package unit.commands.common.impl.database;

import com.munchymc.punishmentplugin.common.database.Database;
import com.munchymc.punishmentplugin.exceptions.database.DatabaseRuntimeException;
import unit.commands.common.impl.connection.FakeConnection;

import java.sql.Connection;
import java.util.LinkedList;
import java.util.List;

public class BaseDatabase extends Database{
    private final List<String> executedSql = new LinkedList<>();
    private static BaseDatabase database;

    public BaseDatabase() {
        synchronized (Database.class){
            if (database != null){
                throw new DatabaseRuntimeException("Database instance already exists!");
            }

            database = this;
        }
    }

    public static BaseDatabase getInstance(){
        if (database == null){
            database = new BaseDatabase();
        }

        return database;
    }

    public List<String> getExecutedSql(){
        return new LinkedList<>(executedSql);
    }

    public void addExecutedString(String sql){
        executedSql.add(sql);
    }

    public synchronized String getLastSql(){
        return executedSql.get(executedSql.size() - 1);
    }

    @Override
    protected Connection acquireConnection() {
        return new FakeConnection(this);
    }

    @Override
    protected void closeConnection(Connection connection) {
    }
}
