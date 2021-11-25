package com.munchymc.punishmentplugin.bukkit.database.actions.command.report;

import com.munchymc.punishmentplugin.common.database.DatabaseCommand;
import com.munchymc.punishmentplugin.common.database.Database;

import java.util.UUID;

public class ReportCreate extends DatabaseCommand {
    private final UUID reportUID;
    private UUID targetUID;
    private UUID submitterUID;
    private String reason;

    public ReportCreate(Database database, UUID targetUID, UUID submitterUID, String reason) {
        super(database);
        this.reportUID = UUID.randomUUID();
        this.targetUID = targetUID;
        this.submitterUID = submitterUID;
        this.reason = reason;
    }

    public ReportCreate(Database database, UUID reportUID) {
        super(database);
        this.reportUID = reportUID;
    }

    @Override
    protected void execute() {
        makeStatement("INSERT INTO reports values (?, ?, ?, ?)", statement -> {
            statement.setString(1, reportUID.toString());
            statement.setString(2, targetUID.toString());
            statement.setString(3, submitterUID.toString());
            statement.setString(4, reason);
        });
    }

    @Override
    protected void unExecute() {
        makeStatement("DELETE FROM reports WHERE Report_UID = ?", statement -> {
            statement.setString(1, String.valueOf(reportUID));
        });
    }

    @Override
    public void close() {

    }
}
