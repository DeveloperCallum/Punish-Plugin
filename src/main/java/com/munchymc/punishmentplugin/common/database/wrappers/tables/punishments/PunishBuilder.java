package com.munchymc.punishmentplugin.common.database.wrappers.tables.punishments;

import com.munchymc.punishmentplugin.common.database.wrappers.tables.actions.ActionsTable;
import com.munchymc.punishmentplugin.common.database.wrappers.tables.users.UsersTable;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

public class PunishBuilder {
    private UsersTable target;
    private UsersTable issuer;
    private UUID punishmentUid;
    private Timestamp dateIssued;
    private String reason;
    private ActionsTable action;
    protected Timestamp expire;

    public PunishBuilder setTarget(UsersTable target) {
        this.target = target;
        return this;
    }

    public PunishBuilder setIssuer(UsersTable issuer) {
        this.issuer = issuer;
        return this;
    }

    public PunishBuilder setPunishmentUid(UUID punishmentUid) {
        this.punishmentUid = punishmentUid;
        return this;
    }

    public PunishBuilder setDateIssued(Timestamp dateIssued) {
        this.dateIssued = dateIssued;
        return this;
    }

    public PunishBuilder setReason(String reason) {
        this.reason = reason;
        return this;
    }

    public PunishBuilder setAction(ActionsTable action) {
        this.action = action;
        return this;
    }

    public void setExpire(Timestamp expire) {
        this.expire = expire;
    }

    public PunishTable build(){
        PunishTable punishTable = new PunishTable();
        punishTable.action = action;
        punishTable.dateIssued = dateIssued;
        punishTable.issuer = issuer;
        punishTable.punishmentUid = punishmentUid;
        punishTable.target = target;
        punishTable.reason = reason;
        punishTable.expire = expire;

        return punishTable;
    }
}
