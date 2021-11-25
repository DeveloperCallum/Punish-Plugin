package unit.commands.bukkit;
import unit.commands.common.base.BaseHumanEntity;

import java.util.*;

public class FakeSender extends BaseHumanEntity {
    protected List<String> messageList = new LinkedList<>();

    public List<String> getMessageList() {
        return new LinkedList<>(messageList);
    }

    @Override
    public void sendMessage(String s) {
        messageList.add(s);
    }

    @Override
    public void sendMessage(String[] strings) {
        for (String s : strings){
            sendMessage(s);
        }
    }
}
