package com.munchymc.punishmentplugin.bukkit.database.actions.query.punish;

import com.munchymc.punishmentplugin.common.database.Database;
import com.munchymc.punishmentplugin.common.database.wrappers.tables.actions.ActionBuilder;
import com.munchymc.punishmentplugin.common.database.wrappers.tables.punishments.PunishBuilder;
import com.munchymc.punishmentplugin.common.database.wrappers.tables.punishments.PunishTable;
import com.munchymc.punishmentplugin.common.database.wrappers.tables.users.UsersBuilder;
import org.bukkit.plugin.Plugin;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class CheckIfPermBanned extends punishQuery {
    private final UUID playerUID;

    public CheckIfPermBanned(Database database, UUID playerUID, Plugin plugin) {
        super(database, plugin);
        this.playerUID = playerUID;
    }

    @Override
    protected PreparedStatement prepare() throws SQLException {
        PreparedStatement query = getConnection().prepareStatement("SELECT Punishment_UID,\n" +
                "       Subject_UID as 'Target_Player_UID',\n" +
                "       Issuer_UID as 'Issuer_Player_UID',\n"  +
                "       Action_Type,\n" +
                "       Date_Issued,\n" +
                "       PunishReason,\n" +
                "       Expire_Date,\n" +
                "       p2.Player_Name as 'Issuer_Player_Name',\n" +
                "       p2.Date_Joined as 'Issuer_Date_Joined',\n" +
                "       p2.Permissions as 'Issuer_Permissions',\n" +

                "       p1.Player_Name as 'Target_Player_Name',\n" +
                "       p1.Date_Joined as 'Target_Date_Joined',\n" +
                "       p1.Permissions as 'Target_Permissions',\n" +
                "       Action_Name,\n" +
                "       Responding_Action,\n" +
                "       Default_Duration,\n" +
                "       Usage_Permission,\n" +
                "       Display_Name\n" +
                "from punishments\n" +
                "         inner join users as p1 ON punishments.Subject_UID = p1.Player_UID\n" +
                "         inner join users as p2 ON punishments.Issuer_UID = p2.Player_UID\n" +
                "         INNER JOIN actions a on punishments.Action_Type = a.Action_Name\n" +
                "where Subject_UID = ?\n" +
                "limit 1;");
        query.setString(1, playerUID.toString());
        return query;
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
        }catch(SQLException ignored){ }

        try{
            builder.setDateIssued(queryRes.getTimestamp("Date_Issued"));
            builder.setReason(queryRes.getString("PunishReason"));
            builder.setExpire(queryRes.getTimestamp("Expire_Date"));
        }catch(SQLException ignored){ }

        try{
            UsersBuilder issuer = new UsersBuilder(UUID.fromString(queryRes.getString("Issuer_Player_UID")));
            issuer.setPlayerName(queryRes.getString("Issuer_Player_Name"));
            builder.setIssuer(issuer.build());
        }catch(SQLException ignored){ }

        try{
            UsersBuilder target = new UsersBuilder(UUID.fromString(queryRes.getString("Target_Player_UID")));
            target.setPlayerName(queryRes.getString("Target_Player_Name"));
            builder.setTarget(target.build());
        }catch(SQLException ignored){ }

        return builder.build();
    }
}
