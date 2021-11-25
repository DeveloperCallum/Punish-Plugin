package com.munchymc.punishmentplugin.common.database.parameterData;

import com.munchymc.punishmentplugin.common.database.SqlOperators;
import com.munchymc.punishmentplugin.exceptions.database.DatabaseRuntimeException;

public class PreparedWhereData<V> extends InternalWhereData<V>{
    //Must contain '=' and '?' but not WHERE
    private final String placeholderData;

    public PreparedWhereData(String name, String placeholderData) {
        super(name);
        this.placeholderData = placeholderData.replaceAll("<NAME>", getName());
    }

    @Override //Must include "<Name>".
    protected void generate(StringBuilder current) {
        if (!this.isActive()) {
            return;
        }

        if (current.length() <= 0) {
            if (getConflate() == null){
                current.append("WHERE ").append(placeholderData);
                this.position = 1;
                return;
            }

            if (getConflate().equals(SqlOperators.NOT)) {
                current.append("WHERE ").append(this.getConflate().toString()).append(" ").append(placeholderData);
                this.position = setPos(current.toString());

                return;
            }

            throw new DatabaseRuntimeException("Cannot use operators 'AND', 'OR' on first where statement");
        }

        current.append(" ").append(this.getConflate().toString()).append(" ").append(placeholderData);
        this.position = setPos(current.toString());
    }

    //SELECT * FROM punishments WHERE DATE(Expire_Date) = '2021-09-07'
}
