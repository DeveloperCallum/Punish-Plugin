package com.munchymc.punishmentplugin.common.database.parameterData;

public abstract class InternalConflateStore<V, T> extends  InternalDataStore<V>{
    private T toAdd = null;

    public InternalConflateStore(String name) {
        super(name);
    }

    public T getConflate() {
        return toAdd;
    }

    @Override
    public void appendSQL(StringBuilder current) {
        generate(current);
    }

    public void setConflate (T value){
        toAdd = value;
    }

    protected abstract void generate(StringBuilder current);
}
