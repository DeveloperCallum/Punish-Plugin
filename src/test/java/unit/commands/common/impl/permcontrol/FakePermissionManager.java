package unit.commands.common.impl.permcontrol;

import com.munchymc.punishmentplugin.bukkit.permissions.PermControl;
import org.bukkit.plugin.Plugin;

public class FakePermissionManager extends PermControl {

    public FakePermissionManager(Plugin plugin) {
        super(plugin);
    }
}
