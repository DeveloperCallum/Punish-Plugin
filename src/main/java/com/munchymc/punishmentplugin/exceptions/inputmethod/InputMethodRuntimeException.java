package com.munchymc.punishmentplugin.exceptions.inputmethod;

public class InputMethodRuntimeException extends RuntimeException{
    public InputMethodRuntimeException(String message) {
        super(message);
    }

    public InputMethodRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
