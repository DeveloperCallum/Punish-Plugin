package com.munchymc.punishmentplugin.bukkit.database.actions.query.punish;

import com.munchymc.punishmentplugin.common.database.Database;
import com.munchymc.punishmentplugin.common.database.DatabaseQuery;
import com.munchymc.punishmentplugin.common.database.wrappers.tables.actions.ActionBuilder;
import com.munchymc.punishmentplugin.common.database.wrappers.tables.punishments.PunishBuilder;
import com.munchymc.punishmentplugin.common.database.wrappers.tables.punishments.PunishTable;
import com.munchymc.punishmentplugin.common.database.wrappers.tables.users.UsersBuilder;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Get the players punishment history, this command currently has a limit of 10 rows.
 * @see com.munchymc.punishmentplugin.common.database.DatabaseAction
 */
public class GetPlayerHistory extends DatabaseQuery<List<PunishTable>> {
    private final UUID player;

    /**
     * Get the players punishment history.
     * @param database The database to query from.
     * @param target The player who's getting their history requested.
     */
    public GetPlayerHistory(Database database, UUID target, Plugin plugin) {
        super(database, plugin);
        this.player = target;
    }

    @Override
    protected PreparedStatement prepare() throws SQLException {
        PreparedStatement prep = getConnection().prepareStatement("SELECT Punishment_UID,\n" +
                "       Subject_UID,\n" +
                "       Issuer_UID,\n" +
                "       Action_Type,\n" +
                "       Date_Issued,\n" +
                "       PunishReason,\n" +
                "       Expire_Date,\n" +
                "       p2.Player_Name as 'Issuer_Player_Name',\n" +
                "       p2.Date_Joined as 'Issuer_Date_Joined',\n" +
                "       p2.Permissions as 'Issuer_Permissions',\n" +
                "       p1.Player_Name,\n" +
                "       p1.Date_Joined,\n" +
                "       p1.Permissions,\n" +
                "       Action_Name,\n" +
                "       Responding_Action,\n" +
                "       Default_Duration,\n" +
                "       Usage_Permission,\n" +
                "       Display_Name\n" +
                "from punishments\n" +
                "         inner join users as p1 ON punishments.Subject_UID = p1.Player_UID\n" +
                "         inner join users as p2 ON punishments.Issuer_UID = p2.Player_UID\n" +
                "         INNER JOIN actions a on punishments.Action_Type = a.Action_Name\n" +
                "where Subject_UID = ?");
        prep.setString(1, player.toString());

        return prep;
    }

    @Override
    protected List<PunishTable> createWrapper(ResultSet queryRes) throws SQLException {
        LinkedList<PunishTable> data = new LinkedList<>();

        while (queryRes.next()) {
            PunishBuilder builder = new PunishBuilder();

            builder.setPunishmentUid(UUID.fromString(queryRes.getString("Punishment_UID")));

            ActionBuilder actionBuilder = new ActionBuilder(queryRes.getString("Action_Type"));
            actionBuilder.setDisplayName(queryRes.getString("Display_Name"));
            builder.setAction(actionBuilder.build());

            builder.setDateIssued(queryRes.getTimestamp("Date_Issued"));
            builder.setReason(queryRes.getString("PunishReason"));
            builder.setExpire(queryRes.getTimestamp("Expire_Date"));

            UsersBuilder issuer = new UsersBuilder(UUID.fromString(queryRes.getString("Issuer_UID")));
            issuer.setPlayerName(queryRes.getString("Issuer_Player_Name"));
            builder.setIssuer(issuer.build());

            UsersBuilder target = new UsersBuilder(UUID.fromString(queryRes.getString("Subject_UID")));
            target.setPlayerName(queryRes.getString("Player_Name"));
            builder.setTarget(target.build());

            data.add(builder.build());
        }

        return data;
    }
}

//Test
