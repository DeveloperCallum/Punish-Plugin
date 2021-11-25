package com.munchymc.punishmentplugin.common.inventory.input;

public interface CustomisableInputMethod<T, V> extends InputMethod<T>{
    void addCustomisation(V customise);
}
