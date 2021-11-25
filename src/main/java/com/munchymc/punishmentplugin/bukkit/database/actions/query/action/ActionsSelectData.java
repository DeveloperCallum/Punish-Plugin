package com.munchymc.punishmentplugin.bukkit.database.actions.query.action;

import com.munchymc.punishmentplugin.common.database.parameterData.InternalData;
import com.munchymc.punishmentplugin.common.database.parameterData.ParameterChunk;
import com.munchymc.punishmentplugin.exceptions.database.DatabaseRuntimeException;

public class ActionsSelectData implements ParameterChunk {
    private final InternalData actionName = new InternalData("Action_Name");
    private final InternalData respondingAction = new InternalData("Responding_Action");
    private final InternalData defaultDuration = new InternalData("Default_Duration");
    private final InternalData usagePermission = new InternalData("Usage_Permission");

    public void selectName(boolean isActive){
        actionName.setActive(isActive);
    }

    public void selectRespondingAction(boolean isActive){
        respondingAction.setActive(isActive);
    }

    public void selectDefaultDuration(boolean isActive){
        defaultDuration.setActive(isActive);
    }

    public void selectUsagePermission(boolean isActive){
        usagePermission.setActive(isActive);
    }

    public boolean isGettingActionName(){
        return actionName.isActive();
    }

    public boolean isGettingRespondingAction(){
        return respondingAction.isActive();

    }

    public boolean isGettingDefaultDuration(){
        return defaultDuration.isActive();

    }

    public boolean isGettingUsagePermission(){
        return usagePermission.isActive();

    }

    @Override
    public String generateSQL() {
        boolean name = actionName.isActive();
        boolean rAction = respondingAction.isActive();
        boolean duration = defaultDuration.isActive();
        boolean permission = usagePermission.isActive();

        if (name && rAction && duration && permission){
            return "SELECT *";
        }

        StringBuilder data = new StringBuilder();

        actionName.appendSQL(data);
        respondingAction.appendSQL(data);
        defaultDuration.appendSQL(data);
        usagePermission.appendSQL(data);

        if (data.toString().equals("")){
            throw new DatabaseRuntimeException("Nothing to select!");
        }

        return "SELECT " + data.toString();
    }
}
