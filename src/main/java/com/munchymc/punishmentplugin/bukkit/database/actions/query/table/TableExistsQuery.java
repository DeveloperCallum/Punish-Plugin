package com.munchymc.punishmentplugin.bukkit.database.actions.query.table;

import com.munchymc.punishmentplugin.common.database.DatabaseQuery;
import com.munchymc.punishmentplugin.common.database.wrappers.liketable.LikeTable;
import com.munchymc.punishmentplugin.common.database.wrappers.liketable.LikeTableBuilder;
import com.munchymc.punishmentplugin.common.database.Database;
import org.bukkit.plugin.Plugin;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class TableExistsQuery extends DatabaseQuery<LikeTable> {
    private String tableName;

    public TableExistsQuery(Database database, String tableName, Plugin plugin) {
        super(database, plugin);
        this.tableName = tableName;
    }

    @Override
    protected PreparedStatement prepare() {
        int param1 = ResultSet.TYPE_SCROLL_SENSITIVE;
        int param2 = ResultSet.CONCUR_UPDATABLE;

        try{
            PreparedStatement prep = getConnection().prepareStatement("show tables like ?", param1, param2);
            prep.setString(1, tableName);
            return prep;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e.getCause()); //Can't do anything to recover.
        }
    }

    @Override
    protected LikeTable createWrapper(ResultSet queryRes) {

        try{
            LikeTableBuilder likeTableBuilder = new LikeTableBuilder(tableName);
            boolean tableExists = queryRes.next();
            queryRes.beforeFirst();
            return likeTableBuilder.setExists(tableExists).build();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void close() {
        this.releaseConnection();
    }
}