package com.munchymc.punishmentplugin.exceptions.database;

public class DatabaseRuntimeException extends RuntimeException{
    public DatabaseRuntimeException(String message) {
        super(message);
    }

    public DatabaseRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public DatabaseRuntimeException(Throwable cause) {
        super(cause);
    }

    public DatabaseRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
