package com.munchymc.punishmentplugin.bukkit.commands.reports.view;

import com.munchymc.punishmentplugin.bukkit.database.actions.query.action.deprecated.ActionNameQuery;
import com.munchymc.punishmentplugin.common.database.wrappers.tables.actions.ActionsTable;
import com.munchymc.punishmentplugin.common.database.Database;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import com.munchymc.punishmentplugin.common.inventory.deprecated.page.StaticPage;
import org.bukkit.plugin.Plugin;

public class ReportMenu extends StaticPage {
    private final Database database;
    private Plugin plugin;

    public ReportMenu(Database database, Plugin plugin) {
        this.database = database;
        this.plugin = plugin;
        create();
    }

    @Override
    protected Inventory create() {
        Inventory inv = Bukkit.createInventory(null, 18, "Reports Menu: Select Reason");

        ActionNameQuery actionsQuery = new ActionNameQuery(database, plugin);
        List<ActionsTable> actionTable = actionsQuery.executeQuery();

        if (actionsQuery == null){
            return null;
        }

        //TODO: Multi-Menu Support
        for (ActionsTable action : actionTable){
            String actionName = action.getName();

            ItemStack reportItem = new ItemStack(Material.PAPER);
            ItemMeta reportMeta = reportItem.getItemMeta();

            reportMeta.setDisplayName(actionName);
            reportItem.setItemMeta(reportMeta);

            inv.addItem(reportItem);
        }

        return inv;
    }
}
