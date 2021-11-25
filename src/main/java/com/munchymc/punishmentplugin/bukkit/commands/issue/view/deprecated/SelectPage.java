package com.munchymc.punishmentplugin.bukkit.commands.issue.view.deprecated;

import com.munchymc.punishmentplugin.bukkit.database.actions.query.action.ActionsQuery;
import com.munchymc.punishmentplugin.bukkit.database.actions.query.action.ActionParameterData;
import com.munchymc.punishmentplugin.bukkit.database.actions.query.action.GetAllActions;
import com.munchymc.punishmentplugin.common.database.Database;
import com.munchymc.punishmentplugin.common.database.wrappers.tables.actions.ActionsTable;
import com.munchymc.punishmentplugin.common.inventory.deprecated.page.StaticPage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.List;

public class SelectPage extends StaticPage {
    private Database database;
    private Plugin plugin;

    public SelectPage(Database database, Plugin plugin) {
        this.database = database;
        this.plugin = plugin;
    }

    @Override
    protected Inventory create() {
        Inventory inventory = Bukkit.createInventory(null, 9 * 4, "Select Player Action");

//        ActionsQuery actionsQuery = new ActionsQuery(database, plugin);
//
//        ActionParameterData actionParameterData = actionsQuery.getParameterData();
//        actionParameterData.getSelectData().selectName(true);
//        actionParameterData.getSelectData().selectRespondingAction(true);
//        actionParameterData.getSelectData().selectUsagePermission(true);
//        actionParameterData.getSelectData().selectDefaultDuration(true);
//
//        List<ActionsTable> data = actionsQuery.executeQuery();

        GetAllActions getQuery = new GetAllActions(database, plugin);
        List<ActionsTable> data = getQuery.executeQuery();

        data.forEach(actionsTable -> {
            //Check that the player has this permission!
            ItemStack displayItem = new ItemStack(Material.PAPER);
            ItemMeta itemMeta = displayItem.getItemMeta();

            itemMeta.setDisplayName(actionsTable.getName());
            itemMeta.setLore(Arrays.asList("Permission Needed: " + actionsTable.getPermission(), "Resulting Action: " + actionsTable.getRespondingAction()));

            displayItem.setItemMeta(itemMeta);
            inventory.addItem(displayItem);
        });

        return inventory;
    }
}
