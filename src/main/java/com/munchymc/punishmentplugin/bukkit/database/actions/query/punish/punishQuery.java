package com.munchymc.punishmentplugin.bukkit.database.actions.query.punish;

import com.munchymc.punishmentplugin.common.database.Database;
import com.munchymc.punishmentplugin.common.database.DatabaseQuery;
import com.munchymc.punishmentplugin.common.database.wrappers.tables.actions.ActionBuilder;
import com.munchymc.punishmentplugin.common.database.wrappers.tables.punishments.PunishBuilder;
import com.munchymc.punishmentplugin.common.database.wrappers.tables.punishments.PunishTable;
import com.munchymc.punishmentplugin.common.database.wrappers.tables.users.UsersBuilder;
import org.bukkit.plugin.Plugin;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public abstract class punishQuery extends DatabaseQuery<PunishTable> {
    public punishQuery(Database database, Plugin plugin) {
        super(database, plugin);
    }

    @Override
    protected PunishTable createWrapper(ResultSet queryRes) throws SQLException {
        if (!queryRes.next()){
            return null;
        }

        PunishBuilder builder = new PunishBuilder();

        builder.setPunishmentUid(UUID.fromString(queryRes.getString("Punishment_UID")));

        try{
            ActionBuilder actionBuilder = new ActionBuilder(queryRes.getString("Action_Type"));
            actionBuilder.setDisplayName(queryRes.getString("Display_Name"));
            builder.setAction(actionBuilder.build());
        }catch (SQLException e){ }

        try{
            builder.setDateIssued(queryRes.getTimestamp("Date_Issued"));
            builder.setReason(queryRes.getString("PunishReason"));
            builder.setExpire(queryRes.getTimestamp("Expire_Date"));
        }catch (SQLException e){ }

        try{
            UsersBuilder issuer = new UsersBuilder(UUID.fromString(queryRes.getString("target_Player_UID")));
            issuer.setPlayerName(queryRes.getString("issuer_Player_Name"));
            builder.setIssuer(issuer.build());
        }catch (SQLException e){ }


        try{
            UsersBuilder target = new UsersBuilder(UUID.fromString(queryRes.getString("issuer_Player_UID")));
            target.setPlayerName(queryRes.getString("target_Player_Name"));
            builder.setTarget(target.build());
        }catch (SQLException e){ }

        return builder.build();
    }
}
