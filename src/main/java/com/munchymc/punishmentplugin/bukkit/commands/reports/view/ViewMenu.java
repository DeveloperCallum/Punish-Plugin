package com.munchymc.punishmentplugin.bukkit.commands.reports.view;

import com.munchymc.punishmentplugin.bukkit.commands.reports.view.page.ReportsPages;
import com.munchymc.punishmentplugin.bukkit.database.actions.query.report.GetReport;
import com.munchymc.punishmentplugin.common.database.wrappers.tables.reports.ReportsTable;
import com.munchymc.punishmentplugin.exceptions.command.CommandNoDataException;
import com.munchymc.punishmentplugin.common.database.Database;
import com.munchymc.punishmentplugin.common.inventory.deprecated.PlainMenu;
import com.munchymc.punishmentplugin.common.inventory.deprecated.page.Page;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class ViewMenu extends PlainMenu {
    private final Database database;
    private Plugin plugin;

    public ViewMenu(Database database, Plugin plugin) {
        this.database = database;
        this.plugin = plugin;
    }

    @Override
    protected Inventory createProcess() {
        GetReport getReport = new GetReport(database, plugin);
        List<ReportsTable> tableList = getReport.executeQuery();

        if (tableList == null) {
            throw new CommandNoDataException("All reports have been handled or no reports exist.", new Throwable());
        }

        Page staticPage = new ReportsPages(tableList);
        this.addPage(staticPage);

        return staticPage.getPageInventory();
    }
}
