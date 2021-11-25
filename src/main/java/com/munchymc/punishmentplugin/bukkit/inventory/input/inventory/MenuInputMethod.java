package com.munchymc.punishmentplugin.bukkit.inventory.input.inventory;

import com.munchymc.punishmentplugin.common.inventory.InventoryMenu;
import com.munchymc.punishmentplugin.common.inventory.input.CustomisableInputMethod;
import com.munchymc.punishmentplugin.common.inventory.input.InputCallback;
import com.munchymc.punishmentplugin.exceptions.inputmethod.InputMethodRuntimeException;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

public class MenuInputMethod implements CustomisableInputMethod<String, Listener> {
    private String data;
    private Plugin plugin;
    private Player currentPlayer;
    private Inventory inventory = null;
    private InputCallback<String> callback = null;
    private final InventoryMenu inventoryMenu;
    private boolean hasFinished = false;

    public MenuInputMethod(Plugin plugin, InventoryMenu inventoryMenu) {
        this.plugin = plugin;
        this.inventoryMenu = inventoryMenu;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public void addCustomisation(Listener customise) {
        Bukkit.getServer().getPluginManager().registerEvents(customise, plugin);
    }

    @Override
    public String getData() {
        return data;
    }

    @Override
    public void execute(InputCallback<String> callback) {
        if(hasFinished || this.callback == null){
            this.callback = callback;
            openMenu();
            return;
        }

        throw new InputMethodRuntimeException("Input method already executing!");
    }

    public void setPlayer(Player player){
        this.currentPlayer = player;
    }

    private void openMenu(){
        if (this.inventory == null){
            this.inventory = inventoryMenu.create();
        }

        this.currentPlayer.openInventory(this.inventory);
    }

    public void completed(){
        this.hasFinished = true;
        callback.run(getData());
    }

    public Inventory getInventory() {
        return inventory;
    }
}
