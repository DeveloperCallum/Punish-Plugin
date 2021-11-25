package com.munchymc.punishmentplugin.common.database.wrappers.tables.reports;

import java.util.UUID;

public class ReportsBuilder {
    protected UUID reportUID;
    protected UUID reportedUID;
    protected String reportedUsername;
    protected UUID submitterUID;
    protected String submitterUsername;
    protected String reason;
    protected boolean hasBeenHandled;

    public ReportsBuilder(UUID reportUID) {
        this.reportUID = reportUID;
    }

    public void setReportUID(UUID reportUID) {
        this.reportUID = reportUID;
    }

    public void setReportedUID(UUID reportedUID) {
        this.reportedUID = reportedUID;
    }

    public void setSubmitterUID(UUID submitterUID) {
        this.submitterUID = submitterUID;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setHasBeenHandled(boolean hasBeenHandled) {
        this.hasBeenHandled = hasBeenHandled;
    }

    public void setReportedUsername(String reportedUsername) {
        this.reportedUsername = reportedUsername;
    }

    public void setSubmitterUsername(String submitterUsername) {
        this.submitterUsername = submitterUsername;
    }

    public ReportsTable build(){
        ReportsTable table = new ReportsTable();
        table.reportUID = reportUID;
        table.reportedUID = reportedUID;
        table.submitterUID = submitterUID;
        table.reason = reason;
        table.hasBeenHandled = hasBeenHandled;
        table.reportedUsername = reportedUsername;
        table.submitterUsername = submitterUsername;

        return table;
    }
}
