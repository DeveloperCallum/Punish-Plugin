package unit.commands.common.base;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import java.util.Map;

public abstract class BaseItemStack extends ItemStack {
    protected BaseItemStack() {
        super();
    }

    public BaseItemStack(int type) {
        super(type);
    }

    public BaseItemStack(Material type) {
        super(type);
    }

    public BaseItemStack(int type, int amount) {
        super(type, amount);
    }

    public BaseItemStack(Material type, int amount) {
        super(type, amount);
    }

    public BaseItemStack(int type, int amount, short damage) {
        super(type, amount, damage);
    }

    public BaseItemStack(Material type, int amount, short damage) {
        super(type, amount, damage);
    }

    public BaseItemStack(int type, int amount, short damage, Byte data) {
        super(type, amount, damage, data);
    }

    public BaseItemStack(Material type, int amount, short damage, Byte data) {
        super(type, amount, damage, data);
    }

    public BaseItemStack(ItemStack stack) throws IllegalArgumentException {
        super(stack);
    }

    @Override
    public Material getType() {
        return super.getType();
    }

    @Override
    public void setType(Material type) {
        super.setType(type);
    }

    @Override
    public int getTypeId() {
        return super.getTypeId();
    }

    @Override
    public void setTypeId(int type) {
        super.setTypeId(type);
    }

    @Override
    public int getAmount() {
        return super.getAmount();
    }

    @Override
    public void setAmount(int amount) {
        super.setAmount(amount);
    }

    @Override
    public MaterialData getData() {
        return super.getData();
    }

    @Override
    public void setData(MaterialData data) {
        super.setData(data);
    }

    @Override
    public void setDurability(short durability) {
        super.setDurability(durability);
    }

    @Override
    public short getDurability() {
        return super.getDurability();
    }

    @Override
    public int getMaxStackSize() {
        return super.getMaxStackSize();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public boolean isSimilar(ItemStack stack) {
        return super.isSimilar(stack);
    }

    @Override
    public ItemStack clone() {
        return super.clone();
    }

    @Override
    public boolean containsEnchantment(Enchantment ench) {
        return super.containsEnchantment(ench);
    }

    @Override
    public int getEnchantmentLevel(Enchantment ench) {
        return super.getEnchantmentLevel(ench);
    }

    @Override
    public Map<Enchantment, Integer> getEnchantments() {
        return super.getEnchantments();
    }

    @Override
    public void addEnchantments(Map<Enchantment, Integer> enchantments) {
        super.addEnchantments(enchantments);
    }

    @Override
    public void addEnchantment(Enchantment ench, int level) {
        super.addEnchantment(ench, level);
    }

    @Override
    public void addUnsafeEnchantments(Map<Enchantment, Integer> enchantments) {
        super.addUnsafeEnchantments(enchantments);
    }

    @Override
    public void addUnsafeEnchantment(Enchantment ench, int level) {
        super.addUnsafeEnchantment(ench, level);
    }

    @Override
    public int removeEnchantment(Enchantment ench) {
        return super.removeEnchantment(ench);
    }

    @Override
    public Map<String, Object> serialize() {
        return super.serialize();
    }

    @Override
    public ItemMeta getItemMeta() {
        return super.getItemMeta();
    }

    @Override
    public boolean hasItemMeta() {
        return super.hasItemMeta();
    }

    @Override
    public boolean setItemMeta(ItemMeta itemMeta) {
        return super.setItemMeta(itemMeta);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }
}
