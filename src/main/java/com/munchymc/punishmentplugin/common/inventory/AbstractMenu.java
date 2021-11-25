package com.munchymc.punishmentplugin.common.inventory;

import org.bukkit.event.Listener;

public abstract class AbstractMenu implements Listener {
    private AbstractPage first;

    public AbstractPage getFirst() {
        return first;
    }

    protected void setFirst(AbstractPage first) {
        this.first = first;
    }

    public abstract AbstractPage getNext();

    public abstract AbstractPage getLast();

    /**
     * Get the current page using recursion.
     *
     * @return The current page.
     */
    public abstract AbstractPage getCurrent();

    /**
     * Get the current page using recursion.
     *
     * @param index The page the function is currently on.
     * @param jumps The jumps left.
     * @return The current page.
     */
    protected AbstractPage getCurrentRec(AbstractPage index, int jumps) {
        if (jumps == 0) {
            return index;
        }

        return getCurrentRec(index.getNext(), --jumps);
    }
}
