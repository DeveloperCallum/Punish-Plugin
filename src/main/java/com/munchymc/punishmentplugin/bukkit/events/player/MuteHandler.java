package com.munchymc.punishmentplugin.bukkit.events.player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

public class MuteHandler implements Listener {
    private static HashMap< UUID, MuteData> playerMuteList = new HashMap<>();

    @EventHandler(ignoreCancelled = true)
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        if (playerMuteList.containsKey(player.getUniqueId())){
            if (playerMuteList.get(player.getUniqueId()).isMuted()){
                player.sendMessage("You are muted!");
                event.setCancelled(true);
            }
        }
    }

    public static void addMute(UUID player, Timestamp expiresAt){
        playerMuteList.put(player, new MuteData(player, expiresAt));
    }

    public static void removeMute(UUID player){
        playerMuteList.remove(player);
    }

    public static class MuteData{
        private Timestamp expiresAt;
        private UUID player;

        public MuteData(UUID player, Timestamp expiresAt) {
            this.player = player;
            this.expiresAt = expiresAt;
        }

        public MuteData(UUID player) {
            this.player = player;
            this.expiresAt = null;
        }

        public boolean isMuted() {
            if (expiresAt == null){
                return false;
            }

            return new Date().before(expiresAt);
        }

        public Timestamp getExpiresAt() {
            return expiresAt;
        }

        public UUID getPlayer() {
            return player;
        }
    }
}
