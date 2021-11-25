package com.munchymc.punishmentplugin.bukkit.database.actions.command.punishments;

import com.munchymc.punishmentplugin.common.database.Database;
import com.munchymc.punishmentplugin.common.database.DatabaseCommand;
import com.munchymc.punishmentplugin.exceptions.database.DatabaseRuntimeException;

import java.util.Date;
import java.util.UUID;

public class CreatePunishmentCommand extends DatabaseCommand {
    private final UUID punishmentUID;
    private UUID subject;
    private UUID issuer;
    private String actionType;
    private String reason;

    public CreatePunishmentCommand(Database database, UUID subject, UUID issuer, String actionType, String reason) {
        super(database);
        this.subject = subject;
        this.issuer = issuer;
        this.actionType = actionType;
        this.reason = reason;
        punishmentUID = UUID.randomUUID();
    }

    public CreatePunishmentCommand(Database database, UUID punishmentUID) {
        super(database);
        this.punishmentUID = punishmentUID;
    }

    @Override
    protected void execute() {
        if (subject == null || issuer == null || actionType == null || reason == null){
            throw new DatabaseRuntimeException("Wrong constructor was used during initialisation");
        }

        makeStatement("INSERT INTO punishments(Punishment_UID, Subject_UID, Issuer_UID, Action_Type, PunishReason) VALUES (?, ?, ?, ?, ?)", statement -> {
            statement.setString(1, punishmentUID.toString());
            statement.setString(2, subject.toString());
            statement.setString(3, issuer.toString());
            statement.setString(4, actionType);
            statement.setString(5, reason);
        });
    }

    @Override
    protected void unExecute() {
        makeStatement("DELETE FROM punishments WHERE Punishment_UID = ?", statement -> {
            statement.setString(1, punishmentUID.toString());
        });
    }
}
//UPDATE punishments SET Expire_Date = current_timestamp() WHERE Expire_Date = (SELECT Expire_Date FROM punishments WHERE Subject_UID = ? order by Expire_Date desc) AND Subject_UID = ?