package unit.commands.bukkit;

import org.bukkit.Material;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import unit.commands.common.base.BaseInventory;

public class FakeInventory extends BaseInventory {
    private ItemStack[] data;

    public FakeInventory(InventoryHolder inventoryHolder, int i, String s) {
        data = new ItemStack[i];

        for (int x = 0; x < i; x++){
            data[x] = new FakeItemStack(Material.AIR);
        }
    }

    @Override
    public void setItem(int i, ItemStack itemStack) {
        data[i] = itemStack;
    }

    @Override
    public ItemStack getItem(int i) {
        return data[i];
    }
}
