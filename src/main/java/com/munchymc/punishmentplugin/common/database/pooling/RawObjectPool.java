package com.munchymc.punishmentplugin.common.database.pooling;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public abstract class RawObjectPool<V> extends ObjectPool<V> {
    private final Queue<V> freeObjects;
    private final List<V> inUseObjects = new ArrayList<>();
    private final int initialAmount;

    public RawObjectPool(int initialAmount) { //Max amount too keep 'pooled'
        freeObjects = new LinkedList<>();
        this.initialAmount = initialAmount;
    }

    protected void createInitial(){
        for (int x = 0; x < initialAmount; x++) {
            setFree(createPoolObj());
        }
    }

    @Override
    public synchronized V get() {
        V poolObj = freeObjects.poll();

        if (poolObj == null){
            poolObj = createPoolObj();
        }

        setInUse(poolObj);
        return poolObj;
    }

    @Override
    public void release(V poolObj) {
        setFree(poolObj);
    }

    private void setFree(V poolObj) {
        inUseObjects.remove(poolObj);
        freeObjects.offer(poolObj);
    }

    private void setInUse(V poolObj){
        freeObjects.remove(poolObj);
        inUseObjects.add(poolObj);
    }
}

//FIXME: If a new object is generated because freeObjects is empty, if that object is then released it gets added to freeObjects.
//FIXME: You can return objects that weren't created by the object pool.