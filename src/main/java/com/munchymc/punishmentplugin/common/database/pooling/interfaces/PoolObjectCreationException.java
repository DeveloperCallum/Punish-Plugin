package com.munchymc.punishmentplugin.common.database.pooling.interfaces;

public class PoolObjectCreationException extends RuntimeException {
    public PoolObjectCreationException() {
        super();
    }

    public PoolObjectCreationException(String message) {
        super(message);
    }

    public PoolObjectCreationException(String message, Throwable cause) {
        super(message, cause);
    }

    public PoolObjectCreationException(Throwable cause) {
        super(cause);
    }

    protected PoolObjectCreationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
