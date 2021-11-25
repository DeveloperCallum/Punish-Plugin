package com.munchymc.punishmentplugin.common.database.parameterData;

public class InternalData {
    private String name;
    private boolean isActive = false;
    private boolean isStart = false;

    public InternalData(String name) {
        this.name = name;
    }

    @Deprecated
    public void setStart(boolean start) {
        isStart = start;
    }

    @Deprecated
    public boolean isStart() {
        return isStart;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isActive() {
        return isActive;
    }

    public String getName() {
        return name;
    }

    @Deprecated
    public void generateConcatString(StringBuilder current) {
        if (isActive) {
            if (!isStart) {
                current.append(", ");
            } else {
                isStart = Boolean.FALSE;
            }

            current.append(name);
        }
    }

    public void appendSQL(StringBuilder current) {
        if (!isActive) {
            return;
        }

        if (current.length() > 0) {
            current.append(", ");
        }

        current.append(name);
    }
}

