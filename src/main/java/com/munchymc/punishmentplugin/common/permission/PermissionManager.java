package com.munchymc.punishmentplugin.common.permission;

import org.bukkit.entity.Player;

import java.util.List;
import java.util.Set;

public interface PermissionManager {

    /**
     * Add the created permission to the player, so it can be used checked by "Player.hasPermission".
     * @param player The player who has the permission
     * @param permStr The name of the permission, for example "object.use".
     */
    void addPermToPlayer(Player player, String permStr);

    /**
     * Remove a registered permission from the player, so it can be used checked by "Player.hasPermission".
     * @param player The player who has the permission
     * @param permStr The name of the permission, for example "object.use".
     */
    void removePermFromPlayer(Player player, String permStr);

    void removeAllPermFromPlayer(Player player);

    /**
     * Check if the player has a permission.
     * @param player The player who has the permission
     * @param permStr The name of the permission, for example "object.use".
     * @return true if the player has the permission.
     * @deprecated
     */
    @Deprecated
    boolean playerHasPermission(Player player, String permStr);
}
