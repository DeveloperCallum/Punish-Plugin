package com.munchymc.punishmentplugin.common.database.parameterData;

import com.munchymc.punishmentplugin.common.database.SqlOperators;
import com.munchymc.punishmentplugin.common.database.parameterData.InternalConflateStore;
import com.munchymc.punishmentplugin.exceptions.database.DatabaseRuntimeException;

public class InternalWhereData<V> extends InternalConflateStore<V, SqlOperators> {
    protected int position = 0;

    public InternalWhereData(String name) {
        super(name);
    }

    @Override
    protected void generate(StringBuilder current) {
        if (!this.isActive()) {
            return;
        }

        if (current.length() <= 0) {
            if (getConflate() == null){
                current.append("WHERE ").append(this.getName()).append(" = ?");
                this.position = 1;
                return;
            }

            if (getConflate().equals(SqlOperators.NOT)) {
                current.append("WHERE ").append(this.getConflate().toString()).append(" ").append(this.getName()).append(" = ").append("?");
                this.position = setPos(current.toString());

                return;
            }

            throw new DatabaseRuntimeException("Cannot use operators 'AND', 'OR' on first where statement");
        }

        current.append(" ").append(this.getConflate().toString()).append(" ").append(this.getName()).append(" = ?");
        this.position = setPos(current.toString());
    }

    public int getPosition() {
        return position;
    }

    protected int setPos(String SQL){
        int currentpos = 0;

        for (char c : SQL.toCharArray()){
            if (c == '='){
                currentpos++;
            }
        }

        return currentpos;
    }

    //Where A = a2 and b = b2 and c = b2;
}
