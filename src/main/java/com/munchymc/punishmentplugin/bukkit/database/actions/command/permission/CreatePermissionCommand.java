package com.munchymc.punishmentplugin.bukkit.database.actions.command.permission;

import com.munchymc.punishmentplugin.common.database.DatabaseCommand;
import com.munchymc.punishmentplugin.common.database.Database;

public class CreatePermissionCommand extends DatabaseCommand {
    private final String name;
    private final String action;
    private final String duration;
    private final String usagePermStr;

    public CreatePermissionCommand(Database database, String name, String action, String duration, String usagePermStr) {
        super(database);
        this.name = name;
        this.action = action;
        this.duration = duration;
        this.usagePermStr = usagePermStr;
    }

    public CreatePermissionCommand(Database database, String name) {
        super(database);
        this.name = name;
        this.action = null;
        this.duration = null;
        this.usagePermStr = null;
    }

    @Override
    protected void execute() {
        if (action == null || duration == null || usagePermStr == null){
            throw new IllegalArgumentException("Action, duration, usagePermStr must all be set.");
        }

        makeStatement("insert into munchymc_dev.actions VALUES (?, ?, ?, ?)", preparedStatement -> {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, action);
            preparedStatement.setString(3, duration);
            preparedStatement.setString(4, usagePermStr);
        });
    }

    @Override
    protected void unExecute() {
        makeStatement("DELETE FROM munchymc_dev.actions WHERE Action_Name = ?", preparedStatement -> {
            preparedStatement.setString(1, name);
        });
    }

    @Override
    public void close() {
        releaseConnection();
    }
}
