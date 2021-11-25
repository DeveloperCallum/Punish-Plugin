package com.munchymc.punishmentplugin.common.inventory;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public interface InventoryMenu {
    Inventory create();
}
