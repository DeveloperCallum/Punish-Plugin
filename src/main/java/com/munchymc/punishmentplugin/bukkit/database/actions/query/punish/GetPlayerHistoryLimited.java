package com.munchymc.punishmentplugin.bukkit.database.actions.query.punish;

import com.munchymc.punishmentplugin.common.database.Database;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class GetPlayerHistoryLimited extends GetPlayerHistory{
    private Database database;
    private UUID target;
    private int offset;
    private int limit;

    /**
     * Get the players punishment history.
     *
     * @param database The database to query from.
     * @param target   The player who's getting their history requested.
     */
    public GetPlayerHistoryLimited(Plugin plugin, Database database, UUID target, int offset, int limit) {
        super(database, target, plugin);
        this.database = database;
        this.target = target;
        this.offset = offset;
        this.limit = limit;
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
                "where Subject_UID = ? limit ? offset ?");

        prep.setString(1, target.toString());
        prep.setInt(2, this.limit);
        prep.setInt(3, this.offset);

        return prep;
    }
}
