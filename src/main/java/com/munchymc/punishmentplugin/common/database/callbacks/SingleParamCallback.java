package com.munchymc.punishmentplugin.common.database.callbacks;

import java.sql.SQLException;

public interface SingleParamCallback<T> extends Callback {
    void run(T TypeParam) throws SQLException;
}
