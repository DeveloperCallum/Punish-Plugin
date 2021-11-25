package com.munchymc.punishmentplugin.common.database.wrappers.liketable;

public class LikeTableBuilder {
    private String name;
    private boolean exists;

    public LikeTableBuilder(String name) {
        this.name = name;
    }

    public LikeTableBuilder setName(String name) {
        if (name != null) {
            this.name = name;
        }

        return this;
    }

    public LikeTableBuilder setExists(boolean exists) {
        this.exists = exists;
        return this;
    }

    public LikeTable build() {
        LikeTable likeTable = new LikeTable(name);
        likeTable.exists = exists;
        return likeTable;
    }
}

