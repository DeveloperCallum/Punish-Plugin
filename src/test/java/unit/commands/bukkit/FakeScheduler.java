package unit.commands.bukkit;

import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;
import unit.commands.common.base.BaseScheduler;

public class FakeScheduler extends BaseScheduler {
    @Override
    public BukkitTask runTaskAsynchronously(Plugin plugin, Runnable runnable) throws IllegalArgumentException {
        runnable.run();
        return null;
    }
}
