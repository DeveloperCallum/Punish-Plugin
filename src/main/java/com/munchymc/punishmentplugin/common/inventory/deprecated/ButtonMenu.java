package com.munchymc.punishmentplugin.common.inventory.deprecated;

import com.munchymc.punishmentplugin.common.inventory.deprecated.PlainMenu;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.LinkedList;
import java.util.List;
@Deprecated
public abstract class ButtonMenu<T> extends PlainMenu {
    private final List<T> dataSet;
    private final int paddedSize;
    private int pagesRequired = -1;
    private final int pageSize;

    private int current = 0;

    public ButtonMenu(List<T> dataSet, int pageSize) {
        this.dataSet = dataSet;
        //this.rawSize = dataSet.size();
        this.pageSize = pageSize;
        this.paddedSize = pageSize + 9;
    }

    protected int calculateNeededPages() {
        if (pagesRequired == -1) {
            if ( dataSet.size() % pageSize == 0){
                pagesRequired =  dataSet.size() / pageSize;
            }else{
                pagesRequired = ( dataSet.size() - ( dataSet.size() % pageSize) + pageSize) / pageSize;
            }
        }

        return pagesRequired;
    }

    protected List<T> getDataSet() {
        return new LinkedList<>(dataSet);
    }

    protected int getPagesRequired() {
        return pagesRequired;
    }

    protected int getPaddedSize() {
        return paddedSize;
    }

    protected int getPageSize() {
        return pageSize;
    }

    protected Inventory getNext(){
        return getPage(++current);
    }

    protected Inventory getLast(){
        return getPage(--current);
    }

    @Override
    public Inventory getPage(int number) {
        current = number;
        return super.getPage(number);
    }

    protected Inventory getCurrentPage(){
        return getPage(current);
    }

    @EventHandler(ignoreCancelled = true)
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getClickedInventory().equals(this.getCurrentPage())) { //FIXME null
            event.setCancelled(true);

            if (event.getCurrentItem() != null) {
                ItemStack item = event.getCurrentItem();
                String name = ChatColor.stripColor(item.getItemMeta().getDisplayName().toLowerCase());

                switch (name) {
                    case "last":
                        event.getWhoClicked().closeInventory();
                        event.getWhoClicked().openInventory(this.getLast());
                        break;

                    case "next":
                        event.getWhoClicked().closeInventory();
                        event.getWhoClicked().openInventory(this.getNext());
                        break;
                }
            }
        }
    }
}
