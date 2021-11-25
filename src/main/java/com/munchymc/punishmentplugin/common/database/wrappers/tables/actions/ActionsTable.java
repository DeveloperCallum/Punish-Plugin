package com.munchymc.punishmentplugin.common.database.wrappers.tables.actions;

import com.munchymc.punishmentplugin.common.database.wrappers.Wrapper;

import java.sql.Time;

public class ActionsTable implements Wrapper {
    private String name;
    protected String respondingAction;
    protected String defaultTime;
    protected String Permission;
    protected String displayName;

    protected ActionsTable(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getRespondingAction() {
        return respondingAction;
    }

    public String getDefaultTime() {
        return defaultTime;
    }

    public String getPermission() {
        return Permission;
    }

    public Object getDisplayName() {
        return displayName;
    }
}
