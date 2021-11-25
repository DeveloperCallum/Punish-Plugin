package com.munchymc.punishmentplugin.common.inventory.deprecated.page;

import org.bukkit.inventory.Inventory;
@Deprecated
public abstract class BasicPage extends Page {
    private Inventory inventory;

    protected abstract Inventory create();

    protected void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    @Override
    public Inventory getPageInventory() {
        if (inventory == null){
            inventory = create();
        }

        return inventory;
    }
}
