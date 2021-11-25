package com.munchymc.punishmentplugin.bukkit.commands.reports.view.page;

import com.munchymc.punishmentplugin.common.database.wrappers.tables.reports.ReportsTable;
import com.munchymc.punishmentplugin.common.inventory.deprecated.page.StaticPage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ReportsPages extends StaticPage {
    private final List<ReportsTable> tableList;

    public ReportsPages(List<ReportsTable> tableList) {
        super();
        this.tableList = tableList;
    }

    @Override
    protected Inventory create() {
        Inventory inv = Bukkit.createInventory(null, (9 * 3), "Unhandled Reports");

        tableList.forEach(reportsTable -> {
            ItemStack dataItem = new ItemStack(Material.PAPER);
            ItemMeta itemMeta = dataItem.getItemMeta();

            itemMeta.setDisplayName(reportsTable.getReportUID().toString());

            itemMeta.setDisplayName(reportsTable.getReportUID().toString());

            List<String> lore = new LinkedList<>(Arrays.asList("Reported: " + reportsTable.getReportedUsername(), "Submitter: " + reportsTable.getSubmitterUsername(), "Reason: " + reportsTable.getReason()));
            itemMeta.setLore(lore);

            dataItem.setItemMeta(itemMeta);
            inv.addItem(dataItem);
        });

        return inv;
    }
}
