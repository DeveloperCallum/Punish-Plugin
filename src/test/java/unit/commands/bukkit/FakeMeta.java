package unit.commands.bukkit;

import org.bukkit.inventory.meta.ItemMeta;
import unit.commands.common.base.BaseItemMeta;

public class FakeMeta extends BaseItemMeta {
    String name = null;

    @Override
    public boolean hasDisplayName() {
        return name != null;
    }

    @Override
    public String getDisplayName() {
        return name;
    }

    @Override
    public void setDisplayName(String s) {
        name = s;
    }

    @Override
    public ItemMeta clone() {
        FakeMeta itemMeta = new FakeMeta();
        itemMeta.name = this.name;

        return itemMeta;
    }
}
