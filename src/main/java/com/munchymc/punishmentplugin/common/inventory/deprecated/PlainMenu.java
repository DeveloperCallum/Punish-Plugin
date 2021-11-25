package com.munchymc.punishmentplugin.common.inventory.deprecated;

import com.munchymc.punishmentplugin.common.inventory.InventoryMenu;
import com.munchymc.punishmentplugin.common.inventory.deprecated.page.Page;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;

import java.util.LinkedList;
import java.util.List;

@Deprecated
public abstract class PlainMenu implements InventoryMenu, Listener {
    List<Page> pages = new LinkedList<>();

    public Inventory getPage(int number){
        return pages.get(number).getPageInventory();
    }

    protected void addPage(Page page){
        pages.add(page);
    }

    protected void removePage(int number){
        pages.remove(number);
    }

    protected abstract Inventory createProcess();

    @Override
    public Inventory create() {
        return createProcess();
    }
}
