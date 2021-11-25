package com.munchymc.punishmentplugin.bukkit.database.actions.query.punish;

import com.munchymc.punishmentplugin.common.database.parameterData.InternalData;
import com.munchymc.punishmentplugin.common.database.parameterData.deprecated.ParameterData;
import com.munchymc.punishmentplugin.exceptions.command.CommandRuntimeException;


public class PunishParameterData implements ParameterData {
    private final boolean usingPlayerUID;
    private final InternalData getSubjectUID = new InternalData("Subject_UID");
    private final InternalData getIssuerUID = new InternalData("Issuer_UID");
    private final InternalData getActionType = new InternalData("Action_Type");
    private final InternalData geDateIssued = new InternalData("Date_Issued");
    private final InternalData getReason = new InternalData("PunishReason"); //Update: Inconsistent Naming.
    private final InternalData getExpireDate = new InternalData("Expire_Date");

    public PunishParameterData(boolean usingPlayerUID) {
        this.usingPlayerUID = usingPlayerUID;
    }

    public boolean isUsingPlayerUID() {
        return usingPlayerUID;
    }

    public void setGetSubjectUID(boolean getSubjectUID) {
        this.getSubjectUID.setActive(getSubjectUID);
    }

    public void setGetIssuerUID(boolean getIssuerUID) {
        this.getIssuerUID.setActive(getIssuerUID);
    }

    public void setGetActionType(boolean getActionType) {
        this.getActionType.setActive(getActionType);
    }

    public void setGeDateIssued(boolean geDateIssued) {
        this.geDateIssued.setActive(geDateIssued);
    }

    public void setGetReason(boolean getReason) {
        this.getReason.setActive(getReason);
    }

    public void setGetExpireDate(boolean getExpireDate) {
        this.getExpireDate.setActive(getExpireDate);
    }

    @Override
    public String createQueryString() {
       boolean isStart;
        StringBuilder query = new StringBuilder("SELECT ");

        getSubjectUID.setStart(true);
        getSubjectUID.generateConcatString(query);
        isStart = getSubjectUID.isStart();

        getIssuerUID.setStart(isStart);
        getIssuerUID.generateConcatString(query);
        isStart = getIssuerUID.isStart();

        getActionType.setStart(isStart);
        getActionType.generateConcatString(query);
        isStart = getActionType.isStart();

        geDateIssued.setStart(isStart);
        geDateIssued.generateConcatString(query);
        isStart = geDateIssued.isStart();

        getReason.setStart(isStart);
        getReason.generateConcatString(query);
        isStart = getReason.isStart();

        getExpireDate.setStart(isStart);
        getExpireDate.generateConcatString(query);

        if (query.toString().toLowerCase().trim().equals("select")){
           throw new CommandRuntimeException("Nothing was selected!");
        }

        if (usingPlayerUID) {
            query.append(" FROM users WHERE Player_UID = ?");
        } else {
            query.append(" FROM users WHERE Player_Name = ?");
        }

        return query.toString();
    }
}
