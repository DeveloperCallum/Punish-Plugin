package com.munchymc.punishmentplugin.bukkit.commands.history.view;

import com.munchymc.punishmentplugin.bukkit.database.actions.command.punishments.CreatePunishmentCommand;
import com.munchymc.punishmentplugin.common.database.Database;
import com.munchymc.punishmentplugin.common.inventory.AbstractMenu;
import com.munchymc.punishmentplugin.common.inventory.AbstractPage;
import com.munchymc.punishmentplugin.util.InventoryUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class HistoryMenu extends AbstractMenu {
    private final Database database;
    private final UUID target;
    private final Player owner;
    private final Plugin plugin;
    private int index = 0;

    public HistoryMenu(Database database, UUID player, Player owner, Plugin plugin) {
        this.database = database;
        this.target = player;
        this.owner = owner;
        this.plugin = plugin;

        HistoryView historyView = new HistoryView(this.database, this.target, 0);
        setFirst(historyView);

        Bukkit.getPluginManager().registerEvents(new ButtonListener(), this.plugin);
    }

    @Override
    public AbstractPage getNext() {
        AbstractPage current = getCurrent();

        if (current.hasNext()) {
            index++;
            return current.getNext();
        }

        if (current instanceof HistoryView) {
            HistoryView hCurrent = (HistoryView) current;

            //Add 18 because that is the page size.
            HistoryView next = new HistoryView(database, target, hCurrent.getOffset() + 18);

            if (next == null) {
                return null;
            }

            index++;
            current.setNext(next);
            next.setLast(current);
            return next;
        }

        return null;
    }

    @Override
    public AbstractPage getLast() {
        AbstractPage current = getCurrent();

        if (current.hasLast()) {
            index--;
            return current.getLast();
        }

        return null;
    }

    @Override
    public AbstractPage getCurrent() {
        return getCurrentRec(getFirst(), index);
    }

    private class ButtonListener implements Listener {
        @EventHandler(ignoreCancelled = true)
        public void onInventoryClick(InventoryClickEvent event) {
            if (!event.getClickedInventory().getTitle().equals(getCurrent().create().getTitle())) {
                return;
            }

            event.setCancelled(true);

            if (!event.getWhoClicked().getUniqueId().equals(owner.getUniqueId())) {
                return;
            }

            if (event.getCurrentItem() == null) {
                return;
            }

            if (event.getCurrentItem().getItemMeta() == null) {
                return;
            }

            //FIXME: Colour Strip
            if (event.getCurrentItem().getItemMeta().getDisplayName().toLowerCase().equals("forwards")) {
                AbstractPage next = getNext();
                event.getWhoClicked().closeInventory();

                if (next == null) {
                    return;
                }

                event.getWhoClicked().openInventory(next.create());
                return;
            }

            if (event.getCurrentItem().getItemMeta().getDisplayName().toLowerCase().equals("backwards")) {
                event.getWhoClicked().closeInventory();
                event.getWhoClicked().openInventory(getLast().create());
                return;
            }

            List<String> data = event.getCurrentItem().getItemMeta().getLore();

            if (ChatColor.stripColor(data.get(data.size() - 1)).equals("Click again to confirm")){
                String PID = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());

                CreatePunishmentCommand delete = new CreatePunishmentCommand(database, UUID.fromString(PID));
                delete.unExecuteCommand();

                ItemMeta originalMeta = event.getCurrentItem().getItemMeta();
                List<String> newLore = new LinkedList<>(originalMeta.getLore());
                newLore.add(ChatColor.RED + "Deleted!");

                ItemStack itemStack = InventoryUtil.createItem(Material.WOOL, (byte) 15, "");
                originalMeta.setLore(newLore);
                itemStack.setItemMeta(originalMeta);

                event.setCurrentItem(itemStack);
            }else{
                ItemMeta originalMeta = event.getCurrentItem().getItemMeta();
                List<String> newLore = new LinkedList<>(originalMeta.getLore());
                newLore.add(ChatColor.GOLD + "Click again to confirm");

                ItemStack itemStack = InventoryUtil.createItem(Material.WOOL, (byte) 7, "");
                originalMeta.setLore(newLore);
                itemStack.setItemMeta(originalMeta);

                event.setCurrentItem(itemStack);
            }

//            String PID =  ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
//            event.getWhoClicked().sendMessage("Deleting: " + PID);
//
//            CreatePunishmentCommand delete = new CreatePunishmentCommand(database, UUID.fromString(PID));
//            delete.unExecuteCommand();
//
//            ItemMeta data = event.getCurrentItem().getItemMeta();
//
//            ItemStack itemStack = InventoryUtil.createItem(Material.WOOL, (byte) 14, "");
//            itemStack.setItemMeta(data);
//
//            event.setCurrentItem(itemStack);
        }
    }

    @Override
    protected void finalize() {
        HandlerList.unregisterAll(this);
        System.out.println("Unregistered");
    }
}

//TODO: Test HistoryMenu. You just updated the menu to work dynamically loading data when requested instead of all at once.