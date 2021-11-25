package unit.commands.bukkit;

import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;
import unit.commands.common.base.BasePluginManager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class FakePluginManager extends BasePluginManager {
    private static final HashMap<Class<? extends Listener>, HashSet<Listener>> events = new HashMap<>();

    @Override
    public void registerEvents(Listener listener, Plugin plugin) {
        HashSet<Listener> data = events.getOrDefault(listener.getClass(), new HashSet<>());
        data.add(listener);

        events.put(listener.getClass(), data);
    }

    @Override
    public void callEvent(Event event) throws IllegalStateException {
        events.forEach((aClass, listeners) -> {
            for (Listener listener : listeners) {
                for (Method method : listener.getClass().getDeclaredMethods()) {
                    if (method.getParameterTypes()[0] == AsyncPlayerChatEvent.class) {
                        try {
                            listener.getClass().getDeclaredMethod(method.getName(), AsyncPlayerChatEvent.class).invoke(listener, event);
                        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }
}
