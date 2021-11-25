package com.munchymc.punishmentplugin.common.database.parameterData;

public interface ParameterData<V, V2> {
    V getSelectData();
    V2 getWhereData();
}
