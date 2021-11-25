package com.munchymc.punishmentplugin.common.database;

public interface DatabaseCallback<T> {
    void execute(T result);
}
