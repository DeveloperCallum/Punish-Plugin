package com.munchymc.punishmentplugin.bukkit.commands.issue.view;

import com.munchymc.punishmentplugin.bukkit.database.actions.query.action.GetAllActions;
import com.munchymc.punishmentplugin.bukkit.database.actions.query.action.GetAllActionsOffset;
import com.munchymc.punishmentplugin.common.database.Database;
import com.munchymc.punishmentplugin.common.database.wrappers.tables.actions.ActionsTable;
import com.munchymc.punishmentplugin.common.inventory.AbstractPage;
import com.munchymc.punishmentplugin.util.InventoryUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.List;

public class SelectActionPage extends AbstractPage {
    private int offset;
    private Database database;
    private Plugin plugin;

    public SelectActionPage(Database database, Plugin plugin, int offset) {
        this.database = database;
        this.plugin = plugin;
        this.offset = offset;
    }

    public SelectActionPage(Database database, Plugin plugin) {
        this.database = database;
        this.plugin = plugin;
        offset = 0;
    }

    @Override
    protected Inventory createProcess() {
        Inventory inventory = Bukkit.createInventory(null, 9 * 4, "Select Player Action");
        GetAllActions getQuery = new GetAllActionsOffset(database, plugin, offset);
        List<ActionsTable> data = getQuery.executeQuery();

        if(data == null){
            return null;
        }

        data.forEach(actionsTable -> {
            //Check that the player has this permission!
            ItemStack displayItem = new ItemStack(Material.PAPER);
            ItemMeta itemMeta = displayItem.getItemMeta();

            itemMeta.setDisplayName(actionsTable.getName());
            itemMeta.setLore(Arrays.asList("Permission Needed: " + actionsTable.getPermission(), "Resulting Action: " + actionsTable.getRespondingAction()));

            displayItem.setItemMeta(itemMeta);
            inventory.addItem(displayItem);
        });

        inventory.setItem(36 - 1, InventoryUtil.createItem(Material.WOOL, (byte) 1, "Forwards"));
        inventory.setItem(36 - 9, InventoryUtil.createItem(Material.WOOL, (byte) 1, "Backwards"));

        return inventory;
    }

    public int getOffset() {
        return offset;
    }
}
