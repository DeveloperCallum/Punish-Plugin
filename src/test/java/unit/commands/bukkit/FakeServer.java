package unit.commands.bukkit;

import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scheduler.BukkitScheduler;
import unit.commands.common.base.BaseServer;

import java.util.*;

public class FakeServer extends BaseServer {
    FakeScheduler fakeScheduler = new FakeScheduler();
    FakePluginManager pluginManager = new FakePluginManager();

    List<Player> availablePlayers = new ArrayList<>();

    @Override
    public Player getPlayer(String s) {
        Player foundPlayer = null;

        for (Player player : availablePlayers) {
            if (player.getName().equals(s) || player.getName().equals(s)){
                foundPlayer = player;
            }
        }

        return foundPlayer;
    }

    @Override
    public Collection<? extends Player> getOnlinePlayers() {
        return null;
    }

    @Override
    public BukkitScheduler getScheduler() {
        return fakeScheduler;
    }

    public boolean addPlayer(Player player) {
        return availablePlayers.add(player);
    }

    public boolean removePlayer(Player player) {
        return availablePlayers.remove(player);
    }

    @Override
    public PluginManager getPluginManager() {
        return pluginManager;
    }

    @Override
    public void setPluginManager(FakePluginManager pluginManager) {
        this.pluginManager = pluginManager;
    }
}
