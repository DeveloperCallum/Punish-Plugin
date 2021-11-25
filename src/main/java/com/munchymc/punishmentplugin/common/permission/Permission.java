package com.munchymc.punishmentplugin.common.permission;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Permission {
    private final String name;
    private final List<Player> players = new ArrayList<>();

    public Permission(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public List<Player> getPlayers() {
        return new ArrayList<>(players);
    }

    public void addPLayer(Player player){
        players.add(player);
    }

    public boolean hasPlayer(Player player){
        for (Player sPlayer : players){
            if (sPlayer.getUniqueId().equals(player.getUniqueId())){
                return true;
            }
        }

        return false;
    }

    public void removePlayer(Player player){
        players.remove(player);
    }
}
