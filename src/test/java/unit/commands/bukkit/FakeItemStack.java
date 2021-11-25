package unit.commands.bukkit;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import unit.commands.common.base.BaseItemStack;

public class FakeItemStack extends BaseItemStack {
    FakeMeta meta = new FakeMeta();

    public FakeItemStack() {
    }

    public FakeItemStack(int type) {
        super(type);
    }

    public FakeItemStack(Material type) {
        super(type);
    }

    public FakeItemStack(int type, int amount) {
        super(type, amount);
    }

    public FakeItemStack(Material type, int amount) {
        super(type, amount);
    }

    public FakeItemStack(int type, int amount, short damage) {
        super(type, amount, damage);
    }

    public FakeItemStack(Material type, int amount, short damage) {
        super(type, amount, damage);
    }

    public FakeItemStack(int type, int amount, short damage, Byte data) {
        super(type, amount, damage, data);
    }

    public FakeItemStack(Material type, int amount, short damage, Byte data) {
        super(type, amount, damage, data);
    }

    public FakeItemStack(ItemStack stack) throws IllegalArgumentException {
        super(stack);
    }

    @Override
    public ItemMeta getItemMeta() {
        return meta.clone();
    }

    @Override
    public boolean hasItemMeta() {
        return meta != null;
    }

    @Override
    public boolean setItemMeta(ItemMeta itemMeta) {
        this.meta = (FakeMeta) itemMeta;
        return true;
    }
}
