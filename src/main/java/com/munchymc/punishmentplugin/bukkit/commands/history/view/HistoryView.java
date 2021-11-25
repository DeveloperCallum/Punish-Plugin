package com.munchymc.punishmentplugin.bukkit.commands.history.view;

import com.munchymc.punishmentplugin.bukkit.database.actions.query.punish.GetPlayerHistoryLimited;
import com.munchymc.punishmentplugin.common.database.Database;
import com.munchymc.punishmentplugin.common.database.wrappers.tables.punishments.PunishTable;
import com.munchymc.punishmentplugin.common.inventory.AbstractPage;
import com.munchymc.punishmentplugin.util.InventoryUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.Plugin;

import java.util.*;

public class HistoryView extends AbstractPage {
    private final Database database;
    private Player owner;
    private final UUID target;
    private final int offset;
    private Plugin plugin;
    private Inventory inventory;

    public HistoryView(Database database, UUID target, int offset) {
        this.database = database;
        this.target = target;
        this.offset = offset;
    }

    @Override
    protected Inventory createProcess() {
        if (inventory != null) {
            return inventory;
        }

        GetPlayerHistoryLimited getPlayer = new GetPlayerHistoryLimited(plugin, database, target, offset, 18);
        List<PunishTable> punishTables = getPlayer.executeQuery();

        if (punishTables.isEmpty()) {
            return null;
        }

        Inventory inventory = Bukkit.createInventory(null, 36, "Punish History");

        for (PunishTable punishTable : punishTables) {
            ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
            SkullMeta skull = (SkullMeta) item.getItemMeta();


            if (punishTable.getExpire() != null) {
                if (new Date().before(punishTable.getExpire())) {
                    skull.setLore(Collections.singletonList(ChatColor.GRAY + "Status: " + ChatColor.RED + "BANNED"));
                } else {
                    skull.setLore(Collections.singletonList(ChatColor.GRAY + "Status: " + ChatColor.GREEN + "NOT BANNED"));
                }
            } else {
                skull.setLore(Collections.singletonList(ChatColor.GRAY + "Status: " + ChatColor.RED + "BANNED"));
            }

            int slot = 0;
            for (PunishTable data : punishTables) {
                if (slot == 0){
                    skull.setOwner(data.getTarget().getPlayerName());
                    skull.setDisplayName(data.getTarget().getPlayerName());

                    item.setItemMeta(skull);
                    inventory.setItem(4, item);
                }

                ItemStack itemStack = new ItemStack(Material.PAPER);
                ItemMeta itemMeta = itemStack.getItemMeta();

                itemMeta.setDisplayName(ChatColor.GOLD + data.getPunishmentUid().toString());

                List<String> loreData = new ArrayList<>();
                loreData.add(ChatColor.GRAY + "Player: " + ChatColor.AQUA + data.getTarget().getPlayerName());
                loreData.add(ChatColor.GRAY + "Expires: (YY/MM/DD) " + ChatColor.AQUA + (data.getExpire() == null ? "Never" : data.getExpire().toString()));
                loreData.add(ChatColor.GRAY + "Reason: " + data.getReason());
                loreData.add("");

                itemMeta.setLore(loreData);
                itemStack.setItemMeta(itemMeta);
                inventory.setItem(9 + slot++, itemStack);
            }

            inventory.setItem(36 - 1, InventoryUtil.createItem(Material.WOOL, (byte) 1, "Forwards"));
            inventory.setItem(36 - 9, InventoryUtil.createItem(Material.WOOL, (byte) 1, "Backwards"));
        }

        return inventory;
    }

    public int getOffset() {
        return offset;
    }
}
