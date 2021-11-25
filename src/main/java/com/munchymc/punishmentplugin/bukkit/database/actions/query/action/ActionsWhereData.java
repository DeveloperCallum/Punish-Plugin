package com.munchymc.punishmentplugin.bukkit.database.actions.query.action;

import com.munchymc.punishmentplugin.common.database.SqlOperators;
import com.munchymc.punishmentplugin.common.database.parameterData.InternalWhereData;
import com.munchymc.punishmentplugin.common.database.parameterData.ParameterChunk;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ActionsWhereData implements ParameterChunk {
    private final InternalWhereData<String> actionName = new InternalWhereData<>("Action_Name");
    private final InternalWhereData<String> respondingAction = new InternalWhereData<>("Responding_Action");
    private final InternalWhereData<String> defaultDuration = new InternalWhereData<>("Default_Duration");
    private final InternalWhereData<String> usagePermission = new InternalWhereData<>("Usage_Permission");

    //InternalConflateStore<String, SqlOperators>

    public void setName(String data) {
        actionName.setValue(data);
        actionName.setActive(true);
    }

    //Update: Use setM(String) inside setM(String, SqlOperators)

    public void setName(String data, SqlOperators operator) {
        actionName.setValue(data);
        actionName.setActive(true);
        actionName.setConflate(operator);
    }

    public void setRespondingAction(String data) {
        respondingAction.setValue(data);
        respondingAction.setActive(true);
    }

    public void setRespondingAction(String data, SqlOperators operator) {
        respondingAction.setValue(data);
        respondingAction.setActive(true);
        respondingAction.setConflate(operator);
    }

    public void setDefaultDuration(String data){
        defaultDuration.setValue(data);
        defaultDuration.setActive(true);
    }

    public void setDefaultDuration(String data, SqlOperators operator){
        defaultDuration.setValue(data);
        defaultDuration.setActive(true);
        defaultDuration.setConflate(operator);

    }

    public void setUsagePermission(String data) {
        usagePermission.setValue(data);
        usagePermission.setActive(true);
    }

    public void setUsagePermission(String data, SqlOperators operator) {
        usagePermission.setValue(data);
        usagePermission.setActive(true);
        usagePermission.setConflate(operator);
    }

    public void removeName() {
        actionName.setValue(null);
        actionName.setActive(false);
    }

    public void removeRespondingAction() {
        respondingAction.setValue(null);
        respondingAction.setActive(false);
    }

    public void removeDefaultDuration() {
        defaultDuration.setValue(null);
        defaultDuration.setActive(false);
    }

    public void removeUsagePermission() {
        usagePermission.setValue(null);
        usagePermission.setActive(false);
    }

    protected String getName(){
        return actionName.getValue();
    }

    @Override
    public String generateSQL() {
        boolean name = actionName.isActive();
        boolean rAction = respondingAction.isActive();
        boolean duration = defaultDuration.isActive();
        boolean permission = usagePermission.isActive();

        if (!name && !rAction && !duration && !permission){
            return "";
        }

        StringBuilder selectData = new StringBuilder();

        actionName.appendSQL(selectData);
        respondingAction.appendSQL(selectData);
        defaultDuration.appendSQL(selectData);
        usagePermission.appendSQL(selectData);

        return selectData.toString();
    }

    public void setWhereData(PreparedStatement preparedStatement) throws SQLException {

        if (actionName.isActive()){
            preparedStatement.setString(actionName.getPosition(), actionName.getValue());
        }

        if (respondingAction.isActive()){
            preparedStatement.setString(respondingAction.getPosition(), respondingAction.getValue());
        }

        if (defaultDuration.isActive()){
            preparedStatement.setString(defaultDuration.getPosition(), defaultDuration.getValue());
        }

        if (usagePermission.isActive()){
            preparedStatement.setString(usagePermission.getPosition(), usagePermission.getValue());

        }
    }
}