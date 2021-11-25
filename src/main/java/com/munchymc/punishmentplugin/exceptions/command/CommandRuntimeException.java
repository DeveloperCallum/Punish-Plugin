package com.munchymc.punishmentplugin.exceptions.command;

public class CommandRuntimeException extends RuntimeException {
    public CommandRuntimeException(String message) {
        super(message);
    }

    public CommandRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
