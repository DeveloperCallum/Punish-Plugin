package com.munchymc.punishmentplugin.common.database.wrappers.tables.punishments;

import com.munchymc.punishmentplugin.common.database.wrappers.tables.actions.ActionsTable;
import com.munchymc.punishmentplugin.common.database.wrappers.tables.users.UsersTable;
import com.munchymc.punishmentplugin.common.database.wrappers.Wrapper;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

public class PunishTable implements Wrapper {
    protected UsersTable target;
    protected UsersTable issuer;
    protected UUID punishmentUid;
    protected Timestamp dateIssued;
    protected Timestamp expire;
    protected String reason;
    protected ActionsTable action;

    protected PunishTable(){

    }

    public UsersTable getTarget() {
        return target;
    }

    public UsersTable getIssuer() {
        return issuer;
    }

    public UUID getPunishmentUid() {
        return punishmentUid;
    }

    public Timestamp getDateIssued() {
        return dateIssued;
    }

    public String getReason() {
        return reason;
    }

    public ActionsTable getAction() {
        return action;
    }

    public Timestamp getExpire() {
        return expire;
    }
}
