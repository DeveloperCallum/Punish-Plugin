package com.munchymc.punishmentplugin.bukkit.database.actions.query.punish;

import com.munchymc.punishmentplugin.common.database.Database;
import org.bukkit.plugin.Plugin;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class BannedPlayerQuery extends punishQuery{
    private UUID playerUID;

    public BannedPlayerQuery(Database database, UUID playerUID, Plugin plugin) {
        super(database, plugin);

        this.playerUID = playerUID;
    }

    @Override
    protected PreparedStatement prepare() throws SQLException {
        PreparedStatement prep = getConnection().prepareStatement(
                "select tPunishments.Punishment_UID,\n" +
                "       tPunishments.Action_Type,\n" +
                "       tPunishments.Date_Issued,\n" +
                "       tPunishments.PunishReason,\n" +
                "       tPunishments.Expire_Date,\n" +
                "       tAction.Display_Name,\n" +
                "       tUser.Player_UID    as issuer_Player_UID,\n" +
                "       tUser.Player_Name   as issuer_Player_Name,\n" +
                "       tTarget.Player_UID  as target_Player_UID,\n" +
                "       tTarget.Player_Name as target_Player_Name\n" +
                "   from punishments as tPunishments\n" +
                "         inner join actions tAction on tPunishments.Action_Type = tAction.Action_Name\n" +
                "         inner join users tUser on tPunishments.Issuer_UID = tUser.Player_UID\n" +
                "         inner join users tTarget on tPunishments.Subject_UID = tTarget.Player_UID\n" +
                "   where Subject_UID = ?\n" +
                "   order by Expire_Date desc\n" +
                "   limit 1");

        prep.setString(1, playerUID.toString());
        return prep;
    }
}
