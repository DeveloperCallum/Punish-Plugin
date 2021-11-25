package com.munchymc.punishmentplugin.common.inventory.input;

import org.bukkit.event.Listener;

public interface InputMethod<T> extends Listener {
    T getData();
    void execute(InputCallback<String> callback);
}
