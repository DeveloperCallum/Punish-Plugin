package unit.commands.common;

import com.munchymc.punishmentplugin.bukkit.commands.executor.CommandExecutor;
import com.munchymc.punishmentplugin.bukkit.commands.permissions.Permissions;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import unit.commands.bukkit.*;
import unit.commands.common.base.BaseServer;
import unit.commands.common.impl.database.BaseDatabase;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class TestCommand {
    private final FakePlugin fakePlugin = new FakePlugin();
    private final BaseDatabase database = BaseDatabase.getInstance();
    private final FakePlayer fakeSender = new FakePlayer();
    private CommandExecutor executor;

    public TestCommand() {
        //Set Bukkit 'Server' field

        try {
            injectServer(new FakeServer());

            //Create a command executor
            executor = new CommandExecutor(fakePlugin);
            executor.registerCommand(new Permissions(database, null, fakePlugin));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    protected void injectServer(Server server) throws NoSuchFieldException, IllegalAccessException {
        Field serverField = Bukkit.class.getDeclaredField("server");
        serverField.setAccessible(true);
        serverField.set(Bukkit.class, server);

        assert Bukkit.getServer().equals(server);
    }

    protected FakePlugin getFakePlugin() {
        return fakePlugin;
    }

    protected BaseDatabase getDatabase() {
        return database;
    }

    protected FakePlayer getFakeSender() {
        return fakeSender;
    }

    protected CommandExecutor getExecutor() {
        return executor;
    }

    protected void executeCommand(String chatInput){
        String[] data = chatInput.split(" ");

        AtomicInteger x = new AtomicInteger();
        getExecutor().onCommand(getFakeSender(), null, data[0], Arrays.stream(data).filter(s -> x.getAndIncrement() > 0).toArray(String[]::new));
    }

    /**
     * @param fakePluginManager set Bukkit.getPluginManger() : PluginManager to this class instance class.
     * @see Bukkit
    **/
    protected void setPluginManager(FakePluginManager fakePluginManager){
        if (Bukkit.getServer() instanceof BaseServer){
            BaseServer baseServer = (BaseServer) Bukkit.getServer();
            baseServer.setPluginManager(fakePluginManager);
        }
    }
}
