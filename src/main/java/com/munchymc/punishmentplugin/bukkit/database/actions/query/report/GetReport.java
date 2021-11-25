package com.munchymc.punishmentplugin.bukkit.database.actions.query.report;

import com.munchymc.punishmentplugin.common.database.DatabaseQuery;
import com.munchymc.punishmentplugin.common.database.wrappers.tables.reports.ReportsBuilder;
import com.munchymc.punishmentplugin.common.database.wrappers.tables.reports.ReportsTable;
import com.munchymc.punishmentplugin.common.database.Database;
import org.bukkit.plugin.Plugin;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class GetReport extends DatabaseQuery<List<ReportsTable>> {

    public GetReport(Database database, Plugin plugin) {
        super(database, plugin);
    }

    @Override
    protected PreparedStatement prepare() throws SQLException {
        PreparedStatement getData = getConnection().prepareStatement("SELECT tableA.Report_UID, tableB.Player_Name as \"Reported_Player_Name\", tableC.Player_Name as \"Submitter_Player_Name\", tableA.Reported_UID, tableA.Submitter_UID, tableA.Reason, tableA.hasBeenHandled FROM reports as tableA INNER JOIN users as tableB on tableA.Reported_UID = tableB.Player_UID INNER JOIN users as tableC on tableA.Submitter_UID = tableC.Player_UID where hasBeenHandled = ?;");
        getData.setBoolean(1, false);
        return getData;
    }

    @Override
    protected List<ReportsTable> createWrapper(ResultSet queryRes) throws SQLException {
        List<ReportsTable> list = new LinkedList<>();

        while (queryRes.next()){
            ReportsBuilder reportsBuilder = new ReportsBuilder(UUID.fromString(queryRes.getString("Report_UID")));
            reportsBuilder.setHasBeenHandled(false);
            reportsBuilder.setReportedUID(UUID.fromString(queryRes.getString("Reported_UID")));
            reportsBuilder.setSubmitterUID(UUID.fromString(queryRes.getString("Submitter_UID")));
            reportsBuilder.setReason(queryRes.getString("Reason"));
            reportsBuilder.setReportedUsername(queryRes.getString("Reported_Player_Name"));
            reportsBuilder.setSubmitterUsername(queryRes.getString("Submitter_Player_Name"));

            list.add(reportsBuilder.build());
        }

        if (list.isEmpty()){
            list = null;
        }

        return list;
    }

    @Override
    public void close() {
        releaseConnection();
    }
}
