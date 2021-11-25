package com.munchymc.punishmentplugin.common.database.wrappers.tables.actions;

import java.sql.Time;

public class ActionBuilder {
    private String name;
    private String respondingAction;
    private String defaultTime;
    private String Permission;
    private String displayName;

    public ActionBuilder(String name){
        this.name = name;
    }

    public ActionBuilder setRespondingAction(String respondingAction) {
        this.respondingAction = respondingAction;
        return this;
    }

    public ActionBuilder setDefaultTime(String defaultTime) {
        this.defaultTime = defaultTime;
        return this;
    }

    public ActionBuilder setPermission(String permission) {
        Permission = permission;
        return this;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public ActionsTable build(){
        ActionsTable actionTable = new ActionsTable(name);
        actionTable.defaultTime = defaultTime;
        actionTable.respondingAction = respondingAction;
        actionTable.Permission = Permission;
        actionTable.displayName = displayName;

        return actionTable;
    }
}
