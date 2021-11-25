package com.munchymc.punishmentplugin.common.database.wrappers.tables.reports;

import java.util.UUID;

public class ReportsTable {
    protected UUID reportUID;
    protected UUID reportedUID;
    protected String reportedUsername;
    protected UUID submitterUID;
    protected String submitterUsername;
    protected String reason;
    protected boolean hasBeenHandled;

    public UUID getReportUID() {
        return reportUID;
    }

    public UUID getReportedUID() {
        return reportedUID;
    }

    public UUID getSubmitterUID() {
        return submitterUID;
    }

    public String getReason() {
        return reason;
    }

    public boolean isHasBeenHandled() {
        return hasBeenHandled;
    }

    public String getReportedUsername() {
        return reportedUsername;
    }

    public String getSubmitterUsername() {
        return submitterUsername;
    }
}
