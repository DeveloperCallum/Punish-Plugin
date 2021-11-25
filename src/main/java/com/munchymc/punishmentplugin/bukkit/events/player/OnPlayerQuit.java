package com.munchymc.punishmentplugin.bukkit.events.player;

import com.munchymc.punishmentplugin.common.permission.PermissionManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class OnPlayerQuit implements Listener {
    private final PermissionManager permControl;

    public OnPlayerQuit(PermissionManager permControl) {
        this.permControl = permControl;
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void onPlayerQuit(PlayerQuitEvent event) {
        permControl.removeAllPermFromPlayer(event.getPlayer());
    }
}
