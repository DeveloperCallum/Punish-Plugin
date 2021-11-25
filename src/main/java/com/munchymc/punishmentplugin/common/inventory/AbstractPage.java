package com.munchymc.punishmentplugin.common.inventory;

import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;

public abstract class AbstractPage implements Listener {
    private AbstractPage next;
    private AbstractPage last;

    public AbstractPage getNext() {
        return next;
    }

    public boolean hasNext() {
        return next != null;
    }

    public AbstractPage getLast() {
        return last;
    }

    public boolean hasLast() {
        return last != null;
    }

    public Inventory create() {
        return createProcess();
    }

    protected abstract Inventory createProcess();

    public void setNext(AbstractPage next) {
        this.next = next;
    }

    public void setLast(AbstractPage last) {
        this.last = last;
    }
}
