package com.munchymc.punishmentplugin.bukkit.inventory.input.chat;

import com.munchymc.punishmentplugin.common.inventory.input.InputCallback;
import com.munchymc.punishmentplugin.common.inventory.input.InputMethod;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.plugin.Plugin;

public class ChatInputMethod implements InputMethod<String>, Listener {
    private Player player;
    private Plugin plugin;
    private String data;
    private InputCallback<String> callback;

    public ChatInputMethod(Player player, Plugin plugin) {
        this.player = player;
        this.plugin = plugin;
    }

    @Override
    public String getData() {
        return data;
    }

    public void execute(InputCallback<String> callback) {
        this.callback = callback;
        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerChat(PlayerChatEvent event) {
        if (!event.getPlayer().getUniqueId().equals(player.getUniqueId())) {
            return;
        }

        data = event.getMessage();
        event.setCancelled(true);

        PlayerChatEvent.getHandlerList().unregister(this);
        System.out.println("Event Unregistered -> PlayerChatEvent \\ ChatInputMethod");

        if (callback == null) {
            return;
        }

        callback.run(data);
    }
}
