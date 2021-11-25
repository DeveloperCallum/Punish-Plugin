package unit.commands.tests;

import com.munchymc.punishmentplugin.bukkit.events.player.MuteHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.junit.Assert;
import org.junit.Test;
import unit.commands.bukkit.FakePlayer;
import unit.commands.bukkit.FakePluginManager;
import unit.commands.common.TestCommand;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.*;

public class EventTest extends TestCommand {

    @Test
    public void MuteTest() throws NoSuchFieldException, IllegalAccessException {
        FakePlayer sender = new FakePlayer();
        sender.setName("Sender");

        FakePlayer receiver = new FakePlayer();
        receiver.setName("Receiver");

        Set<Player> receivers = new HashSet<>(Collections.singletonList((Player) receiver));

        setPluginManager(new FakePluginManager());

        MuteHandler playerChat = new MuteHandler();
        HashMap<UUID, MuteHandler.MuteData> playerMuteList = new HashMap<>();
        Field playerMuteListField = MuteHandler.class.getDeclaredField("playerMuteList");
        playerMuteListField.setAccessible(true);
        Timestamp timestamp = new Timestamp(2022, 9, 17, 1, 0, 0, 0);

        playerMuteList.put(sender.getUniqueId(), new MuteHandler.MuteData(new FakePlayer().getUniqueId(), timestamp));
        playerMuteListField.set(playerChat, playerMuteList);

        Bukkit.getPluginManager().registerEvents(playerChat, getFakePlugin());

        AsyncPlayerChatEvent playerChatEvent = new AsyncPlayerChatEvent(true, sender, "I HATE PEOPLE", receivers);
        Bukkit.getPluginManager().callEvent(playerChatEvent);

        Assert.assertTrue(sender.getLastMessage().toLowerCase().contains("muted"));
        Assert.assertTrue(receiver.getMessageList().size() <= 0);
    }
}

//TODO: Check if a player is muted onJoin & When they get muted add them to the list.