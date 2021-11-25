package com.munchymc.punishmentplugin.common.database.wrappers.liketable;

import com.munchymc.punishmentplugin.common.database.wrappers.Wrapper;

public class LikeTable implements Wrapper {
    protected String name;
    protected boolean exists;

    protected LikeTable(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean exists() {
        return exists;
    }
}