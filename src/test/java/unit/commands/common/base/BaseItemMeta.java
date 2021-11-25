package unit.commands.common.base;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class BaseItemMeta implements ItemMeta {
    @Override
    public boolean hasDisplayName() {
        return false;
    }

    @Override
    public String getDisplayName() {
        return null;
    }

    @Override
    public void setDisplayName(String s) {

    }

    @Override
    public boolean hasLore() {
        return false;
    }

    @Override
    public List<String> getLore() {
        return null;
    }

    @Override
    public void setLore(List<String> list) {

    }

    @Override
    public boolean hasEnchants() {
        return false;
    }

    @Override
    public boolean hasEnchant(Enchantment enchantment) {
        return false;
    }

    @Override
    public int getEnchantLevel(Enchantment enchantment) {
        return 0;
    }

    @Override
    public Map<Enchantment, Integer> getEnchants() {
        return null;
    }

    @Override
    public boolean addEnchant(Enchantment enchantment, int i, boolean b) {
        return false;
    }

    @Override
    public boolean removeEnchant(Enchantment enchantment) {
        return false;
    }

    @Override
    public boolean hasConflictingEnchant(Enchantment enchantment) {
        return false;
    }

    @Override
    public void addItemFlags(ItemFlag... itemFlags) {

    }

    @Override
    public void removeItemFlags(ItemFlag... itemFlags) {

    }

    @Override
    public Set<ItemFlag> getItemFlags() {
        return null;
    }

    @Override
    public boolean hasItemFlag(ItemFlag itemFlag) {
        return false;
    }

    @Override
    public ItemMeta clone() {
        return null;
    }

    @Override
    public Spigot spigot() {
        return null;
    }

    @Override
    public Map<String, Object> serialize() {
        return null;
    }
}
