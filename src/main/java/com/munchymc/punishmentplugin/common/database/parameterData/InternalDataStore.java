package com.munchymc.punishmentplugin.common.database.parameterData;

public class InternalDataStore<V> extends InternalData {
    private V data;
    public InternalDataStore(String name) {
        super(name);
    }

    public V getValue() {
        return data;
    }

    public void setValue(V data) {
        this.data = data;
    }
}

