package com.munchymc.punishmentplugin.bukkit.permissions;

import com.munchymc.punishmentplugin.common.permission.PermissionManager;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.Plugin;

import java.util.*;


public class PermControl implements PermissionManager {
    private Plugin plugin;
    private final Map<UUID, PermissionAttachment> registered = new HashMap<>();

    public PermControl(Plugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Add the created permission to the player, so it can be used checked by "Player.hasPermission".
     * @param player The player who has the permission
     * @param permStr The name of the permission, for example "object.use".
     */
    @Override
    public void addPermToPlayer(Player player, String permStr) {
        PermissionAttachment perm = registered.getOrDefault(player.getUniqueId(), player.addAttachment(plugin));
        perm.setPermission("punish." + permStr.toLowerCase(), true);
        System.out.println("punish." + permStr.toLowerCase());

        registered.put(player.getUniqueId(), perm);
    }

    /**
     * Remove a registered permission from the player, so it can be used checked by "Player.hasPermission".
     * @param player The player who has the permission
     * @param permStr The name of the permission, for example "object.use".
     */
    @Override
    public void removePermFromPlayer(Player player, String permStr) {
        PermissionAttachment perm = registered.get(player.getUniqueId());
        perm.unsetPermission("punish." + permStr.toLowerCase());
    }

    @Override
    public void removeAllPermFromPlayer(Player player) {
        for (Map.Entry<String, Boolean> entry : registered.get(player.getUniqueId()).getPermissions().entrySet()) {
            String permString = entry.getKey();
            Boolean aBoolean = entry.getValue();

            removePermFromPlayer(player, permString);
        }

        registered.remove(player.getUniqueId());
    }

    /**
     * Check if the player has a permission.
     * @param player The player who has the permission
     * @param permStr The name of the permission, for example "object.use".
     * @return true if the player has the permission.
     * @deprecated
     */
    @Override @Deprecated
    public boolean playerHasPermission(Player player, String permStr) {
        return player.hasPermission("punish." + permStr.toLowerCase());
    }
}
