package com.munchymc.punishmentplugin.bukkit.database.actions.query.player;

import com.munchymc.punishmentplugin.common.database.parameterData.deprecated.ParameterData;

public class PlayerParameterData implements ParameterData {
    private final boolean usingPlayerUID;
    private boolean getPlayerUID = false;
    private boolean getPlayerName = false;
    private boolean getDateJoined = false;
    private boolean getPermissions = false;

    public PlayerParameterData(boolean usingPlayerUID) {
        this.usingPlayerUID = usingPlayerUID;
    }

    public void setGetDateJoined(boolean dateJoined) {
        this.getDateJoined = dateJoined;
    }

    public void setGetPermissions(boolean permissions) {
        this.getPermissions = permissions;
    }

    public void setGetPlayerUID(boolean playerUID) {
        this.getPlayerUID = playerUID;
    }

    public void setGetPlayerName(boolean playerName) {
        this.getPlayerName = playerName;
    }

    protected boolean isUsingPlayerUID() {
        return usingPlayerUID;
    }

    protected boolean isUsingPlayerName() {
        return !usingPlayerUID;
    }

    protected boolean isGettingPlayerUID() {
        return getPlayerUID;
    }

    protected boolean isGettingPlayerName() {
        return getPlayerName;
    }

    protected boolean isGettingDateJoined() {
        return getDateJoined;
    }

    protected boolean isGettingPermissions() {
        return getPermissions;
    }

    public String createQueryString() {
        StringBuilder query = new StringBuilder("SELECT ");
        boolean isStart = true;

        if (getPlayerUID) {
            query.append("Player_UID");
            isStart = false;
        }

        if (getPlayerName) {
            if (!isStart) {
                query.append(", ");
            } else {
                isStart = false;
            }

            query.append("Player_Name");
        }

        if (getDateJoined) {
            if (!isStart) {
                query.append(", ");
            } else {
                isStart = false;
            }

            query.append("Date_Joined");
        }

        if (getPermissions) {
            if (!isStart) {
                query.append(", ");
            }

            query.append("Permissions");
        }

        if (usingPlayerUID) {
            query.append(" FROM users WHERE Player_UID = ?");
        } else {
            query.append(" FROM users WHERE Player_Name = ?");
        }

        return query.toString();
    }
}
