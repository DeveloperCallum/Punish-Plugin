package com.munchymc.punishmentplugin.bukkit.database.actions.command.player;

import com.munchymc.punishmentplugin.common.database.DatabaseCommand;
import com.munchymc.punishmentplugin.common.database.Database;

import java.util.UUID;

public class AddPlayerCommand extends DatabaseCommand {
    private final UUID playerUid;
    private final String playerName;

    public AddPlayerCommand(Database database, UUID playerUid, String playerName) {
        super(database);
        this.playerName = playerName;
        this.playerUid = playerUid;
    }

    @Override
    protected void execute() {
        makeStatement("INSERT INTO users (Player_UID, Player_Name) VALUES(?,?) ON DUPLICATE KEY UPDATE Player_Name = VALUES(Player_Name)", preparedStatement -> {
            preparedStatement.setString(1, playerUid.toString());
            preparedStatement.setString(2, playerName);
        });
    }

    @Override
    protected void unExecute() {
        makeStatement("DELETE from users WHERE Player_UID = ?", preparedStatement -> {
            preparedStatement.setString(1, playerUid.toString());
        });
    }

    @Override
    public void close() {
        releaseConnection();
    }
}

