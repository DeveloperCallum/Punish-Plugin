package com.munchymc.punishmentplugin.util;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public final class InventoryUtil {

    public static ItemStack createItem(Material material, String DisplayText){
        ItemStack item = new ItemStack(material);

        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(DisplayText);

        item.setItemMeta(itemMeta);

        return item;
    }

    public static ItemStack createItem(Material material, byte id, String DisplayText){
        ItemStack item = new ItemStack(material, 1, id);

        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(DisplayText);

        item.setItemMeta(itemMeta);

        return item;
    }

    public static ItemStack createItem(Material material, String DisplayText, List<String> lore){
        ItemStack item = new ItemStack(material);

        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(DisplayText);
        itemMeta.setLore(lore);

        item.setItemMeta(itemMeta);

        return item;
    }

    public static ItemStack createItem(Material material, byte id, String DisplayText, List<String> lore){
        ItemStack item = new ItemStack(material,1, id);

        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(DisplayText);
        itemMeta.setLore(lore);

        item.setItemMeta(itemMeta);

        return item;
    }
}
