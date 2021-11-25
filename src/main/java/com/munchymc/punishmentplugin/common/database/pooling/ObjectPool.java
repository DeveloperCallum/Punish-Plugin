package com.munchymc.punishmentplugin.common.database.pooling;

public abstract class ObjectPool<V> {
    public abstract V get();

    public abstract void release(V poolObject);

    protected abstract V createPoolObj();
}
