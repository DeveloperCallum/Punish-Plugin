package unit.commands.bukkit;

import unit.commands.common.base.BasePlayer;
import java.util.*;

public class FakePlayer extends BasePlayer {
    private String name;
    private String displayName;
    protected List<String> messageList = new LinkedList<>();
    protected UUID uuid = UUID.randomUUID();

    public String getLastMessage() {
        return messageList.get(messageList.size() - 1);
    }

    public List<String> getMessageList() {
        return new LinkedList<>(messageList);
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public UUID getUniqueId() {
        return uuid;
    }

    @Override
    public void sendMessage(String s) {
        messageList.add(s);
    }

    @Override
    public void sendMessage(String[] strings) {
        for (String s : strings) {
            sendMessage(s);
        }
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String getName() {
        return name;
    }
}
